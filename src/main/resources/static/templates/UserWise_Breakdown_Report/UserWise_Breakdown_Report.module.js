(function() {
	'use strict';

	angular
	.module('myApp.UserWise_Breakdown_Report', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.UserWise_Breakdown_Report', {
					url : "/UserWise_Breakdown_Report",
					
					views : {
						"sub" : {
							templateUrl : "templates/UserWise_Breakdown_Report/UserWise_Breakdown_Report.html",
							controller : "UserWise_Breakdown_ReportController as vm"
				}
			}
		})
	
	});
	
})();




