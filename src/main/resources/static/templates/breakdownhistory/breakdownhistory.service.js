(function() {
	'use strict';

	angular
		.module('myApp.breakdownhistory')
		.factory('breakdownhistoryService', breakdownhistoryService);

	breakdownhistoryService.$inject = ['breakdownhistoryHttpService', '$q', 'toastr'];
	
	/* @ngInject */
	function breakdownhistoryService(breakdownhistoryHttpService, $q, toastr) {
		var service = {			
				getbreakdownhistory : getbreakdownhistory,
				
		};
		return service;

		// ***************************************************************

		function getbreakdownhistory(obj) {
			var deferred = $q.defer();
			breakdownhistoryHttpService.getbreakdownhistory(obj).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		

		
	}

})();