(function() {
	'use strict';

	angular
	.module('myApp.DashboardDesign', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.DashboardDesign/:id', {
					url : "/DashboardDesign/:id",
					
					views : {
						"sub" : {
							templateUrl : "templates/DashboardDesign/DashboardDesign.html",
							controller : "DashboardDesignController as vm"
				}
			}
		})
	
	});
	
})();




