(function() {
	'use strict';

	angular.module('myApp.DashboardDesign').controller('DashboardDesignController', DashboardDesignController);

	DashboardDesignController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr', 'genericFactory', '$stateParams', '$interval','$rootScope'];

	function DashboardDesignController($scope, ApiEndpoint, $http, toastr, genericFactory, $stateParams, $interval,$rootScope) {

		const baseUrl = ApiEndpoint.url;
		var meter = $stateParams.id;
		var energyUrl = staticUrlMaintenance + "/energy";
		var emsDashboardUrl = staticUrlMaintenance + "/emsDashboard";
		var vm = angular.extend(this, {
	
		});

		(function activate() {
			getDashbordData(meter);
			loadMeterDetials(meter);
		
			loadCardData(meter)
			
			
			
			loadCurrentVoltageCardData(meter)
			
			
			getRegisters(meter)
			loadCurrentConsumptionMonthlyGraphData(meter)
			loadCurrentConsumptionWeeklyGraphData(meter)
			loadVoltageDropGraph(meter);
			loadCurrentDropGraphData(meter);
			
		})();
		
		
			$scope.show=function(index){
			console.log("show fun")
			vm.registerGroups[index].showView=true;
			$scope.showOtherDetials=true;
		}
		$scope.hide=function(index){
			console.log("Hide fun")
			$scope.showOtherDetials=false;
			vm.registerGroups[index].showView=false;
		}
		function getRegisters(meter) {

			var msg = ""
			var url = energyUrl + "/getAllRegisterDetialsByMeterId?meterId="+meter;
			console.log("url : " +url)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.registerGroups = response.data;
				
				//console.log("registers : " + JSON.stringify(vm.registerGroups))

			});


		}
		
			function getDashbordData(meter) {
				$rootScope.loader=true;
	console.log("Load CARD ....................................................")
			var msg = ""
			var url = energyUrl + "/getDashbordData?meterId="+meter;
		//	console.log("url : " +url)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.dashboardData = response.data;
				$rootScope.loader=false;
				console.log("dashboardData : " + JSON.stringify(vm.dashboardData))

			});


		}
			function loadCardData(meter) {
			console.log("machines : ")
			var msg = ""
			var url = emsDashboardUrl+"/getCardData?meterId=" + meter;
			//console.log("energyUrl "+url)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.cardData = response.data;
				//console.log("  cardData : " + JSON.stringify(vm.cardData));
			
			});


		}
		function loadCurrentVoltageCardData(meter) {
			//console.log("machines : ")
			var msg = ""
			var url = emsDashboardUrl+"/getCurrentVoltageCardData?meterId=" + meter;
			//console.log("energyUrl "+url)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.currentVoltageCard = response.data;
				console.log("  currentVoltageCard : " + JSON.stringify(vm.currentVoltageCard));
				
				
				
				
				
					
			vm.radius = 80;
			vm.circumference =288
			
			vm.currentAProgress = 0;
			$interval(function() {
				if (vm.currentAProgress < vm.currentVoltageCard.currentA) {
					vm.currentAProgress += 1;
				}
			}, 100);
				
			vm.currentBProgress = 0;
			$interval(function() {
				if (vm.currentBProgress < vm.currentVoltageCard.currentB) {
					vm.currentBProgress += 1;
				}
			}, 100);	
				
			vm.currentCProgress = 0;
			$interval(function() {
				if (vm.currentCProgress < vm.currentVoltageCard.currentC) {
					vm.currentCProgress += 1;
				}
			}, 100);
			
			
			vm.currentGProgress = 0;
			$interval(function() {
				if (vm.currentGProgress < vm.currentVoltageCard.currentG) {
					vm.currentGProgress += 1;
				}
			}, 100);
			
			
			vm.currentNProgress = 0;
			$interval(function() {
				if (vm.currentNProgress < vm.currentVoltageCard.currentN) {
					vm.currentNProgress += 1;
				}
			}, 100);	
				
				
				vm.currentAvgProgress = 0;
			$interval(function() {
				if (vm.currentAvgProgress < vm.currentVoltageCard.currentAvg) {
					vm.currentAvgProgress += 1;
				}
			}, 100);	
					
			vm.currentPFProgress = 0;
			$interval(function() {
				if (vm.currentPFProgress < vm.currentVoltageCard.powerFactor) {
					vm.currentPFProgress += 1;
				}
			}, 100);
				
				
			vm.apparentProgress = 0;
			$interval(function() {
				if (vm.apparentProgress < vm.currentVoltageCard.apparent) {
					vm.apparentProgress += 1;
				}
			}, 100);
				
				
				
				
				
			vm.voltageABProgress = 0;
			$interval(function() {
				if (vm.voltageABProgress < vm.currentVoltageCard.voltageAB) {
					vm.voltageABProgress += 1;
				}
			}, 100);
			
			
			
				vm.voltageBCProgress = 0;
			$interval(function() {
				if (vm.voltageBCProgress < vm.currentVoltageCard.voltageBC) {
					vm.voltageBCProgress += 1;
				}
			}, 100);
			
			
			
			
				vm.voltageCAProgress = 0;
			$interval(function() {
				if (vm.voltageCAProgress < vm.currentVoltageCard.voltageCA) {
					vm.voltageCAProgress += 1;
				}
			}, 100);
			
			
			
			
				vm.voltageLLProgress = 0;
			$interval(function() {
				if (vm.voltageLLProgress < vm.currentVoltageCard.voltageLL) {
					vm.voltageLLProgress += 1;
				}
			}, 100);
			
			
			
			
				vm.voltageANProgress = 0;
			$interval(function() {
				if (vm.voltageANProgress < vm.currentVoltageCard.voltageAN) {
					vm.voltageANProgress += 1;
				}
			}, 100);
			
			
			
			
				vm.voltageBNProgress = 0;
			$interval(function() {
				if (vm.voltageBNProgress < vm.currentVoltageCard.voltageBN) {
					vm.voltageBNProgress += 1;
				}
			}, 100);
			
			
			
			
			
				vm.voltageCNProgress = 0;
			$interval(function() {
				if (vm.voltageCNProgress < vm.currentVoltageCard.voltageCN) {
					vm.voltageCNProgress += 1;
				}
			}, 100);		
			
			
			
				vm.voltageLNProgress = 0;
			$interval(function() {
				if (vm.voltageLNProgress < vm.currentVoltageCard.voltageLN) {
					vm.voltageLNProgress += 1;
				}
			}, 100);	
			
			});


		}
		function loadMeterDetials(meter) {
			console.log("machines : ")
			var msg = ""
			var url = energyUrl+"/getEnergyMeterById?engergyMeterId=" + meter;
		//	console.log("energyUrl "+url)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.meterDetails = response.data;
				//console.log("  2 : " + JSON.stringify(vm.meterDetails[0]));
			
			});


		}
		function loadVoltageDropGraph(meter) {
			console.log("machines : ")
			var msg = ""
			var url = emsDashboardUrl + "/getVoltageDropGraphData?meterId=" + meter;
			genericFactory.getAll(msg, url).then(function(response) {
				vm.voltageDrops = response.data;
					vm.chartLabelsV = vm.voltageDrops.label;

			// Series names for the lines
			vm.chartSeriesV = vm.voltageDrops.series;

			// Data for the lines
			vm.chartDataV = [
		  // Data for "Open"
				vm.voltageDrops.data1,  // Data for "Closed"
				vm.voltageDrops.data2  // Data for "Trial"
			];


			});


		}
			function loadCurrentDropGraphData(meter) {
			console.log("machines : ")
			var msg = ""
			var url = emsDashboardUrl + "/getCurrentDropGraphData?meterId=" + meter;
			genericFactory.getAll(msg, url).then(function(response) {
				vm.currentDrops = response.data;
					vm.chartLabelsI = vm.currentDrops.label;

			// Series names for the lines
			vm.chartSeriesI = vm.currentDrops.series;

			// Data for the lines
			vm.chartDataI = [
		  // Data for "Open"
				vm.currentDrops.data1,  // Data for "Closed"
				 // Data for "Trial"
			];


			});


		}
			function loadCurrentConsumptionMonthlyGraphData(meter) {
			var msg = ""
			var url = emsDashboardUrl + "/getMonthlyConsumptionGraphData?meterId=" + meter;
			genericFactory.getAll(msg, url).then(function(response) {
				vm.currentConsumptionsM = response.data;
					vm.chartLabelsConM = vm.currentConsumptionsM.label;

			// Series names for the lines
			vm.chartSeriesConM = vm.currentConsumptionsM.series;

			// Data for the lines
			vm.chartDataConM = [
		  // Data for "Open"
				vm.currentConsumptionsM.data1,  // Data for "Closed"
				 // Data for "Trial"
			];


			});


		}
		function loadCurrentConsumptionWeeklyGraphData(meter) {
			console.log("machines : ")
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
		
		
		
		
		
			
			
			
			
			
			
			
			
			// Labels for the x-axis
						// Options for the chart (optional)
			vm.chartOptions2 = {
				responsive: true,
				scales: {
					yAxes: [{
						ticks: {
							beginAtZero: true
						}
					}]
				}
			};

			// Colors for the lines (optional)
			vm.chartColors2 = [
				{ // Open line
					backgroundColor: 'rgba(75, 192, 192, 0.2)',
					borderColor: 'rgba(75, 192, 192, 1)',
					pointBackgroundColor: 'rgba(75, 192, 192, 1)',
					pointBorderColor: '#fff',
					pointHoverBackgroundColor: '#fff',
					pointHoverBorderColor: 'rgba(75, 192, 192, 1)'
				},
				{ // Closed line
					backgroundColor: 'rgba(153, 102, 255, 0.2)',
					borderColor: 'rgba(153, 102, 255, 1)',
					pointBackgroundColor: 'rgba(153, 102, 255, 1)',
					pointBorderColor: '#fff',
					pointHoverBackgroundColor: '#fff',
					pointHoverBorderColor: 'rgba(153, 102, 255, 1)'
				},
				/*{ // Trial line
					backgroundColor: 'rgba(255, 159, 64, 0.2)',
					borderColor: 'rgba(255, 159, 64, 1)',
					pointBackgroundColor: 'rgba(255, 159, 64, 1)',
					pointBorderColor: '#fff',
					pointHoverBackgroundColor: '#fff',
					pointHoverBorderColor: 'rgba(255, 159, 64, 1)'
				}*/
			];
	}
})();
