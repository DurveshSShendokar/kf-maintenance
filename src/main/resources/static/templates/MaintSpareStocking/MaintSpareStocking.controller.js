(function() {
    'use strict';

    angular.module('myApp.MaintSpareStocking').controller('MaintSpareStockingController', MaintSpareStockingController);

    MaintSpareStockingController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr','genericFactory'];

    function MaintSpareStockingController($scope, ApiEndpoint, $http, toastr, genericFactory) {
      
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
            resetFormFields(); 
        }


			 function loadSparestock() {
			    var url = baseUrl + "/MaintSparestock/allMaintspareStocking";
			    $http.get(url)
			        .then(function(response) {
			            vm.spareStocks = response.data;
			            console.log("Fetched spareStocking:", vm.spareStocks);
			        })
			        .catch(function(error) {
			            console.error("Error fetching spareStocking:", error);
			        });
			}
			
        
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

    var url = baseUrl + "/MaintSparestock/delete/" + selectedspareStock.spareStockingId;
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
			    var url = baseUrl + "/maintspares/list";
			    $http.get(url)
			        .then(function(response) {
			            vm.maintspare = response.data;
			            console.log("Fetched maintspare:", vm.maintspare);
			        })
			        .catch(function(error) {
			            console.error("Error fetching maintspare:", error);
			        });
			}
        

        function ok(spareStock) {
            var url = baseUrl + "/MaintSparestock/addSpareStocking";
            var msg = "";
            
           
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
