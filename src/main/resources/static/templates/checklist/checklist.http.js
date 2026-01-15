(function () {
	'use strict';

	angular
		.module('myApp.checklist')
		.factory('checklistHttpService', checklistHttpService);

	checklistHttpService.$inject = ['$http', '$q', '_', 'localStorageService', 'ApiEndpoint'];

	/* @ngInject */
	function checklistHttpService($http, $q, _, localStorageService, ApiEndpoint) {
		
		var user = localStorageService.get(ApiEndpoint.userKey);
		var checklistUrl = staticUrlMaintenance+"/checklist";   // User Url
		
		// Variables
		var users = {};

		var service = {
			addchecklist : addchecklist,
			getchecklists : getchecklists,
			deleteChecklist : deleteChecklist,
			uploadChecklist : uploadChecklist,
		};

		return service;
	
		
		function getchecklists(){
			return $http.get(checklistUrl+'/list');
		}
		
		
		function deleteChecklist(checklist){
			return $http.get(checklistUrl+'/delete/'+checklist.checklist_id);
		}
		
		function addchecklist(checklist){
			//console.log(JSON.stringify(user))
			return $http.post(checklistUrl+'/create', checklist);
		}
		
		
		
		function uploadChecklist(file){
			return $http.post(checklistUrl+'/uploadFile', file);
		}
		
		function searchchecklist(checklist){
			return $http.post(checklistUrl+'/search',checklist);
		}
	
	
	}
})();
