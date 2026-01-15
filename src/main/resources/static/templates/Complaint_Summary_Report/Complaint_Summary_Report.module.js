(function() {
	'use strict';

	angular
	.module('myApp.Complaint_Summary_Report', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.Complaint_Summary_Report', {
					url : "/Complaint_Summary_Report",
					
					views : {
						"sub" : {
							templateUrl : "templates/Complaint_Summary_Report/Complaint_Summary_Report.html",
							controller : "SummaryController as vm"
				}
			}
		})
	
	});
	
})();




