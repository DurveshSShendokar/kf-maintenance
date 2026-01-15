(function() {
	'use strict';
	angular.module('myApp.machine_mst').controller('Machine_mstController', Machine_mstController)
			.controller('Machine_mstModalCtrl', Machine_mstModalCtrl);
	Machine_mstController.$inject = [ '$state', 'machine_mstService', 'categoryService', '$uibModal', '$log',
			'$scope', 'toastr','ApiEndpoint','$http','genericFactory', '$sce', '$timeout' ];
	Machine_mstModalCtrl.$inject = [ '$uibModalInstance', 'items', '$scope' ];
	 function Machine_mstController($state, machine_mstService, categoryService, $uibModal, $log, $scope, toastr,ApiEndpoint,$http,genericFactory, $sce,$timeout) {
			var uploadsUrl = staticUrl+"/upload";
			var machineUrl = staticUrl+"/machine_mst";
			 const baseUrl = ApiEndpoint.url;
		
		var vm = angular.extend(this, {
			machine_msts : [],
			categories : [],
			view : view,
			add : add,
			 assignSpares: [],
			 showAddSpareForm: showAddSpareForm,
            saveSpare: saveSpare,
             remove: remove,
              loadassignSpears: loadassignSpears,
              resetFormFields: resetFormFields,
               buttonsVisible: true ,
            isQrVisible: false  ,
            isEditing: false ,
			cancel:cancel,
			delet : delet,
			ok : ok,
			confirmDelete: confirmDelete,
             cancelDelete:cancelDelete,
			addNew:addNew,
			viewQr:viewQr,
			print:print,
			upload:upload,
			uploadNew:uploadNew,
			downloaddocument: downloaddocument,
			getMachinesByName:getMachinesByName,
			getMachinesByLocation:getMachinesByLocation,
			 saveDocument: saveDocument,
			  ShowAddDocumentTab: ShowAddDocumentTab,
			removedocument: removedocument,
            loadassignSpears: loadassignSpears,
            loadUserDocument: loadUserDocument,
            toggleStatus: toggleStatus,
			openDocument: openDocument
		});

		(function activate() {
			$scope.machine_mst = {};
			 $scope.spareData = {}; 
		     $scope.documentData = {}; 
			loadCategories();
			loadMachineNames()
			loadLocationNames()
			loadSpares();
			$scope.addNewTab=false;
			$scope.viewQrTab=false;
			$scope.uploadTab=false;
			$scope.qrcodeData="Quipment";
			$scope.mutilpleQrCodeData="test"
			$scope.addSpareTab = false;
			 $scope.addDocumentTab = false; 
			
		})();
		
		
					/*Select Single Machines*/
					$scope.selectQR = function (index) {
						
						console.log("index ",index)
						console.log("MACHINE ",JSON.stringify(vm.machine_msts[index]))
							    vm.machine_msts[index].check = !vm.machine_msts[index].check;
							    $scope.selectedDataCounter = vm.machine_msts.filter(machine => machine.check).length;
							    vm.selectAllChk = $scope.selectedDataCounter === vm.machine_msts.length;
							};
							
							
					/*Select All machines*/
					$scope.selectAllTable = function () {
							    angular.forEach(vm.machine_msts, function (machine) {
							        machine.check = vm.selectAllChk;
							    });	    
							    $scope.selectedDataCounter = vm.selectAllChk ? vm.machine_msts.length : 0;
							};
		
					/*Printing Multiple QR codes*/			
					$scope.multiPrintCanvas = function () {
					    var containt = {};
					    containt.title = "Mandatory";
					    var windowContent = '';
					    var count = 0;
					    for (var i = 0; i < vm.machine_msts.length; i++) {
					        if (vm.machine_msts[i].check) {
								console.log("MACHINE      11  "+JSON.stringify(vm.machine_msts[i]))
					            var dataUrl = document.getElementById('anycanvas' + i).toDataURL();	
								console.log("dataUrl      11  "+JSON.stringify(dataUrl))           
								windowContent += '<div style="page-break-after: always; width: 100%; height: 250px; display: flex; flex-direction: column; justify-content: center; align-items: center; text-align: center; padding-top: 30px; padding-bottom: 30px; margin-bottom: 20px;">' +
								                    '<img src="' + dataUrl + '" style="width: 150px; height: 150px;">' +
								                    '<br/>' +
								                    '<span style="font-family: Arial; font-size: 25px;">' +
								                        vm.machine_msts[i].eqid +
								                    '</span>' +
								                 '</div>';
					            count++;
					        }
					    }
					    if (count == 0) {
					        containt.massage = "Please select at least one QR code.";
					        genericFactory.showAlert(containt);
					    } else {
					        var popupWindow = window.open('', '_blank', 'width=600,height=700,scrollbars=no,menubar=no,toolbar=no,location=no,status=no,titlebar=no');
					        popupWindow.document.open();
					        popupWindow.document.write('<html><head><style>@page { margin: 10px; }</style></head><body onload="window.print()">' + windowContent + '</body></html>');
					        popupWindow.document.close();
					        savePrintedCode();
					    }
					};


		
		/* pagination & per_page_records*/
					
						vm.currentPage = 0;  
						vm.pageSize = 10;    
						vm.totalPages = 0;   
						vm.pageWindowSize = 5; 
						vm.pageSizeOptions = [10, 25, 50, 100, 250, 1000];  
						vm.startPage = 0; 
						vm.searchText = "";
						vm.loadMachine_msts = function (page) {
						    vm.currentPage = page;

						    var url = machineUrl + "/PagelistSearch?page=" + vm.currentPage + "&size=" + vm.pageSize + "&searchText=" + encodeURIComponent(vm.searchText || "");

						    genericFactory.getAll("", url).then(function (response) {
						        vm.machine_msts = response.data.machines;
						        vm.totalPages = response.data.totalPages;

						        // Adjust startPage window
						        if (vm.currentPage >= vm.startPage + vm.pageWindowSize) {
						            vm.startPage = vm.currentPage - 1;
						        }
						        if (vm.currentPage < vm.startPage) {
						            vm.startPage = vm.currentPage;
						        }
						    });
						};

						// Called when typing in the search input
						vm.searchMachines = function () {
						    vm.currentPage = 0;
						    vm.loadMachine_msts(vm.currentPage);
						};

						vm.nextPage = function() {
						    if (vm.currentPage < vm.totalPages - 1) {
						        vm.loadMachine_msts(vm.currentPage + 1);
						    }
						};		
						vm.updatePageSize = function () {
						    vm.currentPage = 0; 
						    vm.loadMachine_msts(vm.currentPage);
						};
						vm.prevPage = function() {
						    if (vm.currentPage > 0) {
						        vm.loadMachine_msts(vm.currentPage - 1);
						    }
						};
						vm.getVisiblePages = function() {
						    let endPage = Math.min(vm.startPage + vm.pageWindowSize, vm.totalPages);
						    return new Array(endPage - vm.startPage).fill(0).map((_, i) => vm.startPage + i);
						};
						vm.loadMachine_msts(0);
			
			
						
						
						
						vm.deactivateMachine = function(machineId) {
						    if (confirm("Are you sure you want to deactivate this machine?")) {
						        $http.put('/machine_mst/deactivateMachine/' + machineId)
						            .then(function(response) {
						                if (response.data.status === "success") {
						                    alert(response.data.message);
						                    vm.loadMachine_msts(0); // Refresh the list
						                } else {
						                    alert("Error: " + response.data.message);
						                }
						            }, function(error) {
						                if (error.data && error.data.message) {
						                    alert("Error: " + error.data.message);
						                } else {
						                    alert("An unexpected error occurred while deactivating the machine.");
						                }
						                console.error(error);
						            });
						    }
						};

						
		
		
					// ******************************************************
					
					function getMachinesByName(machineName) {
						console.log("machineName  "+JSON.stringify(machineName));
			
						
						if($scope.selectedLocation==undefined){
								var msg=""
							 var url =machineUrl+"/listByName?machineName="+machineName;
							 console.log("url  "+url);
							genericFactory.getAll(msg,url).then(function(response) {
							vm.machine_msts= response.data;
							console.log("machine_msts : "+JSON.stringify(vm.machine_msts))
											
						});
						}else{
							var msg=""
							 var url =machineUrl+"/getMachineListByLocationAndMachine?location="+$scope.selectedLocation+"&machineName="+machineName;
							 console.log("url  "+url);
							genericFactory.getAll(msg,url).then(function(response) {
							vm.machine_msts= response.data;
							console.log("machine_msts : "+JSON.stringify(vm.machine_msts))
											
						});
						}
						
					
					}
					
					function getMachinesByLocation(location) {
						console.log("Name  "+$scope.selmachineName);
						console.log("location  "+$scope.selectedLocation);
						if($scope.selmachineName==undefined){
							var msg=""
							 var url =machineUrl+"/getMachineListByLocation?location="+location;
							 console.log("url  "+url);
							genericFactory.getAll(msg,url).then(function(response) {
							vm.machine_msts= response.data;
							console.log("machine_msts : "+JSON.stringify(vm.machine_msts))
											
						});
						}else{
							var msg=""
							 var url =machineUrl+"/getMachineListByLocationAndMachine?location="+location+"&machineName="+$scope.selmachineName;
							 console.log("url  "+url);
							genericFactory.getAll(msg,url).then(function(response) {
							vm.machine_msts= response.data;
							console.log("machine_msts : "+JSON.stringify(vm.machine_msts))
											
						});
						}
						
					}
						
					function  cancel () {
						$scope.addNewTab=false;
						$scope.viewQrTab=false;
						 $scope.addSpareTab = false;
						  vm.buttonsVisible = true;
						   $scope.addDocumentTab = false; 
					}
					function addNew(){
					
						$scope.addNewTab=true;
						$scope.viewQrTab=false;
						$scope.uploadTab=false;
						 $scope.addSpareTab = false;
						  resetFormFields();
			            vm.buttonsVisible = true;
			             $scope.addDocumentTab = false; 
					}
					function upload(){
						$scope.uploadTab=true;
						$scope.addNewTab=false;
						$scope.viewQrTab=false;
						$scope.addSpareTab = false;
						vm.buttonsVisible = true;
						 $scope.addDocumentTab = false; 
					}
					function viewQr(machine){
						$scope.viewQrTab=true;
						$scope.addNewTab=false;
						$scope.uploadTab=false;
						$scope.addSpareTab = false;
						$scope.qrcodeData=machine.eqid;
						 $scope.addDocumentTab = false; 
						console.log("hello Log : "+JSON.stringify(machine))
					}
			
					$scope.handleFileSelect = (files)=>{
						$scope.selectedFile = files[0];
						console.log("File: ", $scope.selectedFile);
					}
					
		
				function loadMachineNames() {
						machine_mstService.getMachineNames().then(function(data) {
							vm.machineNames = data;
							console.log(JSON.stringify(vm.machineNames));
						});
					}
					
				function uploadNew(){
						var file = $scope.selectedFile;
						console.log("File: ", file);
			
						if (!file) {
							toastr.error("Please select file");
							return;
						}
						   var url = machineUrl + "/upload-excel";
			
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
								var text=response.data.resmessage
								console.log("RESPONCE  "+JSON.stringify(response.data))
								$('.loading').hide();
								if(response.data.code==200){
									toastr.success('Uploaded....', 'Succesful !!',{ timeOut: 10000 });	
									
									loadMachine_msts();
								}else{
									toastr.error('Upload....', 'UnSuccesful !!');
								}
								$scope.uploadMediaPrepTab= false;
							}, function errorCallback(response) {
						    	$('.loading').hide();
								$scope.uploadMediaPrepTab= false;
								toastr.error('Upload....', 'UnSuccesful !!');
							});
							angular.element("input[type='file']").val(null);
							$scope.selectedFile = null;
					}
					
				 function print(){
							var windowContent = '';
							console.log("PRIting ..........")
							var canvas = document.getElementById('printQR').querySelector('canvas');
							var dataURL = canvas.toDataURL();
							console.log("dataUrl  :: " + dataURL)
							
							console.log("SIZE  IN 12 50 cut ")
							
							windowContent += '<div style="padding: 5px;display: inline-block;solid;margin-left:10px;"><div class="col-xs-6"  style="display: inline-block;"><img src="' + dataURL + '" style="margin-left:25px;"></div></div><div ><div class="col-xs-6"  style=""><span style="font-family: Arial; font-size: 25px;margin-top:-10%"><br/>'+$scope.qrcodeData+'</span></br> <span style=" font-family: Arial;font-size: 25px;padding:4px;margin-bottom:20px"></span><br><br><br><br><span margin-top:35px;></span></div></div>';
				
				
				
				
							var popupWinindow = window.open('', '_blank', 'width=600,height=700,scrollbars=no,menubar=no,toolbar=no,location=no,status=no,titlebar=no');
							popupWinindow.document.write('<html><body onload="window.print()">' + windowContent + '</html>');
							popupWinindow.document.write('<style> @page {  margin: 15;} </style>');
							popupWinindow.document.close();
						}
						
				function loadLocationNames() {
					
							var msg=""
								 var url =machineUrl+"/getMachineLocationslist";
								genericFactory.getAll(msg,url).then(function(response) {
								vm.locations= response.data;
								console.log("locations : "+JSON.stringify(vm.locations))
												
							});
							
						}
			    function loadCategories() {
							
							categoryService.getCategorys().then(function(data) {
								vm.categories = data;
								
							});
						}
				
				
							let selectedmachine_mst = null;
			 function delet(machine_mst) {
				    		selectedmachine_mst = machine_mst;
				    		vm.showModal = true; 
													}
				
				function confirmDelete() {
				    if (!selectedmachine_mst) return;
				    var url = baseUrl + "/machine_mst/delete/" + selectedmachine_mst.machine_id;
				    $http.delete(url)
				        .then(function(response) {
				            toastr.success('Deleted machine successfully!');
				            loadMachine_msts();
				        })
				        .catch(function(error) {
				            toastr.error('Error deleting machine');
				            console.error("Error deleting machine:", error);
				        })
				        .finally(function() {
				            vm.showModal = false; 
				        });
				}
				
					function cancelDelete() {
				    		vm.showModal = false; 
											}
		
					function ok(machine_mst) {
						if(!machine_mst.machine_name || machine_mst.machine_name == ''){
							toastr.error('Please enter machine name');
							return;
						}
						if(!machine_mst.category || !machine_mst.category.cat_id){
							toastr.error('Please select category');
							return;
						}
						
						if(!machine_mst.eqid || machine_mst.eqid == ''){
							toastr.error('Please enter equipment id name');
							machine_mst.eqid = null;
							return;
						}
						
						if(!machine_mst.model || machine_mst.model == ''){
							toastr.error('Please enter model name');
							return;
						}
						if(!machine_mst.make || machine_mst.make == ''){
							toastr.error('Please enter make name');
							return;
						}
						if(!machine_mst.srNo || machine_mst.srNo == ''){
							toastr.error('Please enter sr no name');
							return;
						}
						if(!machine_mst.capacity || machine_mst.capacity == ''){
							toastr.error('Please enter capacity name');
							return;
						}
						if(!machine_mst.location || machine_mst.location == ''){
							toastr.error('Please enter location name');
							return;
						}
						
						if(!machine_mst.type || machine_mst.type == ''){
							toastr.error('Please select type');
							return;
						}

							machine_mstService.addMachine_mst(machine_mst).then(function(response){
								console.log("Responce "+JSON.stringify(response))
								if(response.code==500){
									toastr.error(response.message)
								}else{
									toastr.success(response.message);
								}
								$scope.machine_mst = {};
								loadMachine_msts();
								$scope.addNewTab=false;
							});
						}

						function view(machine_mst) {
							var modalInstance = $uibModal.open({
								animation : true,
								ariaLabelledBy : 'modal-title',
								ariaDescribedBy : 'modal-body',
								templateUrl : 'templates/machine_mst/machine_mstModelView.html',
								controller : 'Machine_mstModalCtrl',
								controllerAs : 'vm',
								size : 'md',
								resolve : {
									items : function() {
										return machine_mst;
									}
								}
							});
				
							modalInstance.result.then(function() {
								
							}, function() {
								$log.info('Modal dismissed at: ' + new Date());
							});
						}
				
						function add(machine_mst) {
							$scope.addNewTab=true;
							$scope.machine_mst = Object.assign({}, machine_mst);
							$scope.machine_mst.type = $scope.machine_mst.type.toString();
							
							setTimeout(function(){
								window.scroll({
									  top: 0, 
									  left: 0, 
									  behavior: 'smooth' 
								});
							},0);
							
							
						}

							  function resetFormFields() {
										            $scope.machine_mst = {};
										            $scope.spareData = {}; 
										            $scope.documentData = {};   
										        }
				
						        vm.edit = function() {
										    vm.isEditing = true; 
										};
										
								vm.cancelEdit = function() {
										    vm.isEditing = false; 
										};
										
										
								vm.toggleQrView = function() {
										    vm.isQrVisible = !vm.isQrVisible; 
										};
				
						 function remove(assignSpareId) 
				 	     {           
				            if (!assignSpareId) {
				                toastr.error('Invalid Spare Assign ID');
				                console.error("Invalid Spare Assign ID:", assignSpareId);
				                return;
				            }
				
				            var url = machineUrl + "/" + assignSpareId + "/delete"; 
				            console.log("Delete URL:", url);
				            $http.delete(url)
				                .then(function(response) {
				                    toastr.success('Spare assignment deleted successfully!');
				                
				                  loadassignSpears($scope.spareData.machineId); 
				                })
				                .catch(function(error) {
				                    toastr.error('Error deleting spare assignment');
				                    console.error("Error deleting spare assignment:", error);
				                });
				        }
				
					 function loadassignSpears(machineId) 
				 	 {
				            var url = machineUrl + "/fetch/" + machineId;     
				           	 $http.get(url)
				                .then(function(response) {
				                    vm.assignSpares = response.data;
				                    console.log("Fetched assignSpares:", vm.assignSpares);
				                })
				                .catch(function(error) {
				                    toastr.error('Error fetching spare assignments');
				                    console.error("Error fetching spare assignments:", error);
				                });
				  		 }		
								 function resetFormFields() {
							            $scope.machine_mst = {};
							            $scope.spareData = {}; 
							            
							        }
				         				        
				     // Function to show the spare form with the machine's equipment ID
				      function showAddSpareForm(machine) {
							$scope.addSpareTab = true;
							$scope.viewQrTab = false;
							
							$scope.spareData = {
								machineId: machine.machine_id,
								eqId: machine.eqid,
								maintspare_ids: []
							};
							vm.buttonsVisible = false;
							loadassignSpears(machine.machine_id);
						}
										        
										        
					 function loadSpares() {
						    var url = baseUrl + "/maintspares/list";
						    $http.get(url)
						        .then(function(response) {
						            vm.spares = response.data;
						            console.log("Fetched spares:", vm.spares);
						        })
						        .catch(function(error) {
						            console.error("Error fetching spares:", error);
						        });
						}			        
				        
				        
				      function saveSpare(spareData) {
							if (!spareData || !spareData.machineId || spareData.maintspare_ids.length === 0) {
								toastr.error("Missing equipment ID or spare ID");
								return;
							}
					console.log("spareData" + spareData);
							var url = baseUrl + "/machine_mst/" + spareData.machineId + "/Machinespares";
							var spares = spareData.maintspare_ids.map(id => ({ maintspare_id: id }));
							
							$http.post(url, spares)
								.then(function(response) {
									if (response.data.code === 200) {
										toastr.success('Spare assigned successfully');
										$scope.addSpareTab = false;
										vm.buttonsVisible = true;
									} else {
										toastr.error('Error assigning spare');
									}
								})
								.catch(function(error) {
									toastr.error('Error assigning spare');
								});
						}
				
				
						//delete function for user manual (document)		
									 	function removedocument(upload_id) 
									 	     {           
									            if (!upload_id) {
									                toastr.error('Invalid Document  ID');
									                console.error("Invalid Document  ID:", upload_id);
									                return;
									            }
							
							            var url = machineUrl +"/"+ upload_id + "/document_delete"; 
							            console.log("Delete URL:", url);
							            $http.delete(url)
							                .then(function(response) {
							                    toastr.success('Document  deleted successfully!');
							                
							                  loadUserDocument($scope.documentData.machineId); 
							                })
							                .catch(function(error) {
							                    toastr.error('Error deleting Document ');
							                    console.error("Error deleting Document :", error);
							                });
							        }
							        
							        
							        //function to download the document
								        function downloaddocument(upload_id)	{			
										
										    var downloadUrl = machineUrl+'/downloadUserManual/' + upload_id;
												 console.log("download URL:", downloadUrl);
										   
										    $http({
										        method: 'GET',
										        url: downloadUrl,
										        responseType: 'blob'  
										    }).then(function(response) {
										        
										        if (response.data) {
										        
										            var file = new Blob([response.data], { type: response.headers('Content-Type') });
										            var fileName = response.headers('Content-Disposition').split('filename=')[1].replace(/\"/g, "");
										
										          
										            var downloadLink = document.createElement('a');
										            var url = window.URL.createObjectURL(file);
										            downloadLink.href = url;
										            downloadLink.download = fileName;
										
										         
										            document.body.appendChild(downloadLink);
										            downloadLink.click();
										
										           
										            document.body.removeChild(downloadLink);
										            window.URL.revokeObjectURL(url);
										        } else {
										            alert("File not found or inactive");
										        }
										    }, function(error) {
										 
										        if (error.status === 403) {
										            alert("You do not have permission to download this file.");
										        } else if (error.status === 404) {
										            alert("File not found.");
										        } else {
										            alert("An error occurred while downloading the file.");
										        }
										    });
										};
							        
							        
						
								
								function toggleStatus(document) {
									   
									    var newStatus = document.active === 1 ? 0 : 1;
									    var url = machineUrl + "/updateManualStatus/" + document.upload_id + "?active=" + newStatus;  
									    $http.put(url)
									        .then(function(response) {
									            if (response.data.code === 200) {
									               
									                document.active = newStatus;  
									                var statusMessage = newStatus === 1 ? 'Status is now Active' : 'Status is now Inactive';
									                toastr.success(statusMessage); 
									            } else {
									                toastr.error(response.data.message);
									            }
									        })
									        .catch(function(error) {
									            toastr.error('Error updating manual status');
									            console.error("Error updating manual status:", error);
									        });
									}
				
								//load user manual (document) in table
								function loadUserDocument(machineId) {
								    var url = machineUrl + "/fetchUploadedDocuments/" + machineId;     
								    $http.get(url)
								        .then(function(response) {
								            vm.loadDocuments = response.data;
								            if (vm.loadDocuments.length === 0) {
								                
								                toastr.warning('No documents found for this machine.');
								            }
								            console.log("Fetched loadDocuments:", vm.loadDocuments);
								        })
								        .catch(function(error) {
								            console.error("Error fetching load documents:", error);
								        });
								}
				
								//for view function
									function openDocument(upload_id) {
										    console.log("Open document with upload ID:", upload_id);
										
										    fetch(machineUrl + "/viewUploadedDocument/" + upload_id, {
										        method: 'GET',
										        headers: {
										            'Accept': 'application/pdf, application/vnd.openxmlformats-officedocument.wordprocessingml.document, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
										        },
										    })
										    .then(response => {
										        if (response.ok) {
										            return response.blob();
										        } else {
										            throw new Error("Document not found");
										        }
										    })
										    .then(blob => {
										        const fileType = blob.type;
										
										        
										        const url = window.URL.createObjectURL(blob);
										
										      
										        if (fileType === 'application/pdf') {
										            // Directly render PDF in iframe
										            vm.documentUrl = $sce.trustAsResourceUrl(url);
										        } else if (fileType === 'application/vnd.openxmlformats-officedocument.wordprocessingml.document' || 
										                   fileType === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet') {
										            // Use Google Docs Viewer or Microsoft Office Viewer
										            const encodedUrl = encodeURIComponent(url);
										            vm.documentUrl = $sce.trustAsResourceUrl(`https://docs.google.com/viewer?url=${encodedUrl}&embedded=true`);
										        } else {
										            toastr.error("Unsupported document format");
										            throw new Error("Unsupported document format");
										        }
										
										        
										        openDocumentModal();
										    })
										    .catch(error => {
										        toastr.error("Document not available");
										        console.error(error);
										    });
										}
										
										// Function to open the document modal
										function openDocumentModal() {
										    var modalInstance = $uibModal.open({
										        templateUrl: 'documentModalContent.html',
										        controller: function ($scope, $uibModalInstance, documentUrl) {
										            $scope.documentUrl = documentUrl;
										
										            $scope.close = function () {
										                $uibModalInstance.dismiss('cancel');
										            };
										        },
										         windowClass: 'modal-lg',
										        resolve: {
										            documentUrl: function () {
										                return vm.documentUrl;
										            }
										        }
										    });
										}
				
									$scope.handleFileSelect = (files) => {
										    if (files.length > 0) {
										        $scope.selectedFile = files[0]; 
										        console.log("File: ", $scope.selectedFile);
										    } else {
										        $scope.selectedFile = null; 
										    }
										};
										
										
								// Function to show the document upload form with the asset's equipment ID
									function ShowAddDocumentTab(machine) {
											    $scope.addSpareTab = false;
											    $scope.viewQrTab = false;
											    $scope.addDocumentTab = true; 
											
											    $scope.documentData = {
											        machineId: machine.machine_id, 
											        equipmentId: machine.eqid, 
											        machineNames: machine.machine_name
											       // userId: vm.userId   
											       
											    };
											 vm.loadDocuments = []; 
											    vm.buttonsVisible = false;
											      loadUserDocument( $scope.documentData.machineId);
											}		
										
										
								// Function to save the uploaded document
									function saveDocument() {
										 const files = $scope.selectedFile;
											console.log("File: ", files);
											if (!files) {
												toastr.error("Please select file");
												return;
											}
										    var url = machineUrl + "/uploadUserManual"; 
										    var fd = new FormData();
										    fd.append('files', files);
										    fd.append('machine_name', $scope.documentData.machineNames); 
										    console.log("Upload URL:", url);										    
										    $('.loading').show(); 										    
										    $http.post(url, fd, {
										        transformRequest: angular.identity,
										        headers: {
										            'Content-Type': undefined 
										        }
										    })
										    .then(function successCallback(response) {
										        $('.loading').hide(); 
										        console.log("Upload response:", response.data);
										        
										        if (response.data.code === 200) {
										            toastr.success('Documents uploaded successfully!', 'Success!', { timeOut: 10000 });
										            $scope.addDocumentTab = false; 
										        } else {
										            toastr.error('Document upload failed: ' + response.data.message, 'Unsuccessful!');
										        }
										    }, function errorCallback(response) {
										        $('.loading').hide();
										        toastr.error('Document upload failed', 'Unsuccessful!');
										        console.error("Error uploading document:", response);
										    });
										    angular.element("input[type='file']").val(null);
										    $scope.selectedFile = null;
										}
					}																        
																		        

				function Machine_mstModalCtrl($uibModalInstance, items, $scope) {
							var vm = angular.extend(this, {
								items : items,
								ok : ok,
								cancel : cancel
							});
					
							(function activate() {
					
							})();
							function ok() {
								$uibModalInstance.close();
							}
					
							function cancel() {
								$uibModalInstance.dismiss('cancel');
							}
						}


})();
