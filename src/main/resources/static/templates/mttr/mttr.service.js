(function() {
	'use strict';

	angular
		.module('myApp.mttr')
		.factory('mttrService', mttrService);

	mttrService.$inject = ['mttrHttpService', '$q', 'toastr'];
	
	/* @ngInject */
	function mttrService(mttrHttpService, $q, toastr) {
		var service = {			
				getMttrs : getMttrs,
				deleteMttr : deleteMttr,
				addMttr : addMttr,
				getMttrQuarterly : getMttrQuarterly,
				
				getMouldMttrs : getMouldMttrs,
				getMouldMttrQuarterly:getMouldMttrQuarterly,
		};
		return service;

		// ***************************************************************

		function getMttrs(obj) {
		var deferred = $q.defer();
			mttrHttpService.getMttrs(obj).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function getMttrQuarterly(obj){
			var deferred = $q.defer();
			mttrHttpService.getMttrQuarterly(obj).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function deleteMttr(ftr){
			var deferred = $q.defer();
			mttrHttpService.deleteMttr(ftr).then(function(response){
				toastr.success('Ftr Deleted....', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		function addMttr(ftr){
			debugger;
			var deferred = $q.defer();
			mttrHttpService.addMttr(ftr).then(function(response){
				toastr.success('Ftr Added....', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		
		
		
		/************************MOULD***********************************/
		function getMouldMttrs(obj) {
			var deferred = $q.defer();
			mttrHttpService.getMouldMttrs(obj).then(function(response){
					deferred.resolve(response.data);
				}, function(err){
					alert(JSON.stringify(err.statusText));
				});
				return deferred.promise;
		}
			
		function getMouldMttrQuarterly(obj){
			var deferred = $q.defer();
			mttrHttpService.getMouldMttrQuarterly(obj).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}

		
	}

})();