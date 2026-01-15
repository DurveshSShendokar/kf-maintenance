(function() {
	'use strict';

	angular.module('myApp.WeekWiseBrekdown').controller('WeekWiseBrekdownController', WeekWiseBrekdownController);

	WeekWiseBrekdownController.$inject = [ '$state', 'machineService', '$uibModal', '$log',
			'$scope', 'toastr', '$stateParams','genericFactory'];

	/* @ngInject */
	function WeekWiseBrekdownController($state, machineService, $uibModal, $log, $scope, toastr, $stateParams,genericFactory) {
			var week = $stateParams.week;
			var machineName = $stateParams.machine;
			var status = $stateParams.status;
			var breakdownUrl = staticUrlMaintenance+"/breakdown";
		var vm = angular.extend(this, {
			
		});

		(function activate() {
		$scope.weekNo=week;
		$scope.machineName=machineName;
		$scope.status=status;
		loadMaitenance()
		})();

		// ******************************************************
		function loadMaitenance(){
			console.log("machines : ")
			var msg=""
				 var url =breakdownUrl+"/getBreakdownDatailsByWeekAndMachienName?week="+$scope.weekNo+"&machineName="+$scope.machineName+"&status="+status;
				 console.log("url : "+url)
				genericFactory.getAll(msg,url).then(function(response) {
				vm.breaksowns= response.data;
				console.log("breaksowns : "+JSON.stringify(vm.breaksowns))
								
			});
			
			
		}
	
	}

	
})();
