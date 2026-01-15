(function() {
	'use strict';

	angular.module('myApp.uploads').controller('UploadsController', UploadsController);

	UploadsController.$inject = [ '$state', '$uibModal','genericFactory', '$log','$scope', 'toastr' , 'localStorageService', 'ApiEndpoint','$window','$rootScope','fileUpload','$http'];
	
	/* @ngInject */
	function UploadsController($state,$uibModal, genericFactory, $log, $scope, toastr, localStorageService, ApiEndpoint,$window,$rootScope,fileUpload,$http) {
		var commonUrl = ApiEndpoint.url+"common";
		var supplierUrl = ApiEndpoint.url+"supplier";
		var materialUrl = ApiEndpoint.url+"material";
		var employeeUrl = ApiEndpoint.url+"employee";
		var uploadsUrl = ApiEndpoint.url+"upload";
		var spareUrl = ApiEndpoint.url+"spare";
		var vm = angular.extend(this, {
			desk:desk,
			project:project,
			room:room,
			department:department,
			brand:brand,
			branch:branch,
			supplier:supplier,
			material:material,
			employee:employee,
			cancel:cancel,
			
			
			addDesk:addDesk,
			changeDeskStatus:changeDeskStatus,
			
			addProject:addProject,
			changeStastusProject:changeStastusProject,
			
			addRoom:addRoom,
			changeStastusRoom:changeStastusRoom,
			
			addDepartment:addDepartment,
			changeStastusDepartment:changeStastusDepartment,
			
			addBrand:addBrand,
			changeBrandStatus:changeBrandStatus,
			
			
			addBranch:addBranch,
			changeBranchStatus:changeBranchStatus,
			
			
			designation:designation,
			addDesignation:addDesignation,
			changeDesignationStatus:changeDesignationStatus,
			
			
			workLocation:workLocation,
			addWorkLocation:addWorkLocation,
			changeWorkLocationStatus:changeWorkLocationStatus,
			
			costCenter:costCenter,
			addCostCenter:addCostCenter,
			changeCostCenterStatus:changeCostCenterStatus,
			
			subsidiary:subsidiary,
			addSubsidiary:addSubsidiary,
			changeSubsidiaryStatus:changeSubsidiaryStatus,
			
			
			
			changeSupplierStatus:changeSupplierStatus,
			
			changeMaterialStatus:changeMaterialStatus,
			changeEmployeeStatus:changeEmployeeStatus,
			
			spare:spare,
			uploadSpare:uploadSpare,
			
			uploadDesk:uploadDesk,
			uploadProject:uploadProject,
			uploadRoom:uploadRoom,
			uploadDepartment:uploadDepartment,
			uploadBranch:uploadBranch,
			uploadBrand:uploadBrand,
			uploadDesignation:uploadDesignation,
			uploadWorkLocation:uploadWorkLocation,
			uploadCostCenter:uploadCostCenter,
			uploadSubsidiary:uploadSubsidiary,
			uploadSupplier:uploadSupplier,
			uploadMaterial:uploadMaterial,
			uploadEmployee:uploadEmployee,
			
		});

		(function activate() {
			
			$scope.listView=false;
			$scope.showList=true;
			
		})();
		
		
		function spare(){
			$scope.spareTab=true
			$scope.deskTab=false
			$scope.projectTab=false
			$scope.roomTab=false
			$scope.departmentTab=false
			$scope.brandTab=false
			$scope.branchTab=false
			$scope.supplierTab=false
			$scope.materialTab=false
			$scope.employeeTab=false
			$scope.designationTab=false
			$scope.workLocationTab=false
			$scope.costCenterTab=false
			$scope.subsidiaryTab=false
			loadSpareData()
		}
		
		function loadSpareData(){
			var msg=""
				 var url =spareUrl+"/getSpareStock";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.spareStocks = response.data;
			console.log("spareStocks : "+JSON.stringify(vm.spareStocks))
								
			});
		}
		function uploadSpare(){
			var file = document.getElementById('uploadSpare').files[0];
			
			console.dir(file);

			if (file == undefined) {
				toastr.error('Please Select a xlsx File');
				return;
			}

			var fileName = file.name;
			var extension = ".xlsx";
			var extension1 = ".xls";
			console.log("Format  "+fileName.includes(extension))

			console.log("Format 1 "+fileName.includes(extension1))
			if(!fileName.includes(extension1)){
				toastr.error('Selected File is not a xlsx or xls');
				return;
			}			

			$('.loading').show();
			var fd = new FormData();
			fd.append('file', file);
			var url = uploadsUrl + "/uploaSpare";
			console.log("URL :: "+url)
			$http.post(url, fd, {
				transformRequest: angular.identity,
				headers: {
					'Content-Type': undefined
				}
			})
			.then(function successCallback(response) {
				
				$('.loading').hide();
				//window.alert("File uploaded successfully!");
			
				toastr.success('Uploaded....', 'Successfully!!',{ timeOut: 10000 });					
				loadRooms();
			}, function errorCallback(response) {
		    	$('.loading').hide();
				//window.alert("File upload - unsuccessfull!");
				//init();
				toastr.error('Upload....', 'UnSuccessfully!!');
				loadRooms();
					    });

			angular.element("input[type='file']").val(null);

		}
		/**************************list view ******************************/
		$scope.showlist=function(){
			$scope.listView=true;
			$scope.showList=false;
			$scope.viewMenu=true;
			$scope.listviewcardStyle={
			"border": "3px solid #64c5b1",
			"height":"795px"
			}
		}
		
		$scope.listview=function(){
			$scope.listView=false;
			$scope.showList=true;
			$scope.viewMenu=false;
			$scope.listviewcardStyle={
			"border": "3px solid #64c5b1",
			"height":"100px"
			}
		}
		
		
		
		//--------DESK--------//
		
		
		
		function uploadDesk(){
			var file = document.getElementById('uploadDesk').files[0];
		
		console.dir(file);

		if (file == undefined) {
			toastr.error('Please Select a xlsx File');
			return;
		}

		var fileName = file.name;
		var extension = ".xlsx";
		var extension1 = ".xls";
		console.log("Format  "+fileName.includes(extension))

		console.log("Format 1 "+fileName.includes(extension1))
		if(!fileName.includes(extension1)){
			toastr.error('Selected File is not a xlsx or xls');
			return;
		}			

		$('.loading').show();
		var fd = new FormData();
		fd.append('file', file);
		var url = uploadsUrl + "/uploadDesk";
		console.log("URL :: "+url)
		$http.post(url, fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}
		})
		.then(function successCallback(response) {
			
			$('.loading').hide();
			//window.alert("File uploaded successfully!");
		
			toastr.success('Uploaded....', 'Successfully!!',{ timeOut: 10000 });					
			loadDesks();
		}, function errorCallback(response) {
	    	$('.loading').hide();
			//window.alert("File upload - unsuccessfull!");
			//init();
			toastr.error('Upload....', 'UnSuccessfully!!');
			loadDesks();
				    });

		angular.element("input[type='file']").val(null);
	}
		
		
		
		
		function uploadProject(){
			var file = document.getElementById('uploadProject').files[0];
		
		console.dir(file);

		if (file == undefined) {
			toastr.error('Please Select a xlsx File');
			return;
		}

		var fileName = file.name;
		var extension = ".xlsx";
		var extension1 = ".xls";
		console.log("Format  "+fileName.includes(extension))

		console.log("Format 1 "+fileName.includes(extension1))
		if(!fileName.includes(extension1)){
			toastr.error('Selected File is not a xlsx or xls');
			return;
		}			

		$('.loading').show();
		var fd = new FormData();
		fd.append('file', file);
		var url = uploadsUrl + "/uploadProject";
		console.log("URL :: "+url)
		$http.post(url, fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}
		})
		.then(function successCallback(response) {
			
			$('.loading').hide();
			//window.alert("File uploaded successfully!");
		
			toastr.success('Uploaded....', 'Successfully!!',{ timeOut: 10000 });					
			loadProjects();
		}, function errorCallback(response) {
	    	$('.loading').hide();
			//window.alert("File upload - unsuccessfull!");
			//init();
			toastr.error('Upload....', 'UnSuccessfully!!');
			loadProjects();
				    });

		angular.element("input[type='file']").val(null);
	}	
		
		function uploadRoom(){
			var file = document.getElementById('uploadRoom').files[0];
		
		console.dir(file);

		if (file == undefined) {
			toastr.error('Please Select a xlsx File');
			return;
		}

		var fileName = file.name;
		var extension = ".xlsx";
		var extension1 = ".xls";
		console.log("Format  "+fileName.includes(extension))

		console.log("Format 1 "+fileName.includes(extension1))
		if(!fileName.includes(extension1)){
			toastr.error('Selected File is not a xlsx or xls');
			return;
		}			

		$('.loading').show();
		var fd = new FormData();
		fd.append('file', file);
		var url = uploadsUrl + "/uploadRoom";
		console.log("URL :: "+url)
		$http.post(url, fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}
		})
		.then(function successCallback(response) {
			
			$('.loading').hide();
			//window.alert("File uploaded successfully!");
		
			toastr.success('Uploaded....', 'Successfully!!',{ timeOut: 10000 });					
			loadRooms();
		}, function errorCallback(response) {
	    	$('.loading').hide();
			//window.alert("File upload - unsuccessfull!");
			//init();
			toastr.error('Upload....', 'UnSuccessfully!!');
			loadRooms();
				    });

		angular.element("input[type='file']").val(null);
	}	
			
		
		function uploadDepartment(){
			var file = document.getElementById('uploadDepartment').files[0];
		
		console.dir(file);

		if (file == undefined) {
			toastr.error('Please Select a xlsx File');
			return;
		}

		var fileName = file.name;
		var extension = ".xlsx";
		var extension1 = ".xls";
		console.log("Format  "+fileName.includes(extension))

		console.log("Format 1 "+fileName.includes(extension1))
		if(!fileName.includes(extension1)){
			toastr.error('Selected File is not a xlsx or xls');
			return;
		}			

		$('.loading').show();
		var fd = new FormData();
		fd.append('file', file);
		var url = uploadsUrl + "/uploadDepartment";
		console.log("URL :: "+url)
		$http.post(url, fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}
		})
		.then(function successCallback(response) {
			
			$('.loading').hide();
			//window.alert("File uploaded successfully!");
		
			toastr.success('Uploaded....', 'Successfully!!',{ timeOut: 10000 });					
			loadDepartments();
		}, function errorCallback(response) {
	    	$('.loading').hide();
			//window.alert("File upload - unsuccessfull!");
			//init();
			toastr.error('Upload....', 'UnSuccessfully!!');
			loadDepartments();
				    });

		angular.element("input[type='file']").val(null);
	}	
		
		function uploadBranch(){
			var file = document.getElementById('branchUpload').files[0];
		
		console.dir(file);

		if (file == undefined) {
			toastr.error('Please Select a xlsx File');
			return;
		}

		var fileName = file.name;
		var extension = ".xlsx";
		var extension1 = ".xls";
		console.log("Format  "+fileName.includes(extension))

		console.log("Format 1 "+fileName.includes(extension1))
		if(!fileName.includes(extension1)){
			toastr.error('Selected File is not a xlsx or xls');
			return;
		}			

		$('.loading').show();
		var fd = new FormData();
		fd.append('file', file);
		var url = uploadsUrl + "/branchUpload";
		console.log("URL :: "+url)
		$http.post(url, fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}
		})
		.then(function successCallback(response) {
			
			$('.loading').hide();
			//window.alert("File uploaded successfully!");
		
			toastr.success('Uploaded....', 'Successfully !!',{ timeOut: 10000 });					
			loadBranches();
		}, function errorCallback(response) {
	    	$('.loading').hide();
			//window.alert("File upload - unsuccessfull!");
			//init();
			toastr.error('Upload....', 'UnSuccessfully!!');
			loadBranches();
				    });

		angular.element("input[type='file']").val(null);
	}	
		
		function uploadBrand(){
			var file = document.getElementById('brandUpload').files[0];
		
		console.dir(file);

		if (file == undefined) {
			toastr.error('Please Select a xlsx File');
			return;
		}

		var fileName = file.name;
		var extension = ".xlsx";
		var extension1 = ".xls";
		console.log("Format  "+fileName.includes(extension))

		console.log("Format 1 "+fileName.includes(extension1))
		if(!fileName.includes(extension1)){
			toastr.error('Selected File is not a xlsx or xls');
			return;
		}			

		$('.loading').show();
		var fd = new FormData();
		fd.append('file', file);
		var url = uploadsUrl + "/brandUpload";
		console.log("URL :: "+url)
		$http.post(url, fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}
		})
		.then(function successCallback(response) {
			
			$('.loading').hide();
			//window.alert("File uploaded successfully!");
		
			toastr.success('Uploaded....', 'Successfully!!',{ timeOut: 10000 });					
			loadBrands();
		}, function errorCallback(response) {
	    	$('.loading').hide();
			//window.alert("File upload - unsuccessfull!");
			//init();
			toastr.error('Upload....', 'UnSuccessfully!!');
			loadBrands();;
				    });

		angular.element("input[type='file']").val(null);
	}	
		
		
		
		function uploadDesignation(){
			var file = document.getElementById('uploadDesignation').files[0];
		
		console.dir(file);

		if (file == undefined) {
			toastr.error('Please Select a xlsx File');
			return;
		}

		var fileName = file.name;
		var extension = ".xlsx";
		var extension1 = ".xls";
		console.log("Format  "+fileName.includes(extension))

		console.log("Format 1 "+fileName.includes(extension1))
		if(!fileName.includes(extension1)){
			toastr.error('Selected File is not a xlsx or xls');
			return;
		}			

		$('.loading').show();
		var fd = new FormData();
		fd.append('file', file);
		var url = uploadsUrl + "/uploadDesignation";
		console.log("URL :: "+url)
		$http.post(url, fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}
		})
		.then(function successCallback(response) {
			
			$('.loading').hide();
			//window.alert("File uploaded successfully!");
		
			toastr.success('Uploaded....', 'Successfully!!',{ timeOut: 10000 });					
			loadDesignations();
		}, function errorCallback(response) {
	    	$('.loading').hide();
			//window.alert("File upload - unsuccessfull!");
			//init();
			toastr.error('Upload....', 'UnSuccessfully!!');
			loadDesignations();
				    });

		angular.element("input[type='file']").val(null);
	}	
	
		
		
		
		function uploadWorkLocation(){
			var file = document.getElementById('uploadWorkLocation').files[0];
		
		console.dir(file);

		if (file == undefined) {
			toastr.error('Please Select a xlsx File');
			return;
		}

		var fileName = file.name;
		var extension = ".xlsx";
		var extension1 = ".xls";
		console.log("Format  "+fileName.includes(extension))

		console.log("Format 1 "+fileName.includes(extension1))
		if(!fileName.includes(extension1)){
			toastr.error('Selected File is not a xlsx or xls');
			return;
		}			

		$('.loading').show();
		var fd = new FormData();
		fd.append('file', file);
		var url = uploadsUrl + "/uploadWorkLocation";
		console.log("URL :: "+url)
		$http.post(url, fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}
		})
		.then(function successCallback(response) {
			
			$('.loading').hide();
			//window.alert("File uploaded successfully!");
		
			toastr.success('Uploaded....', 'Successfully!!',{ timeOut: 10000 });					
			loadWorkLocations();
		}, function errorCallback(response) {
	    	$('.loading').hide();
			//window.alert("File upload - unsuccessfull!");
			//init();
			toastr.error('Upload....', 'UnSuccessfully!!');
			loadWorkLocations();
				    });

		angular.element("input[type='file']").val(null);
	}	
	
		
		
		function uploadCostCenter(){
			var file = document.getElementById('uploadCostCenter').files[0];
		
		console.dir(file);

		if (file == undefined) {
			toastr.error('Please Select a xlsx File');
			return;
		}

		var fileName = file.name;
		var extension = ".xlsx";
		var extension1 = ".xls";
		console.log("Format  "+fileName.includes(extension))

		console.log("Format 1 "+fileName.includes(extension1))
		if(!fileName.includes(extension1)){
			toastr.error('Selected File is not a xlsx or xls');
			return;
		}			

		$('.loading').show();
		var fd = new FormData();
		fd.append('file', file);
		var url = uploadsUrl + "/uploadCostCenter";
		console.log("URL :: "+url)
		$http.post(url, fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}
		})
		.then(function successCallback(response) {
			
			$('.loading').hide();
			//window.alert("File uploaded successfully!");
		
			toastr.success('Uploaded....', 'Successfully!!',{ timeOut: 10000 });					
			loadCostCenters();
		}, function errorCallback(response) {
	    	$('.loading').hide();
			//window.alert("File upload - unsuccessfull!");
			//init();
			toastr.error('Upload....', 'UnSuccessfully!!');
			loadCostCenters();
				    });

		angular.element("input[type='file']").val(null);
	}
		
		
		function uploadSubsidiary(){
			var file = document.getElementById('uploadSubsidiary').files[0];
		
		console.dir(file);

		if (file == undefined) {
			toastr.error('Please Select a xlsx File');
			return;
		}

		var fileName = file.name;
		var extension = ".xlsx";
		var extension1 = ".xls";
		console.log("Format  "+fileName.includes(extension))

		console.log("Format 1 "+fileName.includes(extension1))
		if(!fileName.includes(extension1)){
			toastr.error('Selected File is not a xlsx or xls');
			return;
		}			

		$('.loading').show();
		var fd = new FormData();
		fd.append('file', file);
		var url = uploadsUrl + "/uploadSubsidiary";
		console.log("URL :: "+url)
		$http.post(url, fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}
		})
		.then(function successCallback(response) {
			
			$('.loading').hide();
			//window.alert("File uploaded successfully!");
		
			toastr.success('Uploaded....', 'Successfully!!',{ timeOut: 10000 });					
			loadSubsidiaries();
		}, function errorCallback(response) {
	    	$('.loading').hide();
			//window.alert("File upload - unsuccessfull!");
			//init();
			toastr.error('Upload....', 'UnSuccessfully!!');
			loadSubsidiaries();
				    });

		angular.element("input[type='file']").val(null);
	}	
		
		
		
		
		
		function uploadSupplier(){
			var file = document.getElementById('uploadSupplier').files[0];
		
		console.dir(file);

		if (file == undefined) {
			toastr.error('Please Select a xlsx File');
			return;
		}

		var fileName = file.name;
		var extension = ".xlsx";
		var extension1 = ".xls";
		console.log("Format  "+fileName.includes(extension))

		console.log("Format 1 "+fileName.includes(extension1))
		if(!fileName.includes(extension1)){
			toastr.error('Selected File is not a xlsx or xls');
			return;
		}			

		$('.loading').show();
		var fd = new FormData();
		fd.append('file', file);
		var url = uploadsUrl + "/uploadSupplier";
		console.log("URL :: "+url)
		$http.post(url, fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}
		})
		.then(function successCallback(response) {
			
			$('.loading').hide();
			//window.alert("File uploaded successfully!");
		
			toastr.success('Uploaded....', 'Successfully!!',{ timeOut: 10000 });					
			loadSuppliers();
		}, function errorCallback(response) {
	    	$('.loading').hide();
			//window.alert("File upload - unsuccessfull!");
			//init();
			toastr.error('Upload....', 'UnSuccessfully!!');
			loadSuppliers();
				    });

		angular.element("input[type='file']").val(null);
	}	
		
		
		
		function uploadMaterial(){
			var file = document.getElementById('uploadMaterial').files[0];
		
		console.dir(file);

		if (file == undefined) {
			toastr.error('Please Select a xlsx File');
			return;
		}

		var fileName = file.name;
		var extension = ".xlsx";
		var extension1 = ".xls";
		console.log("Format  "+fileName.includes(extension))

		console.log("Format 1 "+fileName.includes(extension1))
		if(!fileName.includes(extension1)){
			toastr.error('Selected File is not a xlsx or xls');
			return;
		}			

		$('.loading').show();
		var fd = new FormData();
		fd.append('file', file);
		var url = uploadsUrl + "/uploadMaterial";
		console.log("URL :: "+url)
		$http.post(url, fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}
		})
		.then(function successCallback(response) {
			
			$('.loading').hide();
			//window.alert("File uploaded successfully!");
		
			toastr.success('Uploaded....', 'Successfully!!',{ timeOut: 10000 });					
			loadMaterials();
		}, function errorCallback(response) {
	    	$('.loading').hide();
			//window.alert("File upload - unsuccessfull!");
			//init();
			toastr.error('Upload....', 'UnSuccessfully!!');
			loadMaterials();
				    });

		angular.element("input[type='file']").val(null);
	}
		
		
		
		
		function uploadEmployee(){
			var file = document.getElementById('uploadEmployee').files[0];
		
		console.dir(file);

		if (file == undefined) {
			toastr.error('Please Select a xlsx File');
			return;
		}

		var fileName = file.name;
		var extension = ".xlsx";
		var extension1 = ".xls";
		console.log("Format  "+fileName.includes(extension))

		console.log("Format 1 "+fileName.includes(extension1))
		if(!fileName.includes(extension1)){
			toastr.error('Selected File is not a xlsx or xls');
			return;
		}			

		$('.loading').show();
		var fd = new FormData();
		fd.append('file', file);
		var url = uploadsUrl + "/uploadEmployee";
		console.log("URL :: "+url)
		$http.post(url, fd, {
			transformRequest: angular.identity,
			headers: {
				'Content-Type': undefined
			}
		})
		.then(function successCallback(response) {
			
			$('.loading').hide();
			//window.alert("File uploaded successfully!");
		
			toastr.success('Uploaded....', 'Successfully!!',{ timeOut: 10000 });					
			loadEmployee();
		}, function errorCallback(response) {
	    	$('.loading').hide();
			//window.alert("File upload - unsuccessfull!");
			//init();
			toastr.error('Upload....', 'UnSuccessfully!!');
			loadEmployee();
				    });

		angular.element("input[type='file']").val(null);
	}
		
		
		
		
		
		
		
		
		
		
		function addDesk(){
			if($scope.deskNo==undefined||$scope.deskNo==""){
				$scope.deskNoErr=true;
				return;
			}else{
				$scope.deskNoErr=false;
			}
			var desk={}
			desk.floor=""
			desk.bay=""
			desk.active=1
			desk.allocate=0
			desk.deskCode=$scope.deskNo;
			var msg=""
				 var url =commonUrl+"/addDesk";
				genericFactory.add(msg,url,desk).then(function(response) {
					loadDesks();
					var responceObj=response.data
					
					$scope.deskNo=""
					
					if(responceObj.code==200){
						toastr.success(responceObj.msg);	
					}else{
						toastr.error(responceObj.msg);
					}
			
								
			});
		}
		function loadDesks(){
			var msg=""
				 var url =commonUrl+"/getAllDesk";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.desks = response.data;
			
								
			});
		}
		
		function desk(){
			$scope.deskTab=true
			$scope.projectTab=false
			$scope.roomTab=false
			$scope.departmentTab=false
			$scope.brandTab=false
			$scope.branchTab=false
			$scope.supplierTab=false
			$scope.materialTab=false
			$scope.employeeTab=false
			$scope.designationTab=false
			$scope.workLocationTab=false
			$scope.costCenterTab=false
			$scope.subsidiaryTab=false
			loadDesks();
			
		}
		
		
		function changeDeskStatus(desk){
			var msg=""
				 var url =commonUrl+"/changeDeskStatus";
				genericFactory.add(msg,url,desk).then(function(response) {
					loadDesks();
					var responceObj=response.data
					
					$scope.deskNo=""
					
					if(responceObj.code==200){
						toastr.success(responceObj.msg);	
					}else{
						toastr.error(responceObj.msg);
					}
			
								
			});
		}
		
		
		
		//--------Project--------//	
				
		function project(){
			$scope.deskTab=false
			$scope.projectTab=true
			$scope.roomTab=false
			$scope.departmentTab=false
			$scope.brandTab=false
			$scope.branchTab=false
			$scope.supplierTab=false
			$scope.materialTab=false
			$scope.employeeTab=false
			$scope.designationTab=false
			$scope.workLocationTab=false
			$scope.costCenterTab=false
			$scope.subsidiaryTab=false
			loadProjects();
		}
		
		function loadProjects(){
			var msg=""
				 var url =commonUrl+"/getAllProject";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.projects = response.data;
			
								
			});
		}
		function addProject(){
			
			if($scope.projectName==undefined||projectName.deskNo==""){
				$scope.projectNameErr=true;
				return;
			}else{
				$scope.projectNameErr=false;
			}
			var project={}
			project.active=1
			project.projectName=$scope.projectName;
			var msg=""
				 var url =commonUrl+"/addProject";
				genericFactory.add(msg,url,project).then(function(response) {
					loadProjects();
					var responceObj=response.data
					
					$scope.projectName=""
					
					if(responceObj.code==200){
						toastr.success(responceObj.msg);	
					}else{
						toastr.error(responceObj.msg);
					}
			
								
			});
		}
		
		function changeStastusProject(project){
			var msg=""
				 var url =commonUrl+"/changeProjectStatus";
				genericFactory.add(msg,url,project).then(function(response) {
					loadProjects();
					var responceObj=response.data
					
					
					
					if(responceObj.code==200){
						toastr.success(responceObj.msg);	
					}else{
						toastr.error(responceObj.msg);
					}
			
								
			});
		}
		
		
		
		//--------ROOM--------//	
		
		function room(){
			$scope.deskTab=false
			$scope.projectTab=false
			$scope.roomTab=true
			$scope.departmentTab=false
			$scope.brandTab=false
			$scope.branchTab=false
			$scope.supplierTab=false
			$scope.materialTab=false
			$scope.employeeTab=false
			$scope.designationTab=false
			$scope.workLocationTab=false
			$scope.costCenterTab=false
			$scope.subsidiaryTab=false
			loadRooms();
			
			
		}
		
		function loadRooms(){
			var msg=""
				 var url =commonUrl+"/getAllRoom";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.rooms = response.data;
			
								
			});
		}
		function addRoom(){
			
			if($scope.roomName==undefined||roomName.deskNo==""){
				$scope.roomNameErr=true;
				return;
			}else{
				$scope.roomNameErr=false;
			}
			var room={}
			room.active=1
			room.roomName=$scope.roomName;
			var msg=""
				 var url =commonUrl+"/addRoom";
				genericFactory.add(msg,url,room).then(function(response) {
					loadRooms();
					var responceObj=response.data
					
					$scope.roomName=""
					
					if(responceObj.code==200){
						toastr.success(responceObj.msg);	
					}else{
						toastr.error(responceObj.msg);
					}
			
								
			});
		}
		
		function changeStastusRoom(room){
			var msg=""
				 var url =commonUrl+"/changeRoomStatus";
				genericFactory.add(msg,url,room).then(function(response) {
					loadRooms();
					var responceObj=response.data
					
					
					
					if(responceObj.code==200){
						toastr.success(responceObj.msg);	
					}else{
						toastr.error(responceObj.msg);
					}
			
								
			});
		}
		
		
		//--------DEPARTMENT--------//	
		
	
		function department(){
			$scope.deskTab=false
			$scope.projectTab=false
			$scope.roomTab=false
			$scope.departmentTab=true
			$scope.brandTab=false
			$scope.branchTab=false
			$scope.supplierTab=false
			$scope.materialTab=false
			$scope.employeeTab=false
			$scope.designationTab=false
			$scope.workLocationTab=false
			$scope.costCenterTab=false
			$scope.subsidiaryTab=false
			loadDepartments();
		}
		
		function loadDepartments(){
			var msg=""
				 var url =commonUrl+"/getAllDepartment";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.departments = response.data;
			
								
			});
		}
		function addDepartment(){
			
			if($scope.departmentName==undefined||$scope.departmentName==""){
				$scope.departmentNameErr=true;
				return;
			}else{
				$scope.departmentNameErr=false;
			}
			var department={}
			department.active=1
			department.departmentName=$scope.departmentName;
			var msg=""
				 var url =commonUrl+"/addDepartment";
				genericFactory.add(msg,url,department).then(function(response) {
					loadDepartments();
					var responceObj=response.data
					
					$scope.departmentName=""
					
					if(responceObj.code==200){
						toastr.success(responceObj.msg);	
					}else{
						toastr.error(responceObj.msg);
					}
			
								
			});
		}
		
		function changeStastusDepartment(department){
			var msg=""
				 var url =commonUrl+"/changeStastusDepartment";
				genericFactory.add(msg,url,department).then(function(response) {
					loadDepartments();
					var responceObj=response.data
					
					
					
					if(responceObj.code==200){
						toastr.success(responceObj.msg);	
					}else{
						toastr.error(responceObj.msg);
					}
			
								
			});
		}
		
		
		
		
		
		
		
		
		
		
		
		
		//--------BRAND --------//	
		
		
		
		function brand(){
			$scope.deskTab=false
			$scope.projectTab=false
			$scope.roomTab=false
			$scope.departmentTab=false
			$scope.brandTab=true
			$scope.branchTab=false
			$scope.supplierTab=false
			$scope.materialTab=false
			$scope.employeeTab=false
			$scope.designationTab=false
			$scope.workLocationTab=false
			$scope.costCenterTab=false
			$scope.subsidiaryTab=false
			loadBrands();
		}
		
		
		function loadBrands(){
			var msg=""
				 var url =commonUrl+"/getAllBrands";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.brands = response.data;
			
								
			});
		}
		function addBrand(){
			
			if($scope.brandName==undefined||$scope.brandName==""){
				$scope.brandNameErr=true;
				return;
			}else{
				$scope.brandNameErr=false;
			}
			var brand={}
			brand.active=1
			brand.brandName=$scope.brandName;
			var msg=""
				 var url =commonUrl+"/addBrand";
				genericFactory.add(msg,url,brand).then(function(response) {
					loadBrands();
					var responceObj=response.data
					
					$scope.brandName=""
					
					if(responceObj.code==200){
						toastr.success(responceObj.msg);	
					}else{
						toastr.error(responceObj.msg);
					}
			
								
			});
		}
		
		function changeBrandStatus(brand){
			var msg=""
				 var url =commonUrl+"/changeBrandStatus";
				genericFactory.add(msg,url,brand).then(function(response) {
					loadBrands();
					var responceObj=response.data
					
					
					
					if(responceObj.code==200){
						toastr.success(responceObj.msg);	
					}else{
						toastr.error(responceObj.msg);
					}
			
								
			});
		}
		
		
		
		
		//--------BRANCH --------//	
		
		
		function branch(){
			$scope.deskTab=false
			$scope.projectTab=false
			$scope.roomTab=false
			$scope.departmentTab=false
			$scope.brandTab=false
			$scope.branchTab=true
			$scope.supplierTab=false
			$scope.materialTab=false
			$scope.employeeTab=false
			$scope.designationTab=false
			$scope.workLocationTab=false
			$scope.costCenterTab=false
			$scope.subsidiaryTab=false
			loadBranches();
		}
		
		
		

		function loadBranches(){
			var msg=""
				 var url =commonUrl+"/getAllBranches";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.branches = response.data;
			console.log("branches"+JSON.stringify(vm.branches))
								
			});
		}
		function addBranch(){
			
			if($scope.branchName==undefined||$scope.branchName==""){
				$scope.branchNameErr=true;
				return;
			}else{
				$scope.branchNameErr=false;
			}
			var branch={}
			branch.active=1
			branch.branchName=$scope.branchName;
			var msg=""
				 var url =commonUrl+"/addBranch";
				genericFactory.add(msg,url,branch).then(function(response) {
					loadBranches();
					var responceObj=response.data
					
					$scope.branchName=""
					
					if(responceObj.code==200){
						toastr.success(responceObj.msg);	
					}else{
						toastr.error(responceObj.msg);
					}
			
								
			});
		}
		
		function changeBranchStatus(branch){
			var msg=""
				 var url =commonUrl+"/changeBranchStatus";
				genericFactory.add(msg,url,branch).then(function(response) {
					loadBranches();
					var responceObj=response.data
					
					
					
					if(responceObj.code==200){
						toastr.success(responceObj.msg);	
					}else{
						toastr.error(responceObj.msg);
					}
			
								
			});
		}
		
		
		
		
		//--------Designation --------//	
		
		
		function designation(){
			$scope.deskTab=false
			$scope.projectTab=false
			$scope.roomTab=false
			$scope.departmentTab=false
			$scope.brandTab=false
			$scope.branchTab=false
			$scope.supplierTab=false
			$scope.materialTab=false
			$scope.employeeTab=false
			$scope.designationTab=true
			$scope.workLocationTab=false
			$scope.costCenterTab=false
			$scope.subsidiaryTab=false
			loadDesignations();
		}
		
		
		

		function loadDesignations(){
			var msg=""
				 var url =commonUrl+"/getAllDesignation";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.designations = response.data;
			console.log("designations"+JSON.stringify(vm.designations))
								
			});
		}
		function addDesignation(){
			
			if($scope.designationName==undefined||$scope.designationName==""){
				$scope.designationNameErr=true;
				return;
			}else{
				$scope.designationNameErr=false;
			}
			var designation={}
			designation.active=1
			designation.designationName=$scope.designationName;
			var msg=""
				 var url =commonUrl+"/addDesignation";
				genericFactory.add(msg,url,designation).then(function(response) {
					loadDesignations();
					var responceObj=response.data
					
					$scope.designationName=""
					
					if(responceObj.code==200){
						toastr.success(responceObj.msg);	
					}else{
						toastr.error(responceObj.msg);
					}
			
								
			});
		}
		
		function changeDesignationStatus(designation){
			var msg=""
				 var url =commonUrl+"/changeDesignationStatus";
				genericFactory.add(msg,url,designation).then(function(response) {
					loadDesignations();
					var responceObj=response.data
					
					
					
					if(responceObj.code==200){
						toastr.success(responceObj.msg);	
					}else{
						toastr.error(responceObj.msg);
					}
			
								
			});
		}
		
		
		
//--------WorkLocation  --------//	
		
		
		function workLocation(){
			$scope.deskTab=false
			$scope.projectTab=false
			$scope.roomTab=false
			$scope.departmentTab=false
			$scope.brandTab=false
			$scope.branchTab=false
			$scope.supplierTab=false
			$scope.materialTab=false
			$scope.employeeTab=false
			$scope.designationTab=false
			$scope.workLocationTab=true
			$scope.costCenterTab=false
			$scope.subsidiaryTab=false
			loadWorkLocations();
		}
		
		
		

		function loadWorkLocations(){
			var msg=""
				 var url =commonUrl+"/getAllWorklocation";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.workLocations = response.data;
			console.log("workLocations"+JSON.stringify(vm.workLocations))
								
			});
		}
		function addWorkLocation(){
			
			if($scope.workLocationName==undefined||$scope.workLocationName==""){
				$scope.workLocationNameErr=true;
				return;
			}else{
				$scope.workLocationNameErr=false;
			}
			var workLocation={}
			workLocation.active=1
			workLocation.workLocationName=$scope.workLocationName;
			var msg=""
				 var url =commonUrl+"/addWorklocation";
				genericFactory.add(msg,url,workLocation).then(function(response) {
					loadWorkLocations();
					var responceObj=response.data
					
					$scope.workLocationName=""
					
					if(responceObj.code==200){
						toastr.success(responceObj.msg);	
					}else{
						toastr.error(responceObj.msg);
					}
			
								
			});
		}
		
		function changeWorkLocationStatus(workLocation){
			var msg=""
				 var url =commonUrl+"/changeWorkLocationStatus";
				genericFactory.add(msg,url,workLocation).then(function(response) {
					loadWorkLocations();
					var responceObj=response.data
					
					
					
					if(responceObj.code==200){
						toastr.success(responceObj.msg);	
					}else{
						toastr.error(responceObj.msg);
					}
			
								
			});
		}
		
		
		
		
		
		
		//--------Cost Center  --------//	
				
				
				function costCenter(){
					$scope.deskTab=false
					$scope.projectTab=false
					$scope.roomTab=false
					$scope.departmentTab=false
					$scope.brandTab=false
					$scope.branchTab=false
					$scope.supplierTab=false
					$scope.materialTab=false
					$scope.employeeTab=false
					$scope.designationTab=false
					$scope.workLocationTab=false
					$scope.costCenterTab=true
					$scope.subsidiaryTab=false
					loadCostCenters();
				}
				
				
				

				function loadCostCenters(){
					var msg=""
						 var url =commonUrl+"/getAllCostCenter";
						genericFactory.getAll(msg,url).then(function(response) {
						vm.costCenters = response.data;
					console.log("costCenters"+JSON.stringify(vm.costCenters))
										
					});
				}
				
				$scope.checkcostCenterName=function(){
					if($scope.costCenterName==undefined||$scope.costCenterName==""){
						$scope.costCenterNameErr=true;
						$scope.addDisabled=true;
					}else{
					var sBname=$scope.costCenterName;
					var i=0;
					for(i;i<vm.costCenters.length;i++){
						if(sBname==vm.costCenters[i].costCenterName){
							i=vm.costCenters.length;
							$scope.addDisabled=true;
							toastr.error("this name is already exist")
						}else{
							$scope.costCenterNameErr=false;	
							$scope.addDisabled=false;
						}
					}
				}
			}
				
				function addCostCenter(){
					
					if($scope.costCenterName==undefined||$scope.costCenterName==""){
						$scope.costCenterNameErr=true;
						return;
					}else{
						$scope.costCenterNameErr=false;
					}
					$rootScope.loader=true;
					var costCenter={}
					costCenter.active=1
					costCenter.costCenterName=$scope.costCenterName;
					var msg=""
						 var url =commonUrl+"/addCostCenter";
						genericFactory.add(msg,url,costCenter).then(function(response) {
							loadCostCenters();
							var responceObj=response.data
							
							$scope.costCenterName=""
							
							if(responceObj.code==200){
								toastr.success(responceObj.msg);	
								$rootScope.loader=false;
							}else{
								toastr.error(responceObj.msg);
								$rootScope.loader=false;
							}
					
										
					});
				}
				
				function changeCostCenterStatus(costCenter){
					var msg=""
						 var url =commonUrl+"/changeCostCenterStatus";
						genericFactory.add(msg,url,costCenter).then(function(response) {
							loadCostCenters();
							var responceObj=response.data
							
							
							
							if(responceObj.code==200){
								toastr.success(responceObj.msg);	
							}else{
								toastr.error(responceObj.msg);
							}
					
										
					});
				}
				
				
				
				
				
				//--------Subsidiary --------//	
						
						
						function subsidiary(){
							$scope.deskTab=false
							$scope.projectTab=false
							$scope.roomTab=false
							$scope.departmentTab=false
							$scope.brandTab=false
							$scope.branchTab=false
							$scope.supplierTab=false
							$scope.materialTab=false
							$scope.employeeTab=false
							$scope.designationTab=false
							$scope.workLocationTab=false
							$scope.costCenterTab=false
							$scope.subsidiaryTab=true
							loadSubsidiaries();
						}
						
						
						

						function loadSubsidiaries(){
							var msg=""
								 var url =commonUrl+"/getAllSubsidiary";
								genericFactory.getAll(msg,url).then(function(response) {
								vm.subsidiaries = response.data;
							console.log("subsidiaries"+JSON.stringify(vm.subsidiaries))
												
							});
						}
						/************************************************/
						$scope.checkSubsidaryName=function(){
							if($scope.subsidiaryName==undefined||$scope.subsidiaryName==""){
								$scope.subsidiaryNameErr=true;
								$scope.addDisabled=true;
							}else{
							var sBname=$scope.subsidiaryName;
							var i=0;
							for(i;i<vm.subsidiaries.length;i++){
								if(sBname==vm.subsidiaries[i].subsidiaryName){
									i=vm.subsidiaries.length;
									$scope.addDisabled=true;
									toastr.error("this name is already exist")
								}else{
									$scope.subsidiaryNameErr=false;	
									$scope.addDisabled=false;
								}
							}
						}
					}
						/**************************************************/
						function addSubsidiary(){
							
							if($scope.subsidiaryName==undefined||$scope.subsidiaryName==""){
								$scope.subsidiaryNameErr=true;
								$scope.addDisabled=true;
								return;
							}else{
								$scope.subsidiaryNameErr=false;								
							}
							$rootScope.loader=true;
							var subsidiary={}
							subsidiary.active=1
							subsidiary.subsidiaryName=$scope.subsidiaryName;
							var msg=""
								 var url =commonUrl+"/addSubsidiary";
								genericFactory.add(msg,url,subsidiary).then(function(response) {
									loadSubsidiaries();
									var responceObj=response.data
									
									$scope.subsidiaryName=""
									
									if(responceObj.code==200){
										toastr.success(responceObj.msg);
										$rootScope.loader=false;
									}else{
										toastr.error(responceObj.msg);
										$rootScope.loader=false;
									}
							
												
							});
						}
						
						function changeSubsidiaryStatus(subsidiary){
							var msg=""
								 var url =commonUrl+"/changeSubsidiaryStatus";
								genericFactory.add(msg,url,subsidiary).then(function(response) {
									loadSubsidiaries();
									var responceObj=response.data
									
									
									
									if(responceObj.code==200){
										
										toastr.success("Status Updated succesfully");	
									}else{
										toastr.error(responceObj.msg);
									}
							
												
							});
						}
						
						
						
		
		
		
		
		
		
		
		
		
//--------Supplier --------//	
		
		
		function supplier(){
			$scope.deskTab=false
			$scope.projectTab=false
			$scope.roomTab=false
			$scope.departmentTab=false
			$scope.brandTab=false
			$scope.branchTab=false
			$scope.supplierTab=true
			$scope.materialTab=false
			$scope.employeeTab=false
			$scope.designationTab=false
			$scope.workLocationTab=false
			$scope.costCenterTab=false
			$scope.subsidiaryTab=false
			loadSuppliers();
			
		}
		
		function loadSuppliers(){
			var msg=""
				 var url =supplierUrl+"/getAllSupplier";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.suppliers = response.data;
			console.log("suppliers"+JSON.stringify(vm.suppliers))
								
			});
		}
		function changeSupplierStatus(supplier){
			var msg=""
				 var url =supplierUrl+"/changeSupplierStatus";
				genericFactory.add(msg,url,supplier).then(function(response) {
					loadSuppliers();
					var responceObj=response.data
					console.log("responceObj"+JSON.stringify(responceObj))
					
					
					if(responceObj.code==200){
						toastr.success(responceObj.msg);	
					}else{
						toastr.error(responceObj.msg);
					}
			
								
			});
		}
		
		
		
		
		//--------Material --------//		
		
		function material(){
			$scope.deskTab=false
			$scope.projectTab=false
			$scope.roomTab=false
			$scope.departmentTab=false
			$scope.brandTab=false
			$scope.branchTab=false
			$scope.supplierTab=false
			$scope.materialTab=true
			$scope.employeeTab=false
			$scope.designationTab=false
			$scope.workLocationTab=false
			$scope.costCenterTab=false
			$scope.subsidiaryTab=false
			loadMaterials();
			
		}
		function loadMaterials(){
			var msg=""
				 var url =materialUrl+"/getAllMaterial";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.materials = response.data;
			console.log("materials"+JSON.stringify(vm.materials))
								
			});
		}
		
		function changeMaterialStatus (material){
			var msg=""
				 var url =materialUrl+"/changeStatus";
				genericFactory.add(msg,url,material).then(function(response) {
					loadMaterials();
					var responceObj=response.data
					console.log("responceObj"+JSON.stringify(responceObj))
					
					
					if(responceObj.code==200){
						toastr.success(responceObj.msg);	
					}else{
						toastr.error(responceObj.msg);
					}
			
								
			});
		}
		
		//--------Employee --------//		
		function employee(){
			$scope.deskTab=false
			$scope.projectTab=false
			$scope.roomTab=false
			$scope.departmentTab=false
			$scope.brandTab=false
			$scope.branchTab=false
			$scope.supplierTab=false
			$scope.materialTab=false
			$scope.employeeTab=true
			$scope.designationTab=false
			$scope.workLocationTab=false
			$scope.costCenterTab=false
			$scope.subsidiaryTab=false
			loadEmployee()
			
		}
		
		function loadEmployee(){
			var msg=""
				 var url =employeeUrl+"/getAllEmployee";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.employees = response.data;
				console.log("employees"+JSON.stringify(vm.employees))
								
			});
		}
		function changeEmployeeStatus (employee){
	console.log("employee active: "+JSON.stringify(employee.active))
			
			if(employee.active==1){
				employee.active=0
			}else{
				employee.active=1
			}
			$rootScope.loader=true;
			var msg="Employee status updated successfully"
			 var url =employeeUrl+"/changeStatus";
			genericFactory.add(msg,url,employee).then(function(response) {
				if(response.data.code==200){
					toastr.success(response.data.msg);
					$rootScope.loader=false;
				}else{
					toastr.error(response.data.msg);
					$rootScope.loader=false;
				}
					
		});
		}
		
		
		function cancel(){
			$scope.deskTab=false
			$scope.projectTab=false
			$scope.roomTab=false
			$scope.departmentTab=false
			$scope.brandTab=false
			$scope.branchTab=false
			$scope.supplierTab=false
			$scope.materialTab=false
			$scope.employeeTab=false
			$scope.designationTab=false
			$scope.workLocationTab=false
			$scope.costCenterTab=false
			$scope.subsidiaryTab=false
			
		}
	}

	
})();
