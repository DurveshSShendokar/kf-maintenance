(function() {
    'use strict';

    angular.module('myApp.DateWise_Breakdown_Report').controller('DateWise_Breakdown_ReportController', DateWise_Breakdown_ReportController);

    DateWise_Breakdown_ReportController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr'];

    function DateWise_Breakdown_ReportController($scope, ApiEndpoint, $http, toastr) {
        const baseUrl = ApiEndpoint.url;
        
        var vm = angular.extend(this, {
			
            DateRepo: [],
          startDate: null,
            endDate: null,
            generateDateWiseReport: generateDateWiseReport,  
            export: exportExcel
           
        });
        
           (function activate() {
            $scope.DateRepo = {};
          
        
          
        })();
			
			
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
			
			
		  // Watch for changes in startDate and endDate
        $scope.$watchGroup([() => vm.startDate, () => vm.endDate], function(newValues) {
            var startDate = newValues[0];
            var endDate = newValues[1];

            // Only generate the report if both dates are selected
            if (startDate && endDate) {
                generateDateWiseReport();
            }
        });
	
			

 // Function to generate the report based on selected dates
        function generateDateWiseReport() {
            if (!vm.startDate || !vm.endDate) {
                toastr.error('Please select both start date and end date');
                return;
            }

            var formattedStartDate = formatDate(vm.startDate);
            var formattedEndDate = formatDate(vm.endDate);

            var url = baseUrl + "/general_breakdowns/report/date-wise";
            var params = {
                startDate: formattedStartDate,
                endDate: formattedEndDate
            };

            console.log("Request URL:", url);
            console.log("Request Params:", params);

            $http.get(url, { params: params })
                .then(function(response) {
                    vm.DateRepo = response.data;
                    console.log("Fetched Date Wise Report:", vm.DateRepo);
                })
                .catch(function(error) {
                    toastr.error('Error fetching data');
                    console.error("Error fetching Date wise Report:", error);
                });
        }
	
   
        
        function exportExcel() {
    if (!vm.DateRepo || vm.DateRepo.length === 0) {
        toastr.error('No data available for export!');
        return;
    }

    // Format the data for export
    var formattedData = vm.DateRepo.map(function(item, index) {
        return {
            'Sr No': index + 1,
           
            'Location': item.location.locationName,          
            'Lab': item.lab.labName,         
            'Conclusion': item.conclusion,               
             'Root Cause': item.rootCause,   
              'Description': item.description,  
               'Slip Number': item.breakdownNo,    
            'Open Date': formatDate(item.openDate)  , 
            'Closed Date': formatDate(item.closedDate) 
                  
        };
    });

    // Create a worksheet from the formatted data
    var ws = XLSX.utils.json_to_sheet(formattedData);

    // Create a workbook
    var wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "DatewiseGeneralBreakdown ");

    // Export to Excel
    XLSX.writeFile(wb, "DatewiseGeneralBreakdown.xlsx");
}

        
        

        
        
        
    }
})();
