/**
  * @author 		: ABS
  * @name			: materialIssueService
  * @description 	: service for material issue module
  * @date 			: 20/06/2018
  */
(function() {
	'use strict';

	angular.module('myApp.sample').factory('sampleService', sampleService);
	sampleService.$inject = ['sampleHttpService', '$q', 'toastr'];

	function sampleService(sampleHttpService, $q, toastr) {
		var service = {		
				// methods
				getRawMaterial		:	getRawMaterial,
				searchRawMaterial	:	searchRawMaterial,
				saveIssuedMaterial	:	saveIssuedMaterial
		};
		return service;
		
		/**
		  * @author 		: ABS
		  * @name			: getRawMaterial
		  * @description 	: to get list of raw material
		  * @date 			: 20/06/2018
		  */
		function getRawMaterial(materialId){
//			http service call
			var deferred = $q.defer();
			sampleHttpService.getRawMaterial(materialId).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
//				alert(JSON.stringify(err.statusText));
				toastr.error(err.data.message);
			});
			return deferred.promise;
		}
		
	}
})();
