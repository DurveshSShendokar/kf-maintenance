(function() {
    'use strict';

    angular.module('myApp.dashboard')
        .controller('dashboardController', dashboardController)
        .controller('totalAssetsController', totalAssetsController)
        .controller('totalComplaintsController', totalComplaintsController)
         .controller('totalAllocatesController', totalAllocatesController)
         
          .controller('totalNonAllocatesController', totalNonAllocatesController)
           .controller('totalOpenComplaintsController', totalOpenComplaintsController)
            .controller('InprocessComplaintsController', InprocessComplaintsController)
            .controller('totalClosedComplaintsController', totalClosedComplaintsController);
        
        
    dashboardController.$inject = ['$scope', 'ApiEndpoint', '$state', 'genericFactory','localStorageService', 'toastr'];
     totalAssetsController.$inject = ['$scope', 'ApiEndpoint', 'genericFactory', '$http'];
     totalComplaintsController.$inject = ['$scope', 'ApiEndpoint', 'genericFactory', '$http'];
     totalAllocatesController.$inject = ['$scope', 'ApiEndpoint', 'genericFactory', '$http'];
     
     
      totalNonAllocatesController.$inject = ['$scope', 'ApiEndpoint', 'genericFactory', '$http'];
       totalOpenComplaintsController.$inject = ['$scope', 'ApiEndpoint', 'genericFactory', '$http'];
        totalClosedComplaintsController.$inject = ['$scope', 'ApiEndpoint', 'genericFactory', '$http'];
         InprocessComplaintsController.$inject = ['$scope', 'ApiEndpoint', 'genericFactory', '$http'];

    function dashboardController($scope, ApiEndpoint, $state, genericFactory,localStorageService,toastr) {

        const baseUrl = ApiEndpoint.url;
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
        var vm = angular.extend(this, {
				user : userDetail,	
				callFilterDATA:callFilterDATA,
				handleDropdownChange:handleDropdownChange,
				loadMonthlyComplaintCounts:loadMonthlyComplaintCounts
		});
	  vm.selectedEngineerCount = 5;
	  vm.selectedYear = 2025; 
	   vm.selectedEngineer = ''; 
	   vm.filteredEngineerComplaints = [];  // Holds filtered data based on selected engineer
    vm.engineerChartData = [];  
    vm.engineerChartLabels = [];  
    vm.chartSeries = ['Allocate', 'Inprocess', 'Closed' ];
      vm.chartSeries1 = ['Open','Closed' ];
     vm.engineerComplaints = []; 
	  
        (function activate() {
			
			  console.log("Activating dashboardController");
            TotalCount();
            ComplaintCounts();
            allocateAssets();
            loadMonthlyComplaintCounts();
            fetchTotalAssets();
            fetchTotalComplaints();
            fetchTotalAllocates();
            fetchEngineersWithComplaints();
        })();
        
 function formatDate(date) {
    if (!date) return null;

    const d = new Date(date);
    const year = d.getFullYear();
    const month = String(d.getMonth() + 1).padStart(2, '0'); // Zero-based month
    const day = String(d.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}





// Function to handle dropdown change
function handleDropdownChange() {
    if (vm.selectedStatus === "Overall") {
       
        fetchOverallData();
    } else if (vm.selectedStatus === "Dates") {
        
        if (vm.fromDate && vm.toDate) {
            callFilterDATA();
             vm.fromDate = null;
        vm.toDate = null;
        vm.ComplaintCounts = {};
        }
    }
};

// Function to fetch overall data from the original API
function fetchOverallData() {
    const url = `${baseUrl}/dashboard/complaint_count`;

    genericFactory.getAll("", url).then(response => {
            console.log("Overall data:", response.data);
            vm.ComplaintCount = response.data;
           
            toastr.success("Overall data loaded successfully!");
        })
        .catch(function (error) {
            console.error("Error fetching overall complaints:", error);
            toastr.error("Failed to fetch overall complaints. Please try again.");
        });
}





function callFilterDATA() {
    if (!vm.fromDate || !vm.toDate) {
        alert("Please select both From Date and To Date.");
        return;
    }

    const formattedFromDate = formatDate(vm.fromDate);
    const formattedToDate = formatDate(vm.toDate);

    console.log("Formatted From Date:", formattedFromDate);
    console.log("Formatted To Date:", formattedToDate);

    const url = `${baseUrl}/complaints/filtered_cards?startDate=${formattedFromDate}&endDate=${formattedToDate}`;

    genericFactory.getAll("", url).then(response => {
            console.log("Filtered data:", response.data);
            vm.ComplaintCounts = response.data;
            console.log("DATA SELE "+JSON.stringify(vm.ComplaintCounts));
            
            vm.ComplaintCount.Total_Complaint.allocateComplaintCount=vm.ComplaintCounts.allocated.count	
            vm.ComplaintCount.Total_Complaint.nonallocateComplaintCount=vm.ComplaintCounts.non_allocated.count
            
            vm.ComplaintCount.Total_Complaint.activeComplaintCount=vm.ComplaintCounts.open.count
            vm.ComplaintCount.Total_Complaint.inactiveComplaintCount=vm.ComplaintCounts.closed.count
            vm.ComplaintCount.Total_Complaint.inprocessComplaintCount=vm.ComplaintCounts.inprocess.count
            
            vm.ComplaintCount.Total_Complaint.totalComplaints=vm.ComplaintCounts.totalComps.count
            
             toastr.success("Data loaded successfully!");
        })
        .catch(function (error) {
            console.error("Error fetching filtered complaints:", error);
            toastr.error("Failed to fetch filtered complaints. Please try again.");
        });
}

    




 // Function to handle card clicks and navigate to corresponding detailed view
        $scope.handleCardClick = function(cardType) {
            switch (cardType) {
                case 'totalAssets':
                    $state.go('main.dashboard.total-assets');
                    break;
                default:
                    vm.cardData = [];
            }
        };
        
        
       
        $scope.handleCardClickComp = function(cardType) {
            switch (cardType) {
                case 'totalComplaints':
                    $state.go('main.dashboard.total-complaints');
                    break;
                default:
                    vm.CompCardData = [];
            }
        };
        
            $scope.handleCardClickAllocates = function(cardType) {
            switch (cardType) {
                case 'totalAllocates':
                    $state.go('main.dashboard.total-allocates');
                    break;
                default:
                    vm.AllocatesCardData = [];
            }
        };
        
        
        
         $scope.handleCardClickNonAllocates = function(cardType) {
            switch (cardType) {
                case 'totalNonAllocates':
                    $state.go('main.dashboard.total-Nonallocates');
                    break;
                default:
                    vm.totalNonAllocates = [];
            }
        };
         $scope.handleCardClickCompOpen = function(cardType) {
            switch (cardType) {
                case 'OpenComplaints':
                    $state.go('main.dashboard.OpenComplaints');
                    break;
                default:
                    vm.OpenComplaints = [];
            }
        };
         $scope.handleCardClickCompClosed = function(cardType) {
            switch (cardType) {
                case 'ClosedComplaints':
                    $state.go('main.dashboard.ClosedComplaints');
                    break;
                default:
                    vm.ClosedComplaints = [];
            }
        };
        
         $scope.handleCardClickCompInprocess = function(cardType) {
            switch (cardType) {
                case 'InprocessComplaints':
                    $state.go('main.dashboard.InprocessComplaints');
                    break;
                default:
                    vm.InprocessComplaints = [];
            }
        };
        
        //////
     
	  function fetchEngineersWithComplaints() {
			    const url = `${baseUrl}/dashboard/engineerComplaintCounts`;
			    genericFactory.getAll("", url).then(response => {
			        vm.engineerComplaints = response.data;
			        console.log("Engineer Complaints: " + JSON.stringify(vm.engineerComplaints));
			        vm.updateEngineerData();
			    }).catch(error => {
			        console.error('Error fetching engineer complaints:', error);
			    });
			}

		function updateEngineerChart() {
						    var engineerNames = [];
						    var allocateCounts = [];
						    var inprocessCounts = [];
						    var closedCounts = [];
						
						    angular.forEach(vm.filteredEngineerComplaints, function(item) {
						        engineerNames.push(item.name);
						        allocateCounts.push(item.allocateComplaints);
						        inprocessCounts.push(item.inprocessComplaints);
						        closedCounts.push(item.closedComplaints);
						    });
						
						    vm.engineerChartData = [allocateCounts, inprocessCounts, closedCounts];
						    vm.engineerChartLabels = engineerNames;
						
						    console.log("Engineer Names: ", engineerNames);
						    console.log("Allocate Counts: ", allocateCounts);
						    console.log("Inprocess Counts: ", inprocessCounts);
						    console.log("Closed Counts: ", closedCounts);
						    console.log("Chart Data: ", vm.engineerChartData);
						    console.log("Chart Labels: ", vm.engineerChartLabels);
						
						    vm.chartSeries = ['Allocate', 'Inprocess', 'Closed'];
						
						    vm.engineerChartOptions = {
						        type: 'horizontalBar',
						        scales: {
						            xAxes: [{
						                stacked: true,
						                ticks: {
						                    beginAtZero: true
						                },
						                gridLines: {
						                    color: "rgba(0, 0, 0, 0)"
						                }
						            }],
						            yAxes: [{
						                stacked: true,
						                gridLines: {
						                    color: "rgba(0, 0, 0, 0)"
						                }
						            }]
						        },
						        legend: {
						            display: true,
						            labels: {
						                usePointStyle: true
						            }
						        }
						    };
						
						    vm.engineerChartColors = [{
						        backgroundColor: 'rgba(0, 139, 139, 0.5)',  // Allocate color
						        borderColor: 'rgba(0, 139, 139, 1)',       // Allocate border color
						    }, {
						        backgroundColor: 'rgba(105, 105, 105, 0.2)',  // Inprocess color
						        borderColor: 'rgba(105, 105, 105, 0.5)',     // Inprocess border color
						    }, {
						        backgroundColor: 'rgba(46, 139, 87, 0.35)',   // Closed color
						        borderColor: 'rgba(46, 139, 87, 0.8)',        // Closed border color
						    }];
						}
						
			vm.updateEngineerData = function() {
						    if (vm.selectedEngineer) {
						        vm.filteredEngineerComplaints = filterTopEngineers(vm.engineerComplaints, vm.selectedEngineerCount);
						    } else {
						        vm.filteredEngineerComplaints = filterTopEngineers(vm.engineerComplaints, vm.selectedEngineerCount); // Ensure filtering happens even if no engineer is selected
						    }
						    updateEngineerChart();
						};

				
				function filterTopEngineers(data, count) {
						    console.log('Original Data:', JSON.stringify(data, null, 2)); // Log the original data
						    const sortedData = data.slice().sort((a, b) => {
						        const aTotal = a.allocateComplaints + a.inprocessComplaints + a.closedComplaints;
						        const bTotal = b.allocateComplaints + b.inprocessComplaints + b.closedComplaints;
						        return bTotal - aTotal;
						    });
						    console.log('Sorted Data:', JSON.stringify(sortedData, null, 2)); // Log the sorted data
						
						    const filteredData = sortedData.slice(0, count);
						    console.log('Filtered Data:', JSON.stringify(filteredData, null, 2)); // Log the filtered data
						
						    return filteredData;
						}

							
				
			// for engineer name dropdown
					 vm.selectEngineer = function(engineerName) {
			        if (engineerName === '') {
			            vm.filteredEngineerComplaints = angular.copy(vm.engineerComplaints);
			        } else {
			            // Find the selected engineer's data from vm.engineerComplaints
			            var selectedEngineer = vm.engineerComplaints.find(function(engineer) {
			                return engineer.name === engineerName;
			            });
			
			            // Update vm.filteredEngineerComplaints with the selected engineer's data
			            if (selectedEngineer) {
			                vm.filteredEngineerComplaints = [selectedEngineer];
			            }
			        }
			        updateEngineerChart();
			    };
			        
        
        
        /////
				        
        
         function fetchTotalAllocates() {
            const url = `${baseUrl}/assetAllocation/AllocatedAssets`;
            genericFactory.getAll("", url).then(response => {
                vm.AllocatesCardData = response.data;
                console.log("totalAllocates1", vm.AllocatesCardData);
            }).catch(error => {
                console.error('Error fetching  total Allocates:', error);
            });
        }
        
        
          function fetchTotalComplaints() {
            const url = `${baseUrl}/complaints/all`;
            genericFactory.getAll("", url).then(response => {
                vm.CompCardData = response.data;
                console.log("totalComplaints:", vm.CompCardData);
            }).catch(error => {
                console.error('Error fetching total complaints:', error);
            });
        }

		 function fetchTotalAssets() {
            const url = `${baseUrl}/assetInventory/all`;
            genericFactory.getAll("", url).then(response => {
                vm.cardData = response.data;
                console.log("TotalAssets", vm.cardData);
            }).catch(error => {
                console.error('Error fetching total assets:', error);
            });
        }

        function TotalCount() {
            var msg = "";
            var url = baseUrl + "/dashboard/counts";
            genericFactory.getAll(msg, url).then(function(response) {
                vm.TotalCount = response.data;
                console.log("TotalCount" + JSON.stringify(vm.TotalCount));
            });
        }

        function ComplaintCounts() {
            var msg = "";
            var url = baseUrl + "/dashboard/complaint_count";
            genericFactory.getAll(msg, url).then(function(response) {
                vm.ComplaintCount = response.data;
                console.log("ComplaintCounts" + JSON.stringify(vm.ComplaintCounts));
            });
        }

        function allocateAssets() {
            var msg = "";
            var url = baseUrl + "/dashboard/allocate_count";
            genericFactory.getAll(msg, url).then(function(response) {
                vm.allocateAssets = response.data;
                console.log("allocateComplaints" + JSON.stringify(vm.allocateAssets));
            });
        }

        function loadMonthlyComplaintCounts() {
			  console.log("Selected Year: ", vm.selectedYear); 
			    var msg = "";
			    var url = baseUrl + "/dashboard/monthlyComplaintCount?year=" + vm.selectedYear;
			    
			    genericFactory.getAll(msg, url).then(function(response) {
			        vm.monthlyComplaintCounts = response.data;
			        console.log("Monthly Complaint Counts: " + JSON.stringify(vm.monthlyComplaintCounts));
			        prepareChartData(vm.monthlyComplaintCounts);
			    });
			}


        function prepareChartData(data) {
            vm.chartLabels = data.map(function(item) {
                return item.month;
            });

            var openCounts = data.map(function(item) {
                return item.openCount;
            });

            var closedCounts = data.map(function(item) {
                return item.closedCount;
            });
   var inprocessCounts = data.map(function(item) {
                return item.inprocessCount;
            });

				
            vm.chartData = [openCounts, closedCounts, inprocessCounts];
            
             console.log("Monthly Chart Data: ", vm.chartData);
			 console.log("Monthly Chart Labels: ", vm.chartLabels);
            
            vm.chartColors = [{
				
				    backgroundColor: 'rgba(0, 139, 139, 0.5)',  
				    borderColor: 'rgba(0, 139, 139, 1)',       
				}, {
				   backgroundColor: 'rgba(105, 105, 105, 0.2)',  
				    borderColor: 'rgba(105, 105, 105, 0.5)',          
				}];
            vm.chartSeries1 = ['Open', 'Closed' ,'Inprocess'];
            vm.chartOptions = {
                scales: {
                    xAxes: [{
                        stacked: true
                    }],
                    yAxes: [{
                        stacked: true
                    }]
                },
                 legend: {
						            display: true,
						            labels: {
						                usePointStyle: true
						            }
						        }
            };
        }
    }

      function totalAssetsController($scope, ApiEndpoint, genericFactory, $http) {

        const baseUrl = ApiEndpoint.url;

        var vm = angular.extend(this, {});
        vm.cardData = [];

        activate();

        function activate() {
			 console.log("Activating totalAssetsController");
            fetchTotalAssets();
        }

        function fetchTotalAssets() {
            const url = `${baseUrl}/assetInventory/all`;
            genericFactory.getAll("", url).then(response => {
                vm.cardData = response.data;
                console.log("TotalAssets", vm.cardData);
            }).catch(error => {
                console.error('Error fetching total assets:', error);
            });
        }
        
         // Method to handle the export functionality
    vm.export = function() {
        const url = `${baseUrl}/assetInventory/exportExcel`;  
        $http({
            method: 'GET',
            url: url,
            responseType: 'arraybuffer' 
        }).then(function(response) {
            
            var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
            var link = document.createElement('a');
            link.href = window.URL.createObjectURL(blob);
            link.download = 'asset_inventory.xlsx';  
            link.click();  
        }).catch(function(error) {
            console.error('Error exporting Excel:', error);
        });
    };
        
    }
    
    
    
    function totalComplaintsController($scope, ApiEndpoint, genericFactory, $http) {

			    const baseUrl = ApiEndpoint.url;
			
			    var vm = angular.extend(this, {
					filterComplaints:filterComplaints,
					exporty:exporty
				});
			    vm.CompCardData = [];
			    vm.filteredComplaints = [];
			      vm.fromDate = null;
   				 vm.toDate = null;
			
			    activate();
			
			    function activate() {
			        console.log("Activating totalComplaintsController");
			        fetchTotalComplaints();
			        
			    }
			    
			    
			    
			    	$scope.file="AllCompReport"
			vm.labels={'complaints.assetInventory.machine':'Asset','complaintDate':'complaintDate','complaintBy.fName':'fName','complaintBy.lName':'lName','material.materialCode':'materialCode','material.materialName':'materialName','issue':'issue','engineer.fName':'engfName','engineer.lName':'EnglName','workDone':'workDone','remark':'remark'}			
		
		$scope.newExcel= function(){
			
			 //$rootScope.loader=true;
			 setTimeout(function(){
				 
				 document.getElementById('btnExport').click();
				// $rootScope.loader=false;
				//  $rootScope.$digest();
				},1000);		
			
		}
			
			    function fetchTotalComplaints() {
			        const url = `${baseUrl}/complaints/all`;
			        genericFactory.getAll("", url).then(response => {
			            vm.CompCardData = response.data;
			            vm.filteredComplaints = vm.CompCardData; 
			            console.log("totalComplaints", vm.CompCardData);
			        }).catch(error => {
			            console.error('Error fetching total complaints:', error);
			        });
			    }
			function filterComplaints(){
			  
			        if (vm.fromDate && vm.toDate) {
			            const startDate = formatDate(vm.fromDate);
			            const endDate = formatDate(vm.toDate);
			            
			            console.log("Formatted From Date:", startDate);
			            console.log("Formatted To Date:", endDate);
			            
			            const url = `${baseUrl}/complaints/filtered_cards?startDate=${startDate}&endDate=${endDate}`;
			            $http.get(url).then(function(response) {
			                const filteredData = response.data;
			                vm.filteredComplaints = filteredData.totalComps.records;
			                console.log("Filtered complaints:", vm.filteredComplaints);
			            }).catch(function(error) {
			                console.error('Error filtering complaints:', error);
			            });
			        } else {
			            vm.filteredComplaints = vm.CompCardData;
			            console.log("Showing all complaints data:", vm.filteredComplaints);
			        }
			    };
			
			
			   
 function exporty() {
		
    if (!vm.filteredComplaints || vm.filteredComplaints.length === 0) {
        toastr.error('No data available for export!');
        return;
    }

   
    var formattedData = vm.filteredComplaints.map(function(item, index) {
		
		 function getStatusString(status) {
        switch (status) {
            case 1: return 'Open';
            case 2: return 'Allocate';
            case 3: return 'In Process';
            case 4: return 'Closed';
            default: return 'Unknown';  
        }
        }
        
        
		
        return {
            'Sr No': index + 1,
              'ticket Number': item.ticketNo, 
               
            
             'Asset Name': item.assetInventory ? item.assetInventory.model + ' ' + item.assetInventory.equipmentId : '',
               'Priority': item.priority,
            ' Cause': item.cause,         
            'Description': item.description,   
                      'raised By': item.raisedBy ? item.raisedBy.firstName + ' ' + item.raisedBy.lastName : '',

              'Status': getStatusString(item.status),                
            
            'Complaint Date': formatDate(item.complaintDate) ,
             
            'Inprocess Date': item.inProcessDate ? formatDate(item.inProcessDate) : "N/A",
            'Closed Date': item.closedDate ? formatDate(item.closedDate) : "N/A"
                  
        };
    });

    // Create a worksheet from the formatted data
    var ws = XLSX.utils.json_to_sheet(formattedData);

    // Create a workbook
    var wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "Total No Of Complaints ");

    // Export to Excel
    XLSX.writeFile(wb, "Total No Of Complaints .xlsx");
}

        
        
			
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

		}
						    
    
    // complaint allocate
     function totalAllocatesController($scope, ApiEndpoint, genericFactory, $http) {

        const baseUrl = ApiEndpoint.url;

        var vm = angular.extend(this, {
			filterComplaints:filterComplaints,
					exporty:exporty
		});
        vm.AllocatesCardData = [];
         vm.filteredComplaints = [];
			      vm.fromDate = null;
   				 vm.toDate = null;

        activate();

        function activate() {
			 console.log("Activating totalAllocatesController");
            fetchTotalAllocates();
        }

        function fetchTotalAllocates() {
            const url = `${baseUrl}/complaints/Allocate`;
            genericFactory.getAll("", url).then(response => {
                vm.AllocatesCardData = response.data;
                 vm.filteredComplaints = vm.AllocatesCardData; 
                console.log("totalAllocates:", vm.AllocatesCardData);
            }).catch(error => {
                console.error('Error fetching  total Allocates:', error);
            });
        }
        
      /*    vm.exporty = function() {
        const url = `${baseUrl}/complaints/export/allocated`;  
        $http({
            method: 'GET',
            url: url,
            responseType: 'arraybuffer' 
        }).then(function(response) {
            
            var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
            var link = document.createElement('a');
            link.href = window.URL.createObjectURL(blob);
            link.download = 'Allocated_complaints.xlsx';  
            link.click();  
        }).catch(function(error) {
            console.error('Error exporting Excel:', error);
        });
    }; */
		
		function filterComplaints(){
			  
			        if (vm.fromDate && vm.toDate) {
			            const startDate = formatDate(vm.fromDate);
			            const endDate = formatDate(vm.toDate);
			            
			            console.log("Formatted From Date:", startDate);
			            console.log("Formatted To Date:", endDate);
			            
			            const url = `${baseUrl}/complaints/filtered_cards?startDate=${startDate}&endDate=${endDate}`;
			            $http.get(url).then(function(response) {
			                const filteredData = response.data;
			                vm.filteredComplaints = filteredData.allocated.records;
			                console.log("Filtered complaints:", vm.filteredComplaints);
			            }).catch(function(error) {
			                console.error('Error filtering complaints:', error);
			            });
			        } else {
			            vm.filteredComplaints = vm.AllocatesCardData;
			            console.log("Showing all complaints data:", vm.filteredComplaints);
			        }
			    };
			
			   
			   			   
 function exporty() {
		
    if (!vm.filteredComplaints || vm.filteredComplaints.length === 0) {
        toastr.error('No data available for export!');
        return;
    }

   
    var formattedData = vm.filteredComplaints.map(function(item, index) {
		
		 function getStatusString(status) {
        switch (status) {
            case 1: return 'Open';
            case 2: return 'Allocate';
            case 3: return 'In Process';
            case 4: return 'Closed';
            default: return 'Unknown';  
        }
        }
        
        
		
        return {
            'Sr No': index + 1,
              'ticket Number': item.ticketNo, 
               
            
             'Asset Name': item.assetInventory ? item.assetInventory.model + ' ' + item.assetInventory.equipmentId : '',
               'Priority': item.priority,
            ' Cause': item.cause,         
            'Description': item.description,  
             'raised By': item.raisedBy ? item.raisedBy.firstName + ' ' + item.raisedBy.lastName : '',
              'Status': getStatusString(item.status),                
            'Allocate To': item.allocateTo ? item.allocateTo.firstName + ' ' + item.allocateTo.lastName : '',  
            'Complaint Date': formatDate(item.complaintDate),
             'Complaint Time': formatTime(item.complaintTime) 
           
                  
        };
    });

    // Create a worksheet from the formatted data
    var ws = XLSX.utils.json_to_sheet(formattedData);

    // Create a workbook
    var wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "Allocated Complaints ");

    // Export to Excel
    XLSX.writeFile(wb, "Allocated Complaints .xlsx");
}

        
        
			
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
       

// Function to format times
function formatTime(time) {
    if (!time) {
        return "";
    }
    var t = new Date(time);
    var hours = String(t.getHours()).padStart(2, "0");
    var minutes = String(t.getMinutes()).padStart(2, "0");
    var seconds = String(t.getSeconds()).padStart(2, "0");
    return hours + ":" + minutes + ":" + seconds;
}
    

		}
        
    
    
    
    
    function totalNonAllocatesController($scope, ApiEndpoint, genericFactory, $http) {

        const baseUrl = ApiEndpoint.url;

        var vm = angular.extend(this, {
			filterComplaints:filterComplaints,
					exporty:exporty
		});
        vm.totalNonAllocates = [];
       
         vm.filteredComplaints = [];
			      vm.fromDate = null;
   				 vm.toDate = null;

        activate();

        function activate() {
			 console.log("Activating totalNonAllocatesController");
            fetchtotalNonAllocates();
        }

        function fetchtotalNonAllocates() {
            const url = `${baseUrl}/complaints/nonAllocated`;
            genericFactory.getAll("", url).then(response => {
                vm.totalNonAllocates = response.data;
                 vm.filteredComplaints = vm.totalNonAllocates; 
                console.log("totalNonAllocates:", vm.totalNonAllocates);
            }).catch(error => {
                console.error('Error fetching  total Non-Allocates:', error);
            });
        }
        
       function filterComplaints(){
			  
			        if (vm.fromDate && vm.toDate) {
			            const startDate = formatDate(vm.fromDate);
			            const endDate = formatDate(vm.toDate);
			            
			            console.log("Formatted From Date:", startDate);
			            console.log("Formatted To Date:", endDate);
			            
			            const url = `${baseUrl}/complaints/filtered_cards?startDate=${startDate}&endDate=${endDate}`;
			            $http.get(url).then(function(response) {
			                const filteredData = response.data;
			                vm.filteredComplaints = filteredData.non_allocated.records;
			                console.log("Filtered complaints:", vm.filteredComplaints);
			            }).catch(function(error) {
			                console.error('Error filtering complaints:', error);
			            });
			        } else {
			            vm.filteredComplaints = vm.totalNonAllocates;
			            console.log("Showing all complaints data:", vm.filteredComplaints);
			        }
			    };
			
			   
			  		   			   
 function exporty() {
		
    if (!vm.filteredComplaints || vm.filteredComplaints.length === 0) {
        toastr.error('No data available for export!');
        return;
    }

   
    var formattedData = vm.filteredComplaints.map(function(item, index) {
		
		 function getStatusString(status) {
        switch (status) {
            case 1: return 'Open';
            case 2: return 'Allocate';
            case 3: return 'In Process';
            case 4: return 'Closed';
            default: return 'Unknown';  
        }
        }
        
        
		
        return {
            'Sr No': index + 1,
              'ticket Number': item.ticketNo, 
               
            
             'Asset Name': item.assetInventory ? item.assetInventory.model + ' ' + item.assetInventory.equipmentId : '',
               'Priority': item.priority,
            ' Cause': item.cause,         
               'raised By': item.raisedBy ? item.raisedBy.firstName + ' ' + item.raisedBy.lastName : '',
            'Description': item.description,   
              'Status': getStatusString(item.status),                
           // 'Allocate To': item.allocateTo ? item.allocateTo.firstName + ' ' + item.allocateTo.lastName : '',  
            'Complaint Date': formatDate(item.complaintDate),
             'Complaint Time': formatTime(item.complaintTime) 
           
                  
        };
    });

    // Create a worksheet from the formatted data
    var ws = XLSX.utils.json_to_sheet(formattedData);

    // Create a workbook
    var wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "Non-Allocated Complaints ");

    // Export to Excel
    XLSX.writeFile(wb, "Non-Allocated Complaints .xlsx");
}

        
        
			
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
       

// Function to format times
function formatTime(time) {
    if (!time) {
        return "";
    }
    var t = new Date(time);
    var hours = String(t.getHours()).padStart(2, "0");
    var minutes = String(t.getMinutes()).padStart(2, "0");
    var seconds = String(t.getSeconds()).padStart(2, "0");
    return hours + ":" + minutes + ":" + seconds;
}
    

    
        
    }
    
    
    
    function totalOpenComplaintsController($scope, ApiEndpoint, genericFactory, $http) {

        const baseUrl = ApiEndpoint.url;

        var vm = angular.extend(this, {
			filterComplaints:filterComplaints,
					exporty:exporty
		});
        vm.OpenComplaints = [];
          vm.filteredComplaints = [];
			      vm.fromDate = null;
   				 vm.toDate = null;

        activate();

        function activate() {
			 console.log("Activating totalOpenComplaintsController");
            fetchTotalOpenComplaints();
        }

        function fetchTotalOpenComplaints() {
            const url = `${baseUrl}/complaints/open`;
            genericFactory.getAll("", url).then(response => {
                vm.OpenComplaints = response.data;
                  vm.filteredComplaints = vm.OpenComplaints;
                console.log("total OpenComplaints:", vm.OpenComplaints);
            }).catch(error => {
                console.error('Error fetching  total OpenComplaints:', error);
            });
        }
         function filterComplaints(){
			  
			        if (vm.fromDate && vm.toDate) {
			            const startDate = formatDate(vm.fromDate);
			            const endDate = formatDate(vm.toDate);
			            
			            console.log("Formatted From Date:", startDate);
			            console.log("Formatted To Date:", endDate);
			            
			            const url = `${baseUrl}/complaints/filtered_cards?startDate=${startDate}&endDate=${endDate}`;
			            $http.get(url).then(function(response) {
			                const filteredData = response.data;
			                vm.filteredComplaints = filteredData.open.records;
			                console.log("Filtered complaints:", vm.filteredComplaints);
			            }).catch(function(error) {
			                console.error('Error filtering complaints:', error);
			            });
			        } else {
			            vm.filteredComplaints = vm.OpenComplaints;
			            console.log("Showing all complaints data:", vm.filteredComplaints);
			        }
			    };
			
			  		  		   			   
 function exporty() {
		
    if (!vm.filteredComplaints || vm.filteredComplaints.length === 0) {
        toastr.error('No data available for export!');
        return;
    }

   
    var formattedData = vm.filteredComplaints.map(function(item, index) {
		
		 function getStatusString(status) {
        switch (status) {
            case 1: return 'Open';
            case 2: return 'Allocate';
            case 3: return 'In Process';
            case 4: return 'Closed';
            default: return 'Unknown';  
        }
        }
        
        
		
    return {
    'Sr No': index + 1,
    'Ticket Number': item.ticketNo || '', 
    'Asset Name': item.assetInventory 
        ? (item.assetInventory.model || '') + ' ' + (item.assetInventory.equipmentId || '') 
        : '',
    'Priority': item.priority || '',
    'Cause': item.cause || '',   
     'Raised By': item.raisedBy 
        ? (item.raisedBy.firstName || '') + ' ' + (item.raisedBy.lastName || '') 
        : '',    
    'Description': item.description || '',   
    'Status': getStatusString(item.status) || 'Unknown',                
    'Allocate To': item.allocateTo 
        ? (item.allocateTo.firstName || '') + ' ' + (item.allocateTo.lastName || '') 
        : '',  
    'Complaint Date': formatDate(item.complaintDate),
   'Complaint Time': item.complaintTime ? formatTime(item.complaintTime) : ''

};

    });

    // Create a worksheet from the formatted data
    var ws = XLSX.utils.json_to_sheet(formattedData);

    // Create a workbook
    var wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "Open Complaints ");

   // Export without interacting with DOM
    try {
        XLSX.writeFile(wb, "Open Complaints.xlsx");
    } catch (error) {
        console.error("Error exporting file:", error);
    }
}

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
       

// Function to format times
function formatTime(time) {
    if (!time) {
        return "";
    }
    var t = new Date(time);
    var hours = String(t.getHours()).padStart(2, "0");
    var minutes = String(t.getMinutes()).padStart(2, "0");
    var seconds = String(t.getSeconds()).padStart(2, "0");
    return hours + ":" + minutes + ":" + seconds;
}
    

    }
    
    
    
    function totalClosedComplaintsController($scope, ApiEndpoint, genericFactory, $http) {

        const baseUrl = ApiEndpoint.url;

        var vm = angular.extend(this, {
			filterComplaints:filterComplaints,
					exporty:exporty
		});
        vm.ClosedComplaints = [];
        vm.filteredComplaints = [];
			      vm.fromDate = null;
   				 vm.toDate = null;

        activate();

        function activate() {
			 console.log("Activating totalClosedComplaintsController");
            fetchTotalClosedComplaints();
        }

        function fetchTotalClosedComplaints() {
            const url = `${baseUrl}/complaints/closed`;
            genericFactory.getAll("", url).then(response => {
                vm.ClosedComplaints = response.data;
                 vm.filteredComplaints = vm.ClosedComplaints;
                console.log("total ClosedComplaints:", vm.ClosedComplaints);
            }).catch(error => {
                console.error('Error fetching  total ClosedComplaints:', error);
            });
        }
           function filterComplaints(){
			  
			        if (vm.fromDate && vm.toDate) {
			            const startDate = formatDate(vm.fromDate);
			            const endDate = formatDate(vm.toDate);
			            
			            console.log("Formatted From Date:", startDate);
			            console.log("Formatted To Date:", endDate);
			            
			            const url = `${baseUrl}/complaints/filtered_cards?startDate=${startDate}&endDate=${endDate}`;
			            $http.get(url).then(function(response) {
			                const filteredData = response.data;
			                vm.filteredComplaints = filteredData.closed.records;
			                console.log("Filtered complaints:", vm.filteredComplaints);
			            }).catch(function(error) {
			                console.error('Error filtering complaints:', error);
			            });
			        } else {
			            vm.filteredComplaints = vm.ClosedComplaints;
			            console.log("Showing all complaints data:", vm.filteredComplaints);
			        }
			    };
			
			   
			  			  		  		   			   
 function exporty() {
		
    if (!vm.filteredComplaints || vm.filteredComplaints.length === 0) {
        toastr.error('No data available for export!');
        return;
    }

   
    var formattedData = vm.filteredComplaints.map(function(item, index) {
		
		 function getStatusString(status) {
        switch (status) {
            case 1: return 'Open';
            case 2: return 'Allocate';
            case 3: return 'In Process';
            case 4: return 'Closed';
            default: return 'Unknown';  
        }
        }
        
        
		
        return {
            'Sr No': index + 1,
              'ticket Number': item.ticketNo, 
               
            
             'Asset Name': item.assetInventory ? item.assetInventory.model + ' ' + item.assetInventory.equipmentId : '',
               'Priority': item.priority,
            ' Cause': item.cause,         
            'Description': item.description,   
                         'raised By': item.raisedBy ? item.raisedBy.firstName + ' ' + item.raisedBy.lastName : '',

              'Status': getStatusString(item.status),                
            'Allocate To': item.allocateTo ? item.allocateTo.firstName + ' ' + item.allocateTo.lastName : '',  
            'Complaint Date': formatDate(item.complaintDate),
             'Closed date': formatDate(item.closedDate) 
           
                  
        };
    });

    // Create a worksheet from the formatted data
    var ws = XLSX.utils.json_to_sheet(formattedData);

    // Create a workbook
    var wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "Closed Complaints ");

    // Export to Excel
    XLSX.writeFile(wb, "Closed Complaints .xlsx");
}

        
        
			
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


    }
    
    
     function InprocessComplaintsController($scope, ApiEndpoint, genericFactory, $http) {

        const baseUrl = ApiEndpoint.url;

        var vm = angular.extend(this, {
			filterComplaints:filterComplaints,
					exporty:exporty
		});
        vm.InprocessComplaints = [];
        vm.filteredComplaints = [];
			      vm.fromDate = null;
   				 vm.toDate = null;

        activate();

        function activate() {
			 console.log("Activating InprocessComplaintsController");
            fetchInprocessComplaints();
        }

        function fetchInprocessComplaints() {
            const url = `${baseUrl}/complaints/Inprocess`;
            genericFactory.getAll("", url).then(response => {
                vm.InprocessComplaints = response.data;
                 vm.filteredComplaints = vm.InprocessComplaints;
                console.log("total InprocessComplaints:", vm.InprocessComplaints);
            }).catch(error => {
                console.error('Error fetching  total InprocessComplaints:', error);
            });
        }
        
         function filterComplaints(){
			  
			        if (vm.fromDate && vm.toDate) {
			            const startDate = formatDate(vm.fromDate);
			            const endDate = formatDate(vm.toDate);
			            
			            console.log("Formatted From Date:", startDate);
			            console.log("Formatted To Date:", endDate);
			            
			            const url = `${baseUrl}/complaints/filtered_cards?startDate=${startDate}&endDate=${endDate}`;
			            $http.get(url).then(function(response) {
			                const filteredData = response.data;
			                vm.filteredComplaints = filteredData.inprocess.records;
			                console.log("Filtered complaints:", vm.filteredComplaints);
			            }).catch(function(error) {
			                console.error('Error filtering complaints:', error);
			            });
			        } else {
			            vm.filteredComplaints = vm.InprocessComplaints;
			            console.log("Showing all complaints data:", vm.filteredComplaints);
			        }
			    };
			
			   
						  			  		  		   			   
 function exporty() {
		
    if (!vm.filteredComplaints || vm.filteredComplaints.length === 0) {
        toastr.error('No data available for export!');
        return;
    }

   
    var formattedData = vm.filteredComplaints.map(function(item, index) {
		
		 function getStatusString(status) {
        switch (status) {
            case 1: return 'Open';
            case 2: return 'Allocate';
            case 3: return 'In Process';
            case 4: return 'Closed';
            default: return 'Unknown';  
        }
        }
        
        
		
        return {
            'Sr No': index + 1,
              'ticket Number': item.ticketNo, 
               
            
             'Asset Name': item.assetInventory ? item.assetInventory.model + ' ' + item.assetInventory.equipmentId : '',
               'Priority': item.priority,
            ' Cause': item.cause,         
            'Description': item.description,   
                          'raised By': item.raisedBy ? item.raisedBy.firstName + ' ' + item.raisedBy.lastName : '',
  
              'Status': getStatusString(item.status),                
            'Allocate To': item.allocateTo ? item.allocateTo.firstName + ' ' + item.allocateTo.lastName : '',  
            'Complaint Date': formatDate(item.complaintDate),
             'Inprocess date': formatDate(item.inProcessDate) 
           
                  
        };
    });

    // Create a worksheet from the formatted data
    var ws = XLSX.utils.json_to_sheet(formattedData);

    // Create a workbook
    var wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "Inprocess Complaints ");

    // Export to Excel
    XLSX.writeFile(wb, "Inprocess Complaints .xlsx");
}

        
        
			
			    function formatDate(date) {
					    if (!date) { 
					        return ""; 
					    }
					    var d = new Date(date);
					    var day = String(d.getDate()).padStart(2, "0");
					    var month = String(d.getMonth() + 1).padStart(2, "0");
					    var year = d.getFullYear();
					    return year + "-" + month + "-" + day;
					}

    }
    
    
    
    
})();
