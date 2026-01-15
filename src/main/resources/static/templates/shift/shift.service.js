(function() {
	'use strict';

	angular
		.module('myApp.shift')
		.factory('shiftService', shiftService);

	shiftService.$inject = ['shiftHttpService', '$q', 'toastr'];
	
	/* @ngInject */
	function shiftService(shiftHttpService, $q, toastr) {
		var service = {			
			getShifts : getShifts,
			deleteShift : deleteShift,
			addShift : addShift,
			getTargets : getTargets
		};
		return service;

		// ***************************************************************

		function getShifts() {
		var deferred = $q.defer();
			shiftHttpService.getShifts().then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		

		function getTargets() {
		var deferred = $q.defer();
			shiftHttpService.getTargets().then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		
		
		function deleteShift(target){
			var deferred = $q.defer();
			shiftHttpService.deleteShift(target).then(function(response){
				toastr.success('Target Deleted....', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		function addShift(target){
			debugger;
			var deferred = $q.defer();
			shiftHttpService.addShift(target).then(function(response){
				toastr.success('Target Added....', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}

		
	}

})();