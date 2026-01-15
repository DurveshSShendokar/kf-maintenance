(function() {
	'use strict';

	angular
		.module('myApp.breakdown')
		.factory('breakdownService', breakdownService);

	breakdownService.$inject = ['breakdownHttpService', '$q', 'toastr'];
	
	/* @ngInject */
	function breakdownService(breakdownHttpService, $q, toastr) {
		var service = {			
			getBreakdowns : getBreakdowns,
			getBreakdowns2 : getBreakdowns2,
			deleteBreakdown : deleteBreakdown,
			addBreakdown : addBreakdown,
		};
		return service;

		// ***************************************************************

		function getBreakdowns() {
		var deferred = $q.defer();
			breakdownHttpService.getBreakdowns().then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		

		function getBreakdowns2() {
		var deferred = $q.defer();
			breakdownHttpService.getBreakdowns2().then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		
		
		function deleteBreakdown(breakdown){
			var deferred = $q.defer();
			breakdownHttpService.deleteBreakdown(breakdown).then(function(response){
				toastr.success('Breakdown Deleted....', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		function addBreakdown(breakdown){
			debugger;
			var deferred = $q.defer();
			breakdownHttpService.addBreakdown(breakdown).then(function(response){
				toastr.success('Breakdown Added....', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}

		
	}

})();