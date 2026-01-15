(function() {
	'use strict';

	angular.module('myApp.main').factory('mainService', mainService);

	mainService.$inject = [ 'dataService' ];

	function mainService(dataService) {
		var service = {};
		return service;
	}

})();