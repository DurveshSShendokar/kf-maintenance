(function() {
	'use strict';

	angular.module('myApp.WeekWiseMaintenance').controller('WeekWiseMaintenanceController', WeekWiseMaintenanceController);

	WeekWiseMaintenanceController.$inject = [ '$state', 'machineService', '$uibModal', '$log',
			'$scope', 'toastr', '$stateParams','genericFactory'];

	/* @ngInject */
	function WeekWiseMaintenanceController($state, machineService, $uibModal, $log, $scope, toastr, $stateParams,genericFactory) {
			var week = $stateParams.week;
			var machineName = $stateParams.machine;
				var status = $stateParams.status;
			var maintUrl = staticUrlMaintenance+"/maint";
		var vm = angular.extend(this, {
			
		});

		(function activate() {
		$scope.weekNo=week;
		$scope.machineName=machineName;
		$scope.status=status;
		loadMaitenance()
		})();
vm.labels={'srNo':'Sr No','machine.machine_name': 'Machine', 'machine.eqid': 'Machine Id','machine.location': 'Location','frequency': 'Frequency','raisedName':'RaisedBy','doneName':'Done By','week':'Week','overall_status':'overall_status','schedule_date':'Schedule Date','closedDate':'Closed Date'}
$scope.fileName="PPM Details For  "+$scope.machineName+"    on    WEEK No  "+$scope.weekNo;
console.log("File Name  "+$scope.fileName);
		// ******************************************************
		function loadMaitenance(){
			console.log("machines : ")
			var msg=""
				 var url =maintUrl+"/getMaintenanceDatailsByWeekAndMachienName?week="+$scope.weekNo+"&machineName="+$scope.machineName+"&status="+status;
				 console.log("url : "+url)
				genericFactory.getAll(msg,url).then(function(response) {
				vm.maintenace= response.data;
				console.log("maintenace : "+JSON.stringify(vm.maintenace))
								
			});
			 $scope.export=function(){
			 document.getElementById('btnExport').click();
		}
			
		}
	
	}

	
})();
