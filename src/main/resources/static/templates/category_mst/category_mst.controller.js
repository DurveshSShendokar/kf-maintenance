(function() {
    'use strict';

    angular.module('myApp.category_mst').controller('category_mstController', category_mstController);

    category_mstController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr','genericFactory'];

    function category_mstController($scope, ApiEndpoint, $http, toastr, genericFactory) {
      
	   const baseUrl = ApiEndpoint.url;
        
        var vm = angular.extend(this, {
            categories: [],
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
            $scope.Category = {};
            loadCategories();
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
        
           function loadCategories() {
            var url = baseUrl + "/category/list";
            $http.get(url)
                .then(function(response) {
                    vm.categories = response.data;
                    console.log("Fetched categories:", vm.categories);
                })
                .catch(function(error) {
                    console.error("Error fetching categories:", error);
                });
        }

/*
				  function delet(category) {
            var url = baseUrl + "/category/delete/" + category.cat_id;
            console.log("Delete URL:", url);
            $http.delete(url)
                .then(function(response) {
                    toastr.success('Deleted Category Successfully!');
                    loadCategories();
                })
                .catch(function(error) {
                   toastr.error('Cannot delete or update a parent row');
                    console.error("Error deleting Category:", error);
                });
        }
        */
	   
	   	let selectedcategory = null;
       function delet(category) {
    selectedcategory = category;
    vm.showModal = true; // Show the confirmation modal
}

function confirmDelete() {
    if (!selectedcategory) return;

     var url = baseUrl + "/category/delete/" + selectedcategory.cat_id;
    $http.delete(url)
        .then(function(response) {
            toastr.success('Deleted Category successfully!');
            loadCategories();
        })
        .catch(function(error) {
            toastr.error('Error deleting Category');
            console.error("Error deleting Category:", error);
        })
        .finally(function() {
            vm.showModal = false; // Hide the modal after confirmation
        });
}

function cancelDelete() {
    vm.showModal = false; // Hide the modal without taking any action
}

		

        function ok(category) {
            var url = baseUrl + "/category/addCategory";
            var msg = "";
            
            // Check if all fields are filled
            if (!category.cat_name || !category.code) {
                toastr.error('Fill all the fields!');
                return;
            }

            genericFactory.add(msg, url, category)
                .then(function(response) {
                    vm.saved = response.data;
                    console.log("checking Categorys:", vm.saved);

                    if (response.status === 200) {
                        toastr.success('Category added successfully!');
                        loadCategories();
                        $scope.addNewTab = false;
                        resetFormFields(); 
                    } else {
                        toastr.error('Failed to add Category');
                    }
                })
                .catch(function(error) {
                    toastr.error('Failed to add Category');
                    console.error("Error adding Category:", error);
                });
        }

        function resetFormFields() {
            $scope.Category = {}; // Reset the lab object to clear the form fields
        }

        function add(Category) {
            $scope.addNewTab = true;
            $scope.Category = Object.assign({}, Category);
        }

      
      
			
					
				

			
			
			
   
       
    }
})();
