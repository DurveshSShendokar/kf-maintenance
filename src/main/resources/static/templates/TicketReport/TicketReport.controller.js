(function() {
    'use strict';

    angular.module('myApp.TicketReport').controller('TicketReportController', TicketReportController);

    TicketReportController.$inject = ['$scope', 'ApiEndpoint', '$http'];

    function TicketReportController($scope, ApiEndpoint, $http) {
        const baseUrl = ApiEndpoint.url;
        
        var vm = angular.extend(this, {
            TicketSummary: [],
            selectedDateFilter: '',
            selectedDayDate: '',
            selectedFromDate: '',
            selectedToDate: '',
            generateTicketSummaryReport: generateTicketSummaryReport,  
             onFilterChange: onFilterChange,
            onDateChange: onDateChange,
            export: exportExcel
        });
        
        
        
        function onFilterChange() {
            // Reset date fields when filter changes
            vm.selectedDayDate = '';
            vm.selectedFromDate = '';
            vm.selectedToDate = '';
        }

		 function onDateChange() {
            if ((vm.selectedDateFilter === 'day' && vm.selectedDayDate) || 
                (vm.selectedDateFilter === 'date' && vm.selectedFromDate && vm.selectedToDate)) {
                generateTicketSummaryReport();
            }
        }

        function generateTicketSummaryReport() {
            var url = baseUrl + "/complaints/ticket_report";
            var params = {};

            if (vm.selectedDateFilter === 'day' && vm.selectedDayDate) {
                params.selectedDate = formatDate(vm.selectedDayDate);
            } else if (vm.selectedDateFilter === 'date' && vm.selectedFromDate && vm.selectedToDate) {
                params.fromDate = formatDate(vm.selectedFromDate);
                params.toDate = formatDate(vm.selectedToDate);
            } else {
                // Handle error or no filter selected
                console.error("Invalid filter selection or missing date.");
                return;
            }
            console.log("Request URL:", url);
            console.log("Request Params:", params);

            $http.get(url, { params: params })
                .then(function(response) {
                    vm.TicketSummary = response.data;
                    console.log("Fetched Ticket summary:", vm.TicketSummary);
                })
                .catch(function(error) {
                    console.error("Error fetching Ticket summary:", error);
                });
        }
        
        // Function to format date to YYYY-MM-DD
        function formatDate(date) {
            var d = new Date(date);
            var formattedDate = d.getFullYear() + '-' + pad(d.getMonth() + 1) + '-' + pad(d.getDate());
            return formattedDate;
        }
        // Function to pad single digit with zero
        function pad(n) {
            return n < 10 ? '0' + n : n;
        }
        
        
        
         function exportExcel() {
            var url = baseUrl + "/complaints/ticket_export";
            var params = {};
            
            if (vm.selectedDateFilter === 'day' && vm.selectedDayDate) {
                params.selectedDate = formatDate(vm.selectedDayDate);
            } else if (vm.selectedDateFilter === 'date' && vm.selectedFromDate && vm.selectedToDate) {
                params.fromDate = formatDate(vm.selectedFromDate);
                params.toDate = formatDate(vm.selectedToDate);
            } else {
                // Handle error or no filter selected
                console.error("Invalid filter selection or missing date.");
                return;
            }
            console.log("Request URL:", url);
            console.log("Request Params:", params);
            
            $http.get(url, { params: params, responseType: 'blob' })
                .then(function(response) {
                    console.log("Export response data:", response.data);
                    var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
                    var downloadLink = document.createElement('a');
                    downloadLink.href = window.URL.createObjectURL(blob);
                    downloadLink.download = 'TicketResolutionReport.xlsx';
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
