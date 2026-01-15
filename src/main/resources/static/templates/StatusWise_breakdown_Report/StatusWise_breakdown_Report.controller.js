(function() {
    'use strict';

    angular.module('myApp.StatusWise_breakdown_Report').controller('StatusWise_breakdown_ReportController', StatusWise_breakdown_ReportController);

    StatusWise_breakdown_ReportController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr'];

    function StatusWise_breakdown_ReportController($scope, ApiEndpoint, $http, toastr) {
        const baseUrl = ApiEndpoint.url;
        
        var vm = angular.extend(this, {
			
            StatRepo: [],
           selectedStatus: null,
            generateBreakdownReport: generateBreakdownReport, 
            export: exportToExcel,
           
        });
        
           (function activate() {
            $scope.StatRepo = {};
          
            
          
        })();
			
		   // Fetch report based on selected status
        function generateBreakdownReport() {
            if (vm.selectedStatus !== null) {
                var url = baseUrl + "/general_breakdowns/status/" + vm.selectedStatus;
                $http.get(url)
                    .then(function(response) {
                        vm.StatRepo = response.data;
                    })
                    .catch(function(error) {
                        toastr.error('Error fetching data');
                        console.error("Error fetching data:", error);
                    });
            }
        }


       
     /*   // Export Status-wise Breakdown Report to Excel
        function exportExcel() {
            if (vm.selectedStatus === null) {
                toastr.error('Please select a status first');
                return;
            }

            var url = baseUrl + "/general_breakdowns/export/status/" + vm.selectedStatus;

            $http.get(url, { responseType: 'blob' })
                .then(function(response) {
                    var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
                    var downloadLink = document.createElement('a');
                    downloadLink.href = window.URL.createObjectURL(blob);
                    downloadLink.download = 'Status-Wise-Breakdown-Report.xlsx';
                    document.body.appendChild(downloadLink);
                    downloadLink.click();
                    document.body.removeChild(downloadLink);
                })
                .catch(function(error) {
                    toastr.error('Error exporting data');
                    console.error("Error exporting data:", error);
                });
        }
		*/
		
		
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
							
							
							  // Function to map status numbers to text
		    function getStatusText(status) {
		        switch (status) {
		            case 1: return 'Open';
		            
		            case 0: return 'Closed';
		            default: return 'Unknown';
		        }
		    }


							
		function exportToExcel() {
		    if (!vm.StatRepo || vm.StatRepo.length === 0) {
		        toastr.error('No data available for export!');
		        return;
		    }

		    // Format the data for export
		    var formattedData = vm.StatRepo.map(function(item, index) {
		        return {
		            'Sr No': index + 1,
		              'BD Slip Number': item.breakdownNo, 
		                'Location': item.location.locationName, 
		            
		             'Lab Name': item.lab.labName,
		               
		            'Breakdown Cause': item.cause,         
		            'Root Cause': item.rootCause,   
		               'Conclusion': item.conclusion,                 
					   'description': item.description, 
		            
		            'Open Date': formatDate(item.openDate) , 
		          
		              'Closed Date': item.closedDate ? formatDate(item.closedDate) : "N/A",
		             'Status': getStatusText(item.status),  
		                  
		        };
		    });

		    // Create a worksheet from the formatted data status
		    var ws = XLSX.utils.json_to_sheet(formattedData);

		    // Create a workbook
		    var wb = XLSX.utils.book_new();
		    XLSX.utils.book_append_sheet(wb, ws, "StatusWise_Breakdown");

		    // Export to Excel
		    XLSX.writeFile(wb, "StatusWise_Breakdown.xlsx");
		}



					    

        
        
        
    }
})();
