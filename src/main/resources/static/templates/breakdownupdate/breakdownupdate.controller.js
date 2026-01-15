(function() {
	'use strict';

	angular.module('myApp.breakdownupdate').controller('BreakdownupdateController', BreakdownupdateController)
			.controller('BreakdownupdateModalCtrl', BreakdownupdateModalCtrl).controller(
					'BreakdownupdateModalAddEditCtrl', BreakdownupdateModalAddEditCtrl);

	BreakdownupdateController.$inject = [ '$state', '$filter', 'breakdownupdateService', '$uibModal', '$log','$http',
			'$scope', 'toastr', 'machine_mstService', 'shiftService','localStorageService', 'breakdownService','genericFactory','ApiEndpoint'];
	BreakdownupdateModalCtrl.$inject = [ '$uibModalInstance', 'items', '$scope' ];
	BreakdownupdateModalAddEditCtrl.$inject = [ '$uibModalInstance', '$filter', 'breakdownupdate', '$scope', 'breakdownupdateService' ];

	/* @ngInject */
	function BreakdownupdateController($state,$filter, breakdownupdateService, $uibModal, $log,$http, $scope, toastr, machine_mstService, shiftService,localStorageService, breakdownService,genericFactory,ApiEndpoint) {
		var machineUrl = staticUrl+"/machine_mst";
		var MaintSparestockUrl = staticUrl+"/MaintSparestock";
		 const baseUrl = ApiEndpoint.url;
		
		
var userDetail = localStorageService.get(ApiEndpoint.userKey);
if (!userDetail) {
    console.error('User details not found in local storage.');
    return; // Optionally, you can redirect or show a message to the user
}

		var vm = angular.extend(this, {
			breakdownupdates : [],
			breakdowns : [],
			 spareComsuptions:[],
           spareUseds:[],
			spares:[],
			view : view,
			add : add,
			delet : delet,
			utilizedSpares: [],
			 loadAllSpares:loadAllSpares,
			 selectedSpares:[],
			 updateSpareUseds: updateSpareUseds,
			ok : ok,
			addSpare	:	addSpare,
			removeSpare	:	removeSpare,
			getMaintImages:getMaintImages,
						openUploadModal:openUploadModal,
						uploadMaintImages:uploadMaintImages,
						closeUploadModal:closeUploadModal,
cancel:cancel
		});

		(function activate() {
			$scope.breakdownupdate = {};
			$scope.userDetails = sessionStorage.getItem('renataLoggedInUser'); //localStorageService.get('myeplanAdminUser');
			$scope.userDetails = JSON.parse($scope.userDetails);
			$scope.breakdownupdate.action_by = userDetail.firstName+' '+userDetail.lastName;
			$scope.selectedEquipment = 'selectEquipment';
			$scope.selectedShift = 'select shift';
			$scope.date = new Date();
			$scope.showEdit = false;
			$scope.machine_mst = {
				type : ""
			};
			loadBreakdownupdates();
			loadShifts();
			loadMachines();
			loadBreakdowns();
		})();
		
		var counter = 1;
	
		// ******************************************************

		
		

		vm.selectedMaintId = null;
		   vm.showUploadModal = false;
		   

		   function getMaintImages(breakdownId) {
		       $http.get(baseUrl + "/breakdown/getImages/" + breakdownId)
		           .then(function(response) {
		               vm.selectedMaintImages = response.data;
		               vm.showImagesModal = true;
		               vm.selectedMaintIdForImages = breakdownId;
		           })
		           .catch(function(error) {
		               console.error("Error fetching images:", error);
		               alert("Could not load images.");
		           });
		   }

		   vm.getMaintImages = getMaintImages;

		   vm.downloadMaintImage = function(imageId) {
		       const downloadUrl = baseUrl + "/breakdown/downloadImage?imageId=" + imageId;

		       fetch(downloadUrl, {
		           method: 'GET',
		           headers: {
		               // only if JWT is required
		               'Authorization': 'Bearer ' + localStorage.getItem("token")
		           }
		       })
		       .then(response => {
		           if (!response.ok) {
		               throw new Error("Download failed: " + response.statusText);
		           }

		           // Extract filename from Content-Disposition
		           const disposition = response.headers.get("Content-Disposition");
		           let fileName = "downloaded_file";
		           if (disposition && disposition.indexOf("filename=") !== -1) {
		               fileName = disposition.split("filename=")[1].replace(/"/g, '');
		           }

		           return response.blob().then(blob => ({ blob, fileName }));
		       })
		       .then(({ blob, fileName }) => {
		           const url = window.URL.createObjectURL(blob);
		           const link = document.createElement('a');
		           link.href = url;
		           link.download = fileName;  // correct filename
		           document.body.appendChild(link);
		           link.click();
		           document.body.removeChild(link);
		           window.URL.revokeObjectURL(url);
		       })
		       .catch(error => {
		           console.error("Error downloading image:", error);
		           alert("Download failed!");
		       });
		   };

		   function cancel() {
		             $scope.showEdit = false;
		         }


		   function openUploadModal(breakdownId) {
		          console.log("Opening modal for breakdownId:", breakdownId);
		          vm.selectedMaintId = breakdownId;
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

			      $http.post(baseUrl + "/breakdown/uploadImages/" + vm.selectedMaintId, formData, {
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

		
		
		
		
		
		
		
		
		
		function loadBreakdowns() {
			breakdownService.getBreakdowns().then(function(data) {
				vm.breakdowns = data;
			});
		}
		
		
		
		function validateBeforeTrial(breakdownupdate){
			if(!breakdownupdate.bd_slip){
				toastr.error('Please enter slip no');
				return true;
			}
			if($scope.selectedShift == 'select shift'){
				toastr.error('Please select shift');
				return true;	
			}
			if(!$scope.date){
				toastr.error('Please enter date');
				return true;	
			}
			if($scope.machine_mst.type == ''){
				toastr.error('Please select type');
				return true;
			}
			if($scope.selectedEquipment == 'selectEquipment'){
				toastr.error('Please select Equipment');
				return true;
			}
			if(!breakdownupdate.observation || breakdownupdate.observation == ''){
				toastr.error('Please enter observation');
				return true;
			}
			if(!breakdownupdate.root_cause || breakdownupdate.root_cause == ''){
				toastr.error('Please enter root cause');
				return true;
			}
			if(!breakdownupdate.action_taken || breakdownupdate.action_taken == ''){
				toastr.error('Please enter action taken');
				return true;
			}
			/*if(!breakdownupdate.prev_action_plan || breakdownupdate.prev_action_plan == ''){
				toastr.error('Please enter action taken');
				return true;
			}*/
			/*if(!document.getElementById('namespa0').value || document.getElementById('namespa0').value==''){
				toastr.error('Please enter spare');
				document.getElementById('namespa0').focus();
				return true;
			}
			if(!document.getElementById('qtyspa0').value || document.getElementById('qtyspa0').value==''){
				toastr.error('Please enter quantity for spare');
				document.getElementById('qtyspa0').focus();
				return true;
			}
			*/
			return false;
		}
		
		function ok(breakdownupdate) {
			console.log("OK BREAKDWON "+JSON.stringify(breakdownupdate))
			var br ={}
			br = $scope.breakDwn
			br.action_taken=breakdownupdate.action_taken
			br.prev_action_plan=breakdownupdate.prev_action_plan
			br.root_cause=breakdownupdate.root_cause
			br.action_by=userDetail.firstName+" "+userDetail.lastName
			br.observation=$scope.breakdownupdate.observation
			br.done_by=userDetail.firstName+" "+userDetail.lastName	
			console.log("BR :: "+JSON.stringify(br))
			
			 breakdownupdate.breakdown.breakdownSpares=vm.selectedSpares
			
			breakdownupdateService.updateBreakDown(br);
			if(validateBeforeTrial(breakdownupdate)){
				return;
			}
	
			var yy = $scope.date.getFullYear();
			breakdownupdate.deletes = 1;
			breakdownupdate.shift = JSON.parse($scope.selectedShift);
			breakdownupdate.machine = JSON.parse($scope.selectedEquipment);
			breakdownupdate.breakdown_date = $scope.date	//dd + '-' + mm + '-' + yy;
			breakdownupdate.selectedSpare=vm.spares
			breakdownupdate.breakdown = $scope.breakdownupdate.breakdown;
			breakdownupdate.done_by=userDetail.firstName+" "+userDetail.lastName	
			breakdownupdate.action_taken=breakdownupdate.action_taken
			breakdownupdate.prev_action_plan=breakdownupdate.prev_action_plan
			breakdownupdate.root_cause=breakdownupdate.root_cause
			breakdownupdate.action_by=userDetail.firstName+" "+userDetail.lastName
			breakdownupdate.observation=$scope.breakdownupdate.observation
			breakdownupdate.done_by=userDetail.firstName+" "+userDetail.lastName	
			breakdownupdateService.addBreakdownupdate(breakdownupdate).then(function(){
				$scope.breakdownupdate = {};
				loadBreakdownupdates();
				loadBreakdowns();
//				$uibModalInstance.close(breakdownupdate);
				$scope.showEdit = false;
				vm.selectedSpares = []; // Reset selected spares            
                         vm.spareUseds=[]
				
			});
		}
		
		function addSpare() {
    var obj = {
        spare_name: "",
        avialableQuantity: "",
        quantity: 0,
    };
    vm.spares.push(obj);
}

function removeSpare(index) {
    if (index > -1) {
        vm.spares.splice(index, 1);
    }
}
		
		function loadUtilizedSpares(breakdown_id) {
			 var msg=""
            var url = baseUrl + "/breakdown/breakdown_spare_report/" + breakdown_id; 
            console.log("Fetching utilized spares from URL: " + url);
         genericFactory.getAll(msg,url).then(function(response) {
				vm.utilizedSpares= response.data;
				console.log("utilizedSpares : "+JSON.stringify(vm.utilizedSpares))});
        }
        
      
      
        
         function loadAllSpares(machine_id) {
			 
			 
			 
			 var msg=""
				  var url = MaintSparestockUrl + "/getMachineSpareByMachineId?machineId=" +machine_id; 
				genericFactory.getAll(msg,url).then(function(response) {
				vm.AllSpares= response.data;
				console.log("AllSpares : "+JSON.stringify(vm.AllSpares))
								
			});
			
			
			
          /*  var url = MaintSparestockUrl + "/getMachineSpareByMachineId?machineId=" +machine_id; 
            console.log("Fetching all spares from URL: " + url);
           genericFactory.getAll(url)
                .then(function(response) {
                    vm.AllSpares = response.data; 
                    console.log("Fetched all spares:", JSON.stringify(vm.AllSpares));
                })
                .catch(function(error) {
                    console.error("Error fetching all spares:", error);
                    toastr.error("Failed to fetch all spares.");
                });*/
        }
		
		  function updateSpareUseds(selspare) {
				  console.log(" selecteedspare:", JSON.stringify(selspare)  ); // Log the updated spare used array
				//  vm.selectedSpares.push(selecteedspare);
				
				   vm.spares.push(selspare)
				
		
            console.log("Updated spare used:", vm.spareUseds); // Log the updated spare used array
        }

		
		
		function loadShifts() {
			shiftService.getShifts().then(function(data) {
				vm.shifts = data;
			});
		}
		
		

		function loadMachines() {
			/*machine_mstService.getMachine_msts().then(function(data) {
				vm.machines = data;
				console.log("MACHINE s   "+JSON.stringify(vm.machines))
			});*/
			var msg=""
				 var url =machineUrl+"/list";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.machines= response.data;
				console.log("machines : "+JSON.stringify(vm.machines))
								
			});
			
			
		}

		function loadBreakdownupdates() {
			breakdownupdateService.getBreakdownupdates().then(function(data) {
				vm.breakdownupdates = data;
			});
		}
		
		function delet(breakdownupdate){
			breakdownupdateService.deleteBreakdownupdate(breakdownupdate).then(function(){
				loadBreakdowns();
			});
		}

		function view(breakdownupdate) {
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/breakdownupdate/breakdownupdateModelView.html',
				controller : 'BreakdownupdateModalCtrl',
				controllerAs : 'vm',
				size : 'md',
				resolve : {
					items : function() {
						return breakdownupdate;
					}
				}
			});

			modalInstance.result.then(function() {
				
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});
		}

		function add(breakdownupdate) {
			$scope.breakdownupdate.action_by=userDetail.firstName+" "+userDetail.lastName;
			$scope.breakdownupdate.extingRootC=breakdownupdate.extingRootC;
			$scope.breakdownupdate.ext_action_taken=breakdownupdate.extingAction;
			$scope.breakDwn=breakdownupdate;
			$scope.showEdit = true;
			$scope.breakdownupdate.breakdown = breakdownupdate;
			$scope.breakdownupdate.breakdownupdate_name = breakdownupdate.breakdownupdate_name;
//			$scope.breakdownupdate.deletes = breakdownupdate.deletes;
			$scope.breakdownupdate.breakdownupdate_id = breakdownupdate.breakdownupdate_id;
			$scope.breakdownupdate.bd_slip = breakdownupdate.bd_slip;
			$scope.breakdownupdate.observation = breakdownupdate.observation;
			$scope.selectedShift = JSON.stringify(breakdownupdate.shift);
			$scope.selectedEquipment = JSON.stringify(breakdownupdate.machine);
			$scope.machine_mst.type = breakdownupdate.machine.type.toString();
			 loadUtilizedSpares(breakdownupdate.breakdown_id)
			  loadAllSpares(breakdownupdate.machine.machine_id);
			  loadUtilizedSpares(breakdownupdate.breakdown_id);

			  
			  vm.selectedSpares = []; // Reset selected spares            
                         vm.spareUseds=[]
			$scope.detecteBy=breakdownupdate.addedBy.firstName+' '+breakdownupdate.addedBy.lastName
			
			addbuttonn();
			setTimeout(function(){
				window.scroll({
				  top: document.body.scrollHeight, 
				  left: 0, 
				  behavior: 'smooth' 
				});
			},0);
			
			
			/*var dept = breakdownupdate ? breakdownupdate : {};
			// alert(JSON.stringify(usr));
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/breakdownupdate/breakdownupdateModelAddEdit.html',
				controller : 'BreakdownupdateModalAddEditCtrl',
				controllerAs : 'vm',
				size : 'lg',
				resolve : {
					breakdownupdate : function() {
						return dept;
					}
				}
			});

			modalInstance.result.then(function() {
				loadBreakdownupdates();
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});*/
		}

	}

	function BreakdownupdateModalCtrl($uibModalInstance, items, $scope) {
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

	function BreakdownupdateModalAddEditCtrl($uibModalInstance, $filter, breakdownupdate, $scope, breakdownupdateService) {
		var vm = angular.extend(this, {
			ok : ok,
			cancel : cancel,
		});

		(function activate() {
			$scope.breakdownupdate = breakdownupdate;
			
			/*$scope.setDeletes = function(){
				
				$scope.breakdownupdate.deletes = 1;
				
			}*/
			$scope.setFormDate = function() {

				$scope.st = $scope.breakdownupdate.breakdownupdate_start;
				$scope.gt = $scope.breakdownupdate.breakdownupdate_end;

				
				$scope.breakdownupdate.breakdownupdate_start = $filter('date')($scope.st,
						"hh:mm:ss a");
				$scope.breakdownupdate.breakdownupdate_end = $filter('date')($scope.gt,
						"hh:mm:ss a");
			
			}
			
			
			
		})();

		// ******************************************************
		
		function ok(breakdownupdate) {
			
			/*debugger;*/
			breakdownupdateService.addBreakdownupdate(breakdownupdate).then(function(){
				$uibModalInstance.close(breakdownupdate);
				
			});
		}

		
		
		
		function cancel() {
			$uibModalInstance.dismiss('cancel');
		}
	}
})();
