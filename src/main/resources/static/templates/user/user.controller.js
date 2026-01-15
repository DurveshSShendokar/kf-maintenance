(function() {
	'use strict';

	angular.module('myApp.user').controller('UserController', UserController);
	UserController.$inject = [ '$state', 'userService', '$uibModal','ApiEndpoint', '$log', '$scope', 'toastr','genericFactory'];

	/* @ngInject */
	function UserController($state, userService, $uibModal,ApiEndpoint, $log, $scope, toastr,genericFactory) {
				var userUrl = staticUrlMaintenance+"/user";
				const baseUrl = ApiEndpoint.url;
		var vm = angular.extend(this, {
			users : [],
			deleteUser : deleteUser,
			editUser	:	editUser,
			createUser	:	createUser,
			addNewUser	:	addNewUser,
			validateUserId : validateUserId,
			editUserInformation : editUserInformation,
			add:add,
			 cancel: cancel,
			showPopup:showPopup,
			changeStatus:changeStatus
		});

		(function activate() {
			$scope.user = {};
			$scope.user.gender = 'selectGender';
			$scope.showEditButton = false;
			$scope.addUsers=false
			loadUsers();
		})();

		// ******************************************************
		function add(){
				$scope.addUsers=true
				loadRoles();
				loadDepartment();
				loadLabs();
				cancelLabAssign();
			
				$scope.showLabAssignForm = false;
				

		}
		
		
		
		$scope.assignedLabs = [];

		function loadAssignedLabs(userId) {
		    if (!userId) return;

		    var msg = "";
		    var url = baseUrl + "/UserLabPPM/user_labs/" + userId;
		    genericFactory.getAll(msg, url).then(function(response) {
		        vm.assignedLabs = response.data || [];
		    });
		}

		
	

		function loadLabs() {
		    var msg = "";
		    var url = baseUrl + "/lab/all";
		    genericFactory.getAll(msg, url).then(function(response) {
		        vm.labs = response.data;
		    });
		}

		
		vm.showLabAssignment = showLabAssignment;
		vm.assignLab = assignLab;
		vm.cancelLabAssign = cancelLabAssign;

		function showLabAssignment(user) {
		    $scope.showLabAssignForm = true;
		    $scope.labAssignment = {
		        user: user,
		        lab: null
		    };
		    loadLabs();
			loadAssignedLabs(user.id);
		}

		function cancelLabAssign() {
		    $scope.showLabAssignForm = false;
		    $scope.labAssignment = {};
		}


		function assignLab() {
		    if (!$scope.labAssignment.user || !$scope.labAssignment.lab) {
		        toastr.error("Please select both user and lab.");
		        return;
		    }

		    var assignObj = {
		        user: { id: $scope.labAssignment.user.id },
		        lab: { lab_id: $scope.labAssignment.lab.lab_id }
		    };

		    var msg = "";
		    var url = baseUrl + "/UserLabPPM/assign"; 
		    genericFactory.add(msg, url, assignObj).then(function(response) {
		        var data = response.data;
		        if (data.code === 500) {
		            toastr.error(data.message);
		        } else {
		            toastr.success(data.message);
		            $scope.showLabAssignForm = false;
		            $scope.labAssignment = {};
					loadAssignedLabs(assignObj.user.id);
		        }
		    });
		}

		
		
		
		
		
		
		
		
		
		
		function cancel() {
				            $scope.addUsers = false;
				            $scope.showEditButton = false;
				           
				              
				              
				        }
		
		function changeStatus(user){
			var msg=""
				 var url =userUrl+"/changeStatus";
				genericFactory.add(msg,url,user).then(function(response) {
					var data=response.data
					if(data.code==500){
					toastr.error(data.message);

				}else{
					toastr.success(data.message);
					
				}
					loadUsers();
				
								
			});
		}
			function loadRoles(){
			console.log("machines : ")
			var msg=""
				 var url =userUrl+"/getAllRoles";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.roles= response.data;
				console.log("roles : "+JSON.stringify(vm.roles))
								
			});
			
			
		}
		
			function loadDepartment(){
			console.log("machines : ")
			var msg=""
				 var url =userUrl+"/getAllDepartments";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.departments= response.data;
				console.log("machines : "+JSON.stringify(vm.departments))
								
			});
			
			
		}
		function validateUserId(id){
			if(!id || id=="")
				return;
			userService.checkExistingUserId(id).then(function(data){
				if(data == 'error'){
					$scope.error = true;
					//document.getElementById('employeeid').focus();
				}else{
					$scope.error = false;
				}
			});
		}
		
		function addNewUser(){
			$scope.disableEmployeeId = false;
			$scope.showEditButton = false;
			$scope.user = {};
			$scope.user.gender = 'selectGender';
		}

		function loadUsers() {
			userService.getUsers().then(function(data) {
				vm.users = data;
//				console.log(JSON.stringify(vm.users));
			});
		}
function showPopup(user) {
	$scope.selUser=user
    let userResponse = confirm("Do you want to proceed?");
    if (userResponse) {
        onYes(user);
    }
	$scope.showLabAssignment = true;
		  loadLabs();
		  $scope.selectedUser = user; // pre-fill user dropdown if desired
}
function onYes(user) {
   
    // Your function logic here
    var msg=""
				 var url =userUrl+"/deleteUser";
				genericFactory.add(msg,url,user).then(function(response) {
				var data= response.data;
				loadUsers()
				if(data.code==500){
					toastr.error(data.message);

				}else{
					toastr.success(data.message);
				console.log("users : "+JSON.stringify(vm.users))
				}
								
			});
			
}
		function deleteUser(user) {
			let userResponse = confirm("Do you want to proceed?");
			$scope.disableEmployeeId = false;
			$scope.user = {};
			$scope.user.gender = 'selectGender';
			$scope.showEditButton = false;
			userService.deleteUser(user).then(function() {
				loadUsers();
			});
		}
		
		function validations(){
			if(!$scope.user.firstName || $scope.user.firstName == ''){
				toastr.error('Please enter first name');
				//document.getElementById('firstname').focus();
				return true;
			}
			if(!$scope.user.lastName || $scope.user.lastName == ''){
				toastr.error('Please enter last name');
				//document.getElementById('lastname').focus();
				return true;
			}
			if(!$scope.user.id || $scope.user.id == ''){
				toastr.error('Please enter email id');
				//document.getElementById('emailid').focus();
				return true;
			}
			if(!$scope.user.emailId || $scope.user.emailId == ''){
				toastr.error('Please enter email id');
				//document.getElementById('emailid').focus();
				return true;
			}
			
			
			if(!$scope.user.password || $scope.user.password == ''){
				toastr.error('Please enter password');
				//document.getElementById('password').focus();
				return true;
			}
			
			
			return false;
		}
		
		function createUser(iObj){
		
			console.log(" iObj "+JSON.stringify(iObj));
			
			if(iObj.firstName==""||iObj.firstName==undefined){
				$scope.FNameErr=true;
				return;
			}else{
				$scope.FNameErr=false;
			}
			
			if(iObj.lastName==""||iObj.lastName==undefined){
				$scope.lNameErr=true;
				return;
			}else{
				$scope.lNameErr=false;
			}
			
			
			if(iObj.userName==""||iObj.userName==undefined){
				$scope.uNameErr=true;
				return;
			}else{
				$scope.uNameErr=false;
			}
		
		
		
			if(iObj.password==""||iObj.password==undefined){
				$scope.pswordErr=true;
				return;
			}else{
				$scope.pswordErr=false;
			}
			
			
			if(iObj.contactNo==""||iObj.contactNo==undefined){
				$scope.contactNoErr=true;
				return;
			}else{
				$scope.contactNoErr=false;
			}
			
			
				if(iObj.department==""||iObj.department==undefined){
				$scope.departErr=true;
				return;
			}else{
				$scope.departErr=false;
			}
			
			if(iObj.role==""||iObj.role==undefined){
				$scope.roleErr=true;
				return;
			}else{
				$scope.roleErr=false;
			}
			
			userService.createUser(iObj).then(function(data) {
				
	console.log("Datta "+JSON.stringify(data))
		if(data.code==500){
					toastr.error(data.message);

				}else{
					toastr.success(data.message);
					$scope.user = {};
	$scope.addUsers=false
	loadUsers();
				}
				
			});
		}
		
		function editUserInformation(iObj){
			if(validations())
				return;
			
			var createObj = Object.assign({}, $scope.user);
			userService.editUser(createObj).then(function(data) {
				$scope.user = {};
				$scope.user.gender = 'selectGender';
//				console.log(data);
				$scope.addUsers=false
				loadUsers();
				$scope.showEditButton = false;
			});
		}
		
		function editUser(iObj){
			loadRoles();
				loadDepartment();
			$scope.showEditButton = true;
			$scope.user = iObj;
			$scope.disableEmployeeId = true;
			$scope.addUsers=true
			
		}

	}

})();
