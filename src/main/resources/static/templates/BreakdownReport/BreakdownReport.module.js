(function() {
	'use strict';

	angular
	.module('myApp.BreakdownReport', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.BreakdownReport', {
					url : "/BreakdownReport",
					
					views : {
						"sub" : {
							templateUrl : "templates/BreakdownReport/BreakdownReport.html",
							controller : "BreakdownRepoController as vm"
				}
			}
		})
	
	});
	
})();




