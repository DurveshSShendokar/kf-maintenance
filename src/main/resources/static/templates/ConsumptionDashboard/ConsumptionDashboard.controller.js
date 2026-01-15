(function() {
	'use strict';

	angular.module('myApp.ConsumptionDashboard').controller('ConsumptionDashboardController', ConsumptionDashboardController);


	ConsumptionDashboardController.$inject = ['$state', '$uibModal', '$log',
		'$scope', 'toastr', 'localStorageService', '$filter', 'genericFactory', 'ApiEndpoint', '$location', '$rootScope'];

	/* @ngInject */
	function ConsumptionDashboardController($state, $uibModal, $log, $scope, toastr, localStorageService, $filter, genericFactory, ApiEndpoint, $location, $rootScope) {
		const baseUrl = ApiEndpoint.url;
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var energyUrl = staticUrl + "/energy";
		var emsDashboardUrl = staticUrlMaintenance + "/emsDashboard";
		var dashboardUrl = staticUrlMaintenance + "/dashboard";
		var vm = angular.extend(this, {
			chartPieCharts: [],
			statusPieColours23: [],
			chartUnitData:[],
			chartUnitLabels:[],
			chartColors2:[]
		});

		(function activate() {
			loadOverviewCardCount()
			loadOverviewDahboardPieChart()
			loadUnitConsumptions(7)
			loadVolatgeMinMax(7)
			
			
			loadEnergyMeterMater()
			$rootScope.currentDashboard = "EMS"
			$scope.consumptionActive = true
			loadCurrentConsumptionMonthlyGraphData(2);



		})();
		$scope.goToMeterList = function() {
		
			$location.path('main/EnergyMeterMaster');
		
			$rootScope.$apply()
		}
		$scope.changeUnitConsumption=function(slectedDays){	
			console.log("slectedDays    " + slectedDays)
			loadUnitConsumptions(slectedDays)
		}
		$scope.changeValtage=function(slectedDays){	
			loadVolatgeMinMax(slectedDays)
		}
		
		
		
		function loadOverviewCardCount() {
			var msg = ""
			var url = emsDashboardUrl + "/getOverviewDahboardCardCounts";
		//	console.log("url    " + url)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.overviewDahboardCardCounts = response.data;
			});

		}
		function loadOverviewDahboardPieChart() {
			var msg = ""
			var url = emsDashboardUrl + "/getOverviewDahboardPieChart";
		

			genericFactory.getAll(msg, url).then(function(response) {
				vm.overviewDahboardPieChart = response.data;

				vm.chartPieOptions = {
					maintainAspectRatio: true,
					responsive: true
				}
				vm.chartPieCharts = [vm.overviewDahboardPieChart.active, vm.overviewDahboardPieChart.total]
				vm.statusPieLabels = ["Active", "Inactive"];
				vm.statusPieColours23 = ["red", "blue"];

			});

		}
		
		
		
		
		
		function loadUnitConsumptions(days) {
			var msg = ""
			var url = emsDashboardUrl + "/getOverviewDahboardUnitConsumption?noOfDays="+days;
			genericFactory.getAll(msg, url).then(function(response) {
				vm.overviewDahboardUnitConsumption= response.data;
				
				vm.chartUnitLabels = vm.overviewDahboardUnitConsumption.strings;

				// Series names for the lines
				vm.chartSeriesCon = vm.overviewDahboardUnitConsumption.series;

				// Data for the lines
				vm.chartUnitData = vm.overviewDahboardUnitConsumption.stringList;
				vm.chartColors2=['blue','black	']
	
			});

		}


	function loadVolatgeMinMax(days) {
			var msg = ""
			var url = emsDashboardUrl + "/getOverviewDahboardVoltageMinMax?noOfDays="+days;
			console.log("url "+url)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.overviewDahboardUnitConsumption= response.data;
				console.log("cxcc "+JSON.stringify(vm.overviewDahboardUnitConsumption))
		vm.chartLabelsComparison =  vm.overviewDahboardUnitConsumption.stringList;
vm.chartDataComparison = [
			vm.overviewDahboardUnitConsumption.max, // Closed values
			vm.overviewDahboardUnitConsumption.min   // Overdue values
		];
							


			});

		}




		/*vm.chartOptions = {
			responsive: true,
			maintainAspectRatio: false,
			scales: {
				y: {
					beginAtZero: true,
				}
			},
			plugins: {
				legend: {
					display: true,
					position: 'top'
				}
			}
		};*/

		// Chart data and labels for the comparison bar chart
	
		vm.chartColorsComparison = ['#36A2EB', '#FF6384'];







		function loadCurrentConsumptionMonthlyGraphData(meter) {
			var msg = ""
			var url = emsDashboardUrl + "/getDayWiseConsumptionGraphData?meterId=" + meter;
			genericFactory.getAll(msg, url).then(function(response) {
				vm.currentConsumptions = response.data;
				vm.chartLabelsCon = vm.currentConsumptions.label;

				// Series names for the lines
				vm.chartSeriesCon = vm.currentConsumptions.series;
		// Data for the lines
				vm.chartDataCon = [
					// Data for "Open"
					vm.currentConsumptions.data1,  // Data for "Closed"
					// Data for "Trial"
				];


			});


		}
		// ******************************************************
		$scope.goToMeter = function(meterName) {
			$location.path('main/DashboardDesign/' + meterName.meterId);
		}

		function loadEnergyMeterMater() {
			var msg = ""
			var url = energyUrl + "/getAllEnergyMeterMasters";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.locationWiseConsupmtons = response.data;

			});

		}




	}
})();
