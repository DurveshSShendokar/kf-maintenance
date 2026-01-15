/**
  * @author 		: ABS
  * @name			: materialIssueController
  * @description 	: controller for material issue module
  * @date 			: 20/06/2018
  */
(function() {
	'use strict';

	angular.module('myApp.reports').controller('incomingAssemblyController', incomingAssemblyController)
								   .controller('qrcodeTraverseController', qrcodeTraverseController)
								   .controller('itemwiseMonthlyReportController', itemwiseMonthlyReportController)
								   .controller('materialIssuedHistoryController', materialIssuedHistoryController)
								   .controller('monthlyYieldReportController', monthlyYieldReportController)
								   .controller('materialAtEachStageReportController', materialAtEachStageReportController);

	incomingAssemblyController.$inject = ['$state', '$scope', 'reportsService','toastr'];
	qrcodeTraverseController.$inject = ['$state', '$scope', 'reportsService','toastr'];
	itemwiseMonthlyReportController.$inject = ['$state', '$scope', 'reportsService','toastr'];
	materialIssuedHistoryController.$inject = ['$state', '$scope', 'reportsService','toastr'];
	monthlyYieldReportController.$inject = ['$state', '$scope', 'reportsService','toastr'];
	materialAtEachStageReportController.$inject = ['$state', '$scope', 'reportsService','toastr'];
	
	/* @ngInject */
	function incomingAssemblyController($state, $scope, reportsService, toastr) {
		
		var vm = angular.extend(this, {
			reportsList : [],
			generateReport : generateReport
		});

		(function activate() {
			$scope.selectType = '1';
			$scope.date = new Date();
			$scope.reportname = 'incomingReport';
			
			generateReport();
		})();
		
		
		function generateReport(){
			if(!$scope.date){
				toastr.error('Please select date to get report');
				document.getElementById('date').focus();
				return;
			}
			
			var yy = $scope.date.getFullYear();
			var mm = $scope.date.getMonth() + 1 < 10 ? ('0' + ($scope.date.getMonth() + 1)) : ($scope.date.getMonth() + 1);
			var dd = $scope.date.getDate() < 10 ? ('0' + $scope.date.getDate()) : $scope.date.getDate();
			
			var date = yy + '-' + mm + '-' + dd;
			
			if($scope.selectType == '1'){
				reportsService.incomingReport(date).then(function(data){
					vm.reportsList = data;
					$scope.reportname = 'incomingReport';
				});
			}else if($scope.selectType == '2'){
				reportsService.assemblyReport(date).then(function(data){
					vm.reportsList = data;
					$scope.reportname = 'assemblyReport';
				});
			}
		}
		
	}
	
	function qrcodeTraverseController($state, $scope, reportsService, toastr) {
		var vm = angular.extend(this, {
			reportsList : [],
			traverseQrCode : traverseQrCode
		});
		
		
		(function activate() {
//			$scope.tree=[{"name":"Node 1","items":[{"name":"Node 1.1","items":[{"name":"Node 1.1.1","items":[{"name":"Node 1.1.1.1","items":[]}]},{"name":"Node 1.1.2","items":[]}]},{"name":"Node 1.2","items":[]}]}];
			$scope.tree=[];
		})();
		
		function traverseQrCode(code){
			if(!code || code == ''){
					document.getElementById('code').focus();
					toastr.error('Please enter Qr code');
					return;
			}
			$scope.tree = new Array();
			reportsService.traverseQrCode(code).then(function(data){
				if(data == ''){
					toastr.error('Codes not Available');
					return;
				}
				var obj = data;
				$scope.tree = new Array();
				$scope.tree.push(obj);
			});
		}
		
		
	}
	
	function itemwiseMonthlyReportController($state, $scope, reportsService, toastr) {
		var vm = angular.extend(this, {
			reportsList : [],
			generateReport : generateReport
		});

		(function activate() {
			$scope.fromDate = new Date();
			$scope.endDate = new Date();
			
			generateReport();
		})();
		
		function generateReport(){
			if(!$scope.fromDate){
				toastr.error('Please select start date');
				document.getElementById('startDate').focus();
				return;
			}
			
			if(!$scope.endDate){
				toastr.error('Please select end date');
				document.getElementById('endDate').focus();
				return;
			}
			
			if($scope.fromDate > $scope.endDate){
				toastr.error('start date can not be greater than end date');
				return;
			}
			
			$scope.toDate = new Date($scope.endDate);
			/*$scope.fromDate = new Date($scope.endDate);
			var d = new Date($scope.endDate);
			$scope.fromDate.setMonth($scope.fromDate.getMonth() - 1);*/
//			fromDate = new Date(fromDate);
			
			var fDateDay = $scope.fromDate.getDate() < 10 ? '0'+($scope.fromDate.getDate()) : ($scope.fromDate.getDate());
			var fDateMonth = ($scope.fromDate.getMonth() + 1) < 10 ? '0'+($scope.fromDate.getMonth() + 1) : ($scope.fromDate.getMonth() + 1);
			var tDateDay = ($scope.toDate.getDate()) < 10 ? '0'+($scope.toDate.getDate()) : ($scope.toDate.getDate());
			var tDateMonth = ($scope.toDate.getMonth() + 1) < 10 ? '0'+($scope.toDate.getMonth() + 1) : ($scope.toDate.getMonth() + 1);
//			var iObj = JSON.parse($scope.selectItem);
			var obj = {
				startDate  :	$scope.fromDate.getFullYear() + '-' + fDateMonth + '-' + fDateDay,
				endDate  :		$scope.toDate.getFullYear() + '-' + tDateMonth + '-' + tDateDay,
			}
			
			reportsService.getItemwiseMonthlyReport(obj).then(function(data){
				vm.reportsList = data;
			});
		}
	}
		
	function materialIssuedHistoryController($state, $scope, reportsService, toastr) {
		
		var vm = angular.extend(this, {
			itemsList : [],
			reportsList : [],
			generateReport : generateReport,
			getItemsList	:	getItemsList
		});

		(function activate() {
			$scope.fromDate = new Date();
			$scope.toDate = new Date();
			$scope.reportname = 'materialIssuedHistoryReport';
			$scope.selectItem = 'selectItem';
			
			getItemsList();
		})();
		
		function getItemsList(){
			if(!$scope.fromDate || !$scope.toDate){
				return;
			}
			if($scope.fromDate > $scope.toDate){
				toastr.error('start date can not be greater than end date');
				return;
			}
			$scope.selectItem = 'selectItem';
			var fDateDay = $scope.fromDate.getDate() < 10 ? '0'+($scope.fromDate.getDate()) : ($scope.fromDate.getDate());
			var fDateMonth = ($scope.fromDate.getMonth() + 1) < 10 ? '0'+($scope.fromDate.getMonth() + 1) : ($scope.fromDate.getMonth() + 1);
			var tDateDay = ($scope.toDate.getDate()) < 10 ? '0'+($scope.toDate.getDate()) : ($scope.toDate.getDate());
			var tDateMonth = ($scope.toDate.getMonth() + 1) < 10 ? '0'+($scope.toDate.getMonth() + 1) : ($scope.toDate.getMonth() + 1);
			
			var obj = {
				startDate  :	$scope.fromDate.getFullYear() + '-' + fDateMonth + '-' + fDateDay,
				endDate  :		$scope.toDate.getFullYear() + '-' + tDateMonth + '-' + tDateDay
			}
			reportsService.getItemsListForMaterial(obj).then(function(data){
				vm.itemsList = data;
			});
		}
		
		function generateReport(){
			if(!$scope.fromDate){
				toastr.error('Please select start date');
				document.getElementById('fromDate').focus();
				return;
			}
			if(!$scope.toDate){
				toastr.error('Please select end date');
				document.getElementById('toDate').focus();
				return;
			}
			if($scope.selectItem == 'selectItem'){
				toastr.error('Please select item');
				document.getElementById('item').focus();
				return;
			}
			
			var fDateDay = $scope.fromDate.getDate() < 10 ? '0'+($scope.fromDate.getDate()) : ($scope.fromDate.getDate());
			var fDateMonth = ($scope.fromDate.getMonth() + 1) < 10 ? '0'+($scope.fromDate.getMonth() + 1) : ($scope.fromDate.getMonth() + 1);
			var tDateDay = ($scope.toDate.getDate()) < 10 ? '0'+($scope.toDate.getDate()) : ($scope.toDate.getDate());
			var tDateMonth = ($scope.toDate.getMonth() + 1) < 10 ? '0'+($scope.toDate.getMonth() + 1) : ($scope.toDate.getMonth() + 1);
			var iObj = JSON.parse($scope.selectItem);
			var obj = {
				startDate  :	$scope.fromDate.getFullYear() + '-' + fDateMonth + '-' + fDateDay,
				endDate  :		$scope.toDate.getFullYear() + '-' + tDateMonth + '-' + tDateDay,
				itemId : iObj.id
			}
			
			reportsService.materialIssueReport(obj).then(function(data){
				vm.reportsList = data;
			});
		}
		
	}
	
	function monthlyYieldReportController($state, $scope, reportsService, toastr) {
		var vm = angular.extend(this, {
			reportsList : [],
			yearList : [],
			generateReport : generateReport
		});

		(function activate() {
			$scope.selectYear = 'selectYear';
			var date = new Date();
			var year = date.getFullYear();
			
			for(var i = 2015; i <= year; i++)
				vm.yearList.push(i);
			
//			generateReport();
		})();
		
		function generateReport(){
			if($scope.selectYear == 'selectYear'){
				return;
			}
			var year = $scope.selectYear
			reportsService.getMonthlyYieldReport(year).then(function(data){
				vm.reportsList = data;
			});
		}
	}
	
	function materialAtEachStageReportController($state, $scope, reportsService, toastr) {
		var vm = angular.extend(this, {
			reportsList : [],
			generateReport : generateReport
		});

		(function activate() {
			$scope.fromDate = new Date();
			$scope.toDate = new Date();
			
			generateReport();
		})();
		
		function generateReport(){
			if(!$scope.fromDate || !$scope.toDate){
				return;
			}
			if($scope.fromDate > $scope.toDate){
				toastr.error('start date can not be greater than end date');
				return;
			}

			var fDateDay = $scope.fromDate.getDate() < 10 ? '0'+($scope.fromDate.getDate()) : ($scope.fromDate.getDate());
			var fDateMonth = ($scope.fromDate.getMonth() + 1) < 10 ? '0'+($scope.fromDate.getMonth() + 1) : ($scope.fromDate.getMonth() + 1);
			var tDateDay = ($scope.toDate.getDate()) < 10 ? '0'+($scope.toDate.getDate()) : ($scope.toDate.getDate());
			var tDateMonth = ($scope.toDate.getMonth() + 1) < 10 ? '0'+($scope.toDate.getMonth() + 1) : ($scope.toDate.getMonth() + 1);
			
			var obj = {
				startDate  :	$scope.fromDate.getFullYear() + '-' + fDateMonth + '-' + fDateDay,
				endDate  :		$scope.toDate.getFullYear() + '-' + tDateMonth + '-' + tDateDay
			}
			console.log(JSON.stringify(obj));
			reportsService.getMaterialAtEachStageReport(obj).then(function(data){
				vm.reportsList = data;
			});
		}
	}
		
		
		
	
})();