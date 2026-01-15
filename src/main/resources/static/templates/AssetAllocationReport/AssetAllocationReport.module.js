(function() {
	'use strict';

	angular
	.module('myApp.AssetAllocationReport', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.AssetAllocationReport', {
					url : "/AssetAllocationReport",
					
					views : {
						"sub" : {
							templateUrl : "templates/AssetAllocationReport/AssetAllocationReport.html",
							controller : "AssetAllocationRepoController as vm"
				}
			}
		})
	
	});
	
})();




