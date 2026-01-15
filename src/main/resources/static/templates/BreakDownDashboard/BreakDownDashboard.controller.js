(function() {
	'use strict';

	angular.module('myApp.BreakDownDashboard').controller('BreakDownDashboardController', BreakDownDashboardController);


	BreakDownDashboardController.$inject = ['$state', '$uibModal', '$log',
		'$scope', 'toastr', 'localStorageService', '$filter', 'genericFactory', 'ApiEndpoint', '$location','$rootScope'];

	/* @ngInject */
	function BreakDownDashboardController($state, $uibModal, $log, $scope, toastr, localStorageService, $filter, genericFactory, ApiEndpoint, $location,$rootScope) {
		const baseUrl = ApiEndpoint.url;
		var userDetail = localStorageService.get(ApiEndpoint.userKey);

		var vm = angular.extend(this, {
			

		});
		
		vm.selectedMachineCount= 5;

		(function activate() {
			 $rootScope.currentDashboard = "BreakDown";
    BreakdownCount();
    loadBreakdownCountsByMachine();
    loadgetBreakdownGraphData();
    
   vm.selectedWeek = getCurrentWeek();
	get52WeekData(vm.selectedWeek);

    MachineCount();
    BreakCount();

  
    $scope.brekdownActive = true;
    $scope.showTypeData = getWeekNumber(new Date());

    // Set start and end dates for the current week
    let today = new Date();
    let dayOfWeek = today.getDay();
    let startOfWeek = new Date(today);
    startOfWeek.setDate(today.getDate() - dayOfWeek);
    let endOfWeek = new Date(today);
    endOfWeek.setDate(today.getDate() + (6 - dayOfWeek));

    $scope.startDate = formatDate(startOfWeek);
    $scope.endDate = formatDate(endOfWeek);
    
		function formatDate(date) {
		    let day = String(date.getDate()).padStart(2, '0');
		    let month = String(date.getMonth() + 1).padStart(2, '0'); // Months are zero-based
		    let year = date.getFullYear();
		    return `${year}-${month}-${day}`;
		}
			

		})();
		
		
		function get52WeekData(weekno) {
			    if (!weekno) {
			        weekno = getCurrentWeek(); // Default to the current week
			    }
			    console.log("Selected Week:", weekno);
			    var url = baseUrl + "/breakdown/get52WeekBreakDown?weekNo=" + weekno;
			    genericFactory.getAll("", url).then(function(response) {
			        vm.dataWeekMachine = response.data;
			        console.log("Breakdown By Machine and Week:", JSON.stringify(vm.dataWeekMachine));
			        calculateOpenClosedCounts();
			    });
			}

	
	
	function calculateOpenClosedCounts() {
    angular.forEach(vm.dataWeekMachine, function(item) {
        let openCount = 0;
        let closedCount = 0;
         let TrialCount = 0;

        // Sum open and closed data for each week's data
        angular.forEach(item.weekDatas, function(weekData) {
            openCount += weekData.open || 0;  
            closedCount += weekData.closed || 0;
             TrialCount += weekData.trail || 0;
        });

       
        item.openTotal = openCount;
        item.closedTotal = closedCount;
        item.TrialCount = TrialCount;
    });
}
		
		
		$scope.gotoDetials=function(data,machineName,status){
			console.log("Data: " + JSON.stringify(data));
			console.log("machineName: " + machineName);
				$location.path('main/WeekWiseBrekdown/'+data.weekNo+'/'+machineName+'/'+status);
		}
		
		  vm.selectedWeek = getCurrentWeek(); 
        vm.availableWeeks = Array.from({ length: 52 }, (_, i) => i + 1); 
		
		  // Function to calculate the current week of the year
        function getCurrentWeek() {
            const date = new Date();
            const startDate = new Date(date.getFullYear(), 0, 1);
            const days = Math.floor((date - startDate) / (24 * 60 * 60 * 1000));
            return Math.ceil((days + 1) / 7);
        }

		 // On dropdown change
       vm.onWeekChange = function () 
        {
            console.log("Selected Week:", vm.selectedWeek);
            //loadMaintenanceOverallDashaboardGraph(vm.selectedWeek);
            get52WeekData(vm.selectedWeek);
        };
	
		
			function getWeekNumber(date) {
			let currentDate = new Date(date.getTime());
			currentDate.setHours(0, 0, 0, 0);
			currentDate.setDate(currentDate.getDate() + 3 - (currentDate.getDay() + 6) % 7);
			let week1 = new Date(currentDate.getFullYear(), 0, 4);
			return 1 + Math.round(((currentDate.getTime() - week1.getTime()) / 86400000
				- 3 + (week1.getDay() + 6) % 7) / 7);
		}
		    $scope.handleBreakdownCardClick = function(cardType) {
        switch (cardType) {
            case 'totalBreakdown':
                $state.go('main.home.totalBreakdown');
                break;
            default:
                vm.totalBreakdown = [];
        }
    };



$scope.handleUsersCardClickover = function(cardType) {
			switch (cardType) {
				case 'overallBreakdown':
					$state.go('main.home.overallBreakdown');
					break;
				default:
					vm.overallBreakdown = [];
			}
		};

$scope.handleUsersCardClickopen = function(cardType) {
			switch (cardType) {
				case 'totalopenBreakdown':
					$state.go('main.home.totalopenBreakdown');
					break;
				default:
					vm.totalopenBreakdown = [];
			}
		};

$scope.handleUsersCardClicktrial = function(cardType) {
			switch (cardType) {
				case 'totaltrialBreakdown':
					$state.go('main.home.totaltrialBreakdown');
					break;
				default:
					vm.totaltrialBreakdown = [];
			}
		};

$scope.handleUsersCardClickclosed = function(cardType) {
			switch (cardType) {
				case 'totalclosedBreakdown':
					$state.go('main.home.totalclosedBreakdown');
					break;
				default:
					vm.totalclosedBreakdown = [];
			}
		};



    $scope.handleOpenBreakdownCardClick = function(cardType) {
        switch (cardType) {
            case 'openBreakdown':
                $state.go('main.home.openBreakdown');
                break;
            default:
                vm.openBreakdown = [];
        }
    };

    $scope.handleTrialBreakdownCardClick = function(cardType) {
        switch (cardType) {
            case 'trialBreakdown':
                $state.go('main.home.trialBreakdown');
                break;
            default:
                vm.trialBreakdown = [];
        }
    };

    $scope.handleClosedBreakdownCardClick = function(cardType) {
        switch (cardType) {
            case 'closedBreakdown':
                $state.go('main.home.closedBreakdown');
                break;
            default:
                vm.closedBreakdown = [];
        }
    };
    
    // Existing handle card click functions
		$scope.handleMachinesCardClick = function(cardType) {
			switch (cardType) {
				case 'totalMachines':
					$state.go('main.home.totalMachines');
					break;
				default:
					vm.totalMachines = [];
			}
		};
    
		$scope.goToPPMDashboard = function() {
			console.log("locationWiseConsupmtons : ")
			$location.path('main/PPMDashboard');
		}
		$scope.goToBreakDownDashboard = function() {
			console.log("locationWiseConsupmtons : ")
			$location.path('main/BreakDownDashboard');
		}
		$scope.goToConsumptionDashboard = function() {
			console.log("locationWiseConsupmtons : ")
			$location.path('main/ConsumptionDashboard');
		}
		// ******************************************************

			function BreakCount() {
			var msg = "";
			var url = baseUrl + "/breakdown/breakdownstatusCounts";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.BreakCount = response.data;
				console.log("BreakCount" + JSON.stringify(vm.BreakCount));
			});
		}
		
		
			function MachineCount() {
			var msg = "";
			var url = baseUrl + "/dashboard/total_count";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.MachineCount = response.data;
				console.log("BreakCount" + JSON.stringify(vm.MachineCount));
			});
		}


		function BreakdownCount() {
			var msg = "";
			var url = baseUrl + "/dashboard/breakdown_count";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.BreakdownCount = response.data;
				console.log("BreakdownCount" + JSON.stringify(vm.BreakdownCount));
			});
		}
		

		vm.updateMachineData2 = function () {
    vm.filteredBreakdownCountsByMachine = filterTopMachines2(vm.BreakdownCountsByMachine, vm.selectedMachineCount);
    updateBreakdownChart();
};

vm.updateMachineData3 = function () {
    vm.filteredBreakdownCountsByMachineOwner = filterTopMachines2(vm.BreakdownCountsByMachineOwner, vm.selectedMachineCount);
    updategetBreakdownGraphDataChart();
};

		
		
		function loadBreakdownCountsByMachine() {
			var msg = "";
			var url = baseUrl + "/dashboard/getBreakdownCountsByMachine";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.BreakdownCountsByMachine = response.data;
				console.log("Breakdown Counts By Machine: " + JSON.stringify(vm.BreakdownCountsByMachine));
				vm.updateMachineData2();
			});
		}



		function loadgetBreakdownGraphData() {
			var msg = "";
			var url = baseUrl + "/dashboard/getBreakdownGraphData";
			console.log("URL "+url)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.BreakdownCountsByMachineOwner = response.data;
				console.log("Breakdown Counts By Machine Owner: " + JSON.stringify(vm.BreakdownCountsByMachineOwner));
				vm.updateMachineData3();
			});
		}
		
		
		function updategetBreakdownGraphDataChart() {
			var machineOwners = [];
			var openCounts = [];
			var closedCounts = [];
			var trialCounts = [];

			angular.forEach(vm.filteredBreakdownCountsByMachineOwner, function(item) {
	console.log("ITEM",item)
				machineOwners.push(item.user.firstName);
				openCounts.push(item.open);
				closedCounts.push(item.closed);
				trialCounts.push(item.trail);
			});

			vm.chartData2 = [openCounts, closedCounts, trialCounts];
			vm.chartLabels2 = machineOwners;

			vm.chartOptions2 = {
				scales: {
					yAxes: [{
						stacked: true,
						ticks: {
							beginAtZero: true
						},
						gridLines: {
							color: "rgba(0, 0, 0, 0)"
						}
					}],
					xAxes: [{
						stacked: true,
						gridLines: {
							color: "rgba(0, 0, 0, 0)"
						}
					}]
				},
				legend: {
					display: true,
					labels: {
						filter: function(legendItem, chartData) {
							return legendItem.datasetIndex === 0 || legendItem.datasetIndex === 1 || legendItem.datasetIndex === 2;
						},
						usePointStyle: true
					}
				}
			};

			vm.chartColors2 = [{
				backgroundColor: 'rgba(139, 0, 0, 0.5)',
				borderColor: 'rgba(139, 0, 0, 1)',
			}, {
				backgroundColor: 'rgba(0, 0, 139, 0.5)',
				borderColor: 'rgba(0, 0, 139, 1)',
			}, {
				backgroundColor: 'rgba(0, 100, 0, 0.5)',
				borderColor: 'rgba(0, 100, 0, 1)',
			}];
		}

		function updateBreakdownChart() {
			var machineNames = [];
			var openCounts = [];
			var closedCounts = [];
			var trialCounts = [];
			angular.forEach(vm.filteredBreakdownCountsByMachine, function(item) {
				machineNames.push(item.machineName);
				openCounts.push(item.open);
				closedCounts.push(item.closed);
				trialCounts.push(item.trail);
			});
			vm.chartData1 = [openCounts, closedCounts, trialCounts];
			vm.chartLabels1 = machineNames;

			vm.chartOptions1 = {
				scales: {
					yAxes: [{
						stacked: true,
						ticks: {
							beginAtZero: true
						},
						gridLines: {
							color: "rgba(0, 0, 0, 0)"
						}
					}],
					xAxes: [{
						stacked: true,
						gridLines: {
							color: "rgba(0, 0, 0, 0)"
						}
					}]
				},
				legend: {
					display: true,
					labels: {
						filter: function(legendItem, chartData) {
							return legendItem.datasetIndex === 0 || legendItem.datasetIndex === 1 || legendItem.datasetIndex === 2;
						},
						usePointStyle: true
					}
				}
			};

			vm.chartColors1 = [{
				backgroundColor: 'rgba(139, 0, 0, 0.5)',
				borderColor: 'rgba(139, 0, 0, 1)',
			}, {
				backgroundColor: 'rgba(0, 0, 139, 0.5)',
				borderColor: 'rgba(0, 0, 139, 1)',
				
				
			}, {
				backgroundColor: 'rgba(0, 100, 0, 0.5)',
				borderColor: 'rgba(0, 100, 0, 1)',
			}];
		}

		function loadgetBreakdownGraphData() {
			var msg = "";
			var url = baseUrl + "/dashboard/getBreakdownGraphData";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.BreakdownCountsByMachineOwner = response.data;
				console.log("Breakdown Counts By Machine Owner: " + JSON.stringify(vm.BreakdownCountsByMachineOwner));
				vm.updateMachineData3();
			});
		} function filterTopMachines(data, count) {
			return data.sort((a, b) => (b.open + b.closed) - (a.open + a.closed)).slice(0, count);
		}

		function filterTopMachines2(data, count) {

			//console.log('Original Data:', JSON.stringify(data, null, 2));
			const sortedData = data.slice().sort((a, b) => {
				const aTotal = a.open + a.closed + a.trail;
				const bTotal = b.open + b.closed + b.trail;
				return bTotal - aTotal;
			});
			//console.log('Sorted Data:', JSON.stringify(sortedData, null, 2));

			const filteredData = sortedData.slice(0, count);

			//console.log('Filtered Data:', JSON.stringify(filteredData, null, 2));

			return filteredData;
		}
	}
})();
