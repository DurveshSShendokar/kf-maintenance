(function() {
	'use strict';

	angular
		.module('myApp.user')
		.factory('userService', userService);

	userService.$inject = ['userHttpService', '$q', 'toastr'];
	
	/* @ngInject */
	function userService(userHttpService, $q, toastr) {
		var service = {			
			getUsers : getUsers,
			deleteUser : deleteUser,
			createUser : createUser,
			editUser	:	editUser,
			getRoleOfUser : getRoleOfUser,
			assignRolesTo1User : assignRolesTo1User,
			deleteRolesOf1User : deleteRolesOf1User,
			getPermissionsOf1User : getPermissionsOf1User,
			checkExistingUserId : checkExistingUserId
		};
		return service;

		// ***************************************************************
		
		function createUser(user){
			console.log(user);
			var deferred = $q.defer();
			userHttpService.createUser(user).then(function(response){
//				console.log("Successful");
			
				deferred.resolve(response.data);
			}, function(err){
				console.log(err);
				toastr.error(err.data.message);
			});
			return deferred.promise;
		}

		function getUsers() {
			var deferred = $q.defer();
			userHttpService.getUsers().then(function(response){
				deferred.resolve(response.data);
			}, function(err){
//				alert(JSON.stringify(err.statusText));
				toastr.error(err.data.message);
			});
			return deferred.promise;
		}
		
		function editUser(iObj){
			var deferred = $q.defer();
			userHttpService.editUser(iObj).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
//				alert(JSON.stringify(err.statusText));
				toastr.error(err.data.message);
			});
			return deferred.promise;
		}
		
		function deleteUser(user){
			var deferred = $q.defer();
			userHttpService.deleteUser(user).then(function(response){
				toastr.success('User Deleted....', 'Successfully !!');
				 
				deferred.resolve(response.data);
			}, function(err){
//				toastr.error(err.status+' : '+err.statusText, 'Error !!');
				toastr.error(err.data.message);
			});
			return deferred.promise;
		}
		
		/**
		 * purpuse : get role information of particular user
		 */
		function getRoleOfUser(id){
			var deferred = $q.defer();
			userHttpService.getRoleOfUser(id).then(function(response){
//				toastr.success('User Role edited....', 'Successfully !!');
				 
				deferred.resolve(response.data);
			}, function(err){
//				toastr.error(err.status+' : '+err.statusText, 'Error !!');
				toastr.error(err.data.message);
			});
			return deferred.promise;
		}
		
		/**
		 * purpuse : assign multiple roles to single user
		 */
		function assignRolesTo1User(iObj){
			var deferred = $q.defer();
			userHttpService.assignRolesTo1User(iObj).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
//				toastr.error(err.status+' : '+err.statusText, 'Error !!');
				toastr.error(err.data.message);
			});
			return deferred.promise;
		}
		
		/**
		 * purpuse : delete multiple roles of single user
		 */
		function deleteRolesOf1User(iObj){
			var deferred = $q.defer();
			userHttpService.deleteRolesOf1User(iObj).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
//				toastr.error(err.status+' : '+err.statusText, 'Error !!');
				toastr.error(err.data.message);
			});
			return deferred.promise;
		}
		
		function getPermissionsOf1User(id){
			var deferred = $q.defer();
			userHttpService.getPermissionsOf1User(id).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
//				toastr.error(err.status+' : '+err.statusText, 'Error !!');
				toastr.error(err.data.message);
			});
			return deferred.promise;
		}
		
		function checkExistingUserId(id){
			var deferred = $q.defer();
			userHttpService.checkExistingUserId(id).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
//				toastr.error(err.status+' : '+err.statusText, 'Error !!');
//				toastr.error(err.data.message);
				deferred.resolve('error');
			});
			return deferred.promise;
		}
		
		
	}

})();