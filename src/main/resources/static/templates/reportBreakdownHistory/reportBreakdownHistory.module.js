(function() {
	'use strict';

	angular
	.module('myApp.reportBreakdownHistory', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.reportBreakdownHistory', {
					url : "/reportBreakdownHistory",
					views : {
						"sub" : {
							templateUrl : "templates/reportBreakdownHistory/reportBreakdownHistory.html",
							controller : "ReportBreakdownHistoryController as vm"
						}
					}
				})
			
			});

})();