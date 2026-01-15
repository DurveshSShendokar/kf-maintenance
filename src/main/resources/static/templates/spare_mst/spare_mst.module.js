(function() {
	'use strict';

	angular
	.module('myApp.spare_mst', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.spare_mst', {
					url : "/spare_mst",
					
					views : {
						"sub" : {
							templateUrl : "templates/spare_mst/spare_mst.html",
							controller : "spare_mstController as vm"
				}
			}
		})
	
	});
	
})();




