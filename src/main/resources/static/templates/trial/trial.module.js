(function() {
	'use strict';

	angular
	.module('myApp.trial', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.trial', {
					url : "/trial",
					views : {
						"sub" : {
							templateUrl : "templates/trial/trial.html",
							controller : "TrialController as vm"
						}
					}
				})
			});

})();