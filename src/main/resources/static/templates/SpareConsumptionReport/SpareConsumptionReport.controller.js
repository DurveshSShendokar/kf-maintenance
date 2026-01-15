(function() {
    'use strict';

    angular.module('myApp.SpareConsumptionReport').controller('SpareConsumptionReportController', SpareConsumptionReportController);

    SpareConsumptionReportController.$inject = ['$scope', 'ApiEndpoint', '$http'];

    function SpareConsumptionReportController($scope, ApiEndpoint, $http) {
        const baseUrl = ApiEndpoint.url;
        
        var vm = angular.extend(this, {
			 spare: [],
            filterType: '',
            SpareRepo: [],
            selectedTicket: [],
            getSpareStockUtilReport: getSpareStockUtilReport,
            generateSpareUtilReport: generateSpareUtilReport,
           
            export: exportToExcel,
            handleFilterChange: handleFilterChange
        });
        
           (function activate() {
            $scope.SpareRepo = {};
           loadbreakdowns();
             loadSpares();
          
        })();
			
			  // Function to handle filter change
        function handleFilterChange() {
            if (vm.filterType === 'all') {
                getSpareStockUtilReport();  
            }
            
        }

			
        // Fetch breakdown Spare  Report for all spares
        function getSpareStockUtilReport() {
            var url = baseUrl + "/breakdown/breakdownConsumption_report";
            $http.get(url)
                .then(function(response) {
                    vm.SpareUtilRepo = response.data;
                    console.log("Fetched all Spare Util Report:", vm.SpareUtilRepo);
                })
                .catch(function(error) {
                    console.error("Error fetching all Spare Util Report:", error);
                });
        }
			

	 function loadbreakdowns() {
            var url = baseUrl + "/breakdown/list";
            $http.get(url)
                .then(function(response) {
                    vm.breakdowns = response.data;
                    console.log("Fetched breakdowns:", vm.breakdowns);
                })
                .catch(function(error) {
                    console.error("Error fetching breakdowns:", error);
                });
        }
				
				
	 


        function generateSpareUtilReport(maintspare_id) {
           var url = baseUrl + "/breakdown/breakdownConsumption_report/byspare/" + maintspare_id;
            var params = {};

          
            console.log("Request URL:", url);
            console.log("Request Params:", params);

            $http.get(url, { params: params })
                .then(function(response) {
                    vm.SpareUtilRepo = response.data;
                    console.log("Fetched Breakdown Spare Util Report:", vm.SpareUtilRepo);
                })
                .catch(function(error) {
                    console.error("Error fetching Breakdown Spare Util Report:", error);
                });
        }
        
      		 function loadSpares() {
			    var url = baseUrl + "/maintspares/list";
			    $http.get(url)
			        .then(function(response) {
			            vm.spare = response.data;
			            console.log("Fetched spare:", vm.spare);
			        })
			        .catch(function(error) {
			            console.error("Error fetching spares:", error);
			        });
			}
        
    	 function formatDate(date) {
			        var d = new Date(date);
			        var day = String(d.getDate()).padStart(2, "0");
			        var month = String(d.getMonth() + 1).padStart(2, "0");
			        var year = d.getFullYear();
			        return year + "-" + month + "-" + day;
			    }
		
		
	function exportToExcel() {
    if (!vm.SpareUtilRepo || vm.SpareUtilRepo.length === 0) {
        toastr.error('No data available for export!');
        return;
    }

    
    var formattedData = vm.SpareUtilRepo.map(function(item, index) {
        return {
            'Sr No': index + 1,
            'Spare Name': item.spareName, 
			'Machine Name': item.machineName, 
            'breakdownSlipNo': item.breakdownSlipNo,          
            'machineID': item.machineEquipmentId,        
            'Raised date': item.breakdownRaisedDate,             
              'Raised date':formatDate(item.breakdownRaisedDate) ,   
              'Spare consumption date':formatDate(item.spareConsumptionDate) ,  
                'Shift Name': item.shiftName,   
                  'Root Cause': item.rootCause,   
                    'Action Plan ': item.actionPlan,   
                      'Consumed Quantity': item.consumedQuantity,   
                    'Available Quantity ': item.availableQuantity  
                  
        };
    });

    // Create a worksheet from the formatted data
    var ws = XLSX.utils.json_to_sheet(formattedData);

    // Create a workbook
    var wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "Spare Consumption");

    // Export to Excel
    XLSX.writeFile(wb, "Spare_Consumption.xlsx");
}

        
    }
})();
