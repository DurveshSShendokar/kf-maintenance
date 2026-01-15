(function() {
	'use strict';

	angular.module('myApp.mtbf').controller('MtbfController', MtbfController);

	MtbfController.$inject = [ '$state', 'mtbfService', '$uibModal', '$log', '$scope', 'toastr', 'machine_mstService', 'categoryService'];

	/* @ngInject */
	function MtbfController($state, mtbfService, $uibModal, $log, $scope, toastr, machine_mstService, categoryService) {
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
			//loadMachines();
			
			
		})();

		// ******************************************************
		function loadMachines() {
			machine_mstService.getMachine_msts().then(function(data) {
				vm.machines = data;
				console.log("Machines  :: "+JSON.stringify(vm.machines))
				
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
		
		function loadCategories(){
			categoryService.getCategorys().then(function(data){
				vm.categories = data;
			});
		}
		
		function loadMachines() {
			machine_mstService.getMachine_msts().then(function(data) {
				vm.machines = data;
				
			});
		}
		
			
		function ok(ftr) {
//			var ele = document.getElementById('ftrName').value = '';
			ftr.deletes = 1;
			/*debugger;*/
			mtbfService.addFtr(ftr).then(function(){
				$scope.ftr = {};
				loadFtrs();
//				$uibModalInstance.close(ftr);
				
			});
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
			
			/*if($scope.reportDuration == 'quarterly'){
				mtbfService.getftrQuarterly(obj).then(function(data) {
					vm.mtbfs = data;
					
					var closeTickets = vm.mtbfs[0];
					var mtbf = vm.mtbfs[1];
					
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
					
					mtbf.sort(function(a, b) {
						  // sort based on the value in the monthNames object
						  return vm.monthsQur[a.month] - vm.monthsQur[b.month];
						});
					
//					console.log(ftr);
					$scope.data2 = [];
					for(var i = 0; i < mtbf.length; i++){
						$scope.data2.push(mtbf[i].mtbf);
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
				
				mtbfService.getMtbfs(obj).then(function(data) {
					vm.mtbfs = data;
				
//					console.log(closeTickets);
					$scope.data1 = [];
					$scope.data2 = [];
					var monthsArrayToDisplay = [];
					const currentDate = new Date();

					var currentYear = currentDate.getFullYear();

					$scope.data1.push(vm.mtbfs.mtbfJan);
					$scope.data1.push(vm.mtbfs.mtbfFeb);
					$scope.data1.push(vm.mtbfs.mtbfMar);
					$scope.data1.push(vm.mtbfs.mtbfApr);
					$scope.data1.push(vm.mtbfs.mtbfMay);
					$scope.data1.push(vm.mtbfs.mtbfJun);
					$scope.data1.push(vm.mtbfs.mtbfJul);
					$scope.data1.push(vm.mtbfs.mtbfAug);
					$scope.data1.push(vm.mtbfs.mtbfSep);
					$scope.data1.push(vm.mtbfs.mtbfOct);
					$scope.data1.push(vm.mtbfs.mtbfNov);
					$scope.data1.push(vm.mtbfs.mtbfDec);
					
					$scope.data2.push(vm.mtbfs.targetJan);
					$scope.data2.push(vm.mtbfs.targetFeb);
					$scope.data2.push(vm.mtbfs.targetMar);
					$scope.data2.push(vm.mtbfs.targetApr);
					$scope.data2.push(vm.mtbfs.targetMay);
					$scope.data2.push(vm.mtbfs.targetJun);
					$scope.data2.push(vm.mtbfs.targetJul);
					$scope.data2.push(vm.mtbfs.targetAug);
					$scope.data2.push(vm.mtbfs.targetSep);
					$scope.data2.push(vm.mtbfs.targetOct);
					$scope.data2.push(vm.mtbfs.targetNov);
					$scope.data2.push(vm.mtbfs.targetDec);
					
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
							name : 'MTBF',
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
				
				mtbfService.getMtbfs(obj).then(function(data) {
					vm.mtbfs = data;
					
					
					console.log("mtbfs  "+JSON.stringify(vm.mtbfs))

//					var mtbf = vm.mtbfs[1];
					$scope.categories = ["Jan",  "Feb",  "Mar", "Apr",  "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
					
					
//					console.log(closeTickets);
					$scope.data1 = [];
					$scope.data2 = [];
					var monthsArrayToDisplay = [];
					const currentDate = new Date();

					var currentYear = currentDate.getFullYear();

					$scope.data1.push(vm.mtbfs.mtbf1);
					$scope.data1.push(vm.mtbfs.mtbf2);
					$scope.data1.push(vm.mtbfs.mtbf3);
					$scope.data1.push(vm.mtbfs.mtbf4);
					
					$scope.data2.push(vm.mtbfs.target1);
					$scope.data2.push(vm.mtbfs.target2);
					$scope.data2.push(vm.mtbfs.target3);
					$scope.data2.push(vm.mtbfs.target4);
					
					monthsArrayToDisplay.push("QUATER 1st "+' '+currentYear);
					monthsArrayToDisplay.push("QUATER 2nd"+' '+currentYear);
					monthsArrayToDisplay.push("QUATER 3rd"+' '+currentYear);
					monthsArrayToDisplay.push("QUATER 4th"+' '+currentYear);
					
					

					
					
					
	
					
					
					$scope.categories = monthsArrayToDisplay;
					
					/*closeTickets.sort(function(a, b) {
						  // sort based on the value in the monthNames object
						  return vm.months[a.month] - vm.months[b.month];
						});
					
//					console.log(ftr);
					$scope.data2 = [];
					for(var i = 0; i < closeTickets.length; i++){
						$scope.data2.push(closeTickets[i].mtbf);
					}*/
					
					$scope.series = [
						{
							name : 'MTBF',
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
					categories : [vm.mtbfs[0][0].month,  vm.mtbfs[0][1].month,  vm.mtbfs[0][2].month,
						 vm.mtbfs[0][3].month,  vm.mtbfs[0][4].month]
					
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
			mtbfService.deleteFtr(ftr).then(function(){
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
