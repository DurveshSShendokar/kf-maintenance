(function() {
	'use strict';

	angular
		.module('myApp.breakdownupdate')
		.factory('breakdownupdateService', breakdownupdateService);

	breakdownupdateService.$inject = ['breakdownupdateHttpService', '$q', 'toastr'];
	
	/* @ngInject */
	function breakdownupdateService(breakdownupdateHttpService, $q, toastr) {
		var service = {			
			getBreakdownupdates : getBreakdownupdates,
			deleteBreakdownupdate : deleteBreakdownupdate,
			addBreakdownupdate : addBreakdownupdate,
			updateBreakDown:updateBreakDown,
		};
		return service;

		// ***************************************************************

		function getBreakdownupdates() {
		var deferred = $q.defer();
			breakdownupdateHttpService.getBreakdownupdates().then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		
		
		function deleteBreakdownupdate(breakdownupdate){
			var deferred = $q.defer();
			breakdownupdateHttpService.deleteBreakdownupdate(breakdownupdate).then(function(response){
				toastr.success('Breakdownupdate Deleted....', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		function addBreakdownupdate(breakdownupdate){
//			debugger;
			var deferred = $q.defer();
			breakdownupdateHttpService.addBreakdownupdate(breakdownupdate).then(function(response){
				toastr.success('Breakdown updated....', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}

		function updateBreakDown(breakdownupdate){
//			debugger;
			var deferred = $q.defer();
			breakdownupdateHttpService.updateBreakDown(breakdownupdate).then(function(response){
			//	toastr.success('Breakdown updated....', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}

	}

})();