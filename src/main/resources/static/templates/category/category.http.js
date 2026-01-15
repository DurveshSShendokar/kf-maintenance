(function () {
	'use strict';

	angular
		.module('myApp.category')
		.factory('categoryHttpService', categoryHttpService);

	categoryHttpService.$inject = ['$http', '$q', '_', 'localStorageService', 'ApiEndpoint'];

	/* @ngInject */
	function categoryHttpService($http, $q, _, localStorageService, ApiEndpoint) {
		
		var user = localStorageService.get(ApiEndpoint.userKey);
		var categoryUrl = staticUrlMaintenance+"/category";   // User Url
		
		// Variables
		var users = {};

		var service = {
			addCategory : addCategory,
			getCategorys : getCategorys,
			deleteCategory : deleteCategory
		};

		return service;
	
		
		function getCategorys(){
			return $http.get(categoryUrl+'/listByMaintenance');
		}
		
		function deleteCategory(category){
			return $http.get(categoryUrl+'/delete/'+category.cat_id);
		}
		
		function addCategory(category){
			
			debugger;
			
			//console.log(JSON.stringify(user))
			return $http.post(categoryUrl+'/create', category);
		}
		
	}
})();
