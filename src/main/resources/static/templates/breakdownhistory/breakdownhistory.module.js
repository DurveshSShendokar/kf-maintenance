(function() {
	'use strict';

	angular
	.module('myApp.breakdownhistory', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.breakdownhistory', {
					url : "/breakdownhistory",
					views : {
						"sub" : {
							templateUrl : "templates/breakdownhistory/breakdownhistory.html",
							controller : "BreakdownHistoryController as vm"
						}
					}
				})
			});

})();