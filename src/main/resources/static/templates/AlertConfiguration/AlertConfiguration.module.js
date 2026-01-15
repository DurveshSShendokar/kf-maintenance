(function() {
	'use strict';

	angular
	.module('myApp.AlertConfiguration', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.AlertConfiguration', {
					url : "/AlertConfiguration",
					
					views : {
						"sub" : {
							templateUrl : "templates/AlertConfiguration/AlertConfiguration.html",
							controller : "AlertConfigurationController as vm"
				}
			}
		})
	
	});
	
})();




