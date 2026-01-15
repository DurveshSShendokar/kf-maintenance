(function() {
	'use strict';

	angular
	.module('myApp.GaugeChart', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.GaugeChart/:id', {
					url : "/GaugeChart/:id",
					
					views : {
						"sub" : {
							templateUrl : "templates/GaugeChart/GaugeChart.html",
							controller : "GaugeChartController as vm"
				}
			}
		})
	
	});
	
})();




