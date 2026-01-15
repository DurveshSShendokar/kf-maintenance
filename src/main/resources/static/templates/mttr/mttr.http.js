(function () {
	'use strict';

	angular
		.module('myApp.mttr')
		.factory('mttrHttpService', mttrHttpService);

	mttrHttpService.$inject = ['$http', '$q', '_', 'localStorageService', 'ApiEndpoint'];

	/* @ngInject */
	function mttrHttpService($http, $q, _, localStorageService, ApiEndpoint) {
		
		var user = localStorageService.get(ApiEndpoint.userKey);
		var ftrUrl = staticUrlMaintenance+"/trial";   // User Url
		var reportUrl = staticUrlMaintenance+"/report"; 
		// Variables
		var users = {};

		var service = {
				addMttr : addMttr,
			getMttrs : getMttrs,
			deleteMttr : deleteMttr,
			getMttrQuarterly:getMttrQuarterly,
			
			getMouldMttrs : getMouldMttrs,
			getMouldMttrQuarterly:getMouldMttrQuarterly,
		};

		return service;
	
//		--------------------Machine----------------------------------
		function getMttrs(obj){
//			trial/mttrList
			return $http.post(reportUrl+'/getMTTRReport', obj);
		}
		function getMttrQuarterly(obj){
			return $http.post(ftrUrl+'/ftrQtrList', obj);
		}
		
		function deleteMttr(ftr){
			return $http.get(ftrUrl+'/delete/'+ftr.ftr_id);
		}
		
		function addMttr(ftr){
			//console.log(JSON.stringify(user))
			return $http.post(ftrUrl+'/create', ftr);
		}
		
		
//		-----------------------Mould--------------------------------------
		function getMouldMttrs(obj){
//			mouldTrial/mttrList
			return $http.post(staticUrlMaintenance+'/mouldTrial/mttrList', obj);
		}
		function getMouldMttrQuarterly(obj){
			return $http.post(staticUrlMaintenance+'/mouldTrial/ftrQtrList', obj);
		}
		
	}
})();
