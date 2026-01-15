(function() {
	'use strict';

	angular.module('myApp.DeviceModelAddress').controller('DeviceModelAddressController', DeviceModelAddressController);

	DeviceModelAddressController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr', 'genericFactory', 'localStorageService'];

	function DeviceModelAddressController($scope, ApiEndpoint, $http, toastr, genericFactory, localStorageService) {
		const baseUrl = ApiEndpoint.url;
		var assetUrl = staticUrl + "/assetInventory";
			var uploadUrl = staticUrl + "/upload";
		var deviceUrl = staticUrl + "/device";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			addesses:[],
			add: add,
			cancel: cancel,
			ok: ok,
			deletModBusAddresse: deletModBusAddresse,
			edit: edit,
			upload: upload,
			uploadNew:uploadNew,
			newModel:newModel,
		addAddressList:addAddressList,
		deletAddresse:deletAddresse,
		deletModelAddresse:deletModelAddresse


		});
      $scope.handleFileSelect = (files)=>{
			$scope.selectedFile = files[0];
			console.log("File: ", $scope.selectedFile);
		}
		(function activate() {
			loadDeviceModAddress();
			$scope.newModelAdd=false;
		})();
		function deletModelAddresse(deviceModAddress){
			var msg = ""
			var url = deviceUrl + "/deleteDeviceModAddress";
			genericFactory.add(msg, url, deviceModAddress).then(function(response) {
				
				loadDeviceModAddress();
			
				console.log("responce : " + JSON.stringify(vm.responce))

			});
		}
		
		function deletAddresse(address,index){
			vm.addesses.splice(1,index);
		}
		
		function addAddressList(address){
			vm.addesses.push(address);
		}
		function newModel(){
			if($scope.newModelAdd){
				$scope.newModelAdd=false;
			}else{
				$scope.newModelAdd=true;
			}
			
			
		}
		
		
		
		
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
		function loadDeviceModAddress() {

			var msg = ""
			var url = deviceUrl + "/getAllModelAddressMapping";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.deviceModAddress = response.data;
				console.log("deviceModAddress : " + JSON.stringify(vm.deviceModAddress))

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
		function ok() {
			console.log("mapping : " + JSON.stringify(vm.mapping))
				console.log("$scope.addNewTab : " + JSON.stringify($scope.addNewTab))
			if($scope.newModelAdd){
				if (vm.mapping == undefined || vm.mapping.deviceModelName == "") {
				$scope.nameErr = true;
				return;
			} else {
				$scope.nameErr = false;
				vm.mapping.newDeviceMode=true
			}
			}else{
				if (vm.mapping==undefined||vm.mapping.deviceModelName == "") {
				$scope.nameselErr = true;
				return;
			} else {
				$scope.nameselErr = false;
				vm.mapping.newDeviceMode=false
			}
			}
			
			
			if (vm.addesses.length==0) {
				$scope.listErr = true;
				return;
			} else {
				$scope.listErr = false;
			}
			vm.mapping.address=	vm.addesses	
			var msg = ""
			var url = deviceUrl + "/addDeviceModelAddress";
			genericFactory.add(msg, url, vm.mapping).then(function(response) {
				vm.responce = response.data;
				loadModAddress();
				$scope.modBusAddresse={}
				$scope.addNewTab = false;
				console.log("responce : " + JSON.stringify(vm.responce))

			});
			console.log("unitLocation   :", JSON.stringify(unitLocation));

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
			loadModAddress();
			loadDeviceModels();
		}
function loadModAddress() {

			var msg = ""
			var url = deviceUrl + "/getAllModBusAddress";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.modBusAddresses = response.data;
				console.log("modBusAddresses : " + JSON.stringify(vm.modBusAddresses))

			});


		}
function loadDeviceModels() {

			var msg = ""
			var url = deviceUrl + "/getAllDeviceModels";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.deviceModels = response.data;
				console.log("deviceModels : " + JSON.stringify(vm.deviceModels))

			});


		}
	}
})();
