(function() {
	'use strict';

	angular
		.module('myApp.scraptypereport')
		.factory('scraptypereportService', scraptypereportService);

	scraptypereportService.$inject = ['scraptypereportHttpService', '$q', 'toastr'];
	
	/* @ngInject */
	function scraptypereportService(scraptypereportHttpService, $q, toastr) {
		var service = {		
				getScrapsScrapType : getScrapsScrapType,
				getScrapsDepartmentType : getScrapsDepartmentType,
				scrapdeptreport : scrapdeptreport
		};
		return service;

		// ***************************************************************

		function getScrapsScrapType(str, obj) {
			var deferred = $q.defer();
			scraptypereportHttpService.getScrapsScrapType(str, obj).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function getScrapsDepartmentType(str, obj) {
			var deferred = $q.defer();
			scraptypereportHttpService.getScrapsDepartmentType(str, obj).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function scrapdeptreport(obj){
			var deferred = $q.defer();
			scraptypereportHttpService.scrapdeptreport(obj).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		
		
	}

})();