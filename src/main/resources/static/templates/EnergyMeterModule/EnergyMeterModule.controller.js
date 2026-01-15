(function() {
	'use strict';

	angular.module('myApp.EnergyMeterModule').controller('EnergyMeterModuleController', EnergyMeterModuleController);

	EnergyMeterModuleController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr', 'genericFactory', 'localStorageService'];

	function EnergyMeterModuleController($scope, ApiEndpoint, $http, toastr, genericFactory, localStorageService) {
		const baseUrl = ApiEndpoint.url;
		var assetUrl = staticUrl + "/assetInventory";
		var uploadUrl = staticUrl + "/upload";
		var deviceUrl = staticUrl + "/device";
		var energyUrl = staticUrl + "/energy";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			add: add,
			cancel: cancel,
			ok: ok,
			deletMeterModule: deletMeterModule,
			edit: edit,
			upload: upload,
			uploadNew: uploadNew,
			perPage: 10,
			pageNo: 1,
			total_count: 100


		});
		$scope.handleFileSelect = (files) => {
			$scope.selectedFile = files[0];
			console.log("File: ", $scope.selectedFile);
		}
		(function activate() {
			loadEnergyMeterModule();
		})();
		$scope.searchByPagination = function(search) {
			loadEnergyMeterModule();

		}

		$scope.pagination = {
			current: 1
		};

		// page changed 
		$scope.pageChanged = function(pageNo) {
			vm.pageNo = pageNo;
			console.log("Page No  " + pageNo)
			loadEnergyMeterModule();

		}

		function uploadNew() {
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

						loadEnergyMeterModule();
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

		function edit(meterModule) {
			$scope.addNewTab = true;
			$scope.uploadTab = false;
			$scope.meterModule = meterModule;
		}
		function loadEnergyMeterModule() {

			var msg = ""
			var url;
			var urlCount;
			console.log("SERC TEXT " + vm.serachText);
			if (vm.serachText == "" || vm.serachText == undefined) {
				url = energyUrl + "/getEnergyMeterModuleByLimit?pageNo=" + vm.pageNo + "&perPage=" + vm.perPage;
				urlCount = energyUrl + "/getCountOfEnergyMeterModule";
			} else {
				url = energyUrl + "/getEnergyMeterModuleByLimitAndSearch?searchText=" + vm.serachText + "&pageNo=" + vm.pageNo + "&perPage=" + vm.perPage;
				urlCount = energyUrl + "/getEnergyMeterModuleCountByLimitAndSearch?searchText=" + vm.serachText;
			}




			console.log("URL  : " + url)
			console.log("URL  : " + urlCount)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.meterModules = response.data;


			});


			genericFactory.getAll(msg, urlCount).then(function(response1) {
				vm.total_count = response1.data;


			});


		}
		function deletMeterModule(meterModule) {


			var msg = ""
			var url = energyUrl + "/deleteEnergyMeterModule";
			genericFactory.add(msg, url, meterModule).then(function(response) {
				vm.responce = response.data;
				loadEnergyMeterModule();
				if (vm.responce.code == 200) {
					toastr.success(vm.responce.message);
				} else {
					toastr.error(vm.responce.message);
				}
				console.log("responce : " + JSON.stringify(vm.responce))

			});
		}
		function cancel() {
			$scope.addNewTab = false;
			$scope.uploadTab = false;
		}
		function ok(meterModule) {
			if (meterModule == undefined || meterModule.companyName == "") {
				$scope.companyErr = true;
				return;
			} else {
				$scope.companyErr = false;
			}
			if (meterModule.moduleNo == undefined || meterModule.moduleNo == "") {
				$scope.moduleErr = true;
				return;
			} else {
				$scope.moduleErr = false;
			}
			


		
			var msg = ""
			var url = energyUrl + "/addEnergyMeterModule";
			genericFactory.add(msg, url, meterModule).then(function(response) {
				vm.responce = response.data;
				loadEnergyMeterModule();
				$scope.meterModule = {}
				$scope.addNewTab = false;
				console.log("responce : " + JSON.stringify(vm.responce))
				if (vm.responce.code == 200) {
					toastr.success(vm.responce.message);
				} else {
					toastr.error(vm.responce.message);
				}
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
			$scope.uploadTab = false;
		}


	}
})();
