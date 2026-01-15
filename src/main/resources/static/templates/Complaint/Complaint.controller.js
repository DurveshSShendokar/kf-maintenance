(function() {
    'use strict';

    angular.module('myApp.Complaint').controller('ComplaintController', ComplaintController);

    ComplaintController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr','genericFactory','localStorageService'];

    function ComplaintController($scope, ApiEndpoint, $http, toastr, genericFactory,localStorageService) {
      var userDetail = localStorageService.get(ApiEndpoint.userKey);
        const baseUrl = ApiEndpoint.url;
        
        var vm = angular.extend(this, {
            complaints: [],
            add: add,
           
            ok: ok,
            addNew: addNew,
            cancel: cancel,
            resetFormFields: resetFormFields,
             exporty: exporty
        });


        (function activate() {
            $scope.Complaint = {};
            loadComplaints();
            loadAssets();
            $scope.addNewTab = false;
        })();

        function cancel() {
            $scope.addNewTab = false;
        }

         function addNew() {
            $scope.addNewTab = true;
            resetFormFields(); // Reset form fields when adding new
        }
        
           function loadComplaints() {
            var url = baseUrl + "/complaints/all";
            $http.get(url)
                .then(function(response) {
                    vm.complaints = response.data;
                    console.log("Fetched complaints:", vm.complaints);
                })
                .catch(function(error) {
                    console.error("Error fetching complaints:", error);
                });
        }
		 
	
		function loadAssets() {
		          var url = baseUrl + "/assetInventory/all";
		          $http.get(url)
		              .then(function(response) {
		                  vm.assets = response.data;
		                  console.log("Fetched assets:", vm.assets);
		                 
		              })
		              .catch(function(error) {
		                  console.error("Error fetching assets:", error);
		              });
		      }

        function ok(complaint) {
            var url = baseUrl + "/complaints/addComplaint";
            var msg = "";
            
        
            
             if ( !complaint.priority  || !complaint.cause  || !complaint.description ) {
                toastr.error('Fill all the fields!');
                return;
            }
complaint.raisedBy=userDetail

 console.log("adding complaint:", JSON.stringify(complaint));
            genericFactory.add(msg, url, complaint)
                .then(function(response) {
                    vm.saved = response.data;
                    console.log("checking complaint:", vm.saved);

                    if (response.status === 200) {
                        toastr.success('complaint added successfully!');
                        loadComplaints();
                        $scope.addNewTab = false;
                        resetFormFields(); 
                    } else {
                        toastr.error('Failed to add complaint');
                    }
                })
                .catch(function(error) {
                    toastr.error('Failed to add complaint');
                    console.error("Error adding complaint:", error);
                });
        }
           
        
        function resetFormFields() {
            $scope.Complaint = {}; // Reset the complaint object to clear the form fields
        }

        function add(Complaint) {
            $scope.addNewTab = true;
            $scope.Complaint = Object.assign({}, Complaint);
        }
        
        


  // for export excel function 
		       function exporty() {
		            var url = baseUrl + "/complaints/complaint_export";
		
		            $http.get(url, { responseType: 'blob' })
		                .then(function(response) {
							  console.log("Export response data:", response.data);
		                    var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
		                    var downloadLink = document.createElement('a');
		                    downloadLink.href = window.URL.createObjectURL(blob);
		                    downloadLink.download = 'complaints.xlsx';
		                    document.body.appendChild(downloadLink);
		                    downloadLink.click();
		                    document.body.removeChild(downloadLink);
		                })
		                .catch(function(error) {
		                    toastr.error('Error exporting data');
		                    console.error("Error exporting data:", error);
		                });
		        }
		        
		        $scope.handleFileSelect = (files)=>{
					$scope.selectedFile = files[0];
					console.log("File: ", $scope.selectedFile);
				}
		
				

       
        
        

          }
})();
