(function() {
	'use strict';

	angular
	.module('myApp.checklist', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.checklist', {
					url : "/checklist",
					views : {
						"sub" : {
							templateUrl : "templates/checklist/checklist.html",
							controller : "checklistController as vm"
						}
					}
				})
			});

})();