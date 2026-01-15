(function () {
	'use strict';

	angular.module('myApp.grn').factory('grnHttpService', grnHttpService);
	grnHttpService.$inject = ['$http', '$q', '_', 'localStorageService', 'ApiEndpoint'];

	/* @ngInject */
	function grnHttpService($http, $q, _, localStorageService, ApiEndpoint) {
		var user = localStorageService.get(ApiEndpoint.userKey);
		var url = ApiEndpoint.url + '/grn';   // User Url
		var u = staticUrl + '/grn';
		
		//var u = 'http://localhost:8090/grn';
		var service = {
			getMaterialDetails : getMaterialDetails,
			getVendorDetails : getVendorDetails,
			getTableData : getTableData,
			sendQrCode: sendQrCode,
			getTableDataByGrnNo : getTableDataByGrnNo
		};
		return service;
		
		function getVendorDetails(){
			//localhost:8091/grn/venlist
//			return $http.get(url+'/vendor/list'); // http://localhost:8091/vendor/list
			return $http.get(u + '/venlist');	
//			return $http.get(url + '/vendor/list');
		}
		
		function getTableData(vendorId, itemId){
			//localhost:8091/grn/submitGrn?venId=V0004&itemId=RM0024
			return $http.get(u + '/submitGrn?venId='+ vendorId + '&itemId=' + itemId); // http://localhost:8091/grn/serch?venId=1&itemId=4
		}
		
		function getTableDataByGrnNo(grnNo){
			//localhost:8091/grn/serchByGRNno?grnNo=161710001
			return $http.get(u + '/serchByGRNno?grnNo='+ grnNo); // http://localhost:8091/grn/serchByGRNno?grnNo=2
		}
		
		function getMaterialDetails(vendorId){
			//localhost:8091/grn/itemlist?venId=V0021
			return $http.get(u + '/itemlist?venId=' + vendorId); // http://localhost:8091/item/list?venId=1
		}
		
		function sendQrCode(itemId, qrCode){
			//localhost:8091/grn/updateQr?grItmlotId=3&qrCodeNo=5231sdfsd
			return $http.get(u + '/updateQr?grItmlotId='+ itemId +' &qrCodeNo=' + qrCode);	//http://localhost:8091/itemlot/updateQr?grItmlotId=2&qrCodeNo=nik
		}
		
	}
})();
