(function() {
	'use strict';

	angular.module('myApp.MeterDetials').controller('MeterDetialsController', MeterDetialsController);

	MeterDetialsController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr', 'genericFactory', 'localStorageService','$stateParams','$rootScope'];

	function MeterDetialsController($scope, ApiEndpoint, $http, toastr, genericFactory, localStorageService,$stateParams,$rootScope) {
		var media=$stateParams.id;
		var energyUrl = staticUrl + "/energy";

		var vm = angular.extend(this, {
		dayWiseCountSize:7,
		SelYear:2024,
getLastDaysConsupmtion:getLastDaysConsupmtion,
getYearConsupmtion:getYearConsupmtion,
yearWiseConsupmtion:yearWiseConsupmtion,

		});
	
		(function activate() {
			$scope.showOtherDetials=true;
			loadMeterDetialsById();
			getLastDaysConsupmtion();
			getYearConsupmtion();
			getRegisters();
				getLastDaysCost();
				getYearCost();
		$rootScope.currentDashboard="EMS"
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
		 vm.chartData = [65, 59, 80, 81, 56, 55, 40];
		 // Chart labels
      vm.chartLabels = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];

      // Chart options
      vm.chartOptions = {
          responsive: true,
          scales: {
              yAxes: [{
                  ticks: {
                      beginAtZero: true
                  }
              }]
          }
      };

      // Chart colors
      vm.chartColors = [{
          backgroundColor: 'rgba(54, 162, 235, 0.2)',
          borderColor: 'rgba(54, 162, 235, 1)',
          borderWidth: 1
      }];
		function loadMeterDetialsById() {

			var msg = ""
			var url = energyUrl + "/getEnergyMeterMasterById?id="+media;
			genericFactory.getAll(msg, url).then(function(response) {
				vm.meter = response.data;
			//	console.log("meter : " + JSON.stringify(vm.meter))

			});


		}
		function getLastDaysCost(){
			var msg = ""
			var url = energyUrl + "/getLastDaysCurrentCostById?meterId="+media+"&days="+vm.dayWiseCountSize;
			console.log("url : " +url)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.daysCost= response.data;
				vm.dayWiseCostData=vm.daysCost.doubleList
				vm.dayWiseCostLabels=vm.daysCost.strings
				console.log("meter : " + JSON.stringify(vm.dayWiseCostData))

			});
		var msg = ""
			var url1 = energyUrl + "/getLastDaysCostTableById?meterId="+media+"&days="+vm.dayWiseCountSize;
			console.log("url : " +url1)
			genericFactory.getAll(msg, url1).then(function(response) {
				vm.daysCostTables = response.data;
				
				//console.log("meter : " + JSON.stringify(vm.daysConsumptionTables))

			});

		}
			function getLastDaysConsupmtion() {

			var msg = ""
			var url = energyUrl + "/getLastDaysCurrentConsupmtionById?meterId="+media+"&days="+vm.dayWiseCountSize;
			console.log("url : " +url)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.daysConsumptions = response.data;
				vm.dayWiseConsuptionData=vm.daysConsumptions.doubleList
				vm.dayWiseConsuptionLabels=vm.daysConsumptions.strings
		//		console.log("meter : " + JSON.stringify(vm.daysConsumptions))

			});
		var msg = ""
			var url1 = energyUrl + "/getLastDaysConsupmtionTableById?meterId="+media+"&days="+vm.dayWiseCountSize;
			console.log("url : " +url1)
			genericFactory.getAll(msg, url1).then(function(response) {
				vm.daysConsumptionTables = response.data;
				
			//	console.log("meter : " + JSON.stringify(vm.daysConsumptionTables))

			});

		}
		function getRegisters() {

			var msg = ""
			var url = energyUrl + "/getAllRegisterDetialsByMeterId?meterId="+media;
			console.log("url : " +url)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.registerGroups = response.data;
				
				console.log("registers : " + JSON.stringify(vm.registerGroups))

			});


		}
		
		function yearWiseConsupmtion(selYear){
			var msg = ""
			var url = energyUrl + "/getYearWiseConsupmtionByIdAndYear?id="+media+"&year="+selYear;
			console.log("url : " +url)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.yearsConsumptions = response.data;
				vm.yearWiseConsuptionData=vm.yearsConsumptions.doubleList
				vm.yearWiseConsuptionLabels=vm.yearsConsumptions.strings
			//	console.log("meter : " + JSON.stringify(vm.yearsConsumptions))

			});	
		var url1 = energyUrl + "/getYearWiseConsupmtionTableByIdAndYear?id="+media+"&year="+vm.SelYear;
			console.log("url : " +url1)
			genericFactory.getAll(msg, url1).then(function(response) {
				vm.yearsConsumptionTables = response.data;
				
				//console.log("yearsConsumptionTables : " + JSON.stringify(vm.yearsConsumptionTables))

			});	
		}
		function getYearCost(){
						var msg = ""
			var url = energyUrl + "/getMonthlyCurrentCostById?meterId="+media+"&year="+vm.SelYear;
			console.log("url : " +url)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.yearsCost= response.data;
				vm.yearWiseCostData=vm.yearsCost.doubleList
				vm.yearWiseCostLabels=vm.yearsCost.strings
			//	console.log("meter : " + JSON.stringify(vm.yearsConsumptions))

			});

	
			var url1 = energyUrl + "/getMonthlyCurrentCostTableById?meterId="+media+"&year="+vm.SelYear;
			console.log("url : " +url1)
			genericFactory.getAll(msg, url1).then(function(response) {
				vm.yearsCostTables = response.data;
				
			//	console.log("yearsConsumptionTables : " + JSON.stringify(vm.yearsConsumptionTables))

			});	
		}
	function getYearConsupmtion() {

			var msg = ""
			var url = energyUrl + "/getYearWiseConsupmtionByIdAndYear?id="+media+"&year="+vm.SelYear;
			console.log("url : " +url)
			genericFactory.getAll(msg, url).then(function(response) {
				vm.yearsConsumptions = response.data;
				vm.yearWiseConsuptionData=vm.yearsConsumptions.doubleList
				vm.yearWiseConsuptionLabels=vm.yearsConsumptions.strings
			//	console.log("meter : " + JSON.stringify(vm.yearsConsumptions))

			});

	
			var url1 = energyUrl + "/getYearWiseConsupmtionTableByIdAndYear?id="+media+"&year="+vm.SelYear;
			console.log("url : " +url1)
			genericFactory.getAll(msg, url1).then(function(response) {
				vm.yearsConsumptionTables = response.data;
				
			//	console.log("yearsConsumptionTables : " + JSON.stringify(vm.yearsConsumptionTables))

			});	
		}

	}
})();
