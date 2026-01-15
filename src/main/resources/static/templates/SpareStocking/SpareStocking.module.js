(function() {
	'use strict';

	angular
	.module('myApp.SpareStocking', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.SpareStocking', {
					url : "/SpareStocking",
					
					views : {
						"sub" : {
							templateUrl : "templates/SpareStocking/SpareStocking.html",
							controller : "SpareStockingController as vm"
				}
			}
		})
	
	});
	
})();




