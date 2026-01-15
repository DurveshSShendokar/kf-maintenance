(function() {
    'use strict';

    angular.module('myApp.AssetAllocation').controller('AssetAllocationController', AssetAllocationController);

    AssetAllocationController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr', 'genericFactory'];

    function AssetAllocationController($scope, ApiEndpoint, $http, toastr, genericFactory) {

        const baseUrl = ApiEndpoint.url;

        var vm = angular.extend(this, {
            allocations: [],
            employees: [],
            assets: [],
            add: add,
            delet: delet,
            ok: ok,
            addNew: addNew,
            cancel: cancel,
            confirmDelete: confirmDelete,
             cancelDelete:cancelDelete,
            resetFormFields: resetFormFields,
			deAllocate:deAllocate,
            
            getUniqueEmployeeNames: getUniqueEmployeeNames
        });

        (function activate() {
            $scope.AssetAllocation = {};
            loadAssetAllocations();
            loadEmployees();
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

        function loadAssetAllocations() {
            var url = baseUrl + "/assetAllocation/AllocatedAssets";
            $http.get(url)
                .then(function(response) {
                    vm.allocations = response.data;
                    console.log("Fetched AssetAllocations:", vm.allocations);
                })
                .catch(function(error) {
                    console.error("Error fetching AssetAllocations:", error);
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

        function deAllocate(assetAllocation) {
            var url = baseUrl + "/assetAllocation/deallocateAsset/" + assetAllocation.asset_allocation_id;
            console.log("De-Allocate URL:", url);
            $http.post(url)
                .then(function(response) {
                    toastr.success('De-Allocate AssetAllocation Successfully!');
                    loadAssetAllocations();
                })
                .catch(function(error) {
                    toastr.error('Error De-Allocate AssetAllocation');
                    console.error("Error De-Allocate AssetAllocation:", error);
                });
        }



let selectedassetAllocation = null;
       function delet(assetAllocation) {
    selectedassetAllocation = assetAllocation;
    vm.showModal = true; // Show the confirmation modal
}

function confirmDelete() {
    if (!selectedassetAllocation) return;

    var url = baseUrl + "/assetAllocation/delete/" + selectedassetAllocation.asset_allocation_id;

    $http.delete(url)
        .then(function(response) {
            toastr.success('Deleted Asset Allocation successfully!');
            loadAssetAllocations();
        })
        .catch(function(error) {
            toastr.error('Error deleting Allocation');
            console.error("Error deleting Allocation:", error);
        })
        .finally(function() {
            vm.showModal = false; // Hide the modal after confirmation
        });
}

function cancelDelete() {
    vm.showModal = false; // Hide the modal without taking any action
}

        function ok(assetAllocation) {
            var url = baseUrl + "/assetAllocation/addAssetAllocation";
            var msg = "";

            // Check if all fields are filled
            if ( !assetAllocation.allocateTo || !assetAllocation.assetInventory) {
                toastr.error('Fill all the fields!');
                return;
            }

            genericFactory.add(msg, url, assetAllocation)
                .then(function(response) {
                    vm.saved = response.data;
                    console.log("checking AssetAllocations:", vm.saved);

                    if (response.status === 200) {
                        toastr.success('AssetAllocation added successfully!');
                        loadAssetAllocations();
                        $scope.addNewTab = false;
                        resetFormFields();
                    } else {
                        toastr.error('Failed to add AssetAllocation');
                    }
                })
                .catch(function(error) {
                    toastr.error('Failed to add AssetAllocation');
                    console.error("Error adding AssetAllocation:", error);
                });
        }

        function resetFormFields() {
            $scope.AssetAllocation = {};
        }

        function add(AssetAllocation) {
            $scope.addNewTab = true;
            $scope.AssetAllocation = Object.assign({}, AssetAllocation);
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
