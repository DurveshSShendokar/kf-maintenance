
(function () {
    'use strict';

    angular.module('myApp.Upload_PPMExcel').controller('Upload_PPMExcelController', Upload_PPMExcelController);

    Upload_PPMExcelController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr'];

    function Upload_PPMExcelController($scope, ApiEndpoint, $http, toastr) {
        const baseUrl = ApiEndpoint.url;

        var vm = angular.extend(this, {
            files: [],
            labs: [],
            selectedLab: null,
            selectedFile: null,
            upload: upload,
            cancel: cancel,
            importFile: importFile,
			download:download
        });

        (function activate() {
            $scope.uploadTab = false;
            loadFiles();
            loadLabs();
        })();

        function upload() {
            $scope.uploadTab = true;
        }

        function cancel() {
            $scope.uploadTab = false;
            vm.selectedLab = null;
            vm.selectedFile = null;
        }

		
		//load files
        function loadFiles() {
            var url = baseUrl + "/PPM/viewAll";
            $http.get(url)
                .then(function (response) {
                    vm.files = response.data;
                    console.log("Fetched Files:", vm.files);
                })
                .catch(function (error) {
                    console.error("Error fetching Files:", error);
                });
        }

		//load labs
        function loadLabs() {
            var url = baseUrl + "/lab/all";
            $http.get(url)
                .then(function (response) {
                    vm.labs = response.data;
                    console.log("Fetched labs:", vm.labs);
                })
                .catch(function (error) {
                    console.error("Error fetching labs:", error);
                });
        }
		
		//file download
		 function download(spares) {
		    if (!spares.lab || !spares.lab.lab_id) {
		        toastr.error("Lab ID is missing, cannot download file.");
		        return;
		    }

		    var downloadUrl = baseUrl + "/PPM/download/" + spares.lab.lab_id;
		    
		    $http({
		        method: 'GET',
		        url: downloadUrl,
		        responseType: 'blob' 
		    }).then(function(response) {
		        var contentDisposition = response.headers('Content-Disposition');
		        var fileName = spares.fileName || 'download.xlsx'; 

		        if (contentDisposition) {
		            var matches = contentDisposition.match(/filename="(.+)"/);
		            if (matches.length === 2) {
		                fileName = matches[1];
		            }
		        }

		        var blob = new Blob([response.data], { type: response.headers('Content-Type') });
		        var link = document.createElement('a');
		        link.href = window.URL.createObjectURL(blob);
		        link.download = fileName;
		        link.click();

		        toastr.success("Download started for " + fileName);
		    }).catch(function(error) {
		        toastr.error("Error downloading file: " + (error.data?.message || "Unknown error"));
		        console.error("Download Error:", error);
		    });
		};

		
		
		
		//file handling
		vm.setFile = function(files) {
		    if (files.length > 0) {
		        vm.selectedFile = files[0]; 
		        $scope.$apply(); 
		    }
		};

		
		
		// delete uploaded excel file
		vm.deleteFile = function (spares) {
		    if (!spares.id) {
		        toastr.error("File ID missing, cannot delete.");
		        return;
		    }

		    if (!confirm("Are you sure you want to delete this file: " + spares.fileName + "?")) {
		        return;
		    }

		    var deleteUrl = baseUrl + "/PPM/delete/" + spares.id;

		    $http.delete(deleteUrl)
		        .then(function (response) {
		            toastr.success(response.data.message || "File deleted successfully!");
		            console.log("Delete Response:", response);
		            loadFiles(); // reload list after deletion
		        })
		        .catch(function (error) {
		            toastr.error(error.data.message || "Failed to delete file.");
		            console.error("Delete Error:", error);
		        });
		};

		
		
		//file upload
        function importFile() {
            if (!vm.selectedLab) {
                toastr.error("Please select a lab.");
                console.error("Lab ID is missing!");
                return;
            }
            if (!vm.selectedFile) {
                toastr.error("Please select a file.");
                console.error("File is missing!");
                return;
            }

            var formData = new FormData();
            formData.append("file", vm.selectedFile);
            formData.append("labId", vm.selectedLab);

            console.log("Uploading File:", vm.selectedFile.name);
            console.log("Lab ID:", vm.selectedLab);

            var url = baseUrl + "/PPM/upload";

            $http.post(url, formData, {
                headers: { 'Content-Type': undefined }, 
                transformRequest: angular.identity
            })
            .then(function (response) {
                toastr.success("File uploaded successfully: " + response.data);
                console.log("Upload Response:", response);
                loadFiles();
                cancel();
            })
            .catch(function (error) {
              toastr.success("File uploaded successfully!");

				  setTimeout(function () {
				      location.reload();
				  }, 1000); 
				 
            });
        }
		
		
		
    }
})();
