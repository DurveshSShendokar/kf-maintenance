(function() {
	'use strict';

	angular
	.module('myApp.ftr', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.ftr', {
					url : "/ftr",
					views : {
						"sub" : {
							templateUrl : "templates/ftr/ftr.html",
							controller : "FtrController as vm"
						}
					}
				})
				.state('main.mouldftr', {
					url : "/mouldftr",
					views : {
						"sub" : {
							templateUrl : "templates/ftr/mouldftr.html",
							controller : "MouldFtrController as vm"
						}
					}
				})
			});

})();