(function() {
	'use strict';

	angular.module('myApp.EnergyMeterRegister').controller('EnergyMeterRegisterController', EnergyMeterRegisterController);

	EnergyMeterRegisterController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr', 'genericFactory', 'localStorageService'];

	function EnergyMeterRegisterController($scope, ApiEndpoint, $http, toastr, genericFactory, localStorageService) {
		const baseUrl = ApiEndpoint.url;
		var uploadUrl = staticUrl + "/upload";
		var energyUrl = staticUrl + "/energy";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			add: add,
			cancel: cancel,
			ok: ok,
			deletMeterRegister: deletMeterRegister,
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
			loadEnergyMeterRegister();
		})();
		$scope.searchByPagination = function(search) {
			loadEnergyMeterRegister();

		}

		$scope.pagination = {
			current: 1
		};

		// page changed 
		$scope.pageChanged = function(pageNo) {
			vm.pageNo = pageNo;
			console.log("Page No  " + pageNo)
			loadEnergyMeterRegister();

		}

		function uploadNew() {
			var file = $scope.selectedFile;
			console.log("File: ", file);

			if (!file) {
				toastr.error("Please select file");
				return;
			}
			var url = uploadUrl + "/uploadModuleRegister";

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

						loadEnergyMeterRegister();
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

		function edit(meterRegister) {
			$scope.addNewTab = true;
			$scope.uploadTab = false;
			$scope.meterRegister = meterRegister;
			loadEnergyMeterModules();
		}
		function loadEnergyMeterRegister() {

			var msg = ""
			var url;
			var urlCount;
			console.log("SERC TEXT " + vm.serachText);
			if (vm.serachText == "" || vm.serachText == undefined) {
				url = energyUrl + "/getEnergyMeterRegisterByLimit?pageNo=" + vm.pageNo + "&perPage=" + vm.perPage;
				urlCount = energyUrl + "/getCountOfEnergyMeterRegister";
			} else {
				url = energyUrl + "/getEnergyMeterRegisterByLimitAndSearch?searchText=" + vm.serachText + "&pageNo=" + vm.pageNo + "&perPage=" + vm.perPage;
				urlCount = energyUrl + "/getEnergyMeterRegisterCountByLimitAndSearch?searchText=" + vm.serachText;
			}




			console.log("URL  : " + url)
			console.log("URL  : " + urlCount)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.meterRegisters = response.data;


			});


			genericFactory.getAll(msg, urlCount).then(function(response1) {
				vm.total_count = response1.data;


			});


		}
		function deletMeterRegister(meterRegister) {


			var msg = ""
			var url = energyUrl + "/deleteEnergyMeterRegister";
			genericFactory.add(msg, url, meterRegister).then(function(response) {
				vm.responce = response.data;
				loadEnergyMeterRegister();
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
		function ok(meterRegister) {
			if (meterRegister == undefined || meterRegister.module == "") {
				$scope.moduleErr = true;
				return;
			} else {
				$scope.moduleErr = false;
			}
			if (meterRegister.registerName == undefined || meterRegister.registerName == "") {
				$scope.registerNameErr = true;
				return;
			} else {
				$scope.registerNameErr = false;
			}
			if (meterRegister.registerNo == undefined || meterRegister.registerNo == "") {
				$scope.registerErr = true;
				return;
			} else {
				$scope.registerErr = false;
			}
			if (meterRegister.registerFormat == undefined || meterRegister.registerFormat == "") {
				$scope.formatErr = true;
				return;
			} else {
				$scope.formatErr = false;
			}


		
			var msg = ""
			var url = energyUrl + "/addEnergyMeterRegister";
			genericFactory.add(msg, url, meterRegister).then(function(response) {
				vm.responce = response.data;
				loadEnergyMeterRegister();
				$scope.meterRegister = {}
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
			loadEnergyMeterModules();
			loadRegisterCoversionUnit();
			loadRegisterType();
		}
		function loadRegisterCoversionUnit(){
				var msg=""
				 var url =energyUrl+"/getAllRegisterCoversionUnit";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.conversionUnits= response.data;
				console.log("conversionUnits : "+JSON.stringify(vm.conversionUnits))
								
			});
		}
		function loadRegisterType(){
				var msg=""
				 var url =energyUrl+"/getAllRegisterType";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.registerTypes= response.data;
				console.log("registerTypes : "+JSON.stringify(vm.registerTypes))
								
			});
		}
		function loadEnergyMeterModules(){
				var msg=""
				 var url =energyUrl+"/getAllEnergyMeterModule";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.modules= response.data;
				console.log("modules : "+JSON.stringify(vm.modules))
								
			});
			
		}

	}
})();
