/**
  * @author 		: ABS
  * @name			: materialIssueService
  * @description 	: service for material issue module
  * @date 			: 20/06/2018
  */
(function() {
	'use strict';

	angular.module('myApp.unusedRequests').factory('unusedRequestsService', unusedRequestsService);
	unusedRequestsService.$inject = ['unusedRequestsHttpService', '$q', 'toastr'];

	function unusedRequestsService(unusedRequestsHttpService, $q, toastr) {
		var service = {		
				getMaterialList	:	getMaterialList,
				getQrCodeList	:	getQrCodeList,
				searchUnusedRequests	:	searchUnusedRequests,
				saveUnusedRequests	:	saveUnusedRequests
		};
		return service;
		
		/**
		  * @author 		: ABS
		  * @name			: getMachineList
		  * @description 	: to get list of machine
		  * @date 			: 21/06/2018
		  */
		function getMaterialList(){
			var deferred = $q.defer();
			unusedRequestsHttpService.getMaterialList().then(function(response){
				deferred.resolve(response.data);
			}, function(err){
//				alert(JSON.stringify(err.statusText));
				toastr.error(err.data.message);
			});
			return deferred.promise;
		}
		
		function getQrCodeList(iObj){
			var deferred = $q.defer();
			unusedRequestsHttpService.getQrCodeList(iObj).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
//				alert(JSON.stringify(err.statusText));
				toastr.error(err.data.message);
			});
			return deferred.promise;
		}
		
		function saveUnusedRequests(iObj){
			var deferred = $q.defer();
			unusedRequestsHttpService.saveUnusedRequests(iObj).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
//				alert(JSON.stringify(err.statusText));
				toastr.error(err.data.message);
			});
			return deferred.promise;
		}
		
		function searchUnusedRequests(iObj){
			var deferred = $q.defer();
			unusedRequestsHttpService.searchUnusedRequests(iObj).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
//				alert(JSON.stringify(err.statusText));
				toastr.error(err.data.message);
			});
			return deferred.promise;
		}
		
	}
})();
