(function() {
    'use strict';

    angular.module('myApp.lab_mst').controller('lab_mstController', lab_mstController);

    lab_mstController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr','genericFactory'];

    function lab_mstController($scope, ApiEndpoint, $http, toastr, genericFactory) {
      
        const baseUrl = ApiEndpoint.url;
        
        var vm = angular.extend(this, {
            labs: [],
            add: add,
            delet: delet,
            ok: ok,
            addNew: addNew,
            cancel: cancel,
            confirmDelete: confirmDelete,
             cancelDelete:cancelDelete,
            resetFormFields: resetFormFields
        });

        (function activate() {
            $scope.Lab = {};
            loadLabs();
             vm.showModal = false;
            $scope.addNewTab = false;
        })();

        function cancel() {
            $scope.addNewTab = false;
        }

        function addNew() {
            $scope.addNewTab = true;
            resetFormFields(); // Reset form fields when adding new
        }

        function loadLabs() {
            var url = baseUrl + "/lab/all";
            $http.get(url)
                .then(function(response) {
                    vm.labs = response.data;
                    console.log("Fetched labs:", vm.labs);
                })
                .catch(function(error) {
                    console.error("Error fetching labs:", error);
                });
        }

       /* function delet(lab) {
            var url = baseUrl + "/lab/delete/" + lab.lab_id;
            console.log("Delete URL:", url);
            $http.delete(url)
                .then(function(response) {
                    toastr.success('Deleted lab Successfully!');
                    loadLabs();
                })
                .catch(function(error) {
                    toastr.error('Cannot delete or update a parent row');
                    console.error("Error deleting lab:", error);
                });
        }*/
        
        let selectedlab = null;
       function delet(lab) {
    selectedlab = lab;
    vm.showModal = true; // Show the confirmation modal
}

function confirmDelete() {
    if (!selectedlab) return;

   var url = baseUrl + "/lab/delete/" + selectedlab.lab_id;

    $http.delete(url)
        .then(function(response) {
            toastr.success('Deleted Lab successfully!');
            loadLabs();
        })
        .catch(function(error) {
            toastr.error('Error deleting Lab');
            console.error("Error deleting Lab:", error);
        })
        .finally(function() {
            vm.showModal = false; // Hide the modal after confirmation
        });
}

function cancelDelete() {
    vm.showModal = false; // Hide the modal without taking any action
}


        function ok(lab) {
            var url = baseUrl + "/lab/addLab";
            var msg = "";
            
            // Check if all fields are filled
            if (!lab.labName || !lab.labCode) {
                toastr.error('Fill all the fields!');
                return;
            }

            genericFactory.add(msg, url, lab)
                .then(function(response) {
                    vm.saved = response.data;
                    console.log("checking labs:", vm.saved);

                    if (response.status === 200) {
                        toastr.success('Lab added successfully!');
                        loadLabs();
                        $scope.addNewTab = false;
                        resetFormFields(); 
                    } else {
                        toastr.error('Failed to add lab');
                    }
                })
                .catch(function(error) {
                    toastr.error('Failed to add lab');
                    console.error("Error adding lab:", error);
                });
        }

        function resetFormFields() {
            $scope.Lab = {}; // Reset the lab object to clear the form fields
        }

        function add(Lab) {
            $scope.addNewTab = true;
            $scope.Lab = Object.assign({}, Lab);
        }
    }
})();
