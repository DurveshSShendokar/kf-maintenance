(function () {
	'use strict';

	angular
		.module('myApp.mtbf')
		.factory('mtbfHttpService', mtbfHttpService);

	mtbfHttpService.$inject = ['$http', '$q', '_', 'localStorageService', 'ApiEndpoint'];

	/* @ngInject */
	function mtbfHttpService($http, $q, _, localStorageService, ApiEndpoint) {
		var reportUrl = staticUrlMaintenance+"/report"; 
		var user = localStorageService.get(ApiEndpoint.userKey);
		var ftrUrl = staticUrlMaintenance+"/trial";   // User Url
		
		// Variables
		var users = {};

		var service = {
				addMtbf : addMtbf,
			getMtbfs : getMtbfs,
			deleteMtbf : deleteMtbf,
			getMtbfQuarterly:getMtbfQuarterly,
			
			getMouldMtbfs : getMouldMtbfs,
			getMouldMtbfQuarterly:getMouldMtbfQuarterly,
		};

		return service;
	
//		--------------------Machine----------------------------------
		function getMtbfs(obj){
//			trial/mtbfList
			return $http.post(reportUrl+'/getMTBFReports', obj);
		}
		function getMtbfQuarterly(obj){
			return $http.post(ftrUrl+'/ftrQtrList', obj);
		}
		
		function deleteMtbf(ftr){
			return $http.get(ftrUrl+'/delete/'+ftr.ftr_id);
		}
		
		function addMtbf(ftr){
			//console.log(JSON.stringify(user))
			return $http.post(ftrUrl+'/create', ftr);
		}
		
		
//		-----------------------Mould--------------------------------------
		function getMouldMtbfs(obj){
//			mouldTrial/mtbfList
			return $http.post(staticUrlMaintenance+'/mouldTrial/mtbfList', obj);
		}
		function getMouldMtbfQuarterly(obj){
			return $http.post(staticUrlMaintenance+'/mouldTrial/ftrQtrList', obj);
		}
		
	}
})();
