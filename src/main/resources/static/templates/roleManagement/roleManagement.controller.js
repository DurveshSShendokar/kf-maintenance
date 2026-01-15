/**
  * @author 		: ABS
  * @name			: materialIssueController
  * @description 	: controller for material issue module
  * @date 			: 20/06/2018
  */
(function() {
	'use strict';

	angular.module('myApp.roleManagement').controller('roleManagementController', roleManagementController);

	roleManagementController.$inject = ['$state', '$scope', 'roleManagementService','toastr', '$compile', 'userToRoleService'];
	/* @ngInject */
	function roleManagementController($state, $scope, roleManagementService, toastr, $compile, userToRoleService) {
		
		var vm = angular.extend(this, {
			roleDetailsList : [],
			permissionsList : [],
			addNewRowInTable : addNewRowInTable,
			assignPermissionsToRole : assignPermissionsToRole,
			activeDeactive : activeDeactive,
			getSelect : getSelect,
			manageUserRole : manageUserRole
//			save : save
		});

		(function activate() {
			loadAllPermissions();
			loadAllRoleDetails();
		})();
		
		function loadAllRoleDetails(){
			userToRoleService.getAllRoleDetails().then(function(data){
				vm.roleDetailsList = data;
				console.log("JSONcccc"+JSON.stringify(vm.roleDetailsList))
			});
		}
		
		function getSelect(permissions, userPermissions){
			for(var i = 0; i < userPermissions.permission.length; i++){
				if(permissions.permissionId == userPermissions.permission[i].permissionId){
					return true;
				}
			}
		}
		
		function manageUserRole(permissions, userPermissions){
			var flag = true;
			for(var i = 0; i < userPermissions.permission.length; i++){
				if(permissions.permissionId == userPermissions.permission[i].permissionId){
					userPermissions.permission.splice(i,1);
					flag = false;
				}
			}
			
			if(flag){
				var obj = Object.assign({}, permissions);
				obj.type= 'E';
				obj.permission = obj.name;
				delete obj.name;
				delete obj['@id'];
				
				userPermissions.permission.push(obj);
			}
		}
		
		function loadAllPermissions(){
			roleManagementService.getAllPermissionList().then(function(data){
				vm.permissionsList = data;
			});
		}
		
		function addNewRowInTable(){
//			vm.counter ++;
			
			/*var str = '<tr id="roles' + vm.counter +' ">  ' + 
				'<td id="id' + vm.counter + '">' + (vm.counter + 1) + '</td> ' +
				'<td id="roleName' + vm.counter + '"><input id="homeChk" type="text"></td>' +
				'<td id="home' + vm.counter + '"><input id="homeChk" type="checkbox"></td>' +
				'<td id="master' + vm.counter + '"><input id="masterChk" type="checkbox"></td>' +
				'<td id="matrialIn' + vm.counter +'"><input id="materialInChk" type="checkbox"></td>' +
				'<td id="production' + vm.counter + '"><input id="productionChk" type="checkbox"></td>' +
				'<td id="assembly' + vm.counter + '"><input id="assemblyChk" type="checkbox"></td>' +
				'<td id="qc' + vm.counter + '"><input id="qcChk" type="checkbox"></td>' +
				'<td id="packaging' + vm.counter + '"><input id="packagingChk" type="checkbox"></td>' +
				'<td id="dispatch' + vm.counter + '"><input id="dispatchChk" type="checkbox"></td>' +
				'<td id="scrap'+ vm.counter + '"><input id="scrapChk" type="checkbox"></td>' +
				'<td id="maintenance' + vm.counter + '"><input id="maintenanceChk" type="checkbox"></td>' +
				'<td id="roles' + vm.counter + '"><input id="rolesChk" type="checkbox"></td>' + 
				'<td id="active' + vm.counter + '"><button id="activeBtn' + vm.counter + '" class="btn btn-info">Deactived</button><button ng-click="assignPermissionsToRole(vm.counter)" id="saveBtn' + vm.counter + '" class="btn btn-info">Save</button></td>' +
	//			'<td id="save' + vm.counter + '"><button id="saveBtn' + vm.counter + '" onclick= { assignPermissionsToRole(' + vm.counter + ')} class="btn btn-info">Save</button></td>' +
	//			'<td id="save' + vm.counter + '"><button id="saveBtn' + vm.counter + '" class="btn btn-info">Save</button></td>' +
			'</tr>';
			
			angular.element(document.getElementById('rolesList')).append( $compile(str)($scope) );*/
			
			var obj = {
			        roleName: "",
			        permission: []
			 };
			vm.roleDetailsList.push(obj);
		}
		
		function assignPermissionsToRole(iObj){
			if(!iObj.roleName || iObj.roleName == ''){
				toastr.error('Role name can not be blank');
				return;
			}
			userToRoleService.assignPermission(iObj).then(function(){
				toastr.success('Permission updated successfully');
			});
		};
		
		function activeDeactive(iObj){
			iObj.active = !iObj.active;
			
			var obj = {
					roleId : iObj.id,
					activate : iObj.active
				}
			
			userToRoleService.activateDeactivateRole(obj).then(function(){
				toastr.success('done');
				loadAllPermissions();
				loadAllRoleDetails();
			});
		}
		
	}
})();


