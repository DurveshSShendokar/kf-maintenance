(function() {
	'use strict';

	angular
	.module('myApp.UserWise_PPM_Report', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.UserWise_PPM_Report', {
					url : "/UserWise_PPM_Report",
					
					views : {
						"sub" : {
							templateUrl : "templates/UserWise_PPM_Report/UserWise_PPM_Report.html",
							controller : "UserWise_PPM_ReportController as vm"
				}
			}
		})
	
	});
	
})();




