(function() {
	'use strict';

	angular
	.module('myApp.breakdownupdate', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.breakdownupdate', {
					url : "/breakdownupdate",
					views : {
						"sub" : {
							templateUrl : "templates/breakdownupdate/breakdownupdate.html",
							controller : "BreakdownupdateController as vm"
						}
					}
				})
			});

})();