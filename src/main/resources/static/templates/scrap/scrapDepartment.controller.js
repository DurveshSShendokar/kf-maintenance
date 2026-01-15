(function() {
	'use strict';

	angular.module('myApp.scrap').controller('scrapDepartmentController', scrapDepartmentController)

	scrapDepartmentController.$inject = [ '$state', 'scrapService', '$uibModal', '$log',
			'$scope', 'toastr' ];
	/* @ngInject */
	function scrapDepartmentController($state, scrapService, $uibModal, $log, $scope, toastr) {
		var vm = angular.extend(this, {
			getTicketNumber	:	getTicketNumber,
			searchSpecific	:	searchSpecific,
			changeDepartmentStatus	:	changeDepartmentStatus
		});

		(function activate() {
			$scope.selectDept = 'selectDept';
			$scope.selectNote = 'selectNote';
			
			getScraps();
			getDepartmentsList();
//			getScrapTypeList();
		})();

		function getScraps(){
			scrapService.getScraps().then(function(response){
				vm.scrapList = response;
				vm.scrapDisplay = vm.scrapList;
				console.log("ddddddd"+JSON.stringify(vm.scrapDisplay[0].scraplist.item_name))
			});
		}
		
		function getTicketNumber(){
			$scope.selectNote = 'selectNote';
			if($scope.selectDept == 'selectDept')
				return;
			
			var deptId = JSON.parse($scope.selectDept).department_id;
			vm.scrapNoteList = [];
			for(var  i =0; i < vm.scrapList.length; i++){
				if(vm.scrapList[i].scrapDepartment.department_id == deptId)
					vm.scrapNoteList.push(vm.scrapList[i].ticket_no);
			}
		}
				
		function getDepartmentsList(){
			scrapService.getDepartmentsList().then(function(response){
				vm.departmentList = response;
			});
		}
		
		function searchSpecific(){
			if(($scope.selectDept != 'selectDept' && $scope.selectNote == 'selectNote') || ($scope.selectDept == 'selectDept' && $scope.selectNote != 'selectNote')){
				if($scope.selectDept == 'selectDept'){
					toastr.error('Please select department');
					document.getElementById('dept').focus();
					return;
				}
				if($scope.selectNote == 'selectNote'){
					toastr.error('Please select note');
					document.getElementById('note').focus();
					return;
				}
			}else if($scope.selectDept == 'selectDept' && $scope.selectNote == 'selectNote'){
				vm.scrapDisplay = vm.scrapList;
			}else{
				var deptId = JSON.parse($scope.selectDept).department_id;
				var ticketNo = $scope.selectNote;
				vm.scrapDisplay = [];
				for(var i = 0; i < vm.scrapList.length; i++){
					if(vm.scrapList[i].scrapDepartment.department_id == deptId && vm.scrapList[i].ticket_no == ticketNo)
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
			
		}
		
		function changeDepartmentStatus(iObj,iStatus){
			var obj = {
					ticket_no	: iObj.ticket_no,
					status		: iStatus	
			}
			
			scrapService.changeDepartmentStatus(obj).then(function(response){
				if(response.status == 200){
					toastr.success(response.data.message);
//					$scope.showNewEntry = false;
					getScraps();
					getDepartmentsList();
					
					$scope.selectDept = 'selectDept';
					$scope.selectNote = 'selectNote';
				}else
					toastr.error(response.data.message);
			});
			
		}
		
		

	}
})();
