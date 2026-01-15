(function() {
	'use strict';

	angular
	.module('myApp.Allocation', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.Allocation', {
					url : "/Allocation",
					
					views : {
						"sub" : {
							templateUrl : "templates/Allocation/Allocation.html",
							controller : "AllocationController as vm"
				}
			}
		})
	
	});
	
})();




