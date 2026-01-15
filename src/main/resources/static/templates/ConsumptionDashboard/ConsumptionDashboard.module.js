(function() {
	'use strict';

	angular
	.module('myApp.ConsumptionDashboard', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.ConsumptionDashboard', {
					url : "/ConsumptionDashboard",
					views : {
						"sub" : {
							templateUrl : "templates/ConsumptionDashboard/ConsumptionDashboard.html",
							controller : "ConsumptionDashboardController as vm"
						}
					}
				})
			});

})();
