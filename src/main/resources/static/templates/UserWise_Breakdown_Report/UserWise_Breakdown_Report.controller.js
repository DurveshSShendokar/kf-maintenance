(function() {
    'use strict';

    angular.module('myApp.UserWise_Breakdown_Report').controller('UserWise_Breakdown_ReportController', UserWise_Breakdown_ReportController);

    UserWise_Breakdown_ReportController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr'];

    function UserWise_Breakdown_ReportController($scope, ApiEndpoint, $http, toastr) {
        const baseUrl = ApiEndpoint.url;
        
        var vm = angular.extend(this, {
			 users: [],
           selectedUser: null,
            BreakdownRepo: [],
			exportToExcel:exportToExcel,
            generateUserWiseReport: generateUserWiseReport,
			fromDate: '',
			 toDate: ''
			
          
           
        });
        
           (function activate() {
            $scope.BreakdownRepo = {};
          	loadUsers();
          	
        })();
			
			 
			
    
			


		function generateUserWiseReport(userId, fromDate, toDate) {
		    var url = baseUrl + "/breakdown/Breakdown_report/userwise/" + userId;
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
		            vm.BreakdownRepo = response.data;
		            console.log("Fetched User wise Breakdown Report:", vm.BreakdownRepo);
		        })
		        .catch(function(error) {
		            console.error("Error fetching User wise Breakdown Report:", error);
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
		    if (!vm.BreakdownRepo || vm.BreakdownRepo.length === 0) {
		        toastr.error('No data available for export!');
		        return;
		    }

		    // Format the data for export
		    var formattedData = vm.BreakdownRepo.map(function(item, index) {
		        return {
		            'Sr No': index + 1,
		             // 'Machine Name': item.machine ? item.machine.machine_name + ' ' + item.machine.eqid : '',
		               'Machine Name': item.breakdown.machine.machine_name, 
		                'Machine Eqid': item.breakdown.machine.eqid, 
		            
		                'Shift Name': item.breakdown.shift.shift_name, 
						'Status': item.breakdown.status === 1 ? 'Open' :
						          item.breakdown.status === 2 ? 'Trial' :
						          item.breakdown.status === 3 ? 'Closed' : 'Unknown',
 
		                 'Slip Number': item.breakdown.bd_slip, 
		            
		            
		               
		            'Complaint Cause': item.breakdown.observation,         
		            'Root Cause': item.breakdown.root_cause,   
		               'Action Taken': item.breakdown.action_taken,  
		                 'Raised By': item.breakdown.addedBy ? item.breakdown.addedBy.firstName + ' ' + item.breakdown.addedBy.lastName : '',
						 'Update By': item.breakdown.updateBy && item.breakdown.updateBy.firstName
						     ? item.breakdown.updateBy.firstName + ' ' + (item.breakdown.updateBy.lastName || '')
						     : 'N/A',

						 'Trial By': item.breakdown.trialBy && item.breakdown.trialBy.firstName
						     ? item.breakdown.trialBy.firstName + ' ' + (item.breakdown.trialBy.lastName || '')
						     : 'N/A',

		            'Raised Date': formatDate(item.breakdown.ticket_raised_time) , 
		             'Trial Date': formatDate(item.breakdown.ticket_trial_time) ,
		              'Closed Date': formatDate(item.breakdown.ticket_closed_time) ,
					  'Raise To Trial': formatHoursToHM(item.raiseToTrial),
					  'Trial To Closed': formatHoursToHM(item.trialToClosed),
					  'Raise To Closed': formatHoursToHM(item.totalTime),

		                  
		        };
		    });

		    // Create a worksheet from the formatted data
		    var ws = XLSX.utils.json_to_sheet(formattedData);

		    // Create a workbook
		    var wb = XLSX.utils.book_new();
		    XLSX.utils.book_append_sheet(wb, ws, "User wise Breakdown Report");

		    // Export to Excel
		    XLSX.writeFile(wb, "Userwise_Breakdown_Report.xlsx");
		}
		
		function formatHoursToHM(hours) {
		    if (hours == null) return 'N/A';
		    const h = Math.floor(hours);
		    const m = Math.round((hours - h) * 60);
		    return `${h}h ${m}m`;
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
