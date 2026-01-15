(function() {
	'use strict';

	angular.module('myApp.scrap').controller('scrapYardController', scrapYardController)

	scrapYardController.$inject = [ '$state', 'scrapService', '$uibModal', '$log',
			'$scope', 'toastr' ];
	/* @ngInject */
	function scrapYardController($state, scrapService, $uibModal, $log, $scope, toastr) {
		var vm = angular.extend(this, {
			getScrapType	:	getScrapType,
			searchSpecific	:	searchSpecific,
			changeYardStatus	:	changeYardStatus,
			editEntry	:	editEntry,
			createNewTicket	:	createNewTicket
		});

		(function activate() {
			$scope.selectDept = 'selectDept';
			$scope.selectType = 'selectType';
			
			$scope.selectDeptScrap = 'selectDept';
			$scope.selectTypeScrap = 'selectType';
			$scope.selectUomScrap = 'selectUom'
			$scope.showNewEntry	=	false;
			getScraps();
			getDepartmentsList();
			getScrapTypeListForEdit();
		})();

		function getScraps(){
			scrapService.getYardDepartmentList().then(function(response){
				vm.scrapList = response;
				vm.scrapDisplay = vm.scrapList;
				console.log("JSON111"+JSON.stringify(vm.scrapDisplay))
			});
		}
		
		function editEntry(iObj){
//			$scope.showComment = true;
			$scope.showNewEntry = true;
			$scope.scrap = {};
			$scope.scrap = iObj;
			$scope.scrap.scrapentery_date = new Date($scope.scrap.scrapentery_date);
			$scope.selectDeptScrap = JSON.stringify(iObj.scrapDepartment);
			/*$scope.selectTypeScrap = JSON.stringify(iObj.scraplist);
			$scope.selectUomScrap = iObj.uom ? JSON.stringify(iObj.uom) : 'selectUom';*/
			
			delete iObj.scraplist.$$hashKey;
			$scope.selectTypeScrap = JSON.stringify(iObj.scraplist);
			$scope.selectUomScrap = iObj.uom ? iObj.uom : 'selectUom';
			
			setTimeout(function(){
				 window.scroll({
					  top: document.body.scrollHeight, 
					  left: 0, 
					  behavior: 'smooth' 
					});
			 },0);
		}
		
		function getDepartmentsList(){
			scrapService.getDepartmentsList().then(function(response){
				vm.departmentList = response;
			});
		}
		
		function getScrapTypeListForEdit(){
			scrapService.getScrapTypeList().then(function(response){
				vm.scrapTypeListEdit = response;
			});
		}
		
		function getScrapType(){
			if($scope.selectDept == 'selectDept')
				return;
			
			var deptId = JSON.parse($scope.selectDept).department_id;
			vm.scrapTypeList = [];
			
			var set = new Set();
			
			for(var  i =0; i < vm.scrapList.length; i++){
				if(vm.scrapList[i].scrapDepartment.department_id == deptId && !set.has(vm.scrapList[i].scraplist.item_id)){
					set.add(vm.scrapList[i].scraplist.item_id);
					vm.scrapTypeList.push(vm.scrapList[i].scraplist);
				}
					
			}
		}
				
		/*function getDepartmentsList(){
			scrapService.getYardDepartmentList().then(function(response){
				vm.departmentList = response;
			});
		}
		
		function getScrapTypeList(){
			scrapService.getScrapTypeList().then(function(response){
				vm.scrapTypeList = response;
			});
		}*/
		
		function searchSpecific(){
			if($scope.selectDept == 'selectDept' && $scope.selectType == 'selectType'){
				vm.scrapDisplay = vm.scrapList;
			}else if($scope.selectDept != 'selectDept' && $scope.selectType != 'selectType'){
				var deptId = JSON.parse($scope.selectDept).department_id;
				var ticketNo = JSON.parse($scope.selectType).item_id;
				vm.scrapDisplay = [];
				for(var i = 0; i < vm.scrapList.length; i++){
					if(vm.scrapList[i].scrapDepartment.department_id == deptId && vm.scrapList[i].scraplist.item_id == ticketNo)
						vm.scrapDisplay.push(vm.scrapList[i]);
						
				}
				
				setTimeout(function(){
					window.scroll({
						  top: document.body.scrollHeight, 
						  left: 0, 
						  behavior: 'smooth' 
					});
				},0);
			}else if($scope.selectDept != 'selectDept'){
				var deptId = JSON.parse($scope.selectDept).department_id;
//				var ticketNo = JSON.parse($scope.selectType).item_id;
				vm.scrapDisplay = [];
				for(var i = 0; i < vm.scrapList.length; i++){
					if(vm.scrapList[i].scrapDepartment.department_id == deptId)
						vm.scrapDisplay.push(vm.scrapList[i]);
						
				}
				
				setTimeout(function(){
					window.scroll({
						  top: document.body.scrollHeight, 
						  left: 0, 
						  behavior: 'smooth' 
					});
				},0);
			}else if($scope.selectType != 'selectType'){
//				var deptId = JSON.parse($scope.selectDept).department_id;
				var ticketNo = JSON.parse($scope.selectType).item_id;
				vm.scrapDisplay = [];
				for(var i = 0; i < vm.scrapList.length; i++){
					if(vm.scrapList[i].scraplist.item_id == ticketNo)
						vm.scrapDisplay.push(vm.scrapList[i]);
						
				}
				
				setTimeout(function(){
					window.scroll({
						  top: document.body.scrollHeight, 
						  left: 0, 
						  behavior: 'smooth' 
					});
				},0);
			}
			
			
			
			/*if(($scope.selectDept != 'selectDept' && $scope.selectType == 'selectType') || ($scope.selectDept == 'selectDept' && $scope.selectType != 'selectType')){
				if($scope.selectDept == 'selectDept'){
					toastr.error('Please select department');
					document.getElementById('dept').focus();
					return;
				}
				if($scope.selectType == 'selectType'){
					toastr.error('Please select Type');
					document.getElementById('note').focus();
					return;
				}
			}else if($scope.selectDept == 'selectDept' && $scope.selectType == 'selectType'){
				vm.scrapDisplay = vm.scrapList;
			}else{
				
			}*/
			
		}
		
		function changeYardStatus(iObj,iStatus){
			var obj = {
					ticket_no	: iObj.ticket_no,
					status		: iStatus	
			}
			
			scrapService.changeYardStatus(obj).then(function(response){
				if(response.status == 200){
					toastr.success(response.data.message);
//					$scope.showNewEntry = false;
					getScraps();
				}else
					toastr.error(response.data.message);
			});
			
		}
		
		function createNewTicket(iObj){
			if($scope.selectDeptScrap == 'selectDept'){
				toastr.error('Please select department');
				document.getElementById('dept').focus();
				return;
			}
			if($scope.selectTypeScrap == 'selectType'){
				toastr.error('Please select scrap type');
				document.getElementById('type').focus();
				return;
			}
			if(!iObj.quantity || iObj.quantity == ''  ){
				toastr.error('Please enter proper quantity');
				document.getElementById('qty').focus();
				return;
			}
			if($scope.selectUomScrap == 'selectUom'){
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
			iObj.scrapDepartment = JSON.parse($scope.selectDeptScrap);
			iObj.scraplist = JSON.parse($scope.selectTypeScrap);
			iObj.uom	=	$scope.selectUomScrap;
				
			scrapService.updateYard(iObj).then(function(response){
				if(response.status == 200){
					toastr.success(response.data.message);
					$scope.showNewEntry = false;
					getScraps();
				}else
					toastr.error(response.data.message);
//				toastr.success('Ticket created successfully');
			});
			
		}
		

	}
})();
