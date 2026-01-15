/**
  * @author 		: ABS
  * @name			: materialIssueHttpService
  * @description 	: http service for material issue module
  * @date 			: 20/06/2018
  */

(function () {
	'use strict';

	angular
		.module('myApp.sample')
		.factory('sampleHttpService', sampleHttpService);

	sampleHttpService.$inject = ['$http', '$q', '_', 'localStorageService', 'ApiEndpoint'];

	/* @ngInject */
	function sampleHttpService($http, $q, _, localStorageService, ApiEndpoint) {
		
		var user = localStorageService.get(ApiEndpoint.userKey);
		var url = ApiEndpoint.url;   // User Url
		
		// Variables
		var users = {};

		var service = {
//				method declaration
				getRawMaterial		:	getRawMaterial,
				searchRawMaterial	:	searchRawMaterial,
				saveIssuedMaterial	:	saveIssuedMaterial
		};
		return service;
		
		/**
		  * @author 		: ABS
		  * @name			: getRawMaterial
		  * @description 	: to get raw material list from database
		  * @date 			: 20/06/2018
		  */
		function getRawMaterial(materialId){
//			angular integration
			return $http.get('http://192.168.2.8:8091/vendor/list');	//url of database
		}
		
	}
})();
