(function() {
	'use strict';

	angular.module('myApp.UnitLocation').controller('UnitLocationController', UnitLocationController);

	UnitLocationController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr', 'genericFactory', 'localStorageService'];

	function UnitLocationController($scope, ApiEndpoint, $http, toastr, genericFactory, localStorageService) {
		const baseUrl = ApiEndpoint.url;
		var assetUrl = staticUrl + "/assetInventory";
			var uploadUrl = staticUrl + "/upload";
		var deviceUrl = staticUrl + "/device";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			add: add,
			cancel: cancel,
			ok: ok,
			deletLocation: deletLocation,
			edit: edit,
			upload: upload,
			uploadNew:uploadNew,
		


		});
      $scope.handleFileSelect = (files)=>{
			$scope.selectedFile = files[0];
			console.log("File: ", $scope.selectedFile);
		}
		(function activate() {
			loadUnitLocation();
		})();
		
		function uploadNew(){
			var file = $scope.selectedFile;
			console.log("File: ", file);

			if (!file) {
				toastr.error("Please select file");
				return;
			}
			   var url = uploadUrl + "/uploadUnitLocation";

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
						
						loadUnitLocation();
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

		function edit(unitLocation) {
			$scope.addNewTab = true;
			$scope.uploadTab=false;
			$scope.unitLocation = unitLocation;
		}
		function loadUnitLocation() {

			var msg = ""
			var url = deviceUrl + "/getAllUnitLocation";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.unitLocations = response.data;
				console.log("machines : " + JSON.stringify(vm.unitLocations))

			});


		}
		function deletLocation(unitLocation) {
			
				console.log("Delete : .............." )
			var msg = ""
			var url = deviceUrl + "/deleteUnitLocation";
			genericFactory.add(msg, url, unitLocation).then(function(response) {
				vm.responce = response.data;
				loadUnitLocation();
				$scope.addNewTab = false;
				console.log("responce : " + JSON.stringify(vm.responce))

			});
		}
		function cancel() {
			$scope.addNewTab = false;
			$scope.uploadTab=false;
		}
		function ok(unitLocation) {
			if (unitLocation == undefined || unitLocation.plantName == "") {
				$scope.plantNameErr = true;
				return;
			} else {
				$scope.plantNameErr = false;
			}
			if (unitLocation.locationName == undefined || unitLocation.locationName == "") {
				$scope.locationNameErr = true;
				return;
			} else {
				$scope.locationNameErr = false;
			}
			if (unitLocation.locationName == undefined || unitLocation.locationName == "") {
				$scope.locationNameErr = true;
				return;
			} else {
				$scope.locationNameErr = false;
			}
			unitLocation.active = 1;
			unitLocation.addedBy = userDetail.firstName + " " + userDetail.lastName
			unitLocation.addedDate = new Date();
			var msg = ""
			var url = deviceUrl + "/addUnitLocation";
			genericFactory.add(msg, url, unitLocation).then(function(response) {
				vm.responce = response.data;
				loadUnitLocation();
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
		}


	}
})();
