(function() {
	'use strict';

	angular
	.module('myApp.mtbf', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.mtbf', {
					url : "/mtbf",
					views : {
						"sub" : {
							templateUrl : "templates/mtbf/mtbf.html",
							controller : "MtbfController as vm"
						}
					}
				})
				.state('main.mouldmtbf', {
					url : "/mouldmtbf",
					views : {
						"sub" : {
							templateUrl : "templates/mtbf/mouldmtbf.html",
							controller : "MouldMtbfController as vm"
						}
					}
				})
			});

})();