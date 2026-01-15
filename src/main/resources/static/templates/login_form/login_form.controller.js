(function() {
	'use strict';

	angular.module('myApp.login_form').controller('loginController',
			loginController);

	loginController.$inject = ['userService', '$rootScope', '$scope', '$stateParams',
			'$state', 'localStorageService', 'toastr', 'ApiEndpoint',
			'loginService' ];

	/* @ngInject */
	function loginController(userService, $rootScope, $stateParams, $scope, $state, 
			localStorageService, toastr, ApiEndpoint, loginService) {

		var vm = angular.extend(this, {
			doLogin : doLogin,
		});

		(function activate() {
			if(sessionStorage.getItem('renataLoggedInUser') != null && sessionStorage.getItem('permissions') != null){
				$state.go('main.home');
			}
		})();

		// ******************************************************
		
		function doLogin(login) {
			
//			$state.go('main.user');
//			$state.go('main.home');
			console.log(JSON.stringify(login));
			loginService.doLogin(login).then(function(data) {
				console.log(JSON.stringify(data.data.department.departmentName));
					
				if (data.code=200) {
					/*userService.getPermissionsOf1User(data.id).then(function(data){
						sessionStorage.setItem('permissions', JSON.stringify(data));
						$state.go('main.home');
						toastr.success('Login....', 'Succesfully !!');
					});*/
				
					toastr.success(data.message);
					if(data.data.department.departmentName=="IT"){
						$state.go('main.dashboard');	
					}else{
						$state.go('main.home');
					}
					
					localStorageService.set(ApiEndpoint.userKey, data.data);
					localStorageService.set(ApiEndpoint.userKey, data.data);
					sessionStorage.setItem(ApiEndpoint.userKey, JSON.stringify(data.data));
				} else {
					toastr.error(data.message);
				}
			});
		}
	}
})();
