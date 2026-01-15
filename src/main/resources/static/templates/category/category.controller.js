(function() {
	'use strict';

	angular.module('myApp.category').controller('CategoryController', CategoryController)
			.controller('CategoryModalCtrl', CategoryModalCtrl).controller(
					'CategoryModalAddEditCtrl', CategoryModalAddEditCtrl);

	CategoryController.$inject = [ '$state', 'categoryService', '$uibModal', '$log',
			'$scope', 'toastr' ];
	CategoryModalCtrl.$inject = [ '$uibModalInstance', 'items', '$scope' ];
	CategoryModalAddEditCtrl.$inject = [ '$uibModalInstance', '$filter', 'category', '$scope', 'categoryService' ];

	/* @ngInject */
	function CategoryController($state, categoryService, $uibModal, $log, $scope, toastr) {
		var vm = angular.extend(this, {
			categorys : [],
			view : view,
			add : add,
			delet : delet,
			ok : ok
		});

		(function activate() {
			$scope.category = {};
			loadCategorys();
		})();

		// ******************************************************

		
		function ok(category) {
//			var ele = document.getElementById('categoryName').value = '';
			category.deletes = 1;
			/*debugger;*/
			if(!category.cat_name || category.cat_name == ""){
				toastr.error('Please enter category name');
				document.getElementById('categoryName').focus();
				return;
			}
			categoryService.addCategory(category).then(function(){
				$scope.category = {};
				loadCategorys();
//				$uibModalInstance.close(category);
			});
		}

		function loadCategorys() {
			categoryService.getCategorys().then(function(data) {
				vm.categorys = data;
			});
		}
		
		function delet(category){
			categoryService.deleteCategory(category).then(function(){
				loadCategorys();
			});
		}

		function view(category) {
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/category/categoryModelView.html',
				controller : 'CategoryModalCtrl',
				controllerAs : 'vm',
				size : 'md',
				resolve : {
					items : function() {
						return category;
					}
				}
			});

			modalInstance.result.then(function() {
				
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});
		}

		function add(category) {
			$scope.category.cat_name = category.cat_name;
//			$scope.category.deletes = category.deletes;
			$scope.category.cat_id = category.cat_id;
			/*var dept = category ? category : {};
			// alert(JSON.stringify(usr));
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/category/categoryModelAddEdit.html',
				controller : 'CategoryModalAddEditCtrl',
				controllerAs : 'vm',
				size : 'lg',
				resolve : {
					category : function() {
						return dept;
					}
				}
			});

			modalInstance.result.then(function() {
				loadCategorys();
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});*/
		}

	}

	function CategoryModalCtrl($uibModalInstance, items, $scope) {
		var vm = angular.extend(this, {
			items : items,
			ok : ok,
			cancel : cancel
		});

		(function activate() {

		})();

		// ******************************************************

		function ok() {
			$uibModalInstance.close();
		}

		function cancel() {
			$uibModalInstance.dismiss('cancel');
		}
	}

	function CategoryModalAddEditCtrl($uibModalInstance, $filter, category, $scope, categoryService) {
		var vm = angular.extend(this, {
			ok : ok,
			cancel : cancel,
		});

		(function activate() {
			$scope.category = category;
			
			/*$scope.setDeletes = function(){
				
				$scope.category.deletes = 1;
				
			}*/
			$scope.setFormDate = function() {

				$scope.st = $scope.category.category_start;
				$scope.gt = $scope.category.category_end;

				
				$scope.category.category_start = $filter('date')($scope.st,
						"hh:mm:ss a");
				$scope.category.category_end = $filter('date')($scope.gt,
						"hh:mm:ss a");
			
			}
			
			
			
		})();

		// ******************************************************
		
		function ok(category) {
			
			/*debugger;*/
			categoryService.addCategory(category).then(function(){
				$uibModalInstance.close(category);
				
			});
		}

		
		
		
		function cancel() {
			$uibModalInstance.dismiss('cancel');
		}
	}
})();
