(function() {
	'use strict';

	angular.module('myApp.main').controller('mainController', mainController);

	mainController.$inject = [ '$scope', 'localStorageService', 'ApiEndpoint',
			'$state','$location','$rootScope','genericFactory'];
	/* @ngInject */
	function mainController($scope, localStorageService, ApiEndpoint, $state,$location,$rootScope,genericFactory) {
		$scope.openLogout = false;
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var currentUrl = ApiEndpoint.url;
		var notificationUrl = staticUrlMaintenance+"/notification";
		console.log("LOGIN USER DETIALS "+JSON.stringify(userDetail));
		var vm = angular.extend(this, {
//			doLogout : doLogout,
			user : userDetail,
			logout : logout,
			gotoUpload:gotoUpload
		});

		(function activate() {
		$rootScope.currentDashboard="NA"
	console.log("userDetail : "+JSON.stringify(vm.user.department.departmentName))
		loadNotificationCount()
		})();
		function loadNotificationCount(){
			
			
				var msg=""
				 var url =notificationUrl+"/getNotificationCountByDept?deptName="+vm.user.department.departmentName;
				genericFactory.getAll(msg,url).then(function(response) {
				vm.notificationCount= response.data;
				console.log("notificationCount : "+JSON.stringify(vm.notificationCount))
								
			});
			
		}
		vm.goToUserForm = function() {
		       // Redirect to the 'main.user' form
		       $state.go('main.user');
		   };
		   
		   vm.goToHomeForm = function() {
		   	       // Redirect to the 'main.user' form
		   	       $state.go('main.home');
		   	   };
		
		function gotoUpload(){
			
			console.log("got to UPLOAD")
			
			 var url = 'http://20.219.1.165:8090/#!/main/home';

	        // Open the URL in a new window
	        window.open(url, '_blank');
		}
		function logout(){
			sessionStorage.removeItem(ApiEndpoint.userKey);
			sessionStorage.removeItem('permissions');
			$state.go('login_form');
		}
		
	
		
		$scope.openLogout = false;
		$scope.expandLogout = function() {
			$scope.openLogout = !$scope.openLogout;
		}

		$scope.closeDropdown = function() {
			$scope.openLogout = false;
		}	
		
		$scope.expand = function(item){
			console.log("clicked"+item);
			$scope.item.show = true;
		}
		
		$scope.toggle = function() {
	        $scope.myVar = !$scope.myVar;
	    };
	    
	    $scope.IsVisible = false;
        $scope.ShowHide = function () {
            //If DIV is visible it will be hidden and vice versa.
            $scope.IsVisible = $scope.IsVisible ? false : true;
        }
	}
	
	
})();


