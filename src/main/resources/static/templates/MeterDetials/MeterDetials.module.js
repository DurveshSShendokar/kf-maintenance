(function() {
	'use strict';

	angular
	.module('myApp.MeterDetials', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.MeterDetials/:id', {
					url : "/MeterDetials/:id",
					
					views : {
						"sub" : {
							templateUrl : "templates/MeterDetials/MeterDetials.html",
							controller : "MeterDetialsController as vm"
				}
			}
		})
	
	});
	
})();




