(function() {
	'use strict';

	angular.module('myApp.scraptypereport').controller('scrapdeptreportController', scrapdeptreportController)

	scrapdeptreportController.$inject = [ '$state', 'scraptypereportService', '$uibModal', '$log',
			'$scope', 'toastr' ];
	/* @ngInject */
	function scrapdeptreportController($state, scraptypereportService, $uibModal, $log, $scope, toastr) {
		var vm = angular.extend(this, {
			report : [],
			getReport : getReport
		});

		(function activate() {
			$scope.startDate = new Date();
			$scope.endDate = new Date();
			
			getReport();
		})();

		
		function getReport(){
			var obj = {};
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
			}
			
			scraptypereportService.scrapdeptreport(obj).then(function(data){
				vm.report = data;
			});
		}

	}
})();
