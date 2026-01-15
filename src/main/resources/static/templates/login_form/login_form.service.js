(function() {
	'use strict';

	angular.module('myApp.login_form').factory('loginService', loginService);

	loginService.$inject = [ 'loginHttpService', '$q', 'toastr' ];

	function loginService(loginHttpService, $q, toastr) {
		var service = {
			doLogin : doLogin
		};
		return service;

		// ***************************************************************

		function doLogin(login) {
			var deferred = $q.defer();
			loginHttpService.doLogin(login).then(function(response) {
				deferred.resolve(response.data);
			}, function(err) {
				toastr.error('Authentication failed');
//				toastr.error('User Id and Password Doesnt match...!!');
//				toastr.error(err.status + ' : ' + err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
	}

})();