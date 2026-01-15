(function() {
    'use strict';

    angular.module('myApp.SpareStocking').controller('SpareStockingController', SpareStockingController);

    SpareStockingController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr','genericFactory'];

    function SpareStockingController($scope, ApiEndpoint, $http, toastr, genericFactory) {
      
       const baseUrl = ApiEndpoint.url;
      
        
        var vm = angular.extend(this, {
            SpareStock: [],
            add: add,
            delet: delet,
            ok: ok,
            addNew: addNew,
            confirmDelete: confirmDelete,
             cancelDelete:cancelDelete,
            cancel: cancel,
            resetFormFields: resetFormFields
        });

        (function activate() {
            $scope.SpareStock = {};
            loadSparestock();
             loadSpares();
            $scope.addNewTab = false;
        })();

        function cancel() {
            $scope.addNewTab = false;
        }

        function addNew() {
            $scope.addNewTab = true;
            resetFormFields(); // Reset form fields when adding new
        }


			 function loadSparestock() {
			    var url = baseUrl + "/spareStocking/allspareStocking";
			    $http.get(url)
			        .then(function(response) {
			            vm.spareStocks = response.data;
			            console.log("Fetched spareStocking:", vm.spareStocks);
			        })
			        .catch(function(error) {
			            console.error("Error fetching spareStocking:", error);
			        });
			}
			
      /*
      	   function delet(spareStock) {
            var url = baseUrl + "/spareStock/delete/" + spareStock.spare_stock_id;
            console.log("Delete URL:", url);
            $http.delete(url)
                .then(function(response) {
                    toastr.success('Deleted SpareStock Successfully!');
                    loadSparestock();
                })
                .catch(function(error) {
                    toastr.error('Error deleting SpareStock');
                    console.error("Error deleting SpareStock:", error);
                });
        }*/
        
    /*    	let selectedspareStock = null;
       function delet(spareStock) {
    selectedspareStock = spareStock;
    vm.showModal = true; // Show the confirmation modal
}

function confirmDelete() {
    if (!selectedspareStock) return;

    var url = baseUrl + "/spareStocking/delete/" + selectedspareStock.spare_stocking_id;
    $http.delete(url)
        .then(function(response) {
            toastr.success('Deleted sparestock successfully!');
            loadSparestock();
        })
        .catch(function(error) {
            toastr.error('Error deleting sparestock');
            console.error("Error deleting sparestock:", error);
        })
        .finally(function() {
            vm.showModal = false; // Hide the modal after confirmation
        });
}

function cancelDelete() {
    vm.showModal = false; // Hide the modal without taking any action
}
*/


		
        
        	let selectedspareStock = null;
       function delet(spareStock) {
    selectedspareStock = spareStock;
    vm.showModal = true; // Show the confirmation modal
}
function confirmDelete() {
	
	console.log("Selected spare stock:", selectedspareStock);
    if (!selectedspareStock || !selectedspareStock.spareStockingId || isNaN(selectedspareStock.spareStockingId)) {
        toastr.error('Spare stock ID is not valid');
        return;
    }

    var url = baseUrl + "/spareStocking/delete/" + selectedspareStock.spareStockingId;
    $http.delete(url)
        .then(function(response) {
            toastr.success('Deleted spare stock successfully!');
            loadSparestock();
        })
        .catch(function(error) {
            toastr.error('Error deleting spare stock');
            console.error("Error deleting spare stock:", error);
        })
        .finally(function() {
            vm.showModal = false; 
        });
}

function cancelDelete() {
    vm.showModal = false; 
}

		
        
        
        
        
        
        
			 function loadSpares() {
			    var url = baseUrl + "/spares/getSparesByLimit?pageNo=1&perPage=10";
			    $http.get(url)
			        .then(function(response) {
			            vm.spare = response.data;
			            console.log("Fetched spare:", vm.spare);
			        })
			        .catch(function(error) {
			            console.error("Error fetching spares:", error);
			        });
			}
        

        function ok(spareStock) {
            var url = baseUrl + "/spareStocking/addSpareStocking";
            var msg = "";
            
            // Check if all fields are filled
            if ( !spareStock.stockingQuantity || !spareStock.unitPrice ) {
                toastr.error('Fill all the fields!');
                return;
            }
 console.log("spare:", JSON.stringify(spareStock));
            genericFactory.add(msg, url, spareStock)
                .then(function(response) {
                    vm.saved = response.data;
                    console.log("checking SpareStock:", vm.saved);

                    if (response.status === 200) {
                        toastr.success('SpareStock added successfully!');
                        loadSparestock();
                        $scope.addNewTab = false;
                        resetFormFields(); 
                    } else {
                        toastr.error('Failed to add SpareStock');
                    }
                })
                .catch(function(error) {
                    toastr.error('Failed to add SpareStock');
                    console.error("Error adding SpareStock:", error);
                });
        }

        function resetFormFields() {
            $scope.SpareStock = {}; // Reset the lab object to clear the form fields
        }

        function add(SpareStock) {
            $scope.addNewTab = true;
            $scope.SpareStock = Object.assign({}, SpareStock);
        }
        
        

      


          }
})();
