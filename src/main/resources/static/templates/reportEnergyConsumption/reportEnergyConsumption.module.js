(function() {
	'use strict';

	angular
	.module('myApp.reportEnergyConsumption', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.reportEnergyConsumption', {
					url : "/reportEnergyConsumption",
					views : {
						"sub" : {
							templateUrl : "templates/reportEnergyConsumption/reportEnergyConsumption.html",
							controller : "ReportEnergyConsumptionController as vm"
						}
					}
				})
			
			});

})();