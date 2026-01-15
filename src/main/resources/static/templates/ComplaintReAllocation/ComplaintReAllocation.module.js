(function() {
	'use strict';

	angular
	.module('myApp.ComplaintReAllocation', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.ComplaintReAllocation', {
					url : "/ComplaintReAllocation",
					
					views : {
						"sub" : {
							templateUrl : "templates/ComplaintReAllocation/ComplaintReAllocation.html",
							controller : "ComplaintReAllocationController as vm"
				}
			}
		})
	
	});
	
})();




