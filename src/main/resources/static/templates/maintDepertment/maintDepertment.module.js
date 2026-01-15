(function() {
	'use strict';

	angular
	.module('myApp.maintDepertment', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.maintDepertment', {
					url : "/maintDepertment",
					views : {
						"sub" : {
							templateUrl : "templates/maintDepertment/maintDepertment.html",
							controller : "MaintDepertmentController as vm"
						}
					}
				})
			});

})();