	(function() {
    'use strict';

    angular.module('myApp.Ticket').controller('TicketController', TicketController);

    TicketController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr','genericFactory', '$sce', '$uibModal'];

    function TicketController($scope, ApiEndpoint, $http, toastr, genericFactory, $sce, $uibModal) {
      
        const baseUrl = ApiEndpoint.url;
         var newStatus;
        
        var vm = angular.extend(this, {
            spareComsuptions:[],
           spareUseds:[],
            addNew: addNew,
            cancel: cancel,
             add: add,
              ok: ok,
              addbuttonn:addbuttonn,
			removebuttonn:removebuttonn,
			 utilizedSpares: [],
			 loadAllSpares:loadAllSpares,
			 selectedSpares:[],
			 updateSpareUseds: updateSpareUseds,
			  loadUserDocument: loadUserDocument,
			    ShowAddDocumentTab: ShowAddDocumentTab,
			       openDocument: openDocument,
			 downloadUserManual:downloadUserManual //
        });

        (function activate() {
            $scope.Complaint = {};
             $scope.documentData = {}; 
          loadComplaints();
           loadspares();
            loadAssets();
           $scope.addDocumentTab = false; 
            $scope.addNewTab = false;
        })();

        function cancel() {
            $scope.addNewTab = false;
              $scope.addDocumentTab = false; 
        }

        function addNew() {
            $scope.addNewTab = true;
              $scope.addDocumentTab = false; 
           
        }
        
        vm.isClosed = function() {
    return vm.Complaint && vm.Complaint.status === 4;
};

        
         function loadComplaints() {
            var url = baseUrl + "/complaints/all";
            $http.get(url)
                .then(function(response) {
                    vm.complaints = response.data;
                    console.log("Fetched complaints:", vm.complaints);
                })
                .catch(function(error) {
                    console.error("Error fetching complaints:", error);
                });
        }
        
        
        
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

        function loadspares() {
            var url = baseUrl + "/spares/getAllSapres";
              console.log("url     :"+url);
            $http.get(url)
                .then(function(response) {
                    vm.sparesList = response.data;
                    console.log("Fetched spares:", vm.spares);
                })
                .catch(function(error) {
                    console.error("Error fetching spares:", error);
                });
        }
          function loadUtilizedSpares(comp_id) {
            var url = baseUrl + "/complaints/complaint_spares/" + comp_id; 
            console.log("Fetching utilized spares from URL: " + url);
            $http.get(url)
                .then(function(response) {
                    vm.utilizedSpares = response.data; 
                    console.log("Fetched utilized spares:", vm.utilizedSpares);
                })
                .catch(function(error) {
                    console.error("Error fetching utilized spares:", error);
                   
                });
        }
        
        
         function loadAllSpares(asset_inventory_id) {
            var url = baseUrl + "/spareStocking/getAllocatedSpareByMachineId?assetInventoryId=" + asset_inventory_id; 
            console.log("Fetching all spares from URL: " + url);
            $http.get(url)
                .then(function(response) {
                    vm.AllSpares = response.data; 
                    console.log("Fetched all spares:", JSON.stringify(vm.AllSpares));
                })
                .catch(function(error) {
                    console.error("Error fetching all spares:", error);
                    toastr.error("Failed to fetch all spares.");
                });
        }
        
        
         function ok(complaint) {
            var url = baseUrl + "/complaints/updateComplaint";
            var msg = "";
            
              // Check if all fields are filled 
              
              complaint.spares=vm.selectedSpares
            if (!complaint.cause || !complaint.rootCause || !complaint.description ||
                !complaint.conclusion) {
                toastr.error('Fill all the fields!');
                return;
            }
            
            
            
            
           
				 // Set newStatus based on the selected status value
				    switch (complaint.status) {
				        case 1:
				            newStatus = 'Open';
				            break;
				        case 2:
				            newStatus = 'Allocate';
				            break;
				        case 3:
				            newStatus = 'In-Process';
				            break;
				        case 4:
				            newStatus = 'Closed';
				            break;
				        default:
				            newStatus = ''; 	
				    }
				    
						  
				    
				      console.log("spareUseds:", JSON.stringify(vm.selectedSpares));
				      
				      
				      
				     
				            genericFactory.add(msg, url, complaint)
				                .then(function(response) {
					
                    vm.saved = response.data;
                    console.log("checking complaint:", vm.saved);

                    if (response.status === 200) {
                        toastr.success('complaint added successfully!');
                        loadComplaints();
                        $scope.addNewTab = false;
                         vm.selectedSpares = []; // Reset selected spares            
                         vm.spareUseds=[]
                        
                    } else {
                        toastr.error('Failed to add complaint');
                    }
                })
                 .catch(function(error) {
    let errorMessage = 'An unexpected error occurred. Please try again.';
    if (error && error.data) {
        if (typeof error.data === 'string') {
            errorMessage = error.data; // Use the custom message from backend
        } else if (error.data.message) {
            errorMessage = error.data.message;
        }
    }
    toastr.error(errorMessage);
    console.error("Error updating complaint:", error);
});

        }
        
        
        //load user manual (document) in table
				function loadUserDocument(asset) {
				    var url = baseUrl + "/assetInventory/fetchUploadedDocuments/" + asset.asset_inventory_id;     
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
				
				
				function ShowAddDocumentTab(asset) {
					    $scope.addNewTab = false;
					    $scope.addDocumentTab = true;
					
					    if (asset && asset.asset_inventory_id) {
					        loadUserDocument(asset); // Fetch documents for the selected asset
					    } else {
					        toastr.warning("No asset selected");
					    }
					}
	


			// Function to open the document with the specified asset ID
					function openDocument(asset) {
						    console.log("Open document with asset ID:", asset);
						
						    fetch(baseUrl + "/complaints/viewUploadedDocumentByAsset/" +asset.asset_inventory_id, {
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

        
        function downloadUserManual(Complaint) {
			 //   var assetInventoryId = $scope.Complaint.assetInventory.asset_inventory_id; 
			    var url = baseUrl + "/complaints/downloadUserManualByAsset/" + Complaint.assetInventory.asset_inventory_id;
			
			    $http.get(url, { responseType: 'blob' }) 
			        .then(function(response) {
			            // Create a URL for the blob object
			            var blob = new Blob([response.data], { type: response.headers('Content-Type') });
			            var downloadUrl = URL.createObjectURL(blob);
			
			            // Create a link element and click it to download the file
			            var a = document.createElement('a');
			            a.href = downloadUrl;
			            a.download = response.headers('Content-Disposition').split('filename=')[1].replace(/"/g, ''); // Get the filename
			            document.body.appendChild(a);
			            a.click();
			            document.body.removeChild(a);
			        })
			        .catch(function(error) {
			            console.error("Error downloading user manual:", error);
			            toastr.error("Failed to download user manual.");
			        });
			}
			
			

        		
        

			function add(Complaint) {
			    $scope.addNewTab = true;
			    $scope.Complaint = angular.copy(Complaint);
			    // Assign selected status to Complaint
			    $scope.Complaint.status = Complaint.status.toString();
			    console.log("Selected Complaint "+JSON.stringify(Complaint))
			    loadUtilizedSpares(Complaint.comp_id)
			    loadAllSpares(Complaint.assetInventory.asset_inventory_id);
			   // Reset the selected spares and utilized spares
				    vm.selectedSpares = [];
				    vm.spareUseds = []; 
			   
			}
			
			
			
			  function updateSpareUseds(selecteedspare) {
				  
				//  vm.selectedSpares.push(selecteedspare);
            vm.spareUseds = vm.selectedSpares.map(function(spare) {
                return {
                    spare: spare,
                    availableQuantity: spare.avialableQuantity,
                    quantity: "" // Initialize quantity as empty
                };
            });
            console.log("Updated spare used:", vm.spareUseds); // Log the updated spare used array
        }


        function addbuttonn() {
            if (vm.selectedSpares.length > 0) {
                vm.selectedSpares.forEach(function(spare) {
                    var obj = {
                        quantity: "", // Initialize quantity as empty
                        spare: spare // Assign the selected spare
                    };
                    vm.spareUseds.push(obj); // Add to utilized spares
                });
                vm.selectedSpares = []; // Reset selected spares after adding
            }
        }
			
		   
		function removebuttonn(){
			
			vm.spareUseds.splice(1,1)
		}

        

}
})();
