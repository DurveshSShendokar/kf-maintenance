(function() {
	'use strict';

	angular
	.module('myApp.location_mst', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.location_mst', {
					url : "/location_mst",
					
					views : {
						"sub" : {
							templateUrl : "templates/location_mst/location_mst.html",
							controller : "location_mstController as vm"
				}
			}
		})
	
	});
	
})();




