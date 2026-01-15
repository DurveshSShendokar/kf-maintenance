(function() {
	'use strict';

	angular
	.module('myApp.AssetAllocation', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.AssetAllocation', {
					url : "/AssetAllocation",
					
					views : {
						"sub" : {
							templateUrl : "templates/AssetAllocation/AssetAllocation.html",
							controller : "AssetAllocationController as vm"
				}
			}
		})
	
	});
	
})();




