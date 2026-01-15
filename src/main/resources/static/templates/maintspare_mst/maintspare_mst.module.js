(function() {
	'use strict';

	angular
	.module('myApp.maintspare_mst', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.maintspare_mst', {
					url : "/maintspare_mst",
					
					views : {
						"sub" : {
							templateUrl : "templates/maintspare_mst/maintspare_mst.html",
							controller : "maintspare_mstController as vm"
				}
			}
		})
	
	});
	
})();




