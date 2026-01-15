(function() {
    'use strict';

    angular.module('myApp.location_mst').controller('location_mstController', location_mstController);

    location_mstController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr','genericFactory'];

    function location_mstController($scope, ApiEndpoint, $http, toastr, genericFactory) {
      
	const baseUrl = ApiEndpoint.url;
	
        var vm = angular.extend(this, {
           
           
           
         
            ok : ok,
           add: add,
           delet: delet,
           confirmDelete: confirmDelete,
             cancelDelete:cancelDelete,
             resetFormFields: resetFormFields,
            addNew: addNew,
            cancel: cancel
            
        });

        (function activate() {
            $scope.location = {};
          loadLocations();
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
        
         function loadLocations() {
			    var url = baseUrl + "/location/all";
			    $http.get(url)
			        .then(function(response) {
			            vm.locations = response.data;
			            console.log("Fetched locations:", vm.locations);
			        })
			        .catch(function(error) {
			            console.error("Error fetching locations:", error);
			        });
			}
			


			
			/*	 function delet(location) {
			    var url = baseUrl + "/location/delete/" + location.location_id;
			    console.log("Delete URL:", url);
			    $http.delete(url)
			        .then(function(response) {
			            toastr.success('Deleted location Successfully!');
			            loadLocations();
			        })
			        .catch(function(error) {
			           toastr.error('Cannot delete or update a parent row');
			            console.error("Error deleting location:", error);
			        });
			}*/
			
				let selectedlocation = null;
       function delet(location) {
    selectedlocation = location;
    vm.showModal = true; // Show the confirmation modal
}

function confirmDelete() {
    if (!selectedlocation) return;

    var url = baseUrl + "/location/delete/" + selectedlocation.location_id;

    $http.delete(url)
        .then(function(response) {
            toastr.success('Deleted Locations successfully!');
            loadLocations();
        })
        .catch(function(error) {
            toastr.error('Error deleting Location');
            console.error("Error deleting Location:", error);
        })
        .finally(function() {
            vm.showModal = false; // Hide the modal after confirmation
        });
}

function cancelDelete() {
    vm.showModal = false; // Hide the modal without taking any action
}




						
						function ok(location) {
							    var url = baseUrl + "/location/addLocation";
							    var msg = "";
							    
							    // Check if all fields are filled
							    if (!location.locationName || !location.locationCode) {
							        toastr.error('Please fill all the fields!');
							        return;
							    }
							
							    genericFactory.add(msg, url, location)
							        .then(function(response) {
							            vm.saved = response.data;
							            console.log("Checking locations:", vm.saved);
							
							            if (response.status === 200) {
							                toastr.success('Location added successfully!');
							                loadLocations();
							                $scope.addNewTab = false;
							                resetFormFields(); // Reset form fields here
							            } else {
							                toastr.error('Failed to add location');
							            }
							        })
							        .catch(function(error) {
							            toastr.error('Failed to add location');
							            console.error("Error adding location:", error);
							        });
							}


				
				function resetFormFields() {
						    $scope.Location = {}; // Reset the location object to an empty object
						}
								
										

			
			
			
   
        function add(Location) {
            $scope.addNewTab = true;
            $scope.Location = Object.assign({}, Location);
        }

      
      
			

					
				


      
      
			
					
				

			
			
			
   
       
    }
})();
