(function() {
	'use strict';

	angular
		.module('myApp.machine')
		.factory('machineService', machineService);

	machineService.$inject = ['machineHttpService', '$q', 'toastr'];
	
	/* @ngInject */
	function machineService(machineHttpService, $q, toastr) {
		var service = {			
			getMachines : getMachines,
			deleteMachine : deleteMachine,
			addMachine : addMachine,
			sendMail : sendMail,
			sendSms : sendSms,
			getRoles : getRoles,
			getMachineTypes : getMachineTypes,
		};
		return service;

		// ***************************************************************

		function getMachines() {
			var deferred = $q.defer();
			console.log('get machine service');
			machineHttpService.getMachines().then(function(response){
				deferred.resolve(response.data);
				console.log('response');
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function deleteMachine(machine){
			var deferred = $q.defer();
			machineHttpService.deleteMachine(machine).then(function(response){
				toastr.success('Schedule Deleted....', 'Succesfully !!');
				 
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		function addMachine(machine){
			var deferred = $q.defer();
			machineHttpService.addMachine(machine).then(function(response){
				toastr.success('Schedule Added....', 'Succesfully !!');
				
				deferred.resolve(response.data);
			}, function(err){
				toastr.error('sorry machine cant registered at this time', 'Error !!');
			});
			return deferred.promise;
		}
		
		function sendMail(machine){
			var deferred = $q.defer();
			machineHttpService.sendMail(machine).then(function(response){
				toastr.success('Mail Sent....!', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		function sendSms(machine){
			var deferred = $q.defer();
			machineHttpService.sendSms(machine).then(function(response){
				toastr.success('SMS Sent....!', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		function getRoles() {
			var deferred = $q.defer();
			machineHttpService.getRoles().then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function getMachineTypes() {
			var deferred = $q.defer();
			machineHttpService.getMachineTypes().then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
	}

})();