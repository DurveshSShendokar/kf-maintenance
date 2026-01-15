(function() {
	'use strict';

	angular
	.module('myApp.PPMDashboard', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.PPMDashboard', {
					url : "/PPMDashboard",
					views : {
						"sub" : {
							templateUrl : "templates/PPMDashboard/PPMDashboard.html",
							controller : "PPMDashboardController as vm"
						}
					}
				})
			});

})();
