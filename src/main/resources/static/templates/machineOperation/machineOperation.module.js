(function() {
	'use strict';

	angular
	.module('myApp.machineOperation', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.machineOperation', {
					url : "/machineOperation",
					views : {
						"sub" : {
							templateUrl : "templates/machineOperation/machineOperation.html",
							controller : "MachineOperationController as vm"
						}
					}
				})
			});

})();