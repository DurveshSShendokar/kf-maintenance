(function () {
	'use strict';

	angular
		.module('myApp.machine_mst')
		.factory('machine_mstHttpService', machine_mstHttpService);

	machine_mstHttpService.$inject = ['$http', '$q', '_', 'localStorageService', 'ApiEndpoint'];

	/* @ngInject */
	function machine_mstHttpService($http, $q, _, localStorageService, ApiEndpoint) {
		
		
		var machine_mst = localStorageService.get(ApiEndpoint.machine_mstKey);
		var machine_mstUrl = staticUrlMaintenance+"/machine_mst";   // Machine_mst Url
		var maintUrl = staticUrlMaintenance+"/maint"; 
		// Variables
		var machine_msts = {};

		var service = {
			addMachine_mst : addMachine_mst,
			sendMail : sendMail,
			sendSms : sendSms,
			getMachine_msts : getMachine_msts,
			deleteMachine_mst : deleteMachine_mst,
			getRoles : getRoles,
			getMachine_mstTypes : getMachine_mstTypes,
			getDoneMaintenanceByMachine:getDoneMaintenanceByMachine,
			getMachineNames:getMachineNames,
			getMachineByName:getMachineByName
		};

		return service;
	
		function getRoles(){
			return $http.get(machine_mstUrl+'/role');
		}
		
		function getMachine_mstTypes(){
			return $http.get(machine_mstUrl+'/machine_mst_type');
		}
		
		function getMachine_msts(){
			return $http.get(machine_mstUrl+'/machineName');
		}

		function getMachineNames(){
			console.log("URL :: "+machine_mstUrl+'/machineName')
			return $http.get(machine_mstUrl+'/machineName');
		}

		function getDoneMaintenanceByMachine(machine_id){
			return $http.get(maintUrl+'/getDoneMaintenanceByMachine/'+machine_id);
		}
		function getMachineByName(machineName){
			var obj ={}
			obj.machine_name=machineName
			return $http.post(machine_mstUrl+'/getGetMachinesByName', obj);
			//return $http.get(machine_mstUrl+'/getGetMachinesByName/'+machineName);
		}
		
		function deleteMachine_mst(machine_mst){
			return $http.get(machine_mstUrl+'/delete/'+machine_mst.machine_id);
		}
		
		function addMachine_mst(machine_mst){
		
		
			
			return $http.post(machine_mstUrl+'/create', machine_mst);
		}
		
		
		function sendMail(machine_mst){
			//console.log(JSON.stringify(machine_mst))
			return $http.post(machine_mstUrl+'/sendmail', machine_mst);
		}
		
		function sendSms(machine_mst){
			//console.log(JSON.stringify(machine_mst))
			return $http.post(machine_mstUrl+'/sendsms', machine_mst);
		}
		
	}
})();
