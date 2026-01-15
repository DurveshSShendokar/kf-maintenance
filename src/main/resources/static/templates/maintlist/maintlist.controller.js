(function() {
	'use strict';

	angular.module('myApp.maintlist').controller('MaintlistController', MaintlistController)
			.controller('MaintlistModalCtrl', MaintlistModalCtrl).controller(
					'MaintlistModalAddEditCtrl', MaintlistModalAddEditCtrl);

	MaintlistController.$inject = [ '$state', 'maintlistService', '$uibModal', '$log','$http',
			'$scope', 'toastr', 'machineService','localStorageService', 'machine_mstService', '$filter','genericFactory','ApiEndpoint'];
	MaintlistModalCtrl.$inject = [ '$uibModalInstance', 'items', '$scope' ];
	MaintlistModalAddEditCtrl.$inject = [ '$uibModalInstance', 'maintlist', '$scope', 'maintlistService', '$filter'];

	/* @ngInject */
	function MaintlistController($state, maintlistService, $uibModal, $log, $http, $scope, toastr, machineService,localStorageService, machine_mstService, $filter,genericFactory,ApiEndpoint) {
		var checklistUrl = staticUrlMaintenance+"/checklist";
		const baseUrl = ApiEndpoint.url;
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			maintlists : [],
			checklists : [],
			checklists2 : [],
			machines : [],
			view : view,
			add : add,
			cancel:cancel,
			delet : delet,
			downloadMaintImage:downloadMaintImage,
			getChecklist : getChecklist,
			getChecklist2 : getChecklist2,
			saveMaintenanceReport : saveMaintenanceReport,
			takeAction	:	takeAction,
			getRecord : getRecord,
			getMaintImages:getMaintImages,
			openUploadModal:openUploadModal,
			uploadMaintImages:uploadMaintImages,
			closeUploadModal:closeUploadModal
		});

		(function activate() {
			$scope.maint = {};
			$scope.showEdit = false;
			vm.observation = '';
//			$scope.userDetails = localStorageService.get('myeplanAdminUser');
			$scope.userDetails = sessionStorage.getItem('KFapplication'); //localStorageService.get('renataLoggedInUser');
			$scope.userDetails = JSON.parse($scope.userDetails);
			
			$scope.actionDoneBy = $scope.userDetails;
			$scope.selectMachine = 'selectMachine';
			
			loadMaintlists();
			loadMachines();
			
			

			$scope.fetchdata = function() {

				/* $scope.arr */

//				debugger;

				$scope.sd = $scope.trial.start_date;
				$scope.ed = $scope.trial.end_date;

				$scope.formattedStartDate = 0;

				$scope.formattedEndDate = 0;

				$scope.formattedStartDate = $filter('date')($scope.sd,
						"yyyy-MM-dd");

				$scope.formattedEndDate = $filter('date')($scope.ed,
						"yyyy-MM-dd");

				$scope.trial.start_date = $scope.formattedStartDate;
				$scope.trial.end_date = $scope.formattedEndDate;

			}
			
		})();

		// ******************************************************

		vm.selectedMaintId = null;
		   vm.showUploadModal = false;
		   

		   function getMaintImages(maintId) {
		       $http.get(baseUrl + "/maint/getMaintImages/" + maintId)
		           .then(function(response) {
		               vm.selectedMaintImages = response.data;
		               vm.showImagesModal = true;
		               vm.selectedMaintIdForImages = maintId;
		           })
		           .catch(function(error) {
		               console.error("Error fetching images:", error);
		               alert("Could not load images.");
		           });
		   }

		   vm.getMaintImages = getMaintImages;
		
		   function downloadMaintImage(maintId, imageId) {
		       const downloadUrl = baseUrl + "/maint/downloadMaintImage?maintId=" + maintId + "&imageId=" + imageId;
		       const link = document.createElement('a');
		       link.href = downloadUrl;
		       link.download = ''; // let server control filename
		       document.body.appendChild(link);
		       link.click();
		       document.body.removeChild(link);
		   }

		   function cancel() {
		             $scope.showEdit = false;
		         }
		

		   function openUploadModal(maintId) {
		          console.log("Opening modal for maintId:", maintId);
		          vm.selectedMaintId = maintId;
		          vm.showUploadModal = true;
		      }

		      function closeUploadModal() {
		          vm.showUploadModal = false;
				  vm.selectedFiles = [];
		         // document.getElementById('uploadFiles').value = ""; // clear file input
		      }


			  // Upload selected images for the selected maintenance entry
			  function uploadMaintImages() {
			      if (!vm.selectedFiles || vm.selectedFiles.length === 0) {
			          alert("Please select at least one file to upload.");
			          return;
			      }

			      var formData = new FormData();
			      for (var i = 0; i < vm.selectedFiles.length; i++) {
			          formData.append("files", vm.selectedFiles[i]);
			      }

			      $http.post(baseUrl + "/maint/uploadMaintImages/" + vm.selectedMaintId, formData, {
			          transformRequest: angular.identity,
			          headers: { 'Content-Type': undefined }
			      }).then(function(response) {
			          alert("Upload successful!");
			          vm.closeUploadModal();
			      }).catch(function(error) {
			          console.error("Upload error:", error);
			          alert("Upload failed. Please try again.");
			      });
			  }

		
		function getChecklist(maint) {

			vm.observation = maint.overall_status ? maint.overall_status : null;
			vm.spare_used = maint.spare_used ? maint.spare_used : null;
			var msg=""
				 var url =checklistUrl+"/getchecklist?machine_id="+maint.machine.machine_id+"&freq="+maint.frequency;
				 console.log("url  "+url);
				genericFactory.getAll(msg,url).then(function(response) {
					console.log("response"+JSON.stringify(response));
				});
			maintlistService.getChecklist(maint).then(function(data) {
				vm.checklists = data;
				console.log(JSON.stringify(vm.checklists));
//				if(vm.checklists.length > 0){
					$scope.showEdit = true;
				/*}else{
					$scope.showEdit = false;
				}*/
				$scope.machineDetails = maint;
				$scope.maint_id = maint.maint_id;
				
				$scope.equipment = maint.machine.machine_name+' - '+maint.machine.eqid;
//				console.log(JSON.stringify(vm.checklists));
				
				setTimeout(function(){
					window.scroll({
						  top: document.body.scrollHeight, 
						  left: 0, 
						  behavior: 'smooth' 
					});
				},0);
		
			});
		}
		
		
		

		function getChecklist2(maint) {
			
			debugger;
			
			maintlistService.getChecklist2(maint).then(function(data) {
				vm.checklists2 = data;
				
					for(var j = 0; j < vm.checklists2.length; j++){
						if($scope.maint_id == vm.checklists2[j].maint.maint_id){
							if(vm.checklists2[j].status == 'ok')
								vm.checklists[j].action = true;
							else
								vm.checklists[j].action = false;
						}
					}
				
					setTimeout(function(){
						window.scroll({
							  top: document.body.scrollHeight, 
							  left: 0, 
							  behavior: 'smooth' 
						});
					},0);
//				console.log(vm.checklists2);
				
			});
		}
		
		
		function loadMaintlists() {
			machineService.getMachines().then(function(data) {
				vm.maintlists = data;
//				console.log(JSON.stringify(vm.maintlists));
			});
		}
		
		
		function getRecord(maint) {
			if($scope.selectMachine && $scope.selectMachine != 'selectMachine')				
				maint.machine = JSON.parse($scope.selectMachine);
			else
				maint.machine = null;
			maintlistService.getrecords(maint).then(function(data) {
				vm.maintlists = data;
				console.log(JSON.stringify(vm.maintlists));
			});
		}
		
		
		
		function loadMachines() {
			machine_mstService.getMachine_msts().then(function(data) {
				vm.machines = data;
				
			});
		}
		
		function delet(maint){
			maintlistService.deleteMaintlist(maint).then(function(){
				loadMaintlists();
			});
		}
	
		function takeAction(iIndex){
			vm.checklists[iIndex].action = !vm.checklists[iIndex].action; 
		}
		
		
		function saveMaintenanceReport(){
			var arr = [];
			for(var i in vm.checklists){
				var obj = {
					checkpoint:vm.checklists[i].checklist_id,
					value:vm.checklists[i].status
					
				};
				
				arr.push(obj);
			}
		
		
			var iObj =  {
			        "checkpointMaaint": arr,
			        "maint_id" : $scope.maint_id,
			        "mode": "bjh",
			        "frequency": "bj hn",
			        "overall_status": vm.observation,
					"spare_used": vm.spare_used,
			        "done_by": userDetail,
			        "machine": {
			            "machine_id": $scope.machineDetails.machine.machine_id,
			            "machine_name": $scope.equipment
			        }
			 }
			
			console.log("REQ "+JSON.stringify(iObj));
			//return;
			maintlistService.saveChecklists(iObj).then(function(iObj) {
				$scope.showEdit = false;
				toastr.success('Successfully submitted');
				loadMaintlists();
			});
		}

		function view(maintlist) {
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/maintlist/maintlistModelView.html',
				controller : 'MaintlistModalCtrl',
				controllerAs : 'vm',
				size : 'md',
				resolve : {
					items : function() {
						return maintlist;
					}
				}
			});

			modalInstance.result.then(function() {
				
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});
		}

		function add(maintlist) {
			var usr = maintlist ? maintlist : {};
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/maintlist/maintlistModelAddEdit.html',
				controller : 'MaintlistModalAddEditCtrl',
				controllerAs : 'vm',
				size : 'lg',
				resolve : {
					maintlist : function() {
						return usr;
					}
				}
			});

			modalInstance.result.then(function() {
				loadMaintlists();
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});
		}

	}

	function MaintlistModalCtrl($uibModalInstance, items, $scope) {
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

	function MaintlistModalAddEditCtrl($uibModalInstance, maintlist, $scope, maintlistService, $filter) {
		var vm = angular.extend(this, {
			ok : ok,
			cancel : cancel,
			roles : [],
			maintlistTypes : [],
			maintlists : [],
		});

		(function activate() {
			$scope.maintlist = maintlist;
			loadRoles();
			loadMaintlistTypes();
			loadMaintlists();
			/*
			$scope.setTime = function(){
				
				$scope.CurrentDate = new Date();
				
				$scope.cd = $filter('date')($scope.CurrentDate, "hh:mm:ss a");
				
				$scope.maintlist.added_time =  $scope.cd; 
			}
			*/
			
		})();

		// ******************************************************
		

		function loadMaintlists() {
			maintlistService.getMaintlists().then(function(data) {
				vm.maintlists = data;

				console.log(JSON.stringify(vm.maintlists));
			});
		}

		
		function loadRoles(){
			maintlistService.getRoles().then(function(data){
				vm.roles = data;
			});			
		}
		
		function loadMaintlistTypes(){
			maintlistService.getMaintlistTypes().then(function(data){
				vm.maintlistTypes = data;
			});			
		}
		
		function ok(maintlist) {
			
			debugger;
			maintlistService.addMaintlist(maintlist).then(function(){
				$uibModalInstance.close(maintlist);
				sendMail(maintlist);
				sendSms(maintlist);
			});
		}
		
		function sendMail(maintlist){
			maintlistService.sendMail(maintlist).then(function(){
				
			});
		}
		
		function sendSms(maintlist){
			maintlistService.sendSms(maintlist).then(function(){
				
			});
		}

		function cancel() {
			$uibModalInstance.dismiss('cancel');
		}
	}
})();
