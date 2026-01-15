(function() {
	'use strict';

	angular
	.module('myApp.ApprovedPPM', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.ApprovedPPM', {
					url : "/ApprovedPPM",
					
					views : {
						"sub" : {
							templateUrl : "templates/ApprovedPPM/ApprovedPPM.html",
							controller : "ApprovedPPMController as vm"
				}
			}
		})
	
	});
	
})();




