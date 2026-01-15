(function() {
	'use strict';

	angular
	.module('myApp.SpareUtilReport', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.SpareUtilReport', {
					url : "/SpareUtilReport",
					
					views : {
						"sub" : {
							templateUrl : "templates/SpareUtilReport/SpareUtilReport.html",
							controller : "SpareUtilReportController as vm"
				}
			}
		})
	
	});
	
})();




