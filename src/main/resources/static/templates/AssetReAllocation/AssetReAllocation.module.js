(function() {
	'use strict';

	angular
	.module('myApp.AssetReAllocation', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.AssetReAllocation', {
					url : "/AssetReAllocation",
					
					views : {
						"sub" : {
							templateUrl : "templates/AssetReAllocation/AssetReAllocation.html",
							controller : "AssetReAllocationController as vm"
				}
			}
		})
	
	});
	
})();




