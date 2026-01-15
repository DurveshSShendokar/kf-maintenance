(function() {
	'use strict';

	angular
		.module('myApp.trial')
		.factory('trialService', trialService);

	trialService.$inject = ['trialHttpService', '$q', 'toastr'];
	
	/* @ngInject */
	function trialService(trialHttpService, $q, toastr) {
		var service = {			
			getTrials : getTrials,
			deleteTrial : deleteTrial,
			addTrial : addTrial,
			addTrial1 : addTrial1,
			addTrial2 : addTrial2,
			getMaintData : getMaintData
		};
		return service;

		// ***************************************************************

		function getTrials() {
		var deferred = $q.defer();
			trialHttpService.getTrials().then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		
		function getMaintData(trial){
			debugger;
			var deferred = $q.defer();
			trialHttpService.getMaintData(trial).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		
		function deleteTrial(trial){
			var deferred = $q.defer();
			trialHttpService.deleteTrial(trial).then(function(response){
				toastr.success('Trial Deleted....', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		function addTrial(trial){
			debugger;
			var deferred = $q.defer();
			trialHttpService.addTrial(trial).then(function(response){
				toastr.success('Breakdown Updated....', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}

		
		function addTrial1(trial){
			debugger;
			var deferred = $q.defer();
			trialHttpService.addTrial1(trial).then(function(response){
				toastr.success('Send for maintenance....', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		function addTrial2(trial){
			debugger;
			var deferred = $q.defer();
			trialHttpService.addTrial2(trial).then(function(response){
				toastr.success('Breakdown Updated....', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
	}

})();