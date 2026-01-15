(function() {
	'use strict';

	angular
	.module('myApp.Engineer_Complaint_Status', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.Engineer_Complaint_Status', {
					url : "/Engineer_Complaint_Status",
					
					views : {
						"sub" : {
							templateUrl : "templates/Engineer_Complaint_Status/Engineer_Complaint_Status.html",
							controller : "EngComplaintController as vm"
				}
			}
		})
	
	});
	
})();




