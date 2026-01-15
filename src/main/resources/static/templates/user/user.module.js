(function() {
	'use strict';

	angular
	.module('myApp.user', [ 'datatables'])	//,'ngMaterial', 'ngMessages' 
	.config(function($stateProvider) {
				$stateProvider
				.state('main.user', {
					url : "/user",
					views : {
						"sub" : {
							templateUrl : "templates/user/user.html",
							controller : "UserController as vm"
						}
					}
				})
			});

})();