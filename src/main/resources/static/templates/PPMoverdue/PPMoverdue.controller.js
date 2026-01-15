(function() {
    'use strict';

    angular.module('myApp.PPMoverdue').controller('PPMoverdueController', PPMoverdueController);

    PPMoverdueController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr','genericFactory','localStorageService'];

    function PPMoverdueController($scope, ApiEndpoint, $http, toastr, genericFactory,localStorageService) {
      
        const baseUrl = ApiEndpoint.url;
        	var userDetail = localStorageService.get(ApiEndpoint.userKey);
        var vm = angular.extend(this, {
            
           
            addNew: addNew,
            cancel: cancel,
             add: add,
            saveOverduePPM: saveOverduePPM,
			
			downloadMaintImage:downloadMaintImage,
			getMaintImages:getMaintImages,
						openUploadModal:openUploadModal,
						uploadMaintImages:uploadMaintImages,
						closeUploadModal:closeUploadModal
        });

        (function activate() {
			$scope.showEdit = false;
            $scope.Complaint = {};
          loadComplaints();
		  
		//  $scope.actionDoneBy = $scope.userDetails;
           loadChecklist();
          
            $scope.addNewTab = false;
        })();

        function cancel() {
            $scope.addNewTab = false;
        }

        function addNew() {
            $scope.addNewTab = true;
           
        }
		
		
		
		vm.selectedMaintId = null;
			   vm.showUploadModal = false;
			   

			   function getMaintImages(maintId) {
			       $http.get(baseUrl + "/maint/getMaintImages/" + maintId)
			           .then(function(response) {
			               vm.selectedMaintImages = response.data;
			               vm.showImagesModal = true;
			               vm.selectedMaintIdForImages = maintId;
			           })
			           .catch(function(error) {
			               console.error("Error fetching images:", error);
			               alert("Could not load images.");
			           });
			   }

			   vm.getMaintImages = getMaintImages;
			
			   function downloadMaintImage(maintId, imageId) {
			       const downloadUrl = baseUrl + "/maint/downloadMaintImage?maintId=" + maintId + "&imageId=" + imageId;
			       const link = document.createElement('a');
			       link.href = downloadUrl;
			       link.download = ''; // let server control filename
			       document.body.appendChild(link);
			       link.click();
			       document.body.removeChild(link);
			   }

			 
			

			   function openUploadModal(maintId) {
			          console.log("Opening modal for maintId:", maintId);
			          vm.selectedMaintId = maintId;
			          vm.showUploadModal = true;
			      }

			      function closeUploadModal() {
			          vm.showUploadModal = false;
					  vm.selectedFiles = [];
			         // document.getElementById('uploadFiles').value = ""; // clear file input
			      }


				  // Upload selected images for the selected maintenance entry
				  function uploadMaintImages() {
				      if (!vm.selectedFiles || vm.selectedFiles.length === 0) {
				          alert("Please select at least one file to upload.");
				          return;
				      }

				      var formData = new FormData();
				      for (var i = 0; i < vm.selectedFiles.length; i++) {
				          formData.append("files", vm.selectedFiles[i]);
				      }

				      $http.post(baseUrl + "/maint/uploadMaintImages/" + vm.selectedMaintId, formData, {
				          transformRequest: angular.identity,
				          headers: { 'Content-Type': undefined }
				      }).then(function(response) {
				          alert("Upload successful!");
				          vm.closeUploadModal();
				      }).catch(function(error) {
				          console.error("Upload error:", error);
				          alert("Upload failed. Please try again.");
				      });
				  }

        
        
		function loadComplaints() {
		    var url = baseUrl + "/maint/getCurrentWeekOverduesMaintenence";
		    
		    $http.get(url)
		        .then(function(response) {
		            vm.complaints = response.data;
		            console.log("Fetched Complaints:", vm.complaints);

		            if (vm.complaints.length > 0) {
		                var complaint = vm.complaints[0]; // Load checklist for the first complaint
		                
		                if (complaint.machine && complaint.machine.machine_id && complaint.frequency) {
		                    loadChecklist(complaint.machine.machine_id, complaint.frequency);
		                } else {
		                  //  toastr.error("Invalid Machine ID or Frequency. Checklist cannot be loaded.");
		                }
		            } else {
		                toastr.warning("No complaints found.");
		            }
		        })
		        .catch(function(error) {
		            console.error("Error fetching complaints:", error);
		        });
		}

		function loadChecklist(machine_id, freq) {
		   

		    if (!machine_id || !freq) {
		       // toastr.error("Invalid Machine ID or Frequency. Checklist cannot be loaded.");
		        return;
		    }

		    var url = baseUrl + "/checklist/getchecklist?machine_id=" + machine_id + "&freq=" + freq;
		    console.log("Fetching checklist from:", url);
		    
		    $http.get(url)
		        .then(function(response) {
		            vm.checklist = response.data;
		            console.log("Fetched Checklist:", vm.checklist);

		            if (vm.checklist.length === 0) {
		               // toastr.warning("No checklist found for the selected machine and frequency.");
		            }
		        })
		        .catch(function(error) {
		            console.error("Error fetching checklist:", error);
		            toastr.error("Failed to fetch checklist.");
		        });
		}

        
        
        
		function saveOverduePPM() {
		          if (!vm.checklist || vm.checklist.length === 0) {
		              toastr.error("No checklist data available.");
		              return;
		          }

		          let requestData = {
		              maint_id: $scope.Complaint.maint_id,
		              overall_status:  vm.observation,
					  techmark:  vm.TechMark,
					  "spare_used": vm.spare_used,
		              done_by: { id: userDetail.id },
		              checkpointMaaint: vm.checklist.map(item => ({
		                  checkpoint: item.checklist_id, 
		                  value: item.status 
		              }))
		          };

		          var url = baseUrl + "/maint/updateOverdueMaint";
		          genericFactory.add("Saving Overdue PPM", url, requestData)
		              .then(function(response) {
		                  if (response.status === 200) {
		                      toastr.success("Overdue PPM saved successfully!");
		                      loadComplaints();
		                      $scope.addNewTab = false;
							  vm.observation = "";
		                  } else {
		                      toastr.error("Failed to save Overdue PPM.");
		                  }
		              })
		              .catch(function(error) {
		                  console.error("Error saving Overdue PPM:", error);
		                  toastr.error("Failed to save Overdue PPM.");
		              });
		      }
           
        
       

		function add(complaint) {
			console.log("Complaint data received in add function:", complaint);

			           if (!complaint.machine || !complaint.machine.machine_id) {
			               console.error("Error: Machine ID is undefined");
			               toastr.error("Machine ID is missing.");
			               return;
			           }
			           
			           if (!complaint.frequency) {
			               console.error("Error: Frequency is undefined");
			               toastr.error("Frequency is missing.");
			               return;
			           }
					   $scope.equipment = complaint.machine.eqid; 
					   $scope.raisedBy = complaint.raisedBy.firstName +'_'+ complaint.raisedBy.lastName;
			           $scope.addNewTab = true;
			           $scope.Complaint = Object.assign({}, complaint);
			           loadChecklist(complaint.machine.machine_id, complaint.frequency);
			       };	

      

        

        

}
})();
