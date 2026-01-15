(function() {
	'use strict';

	angular
	.module('myApp.maintreport', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.maintreport', {
					url : "/maintreport",
					views : {
						"sub" : {
							templateUrl : "templates/maintreport/maintreport.html",
							controller : "MaintreportController as vm"
						}
					}
				})
			});

})();