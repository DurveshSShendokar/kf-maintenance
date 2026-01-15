(function() {
	'use strict';

	angular
		.module('myApp.scrap')
		.factory('scrapService', scrapService);

	scrapService.$inject = ['scrapHttpService', '$q', 'toastr'];
	
	/* @ngInject */
	function scrapService(scrapHttpService, $q, toastr) {
		var service = {		
				getScraps	:	getScraps,
				getDepartmentsList	:	getDepartmentsList,
				getScrapTypeList	:	getScrapTypeList,
				createNewTicket		:	createNewTicket,
				getYardDepartmentList	:	getYardDepartmentList,
				changeDepartmentStatus	:	changeDepartmentStatus,
				changeYardStatus	:	changeYardStatus,
				updateYard	:	updateYard,
				getScrapDeparmentListDateWise	:	getScrapDeparmentListDateWise
		};
		return service;

		// ***************************************************************

		function getScraps() {
			var deferred = $q.defer();
			scrapHttpService.getScraps().then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function getDepartmentsList() {
			var deferred = $q.defer();
			scrapHttpService.getDepartmentsList().then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function getScrapTypeList() {
			var deferred = $q.defer();
			scrapHttpService.getScrapTypeList().then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function createNewTicket(iObj){
			var deferred = $q.defer();
			scrapHttpService.createNewTicket(iObj).then(function(response){
				deferred.resolve(response);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function getYardDepartmentList(){
			var deferred = $q.defer();
			scrapHttpService.getYardDepartmentList().then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function changeDepartmentStatus(iObj){
			var deferred = $q.defer();
			scrapHttpService.changeDepartmentStatus(iObj).then(function(response){
				deferred.resolve(response);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function changeYardStatus(iObj){
			var deferred = $q.defer();
			scrapHttpService.changeYardStatus(iObj).then(function(response){
				deferred.resolve(response);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function updateYard(iObj){
			var deferred = $q.defer();
			scrapHttpService.updateYard(iObj).then(function(response){
				deferred.resolve(response);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function getScrapDeparmentListDateWise(obj){
			var deferred = $q.defer();
			scrapHttpService.getScrapDeparmentListDateWise(obj).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		
	}

})();