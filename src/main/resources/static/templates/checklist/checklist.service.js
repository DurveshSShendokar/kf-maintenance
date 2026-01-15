(function() {
	'use strict';

	angular.module('myApp.checklist').factory('checklistService', checklistService);

	checklistService.$inject = [ 'checklistHttpService', '$q', 'toastr' ];

	/* @ngInject */
	function checklistService(checklistHttpService, $q, toastr) {
		var service = {
			getchecklists : getchecklists,
			deleteChecklist : deleteChecklist,
			addchecklist : addchecklist,
			uploadChecklist : uploadChecklist,
			searchchecklist : searchchecklist,
		};
		return service;

		// ***************************************************************

		function getchecklists() {
			var deferred = $q.defer();
			checklistHttpService.getchecklists().then(function(response) {
				deferred.resolve(response.data);
			}, function(err) {
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}
		
		function deleteChecklist(checklist) {
			var deferred = $q.defer();
			checklistHttpService.deleteChecklist(checklist).then(function(response) {
				toastr.success('Deleted....', 'Succesfully !!');
				deferred.resolve(response.data);
			}, function(err) {
				toastr.error(err.status + ' : ' + err.statusText, 'Error !!');
			});
			return deferred.promise;
		}
		
	

		function addchecklist(checklist) {
			var deferred = $q.defer();
			checklistHttpService.addchecklist(checklist).then(function(response) {
				//toastr.success('FSMA Entry Added....', 'Succesfully !!');
				deferred.resolve(response.data);
				
				/*var arrs = [];
				   console.log('asdfgh',response);
				   arrs = response.data.qrList;
				   
				   if(arrs && arrs.length > 0){
					   
					   //alert(response.data.qrList);
					   
					   $rootScope.$broadcast('fileUploadLaptopAssetSuccess', {
			                	str : 'exist',
			                	response : arrs
			            });
					   
				   }
				   
				   
				   else{
					   
					   
				   }*/
				
			
			}, function(err) {
				toastr.error(err.status + ' : ' + err.statusText, 'Error !!');
			});
			return deferred.promise;
		}

		

		function uploadChecklist(file) {
			var deferred = $q.defer();
			checklistHttpService.uploadChecklist(file).then(function(response) {
				toastr.success('File Imported Successful !!');

				deferred.resolve(response.data);
			}, function(err) {
				debugger;
				toastr.error(err.status + ' : ' + err.statusText, 'Error !!');
			});
			return deferred.promise;
		}

	
		function searchchecklist(checklist) {
			var deferred = $q.defer();
			checklistHttpService.searchchecklist(checklist).then(function(response) {
				deferred.resolve(response.data);
			}, function(err) {
				alert(JSON.stringify(err.statusText));
			});
			return deferred.promise;
		}

	}

})();