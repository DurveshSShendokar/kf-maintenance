(function() {
    'use strict';

    angular.module('myApp.ComplaintReAllocation').controller('ComplaintReAllocationController', ComplaintReAllocationController);

    ComplaintReAllocationController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr', 'genericFactory'];

    function ComplaintReAllocationController($scope, ApiEndpoint, $http, toastr, genericFactory) {

        const baseUrl = ApiEndpoint.url;

        var vm = angular.extend(this, {
            allocations: [],
            employees: [],
          
            add: add,
         
            addNew: addNew,
            cancel: cancel,
          
            resetFormFields: resetFormFields,
			ReAllocate:ReAllocate,
            
            getUniqueEmployeeNames: getUniqueEmployeeNames
        });

        (function activate() {
            $scope.ComplaintReAllocation = {};
            loadReComplaintAllocations();
            loadEmployees();
          
            $scope.addNewTab = false;
        })();

        function cancel() {
            $scope.addNewTab = false;
        }

        function addNew() {
            $scope.addNewTab = true;
            resetFormFields(); // Reset form fields when adding new
        }

        function loadReComplaintAllocations() {
            var url = baseUrl + "/complaints/getReAllocateComplaints";
            $http.get(url)
                .then(function(response) {
                    vm.allocations = response.data;
                    console.log("Fetched ComplaintAllocations:", vm.allocations);
                })
                .catch(function(error) {
                    console.error("Error fetching ComplaintAllocations:", error);
                });
        }

      

        function loadEmployees() {
            var url = baseUrl + "/user/getITEnginner";
            $http.get(url)
                .then(function(response) {
                    vm.employees = response.data;
                    console.log("Fetched ITEnginner:", vm.employees);
                    vm.uniqueEmployeeNames = getUniqueEmployeeNames();
                })
                .catch(function(error) {
                    console.error("Error fetching employees:", error);
                });
        }

		function ReAllocate(ComplaintReAllocation) {
		   
		    var engineerId = $scope.ComplaintReAllocation.complaint.allocateTo.id; 
			var complaintId = $scope.ComplaintReAllocation.complaint.comp_id; 
			var url = baseUrl + "/complaints/reAllocateComplaint?complaintId=" + complaintId + "&engineerId=" + engineerId;

		    console.log("Re-Allocate URL:", url);

		    $http.post(url)
		        .then(function(response) {
		            if (response.data.status === 200) {
		                toastr.success('Asset Reallocated Successfully!');
		                $scope.addNewTab = false;
		                loadReComplaintAllocations();
		            } else {
		                toastr.error(response.data.message || 'Error in Reallocation');
		            }
		        })
		        .catch(function(error) {
		            toastr.error('Error Reallocating Asset');
		            console.error("Error Re-Allocate Asset:", error);
		        });
		}


      
        function resetFormFields() {
            $scope.ComplaintReAllocation = {};
        }

        function add(ComplaintReAllocation) {
            $scope.addNewTab = true;
            $scope.ComplaintReAllocation = Object.assign({}, ComplaintReAllocation);
        }

		// Function to filter unique employee names
		      function getUniqueEmployeeNames() {
		          var uniqueEmployeeNames = [];
		          vm.employees.forEach(function(employee) {
		              var fullName = employee.firstName + ' ' + employee.lastName;
		              if (uniqueEmployeeNames.indexOf(fullName) === -1) {
		                  uniqueEmployeeNames.push(fullName);
		              }
		          });
		          return uniqueEmployeeNames;
		      }

    }
})();
