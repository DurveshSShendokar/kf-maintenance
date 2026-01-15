(function () {
	'use strict';

	angular
		.module('myApp.machine')
		.factory('machineHttpService', machineHttpService);

	machineHttpService.$inject = ['$http', '$q', '_', 'localStorageService', 'ApiEndpoint'];

	/* @ngInject */
	function machineHttpService($http, $q, _, localStorageService, ApiEndpoint) {
		
		var machine = localStorageService.get(ApiEndpoint.machineKey);
		var machineUrl = staticUrlMaintenance+"/maint";   // Machine Url
		
		// Variables
		var machines = {};

		var service = {
			addMachine : addMachine,
			sendMail : sendMail,
			sendSms : sendSms,
			getMachines : getMachines,
			deleteMachine : deleteMachine,
			getRoles : getRoles,
			getMachineTypes : getMachineTypes,
		};

		return service;
	
		function getRoles(){
			return $http.get(machineUrl+'/role');
		}
		
		function getMachineTypes(){
			return $http.get(machineUrl+'/machine_type');
		}
		
		function getMachines(){
			return $http.get(machineUrl+'/total_openmaintenance');
		}
		
		function deleteMachine(machine){
			return $http.get(machineUrl+'/delete/'+machine.machine_id);
		}
		
		function addMachine(machine){
			//console.log(JSON.stringify(machine))
			console.log(machine);
			return $http.post(machineUrl+'/create', machine);
		}
		function sendMail(machine){
			//console.log(JSON.stringify(machine))
			return $http.post(machineUrl+'/sendmail', machine);
		}
		
		function sendSms(machine){
			//console.log(JSON.stringify(machine))
			return $http.post(machineUrl+'/sendsms', machine);
		}
		
	}
})();
