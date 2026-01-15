(function() {
	'use strict';

	angular
	.module('myApp.lab_mst', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.lab_mst', {
					url : "/lab_mst",
					
					views : {
						"sub" : {
							templateUrl : "templates/lab_mst/lab_mst.html",
							controller : "lab_mstController as vm"
				}
			}
		})
	
	});
	
})();




