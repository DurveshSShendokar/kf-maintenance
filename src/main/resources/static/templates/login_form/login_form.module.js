(function() {
	'use strict';

	angular.module('myApp.login_form', []).config(function($stateProvider) {
		$stateProvider.state('login_form', {
			url : "/login_form",
			views : {
				"main" : {
					templateUrl : "templates/login_form/login_form.html",
					controller : "loginController as vm"
				}
			}
		})
	});

})();