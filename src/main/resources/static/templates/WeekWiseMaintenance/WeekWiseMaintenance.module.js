(function() {
	'use strict';

	angular
	.module('myApp.WeekWiseMaintenance', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.WeekWiseMaintenance/:week/:machine/:status', {
					url : "/WeekWiseMaintenance/:week/:machine/:status",
					views : {
						"sub" : {
							templateUrl : "templates/WeekWiseMaintenance/WeekWiseMaintenance.html",
							controller : "WeekWiseMaintenanceController as vm"
						}
					}
				})
			});

})();