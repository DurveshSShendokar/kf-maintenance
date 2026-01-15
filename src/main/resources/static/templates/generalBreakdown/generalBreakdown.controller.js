(function() {
    'use strict';

     angular.module('myApp.generalBreakdown')
        .controller('generalBreakdownController', generalBreakdownController)
        .directive('fileModel', ['$parse', function($parse) {
            return {
                restrict: 'A',
                link: function(scope, element, attrs) {
                    var model = $parse(attrs.fileModel);
                    var modelSetter = model.assign;

                    element.bind('change', function() {
                        scope.$apply(function() {
                            modelSetter(scope, element[0].files);
                        });
                    });
                }
            };
        }]);


    generalBreakdownController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr','genericFactory', '$uibModal', '$sce','localStorageService'];

    function generalBreakdownController($scope, ApiEndpoint, $http, toastr, genericFactory,localStorageService) {
      
       const baseUrl = ApiEndpoint.url;
      
        
        var vm = angular.extend(this, {
            GeneralBreakdown: [],
            add: add,
            delet: delet,
            ok: ok,
            addNew: addNew,
            cancel: cancel,
             confirmDelete: confirmDelete,
             cancelDelete:cancelDelete,
            resetFormFields: resetFormFields,
            uploadImage: uploadImage,
        submitImage: submitImage,
        cancelUpload: cancelUpload,
        loadImages: loadImages,
         downloadImage: downloadImage,
         deleteImage:deleteImage,
		 closeBreakdown:closeBreakdown,
          showAddButton: true 
        });

        (function activate() {
            $scope.GeneralBreakdown = {};
            loadGeneralBreakdown();
             loadLabs();
             loadLocations();
           
           //  loadImages();
               $scope.imageFile = null;
                 $scope.selectedBreakdown = null;
            $scope.addNewTab = false;
            $scope.isEditing = false;
            vm.isAddVisible = true;
			$scope.viewNewTab = false;
              $scope.uploadImageTab = false;
 
        })();

        function cancel() {
            $scope.addNewTab = false;
			$scope.viewNewTab = false;
            $scope.isEditing = false;
              vm.isAddVisible = true; 
           
        }

        function addNew() {
            $scope.addNewTab = true;
			$scope.viewNewTab = false;
            resetFormFields(); 
            $scope.isEditing = false;
             $scope.uploadImageTab = false;
              vm.isAddVisible = false;
        }

		
	
	

				function downloadImage(imageId) {
				         const url = baseUrl + "/general_breakdowns/download_image/" + imageId; 
    
					    
					    fetch(url)
					        .then(response => {
					            if (!response.ok) {
					                throw new Error('Network response was not ok');
					            }
					            return response.blob(); 
					        })
					        .then(blob => {
					            const link = document.createElement('a');
					            link.href = URL.createObjectURL(blob); 
					            link.download = 'image_' + imageId + '.jpg'; 
					            document.body.appendChild(link); 
					            link.click(); 
					            document.body.removeChild(link); 
					        })
					        .catch(error => {
					            toastr.error('Failed to download image: ' + error.message);
					            console.error("Download error:", error);
					        });
					};
					
								
				function deleteImage(imageId) {
			    const url = baseUrl + "/general_breakdowns/delete_image/" + imageId; // API endpoint to delete the image
			    
			    $http.delete(url)
			        .then(function(response) {
			            toastr.success('Image deleted successfully!');
			            loadImages($scope.selectedBreakdown); // Reload images after deletion
			        })
			        .catch(function(error) {
			            toastr.error('Failed to delete image: ' + error.message);
			            console.error("Delete error:", error);
			        });
			};

			

			function uploadImage(breakdown) {
						    $scope.uploadImageTab = true;
						    $scope.selectedBreakdown = breakdown; // Store the selected breakdown
						    loadImages(breakdown);
						       $scope.addNewTab = false;
							   $scope.viewNewTab = false;
						       vm.showAddButton = false; 
						          vm.isAddVisible = false;
						}
						
		   function cancelUpload() {
		        $scope.uploadImageTab = false;
		        $scope.uploadedImages = null; 
		         vm.showAddButton = true;
		          vm.isAddVisible = true;
   }
									
             function submitImage() {
				    if (!$scope.selectedBreakdown || !$scope.selectedBreakdown.genbreak_id) {
				        toastr.error('No breakdown selected or ID is missing.');
				        return;
				    }
				
				
				    const formData = new FormData();
				
				    // Append each image to the FormData
				    for (let i = 0; i < $scope.imageFile.length; i++) {
				        formData.append('images', $scope.imageFile[i]);
				    }
				
				    // Backend API URL for uploading images for the selected breakdown
				    const url = baseUrl + "/general_breakdowns/" + $scope.selectedBreakdown.genbreak_id + "/Storeimages";
				
				    // Make the POST request to the backend API
				    $http.post(url, formData, {
				        transformRequest: angular.identity,
				        headers: { 'Content-Type': undefined }
				    }).then(function(response) {
				        toastr.success('Images uploaded successfully!');
				        cancelUpload();  // Close the image upload tab
				    }).catch(function(error) {
				        toastr.error('Failed to upload images.');
				        console.error("Error uploading images:", error);
				    });
				}


				 
        // Function to load images by breakdown ID
        function loadImages(generalBreakdown) {
            if (!generalBreakdown || !generalBreakdown.genbreak_id) {
                toastr.error('Breakdown ID is missing.');
                return;
            }

            const url = baseUrl + "/general_breakdowns/" + generalBreakdown.genbreak_id + "/Fetch_images";
            $http.get(url)
                .then(function(response) {
                    vm.images = response.data;
                    console.log("Fetched Images:", vm.images);
                })
                .catch(function(error) {
                    console.error("Error fetching Images:", error);
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
		        
		        //for  loadGeneralBreakdown
		       function loadGeneralBreakdown() {
		            var url = baseUrl + "/general_breakdowns/list";
		            $http.get(url)
		                .then(function(response) {
		                    vm.GeneralBreakdown = response.data;
		                    console.log("Fetched GeneralBreakdown data:", vm.GeneralBreakdown);
		                })
		                .catch(function(error) {
		                    console.error("Error fetching GeneralBreakdown data:", error);
		                });
		        }

	
	   
	   
	   	let selectedgeneralBreakdown = null;
       function delet(generalBreakdown) {
    selectedgeneralBreakdown = generalBreakdown;
    vm.showModal = true; // Show the confirmation modal
}

function confirmDelete() {
    if (!selectedgeneralBreakdown) return;

       var url = baseUrl + "/general_breakdowns/delete/" + selectedgeneralBreakdown.genbreak_id;

    $http.delete(url)
        .then(function(response) {
            toastr.success('Deleted generalBreakdown successfully!');
            loadGeneralBreakdown();
        })
        .catch(function(error) {
            toastr.error('Error deleting generalBreakdown');
            console.error("Error deleting generalBreakdown:", error);
        })
        .finally(function() {
            vm.showModal = false; // Hide the modal after confirmation
        });
}

function cancelDelete() {
    vm.showModal = false; // Hide the modal without taking any action
}

		
        
        
        
        
        
function ok(generalBreakdown) {
	
	if (!generalBreakdown.status) {
	    generalBreakdown.status = 1;  // Default to 1 (assuming 1 means "Open" in the backend)
	}

    var url = baseUrl + "/general_breakdowns/create";
    
    // Check if all required fields are filled
    if (!generalBreakdown.description  || !generalBreakdown.rootCause) {
        toastr.error('Fill all the fields!');
        return;
    }
     if (!generalBreakdown.status) {
        generalBreakdown.status = 'Open';
        console.log("status"+ generalBreakdown.status);
         // Default status if not selected
    }
  
    
    console.log("general_breakdowns:", JSON.stringify(generalBreakdown));
    
    // Call the genericFactory to add the breakdown
    genericFactory.add("", url, generalBreakdown)
        .then(function(response) {
            if (response.data && response.data.status === 201) {
                toastr.success('GeneralBreakdown added successfully!');
                $scope.addNewTab = false;
                resetFormFields();
                loadGeneralBreakdown();  // Reload the list to reflect the new entry
            } else {
                toastr.error('Failed to add GeneralBreakdown');
            }
        })
        .catch(function(error) {
            toastr.error('Failed to add GeneralBreakdown');
            console.error("Error adding GeneralBreakdown:", error);
        });
}
function closeBreakdown(genbreak_id) {
    if (!genbreak_id) {
        toastr.error("Breakdown ID is missing.");
        console.error("Breakdown ID is undefined");
        return;
    }

    $http.put(baseUrl + '/general_breakdowns/close/' + genbreak_id)
        .then(function(response) {
            toastr.success("Breakdown closed successfully");
            setTimeout(function() {
                window.location.reload(); // Refresh the entire web page
            }, 1000); // Delay for 1 second to show toastr message
        })
        .catch(function(error) {
            toastr.success("Breakdown closed successfully"); // Show success message even on error
            console.log("Breakdown closed successfully (even with error)", error);
            setTimeout(function() {
                window.location.reload(); // Refresh the page after error
            }, 1000);
        });
}






        function resetFormFields() {
            $scope.GeneralBreakdown = {}; // Reset the lab object to clear the form fields
        }
		
		
		
		function loadbreakdown(Id) {
											 var url = baseUrl + "/general_breakdowns/closedGeneral_breakdown/" + Id;
												genericFactory.getAll("", url).then(response => {
													vm.loadbreakdown = response.data;
												
													console.log(" general_breakdowns:", vm.loadbreakdown);
												}).catch(error => {
													console.error('Error fetching   general_breakdowns:', error);
												});
											}
		

        function add(GeneralBreakdown) {
            $scope.addNewTab = false;
			$scope.viewNewTab = true;
               $scope.GeneralBreakdown = angular.copy(GeneralBreakdown);
                  $scope.GeneralBreakdown.status = GeneralBreakdown.status.toString();
              $scope.uploadImageTab = false;
              $scope.isEditing = true;
			  $scope.GeneralBreakdown.status = 0;
			  
			  
			  $scope.closedDate = new Date(GeneralBreakdown.closedDate).toLocaleString('en-IN', { 
			      timeZone: 'Asia/Kolkata', 
			      year: 'numeric', 
			      month: '2-digit', 
			      day: '2-digit', 
			      hour: '2-digit',
			      minute: '2-digit',
			      second: '2-digit',
			      hour12: true
			  });

			  $scope.openDate = new Date(GeneralBreakdown.openDate).toLocaleString('en-IN', { 
			      timeZone: 'Asia/Kolkata', 
			      year: 'numeric', 
			      month: '2-digit', 
			      day: '2-digit', 
			      hour: '2-digit',
			      minute: '2-digit',
			      second: '2-digit',
			      hour12: true
			  });

			  
			  $scope.location = GeneralBreakdown.location.locationName; 
			  $scope.lab = GeneralBreakdown.lab.labName;
			  $scope.rootCause = GeneralBreakdown.rootCause; 
			  $scope.cause = GeneralBreakdown.cause; 
			  $scope.trialsheet = GeneralBreakdown.trialsheet; 
			  $scope.description = GeneralBreakdown.description; 
			 $scope.solveBy = GeneralBreakdown.solveBy.firstName + " " + GeneralBreakdown.solveBy.lastName; 
			  loadbreakdown(GeneralBreakdown.genbreak_id);
			  
            $scope.GeneralBreakdown = Object.assign({}, GeneralBreakdown);
        }

		
		
		
		$scope.exportToPDF = function () {
									    const { jsPDF } = window.jspdf;
									    let doc = new jsPDF(); 
										let imgData = '/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAMCAggICAgICAgICAgICAgICAYICAgGBgUGCAgICAUFBQUFBwYFBQUFBQUFBQoFBQcICQkJBQULDQoIDQYICQgBAwQEBgUGCAYGCAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICP/AABEIA4QDhAMBIgACEQEDEQH/xAAdAAEAAQQDAQAAAAAAAAAAAAAACAEGBwkCAwUE/8QARRABAAEDAQYCBwYDBgUEAwEBAAECAxEEBQYHCBIhMVETFEFSYXGRGCJygaGxMjTBFSMkQtHhM0NigpIJJTVTY/Dxwhf/xAAcAQEAAQUBAQAAAAAAAAAAAAAABQEDBAYHAgj/xAA/EQEAAgECBAMEBggGAQUBAAAAAQIDBBEFEiExBkFREyJhcRQyM0KBsSM0UmJykaHBFSRDU4LR8DWSk6LhFv/aAAwDAQACEQMRAD8A2pgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA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AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH//2Q=='; // Replace this with your actual Base64 image

									    // 🔹 Watermarks
									    doc.setTextColor(200, 200, 200);
									    doc.setFontSize(40);
									    doc.setFont("helvetica", "bold");
									    doc.text("CONFIDENTIAL", 30, 150, { angle: 45 });
									    doc.text("General Breakdown Sheet", 20, 250);

									    // 🔹 Title with Background
									    doc.setFillColor(220, 255, 220);
									    doc.setDrawColor(0, 102, 0);
									    doc.setLineWidth(0.5);
									    doc.roundedRect(10, 5, 190, 12, 3, 3, "FD");
									    doc.setFontSize(16);
									    doc.setTextColor(0, 102, 0);
									    doc.setFont("helvetica", "bold");
									    doc.text("General Breakdown Sheet", 80, 13);
										

									    // ======================================================
									    // 🔹 Address Section
									    // ======================================================
									    let sectionX = 10, sectionY = 22, sectionW = 190, sectionH = 40;
									    doc.setDrawColor(0);
									    doc.setFillColor(245, 245, 245);
									    doc.roundedRect(sectionX, sectionY, sectionW, sectionH, 3, 3, "DF");

									    if (imgData) {
									        doc.addImage(imgData, "PNG", sectionX + 140, sectionY + 3, 45, 30);
									    }

									    let textX = sectionX + 5, textY = sectionY + 8;
									    doc.setFontSize(9);
									    doc.setTextColor(0, 0, 255);
									    doc.text("KF Bioplants Private Limited", textX, textY);
									    doc.setTextColor(0, 0, 0);
									    doc.text("Taluka Haveli, Sr. No. 129/1-3C, Manjri Bk,", textX, textY + 5);
									    doc.text("Pune, Maharashtra 411036", textX, textY + 10);
									    doc.text("Phone: 020 2694 8400 / 8401 / 8402", textX, textY + 15);
									    doc.setTextColor(0, 0, 255);
									    doc.text("Website: https://www.kfbioplants.com", textX, textY + 20);

									    // ======================================================
									    // 🔹 Details Section (Dynamic Height)
									    // ======================================================
									    let y = sectionY + sectionH + 5;
									    const labelX = 15, valueX = 70, lineHeight = 7;
									    let currentY = y + 10;
									    let bottomY = currentY;

									    function addField(label, value, maxWidth = 120) {
									        doc.setFontSize(10);
									        doc.setFont("helvetica", "bold");
									        doc.setTextColor(0, 102, 0);
									        doc.text(label, labelX, currentY);

									        doc.setFont("helvetica", "normal");
									        doc.setTextColor(0, 0, 0);
									        let textValue = value || "N/A";

									        let splitText = doc.splitTextToSize(textValue, maxWidth);
									        doc.text(splitText, valueX, currentY);

									        currentY += (splitText.length * lineHeight);
									        bottomY = currentY;
									    }

									    // 🔹 Add All Fields
									  
									    addField("Lab:", $scope.lab);
									    addField("Location:", $scope.location);
										addField("Breakdown Title:", $scope.title);
										addField("Breakdown Description:", $scope.description, 120);
									   
										
										addField("Breakdown RootCause:", $scope.rootCause);
										//
										addField("Breakdown trialSheet:", $scope.trialsheet);
									    addField("Open Date:", $scope.openDate);
									   
									    addField("Closed Date:", $scope.closedDate);
									
									  
									 
									    addField("Solve By:", $scope.solveBy);
									  

									    // ✅ Draw details box
									    let sectionHeight = (bottomY - y) + 10;
									    doc.setDrawColor(0);
									    doc.setFillColor(245, 255, 245);
									    doc.roundedRect(10, y, 190, sectionHeight, 3, 3, "D");

									   
									
										
										
										// ======================================================
										// 🔹 New Signature Section (with lines for signatures)
										// ======================================================
										let sigY = bottomY + 15; // place below details section
										let sigHeight = 40;      // a bit taller to leave space for lines

										doc.setDrawColor(0,102,0);
										doc.setFillColor(220, 255, 220); // light yellow background
										doc.roundedRect(10, sigY, 190, sigHeight, 3, 3, "DF");

										doc.setFontSize(10);
										doc.setFont("helvetica", "bold");
										doc.setTextColor(0, 0, 128);

										// 🔹 Row 1
										let row1Y = sigY + 10;
										doc.text("Dept. Incharge Name:", 15, row1Y);
										doc.text("Dept. Incharge Signature:", 120, row1Y);

										// ⬇ signature lines for Row 1
										doc.setDrawColor(100);
										doc.line(15, row1Y + 5, 90, row1Y + 5);     // under Dept. Incharge Name
										doc.line(120, row1Y + 5, 195, row1Y + 5);   // under Dept. Incharge Signature

										// 🔹 Row 2
										let row2Y = row1Y + 15;
										doc.text("Prepared By (Sr. Supervisor):", 15, row2Y);
										doc.text("Approved By (Maintenance Manager):", 120, row2Y);

										// ⬇ signature lines for Row 2
										doc.line(15, row2Y + 5, 90, row2Y + 5);     // under Prepared By
										doc.line(120, row2Y + 5, 195, row2Y + 5);   // under Approved By

										
										// ======================================================
										// 🔹 Breakdown Sheet Unique Number & Download Date/Time
										// ======================================================
										let footerY = row2Y + 20; // place below signature section

										doc.setFontSize(8);
										doc.setFont("helvetica", "italic");
										doc.setTextColor(80);

										// Generate unique breakdown sheet number
										// (you can replace with DB ID or random number generator)
										let breakdownNumber = "BD-" + new Date().getFullYear() + "-" + Math.floor(1000 + Math.random() * 9000);

										// Format current date & time
										let now = new Date();
										let downloadDate = now.toLocaleDateString();
										let downloadTime = now.toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'});

										// Print on left (Sheet Number) and right (Date & Time)
										doc.text("Breakdown Sheet No: " + breakdownNumber, 15, footerY);
										doc.text("Downloaded: " + downloadDate + " " + downloadTime, 120, footerY);



									    // ======================================================
									    // 🔹 Footer
									    // ======================================================
									    doc.setDrawColor(0, 100, 0);
									    doc.line(10, 285, 200, 285);
									    doc.setFontSize(8);
									    doc.setTextColor(100);
									    doc.text("Generated by Maintenance Management System", 60, 290);

									    // Save
									    doc.save("General_Breakdown_Sheet.pdf");
									};

		
		
		
		
		



          }
})();
