(function() {
	'use strict';

	angular
	.module('myApp.scrap', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.scrap', {
					url : "/scrap",
					views : {
						"sub" : {
							templateUrl : "templates/scrap/scrap.html",
							controller : "scrapController as vm"
						}
					}
				})
				.state('main.scrapdepartment', {
					url : "/scrapdepartment",
					views : {
						"sub" : {
							templateUrl : "templates/scrap/scrapDepartment.html",
							controller : "scrapDepartmentController as vm"
						}
					}
				})
				.state('main.scrapyard', {
					url : "/scrapyard",
					views : {
						"sub" : {
							templateUrl : "templates/scrap/scrapyard.html",
							controller : "scrapYardController as vm"
						}
					}
				})
			});

})();