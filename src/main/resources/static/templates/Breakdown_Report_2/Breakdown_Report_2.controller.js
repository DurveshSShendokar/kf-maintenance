(function() {
    'use strict';

    angular.module('myApp.Breakdown_Report_2').controller('Breakdown_Report_2Controller', Breakdown_Report_2Controller);

    Breakdown_Report_2Controller.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr'];

    function Breakdown_Report_2Controller($scope, ApiEndpoint, $http, toastr) {
        const baseUrl = ApiEndpoint.url;
        
        var vm = angular.extend(this, {
			 machines: [],
           selectedmachine: null,
            BreakRepo: [],
            
            generateBreakWiseReport: generateBreakWiseReport,  
            export: exportExcel
           
        });
        
           (function activate() {
            $scope.BreakRepo = {};
              vm.BreakRepo = [];
          	loadMachines();
          	
        })();
			
			 
			



  function generateBreakWiseReport(machineId) {
    if (!machineId) return;  

    var url = `${baseUrl}/analysis_time_frames/report/${machineId}`;
    console.log("Requesting breakdown report for machine ID:", machineId);

    $http.get(url)
        .then(function(response) {
           
            if (Array.isArray(response.data)) {
                vm.BreakRepo = response.data;
                console.log("Fetched Break Report:", vm.BreakRepo);
            } else {
                console.warn("Unexpected response format:", response.data);
                vm.BreakRepo = [];  
            }
        })
        .catch(function(error) {
            console.error("Error fetching Break Report:", error);
            toastr.error("Could not load data for the selected machine.");
            vm.BreakRepo = []; 
        });
}

        
        
      	 function loadMachines() {
            var url = baseUrl + "/machine_mst/list";
            $http.get(url)
                .then(function(response) {
                    vm.machines = response.data;
                    console.log("Fetched machines:", vm.machines);
                })
                .catch(function(error) {
                    console.error("Error fetching machines:", error);
                });
        }
        
        
			function exportExcel() {
			    if (!vm.selectedCategory) {
			        toastr.error('Please select a category for export');
			        return;
			    }
			
			    var categoryId = vm.selectedCategory; // Now this will have the correct category ID
			    var url = baseUrl + "/general_breakdowns/export/category/" + categoryId;
			
			    console.log("Exporting Category-wise Breakdown Report, Category ID:", categoryId);
			
			    $http.get(url, { responseType: 'blob' })
			        .then(function(response) {
			            var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
			            var downloadLink = document.createElement('a');
			            downloadLink.href = window.URL.createObjectURL(blob);
			            downloadLink.download = 'Category-Wise-Breakdown-Report.xlsx';
			            document.body.appendChild(downloadLink);
			            downloadLink.click();
			            document.body.removeChild(downloadLink);
			        })
			        .catch(function(error) {
			            toastr.error('Error exporting data');
			            console.error("Error exporting data:", error);
			        });
			}


    }
})();
