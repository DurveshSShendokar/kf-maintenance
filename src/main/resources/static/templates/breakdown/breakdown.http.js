(function () {
	'use strict';

	angular
		.module('myApp.breakdown')
		.factory('breakdownHttpService', breakdownHttpService);

	breakdownHttpService.$inject = ['$http', '$q', '_', 'localStorageService', 'ApiEndpoint'];

	/* @ngInject */
	function breakdownHttpService($http, $q, _, localStorageService, ApiEndpoint) {
		
		var user = localStorageService.get(ApiEndpoint.userKey);
		var breakdownUrl = staticUrlMaintenance+"/breakdown";   // User Url
		
		// Variables
		var users = {};

		var service = {
			addBreakdown : addBreakdown,
			getBreakdowns : getBreakdowns,
			getBreakdowns2 : getBreakdowns2,
			deleteBreakdown : deleteBreakdown
		};

		return service;
	
		
		function getBreakdowns(){
			return $http.get(breakdownUrl+'/list');
		}
		

		function getBreakdowns2(){
			return $http.get(breakdownUrl+'/list2');
		}
		
		function deleteBreakdown(breakdown){
			return $http.get(breakdownUrl+'/delete/'+breakdown.breakdown_id);
		}
		
		function addBreakdown(breakdown){
			
			debugger;
			
			//console.log(JSON.stringify(user))
			return $http.post(breakdownUrl+'/create', breakdown);
		}
		
	}
})();
