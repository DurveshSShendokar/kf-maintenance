(function() {
	'use strict';

	angular
	.module('myApp.category', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.category', {
					url : "/category",
					views : {
						"sub" : {
							templateUrl : "templates/category/category.html",
							controller : "CategoryController as vm"
						}
					}
				})
			});

})();