(function() {
	'use strict';

	angular.module('myApp.lab_mst').controller('GaugeChartController', GaugeChartController);

	GaugeChartController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr', 'genericFactory', '$stateParams','$interval'];

	function GaugeChartController($scope, ApiEndpoint, $http, toastr, genericFactory, $stateParams,$interval) {

		var meter = $stateParams.id;
		var energyUrl = staticUrlMaintenance + "/energy";
		var dashboardUrl = staticUrlMaintenance + "/dashboard";
		var emsDashboardUrl = staticUrlMaintenance + "/emsDashboard";
		var vm = angular.extend(this, {

		});
		(function activate() {
			loadMeterDetials(meter);
			loadMeterStatus(meter)
			loadMeterWiseCardData(meter)
			loadVoltageDropGraph(meter)
			loadCurrentDropGraphData(meter)
			getRegisters(meter)
			loadCurrentVoltageCardData(meter)
		})();
		$scope.progressPercentage = 0; // Initial progress
	$scope.currentprogressPercentage = 0; 
		$scope.powerFactorprogressPercentage = 0; 
		function loadMeterWiseCardData(meter) {
			var msg = ""
			var url = emsDashboardUrl + "/getMeterWiseCardData?meterId=" + meter;
			console.log("energyUrl " + url)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.meterCardData = response.data;
				console.log("  meterCardData: " + JSON.stringify(vm.meterCardData));
					var voltagePer= (vm.meterCardData.voltage/480)*100;
					var pfPer= (vm.meterCardData.powerFactor/1)*100;
				/*	$scope.voltageValueshow=vm.meterCardData.voltage
				
				$scope.progressPercentage =voltagePer // Initial progress
				$scope.currentprogressPercentage = vm.meterCardData.current; */
				$scope.powerFactorprogressPercentage = pfPer; 
					$scope.pfPerValue = vm.meterCardData.powerFactor; 
					});


		}
    // Function to calculate the end point of the arc based on percentage
    function calculateProgressPath(progress) {
        var radius = 90;
        var startX = 10;
        var startY = 110;
        var endX, endY;

        // Convert percentage to an angle (0% = 180 degrees, 100% = 360 degrees)
        var angle = Math.PI * progress / 100; // Progress in radians

        // Calculate end point of the arc based on angle
        endX = 100 + radius * Math.cos(Math.PI + angle);  // Adjust angle for left hemisphere
        endY = 110 + radius * Math.sin(Math.PI + angle);  // Adjust for height

        // Determine if it's a large arc or a small arc
        var largeArcFlag = progress > 100 ? 1 : 0;

        // Define the path using the Arc (A) command for SVG
        return `M 10 110 A ${radius} ${radius} 0 ${largeArcFlag} 1 ${endX} ${endY}`;
    }



    // Watch for changes in the progressPercentage to update the path
    $scope.$watch('progressPercentage', function(newValue) {
        $scope.progressPath = calculateProgressPath(newValue);
    });
      $scope.$watch('currentprogressPercentage', function(newValue) {
        $scope.currentProgessPath = calculateProgressPath(newValue);
    });
 $scope.$watch('powerFactorprogressPercentage', function(newValue) {
        $scope.powerFactorprogressPath = calculateProgressPath(newValue);
    });
    // Example of dynamically updating progress
   /*	 setInterval(function() {
        $scope.progressPercentage = ($scope.progressPercentage + 5) % 101; // Increment progress
        $scope.$apply();
    }, 1000);
		*/
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		$scope.show = function(index) {
			console.log("show fun")
			vm.registerGroups[index].showView = true;
			$scope.showOtherDetials = true;
		}
		$scope.hide = function(index) {
			console.log("Hide fun")
			$scope.showOtherDetials = false;
			vm.registerGroups[index].showView = false;
		}
		function getRegisters(meter) {

			var msg = ""
			var url = emsDashboardUrl + "/getAllRegisterDetialsByMeterId?meterId=" + meter;
			console.log("url : " + url)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.registerGroups = response.data;

				//console.log("registers : " + JSON.stringify(vm.registerGroups))

			});


		}

		function loadMeterDetials(meter) {
			console.log("machines : ")
			var msg = ""
			var url = energyUrl + "/getEnergyMeterById?engergyMeterId=" + meter;
			//	console.log("energyUrl "+url)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.meterDetails = response.data;


			});


		}

		function loadMeterStatus(meter) {
			var msg = ""
			var url = emsDashboardUrl + "/getMeterActiveStatus?materId=" + meter;
			console.log("energyUrl " + url)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.meterStatus = response.data;
				console.log("  meterStatus: " + JSON.stringify(vm.meterStatus));

			});


		}
	

		function loadVoltageDropGraph(meter) {
			console.log("machines : ")
			var msg = ""
			var url = emsDashboardUrl + "/getVoltageDropGraphData?meterId=" + meter;
			genericFactory.getAll(msg, url).then(function(response) {
				vm.voltageDrops = response.data;
				vm.chartLabelsV = vm.voltageDrops.label;
			console.log("DATA "+JSON.stringify(vm.voltageDrops))
			
			
			vm.chartColors2=['#58e62d','#df3a18']
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
		function loadCurrentVoltageCardData(meter) {
			//console.log("machines : ")
			var msg = ""
			var url = emsDashboardUrl+"/getCurrentVoltageCardData?meterId=" + meter;
			//console.log("energyUrl "+url)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.currentVoltageCard = response.data;
				console.log("  currentVoltageCard : " + JSON.stringify(vm.currentVoltageCard));
				
				
				var voltagetoal=parseFloat(vm.currentVoltageCard.voltageRY )+parseFloat(vm.currentVoltageCard.voltageYB)+parseFloat(vm.currentVoltageCard.voltageBR) 
					var currenttoal=parseFloat(vm.currentVoltageCard.currentR) +parseFloat(vm.currentVoltageCard.currentY)+parseFloat(vm.currentVoltageCard.currentB); 
					var voltageAvg=parseFloat(voltagetoal/3).toFixed(2);;
						var currentAvg=parseFloat(currenttoal/3).toFixed(2);
					console.log("  voltagetoal : " + JSON.stringify(voltagetoal));
					console.log("  currenttoal : " + JSON.stringify(currenttoal));
			
			console.log("  voltageAvg : " + JSON.stringify(voltageAvg));
			console.log("  currentAvg : " + JSON.stringify(currentAvg));
			var voltagePer= (voltageAvg/440)*100;
			var curentPer= (currentAvg/1000)*100;
			console.log("  curentPer : " + JSON.stringify(curentPer));
			console.log("  voltagePer : " + JSON.stringify(voltagePer));
			$scope.progressPercentage =voltagePer
			$scope.voltageValueshow=voltageAvg
			
			$scope.currentValueshow=currentAvg
			
			$scope.currentprogressPercentage=curentPer
			
			
			
				//$scope.progressPercentage =voltagePer // Initial progress
				
			
			});


		}
	}
})();
