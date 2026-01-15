(function() {
    'use strict';

    angular.module('myApp.Break_dashboard').controller('Break_dashboardController', Break_dashboardController);
        
    Break_dashboardController.$inject = ['$scope', '$http', 'ApiEndpoint', '$state', 'genericFactory', 'localStorageService'];

    function Break_dashboardController($scope, $http, ApiEndpoint, $state, genericFactory, localStorageService) {

        const baseUrl = ApiEndpoint.url;
        var userDetail = localStorageService.get(ApiEndpoint.userKey);
        
        var vm = angular.extend(this, {
            user: userDetail,
            loadMachines: loadMachines,
            loadBreakdashboard: loadBreakdashboard,         
            onMachineSelect: onMachineSelect,
            showBreakdownDetails: showBreakdownDetails,    
                  
            machines: [],
            ATRepo: {},
            ATRepo2: [] ,        
		    selectedmachine :null, 
		    open_breaks : {},
		    closed_breaks : {},
		    trial_breaks : {},
		    total_breaks : {},
		    repetative_breaks : {}, 
		    breakdownDetails: [] ,
		    showTimeline: false,
            timelineData: [],timelineData :[]
        });
     
        (function activate() {
            console.log("Activating Break_dashboardController");
            loadMachines();
        })();
        
        
      function onMachineSelect(machineId) {
            if (!machineId) return;          
             vm.breakdownDetails = [];        
            loadBreakdashboard(machineId);
            fetchTotalBreaks(machineId);
            fetchRepetativeBreaks(machineId);
            fetchOpenBreaks(machineId);
            fetchClosedBreaks(machineId);
            fetchTrialBreaks(machineId);
        }
        
         function showBreakdownDetails(type) {
            switch(type) {
                case 'total':
                    vm.breakdownDetails = vm.total_breaks.breakdowns.map(bd => bd.breakdown) || [];
                    break;
                case 'repetitive':
                     vm.breakdownDetails = vm.ATRepo.breakdowns.map(bd => bd.breakdown) || [];
                    break;
                case 'open':
                    vm.breakdownDetails = vm.open_breaks.openDetails || [];
                    break;
                case 'closed':
                    vm.breakdownDetails = vm.closed_breaks.closedDetails || [];
                    break;
                case 'trial':
                    vm.breakdownDetails = vm.trial_breaks.trialDetails || [];
                    break;
                default:
                    vm.breakdownDetails = [];
            }
        }
        
        
    /*      function showBreakdownDetails(type) {
            vm.showTimeline = (type === 'total');  
         //   vm.timelineData = [];  
            
            switch(type) {
                case 'total':
                    vm.breakdownDetails = vm.timelineData.breakdown || [];
                    fetchTimelineData(vm.selectedmachine);  
                    break;
                case 'repetitive':
                    vm.breakdownDetails = vm.ATRepo.breakdowns.map(bd => bd.breakdown) || [];
                    break;
                case 'open':
                    vm.breakdownDetails = vm.open_breaks.openDetails || [];
                    break;
                case 'closed':
                    vm.breakdownDetails = vm.closed_breaks.closedDetails || [];
                    break;
                case 'trial':
                    vm.breakdownDetails = vm.trial_breaks.trialDetails || [];
                    break;
                default:
                    vm.breakdownDetails = [];
            }
        }
        */
        
         function fetchTimelineData(machineId) {
            var url = baseUrl + "/breakdown/Break_dashboard/" + machineId;
            $http.get(url)
                .then(function(response) {
                    if (response.status === 200) {
                        vm.timelineData = response.data;  
                        
                        console.log("Timeline data:", vm.timelineData);
                    } else {
                        toastr.error("No timeline data found.");
                    }
                })
                .catch(function(error) {
                    console.error("Error fetching timeline data:", error);
                    toastr.error("Error fetching timeline data.");
                });
        }
        
        function loadMachines() {
            var url = baseUrl + "/machine_mst/list";
            $http.get(url)
                .then(function(response) {
                    vm.machines = response.data;
                    console.log("Fetched machines:", vm.machines);
                })
                .catch(function(error) {
                    console.error("Error fetching machines:", error);
                });
        }
        
        function loadBreakdashboard(machineId) {
            if (!machineId) return;  

            var url = baseUrl + "/analysis_time_frames/report/" + machineId;
            $http.get(url)
                .then(function(response) {
                    if (response.status === 200) {
                        vm.ATRepo = response.data;  
                        console.log("Repetative Breakdown :", vm.ATRepo);
                    } else {
                        toastr.error("No Repetative breakdown  found.");
                    }
                })
                .catch(function(error) {
                    console.error("Error fetching Repetative breakdown :", error);
                    toastr.error("Error fetching Repetative breakdown .");
                });
        }

			  function fetchTotalBreaks(machineId) {
            if (!machineId) return;  
            
            var url = baseUrl + "/breakdown/report/" + machineId;
            $http.get(url)
                .then(function(response) {
                    if (response.status === 200) {
                        vm.total_breaks = response.data;  
                        console.log("Breakdown total summary:", vm.total_breaks);
                    } else {
                        toastr.error("No breakdown total summary found.");
                    }
                })
                .catch(function(error) {
                    console.error("Error fetching breakdown total summary:", error);
                    toastr.error("Error fetching breakdown summary.");
                });
        }

		
        
        function fetchTrialBreaks(machineId) {
            if (!machineId) return;  
            
            var url = baseUrl + "/breakdown/Breakdowncounts/" + machineId;
            $http.get(url)
                .then(function(response) {
                    if (response.status === 200) {
                        vm.trial_breaks = response.data;  
                        console.log("Breakdown closed summary:", vm.trial_breaks);
                    } else {
                        toastr.error("No breakdown closed summary found.");
                    }
                })
                .catch(function(error) {
                    console.error("Error fetching breakdown closed summary:", error);
                    toastr.error("Error fetching breakdown summary.");
                });
        }
        
        
        function fetchOpenBreaks(machineId) {
            if (!machineId) return;  
            
            var url = baseUrl + "/breakdown/Breakdowncounts/" + machineId;
            $http.get(url)
                .then(function(response) {
                    if (response.status === 200) {
                        vm.open_breaks = response.data;  
                        console.log("Breakdown open summary:", vm.open_breaks);
                    } else {
                        toastr.error("No breakdown open summary found.");
                    }
                })
                .catch(function(error) {
                    console.error("Error fetching breakdown open summary:", error);
                    toastr.error("Error fetching breakdown summary.");
                });
        }
        
        
        
        function fetchClosedBreaks(machineId) {
            if (!machineId) return;  
            
            var url = baseUrl + "/breakdown/Breakdowncounts/" + machineId;
            $http.get(url)
                .then(function(response) {
                    if (response.status === 200) {
                        vm.closed_breaks = response.data;  
                        console.log("Breakdown Closed summary:", vm.closed_breaks);
                    } else {
                        toastr.error("No breakdown Closed summary found.");
                    }
                })
                .catch(function(error) {
                    console.error("Error fetching breakdown Closed summary:", error);
                    toastr.error("Error fetching breakdown summary.");
            });
        }  
        
        
        
         function fetchRepetativeBreaks(machineId) {
            var url = baseUrl + "/analysis_time_frames/report/" + machineId;
            genericFactory.getAll("", url)
                .then(response => {
                    vm.repetative_breaks = response.data;
                })
                .catch(error => {
                    console.error("Error fetching repetative breaks:", error);
                });
        }
        
        
            
    }

})();
