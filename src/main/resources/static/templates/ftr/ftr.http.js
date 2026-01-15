(function () {
	'use strict';

	angular
		.module('myApp.ftr')
		.factory('ftrHttpService', ftrHttpService);

	ftrHttpService.$inject = ['$http', '$q', '_', 'localStorageService', 'ApiEndpoint'];

	/* @ngInject */
	function ftrHttpService($http, $q, _, localStorageService, ApiEndpoint) {
		
		var user = localStorageService.get(ApiEndpoint.userKey);
		var ftrUrl = staticUrlMaintenance+"/trial";   // User Url
		var reportUrl = staticUrlMaintenance+"/report";
		// Variables
		var users = {};

		var service = {
			addFtr : addFtr,
			getFtrs : getFtrs,
			deleteFtr : deleteFtr,
			getftrQuarterly:getftrQuarterly,
			
			getMouldFtrs : getMouldFtrs,
			getMouldFtrQuarterly:getMouldFtrQuarterly,
		};

		return service;
	
//		--------------------Machine----------------------------------
		function getFtrs(obj){
			return $http.post(reportUrl+'/getFTPFReport', obj);
		}
		function getftrQuarterly(obj){
			return $http.post(ftrUrl+'/ftrQtrList', obj);
		}
		
		function deleteFtr(ftr){
			return $http.get(ftrUrl+'/delete/'+ftr.ftr_id);
		}
		
		function addFtr(ftr){
			//console.log(JSON.stringify(user))
			return $http.post(ftrUrl+'/create', ftr);
		}
		
		
//		-----------------------Mould--------------------------------------
		function getMouldFtrs(obj){
			return $http.post(staticUrlMaintenance+'/mouldTrial/ftrList', obj);
		}
		function getMouldFtrQuarterly(obj){
			return $http.post(staticUrlMaintenance+'/mouldTrial/ftrQtrList', obj);
		}
		
	}
})();
