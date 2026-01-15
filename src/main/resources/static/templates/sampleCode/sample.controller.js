/**
  * @author 		: ABS
  * @name			: materialIssueController
  * @description 	: controller for material issue module
  * @date 			: 20/06/2018
  */
(function() {
	'use strict';

	angular.module('myApp.sample').controller('sampleController', sampleController);

	sampleController.$inject = ['$state', '$scope', 'sampleService','toastr'];
	/* @ngInject */
	function sampleController($state, $scope, sampleService, toastr) {
		
		var vm = angular.extend(this, {
			sampleFunction : sampleFunction
		});

		(function activate() {
//			variableInitialization
		})();
		
		/**
		  * @author 		: ABS
		  * @name			: getRawMaterial
		  * @description 	: to fetch raw material list
		  * @date 			: 20/06/2018
		  */
		function sampleFunction(){
//			service call
			sampleService.getRawMaterial($scope.selectedRawMaterial.id).then(function(data) {
				$scope.rawMaterial = data;
			});
		}
		
	}
})();


