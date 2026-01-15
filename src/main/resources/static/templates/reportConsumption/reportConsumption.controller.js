(function() {
	'use strict';

	angular.module('myApp.reportConsumption').controller('ReportConsumptionController', ReportConsumptionController)

	ReportConsumptionController.$inject = [ '$state', '$uibModal', '$log',
			'$scope', 'toastr','genericFactory' ];
	/* @ngInject */
	function ReportConsumptionController($state, $uibModal, $log, $scope, toastr,genericFactory) {
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
				
		
			var obj={};
			obj.reportType=report.type2
			obj.reportFor=report.type
			if(report.type2=="Date"){
				if(report.startDate==""||report.startDate==undefined){
				$scope.startDateErr=true;
				return;
			}else{
				$scope.startDateErr=false;
			}
			
				if(report.endDate==""||report.endDate==undefined){
				$scope.endDateErr=true;
				return;
			}else{
				$scope.endDateErr=false;
			}
				obj.fromDate=report.startDate;
			obj.toDate=report.endDate;
			}
			if(report.type2=="Month"){
					obj.month=report.month;
			obj.year=report.year;
			}
			if(report.type2=="Year"){
				
			obj.year=report.year;
			}
			if(report.type2=="Week"){
				let currentDate = new Date();

// Get the current year
						let currentYear = currentDate.getFullYear();

				var dateObj=getWeekStartAndEndDate(report.week, currentYear);
					console.log("reports : "+JSON.stringify(dateObj))
			obj.fromDate=dateObj.startDate;
			obj.toDate=dateObj.endDate;
			obj.reportType="Date"
			console.log("reports 2 : "+JSON.stringify(obj))
			}
			if(report.type=="Meter"){
					if(report==undefined||report.module==""){
				$scope.moduleErr=true;
				return;
			}else{
				$scope.moduleErr=false;
			}
		
				obj.meeterId=report.module.id;
			}
			
			
		
			console.log("obj : "+JSON.stringify(obj))
			var msg=""
				 var url =energyUrl+"/getReportCurrentCunsumptionCostByIdAndDates";
				genericFactory.add(msg,url,obj).then(function(response) {
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
