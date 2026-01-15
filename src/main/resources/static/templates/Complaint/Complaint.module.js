(function() {
	'use strict';

	angular
	.module('myApp.Complaint', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.Complaint', {
					url : "/Complaint",
					
					views : {
						"sub" : {
							templateUrl : "templates/Complaint/Complaint.html",
							controller : "ComplaintController as vm"
				}
			}
		})
	
	});
	
})();




