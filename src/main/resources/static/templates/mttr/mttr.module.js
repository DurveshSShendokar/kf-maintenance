(function() {
	'use strict';

	angular
	.module('myApp.mttr', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.mttr', {
					url : "/mttr",
					views : {
						"sub" : {
							templateUrl : "templates/mttr/mttr.html",
							controller : "MttrController as vm"
						}
					}
				})
				.state('main.mouldmttr', {
					url : "/mouldmttr",
					views : {
						"sub" : {
							templateUrl : "templates/mttr/mouldmttr.html",
							controller : "MouldMttrController as vm"
						}
					}
				})
			});

})();