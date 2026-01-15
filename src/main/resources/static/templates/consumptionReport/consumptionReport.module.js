(function() {
	'use strict';

	angular
	.module('myApp.consumptionReport', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.consumptionReport', {
					url : "/consumptionReport",
					views : {
						"sub" : {
							templateUrl : "templates/consumptionReport/consumptionReport.html",
							controller : "ConsumptionReportController as vm"
						}
					}
				})
			});

})();