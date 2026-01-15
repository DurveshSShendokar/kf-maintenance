(function () {
	'use strict';

	angular
		.module('myApp.breakdownhistory')
		.factory('breakdownhistoryHttpService', breakdownhistoryHttpService);

	breakdownhistoryHttpService.$inject = ['$http', '$q', '_', 'localStorageService', 'ApiEndpoint'];

	/* @ngInject */
	function breakdownhistoryHttpService($http, $q, _, localStorageService, ApiEndpoint) {
		
		var user = localStorageService.get(ApiEndpoint.userKey);
		var ftrUrl = staticUrlMaintenance+"/trial";   // User Url
		
		// Variables
		var users = {};

		var service = {
			getbreakdownhistory : getbreakdownhistory,
		};

		return service;
	
//		--------------------Machine----------------------------------
		function getbreakdownhistory(obj){
//			trial/mtbfList
			return $http.post(ftrUrl+'/breakdownHistoryList', obj);
		}
		
		
	}
})();
