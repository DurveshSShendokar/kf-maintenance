

(function() {
    'use strict';

    angular.module('myApp.AssetInventory').controller('AssetInventoryController', AssetInventoryController);

    AssetInventoryController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr', 'genericFactory', '$uibModal', '$sce', '$window'];

    function AssetInventoryController($scope, ApiEndpoint, $http, toastr, genericFactory, $uibModal, $sce, $window) {
        const baseUrl = ApiEndpoint.url;
		var assetUrl = staticUrl+"/assetInventory";
        var vm = angular.extend(this, {
            assets: [],
            spares: [],
            categories: [],//
             assignSpares: [],
            add: add,
            showAddSpareForm: showAddSpareForm,
            ShowAddDocumentTab: ShowAddDocumentTab,
            saveSpare: saveSpare,
            saveDocument: saveDocument,
            delet: delet,
            remove: remove,
            ok: ok,
             removedocument: removedocument,
            loadassignSpears: loadassignSpears,
            loadUserDocument: loadUserDocument,
            downloaddocument: downloaddocument,
            toggleStatus: toggleStatus, 
            addNew: addNew,
             confirmDelete: confirmDelete,
             cancelDelete:cancelDelete,
            cancel: cancel,//
            resetFormFields: resetFormFields,
            viewQr:viewQr,
            uploadNew: uploadNew,
			upload:upload,
             showChooseOption: true,
            export: exportExcel,
            print:print ,
            buttonsVisible: true ,
            isQrVisible: false  ,
            isEditing: false ,
          //	 userId: 1,
           	openDocument: openDocument
           
           
        });

		        (function activate() 
		        	{
		            $scope.assetInventory = {};
		            $scope.spareData = {}; 
		            $scope.documentData = {}; 
		            loadAssets();
		            loadCategories();
		            loadLocations();
		            loadLabs();
		            loadSpares();
		             vm.showModal = false;
		            $scope.uploadTab=false;
		            $scope.qrcodeData="AssetID";
					$scope.mutilpleQrCodeData="test"
		            $scope.viewQrTab=false;
					$scope.addSpareTab = false;
		            $scope.addNewTab = false;
		            $scope.addDocumentTab = false; 
		        })();
            
		        function insertLineBreaks(str, everyNChars) {
					  const regex = new RegExp(`(.{1,${everyNChars}})`, 'g');
					  return str.match(regex).join('\n');
				}
				
				
				
				/*##########################################################*/
				
				
				
				
				/*Select Single Machines*/
							$scope.selectQR = function (index) {
									    vm.assets[index].check = !vm.assets[index].check;
									    $scope.selectedDataCounter = vm.assets.filter(machine => machine.check).length;
									    vm.selectAllChk = $scope.selectedDataCounter === vm.assets.length;
									};
									
									
							/*Select All machines*/
							$scope.selectAllTable = function () {
									    angular.forEach(vm.assets, function (machine) {
									        machine.check = vm.selectAllChk;
									    });	    
									    $scope.selectedDataCounter = vm.selectAllChk ? vm.assets.length : 0;
									};

							/*Printing Multiple QR codes*/			
							$scope.multiPrintCanvas = function () {
							    var containt = {};
							    containt.title = "Mandatory";
							    var windowContent = '';
							    var count = 0;
							    for (var i = 0; i < vm.assets.length; i++) {
							        if (vm.assets[i].check) {
							            var dataUrl = document.getElementById('anycanvas' + i).toDataURL();	           
										windowContent += '<div style="page-break-after: always; width: 100%; height: 250px; display: flex; flex-direction: column; justify-content: center; align-items: center; text-align: center; padding-top: 30px; padding-bottom: 30px; margin-bottom: 20px;">' +
										                    '<img src="' + dataUrl + '" style="width: 150px; height: 150px;">' +
										                    '<br/>' +
										                    '<span style="font-family: Arial; font-size: 25px;">' +
										                        vm.assets[i].equipmentId +
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


							// Cancel Upload function
							vm.cancelUpload = function () {
							    $scope.uploadTab=false;
							    toastr.info("Upload cancelled.");
							    $scope.selectedFile = null;
							    angular.element("input[type='file']").val(null);
							};

				/* pagination & per_page_records*/
							
								vm.currentPage = 0;  
								vm.pageSize = 5;    
								vm.totalPages = 0;   
								vm.pageWindowSize = 5; 
								vm.pageSizeOptions = [5,10, 25, 50, 75, 100];  
								vm.startPage = 0; 
					    vm.load_Assets = function(page) {
								    vm.currentPage = page;
								    var url = assetUrl + "/Pagelist?page=" + vm.currentPage + "&size=" + vm.pageSize;
					
								    genericFactory.getAll("", url).then(function(response) {
								        vm.assets = response.data.content; 	
								        vm.totalPages = response.data.totalPages; 	
								        if (vm.currentPage >= vm.startPage + vm.pageWindowSize) {
								            vm.startPage = vm.currentPage - 1;
								        }
								        if (vm.currentPage < vm.startPage) {
								            vm.startPage = vm.currentPage;
								        }
								        console.log("Assets:", vm.assets);
								        console.log("Total Pages:", vm.totalPages);
								    });
								};
								vm.nextPage = function() {
								    if (vm.currentPage < vm.totalPages - 1) {
								        vm.load_Assets(vm.currentPage + 1);
								    }
								};		
								vm.updatePageSize = function () {
								    vm.currentPage = 0; 
								    vm.load_Assets(vm.currentPage);
								};
								vm.prevPage = function() {
								    if (vm.currentPage > 0) {
								        vm.load_Assets(vm.currentPage - 1);
								    }
								};
								vm.getVisiblePages = function() {
								    let endPage = Math.min(vm.startPage + vm.pageWindowSize, vm.totalPages);
								    return new Array(endPage - vm.startPage).fill(0).map((_, i) => vm.startPage + i);
								};
								vm.load_Assets(0);
					
					

				
			/*##########################################################3*/	
				
				
			//for load assets
				        function loadAssets() {
				            var url = baseUrl + "/assetInventory/all";
				            $http.get(url)
				                .then(function(response) {
				                    vm.assets = response.data;
				                    console.log("Fetched assets:", vm.assets);
				                })
				                .catch(function(error) {
				                    console.error("Error fetching assets:", error);
				                });
				        }
				
				
				
				
				
			   // for print QR code function
			        function print()
			          {
						var windowContent = '';
						console.log("PRIting ..........")
						var canvas = document.getElementById('printQR').querySelector('canvas');
						var dataURL = canvas.toDataURL();
						var  qrcodeData=insertLineBreaks($scope.qrcodeData, 21)
						console.log("dataUrl  :: " + dataURL)
						
						console.log("SIZE  IN 12 50 cut ")
						
						windowContent += '<div style="padding: 5px;display: inline-block;solid;margin-left:10px;"><div class="col-xs-6"  style="display: inline-block;"><img src="' + dataURL + '" style="margin-left:25px;"></div></div><div style="display: inline-block; solid;"><div class="col-xs-6"  style="display: inline-block;"><span style="font-family: Arial; font-size: 25px;margin-top:-10%"></span></br>    <span style=" font-family: Arial;font-size: 25px;padding:4px;margin-bottom:20px"></span><br><br><br><br><span margin-top:35px;></span></div></div><br/	><b><span style="font-size: 30px;">'+ qrcodeData+'</span></b>';
			
			
			
			
						var popupWinindow = window.open('', '_blank', 'width=600,height=700,scrollbars=no,menubar=no,toolbar=no,location=no,status=no,titlebar=no');
						popupWinindow.document.write('<html><body onload="window.print()">' + windowContent + '</html>');
						popupWinindow.document.write('<style> @page {  margin: 15;} </style>');
						popupWinindow.document.close();
						
					}
					
								
					function viewQr (asset){
						$scope.qrcodeData=asset.equipmentId;
						setTimeout(function() {
					     $scope.viewQrTab=true;
								$scope.addNewTab=false;
								$scope.uploadTab=false;
								 $scope.addDocumentTab = false; 
								  $scope.addSpareTab = false;
								 
						}, 200);
						
					
					}
				        function cancel() {
				            $scope.addNewTab = false;
				            $scope.addSpareTab = false;
				             resetFormFields();
				              vm.uploadTab = false;
				             vm.buttonsVisible = true;
				              $scope.addDocumentTab = false; 
				              
				        }
    
				        function upload(){
							$scope.uploadTab=true;
							 $scope.showChooseOption = true;
							$scope.addNewTab=false;
							$scope.viewQrTab=false;
							$scope.addSpareTab = false;
							 $scope.addDocumentTab = false; 
							vm.buttonsVisible = true;
				
						}
				
				        function addNew() {
				            $scope.addNewTab = true;
				             $scope.addSpareTab = false;
				            $scope.uploadTab=false;
				             $scope.viewQrTab=false;
				              $scope.addDocumentTab = false; 
				            resetFormFields();
				            vm.buttonsVisible = true;
				        }
        
				        	// Set to true when edit is clicked
				       		 vm.edit = function() {
							    vm.isEditing = true; 
							};
							// Set to false to cancel editing
							vm.cancelEdit = function() {
							    vm.isEditing = false; 
							};
				
							 // Toggle QR view state
							vm.toggleQrView = function() {
							    vm.isQrVisible = !vm.isQrVisible;
							};



			////////////////////////////////////////////////////////////////////////////				        
											// AssetInventory 
				////////////////////////////////////////////////////////////////////////////	



			  vm.downloadTemplate = function() {
        
        $http({
            method: 'GET',
            url: '/assetInventory/download-template',
            responseType: 'blob' // Important for downloading files
        }).then(function(response) {
            // Create a blob from the response data
            var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
            var link = document.createElement('a');
            link.href = window.URL.createObjectURL(blob);
            link.download = 'AssetTemplate.xlsx'; // Specify the filename for download
            link.click(); // Simulate click to trigger download
        }, function(error) {
            console.error('Error downloading the template:', error);
        });
       };



		  // for export excel function 
		       function exportExcel() {
		            var url = assetUrl + "/exportExcel";
		
		            $http.get(url, { responseType: 'blob' })
		                .then(function(response) {
							  console.log("Export response data:", response.data);
		                    var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
		                    var downloadLink = document.createElement('a');
		                    downloadLink.href = window.URL.createObjectURL(blob);
		                    downloadLink.download = 'asset_inventory.xlsx';
		                    document.body.appendChild(downloadLink);
		                    downloadLink.click();
		                    document.body.removeChild(downloadLink);
		                })
		                .catch(function(error) {
		                    toastr.error('Error exporting data');
		                    console.error("Error exporting data:", error);
		                });
		        }
		        
		        $scope.handleFileSelect = (files)=>{
					$scope.selectedFile = files[0];
					console.log("File: ", $scope.selectedFile);
				}
		
		//for upload excel function.	
			function uploadNew(){
				var file = $scope.selectedFile;
				console.log("File: ", file);
	
				if (!file) {
					toastr.error("Please select file");
					return;
				}
				   var url = assetUrl + "/uploadExcel";
	
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
							toastr.success('Uploaded....', 'Succesfully !!',{ timeOut: 10000 });	
							
							loadAssets();
						}else{
							toastr.error('Upload....', 'UnSuccesfull !!');
						}
						 $scope.uploadTab = false;
						$scope.uploadMediaPrepTab= false;
					}, function errorCallback(response) {
				    	$('.loading').hide();
						$scope.uploadMediaPrepTab= false;
						toastr.error('Upload....', 'UnSuccesfull !!');
					});
					angular.element("input[type='file']").val(null);
					$scope.selectedFile = null;
					  $scope.showChooseOption = false; 
			}

		
	        
		//for load locations
	        function loadCategories() {
	            var url = baseUrl + "/category/listByIT";
	            console.log("URL "+url)
	            $http.get(url)
	                .then(function(response) {
	                    vm.categories = response.data;
	                    console.log("Fetched categories:", vm.categories);
	                })
	                .catch(function(error) {
	                    console.error("Error fetching categories:", error);
	                });
	        }
        
       
        //for load locations
		      function loadLocations() {
		            var url = baseUrl + "/location/all";
		            $http.get(url)
		                .then(function(response) {
		                    vm.locations = response.data;
		                    console.log("Fetched locations:", vm.locations);
		                })
		                .catch(function(error) {
		                    console.error("Error fetching locations:", error);
		                });
		        }
			//for load labs
		       function loadLabs() {
		            var url = baseUrl + "/lab/all";
		            $http.get(url)
		                .then(function(response) {
		                    vm.labs = response.data;
		                    console.log("Fetched labs:", vm.labs);
		                })
		                .catch(function(error) {
		                    console.error("Error fetching labs:", error);
		                });
		        }
	        	//for load spare
			        function loadSpares() {
					    var url = baseUrl + "/spares/getAllSapres";
					    $http.get(url)
					        .then(function(response) {
					            vm.spares = response.data;
					            console.log("Fetched spares:", vm.spares);
					        })
					        .catch(function(error) {
					            console.error("Error fetching spares:", error);
					        });
					}

			
					
						
	let selectedassetInventory = null;
       function delet(assetInventory) {
    selectedassetInventory = assetInventory;
    vm.showModal = true; // Show the confirmation modal
}

function confirmDelete() {
    if (!selectedassetInventory) return;

   var url = baseUrl + "/assetInventory/delete/" + selectedassetInventory.asset_inventory_id;

    $http.delete(url)
        .then(function(response) {
            toastr.success('Deleted Asset successfully!');
            loadAssets();
        })
        .catch(function(error) {
            toastr.error('Error deleting Asset');
            console.error("Error deleting Asset:", error);
        })
        .finally(function() {
            vm.showModal = false; // Hide the modal after confirmation
        });
}

function cancelDelete() {
    vm.showModal = false; // Hide the modal without taking any action
}

	
					
 
				  // for save asset
					    function ok(assetInventory) {
					            var url = baseUrl + "/assetInventory/addAssetInventory";
					            var msg = "";
					
					            // Check if all fields are filled
					            if (!assetInventory.machine || !assetInventory.labInsideLoc ||
					                !assetInventory.lab ||  !assetInventory.model ||  !assetInventory.loginName ||  !assetInventory.comapnyName ) {
					                toastr.error('Fill all the fields!');
					                return;
					            }
					
					            // Check if equipment ID is unique
					            if (!isUniqueEquipmentId(assetInventory.equipmentId, assetInventory.asset_inventory_id)) {
					                toastr.error('Equipment ID must be unique!');
					                return;
					            }
					
					            // Save assetInventory including QR code
					            genericFactory.add(msg, url, assetInventory)
					                .then(function(response) {
					                    vm.save = response.data;
					                    console.log("Assets: " + JSON.stringify(vm.save));
					
					                    if (response.status === 200) {
					                        toastr.success('Asset added successfully!');
					                        loadAssets();
					                        $scope.addNewTab = false;
					                        resetFormFields(); // Reset form fields after successful addition
					                    } else {
					                        toastr.error('Failed to add asset');
					                    }
					                })
					                .catch(function(error) {
					                    toastr.error('Failed to add asset');
					                    console.error("Error adding asset:", error);
					                });
					        }

     
				    // Function to check if the equipment ID is unique
				        function isUniqueEquipmentId(equipmentId, currentId) {
				            return !vm.assets.some(function(asset) {
				                return asset.equipmentId === equipmentId && asset.asset_inventory_id !== currentId;
				            });
				        }
				
					//for edit function
				        function add(AssetInventory) {
				            $scope.addNewTab = true;
				            $scope.AssetInventory = Object.assign({}, AssetInventory);
				        }
				        
				      // Function to reset form fields
						        function resetFormFields() {
						            $scope.AssetInventory = {};
						            $scope.spareData = {}; 
						            $scope.documentData = {}; 
						            
						        }

        
       
				////////////////////////////////////////////////////////////////////////////				        
								// User Manual and Assign Spear to Asset.
				////////////////////////////////////////////////////////////////////////////	
				
		
		
			
				//delete function for user manual (document)		
					 	function removedocument(upload_id) 
					 	     {           
					            if (!upload_id) {
					                toastr.error('Invalid Document  ID');
					                console.error("Invalid Document  ID:", upload_id);
					                return;
					            }
						  if ($window.confirm("Are you sure you want to delete this document?")) {
			            var url = baseUrl + "/assetInventory/" + upload_id + "/document_delete"; 
			            console.log("Delete URL:", url);
			            $http.delete(url)
			                .then(function(response) {
			                    toastr.success('Document  deleted successfully!');
			                
			                  loadUserDocument($scope.documentData.assetInventoryId); 
			                })
			                .catch(function(error) {
			                    toastr.error('Error deleting Document ');
			                    console.error("Error deleting Document :", error);
			                });
			                  } else {
							        toastr.info('Delete operation canceled');
							    }
			        }
		
					//for active and inactive document status 
				function toggleStatus(document) {
					    // Toggle the status from 1 (active) to 0 (inactive) or vice versa
					    var newStatus = document.active === 1 ? 0 : 1;
					    var url = assetUrl + "/updateManualStatus/" + document.upload_id + "?active=" + newStatus;  
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


				
				
					//function to download the document
				        function downloaddocument(upload_id)	{			
						
						    var downloadUrl = assetUrl+'/downloadUserManual/' + upload_id;
						
						   
						    $http({
						        method: 'GET',
						        url: downloadUrl,
						        responseType: 'blob'  
						    }).then(function(response) {
						        
						        if (response.data) {
						            // Create a URL for the file and set the filename
						            var file = new Blob([response.data], { type: response.headers('Content-Type') });
						            var fileName = response.headers('Content-Disposition').split('filename=')[1].replace(/\"/g, "");
						
						            // Use an anchor element to trigger the download
						            var downloadLink = document.createElement('a');
						            var url = window.URL.createObjectURL(file);
						            downloadLink.href = url;
						            downloadLink.download = fileName;
						
						            // Append the link to the body and trigger a click
						            document.body.appendChild(downloadLink);
						            downloadLink.click();
						
						            // Clean up by removing the link and revoking the URL
						            document.body.removeChild(downloadLink);
						            window.URL.revokeObjectURL(url);
						        } else {
						            alert("File not found or inactive");
						        }
						    }, function(error) {
						        // Handle error response
						        if (error.status === 403) {
						            alert("You do not have permission to download this file.");
						        } else if (error.status === 404) {
						            alert("File not found.");
						        } else {
						            alert("An error occurred while downloading the file.");
						        }
						    });
						};


				// Function to open the document with the specified upload ID
					function openDocument(upload_id) {
						    console.log("Open document with upload ID:", upload_id);
						
						    fetch(assetUrl + "/viewUploadedDocument/" + upload_id, {
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
						
						        // Open the document modal with zoom functionality
						        openDocumentModal();
						    })
						    .catch(error => {
						        toastr.error("Document not available");
						        console.error(error);
						    });
						}
						
						// Function to open the document modal with zoom controls
						function openDocumentModal() {
						    var modalInstance = $uibModal.open({
						        templateUrl: 'documentModalContent.html',
						        controller: function ($scope, $uibModalInstance, documentUrl) {
						            $scope.documentUrl = documentUrl; // Document URL passed to the scope
						            
						            // Initialize zoom level
						            $scope.zoomLevel = 1; // Default zoom level is 100%
						
						            // Function to zoom in
						            $scope.zoomIn = function() {
						                $scope.zoomLevel += 0.1; // Increase zoom level by 10%
						            };
						
						            // Function to zoom out
						            $scope.zoomOut = function() {
						                if ($scope.zoomLevel > 0.1) { // Prevent zooming out too much
						                    $scope.zoomLevel -= 0.1; // Decrease zoom level by 10%
						                }
						            };
						
						            // Close modal function
						            $scope.close = function () {
						                $uibModalInstance.dismiss('cancel');
						            };
						        },
						        resolve: {
						            documentUrl: function () {
						                return vm.documentUrl; // Resolving the document URL
						            }
						        }
						    });
						}
							$scope.handleFileSelect = (files) => {
							    if (files.length > 0) {
							        // If there are already selected files, merge them with the new ones
							        if (!$scope.selectedFile) {
							            $scope.selectedFile = []; 
							        }
							        
							        // Convert the FileList to an array and concatenate with the existing array
							        $scope.selectedFile = $scope.selectedFile.concat(Array.from(files));
							        console.log("Selected Files: ", $scope.selectedFile);
							    } else {
							        $scope.selectedFile = []; 
							    }
							};

						
				// Function to show the document upload form with the asset's equipment ID
					function ShowAddDocumentTab(asset) {
							    $scope.addSpareTab = false;
							    $scope.viewQrTab = false;
							    $scope.addDocumentTab = true; 
							
							    $scope.documentData = {
							        assetInventoryId: asset.asset_inventory_id, 
							        equipmentId: asset.equipmentId, 
							        model: asset.model
							       // userId: vm.userId   
							       
							    };
							 vm.loadDocuments = []; 
							    vm.buttonsVisible = false;
							      loadUserDocument(asset.asset_inventory_id);
							}		

		
				//load user manual (document) in table
				function loadUserDocument(assetInventoryId) {
				    var url = assetUrl + "/fetchUploadedDocuments/" + assetInventoryId;     
				    $http.get(url)
				        .then(function(response) {
				            vm.loadDocuments = response.data;
				            if (vm.loadDocuments.length === 0) {
				                
				                toastr.warning('No documents found for this asset.');
				            }
				            console.log("Fetched loadDocuments:", vm.loadDocuments);
				        })
				        .catch(function(error) {
				            console.error("Error fetching load documents:", error);
				        });
				}		
						
						
				// Function to save the uploaded document
					function saveDocument() {
						 const files = $scope.selectedFile;
						  
							console.log("File: ", files);
				
							if (!files) {
								toastr.error("Please select file");
								return;
							}
							  
						
						    var url = assetUrl + "/uploadUserManual"; 
						    var fd = new FormData();
						
						
						    // Append each file to the FormData object
							    angular.forEach(files, function(file) {
							        fd.append('files', file);
							    });
						    fd.append('model', $scope.documentData.model); 
						   // fd.append('userId', userId); 
						
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
						            // Keep the Add Document tab open after uploading
       							     $scope.addDocumentTab = true;
       							      // Clear the file input but keep the tab open
							            $scope.selectedFile = null; 
							            angular.element("input[type='file']").val(null); // Clear file input
							              loadUserDocument($scope.documentData.assetInventoryId);
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
																	        
														        
														        
														        
	 	 // Function to show the spare form with the asset's equipment ID
		       function showAddSpareForm(asset) {
		            $scope.addSpareTab = true;
		             $scope.viewQrTab=false;
		             $scope.spareData = {
							assetInventoryId: asset.asset_inventory_id, 
							 equipmentId: asset.equipmentId,
							spare_ids: []
					  };
						 vm.buttonsVisible = false;
						 loadassignSpears(asset.asset_inventory_id);
								       
					 }										        
			        
     		//save aasign spare function   
   			 	function saveSpare(spareData) 
			        {
			  
			            if (!spareData || !spareData.assetInventoryId || spareData.spare_ids.length === 0) {
			                toastr.error("Missing equipment ID or spare ID");
			                console.error("saveSpare Error: Missing equipmentId or spare_id in spareData", spareData);
			                return;
			            }

				            var url = assetUrl + "/" +  spareData.assetInventoryId + "/Assetspares";
				            console.log("Save Spare URL:", url);
				            console.log("Spare Data: ", spareData);
				
				            var spares = spareData.spare_ids.map(id => ({ spare_id: id }));
				
				            $http.post(url, spares)
				                .then(function(response) {
				                    if (response.data.code === 200) {
				                        console.log("Spare saved successfully!", response.data);
				                        toastr.success('Spare assigned successfully!');
				                        $scope.addSpareTab = false; // Close the form after success
				                          vm.buttonsVisible = true;
				                          
				                    } else {
				                        toastr.error('Error assigning spare');
				                    }
				                })
				                .catch(function(error) {
				                    toastr.error('Error assigning spare');
				                    console.error("Error saving spare:", error);
				                });
				        }

    		 //delete function assetspareassign			
			 	 function remove(assignSpareId) 
			 	     {           
			            if (!assignSpareId) {
			                toastr.error('Invalid Spare Assign ID');
			                console.error("Invalid Spare Assign ID:", assignSpareId);
			                return;
			            }
						 if ($window.confirm("Are you sure you want to delete this Spear?")) {
			            var url = baseUrl + "/assetInventory/" + assignSpareId + "/delete"; 
			            console.log("Delete URL:", url);
			            $http.delete(url)
			                .then(function(response) {
			                    toastr.success('Spare assignment deleted successfully!');
			                
			                  loadassignSpears($scope.spareData.assetInventoryId); 
			                })
			                .catch(function(error) {
			                    toastr.error('Error deleting spare assignment');
			                    console.error("Error deleting spare assignment:", error);
			                });
			                } else {
							        toastr.info('Delete operation canceled');
							    }
			        }

						//for active and inactive spear status 
				function toggleStatus(spare) {
					    // Toggle the status from 1 (active) to 0 (inactive) or vice versa
					    var newStatus = spare.active === 1 ? 0 : 1;
					    var url = assetUrl + "/updateSpareStatus/" + spare.spare_id + "?active=" + newStatus;  
					    $http.put(url)
					        .then(function(response) {
					            if (response.data.code === 200) {
					               
					                spare.active = newStatus;  
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
					
					
								

			//load assign spear in table
				  function loadassignSpears(assetInventoryId) 
				 	 {
				            var url = baseUrl + "/assetInventory/fetch/" + assetInventoryId;     
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
				 


     
    }
})();

