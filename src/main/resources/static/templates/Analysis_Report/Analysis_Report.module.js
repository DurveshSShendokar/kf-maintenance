(function() {
	'use strict';

	angular
	.module('myApp.Analysis_Report', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.Analysis_Report', {
					url : "/Analysis_Report",
					
					views : {
						"sub" : {
							templateUrl : "templates/Analysis_Report/Analysis_Report.html",
							controller : "Analysis_ReportController as vm"
				}
			}
		})
	
	});
	
})();




