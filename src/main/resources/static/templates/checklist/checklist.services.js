(function() {
	'use strict';
	angular.module('myApp.checklist').service('fileUpload',
			[ '$http', 'toastr', function($http, toastr) {
				this.uploadFileToUrl = function(file, uploadUrl) {
					var fd = new FormData();
					fd.append('file', file);
					$http.post(uploadUrl, fd, {

						transformRequest : angular.identity,
						headers : {
							'Content-Type' : undefined
						}
					}).success(function() {

						toastr.success('File Upload....', 'Succesfully !!');
						
					}).error(function() {
						toastr.error('sorry...!!!');
					})
				}
			} ]);
})();