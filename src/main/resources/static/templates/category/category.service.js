(function() {
	'use strict';

	angular
		.module('myApp.category')
		.factory('categoryService', categoryService);

	categoryService.$inject = ['categoryHttpService', '$q', 'toastr'];
	
	/* @ngInject */
	function categoryService(categoryHttpService, $q, toastr) {
		var service = {			
			getCategorys : getCategorys,
			deleteCategory : deleteCategory,
			addCategory : addCategory,
		};
		return service;

		// ***************************************************************

		function getCategorys() {
		var deferred = $q.defer();
			categoryHttpService.getCategorys().then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		
		
		function deleteCategory(category){
			var deferred = $q.defer();
			categoryHttpService.deleteCategory(category).then(function(response){
				toastr.success('Category Deleted....', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		function addCategory(category){
			debugger;
			var deferred = $q.defer();
			categoryHttpService.addCategory(category).then(function(response){
				toastr.success('Category Added....', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}

		
	}

})();