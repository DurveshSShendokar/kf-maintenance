(function() {
	'use strict';

	angular.module('myApp.EnergyMeterPriceSlab').controller('EnergyMeterPriceSlabController', EnergyMeterPriceSlabController);

	EnergyMeterPriceSlabController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr', 'genericFactory', 'localStorageService'];

	function EnergyMeterPriceSlabController($scope, ApiEndpoint, $http, toastr, genericFactory, localStorageService) {
		const baseUrl = ApiEndpoint.url;
		var uploadUrl = staticUrl + "/upload";
		var energyUrl = staticUrl + "/energy";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			priceSlabItems:[],
			add: add,
			cancel: cancel,
			ok: ok,
			deletMeterRegister: deletMeterRegister,
			edit: edit,
			upload: upload,
			uploadNew: uploadNew,
			perPage: 10,
			pageNo: 1,
			total_count: 100,
			addRow:addRow,
			remove:remove,
			view:view

		});
	
		(function activate() {
			loadPriceSlab();
		})();
		
		function view(priceSlab){
		
			var msg=""
				
			console.log("SSS "+JSON.stringify(priceSlab))
			var	url = energyUrl + "/getAllConsumptionPriceSlabDetailsById?priceSlabId="+priceSlab.id;
			console.log("url "+url)
				genericFactory.getAll(msg, url).then(function(response) {
				vm.priceSlabItems = response.data;
				$scope.priceSlab=priceSlab;
				console.log("SSS 1  "+JSON.stringify(vm.priceSlabItems))
				$scope.addNewTab = true;

			});

		}
		
		function addRow(){
			var slab={};
			slab.fromRange="";
			slab.toRange=""
			slab.price="";
			vm.priceSlabItems.push(slab);
		}
		function remove(priceSlabItem){
			vm.priceSlabItems.splice(priceSlabItem,1);
		}
		$scope.searchByPagination = function(search) {
			loadPriceSlab();

		}

		$scope.pagination = {
			current: 1
		};

		// page changed 
		$scope.pageChanged = function(pageNo) {
			vm.pageNo = pageNo;
			console.log("Page No  " + pageNo)
			loadPriceSlab();

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

						loadPriceSlab();
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

		function edit(priceSlab) {
			$scope.addNewTab = true;
			$scope.uploadTab = false;
				var msg=""
				
			console.log("SSS "+JSON.stringify(priceSlab))
			var	url = energyUrl + "/getAllConsumptionPriceSlabDetailsById?priceSlabId="+priceSlab.id;
			console.log("url "+url)
				genericFactory.getAll(msg, url).then(function(response) {
				vm.priceSlabItems = response.data;
				$scope.priceSlab=priceSlab;
				console.log("SSS 1  "+JSON.stringify(vm.priceSlabItems))
				$scope.addNewTab = true;

			});

		}
		function loadPriceSlab() {

			var msg = ""
			var url;
			var urlCount;
			console.log("SERC TEXT " + vm.serachText);
			if (vm.serachText == "" || vm.serachText == undefined) {
				url = energyUrl + "/getConsumptionPriceSlabByLimit?pageNo=" + vm.pageNo + "&perPage=" + vm.perPage;
				urlCount = energyUrl + "/getCountOfConsumptionPriceSlab";
			} else {
				url = energyUrl + "/getConsumptionPriceSlabByLimitAndSearch?searchText=" + vm.serachText + "&pageNo=" + vm.pageNo + "&perPage=" + vm.perPage;
				urlCount = energyUrl + "/getConsumptionPriceSlabCountByLimitAndSearch?searchText=" + vm.serachText;
			}




			console.log("URL  : " + url)
			console.log("URL  : " + urlCount)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.priceSlabs = response.data;


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
				loadPriceSlab();
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
		function ok(priceSlab) {
			if (priceSlab == undefined || priceSlab.priceSlabId == "") {
				$scope.slabIsErr = true;
				return;
			} else {
				$scope.slabIsErr = false;
			}
			if (priceSlab.effectiveFrom == undefined || priceSlab.effectiveFrom == "") {
				$scope.effectiveErr = true;
				return;
			} else {
				$scope.effectiveErr = false;
			}
			if (vm.priceSlabItems.length==0) {
				$scope.listErr = true;
				return;
			} else {
				$scope.listErr = false;
			}
			priceSlab.list=vm.priceSlabItems;


		
			var msg = ""
			var url = energyUrl + "/addPriceSlab";
			genericFactory.add(msg, url, priceSlab).then(function(response) {
				vm.responce = response.data;
				loadPriceSlab();
				vm.priceSlabItems = []
				$scope.priceSlab={}
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
