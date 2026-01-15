(function() {
    'use strict';

    angular.module('myApp.AssetReAllocation').controller('AssetReAllocationController', AssetReAllocationController);

    AssetReAllocationController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr', 'genericFactory'];

    function AssetReAllocationController($scope, ApiEndpoint, $http, toastr, genericFactory) {

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
            $scope.AssetReAllocation = {};
            loadReAssetAllocations();
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

        function loadReAssetAllocations() {
            var url = baseUrl + "/assetAllocation/getAssetAllocationHistory";
            $http.get(url)
                .then(function(response) {
                    vm.allocations = response.data;
                    console.log("Fetched AssetAllocations:", vm.allocations);
                })
                .catch(function(error) {
                    console.error("Error fetching AssetAllocations:", error);
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

		function ReAllocate(assetAllocation) {
		    if (!$scope.AssetReAllocation.allocateTo) {
		        toastr.error('Please select an Engineer before reallocation');
		        return;
		    }

		    var userId = $scope.AssetReAllocation.allocateTo.id; 
			var historyId = $scope.AssetReAllocation.history_id; 
		    var url = baseUrl + "/assetAllocation/reallocateAsset/" + historyId + "?userId=" + userId;

		    console.log("Re-Allocate URL:", url);

		    $http.post(url)
		        .then(function(response) {
		            if (response.data.code === 200) {
		                toastr.success('Asset Reallocated Successfully!');
		                $scope.addNewTab = false;
		                loadReAssetAllocations();
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
            $scope.AssetReAllocation = {};
        }

        function add(AssetReAllocation) {
            $scope.addNewTab = true;
            $scope.AssetReAllocation = Object.assign({}, AssetReAllocation);
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
