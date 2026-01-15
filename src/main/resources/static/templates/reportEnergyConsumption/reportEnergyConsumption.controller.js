(function() {
	'use strict';

	angular.module('myApp.reportEnergyConsumption').controller('ReportEnergyConsumptionController', ReportEnergyConsumptionController)

	ReportEnergyConsumptionController.$inject = [ '$state', '$uibModal', '$log',
			'$scope', 'toastr','genericFactory' ];
	/* @ngInject */
	function ReportEnergyConsumptionController($state, $uibModal, $log, $scope, toastr,genericFactory) {
			var energyUrl = staticUrl + "/energy";
		var vm = angular.extend(this, {
		cancel:cancel,
		getData:getData,
		years:[],
		});

		(function activate() {
		getMeters();
		vm.weeks=getWeekNumbersUpToToday()
		console.log("Weeks   "+JSON.stringify(vm.weeks))
		console.log("122   "+JSON.stringify(getWeekStartAndEndDate(5, 2024)));
		var currentYear = new Date().getFullYear(); // Get the current year
 // Initialize an empty array

			for (let i = 0; i < 5; i++) {
			  vm.years.push(currentYear - i); // Push the last 5 years into the array
			}
			
			
			
			
		})();

		function cancel(){
			
		}
		
		
		
		
		function getWeekStartAndEndDate(weekNumber, year) {
    // Create a date object set to January 1st of the given year
    let startDate = new Date(year, 0, 1);

    // Get the day of the week for January 1st (0 = Sunday, 1 = Monday, etc.)
    let dayOfWeek = startDate.getDay();

    // Calculate the start date of the week (adjusting for the day of the week)
    // The "+ (dayOfWeek ? dayOfWeek : 7)" handles the case where January 1st is a Sunday
    let daysToFirstWeek = 1 + (weekNumber - 1) * 7 - (dayOfWeek ? dayOfWeek - 1 : 6);
    startDate.setDate(startDate.getDate() + daysToFirstWeek);

    // Create a new date for the end of the week, which is 6 days after the start date
    let endDate = new Date(startDate);
    endDate.setDate(startDate.getDate() + 6);

    // Return the start and end dates
    return {
        startDate: startDate,
        endDate: endDate
    };
}

function getWeekNumber(date) {
    let startOfYear = new Date(date.getFullYear(), 0, 1);
    let days = Math.floor((date - startOfYear) / (24 * 60 * 60 * 1000));
    let weekNumber = Math.ceil((days + startOfYear.getDay() + 1) / 7);
    return weekNumber;
}

// Function to get an array of week numbers up to today
function getWeekNumbersUpToToday() {
    let weekNumbers = [];
    let today = new Date();
    let currentWeek = getWeekNumber(new Date(today.getFullYear(), 0, 1));
    let currentDate = new Date(today.getFullYear(), 0, 1);

    while (currentDate <= today) {
        weekNumbers.push(currentWeek);
        currentDate.setDate(currentDate.getDate() + 7);
        currentWeek++;
    }

    return weekNumbers;
}

		function getData(report){
			console.log("report : "+JSON.stringify(report))
				
		
		
			if(report==undefined||report.type==""){
				$scope.typeErr=true;
				return;
			}else{
					$scope.typeErr=false;
					if(report.type=="Meter"){
							if(report.module==undefined){
								$scope.moduleErr=true;
								return;
							}else{
								$scope.moduleErr=false;
							}
					}
			}
			
			if(report.startDate==undefined){
					$scope.startDateErr=true;
				return;
			}else{
				$scope.startDateErr=false;
			}
			
			if(report.endDate==undefined){
					$scope.endDateErr=true;
				return;
			}else{
				$scope.endDateErr=false;
			}
			
		
			console.log("report : "+JSON.stringify(report))
			var msg=""
				 var url =energyUrl+"/getReportEngergyConsumptionReport";
				genericFactory.add(msg,url,report).then(function(response) {
				vm.reports= response.data;
				console.log("reports : "+JSON.stringify(vm.reports))
								
			});
		}
		function getMeters(){
			var msg=""
				 var url =energyUrl+"/getAllMeters";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.meters= response.data;
				console.log("modules : "+JSON.stringify(vm.modules))
								
			});
		}

	}
})();
