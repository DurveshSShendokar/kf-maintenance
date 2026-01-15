(function() {
	'use strict';

	angular.module('myApp.machine_mst').controller('MaintDepertmentController', MaintDepertmentController);

	MaintDepertmentController.$inject = [ '$state','$log',
			'$scope', 'toastr','genericFactory','ApiEndpoint','localStorageService'];

	/* @ngInject */
	function MaintDepertmentController($state,$log, $scope, toastr,genericFactory,ApiEndpoint,localStorageService) {
		var departmentUrl = staticUrlMaintenance+"/department";
		var vm = angular.extend(this, {
			save:save,
			edit:edit,
			delte:delte
		});

		(function activate() {
			loadDepartment();
		})();

		// ******************************************************
		function edit(department){
			$scope.department=department
		}
		function loadDepartment(){
			
			var msg=""
				 var url =departmentUrl+"/getAllDepartments";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.departments= response.data;
				console.log("departments : "+JSON.stringify(vm.departments))
								
			});
			
			
		}
	
		function delte(department){
			
			var msg=""
				 var url =departmentUrl+"/deletDepartment";
				genericFactory.add(msg,url,department).then(function(response) {
				vm.consumptionDevices= response.data;
				console.log("consumptionDevices : "+JSON.stringify(vm.consumptionDevices))
				loadDepartment();
								
			});
			
			
		}
		function save(department){
			if(!department.departmentName || department.departmentName == ''){
				toastr.error('Please Enter Department Name ');
				
				return;
			}
			var msg=""
				 var url =departmentUrl+"/addDepartment";
				genericFactory.add(msg,url,department).then(function(response) {
				vm.consumptionDevices= response.data;
				console.log("consumptionDevices : "+JSON.stringify(vm.consumptionDevices))
				loadDepartment();
				$scope.department={}
								
			});
			
			
		}
		

	}

	

})();
