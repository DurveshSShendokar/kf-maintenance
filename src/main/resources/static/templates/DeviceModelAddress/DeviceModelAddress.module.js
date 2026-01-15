(function() {
	'use strict';

	angular
	.module('myApp.DeviceModelAddress', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.DeviceModelAddress', {
					url : "/DeviceModelAddress",
					
					views : {
						"sub" : {
							templateUrl : "templates/DeviceModelAddress/DeviceModelAddress.html",
							controller : "DeviceModelAddressController as vm"
				}
			}
		})
	
	});
	
})();




