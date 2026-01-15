(function() {
	'use strict';

	angular
		.module('myApp.mtbf')
		.factory('mtbfService', mtbfService);

	mtbfService.$inject = ['mtbfHttpService', '$q', 'toastr'];
	
	/* @ngInject */
	function mtbfService(mtbfHttpService, $q, toastr) {
		var service = {			
				getMtbfs : getMtbfs,
				deleteMtbf : deleteMtbf,
				addMtbf : addMtbf,
				getMtbfQuarterly : getMtbfQuarterly,
				
				getMouldMtbfs : getMouldMtbfs,
				getMouldMtbfQuarterly:getMouldMtbfQuarterly,
		};
		return service;

		// ***************************************************************

		function getMtbfs(obj) {
		var deferred = $q.defer();
			mtbfHttpService.getMtbfs(obj).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function getMtbfQuarterly(obj){
			var deferred = $q.defer();
			mtbfHttpService.getMtbfQuarterly(obj).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function deleteMtbf(ftr){
			var deferred = $q.defer();
			mtbfHttpService.deleteMtbf(ftr).then(function(response){
				toastr.success('Ftr Deleted....', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		function addMtbf(ftr){
			debugger;
			var deferred = $q.defer();
			mtbfHttpService.addMtbf(ftr).then(function(response){
				toastr.success('Ftr Added....', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		
		
		
		/************************MOULD***********************************/
		function getMouldMtbfs(obj) {
			var deferred = $q.defer();
			mtbfHttpService.getMouldMtbfs(obj).then(function(response){
					deferred.resolve(response.data);
				}, function(err){
					alert(JSON.stringify(err.statusText));
				});
				return deferred.promise;
		}
			
		function getMouldMtbfQuarterly(obj){
			var deferred = $q.defer();
			mtbfHttpService.getMouldMtbfQuarterly(obj).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}

		
	}

})();