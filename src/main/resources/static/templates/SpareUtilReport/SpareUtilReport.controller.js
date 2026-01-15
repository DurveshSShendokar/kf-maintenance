(function() {
    'use strict';

    angular.module('myApp.SpareUtilReport').controller('SpareUtilReportController', SpareUtilReportController);

    SpareUtilReportController.$inject = ['$scope', 'ApiEndpoint', '$http'];

    function SpareUtilReportController($scope, ApiEndpoint, $http) {
        const baseUrl = ApiEndpoint.url;
        
        var vm = angular.extend(this, {
			 spare: [],
            filterType: '',
            SpareRepo: [],
            selectedTicket: [],
            getSpareStockUtilReport: getSpareStockUtilReport,
            generateSpareUtilReport: generateSpareUtilReport,
             generateCompUtilReport: generateCompUtilReport,  
            export: exportExcel,
            handleFilterChange: handleFilterChange
        });
        
           (function activate() {
            $scope.SpareRepo = {};
           loadComplaints();
             loadSpares();
          
        })();
			
			  // Function to handle filter change
        function handleFilterChange() {
            if (vm.filterType === 'all') {
                getSpareStockUtilReport();  
            }
             if (vm.filterType === 'ticket') {
                generateCompUtilReport();  
            }
        }

			
        // Fetch Spare Stock Report for all spares
        function getSpareStockUtilReport() {
            var url = baseUrl + "/spares/utilization-report";
            $http.get(url)
                .then(function(response) {
                    vm.SpareUtilRepo = response.data;
                    console.log("Fetched all Spare Util Report:", vm.SpareUtilRepo);
                })
                .catch(function(error) {
                    console.error("Error fetching all Spare Util Report:", error);
                });
        }
			

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
				
				
	  // Generate report based on comp_id (complaint ID)
        function generateCompUtilReport(comp_id) {
            if (!comp_id) {
                console.error("comp_id is undefined or null");
                return;
            }

            var url = baseUrl + "/spares/utilization-report/comp/" + comp_id;
            console.log("Request URL:", url);

            $http.get(url)
                .then(function(response) {
                    // Assuming the response is the array containing the spare utilization report
                    vm.SpareUtilRepo = response.data;

                    // Extract ticket numbers from the response
                    vm.tickets = [];
                    angular.forEach(vm.SpareUtilRepo, function(item) {
                        if (item.spareConsumption && item.spareConsumption.complaint && item.spareConsumption.complaint.ticketNo) {
                            vm.tickets.push(item.spareConsumption.complaint.ticketNo);
                        }
                    });

                    console.log("Fetched Tickets for Dropdown:", vm.tickets);
                })
                .catch(function(error) {
                    console.error("Error fetching Tickets Report:", error);
                });
        }
        
      			 function loadSpares() {
			    var url = baseUrl + "/spares/getAllSapres";
			    $http.get(url)
			        .then(function(response) {
			            vm.spare = response.data;
			            console.log("Fetched spare:", vm.spare);
			        })
			        .catch(function(error) {
			            console.error("Error fetching spares:", error);
			        });
			}



        function generateSpareUtilReport(spareId) {
           var url = baseUrl + "/spares/utilization-report/" + spareId;
            var params = {};

          
            console.log("Request URL:", url);
            console.log("Request Params:", params);

            $http.get(url, { params: params })
                .then(function(response) {
                    vm.SpareUtilRepo = response.data;
                    console.log("Fetched Spare Util Report:", vm.SpareUtilRepo);
                })
                .catch(function(error) {
                    console.error("Error fetching Spare Util Report:", error);
                });
        }
        
      			
        
        
      function exportExcel() {
    if (!vm.SpareUtilRepo || vm.SpareUtilRepo.length === 0) {
        toastr.error('No data available for export!');
        return;
    }

    var formattedData = vm.SpareUtilRepo.map(function (item, index) {
        const allocateTo = item.spareConsumption.complaint.allocateTo; // Safely access allocateTo

        return {
            'Sr No': index + 1,
            'Spare Name': item.spareConsumption.spare.spare_name || 'N/A',
            'Ticket Number': item.spareConsumption.complaint.ticketNo || 'N/A',
            'Asset Name': item.spareConsumption.complaint.assetInventory.model || 'N/A',
            'Asset EQID': item.spareConsumption.complaint.assetInventory.equipmentId || 'N/A',
            'Asset Owner': allocateTo
                ? `${allocateTo.firstName || ''} ${allocateTo.lastName || ''}`.trim()
                : 'Not Allocated',
            'Complaint Date': formatDate(item.spareConsumption.complaint.complaintDate),
            'Complaint Time': formatDate(item.spareConsumption.consuptionDate),
            'Utilized Quantity': item.utilizedQuantity || 0,
        };
    });

    // Create a worksheet from the formatted data
    var ws = XLSX.utils.json_to_sheet(formattedData);

    // Create a workbook
    var wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "Spare Utilization Report");

    // Export to Excel
    XLSX.writeFile(wb, "Spare Utilization Report.xlsx");
}

function formatDate(date) {
    if (!date) {
        return ""; // Return an empty string for null or undefined dates
    }
    var d = new Date(date);
    var day = String(d.getDate()).padStart(2, "0");
    var month = String(d.getMonth() + 1).padStart(2, "0");
    var year = d.getFullYear();
    return year + "-" + month + "-" + day;
}

        
        
        
    }
})();
