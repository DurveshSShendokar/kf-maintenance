(function() {
	'use strict';

	angular
	.module('myApp.generalBreakdown', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.generalBreakdown', {
					url : "/generalBreakdown",
					
					views : {
						"sub" : {
							templateUrl : "templates/generalBreakdown/generalBreakdown.html",
							controller : "generalBreakdownController as vm"
				}
			}
		})
	
	});
	
})();




