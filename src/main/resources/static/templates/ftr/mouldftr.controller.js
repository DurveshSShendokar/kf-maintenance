(function() {
	'use strict';

	angular.module('myApp.ftr').controller('MouldFtrController', MouldFtrController);

	MouldFtrController.$inject = [ '$state', 'ftrService', '$uibModal', '$log', '$scope', 'toastr', 'mouldbreakdownService'];

	/* @ngInject */
	function MouldFtrController($state, ftrService, $uibModal, $log, $scope, toastr, mouldbreakdownService) {
		var vm = angular.extend(this, {
			ftr : [],
			getReportGenerationDetails : getReportGenerationDetails
		});
		
		(function activate() {
			$scope.selectMould	=	'selectMould';
			$scope.reportDuration = 'monthly';
			$scope.showGraph = false;
			
			vm.months = {"Jan":1,  "Feb":2,  "Mar":3, "Apr":4,  "May":5, "Jun":6, "Jul":7, "Aug":8, "Sep":9, "Oct":10, "Nov":11, "Dec":12};
			vm.monthsQur = {"Jan-Mar":1,  "Apr-Jun":2,  "Jul-Sept":3, "Oct-Dec":4};
			
			loadMouldList();
		})();

		
		function loadMouldList() {
			mouldbreakdownService.getMouldList().then(function(data) {
				vm.mouldList = data;
			});
		}
		
		function getReportGenerationDetails(){
			$scope.showGraph = false;
			
			if($scope.selectMould == 'selectMould'){
				toastr.error('Please select Mould');
				return;
			}
			
			var obj = {
					mouldmasterLocal : JSON.parse($scope.selectMould)
			}
			if($scope.reportDuration == 'monthly'){
				monthlyGraph(obj);
			}else if($scope.reportDuration == 'quarterly'){
				quartGraph(obj);
			}
		}
		
		function monthlyGraph(obj){
			ftrService.getMouldFtrs(obj).then(function(data){
				vm.ftrs = data;
				
				var closeTickets = vm.ftrs[0];
				var ftr = vm.ftrs[1];
				$scope.categories = ["Jan",  "Feb",  "Mar", "Apr",  "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
				closeTickets.sort(function(a, b) {
					  // sort based on the value in the monthNames object
//					  return vm.months[a.month] - vm.months[b.month];
					return new Date(a.raised_date) - new Date(b.raised_date);
				});
				
//				console.log(closeTickets);
				$scope.data1 = [];
				var monthsArrayToDisplay = [];
				
				for(var i = 0; i < closeTickets.length; i++){
					$scope.data1.push(closeTickets[i].closedTickets);
					monthsArrayToDisplay.push(closeTickets[i].month + ' ' +new Date(closeTickets[i].raised_date).getFullYear());
				}
				
				ftr.sort(function(a, b) {
					  // sort based on the value in the monthNames object
//					  return vm.months[a.month] - vm.months[b.month];
					return new Date(a.raised_date) - new Date(b.raised_date);
				});
				
				$scope.categories = monthsArrayToDisplay;
				
//				console.log(ftr);
				$scope.data2 = [];
				for(var i = 0; i < ftr.length; i++){
					$scope.data2.push(ftr[i].ftr);
				}
				
				$scope.series = [
					{
						name : 'Total close ticket',
						data : $scope.data1
					},
					{
						name : 'FTR Count',
						data : $scope.data2
					} ]
				$scope.showGraph = true;
				setTimeout(function(){
					window.scroll({
						  top: document.body.scrollHeight,  
						  left: 0, 
						  behavior: 'smooth' 
						});
				},0);
			});
		}
		
		function quartGraph(obj){
			ftrService.getMouldFtrQuarterly(obj).then(function(data){
				vm.ftrs = data;
				
				var closeTickets = vm.ftrs[0];
				var ftr = vm.ftrs[1];
				
				$scope.categories = ["Jan-Mar",  "Apr-Jun",  "Jul-Sept", "Oct-Dec"];
				
				closeTickets.sort(function(a, b) {
					  // sort based on the value in the monthNames object
//					  return vm.monthsQur[a.month] - vm.monthsQur[b.month];
					return new Date(a.raised_date) - new Date(b.raised_date);
				});
				
//				console.log(closeTickets);
				$scope.data1 = [];
				var monthsArrayToDisplay = [];
				
				for(var i = 0; i < closeTickets.length; i++){
					$scope.data1.push(closeTickets[i].closedTickets);
					monthsArrayToDisplay.push(closeTickets[i].month + ' ' +new Date(closeTickets[i].raised_date).getFullYear());
				}
				
				$scope.categories = monthsArrayToDisplay;
				
				ftr.sort(function(a, b) {
					  // sort based on the value in the monthNames object
//					  return vm.monthsQur[a.month] - vm.monthsQur[b.month];
					return new Date(a.raised_date) - new Date(b.raised_date);
				});
				
//				console.log(ftr);
				$scope.data2 = [];
				for(var i = 0; i < ftr.length; i++){
					$scope.data2.push(ftr[i].ftr);
				}
				
				$scope.series = [
					{
						name : 'Total close ticket',
						data : $scope.data1
					},
					{
						name : 'FTR Count',
						data : $scope.data2
					} ]
				$scope.showGraph = true;
				setTimeout(function(){
					window.scroll({
						  top: document.body.scrollHeight,  
						  left: 0, 
						  behavior: 'smooth' 
						});
				},0);
			});
		}



	}

})();
