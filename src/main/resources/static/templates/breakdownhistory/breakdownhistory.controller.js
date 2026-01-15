(function() {
	'use strict';

	angular.module('myApp.breakdownhistory').controller('BreakdownHistoryController', BreakdownHistoryController);

	BreakdownHistoryController.$inject = [ '$state', 'breakdownhistoryService', '$uibModal', '$log', '$scope', 'toastr', 'machine_mstService'];

	/* @ngInject */
	function BreakdownHistoryController($state, breakdownhistoryService, $uibModal, $log, $scope, toastr, machine_mstService) {
		var vm = angular.extend(this, {
			breakdownHistory : [],
			loadHistory : loadHistory,
			convertJSONToExcel:convertJSONToExcel
		});
		
		(function activate() {
			$scope.selectMachine = 'selectMachine';
			$scope.startDate = new Date();
			$scope.endDate = new Date();
				$scope.endDate.setDate($scope.endDate.getDate() + 1);

			
			loadHistory();
			loadMachines();
			
		})();
		vm.labels = { 'srNo': 'Sr No', 'bd_slip': 'Breakdown Slip','machine.machine_name': 'Machine Name', 'machine.eqid': 'Machine Id',  'observation': 'Obervation', 'statusStr':'Status', 'done_by': 'Done By' ,'ticket_raised_time': 'Ticket Created Date Time','ticket_closed_time' :'Ticket Closed Time','tc_tr_hr-arr.total_trial_hr': 'TTR','diff_days' :'TBF','total_trial_hr':'Total Breakdowns(Hrs)'}
		$scope.export = function() {
			document.getElementById('btnExport').click();
		}

		 function convertJSONToExcel() {
		      // Sample JSON data
		      var jsonData = vm.breakdownHistory
		      // Create a new workbook
		      var wb = XLSX.utils.book_new();
		      var ws = XLSX.utils.json_to_sheet(jsonData);

		      // Add the worksheet to the workbook
		      XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');

		      // Save the workbook as an Excel file
		      XLSX.writeFile(wb, 'BrekdownHistory.xlsx');
		    }

		// ******************************************************
		
		function loadHistory(){
			
			var obj = {
			}
		//	$scope.endDate.setDate($scope.endDate.getDate() + 1);

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
			/*if($scope.selectMachine != 'selectMachine'){
				var machineObj = $scope.selectMachine;
				obj.machine = {
						machine_name : machineObj
				}
			}*/
			console.log("obj"+JSON.stringify(obj ))
			breakdownhistoryService.getbreakdownhistory(obj).then(function(data){
				vm.breakdownHistoryData = data;
				console.log("Sssss"+JSON.stringify(vm.breakdownHistoryData.length))
				setTimeout(function(){
					window.scroll({
						  top: document.body.scrollHeight, 
						  left: 0, 
						  behavior: 'smooth' 
					});
				},0);
			});
		}
		
		function loadMachines() {
			machine_mstService.getMachineNames().then(function(data) {
				vm.machines = data;
				console.log("Machine Name :: "+JSON.stringify(vm.machines ))
				
			});
		}		
			
		

		
		
	}

})();
