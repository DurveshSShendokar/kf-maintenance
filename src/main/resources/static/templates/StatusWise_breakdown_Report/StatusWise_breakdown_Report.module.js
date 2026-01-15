(function() {
	'use strict';

	angular
	.module('myApp.StatusWise_breakdown_Report', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.StatusWise_breakdown_Report', {
					url : "/StatusWise_breakdown_Report",
					
					views : {
						"sub" : {
							templateUrl : "templates/StatusWise_breakdown_Report/StatusWise_breakdown_Report.html",
							controller : "StatusWise_breakdown_ReportController as vm"
				}
			}
		})
	
	});
	
})();




