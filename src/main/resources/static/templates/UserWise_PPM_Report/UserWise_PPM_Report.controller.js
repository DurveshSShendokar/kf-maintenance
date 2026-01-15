(function() {
    'use strict';

    angular.module('myApp.UserWise_PPM_Report').controller('UserWise_PPM_ReportController', UserWise_PPM_ReportController);

    UserWise_PPM_ReportController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr'];

    function UserWise_PPM_ReportController($scope, ApiEndpoint, $http, toastr) {
        const baseUrl = ApiEndpoint.url;
        
        var vm = angular.extend(this, {
			 users: [],
           selectedUser: null,
            PPMRepo: [],
			exportToExcel:exportToExcel,
            generateUserWiseReport: generateUserWiseReport,
			fromDate: '',
			 toDate: '',
			 addNew: addNew,
			 				            cancel: cancel,
			 							add: add
			
          
           
        });
        
           (function activate() {
            $scope.PPMRepo = {};
          	loadUsers();
			$scope.addNewTab = false;
          	
        })();
			
			 
			
									function cancel() {
										          $scope.addNewTab = false;
										      }

										      function addNew() {
										          $scope.addNewTab = true;
										         
										      }
											  
											  
											  
											  function add(approval) {
											  	    $scope.addNewTab = true; 
											  	    $scope.equipment = approval.machine.eqid; 
											  	    $scope.PPMRepo = Object.assign({}, approval);
													loadChecklist(approval.maint_id);
											  		
											  	   
											  	}
												
						
												function loadChecklist(maintId) {
														    var url = baseUrl + "/maint/getChecklistByMaintId/" + maintId;
														    $http.get(url)
														        .then(function(response) {
														            vm.loadChecklist = response.data.checklist; 
														            console.log("Fetched checklist:", vm.loadChecklist);
														        })
														        .catch(function(error) {
														            console.error("Error fetching checklist:", error);
														        });
														}
				
			


		function generateUserWiseReport(userId, fromDate, toDate) {
		    var url = baseUrl + "/maint/UserWise_Report/" + userId;
		    var params = {};

		    // Add date filters if provided
		    if (fromDate && toDate) {
		        params.fromDate = formatDate(fromDate);
		        params.toDate = formatDate(toDate);
		    }

		    console.log("Request URL:", url);
		    console.log("Request Params:", params);

		    $http.get(url, { params: params })
		        .then(function(response) {
		            vm.PPMRepo = response.data;
		            console.log("Fetched User wise PPM Report:", vm.PPMRepo);
		        })
		        .catch(function(error) {
		            console.error("Error fetching User wise PPM Report:", error);
		        });
		}

        
      		 function loadUsers() {
            var url = baseUrl + "/user/getAlluserForMachine";
            $http.get(url)
                .then(function(response) {
                    vm.users = response.data;
                    console.log("Fetched users:", vm.users);
                })
                .catch(function(error) {
                    console.error("Error fetching users:", error);
                });
        }
   

		function exportToExcel() {
		    if (!vm.PPMRepo || vm.PPMRepo.length === 0) {
		        toastr.error('No data available for export!');
		        return;
		    }

		    // Format the data for export
		    var formattedData = vm.PPMRepo.maintenanceReports.map(function(item, index) {
		        return {
		            'Sr No': index + 1,
		            'Machine Name': item.maint.machine.machine_name, 	
		            'Machine Eqid': item.maint.machine.eqid,          	
		            'Frequency': item.frequency,         	
		            'Acceptable Range': item.acceptableRange,             	
		            'Overall Status': item.maint.overall_status,  
		            'Checkpoints': (item.checklist || "").replace(/ \/ /g, ' /\n'), // Ensure it's a string before replacing
		            'Scheduled Date': formatDate(item.maint.schedule_date), 
		            'Closed Date': item.maint.closedDate ? formatDate(item.maint.closedDate) : "N/A",   
		        };
		    });

		    var ws = XLSX.utils.json_to_sheet(formattedData);

		    // Set column widths for better readability
		    var wscols = [
		        { wch: 10 },  // Sr No
		        { wch: 20 },  // Machine Name
		        { wch: 15 },  // Machine Eqid
		        { wch: 15 },  // Frequency
		        { wch: 20 },  // Acceptable Range
		        { wch: 15 },  // Overall Status
		        { wch: 50 },  // Checkpoints (increase width for better readability)
		        { wch: 20 },  // Scheduled Date
		        { wch: 20 }   // Closed Date
		    ];
		    ws['!cols'] = wscols;

		    // Apply text wrapping only to the "Checkpoints" column (Column G)
		    Object.keys(ws).forEach(cell => {
		        if (cell.startsWith('G') && ws[cell].v) {  // Apply only to 'Checkpoints' column
		            let cellValue = String(ws[cell].v);  // Convert value to string
		            if (cellValue.includes('\n')) {
		                ws[cell].s = { alignment: { wrapText: true } }; // Enable text wrapping
		            }
		        }
		    });

		    var wb = XLSX.utils.book_new();
		    XLSX.utils.book_append_sheet(wb, ws, "Userwise PPM Report");		   
		    XLSX.writeFile(wb, "UserWisePPM_Report.xlsx");
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
