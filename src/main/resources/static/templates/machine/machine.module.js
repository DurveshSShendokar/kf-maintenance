(function() {
	'use strict';

	angular
	.module('myApp.machine', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.machine', {
					url : "/machine",
					views : {
						"sub" : {
							templateUrl : "templates/machine/machine.html",
							controller : "MachineController as vm"
						}
					}
				})
			});

})();