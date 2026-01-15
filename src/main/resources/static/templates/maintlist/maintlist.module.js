(function() {
	'use strict';

	angular
	.module('myApp.maintlist', [ 'datatables' ])
	.config(function($stateProvider) {
		$stateProvider
		.state('main.maintlist', {
			url : "/maintlist",
			views : {
				"sub" : {
					templateUrl : "templates/maintlist/maintlist.html",
					controller : "MaintlistController as vm"
				}
			}
		});
	})

	.directive('fileModel', ['$parse', function ($parse) {
		return {
			restrict: 'A',
			link: function(scope, element, attrs) {
				var model = $parse(attrs.fileModel);
				var modelSetter = model.assign;

				element.bind('change', function(){
					scope.$apply(function(){
						modelSetter(scope, element[0].files);
					});
				});
			}
		};
	}]);

})();
