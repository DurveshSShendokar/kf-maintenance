(function() {
	'use strict';

	angular
		.module('myApp.ftr')
		.factory('ftrService', ftrService);

	ftrService.$inject = ['ftrHttpService', '$q', 'toastr'];
	
	/* @ngInject */
	function ftrService(ftrHttpService, $q, toastr) {
		var service = {			
			getFtrs : getFtrs,
			deleteFtr : deleteFtr,
			addFtr : addFtr,
			getftrQuarterly : getftrQuarterly,
			
			getMouldFtrs : getMouldFtrs,
			getMouldFtrQuarterly:getMouldFtrQuarterly,
		};
		return service;

		// ***************************************************************

		function getFtrs(obj) {
		var deferred = $q.defer();
			ftrHttpService.getFtrs(obj).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function getftrQuarterly(obj){
			var deferred = $q.defer();
			ftrHttpService.getftrQuarterly(obj).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function deleteFtr(ftr){
			var deferred = $q.defer();
			ftrHttpService.deleteFtr(ftr).then(function(response){
				toastr.success('Ftr Deleted....', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		function addFtr(ftr){
			debugger;
			var deferred = $q.defer();
			ftrHttpService.addFtr(ftr).then(function(response){
				toastr.success('Ftr Added....', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		
		
		
		/************************MOULD***********************************/
		function getMouldFtrs(obj) {
			var deferred = $q.defer();
				ftrHttpService.getMouldFtrs(obj).then(function(response){
					deferred.resolve(response.data);
				}, function(err){
					alert(JSON.stringify(err.statusText));
				});
				return deferred.promise;
			}
			
			function getMouldFtrQuarterly(obj){
				var deferred = $q.defer();
				ftrHttpService.getMouldFtrQuarterly(obj).then(function(response){
					deferred.resolve(response.data);
				}, function(err){
					alert(JSON.stringify(err.statusText));
				});
				return deferred.promise;
			}

		
	}

})();