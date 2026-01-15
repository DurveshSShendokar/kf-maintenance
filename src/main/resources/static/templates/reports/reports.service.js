/**
  * @author 		: ABS
  * @name			: materialIssueService
  * @description 	: service for material issue module
  * @date 			: 20/06/2018
  */
(function() {
	'use strict';

	angular.module('myApp.reports').factory('reportsService', reportsService);
	reportsService.$inject = ['reportsHttpService', '$q', 'toastr'];

	function reportsService(reportsHttpService, $q, toastr) {
		var service = {		
				incomingReport	:	incomingReport,
				assemblyReport	:	assemblyReport,
				traverseQrCode	:	traverseQrCode,
				getItemsList	:	getItemsList,
				getItemwiseMonthlyReport	:	getItemwiseMonthlyReport,
				getItemsListForMaterial	:	getItemsListForMaterial,
				materialIssueReport	:	materialIssueReport,
				getMonthlyYieldReport	:	getMonthlyYieldReport,
				getMaterialAtEachStageReport	:	getMaterialAtEachStageReport
		};
		return service;
		
		function incomingReport(date){
			var deferred = $q.defer();
			reportsHttpService.incomingReport(date).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.data.message);
			});
			return deferred.promise;
		}
		
		function assemblyReport(date){
			var deferred = $q.defer();
			reportsHttpService.assemblyReport(date).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.data.message);
			});
			return deferred.promise;
		}
		
		function traverseQrCode(code){
			var deferred = $q.defer();
			reportsHttpService.traverseQrCode(code).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.data.message);
			});
			return deferred.promise;
		}
		
		function getItemsList(){
			var deferred = $q.defer();
			reportsHttpService.getItemsList().then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.data.message);
			});
			return deferred.promise;
		}
		
		function getItemwiseMonthlyReport(obj){
			var deferred = $q.defer();
			reportsHttpService.getItemwiseMonthlyReport(obj).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.data.message);
			});
			return deferred.promise;
		}
		
		function getItemsListForMaterial(obj){
			var deferred = $q.defer();
			reportsHttpService.getItemsListForMaterial(obj).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.data.message);
			});
			return deferred.promise;
		}
		
		function materialIssueReport(obj){
			var deferred = $q.defer();
			reportsHttpService.materialIssueReport(obj).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.data.message);
			});
			return deferred.promise;
		}
		
		function getMonthlyYieldReport(obj){
			var deferred = $q.defer();
			reportsHttpService.getMonthlyYieldReport(obj).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.data.message);
			});
			return deferred.promise;
		}
		
		function getMaterialAtEachStageReport(obj){
			var deferred = $q.defer();
			reportsHttpService.getMaterialAtEachStageReport(obj).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.data.message);
			});
			return deferred.promise;
		}
		
	}
})();
