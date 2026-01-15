/**
  * @author 		: ABS
  * @name			: grnController
  * @description 	: controller for GRN module
  * @date 			: 12/06/2018
  */
(function() {
	'use strict';

	angular.module('myApp.grn').controller('grnController', grnController);
	grnController.$inject = ['$state', '$scope', 'grnService','toastr','DTColumnDefBuilder','DTOptionsBuilder'];//,'DTColumnDefBuilder'
	
	/* @ngInject */
	function grnController($state, $scope, grnService, toastr, DTColumnDefBuilder, DTOptionsBuilder) {//, DTColumnDefBuilder
		$scope.changeMaterial = true;
		$scope.vendorName = ""
		$scope.materialName = '';
		$scope.materialObj = 'selectMaterial';
		$scope.vendor = '';
		$scope.grnNo = "";
		$scope.vendorId = "";
		$scope.itemId = "";
		//$scope.printQty = true;
		$scope.hideSelectVendor = false;
		$scope.hideSelectMaterial = false;
		$scope.materialDetails = [];
		$scope.vendorDetails = [];
//		$scope.materialDetails = [{name: "A", vendorNo: "1", grnNo: "11"},{name: "B", vendorNo: "2", grnNo: "22"},{name: "C", vendorNo: "3", grnNo: "33"}];
//		$scope.vendorDetails = [{id:'1', name: "ABC"},{id:'2', name: "PQR"},{id:'3', name: "XYZ"}];
		$scope.selectedVendor = {};
		$scope.qrCodeArr = [];
		$scope.printButton = true;
//		vm.selectAllChk = false;
		$scope.showTable = false;
		$scope.finalArr = [];
		var selectedDataCounter = 0;
		
//		$scope.allDetails	=		[{grnId : 1234},{grnId: 5678},{grnId:91011}]
		
		var vm = angular.extend(this, {
//				changeMaterialDetails : changeMaterialDetails,
//				fetchVendorDetails : fetchVendorDetails,
//				fetchMaterialDetails : fetchMaterialDetails,
			selectAllChk : false
		});
		
		(function activate(){
			//loadVendors();
			/*	$scope.changeMaterial = true;
			$scope.vendorName = ""
			$scope.materialName = '';
			$scope.grnNo = "";
			$scope.vendorNo = "";
			$scope.hideSelectVendor = false;
			$scope.materialDetails = [{name: "A", vendorNo: "1", grnNo: "11"},{name: "B", vendorNo: "2", grnNo: "22"},{name: "C", vendorNo: "3", grnNo: "33"}];
			$scope.vendorDetails = [{id:'1', name: "ABC"},{id:'2', name: "PQR"},{id:'3', name: "XYZ"}];
			$scope.selectedVendor = {};
			$scope.fetchVendorDetails();*/
		})();
		
		/**
		  * @author : ABS
		  * @description : fetch vendor details on load.
		  * @date : 19/06/2018
		  */
		/*function loadVendors(){
			grnService.getVendorDetails().then(function(data) {
				$scope.vendorDetails = data;
			});
		}
		*/
		$scope.fetchVendorDetails = function(){
			grnService.getVendorDetails().then(function(data) {
				$scope.vendorDetails = data;
			});
		}
		
		/**
		  * @author : ABS
		  * @description : fetch material details on selection of vendor.
		  * @date : 19/06/2018
		  * @param: {Object}	vendor - vendor object 
		  */
		$scope.fetchMaterialDetails = function(vendor){
			$scope.materialObj = 'selectMaterial';
			$scope.itemId = null;
			console.log(JSON.stringify(vendor));
//			$scope.showTable = false;
//			$scope.printButton = true;
		//	$scope.selectedVendor = vendor && vendor != 'selectVendor' ? JSON.parse(vendor) : null;
			
//			console.log(vendor);
			$scope.hideSelectVendor = true;
			$scope.changeMaterial = false;
			$scope.hideSelectMaterial = false;
		//	$scope.vendorId = $scope.selectedVendor && $scope.selectedVendor.id ? $scope.selectedVendor.id : null;
			$scope.vendorId = vendor.id;
			//			var vendorId = $scope.selectedVendor && $scope.selectedVendor.id ? $scope.selectedVendor.id : null;
			
			/*if($scope.vendorId == null){
				toastr.error('Please select vendor');
				return;
			}*/
			grnService.getMaterialDetails($scope.vendorId).then(function(data) {
				$scope.materialDetails = data;
			});
		}
		
		/**
		  * @author : ABS
		  * @Description : to store item Id on change
		  * @date : 19/06/2018
		  * @param: {Object} iObj material object
		  */
		$scope.changeMaterialFun = function(iObj){
			$scope.hideSelectMaterial = true;
			var obj = JSON.parse(iObj);
			$scope.itemId = obj.id;
			$scope.vendorRefNo = obj.vendorRefNo;
//			$scope.vendorNo = obj.vendorNo;
		}
		
		/**
		  * @author : ABS
		  * @Description : fetch grn table data and show
		  * @date : 19/06/2018
		  */
		 $scope.fetchTableData = function(){
			 vm.selectAllChk = false;
			 $scope.printButton = true;
			 $scope.showTable = false;
			 $scope.printButton = true;
			 
			 var qrDiv = document.getElementById("QR");	// to remove all generated QR code from div
			 /*while (qrDiv.firstChild) {
				 qrDiv.removeChild(qrDiv.firstChild);
			 }*/
			 
			 for(var i = 0; i < $scope.finalArr.length; i++){
				 var qrDiv = document.getElementById("qr"+i);	// to remove all generated QR code from div
					 while (qrDiv.firstChild) {
						 qrDiv.removeChild(qrDiv.firstChild);
					 }
			 }
			 
			 $scope.finalArr = [];
			 
			 if($scope.grnNo){
				 if(isNaN($scope.grnNo) || parseInt($scope.grnNo) < 0){
					 toastr.error('Please enter proper GRN Number');
					 return;
				 }
				 grnService.getTableDataByGrnNo(parseInt($scope.grnNo)).then(function(data) {
						$scope.allDetails = data;
						console.log(JSON.stringify(data))
						if($scope.allDetails && $scope.allDetails.length > 0){
							$scope.showTable = true;
							setCheckbox();
							$scope.disableQrCodeButton = false;
						}else{
							$scope.showTable = false;
							toastr.error('Data is not present for entered GRN Number');
						}
				 });
			 }else{
				 if(!$scope.vendorId || $scope.vendorId == null){
					 toastr.error('Please select vendor');
					 return;
				 }
				 
				 if(!$scope.materialObj == 'selectMaterial'){
					 toastr.error('Please select material');
					 return;
				 }
				 grnService.getTableData($scope.vendorId, $scope.itemId).then(function(data) {
						$scope.allDetails = data;
						
						if($scope.allDetails && $scope.allDetails.length > 0){
							$scope.showTable = true;
							setCheckbox();
							$scope.disableQrCodeButton = false;
							setTimeout(function(){
								 window.scroll({
									  top: document.body.scrollHeight, 
									  left: 0, 
									  behavior: 'smooth' 
									});
							 },10);
							 
						}else{
							$scope.showTable = false;
							toastr.error('Data is not present for selected vendor and material');
						}
				 });
			 }
		 }
		 
		 var setCheckbox = function(){
			 for(var index in $scope.allDetails){
				 if($scope.allDetails[index].barcode && ($scope.allDetails[index].barcode != "" || $scope.allDetails[index].barcode != null)){
//					 $scope.allDetails[index].printQty = true;
//					 selectedDataCounter++;
				 }
//				 else
					 $scope.allDetails[index].printQty = false;
			 }
		 }
		 
		 /**
		  * @author : ABS
		  * @Description : toggle check box
		  * @date : 19/06/2018
		  */
		 $scope.enablePrintQuantity = function(arr){
			 arr.printQty = !arr.printQty;
			 if(arr.printQty == true){
				 arr.printCopies = arr.printCopies ? arr.printCopies : 1;
				 selectedDataCounter++;
			 }else
				 selectedDataCounter--;
			 
			 if(selectedDataCounter == $scope.allDetails.length)
				 vm.selectAllChk = true;
			 else
				 vm.selectAllChk = false;
			 
		 }
		 
		 /**
		  * @author : ABS
		  * @Description : select all data on click of Select All checkbox
		  * @date : 19/06/2018
		  */
		 $scope.selectAllTable = function(){
//			 vm.selectAllChk = !vm.selectAllChk;
			 for(var index in $scope.allDetails){
				 $scope.allDetails[index].printQty = vm.selectAllChk;
				 if(vm.selectAllChk)
					 $scope.allDetails[index].printCopies = $scope.allDetails[index].printCopies ? $scope.allDetails[index].printCopies : 1;
			 }
			 if(vm.selectAllChk)
				 selectedDataCounter = $scope.allDetails.length;
			 else
				selectedDataCounter = 0;
			 
		 }
		 
		 /**
		  * @author : ABS
		  * @description : to generate QR code
		  * @date : 19/06/2018
		  */
		 $scope.makeCode = function(){
			 $scope.disableQrCodeButton = true;
			 $scope.qrCodeArr = [];
			 var sArr = [];
			 var copiesArr = [];
			 $scope.copiesArr = [];
			 $scope.qrArrAll = [];
			 
			 for(var i = 0; i < $scope.finalArr.length; i++){
				 var qrDiv = document.getElementById("qr"+i);	// to remove all generated QR code from div
					 while (qrDiv.firstChild) {
						 qrDiv.removeChild(qrDiv.firstChild);
					 }
			 }
			 
			 $scope.finalArr = [];
			 var qrDiv = document.getElementById("QR");	// to remove all generated QR code from div
			 /*while (qrDiv.firstChild) {
				 qrDiv.removeChild(qrDiv.firstChild);
			 }*/
			 
			 for(var index in $scope.allDetails){	// put all selected rows into array
				 if($scope.allDetails[index].printQty == true){
					 $scope.qrCodeArr.push($scope.allDetails[index]);
//					 var val = parseInt(document.getElementById('print'+index).value);
					 var val = parseInt($scope.allDetails[index].printCopies);
					 if(isNaN(val) || val < 1){
						 $scope.printButton = true;
						 toastr.error('Please enter proper number to print copies.');
						 document.getElementById('print'+index).focus();
						 return;
					 }
					 copiesArr.push(val);
					 $scope.copiesArr.push(val);
					 for(var i = 0; i < val; i++){
						 var iObj = Object.assign({},$scope.allDetails[index]);
						 if(i > 0){
							 iObj.copy = true;
						 }
						 $scope.qrArrAll.push(iObj);
					 }
				 }
			 }
			 
			 $scope.finalArr = $scope.qrArrAll;
			 if(!$scope.qrCodeArr || $scope.qrCodeArr.length == 0){
				 toastr.error('Please select item to generate QR code.');
				 return;
			 }
				 
				
			 setTimeout(function(){
				 var arr = [];
				 for(var i in $scope.finalArr){
					 var day = new Date($scope.finalArr[i].grnDate).getDate();
					 var month = new Date($scope.finalArr[i].grnDate).getMonth();
					 month = parseInt(month) + 1;
					 var year = new Date($scope.finalArr[i].grnDate).getFullYear();
					 
					 var qrCodeStr = '001-' + $scope.finalArr[i].grnId + '-'+ $scope.finalArr[i].itemId +'-'+ $scope.finalArr[i].itemLotNo + '-' + day + '/' + month + '/' + year;		//$scope.qrCodeArr[i].venPostDate;
					 qrCodeStr = qrCodeStr.replace(/\s/g,'');
					 
					 $scope.finalArr.barcode = qrCodeStr;
					 
					 var e = document.getElementById('qr'+i);
					 generateQRCode('qr'+i, qrCodeStr);
					 
					 if(!$scope.finalArr[i].barcode || $scope.finalArr[i].barcode == '' || $scope.finalArr[i].barcode == null){
						 if(arr.indexOf($scope.finalArr[i].grnItmLotID) == -1){
							 arr.push($scope.finalArr[i].grnItmLotID);
							 grnService.sendQrCode($scope.finalArr[i].grnItmLotID,qrCodeStr).then(function(data){
							 });
						 }
					 }
				 }
			 },0);
			 
			 
			 /*for(var i in $scope.qrCodeArr){	// create child div to show QR code and make QR code.
				 var node = document.createElement("div");     
				 node.setAttribute("id", 'qr' + i);
				 document.getElementById("QR").appendChild(node);
				 
				 var ele = document.getElementById('qr'+i);
				 ele.style.borderStyle = "ridge";
				 ele.style.borderColor = "#e67817"
				 ele.style.borderWidth = 'thin';
				 ele.style.paddingTop = "2vh";
				 
				 var day = new Date($scope.qrCodeArr[i].grnDate).getDate();
				 var month = new Date($scope.qrCodeArr[i].grnDate).getMonth();
				 month = parseInt(month) + 1;
				 var year = new Date($scope.qrCodeArr[i].grnDate).getFullYear();
				 
				 var qrCodeStr = $scope.qrCodeArr[i].grnId +'-'+ $scope.qrCodeArr[i].itemLotNo + '-' + day + '/' + month + '/' + year;		//$scope.qrCodeArr[i].venPostDate;
				 generateQRCode('qr'+i, qrCodeStr);
				 
				 var s = $scope.qrCodeArr[i].itemDtils + '-' + $scope.qrCodeArr[i].itemLotNo + '-' + day + '/' + month + '/' + year;
				 var iObj = {
						 id : i,
						 values : s
				 };
				 sArr.push(iObj);
				 var node = document.createElement("div");     
				 node.setAttribute("id", 'qrText' + i);
				 document.getElementById("qr"+i).appendChild(node);
				 document.getElementById('qrText' + i).innerHTML  =  sArr[i].values;
				 
				 for(var j = 0; j < copiesArr[i]  - 1; j++){
					 var node = document.createElement("div");     
					 node.setAttribute("id", 'qr' + i + 'hidden' + j);
					 node.setAttribute("class",'ng-hide');
					 document.getElementById("QR").appendChild(node);
					 
					 var ele = document.getElementById('qr'+i+'hidden'+j);
					 ele.style.borderStyle = "ridge";
					 ele.style.paddingTop = "2vh";
					 ele.style.borderColor = "#e67817"
					 ele.style.borderWidth = 'thin';
					 generateQRCode('qr'+i+'hidden'+j, qrCodeStr);
					 
					 var node = document.createElement("div");     
					 node.setAttribute("id", 'qr'+i+'hidden'+j+'text');
					 document.getElementById('qr'+i+'hidden'+j).appendChild(node);
					 document.getElementById('qr'+i+'hidden'+j+'text').innerHTML  =  sArr[i].values;
				 }
				 
				 if(!$scope.qrCodeArr[i].barcode || $scope.qrCodeArr[i].barcode == '' || $scope.qrCodeArr[i].barcode == null){
					 grnService.sendQrCode($scope.qrCodeArr[i].grnItmLotID,qrCodeStr).then(function(data) {
					 });
				 }
			 }*/
			 if($scope.qrCodeArr && $scope.qrCodeArr.length > 0)
				 $scope.printButton = false;
			 else
				 $scope.printButton = true;
			 
			 setTimeout(function(){
				 window.scroll({
					  top: document.body.scrollHeight, 
					  left: 0, 
					  behavior: 'smooth' 
					});
			 },10);
			 console.log(JSON.stringify($scope.finalArr));
		 }
		 
		 /**
		  * @author : ABS
		  * @description : to generate QR code
		  * @date : 19/06/2018
		  */
		 var generateQRCode = function(divId, s){
//			 var s = grnId + '' + itemLotNo + '' + venPostDate;
			 var qrcode = new QRCode(document.getElementById(divId), {
					width : 100,
					height : 100
			 });
			 qrcode.makeCode(s);
		 }
		 
		 /**
		  * @author : ABS
		  * @description : to print QR code
		  * @date : 19/06/2018
		  */
		 $scope.printCode = function(){
			var innerContents = document.getElementById('QR').innerHTML;
	        var popupWinindow = window.open('', '_blank', 'width=600,height=700,scrollbars=no,menubar=no,toolbar=no,location=no,status=no,titlebar=no');
	        popupWinindow.document.open();
//	        document.body.innerHTML = innerContents;
//	        window.print();
	        popupWinindow.document.write('<style> @page {  margin: 8;} </style>');
	        popupWinindow.document.write('<html><body onload="window.print()">' + innerContents + '</html>');
	        popupWinindow.document.close();
		 }
		 
		 
		 
		 /**
		  * @author : ABS
		  * @Description : init controller
		  * @date : 19/06/2018
		  */
		var init = function(){
			$scope.fetchVendorDetails();
			$scope.dtOptions = DTOptionsBuilder.newOptions().withDOM('C<"clear">lfrtip');
		    $scope.dtColumnDefs = [
		         DTColumnDefBuilder.newColumnDef(7).notSortable(),
		         DTColumnDefBuilder.newColumnDef(8).notSortable()
		    ];
			
		}
		init();
		
		
	}
})();


