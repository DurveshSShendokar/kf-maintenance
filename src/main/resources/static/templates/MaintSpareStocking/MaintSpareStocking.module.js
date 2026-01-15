(function() {
	'use strict';

	angular
	.module('myApp.MaintSpareStocking', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.MaintSpareStocking', {
					url : "/MaintSpareStocking",
					
					views : {
						"sub" : {
							templateUrl : "templates/MaintSpareStocking/MaintSpareStocking.html",
							controller : "MaintSpareStockingController as vm"
				}
			}
		})
	
	});
	
})();




