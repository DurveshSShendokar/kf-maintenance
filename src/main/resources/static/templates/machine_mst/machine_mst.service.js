(function() {
	'use strict';

	angular
		.module('myApp.machine_mst')
		.factory('machine_mstService', machine_mstService);

	machine_mstService.$inject = ['machine_mstHttpService', '$q', 'toastr'];
	
	/* @ngInject */
	function machine_mstService(machine_mstHttpService, $q, toastr) {
		var service = {			
			getMachine_msts : getMachine_msts,
			deleteMachine_mst : deleteMachine_mst,
			addMachine_mst : addMachine_mst,
			sendMail : sendMail,
			sendSms : sendSms,
			getRoles : getRoles,
			getMachine_mstTypes : getMachine_mstTypes,
			getDoneMaintenanceByMachine:getDoneMaintenanceByMachine,
			getMachineNames:getMachineNames,
			getMachineByName:getMachineByName
		};
		return service;

		// ***************************************************************

		function getDoneMaintenanceByMachine(machine_id) {
			var deferred = $q.defer();
			machine_mstHttpService.getDoneMaintenanceByMachine(machine_id).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		function getMachine_msts() {
			var deferred = $q.defer();
			machine_mstHttpService.getMachine_msts().then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		function getMachineNames() {
			var deferred = $q.defer();
			machine_mstHttpService.getMachineNames().then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		function getMachineByName(machineName) {
			var deferred = $q.defer();
			machine_mstHttpService.getMachineByName(machineName).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				//alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		function deleteMachine_mst(machine_mst){
			var deferred = $q.defer();
			machine_mstHttpService.deleteMachine_mst(machine_mst).then(function(response){
				toastr.success('Deleted....', 'Succesfully !!');
				 
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		function addMachine_mst(machine_mst){
			var deferred = $q.defer();
			machine_mstHttpService.addMachine_mst(machine_mst).then(function(response){
				//toastr.success('Added....', 'Succesfully !!');
				
				deferred.resolve(response.data);
			}, function(err){
				toastr.error('sorry cant registered at this time', 'Error !!');
			});
			return deferred.promise;
		}
		
		function sendMail(machine_mst){
			var deferred = $q.defer();
			machine_mstHttpService.sendMail(machine_mst).then(function(response){
				toastr.success('Mail Sent....!', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		function sendSms(machine_mst){
			var deferred = $q.defer();
			machine_mstHttpService.sendSms(machine_mst).then(function(response){
				toastr.success('SMS Sent....!', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		function getRoles() {
			var deferred = $q.defer();
			machine_mstHttpService.getRoles().then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function getMachine_mstTypes() {
			var deferred = $q.defer();
			machine_mstHttpService.getMachine_mstTypes().then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}

		
	}

})();