(function() {
	'use strict';

	angular.module('myApp.machine_mst').controller('CunsumptionDeviceController', CunsumptionDeviceController);

	CunsumptionDeviceController.$inject = [ '$state','$log',
			'$scope', 'toastr','genericFactory','ApiEndpoint','localStorageService'];

	/* @ngInject */
	function CunsumptionDeviceController($state,$log, $scope, toastr,genericFactory,ApiEndpoint,localStorageService) {
		var user = localStorageService.get(ApiEndpoint.userKey);
		var consumptionUrl = staticUrlMaintenance+"/consumption";
		var vm = angular.extend(this, {
			add:add,
			edit:edit,
			cancel:cancel,
			save:save,
			changeStatus:changeStatus,
			delte:delte
		});

		(function activate() {
			loadDevices();
			$scope.addNewTab=false;
		})();

		// ******************************************************
		
		function loadDevices(){
			
			var msg=""
				 var url =consumptionUrl+"/getAllConsumptionDevices";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.consumptionDevices= response.data;
				console.log("consumptionDevices : "+JSON.stringify(vm.consumptionDevices))
								
			});
			
			
		}
		function add(){
			$scope.addNewTab=true;
		
		}
		function edit(device){
			$scope.addNewTab=true;
			$scope.device=device
		
		}

		function cancel(){
			$scope.addNewTab=false;
		
		}
		function changeStatus(device){
			
		
		}
		
		function delte(device){
			var url=consumptionUrl+"/deletConsumptionDevice";
			var msg=""
				
			genericFactory.add(msg,url,device).then(function(response) {
				
				loadDevices();
				$scope.addNewTab=false;
		
		
	})
		}
		
		
	function save(device) {
		if(!device.deviceType || device.deviceType == ''){
			toastr.error('Please Select  Device Type');
			return;
		}
		if(!device.meterId || device.meterId==''){
			toastr.error('Please Enter Meter Id');
			return;
		}
		
		if(!device.deviceMac || device.deviceMac == ''){
			toastr.error('Please Enter Device Mac Id');
			
			return;
		}
		
		if(!device.location|| device.location== ''){
			toastr.error('Please Enter Location');
			return;
		}
						var url=consumptionUrl+"/addCconsumptionDevice";
						var msg=""
							device.addedBy=	user.firstName+" "+user.lastName
				genericFactory.add(msg,url,device).then(function(response) {
					
					loadDevices();
					$scope.addNewTab=false;
					
					
				})
		
		
		}
		
	
	
		

	}

	

})();
