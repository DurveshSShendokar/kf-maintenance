(function() {
	'use strict';

	angular
	.module('myApp.EnergyMeterRegisterGroup', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.EnergyMeterRegisterGroup', {
					url : "/EnergyMeterRegisterGroup",
					
					views : {
						"sub" : {
							templateUrl : "templates/EnergyMeterRegisterGroup/EnergyMeterRegisterGroup.html",
							controller : "EnergyMeterRegisterGroupController as vm"
				}
			}
		})
	
	});
	
})();




