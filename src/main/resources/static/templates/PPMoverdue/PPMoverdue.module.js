(function() {
	'use strict';

	angular
	.module('myApp.PPMoverdue', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.PPMoverdue', {
					url : "/PPMoverdue",
					
					views : {
						"sub" : {
							templateUrl : "templates/PPMoverdue/PPMoverdue.html",
							controller : "PPMoverdueController as vm"
				}
			}
		})
	
	});
	
})();




