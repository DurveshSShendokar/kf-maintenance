(function() {
	'use strict';

	angular
	.module('myApp.Notification', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.Notification', {
					url : "/Notification",
					
					views : {
						"sub" : {
							templateUrl : "templates/Notification/Notification.html",
							controller : "NotificationController as vm"
				}
			}
		})
	
	});
	
})();




