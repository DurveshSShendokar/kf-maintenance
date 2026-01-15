(function() {
	'use strict';

	angular
	.module('myApp.WeekWiseBrekdown', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.WeekWiseBrekdown/:week/:machine/:status', {
					url : "/WeekWiseBrekdown/:week/:machine/:status",
					views : {
						"sub" : {
							templateUrl : "templates/WeekWiseBrekdown/WeekWiseBrekdown.html",
							controller : "WeekWiseBrekdownController as vm"
						}
					}
				})
			});

})();