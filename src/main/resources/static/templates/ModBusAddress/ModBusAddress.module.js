(function() {
	'use strict';

	angular
	.module('myApp.ModBusAddress', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.ModBusAddress', {
					url : "/ModBusAddress",
					
					views : {
						"sub" : {
							templateUrl : "templates/ModBusAddress/ModBusAddress.html",
							controller : "ModBusAddressController as vm"
				}
			}
		})
	
	});
	
})();




