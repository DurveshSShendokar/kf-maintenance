(function () {
	'use strict';

	angular
		.module('myApp.scraptypereport')
		.factory('scraptypereportHttpService', scraptypereportHttpService);

	scraptypereportHttpService.$inject = ['$http', '$q', '_', 'localStorageService', 'ApiEndpoint'];

	/* @ngInject */
	function scraptypereportHttpService($http, $q, _, localStorageService, ApiEndpoint) {
		
		var user = localStorageService.get(ApiEndpoint.userKey);
//		var scrapUrl = ApiEndpoint.url+"scrapentery";   // User Url
		var scrapUrl = staticUrlScrap + "/scrapentery";
		
		// Variables
		var users = {};

		var service = {
				getScrapsDepartmentType : getScrapsDepartmentType,
				getScrapsScrapType : getScrapsScrapType,
				scrapdeptreport : scrapdeptreport
		};

		return service;
	
		
		function getScrapsDepartmentType(str, obj){
//			scrapentery/department_graph
			return $http.get(scrapUrl+'/department_graph?uom='+str+ '&from=' +obj.startDate + '&to=' +obj.endDate);
		}
		
		function getScrapsScrapType(str, obj){
//			scrapentery/item_graph
			return $http.get(scrapUrl+'/item_graph?uom='+str+ '&from=' +obj.startDate + '&to=' +obj.endDate);
		}
		
		function scrapdeptreport(obj){
//			localhost:8090/scrapentery/date_list?startDate=2018-08-21&endDate=2018-09-04
			return $http.get(scrapUrl+'/date_list?startDate=' + obj.startDate + '&endDate=' + obj.endDate);
		}
		
		
		
	}
})();
