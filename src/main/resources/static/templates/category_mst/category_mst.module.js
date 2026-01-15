(function() {
	'use strict';

	angular
	.module('myApp.category_mst', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.category_mst', {
					url : "/category_mst",
					
					views : {
						"sub" : {
							templateUrl : "templates/category_mst/category_mst.html",
							controller : "category_mstController as vm"
				}
			}
		})
	
	});
	
})();




