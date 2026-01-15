(function() {
	'use strict';
	angular.module('myApp.common2',[])
	.directive('tree', function() {
	    return {
	        template: '<ol type="A"><tree-node ng-repeat="item in items track by $index"></tree-node></ol>',
	        restrict: 'E',
	        replace: true,
	        scope: {
	            items: '=',
	        }
	    }
	}).directive('treeNode', function($compile) {
	    return { 
	        restrict: 'E',
//	        template: '<li >{{item.name}}</li>',
	        template: '<li ng-if="item" style="font-weight: 600;margin-left: 2%;font-family: sans-serif;">{{item.detail + " - " + item.qrCodeDisplay}}</li>',
	        link: function(scope, elm, attrs) {
	        	if(scope.item && scope.item.qrCode){
	        		scope.item.qrCodeDisplay = scope.item.qrCode.slice(4, scope.item.qrCode.length);
	        	}
	    },
	    controller: 'treeController'
	    };
	}).controller('treeController', function($scope, $compile, $element){
		function getRandomColor() {
			  var letters = '0123456789ABCDEF';
			  var color = '#';
			  for (var i = 0; i < 6; i++) {
			    color += letters[Math.floor(Math.random() * 16)];
			  }
			  return color;
			}
		
		if ($scope.item && $scope.item.childQrCode && $scope.item.childQrCode.length > 0) {
			$scope.item.color = getRandomColor();
        	var children = $compile('<tree style="color: {{item.color}};" items="item.childQrCode"></tree>')($scope);
        	$element.append(children);
        }
	});
	
	
})();