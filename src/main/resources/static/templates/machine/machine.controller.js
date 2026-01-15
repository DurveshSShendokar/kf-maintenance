(function() {
	'use strict';

	angular.module('myApp.machine').controller('MachineController', MachineController)
			.controller('MachineModalCtrl', MachineModalCtrl).controller(
					'MachineModalAddEditCtrl', MachineModalAddEditCtrl);

	MachineController.$inject = [ '$state', 'machineService', '$uibModal', '$log',
			'$scope', 'toastr', 'machine_mstService','localStorageService','ApiEndpoint', 'genericFactory' ];
	MachineModalCtrl.$inject = [ '$uibModalInstance', 'items', '$scope' ];
	MachineModalAddEditCtrl.$inject = [ '$uibModalInstance', 'machine', '$scope', 'machineService', '$filter' ];

	/* @ngInject */
	function MachineController($state, machineService, $uibModal, $log, $scope, toastr, machine_mstService,localStorageService,ApiEndpoint, genericFactory ) {
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		const baseUrl = ApiEndpoint.url;
		var vm = angular.extend(this, {
			machines : [],
			view : view,
			add : add,
			delet : delet,
			saveMachinesData : saveMachinesData,
			changeMode : changeMode,
			isDate : isDate,
			changeMachine : changeMachine,
			changeMachineNames:changeMachineNames
		});

		(function activate() {
			$scope.selectedMachine = {};
			loadLabs();
			$scope.showSelectFrequency = true;
			$scope.showSelectMachine = true;
			$scope.selectedMode = "selectFrequency";
			$scope.selectedMachine = "selectMachine";
			$scope.monthArr = ['jan','feb', 'mar', 'apr', 'may', 'jun', 'jul', 'aug', 'sep', 'aug', 'nov', 'dec'];
			loadMachines();
			loadMachineNames();
		})();

		// ******************************************************

		function loadMachines() {
			machine_mstService.getMachine_msts().then(function(data) {
				vm.machines = data;
//				console.log(JSON.stringify(vm.machines));
			});
		}
		function changeMachineNames(machineName) {
			console.log("machineName  "+JSON.stringify(machineName));

			machine_mstService.getMachineByName(machineName).then(function(data) {
				vm.machines = data;
				console.log("machinesId  "+JSON.stringify(vm.machines));
			});
		}
		function loadMachineNames() {
			machine_mstService.getMachineNames().then(function(data) {
				vm.machineNames = data;
				console.log(JSON.stringify(vm.machineNames));
			});
		}

		function loadLabs() {
		    var url = baseUrl + "/lab/all";
			console.log("URL ",url)
			var msg="";
		    genericFactory.getAll(msg,url)
		        .then(function(response) {
		            vm.labs = response.data;
					console.log(JSON.stringify(vm.labs));
		            
		        })
		        .catch(function(error) {
		            console.error("Error fetching labs:", error);
		        });
		}
		
		function delet(machine){
			machineService.deleteMachine(machine).then(function(){
				loadMachines();
			});
		}

		function view(machine) {
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/machine/machineModelView.html',
				controller : 'MachineModalCtrl',
				controllerAs : 'vm',
				size : 'md',
				resolve : {
					items : function() {
						return machine;
					}
				}
			});

			modalInstance.result.then(function() {
				
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});
		}
		
		function changeMachine(selectedMachine){
			console.log($scope.selectedMode);
			var machine = JSON.parse(selectedMachine);
		}

		function add(machine) {
			var usr = machine ? machine : {};
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/machine/machineModelAddEdit.html',
				controller : 'MachineModalAddEditCtrl',
				controllerAs : 'vm',
				size : 'lg',
				resolve : {
					machine : function() {
						return usr;
					}
				}
			});

			modalInstance.result.then(function() {
				loadMachines();
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});
		}
		/**
		 * format Date
		 */
		function formatDate(date) {
		    var d = new Date(date),
		        month = '' + (d.getMonth() + 1),
		        day = '' + d.getDate(),
		        year = d.getFullYear();

		    if (month.length < 2) month = '0' + month;
		    if (day.length < 2) day = '0' + day;

		    return [year, month, day].join('-');
		}
		
		/**
		 * find first and last days for week
		 */
		function getWeeksInMonth(month, year){
			   var weeks=[],
			       firstDate=new Date(year, month, 1),
			       lastDate=new Date(year, month+1, 0), 
			       numDays= lastDate.getDate();
			   
			   var start=1;
			   var end=7-firstDate.getDay();
			   while(start<=numDays){
				   var m = (month + 1) < 10 ? '0'+ (month + 1) : (month + 1);
				   var s = start < 10 ? '0' + start : start;
				   var e = end < 10 ? '0' + end : end;
			       weeks.push({min:year + '-' + m + '-' + s,max: year + '-' + m + '-' + e});
			       start = end + 1;
			       end = end + 7;
			       if(end>numDays)
			           end=numDays;    
			   }        
			    return weeks;
			}   
		
		function changeMode(selectedMode){
			$scope.selectedMode = selectedMode;
			var year = new Date().getFullYear();
			
			setTimeout(function(){
				window.scroll({
					  top: document.body.scrollHeight, 
					  left: 0, 
					  behavior: 'smooth' 
				});
			},0);
			
			if($scope.selectedMode == 'yearly'){
				$scope.yearDate = null;
			}
			if($scope.selectedMode == 'monthly'){
				$scope.monthlyArray = [{month: 'Jan'},{month: 'Feb'},{month: 'Mar'},{month: 'Apr'},{month: 'May'},{month: 'Jun'},{month: 'Jul'},{month: 'Aug'},{month: 'Sep'},{month: 'Oct'},{month: 'Nov'},{month: 'Dec'}];
				for(var i = 0; i < $scope.monthlyArray.length; i++){
					
					/*var FirstDay = new Date(year, month, 1);
			        var LastDay = new Date(year, month + 1, 0);*/
					
					var FirstDay = formatDate(new Date(year, i, 1));
			        var LastDay = formatDate(new Date(year, i + 1, 0));
			        
					$scope.monthlyArray[i].max = LastDay;
					$scope.monthlyArray[i].min = FirstDay;
//					$scope.monthlyArray[i].selectedDate = ;
				}
			}
			
			if($scope.selectedMode == 'weekly'){
				$scope.weeklyArray = [{month: 'Jan'},{month: 'Feb'},{month: 'Mar'},{month: 'Apr'},{month: 'May'},{month: 'Jun'},{month: 'Jul'},{month: 'Aug'},{month: 'Sep'},{month: 'Oct'},{month: 'Nov'},{month: 'Dec'}];
				for(var i = 0; i < $scope.weeklyArray.length; i++){
					
					/*var FirstDay = new Date(year, month, 1);
			        var LastDay = new Date(year, month + 1, 0);*/
					
					var weekArr = getWeeksInMonth(i, year);
					$scope.weeklyArray[i].weeks = weekArr;
//					$scope.weeklyArray[i].max = LastDay;
//					$scope.weeklyArray[i].min = FirstDay;
//					$scope.monthlyArray[i].selectedDate = ;
				}
			}
			
			if($scope.selectedMode == 'quarterly' || $scope.selectedMode == 'halfyearly'){
				$scope.quarterlyArray = [{month: 'Jan'},{month: 'Feb'},{month: 'Mar'},{month: 'Apr'},{month: 'May'},{month: 'Jun'},{month: 'Jul'},{month: 'Aug'},{month: 'Sep'},{month: 'Oct'},{month: 'Nov'},{month: 'Dec'}];
				for(var i = 0; i < $scope.quarterlyArray.length; i++){
					
					/*var FirstDay = new Date(year, month, 1);
			        var LastDay = new Date(year, month + 1, 0);*/
					
					var FirstDay = formatDate(new Date(year, i, 1));
			        var LastDay = formatDate(new Date(year, i + 1, 0));
			        
					$scope.quarterlyArray[i].max = LastDay;
					$scope.quarterlyArray[i].min = FirstDay;
//					$scope.monthlyArray[i].selectedDate = ;
				}
			}
		}
		
		
		

		function saveMachinesData(){
			
			var dateArr = [];
			var model;
			if($scope.selectedMachine == 'selectMachine'){
				toastr.error('Please select machine');
				return;
			}else
				var selectedMachine = JSON.parse($scope.selectedMachine); 
			
			if($scope.selectedMode == 'monthly'){
				dateArr = monthly();
			}else if($scope.selectedMode == 'weekly'){
				dateArr = weekly();
			}else if($scope.selectedMode == 'quarterly'){
				dateArr = quarterly();
			}else if($scope.selectedMode == 'halfyearly'){
				dateArr = quarterly();		//halfYearly();
			}else if($scope.selectedMode == 'yearly'){
				dateArr = yearly();
			}
			
			if(dateArr.length == 0){
				toastr.error('Please enter proper date');
				return;
			}
			var iObj =  {
			        "arr": dateArr,
			        "mode": model,
			        "frequency": $scope.selectedMode,
			        "overall_status": null,
			      
			       //  "raisedBy": userDetail,
			        "machine": {
			            "machine_id": selectedMachine.machine_id,
			            "machine_name": selectedMachine.machine_name
			        },
					"lab":JSON.parse($scope.selectedLab)
				/*	,
					"lab": {
					           "lab_id": $scope.selectedLab.lab_id
					       }*/
			 }
			console.log("OBJ "+JSON.stringify(iObj))
			machineService.addMachine(iObj).then(function(data) {
				setTimeout(function() {
				         location.reload();
				     }, 1000); // Delay to ensure toastr message is visible
				 
				
				$scope.selectedMachine == 'selectMachine';
				$scope.showSelectMachine = true;
				$scope.showSelectMachineName=true;
				//$scope.machine_mst.type = '';
				$scope.quarterlyArray = [{month: 'Jan'},{month: 'Feb'},{month: 'Mar'},{month: 'Apr'},{month: 'May'},{month: 'Jun'},{month: 'Jul'},{month: 'Aug'},{month: 'Sep'},{month: 'Oct'},{month: 'Nov'},{month: 'Dec'}];
				$scope.monthlyArray = [{month: 'Jan'},{month: 'Feb'},{month: 'Mar'},{month: 'Apr'},{month: 'May'},{month: 'Jun'},{month: 'Jul'},{month: 'Aug'},{month: 'Sep'},{month: 'Oct'},{month: 'Nov'},{month: 'Dec'}];
				$scope.weeklyArray = [{month: 'Jan'},{month: 'Feb'},{month: 'Mar'},{month: 'Apr'},{month: 'May'},{month: 'Jun'},{month: 'Jul'},{month: 'Aug'},{month: 'Sep'},{month: 'Oct'},{month: 'Nov'},{month: 'Dec'}];
				$scope.yearDate = null;
				$scope.selectedMachine = "selectMachine";
				$scope.selectedMode = 'selectFrequency';
				
				//loadMachinesData();
				//$scope.selectedLab = null;
			});
		}
		
		var monthly = function(){
			var arr = [];
			for(var i = 0; i < $scope.monthlyArray.length; i++){
				if(isDate($scope.monthlyArray[i].selectedDate))
					arr.push($scope.monthlyArray[i].selectedDate);
			}
			
			return arr;
		}
		
		var weekly = function(){
			var arr = [];
			for(var i = 0; i < $scope.weeklyArray.length; i++){
				for(var j = 0; j < $scope.weeklyArray[i].weeks.length; j++){
					if(isDate($scope.weeklyArray[i].weeks[j].selectedDate))
						arr.push($scope.weeklyArray[i].weeks[j].selectedDate);
				}
			}
			
			return arr;
		}
		
		var quarterly = function(){
			var arr = [];
			for(var i = 0; i < $scope.quarterlyArray.length; i++){
				if(isDate($scope.quarterlyArray[i].selectedDate))
					arr.push($scope.quarterlyArray[i].selectedDate);
			}
			
			return arr;
		}
		
		var halfYearly = function(){
			var arr = [];
			for(var i = 0; i < $scope.monthArr.length; i++){
				if(isDate(document.getElementById($scope.monthArr[i]).value))
					arr.push(document.getElementById($scope.monthArr[i]).value);
			}
			
			return arr;
		}
		
		var yearly = function(){
			var arr = [];
			if(isDate(document.getElementById('jan').value))
				arr.push(document.getElementById('jan').value);
			
			return arr;
		}
		
		function isDate(value) {
		    switch (typeof value) {
		        case 'number':
		            return true;
		        case 'string':
		            return !isNaN(Date.parse(value));
		        case 'object':
		            if (value instanceof Date) {
		                return !isNaN(value.getTime());
		            }
		        default:
		            return false;
		    }
		}

	}

	function MachineModalCtrl($uibModalInstance, items, $scope) {
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

	function MachineModalAddEditCtrl($uibModalInstance, machine, $scope, machineService, $filter) {
		var vm = angular.extend(this, {
			ok : ok,
			cancel : cancel,
			roles : [],
			machineTypes : [],
			machines : [],
		});

		(function activate() {
			$scope.machine = machine;
			loadRoles();
			loadMachineTypes();
			loadMachines();
			/*
			$scope.setTime = function(){
				
				$scope.CurrentDate = new Date();
				
				$scope.cd = $filter('date')($scope.CurrentDate, "hh:mm:ss a");
				
				$scope.machine.added_time =  $scope.cd; 
			}
			*/
			
		})();

		// ******************************************************
		

		function loadMachines() {
			console.log('inside load machines');
			machineService.getMachines().then(function(data) {
				vm.machines = data;
				console.log('machines');
				console.log(JSON.stringify(vm.machines));
			});
		}

		
		function loadRoles(){
			machineService.getRoles().then(function(data){
				vm.roles = data;
			});			
		}
		
		function loadMachineTypes(){
			machineService.getMachineTypes().then(function(data){
				vm.machineTypes = data;
			});			
		}
		
		function ok(machine) {
			
			debugger;
			machineService.addMachine(machine).then(function(){
				$uibModalInstance.close(machine);
				sendMail(machine);
				sendSms(machine);
			});
		}
		
		function sendMail(machine){
			machineService.sendMail(machine).then(function(){
				
			});
		}
		
		function sendSms(machine){
			machineService.sendSms(machine).then(function(){
				
			});
		}

		function cancel() {
			$uibModalInstance.dismiss('cancel');
		}
		
	}
})();
