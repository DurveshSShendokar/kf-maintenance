(function() {
	'use strict';

	angular.module('myApp.machineOperation').controller('MachineOperationController', MachineOperationController);
//	.controller('Machine_mstModalAddEditCtrl', Machine_mstModalAddEditCtrl);

	MachineOperationController.$inject = [ '$state', 'machine_mstService', 'categoryService', '$uibModal', '$log',
			'$scope', 'toastr','ApiEndpoint','$http','genericFactory' ];
	
	/* @ngInject */
	function MachineOperationController($state, machine_mstService, categoryService, $uibModal, $log, $scope, toastr,ApiEndpoint,$http,genericFactory) {
		
			var reportUrl = staticUrl+"/report";
		
		var vm = angular.extend(this, {
			
			addNew:addNew,
			cancel:cancel,
			ok:ok,
			edit:edit,
			 confirmDelete: confirmDelete,
             cancelDelete:cancelDelete,
			delet:delet
			
			
		});

		(function activate() {
			
			
			$scope.addNewTab=false;
			loadMachineOperations()
		})();

		// ******************************************************
		function edit(operations){
			$scope.addNewTab=true;
			loadMachine_msts();
			
			$scope.operations=operations
		}
	/*	function delet(operations){
			var msg=""
				 var url =reportUrl+"/deleteMachineOperation";
				genericFactory.add(msg,url,operations).then(function(response) {
					console.log("response "+JSON.stringify(response.data))

					
					if(response.data.code==200){
						loadMachineOperations()
					
						toastr.success(response.data,message);
					}else{
						toastr.error(response.data.message);
					}
					
								
			});
			
		}*/
		
		
	  let selectedoperations = null;
        function delet(operations) {
            selectedoperations = operations;
            vm.showModal = true; // Show the confirmation modal
        }

        // Confirm delete function - sends the delete request to the backend
        function confirmDelete() {
            if (!selectedoperations) return;

            var url = reportUrl + "/deleteMachineOperation";
            
            // Send the delete request with the machine operation to be deleted
            $http.post(url, selectedoperations)
                .then(function(response) {
                    toastr.success('Deleted Machine Operation successfully!');
                    loadMachineOperations();  // Reload machine operations after deletion
                })
                .catch(function(error) {
                    toastr.error('Error deleting Machine Operation');
                    console.error("Error deleting Machine Operation:", error);
                })
                .finally(function() {
                    vm.showModal = false; // Hide the modal after confirmation
                });
        }

        // Function to cancel delete and close the modal
        function cancelDelete() {
            vm.showModal = false; // Hide the modal without taking any action
        }


	
		
		function addNew(){
		
			$scope.addNewTab=true;
			loadMachine_msts();
		}
		function cancel(){
			$scope.addNewTab=false;
			
		}
		
		function loadMachine_msts() {
			machine_mstService.getMachine_msts().then(function(data) {
				vm.machine_msts = data;
				
			});
		}
		function loadMachineOperations(){
			var msg=""
				 var url =reportUrl+"/getAllMachineOperations";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.machineOperations= response.data;
				console.log("machineOperations : "+JSON.stringify(vm.machineOperations))
								
			});
			
		}
		
		
	function ok(operation) {
		if(operation==undefined||!operation.machine || operation.machine == ''){
			toastr.error('Please enter machine name');
			return;
		}
		if(operation.hourDay==undefined ){
			toastr.error('Please Operating Hour/Day');
			return;
		}
		
		if(operation.daysOfWeek ==undefined){
			toastr.error('Please enter Day/Week');
			machine_mst.eqid = null;
			return;
		}
		
	
		console.log("operation "+JSON.stringify(operation))
		
		
//			
		var msg=""
			 var url =reportUrl+"/addMachineOperation";
			genericFactory.add(msg,url,operation).then(function(response) {
				console.log("response "+JSON.stringify(response.data))

				
				if(response.data.code==200){
					loadMachineOperations()
					$scope.addNewTab=false;
					$scope.operations={}
					toastr.success(response.data,message);
				}else{
					toastr.error(response.data.message);
				}
				
							
		});
		
		}
		
	
	

	}

	
})();
