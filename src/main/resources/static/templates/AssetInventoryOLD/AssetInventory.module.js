(function() {
	'use strict';

	angular
	.module('myApp.AssetInventory', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.AssetInventory', {
					url : "/AssetInventory",
					
					views : {
						"sub" : {
							templateUrl : "templates/AssetInventory/AssetInventory.html",
							controller : "AssetInventoryController as vm"
				}
			}
		})
	
	});
	
})();




