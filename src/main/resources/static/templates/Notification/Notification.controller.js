(function() {
    'use strict';

    angular.module('myApp.Notification').controller('NotificationController', NotificationController);

    NotificationController.$inject = ['$scope', '$state', 'ApiEndpoint', '$http', 'toastr', 'genericFactory','localStorageService'];

    function NotificationController($scope, $state, ApiEndpoint, $http, toastr, genericFactory,localStorageService) {
        	var notificationUrl = staticUrl+"/notification";
        const baseUrl = ApiEndpoint.url;
        	var userDetail = localStorageService.get(ApiEndpoint.userKey);
        var vm = angular.extend(this, {
           
             markAsViewed: markAsViewed,
            notification: [],
            view:view,
           cancel:cancel
        });

        activate();

        function activate() {
            loadNotification();
            
        }
function view(notification){
	$scope.addNewTab=true;
	$scope.notification=notification
	var msg=""
				 var url =notificationUrl+"/updateNotification";
				 console.log("url  "+url);
				genericFactory.add(msg,url,notification).then(function(response) {
				loadNotification();
								
			});
	
}
function cancel(){
	$scope.addNewTab=false;
}

        function loadNotification() {
            var url = baseUrl + "/notification/getNotificationListByDept?deptName="+userDetail.department.departmentName;
            	console.log("baseUrl : "+url)
            $http.get(url)
                .then(function(response) {
                    vm.notification = response.data;
                    console.log("Fetched Notification:", vm.notification);
                })
                .catch(function(error) {
                    console.error("Error fetching notification:", error);
                });
        }

       function markAsViewed(id) {
    var url = baseUrl + "/notification/markAsViewed/" + id;
    $http.put(url)
        .then(function(response) {
            
            toastr.success(
                'Notification marked as viewed successfully!',  
                'Success',  
                {
                    "closeButton": true,  
                    "progressBar": true,  
                    "timeOut": "3000",  
                    "extendedTimeOut": "1000",  
                    "positionClass": "toast-top-right"  
                }
            );
            loadNotification();  
        })
        .catch(function(error) {
            console.error("Error marking notification as viewed:", error);
            toastr.error("Error marking notification as viewed.", "Error");
        });
}

        
    }
})();
