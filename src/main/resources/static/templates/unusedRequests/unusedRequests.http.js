/**
  * @author 		: ABS
  * @name			: materialIssueHttpService
  * @description 	: http service for material issue module
  * @date 			: 20/06/2018
  */

(function () {
	'use strict';

	angular
		.module('myApp.unusedRequests')
		.factory('unusedRequestsHttpService', unusedRequestsHttpService);

	unusedRequestsHttpService.$inject = ['$http', '$q', '_', 'localStorageService', 'ApiEndpoint'];

	/* @ngInject */
	function unusedRequestsHttpService($http, $q, _, localStorageService, ApiEndpoint) {
		
		var user = localStorageService.get(ApiEndpoint.userKey);
		var url = ApiEndpoint.url;   // User Url
		var u = staticUrl + '/materialReturnSlip';
		
		var users = {};

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
		  * @description 	: to get machine list from database
		  * @date 			: 27/07/2018
		  */
		
		function getMaterialList(){
			//http://localhost:8091/materialReturnSlip/materialReturnMachineList
			return $http.get(u + '/unUsedRunnerRowMaterialList');	//url of database
		}
		
		function getQrCodeList(iObj){
			//http://localhost:8091/materialReturnSlip/sendItemId?itemId=RM0024
			return $http.get(u + '/sendItemId?itemId=' + iObj.itemId);	
		}
		
		function saveUnusedRequests(iObj){
			// http://localhost:8091/materialReturnSlip/unUsedRunnerQtyUpdateFromStockIn
			return $http.post(u + '/unUsedRunnerQtyUpdateFromStockIn',iObj);	
		}
		
		function searchUnusedRequests(iObj){
			//http://localhost:8091/materialReturnSlip/getUnUsedRunnerItemList?itemId=RM0024
//			changed service Oct 12, 2018
			return $http.get(u + '/getUnUsedRunnerItemList?itemId=' + iObj.itemId + '&qrCode=' + iObj.qrcode);	
		}
	}
})();
