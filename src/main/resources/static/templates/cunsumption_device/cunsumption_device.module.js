(function() {
	'use strict';

	angular
	.module('myApp.cunsumption_device', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.cunsumption_device', {
					url : "/cunsumption_device",
					views : {
						"sub" : {
							templateUrl : "templates/cunsumption_device/cunsumption_device.html",
							controller : "CunsumptionDeviceController as vm"
						}
					}
				})
			});

})();