(function() {
	'use strict';

	angular
	.module('myApp.ControlPanelLocation', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.ControlPanelLocation', {
					url : "/ControlPanelLocation",
					
					views : {
						"sub" : {
							templateUrl : "templates/ControlPanelLocation/ControlPanelLocation.html",
							controller : "ControlPanelLocationController as vm"
				}
			}
		})
	
	});
	
})();




