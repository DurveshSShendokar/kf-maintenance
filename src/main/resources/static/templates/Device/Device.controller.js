(function() {
	'use strict';

	angular.module('myApp.Device').controller('DeviceController', DeviceController);

	DeviceController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr', 'genericFactory', 'localStorageService'];

	function DeviceController($scope, ApiEndpoint, $http, toastr, genericFactory, localStorageService) {
		const baseUrl = ApiEndpoint.url;
		var assetUrl = staticUrl + "/assetInventory";
		var uploadUrl = staticUrl + "/upload";
		var deviceUrl = staticUrl + "/device";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			addNew: addNew,
			cancel: cancel,
			ok: ok,
			deletModBusAddresse: deletModBusAddresse,
			edit: edit,
			upload: upload,
			uploadNew: uploadNew,



		});
		$scope.handleFileSelect = (files) => {
			$scope.selectedFile = files[0];
			console.log("File: ", $scope.selectedFile);
		}
		(function activate() {
			loadDevices();
		})();

		function uploadNew() {
			var file = $scope.selectedFile;
			console.log("File: ", file);

			if (!file) {
				toastr.error("Please select file");
				return;
			}
			var url = uploadUrl + "/uploadDevice";

			$('.loading').show();
			var fd = new FormData();
			fd.append('file', file);

			console.log("URL :: " + url)
			$http.post(url, fd, {
				transformRequest: angular.identity,
				headers: {
					'Content-Type': undefined
				}
			})
				.then(function successCallback(response) {
					var text = response.data.resmessage
					console.log("RESPONCE  " + JSON.stringify(response.data))
					$('.loading').hide();
					if (response.data.code == 200) {
						toastr.success('Uploaded....', 'Succesfully !!', { timeOut: 10000 });

						loadDevices();
					} else {
						toastr.error('Upload....', 'UnSuccesfull !!');
					}
					$scope.uploadTab = false;
					$scope.uploadMediaPrepTab = false;
				}, function errorCallback(response) {
					$('.loading').hide();
					$scope.uploadTab = false;
					toastr.error('Upload....', 'UnSuccesfull !!');
				});
			angular.element("input[type='file']").val(null);
			$scope.selectedFile = null;
			$scope.showChooseOption = false;
		}
		function upload() {
			$scope.uploadTab = true;
			$scope.addNewTab = false;
		}

		function edit(device) {
			$scope.addNewTab = true;
			$scope.uploadTab = false;
			loadDeviceModel();
			loadUnitLocation();
			$scope.device = device;
		}
		function loadDevices() {

			var msg = ""
			var url = deviceUrl + "/getAllDevice";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.devices = response.data;
				console.log("devices : " + JSON.stringify(vm.devices))

			});


		}
		function deletModBusAddresse(device) {

			console.log("Delete : ..............")
			var msg = ""
			var url = deviceUrl + "/deleteModBusAddresse";
			genericFactory.add(msg, url, modBusAddresse).then(function(response) {
				vm.responce = response.data;
				loadDevices();
				$scope.addNewTab = false;
				console.log("responce : " + JSON.stringify(vm.responce))

			});
		}
		function cancel() {
			$scope.addNewTab = false;
			$scope.uploadTab = false;
		}
		function ok(device) {
			console.log("device : " + JSON.stringify(device))
			if (device == undefined || device.location == "") {
				$scope.locationErr = true;
				return;
			} else {
				$scope.locationErr = false;
			}
			if (device.model == undefined || device.model == "") {
				$scope.addressErr = true;
				return;
			} else {
				$scope.addressErr = false;
			}
			
			if (device.deviceName == undefined || device.deviceName == "") {
				$scope.deviceNameErr = true;
				return;
			} else {
				$scope.deviceNameErr = false;
			}
			
			if (device.deviceId == undefined || device.deviceId == "") {
				$scope.deiveIdErr = true;
				return;
			} else {
				$scope.deiveIdErr = false;
			}

			device.active = 1;
			device.addedBy = userDetail.firstName + " " + userDetail.lastName
			device.addedDate = new Date();
			var msg = ""
			var url = deviceUrl + "/addDevices";
			
				console.log("device111 : " + JSON.stringify(device))
			genericFactory.add(msg, url, device).then(function(response) {
				vm.responce = response.data;
				loadDevices();
				$scope.modBusAddresse = {}
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

		function addNew() {
			$scope.addNewTab = true;
			$scope.uploadTab = false;
			loadDeviceModel();
			loadUnitLocation();
		}

		function loadDeviceModel() {

			var msg = ""
			var url = deviceUrl + "/getAllDeviceModels";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.deviceModels = response.data;
				console.log("deviceModels : " + JSON.stringify(vm.deviceModels))

			});


		}
		function loadUnitLocation() {

			var msg = ""
			var url = deviceUrl + "/getAllUnitLocation";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.unitLocations = response.data;
				console.log("machines : " + JSON.stringify(vm.unitLocations))

			});


		}

	}
})();
