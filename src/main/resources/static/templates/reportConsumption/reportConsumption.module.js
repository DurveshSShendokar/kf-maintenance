(function() {
	'use strict';

	angular
	.module('myApp.reportConsumption', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.reportConsumption', {
					url : "/reportConsumption",
					views : {
						"sub" : {
							templateUrl : "templates/reportConsumption/reportConsumption.html",
							controller : "ReportConsumptionController as vm"
						}
					}
				})
			
			});

})();