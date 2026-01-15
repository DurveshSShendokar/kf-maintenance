(function () {
	'use strict';

	angular
		.module('myApp.breakdownupdate')
		.factory('breakdownupdateHttpService', breakdownupdateHttpService);

	breakdownupdateHttpService.$inject = ['$http', '$q', '_', 'localStorageService', 'ApiEndpoint'];

	/* @ngInject */
	function breakdownupdateHttpService($http, $q, _, localStorageService, ApiEndpoint) {
		
		var user = localStorageService.get(ApiEndpoint.userKey);
		var breakdownupdateUrl = staticUrlMaintenance+"/breakdownupdate";  
		var breakdownUrl = staticUrlMaintenance+"/breakdown"
		// User Url
		
		// Variables
		var users = {};

		var service = {
			addBreakdownupdate : addBreakdownupdate,
			getBreakdownupdates : getBreakdownupdates,
			deleteBreakdownupdate : deleteBreakdownupdate,
			updateBreakDown:updateBreakDown
		};

		return service;
	
		
		function getBreakdownupdates(){
			return $http.get(breakdownupdateUrl+'/list');
		}
		
		function deleteBreakdownupdate(breakdownupdate){
			//breakdown/delete/breakdown_id
			return $http.get(staticUrlMaintenance+'/breakdown/delete/'+breakdownupdate.breakdown_id);
		}
		
		function addBreakdownupdate(breakdownupdate){
			
//			debugger;
			
			console.log("breakdownupdate"+JSON.stringify(breakdownupdate))
			return $http.post(breakdownupdateUrl+'/create', breakdownupdate);
		}
		function updateBreakDown(breakdownupdate){
			
//			debugger;
			
			console.log("UPDATES"+JSON.stringify(breakdownupdate))
			return $http.post(breakdownUrl+'/updateBreakdown', breakdownupdate);
		}
		
	}
})();
