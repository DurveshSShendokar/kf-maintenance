(function() {
    'use strict';

    angular.module('myApp.ApprovedPPM').controller('ApprovedPPMController', ApprovedPPMController);

    ApprovedPPMController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr','genericFactory'];

    function ApprovedPPMController($scope, ApiEndpoint, $http, toastr, genericFactory) {
      
	  const baseUrl = ApiEndpoint.url;
        
        var vm = angular.extend(this, {
						addNew: addNew,
			            cancel: cancel,
						add: add,
						approve: approve,
						unapprove:unapprove,
						saveStatus: saveStatus
						
            
           
        });

        (function activate() {
            $scope.pendingApprovals = {};
            loadpendingApprovals();
			
			$scope.addNewTab = false;
           
        })();
		
		
		
				
		
		
		

		function cancel() {
		          $scope.addNewTab = false;
		      }

		      function addNew() {
		          $scope.addNewTab = true;
		         
		      }
			  
			function saveStatus(checklistItem) {
			      console.log("Updating Checklist Status...");
			      console.log("maintCheckPointId:", checklistItem.maintCheckPointId);
			      console.log("status:", checklistItem.status);

			      if (!checklistItem.maintCheckPointId || !checklistItem.status) {
			          console.error("Error: Missing Parameters - maintCheckPointId or status is undefined/null");
			          return;
			      }

			      $http({
			          method: "POST",
			          url: "/maint/updateChecklistStatus", 
			          headers: {
			              'Content-Type': 'application/x-www-form-urlencoded'
			          },
			          data: $.param({
			              maintCheckPointId: checklistItem.maintCheckPointId,
			              status: checklistItem.status
			          })
			      }).then(function(response) {
			          console.log("Success:", response.data);
			          checklistItem.editMode = false;
			      }).catch(function(error) {
			          console.error("Error:", error);
			      });
			  };


			  
			  function loadChecklist(maintId) {
			  		    var url = baseUrl + "/maint/getChecklistByMaintId/" + maintId;
			  		    $http.get(url)
			  		        .then(function(response) {
			  		            vm.loadChecklist = response.data.checklist; 
			  		            console.log("Fetched checklist:", vm.loadChecklist);
			  		        })
			  		        .catch(function(error) {
			  		            console.error("Error fetching checklist:", error);
			  		        });
			  		}
					
					function add(approval) {
								    $scope.addNewTab = true; 
								    $scope.equipment = approval.machine.eqid; 
									$scope.overallStatus = approval.overall_status;
								    $scope.pendingApprovals = Object.assign({}, approval);
									vm.executiveRemark = "";
									
								    loadChecklist(approval.maint_id);
								}


		function loadpendingApprovals() {
			    var url = baseUrl + "/maint/pendingClosedApprovals";
			    $http.get(url)
			        .then(function(response) {
			            vm.loadpendingApprovals = response.data.datas;
			            console.log("Fetched loadpendingApprovals:", vm.loadpendingApprovals);
			        })
			        .catch(function(error) {
			            console.error("Error fetching loadpendingApprovals:", error);
			        });
			}

		
			
			
			function approve(department,executiveRemark) {
				if (!executiveRemark || executiveRemark.trim() === "") {
			        toastr.error("Please provide a reason for approving.");
					 return;
			    }
			    var url = baseUrl + "/maint/updateClosedApproval";
			    var payload = {
			        maintId: department.maint_id,  
					executiveRemark: executiveRemark
			    };

			    $http.post(url, null, { params: payload })
			        .then(function(response) {
			            toastr.success("Maintenance approved successfully!");
			            loadpendingApprovals(); 
			        })
			        .catch(function(error) {
			            toastr.success("Maintenance approved successfully!");
			          
						$scope.addNewTab = false; 
						// Refresh the entire page
						         setTimeout(function() {
						             location.reload();
						         }, 500);
			        });
			}
			
			
			function unapprove(department, executiveRemark) {
			    if (!executiveRemark || executiveRemark.trim() === "") {
			        toastr.error("Please provide a reason for unapproving.");
			        return;
			    }

			    var url = baseUrl + "/maint/unapproveMaintenance";
			    var payload = {
			        maintId: department.maint_id,
			        executiveRemark: executiveRemark
			    };

			    $http.post(url, null, { params: payload })
			        .then(function(response) {
			            toastr.success("Maintenance unapproved successfully!");
			            $scope.addNewTab = false;
			            loadpendingApprovals();  
			        })
			        .catch(function(error) {
						toastr.success("Maintenance unapproved successfully!");
								            $scope.addNewTab = false;
						// Refresh the entire page
						   setTimeout(function() {
						   location.reload();
						      }, 500); 
						   });												
			}

			

			


   
       
    }
})();
