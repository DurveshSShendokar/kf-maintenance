(function() {
	'use strict';

	angular.module('myApp.AlertConfiguration').controller('AlertConfigurationController', AlertConfigurationController);

	AlertConfigurationController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr', 'genericFactory', 'localStorageService'];

	function AlertConfigurationController($scope, ApiEndpoint, $http, toastr, genericFactory, localStorageService) {
		const baseUrl = ApiEndpoint.url;
		var alertUrl = staticUrl + "/alert";
		var energyUrl = staticUrl + "/energy";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			add: add,
			cancel: cancel,
			ok: ok,
			deletAlert: deletAlert,
			edit: edit,
			upload: upload,
			uploadNew: uploadNew,
			setRegister:setRegister,
			perPage: 10,
			pageNo: 1,
			total_count: 100,
			change:change


		});

		$scope.handleFileSelect = (files) => {
			$scope.selectedFile = files[0];
			console.log("File: ", $scope.selectedFile);
		}
		(function activate() {
			loadAlertConfigs();
			$scope.editFrom=false
		})();
		function change(){
			console.log("changing.........")
			$scope.editFrom=false	
		}
		function setRegister(register){
			$scope.alertConfig.registerNo=register.registerNo	;
			$scope.alertConfig.registerName=register.registerName	
		}
	function loadRegisters(){
				var msg=""
				 var url =energyUrl+"/getAllRegisters";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.registers= response.data;
				console.log("registers : "+JSON.stringify(vm.registers))
								
			});
			
		}
		$scope.getRegisterName=function(registerNo){
			var msg=""
			var 	url = energyUrl + "/getRegisterByName?registerNo=" +registerNo;
			genericFactory.getAll(msg, url).then(function(response) {
				vm.register = response.data;
				$scope.alertConfig.registerName=vm.register.data.registerName;
			if(vm.register.code==500){
				$scope.invalidErr=true	
			}
			
			if(vm.register.code==200){     $scope.invalidErr=false	     
				 $scope.alertConfig.registerName=vm.register.data.registerName;
			}
			console.log("Page No  " + JSON.stringify(vm.register))
			});
		}
		$scope.searchByPagination = function(search) {
			loadAlertConfigs();

		}

		$scope.pagination = {
			current: 1
		};

		// page changed 
		$scope.pageChanged = function(pageNo) {
			vm.pageNo = pageNo;
			console.log("Page No  " + pageNo)
			loadAlertConfigs();

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

		function edit(alertConfig) {
			$scope.addNewTab = true;
			$scope.uploadTab = false;
			$scope.editFrom=true;
			$scope.alertConfig = alertConfig;
			console.log("alertConfig : " + JSON.stringify(alertConfig))
		loadRegisters()
		}
		function loadAlertConfigs() {

			var msg = ""
			var url;
			var urlCount;
			console.log("SERC TEXT " + vm.serachText);
			if (vm.serachText == "" || vm.serachText == undefined) {
				url = alertUrl + "/getAlertConfigurationByLimit?pageNo=" + vm.pageNo + "&perPage=" + vm.perPage;
				urlCount = alertUrl + "/geAlertConfigurationCount";
			} else {
				url = alertUrl + "/getAlertConfigurationCountAndSearch?searchText=" + vm.serachText + "&pageNo=" + vm.pageNo + "&perPage=" + vm.perPage;
				urlCount = alertUrl + "/getCountAlertConfigurationBySearch?searchText=" + vm.serachText;
			}




			console.log("URL  : " + url)
			console.log("URL  : " + urlCount)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.alerts = response.data;


			});


			genericFactory.getAll(msg, urlCount).then(function(response1) {
				vm.total_count = response1.data;


			});


		}
		function deletAlert(alertConfig) {

	
			var msg = ""
			var url = alertUrl + "/deleteAlertConfiguration";
			genericFactory.add(msg, url, alertConfig).then(function(response) {
				vm.responce = response.data;
				loadAlertConfigs();
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
		function ok(alertConfig) {
			if (alertConfig == undefined || alertConfig.alertFor == "") {
				$scope.aletForErr = true;
				return;
			} else {
				$scope.aletForErr = false;
			}
			if (alertConfig.registerNo == undefined || alertConfig.registerNo == "") {
				$scope.registerErr = true;
				return;
			} else {
				$scope.registerErr = false;
			}
			
			if (alertConfig.fromRange == undefined || alertConfig.fromRange == "") {
				$scope.fromRangeErr = true;
				return;
			} else {
				$scope.fromRangeErr = false;
			}
			if (alertConfig.toRange == undefined || alertConfig.toRange == "") {
				$scope.toRangeErr = true;
				return;
			} else {
				$scope.toRangeErr = false;
			}
                                   			console.log("alertConfig : " + JSON.stringify(alertConfig))                                                                                                                                                 
			if($scope.invalidErr){return;}
		
			var msg = ""
			var url = alertUrl + "/addAlertConfiguration";
			
			console.log("alertConfig : " + JSON.stringify(alertConfig))
			genericFactory.add(msg, url, alertConfig).then(function(response) {
				vm.responce = response.data;
				loadAlertConfigs();
				$scope.alertConfig = {}
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
				$scope.editFrom=false	
				$scope.alertConfig = {}
			$scope.addNewTab = true;
			loadRegisters()
		}
	

	}
})();
