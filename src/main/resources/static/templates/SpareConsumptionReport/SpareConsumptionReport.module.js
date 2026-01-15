(function() {
	'use strict';

	angular
	.module('myApp.SpareConsumptionReport', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.SpareConsumptionReport', {
					url : "/SpareConsumptionReport",
					
					views : {
						"sub" : {
							templateUrl : "templates/SpareConsumptionReport/SpareConsumptionReport.html",
							controller : "SpareConsumptionReportController as vm"
				}
			}
		})
	
	});
	
})();




