(function() {
	'use strict';
	angular.module('myApp.unusedRequests', ['datatables'])
	.config(function($stateProvider) {
		$stateProvider.state('main.unusedRequests', {
//			abstract : true,
			url : "/unusedRequests",
			views : {
				"sub" : {
					templateUrl: 'templates/unusedRequests/unusedRequests.html',
					controller : "unusedRequestsController as vm"
				}
			}
		})
	});
})();