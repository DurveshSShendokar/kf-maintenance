(function() {
	'use strict';

	angular
	.module('myApp.Device', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.Device', {
					url : "/Device",
					
					views : {
						"sub" : {
							templateUrl : "templates/Device/Device.html",
							controller : "DeviceController as vm"
				}
			}
		})
	
	});
	
})();




