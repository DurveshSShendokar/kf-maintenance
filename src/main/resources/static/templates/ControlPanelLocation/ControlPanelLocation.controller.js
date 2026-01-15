(function() {
	'use strict';

	angular.module('myApp.ControlPanelLocation').controller('ControlPanelLocationController', ControlPanelLocationController);

	ControlPanelLocationController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr', 'genericFactory', 'localStorageService'];

	function ControlPanelLocationController($scope, ApiEndpoint, $http, toastr, genericFactory, localStorageService) {
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
			deletControlPanel: deletControlPanel,
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
			loadControlPanel();
		})();
		$scope.searchByPagination = function(search) {
			loadControlPanel();

		}

		$scope.pagination = {
			current: 1
		};

		// page changed 
		$scope.pageChanged = function(pageNo) {
			vm.pageNo = pageNo;
			console.log("Page No  " + pageNo)
			loadControlPanel();

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

						loadControlPanel();
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

		function edit(controlPanel) {
			$scope.addNewTab = true;
			$scope.uploadTab = false;
			$scope.controlPanel = controlPanel;
		}
		function loadControlPanel() {

			var msg = ""
			var url;
			var urlCount;
			console.log("SERC TEXT " + vm.serachText);
			if (vm.serachText == "" || vm.serachText == undefined) {
				url = energyUrl + "/getControlPanelByLimit?pageNo=" + vm.pageNo + "&perPage=" + vm.perPage;
				urlCount = energyUrl + "/getCountOfControlPanel";
			} else {
				url = energyUrl + "/getControlPanelByLimitAndSearch?searchText=" + vm.serachText + "&pageNo=" + vm.pageNo + "&perPage=" + vm.perPage;
				urlCount = energyUrl + "/getControlPanelCountByLimitAndSearch?searchText=" + vm.serachText;
			}




			console.log("URL  : " + url)
			console.log("URL  : " + urlCount)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.controlPanels = response.data;


			});


			genericFactory.getAll(msg, urlCount).then(function(response1) {
				vm.total_count = response1.data;


			});


		}
		function deletControlPanel(controlLocation) {


			var msg = ""
			var url = energyUrl + "/deleteControlPanel";
			genericFactory.add(msg, url, controlLocation).then(function(response) {
				vm.responce = response.data;
				loadControlPanel();
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
		function ok(controlPanel) {
			if (controlPanel == undefined || controlPanel.plantName == "") {
				$scope.plantNameErr = true;
				return;
			} else {
				$scope.plantNameErr = false;
			}
			if (controlPanel.locationName == undefined || controlPanel.locationName == "") {
				$scope.locationNameErr = true;
				return;
			} else {
				$scope.locationNameErr = false;
			}
			if (controlPanel.panelName == undefined || controlPanel.panelName == "") {
				$scope.panelNameErr = true;
				return;
			} else {
				$scope.panelNameErr = false;
			}


			if (controlPanel.panelIpAddress == undefined || controlPanel.panelIpAddress == "") {
				$scope.ipErr = true;
				return;
			} else {
				$scope.ipErr = false;
			}

			if (controlPanel.panelPort == undefined || controlPanel.panelPort == "") {
				$scope.portErr = true;
				return;
			} else {
				$scope.portErr = false;
			}



			controlPanel.active = 1;
			controlPanel.addedBy = userDetail.firstName + " " + userDetail.lastName
			controlPanel.addedDate = new Date();
			var msg = ""
			var url = energyUrl + "/addControlPanel";
			genericFactory.add(msg, url, controlPanel).then(function(response) {
				vm.responce = response.data;
				loadControlPanel();
				$scope.controlPanel = {}
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
