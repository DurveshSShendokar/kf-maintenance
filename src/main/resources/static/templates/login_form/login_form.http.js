(function() {
	'use strict';

	angular.module('myApp.login_form').factory('loginHttpService',
			loginHttpService);

	loginHttpService.$inject = [ '$http', '$q', '_', 'localStorageService',
			'ApiEndpoint' ];

	function loginHttpService($http, $q, _, localStorageService, ApiEndpoint) {

		var user = localStorageService.get(ApiEndpoint.userKey);
//		var userUrl = ApiEndpoint.url + "login";
		var u = staticUrl + '/login';
		
		var users = {};

		var service = {
			doLogin : doLogin
		};

		return service;

		function doLogin(iObj) {
			
			console.log("URL : "+u)
			var req = {
				 method: 'POST',
				 url: u + '/authenticate',
				 headers: {
				   'Content-Type': "application/json",
				   'Accept': 'application/json',
				   id : iObj.userName,
				   password : iObj.password
				 },
				 data : {}
			}

			return $http(req);/*.success(function(data, status, headers, config) {
				console.log(data);
		    }).error(function(err){
		    	console.log(err);
		    });*/
			
		}

	}
})();
