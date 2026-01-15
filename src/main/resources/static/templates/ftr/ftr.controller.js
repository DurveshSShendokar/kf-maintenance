(function() {
	'use strict';

	angular.module('myApp.ftr').controller('FtrController', FtrController)
			.controller('FtrModalCtrl', FtrModalCtrl).controller(
					'FtrModalAddEditCtrl', FtrModalAddEditCtrl);

	FtrController.$inject = [ '$state', 'ftrService', '$uibModal', '$log',
			'$scope', 'toastr', 'machine_mstService', 'categoryService'];
	FtrModalCtrl.$inject = [ '$uibModalInstance', 'items', '$scope' ];
	FtrModalAddEditCtrl.$inject = [ '$uibModalInstance', '$filter', 'ftr', '$scope', 'ftrService' ];

	/* @ngInject */
	function FtrController($state, ftrService, $uibModal, $log, $scope, toastr, machine_mstService, categoryService) {
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
			ftrService.addFtr(ftr).then(function(){
				$scope.ftr = {};
				loadFtrs();
//				$uibModalInstance.close(ftr);
				
			});
		}
		
		function changeCategory(){
			if($scope.category == 'category'){
				return;
			}
			
			$scope.selectMachine = 'selectMachine';
			$scope.type = "";
		}
		
		function changeMachine(){
			if($scope.selectMachine == 'selectMachine'){
				return;
			}
			
			$scope.category = 'category';
		}
		
		function getReportGenerationDetails(){
			$scope.showGraph = false;
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
			
			if($scope.reportDuration == 'quarterly'){
				ftrService.getFtrs(obj).then(function(data) {
					vm.ftrs = data;

					
//					console.log(closeTickets);
					$scope.data1 = [];
					$scope.data2 = [];
					var monthsArrayToDisplay = [];
					const currentDate = new Date();

					var currentYear = currentDate.getFullYear();

					$scope.data1.push(vm.ftrs.ftr1st);
					$scope.data1.push(vm.ftrs.ftr2nd);
					$scope.data1.push(vm.ftrs.ftr3rd);
					$scope.data1.push(vm.ftrs.ftr4th);
				
					$scope.data2.push(vm.ftrs.cloaedBreakDOen1st);
					$scope.data2.push(vm.ftrs.cloaedBreakDOen2nd);
					$scope.data2.push(vm.ftrs.cloaedBreakDOen3rd);
					$scope.data2.push(vm.ftrs.cloaedBreakDOen4Th);
				
					monthsArrayToDisplay.push("Jan-Mar "+' '+currentYear);
					monthsArrayToDisplay.push("Apr-Jun "+' '+currentYear);
					monthsArrayToDisplay.push("Jul-Sept "+' '+currentYear);
					monthsArrayToDisplay.push("Oct-Dec "+' '+currentYear);
				

					$scope.categories = monthsArrayToDisplay;


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
//					vendorPefo();
										
				});
			/*	ftrService.getftrQuarterly(obj).then(function(data) {
					vm.ftrs = data;
					
					var closeTickets = vm.ftrs[0];
					var ftr = vm.ftrs[1];
					
					$scope.categories = ["Jan-Mar",  "Apr-Jun",  "Jul-Sept", "Oct-Dec"];
					
					closeTickets.sort(function(a, b) {
						  // sort based on the value in the monthNames object
//						  return vm.monthsQur[a.month] - vm.monthsQur[b.month];
						return new Date(a.raised_date) - new Date(b.raised_date);
					});
					
//					console.log(closeTickets);
					$scope.data1 = [];
					var monthsArrayToDisplay = [];
					
					for(var i = 0; i < closeTickets.length; i++){
						$scope.data1.push(closeTickets[i].closedTickets);
						monthsArrayToDisplay.push(closeTickets[i].month + ' ' +new Date(closeTickets[i].raised_date).getFullYear());
					}
					
					$scope.categories = monthsArrayToDisplay;
					
					ftr.sort(function(a, b) {
						  // sort based on the value in the monthNames object
//						  return vm.monthsQur[a.month] - vm.monthsQur[b.month];
						return new Date(a.raised_date) - new Date(b.raised_date);
					});
					
//					console.log(ftr);
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
					
				});*/
				
			}else if($scope.reportDuration == 'monthly'){
				
				ftrService.getFtrs(obj).then(function(data) {
					vm.ftrs = data;

					
//					console.log(closeTickets);
					$scope.data1 = [];
					$scope.data2 = [];
					var monthsArrayToDisplay = [];
					const currentDate = new Date();

					var currentYear = currentDate.getFullYear();

					$scope.data1.push(vm.ftrs.ftrJan);
					$scope.data1.push(vm.ftrs.ftrFeb);
					$scope.data1.push(vm.ftrs.ftrMar);
					$scope.data1.push(vm.ftrs.ftrApr);
					$scope.data1.push(vm.ftrs.ftrMay);
					$scope.data1.push(vm.ftrs.ftrJun);
					$scope.data1.push(vm.ftrs.ftrJul);
					$scope.data1.push(vm.ftrs.ftrAug);
					$scope.data1.push(vm.ftrs.ftrSep);
					$scope.data1.push(vm.ftrs.ftrOct);
					$scope.data1.push(vm.ftrs.ftrNov);
					$scope.data1.push(vm.ftrs.ftrDec);
					
					$scope.data2.push(vm.ftrs.closedJan);
					$scope.data2.push(vm.ftrs.closedFeb);
					$scope.data2.push(vm.ftrs.closedMar);
					$scope.data2.push(vm.ftrs.closedApr);
					$scope.data2.push(vm.ftrs.closedMay);
					$scope.data2.push(vm.ftrs.closedJun);
					$scope.data2.push(vm.ftrs.closedJul);
					$scope.data2.push(vm.ftrs.closedAug);
					$scope.data2.push(vm.ftrs.closedSep);
					$scope.data2.push(vm.ftrs.closedOct);
					$scope.data2.push(vm.ftrs.closedNov);
					$scope.data2.push(vm.ftrs.closedDec);
					
					monthsArrayToDisplay.push("JAN "+' '+currentYear);
					monthsArrayToDisplay.push("FEB "+' '+currentYear);
					monthsArrayToDisplay.push("MAR "+' '+currentYear);
					monthsArrayToDisplay.push("APR "+' '+currentYear);
					monthsArrayToDisplay.push("AMY "+' '+currentYear);
					monthsArrayToDisplay.push("JUN "+' '+currentYear);
					monthsArrayToDisplay.push("JUL "+' '+currentYear);
					monthsArrayToDisplay.push("AUG "+' '+currentYear);
					monthsArrayToDisplay.push("SEP "+' '+currentYear);
					monthsArrayToDisplay.push("OCT "+' '+currentYear);
					monthsArrayToDisplay.push("NOV "+' '+currentYear);
					monthsArrayToDisplay.push("DEC "+' '+currentYear);

					$scope.categories = monthsArrayToDisplay;


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
					categories : [vm.ftrs[0][0].month,  vm.ftrs[0][1].month,  vm.ftrs[0][2].month,
						 vm.ftrs[0][3].month,  vm.ftrs[0][4].month]
					
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
			ftrService.deleteFtr(ftr).then(function(){
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

	function FtrModalCtrl($uibModalInstance, items, $scope) {
		var vm = angular.extend(this, {
			items : items,
			ok : ok,
			cancel : cancel
		});

		(function activate() {

		})();

		// ******************************************************

		function ok() {
			$uibModalInstance.close();
		}

		function cancel() {
			$uibModalInstance.dismiss('cancel');
		}
	}

	function FtrModalAddEditCtrl($uibModalInstance, $filter, ftr, $scope, ftrService) {
		var vm = angular.extend(this, {
			ok : ok,
			cancel : cancel,
		});

		(function activate() {
			$scope.ftr = ftr;
			
			/*$scope.setDeletes = function(){
				
				$scope.ftr.deletes = 1;
				
			}*/
			$scope.setFormDate = function() {

				$scope.st = $scope.ftr.ftr_start;
				$scope.gt = $scope.ftr.ftr_end;

				
				$scope.ftr.ftr_start = $filter('date')($scope.st,
						"hh:mm:ss a");
				$scope.ftr.ftr_end = $filter('date')($scope.gt,
						"hh:mm:ss a");
			
			}
			
			
			
		})();

		// ******************************************************
		
		function ok(ftr) {
			
			/*debugger;*/
			ftrService.addFtr(ftr).then(function(){
				$uibModalInstance.close(ftr);
				
			});
		}

		
		
		
		function cancel() {
			$uibModalInstance.dismiss('cancel');
		}
	}
})();
