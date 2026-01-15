(function() {
	'use strict';
	angular.module('myApp.sample', [

	]).config(function($stateProvider) {
		$stateProvider.state('main.sample', {
//			abstract : true,
			url : "/sample",
			views : {
				"sub" : {
					templateUrl: 'templates/sampleCOde/sample.html',
					controller : "sampleController as vm"
				}
			}
		})
	});
})();