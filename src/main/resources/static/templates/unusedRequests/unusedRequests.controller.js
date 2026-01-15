/**
  * @author 		: ABS
  * @name			: materialIssueController
  * @description 	: controller for material issue module
  * @date 			: 20/06/2018
  */
(function() {
	'use strict';

	angular.module('myApp.unusedRequests').controller('unusedRequestsController', unusedRequestsController);

	unusedRequestsController.$inject = ['$state', '$scope', 'unusedRequestsService','toastr','DTColumnDefBuilder','DTOptionsBuilder'];
	/* @ngInject */
	function unusedRequestsController($state, $scope, unusedRequestsService, toastr, DTColumnDefBuilder, DTOptionsBuilder) {
		
		var vm = angular.extend(this, {
			saveUnusedRequests	:	saveUnusedRequests,
			getQrCodeList		:	getQrCodeList,
			searchUnusedRequests:	searchUnusedRequests,
			printQrCode			:	printQrCode
		});

		(function activate() {
			$scope.selectMaterial = 'selectMaterial';
			$scope.selectQr = 'selectQr';
			
			$scope.userDetails = sessionStorage.getItem('renataLoggedInUser'); //localStorageService.get('renataLoggedInUser');
			$scope.userDetails = JSON.parse($scope.userDetails);
			$scope.loggedInUserUid = $scope.userDetails.id;
			$scope.operatorname = $scope.userDetails.firstName;
			
			$scope.showTable = false;
			$scope.dtOptions = DTOptionsBuilder.newOptions().withDOM('C<"clear">lfrtip');
			$scope.dtColumnDefs = [
			                       DTColumnDefBuilder.newColumnDef(3).notSortable(),
			                       DTColumnDefBuilder.newColumnDef(4).notSortable(),
			                       DTColumnDefBuilder.newColumnDef(5).notSortable(),
			                       DTColumnDefBuilder.newColumnDef(6).notSortable(),
			                       DTColumnDefBuilder.newColumnDef(7).notSortable()
			      		    ];
			getMaterialList();
		})();
		
		/**
		  * @author : ABS
		  * @name	: getMachineList
		  * @Description : to get machine list
		  * @date : 27/07/2018
		  */
		function getMaterialList(){
			unusedRequestsService.getMaterialList().then(function(data) {		
				vm.materialDetails = data;
			});
		}
		
		function getQrCodeList(){
			$scope.selectQr = 'selectQr';
			if($scope.selectMaterial == 'selectMaterial')
				return;
			var iObj = JSON.parse($scope.selectMaterial);
			unusedRequestsService.getQrCodeList(iObj).then(function(data) {		
				vm.qrCodeList = data;
				for(var i in vm.qrCodeList)
					vm.qrCodeList[i].qrCodeDisplay = vm.qrCodeList[i].qrCode.slice(4, vm.qrCodeList[i].qrCode.length);
			});
		}
		
		function searchUnusedRequests(){
			if($scope.selectMaterial == 'selectMaterial'){
				toastr.error('Please select material');
				document.getElementById('material').focus();
				return;
			}
			if($scope.selectQr == 'selectQr'){
				toastr.error('Please select QR code');
				document.getElementById('qrSelect').focus();
				return;
			}
			
			$scope.selectedMaterialObj = JSON.parse($scope.selectMaterial);
			$scope.selectedQrObj	=	JSON.parse($scope.selectQr);
			
			var obj =  {
				itemId : $scope.selectedMaterialObj.itemId,
				qrcode : $scope.selectedQrObj.qrCode
			};
			
			unusedRequestsService.searchUnusedRequests(obj).then(function(data) {		
				vm.unusedRequetsList = data;
				if(vm.unusedRequetsList.length == 0)
					$scope.showTable = false;
				else
					$scope.showTable = true;
				for(var i in vm.unusedRequetsList)
					vm.unusedRequetsList[i].status = vm.unusedRequetsList[i].status ? vm.unusedRequetsList[i].status.toUpperCase() : 'status';
					
				if(vm.unusedRequetsList.length == 0){
					$scope.selectMaterial = 'selectMaterial';
					$scope.selectQr = 'selectQr';
//					toastr.error('Materials are not present');
				}
				
				setTimeout(function(){
					window.scroll({
						  top: document.body.scrollHeight,  
						  left: 0, 
						  behavior: 'smooth' 
						});
				},0);
			});
		}
		
		function saveUnusedRequests(iObj, iIndex){
			var printObj = Object.assign({}, iObj);
			if(parseFloat(iObj.qty) <= 0){
				toastr.error('Please enter quantity');
				document.getElementById('qty'+iIndex).focus();
				return;
			}
			if(iObj.status == 'status'){
				toastr.error('Please select status');
				document.getElementById('status'+iIndex).focus();
				return;
			}
			
			var obj = {
				"qrCode":$scope.selectedQrObj.qrCode,
				"qty":parseFloat(iObj.qty),
				"status":iObj.status,
				"grnItemLotId":$scope.selectedQrObj.grnItemLotId,
				"marerialReturnId":iObj.marerialReturnId,
				"itemId":$scope.selectedMaterialObj.itemId,
				"uom":"KG",	//SURAJ
				"poNo":iObj.poNo
			}
			
			unusedRequestsService.saveUnusedRequests(obj).then(function(data) {		
				//toastr.success('unused request saved successfully');
				searchUnusedRequests();
//				vm.qrCodeList = data;
			});
			
			var qrDiv = document.getElementById('qr');
			if(qrDiv){
				while (qrDiv.firstChild) {
					 qrDiv.removeChild(qrDiv.firstChild);
				 }
			}
			
			setTimeout(function(){
				makeCode('qr', $scope.selectedQrObj.qrCode);
			},0);
			
			if(iObj.status == 'A'){
				setTimeout(function(){
					printQrCode(printObj);
				}, 5);
			}
		}
		
		/**
		  * @author : ABS
		  * @description : to generate QR code
		  * @date : 11/09/2018
		  */
		 var makeCode = function(divId, s){
//			 var s = grnId + '' + itemLotNo + '' + venPostDate;
			 var qrcode = new QRCode(document.getElementById(divId), {
					width : 100,
					height : 100
			 });
			 qrcode.makeCode(s);
		 }
		 
		function printQrCode(iObj){
			$scope.item = iObj.itemName;
			/*var qrDiv = document.getElementById('qr').innerHTML;
			if(qrDiv){
				while (qrDiv.firstChild) {
					 qrDiv.removeChild(qrDiv.firstChild);
				 }
			}*/
			
			/*setTimeout(function(){
				makeCode('qr', $scope.selectedQrObj.qrCode);
			},0);*/
			
			setTimeout(function(){
				var innerContents = '';
				innerContents = innerContents + document.getElementById('qrPrintParent').innerHTML;
		        var popupWinindow = window.open('', '_blank', 'width=600,height=700,scrollbars=no,menubar=no,toolbar=no,location=no,status=no,titlebar=no');
		        popupWinindow.document.open();
		        popupWinindow.document.write('<style> @page {  margin: 8;} </style>');
		        popupWinindow.document.write('<html><body onload="window.print()">' + innerContents + '</html>');
		        popupWinindow.document.close();
			},10);
		}
		
	}
})();


