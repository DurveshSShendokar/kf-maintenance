(function() {
    'use strict';

    angular.module('myApp.Analysis_Report').controller('Analysis_ReportController', Analysis_ReportController);

    Analysis_ReportController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr'];

    function Analysis_ReportController($scope, ApiEndpoint, $http, toastr) {
        const baseUrl = ApiEndpoint.url;

        var vm = angular.extend(this, {
            machines: [],
            selectedMachine: null,
            loadBreakdownSummary:loadBreakdownSummary,
            ATRepo: [],
            exportData: exportData
        });

        (function activate() {
            loadMachines();
        })();

        // Load machines to populate dropdown
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

     function loadBreakdownSummary(machineId) {
		    if (!machineId) return;  
		
		    // Clear previous data
		    vm.ATRepo = [];
		
		    var url = baseUrl + "/analysis_time_frames/summary_report/" + machineId;
		    $http.get(url)
		        .then(function(response) {
		            if (response.status === 200 && response.data) {
		                vm.ATRepo = response.data;
		                if (!vm.ATRepo || Object.keys(vm.ATRepo).length === 0) {
		                    toastr.error("No breakdown summary found.");
		                }
		            } else {
		                vm.ATRepo = []; // Ensure table is cleared
		                toastr.error("No breakdown summary found.");
		            }
		        })
		        .catch(function(error) {
		            vm.ATRepo = []; // Ensure table is cleared
		           
		           toastr.error("No breakdown summary found.");
		        });
		}
		
		
	function exportData() {
    // Check if a machine is selected
    if (!vm.selectedMachine || !vm.selectedMachine.machine_id) {
        toastr.error("Please select a machine.");
        return;
    }

    // Proceed with export if a machine is selected
    var url = baseUrl + "/analysis_time_frames/summary_report/" + vm.selectedMachine.machine_id + "/export";  // Assuming machine_id is available
    $http.get(url, { responseType: 'arraybuffer' })
        .then(function(response) {
            // Convert binary data to a downloadable file
            var file = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
            var link = document.createElement('a');
            link.href = URL.createObjectURL(file);
            link.download = 'BreakdownSummary.xlsx';
            link.click();
            toastr.success("Export successful!");
        })
        .catch(function(error) {
            toastr.error("Failed to export breakdown summary.");
            console.error("Error during export:", error);
        });
}


    }
})();
