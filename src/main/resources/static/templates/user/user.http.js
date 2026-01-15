(function () {
	'use strict';

	angular
		.module('myApp.user')
		.factory('userHttpService', userHttpService);

	userHttpService.$inject = ['$http', '$q', '_', 'localStorageService', 'ApiEndpoint'];

	/* @ngInject */
	function userHttpService($http, $q, _, localStorageService, ApiEndpoint) {
		
		var user = localStorageService.get(ApiEndpoint.userKey);
		var userUrl = ApiEndpoint.url+"user";   // User Url
		var u = staticUrl + '/user';
		
		// Variables
		var users = {};

		var service = {
			getUsers : getUsers,
			deleteUser : deleteUser,
			createUser : createUser,
			editUser : editUser,
			getRoleOfUser : getRoleOfUser,
			assignRolesTo1User : assignRolesTo1User,
			deleteRolesOf1User : deleteRolesOf1User,
			getPermissionsOf1User : getPermissionsOf1User,
			checkExistingUserId : checkExistingUserId
		};

		return service;
	
		function createUser(user){
			
			console.log("Saving//////////////////////////////")
			return $http.post(u + '/create', user, {timeout: 5000});
		}
		
		function getUsers(){
//			localhost:8091/user/alluser
			return $http.get(u + '/getAlluser');
		}
		
		function deleteUser(user){
//			localhost:8091/user/{id}
			return $http.delete(u + '/' +user.id);
		}
		
		function editUser(iObj){
//			localhost:8091/user/edit
			return $http.post( u + '/edit', iObj);
		}
		
		function getRoleOfUser(id){
//			localhost:8091/user/userroles/{id}
			return $http.get(u + '/userroles/' + id);
		}
		
		function assignRolesTo1User(iObj){
//			localhost:8091/user/assignroles
			return $http.put(u + '/assignroles/' , iObj);
		}
		
		function deleteRolesOf1User(iObj){
//			localhost:8091/user/assignroles
			return $http.put(u + '/deleteroles/' , iObj);
		}
		
		function getPermissionsOf1User(id){
			//user/permissions/{userid} - get
			return $http.get(u + '/getUserPermission/' + id);
		}
		
		function checkExistingUserId(id){
			//user/usercheck/{userid} - get
			return $http.get(u + '/userCheck?id=' + id);
		}
		
		
	}
})();
