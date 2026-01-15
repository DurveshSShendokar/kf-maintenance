(function() {
    'use strict';

    angular.module('myApp.AssetAllocationReport').controller('AssetAllocationRepoController', AssetAllocationRepoController);

    AssetAllocationRepoController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr'];

    function AssetAllocationRepoController($scope, ApiEndpoint, $http, toastr) {
        const baseUrl = ApiEndpoint.url;
        
        var vm = angular.extend(this, {
			assets: [],
            filterType: '', // Set default filter to 'All'
            startDate: null,
			selectedAssetId: null,
			assetHistoryReport: null,
            endDate: null,
            fetchAssetHistoryReport: fetchAssetHistoryReport,
         
            exportToExcel:exportToExcel,
         
			loadAssets:loadAssets
        });

       
		$scope.$watchGroup(
		           [() => vm.selectedAssetId, () => vm.startDate, () => vm.endDate],
		           function(newValues, oldValues) {
		               if (newValues !== oldValues) {
		                   fetchAssetHistoryReport();
		               }
		           }
		       );
        
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


		
					function loadAssets() {
					       var url = baseUrl + "/assetInventory/all";
					       $http.get(url)
					           .then(function(response) {
					               vm.assets = response.data;
					           })
					           .catch(function(error) {
					               console.error("Error fetching assets:", error);
					               toastr.error("Failed to load assets");
					           });
					   }
					   loadAssets();


			   // Fetch report (with or without dates)
			   function fetchAssetHistoryReport() {
			       var url = baseUrl + "/assetAllocation/getAssetHistoryReport";

			       var params = {};
			       if (vm.selectedAssetId) params.assetId = vm.selectedAssetId;  // optional now
			       if (vm.startDate) params.startDate = formatDate(vm.startDate);
			       if (vm.endDate) params.endDate = formatDate(vm.endDate);

			       $http.get(url, { params: params })
			           .then(function(response) {
			               vm.assetHistoryReport = response.data;
			               vm.activeAllocations = vm.assetHistoryReport.activeAllocations;
			               vm.historyAllocations = vm.assetHistoryReport.historyAllocations;
			               console.log("Fetched Asset History Report:", vm.assetHistoryReport);
			           })
			           .catch(function(error) {
			               console.error("Error fetching Asset History Report:", error);
			               toastr.error("Failed to fetch Asset History Report");
			           });
			   }

				
				// Manually format the date to ISO string without timezone conversion
				function formatDateToISO(date) {
				    var year = date.getFullYear();
				    var month = String(date.getMonth() + 1).padStart(2, '0');
				    var day = String(date.getDate()).padStart(2, '0');
				    var hours = String(date.getHours()).padStart(2, '0');
				    var minutes = String(date.getMinutes()).padStart(2, '0');
				    var seconds = String(date.getSeconds()).padStart(2, '0');
				    var milliseconds = String(date.getMilliseconds()).padStart(3, '0');
				    return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}.${milliseconds}`;
				}
				
				function formatHoursToHM(hours) {
					    if (hours == null) return 'N/A';
					    const h = Math.floor(hours);
					    const m = Math.round((hours - h) * 60);
					    return `${h}h ${m}m`;
					}
				
				// Watch for changes in startDate and endDate
				$scope.$watchGroup([() => vm.startDate, () => vm.endDate], function(newValues) {
				    if (vm.filterType === 'date') {
				        var startDate = newValues[0];
				        var endDate = newValues[1];
				        if (startDate && endDate) {
				            fetchAssetHistoryReport();
				        }
				    }
				});


				function exportToExcel() {
				    var active = vm.assetHistoryReport.activeAllocations || [];
				    var history = vm.assetHistoryReport.historyAllocations || [];

				    if (active.length === 0 && history.length === 0) {
				        toastr.error('No data available for export!');
				        return;
				    }

				    var ws_data = []; // final sheet data
					
					// --- History Allocations ---
							    if (history.length > 0) {
							        ws_data.push(["History Allocations"]); // section title
							        ws_data.push([
							            "Sr No", "Machine", "Asset ID", "Department", "Lab", "Inside Lab Location",
							            "Status", "Allocate To", "Allocation Date", "Deallocation Date"
							        ]);

							        history.forEach(function(item, index) {
							            ws_data.push([
							                index + 1,
							                item.assetInventory ? item.assetInventory.machine : "N/A",
							                item.assetInventory ? (item.assetInventory.equipmentId + " - " + item.assetInventory.comapnyName) : "N/A",
							                item.assetInventory && item.assetInventory.category && item.assetInventory.category.department 
							                    ? item.assetInventory.category.department.departmentName 
							                    : "N/A",
							                item.assetInventory && item.assetInventory.lab ? item.assetInventory.lab.labName : "N/A",
							                item.assetInventory ? item.assetInventory.labInsideLoc : "N/A",
							                item.status || "N/A",
							                item.allocateTo ? (item.allocateTo.firstName + " " + (item.allocateTo.lastName || "")) : "N/A",
							                item.allocationDate ? formatDate(item.allocationDate) : "N/A",
							                item.deallocationDate ? formatDate(item.deallocationDate) : "N/A"
							            ]);
							        });
							    }

					

				    // --- Active Allocations ---
				    if (active.length > 0) {
				        ws_data.push(["Active Allocations"]); // section title
				        ws_data.push([
				            "Sr No", "Machine", "Asset ID", "Department", "Lab", "Inside Lab Location",
				            "Status", "Allocate To", "Allocation Date", "Deallocation Date"
				        ]);

				        active.forEach(function(item, index) {
				            ws_data.push([
				                index + 1,
				                item.assetInventory ? item.assetInventory.machine : "N/A",
				                item.assetInventory ? (item.assetInventory.equipmentId + " - " + item.assetInventory.comapnyName) : "N/A",
				                item.assetInventory && item.assetInventory.category && item.assetInventory.category.department 
				                    ? item.assetInventory.category.department.departmentName 
				                    : "N/A",
				                item.assetInventory && item.assetInventory.lab ? item.assetInventory.lab.labName : "N/A",
				                item.assetInventory ? item.assetInventory.labInsideLoc : "N/A",
				                item.status || "N/A",
				                item.allocateTo ? (item.allocateTo.firstName + " " + (item.allocateTo.lastName || "")) : "N/A",
				                item.allocationDate ? formatDate(item.allocationDate) : "N/A",
				                item.deallocationDate ? formatDate(item.deallocationDate) : "N/A"
				            ]);
				        });

				        ws_data.push([]); // blank row after section
				    }

			
				    // Convert array-of-arrays into sheet
				    var ws = XLSX.utils.aoa_to_sheet(ws_data);

				    // Auto column width
				    var colWidths = ws_data[1].map(col => ({ wch: col.length + 20 }));
				    ws['!cols'] = colWidths;

				    // Workbook
				    var wb = XLSX.utils.book_new();
				    XLSX.utils.book_append_sheet(wb, ws, "Asset Allocation Report");

				    XLSX.writeFile(wb, "AssetAllocation_Report.xlsx");
				}

      
    }
})();
