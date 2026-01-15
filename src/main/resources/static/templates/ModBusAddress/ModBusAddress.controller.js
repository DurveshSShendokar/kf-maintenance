(function() {
	'use strict';

	angular.module('myApp.ModBusAddress').controller('ModBusAddressController', ModBusAddressController);

	ModBusAddressController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr', 'genericFactory', 'localStorageService'];

	function ModBusAddressController($scope, ApiEndpoint, $http, toastr, genericFactory, localStorageService) {
		const baseUrl = ApiEndpoint.url;
		var assetUrl = staticUrl + "/assetInventory";
			var uploadUrl = staticUrl + "/upload";
		var deviceUrl = staticUrl + "/device";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			add: add,
			cancel: cancel,
			ok: ok,
			deletModBusAddresse: deletModBusAddresse,
			edit: edit,
			upload: upload,
			uploadNew:uploadNew,
		


		});
      $scope.handleFileSelect = (files)=>{
			$scope.selectedFile = files[0];
			console.log("File: ", $scope.selectedFile);
		}
		(function activate() {
			loadModAddress();
		})();
		
		function uploadNew(){
			var file = $scope.selectedFile;
			console.log("File: ", file);

			if (!file) {
				toastr.error("Please select file");
				return;
			}
			   var url = uploadUrl + "/uploadModBusAddress";

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
						
						loadModAddress();
					}else{
						toastr.error('Upload....', 'UnSuccesfull !!');
					}
						$scope.uploadTab=false;
					$scope.uploadMediaPrepTab= false;
				}, function errorCallback(response) {
			    	$('.loading').hide();
					$scope.uploadTab=false;
					toastr.error('Upload....', 'UnSuccesfull !!');
				});
				angular.element("input[type='file']").val(null);
				$scope.selectedFile = null;
				  $scope.showChooseOption = false; 
		}
		function upload(){
			$scope.uploadTab=true;
			$scope.addNewTab = false;
		}

		function edit(modBusAddresse) {
			$scope.addNewTab = true;
			$scope.uploadTab=false;
			$scope.modBusAddresse = modBusAddresse;
		}
		function loadModAddress() {

			var msg = ""
			var url = deviceUrl + "/getAllModBusAddress";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.modBusAddresses = response.data;
				console.log("modBusAddresses : " + JSON.stringify(vm.modBusAddresses))

			});


		}
		function deletModBusAddresse(modBusAddresse) {
			
				console.log("Delete : .............." )
			var msg = ""
			var url = deviceUrl + "/deleteModBusAddresse";
			genericFactory.add(msg, url, modBusAddresse).then(function(response) {
				vm.responce = response.data;
				loadModAddress();
				$scope.addNewTab = false;
				console.log("responce : " + JSON.stringify(vm.responce))

			});
		}
		function cancel() {
			$scope.addNewTab = false;
			$scope.uploadTab=false;
		}
		function ok(modBusAddresse) {
			if (modBusAddresse == undefined || modBusAddresse.name == "") {
				$scope.nameErr = true;
				return;
			} else {
				$scope.nameErr = false;
			}
			if (modBusAddresse.modAddress == undefined || modBusAddresse.modAddress == "") {
				$scope.modAddressErr = true;
				return;
			} else {
				$scope.modAddressErr = false;
			}
			
			modBusAddresse.active = 1;
			modBusAddresse.addedBy = userDetail.firstName + " " + userDetail.lastName
			modBusAddresse.addedDate = new Date();
			var msg = ""
			var url = deviceUrl + "/addModBusAddress";
			genericFactory.add(msg, url, modBusAddresse).then(function(response) {
				vm.responce = response.data;
				loadModAddress();
				$scope.modBusAddresse={}
				$scope.addNewTab = false;
				console.log("responce : " + JSON.stringify(vm.responce))

			});

		}

		function upload() {
			$scope.uploadTab = true;
			$scope.showChooseOption = true;
			$scope.addNewTab = false;
			$scope.viewQrTab = false;
		}


		function delet() {

		}

		function add() {
			$scope.addNewTab = true;
			$scope.uploadTab=false;
		}


	}
})();
