(function() {
    'use strict';

    angular.module('myApp.BreakdownReport').controller('BreakdownRepoController', BreakdownRepoController);

    BreakdownRepoController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr'];

    function BreakdownRepoController($scope, ApiEndpoint, $http, toastr) {
        const baseUrl = ApiEndpoint.url;
        
        var vm = angular.extend(this, {
            breakRepo: [],
            filterType: '', // Set default filter to 'All'
            startDate: null,
            endDate: null,
            generateDateBreakdownReport: generateDateBreakdownReport,
            getAllBreakdownReports: getAllBreakdownReports,
            exportToExcel:exportToExcel,
            handleFilterChange: handleFilterChange
        });

        // Function to handle filter change
        function handleFilterChange() {
            if (vm.filterType === 'all') {
                getAllBreakdownReports();  // Automatically fetch all breakdowns when "All" is selected
            } else if (vm.filterType === 'date') {
                // Clear the existing report data when user selects "Date"
                vm.breakRepo = [];
            }
        }
        
        
         function formatDate(date) {
					    if (!date) { // Check for null, undefined, or falsy values
					        return ""; // Return an empty string for null or undefined dates
					    }
					    var d = new Date(date);
					    var day = String(d.getDate()).padStart(2, "0");
					    var month = String(d.getMonth() + 1).padStart(2, "0");
					    var year = d.getFullYear();
					    return year + "-" + month + "-" + day;
					}


        // Fetch all breakdown reports
        function getAllBreakdownReports() {
            var url = baseUrl + "/breakdown/Breakdown_report/all";
            $http.get(url)
                .then(function(response) {
                    vm.breakRepo = response.data;
                    console.log("Fetched all Breakdown Reports:", vm.breakRepo);
                })
                .catch(function(error) {
                    console.error("Error fetching all Breakdown Reports:", error);
                });
        }

  function generateDateBreakdownReport() {
    if (!vm.startDate || !vm.endDate) {
        toastr.error('Please select both start date and end date');
        return;
    }

   
    var adjustedStartDate = new Date(vm.startDate);
    adjustedStartDate.setHours(0, 0, 0, 0); // Start of the day

  
    var adjustedEndDate = new Date(vm.endDate);
    adjustedEndDate.setHours(23, 59, 59, 999); // End of the day

   
    var formattedStartDate = formatDateToISO(adjustedStartDate);
    var formattedEndDate = formatDateToISO(adjustedEndDate);

    
    console.log("Formatted Start Date:", formattedStartDate);
    console.log("Formatted End Date:", formattedEndDate);

  
    var url = baseUrl + "/breakdown/Breakdown_report";
    var params = {
        startDate: formattedStartDate,
        endDate: formattedEndDate
    };

    // Make the API call
    $http.get(url, { params: params })
        .then(function(response) {
            vm.breakRepo = response.data;
            console.log("Fetched Date-Wise Breakdown Report:", vm.breakRepo);
        })
        .catch(function(error) {
            console.error("Error fetching Breakdown Report:", error);
        });
}

// Manually format the date to ISO string without timezone conversion
function formatDateToISO(date) {
    var year = date.getFullYear();
    var month = String(date.getMonth() + 1).padStart(2, '0');
    var day = String(date.getDate()).padStart(2, '0');
    var hours = String(date.getHours()).padStart(2, '0');
    var minutes = String(date.getMinutes()).padStart(2, '0');
    var seconds = String(date.getSeconds()).padStart(2, '0');
    var milliseconds = String(date.getMilliseconds()).padStart(3, '0');
    return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}.${milliseconds}`;
}

function formatHoursToHM(hours) {
	    if (hours == null) return 'N/A';
	    const h = Math.floor(hours);
	    const m = Math.round((hours - h) * 60);
	    return `${h}h ${m}m`;
	}

// Watch for changes in startDate and endDate
$scope.$watchGroup([() => vm.startDate, () => vm.endDate], function(newValues) {
    if (vm.filterType === 'date') {
        var startDate = newValues[0];
        var endDate = newValues[1];
        if (startDate && endDate) {
            generateDateBreakdownReport();
        }
    }
});


							
	function exportToExcel() {
    if (!vm.breakRepo || vm.breakRepo.length === 0) {
        toastr.error('No data available for export!');
        return;
    }

    // Format the data for export
    var formattedData = vm.breakRepo.map(function(item, index) {
        return {
            'Sr No': index + 1,
             // 'Machine Name': item.machine ? item.machine.machine_name + ' ' + item.machine.eqid : '',
               'Machine Name': item.breakdown.machine.machine_name, 
                'Machine Eqid': item.breakdown.machine.eqid, 
				'Status': item.breakdown.status === 1 ? 'Open' :
									          item.breakdown.status === 2 ? 'Trial' :
									          item.breakdown.status === 3 ? 'Closed' : 'Unknown',
                'Shift Name': item.breakdown.shift.shift_name, 
                
                 'Slip Number': item.breakdown.bd_slip, 
            
            
               
            'Complaint Cause': item.breakdown.observation,         
            'Root Cause': item.breakdown.root_cause,   
               'Action Taken': item.breakdown.action_taken,  
                 'Raised By': item.breakdown.addedBy ? item.breakdown.addedBy.firstName + ' ' + item.breakdown.addedBy.lastName : '',
				 'Update By': item.breakdown.updateBy && item.breakdown.updateBy.firstName
				 					     ? item.breakdown.updateBy.firstName + ' ' + (item.breakdown.updateBy.lastName || '')
				 					     : 'N/A',

				 					 'Trial By': item.breakdown.trialBy && item.breakdown.trialBy.firstName
				 					     ? item.breakdown.trialBy.firstName + ' ' + (item.breakdown.trialBy.lastName || '')
				 					     : 'N/A',
            'Raised Date': formatDate(item.breakdown.ticket_raised_time) , 
             'Trial Date': formatDate(item.breakdown.ticket_trial_time) ,
              'Closed Date': formatDate(item.breakdown.ticket_closed_time) ,
			  'Raise To Trial': formatHoursToHM(item.raiseToTrial),
			  				  'Trial To Closed': formatHoursToHM(item.trialToClosed),
			  				  'Raise To Closed': formatHoursToHM(item.totalTime)
                  
        };
    });

    // Create a worksheet from the formatted data
    var ws = XLSX.utils.json_to_sheet(formattedData);

    // Create a workbook
    var wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "Breakdown Report");

    // Export to Excel
    XLSX.writeFile(wb, "Breakdown_Report.xlsx");
}

      
    }
})();
