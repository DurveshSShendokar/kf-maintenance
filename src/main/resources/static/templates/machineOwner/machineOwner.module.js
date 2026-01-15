(function() {
	'use strict';

	angular
	.module('myApp.machineOwner', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.machineOwner', {
					url : "/machineOwner",
					views : {
						"sub" : {
							templateUrl : "templates/machineOwner/machineOwner.html",
							controller : "MachineOwnerController as vm"
						}
					}
				})
			});

})();
