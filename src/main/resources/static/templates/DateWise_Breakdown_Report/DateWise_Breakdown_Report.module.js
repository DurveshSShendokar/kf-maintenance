(function() {
	'use strict';

	angular
	.module('myApp.DateWise_Breakdown_Report', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.DateWise_Breakdown_Report', {
					url : "/DateWise_Breakdown_Report",
					
					views : {
						"sub" : {
							templateUrl : "templates/DateWise_Breakdown_Report/DateWise_Breakdown_Report.html",
							controller : "DateWise_Breakdown_ReportController as vm"
				}
			}
		})
	
	});
	
})();




