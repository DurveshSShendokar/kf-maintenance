(function() {
    'use strict';

    angular.module('myApp.SpareReport').controller('SpareReportController', SpareReportController);

    SpareReportController.$inject = ['$scope', 'ApiEndpoint', '$http'];

    function SpareReportController($scope, ApiEndpoint, $http) {
        const baseUrl = ApiEndpoint.url;
        
        var vm = angular.extend(this, {
			 spare: [],
            filterType: '',
            SpareRepo: [],
            getSpareStockReport: getSpareStockReport,
            generateSpareReport: generateSpareReport,  
            export: exportExcel,
            handleFilterChange: handleFilterChange
        });
        
           (function activate() {
            $scope.SpareRepo = {};
          
             loadSpares();
          
        })();
			
			  // Function to handle filter change
        function handleFilterChange() {
            if (vm.filterType === 'all') {
                getSpareStockReport();  // Automatically fetch all spares when "All" is selected
            }
        }

			
        // Fetch Spare Stock Report for all spares
        function getSpareStockReport() {
            var url = baseUrl + "/spares/spare_stock";
            $http.get(url)
                .then(function(response) {
                    vm.SpareRepo = response.data;
                    console.log("Fetched all Spare Report:", vm.SpareRepo);
                })
                .catch(function(error) {
                    console.error("Error fetching all Spare Report:", error);
                });
        }
			


        function generateSpareReport(spareId) {
           var url = baseUrl + "/spares/spare_stock/" + spareId;
            var params = {};

          
            console.log("Request URL:", url);
            console.log("Request Params:", params);

            $http.get(url, { params: params })
                .then(function(response) {
                    vm.SpareRepo = response.data;
                    console.log("Fetched Spare Report:", vm.SpareRepo);
                })
                .catch(function(error) {
                    console.error("Error fetching Spare Report:", error);
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
        
        
       // Export Spare Report to Excel
        function exportExcel() {
          	
    if (!vm.SpareRepo || vm.SpareRepo.length === 0) {
        toastr.error('No data available for export!');
        return;
    }

   
    var formattedData = vm.SpareRepo.map(function(item, index) {
		
		
        
		
        return {
            'Sr No': index + 1,
              'Spare Name': item.spare.spare_name, 
               
            
           
               'Total Quantity': item.totalQuantity,
            ' Available Quantity': item.availableQuantity,         
            'Utilized Quantity': item.utilizedQuantity,   
                             
            
            
             
         
                  
        };
    });

    // Create a worksheet from the formatted data
    var ws = XLSX.utils.json_to_sheet(formattedData);

    // Create a workbook
    var wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "Spear Report ");

    // Export to Excel
    XLSX.writeFile(wb, "Spear Report .xlsx");
}

        
        
			
			  


        
        
        
    }
})();
