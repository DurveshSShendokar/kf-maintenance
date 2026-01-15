(function() {
	'use strict';

	angular.module('myApp.PPMDashboard').controller('PPMDashboardController', PPMDashboardController);


	PPMDashboardController.$inject = ['$state', '$uibModal', '$log',
		'$scope', 'toastr', 'localStorageService', '$filter', 'genericFactory', 'ApiEndpoint', '$location','$rootScope'];

	/* @ngInject */
	function PPMDashboardController($state, $uibModal, $log, $scope, toastr, localStorageService, $filter, genericFactory, ApiEndpoint, $location,$rootScope) {
		const baseUrl = ApiEndpoint.url;
		var userDetail = localStorageService.get(ApiEndpoint.userKey);

		var vm = angular.extend(this, {

		});

		(function activate() {
				$rootScope.currentDashboard="PPM"
			console.log("CURR DASH "+$rootScope.currentDashboard)
			$scope.showTypeData =getWeekNumber(new Date())
			$scope.ppmActive = true
			MaintainceCount();
			ApprovedCount();
			UnApprovedCount();
			vm.selectedWeek=getCurrentWeek() ;
			
					get52WeekData(vm.selectedWeek);
		/*	loadMaintenanceCountsByMachine();*/
			loadMaintenanceOverallDashaboardGraph();
		
			TotalCount();
				let today = new Date();

		
		let dayOfWeek = today.getDay();
		
		
		let startOfWeek = new Date(today);
		startOfWeek.setDate(today.getDate() - dayOfWeek);
		
		// Calculate the difference to get the end of the week (Saturday)
		let endOfWeek = new Date(today);
		endOfWeek.setDate(today.getDate() + (6 - dayOfWeek));

		function formatDate(date) {
		    let day = String(date.getDate()).padStart(2, '0');
		    let month = String(date.getMonth() + 1).padStart(2, '0'); // Months are zero-based
		    let year = date.getFullYear();
		    return `${year}-${month}-${day}`;
		}
			$scope.startDate=formatDate(startOfWeek);
			$scope.endDate=formatDate(endOfWeek)
			
		})();
	function get52WeekData(weekno){
		
		console.log("selectedWeeek"+vm.selectedWeek)
		var msg = "";
			var url = baseUrl + "/maint/get52WeekMaintenence?weekNo="+weekno;
			genericFactory.getAll(msg, url).then(function(response) {
				vm.dataWeekMachine = response.data;
				console.log("Maintenance By Machine and Week : " + JSON.stringify(vm.dataWeekMachine));
			 calculateOpenClosedCounts();
			});
	}
	
	function calculateOpenClosedCounts() {
    angular.forEach(vm.dataWeekMachine, function(item) {
        let openCount = 0;
        let closedCount = 0;

        // Sum open and closed data for each week's data
        angular.forEach(item.weekDatas, function(weekData) {
            openCount += weekData.open || 0;  
            closedCount += weekData.closed || 0;
        });

       
        item.openTotal = openCount;
        item.closedTotal = closedCount;
    });
}
		
	
        vm.selectedWeek = getCurrentWeek() + 1; 
        vm.availableWeeks = Array.from({ length: 52 }, (_, i) => i + 1); 

		  loadMaintenanceOverallDashaboardGraph(vm.selectedWeek);

        // On dropdown change
       vm.onWeekChange = function () 
        {
            console.log("Selected Week:", vm.selectedWeek);
            loadMaintenanceOverallDashaboardGraph(vm.selectedWeek);
            get52WeekData(vm.selectedWeek);
        };

        // Function to calculate the current week of the year
        function getCurrentWeek() {
            const date = new Date();
            const startDate = new Date(date.getFullYear(), 0, 1);
            const days = Math.floor((date - startDate) / (24 * 60 * 60 * 1000));
            return Math.ceil((days + 1) / 7);
        }
		
		
		

	
		function getWeekNumber(date) {		
			let currentDate = new Date(date.getTime());
			currentDate.setHours(0, 0, 0, 0);
			currentDate.setDate(currentDate.getDate() + 3 - (currentDate.getDay() + 6) % 7);
			let week1 = new Date(currentDate.getFullYear(), 0, 4);
			// Adjust to Thursday in week 1 and count number of weeks from the beginning of the year
			return 1 + Math.round(((currentDate.getTime() - week1.getTime()) / 86400000
				- 3 + (week1.getDay() + 6) % 7) / 7);
		}
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
		   $scope.handleMaintainceCardClick = function(cardType) {
        switch (cardType) {
            case 'totalMaintaince':
                $state.go('main.home.totalMaintaince');
                break;
            default:
                vm.totalMaintaince = [];
        }
    };


$scope.gotoDetials=function(data,machineName,status){
	
	console.log("Data  : "+JSON.stringify(data))
	console.log("Machine Name : "+machineName)
		$location.path('main/WeekWiseMaintenance/'+data.weekNo+'/'+machineName+'/'+status);

	
}
    $scope.openMaintainceCardClick = function(cardType) {
        switch (cardType) {
            case 'openMaintaince':
                $state.go('main.home.openMaintaince');
                break;
            default:
                vm.openMaintaince = [];
        }
    };
    
    
     $scope.totalopenMaintainceCardClick = function(cardType) {
        switch (cardType) {
            case 'totalopenMaintaince':
                $state.go('main.home.totalopenMaintaince');
                break;
            default:
                vm.totalopenMaintaince = [];
        }
    };

	 $scope.totalclosedMaintainceCardClick = function(cardType) {
        switch (cardType) {
            case 'totalclosedMaintaince':
                $state.go('main.home.totalclosedMaintaince');
                break;
            default:
                vm.totalclosedMaintaince = [];
        }
    };

    $scope.closedMaintainceCardClick = function(cardType) {
        switch (cardType) {
            case 'closedMaintaince':
                $state.go('main.home.closedMaintaince');
                break;
            default:
                vm.closedMaintaince = [];
        }
    };

    $scope.TodayMaintainceCardClick = function(cardType) {
        switch (cardType) {
            case 'TodayMaintaince':
                $state.go('main.home.TodayMaintaince');
                break;
            default:
                vm.TodayMaintaince = [];
        }
    };
	
	$scope.handleApprovedCardClick = function(cardType) {
				switch (cardType) {
					case 'totalApproved':
						$state.go('main.home.totalApproved');
						break;
					default:
						vm.totalApproved = [];
				}
			};
			
			$scope.handleUnApprovedCardClick = function(cardType) {
							switch (cardType) {
								case 'totalUnApproved':
									$state.go('main.home.totalUnApproved');
									break;
								default:
									vm.totalUnApproved = [];
							}
						};
	
		function TotalCount() {
			var msg = "";
			var url = baseUrl + "/dashboard/total_count";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.TotalCount = response.data;
				console.log("TotalCount" + JSON.stringify(vm.TotalCount));
			});
		}
		
		
		function ApprovedCount() {
					var msg = "";
					var url = baseUrl + "/maint/getClosedApprovals";
					genericFactory.getAll(msg, url).then(function(response) {
						vm.ApprovedCount = response.data;
						console.log("ApprovedCount" + JSON.stringify(vm.ApprovedCount));
					});
				}
				
				function UnApprovedCount() {
									var msg = "";
									var url = baseUrl + "/maint/getUnApprovals";
									genericFactory.getAll(msg, url).then(function(response) {
										vm.UnApprovedCount = response.data;
										console.log("ApprovedCount" + JSON.stringify(vm.UnApprovedCount));
									});
								}

		
		
		function MaintainceCount() {
			var msg = "";
			var url = baseUrl + "/dashboard/maintaince_count";

	
				genericFactory.getAll(msg, url).then(function(response) {
				vm.MaintainceCount = response.data;
				console.log("MaintainceCount" + JSON.stringify(vm.MaintainceCount));
			});
		}
		
		// Generate an array of 52 weeks
		vm.weeks = Array.from({ length: 52 }, (_, i) => i + 1);

		
		/*function loadMaintenanceCountsByMachine() {
			var msg = "";
			var url = baseUrl + "/dashboard/getMaintenanceCountsByMachine?week="+$scope.showTypeData;
			genericFactory.getAll(msg, url).then(function(response) {
				vm.maintenanceCountsByMachine = response.data;
				console.log("Maintenance Counts By Machine: " + JSON.stringify(vm.maintenanceCountsByMachine));
				vm.updateMachineData();
			});
		}*/
		
		
		
//weekly
	function loadMaintenanceOverallDashaboardGraph(selectedWeek) {
		
		var selectedWeek = vm.selectedWeek || null;
		  var selectedMonth = vm.selectedMonth || null;
		  var currentYear = new Date().getFullYear();
		  
			/*var msg = "";
			var url = baseUrl + "/dashboard/getMaintenanceOverallDashaboardGraph?week="+selectedWeek;*/
			
			var msg = "";
			  var url = baseUrl + "/dashboard/getMaintenanceOverallDashaboardGraph?year=" + currentYear;

			  if (selectedMonth) {
			      url += "&month=" + selectedMonth;
			  } else if (selectedWeek) {
			      url += "&week=" + selectedWeek;
			  }


			
			genericFactory.getAll(msg, url).then(function(response) {
				vm.maintenanceOverallGraph= response.data;
				console.log("maintenanceOverallGraph : " + JSON.stringify(vm.maintenanceCountsByMachine));
				
					var machineNames = [];
			var openCounts = [];
			var closedCounts = [];
			var overuesCounts = [];
			angular.forEach(vm.maintenanceOverallGraph, function(item) {
				machineNames.push(item.date);
				openCounts.push(item.open);
				closedCounts.push(item.closed);
				overuesCounts.push(item.overdue)
			});
			vm.chartDataOverall = [openCounts, closedCounts,overuesCounts];
			vm.chartLabelsOverall = machineNames;
			
			 vm.chartColorsOverall = [{
            backgroundColor: 'rgba(255, 99, 132, 0.5)', // Light pink for "open"
            borderColor: 'rgba(255, 99, 132, 1)',
        }, {
            backgroundColor: 'rgba(54, 162, 235, 0.5)', // Light blue for "closed"
            borderColor: 'rgba(54, 162, 235, 1)',
        }, {
            backgroundColor: 'rgba(0, 139, 139, 0.5)', // Dark cyan with 50% transparency
    borderColor: 'rgba(0, 139, 139, 1)', 
        }];

			//	vm.updateMachineData();
			});
		}
		
		vm.updateGraphData = function (type) {
			
			if (type === 'week') {
			               vm.selectedMonth = null; // Reset month if week is selected
			           } else if (type === 'month') {
			               vm.selectedWeek = null; // Reset week if month is selected
			           }
		    console.log("Selected Week:", vm.selectedWeek);
		    console.log("Selected Month:", vm.selectedMonth);
		    loadMaintenanceOverallDashaboardGraph();
		
		};
		
		
		
		
		
		
		
		
		
		
		
/*
		vm.updateMachineData = function() {
			vm.filteredMaintenanceCountsByMachine = filterTopMachines(vm.maintenanceCountsByMachine, vm.selectedMachineCount);
			updateMaintenanceChart();
	};

		function filterTopMachines(data, count) {
			return data.sort((a, b) => (b.open + b.closed) - (a.open + a.closed)).slice(0, count);
		}


		function updateMaintenanceChart() {
			var machineNames = [];
			var openCounts = [];
			var closedCounts = [];
			angular.forEach(vm.filteredMaintenanceCountsByMachine, function(item) {
				machineNames.push(item.machineName);
				openCounts.push(item.open);
				closedCounts.push(item.closed);
			});
			vm.chartData = [openCounts, closedCounts];
			vm.chartLabels = machineNames;

			vm.chartOptions = {
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

			vm.chartColors = [{
    backgroundColor: 'rgba(255, 206, 86, 0.5)', // Light yellow
    borderColor: 'rgba(255, 206, 86, 1)',
}, {
    backgroundColor: 'rgba(153, 102, 255, 0.5)', // Light purple
    borderColor: 'rgba(153, 102, 255, 1)',
}, {
    backgroundColor: 'rgba(255, 159, 64, 0.5)', // Light orange
    borderColor: 'rgba(255, 159, 64, 1)',
}];

		}*/

	}
})();
