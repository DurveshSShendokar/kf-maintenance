(function() {
	'use strict';

	angular
	.module('myApp.department_mst', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.department_mst', {
					url : "/department_mst",
					
					views : {
						"sub" : {
							templateUrl : "templates/department_mst/department_mst.html",
							controller : "department_mstController as vm"
				}
			}
		})
	
	});
	
})();




