/**
  * @author 		: ABS
  * @name			: materialIssueHttpService
  * @description 	: http service for material issue module
  * @date 			: 20/06/2018
  */

(function () {
	'use strict';

	angular
		.module('myApp.reports')
		.factory('reportsHttpService', reportsHttpService);

	reportsHttpService.$inject = ['$http', '$q', '_', 'localStorageService', 'ApiEndpoint'];

	/* @ngInject */
	function reportsHttpService($http, $q, _, localStorageService, ApiEndpoint) {
		
		var user = localStorageService.get(ApiEndpoint.userKey);
		var url = ApiEndpoint.url;   // User Url
		var u = staticUrl + '/report';
		
		// Variables
		var users = {};

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
//			localhost:8091/grn/GRNReports?date=2018-01-08
			return $http.get( staticUrl + '/grn/GRNReports?date=' + date);
		}
		
		function assemblyReport(date){
//			localhost:8091/grn/assemblyOlReports?date=2018-08-08
			return $http.get( staticUrl + '/grn/assemblyOlReports?date=' + date);
		}
		
		function traverseQrCode(code){
			return $http.get( staticUrl + '/grn/barcodeTranervesReports?qrCode=' + code);
		}
		
		function getItemsList(){
			return $http.get( staticUrl + '');
		}
		
		function getItemwiseMonthlyReport(obj){
//			http://localhost:8091/report/reportMaterialYield?fromLocalDate=2018-09-01&toLocalDate=2018-09-30
			return $http.get( u + '/reportMaterialYield?fromLocalDate=' + obj.startDate + '&toLocalDate=' + obj.endDate);
		}
		
		function getItemsListForMaterial(obj){
//			localhost:8091/report/itemOfmaterialIssueReport?startDate=2016-01-04&endDate=2016-01-04
			return $http.get( u + '/itemOfmaterialIssueReport?startDate='+ obj.startDate + '&endDate=' + obj.endDate);
		}
		
		function materialIssueReport(obj){
//			localhost:8091/report/materialIssueReport?startDate=2016-01-04&endDate=2016-01-04&itemID=RM0002
			return $http.get( u + '/materialIssueReport?startDate='+ obj.startDate + '&endDate=' + obj.endDate + '&itemID=' + obj.itemId);
		}
		
		function getMonthlyYieldReport(year){
//			http://localhost:8091/report/reportMaterialYearWiseYield?fromLocalDate=2018
			return $http.get(u + '/reportMaterialYearWiseYield?fromLocalDate='+ year);
		}
		
		function getMaterialAtEachStageReport(obj){
//			localhost:8091/report/wipTableData?startDate=2000-09-21&endDate=2019-09-21
			console.log('Url:'+u + '/wipTableData?startDate=' + obj.startDate + '&endDate=' + obj.endDate);
			return $http.get( u + '/wipTableData?startDate=' + obj.startDate + '&endDate=' + obj.endDate);
			
		}
		
		
	}
})();
