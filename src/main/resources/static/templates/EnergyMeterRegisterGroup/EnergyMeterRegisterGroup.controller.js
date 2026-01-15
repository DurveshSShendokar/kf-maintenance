(function() {
	'use strict';

	angular.module('myApp.EnergyMeterRegisterGroup').controller('EnergyMeterRegisterGroupController', EnergyMeterRegisterGroupController);

	EnergyMeterRegisterGroupController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr', 'genericFactory', 'localStorageService'];

	function EnergyMeterRegisterGroupController($scope, ApiEndpoint, $http, toastr, genericFactory, localStorageService) {
		const baseUrl = ApiEndpoint.url;
		var uploadUrl = staticUrl + "/upload";
		var energyUrl = staticUrl + "/energy";
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var vm = angular.extend(this, {
			groupItems:[],
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
		remove:remove,
			view:view,
			loadRegisters:loadRegisters

		});
	
		(function activate() {
			loadGroups();
		})();
			
			function remove(index){
				
				console.log(" groupItems "+JSON.stringify(vm.groupItems[index]));
				var id=vm.groupItems[index].id
					console.log(" id "+id );
				if(vm.groupItems[index].id==undefined){
					console.log("REMOVE")
					vm.groupItems.splice(index,1)
				}else{
					console.log("Delete from DB")
					vm.groupItems.splice(index,1)
					var msg=""
						console.log("ite  : "+JSON.stringify(vm.groupItems[index]))
				 var url =energyUrl+"/getDeleteGroupItem?id="+id;
				 console.log("url  : "+url)
				genericFactory.getAll(msg,url).then(function(response) {
				vm.modules= response.data;
				console.log("registers : "+JSON.stringify(vm.modules))
								
			});
				}
				
				
			}
		function view(group){
			$scope.viewT=true
			loadRegisters();
			loadModules();
				var msg=""
				$scope.editFrm=true;
			console.log("SSS "+JSON.stringify(group))
			var	url = energyUrl + "/getAllEnergyMeterRegisterGroupItemsByGroup?groupId="+group.id;
			console.log("url "+url)
				genericFactory.getAll(msg, url).then(function(response) {
				vm.groupItems = response.data;
				$scope.group=group;
				angular.forEach(vm.groupItems, function(items) {
					var obj={};
					console.log("items 1  "+JSON.stringify(items))
  				obj.registerName=items.registerName
  				obj.registerNo=items.registerNo
  				items.register=obj
  					items.editFrm=true;
				});
				$scope.group.noOfRegister=parseInt(group.noOfRegister)
				console.log("SSS 1  "+JSON.stringify(vm.groupItems))
				$scope.addNewTab = true;

			});


		}
		
	
		$scope.searchByPagination = function(search) {
			loadGroups();

		}
		$scope.updateRow=function(noOfRegister){
			//vm.groupItems=[]
			for(var i=1;i<=noOfRegister;i++){
				var groupItem={};
				groupItem.registerName="";
				groupItem.registerNo="";
				vm.groupItems.push(groupItem)
				console.log(" I "+i)
			}
		}
		$scope.pagination = {
			current: 1
		};

		// page changed 
		$scope.pageChanged = function(pageNo) {
			vm.pageNo = pageNo;
			console.log("Page No  " + pageNo)
			loadGroups();

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

						loadGroups();
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
$scope.update=function (index){
			vm.groupItems[index].editFrm=false;
}
		function edit(group) {
			$scope.addNewTab = true;
			$scope.uploadTab = false;
			$scope.editFrm=true;
				loadRegisters();
				loadModules();
				var msg=""
				
			console.log("SSS "+JSON.stringify(group))
			var	url = energyUrl + "/getAllEnergyMeterRegisterGroupItemsByGroup?groupId="+group.id;
			console.log("url "+url)
				genericFactory.getAll(msg, url).then(function(response) {
				vm.groupItems = response.data;
				$scope.group=group;
				angular.forEach(vm.groupItems, function(items) {
					var obj={};
					console.log("items 1  "+JSON.stringify(items))
  				obj.registerName=items.registerName
  				obj.registerNo=items.registerNo
  				items.register=obj
  				items.editFrm=true;
				});
				$scope.group.noOfRegister=parseInt(group.noOfRegister)
				console.log("SSS 1  "+JSON.stringify(vm.groupItems))
				$scope.addNewTab = true;

			});

		}
		function loadGroups() {

			var msg = ""
			var url;
			var urlCount;
			console.log("SERC TEXT " + vm.serachText);
			if (vm.serachText == "" || vm.serachText == undefined) {
				url = energyUrl + "/getEnergyMeterRegisterGroupByLimit?pageNo=" + vm.pageNo + "&perPage=" + vm.perPage;
				urlCount = energyUrl + "/getCountOfEnergyMeterRegisterGroup";
			} else {
				url = energyUrl + "/getEnergyMeterRegisterGroupByLimitAndSearch?searchText=" + vm.serachText + "&pageNo=" + vm.pageNo + "&perPage=" + vm.perPage;
				urlCount = energyUrl + "/getEnergyMeterRegisterGroupCountByLimitAndSearch?searchText=" + vm.serachText;
			}




			console.log("URL  : " + url)
			console.log("URL  : " + urlCount)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.groups = response.data;


			});


			genericFactory.getAll(msg, urlCount).then(function(response1) {
				vm.total_count = response1.data;


			});


		}
		function deletMeterRegister(group) {


			var msg = ""
			var url = energyUrl + "/deleteGroup";
			genericFactory.add(msg, url, group).then(function(response) {
				vm.responce = response.data;
				loadGroups();
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
		function ok(group) {
			if (group == undefined || group.module == "") {
				$scope.moduleErr = true;
				return;
			} else {
				$scope.moduleErr = false;
			}
			if (group.groupName == ""||group.groupName == undefined) {
				$scope.groupErr = true;
				return;
			} else {
				$scope.groupErr = false;
			}
			if (group.noOfRegister == undefined || group.noOfRegister == "") {
				$scope.noRegErr = true;
				return;
			} else {
				$scope.noRegErr = false;
			}
			if (vm.groupItems.length==0) {
				$scope.listErr = true;
				return;
			} else {
				$scope.listErr = false;
			}
			
			angular.forEach(vm.groupItems, function(items) {
  				items.registerName=items.register.registerName
  				items.registerNo=items.register.registerNo
				});
			
			
			
			group.items=vm.groupItems;


			console.log("group : " + JSON.stringify(group));
			var msg = ""
			var url = energyUrl + "/addGroup";
			genericFactory.add(msg, url, group).then(function(response) {
				vm.responce = response.data;
				loadGroups();
				vm.groupItems=[],
				$scope.group = {}
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
			//loadRegisters();
			loadModules();
		}
		function loadRegisters(moduleId){
				var msg=""
				 var url =energyUrl+"/getAllRegistersByModuleId?moduleId="+moduleId;
				 console.log("URL "+url)
				genericFactory.getAll(msg,url).then(function(response) {
				vm.registers= response.data;
				console.log("registers : "+JSON.stringify(vm.registers))
								
			});
			
		}
		function loadModules(){
				var msg=""
				 var url =energyUrl+"/getAllEnergyMeterModule";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.modules= response.data;
				console.log("registers : "+JSON.stringify(vm.modules))
								
			});
			
		}

	}
})();
