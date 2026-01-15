(function() {
	'use strict';

	angular.module('myApp.shift').controller('ShiftController', ShiftController)
			.controller('ShiftModalCtrl', ShiftModalCtrl).controller(
					'ShiftModalAddEditCtrl', ShiftModalAddEditCtrl);

	ShiftController.$inject = [ '$state', 'shiftService', '$uibModal', '$log',
			'$scope', 'toastr', 'categoryService','machine_mstService','genericFactory','fileUpload','$http'];
	
	ShiftModalCtrl.$inject = [ '$uibModalInstance', 'items', '$scope' ];
	ShiftModalAddEditCtrl.$inject = [ '$uibModalInstance', '$filter', 'shift', '$scope', 'shiftService'];

	/* @ngInject */
	function ShiftController($state, shiftService, $uibModal, $log, $scope, toastr, categoryService,machine_mstService,genericFactory,fileUpload,$http) {
	
		var reportUrl = staticUrl+"/report";
			var uploadUrl = staticUrl+"/upload";

		var vm = angular.extend(this, {
			shifts : [],
			targets : [],
			view : view,
			add : add,
			delet : delet,
			ok : ok,
			edit:edit,
			confirmDelete: confirmDelete,
             cancelDelete:cancelDelete,
			upload:upload,
			addnew:addnew,
			cancel:cancel,
			uploadNew:uploadNew
		});

		(function activate() {
			$scope.shift = {};
			$scope.target = {};
			loadShifts();
			loadTargets();
			loadMachines();
			$scope.addTab=false
			$scope.yearArr = [];
			
			var currentYear = new Date().getFullYear();
			
			$scope.yearArr.push(currentYear);
			$scope.yearArr.push(currentYear+1);
			
			
		//	$scope.target.year = currentYear;
			
			
		})();
		
		
		
		$scope.handleFileSelect = (files)=>{
			$scope.selectedFile = files[0];
			console.log("File: ", $scope.selectedFile);
		}
		function cancel(){
				$scope.addTab=false
			$scope.uploadTab=false
		}
		function uploadNew(){
				$scope.uploadTab=true
				$scope.addTab=false
		}
		function upload(){
				$scope.uploadTab=true
				$scope.addTab=false
				
				var file = $scope.selectedFile;
			console.log("File: ", file);

			if (!file) {
				toastr.error("Please select file");
				return;
			}

		
				var url = uploadUrl + "/uploadTarget";
				console.log("URL: ", url);
				$('.loading').show();
				var fd = new FormData();
				fd.append('file', file);
			
				console.log("URL :: "+url)
				$http.post(url, fd, {
					transformRequest: angular.identity,
					headers: {
						'Content-Type': undefined
					}
				})
				.then(function successCallback(response) {
					console.log("RESPONCE  "+JSON.stringify(response.data))
					$('.loading').hide();
					if(response.data.code==200){
						toastr.success('Uploaded....', 'Succesful !!',{ timeOut: 10000 });	
						
							loadTargets();
					}else{
						toastr.error('Upload....', 'UnSuccesful !!');
					}
					$scope.uploadTab= false;
				}, function errorCallback(response) {
			    	$('.loading').hide();
					$scope.uploadTab= false;
					toastr.error('Upload....', 'UnSuccesful !!');
				});
				angular.element("input[type='file']").val(null);
				$scope.selectedFile = null;
			
		}
		function addnew(){
				$scope.uploadTab=false
				$scope.addTab=true
			
		}
		
		function edit(target){
			$scope.target=target
		}
		/*function delet(target){
			var msg=""
				 var url =reportUrl+"/deleteTaget";
				genericFactory.add(msg,url,target).then(function(response) {
					console.log("response "+JSON.stringify(response.data))

					
					if(response.data.code==200){
						loadTargets()
					
						toastr.success(response.data,message);
					}else{
						toastr.error(response.data.message);
					}
					
								
			});
			
		}*/
		
		
		 let selectedtarget = null;
        function delet(target) {
            selectedtarget = target;
            vm.showModal = true; 
        }

        // Confirm delete function - sends the delete request to the backend
        function confirmDelete() {
            if (!selectedtarget) return;

            var url = reportUrl + "/deleteTaget";
            
            // Send the delete request with the machine operation to be deleted
            $http.post(url, selectedtarget)
                .then(function(response) {
                    toastr.success('Deleted Machine Target successfully!');
                    loadTargets();  
                })
                .catch(function(error) {
                    toastr.error('Error deleting Machine Target');
                    console.error("Error deleting Machine Target:", error);
                })
                .finally(function() {
                    vm.showModal = false; 
                });
        }

        // Function to cancel delete and close the modal
        function cancelDelete() {
            vm.showModal = false; 
        }


		
		
		// ******************************************************

		function loadMachines() {
			machine_mstService.getMachine_msts().then(function(data) {
				vm.machines = data;
				console.log("Machines  :: "+JSON.stringify(vm.machines))
				
			});
		}
		function ok(target) {
//			var ele = document.getElementById('shiftName').value = '';
		
			/*debugger;*/
			
			if(!target.machineName){
				toastr.error('Please select Machine');
				return;
			}
			if(!target.year || target.year == ''){
				toastr.error('Please select year');
				return;
			}
			if(!target.month || target.month == ''){
				toastr.error('Please select month');
				return;
			}
			if(!target.type || target.type == ''){
				toastr.error('Please select type');
				return;
			}
			if(!target.hour || target.hour == ''){
				toastr.error('Please enter hours');
				return;
			}
			
		/*	shiftService.addShift(target).then(function(){
				$scope.target = {};
				loadTargets();
				
//				$uibModalInstance.close(shift);
				
			});*/
			
			var msg=""
				 var url =reportUrl+"/createTaget";
				genericFactory.add(msg,url,target).then(function(response) {
					console.log("response "+JSON.stringify(response.data))

					
					if(response.data.code==200){
						//loadMachineOperations()
						$scope.addNewTab=false;
						$scope.target = {};
						loadTargets();
												toastr.success(response.data,message);
					}else{
						toastr.error(response.data.message);
					}
					
								
			});
		}
		
		
		
		function loadCategories() {
			
			categoryService.getCategorys().then(function(data) {
				vm.categories = data;
				
			});
		}

		function loadShifts() {
			shiftService.getShifts().then(function(data) {
				vm.shifts = data;
			});
		}
		
		
		function loadTargets() {
			shiftService.getTargets().then(function(data) {
				vm.targets = data;
				
				console.log("Target "+JSON.stringify(vm.targets ))
			});
		}
		
		
		

		function view(shift) {
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/shift/shiftModelView.html',
				controller : 'ShiftModalCtrl',
				controllerAs : 'vm',
				size : 'md',
				resolve : {
					items : function() {
						return shift;
					}
				}
			});

			modalInstance.result.then(function() {
				
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});
		}

		function add(target) {
//			$scope.shift.shift_name = shift.shift_name;
//			$scope.shift.deletes = shift.deletes;
//			$scope.shift.shift_id = shift.shift_id;
			$scope.target = {};
			$scope.target.category = target.category;
//			$scope.target.category.cat_id = target.category.cat_id;
			$scope.target.year = target.year;
			$scope.target.month = target.month;
			$scope.target.type = target.type;
			$scope.target.hour = target.hour;
			$scope.target.target_id = target.target_id;
			
			setTimeout(function(){
				window.scroll({
					  top: 0, 
					  left: 0, 
					  behavior: 'smooth' 
				});
			}, 0);
			
			/*var dept = shift ? shift : {};
			// alert(JSON.stringify(usr));
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/shift/shiftModelAddEdit.html',
				controller : 'ShiftModalAddEditCtrl',
				controllerAs : 'vm',
				size : 'lg',
				resolve : {
					shift : function() {
						return dept;
					}
				}
			});

			modalInstance.result.then(function() {
				loadShifts();
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});*/
		}

	}

	function ShiftModalCtrl($uibModalInstance, items, $scope) {
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

	function ShiftModalAddEditCtrl($uibModalInstance, $filter, shift, $scope, shiftService) {
		var vm = angular.extend(this, {
			ok : ok,
			cancel : cancel,
		});

		(function activate() {
			$scope.shift = shift;
			
			/*$scope.setDeletes = function(){
				
				$scope.shift.deletes = 1;
				
			}*/
			$scope.setFormDate = function() {

				$scope.st = $scope.shift.shift_start;
				$scope.gt = $scope.shift.shift_end;

				
				$scope.shift.shift_start = $filter('date')($scope.st,
						"hh:mm:ss a");
				$scope.shift.shift_end = $filter('date')($scope.gt,
						"hh:mm:ss a");
			
			}
			
			
			
		})();

		// ******************************************************
		
		function ok(shift) {
			
			/*debugger;*/
			shiftService.addShift(shift).then(function(){
				$uibModalInstance.close(shift);
				
			});
		}

		
		
		
		function cancel() {
			$uibModalInstance.dismiss('cancel');
		}
	}
})();
