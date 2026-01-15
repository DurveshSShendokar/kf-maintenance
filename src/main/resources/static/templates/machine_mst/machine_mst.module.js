(function() {
	'use strict';

	angular
	.module('myApp.machine_mst', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.machine_mst', {
					url : "/machine_mst",
					views : {
						"sub" : {
							templateUrl : "templates/machine_mst/machine_mst.html",
							controller : "Machine_mstController as vm"
						}
					}
				})
			});

})();