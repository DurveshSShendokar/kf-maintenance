(function() {
	'use strict';

	angular
	.module('myApp.breakdown', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.breakdown', {
					url : "/breakdown",
					params : {machine: null},
					views : {
						"sub" : {
							templateUrl : "templates/breakdown/breakdown.html",
							controller : "BreakdownController as vm"
						}
					}
				})
			});

})();