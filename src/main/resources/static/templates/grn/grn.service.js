(function() {
	'use strict';

	angular.module('myApp.grn').factory('grnService', grnService);
	grnService.$inject = ['grnHttpService', '$q', 'toastr'];

	function grnService(grnHttpService, $q, toastr) {
		var service = {			
				getVendorDetails: getVendorDetails,
				getMaterialDetails : getMaterialDetails,
				getTableData : getTableData,
				sendQrCode : sendQrCode,
				getTableDataByGrnNo : getTableDataByGrnNo
		};
		return service;
		
		
		function getVendorDetails() {
			var deferred = $q.defer();
			grnHttpService.getVendorDetails().then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				//toastr.error(err.data.message);
//				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function getTableDataByGrnNo(grnNo){
			var deferred = $q.defer();
			grnHttpService.getTableDataByGrnNo(grnNo).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.data.message);
//				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function getTableData(vendorId, itemId) {
			var deferred = $q.defer();
			grnHttpService.getTableData(vendorId, itemId).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
//				alert(JSON.stringify(err.statusText));
				toastr.error(err.data.message);
			});
			return deferred.promise;
		}
		
		function getMaterialDetails(vendorId) {
			var deferred = $q.defer();
			grnHttpService.getMaterialDetails(vendorId).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
//				alert(JSON.stringify(err.statusText));
				toastr.error(err.data.message);
			});
			return deferred.promise;
		}
		
		function sendQrCode(id, qr){
			var deferred = $q.defer();
			grnHttpService.sendQrCode(id, qr).then(function(response){
				toastr.success('QR code submitted....', 'Successfully !!');
				deferred.resolve(response.data);
			}, function(err){
//				toastr.error(err.status+' : '+err.statusText, 'Error !!');
				toastr.error(err.data.message);
			});
			return deferred.promise;
		}
	}
	
})();
