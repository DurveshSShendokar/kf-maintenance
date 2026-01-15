(function() {
    'use strict';

    angular.module('myApp.reportBreakdownHistory').controller('ReportBreakdownHistoryController', ReportBreakdownHistoryController)

    ReportBreakdownHistoryController.$inject = ['$state', '$uibModal', '$log', '$scope', 'toastr', 'genericFactory'];

    function ReportBreakdownHistoryController($state, $uibModal, $log, $scope, toastr, genericFactory) {
        var trialUrl = staticUrl + "/trial";
        var vm = angular.extend(this, {
            loadHistory: loadHistory,
            exportToExcel: exportToExcel
        });

        // Initialize with default dates and load history
        (function activate() {
            $scope.startDate = new Date();
            $scope.endDate = new Date();
            loadHistory();
        })();

        // Function to load history based on the selected dates
        function loadHistory() {
            console.log("machines : ");
            var msg = "";
            var obj = {};
            obj.startDate = $scope.startDate;
            obj.endDate = $scope.endDate;
            var url = trialUrl + "/breakdownHistoryList";
            genericFactory.add(msg, url, obj).then(function(response) {
                vm.histories = response.data;
                console.log("history : " + JSON.stringify(vm.histories));
            });
        }

        // Export to Excel
        function exportToExcel() {
            if (!vm.histories || vm.histories.length === 0) {
                toastr.error('No data available for export!');
                return;
            }

            // Format the data for export
            var formattedData = vm.histories.map(function(item, index) {
                return {
                    'Sr No': index + 1,
                    'Breakdown Slip': item.bd_slip,
                    'Machine Name': item.machine.machine_name,
                    'Machine Id': item.machine.eqid,
                    'Observation': item.observation,
                    'Root Cause': item.root_cause,
                    'Action Taken': item.action_taken,
                    'Status': item.statusStr,
                    'Done By': item.done_by,
                    'Ticket Created Date': item.createDate,
                    'Ticket Created Time': item.createTime,
                    'Ticket Closed Date': item.closedDate,
                    'Ticket Closed Time': item.closedTime,
                    'TTR': item.ttr,
                    'TBF': item.diff_days,
                    'Total Breakdowns (Hrs)': item.total_trial_hr
                    
                };
            });

            // Create a worksheet from the formatted data
            var ws = XLSX.utils.json_to_sheet(formattedData);

            // Create a workbook
            var wb = XLSX.utils.book_new();
            XLSX.utils.book_append_sheet(wb, ws, "Breakdown History");

            // Export to Excel
            XLSX.writeFile(wb, "BreakdownHistory.xlsx");
        }   
        
        
        
        
         }
})();
