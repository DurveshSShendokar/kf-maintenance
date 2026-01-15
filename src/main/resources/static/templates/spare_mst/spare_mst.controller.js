(function() {
    'use strict';

    angular.module('myApp.spare_mst').controller('spare_mstController', spare_mstController);

    spare_mstController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr','genericFactory'];

    function spare_mstController($scope, ApiEndpoint, $http, toastr, genericFactory) {
      
	 const baseUrl = ApiEndpoint.url;
        var spareUrl = staticUrl+"/spares";
        var vm = angular.extend(this, {
            spares: [],
            add: add,
            delet: delet,
            ok: ok,
            addNew: addNew,
            cancel: cancel,
            resetFormFields: resetFormFields,
            uploadNew: uploadNew,
             confirmDelete: confirmDelete,
             cancelDelete:cancelDelete,
			upload:upload,
             showChooseOption: true,
            export: exportExcel 
        });

        (function activate() {
            $scope.Spare = {};
            loadSpares();
            $scope.addNewTab = false;
             vm.showModal = false;
             $scope.uploadTab=false;
        })();


		   function upload(){
			$scope.uploadTab=true;
			 $scope.showChooseOption = true;
			$scope.addNewTab=false;
			
		}
		
        function cancel() {
            $scope.addNewTab = false;
        }

        function addNew() {
            $scope.addNewTab = true;
             $scope.uploadTab=false;
            resetFormFields(); // Reset form fields when adding new
        }


		   function exportExcel() {
            var url = spareUrl + "/exportExcel";

            $http.get(url, { responseType: 'blob' })
                .then(function(response) {
					  console.log("Export response data:", response.data);
                    var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
                    var downloadLink = document.createElement('a');
                    downloadLink.href = window.URL.createObjectURL(blob);
                    downloadLink.download = 'Spare.xlsx';
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
		
		function uploadNew(){
			var file = $scope.selectedFile;
			console.log("File: ", file);

			if (!file) {
				toastr.error("Please select file");
				return;
			}
			   var url = spareUrl + "/uploadExcel";

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
						
						loadSpares();
					}else{
						toastr.error('Upload....', 'UnSuccesful !!');
					}
					 $scope.uploadTab = false;
					$scope.uploadMediaPrepTab= false;
				}, function errorCallback(response) {
			    	$('.loading').hide();
					$scope.uploadMediaPrepTab= false;
					toastr.error('Upload....', 'UnSuccesful !!');
				});
				angular.element("input[type='file']").val(null);
				$scope.selectedFile = null;
				  $scope.showChooseOption = false; 
		}
		
		
		
		
		
		
	/*let selectedSpare = null;

	 function delet(spare) {
		    selectedSpare = spare; 
		    		   
		    console.log("Opening modal for spare:", spare);
		    
		    if ($('#deleteConfirmationModal').length) {
		        $('#deleteConfirmationModal').modal('show'); 
		    } else {
		        console.error("Modal not found in the DOM!");
		    }
		}
		
		

		 function confirmDelete() {
		        if (!selectedSpare) return;
		        var url = baseUrl + "/spares/delete/" + selectedSpare.spare_id;
		        console.log("Delete URL:", url);
		
		        $http.delete(url)
		            .then(function(response) {
		                toastr.success('Deleted spare successfully!');
		                loadSpares();
		                $('#deleteConfirmationModal').modal('hide'); // Hide the modal
		            })
		            .catch(function(error) {
		                toastr.error('Error deleting spare');
		                console.error("Error deleting spare:", error);
		            });
		    }
		
	*/	
	
	let selectedSpare = null;
       function delet(spare) {
    selectedSpare = spare;
    vm.showModal = true; // Show the confirmation modal
}

function confirmDelete() {
    if (!selectedSpare) return;

    var url = baseUrl + "/spares/delete/" + selectedSpare.spare_id;

    $http.delete(url)
        .then(function(response) {
            toastr.success('Deleted spare successfully!');
            loadSpares();
        })
        .catch(function(error) {
            toastr.error('Error deleting spare');
            console.error("Error deleting spare:", error);
        })
        .finally(function() {
            vm.showModal = false; // Hide the modal after confirmation
        });
}

function cancelDelete() {
    vm.showModal = false; // Hide the modal without taking any action
}

		
		
		
		
		
		
			 function loadSpares() {
			    var url = baseUrl + "/spares/getAllSapres";
			    $http.get(url)
			        .then(function(response) {
			            vm.spares = response.data;
			            console.log("Fetched spare:", vm.spares);
			        })
			        .catch(function(error) {
			            console.error("Error fetching spares:", error);
			        });
			}

        function ok(spare) {
            var url = baseUrl + "/spares/addSpares";
            var msg = "";
            
            // Check if all fields are filled
            if (!spare.spare_name || !spare.suuplier_name || !spare.code) {
                toastr.error('Fill all the fields!');
                return;
            }

            genericFactory.add(msg, url, spare)
                .then(function(response) {
                    vm.saved = response.data;
                    console.log("checking spare:", vm.saved);

                    if (response.status === 200) {
                        toastr.success('spare added successfully!');
                        loadSpares();
                        $scope.addNewTab = false;
                        resetFormFields(); 
                    } else {
                        toastr.error('Failed to add spare');
                    }
                })
                .catch(function(error) {
                    toastr.error('Failed to add spare');
                    console.error("Error adding spare:", error);
                });
        }

        function resetFormFields() {
            $scope.Spare = {}; // Reset the lab object to clear the form fields
        }

        function add(Spare) {
            $scope.addNewTab = true;
            $scope.Spare = Object.assign({}, Spare);
        }

      
			
					
				

			
			
			
   
       
    }
})();
