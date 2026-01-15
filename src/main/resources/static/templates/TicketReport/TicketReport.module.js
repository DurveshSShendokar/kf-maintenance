(function() {
	'use strict';

	angular
	.module('myApp.TicketReport', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.TicketReport', {
					url : "/TicketReport",
					
					views : {
						"sub" : {
							templateUrl : "templates/TicketReport/TicketReport.html",
							controller : "TicketReportController as vm"
				}
			}
		})
	
	});
	
})();




