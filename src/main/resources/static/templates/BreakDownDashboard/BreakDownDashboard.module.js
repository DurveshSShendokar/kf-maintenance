(function() {
	'use strict';

	angular
	.module('myApp.BreakDownDashboard', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.BreakDownDashboard', {
					url : "/BreakDownDashboard",
					views : {
						"sub" : {
							templateUrl : "templates/BreakDownDashboard/BreakDownDashboard.html",
							controller : "BreakDownDashboardController as vm"
						}
					}
				})
			});

})();
