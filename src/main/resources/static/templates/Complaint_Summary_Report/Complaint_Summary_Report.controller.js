(function() {
    'use strict';

    angular.module('myApp.Complaint_Summary_Report').controller('SummaryController', SummaryController);

    SummaryController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr', 'genericFactory'];

    function SummaryController($scope, ApiEndpoint, $http, toastr, genericFactory) {
      
        const baseUrl = ApiEndpoint.url;
        
        var vm = angular.extend(this, {
            complaints: [],
            complaintSummary: [],
            selectedStatus: '',
            selectedDateFilter: '',
            selectedDayDate: '',
            selectedFromDate: '',
            selectedToDate: '',
             export: exportExcel,
            generateComplaintSummaryReport: generateComplaintSummaryReport
        });

        (function activate() {
            loadComplaints();
        })();

        function loadComplaints() {
            var url = baseUrl + "/complaints/all";
            $http.get(url)
                .then(function(response) {
                    vm.complaints = response.data;
                    console.log("Fetched complaints:", vm.complaints);
                })
                .catch(function(error) {
                    console.error("Error fetching complaints:", error);
                });
        }
        
        function generateComplaintSummaryReport() {
            var url = baseUrl + "/complaints/filter";

            var params = {
                status: vm.selectedStatus,
                filterType: vm.selectedDateFilter
            };
            
            
 	 if (vm.selectedDateFilter === 'day' && vm.selectedDayDate) 
  	{
        // Adjust for timezone offset to send in UTC format
        let selectedDate = new Date(vm.selectedDayDate);
        selectedDate.setMinutes(selectedDate.getMinutes() - selectedDate.getTimezoneOffset());
        params.date = selectedDate.toISOString().split('T')[0]; // Format as YYYY-MM-DD
    } 
    else if (vm.selectedDateFilter === 'date' && vm.selectedFromDate && vm.selectedToDate)
     {
        // Adjust for timezone offset to send in UTC format
        let fromDate = new Date(vm.selectedFromDate);
        let toDate = new Date(vm.selectedToDate);
        fromDate.setMinutes(fromDate.getMinutes() - fromDate.getTimezoneOffset());
        toDate.setMinutes(toDate.getMinutes() - toDate.getTimezoneOffset());
        params.fromDate = fromDate.toISOString().split('T')[0];
        params.toDate = toDate.toISOString().split('T')[0];
    }

				
			console.log("Request URL:", url);
            console.log("Request Params:", params);

				
            $http.get(url, { params: params })
                .then(function(response) {
                    vm.complaintSummary = response.data;
                    console.log("Fetched complaint summary:", vm.complaintSummary);
                })
                .catch(function(error) {
                    console.error("Error fetching complaint summary:", error);
                });
        }
        
        
           function exportExcel() {
            var url = baseUrl + "/complaints/filter_export";
            var params = {
							status: vm.selectedStatus,
        					filterType: vm.selectedDateFilter
        				};
            
           if (vm.selectedDateFilter === 'day' && vm.selectedDayDate) {
			        params.date = formatDate(vm.selectedDayDate);
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
                    downloadLink.download = 'ComplaintSummaryReport.xlsx';
                    document.body.appendChild(downloadLink);
                    downloadLink.click();
                    document.body.removeChild(downloadLink);
                })
                .catch(function(error) {
                    toastr.error('Error exporting data');
                    console.error("Error exporting data:", error);
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
        
        
        
    }
})();
