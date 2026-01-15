(function () {
	'use strict';

	angular
		.module('myApp.maintlist')
		.factory('maintlistHttpService', maintlistHttpService);

	maintlistHttpService.$inject = ['$http', '$q', '_', 'localStorageService', 'ApiEndpoint'];

	/* @ngInject */
	function maintlistHttpService($http, $q, _, localStorageService, ApiEndpoint) {
		
		var maintlist = localStorageService.get(ApiEndpoint.maintlistKey);
		var maintlistUrl = staticUrlMaintenance+"maintlist";   // Maintlist Url
		
		var maintUrl = staticUrlMaintenance+"/maint";
		
		var checklistUrl = staticUrlMaintenance+"/checklist";
		
		var SchedulechecklistUrl = staticUrlMaintenance+"/scheduledchecklist";
		
		// Variables
		var maintlists = {};

		var service = {
			addMaintlist : addMaintlist,
			sendMail : sendMail,
			sendSms : sendSms,
			getMaintlists : getMaintlists,
			deleteMaintlist : deleteMaintlist,
			getRoles : getRoles,
			getMaintlistTypes : getMaintlistTypes,
			getChecklist : getChecklist,
			saveChecklists	:	saveChecklists,
			getChecklist2 : getChecklist2,
			getrecords : getrecords
		};

		return service;
	
		function getRoles(){
			return $http.get(maintlistUrl+'/role');
		}
		
		function getMaintlistTypes(){
			return $http.get(maintlistUrl+'/maintlist_type');
		}
		
		function getMaintlists(){
			return $http.get(maintlistUrl+'/list');
		}
		
		/**
		 * @author	ABS
		 * @date	July 24, 2018
		 * @name	saveChecklists
		 * @pupose	to save edited checklist report 
		 */
		function saveChecklists(iObj){
			
			return $http.post(maintUrl+'/updateStatus', iObj);
			
		}
		
		function getrecords(maint){
			
			return $http.post(maintUrl+'/getRecord', maint);
			
		}
		
		function getChecklist(maint){
			
			debugger;
			
			return $http.get(checklistUrl+'/getchecklist?machine_id='+maint.machine.machine_id+'&freq='+maint.frequency+'&type='+maint.mode);
		}
		
		function getChecklist2(maint){
			
			debugger;
			
			return $http.get(SchedulechecklistUrl+'/list?maint_id='+maint.maint_id);
		}
		
		
		
		function deleteMaintlist(maintlist){
			
			debugger;
			
			return $http.get(maintUrl+'/delete/'+maintlist.maint_id);
		}
		
		function addMaintlist(maintlist){
			//console.log(JSON.stringify(maintlist))
			debugger;
			
			return $http.post(maintlistUrl+'/create', maintlist);
		}
		function sendMail(maintlist){
			//console.log(JSON.stringify(maintlist))
			return $http.post(maintlistUrl+'/sendmail', maintlist);
		}
		
		function sendSms(maintlist){
			//console.log(JSON.stringify(maintlist))
			return $http.post(maintlistUrl+'/sendsms', maintlist);
		}
		
	}
})();
