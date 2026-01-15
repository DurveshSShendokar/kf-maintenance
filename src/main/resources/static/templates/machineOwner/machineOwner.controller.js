(function() {
	'use strict';

	angular.module('myApp.machineOwner').controller('MachineOwnerController', MachineOwnerController);


	MachineOwnerController.$inject = [ '$state','$uibModal','$log',
			'$scope', 'toastr','localStorageService','$filter','genericFactory','machine_mstService'];
	
	/* @ngInject */
	function MachineOwnerController($state,$uibModal,$log, $scope, toastr,localStorageService,$filter,genericFactory,machine_mstService) {
		
		var machineUrl = staticUrlMaintenance+"/machine_mst";
		var userUrl = staticUrlMaintenance+"/user";
		var machineOwnerUrl = staticUrlMaintenance+"/machineOwner";
		var vm = angular.extend(this, {
		saveOwner:saveOwner,
			delet:delet,
			edit:edit,
			loadMachineEqids:loadMachineEqids,
			isLoading: false 
		});

		(function activate() {
			//loadMachines()
			loadUsers()
			loadMachineOwners()
			loadMachineNames();
			$scope.machOwner = {
			   user: {}
			};

		})();
		
	


		// ******************************************************
		function edit(machOwner){
			$scope.machOwner=machOwner
		}
		function loadMachines(){
			console.log("machines : ")
			var msg=""
				 var url =machineUrl+"/list";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.machines= response.data;
				console.log("machines : "+JSON.stringify(vm.machines))
								
			});
			
			
		}
		function loadMachineNames() {
			machine_mstService.getMachineNames().then(function(data) {
				vm.machineNames = data;
				console.log(JSON.stringify(vm.machineNames));
			});
		}
		
		
		function loadMachineEqids(machineName) {
		    var msg = "";
		    var url = machineUrl + "/machineEqidsByName?machineName=" + encodeURIComponent(machineName);
		    genericFactory.getAll(msg, url).then(function(response) {
		        vm.machineEqids = response.data;
		        console.log("machineEqids : " + JSON.stringify(vm.machineEqids));
		    });
		}

		function loadUsers(){
			
			var msg=""
				 var url =userUrl+"/getAlluserForMachine";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.users= response.data;
				console.log("users : "+JSON.stringify(vm.users))
								
			});
			
			
		}
	
		
			function loadMachineOwners() {
			    var msg = "";
			    var url = machineOwnerUrl + "/getAllMachineOwners";

			    vm.isLoading = true; // explicitly show loader here

			    genericFactory.getAll(msg, url)
			        .then(function(response) {
			            vm.machineOwners = response.data;
			            console.log("machineOwners : " + JSON.stringify(vm.machineOwners));

			            // Allow DOM digest to complete before hiding loader
			            $timeout(function() {
			                vm.isLoading = false; // hide loader AFTER table is updated
			            }, 200); // adjust delay if needed
			        })
			        .catch(function(error) {
			            
			            vm.isLoading = false;
			        });
			}

	function delet(machOwner){
			
			console.log("machOwner : "+JSON.stringify(machOwner))
			var msg=""
				 var url =machineOwnerUrl+"/deletMachineOwner";
				genericFactory.add(msg,url,machOwner).then(function(response) {
					loadMachineOwners()
				
								
			});
			
		}
		
		// Watch for machine name selection to load corresponding EQIDs
		$scope.$watch('machOwner.machineName', function(newMachineName) {
		    if (newMachineName) {
		        vm.loadMachineEqids(newMachineName);
		    } else {
		        vm.machineEqids = []; 
		    }
		});


		$scope.$watchGroup(['machOwner.machineName', 'machOwner.machineId', 'machOwner.userId'], function (newValues) {
		    var machineName = newValues[0]; // for reference only
		    var machineId = newValues[1];
		    var userId = newValues[2];

		    console.log("Trigger saveOwner with:", machineName, machineId, userId);

		    if (machineName && machineId && userId) {
		        vm.saveOwner(machineId, userId);
		    }
		});

	//	function saveOwner(machineId, userId) {
			function saveOwner(machineId, userId) {
			    if (!machineId || !userId) {
			        toastr.error("Please select Machine EQID and User.");
			        return;
			    }

			    var machOwner = {
			        machine: { machine_id: machineId },
			        user: { id: userId },
			        addedDate: new Date()
			    };

			    console.log("machOwner to be saved:", JSON.stringify(machOwner));

			    vm.isLoading = true; // SHOW LOADER immediately

			    var url = machineOwnerUrl + "/addMachineOwners";
			    var startTime = new Date().getTime(); // record start time

			    genericFactory.add("", url, machOwner).then(function (response) {
			        toastr.success(response.data.status || "Machine Owner added successfully!");

			        loadMachineOwners();

			        $scope.machOwner = {}; // reset form

			        var elapsed = new Date().getTime() - startTime;
			        var remaining = 4000 - elapsed; // calculate remaining time to complete 4 sec

			        if (remaining > 0) {
			            setTimeout(function () {
			                $scope.$apply(function () {
			                    vm.isLoading = false; // hide loader after remaining time
			                });
			            }, remaining);
			        } else {
			            vm.isLoading = false; // 4 seconds already passed, hide immediately
			        }
			    }).catch(function (error) {
			        toastr.error("Error saving machine owner.");
			        vm.isLoading = false;
			    });
			}


		
	}
})();
