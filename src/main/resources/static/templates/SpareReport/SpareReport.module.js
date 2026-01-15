(function() {
	'use strict';

	angular
	.module('myApp.SpareReport', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.SpareReport', {
					url : "/SpareReport",
					
					views : {
						"sub" : {
							templateUrl : "templates/SpareReport/SpareReport.html",
							controller : "SpareReportController as vm"
				}
			}
		})
	
	});
	
})();




