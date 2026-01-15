(function() {
	'use strict';

	angular.module('myApp.scrap').controller('scrapController', scrapController)

	scrapController.$inject = [ '$state', 'scrapService', '$uibModal', '$log',
			'$scope', 'toastr', ];
	/* @ngInject */
	function scrapController($state, scrapService, $uibModal, $log, $scope, toastr) {
		var vm = angular.extend(this, {
			createNewTicket	:	createNewTicket,
			createNewEntry	:	createNewEntry,
			edit			:	edit,
			getItemsList	:	getItemsList
		});

		(function activate() {
			$scope.showNewEntry = false;
			$scope.fromDate = new Date();
			$scope.toDate = new Date();
			//getScraps();
			getItemsList();
			getDepartmentsList();
			getScrapTypeList();
		})();

		$(document).on('submit', '#qty', function(e){
		    e.preventDefault();
		    var formData = $(this).serialize();
		    alert(formData);
		});
		
		
		function getScraps(){
			scrapService.getScraps().then(function(response){
				vm.scrapList = response;
			});
		}

		function getItemsList(){
			if(!$scope.fromDate || !$scope.toDate){
				return;
			}else if($scope.fromDate > $scope.toDate){
				toastr.error('From date can not be greater than end date');
				return;
			}
			
			var fDateDay = $scope.fromDate.getDate() < 10 ? '0'+($scope.fromDate.getDate()) : ($scope.fromDate.getDate());
			var fDateMonth = ($scope.fromDate.getMonth() + 1) < 10 ? '0'+($scope.fromDate.getMonth() + 1) : ($scope.fromDate.getMonth() + 1);
			var tDateDay = ($scope.toDate.getDate()) < 10 ? '0'+($scope.toDate.getDate()) : ($scope.toDate.getDate());
			var tDateMonth = ($scope.toDate.getMonth() + 1) < 10 ? '0'+($scope.toDate.getMonth() + 1) : ($scope.toDate.getMonth() + 1);
			var obj = {
				startDate  :	$scope.fromDate.getFullYear() + '-' + fDateMonth + '-' + fDateDay,
				endDate  :		$scope.toDate.getFullYear() + '-' + tDateMonth + '-' + tDateDay,
			}
			
			scrapService.getScrapDeparmentListDateWise(obj).then(function(response){
				vm.scrapList = response;
			});
		}
		
		function createNewEntry(){
			$scope.showComment = false;
			$scope.scrap = {};
			$scope.hidetext = true;
			$scope.selectDept = 'selectDept';
			$scope.selectType = 'selectType';
			$scope.selectUom = 'selectUom';
			$scope.disableDept = false;
			
			$scope.scrap.scrapentery_date = new Date();
			$scope.showNewEntry = true;
			
			$scope.scrap.ticket_no = vm.scrapList.length == 0 ? 1 : vm.scrapList[vm.scrapList.length - 1].scrapentery_id + 1;
			
			/*setTimeout(function(){
				 window.scroll({
					  top: document.body.scrollHeight, 
					  left: 0, 
					  behavior: 'smooth' 
					});
			 },0);*/
		}
		
		function getDepartmentsList(){
			scrapService.getDepartmentsList().then(function(response){
				vm.departmentList = response;
			});
		}
		
		function getScrapTypeList(){
			scrapService.getScrapTypeList().then(function(response){
				vm.scrapTypeList = response;
			});
		}
		
		function edit(iObj){
			$scope.showComment = true;
			$scope.showNewEntry = true;
			$scope.hidetext = false;
			$scope.disableDept = true;
			$scope.scrap = {};
			$scope.scrap = iObj;
			$scope.scrap.scrapentery_date = new Date($scope.scrap.scrapentery_date);
			$scope.selectDept = JSON.stringify(iObj.scrapDepartment);
			/*$scope.selectType = JSON.stringify(iObj.scraplist);
			$scope.selectUom = iObj.uom ? JSON.stringify(iObj.uom) : 'selectUom';*/
			
			
			delete iObj.scraplist.$$hashKey;
			$scope.selectType = JSON.stringify(iObj.scraplist);
			$scope.selectUom = iObj.uom ? iObj.uom : 'selectUom';
			
			setTimeout(function(){
				 window.scroll({
					  top: 0, 
					  left: 0, 
					  behavior: 'smooth' 
					});
			 },0);
		}
		
		function createNewTicket(iObj){
			if($scope.selectDept == 'selectDept'){
				toastr.error('Please select department');
				document.getElementById('dept').focus();
				return;
			}
			if($scope.selectType == 'selectType'){
				toastr.error('Please select scrap type');
				document.getElementById('type').focus();
				return;
			}
			if(!iObj.quantity || iObj.quantity == ''  ){
				toastr.error('Please enter proper quantity');
				document.getElementById('qty').focus();
				return;
			}
			if($scope.selectUom == 'selectUom'){
				toastr.error('Please select UOM');
				document.getElementById('uom').focus();
				return;
			}
			if(!iObj.remark || iObj.remark == ''){
				toastr.error('Please enter remark');
				document.getElementById('uom').focus();
				return;
			}
			if($scope.showComment && (!iObj.comment || iObj.comment == '')){
				toastr.error('Please enter comment');
				document.getElementById('comment').focus();
				return;
			}
			
			iObj.comment = iObj.comment ? iObj.comment : "";
			iObj.status = iObj.status ? iObj.status : "pending";
			iObj.scrapDepartment = JSON.parse($scope.selectDept);
			iObj.scraplist = JSON.parse($scope.selectType);
			iObj.uom	=	$scope.selectUom;
				
			scrapService.createNewTicket(iObj).then(function(response){
				if(response.status == 200){
					toastr.success(response.data.message);
					$scope.showNewEntry = false;
//					getScraps();
					getItemsList();
				}else
					toastr.error(response.data.message);
//				toastr.success('Ticket created successfully');
			});
			
		}
		

	}
})();
