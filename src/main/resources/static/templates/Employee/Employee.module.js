(function() {
	'use strict';

	angular
	.module('myApp.Employee', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.Employee', {
					url : "/Employee",
					
					views : {
						"sub" : {
							templateUrl : "templates/Employee/Employee.html",
							controller : "EmployeeController as vm"
				}
			}
		})
	
	});
	
})();




