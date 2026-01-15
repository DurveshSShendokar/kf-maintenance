(function() {
	'use strict';

	angular
	.module('myApp.EnergyMeterMaster', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.EnergyMeterMaster', {
					url : "/EnergyMeterMaster",
					
					views : {
						"sub" : {
							templateUrl : "templates/EnergyMeterMaster/EnergyMeterMaster.html",
							controller : "EnergyMeterMasterController as vm"
				}
			}
		})
	
	});
	
})();




