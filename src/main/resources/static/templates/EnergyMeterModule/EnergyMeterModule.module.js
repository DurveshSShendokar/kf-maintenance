(function() {
	'use strict';

	angular
	.module('myApp.EnergyMeterModule', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.EnergyMeterModule', {
					url : "/EnergyMeterModule",
					
					views : {
						"sub" : {
							templateUrl : "templates/EnergyMeterModule/EnergyMeterModule.html",
							controller : "EnergyMeterModuleController as vm"
				}
			}
		})
	
	});
	
})();




