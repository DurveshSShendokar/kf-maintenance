(function() {
	'use strict';

	angular.module('myApp.EnergyMeterMaster').controller('EnergyMeterMasterController', EnergyMeterMasterController);

	EnergyMeterMasterController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr', 'genericFactory', 'localStorageService','$location'];

	function EnergyMeterMasterController($scope, ApiEndpoint, $http, toastr, genericFactory, localStorageService,$location) {
		const baseUrl = ApiEndpoint.url;
		var uploadUrl = staticUrl + "/upload";
		var energyUrl = staticUrl + "/energy";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			add: add,
			cancel: cancel,
			ok: ok,
			deletMeterMaster: deletMeterMaster,
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
			loadEnergyMeterMaster();
		})();
		$scope.goToDasgboard = function(meterMaster) {
		console.log("meterMaster: ", JSON.stringify(meterMaster));
			$location.path('main/GaugeChart/'+meterMaster.id);
		
		
		}
		
		
		$scope.searchByPagination = function(search) {
			loadEnergyMeterMaster();

		}

		$scope.pagination = {
			current: 1
		};

		// page changed 
		$scope.pageChanged = function(pageNo) {
			vm.pageNo = pageNo;
			console.log("Page No  " + pageNo)
			loadEnergyMeterMaster();

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

		function edit(meterMaster) {
			$scope.addNewTab = true;
			$scope.uploadTab = false;
			$scope.meterMaster = meterMaster;
			loadEnergyMeterModules();
			loadControlPanels();
		}
		function loadEnergyMeterMaster() {

			var msg = ""
			var url;
			var urlCount;
			console.log("SERC TEXT " + vm.serachText);
			if (vm.serachText == "" || vm.serachText == undefined) {
				url = energyUrl + "/getEnergyMeterMasterByLimit?pageNo=" + vm.pageNo + "&perPage=" + vm.perPage;
				urlCount = energyUrl + "/getCountOfEnergyMeterMaster";
			} else {
				url = energyUrl + "/getEnergyMeterMasterByLimitAndSearch?searchText=" + vm.serachText + "&pageNo=" + vm.pageNo + "&perPage=" + vm.perPage;
				urlCount = energyUrl + "/getEnergyMeterMasterCountByLimitAndSearch?searchText=" + vm.serachText;
			}




			console.log("URL  : " + url)
			console.log("URL  : " + urlCount)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.meterMasters = response.data;


			});


			genericFactory.getAll(msg, urlCount).then(function(response1) {
				vm.total_count = response1.data;


			});


		}
		function deletMeterMaster(meterMaster) {


			var msg = ""
			var url = energyUrl + "/deleteEnergyMeterMaster";
			genericFactory.add(msg, url, meterMaster).then(function(response) {
				vm.responce = response.data;
				loadEnergyMeterMaster();
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
		function ok(meterMaster) {
			if (meterMaster == undefined || meterMaster.panel == "") {
				$scope.panelErr = true;
				return;
			} else {
				$scope.panelErr = false;
			}
			if (meterMaster.module == undefined || meterMaster.module == "") {
				$scope.moduleErr = true;
				return;
			} else {
				$scope.moduleErr = false;
			}
			if (meterMaster.energyMeterNo == undefined || meterMaster.energyMeterNo == "") {
				$scope.meterNoErr = true;
				return;
			} else {
				$scope.meterNoErr = false;
			}
			


		
			var msg = ""
			var url = energyUrl + "/addEnergyMeterMaster";
			genericFactory.add(msg, url, meterMaster).then(function(response) {
				vm.responce = response.data;
				loadEnergyMeterMaster();
				$scope.meterMaster = {}
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
				loadControlPanels();
		}
		function loadControlPanels(){
				var msg=""
				 var url =energyUrl+"/getAllControlPanels";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.panels= response.data;
				console.log("panels : "+JSON.stringify(vm.panels))
								
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
