(function() {
	'use strict';

	angular.module('myApp.checklist').controller('checklistController', checklistController)
			.controller('checklistModalCtrl', checklistModalCtrl).controller('checklistPopupCtrl', checklistPopupCtrl);

	checklistController.$inject = [ '$http','$state', 'checklistService', '$uibModal', '$log',
			'$scope', 'toastr', 'ApiEndpoint','machine_mstService', 'fileUpload','$filter', '$window','genericFactory'];
	checklistModalCtrl.$inject = [ '$uibModalInstance', 'items', '$scope','$filter'];
	checklistPopupCtrl.$inject = [ '$uibModalInstance', 'items', '$scope','$filter','$rootScope'];
	

	/* @ngInject */
	function checklistController($http,$state, checklistService, $uibModal, $log, $scope, toastr, ApiEndpoint, machine_mstService, fileUpload,$filter, $window,genericFactory) {
		var uploadsUrl = staticUrl+"/upload";
		var checklistUrl = staticUrlMaintenance+"/checklist";
		var machineUrl = staticUrlMaintenance+"/machine_mst";
		const baseUrl = ApiEndpoint.url;
		var vm = angular.extend(this, {
			checklists : [],
			checklistArray:[],
			ok : ok,
			uploadXlxs : uploadXlxs,
			view : view,
			add : add,
			delet : delet,
			changeFile : changeFile,
			changeMode : changeMode,
			getEqidsByLabAndMachineName:getEqidsByLabAndMachineName,
			submitChecklist : submitChecklist,
			increment : increment,
			decrement : decrement,
			showUploadTab:showUploadTab,
			upload:upload,
			getMachinesByName:getMachinesByName,
			addNew:addNew,
		//	filterMachinesByName:filterMachinesByName,
			getMachinesByLab:getMachinesByLab,
			cancel:cancel,
			loadLabs:loadLabs,
			getCheckListByMachineName:getCheckListByMachineName,
			getCheckListByEquipId:getCheckListByEquipId,
			getCheckListByEquipIdAndFrequency:getCheckListByEquipIdAndFrequency
			
		});
		(function activate() {
			$scope.uploadTab=false
			$scope.selectedMachine = 'selectMachine';
			$scope.selectedMode	= 'selectFrequency';
			$scope.selectedType	=	'selectType';
			$scope.checkpointCounter = 0;
			$scope.myFile = '';
			loadMachineNames();
			loadLabs();
			
		})();

    vm.isAddFormVisible = false;

    vm.showAddForm = function() {
        vm.isAddFormVisible = true;
    };

    vm.hideAddForm = function() {
        vm.isAddFormVisible = false;
    };
	
	
	

	vm.loadChecklists = function() {
	    if (!vm.selectedMachine || !vm.selectedMachine.eqid) {
			
			console.log("Selected machine:", vm.selectedMachine);
			console.log("Equip ID:", vm.selectedMachine?.eqid);

	        console.warn("Machine not selected or equipId missing");
	        return;
	    }

	    $http.get('/checklist/listByMachineEquipId?equipId=' + vm.selectedMachine.eqid)
	        .then(function(response) {
	            vm.checklists = response.data;
	        })
	        .catch(function(error) {
	            console.error("Error loading checklist:", error);
	        });
	};


	
	// Variables
	vm.isReordering = false;
	vm.reorderChecklist = null;
	vm.newSerialNumber = null;

	// Open the reorder form
	vm.openReorderForm = function(checklist) {
	    vm.isReordering = true;
	    vm.reorderChecklist = angular.copy(checklist);
	    vm.newSerialNumber = checklist.serialNumber; // default to current
		 vm.selectedMachine = checklist.machine;
	};

	// Cancel reorder form
	vm.cancelReorder = function() {
	    vm.isReordering = false;
	    vm.reorderChecklist = null;
	    vm.newSerialNumber = null;
	};

	// Call API to reorder
	vm.saveReorder = function() {
	    const checklistId = vm.reorderChecklist.checklist_id;
	    const newPosition = vm.newSerialNumber;

	    if (!checklistId || newPosition === null || newPosition === undefined) {
	        alert("Checklist ID or new serial number is missing.");
	        return;
	    }

		$http.put('/checklist/checklistReorder/' + checklistId + '?newPosition=' + newPosition)
		    .then(function(response) {
		        alert("Reordered successfully!");

		        // ✅ Reset and refresh
		        vm.isReordering = false;
		        vm.reorderChecklist = null;
		        vm.newSerialNumber = null;

		        vm.loadChecklists(); // <-- will now use updated vm.selectedMachine.eqid
		    })
		    .catch(function(error) {
		        console.error("Reorder error:", error);
		        alert("Error while reordering: " + (error.data?.message || "Unexpected error."));
		    });

	};

	
	
	
	
	
	
	
	
	
	vm.editChecklist = editChecklist;
	vm.updateChecklist = updateChecklist;
	vm.selectedChecklist = null; // to hold the checklist being edited

	function editChecklist(checklist) {
	    vm.selectedChecklist = angular.copy(checklist);

	    // Ensure machine object exists
	    if (!vm.selectedChecklist.machine) {
	        vm.selectedChecklist.machine = { machine_id: null };
	   		 }

	    vm.isEditing = true;
	}
	vm.checkTypes = ['RANGE', 'ANY'];


	function updateChecklist() 
		
	{
	    var url = checklistUrl + "/Update/" + vm.selectedChecklist.checklist_id;

	    $http.put(url, vm.selectedChecklist)
	        .then(function(response) {
	            toastr.success("Checklist updated successfully.");

	            for (var i = 0; i < vm.checklists.length; i++) {
	                if (vm.checklists[i].checklist_id === vm.selectedChecklist.checklist_id) {
	                    vm.checklists[i].task = vm.selectedChecklist.task;
	                    vm.checklists[i].operation = vm.selectedChecklist.operation;
	                    vm.checklists[i].acceptableRange = vm.selectedChecklist.acceptableRange;
	                    vm.checklists[i].frequency = vm.selectedChecklist.frequency;
										  
						vm.checklists[i].lower_range = vm.selectedChecklist.lower_range;
						vm.checklists[i].upper_range = vm.selectedChecklist.upper_range;
						vm.checklists[i].check_unit = vm.selectedChecklist.check_unit;
						vm.checklists[i].checkType = vm.selectedChecklist.checkType;
	                    break;
	                }
	            }

	            vm.selectedChecklist = {};
	            vm.isEditing = false;
	        })
	        .catch(function(error) {
	            toastr.error("Failed to update checklist.");
	            console.error("Update error:", error);
	        });
	}

	
					
					

	vm.cancelEdit = function () {
	    vm.selectedChecklist = null;
	    vm.isEditing = false;
	};

	
	//for load labs
				     
						
						function loadLabs() {
						   
						    var url = baseUrl + "/lab/all";
						    genericFactory.getAll("", url).then(function(response) {
						        vm.labs = response.data;
						        console.log("labs fetched:", vm.labs);
						      
						    });
						}
						function getMachinesByLab(labId) {
						    if (!labId) {
						        toastr.error("Please select a lab before fetching machines.");
						        return;
						    }

						    console.log("Fetching machines for labId:", labId);

						    var url = machineUrl + "/uniqueMachines/" + labId;
						    genericFactory.getAll("", url)
						        .then(function(response) {
						            vm.machines = response.data;
						            console.log("Machines fetched:", vm.machines);
						            vm.machineNames = [...new Set(vm.machines.map(machine => machine.machine_name))];
									vm.machineEqids = [...new Set(vm.machines.map(machine => machine.eqid))];
									console.log("Machines_Names fetched:", vm.machineNames);
									console.log("Machines_Eqids fetched:", vm.machineEqids);
						            vm.filteredMachines = [];
						        })
						        .catch(function(error) {
						            console.error("Error fetching machines for lab:", error);

						            // Auto-display backend message if present
						            let errorMessage = (error && error.data && error.data.message)
						                ? error.data.message
						                : (error.statusText || "An error occurred");

						            toastr.error(errorMessage);
						        });
						}


	

			
			function getCheckListByEquipIdAndFrequency(equipmentId,frequncy){
				console.log("equipmentId : "+JSON.stringify(equipmentId))
				console.log("frequncy : "+JSON.stringify(frequncy))
			var msg=""
				 var url =checklistUrl+"/listByMachineEquipIdAndFreq?equipId="+equipmentId+"&frequency="+frequncy;
				 console.log(" url  : "+url)
				genericFactory.getAll(msg,url).then(function(response) {
				vm.checklists= response.data;
				console.log("checklists : "+JSON.stringify(vm.checklists))
								
			});
		}
		
			function getCheckListByMachineName(machineName){
				console.log("machineName : "+JSON.stringify(machineName))
			var msg=""
				 var url =checklistUrl+"/listByMachineName?machineName="+machineName;
				 console.log(" url  : "+url)
				genericFactory.getAll(msg,url).then(function(response) {
				vm.checklists= response.data;
				console.log("checklists : "+JSON.stringify(vm.checklists))
								
			});
		}
				function getCheckListByEquipId(equipId){
					console.log("equipId : "+JSON.stringify(equipId.eqid))
			var msg=""
				 var url =checklistUrl+"/listByMachineEquipId?equipId="+equipId;
				 console.log(" url  : "+url)
				genericFactory.getAll(msg,url).then(function(response) {
				vm.checklists= response.data;
				console.log("checklists : "+JSON.stringify(vm.checklists))
								
			});
		}
		function addNew(){
			$scope.addTab=true;
			$scope.uploadTab=false;
		}
		function cancel(){
			$scope.addTab=false;
			vm.addTab = false;
			   vm.checklistArray = [];
		}
		function getEqidsByLabAndMachineName () {
			if (!vm.selectedLabId || !vm.selmachineName) {
			       toastr.error("Please select both Lab and Machine Name.");
			       return;
			   }
		    var url = baseUrl + "/machine_mst/getMachinesByLabAndName?labId=" + vm.selectedLabId + "&machineName=" + vm.selmachineName;

		    $http.get(url)
		        .then(function (response) {
		            if (response.data.status === "success") {
		                vm.machines = response.data.data; // each has machine_name, eqid, etc.
		                toastr.success("Machine IDs loaded.");
		            } else {
		                vm.machines = [];
		                toastr.warning(response.data.message || "No machines found.");
		            }
		        })
		        .catch(function (error) {
		            vm.machines = [];
		            toastr.error("Error fetching machine IDs.");
		            console.error("Fetch EqIDs Error:", error);
		        });
		};

		
		function loadMachineNames() {
			machine_mstService.getMachineNames().then(function(data) {
				vm.machineNames = data;
				console.log(JSON.stringify(vm.machineNames));
			});
		}
		function getMachinesByName(machineName) {
			console.log("machine Names : "+machineName)
			var obj={};
			obj.machine_name=machineName
			var msg=""
				 var url =machineUrl+"/getGetMachinesByName";
				genericFactory.add(msg,url,obj).then(function(response) {
				vm.machines= response.data;
				console.log("machines123 : "+JSON.stringify(vm.machines))
								
			});
		}
		function showUploadTab(){
			$scope.uploadTab=true;
			$scope.addTab=false;
		}
		
		$scope.handleFileSelect = (files)=>{
			$scope.selectedFile = files[0];
			console.log("File: ", $scope.selectedFile);
		}
		
		function upload() {
		    var file = $scope.selectedFile;
		    console.log("File: ", file);

		    if (!file) {
		        toastr.error("Please select file");
		        return;
		    }

		    var url = checklistUrl + "/uploadFullChecklists";
		    console.log("URL: ", url);
		    $('.loading').show();

		    var fd = new FormData();
		    fd.append('file', file);

		    $http.post(url, fd, {
		        transformRequest: angular.identity,
		        headers: {
		            'Content-Type': undefined
		        }
		    })
		    .then(function successCallback(response) {
		        console.log("RESPONSE: " + JSON.stringify(response.data));
		        $('.loading').hide();

		        let message = response.data.message || 'Upload completed.';

		 
		        if (message.toLowerCase().includes('duplicate') || message.toLowerCase().includes('already')) {
		            toastr.warning(message, 'Partial Success', { timeOut: 1000 });
		        } else {
		            toastr.success(message, 'Successful !!', { timeOut: 1000 });
		            loadchecklists();
		        }

		        $scope.uploadTab = false;
		    }, function errorCallback(response) {
		        $('.loading').hide();
		        $scope.uploadTab = false;
		        toastr.error('Upload failed. Please try again.', 'Unsuccessful !!');
		    });

		    angular.element("input[type='file']").val(null);
		    $scope.selectedFile = null;
		}
		
		vm.downloadTemplate = function () {
		    var downloadUrl = checklistUrl +'/downloadChecklistTemplate'; 

		    window.open(downloadUrl, '_blank');
		};

		
		vm.refreshPage = function() {
    toastr.success('Checklist Uploaded Successfully...', 'Info', {
        timeOut: 10000
    });

    
    $window.location.reload();
};

		function ok(checklist) {
			debugger;
			checklistService.addchecklist(checklist).then(function() {
				$uibModalInstance.close(checklist);
			});
			loadchecklists();
			
		}
		
		function changeMode(selectedMode){
			$scope.selectedMode = selectedMode;
		}
		
		function loadMachines() {
			machine_mstService.getMachine_msts().then(function(data) {
				vm.machines = data;
//				console.log(JSON.stringify(vm.machines));
			});
		}
		
		function increment() {
			
			    $scope.checkpointCounter++;

			    if (vm.checklistArray.length === 0) {
			        vm.checklistArray.push({
			            checkpoint: "",
			            acceptableRange: "",
			            checkType: "",
			            check_unit: "",
			            lower_range: "",
			            upper_range: ""
			        });
			    } else {
			        let lastItem = vm.checklistArray[vm.checklistArray.length - 1];

			        if (
			            !lastItem.checkpoint ||
			            !lastItem.acceptableRange ||
			            !lastItem.checkType ||
			            (lastItem.checkType !== 'ANY' && (
			                lastItem.check_unit === "" ||
			                lastItem.lower_range === "" ||
			                lastItem.upper_range === ""
			            ))
			        ) {
			            toastr.error('Please enter all required values before adding a new checkpoint.');
			        } else {
			            vm.checklistArray.push({
			                checkpoint: "",
			                acceptableRange: "",
			                checkType: "",
			                check_unit: "",
			                lower_range: "",
			                upper_range: ""
			            });
			        }
			    }
			};


		vm.cancelImport = function () {
		    document.getElementById("inputExcelFile").value = null;
		    alert("Import cancelled.");
		};

		
		function decrement() {
		    if (vm.checklistArray.length > 0) {
		        vm.checklistArray.pop();
		        $scope.checkpointCounter--;
		    } else {
		        toastr.warning('No more checkpoints to remove.');
		    }
		}

		
		function submitChecklist() {
		    var arr = [];

		    if ($scope.selectedMachine === 'selectMachine') {
		        toastr.error('Please select a machine.');
		        return;
		    }
		   
		    if ($scope.selectedMode === 'selectFrequency') {
		        toastr.error('Please select a frequency.');
		        return;
		    }

		    angular.forEach(vm.checklistArray, function(item, index) {
		        if (!item.checkpoint || !item.acceptableRange || !item.checkType) {
		            toastr.error('Please fill all fields for checkpoint ' + (index + 1));
		            return;
		        }

		        let iObj = {
		            task: item.checkpoint,
		            operation: item.operation,
		            acceptableRange: item.acceptableRange,
		            checkType: item.checkType,
		            check_unit: item.check_unit,
		            lower_range: item.lower_range,
		            upper_range: item.upper_range
		        };

		        arr.push(iObj);
		    });

		    var machineObj = JSON.parse($scope.selectedMachine);

		    var payload = {
		        "machine": {
		            "machine_id": machineObj.machine_id
		        },
		        "checklist": arr,
		        "frequency": $scope.selectedMode,
		        "type": $scope.selectedType
		    };

		    console.log("Submitting payload:", JSON.stringify(payload));

		    checklistService.addchecklist(payload).then(function(response) {
		        console.log("Response:", JSON.stringify(response));

		        toastr.success('Checklist submitted successfully!', 'Success');
		        $scope.selectedMachine = 'selectMachine';
		        $scope.selectedType = 'selectType';
		        $scope.selectedMode = 'selectFrequency';

		        vm.checklistArray = [];
		        $scope.checkpointCounter = 0;

		        loadchecklists();
		        viewPopup(response);
		    });
		}


		function changeFile(file){
			console.log(file);
		}
		
		function viewPopup(data) {
			var asset = data ? data : {};
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/checklist/checklistPopup.html',
				controller : 'checklistPopupCtrl',
				controllerAs : 'vm',
				size : 'md',
				resolve : {
					items : function() {
						return asset;
					}
				}
			});

			modalInstance.result.then(function() {

			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});
		}
		
		//************************************************
		function uploadXlxs() {
			
			debugger;	
			var file = document.getElementById('fileAsset').files[0];

			var uploadUrl = checklistUrl + "/uploadFile";
			fileUpload.uploadFileToUrl(file, uploadUrl);
		
			angular.element("input[type='file']").val(null);
		}
		
		function loadchecklists() {
			checklistService.getchecklists().then(function(data) {
				vm.checklists = data;
			
			});
		}
		
	
		
		
		
		
		function delet(checklist) {
		    // Show a confirmation alert box before proceeding
		    if (confirm("Are you sure you want to delete this checklist?")) {
		        checklistService.deleteChecklist(checklist).then(function () {
		            loadchecklists();
		        });
		    }
		}


		function view(checklist) {
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/checklist/checklistModelView.html',
				controller : 'checklistModalCtrl',
				controllerAs : 'vm',
				size : 'md',
				resolve : {
					items : function() {
						return checklist;
					}
				}
			});

			modalInstance.result.then(function() {
				loadchecklists();
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});
			loadchecklists();
		}

		function add(checklist) {
			var loc = checklist ? checklist : {};
			// alert(JSON.stringify(usr));
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/checklist/checklist.html',
				controller : 'checklistController',
				controllerAs : 'vm',
				size : 'lg',
				resolve : {
					checklist : function() {
						return loc;
					}
				}
			});

			modalInstance.result.then(function() {
				loadchecklists();
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});
			loadchecklists();
		}

	}

	function checklistModalCtrl($uibModalInstance, items, $scope) {
		var vm = angular.extend(this, {
			items : items,
			ok : ok,
			cancel : cancel
		});

		(function activate() {

		})();

		// ******************************************************
		console.log(vm.items);
		function ok() {
			$uibModalInstance.close();
		}

		function cancel() {
			$uibModalInstance.dismiss('cancel');
		}
	}
	
	function checklistPopupCtrl($uibModalInstance, items, $scope, $rootScope) {
		var vm = angular.extend(this, {
			items : items,
			ok : ok,
			cancel : cancel
		});
		console.log(items);
		(function activate() {

		})();

		// ******************************************************

		function ok() {
			$uibModalInstance.close();
		}

		function cancel() {
			/*$rootScope.$broadcast('done', {
                str: 'something'
            });*/
			$uibModalInstance.dismiss('cancel');
		}
	}

	
})();

