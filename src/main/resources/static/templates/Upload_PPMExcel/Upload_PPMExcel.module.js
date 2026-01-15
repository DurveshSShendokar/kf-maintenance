(function() {
	'use strict';

	angular
	.module('myApp.Upload_PPMExcel', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.Upload_PPMExcel', {
					url : "/Upload_PPMExcel",
					
					views : {
						"sub" : {
							templateUrl : "templates/Upload_PPMExcel/Upload_PPMExcel.html",
							controller : "Upload_PPMExcelController as vm"
				}
			}
		})
	
	});
	
})();




