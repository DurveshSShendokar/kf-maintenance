(function() {
	'use strict';

	angular
	.module('myApp.EnergyMeterRegister', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.EnergyMeterRegister', {
					url : "/EnergyMeterRegister",
					
					views : {
						"sub" : {
							templateUrl : "templates/EnergyMeterRegister/EnergyMeterRegister.html",
							controller : "EnergyMeterRegisterController as vm"
				}
			}
		})
	
	});
	
})();




