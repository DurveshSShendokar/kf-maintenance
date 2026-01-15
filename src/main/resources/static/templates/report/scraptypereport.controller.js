(function() {
	'use strict';

	angular.module('myApp.scraptypereport').controller('scraptypereportController', scraptypereportController)

	scraptypereportController.$inject = [ '$state', 'scraptypereportService', '$uibModal', '$log',
			'$scope', 'toastr' ];
	/* @ngInject */
	function scraptypereportController($state, scraptypereportService, $uibModal, $log, $scope, toastr) {
		var vm = angular.extend(this, {
			genearteReport : genearteReport,
			getReport : getReport
		});

		var obj = {};
		(function activate() {
			$scope.showGraph = false;
			$scope.selectType = 'selectType';
			$scope.selectUom = 'selectUom';
			$scope.startDate=new Date();
			$scope.endDate=new Date();
//			getReport();
		})();

		
		function getReport(){
			
			if(!$scope.endDate || !$scope.startDate){
				toastr.error('Please select start date and end date');
				return;
			}
			if($scope.endDate && $scope.startDate){
				var sdd = $scope.startDate.getDate() < 10 ? '0' + ($scope.startDate.getDate()) : $scope.startDate.getDate();
				var smm = $scope.startDate.getMonth() + 1 < 10 ? '0' + ($scope.startDate.getMonth() + 1) : ($scope.startDate.getMonth() + 1);
				var syy = $scope.startDate.getFullYear();
				
				var edd = $scope.endDate.getDate() < 10 ? '0' + ($scope.endDate.getDate()) : $scope.endDate.getDate();
				var emm = $scope.endDate.getMonth() + 1 < 10 ? '0' + ($scope.endDate.getMonth() + 1) : ($scope.endDate.getMonth() + 1);
				var eyy = $scope.endDate.getFullYear();

				obj.startDate = syy + '-' + smm + '-' + sdd;
				obj.endDate = eyy + '-' + emm + '-' + edd;
				//alert(obj.startDate);
				//alert(obj.endDate);
			}
			
			/*scraptypereportService.scrapdeptreport(obj).then(function(data){
				vm.report = data;
			});*/
		}
		
		function genearteReport(){
			if($scope.selectType == 'selectType'){
				toastr.error('Please select report type');
				document.getElementById('type').focus();
				return;
			}
			if($scope.selectUom == 'selectUom'){
				toastr.error('Please select UOM');
				document.getElementById('uom').focus();
				return;
			}
			if(!$scope.endDate || !$scope.startDate){
				toastr.error('Please select start date and end date');
				return;
			}
			
			var sdd = $scope.startDate.getDate() < 10 ? '0' + ($scope.startDate.getDate()) : $scope.startDate.getDate();
			var smm = $scope.startDate.getMonth() + 1 < 10 ? '0' + ($scope.startDate.getMonth() + 1) : ($scope.startDate.getMonth() + 1);
			var syy = $scope.startDate.getFullYear();
			
			var edd = $scope.endDate.getDate() < 10 ? '0' + ($scope.endDate.getDate()) : $scope.endDate.getDate();
			var emm = $scope.endDate.getMonth() + 1 < 10 ? '0' + ($scope.endDate.getMonth() + 1) : ($scope.endDate.getMonth() + 1);
			var eyy = $scope.endDate.getFullYear();
			
			var obj = {};
			obj.startDate = syy + '-' + smm + '-' + sdd;
			obj.endDate = eyy + '-' + emm + '-' + edd;
			
			$scope.showGraph = false;
			if($scope.selectType == 'department'){
				scraptypereportService.getScrapsDepartmentType($scope.selectUom, obj).then(function(data){
					vm.graphData = data;
					
					$scope.categories = [];
					$scope.series = [];
					var data = [];
					
					for(var i = 0; i < vm.graphData.length; i++){
						$scope.categories.push(vm.graphData[i][0]);
						data.push(vm.graphData[i][1]);
					}
					$scope.series.push({data : data});
					$scope.showGraph = true;
					
					setTimeout(function(){
						window.scroll({
							  top: document.body.scrollHeight,  
							  left: 0, 
							  behavior: 'smooth' 
							});
					}, 0);
					
				});
			}else if($scope.selectType == 'scrap'){
				scraptypereportService.getScrapsScrapType($scope.selectUom, obj).then(function(data){
					vm.graphData = data;
					
					$scope.categories = [];
					$scope.series = [];
					var data = [];
					
					for(var i = 0; i < vm.graphData.length; i++){
						$scope.categories.push(vm.graphData[i][0]);
						data.push(vm.graphData[i][1]);
					}
					$scope.series.push({data : data});
					$scope.showGraph = true;
					
					setTimeout(function(){
						window.scroll({
							  top: document.body.scrollHeight,  
							  left: 0, 
							  behavior: 'smooth' 
							});
					}, 0);
				});
			}
			
		}

	}
})();
