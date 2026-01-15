(function() {
	'use strict';

	angular
	.module('myApp.UnitLocation', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.UnitLocation', {
					url : "/UnitLocation",
					
					views : {
						"sub" : {
							templateUrl : "templates/UnitLocation/UnitLocation.html",
							controller : "UnitLocationController as vm"
				}
			}
		})
	
	});
	
})();




