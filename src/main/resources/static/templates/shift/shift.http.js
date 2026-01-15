(function () {
	'use strict';

	angular
		.module('myApp.shift')
		.factory('shiftHttpService', shiftHttpService);

	shiftHttpService.$inject = ['$http', '$q', '_', 'localStorageService', 'ApiEndpoint'];

	/* @ngInject */
	function shiftHttpService($http, $q, _, localStorageService, ApiEndpoint) {
		
		var user = localStorageService.get(ApiEndpoint.userKey);
		var shiftUrl = staticUrlMaintenance+"/shift";
		var reportUrl = staticUrlMaintenance+"/report";// User Url
		
		// Variables
		var users = {};

		var service = {
			addShift : addShift,
			getShifts : getShifts,
			deleteShift : deleteShift,
			getTargets : getTargets
		};

		return service;
	
		
		function getShifts(){
			return $http.get(shiftUrl+'/list');
		}
		

		function getTargets(){
			console.log("Target url  "+reportUrl+'/getTargets')
			return $http.get(reportUrl+'/getTargets');
		}
		
		function deleteShift(target){
			return $http.get(shiftUrl+'/delete/'+target.target_id);
		}
		
		function addShift(target){
			
			//debugger;
			
			//console.log(JSON.stringify(user))
			return $http.post(reportUrl+'/createTaget', target);
		}
		
	}
})();
