(function() {
	'use strict';

	angular
	.module('myApp.scraptypereport', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.scraptypereport', {
					url : "/scraptypereport",
					views : {
						"sub" : {
							templateUrl : "templates/report/scraptypereport.html",
							controller : "scraptypereportController as vm"
						}
					}
				})
				.state('main.scrapdeptreport', {
					url : "/scrapdeptreport",
					views : {
						"sub" : {
							templateUrl : "templates/report/scrapdeptreport.html",
							controller : "scrapdeptreportController as vm"
						}
					}
				})
			});

})();