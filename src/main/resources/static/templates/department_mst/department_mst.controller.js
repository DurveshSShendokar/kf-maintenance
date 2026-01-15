(function() {
    'use strict';

    angular.module('myApp.department_mst').controller('department_mstController', department_mstController);

    department_mstController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr','genericFactory'];

    function department_mstController($scope, ApiEndpoint, $http, toastr, genericFactory) {
      
	  const baseUrl = ApiEndpoint.url;
        
        var vm = angular.extend(this, {
            Departments: [],
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
            $scope.Department = {};
            loadDepartments();
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


		function loadDepartments() {
			    var url = baseUrl + "/department/all";
			    $http.get(url)
			        .then(function(response) {
			            vm.departments = response.data;
			            console.log("Fetched departments:", vm.departments);
			        })
			        .catch(function(error) {
			            console.error("Error fetching departments:", error);
			        });
			}
      
      
			
		/*	
				  function delet(department) {
            var url = baseUrl + "/department/delete/" + department.departmentId;
            console.log("Delete URL:", url);
            $http.delete(url)
                .then(function(response) {
                    toastr.success('Deleted department Successfully!');
                    loadDepartments();
                })
                .catch(function(error) {
                    toastr.error('Cannot delete or update a parent row');
                    console.error("Error deleting department:", error);
                });
        }*/
        
        	let selecteddepartment = null;
       function delet(department) {
    selecteddepartment = department;
    vm.showModal = true; // Show the confirmation modal
}

function confirmDelete() {
    if (!selecteddepartment) return;

    var url = baseUrl + "/department/delete/" + selecteddepartment.departmentId;

    $http.delete(url)
        .then(function(response) {
            toastr.success('Deleted Department successfully!');
            loadDepartments();
        })
        .catch(function(error) {
            toastr.error('Error deleting Department');
            console.error("Error deleting Department:", error);
        })
        .finally(function() {
            vm.showModal = false; // Hide the modal after confirmation
        });
}

function cancelDelete() {
    vm.showModal = false; // Hide the modal without taking any action
}

		

        function ok(department) {
            var url = baseUrl + "/department/addDepartment1";
            var msg = "";
            
            // Check if all fields are filled
            if (!department.departmentName || !department.code) {
                toastr.error('Fill all the fields!');
                return;
            }

            genericFactory.add(msg, url, department)
                .then(function(response) {
                    vm.saved = response.data;
                    console.log("checking department:", vm.saved);

                    if (response.status === 200) {
                        toastr.success('department added successfully!');
                        loadDepartments();
                        $scope.addNewTab = false;
                        resetFormFields(); 
                    } else {
                        toastr.error('Failed to add department');
                    }
                })
                .catch(function(error) {
                    toastr.error('Failed to add department');
                    console.error("Error adding department:", error);
                });
        }

       function resetFormFields() {
            $scope.Department = {}; // Reset the lab object to clear the form fields
        }

        function add(Department) {
            $scope.addNewTab = true;
            $scope.Department = Object.assign({}, Department);
        }

      
      

					
				

			
			
			
   
       
    }
})();
