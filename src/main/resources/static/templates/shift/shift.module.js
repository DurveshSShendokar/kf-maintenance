(function() {
	'use strict';

	angular
	.module('myApp.shift', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.shift', {
					url : "/target",
					views : {
						"sub" : {
							templateUrl : "templates/shift/shift.html",
							controller : "ShiftController as vm"
						}
					}
				})
			});

})();