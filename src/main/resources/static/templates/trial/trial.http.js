(function () {
	'use strict';

	angular
		.module('myApp.trial')
		.factory('trialHttpService', trialHttpService);

	trialHttpService.$inject = ['$http', '$q', '_', 'localStorageService', 'ApiEndpoint'];

	/* @ngInject */
	function trialHttpService($http, $q, _, localStorageService, ApiEndpoint) {
		
		var user = localStorageService.get(ApiEndpoint.userKey);
//		var trialUrl = ApiEndpoint.url+"trial";   // User Url
		var trialUrl = staticUrlMaintenance + "/trial";
		
		// Variables
		var users = {};

		var service = {
			addTrial : addTrial,
			addTrial1 : addTrial1,
			addTrial2 : addTrial2,
			getTrials : getTrials,
			deleteTrial : deleteTrial,
			getMaintData : getMaintData
		};

		return service;
	
		
		function getTrials(){
			return $http.get(trialUrl+'/list');
		}
		
		function deleteTrial(trial){
			//breakdownupdate/delete/breakdown_update_id
			return $http.get(staticUrlMaintenance + '/breakdownupdate/delete/'+trial.breakdown_update_id);
		}
		
		function addTrial(trial){
		
			return $http.post(trialUrl+'/create', trial);
		}
		
		function getMaintData(trial){
			
			return $http.post(trialUrl+'/getRecord', trial);
		}
		
		function addTrial1(trial){
			
		
			return $http.post(trialUrl+'/create1', trial);
		}
		
	
		function addTrial2(trial){
		
			return $http.post(trialUrl+'/create2', trial);
		}
	
		
	}
})();
