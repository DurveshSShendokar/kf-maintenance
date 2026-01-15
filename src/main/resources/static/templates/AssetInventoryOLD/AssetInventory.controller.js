(function() {
    'use strict';

    angular.module('myApp.AssetInventory').controller('AssetInventoryController', AssetInventoryController);

    AssetInventoryController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr', 'genericFactory'];

    function AssetInventoryController($scope, ApiEndpoint, $http, toastr, genericFactory) {
        const baseUrl = ApiEndpoint.url;
		var assetUrl = staticUrl+"/assetInventory";
        var vm = angular.extend(this, {
            assets: [],
            categories: [],
            add: add,
            delet: delet,
            ok: ok,
            addNew: addNew,
            cancel: cancel,
            resetFormFields: resetFormFields,
            viewQr:viewQr,
            uploadNew: uploadNew,
			upload:upload,
			print:print,
             showChooseOption: true,
            export: exportExcel 
           
        });

        (function activate() {
            $scope.assetInventory = {};
            loadAssets();
            loadCategories();
            loadLocations();
            loadLabs();
            $scope.uploadTab=false;
           	$scope.qrcodeData="AssetID";
	
            $scope.addNewTab = false;
        })();
        
        function print(){
			var windowContent = '';
			console.log("PRIting ..........")
			var canvas = document.getElementById('printQR').querySelector('canvas');
			var dataURL = canvas.toDataURL();
			//windowContent += '<div style="page-break-after: always"><div style="width:350px;height:180px;border:1px solid"><span style="height:45px;witdh:100px; margin: -75px 0px 0px 5px ;important;"><span style="height:100px;witdh:100px;padding:5px;margin-left:20px" src="'  + dataUrl + '</span></div>';
			console.log("dataUrl  :: " + dataURL)
			//12 X 50 
			console.log("SIZE  IN 12 50 cut ")
			//	windowContent+=	'<div style="page-break-after: always ;important;margin-left:20px;margin-top:40px" ><div  "><span style=" font-family: Arial"><img src="'  + dataURL + '"></div><span>Media Code :'+mediaprep.media.mediaName+', <br/>Batch Number: '+batchNo+',<br/>Volume :'+mediaprep.volume+' </span></div>'
			//windowContent+=	'<div style="page-break-after: always ;important;margin-left:20px;margin-top:40px" ><span style=" font-family: Arial;font-size: 50px;"><b>DATTATRAY BODHALE<b><span></div>'

			windowContent += '<div style="padding: 5px;display: inline-block;solid;margin-left:10px;"><div class="col-xs-6"  style="display: inline-block;"><img src="' + dataURL + '" style="margin-left:25px;"></div></div><div style="display: inline-block; solid;"><div class="col-xs-6"  style="display: inline-block;"><span style="font-family: Arial; font-size: 25px;margin-top:-10%"><img src="img/kf-bioplantr.png" class="logo" style="    width: 280px;"></span></br> <span style=" font-family: Arial;font-size: 25px;padding:4px;margin-bottom:20px"></span><br><br><br><br><span margin-top:35px;></span></div></div>';




			var popupWinindow = window.open('', '_blank', 'width=600,height=700,scrollbars=no,menubar=no,toolbar=no,location=no,status=no,titlebar=no');
			popupWinindow.document.write('<html><body onload="window.print()">' + windowContent + '</html>');
			popupWinindow.document.write('<style> @page {  margin: 15;} </style>');
			popupWinindow.document.close();
			//	savePrintedCode()
		}
		function viewQr (asset){
		
			 
			 $scope.viewQrTab=true;
			$scope.addNewTab=false;
			$scope.uploadTab=false;
			$scope.qrcodeData=asset.equipmentId;
			console.log("hello Log : "+JSON.stringify(asset))
		}
        function cancel() {
            $scope.addNewTab = false;
        }
        
        function upload(){
			$scope.uploadTab=true;
			 $scope.showChooseOption = true;
			$scope.addNewTab=false;
			$scope.viewQrTab=false;
		}

        function addNew() {
            $scope.addNewTab = true;
            $scope.uploadTab=false;
            resetFormFields();
        }
        
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
						toastr.success('Uploaded....', 'Succesful !!',{ timeOut: 10000 });	
						
						loadAssets();
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

        function loadCategories() {
            var url = baseUrl + "/category/list";
            $http.get(url)
                .then(function(response) {
                    vm.categories = response.data;
                    console.log("Fetched categories:", vm.categories);
                })
                .catch(function(error) {
                    console.error("Error fetching categories:", error);
                });
        }

        function loadLocations() {
            var url = assetUrl + "/locations";
            $http.get(url)
                .then(function(response) {
                    vm.locations = response.data;
                    console.log("Fetched locations:", vm.locations);
                })
                .catch(function(error) {
                    console.error("Error fetching locations:", error);
                });
        }

        function loadLabs() {
            var url = assetUrl + "/labs";
            $http.get(url)
                .then(function(response) {
                    vm.labs = response.data;
                    console.log("Fetched labs:", vm.labs);
                })
                .catch(function(error) {
                    console.error("Error fetching labs:", error);
                });
        }

       
        function delet(assetInventory) {
            var url = baseUrl + "/assetInventory/delete/" + assetInventory.asset_inventory_id;
            console.log("Delete URL:", url);
            $http.delete(url)
                .then(function(response) {
                    toastr.success('Deleted Successfully!');
                    loadAssets();
                })
                .catch(function(error) {
                    toastr.error('Error deleting asset');
                    console.error("Error deleting asset:", error);
                });
        }
        
        
       
       
		


        function ok(assetInventory) {
            var url = baseUrl + "/assetInventory/addAssetInventory";
            var msg = "";

            // Check if all fields are filled
            if (!assetInventory.machine || !assetInventory.category || !assetInventory.location ||
                !assetInventory.lab || !assetInventory.equipmentId || !assetInventory.model) {
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

        // Function to reset form fields
        function resetFormFields() {
            $scope.AssetInventory = {};
        }

        // Function to check if the equipment ID is unique
        function isUniqueEquipmentId(equipmentId, currentId) {
            return !vm.assets.some(function(asset) {
                return asset.equipmentId === equipmentId && asset.asset_inventory_id !== currentId;
            });
        }

        function add(AssetInventory) {
            $scope.addNewTab = true;
            $scope.AssetInventory = Object.assign({}, AssetInventory);
        }

     
    }
})();
