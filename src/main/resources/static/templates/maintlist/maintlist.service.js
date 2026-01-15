(function() {
	'use strict';

	angular
		.module('myApp.maintlist')
		.factory('maintlistService', maintlistService);

	maintlistService.$inject = ['maintlistHttpService', '$q', 'toastr'];
	
	/* @ngInject */
	function maintlistService(maintlistHttpService, $q, toastr) {
		var service = {			
			getMaintlists : getMaintlists,
			deleteMaintlist : deleteMaintlist,
			addMaintlist : addMaintlist,
			sendMail : sendMail,
			sendSms : sendSms,
			getRoles : getRoles,
			getMaintlistTypes : getMaintlistTypes,
			getChecklist : getChecklist,
			saveChecklists	:	saveChecklists,
			getChecklist2 : getChecklist2,
			getrecords : getrecords
		};
		return service;

		// ***************************************************************

		function getMaintlists() {
			var deferred = $q.defer();
			maintlistHttpService.getMaintlists().then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		
		function getrecords(maint) {
			var deferred = $q.defer();
			maintlistHttpService.getrecords(maint).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		
		function getChecklist(maint) {
			var deferred = $q.defer();
			maintlistHttpService.getChecklist(maint).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		
		function getChecklist2(maint) {
			var deferred = $q.defer();
			maintlistHttpService.getChecklist2(maint).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		
		/**
		 * @author	ABS
		 * @date	July 24, 2018
		 * @name	saveChecklists
		 * @pupose	to save edited checklist report 
		 */
		function saveChecklists(iObj){
			var deferred = $q.defer();
			maintlistHttpService.saveChecklists(iObj).then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function deleteMaintlist(maintlist){
			var deferred = $q.defer();
			maintlistHttpService.deleteMaintlist(maintlist).then(function(response){
				toastr.success('Maintlist Deleted....', 'Succesfully !!');
				 
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		function addMaintlist(maintlist){
			var deferred = $q.defer();
			maintlistHttpService.addMaintlist(maintlist).then(function(response){
				toastr.success('Maintlist Added....', 'Succesfully !!');
				
				deferred.resolve(response.data);
			}, function(err){
				toastr.error('sorry maintlist cant registered at this time', 'Error !!');
			});
			return deferred.promise;
		}
		
		function sendMail(maintlist){
			var deferred = $q.defer();
			maintlistHttpService.sendMail(maintlist).then(function(response){
				toastr.success('Mail Sent....!', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		function sendSms(maintlist){
			var deferred = $q.defer();
			maintlistHttpService.sendSms(maintlist).then(function(response){
				toastr.success('SMS Sent....!', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err){
				toastr.error(err.status+' : '+err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
		function getRoles() {
			var deferred = $q.defer();
			maintlistHttpService.getRoles().then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function getMaintlistTypes() {
			var deferred = $q.defer();
			maintlistHttpService.getMaintlistTypes().then(function(response){
				deferred.resolve(response.data);
			}, function(err){
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}

		
	}

})();