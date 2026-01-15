(function() {
    'use strict';

    angular.module('myApp.Allocation').controller('AllocationController', AllocationController);

    AllocationController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr','genericFactory','localStorageService'];

    function AllocationController($scope, ApiEndpoint, $http, toastr, genericFactory,localStorageService) {
      
        const baseUrl = ApiEndpoint.url;
        	var userDetail = localStorageService.get(ApiEndpoint.userKey);
        var vm = angular.extend(this, {
            
           
            addNew: addNew,
            cancel: cancel,
             add: add,
            ok: ok
        });

        (function activate() {
            $scope.Complaint = {};
          loadComplaints();
           loadEmployees();
            loadAssets();
            $scope.addNewTab = false;
        })();

        function cancel() {
            $scope.addNewTab = false;
        }

        function addNew() {
            $scope.addNewTab = true;
           
        }
        
        
         function loadComplaints() {
            var url = baseUrl + "/complaints/unallocatedComplaints";
            $http.get(url)
                .then(function(response) {
                    vm.complaints = response.data;
                    console.log("Fetched complaints:", vm.complaints);
                })
                .catch(function(error) {
                    console.error("Error fetching complaints:", error);
                });
        }
        
        
        
          function loadAssets() {
            var url = baseUrl + "/assetInventory/all";
            $http.get(url)
                .then(function(response) {
                    vm.assets = response.data;
                    console.log("Fetched assets:", vm.assets);
                   
                })
                .catch(function(error) {
                    console.error("Error fetching assets:", error);
                });
        }

        function loadEmployees() {
            var url = baseUrl + "/user/getITEnginner";
            
               console.error("URL ", url);
            $http.get(url)
                .then(function(response) {
                    vm.users = response.data;
                    console.log("Fetched users:", vm.users);
                })
                .catch(function(error) {
                    console.error("Error fetching employees:", error);
                });
        }
        
        
        
          function ok(complaint) {
			
			complaint.allocateTo=$scope.Complaint.allocateTo;
			 console.log(" complaints:",JSON.stringify($scope.Complaint));
						

            var url = baseUrl + "/complaints/allocateCompliant";
            var msg = "";
            genericFactory.add(msg, url, complaint)
                .then(function(response) {
                    vm.saved = response.data;
                    console.log("checking complaint:", vm.saved);

                    if (response.status === 200) {
                        toastr.success('complaint added successfully!');
                        loadComplaints();
                        $scope.addNewTab = false;
                        
                        
                    } else {
                        toastr.error('Failed to add complaint');
                    }
                })
                .catch(function(error) {
                    toastr.error('Failed to add complaint');
                    console.error("Error adding complaint:", error);
                });
        }
           
        
       

        function add(Complaint) {
            $scope.addNewTab = true;
            $scope.Complaint = Object.assign({}, Complaint);
        }
        
      

        

        

}
})();
