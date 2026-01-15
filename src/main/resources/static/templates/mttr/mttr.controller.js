(function() {
	'use strict';

	angular.module('myApp.mttr').controller('MttrController', MttrController);

	MttrController.$inject = [ '$state', 'mttrService', '$uibModal', '$log', '$scope', 'toastr', 'machine_mstService', 'categoryService'];

	/* @ngInject */
	function MttrController($state, mttrService, $uibModal, $log, $scope, toastr, machine_mstService, categoryService) {
		var vm = angular.extend(this, {
			ftrs : [],
			categories : [],
			view : view,
			add : add,
			delet : delet,
//			vendorPefo : vendorPefo,
			ok : ok,
			getReportGenerationDetails : getReportGenerationDetails,
			changeCategory : changeCategory,
			changeMachine : changeMachine
		});
		
		(function activate() {
			$scope.ftr = {};
			$scope.selectMachine = 'selectMachine';
			$scope.reportDuration = 'monthly';
			$scope.category = 'category';
			$scope.type = "";
			vm.months = {"Jan":1,  "Feb":2,  "Mar":3, "Apr":4,  "May":5, "Jun":6, "Jul":7, "Aug":8, "Sep":9, "Oct":10, "Nov":11, "Dec":12};
			vm.monthsQur = {"Jan-Mar":1,  "Apr-Jun":2,  "Jul-Sept":3, "Oct-Dec":4};
			vm.m = ["jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
//			loadFtr();
			loadMachines();
			loadCategories();
			
		})();

		// ******************************************************
		
		function loadCategories(){
			categoryService.getCategorys().then(function(data){
				vm.categories = data;
			});
		}
		
		function changeMachine(){
			if($scope.selectMachine == 'selectMachine'){
				return;
			}
			
			$scope.category = 'category';
		}
		
		function changeCategory(){
			if($scope.category == 'category'){
				return;
			}
			
			$scope.selectMachine = 'selectMachine';
			$scope.type = "";
		}
		
		function loadMachines() {
			machine_mstService.getMachine_msts().then(function(data) {
				vm.machines = data;
				console.log("Machines  :: "+JSON.stringify(vm.machines))
				
			});
		}
		
			
		function ok(ftr) {
//			var ele = document.getElementById('ftrName').value = '';
			ftr.deletes = 1;
			/*debugger;*/
			mttrService.addFtr(ftr).then(function(){
				$scope.ftr = {};
				loadFtrs();
//				$uibModalInstance.close(ftr);
				
			});
		}
		
		function getReportGenerationDetails(){
			
			
			
			$scope.showGraph = false;
			if($scope.type == "" && $scope.selectMachine == 'selectMachine'){
				toastr.error('Please select equipment type and group ');
				return;
			}
			console.log("Mahine  "+$scope.selectMachine)
			console.log("reportDuration    "+$scope.reportDuration)
			var obj = {
						machine : $scope.selectMachine,
						reportDuration : $scope.reportDuration
				};
			
			
			console.log("obj  "+JSON.stringify(obj))
		/*	if($scope.reportDuration == 'quarterly'){
				mttrService.getftrQuarterly(obj).then(function(data) {
					vm.mttrs = data;
					
					var closeTickets = vm.mttrs[0];
					var mttr = vm.mttrs[1];
					
					$scope.categories = ["Jan-Mar",  "Apr-Jun",  "Jul-Sept", "Oct-Dec"];
					
					closeTickets.sort(function(a, b) {
						  // sort based on the value in the monthNames object
						  return vm.monthsQur[a.month] - vm.monthsQur[b.month];
						});
					
//					console.log(closeTickets);
					$scope.data1 = [];
					for(var i = 0; i < closeTickets.length; i++){
						$scope.data1.push(closeTickets[i].closedTickets);
					}
					
					mttr.sort(function(a, b) {
						  // sort based on the value in the monthNames object
						  return vm.monthsQur[a.month] - vm.monthsQur[b.month];
						});
					
//					console.log(ftr);
					$scope.data2 = [];
					for(var i = 0; i < mttr.length; i++){
						$scope.data2.push(mttr[i].mttr);
					}
					
					$scope.series = [
						{
							name : 'Total close ticket',
							data : $scope.data1
						},
						{
							name : 'MTTR Count',
							data : $scope.data2
						} ]
					$scope.showGraph = true;
					
				});
				
			}else */
			
			if($scope.reportDuration == 'monthly'){
				
				mttrService.getMttrs(obj).then(function(data) {
					vm.mttrs = data;
					console.log("MTTRS"+JSON.stringify(	vm.mttrs))
					var closeTickets = vm.mttrs;
//					var mttr = vm.mttrs[1];
					$scope.categories = ["Jan",  "Feb",  "Mar", "Apr",  "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
					
					/*closeTickets.sort(function(a, b) {
						  // sort based on the value in the monthNames object
//						  return vm.months[a.month] - vm.months[b.month];
							return new Date(a.raised_date) - new Date(b.raised_date);
						});*/
					
//					console.log(closeTickets);
					const currentDate = new Date();

					var currentYear = currentDate.getFullYear();
					$scope.data1 = [];
					$scope.data2 = [];
					var monthsArrayToDisplay = [];
					
					$scope.data1.push(vm.mttrs.mttrJan);
					$scope.data2.push(vm.mttrs.targetJan);
					monthsArrayToDisplay.push("JAN "+" "+currentYear)
					
					$scope.data1.push(vm.mttrs.mttrFeb);
					$scope.data2.push(vm.mttrs.targetFeb);
					monthsArrayToDisplay.push("FEB"+" "+currentYear)
					
					$scope.data1.push(vm.mttrs.mttrMar);
					$scope.data2.push(vm.mttrs.targetMar);
					monthsArrayToDisplay.push("MAR"+" "+currentYear)
					
					$scope.data1.push(vm.mttrs.mttrApr);
					$scope.data2.push(vm.mttrs.targetApr);
					monthsArrayToDisplay.push("APR"+" "+currentYear)
					
					$scope.data1.push(vm.mttrs.mttrJun);
					$scope.data2.push(vm.mttrs.targetJun);
					monthsArrayToDisplay.push("JUN"+" "+currentYear)
					
					$scope.data1.push(vm.mttrs.mttrJul);
					$scope.data2.push(vm.mttrs.targetJul);
					monthsArrayToDisplay.push("JUL"+" "+currentYear)
					
					$scope.data1.push(vm.mttrs.mttrAug);
					$scope.data2.push(vm.mttrs.targetAug);
					monthsArrayToDisplay.push("AUG"+" "+currentYear)
					
					$scope.data1.push(vm.mttrs.mttrSep);
					$scope.data2.push(vm.mttrs.targetSep);
					monthsArrayToDisplay.push("SEP"+" "+currentYear)
					
					$scope.data1.push(vm.mttrs.mttrOct);
					$scope.data2.push(vm.mttrs.targetOct);
					monthsArrayToDisplay.push("OCT"+" "+currentYear)
					
					
					$scope.data1.push(vm.mttrs.mttrNov);
					$scope.data2.push(vm.mttrs.targetNov);
					monthsArrayToDisplay.push("NOV"+" "+currentYear)
					
					$scope.data1.push(vm.mttrs.mttrDec);
					$scope.data2.push(vm.mttrs.targetDec);
					monthsArrayToDisplay.push("DEC"+" "+currentYear)
					
				console.log("data1  "+JSON.stringify(	$scope.data1))
				console.log("data2  "+JSON.stringify(	$scope.data2))

					
					
					
					$scope.categories = monthsArrayToDisplay;
					
					/*closeTickets.sort(function(a, b) {
						  // sort based on the value in the monthNames object
						  return vm.months[a.month] - vm.months[b.month];
						});
					
//					console.log(ftr);
					$scope.data2 = [];
					for(var i = 0; i < closeTickets.length; i++){
						$scope.data2.push(closeTickets[i].mttr);
					}*/
					
					$scope.series = [
						{
							name : 'MTTR',
							data : $scope.data1
						},
						{
							name : 'Target',
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
//					vendorPefo();
										
				});
			}
	if($scope.reportDuration == 'quarterly'){
				
				mttrService.getMttrs(obj).then(function(data) {
					vm.mttrs = data;
					console.log("MTTRS"+JSON.stringify(	vm.mttrs))
					var closeTickets = vm.mttrs;
//					var mttr = vm.mttrs[1];
					$scope.categories = ["Jan",  "Feb",  "Mar", "Apr",  "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
					
					/*closeTickets.sort(function(a, b) {
						  // sort based on the value in the monthNames object
//						  return vm.months[a.month] - vm.months[b.month];
							return new Date(a.raised_date) - new Date(b.raised_date);
						});*/
					
//					console.log(closeTickets);
					const currentDate = new Date();

					var currentYear = currentDate.getFullYear();
					$scope.data1 = [];
					$scope.data2 = [];
					var monthsArrayToDisplay = [];
					
					$scope.data1.push(vm.mttrs.mttr1st);
					$scope.data2.push(vm.mttrs.target1st);
					monthsArrayToDisplay.push("QUATER 1 st "+" "+currentYear)
					
					$scope.data1.push(vm.mttrs.mttr2nd);
					$scope.data2.push(vm.mttrs.target2nd);
					monthsArrayToDisplay.push("QUATER 2 nd"+" "+currentYear)
					
					$scope.data1.push(vm.mttrs.mttr3rd);
					$scope.data2.push(vm.mttrs.target3rd);
					monthsArrayToDisplay.push("QUATER 3 rd"+" "+currentYear)
					
					$scope.data1.push(vm.mttrs.mttr4th);
					$scope.data2.push(vm.mttrs.target4th);
					monthsArrayToDisplay.push("QUATER 4 th"+" "+currentYear)
					
					
				console.log("data1  "+JSON.stringify(	$scope.data1))
				console.log("data2  "+JSON.stringify(	$scope.data2))

					
					
					
					$scope.categories = monthsArrayToDisplay;
					
					/*closeTickets.sort(function(a, b) {
						  // sort based on the value in the monthNames object
						  return vm.months[a.month] - vm.months[b.month];
						});
					
//					console.log(ftr);
					$scope.data2 = [];
					for(var i = 0; i < closeTickets.length; i++){
						$scope.data2.push(closeTickets[i].mttr);
					}*/
					
					$scope.series = [
						{
							name : 'MTTR',
							data : $scope.data1
						},
						{
							name : 'Target',
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
//					vendorPefo();
										
				});
			}
		}

		
		/*function vendorPefo() {
			
			var bt = 20;
			
			
			Highcharts.chart('container4', {
				chart : {
					type : 'column'
				},
				title : {
					text : "Target-",
				},
				xAxis : {
					categories : [vm.mttrs[0][0].month,  vm.mttrs[0][1].month,  vm.mttrs[0][2].month,
						 vm.mttrs[0][3].month,  vm.mttrs[0][4].month]
					
					categories : ["Jan",  "Feb",  "Mar",
						"Apr",  "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
				},
				yAxis : {

				},
				plotOptions : {
					line : {
						dataLabels : {
							enabled : true
						},
						enableMouseTracking : false
					}
				},
				series : [
						{
							name : 'Total close ticket',
							data : $scope.data1
						},
						{
							name : 'FTR Count',
							data : $scope.data2
						} ]
			});
			
			
		}*/
		
		function delet(ftr){
			mttrService.deleteFtr(ftr).then(function(){
				loadFtrs();
			});
		}

		function view(ftr) {
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/ftr/ftrModelView.html',
				controller : 'FtrModalCtrl',
				controllerAs : 'vm',
				size : 'md',
				resolve : {
					items : function() {
						return ftr;
					}
				}
			});

			modalInstance.result.then(function() {
				
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});
		}

		function add(ftr) {
			$scope.ftr.ftr_name = ftr.ftr_name;
//			$scope.ftr.deletes = ftr.deletes;
			$scope.ftr.ftr_id = ftr.ftr_id;
			/*var dept = ftr ? ftr : {};
			// alert(JSON.stringify(usr));
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/ftr/ftrModelAddEdit.html',
				controller : 'FtrModalAddEditCtrl',
				controllerAs : 'vm',
				size : 'lg',
				resolve : {
					ftr : function() {
						return dept;
					}
				}
			});

			modalInstance.result.then(function() {
				loadFtrs();
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});*/
		}

		
		
	}

})();
