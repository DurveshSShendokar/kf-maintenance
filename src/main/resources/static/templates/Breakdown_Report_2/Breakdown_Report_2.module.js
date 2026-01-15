(function() {
	'use strict';

	angular
	.module('myApp.Breakdown_Report_2', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.Breakdown_Report_2', {
					url : "/Breakdown_Report_2",
					
					views : {
						"sub" : {
							templateUrl : "templates/Breakdown_Report_2/Breakdown_Report_2.html",
							controller : "Breakdown_Report_2Controller as vm"
				}
			}
		})
	
	});
	
})();




