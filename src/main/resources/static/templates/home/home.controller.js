(function() {
	'use strict';

	angular.module('myApp.home')

		.controller('HomeController', HomeController)

		.controller('totalMachinesController', totalMachinesController)
		.controller('overallBreakdownController', overallBreakdownController)

		.controller('totalBreakdownController', totalBreakdownController)
		.controller('openBreakdownController', openBreakdownController)
		.controller('closedBreakdownController', closedBreakdownController)
		.controller('trialBreakdownController', trialBreakdownController)

	
	.controller('totalMaintainceController', totalMaintainceController)
		.controller('openMaintainceController', openMaintainceController)
		.controller('totalopenMaintainceController', totalopenMaintainceController)
		.controller('totalclosedMaintainceController', totalclosedMaintainceController)
		.controller('closedMaintainceController', closedMaintainceController)
		.controller('TodayMaintainceController', TodayMaintainceController)
		.controller('totalApprovedController', totalApprovedController)
		.controller('totalUnApprovedController', totalUnApprovedController)
		
		.controller('totaltrialBreakdownController', totaltrialBreakdownController)
		.controller('totalclosedBreakdownController', totalclosedBreakdownController)
		.controller('totalopenBreakdownController', totalopenBreakdownController)
		
			
				
				.controller('overallBreakdownByMachineController', overallBreakdownByMachineController)
				.controller('totalRepeatBreakdownByMachineController', totalRepeatBreakdownByMachineController)
				
				.controller('totaltrialBreakdownByMachineController', totaltrialBreakdownByMachineController)
				.controller('totalclosedBreakdownByMachineController', totalclosedBreakdownByMachineController)
				.controller('totalopenBreakdownByMachineController', totalopenBreakdownByMachineController)
				
				
				

						.controller('TodayMaintainceByMachineController', TodayMaintainceByMachineController)
							.controller('totalMaintainceByMachineController', totalMaintainceByMachineController)
							.controller('totalApprovedByMachineController', totalApprovedByMachineController)
							
							.controller('totalUnApprovedByMachineController', totalUnApprovedByMachineController)
							.controller('totalopenMaintainceByMachineController', totalopenMaintainceByMachineController)
							.controller('totalclosedMaintainceByMachineController', totalclosedMaintainceByMachineController);

	HomeController.$inject = ['$scope', 'ApiEndpoint', '$state', 'genericFactory', '$http', 'localStorageService', '$location', '$rootScope'];

	totalMachinesController.$inject = ['$scope', 'ApiEndpoint', '$state', 'genericFactory', '$http'];
	overallBreakdownController.$inject = ['$scope', 'ApiEndpoint', '$state', 'genericFactory', '$http'];

	totalBreakdownController.$inject = ['$scope', 'ApiEndpoint', '$state', 'genericFactory', '$http'];
	openBreakdownController.$inject = ['$scope', 'ApiEndpoint', '$state', 'genericFactory', '$http'];
	closedBreakdownController.$inject = ['$scope', 'ApiEndpoint', '$state', 'genericFactory', '$http'];
	trialBreakdownController.$inject = ['$scope', 'ApiEndpoint', '$state', 'genericFactory', '$http'];

	totalMaintainceController.$inject = ['$scope', 'toastr', 'ApiEndpoint', '$state', 'genericFactory', '$http'];
	openMaintainceController.$inject = ['$scope', 'ApiEndpoint', '$state', 'genericFactory', '$http'];
	closedMaintainceController.$inject = ['$scope', 'ApiEndpoint', '$state', 'genericFactory', '$http'];
	totalopenMaintainceController.$inject = ['$scope', 'ApiEndpoint', '$state', 'genericFactory', '$http'];
	totalclosedMaintainceController.$inject = ['$scope', 'ApiEndpoint', '$state', 'genericFactory', '$http'];
	TodayMaintainceController.$inject = ['$scope', 'ApiEndpoint', '$state', 'genericFactory', '$http'];
	totalApprovedController.$inject = ['$scope', 'ApiEndpoint', '$state', 'genericFactory', '$http'];
	totalUnApprovedController.$inject = ['$scope', 'ApiEndpoint', '$state', 'genericFactory', '$http'];
	
	totalopenBreakdownController.$inject = ['$scope', 'ApiEndpoint', '$state', 'genericFactory', '$http'];
	totalclosedBreakdownController.$inject = ['$scope', 'ApiEndpoint', '$state', 'genericFactory', '$http'];
	totaltrialBreakdownController.$inject = ['$scope', 'ApiEndpoint', '$state', 'genericFactory', '$http'];
	
	
	//machine

	overallBreakdownByMachineController.$inject = ['$scope', 'ApiEndpoint', '$state', '$stateParams', 'genericFactory', '$http', 'toastr'];
			
			totalRepeatBreakdownByMachineController.$inject = ['$scope', 'ApiEndpoint', '$state', '$stateParams', 'genericFactory', '$http', 'toastr'];
		
		
		
		totalopenBreakdownByMachineController.$inject = ['$scope', 'ApiEndpoint', '$state', '$stateParams', 'genericFactory', '$http', 'toastr'];
		
		totalclosedBreakdownByMachineController.$inject = ['$scope', 'ApiEndpoint', '$state', '$stateParams', 'genericFactory', '$http', 'toastr'];
				
		totaltrialBreakdownByMachineController.$inject = ['$scope', 'ApiEndpoint', '$state', '$stateParams', 'genericFactory', '$http', 'toastr'];
		
///

TodayMaintainceByMachineController.$inject = ['$scope', 'ApiEndpoint', '$state', '$stateParams', 'genericFactory', '$http', 'toastr'];
		
		totalMaintainceByMachineController.$inject = ['$scope', 'ApiEndpoint', '$state', '$stateParams', 'genericFactory', '$http', 'toastr'];
				
		totalApprovedByMachineController.$inject = ['$scope', 'ApiEndpoint', '$state', '$stateParams', 'genericFactory', '$http', 'toastr'];
		
		totalUnApprovedByMachineController.$inject = ['$scope', 'ApiEndpoint', '$state', '$stateParams', 'genericFactory', '$http', 'toastr'];
				
				totalopenMaintainceByMachineController.$inject = ['$scope', 'ApiEndpoint', '$state', '$stateParams', 'genericFactory', '$http', 'toastr'];
						
				totalclosedMaintainceByMachineController.$inject = ['$scope', 'ApiEndpoint', '$state', '$stateParams', 'genericFactory', '$http', 'toastr'];
		
			// for ppm 	
			
			
			function totalclosedMaintainceByMachineController($scope, ApiEndpoint, $state, $stateParams, genericFactory, $http, toastr) {
			    const baseUrl = ApiEndpoint.url;
			    let machineId = null;

			    var vm = angular.extend(this, {
			        filterComplaints: filterComplaints,
			        fetchtotalclosedMaintainceByMachine: fetchtotalclosedMaintainceByMachine,
			        exportToExcel: exportToExcel
			    });

			    vm.totalclosedMaintainceByMachine = [];
			    vm.filteredComplaints = [];
			    vm.fromDate = null;
			    vm.toDate = null;

			    activate();

			    function activate() {
			        console.log("Activating totalclosedMaintainceByMachineController");
			        machineId = $stateParams.machineId;
			        if (machineId) {
			            fetchtotalclosedMaintainceByMachine(machineId);
			            filterComplaints();
			        }
			    }

			    function fetchtotalclosedMaintainceByMachine(machineId) {
			        if (!machineId) return;

			        let url = `${baseUrl}/dashboard/maintaince_records/${machineId}`;
			        $http.get(url).then(response => {
			            vm.totalclosedMaintainceByMachine = response.data;
			            vm.filteredComplaints = vm.totalclosedMaintainceByMachine.totalClosedRecords; // default open list
			            console.log("Closed PPM by machine:", vm.totalclosedMaintainceByMachine);
			        }).catch(error => {
			            console.error("Error fetching Closed PPM:", error);
			        });
			    }

			    function filterComplaints() {
			        if (!machineId) {
			            console.warn("Please provide a machineId!");
			            return;
			        }

			        let url = `${baseUrl}/dashboard/maintaince_records/${machineId}`;
			        if (vm.fromDate && vm.toDate) {
			            const fromDate = formatDate(vm.fromDate);
			            const toDate = formatDate(vm.toDate);
			            url += `?fromDate=${fromDate}&toDate=${toDate}`;
			            console.log("Filtering with FromDate:", fromDate, "ToDate:", toDate);
			        }

			        $http.get(url).then(response => {
			            vm.breakdownCounts = response.data;
			            vm.filteredComplaints = vm.breakdownCounts.totalClosedRecords; // default show overdue list
			            console.log("Filtered Closed PPM counts:", vm.breakdownCounts);
			        }).catch(error => {
			            console.error("Error filtering complaints:", error);
			        });
			    }

			    function formatDate(date) {
			        if (!date) return "";
			        var d = new Date(date);
			        var day = String(d.getDate()).padStart(2, "0");
			        var month = String(d.getMonth() + 1).padStart(2, "0");
			        var year = d.getFullYear();
			        return year + "-" + month + "-" + day;
			    }

			    function getStatusText(status) {
			        switch (status) {
			            case 1: return "Open";
			            case 2: return "Trial";
			            case 3: return "Closed";
			            default: return "Unknown";
			        }
			    }

			    function exportToExcel() {
			        if (!vm.filteredComplaints || vm.filteredComplaints.length === 0) {
			            // toastr.error("No data available for export!");
			            return;
			        }

			        var formattedData = vm.filteredComplaints.map(function(item, index) {
			            return {
			                "Sr No": index + 1,
			              "Machine Name": item.machine ? item.machine.machine_name + " " + item.machine.eqid : "",
			                "Lab Name": item.lab.labName,
			           
			                "Frequency": item.frequency,
			                "Overall Status": item.overall_status,
			              
			                "Schedule Date": formatDate(item.schedule_date),
							'Raised By': item.raisedBy ? item.raisedBy.firstName + ' ' + item.raisedBy.lastName : '',
							'Done By': item.done_by ? item.done_by.firstName + ' ' + item.done_by.lastName : ''
			            };
			        });

			        var ws = XLSX.utils.json_to_sheet(formattedData);
			        var wb = XLSX.utils.book_new();
			        XLSX.utils.book_append_sheet(wb, ws, "Closed PPM By Machine");
			        XLSX.writeFile(wb, "Closed_PPMByMachine.xlsx");
			    }
			}

			
			
			
			
			function totalopenMaintainceByMachineController($scope, ApiEndpoint, $state, $stateParams, genericFactory, $http, toastr) {
			    const baseUrl = ApiEndpoint.url;
			    let machineId = null;

			    var vm = angular.extend(this, {
			        filterComplaints: filterComplaints,
			        fetchtotalopenMaintainceByMachine: fetchtotalopenMaintainceByMachine,
			        exportToExcel: exportToExcel
			    });

			    vm.totalopenMaintainceByMachine = [];
			    vm.filteredComplaints = [];
			    vm.fromDate = null;
			    vm.toDate = null;

			    activate();

			    function activate() {
			        console.log("Activating totalopenMaintainceByMachineController");
			        machineId = $stateParams.machineId;
			        if (machineId) {
			            fetchtotalopenMaintainceByMachine(machineId);
			            filterComplaints();
			        }
			    }

			    function fetchtotalopenMaintainceByMachine(machineId) {
			        if (!machineId) return;

			        let url = `${baseUrl}/dashboard/maintaince_records/${machineId}`;
			        $http.get(url).then(response => {
			            vm.totalopenMaintainceByMachine = response.data;
			            vm.filteredComplaints = vm.totalopenMaintainceByMachine.totalOpenRecords; // default open list
			            console.log("Open PPM by machine:", vm.totalopenMaintainceByMachine);
			        }).catch(error => {
			            console.error("Error fetching Open PPM:", error);
			        });
			    }

			    function filterComplaints() {
			        if (!machineId) {
			            console.warn("Please provide a machineId!");
			            return;
			        }

			        let url = `${baseUrl}/dashboard/maintaince_records/${machineId}`;
			        if (vm.fromDate && vm.toDate) {
			            const fromDate = formatDate(vm.fromDate);
			            const toDate = formatDate(vm.toDate);
			            url += `?fromDate=${fromDate}&toDate=${toDate}`;
			            console.log("Filtering with FromDate:", fromDate, "ToDate:", toDate);
			        }

			        $http.get(url).then(response => {
			            vm.breakdownCounts = response.data;
			            vm.filteredComplaints = vm.breakdownCounts.totalOpenRecords; // default show overdue list
			            console.log("Filtered Open PPM counts:", vm.breakdownCounts);
			        }).catch(error => {
			            console.error("Error filtering complaints:", error);
			        });
			    }

			    function formatDate(date) {
			        if (!date) return "";
			        var d = new Date(date);
			        var day = String(d.getDate()).padStart(2, "0");
			        var month = String(d.getMonth() + 1).padStart(2, "0");
			        var year = d.getFullYear();
			        return year + "-" + month + "-" + day;
			    }

			    function getStatusText(status) {
			        switch (status) {
			            case 1: return "Open";
			            case 2: return "Trial";
			            case 3: return "Closed";
			            default: return "Unknown";
			        }
			    }

				function exportToExcel() {
						        if (!vm.filteredComplaints || vm.filteredComplaints.length === 0) {
						            // toastr.error("No data available for export!");
						            return;
						        }

						        var formattedData = vm.filteredComplaints.map(function(item, index) {
						            return {
						                "Sr No": index + 1,
						              "Machine Name": item.machine ? item.machine.machine_name + " " + item.machine.eqid : "",
						                "Lab Name": item.lab.labName,
						           
						                "Frequency": item.frequency,
						                "Overall Status": item.overall_status,
						              
						                "Schedule Date": formatDate(item.schedule_date),
										'Raised By': item.raisedBy ? item.raisedBy.firstName + ' ' + item.raisedBy.lastName : '',
										//'Done By': item.done_by ? item.done_by.firstName + ' ' + item.done_by.lastName : ''
						            };
						        });

			        var ws = XLSX.utils.json_to_sheet(formattedData);
			        var wb = XLSX.utils.book_new();
			        XLSX.utils.book_append_sheet(wb, ws, "Open PPM By Machine");
			        XLSX.writeFile(wb, "Open_PPMByMachine.xlsx");
			    }
			}

			
			
			
			
			
			function totalUnApprovedByMachineController($scope, ApiEndpoint, $state, $stateParams, genericFactory, $http, toastr) {
			    const baseUrl = ApiEndpoint.url;
			    let machineId = null;

			    var vm = angular.extend(this, {
			        filterComplaints: filterComplaints,
			        fetchtotalUnApprovedByMachine: fetchtotalUnApprovedByMachine,
			        exportToExcel: exportToExcel
			    });

			    vm.totalUnApprovedByMachine = [];
			    vm.filteredComplaints = [];
			    vm.fromDate = null;
			    vm.toDate = null;

			    activate();

			    function activate() {
			        console.log("Activating totalUnApprovedByMachineController");
			        machineId = $stateParams.machineId;
			        if (machineId) {
			            fetchtotalUnApprovedByMachine(machineId);
			            filterComplaints();
			        }
			    }

			    function fetchtotalUnApprovedByMachine(machineId) {
			        if (!machineId) return;

			        let url = `${baseUrl}/dashboard/maintaince_records/${machineId}`;
			        $http.get(url).then(response => {
			            vm.totalUnApprovedByMachine = response.data;
			            vm.filteredComplaints = vm.totalUnApprovedByMachine.totalUnApprovedRecords; // default open list
			            console.log("UnApproved PPM by machine:", vm.totalUnApprovedByMachine);
			        }).catch(error => {
			            console.error("Error fetching UnApproved PPM:", error);
			        });
			    }

			    function filterComplaints() {
			        if (!machineId) {
			            console.warn("Please provide a machineId!");
			            return;
			        }

			        let url = `${baseUrl}/dashboard/maintaince_records/${machineId}`;
			        if (vm.fromDate && vm.toDate) {
			            const fromDate = formatDate(vm.fromDate);
			            const toDate = formatDate(vm.toDate);
			            url += `?fromDate=${fromDate}&toDate=${toDate}`;
			            console.log("Filtering with FromDate:", fromDate, "ToDate:", toDate);
			        }

			        $http.get(url).then(response => {
			            vm.breakdownCounts = response.data;
			            vm.filteredComplaints = vm.breakdownCounts.totalUnApprovedRecords; // default show overdue list
			            console.log("Filtered UnApproved PPM counts:", vm.breakdownCounts);
			        }).catch(error => {
			            console.error("Error filtering complaints:", error);
			        });
			    }

			    function formatDate(date) {
			        if (!date) return "";
			        var d = new Date(date);
			        var day = String(d.getDate()).padStart(2, "0");
			        var month = String(d.getMonth() + 1).padStart(2, "0");
			        var year = d.getFullYear();
			        return year + "-" + month + "-" + day;
			    }

			    function getStatusText(status) {
			        switch (status) {
			            case 1: return "Open";
			            case 2: return "Trial";
			            case 3: return "Closed";
			            default: return "Unknown";
			        }
			    }

				function exportToExcel() {
						        if (!vm.filteredComplaints || vm.filteredComplaints.length === 0) {
						            // toastr.error("No data available for export!");
						            return;
						        }

						        var formattedData = vm.filteredComplaints.map(function(item, index) {
						            return {
						                "Sr No": index + 1,
						              "Machine Name": item.machine ? item.machine.machine_name + " " + item.machine.eqid : "",
						                "Lab Name": item.lab.labName,
						           
						                "Frequency": item.frequency,
						                "Overall Status": item.overall_status,
										"UnApproval Date ": item.unApprovalDate,
						                "Schedule Date": formatDate(item.schedule_date),
										'Raised By': item.raisedBy ? item.raisedBy.firstName + ' ' + item.raisedBy.lastName : '',
										'Done By': item.done_by ? item.done_by.firstName + ' ' + item.done_by.lastName : ''
						            };
						        });


			        var ws = XLSX.utils.json_to_sheet(formattedData);
			        var wb = XLSX.utils.book_new();
			        XLSX.utils.book_append_sheet(wb, ws, "UnApproved PPM By Machine");
			        XLSX.writeFile(wb, "UnApproved_PPMByMachine.xlsx");
			    }
			}

			
			
			
			function TodayMaintainceByMachineController($scope, ApiEndpoint, $state, $stateParams, genericFactory, $http, toastr) {

										const baseUrl = ApiEndpoint.url;
										let machineId = null;  

										var vm = angular.extend(this, {
											filterComplaints:filterComplaints,
											fetchTodayMaintainceByMachine:fetchTodayMaintainceByMachine,
											exportToExcel:exportToExcel
										});
										vm.TodayMaintainceByMachine = [];
										 vm.filteredComplaints = [];
											      vm.fromDate = null;
								   				 vm.toDate = null;

												 activate();

										function activate() {
											console.log("Activating TodayMaintainceByMachineController");
											 machineId = $stateParams.machineId;
											       if (machineId) {
											           fetchTodayMaintainceByMachine(machineId);
													   filterComplaints();
											       }
										}

										function fetchTodayMaintainceByMachine(machineId) {
										      if (!machineId) return;

										      let url = `${baseUrl}/dashboard/maintaince_records/${machineId}`;

										      $http.get(url).then(response => {
										          vm.TodayMaintainceByMachine = response.data;
										          vm.filteredComplaints = vm.TodayMaintainceByMachine.totalOverdueRecords; // default open list
										          console.log("Overude  PPM by machine:", vm.TodayMaintainceByMachine);
										      }).catch(error => {
										          console.error('Error fetching Overdue  PPM:', error);
										      });
										  }
										
									
										  function filterComplaints() {
										        if (!machineId) {
										            console.warn("Please provide a machineId!");
										            return;
										        }

										        let url = `${baseUrl}/dashboard/maintaince_records/${machineId}`;
										        if (vm.fromDate && vm.toDate) {
										            const fromDate = formatDate(vm.fromDate);
										            const toDate = formatDate(vm.toDate);
										            url += `?fromDate=${fromDate}&toDate=${toDate}`;
										            console.log("Filtering with FromDate:", fromDate, "ToDate:", toDate);
										        }

										        $http.get(url).then(response => {
										            vm.breakdownCounts = response.data;
										            vm.filteredComplaints = vm.breakdownCounts.totalOverdueRecords; // default show open list
													
										            console.log("Filtered Overdue PPM counts:", vm.breakdownCounts);
										        }).catch(error => {
										            console.error('Error filtering complaints:', error);
										        });
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
													
													
													  // Function to map status numbers to text
								 

								


													
													  function exportToExcel() {
													  					        if (!vm.filteredComplaints || vm.filteredComplaints.length === 0) {
													  					            // toastr.error("No data available for export!");
													  					            return;
													  					        }

													  					        var formattedData = vm.filteredComplaints.map(function(item, index) {
													  					            return {
													  					                "Sr No": index + 1,
													  					              "Machine Name": item.machine ? item.machine.machine_name + " " + item.machine.eqid : "",
													  					                "Lab Name": item.lab.labName,
													  					           
													  					                "Frequency": item.frequency,
													  					                "machine location": item.machine.location,
													  								//	"UnApproval Date ": item.unApprovalDate,
													  					                "Schedule Date": formatDate(item.schedule_date),
													  									'Raised By': item.raisedBy ? item.raisedBy.firstName + ' ' + item.raisedBy.lastName : '',
													  								//	'Done By': item.done_by ? item.done_by.firstName + ' ' + item.done_by.lastName : ''
													  					            };
													  					        });


								    // Create a worksheet from the formatted data status
								    var ws = XLSX.utils.json_to_sheet(formattedData);

								    // Create a workbook
								    var wb = XLSX.utils.book_new();
								    XLSX.utils.book_append_sheet(wb, ws, "Overdue ppm By Machine");

								    // Export to Excel
								    XLSX.writeFile(wb, "Overdue_PPMByMachine.xlsx");
										}


										
									}
						
									
									
					function totalMaintainceByMachineController($scope, ApiEndpoint, $state, $stateParams, genericFactory, $http, toastr) {
									    const baseUrl = ApiEndpoint.url;
									    let machineId = null;

									    var vm = angular.extend(this, {
									        filterComplaints: filterComplaints,
									        fetchtotalMaintainceByMachine: fetchtotalMaintainceByMachine,
									        exportToExcel: exportToExcel
									    });

									    vm.totalMaintainceByMachine = [];
									    vm.filteredComplaints = [];
									    vm.fromDate = null;
									    vm.toDate = null;

									    activate();

									    function activate() {
									        console.log("Activating totalMaintainceByMachineController");
									        machineId = $stateParams.machineId;
									        if (machineId) {
									            fetchtotalMaintainceByMachine(machineId);
									            filterComplaints();
									        }
									    }

									    function fetchtotalMaintainceByMachine(machineId) {
									        if (!machineId) return;

									        let url = `${baseUrl}/dashboard/maintaince_records/${machineId}`;
									        $http.get(url).then(response => {
									            vm.totalMaintainceByMachine = response.data;
									            vm.filteredComplaints = vm.totalMaintainceByMachine.totalMaintenanceRecords; // default open list
									            console.log("total PPM by machine:", vm.totalMaintainceByMachine);
									        }).catch(error => {
									            console.error("Error fetching total PPM:", error);
									        });
									    }

									    function filterComplaints() {
									        if (!machineId) {
									            console.warn("Please provide a machineId!");
									            return;
									        }

									        let url = `${baseUrl}/dashboard/maintaince_records/${machineId}`;
									        if (vm.fromDate && vm.toDate) {
									            const fromDate = formatDate(vm.fromDate);
									            const toDate = formatDate(vm.toDate);
									            url += `?fromDate=${fromDate}&toDate=${toDate}`;
									            console.log("Filtering with FromDate:", fromDate, "ToDate:", toDate);
									        }

									        $http.get(url).then(response => {
									            vm.breakdownCounts = response.data;
									            vm.filteredComplaints = vm.breakdownCounts.totalMaintenanceRecords; // default show overdue list
									            console.log("Filtered total PPM counts:", vm.breakdownCounts);
									        }).catch(error => {
									            console.error("Error filtering complaints:", error);
									        });
									    }
										
										$scope.getPPMStatus = function(maint) {
												  const scheduleDate = maint.schedule_date ? new Date(maint.schedule_date) : null;
												  const closedDate = maint.closedDate ? new Date(maint.closedDate) : null;
												  const approvalBit = parseInt(maint.approvalBit); // Ensure it's a number

												  if (maint.statusCode === 1 && approvalBit === 0) {
												    return 'Open PPM';
												  } else if (
												    maint.statusCode === 0 &&
												    (approvalBit === 0 || approvalBit === 2) &&
												    scheduleDate &&
												    closedDate &&
												    scheduleDate < closedDate
												  ) {
												    return 'OverdueClosed PPM';
												  } else if (
												    maint.statusCode === 0 &&
												    approvalBit === 0 &&
												    scheduleDate &&
												    closedDate &&
												    scheduleDate > closedDate
												  ) {
												    return 'OpenClosed PPM';
												  } else if (
												    maint.statusCode === 0 &&
												    approvalBit === 0 
												   
												  ) {
												    return 'Closed PPM';
												  } else if (maint.statusCode === 2 && approvalBit === 1) {
												    return 'Approved PPM';
												  } else if (maint.statusCode === 1 && approvalBit === 2) {
												    return 'Unapproved PPM';
												  } else {
												    return 'N/A';
												  }
												};

												

									    function formatDate(date) {
									        if (!date) return "";
									        var d = new Date(date);
									        var day = String(d.getDate()).padStart(2, "0");
									        var month = String(d.getMonth() + 1).padStart(2, "0");
									        var year = d.getFullYear();
									        return year + "-" + month + "-" + day;
									    }

									 

										function exportToExcel() {
																        if (!vm.filteredComplaints || vm.filteredComplaints.length === 0) {
																            // toastr.error("No data available for export!");
																            return;
																        }

																        var formattedData = vm.filteredComplaints.map(function(item, index) {
																            return {
																                "Sr No": index + 1,
																              "Machine Name": item.machine ? item.machine.machine_name + " " + item.machine.eqid : "",
																                "Lab Name": item.lab.labName,
																           
																                "Frequency": item.frequency,
																                "Overall Status": item.overall_status,
																				"Machine Location ": item.machine.location,
																                "Schedule Date": formatDate(item.schedule_date),
																				'Raised By': item.raisedBy ? item.raisedBy.firstName + ' ' + item.raisedBy.lastName : '',
																				'Done By': item.done_by ? item.done_by.firstName + ' ' + item.done_by.lastName : ''
																            };
																        });


									        var ws = XLSX.utils.json_to_sheet(formattedData);
									        var wb = XLSX.utils.book_new();
									        XLSX.utils.book_append_sheet(wb, ws, "Total PPM By Machine");
									        XLSX.writeFile(wb, "Total_PPMByMachine.xlsx");
									    }
									}
			
					function totalApprovedByMachineController($scope, ApiEndpoint, $state, $stateParams, genericFactory, $http, toastr) {
									    const baseUrl = ApiEndpoint.url;
									    let machineId = null;

									    var vm = angular.extend(this, {
									        filterComplaints: filterComplaints,
									        fetchtotalApprovedByMachine: fetchtotalApprovedByMachine,
									        exportToExcel: exportToExcel
									    });

									    vm.totalApprovedByMachine = [];
									    vm.filteredComplaints = [];
									    vm.fromDate = null;
									    vm.toDate = null;

									    activate();

									    function activate() {
									        console.log("Activating totalApprovedByMachineController");
									        machineId = $stateParams.machineId;
									        if (machineId) {
									            fetchtotalApprovedByMachine(machineId);
									            filterComplaints();
									        }
									    }

									    function fetchtotalApprovedByMachine(machineId) {
									        if (!machineId) return;

									        let url = `${baseUrl}/dashboard/maintaince_records/${machineId}`;
									        $http.get(url).then(response => {
									            vm.totalApprovedByMachine = response.data;
									            vm.filteredComplaints = vm.totalApprovedByMachine.totalApprovedRecords; // default open list
									            console.log("Approved PPM by machine:", vm.totalApprovedByMachine);
									        }).catch(error => {
									            console.error("Error fetching Approved PPM:", error);
									        });
									    }

									    function filterComplaints() {
									        if (!machineId) {
									            console.warn("Please provide a machineId!");
									            return;
									        }

									        let url = `${baseUrl}/dashboard/maintaince_records/${machineId}`;
									        if (vm.fromDate && vm.toDate) {
									            const fromDate = formatDate(vm.fromDate);
									            const toDate = formatDate(vm.toDate);
									            url += `?fromDate=${fromDate}&toDate=${toDate}`;
									            console.log("Filtering with FromDate:", fromDate, "ToDate:", toDate);
									        }

									        $http.get(url).then(response => {
									            vm.breakdownCounts = response.data;
									            vm.filteredComplaints = vm.breakdownCounts.totalApprovedRecords; // default show overdue list
									            console.log("Filtered Approved PPM counts:", vm.breakdownCounts);
									        }).catch(error => {
									            console.error("Error filtering complaints:", error);
									        });
									    }

									    function formatDate(date) {
									        if (!date) return "";
									        var d = new Date(date);
									        var day = String(d.getDate()).padStart(2, "0");
									        var month = String(d.getMonth() + 1).padStart(2, "0");
									        var year = d.getFullYear();
									        return year + "-" + month + "-" + day;
									    }

									    function getStatusText(status) {
									        switch (status) {
									            case 1: return "Open";
									            case 2: return "Trial";
									            case 3: return "Closed";
									            default: return "Unknown";
									        }
									    }

										function exportToExcel() {
																									        if (!vm.filteredComplaints || vm.filteredComplaints.length === 0) {
																									            // toastr.error("No data available for export!");
																									            return;
																									        }

																									        var formattedData = vm.filteredComplaints.map(function(item, index) {
																									            return {
																									                "Sr No": index + 1,
																									              "Machine Name": item.machine ? item.machine.machine_name + " " + item.machine.eqid : "",
																									                "Lab Name": item.lab.labName,
																									           
																									                "Frequency": item.frequency,
																									                "Overall Status": item.overall_status,
																													"Machine Location ": item.machine.location,
																									                "Schedule Date": formatDate(item.schedule_date),
																													"Approved Date": formatDate(item.closedApprovalDate),
																													'Raised By': item.raisedBy ? item.raisedBy.firstName + ' ' + item.raisedBy.lastName : '',
																													'Done By': item.done_by ? item.done_by.firstName + ' ' + item.done_by.lastName : '',
																													'Approved By': item.approvalBy ? item.approvalBy.firstName + ' ' + item.approvalBy.lastName : ''
																									            };
																									        });

									        var ws = XLSX.utils.json_to_sheet(formattedData);
									        var wb = XLSX.utils.book_new();
									        XLSX.utils.book_append_sheet(wb, ws, "Approved PPM By Machine");
									        XLSX.writeFile(wb, "Approved_PPMByMachine.xlsx");
									    }
									}
		
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
							
		// for breakdown
		function overallBreakdownByMachineController($scope, ApiEndpoint, $state, $stateParams, genericFactory, $http, toastr) {

								const baseUrl = ApiEndpoint.url;
								let machineId = null;  

								var vm = angular.extend(this, {
									filterComplaints:filterComplaints,
									fetchoverallBreakdownByMachine:fetchoverallBreakdownByMachine,
									exportToExcel:exportToExcel
								});
								vm.overallBreakdownByMachine = [];
								 vm.filteredComplaints = [];
									      vm.fromDate = null;
						   				 vm.toDate = null;

										 activate();

								function activate() {
									console.log("Activating overallBreakdownByMachineController");
									 machineId = $stateParams.machineId;
									       if (machineId) {
									           fetchoverallBreakdownByMachine(machineId);
											   filterComplaints();
									       }
								}

								function fetchoverallBreakdownByMachine(machineId) {
								      if (!machineId) return;

								      let url = `${baseUrl}/breakdown/report/${machineId}`;

								      $http.get(url).then(response => {
								          vm.overallBreakdownByMachine = response.data;
								          vm.filteredComplaints = vm.overallBreakdownByMachine.breakdowns; // default open list
								          console.log("Total  breakdowns by machine:", vm.overallBreakdownByMachine);
								      }).catch(error => {
								          console.error('Error fetching total  breakdowns:', error);
								      });
								  }
								
							
								  function filterComplaints() {
								        if (!machineId) {
								            console.warn("Please provide a machineId!");
								            return;
								        }

								        let url = `${baseUrl}/breakdown/report/${machineId}`;
								        if (vm.fromDate && vm.toDate) {
								            const fromDate = formatDate(vm.fromDate);
								            const toDate = formatDate(vm.toDate);
								            url += `?fromDate=${fromDate}&toDate=${toDate}`;
								            console.log("Filtering with FromDate:", fromDate, "ToDate:", toDate);
								        }

								        $http.get(url).then(response => {
								            vm.breakdownCounts = response.data;
								            vm.filteredComplaints = vm.breakdownCounts.breakdowns; // default show open list
											
								            console.log("Filtered Overall breakdown counts:", vm.breakdownCounts);
								        }).catch(error => {
								            console.error('Error filtering complaints:', error);
								        });
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
											
											
											  // Function to map status numbers to text
						    function getStatusText(status) {
						        switch (status) {
						            case 1: return 'Open';
						            case 2: return 'Trial';
						            case 3: return 'Closed';
						            default: return 'Unknown';
						        }
						    }

						


											
						function exportToExcel() {
						    if (!vm.filteredComplaints || vm.filteredComplaints.length === 0) {
						      //  toastr.error('No data available for export!');
						        return;
						    }

						    // Format the data for export
						    var formattedData = vm.filteredComplaints.map(function(item, index) {
						        return {
						            'Sr No': index + 1,
						              'BD Slip Number': item.breakdown.bd_slip, 
						                'Shift Name': item.breakdown.shift.shift_name, 
						            
						             'Machine Name': item.breakdown.machine ? item.breakdown.machine.machine_name + ' ' + item.breakdown.machine.eqid : '',
						               
						            'Complaint Cause': item.breakdown.observation,         
						            'Root Cause': item.breakdown.root_cause,   
						               'Action Taken': item.breakdown.action_taken,                 
						            
						            'Raised Date': formatDate(item.breakdown.ticket_raised_time) , 
						       
						             'Status': getStatusText(item.breakdown.status),  
						                  
						        };
						    });

						    // Create a worksheet from the formatted data status
						    var ws = XLSX.utils.json_to_sheet(formattedData);

						    // Create a workbook
						    var wb = XLSX.utils.book_new();
						    XLSX.utils.book_append_sheet(wb, ws, "Overall Breakdowns By Machine");

						    // Export to Excel
						    XLSX.writeFile(wb, "Overall_BreakdownsByMachine.xlsx");
								}


								
							}
				
		
		
		
		function totalRepeatBreakdownByMachineController($scope, ApiEndpoint, $state, $stateParams, genericFactory, $http, toastr) {

						const baseUrl = ApiEndpoint.url;
						let machineId = null;  

						var vm = angular.extend(this, {
							filterComplaints:filterComplaints,
							fetchtotalRepeatBreakdownByMachine:fetchtotalRepeatBreakdownByMachine,
							exportToExcel:exportToExcel
						});
						vm.totalRepeatBreakdownByMachine = [];
						 vm.filteredComplaints = [];
							      vm.fromDate = null;
				   				 vm.toDate = null;

								 activate();

						function activate() {
							console.log("Activating totalRepeatBreakdownByMachineController");
							 machineId = $stateParams.machineId;
							       if (machineId) {
							           fetchtotalRepeatBreakdownByMachine(machineId);
									   filterComplaints();
							       }
						}

						function fetchtotalRepeatBreakdownByMachine(machineId) {
						      if (!machineId) return;

						      let url = `${baseUrl}/analysis_time_frames/report/${machineId}`;

						      $http.get(url).then(response => {
						          vm.totalRepeatBreakdownByMachine = response.data;
						          vm.filteredComplaints = vm.totalRepeatBreakdownByMachine.breakdowns; // default open list
						          console.log("Total Repeat breakdowns by machine:", vm.totalRepeatBreakdownByMachine);
						      }).catch(error => {
						          console.error('Error fetching total Repeat breakdowns:', error);
						      });
						  }
						
					
						  function filterComplaints() {
						        if (!machineId) {
						            console.warn("Please provide a machineId!");
						            return;
						        }

						        let url = `${baseUrl}/analysis_time_frames/report/${machineId}`;
						        if (vm.fromDate && vm.toDate) {
						            const fromDate = formatDate(vm.fromDate);
						            const toDate = formatDate(vm.toDate);
						            url += `?fromDate=${fromDate}&toDate=${toDate}`;
						            console.log("Filtering with FromDate:", fromDate, "ToDate:", toDate);
						        }

						        $http.get(url).then(response => {
						            vm.breakdownCounts = response.data;
						            vm.filteredComplaints = vm.breakdownCounts.breakdowns; // default show open list
									
						            console.log("Filtered Repeat breakdown counts:", vm.breakdownCounts);
						        }).catch(error => {
						            console.error('Error filtering complaints:', error);
						        });
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
									
									
									  // Function to map status numbers to text
				    function getStatusText(status) {
				        switch (status) {
				            case 1: return 'Open';
				            case 2: return 'Trial';
				            case 3: return 'Closed';
				            default: return 'Unknown';
				        }
				    }

					function reInitDataTable() {
					    if ($.fn.DataTable.isDataTable('#example')) {
					        $('#example').DataTable().clear().destroy();
					    }
					    setTimeout(function() {
					        $('#example').DataTable({
					            paging: true,
					            searching: true,
					            ordering: true
					        });
					    }, 0);
					}


									
				function exportToExcel() {
				    if (!vm.filteredComplaints || vm.filteredComplaints.length === 0) {
				      //  toastr.error('No data available for export!');
				        return;
				    }

				    // Format the data for export
				    var formattedData = vm.filteredComplaints.map(function(item, index) {
				        return {
				            'Sr No': index + 1,
				              'BD Slip Number': item.bd_slip, 
				                'Shift Name': item.shift.shift_name, 
				            
				             'Machine Name': item.machine ? item.machine.machine_name + ' ' + item.machine.eqid : '',
				               
				            'Complaint Cause': item.observation,         
				            'Root Cause': item.root_cause,   
				               'Action Taken': item.action_taken,                 
				            
				            'Raised Date': formatDate(item.ticket_raised_time) , 
				       
				             'Status': getStatusText(item.status),  
				                  
				        };
				    });

				    // Create a worksheet from the formatted data status
				    var ws = XLSX.utils.json_to_sheet(formattedData);

				    // Create a workbook
				    var wb = XLSX.utils.book_new();
				    XLSX.utils.book_append_sheet(wb, ws, "Repeat Breakdowns By Machine");

				    // Export to Excel
				    XLSX.writeFile(wb, "OverallRepeat_BreakdownsByMachine.xlsx");
						}


						
					}
		


		function totalopenBreakdownByMachineController($scope, ApiEndpoint, $state, $stateParams, genericFactory, $http, toastr) {

				const baseUrl = ApiEndpoint.url;
				let machineId = null;  

				var vm = angular.extend(this, {
					filterComplaints:filterComplaints,
					fetchtotalopenBreakdownByMachine:fetchtotalopenBreakdownByMachine,
					exportToExcel:exportToExcel
				});
				vm.totalopenBreakdownByMachine = [];
				 vm.filteredComplaints = [];
					      vm.fromDate = null;
		   				 vm.toDate = null;

						 activate();

				function activate() {
					console.log("Activating totalopenBreakdownByMachineController");
					 machineId = $stateParams.machineId;
					       if (machineId) {
					           fetchtotalopenBreakdownByMachine(machineId);
							   filterComplaints();
					       }
				}

				function fetchtotalopenBreakdownByMachine(machineId) {
				      if (!machineId) return;

				      let url = `${baseUrl}/breakdown/Breakdowncounts/${machineId}`;

				      $http.get(url).then(response => {
				          vm.totalopenBreakdownByMachine = response.data;
				          vm.filteredComplaints = vm.totalopenBreakdownByMachine.openDetails; // default open list
				          console.log("Total open breakdowns by machine:", vm.totalopenBreakdownByMachine);
				      }).catch(error => {
				          console.error('Error fetching total open breakdowns:', error);
				      });
				  }
				
			
				  function filterComplaints() {
				        if (!machineId) {
				            console.warn("Please provide a machineId!");
				            return;
				        }

				        let url = `${baseUrl}/breakdown/BreakdowncountsFiler/${machineId}`;
				        if (vm.fromDate && vm.toDate) {
				            const fromDate = formatDate(vm.fromDate);
				            const toDate = formatDate(vm.toDate);
				            url += `?fromDate=${fromDate}&toDate=${toDate}`;
				            console.log("Filtering with FromDate:", fromDate, "ToDate:", toDate);
				        }

				        $http.get(url).then(response => {
				            vm.breakdownCounts = response.data;
				            vm.filteredComplaints = vm.breakdownCounts.openDetails; // default show open list
							
				            console.log("Filtered breakdown counts:", vm.breakdownCounts);
				        }).catch(error => {
				            console.error('Error filtering complaints:', error);
				        });
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
							
							
							  // Function to map status numbers to text
		    function getStatusText(status) {
		        switch (status) {
		            case 1: return 'Open';
		            case 2: return 'Trial';
		            case 3: return 'Closed';
		            default: return 'Unknown';
		        }
		    }

			function reInitDataTable() {
			    if ($.fn.DataTable.isDataTable('#example')) {
			        $('#example').DataTable().clear().destroy();
			    }
			    setTimeout(function() {
			        $('#example').DataTable({
			            paging: true,
			            searching: true,
			            ordering: true
			        });
			    }, 0);
			}


							
		function exportToExcel() {
		    if (!vm.filteredComplaints || vm.filteredComplaints.length === 0) {
		      //  toastr.error('No data available for export!');
		        return;
		    }

		    // Format the data for export
		    var formattedData = vm.filteredComplaints.map(function(item, index) {
		        return {
		            'Sr No': index + 1,
		              'BD Slip Number': item.bd_slip, 
		                'Shift Name': item.shift.shift_name, 
		            
		             'Machine Name': item.machine ? item.machine.machine_name + ' ' + item.machine.eqid : '',
		               
		            'Complaint Cause': item.observation,         
		            'Root Cause': item.root_cause,   
		               'Action Taken': item.action_taken,                 
		            
		            'Raised Date': formatDate(item.ticket_raised_time) , 
		       
		             'Status': getStatusText(item.status),  
		                  
		        };
		    });

		    // Create a worksheet from the formatted data status
		    var ws = XLSX.utils.json_to_sheet(formattedData);

		    // Create a workbook
		    var wb = XLSX.utils.book_new();
		    XLSX.utils.book_append_sheet(wb, ws, "Open Breakdowns By Machine");

		    // Export to Excel
		    XLSX.writeFile(wb, "OverallOpen_BreakdownsByMachine.xlsx");
				}


				
			}
			
			
			
			
			
			
			
			
			function totalclosedBreakdownByMachineController($scope, ApiEndpoint, $state, $stateParams, genericFactory, $http, toastr) {

							const baseUrl = ApiEndpoint.url;
							let machineId = null;  

							var vm = angular.extend(this, {
								filterComplaints:filterComplaints,
								totalclosedBreakdownByMachine:totalclosedBreakdownByMachine,
								exportToExcel:exportToExcel
							});
							vm.totalclosedBreakdownByMachine = [];
							 vm.filteredComplaints = [];
								      vm.fromDate = null;
					   				 vm.toDate = null;

									 activate();

							function activate() {
								console.log("Activating totalclosedBreakdownByMachineController");
								 machineId = $stateParams.machineId;
								       if (machineId) {
								           totalclosedBreakdownByMachine(machineId);
										   filterComplaints();
								       }
							}

							function totalclosedBreakdownByMachine(machineId) {
							      if (!machineId) return;

							      let url = `${baseUrl}/breakdown/Breakdowncounts/${machineId}`;

							      $http.get(url).then(response => {
							          vm.totalclosedBreakdownByMachine = response.data;
							          vm.filteredComplaints = vm.totalclosedBreakdownByMachine.closedDetails; // default open list
							          console.log("Total closed breakdowns by machine:", vm.totalclosedBreakdownByMachine);
							      }).catch(error => {
							          console.error('Error fetching total closed breakdowns:', error);
							      });
							  }
							
						
							  function filterComplaints() {
							        if (!machineId) {
							            console.warn("Please provide a machineId!");
							            return;
							        }

							        let url = `${baseUrl}/breakdown/BreakdowncountsFiler/${machineId}`;
							        if (vm.fromDate && vm.toDate) {
							            const fromDate = formatDate(vm.fromDate);
							            const toDate = formatDate(vm.toDate);
							            url += `?fromDate=${fromDate}&toDate=${toDate}`;
							            console.log("Filtering with FromDate:", fromDate, "ToDate:", toDate);
							        }

							        $http.get(url).then(response => {
							            vm.breakdownCounts = response.data;
							            vm.filteredComplaints = vm.breakdownCounts.closedDetails; // default show open list
										
							            console.log("Filtered breakdown counts:", vm.breakdownCounts);
							        }).catch(error => {
							            console.error('Error filtering complaints:', error);
							        });
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
										
										
										  // Function to map status numbers to text
					    function getStatusText(status) {
					        switch (status) {
					            case 1: return 'Open';
					            case 2: return 'Trial';
					            case 3: return 'Closed';
					            default: return 'Unknown';
					        }
					    }

				

										
					function exportToExcel() {
					    if (!vm.filteredComplaints || vm.filteredComplaints.length === 0) {
					      //  toastr.error('No data available for export!');
					        return;
					    }

					    // Format the data for export
					    var formattedData = vm.filteredComplaints.map(function(item, index) {
					        return {
					            'Sr No': index + 1,
					              'BD Slip Number': item.bd_slip, 
					                'Shift Name': item.shift.shift_name, 
					            
					             'Machine Name': item.machine ? item.machine.machine_name + ' ' + item.machine.eqid : '',
					               
					            'Complaint Cause': item.observation,         
					            'Root Cause': item.root_cause,   
					               'Action Taken': item.action_taken,                 
					            
					            'Raised Date': formatDate(item.ticket_raised_time) , 
					       
					             'Status': getStatusText(item.status),  
					                  
					        };
					    });

					    // Create a worksheet from the formatted data status
					    var ws = XLSX.utils.json_to_sheet(formattedData);

					    // Create a workbook
					    var wb = XLSX.utils.book_new();
					    XLSX.utils.book_append_sheet(wb, ws, "Closed Breakdowns By Machine");

					    // Export to Excel
					    XLSX.writeFile(wb, "OverallClosed_BreakdownsByMachine.xlsx");
							}


							
						}



								
								function totaltrialBreakdownByMachineController($scope, ApiEndpoint, $state, $stateParams, genericFactory, $http, toastr) {

												const baseUrl = ApiEndpoint.url;
												let machineId = null;  

												var vm = angular.extend(this, {
													filterComplaints:filterComplaints,
													totaltrialBreakdownByMachine:totaltrialBreakdownByMachine,
													exportToExcel:exportToExcel
												});
												vm.totaltrialBreakdownByMachine = [];
												 vm.filteredComplaints = [];
													      vm.fromDate = null;
										   				 vm.toDate = null;

														 activate();

												function activate() {
													console.log("Activating totaltrialBreakdownByMachineController");
													 machineId = $stateParams.machineId;
													       if (machineId) {
													           totaltrialBreakdownByMachine(machineId);
															   filterComplaints();
													       }
												}

												function totaltrialBreakdownByMachine(machineId) {
												      if (!machineId) return;

												      let url = `${baseUrl}/breakdown/Breakdowncounts/${machineId}`;

												      $http.get(url).then(response => {
												          vm.totaltrialBreakdownByMachine = response.data;
												          vm.filteredComplaints = vm.totaltrialBreakdownByMachine.trialDetails; // default open list
												          console.log("Total Trial breakdowns by machine:", vm.totaltrialBreakdownByMachine);
												      }).catch(error => {
												          console.error('Error fetching total Trial breakdowns:', error);
												      });
												  }
												
											
												  function filterComplaints() {
												        if (!machineId) {
												            console.warn("Please provide a machineId!");
												            return;
												        }

												        let url = `${baseUrl}/breakdown/BreakdowncountsFiler/${machineId}`;
												        if (vm.fromDate && vm.toDate) {
												            const fromDate = formatDate(vm.fromDate);
												            const toDate = formatDate(vm.toDate);
												            url += `?fromDate=${fromDate}&toDate=${toDate}`;
												            console.log("Filtering with FromDate:", fromDate, "ToDate:", toDate);
												        }

												        $http.get(url).then(response => {
												            vm.breakdownCounts = response.data;
												            vm.filteredComplaints = vm.breakdownCounts.trialDetails; // default show open list
															
												            console.log("Filtered breakdown counts:", vm.breakdownCounts);
												        }).catch(error => {
												            console.error('Error filtering complaints:', error);
												        });
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
															
															
															  // Function to map status numbers to text
										    function getStatusText(status) {
										        switch (status) {
										            case 1: return 'Open';
										            case 2: return 'Trial';
										            case 3: return 'Closed';
										            default: return 'Unknown';
										        }
										    }

									

															
										function exportToExcel() {
										    if (!vm.filteredComplaints || vm.filteredComplaints.length === 0) {
										      //  toastr.error('No data available for export!');
										        return;
										    }

										    // Format the data for export
										    var formattedData = vm.filteredComplaints.map(function(item, index) {
										        return {
										            'Sr No': index + 1,
										              'BD Slip Number': item.bd_slip, 
										                'Shift Name': item.shift.shift_name, 
										            
										             'Machine Name': item.machine ? item.machine.machine_name + ' ' + item.machine.eqid : '',
										               
										            'Complaint Cause': item.observation,         
										            'Root Cause': item.root_cause,   
										               'Action Taken': item.action_taken,                 
										            
										            'Raised Date': formatDate(item.ticket_raised_time) , 
										       
										             'Status': getStatusText(item.status),  
										                  
										        };
										    });

										    // Create a worksheet from the formatted data status
										    var ws = XLSX.utils.json_to_sheet(formattedData);

										    // Create a workbook
										    var wb = XLSX.utils.book_new();
										    XLSX.utils.book_append_sheet(wb, ws, "Trial Breakdowns By Machine");

										    // Export to Excel
										    XLSX.writeFile(wb, "OverallTrial_BreakdownsByMachine.xlsx");
												}


												
											}

		
		
		
		
		

	function HomeController($scope, ApiEndpoint, $state, genericFactory, $http, localStorageService, $location, $rootScope) {

		const baseUrl = ApiEndpoint.url;

		//<<<<<<< HEAD

		var vm = angular.extend(this, {
			TotalCount:TotalCount,
			MaintainceCount:MaintainceCount,
			BreakdownCount:BreakdownCount,
			BreakCount:BreakCount
		});
		vm.selectedMachineCount = 5;
		vm.selectedMachineCountBreakdown = 5;
		vm.selectedMachineOwnerCount = 5;
		vm.selectedMachineOwnerCountMaintenance = 5;
		//=======
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var deviceUrl = staticUrl + "/device";
		var energyUrl = staticUrl + "/energy";
		var vm = angular.extend(this, {
			user: userDetail
		});
		vm.selectedMachineCount = 10; // Default value


		(function activate() {
			console.log("Muser  : " + JSON.stringify(vm.user));


		})();

		$scope.goToPPMDashboard = function() {
		
			$location.path('main/PPMDashboard');
			$rootScope.currentDashboard = "PPM"
				console.log("locationWiseConsupmtons : "+	$rootScope.currentDashboard )
			$rootScope.$apply()
		}
		$scope.goToBreakDownDashboard = function() {
			console.log("locationWiseConsupmtons : ")
			$location.path('main/BreakDownDashboard');
			$rootScope.currentDashboard = "BreakDown"
		}
		$scope.goToConsumptionDashboard = function() {
			console.log("locationWiseConsupmtons : ")
			$location.path('main/ConsumptionDashboard');
			$rootScope.currentDashboard = "EMS"
		}
		$scope.goToMeter = function(meterName) {
			console.log("locationWiseConsupmtons : " + JSON.stringify(meterName))
			$location.path('main/MeterDetials/' + meterName.meterId);
		}

		function loadEnergyMeterMater() {
			var msg = ""
			var url = energyUrl + "/getAllEnergyMeterMasters";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.locationWiseConsupmtons = response.data;
				console.log("locationWiseConsupmtons : " + JSON.stringify(vm.locationWiseConsupmtons))

			});

		}
		// Existing handle card click functions
		$scope.handleMachinesCardClick = function(cardType) {
			switch (cardType) {
				case 'totalMachines':
					$state.go('main.home.totalMachines');
					break;
				default:
					vm.totalMachines = [];
			}
		};

$scope.handleUsersCardClickover = function(cardType) {
			switch (cardType) {
				case 'overallBreakdown':
					$state.go('main.home.overallBreakdown');
					break;
				default:
					vm.overallBreakdown = [];
			}
		};

$scope.handleUsersCardClickopen = function(cardType) {
			switch (cardType) {
				case 'totalopenBreakdown':
					$state.go('main.home.totalopenBreakdown');
					break;
				default:
					vm.totalopenBreakdown = [];
			}
		};

$scope.handleUsersCardClicktrial = function(cardType) {
			switch (cardType) {
				case 'totaltrialBreakdown':
					$state.go('main.home.totaltrialBreakdown');
					break;
				default:
					vm.totaltrialBreakdown = [];
			}
		};

$scope.handleUsersCardClickclosed = function(cardType) {
			switch (cardType) {
				case 'totalclosedBreakdown':
					$state.go('main.home.totalclosedBreakdown');
					break;
				default:
					vm.totalclosedBreakdown = [];
			}
		};


		$scope.handleBreakdownCardClick = function(cardType) {
			switch (cardType) {
				case 'totalBreakdown':
					$state.go('main.home.totalBreakdown');
					break;
				default:
					vm.totalBreakdown = [];
			}
		};

		$scope.handleOpenBreakdownCardClick = function(cardType) {
			switch (cardType) {
				case 'openBreakdown':
					$state.go('main.home.openBreakdown');
					break;
				default:
					vm.openBreakdown = [];
			}
		};

		$scope.handleTrialBreakdownCardClick = function(cardType) {
			switch (cardType) {
				case 'trial':
					$state.go('main.trial');
					break;
				default:
					vm.trialBreakdown = [];
			}
		};

		$scope.handleClosedBreakdownCardClick = function(cardType) {
			switch (cardType) {
				case 'closedBreakdown':
					$state.go('main.home.closedBreakdown');
					break;
				default:
					vm.closedBreakdown = [];
			}
		};

		$scope.handleMaintainceCardClick = function(cardType) {
			switch (cardType) {
				case 'totalMaintaince':
					$state.go('main.home.totalMaintaince');
					break;
				default:
					vm.totalMaintaince = [];
			}
		};
			
		
		$scope.handleApprovedCardClick = function(cardType) {
					switch (cardType) {
						case 'totalApproved':
							$state.go('main.home.totalApproved');
							break;
						default:
							vm.totalApproved = [];
					}
				};

				
				$scope.handleUnApprovedCardClick = function(cardType) {
									switch (cardType) {
										case 'totalUnApproved':
											$state.go('main.home.totalUnApproved');
											break;
										default:
											vm.totalUnApproved = [];
									}
								};

		
		$scope.openMaintainceCardClick = function(cardType) {
			switch (cardType) {
				case 'openMaintaince':
					$state.go('main.home.openMaintaince');
					break;
				default:
					vm.openMaintaince = [];
			}
		};
		
		$scope.totalopenMaintainceCardClick = function(cardType) {
			switch (cardType) {
				case 'totalopenMaintaince':
					$state.go('main.home.totalopenMaintaince');
					break;
				default:
					vm.totalopenMaintaince = [];
			}
		};
		
		
		$scope.totalclosedMaintainceCardClick = function(cardType) {
			switch (cardType) {
				case 'totalclosedMaintaince':
					$state.go('main.home.totalclosedMaintaince');
					break;
				default:
					vm.totalclosedMaintaince = [];
			}
		};

		$scope.closedMaintainceCardClick = function(cardType) {
			switch (cardType) {
				case 'closedMaintaince':
					$state.go('main.home.closedMaintaince');
					break;
				default:
					vm.closedMaintaince = [];
			}
		};

		$scope.TodayMaintainceCardClick = function(cardType) {
			switch (cardType) {
				case 'TodayMaintaince':
					$state.go('main.home.TodayMaintaince');
					break;
				default:
					vm.TodayMaintaince = [];
			}
		};

		function loadMaintenanceCountsByMachine() {
			var msg = "";
			var url = baseUrl + "/dashboard/getMaintenanceCountsByMachine";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.maintenanceCountsByMachine = response.data;
				console.log("Maintenance Counts By Machine: " + JSON.stringify(vm.maintenanceCountsByMachine));
				vm.updateMachineData();
			});
		}

		function updateMaintenanceChart() {
			var machineNames = [];
			var openCounts = [];
			var closedCounts = [];
			angular.forEach(vm.filteredMaintenanceCountsByMachine, function(item) {
				machineNames.push(item.machineName);
				openCounts.push(item.open);
				closedCounts.push(item.closed);
			});
			vm.chartData = [openCounts, closedCounts];
			vm.chartLabels = machineNames;

			vm.chartOptions = {
				scales: {
					yAxes: [{
						stacked: true,
						ticks: {
							beginAtZero: true
						},
						gridLines: {
							color: "rgba(0, 0, 0, 0)"
						}
					}],
					xAxes: [{
						stacked: true,
						gridLines: {
							color: "rgba(0, 0, 0, 0)"
						}
					}]
				},
				legend: {
					display: true,
					labels: {
						filter: function(legendItem, chartData) {
							return legendItem.datasetIndex === 0 || legendItem.datasetIndex === 1;
						},
						usePointStyle: true
					}
				}
			};

			vm.chartColors = [{
				backgroundColor: 'rgba(0, 139, 139, 0.5)',
				borderColor: 'rgba(0, 139, 139, 1)',
			}, {
				backgroundColor: 'rgba(105, 105, 105, 0.2)',
				borderColor: 'rgba(105, 105, 105, 0.5)',
			}];
		}

		function loadBreakdownCountsByMachine() {
			var msg = "";
			var url = baseUrl + "/dashboard/getBreakdownCountsByMachine";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.BreakdownCountsByMachine = response.data;
				console.log("Breakdown Counts By Machine: " + JSON.stringify(vm.BreakdownCountsByMachine));
				vm.updateMachineData2();
			});
		}

		function updateBreakdownChart() {
			var machineNames = [];
			var openCounts = [];
			var closedCounts = [];
			var trialCounts = [];
			angular.forEach(vm.filteredBreakdownCountsByMachine, function(item) {
				machineNames.push(item.machineName);
				openCounts.push(item.open);
				closedCounts.push(item.closed);
				trialCounts.push(item.trail);
			});
			vm.chartData1 = [openCounts, closedCounts, trialCounts];
			vm.chartLabels1 = machineNames;

			vm.chartOptions1 = {
				scales: {
					yAxes: [{
						stacked: true,
						ticks: {
							beginAtZero: true
						},
						gridLines: {
							color: "rgba(0, 0, 0, 0)"
						}
					}],
					xAxes: [{
						stacked: true,
						gridLines: {
							color: "rgba(0, 0, 0, 0)"
						}
					}]
				},
				legend: {
					display: true,
					labels: {
						filter: function(legendItem, chartData) {
							return legendItem.datasetIndex === 0 || legendItem.datasetIndex === 1 || legendItem.datasetIndex === 2;
						},
						usePointStyle: true
					}
				}
			};

			vm.chartColors1 = [{
				backgroundColor: 'rgba(0, 139, 139, 0.5)',
				borderColor: 'rgba(0, 139, 139, 1)',
			}, {
				backgroundColor: 'rgba(46, 139, 87, 0.35)',
				borderColor: 'rgba(46, 139, 87, 0.8)',
				
			}, {
				backgroundColor: 'rgba(105, 105, 105, 0.2)',
				borderColor: 'rgba(105, 105, 105, 0.5)',
			}];

		}

		function loadgetBreakdownGraphData() {
			var msg = "";
			var url = baseUrl + "/dashboard/getBreakdownGraphData";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.BreakdownCountsByMachineOwner = response.data;
				console.log("Breakdown Counts By Machine Owner: " + JSON.stringify(vm.BreakdownCountsByMachineOwner));
				vm.updateMachineData3();
			});
		}

		function updategetBreakdownGraphDataChart() {
			var machineOwners = [];
			var openCounts = [];
			var closedCounts = [];
			var trialCounts = [];

			angular.forEach(vm.filteredBreakdownCountsByMachineOwner, function(item) {
				machineOwners.push(item.user.firstName);
				openCounts.push(item.open);
				closedCounts.push(item.closed);
				trialCounts.push(item.trail);
			});

			vm.chartData2 = [openCounts, closedCounts, trialCounts];
			vm.chartLabels2 = machineOwners;

			vm.chartOptions2 = {
				scales: {
					yAxes: [{
						stacked: true,
						ticks: {
							beginAtZero: true
						},
						gridLines: {
							color: "rgba(0, 0, 0, 0)"
						}
					}],
					xAxes: [{
						stacked: true,
						gridLines: {
							color: "rgba(0, 0, 0, 0)"
						}
					}]
				},
				legend: {
					display: true,
					labels: {
						filter: function(legendItem, chartData) {
							return legendItem.datasetIndex === 0 || legendItem.datasetIndex === 1 || legendItem.datasetIndex === 2;
						},
						usePointStyle: true
					}
				}
			};

			vm.chartColors2 = [{
				backgroundColor: 'rgba(0, 139, 139, 0.5)',
				borderColor: 'rgba(0, 139, 139, 1)',
			}, {
				backgroundColor: 'rgba(46, 139, 87, 0.35)',
				borderColor: 'rgba(46, 139, 87, 0.8)',
				
			}, {
				backgroundColor: 'rgba(105, 105, 105, 0.2)',
				borderColor: 'rgba(105, 105, 105, 0.5)',
			}];
		}


		function loadgetMaintenanceGraphData() {
			var msg = "";
			var url = baseUrl + "/dashboard/getMaintenanceGraphData";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.MaintainceCountsByMachineOwner = response.data;
				console.log("Maintenance Counts By Machine Owner: " + JSON.stringify(vm.MaintainceCountsByMachineOwner));
				vm.updateMachineData4();
			});
		}

		function updategetMaintenanceGraphDataChart() {
			var machineOwners = [];
			var openCounts = [];
			var closedCounts = [];

			angular.forEach(vm.filteredMaintainceCountsByMachineOwner, function(item) {
				machineOwners.push(item.user.firstName);
				openCounts.push(item.open);
				closedCounts.push(item.closed);
			});

			vm.chartData3 = [openCounts, closedCounts];
			vm.chartLabels3 = machineOwners;

			vm.chartOptions3 = {
				scales: {
					yAxes: [{
						stacked: true,
						ticks: {
							beginAtZero: true
						},
						gridLines: {
							color: "rgba(0, 0, 0, 0)"
						}
					}],
					xAxes: [{
						stacked: true,
						gridLines: {
							color: "rgba(0, 0, 0, 0)"
						}
					}]
				},
				legend: {
					display: true,
					labels: {
						filter: function(legendItem, chartData) {
							if (vm.openChecked && vm.closedChecked) {
								return legendItem.datasetIndex === 0 && legendItem.datasetIndex === 1; // Display both datasets if both checkboxes are checked
							}
							return legendItem.datasetIndex === 0 || legendItem.datasetIndex === 1;
						},
						usePointStyle: true
					}
				}
			};

			vm.chartColors3 = [{
				backgroundColor: 'rgba(0, 139, 139, 0.5)',
				borderColor: 'rgba(0, 139, 139, 1)',
			}, {
				backgroundColor: 'rgba(105, 105, 105, 0.2)',
				borderColor: 'rgba(105, 105, 105, 0.5)',
			}];
		}

		function TotalCount() {
			var msg = "";
			var url = baseUrl + "/dashboard/total_count";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.TotalCount = response.data;
				console.log("TotalCount" + JSON.stringify(vm.TotalCount));
			});
		}
		
		
			function BreakCount() {
			var msg = "";
			var url = baseUrl + "/breakdown/breakdownstatusCounts";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.BreakCount = response.data;
				console.log("BreakCount" + JSON.stringify(vm.BreakCount));
			});
		}

		function BreakdownCount() {
			var msg = "";
			var url = baseUrl + "/dashboard/breakdown_count";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.BreakdownCount = response.data;
				console.log("BreakdownCount" + JSON.stringify(vm.BreakdownCount));
			});
		}

		function MaintainceCount() {
			var msg = "";
			var url = baseUrl + "/dashboard/maintaince_count";
			genericFactory.getAll(msg, url).then(function(response) {
				vm.MaintainceCount = response.data;
				console.log("MaintainceCount" + JSON.stringify(vm.MaintainceCount));
			});
		}

		vm.updateMachineData = function() {
			vm.filteredMaintenanceCountsByMachine = filterTopMachines(vm.maintenanceCountsByMachine, vm.selectedMachineCount);
			updateMaintenanceChart();


		};

		vm.updateMachineData2 = function() {


			vm.filteredBreakdownCountsByMachine = filterTopMachines2(vm.BreakdownCountsByMachine, vm.selectedMachineCountBreakdown);
			updateBreakdownChart();
		};

		vm.updateMachineData3 = function() {


			vm.filteredBreakdownCountsByMachineOwner = filterTopMachines2(vm.BreakdownCountsByMachineOwner, vm.selectedMachineOwnerCount);
			updategetBreakdownGraphDataChart();
		};

		vm.updateMachineData4 = function() {


			vm.filteredMaintainceCountsByMachineOwner = filterTopMachines(vm.MaintainceCountsByMachineOwner, vm.selectedMachineOwnerCountMaintenance);
			updategetMaintenanceGraphDataChart();
		};



		function filterTopMachines(data, count) {
			return data.sort((a, b) => (b.open + b.closed) - (a.open + a.closed)).slice(0, count);
		}

		function filterTopMachines2(data, count) {

			// console.log('Original Data:', JSON.stringify(data, null, 2));
			const sortedData = data.slice().sort((a, b) => {
				const aTotal = a.open + a.closed + a.trail;
				const bTotal = b.open + b.closed + b.trail;
				return bTotal - aTotal;
			});
			// console.log('Sorted Data:', JSON.stringify(sortedData, null, 2));

			const filteredData = sortedData.slice(0, count);

			//   console.log('Filtered Data:', JSON.stringify(filteredData, null, 2));

			return filteredData;
		}

	}


	function totalMachinesController($scope, ApiEndpoint, $state, genericFactory, $http) {

		const baseUrl = ApiEndpoint.url;

		var vm = angular.extend(this, {
			exportToExcel:exportToExcel
		});
		vm.totalMachines = [];

		activate();

		function activate() {
			console.log("Activating totalMachinesController");
			fetchtotalMachines();
		}

		vm.labels =			 { 'srNo': 'Sr No', 'machine_name': 'Machine Name','eqid': 'Machine Id', 'location': 'location',
									'make': 'Make', 'model': 'model','capacity': 'Capacity', 'category.cat_name': 'Category' 
									 }
		$scope.export = function() {
			console.log(" totalMachines:", vm.totalMachines.length);
			setTimeout(() => {
				document.getElementById('btnExport').click();
			}, "1000");
		
		}

		function fetchtotalMachines() {
			const url = `${baseUrl}/machine_mst/list`;
			genericFactory.getAll("", url).then(response => {
				vm.totalMachines = response.data;
				console.log(" totalMachines:", vm.totalMachines);
			}).catch(error => {
				console.error('Error fetching   totalMachines:', error);
			});
		}
		
		 function exportToExcel(fromDate, toDate) {
				    const url = `${baseUrl}/machine_mst/export/list`;
				
				   
				    const params = {};
				
				  
				    if (fromDate && toDate) {
				        params.fromDate = formatDate(fromDate);  // Pass 'fromDate'
				        params.toDate = formatDate(toDate);      // Pass 'toDate'
				    } else {
				        
				    }
				
				    console.log("Exporting with params:", params);
				
				    $http({
				        method: "GET",
				        url: url,
				        params: params,
				        responseType: "arraybuffer"
				    })
				    .then(function (response) {
				        // Create a Blob from the response data
				        var blob = new Blob([response.data], {
				            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
				        });
				
				        // Create a temporary download link and trigger download
				        var link = document.createElement("a");
				        link.href = window.URL.createObjectURL(blob);
				
				        // Set download file name based on whether dates are specified
				        link.download = (fromDate && toDate) ? "Datewise_All_breakdown.xlsx" : "Total_All_breakdown.xlsx";
				
				        // Trigger the download
				        link.click();
				    })
				    .catch(function (error) {
				        console.error("Error exporting Excel:", error);
				    });
				}


		
		
	}



	function overallBreakdownController($scope, ApiEndpoint, $state, genericFactory, $http) {

		const baseUrl = ApiEndpoint.url;

		var vm = angular.extend(this, {
			filterComplaints:filterComplaints,
			exportToExcel:exportToExcel,
			getMaintImages:getMaintImages,
							
								
		});
		vm.overallBreakdown = [];
		 vm.filteredComplaints = [];
			      vm.fromDate = null;
   				 vm.toDate = null;

		activate();

		function activate() {
			console.log("Activating overallBreakdownController");
			fetchoverallBreakdown();
		}

		//////////
		
		vm.selectedMaintId = null;
				
				   

				   function getMaintImages(breakdownId) {
				       $http.get(baseUrl + "/breakdown/getImages/" + breakdownId)
				           .then(function(response) {
				               vm.selectedMaintImages = response.data;
				               vm.showImagesModal = true;
				               vm.selectedMaintIdForImages = breakdownId;
				           })
				           .catch(function(error) {
				               console.error("Error fetching images:", error);
				               alert("Could not load images.");
				           });
				   }

				   vm.getMaintImages = getMaintImages;

				   vm.downloadMaintImage = function(imageId) {
				       const downloadUrl = baseUrl + "/breakdown/downloadImage?imageId=" + imageId;

				       fetch(downloadUrl, {
				           method: 'GET',
				           headers: {
				               // only if JWT is required
				               'Authorization': 'Bearer ' + localStorage.getItem("token")
				           }
				       })
				       .then(response => {
				           if (!response.ok) {
				               throw new Error("Download failed: " + response.statusText);
				           }

				           // Extract filename from Content-Disposition
				           const disposition = response.headers.get("Content-Disposition");
				           let fileName = "downloaded_file";
				           if (disposition && disposition.indexOf("filename=") !== -1) {
				               fileName = disposition.split("filename=")[1].replace(/"/g, '');
				           }

				           return response.blob().then(blob => ({ blob, fileName }));
				       })
				       .then(({ blob, fileName }) => {
				           const url = window.URL.createObjectURL(blob);
				           const link = document.createElement('a');
				           link.href = url;
				           link.download = fileName;  // correct filename
				           document.body.appendChild(link);
				           link.click();
				           document.body.removeChild(link);
				           window.URL.revokeObjectURL(url);
				       })
				       .catch(error => {
				           console.error("Error downloading image:", error);
				           alert("Download failed!");
				       });
				   };

				 
				
				
		
		
		///////////
		function fetchoverallBreakdown() {
			const url = `${baseUrl}/breakdown/all`;
			genericFactory.getAll("", url).then(response => {
				vm.overallBreakdown = response.data;
				  vm.filteredComplaints = vm.overallBreakdown;
				console.log(" overallBreakdown:", vm.overallBreakdown);
			}).catch(error => {
				console.error('Error fetching   overallBreakdown:', error);
			});
		}
		
	
		
			function filterComplaints(){
			  
			        if (vm.fromDate && vm.toDate) {
			            const fromDate = formatDate(vm.fromDate);
			            const toDate = formatDate(vm.toDate);
			            
			            console.log("Formatted From Date:", fromDate);
			            console.log("Formatted To Date:", toDate);
			            
			            const url = `${baseUrl}/breakdown/datewise/all?fromDate=${fromDate}&toDate=${toDate}`;
			            $http.get(url).then(function(response) {
			                const filteredData = response.data;
			                vm.filteredComplaints = filteredData;
			                console.log("Filtered complaints:", vm.filteredComplaints);
			            }).catch(function(error) {
			                console.error('Error filtering complaints:', error);
			            });
			        } else {
			            vm.filteredComplaints = vm.overallBreakdown;
			            console.log("Showing all complaints data:", vm.filteredComplaints);
			        }
			    };
			
			    
			    
			/*       function exportToExcel(fromDate, toDate) {
				    const url = `${baseUrl}/breakdown/export/Allbreakdowns`;
				
				   
				    const params = {};
				
				  
				    if (fromDate && toDate) {
				        params.fromDate = formatDate(fromDate);  // Pass 'fromDate'
				        params.toDate = formatDate(toDate);      // Pass 'toDate'
				    } else {
				        
				    }
				
				    console.log("Exporting with params:", params);
				
				    $http({
				        method: "GET",
				        url: url,
				        params: params,
				        responseType: "arraybuffer"
				    })
				    .then(function (response) {
				        // Create a Blob from the response data
				        var blob = new Blob([response.data], {
				            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
				        });
				
				        // Create a temporary download link and trigger download
				        var link = document.createElement("a");
				        link.href = window.URL.createObjectURL(blob);
				
				        // Set download file name based on whether dates are specified
				        link.download = (fromDate && toDate) ? "Datewise_All_breakdown.xlsx" : "Total_All_breakdown.xlsx";
				
				        // Trigger the download
				        link.click();
				    })
				    .catch(function (error) {
				        console.error("Error exporting Excel:", error);
				    });
				}
				*/
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

					
function exportToExcel() {
    if (!vm.filteredComplaints || vm.filteredComplaints.length === 0) {
        toastr.error('No data available for export!');
        return;
    }

    // Format the data for export
    var formattedData = vm.filteredComplaints.map(function(item, index) {
        return {
            'Sr No': index + 1,
              'BD Slip Number': item.bd_slip, 
                'Shift Name': item.shift.shift_name, 
            
             'Machine Name': item.machine ? item.machine.machine_name + ' ' + item.machine.eqid : '',
               
            'Complaint Cause': item.observation,         
            'Root Cause': item.root_cause,   
               'Action Taken': item.action_taken,                 
            
            'Raised Date': formatDate(item.ticket_raised_time) , 
            'Trial Date': item.ticket_trial_time ? formatDate(item.ticket_trial_time) : "N/A",
            'Closed Date': item.ticket_closed_time ? formatDate(item.ticket_closed_time) : "N/A"
                  
        };
    });

    // Create a worksheet from the formatted data
    var ws = XLSX.utils.json_to_sheet(formattedData);

    // Create a workbook
    var wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "Overall Breakdowns");

    // Export to Excel
    XLSX.writeFile(wb, "OverallBreakdowns.xlsx");
}

        
        




		
		
	}

	function totalopenBreakdownController($scope, ApiEndpoint, $state, genericFactory, $http) {

		const baseUrl = ApiEndpoint.url;

		var vm = angular.extend(this, {
			filterComplaints:filterComplaints,
			exportToExcel:exportToExcel
		});
		vm.totalopenBreakdown = [];
		 vm.filteredComplaints = [];
			      vm.fromDate = null;
   				 vm.toDate = null;

		activate();

		function activate() {
			console.log("Activating totalopenBreakdownController");
			fetchtotalopenBreakdown();
		}

		function fetchtotalopenBreakdown() {
			const url = `${baseUrl}/breakdown/open`;
			genericFactory.getAll("", url).then(response => {
				vm.totalopenBreakdown = response.data;
				  vm.filteredComplaints = vm.totalopenBreakdown;
				console.log(" totalopenBreakdown:", vm.totalopenBreakdown);
			}).catch(error => {
				console.error('Error fetching   totalopenBreakdown:', error);
			});
		}
		
		/*vm.labels = { 'srNo': 'Sr No', 'bd_slip': 'SlipNumber', 'machine.shift.shift_name': 'Shift Name', 'machine.machine_name': 'Machine Name','machine.eqid': 'Machine Id', 'root_cause': 'root Cause','observation': 'Complaint Cause', 'action_taken': 'Action Taken', 'ticket_raised_time': 'Raised Date' }
		$scope.export = function() {
			console.log(" totalopenBreakdown:", vm.totalopenBreakdown.length);
			setTimeout(() => {
				document.getElementById('btnExport').click();
			}, "1000");
		
		}*/
		
		function filterComplaints(){
			  
			        if (vm.fromDate && vm.toDate) {
			            const fromDate = formatDate(vm.fromDate);
			            const toDate = formatDate(vm.toDate);
			            
			            console.log("Formatted From Date:", fromDate);
			            console.log("Formatted To Date:", toDate);
			            
			            const url = `${baseUrl}/breakdown/datewise/open?fromDate=${fromDate}&toDate=${toDate}`;
			            $http.get(url).then(function(response) {
			                const filteredData = response.data;
			                vm.filteredComplaints = filteredData;
			                console.log("Filtered complaints:", vm.filteredComplaints);
			            }).catch(function(error) {
			                console.error('Error filtering complaints:', error);
			            });
			        } else {
			            vm.filteredComplaints = vm.totalopenBreakdown;
			            console.log("Showing all complaints data:", vm.filteredComplaints);
			        }
			    };
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
					
					
					  // Function to map status numbers to text
    function getStatusText(status) {
        switch (status) {
            case 1: return 'Open';
            case 2: return 'Trial';
            case 3: return 'Closed';
            default: return 'Unknown';
        }
    }


					
function exportToExcel() {
    if (!vm.filteredComplaints || vm.filteredComplaints.length === 0) {
        toastr.error('No data available for export!');
        return;
    }

    // Format the data for export
    var formattedData = vm.filteredComplaints.map(function(item, index) {
        return {
            'Sr No': index + 1,
              'BD Slip Number': item.bd_slip, 
                'Shift Name': item.shift.shift_name, 
            
             'Machine Name': item.machine ? item.machine.machine_name + ' ' + item.machine.eqid : '',
               
            'Complaint Cause': item.observation,         
            'Root Cause': item.root_cause,   
               'Action Taken': item.action_taken,                 
            
            'Raised Date': formatDate(item.ticket_raised_time) , 
          //  'Trial Date': item.ticket_trial_time ? formatDate(item.ticket_trial_time) : "N/A",
            //  'Closed Date': item.ticket_closed_time ? formatDate(item.ticket_closed_time) : "N/A",
             'Status': getStatusText(item.status),  
                  
        };
    });

    // Create a worksheet from the formatted data status
    var ws = XLSX.utils.json_to_sheet(formattedData);

    // Create a workbook
    var wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "Open Breakdowns");

    // Export to Excel
    XLSX.writeFile(wb, "OverallOpen_Breakdowns.xlsx");
}


		
	}
	
		
	function totaltrialBreakdownController($scope, ApiEndpoint, $state, genericFactory, $http) {

		const baseUrl = ApiEndpoint.url;

		var vm = angular.extend(this, {
			filterComplaints:filterComplaints,
			exportToExcel:exportToExcel
		});
		vm.totaltrialBreakdown = [];
		 vm.filteredComplaints = [];
			      vm.fromDate = null;
   				 vm.toDate = null;

		activate();

		function activate() {
			console.log("Activating totaltrialBreakdownController");
			fetchtotaltrialBreakdown();
		}

		function fetchtotaltrialBreakdown() {
			const url = `${baseUrl}/breakdown/trial`;
			genericFactory.getAll("", url).then(response => {
				vm.totaltrialBreakdown = response.data;
				  vm.filteredComplaints = vm.totaltrialBreakdown;
				console.log(" totaltrialBreakdown:", vm.totaltrialBreakdown);
			}).catch(error => {
				console.error('Error fetching   totaltrialBreakdown:', error);
			});
		}
		
	
		
		function filterComplaints(){
			  
			        if (vm.fromDate && vm.toDate) {
			            const fromDate = formatDate(vm.fromDate);
			            const toDate = formatDate(vm.toDate);
			            
			            console.log("Formatted From Date:", fromDate);
			            console.log("Formatted To Date:", toDate);
			            
			            const url = `${baseUrl}/breakdown/datewise/trial?fromDate=${fromDate}&toDate=${toDate}`;
			            $http.get(url).then(function(response) {
			                const filteredData = response.data;
			                vm.filteredComplaints = filteredData;
			                console.log("Filtered complaints:", vm.filteredComplaints);
			            }).catch(function(error) {
			                console.error('Error filtering complaints:', error);
			            });
			        } else {
			            vm.filteredComplaints = vm.totaltrialBreakdown;
			            console.log("Showing all complaints data:", vm.filteredComplaints);
			        }
			    };
			
			    
			    
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
					
					
					  // Function to map status numbers to text
    function getStatusText(status) {
        switch (status) {
            case 1: return 'Open';
            case 2: return 'Trial';
            case 3: return 'Closed';
            default: return 'Unknown';
        }
    }


					
function exportToExcel() {
    if (!vm.filteredComplaints || vm.filteredComplaints.length === 0) {
        toastr.error('No data available for export!');
        return;
    }

    // Format the data for export
    var formattedData = vm.filteredComplaints.map(function(item, index) {
        return {
            'Sr No': index + 1,
              'BD Slip Number': item.bd_slip, 
                'Shift Name': item.shift.shift_name, 
            
             'Machine Name': item.machine ? item.machine.machine_name + ' ' + item.machine.eqid : '',
               
            'Complaint Cause': item.observation,         
            'Root Cause': item.root_cause,   
               'Action Taken': item.action_taken,                 
            
            'Raised Date': formatDate(item.ticket_raised_time) , 
            'Trial Date': item.ticket_trial_time ? formatDate(item.ticket_trial_time) : "N/A",
             'Status': getStatusText(item.status),  
                  
        };
    });

    // Create a worksheet from the formatted data status
    var ws = XLSX.utils.json_to_sheet(formattedData);

    // Create a workbook
    var wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "Trial Breakdowns");

    // Export to Excel
    XLSX.writeFile(wb, "OverallTrial_Breakdowns.xlsx");
}



			    
		
	}
	
	
	


	function totalBreakdownController($scope, ApiEndpoint, $state, genericFactory, $http) {

		const baseUrl = ApiEndpoint.url;

		var vm = angular.extend(this, {
			exportToExcel:exportToExcel
		});
		vm.totalBreakdown = [];
		vm.machienNames = [];
		activate();

		function activate() {
			console.log("Activating totalBreakdownController");
			fetchtotalBreakdown();
		}
		vm.labels = { 'srNo': 'Sr No', 'bd_slip': 'Breakdown Slip','machine.machine_name': 'Machine Name', 'machine.eqid': 'Machine Id', 'machine.location': 'Location', 'observation': 'Obervation', 'shift.shift_name':'Shift','raisedName': 'RaisedBy', 'statusStr': 'Status' }
		$scope.export = function() {
			document.getElementById('btnExport').click();
		}
		$scope.filterData=function(machineName){
			const url = `${baseUrl}/breakdown/getBreakDownForWeekByMachien?machineName=`+machineName;
			console.log("URL " + url)
			genericFactory.getAll("", url).then(response => {
				vm.totalBreakdown = response.data;
				updateListMachine(vm.totalBreakdown)
				console.log(" totalBreakdown:", vm.totalBreakdown.length);
			}).catch(error => {
				console.error('Error fetching   totalBreakdown:', error);
			});
		}

		function fetchtotalBreakdown() {
			const url = `${baseUrl}/breakdown/getBreakDownForWeek`;
			console.log("URL " + url)
			genericFactory.getAll("", url).then(response => {
				vm.totalBreakdown = response.data;
				updateListMachine(vm.totalBreakdown)
				console.log(" totalBreakdown:", vm.totalBreakdown.length);
			}).catch(error => {
				console.error('Error fetching   totalBreakdown:', error);
			});
		}
		function updateListMachine(totalBreakdown) {
			angular.forEach(totalBreakdown, function(item) {
				console.log(" ITEM :", JSON.stringify(item.machine.machine_name));
				var str = item.machine.machine_name;
				if (!vm.machienNames.includes(str)) {
					// Add the string to the array if it's not a duplicate
					vm.machienNames.push(str);
				}
			});
			console.log(" ITEM :", JSON.stringify(vm.machienNames));
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
					
					
					  // Function to map status numbers to text
    function getStatusText(status) {
        switch (status) {
            case 1: return 'Open';
            case 2: return 'Trial';
            case 3: return 'Closed';
            default: return 'Unknown';
        }
    }


					
function exportToExcel() {
    if (!vm.filteredComplaints || vm.filteredComplaints.length === 0) {
        toastr.error('No data available for export!');
        return;
    }

    // Format the data for export
    var formattedData = vm.filteredComplaints.map(function(item, index) {
        return {
            'Sr No': index + 1,
              'BD Slip Number': item.bd_slip, 
                'Shift Name': item.shift.shift_name, 
            
             'Machine Name': item.machine ? item.machine.machine_name + ' ' + item.machine.eqid : '',
               
            'Complaint Cause': item.observation,         
            'Root Cause': item.root_cause,   
               'Action Taken': item.action_taken,                 
            
            'Raised Date': formatDate(item.ticket_raised_time) , 
            'Trial Date': item.ticket_trial_time ? formatDate(item.ticket_trial_time) : "N/A",
              'Closed Date': item.ticket_closed_time ? formatDate(item.ticket_closed_time) : "N/A",
             'Status': getStatusText(item.status),  
                  
        };
    });

    // Create a worksheet from the formatted data status
    var ws = XLSX.utils.json_to_sheet(formattedData);

    // Create a workbook
    var wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "All Breakdowns");

    // Export to Excel
    XLSX.writeFile(wb, "Overall_Breakdowns.xlsx");
}



			    
		
	}


	function openBreakdownController($scope, ApiEndpoint, $state, genericFactory, $http) {

		const baseUrl = ApiEndpoint.url;

		var vm = angular.extend(this, {});
		vm.openBreakdown = [];
		vm.machienNames = [];
		activate();

		function activate() {
			console.log("Activating openBreakdownController");
			fetchopenBreakdown();
		}
vm.labels = { 'srNo': 'Sr No', 'bd_slip': 'Breakdown Slip','machine.machine_name': 'Machine Name', 'machine.eqid': 'Machine Id', 'machine.location': 'Location', 'observation': 'Obervation', 'shift.shift_name':'Shift','raisedName': 'RaisedBy', 'ticket_raised_time': 'RaisedDate', 'statusStr': 'Status' }
		$scope.export = function() {
			document.getElementById('btnExport').click();
		}
		$scope.filterData=function(machineName){
			const url = `${baseUrl}/breakdown/getOpenBreakDownForWeekByMachien?machineName=`+machineName;
			console.log("URL " + url)
			genericFactory.getAll("", url).then(response => {
				vm.openBreakdown = response.data;
				updateListMachine(vm.totalBreakdown)
				console.log(" totalBreakdown:", vm.totalBreakdown.length);
			}).catch(error => {
				console.error('Error fetching   totalBreakdown:', error);
			});
		}
		function fetchopenBreakdown() {
			const url = `${baseUrl}/breakdown/getOpenBreakDownForWeek`;
			genericFactory.getAll("", url).then(response => {
				vm.openBreakdown = response.data;
				updateListMachine(vm.openBreakdown)
				console.log(" openBreakdown:", vm.openBreakdown);
			}).catch(error => {
				console.error('Error fetching   openBreakdown:', error);
			});
		}
		function updateListMachine(openBreakdown) {
			angular.forEach(openBreakdown, function(item) {
				console.log(" ITEM :", JSON.stringify(item.machine.machine_name));
				var str = item.machine.machine_name;
				if (!vm.machienNames.includes(str)) {
					// Add the string to the array if it's not a duplicate
					vm.machienNames.push(str);
				}
			});
			console.log(" ITEM :", JSON.stringify(vm.machienNames));
		}
	}


	function closedBreakdownController($scope, ApiEndpoint, $state, genericFactory, $http) {

		const baseUrl = ApiEndpoint.url;

		var vm = angular.extend(this, {});
		vm.closedBreakdown = [];
		vm.machienNames = [];
		activate();

		function activate() {
			console.log("Activating closedBreakdownController");
			fetchclosedBreakdown();
		}
vm.labels = { 'srNo': 'Sr No', 'bd_slip': 'Breakdown Slip','machine.machine_name': 'Machine Name', 'machine.eqid': 'Machine Id', 'machine.location': 'Location', 'observation': 'Obervation', 'shift.shift_name':'Shift','raisedName': 'RaisedBy', 'statusStr': 'Status', 'ticket_raised_time': 'Ticket Create Time', 'ticket_closed_time': 'Ticket Closed Time', 'ttr': 'TTR' }
		$scope.export = function() {
			document.getElementById('btnExport').click();
		}
		$scope.filterData=function(machineName){
			const url = `${baseUrl}/breakdown/getClosedBreakDownForWeekByMachien?machineName=`+machineName;//
			console.log("URL " + url)
			genericFactory.getAll("", url).then(response => {
				vm.closedBreakdown = response.data;
				updateListMachine(vm.totalBreakdown)
				console.log(" totalBreakdown:", vm.totalBreakdown.length);
			}).catch(error => {
				console.error('Error fetching   totalBreakdown:', error);
			});
		}
		function fetchclosedBreakdown() {
			const url = `${baseUrl}/breakdown/getClosedBreakDownForWeek`;
			genericFactory.getAll("", url).then(response => {
				vm.closedBreakdown = response.data;
				updateListMachine(vm.closedBreakdown)
				console.log(" closedBreakdown:", vm.closedBreakdown);
			}).catch(error => {
				console.error('Error fetching   closedBreakdown:', error);
			});
		}
		function updateListMachine(closedBreakdown) {
			angular.forEach(closedBreakdown, function(item) {
				console.log(" ITEM :", JSON.stringify(item.machine.machine_name));
				var str = item.machine.machine_name;
				if (!vm.machienNames.includes(str)) {
					// Add the string to the array if it's not a duplicate
					vm.machienNames.push(str);
				}
			});
			console.log(" ITEM :", JSON.stringify(vm.machienNames));
		}
	}



	function trialBreakdownController($scope, ApiEndpoint, $state, genericFactory, $http) {

		const baseUrl = ApiEndpoint.url;

		var vm = angular.extend(this, {});
		vm.trialBreakdown = [];
vm.machienNames=[]
		activate();

		function activate() {

			fetchtrialBreakdown();
		}
vm.labels = { 'srNo': 'Sr No', 'bd_slip': 'Breakdown Slip','machine.machine_name': 'Machine Name', 'machine.eqid': 'Machine Id', 'machine.location': 'Location', 'observation': 'Obervation','root_cause': 'Root Cause','action_taken': 'Action Taken', 'shift.shift_name':'Shift','raisedName': 'RaisedBy', 'statusStr': 'Status' }
		$scope.export = function() {
			document.getElementById('btnExport').click();
		}
			$scope.filterData=function(machineName){
			const url = `${baseUrl}/breakdown/getTrailBreakDownForWeekByMachie?machineName=`+machineName;
			console.log("URL " + url)
			genericFactory.getAll("", url).then(response => {
				vm.trialBreakdown = response.data;
				updateListMachine(vm.totalBreakdown)
				console.log(" totalBreakdown:", vm.totalBreakdown.length);
			}).catch(error => {
				console.error('Error fetching   totalBreakdown:', error);
			});
		}
		function fetchtrialBreakdown() {
			const url = `${baseUrl}/breakdown/getTrailBreakDownForWeek`;
			console.log("URL " + url);
			genericFactory.getAll("", url).then(response => {
				vm.trialBreakdown = response.data;
				updateListMachine(vm.trialBreakdown)
				console.log(" trialBreakdown:", vm.trialBreakdown);
			}).catch(error => {
				console.error('Error fetching   trialBreakdown:', error);
			});
		}
		function updateListMachine(trialBreakdown) {
			angular.forEach(trialBreakdown, function(item) {
				console.log(" ITEM :", JSON.stringify(item.machine.machine_name));
				var str = item.machine.machine_name;
				if (!vm.machienNames.includes(str)) {
					// Add the string to the array if it's not a duplicate
					vm.machienNames.push(str);
				}
			});
			console.log(" ITEM :", JSON.stringify(vm.machienNames));
		}
	}
	
	
/*	
	function totalclosedBreakdownController($scope, ApiEndpoint, $state, genericFactory, $http) {

			const baseUrl = ApiEndpoint.url;

			var vm = angular.extend(this, {
					filterComplaints:filterComplaints,
					exportToExcel:exportToExcel
			});
			vm.totalclosedBreakdown = [];
			 vm.filteredComplaints = [];
				      vm.fromDate = null;
	   				 vm.toDate = null;

			activate();

			function activate() {
				console.log("Activating totalclosedBreakdownController");
				fetchtotalclosedBreakdown();
			}

			function fetchtotalclosedBreakdown() {
				const url = `${baseUrl}/breakdown/closed`;
				genericFactory.getAll("", url).then(response => {
					vm.totalclosedBreakdown = response.data;
					  vm.filteredComplaints = vm.totalclosedBreakdown;
					console.log(" totalclosedBreakdown:", vm.totalclosedBreakdown);
				}).catch(error => {
					console.error('Error fetching   totalclosedBreakdown:', error);
				});
			}
			
		
			
			function filterComplaints(){
				  
				        if (vm.fromDate && vm.toDate) {
				            const fromDate = formatDate(vm.fromDate);
				            const toDate = formatDate(vm.toDate);
				            
				            console.log("Formatted From Date:", fromDate);
				            console.log("Formatted To Date:", toDate);
				            
				            const url = `${baseUrl}/breakdown/datewise/closed?fromDate=${fromDate}&toDate=${toDate}`;
				            $http.get(url).then(function(response) {
				                const filteredData = response.data;
				                vm.filteredComplaints = filteredData;
				                console.log("Filtered complaints:", vm.filteredComplaints);
				            }).catch(function(error) {
				                console.error('Error filtering complaints:', error);
				            });
				        } else {
				            vm.filteredComplaints = vm.totalclosedBreakdown;
				            console.log("Showing all complaints data:", vm.filteredComplaints);
				        }
				    };
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
						
						
						  // Function to map status numbers to text
	    function getStatusText(status) {
	        switch (status) {
	            case 1: return 'Open';
	            case 2: return 'Trial';
	            case 3: return 'Closed';
	            default: return 'Unknown';
	        }
	    }


						
	function exportToExcel() {
	    if (!vm.filteredComplaints || vm.filteredComplaints.length === 0) {
	        toastr.error('No data available for export!');
	        return;
	    }

	    // Format the data for export
	    var formattedData = vm.filteredComplaints.map(function(item, index) {
	        return {
	            'Sr No': index + 1,
	              'BD Slip Number': item.bd_slip, 
	                'Shift Name': item.shift.shift_name, 
	            
	             'Machine Name': item.machine ? item.machine.machine_name + ' ' + item.machine.eqid : '',               
	            'Complaint Cause': item.observation,         
	            'Root Cause': item.root_cause,   
	               'Action Taken': item.action_taken,                 
	            
	            'Raised Date': formatDate(item.ticket_raised_time) , 
	          //  'Trial Date': item.ticket_trial_time ? formatDate(item.ticket_trial_time) : "N/A",
	              'Closed Date': item.ticket_closed_time ? formatDate(item.ticket_closed_time) : "N/A",
	                 'Raised By': item.addedBy ? item.addedBy.firstName + ' ' + item.addedBy.lastName : '',
	             'Status': getStatusText(item.status),  
	                  
	        };
	    });

	    // Create a worksheet from the formatted data status
	    var ws = XLSX.utils.json_to_sheet(formattedData);

	    // Create a workbook
	    var wb = XLSX.utils.book_new();
	    XLSX.utils.book_append_sheet(wb, ws, "Closed Breakdowns");

	    // Export to Excel
	    XLSX.writeFile(wb, "OverallClosed_Breakdowns.xlsx");
	}



				    
				    
		}*/
		


	function totalclosedBreakdownController($scope, ApiEndpoint, $state, genericFactory, $http) {

			const baseUrl = ApiEndpoint.url;

			var vm = angular.extend(this, {
					filterComplaints:filterComplaints,
					exportToExcel:exportToExcel,
					addNew: addNew,
					 cancel: cancel,
					 add:add
					
			});
			vm.totalclosedBreakdown = [];
			 vm.filteredComplaints = [];
				      vm.fromDate = null;
	   				 vm.toDate = null;

			activate();

			function activate() {
				console.log("Activating totalclosedBreakdownController");
				fetchtotalclosedBreakdown();
			//	loadbreakdown();
				$scope.pendingApprovals = {};
			 $scope.addNewTab = false;
			}
			
			
			
			function cancel() {
							          $scope.addNewTab = false;
							      }

							      function addNew() {
							          $scope.addNewTab = true;
							         
							      }
								  
								  
								  
								  function add(approval) {
								  	    $scope.addNewTab = true; 
								  	    $scope.equipment = approval.machine.eqid; 
										$scope.MachineName = approval.machine.machine_name;
										$scope.rootCause = approval.root_cause; 
										$scope.observation = approval.observation; 
										$scope.repairDetails = approval.action_taken; 
										$scope.lab = approval.lab.labName; 
										$scope.departname = approval.department.departmentName; 
										$scope.location = approval.machine.location; 
										$scope.totalBreakdownTime = approval.totalBreakdownTime; 
										$scope.repairingTime = approval.repairingTime; 
										$scope.attendUsers = approval.attendedUsers  ? approval.attendedUsers : "N/A";
										
										$scope.ScheduleDate = new Date(approval.ticket_raised_time).toLocaleString('en-IN', { 
															    timeZone: 'Asia/Kolkata', 
															    year: 'numeric', 
															    month: '2-digit', 
															    day: '2-digit', 
																
																														    hour: '2-digit', 
																														    minute: '2-digit',
															   
															    hour24: true 
															});
															$scope.RepairedDate = new Date(approval.actualWorkingStartTime).toLocaleString('en-IN', { 
															    timeZone: 'Asia/Kolkata', 
															    year: 'numeric', 
															    month: '2-digit', 
															    day: '2-digit', 
															    hour: '2-digit', 
															    minute: '2-digit', 
															//    second: '2-digit',  // optional
															    hour24: true 
															});		

															$scope.ClosedDate = new Date(approval.ticket_closed_time).toLocaleString('en-IN', { 
															    timeZone: 'Asia/Kolkata', 
															    year: 'numeric', 
															    month: '2-digit', 
															    day: '2-digit', 
															    hour: '2-digit', 
															    minute: '2-digit', 
															 //   second: '2-digit', 
															    hour24: true 
															});

															$scope.trialDate = new Date(approval.ticket_trial_time).toLocaleString('en-IN', { 
															    timeZone: 'Asia/Kolkata', 
															    year: 'numeric', 
															    month: '2-digit', 
															    day: '2-digit', 
															    hour: '2-digit', 
															    minute: '2-digit', 
															   // second: '2-digit', 
															    hour24: true 
															});
			
																	
										
										$scope.DoneBy = approval.updateBy.firstName + " " + approval.updateBy.lastName;
										
										$scope.sparesUsed = approval.spare_used ? approval.spare_used : "Not Used";


								  	    $scope.pendingApprovals = Object.assign({}, approval);
										loadbreakdown(approval.breakdown_id);
								  		
								  	   
								  	}
									
									
									

		
									
									$scope.exportToPDF = function () {
									    const { jsPDF } = window.jspdf;
									    let doc = new jsPDF(); 
	//									let imgData = '/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAMCAggICAgICAgICAgICAgICAYICAgGBgUGCAgICAUFBQUFBwYFBQUFBQUFBQoFBQcICQkJBQULDQoIDQYICQgBAwQEBgUGCAYGCAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICP/AABEIA4QDhAMBIgACEQEDEQH/xAAdAAEAAQQDAQAAAAAAAAAAAAAACAEGBwkCAwUE/8QARRABAAEDAQYCBwYDBgUEAwEBAAECAxEEBQYHCBIhMVETFEFSYXGRGCJygaGxMjTBFSMkQtHhM0NigpIJJTVTY/Dxwhf/xAAcAQEAAQUBAQAAAAAAAAAAAAAABQEDBAYHAgj/xAA/EQEAAgECBAMEBggGAQUBAAAAAQIDBBEFEiExBkFREyJhcRQyM0KBsSM0UmJykaHBFSRDU4LR8DWSk6LhFv/aAAwDAQACEQMRAD8A2pgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA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AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH//2Q=='; // Replace this with your actual Base64 image

										
									
										// 🖼️ Base64 of your images
										 // let imgA = '/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAMCAggICAgICAgICAgICAgICAYICAgGBgUGCAgICAUFBQUFBwYFBQUFBQUFBQoFBQcICQkJBQULDQoIDQYICQgBAwQEBgUGCAYGCAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICP/AABEIA4QDhAMBIgACEQEDEQH/xAAdAAEAAQQDAQAAAAAAAAAAAAAACAEGBwkCAwUE/8QARRABAAEDAQYCBwYDBgUEAwEBAAECAxEEBQYHCBIhMVETFEFSYXGRGCJygaGxMjTBFSMkQtHhM0NigpIJJTVTY/Dxwhf/xAAcAQEAAQUBAQAAAAAAAAAAAAAABQEDBAYHAgj/xAA/EQEAAgECBAMEBggGAQUBAAAAAQIDBBEFEiExBkFREyJhcRQyM0KBsSM0UmJykaHBFSRDU4LR8DWSk6LhFv/aAAwDAQACEQMRAD8A2pgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA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AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH//2Q=='; // Replace this with your actual Base64 image
								
										  let imgA = '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';
										  
										  let imgB = "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"; // 👉 image for FUTURA LAB 1 & 2

										  // ✅ Choose image based on lab name
										  let labName = $scope.lab ? $scope.lab.trim().toUpperCase() : "";
										  let imgData;

										  if (labName === "FUTURA LAB 1" || labName === "FUTURA LAB 2") {
										      imgData = imgB;
										  } else {
										      imgData = imgA;
										  }
										
									    // 🔹 Watermarks
									    doc.setTextColor(200, 200, 200);
									    doc.setFontSize(40);
									    doc.setFont("helvetica", "bold");
									    doc.text("CONFIDENTIAL", 30, 150, { angle: 45 });
									    doc.text("Breakdown Sheet", 40, 250);

									    // 🔹 Title with Background
									    doc.setFillColor(220, 255, 220);
									    doc.setDrawColor(0, 102, 0);
									    doc.setLineWidth(0.5);
									    doc.roundedRect(10, 5, 190, 12, 3, 3, "FD");
									    doc.setFontSize(16);
									    doc.setTextColor(0, 102, 0);
									    doc.setFont("helvetica", "bold");
									    doc.text("Breakdown Sheet", 80, 13);
										

									    // ======================================================
									    // 🔹 Address Section
									    // ======================================================
									    let sectionX = 10, sectionY = 22, sectionW = 190, sectionH = 40;
									    doc.setDrawColor(0);
									    doc.setFillColor(245, 245, 245);
									    doc.roundedRect(sectionX, sectionY, sectionW, sectionH, 3, 3, "DF");

									    if (imgData) {
									        doc.addImage(imgData, "PNG", sectionX + 140, sectionY + 3, 45, 30);
									    }

									    let textX = sectionX + 5, textY = sectionY + 8;
									    doc.setFontSize(9);
									    doc.setTextColor(0, 0, 255);
									    doc.text("KF Bioplants Private Limited", textX, textY);
									    doc.setTextColor(0, 0, 0);
									    doc.text("Taluka Haveli, Sr. No. 129/1-3C, Manjri Bk,", textX, textY + 5);
									    doc.text("Pune, Maharashtra 411036", textX, textY + 10);
									    doc.text("Phone: 020 2694 8400 / 8401 / 8402", textX, textY + 15);
									    doc.setTextColor(0, 0, 255);
									    doc.text("Website: https://www.kfbioplants.com", textX, textY + 20);

									    // ======================================================
									    // 🔹 Details Section (Dynamic Height)
									    // ======================================================
									    let y = sectionY + sectionH + 5;
									    const labelX = 15, valueX = 70, lineHeight = 7;
									    let currentY = y + 10;
									    let bottomY = currentY;

									    function addField(label, value, maxWidth = 120) {
									        doc.setFontSize(10);
									        doc.setFont("helvetica", "bold");
									        doc.setTextColor(0, 102, 0);
									        doc.text(label, labelX, currentY);

									        doc.setFont("helvetica", "normal");
									        doc.setTextColor(0, 0, 0);
									        let textValue = value || "N/A";

									        let splitText = doc.splitTextToSize(textValue, maxWidth);
									        doc.text(splitText, valueX, currentY);

									        currentY += (splitText.length * lineHeight);
									        bottomY = currentY;
									    }

									    // 🔹 Add All Fields
									    addField("Machine Name:", $scope.MachineName);
									    addField("Equipment ID:", $scope.equipment);
										addField("DepartMent :", $scope.departname);
									    addField("Lab:", $scope.lab);
									    addField("Location:", $scope.location);
										addField("Breakdown Description:", $scope.observation, 120);
										addField("Corrective Action :", $scope.repairDetails, 120);
										addField("Breakdown RootCause:", $scope.rootCause, 120);
										addField("Spare Used:", $scope.sparesUsed);
									   
									    addField("Repairing Time:", $scope.repairingTime);
										addField("Repairing Date:", $scope.RepairedDate);
									    addField("Total BD Time:", $scope.totalBreakdownTime);
									    addField("Raise Time:", $scope.ScheduleDate);
									
									    addField("End Time:", $scope.ClosedDate);
								
									 
									
									  
									    addField("Attend By:", $scope.DoneBy);
									   // addField("Attended Users:", $scope.attendUsers);

									    // ✅ Draw details box
									    let sectionHeight = (bottomY - y) + 10;
									    doc.setDrawColor(0);
									    doc.setFillColor(245, 255, 245);
									    doc.roundedRect(10, y, 190, sectionHeight, 3, 3, "D");

									   
									
										
										
										// ======================================================
										// 🔹 New Signature Section (with lines for signatures)
										// ======================================================
										let sigY = bottomY + 15; // place below details section
										let sigHeight = 40;      // a bit taller to leave space for lines

										doc.setDrawColor(0,102,0);
										doc.setFillColor(220, 255, 220); // light yellow background
										doc.roundedRect(10, sigY, 190, sigHeight, 3, 3, "DF");

										doc.setFontSize(10);
										doc.setFont("helvetica", "bold");
										doc.setTextColor(0, 0, 128);

										// 🔹 Row 1
										let row1Y = sigY + 10;
										doc.text("Dept. Incharge Name:", 15, row1Y);
										doc.text("Dept. Incharge Signature:", 120, row1Y);

										// ⬇ signature lines for Row 1
										doc.setDrawColor(100);
										doc.line(15, row1Y + 5, 90, row1Y + 5);     // under Dept. Incharge Name
										doc.line(120, row1Y + 5, 195, row1Y + 5);   // under Dept. Incharge Signature

										// 🔹 Row 2
										let row2Y = row1Y + 15;
										doc.text("Prepared By (Sr. Supervisor):", 15, row2Y);
										doc.text("Approved By (Maintenance Manager):", 120, row2Y);

										// ⬇ signature lines for Row 2
										doc.line(15, row2Y + 5, 90, row2Y + 5);     // under Prepared By
										doc.line(120, row2Y + 5, 195, row2Y + 5);   // under Approved By

										
										// ======================================================
										// 🔹 Breakdown Sheet Unique Number & Download Date/Time
										// ======================================================
										let footerY = row2Y + 20; // place below signature section

										doc.setFontSize(8);
										doc.setFont("helvetica", "italic");
										doc.setTextColor(80);

										// Generate unique breakdown sheet number
										// (you can replace with DB ID or random number generator)
										let breakdownNumber = "BD-" + new Date().getFullYear() + "-" + Math.floor(1000 + Math.random() * 9000);

										// Format current date & time
										let now = new Date();
										let downloadDate = now.toLocaleDateString();
										let downloadTime = now.toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'});

										// Print on left (Sheet Number) and right (Date & Time)
										doc.text("Breakdown Sheet No: " + breakdownNumber, 15, footerY);
										doc.text("Downloaded: " + downloadDate + " " + downloadTime, 120, footerY);



									    // ======================================================
									    // 🔹 Footer
									    // ======================================================
									    doc.setDrawColor(0, 100, 0);
									    doc.line(10, 285, 200, 285);
									    doc.setFontSize(8);
									    doc.setTextColor(100);
									    doc.text("Generated by Maintenance Management System", 60, 290);

									    // Save
									    doc.save("Breakdown_Sheet.pdf");
									};
									
									
									
						function loadbreakdown(breakdownId) {
										 var url = baseUrl + "/breakdown/closed/" + breakdownId;
											genericFactory.getAll("", url).then(response => {
												vm.loadbreakdown = response.data;
											
												console.log(" loadbreakdown:", vm.loadbreakdown);
											}).catch(error => {
												console.error('Error fetching   loadbreakdown:', error);
											});
										}
										


			function fetchtotalclosedBreakdown() {
				const url = `${baseUrl}/breakdown/closed`;
				genericFactory.getAll("", url).then(response => {
					vm.totalclosedBreakdown = response.data;
					  vm.filteredComplaints = vm.totalclosedBreakdown;
					console.log(" totalclosedBreakdown:", vm.totalclosedBreakdown);
				}).catch(error => {
					console.error('Error fetching   totalclosedBreakdown:', error);
				});
			}
			
		
			
			function filterComplaints(){
				  
				        if (vm.fromDate && vm.toDate) {
				            const fromDate = formatDate(vm.fromDate);
				            const toDate = formatDate(vm.toDate);
				            
				            console.log("Formatted From Date:", fromDate);
				            console.log("Formatted To Date:", toDate);
				            
				            const url = `${baseUrl}/breakdown/datewise/closed?fromDate=${fromDate}&toDate=${toDate}`;
				            $http.get(url).then(function(response) {
				                const filteredData = response.data;
				                vm.filteredComplaints = filteredData;
				                console.log("Filtered complaints:", vm.filteredComplaints);
				            }).catch(function(error) {
				                console.error('Error filtering complaints:', error);
				            });
				        } else {
				            vm.filteredComplaints = vm.totalclosedBreakdown;
				            console.log("Showing all complaints data:", vm.filteredComplaints);
				        }
				    };
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
						
						
						  // Function to map status numbers to text
	    function getStatusText(status) {
	        switch (status) {
	            case 1: return 'Open';
	            case 2: return 'Trial';
	            case 3: return 'Closed';
	            default: return 'Unknown';
	        }
	    }


						
	function exportToExcel() {
	    if (!vm.filteredComplaints || vm.filteredComplaints.length === 0) {
	        toastr.error('No data available for export!');
	        return;
	    }

	    // Format the data for export
	    var formattedData = vm.filteredComplaints.map(function(item, index) {
	        return {
	            'Sr No': index + 1,
	              'BD Slip Number': item.bd_slip, 
	                'Shift Name': item.shift.shift_name, 
	            
	             'Machine Name': item.machine ? item.machine.machine_name + ' ' + item.machine.eqid : '',
	               
	            'Complaint Cause': item.observation,         
	            'Root Cause': item.root_cause,   
	               'Action Taken': item.action_taken,                 
	            
	            'Raised Date': formatDate(item.ticket_raised_time) , 
	          //  'Trial Date': item.ticket_trial_time ? formatDate(item.ticket_trial_time) : "N/A",
	              'Closed Date': item.ticket_closed_time ? formatDate(item.ticket_closed_time) : "N/A",
	                 'Raised By': item.addedBy ? item.addedBy.firstName + ' ' + item.addedBy.lastName : '',
					 'Update By': item.done_by, 
	             'Status': getStatusText(item.status),  
	                  
	        };
	    });

	    // Create a worksheet from the formatted data status
	    var ws = XLSX.utils.json_to_sheet(formattedData);

	    // Create a workbook
	    var wb = XLSX.utils.book_new();
	    XLSX.utils.book_append_sheet(wb, ws, "Closed Breakdowns");

	    // Export to Excel
	    XLSX.writeFile(wb, "OverallClosed_Breakdowns.xlsx");
	}
}
		



	function totalApprovedController($scope, ApiEndpoint, $state, genericFactory, $http) {

		const baseUrl = ApiEndpoint.url;

		var vm = angular.extend(this, {
			filterComplaints:filterComplaints,
			exportToExcel:exportToExcel,
			addNew: addNew,
						            cancel: cancel,
									add: add,
			
		});
		vm.totalApproved = [];
		 vm.filteredComplaints = [];
			      vm.fromDate = null;
				 vm.toDate = null;
		vm.machienNames = [];
		activate();

		function activate() {
			console.log("Activating totalApprovedController");
			fetchtotalApproved();
			$scope.pendingApprovals = {};
			$scope.addNewTab = false;
		}
	
		function cancel() {
				          $scope.addNewTab = false;
				      }

				      function addNew() {
				          $scope.addNewTab = true;
				         
				      }
					  
					  
					  
					  function add(approval) {
					  	    $scope.addNewTab = true; 
					  	    $scope.equipment = approval.machine.eqid; 
							$scope.MachineName = approval.machine.machine_name;
							$scope.overallStatus = approval.overall_status; 
							$scope.ExecutiveRemark = approval.executiveRemark; 
							$scope.lab = approval.lab.labName; 
							$scope.location = approval.machine.location; 
							$scope.Freq = approval.frequency; 
							
							$scope.ScheduleDate = new Date(approval.schedule_date).toLocaleString('en-IN', { 
												    timeZone: 'Asia/Kolkata', 
												    year: 'numeric', 
												    month: '2-digit', 
												    day: '2-digit', 
												   
												    hour12: true 
												});

							$scope.ClosedDate = new Date(approval.closedDate).toLocaleString('en-IN', { 
														    timeZone: 'Asia/Kolkata', 
														    year: 'numeric', 
														    month: '2-digit', 
														    day: '2-digit', 
														   
														    hour12: true 
														});
							$scope.closedApprovalDate = new Date(approval.closedApprovalDate).toLocaleString('en-IN', { 
														    timeZone: 'Asia/Kolkata', 
														    year: 'numeric', 
														    month: '2-digit', 
														    day: '2-digit', 
														    
														    hour12: true 
														});
														
														
							
							$scope.DoneBy = approval.done_by.firstName + " " + approval.done_by.lastName;
							
							$scope.ApprovalBy = approval.approvalBy 
							    ? approval.approvalBy.firstName + " " + approval.approvalBy.lastName 
							    : "Not Assigned";

					  	    $scope.pendingApprovals = Object.assign({}, approval);
							loadChecklist(approval.maint_id);
					  		
					  	   
					  	}
						
						
						$scope.exportToPDF = function () {
						    const { jsPDF } = window.jspdf;
						    let doc = new jsPDF();

						    // Load your hardcoded image (Replace with actual Base64)
	//						let imgData ='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ABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAf//Z';
						
							
							
							// 🖼️ Base64 of your images
																	 // let imgA = '/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAMCAggICAgICAgICAgICAgICAYICAgGBgUGCAgICAUFBQUFBwYFBQUFBQUFBQoFBQcICQkJBQULDQoIDQYICQgBAwQEBgUGCAYGCAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICP/AABEIA4QDhAMBIgACEQEDEQH/xAAdAAEAAQQDAQAAAAAAAAAAAAAACAEGBwkCAwUE/8QARRABAAEDAQYCBwYDBgUEAwEBAAECAxEEBQYHCBIhMVETFEFSYXGRGCJygaGxMjTBFSMkQtHhM0NigpIJJTVTY/Dxwhf/xAAcAQEAAQUBAQAAAAAAAAAAAAAABQEDBAYHAgj/xAA/EQEAAgECBAMEBggGAQUBAAAAAQIDBBEFEiExBkFREyJhcRQyM0KBsSM0UmJykaHBFSRDU4LR8DWSk6LhFv/aAAwDAQACEQMRAD8A2pgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA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AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH//2Q=='; // Replace this with your actual Base64 image

																	  let imgA ='/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxASDhAQEBAVEBAVEBINDRUVDRAQEA0NIB0iIiAdHxkkKDQsJCYxJx8fLTItMStAQzBDIys9QD9ANzQ5MC0BCgoKDg0OFRAQFTclFx4sLS0rKysrKzEtKzcrNzctLTcuMi83MCstNystKy8vNzEuLzc3Kzc3Ky03LTIyLy8tLf/AABEIAMgAyAMBIgACEQEDEQH/xAAbAAACAwEBAQAAAAAAAAAAAAABAgADBAUGB//EADsQAAEEAAQDBQYFAwIHAAAAAAEAAgMRBBIhMRNBUQUiYXGBBjKRobHwFCNCUsEzYtEV8SQlQ1OCkuH/xAAZAQEBAQEBAQAAAAAAAAAAAAAAAQIEAwX/xAAqEQEBAAIBBAAEBgMBAAAAAAAAAQIREgMEITETFDJhQUJRcZGhIvDxUv/aAAwDAQACEQMRAD8A+zNGqBGqdoQpHtshCuiCrAVwChlUKRM5BoVZiFKAiUwUUUAgmVZFC1FEBARtBREG1LSogIGCKClogqKKIiKKIEoCokLkudBZaipL1EDEJAExOn3uoNkeiMbzTlAbJXuRPdTdEoMChKKgCJUDlCgiiiiAqKJSUQyBKQuUAUXRsyNlKSAkdKqaXUjYWbMU5FDdEsX5lC5UuNfFGQnLsjNPnVb5EYnHKNLVWId7unP5IiGYJfxPeJ02+aYlv7SFSXMz9Bl+aC78UOg+KiqLYup+CiC4vUDlUCi1R08WoP0vmqTJ4KBuiBaqxJF4KreEaoIIkFqdKEC9D2sCUlJmS6lRNHL0tpHaEJuaKheoSUoHeHqmLwPsoEeDRKlGkJ3906EIOcco05dQgL76c0SDWyrMhI9fBFztDqqm1h/lF7tFTn+7RL9PRGKshecoVeJedDex+aSKTSkmJf3fVEWCd37voqXvt4ujp0VRedPlulfmzDTWtPJBoNdFFSGP6KINYcronaKh8rQdOe4rZFsim3VZuNVaaJAow6FVlyrMi1sie9FlDle12ihljoXFQBI55TRnXwRmyyJn+qr4m+isAHzSt5muaIqe4nVHMb2J+KaZ2o0UfKRdIvtWQbT5XdPolzHMPVRsp1v7CGizZsp0S5X5Re1eCOIl7pHgmM/cGnIdUCOhdpod1Y6AAc/Wkk8/u1pr4qsznKBXK753aGq0SsaBpYPiOSZ5ZkPWvHdUzSEj1CEp7vp81Wbi0YNrMgJGvkUmNDO7vWYXvsq8DOcgF9VMfIcrTf6gfVGLNLiyHQknTbfRZ5Z28Vha4uGU7VaTjOIIJJ0CzwOIe3XYO+qI6X4wWLLhZrUNUWYgXt/uogmoBrUXVJY9DR/hLmFk0dtBetovrfWzsNVh2rxIQQOW/JWZvBZQaNVrtqOSvc49bFeau2bDNargwciqGyV/hc3H9pTRuAbhnStJq2zRtN+RNq708+plxm66pCIXkfxkobI/iXBn4UsU8gbPDJ0ZI0mz0B1Wpk+La4wtlbNIwcaIlzRI6P8AZK3lYOjuqzyc07vG/lelZd+qUtOvmq8PiA4NOoJaH5To5oPIj4j0VrZN/MLW3v78xVJfNBz6Kad1kUjQLijUigym0eMfu0wAzD1VzGUKGqisk0hLSpnOSr5BW40HISkEI4QNUaB5oFz2Qmkl0r+AkkiGahewtEw9zNetbUgEkxICLjba8EZIMrQbsfynLKbZI1CqW69BgnUzlujjZLaNBo4FLhs2XQWL8N1Xjby6gjXpzVeNu1nH/tb8FSHjiA0Pd21pZ/OwoHjMd9kRu4o/aPmosoeOpQQXg/ehN+aSQgCzoKqy4AAoB3I+Xgq8bCx8T2vaHNLbILLC83XldS2Lm4ljz/UbQF6uaT5pmStBNPaQBfvs2XzX2ZxcUOHxErmNfISIYgQDnJB08uq9d7Mez0UEY4jQ6Z4ImsAgNI90eH1UmW3z+37zPra44z1u+fX/AF2pMU0tJbJHnru28ZQfFcodjFwdIzEcbEu7jpnEVhmnfhsGgNfVeV7B7Nwr+0MZHM1vDY6QRgvLA2n116L1/s7g4Io5nQOuF8pdHqTlI7rtTuLBVl2x0ure5s5YzXn8fPj7M7PZ2KMBzpCAwZYKr8nq4Xu8738NlXFhoMvDjzthJzTAOLX4l/8Ae/VxHhotbvzJMzzQaaIOmUE6eq2CIHSNoreS9pddCAtTGOqdv0/wxGMR5gQ0xvDAwOa002McvIeIWqCYEuY7R4Adp7sjOTh4fT4E5czc2YvcWgEA5jnYee3LzWHtCXhCOTUFkgJBBH5LiA4C+VG66tCVc/8ACbnp1ZcTGP8AqN3o99uhRjnaSS1wcOeVwdr6L5/7VYOEdrRNc1rY3mJ0+uRptxzEn+U+EwbW9rtGANwtymUtcXxtbWozc/8AKxy8uGd/l8S48ZqXXvy9ke14RiBAZBxasN189+q3/iDt/HJeX/E4J3akbeC/8VbmElpaywDTtTroNDS2dp9vRRznDxQPxMwGZ7WD3B4lXbpw7nGTK55TUupr/fbs4ia2OBP0UEv5df2jouJhu34JsNLLGx4dGBxYw0GRuvTmP8LmD20iMRc3DykNIaSAKA6k7DyTlGr3fQkl5e3qMX2nFCXOlkbHYAGYgXXTqg/tKN0LZA8Fj/cOV3eHwXmPaXGRYjsx80eoJjqwMzHZgCFowWLhi7Owkk1mmgRNADnPkPIDqnLy8vmZ8W47/wAeO9vQS41hyNzWXEZaa4iyLHyNp8TOGtdmJAa0PdoTTSd/kV5zsjteKSYYYxS4eVrnSsDyLJy+XTbwCmP9psNxJISJ8/C4Mg4bRnq6AG96noFrlGvm+lx5cvHp6LszFtcMrTrZNEEaevmE3ajyGss/rAHmvPez/bkEjZpAJQ+Il5YGtccpIvKANbqjax4P2sOImeDE/IZomwARgiIag5neOhTlGfm+jbj5+r09hJG8jUXz5brHBGeK4H9t9VRivagcZ8UGGfiHRgGbKWtEZ6a7lW9jdotmLp2NIDmtoGszSruVqdXDLK441e+HXYKLVnvcH4IqtsDfvyUmdUbyToGknXYUo373VfaDYjC4TNzx2M4IBs3p/CxXVlvjdPnHZHZH4jCTlms0bmvYOb2kaj5fJeu9iu3BNAIZD+bHz5yR8j5jmrMNJgY3B8UQY+i2wytFGs7MuzCL1zERkEk7rEwsfJ6HZ59C45Y5Tfq/f9HB9nsDBP2jjGytbI3NK9gJP79x8V7HCYAQwOgjOjQ5zL5Nc5xr0XOw57OY8PbAGuBzMIjNtK7GJdnY2WMF3dNt5yRHcDx2I8vFaxx06O17f4ONt9+fM+5MPGCSHtOWntJturgSd/irmsc5mdxDQDlIBILh4nlay4KZjw0cbukF1kD+oPMfLzBV0crb4mrsuhHvVXPp/wDFt1y78xeG7ZWhsRq7GhPI5ei43tU4nDlhNuc5kMPdILg5wHX7yrsySNy5pTlicC+g6vHU/PouJ2a04zEtn1OFhJ4RN/8AEYjaxfIa+pUrn7jLc4T3l4cL2vMZ7ZgEmUxgQtlsjLlzG79Fm7WdHH2nD/p5AJyB4jdbC+9Rp4br0mIj7Mc5xdAC4uJceGbLr1T4OfAQkuhiDD1EferzWOFcN7HO5ZXc83e/xjkYp/8Az6E9GUfPI5U+zc/C7WxYlNOdxcpcQLJcD9NV13/6c55eYBmLi8u4Zsu3tN2mcBiCDMwvIFA5S11dLCca3e0zl5SzfLl/Lg+zgLsZ2hIz+kWyix7ur7HyBU9lsTG3svGte5ovPoXC9WACh5r0TMRhYsLMIfy42ttwykWToPMrzPsdhcG+GXjsDpWuMjQbvhUPjqs61ZHhehenn08JZbZl+3lngiLexZS7QPnDmeIto/groYjtFsWAwH5bJHuH5Tni2QuFWfmu1iZ8HIGiSMODdGAx6NHgPRVyM7PdEYjD3M3EADXNp+16FXhXr8nnj9GU+mRwMLNl7YY6WdklNOaQZWMvIdOngt0EgPb0hv8ARXrwwui+Ps7Mx4hGdoDW9w5QB4beqYDs8EPEIEgObNwzebzVmNTHs+pJJbPq5Od7IOA7Tx1dZK/91m9hsVG0Yhr3ta8yxloJALjqNPVdjB/gY3CRkYZILoiM2FUW4ISGVsQ4pdmvKfe6gbBWY3wuPaZ43Cyzxb/bhdrNe3ET4nCzmNxlkErbLHBw1O2hB3Fr1fsdinSYUSPADnEiw0NBonWlzJcLgJHmSSK3E5ie93j4gFeh7MDXRjhgBoNNFAUNtlZjq7a6PbZdPqXLfi/duEvXTyKirdERv/G6K27FQCsHn8+XRHJr69Ew56a69Fh2hQ66DTlsniiYRoL630QMGx3GxobBUdpwFzWBjcwE0T67ujQbJ1RjO6m40SRtFa6V8EzY2kaV6dFxBFjKyvD3NIbxf6Ge/wAywOX/AG/mkiwmLZGAyNwIbFsYN2saCD11B5pv7Of5m/8Ai/w3Y3sVjjxI3uhkJ1cyiHn+5p0Pnv4rG7s3HUMs8DhtZjma7Q9A6lthhlbPlbYw9OP6QA42T4nU+Cx8LFsLWsY4NL5nmjCQWufIRodt2H18Cjzzk96s/bZYvZl0hvFzunAObhtaIoifGtXeq9FEA1oY1oa0ABoAADR5LzZxGJbLFGZHZuHE59iI/muzCiBuLA1GwBWiNmMPD1fZP5gIhZlPdvUE7d47a3y0SXR08sMd2YXf9u5SqcAehrTluuXLJMyDCMc6RsjnNilFxGRxyO5nTcBZ2RYxv6DndIySUtMOV5yRB2h60/4eITb0vX1+Wu3Wu30UL2+HjsuM6HFk65yM7XmnQg5RKdB/4V8Ffi8HL+JL4wQCyBriOHlc0OfmBvXYivFNr8a3zxb5nDKQK1SAHKLGlV6LiOwmN4OSi13CyU0xNaBwhoK2PE9KWh0eLzPIzZczabUOsVtujfvVm+6TbM6+/wAldJrRyrofNWBuh0XLwOHmbO4uDwx8hL8xi1HDYATXOwdlJY8XqQH1xJA7KYa4feyFt6/tu02vxvG+NdVzRQ800rBkOl6LgPZju8bdmyuygcHJmyNy78s2b0TsmxLjIWFzmCSSN2sIy08VXk3Nau2PmPP013MBFTNq1PRHHs0Zp+sfBcOH8YGtL3mIZGCVx4GSM8N2Z3o7KV0sEZH4eN8vvOIfWlNHID75qypj1+d1qx0JnQ7Oq/I2uXGxuRzro3TRpstGJZ3yqXM5cv5VaF8TmkAGyRYpRWRkl7L5aDyUQXmI676b6EglPw6IG9DXzVrw6hWpvVRzDpfXleyzp1clbHUHUK6K6Eg6JpHCwQPNK+MCnDZVnaPZ8fqlA5K0vBGu6R8fMKkv6qnNpFrr19FaNQlI7tV4nxKi7Qi0v2PNPGFZlB8ETeme+oVgoqOiQyovgHJaTOKVRYVwQsp0bQV5lL6FMSOiUtajFkOXAjUUevgmdCMt36KrJtRTSOcBQo6Ks2foMTRXO/RLPt6qQuFU4EHqmnHd3sXoqxWV+pvZKQrKQpGQhHeHmomAUQbeGLTMsaFVCXW0/FFUeaj3spnM0PTkkqxXjYQjdlNb/wCE8h/UPTzVPSuU0NqKjJSrGuDhql4Y1Ua3PVAzaj5q7cfRCOKxqERCAjFs/BVdbpwU72Wqw0gqruVY0o5Qmao5qPPat0arMSuz1uoR0Ua5VnMZSkLRfVEtTS8/1YnBIQtjo0hiTS84youcfktHBUMCaOUZsyVxNadVsEAULBWiMXKM7ZAfeFHqiY+Y1TuYq8pGxVeZOait4oOjh6jdRBfhQKIPl6KnFW3bl5bK1tg390rJmAnfko6N6y2yRXd79fJbXDu6eaxQHl0OnktbDaRc/aBlJ2tCDdNETp5KvOrKRSgoowBQpOlcEUtJmuQBUIQMRapc0jZODSfQoelQcDvuoQR4hF8fRBj+RRf2EFQhAs5hFptGQIRKakCEQtJHt0VtJXjREUUgQiEyCpzEFaQogeB1iuY09FXiCBtujNHQ9VQQpXRJN7CEa/e62kCvRY2ha33lHkkXP3ADtFbFqEkYsJWHKfBVizfpoyaIApwlcEeaIgpQUUCvao02nBtVvaiwxS1SINooC1yV8ahCZrkPXpW13IpiL1TOYq9R/KHswPIokIjVGkZpKSvCspK4IiohKAVZSFIEpRNSiAyNWatSEFFHRjfCxrdloGrQfBRRVnMkZo0nnboooiW6sDDycvuleQoojOc1VZCIKiiAohRREVubSNqKIqKKKIpmlEhRRGaldEUFERECEFEQpCFKKIBSiiiD/9k=';
																	  
																	  
																	  let imgB = "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"; // 👉 image for FUTURA LAB 1 & 2

																	  // ✅ Choose image based on lab name
																	  let labName = $scope.lab ? $scope.lab.trim().toUpperCase() : "";
																	  let imgData;

																	  if (labName === "FUTURA LAB 1" || labName === "FUTURA LAB 2") {
																	      imgData = imgB;
																	  } else {
																	      imgData = imgA;
																	  }

							doc.setTextColor(200, 200, 200);
							doc.setFontSize(40);
							doc.setFont("helvetica", "bold");

							// First watermark (Diagonal)
							doc.text("CONFIDENTIAL", 30, 150, { angle: 45 });

							// Second watermark (Horizontal at bottom)
							doc.text("APPROVED", 60, 250, { angle: 0 });



						    // Set Title with Background
						    doc.setFillColor(220, 255, 220);
						    doc.rect(10, 5, 190, 12, "F");
						    doc.setFontSize(16);
						    doc.setTextColor(0, 102, 0);
						    doc.text("Approved PPM Sheet", 80, 13);

						
							
						


						    // KF Bioplants Address (Top Left)
						    let addressY = 20;
						    doc.setFontSize(10);
						    doc.setTextColor(0, 0, 255);
						    doc.text("KF Bioplants Private Limited", 10, addressY);
						    doc.setTextColor(0, 0, 0);
						    doc.text("Taluka Haveli, Sr. No. 129/1-3C, Manjri Bk,", 10, addressY + 5);
						    doc.text("Pune, Maharashtra 411036", 10, addressY + 10);
						    doc.text("Phone: 020 2694 8400, 020 2694 8401, 020 2694 8402", 10, addressY + 15);
						    doc.setTextColor(0, 0, 255);
						    doc.text("Website: https://www.kfbioplants.com", 10, addressY + 20);
						    doc.setTextColor(0, 0, 0);

						    let y = addressY + 22;
						    let rightColumnX = 140; // Right-side alignment

							// Adjust the width and height to match the address size
							doc.addImage(imgData, 'PNG', rightColumnX, addressY - 1, 60, 15);


						    // Section Box for Machine Details
						    doc.setDrawColor(0);
						    doc.setFillColor(235, 255, 235);
						    doc.rect(10, y, 190, 35, "DF");

						    // Left Side Fields (Above Table)
						    doc.setTextColor(0, 102, 0);
						    doc.text("Machine Name:", 15, y + 6);
						    doc.setTextColor(0, 0, 0);
						    doc.text(`${$scope.MachineName || 'N/A'}`, 60, y + 6);

						    doc.setTextColor(0, 102, 0);
						    doc.text("Equipment ID:", 15, y + 12);
						    doc.setTextColor(0, 0, 0);
						    doc.text(`${$scope.equipment || 'N/A'}`, 60, y + 12);

						    doc.setTextColor(0, 102, 0);
						    doc.text("Frequency:", 15, y + 18);
						    doc.setTextColor(0, 0, 0);
						    doc.text(`${$scope.Freq || 'N/A'}`, 60, y + 18);

						    doc.setTextColor(0, 102, 0);
						    doc.text("Lab:", 15, y + 24);
						    doc.setTextColor(0, 0, 0);
						    doc.text(`${$scope.lab || 'N/A'}`, 60, y + 24);

						    doc.setTextColor(0, 102, 0);
						    doc.text("Location:", 15, y + 30);
						    doc.setTextColor(0, 0, 0);
						    doc.text(`${$scope.location || 'N/A'}`, 60, y + 30);

						    // Right Side Fields (Above Table)
						    doc.setTextColor(0, 102, 0);
						    doc.text("Schedule Date:", rightColumnX, y + 6);
						    doc.setTextColor(0, 0, 0);
						    doc.text(`${$scope.ScheduleDate || 'N/A'}`, rightColumnX + 40, y + 6);

						    doc.setTextColor(0, 102, 0);
						    doc.text("Closed Date:", rightColumnX, y + 12);
						    doc.setTextColor(0, 0, 0);
						    doc.text(`${$scope.ClosedDate || 'N/A'}`, rightColumnX + 40, y + 12);

						    doc.setTextColor(0, 102, 0);
						    doc.text("Approved Date:", rightColumnX, y + 18);
						    doc.setTextColor(0, 0, 0);
						    doc.text(`${$scope.closedApprovalDate || 'N/A'}`, rightColumnX + 40, y + 18);

						    let finalY = y + 40;

						    // Checklist Table
						    if ($scope.vm.loadChecklist && $scope.vm.loadChecklist.datas && $scope.vm.loadChecklist.datas.checkpointlist) {
						        let checklist = $scope.vm.loadChecklist.datas.checkpointlist;

						        doc.autoTable({
						            startY: finalY,
						            head: [['#', 'Checklist', 'Acceptable Range', 'Value']],
						            body: checklist.map((item, index) => [
						                index + 1,
						                item.task,
						                item.acceptableRange,
						                item.status
						            ]),
						            theme: 'grid',
						            styles: {
						                fontSize: 10,
						                cellPadding: 3,
						            },
						            headStyles: {
						                fillColor: [0, 128, 0],
						                textColor: 255,
						                fontStyle: 'bold',
						            },
						            alternateRowStyles: {
						                fillColor: [235, 255, 235],
						            },
						        });

								finalY = doc.lastAutoTable.finalY + 5;

								// Calculate remaining height on the page
								let pageHeight = doc.internal.pageSize.height;
								let footerEstimatedHeight = 50; // Adjust if needed
								let availableHeight = pageHeight - finalY;

								// If not enough space, add a new page
								if (availableHeight < footerEstimatedHeight) {
								    doc.addPage();
								    finalY = 10; // Reset Y position for new page
								}

						    }

							// Footer Section Box (Adjust Height Dynamically)
							doc.setDrawColor(0);
							doc.setFillColor(235, 255, 235);

							// Define max width and line height
							const maxTextWidth = 70;
							let lineHeight = 6;

							// Split text to fit within the box
							let overallStatusLines = doc.splitTextToSize($scope.overallStatus || 'N/A', maxTextWidth);
							let executiveRemarkLines = doc.splitTextToSize($scope.ExecutiveRemark || 'N/A', maxTextWidth);

							// Calculate total height needed for text
							let overallStatusHeight = overallStatusLines.length * lineHeight;
							let executiveRemarkHeight = executiveRemarkLines.length * lineHeight;

							// Set dynamic footer height
							let footerHeight = Math.max(overallStatusHeight + executiveRemarkHeight + 20, 30);
							doc.rect(10, finalY, 190, footerHeight, "DF");

							// Left Side Fields
							doc.setTextColor(0, 102, 0);
							doc.text("Overall Status:", 15, finalY + 6);
							doc.setTextColor(0, 0, 0);
							doc.text(overallStatusLines, 50, finalY + 6);

							// Adjust "Executive Remark" dynamically
							let nextY = finalY + overallStatusHeight + 8;
							doc.setTextColor(0, 102, 0);
							doc.text("Executive Remark:", 15, nextY);
							doc.setTextColor(0, 0, 0);
							doc.text(executiveRemarkLines, 50, nextY);

							// Right Side Fields
							doc.setTextColor(0, 102, 0);
							doc.text("Closed By:", rightColumnX, finalY + 6);
							doc.setTextColor(0, 0, 0);
							doc.text(`${$scope.DoneBy || 'N/A'}`, rightColumnX + 30, finalY + 6);

							// Adjust "Approved By" dynamically
							let rightNextY = finalY + 15;
							doc.setTextColor(0, 102, 0);
							doc.text("Approved By:", rightColumnX, rightNextY);
							doc.setTextColor(0, 0, 0);
							doc.text(`${$scope.ApprovalBy || 'N/A'}`, rightColumnX + 30, rightNextY);

							// Outer Border (Match Footer Box)
							let finalBorderHeight = finalY + footerHeight + 5; // Adjusted height
							doc.setDrawColor(0);
							doc.rect(5, 5, 200, finalBorderHeight, "S");

							// Save the PDF
							doc.save(`PPM_Sheet.pdf`);
						};


						
						function loadChecklist(maintId) {
									    var url = baseUrl + "/maint/getClosedApproved/" + maintId;
									    $http.get(url)
									        .then(function(response) {
									            vm.loadChecklist = response.data; 
									            console.log("Fetched checklist:", vm.loadChecklist);
									        })
									        .catch(function(error) {
									            console.error("Error fetching checklist:", error);
									        });
									}
		  

		function fetchtotalApproved() {
			const url = `${baseUrl}/maint/getClosedApprovals`;
			genericFactory.getAll("", url).then(response => {
				vm.totalApproved = response.data.datas;
					 vm.filteredComplaints = vm.totalApproved;
				console.log(" totalApproved :", vm.totalApproved);
			}).catch(error => {
				console.error('Error fetching   totalApproved:', error);
			});
		}
		
		 function filterComplaints(){
			  
			        if (vm.fromDate && vm.toDate) {
			            const fromDate = formatDate(vm.fromDate);
			            const toDate = formatDate(vm.toDate);
			            
			            console.log("Formatted From Date:", fromDate);
			            console.log("Formatted To Date:", toDate);
			            
			            const url = `${baseUrl}/maint/getClosedApprovalsByDate?fromDate=${fromDate}&toDate=${toDate}`;
			            $http.get(url).then(function(response) {
			                const filteredData = response.data.datas;
			                vm.filteredComplaints = filteredData;
			                console.log("Filtered approved:", vm.filteredComplaints);
			            }).catch(function(error) {
			                console.error('Error filtering approved:', error);
			            });
			        } else {
			            vm.filteredComplaints = vm.totalApproved;
			            console.log("Showing all approved data:", vm.filteredComplaints);
			        }
			    };
			
			 function formatDate(date) {
			        var d = new Date(date);
			        var day = String(d.getDate()).padStart(2, "0");
			        var month = String(d.getMonth() + 1).padStart(2, "0");
			        var year = d.getFullYear();
			        return year + "-" + month + "-" + day;
			    }
		
				function exportToExcel() {
				    const url = `${baseUrl}/maint/exportClosedApprovals`;
				    
				    $http({
				        method: 'GET',
				        url: url,
				        responseType: 'arraybuffer' // Ensures binary file handling
				    }).then(function(response) {
				        var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
				        var link = document.createElement('a');
				        link.href = window.URL.createObjectURL(blob);
				        link.download = 'ClosedApprovals.xlsx';
				        link.click();
				    }).catch(function(error) {
				        console.error('Error exporting to Excel:', error);
				    });
				}

				
								
							
		
		
	}


	
	function totalUnApprovedController($scope, ApiEndpoint, $state, genericFactory, $http) {

			const baseUrl = ApiEndpoint.url;

			var vm = angular.extend(this, {
				filterComplaints:filterComplaints,
				exportToExcel:exportToExcel,
				addNew: addNew,
	            cancel: cancel,
				add: add,
				
			});
			vm.totalUnApproved = [];
			 vm.filteredComplaints = [];
				      vm.fromDate = null;
					 vm.toDate = null;
			vm.machienNames = [];
			activate();

			function activate() {
				console.log("Activating totalUnApprovedController");
				fetchtotalUnApproved();
				$scope.pendingApprovals = {};
							$scope.addNewTab = false;
			}
		

			function fetchtotalUnApproved() {
				const url = `${baseUrl}/maint/getUnApprovals`;
				genericFactory.getAll("", url).then(response => {
					vm.totalUnApproved = response.data.datas;
						 vm.filteredComplaints = vm.totalUnApproved;
					console.log(" totalUnApproved :", vm.totalUnApproved);
				}).catch(error => {
					console.error('Error fetching   totalUnApproved:', error);
				});
			}
			
			function cancel() {
							          $scope.addNewTab = false;
							      }

							      function addNew() {
							          $scope.addNewTab = true;
							         
							      }
								  
								  
								  
								  function add(approval) {
								  	    $scope.addNewTab = true; 
								  	    $scope.equipment = approval.machine.eqid; 
										$scope.overallStatus = approval.overall_status;
								  	    $scope.pendingApprovals = Object.assign({}, approval);
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
			
			 function filterComplaints(){
				  
				        if (vm.fromDate && vm.toDate) {
				            const fromDate = formatDate(vm.fromDate);
				            const toDate = formatDate(vm.toDate);
				            
				            console.log("Formatted From Date:", fromDate);
				            console.log("Formatted To Date:", toDate);
				            
				            const url = `${baseUrl}/maint/getUnApprovalsByDate?fromDate=${fromDate}&toDate=${toDate}`;
				            $http.get(url).then(function(response) {
				                const filteredData = response.data.datas;
				                vm.filteredComplaints = filteredData;
				                console.log("Filtered Unapproved:", vm.filteredComplaints);
				            }).catch(function(error) {
				                console.error('Error filtering Unapproved:', error);
				            });
				        } else {
				            vm.filteredComplaints = vm.totalUnApproved;
				            console.log("Showing all Unapproved data:", vm.filteredComplaints);
				        }
				    };
				
				 function formatDate(date) {
				        var d = new Date(date);
				        var day = String(d.getDate()).padStart(2, "0");
				        var month = String(d.getMonth() + 1).padStart(2, "0");
				        var year = d.getFullYear();
				        return year + "-" + month + "-" + day;
				    }
			
					
					
									
					function exportToExcel() {
								    const url = `${baseUrl}/maint/exportunApprovals`;
								    
								    $http({
								        method: 'GET',
								        url: url,
								        responseType: 'arraybuffer' // Ensures binary file handling
								    }).then(function(response) {
								        var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
								        var link = document.createElement('a');
								        link.href = window.URL.createObjectURL(blob);
								        link.download = 'UnApprovals.xlsx';
								        link.click();
								    }).catch(function(error) {
								        console.error('Error exporting to Excel:', error);
								    });
								}
				
			
			
		}








	function totalMaintainceController($scope,toastr, ApiEndpoint, $state, genericFactory, $http) {

		const baseUrl = ApiEndpoint.url;

		var vm = angular.extend(this, {
			filterComplaints: filterComplaints,
					onFilterTypeChange: onFilterTypeChange,
					
					
		});
		vm.totalMaintaince = [];
		 vm.filteredComplaints = [];
		 vm.filterType = "";
		 vm.selectedMonth = "";
		 vm.selectedLabId = "";
		 vm.selectedYear = "";
		 vm.months = [
		 	{ name: "January", value: "01" },
		 	{ name: "February", value: "02" },
		 	{ name: "March", value: "03" },
		 	{ name: "April", value: "04" },
		 	{ name: "May", value: "05" },
		 	{ name: "June", value: "06" },
		 	{ name: "July", value: "07" },
		 	{ name: "August", value: "08" },
		 	{ name: "September", value: "09" },
		 	{ name: "October", value: "10" },
		 	{ name: "November", value: "11" },
		 	{ name: "December", value: "12" }
		 ];

		 var currentYear = new Date().getFullYear();
		 vm.years = [currentYear - 1, currentYear, currentYear + 1];

			      vm.fromDate = null;
   				 vm.toDate = null;
		vm.machienNames = [];
		activate();
		
		function onFilterTypeChange() {
				
				vm.selectedMonth = "";
				vm.selectedLabId -"";
				vm.selectedYear = "";
				vm.fromDate = null;
				vm.toDate = null;
			}

		function activate() {
			console.log("Activating totalMaintainceController");
			
			loadLabs();
		}
	
		vm.exportToExcel = function () {
		    if (!vm.filteredComplaints || !vm.filteredComplaints.maintList || vm.filteredComplaints.maintList.length === 0) {
		        toastr.error('No data available for export!');
		        return;
		    }

			var exportData = vm.filteredComplaints.maintList.map(function (maint, index) {
			    return {
			        "Sr.No": index + 1,
			        "Machine Name": maint.machine.machine_name + " - " + maint.machine.eqid,
			        "Overall Status": maint.overall_status,
			        "Location": maint.machine.location,
			        "Lab": maint.lab.labName,
			        "Frequency": maint.frequency,
			        "Executive Remark":
			            ((maint.statusCode === 2 && maint.approvalBit === 1) ||
			             (maint.statusCode === 1 && maint.approvalBit === 2))
			            ? maint.executiveRemark : 'Pending Approval',
			        "PPM Status": $scope.getPPMStatus(maint),
			        "Raised By": (maint.raisedBy ? maint.raisedBy.firstName + " " + maint.raisedBy.lastName : 'N/A'),
			        "Closed By": (maint.done_by ? maint.done_by.firstName + " " + maint.done_by.lastName : 'N/A'),

			        "Total Counts": vm.getCountForStatus(maint.machine.machine_name, 'Total'),
			        "Total Open Counts": vm.getCountForStatus(maint.machine.machine_name, 'TotalOpen'),
			        "OpenClosed Counts": vm.getCountForStatus(maint.machine.machine_name, 'OpenClosed'),
			        "OverdueClosed Counts": vm.getCountForStatus(maint.machine.machine_name, 'OverdueClosed'),
			        "Approved Counts": vm.getCountForStatus(maint.machine.machine_name, 'Approved'),
			        "Unapproved Counts": vm.getCountForStatus(maint.machine.machine_name, 'Unapproved'),

			        "Schedule Date": formatDate(maint.schedule_date),
			        "Closed Date": maint.closedDate ? formatDate(maint.closedDate) : 'N/A',
			        "Approved Date":
			            (maint.statusCode === 2 && maint.approvalBit === 1) ? formatDate(maint.closedApprovalDate) : 'N/A',
			        "Unapproved Date":
			            (maint.statusCode === 1 && maint.approvalBit === 2) ? formatDate(maint.unApprovalDate) : 'N/A'
			    };
			});

		    // Convert JSON to worksheet
		    var worksheet = XLSX.utils.json_to_sheet(exportData);

		    // Create workbook
		    var workbook = XLSX.utils.book_new();
		    XLSX.utils.book_append_sheet(workbook, worksheet, "Maintenance Report");

		    // Save as Excel file
		    var excelBuffer = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
		    var blob = new Blob([excelBuffer], { type: 'application/octet-stream' });
		    saveAs(blob, 'Maintenance_Report.xlsx');
		};

		// Helper for date formatting
		function formatDate(date) {
		    if (!date) return '';
		    var d = new Date(date);
		    if (isNaN(d.getTime())) return '';
		    var day = ('0' + d.getDate()).slice(-2);
		    var month = ('0' + (d.getMonth() + 1)).slice(-2);
		    var year = d.getFullYear();
		    return `${day}-${month}-${year}`;
		}

		function filterComplaints() {
		    const filterType = vm.selectedFilterType;
		    console.log("Selected Filter Type: ", vm.selectedFilterType);
		    console.log("Month:", vm.selectedMonth, "Year:", vm.selectedYear);
		    console.log("From:", vm.fromDate, "To:", vm.toDate);
		    console.log("Selected Lab ID:", vm.selectedLabId);

		    let url = `${baseUrl}/maint/getMaintReported`;

		    // Handle the various filter types
			if (filterType === 'month') {
			    if (!vm.selectedMonth || !vm.selectedYear) {
			        alert("Please select month and year.");
			        return;
			    }
			    url += `?month=${vm.selectedMonth}&year=${vm.selectedYear}`;

			    if (vm.selectedLabId) {
			        url += `&labId=${vm.selectedLabId}`;
			    }
			}
				 else if (filterType === 'year') {
		        if (!vm.selectedYear) {
		            alert("Please select year.");
		            return;
		        }
		        url += `?year=${vm.selectedYear}`;

			} else if (filterType === 'dateRange') {
			    if (!vm.fromDate || !vm.toDate) {
			        alert("Please select from date and to date.");
			        return;
			    }
			    const fromDate = formatDate(vm.fromDate);
			    const toDate = formatDate(vm.toDate);
			    url += `?fromDate=${fromDate}&toDate=${toDate}`;

			    if (vm.selectedLabId) {
			        url += `&labId=${vm.selectedLabId}`;
			    }


		    } else if (filterType === 'allPpm') {
		        url += `?allPpm=true`;

		    } else if (filterType === 'lab') {
		        if (!vm.selectedLabId) {
		            alert("Please select a lab.");
		            return;
		        }
		        url += `?labId=${vm.selectedLabId}`;

		    } else {
		        alert("Please select a valid filter type or lab.");
		        return;
		    }

		    // Call the function to fetch the filtered data
		    fetchFilteredData(url);
		}
		
				
		vm.getCountForStatus = function(machineName, status) {
		  let summaryList = vm.filteredComplaints.machineWiseStatusSummary[machineName];
		  if (!summaryList) return 0;

		  let match = summaryList.find(s => s.status === status);
		  return match ? match.count : 0;
		};

		
		$scope.getPPMStatus = function(maint) {
		  const scheduleDate = maint.schedule_date ? new Date(maint.schedule_date) : null;
		  const closedDate = maint.closedDate ? new Date(maint.closedDate) : null;
		  const approvalBit = parseInt(maint.approvalBit); // Ensure it's a number

		  if (maint.statusCode === 1 && approvalBit === 0) {
		    return 'Open PPM';
		  } else if (
		    maint.statusCode === 0 &&
		    (approvalBit === 0 || approvalBit === 2) &&
		    scheduleDate &&
		    closedDate &&
		    scheduleDate < closedDate
		  ) {
		    return 'OverdueClosed PPM';
		  } else if (
		    maint.statusCode === 0 &&
		    approvalBit === 0 &&
		    scheduleDate &&
		    closedDate &&
		    scheduleDate > closedDate
		  ) {
		    return 'OpenClosed PPM';
		  } else if (
		    maint.statusCode === 0 &&
		    approvalBit === 0 &&
		    scheduleDate &&
		    closedDate &&
		    scheduleDate.getTime() === closedDate.getTime()
		  ) {
		    return 'Closed PPM';
		  } else if (maint.statusCode === 2 && approvalBit === 1) {
		    return 'Approved PPM';
		  } else if (maint.statusCode === 1 && approvalBit === 2) {
		    return 'Unapproved PPM';
		  } else {
		    return 'N/A';
		  }
		};



		function fetchFilteredData(url) {
		    $http.get(url).then(function(response) {
		        vm.filteredComplaints = response.data;
		        console.log("Filtered data:", vm.filteredComplaints);
			
				console.log("Maint list:", vm.filteredComplaints.maintList);

		    }).catch(function(error) {
		        console.error('Error fetching filtered data:', error);
		    });
		}

		function loadLabs() {
		           var url = baseUrl + "/lab/all";
		           $http.get(url)
		               .then(function(response) {
		                   vm.labs = response.data;
		                   console.log("Fetched labs:", vm.labs);
		               })
		               .catch(function(error) {
		                   console.error("Error fetching labs:", error);
		               });
		       }
		
		
	
			 function formatDate(date) {
			        var d = new Date(date);
			        var day = String(d.getDate()).padStart(2, "0");
			        var month = String(d.getMonth() + 1).padStart(2, "0");
			        var year = d.getFullYear();
			        return year + "-" + month + "-" + day;
			    }
		
				
					
	}



	function openMaintainceController($scope, ApiEndpoint, $state, genericFactory, $http) {

		const baseUrl = ApiEndpoint.url;

		var vm = angular.extend(this, {});
		vm.openMaintaince = [];
		vm.machienNames = [];

		activate();

		vm.labels = { 'srNo': 'Sr No', 'machine.machine_name': 'Machine', 'machine.eqid': 'Machine Is', 'machine.location': 'Location', 'frequency': 'Frequency', 'raisedName': 'RaisedBy', 'week': 'Week', 'scheduleDateStr': 'Date' }
		$scope.export = function() {
			console.log(" openMaintaince:", vm.openMaintaince.length);
			setTimeout(() => {
				document.getElementById('btnExport').click();
			}, "1000");
		
		}

		function activate() {
			console.log("Activating openMaintainceController");
			fetchopenMaintaince();
		}
		$scope.filterData = function(machineName) {
			console.log(" machineName:", machineName);
			const url = `${baseUrl}/maint/getCurrentWeekOpenMaintenenceByMachineName?machineName=` + machineName;
			genericFactory.getAll("", url).then(response => {
				vm.openMaintaince = response.data;
				console.log(" openMaintaince:", vm.openMaintaince);

			}).catch(error => {
				console.error('Error fetching   openMaintaince:', error);
			});
		}
		function fetchopenMaintaince() {
			const url = `${baseUrl}/maint/getCurrentWeekOpenMaintenence`;
			genericFactory.getAll("", url).then(response => {
				vm.openMaintaince = response.data;
				console.log(" openMaintaince:", vm.openMaintaince);
				updateListMachine(vm.openMaintaince)
			}).catch(error => {
				console.error('Error fetching   openMaintaince:', error);
			});
		}

		function updateListMachine(openMaintaince) {
			angular.forEach(openMaintaince, function(item) {
				console.log(" ITEM :", JSON.stringify(item.machine.machine_name));
				var str = item.machine.machine_name;
				if (!vm.machienNames.includes(str)) {
					// Add the string to the array if it's not a duplicate
					vm.machienNames.push(str);
				}
			});
			console.log(" ITEM :", JSON.stringify(vm.machienNames));
		}
		function exportJsonToExcel(jsonData, customColumns, fileName) {
			// Map JSON data to custom columns
			const dataWithCustomColumns = jsonData.map(item => {
				const newItem = {};
				for (const key in item) {
					if (customColumns[key]) {
						newItem[customColumns[key]] = item[key];
					} else {
						newItem[key] = item[key]; // Keep original if no mapping exists
					}
				}
				return newItem;
			});

			// Convert mapped data to worksheet
			const worksheet = XLSX.utils.json_to_sheet(dataWithCustomColumns);

			// Create a new workbook
			const workbook = XLSX.utils.book_new();

			// Append the worksheet to the workbook
			XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");

			// Generate Excel file
			XLSX.writeFile(workbook, fileName + ".xlsx");
		}
	}


	function closedMaintainceController($scope, ApiEndpoint, $state, genericFactory, $http) {

		const baseUrl = ApiEndpoint.url;

		var vm = angular.extend(this, {});
		vm.closedMaintaince = [];
		vm.machienNames = [];
		activate();

		function activate() {
			console.log("Activating closedMaintainceController");
			fetchclosedMaintaince();
		}
		vm.labels = { 'srNo': 'Sr No', 'machine.machine_name': 'Machine', 'machine.eqid': 'Machine Id', 'machine.location': 'Location', 'frequency': 'Frequency', 'raisedName': 'RaisedBy', 'doneName': 'Done By', 'week': 'Week', 'overall_status': 'overall_status', 'scheduleDateStr': 'Date', 'closedDateStr': ' Closed Date' }
		$scope.export = function() {
			document.getElementById('btnExport').click();
		}
		$scope.filterData = function(machineName) {
			const url = `${baseUrl}/maint/getCurrentWeekClosedMaintenenceByMachine?machineName=` + machineName;
			genericFactory.getAll("", url).then(response => {
				vm.closedMaintaince = response.data;
				updateListMachine(vm.closedMaintaince)
				console.log(" closedMaintaince:", vm.closedMaintaince);
			}).catch(error => {
				console.error('Error fetching   closedMaintaince:', error);
			});
		}
		function fetchclosedMaintaince() {
			const url = `${baseUrl}/maint/getCurrentWeekClosedMaintenence`;
			genericFactory.getAll("", url).then(response => {
				vm.closedMaintaince = response.data;
				updateListMachine(vm.closedMaintaince)
				console.log(" closedMaintaince:", vm.closedMaintaince);
			}).catch(error => {
				console.error('Error fetching   closedMaintaince:', error);
			});
		}
		function updateListMachine(TodayMaintaince) {
			angular.forEach(TodayMaintaince, function(item) {
				console.log(" ITEM :", JSON.stringify(item.machine.machine_name));
				var str = item.machine.machine_name;
				if (!vm.machienNames.includes(str)) {
					// Add the string to the array if it's not a duplicate
					vm.machienNames.push(str);
				}
			});
			console.log(" ITEM :", JSON.stringify(vm.machienNames));
		}
	}
	
	
	function totalclosedMaintainceController($scope, ApiEndpoint, $state, genericFactory, $http) {

		const baseUrl = ApiEndpoint.url;

		var vm = angular.extend(this, {
			filterComplaints:filterComplaints,
			exportToExcel:exportToExcel,
							addNew: addNew,
				            cancel: cancel,
							add: add,
							getMaintImages:getMaintImages
			
		});
		vm.totalclosedMaintaince = [];
		 vm.filteredComplaints = [];
			      vm.fromDate = null;
   				 vm.toDate = null;
		vm.machienNames = [];
		activate();

		function activate() {
			console.log("Activating totalclosedMaintainceController");
			fetchclosedMaintaince();
			$scope.pendingApprovals = {};
			$scope.addNewTab = false;
		}
		

		   function getMaintImages(maintId) {
		       $http.get(baseUrl + "/maint/getMaintImages/" + maintId)
		           .then(function(response) {
		               vm.selectedMaintImages = response.data;
		               vm.showImagesModal = true;
		               vm.selectedMaintIdForImages = maintId;
		           })
		           .catch(function(error) {
		               console.error("Error fetching images:", error);
		               alert("Could not load images.");
		           });
		   }

		   vm.getMaintImages = getMaintImages;

		   vm.downloadMaintImage = function(imageId) {
		       const downloadUrl = baseUrl + "/maint/downloadMaintImage?imageId=" + imageId;

		       fetch(downloadUrl, {
		           method: 'GET',
		           headers: {
		               // only if JWT is required
		               'Authorization': 'Bearer ' + localStorage.getItem("token")
		           }
		       })
		       .then(response => {
		           if (!response.ok) {
		               throw new Error("Download failed: " + response.statusText);
		           }

		           // Extract filename from Content-Disposition
		           const disposition = response.headers.get("Content-Disposition");
		           let fileName = "downloaded_file";
		           if (disposition && disposition.indexOf("filename=") !== -1) {
		               fileName = disposition.split("filename=")[1].replace(/"/g, '');
		           }

		           return response.blob().then(blob => ({ blob, fileName }));
		       })
		       .then(({ blob, fileName }) => {
		           const url = window.URL.createObjectURL(blob);
		           const link = document.createElement('a');
		           link.href = url;
		           link.download = fileName;  // correct filename
		           document.body.appendChild(link);
		           link.click();
		           document.body.removeChild(link);
		           window.URL.revokeObjectURL(url);
		       })
		       .catch(error => {
		           console.error("Error downloading image:", error);
		           alert("Download failed!");
		       });
		   };

		 


		
		
		
		
		
		//for maint table
		$scope.isOutOfRange = function(maint) {
		    if (!maint || !maint.checkpointlist || !Array.isArray(maint.checkpointlist)) {
		        return false;
		    }
		    for (let i = 0; i < maint.checkpointlist.length; i++) {
		        const checkpointItem = maint.checkpointlist[i];
		        const status = parseFloat(checkpointItem.status);
		        const lower = parseFloat(checkpointItem.checkpoint.lower_range);
		        const upper = parseFloat(checkpointItem.checkpoint.upper_range);
		        if (!isNaN(status) && !isNaN(lower) && !isNaN(upper)) {
		            if (status < lower || status > upper) {
		                return true;
		            }
		        }
		    }

		    return false;
		};


		//for checkpoint table
		$scope.isOutOfRangeCheckpoint = function(checkpointItem) {
		    if (!checkpointItem || !checkpointItem.checkpoint) return false;

		    const status = parseFloat(checkpointItem.status);
		    const lower = parseFloat(checkpointItem.checkpoint.lower_range);
		    const upper = parseFloat(checkpointItem.checkpoint.upper_range);

		    if (!isNaN(status) && !isNaN(lower) && !isNaN(upper)) {
		        return status < lower || status > upper;
		    }

		    return false;
		};
		
		
		$scope.getPPMStatus = function(maint) {
		  const scheduleDate = maint.schedule_date ? new Date(maint.schedule_date) : null;
		  const closedDate = maint.closedDate ? new Date(maint.closedDate) : null;
		  const approvalBit = parseInt(maint.approvalBit); // Ensure it's a number

		  if (maint.statusCode === 1 && approvalBit === 0) {
		    return 'Open PPM';
		  } else if (
		    maint.statusCode === 0 &&
		    (approvalBit === 0 || approvalBit === 2) &&
		    scheduleDate &&
		    closedDate &&
		    scheduleDate < closedDate
		  ) {
		    return 'OverdueClosed PPM';
		  } else if (
		    maint.statusCode === 0 &&
		    approvalBit === 0 &&
		    scheduleDate &&
		    closedDate &&
		    scheduleDate > closedDate
		  ) {
		    return 'OpenClosed PPM';
		  } else if (
		    maint.statusCode === 0 &&
		    approvalBit === 0 &&
		    scheduleDate &&
		    closedDate &&
		    scheduleDate.getTime() === closedDate.getTime()
		  ) {
		    return 'Closed PPM';
		  } else if (maint.statusCode === 2 && approvalBit === 1) {
		    return 'Approved PPM';
		  } else if (maint.statusCode === 1 && approvalBit === 2) {
		    return 'Unapproved PPM';
		  } else {
		    return 'N/A';
		  }
		};

				

		
		function fetchclosedMaintaince() {
			const url = `${baseUrl}/maint/getMaintenanceWithCheckValidation`;
			genericFactory.getAll("", url).then(response => {
				vm.totalclosedMaintaince = response.data;
				 vm.filteredComplaints = vm.totalclosedMaintaince;
				//updateListMachine(vm.totalclosedMaintaince)
				console.log(" Total_closed_Maintaince:", vm.filteredComplaints);
				console.log("closedMaintaince:", vm.totalclosedMaintaince);
				console.log("Sample maint object:", vm.filteredComplaints[0]);


			}).catch(error => {
				console.error('Error fetching   TotalclosedMaintaince:', error);
			});
		}
		
		
		function cancel() {
								          $scope.addNewTab = false;
								      }

								      function addNew() {
								          $scope.addNewTab = true;
								         
								      }
									  
									  
									  
									  function add(approval) {
									  	    $scope.addNewTab = true; 
									  	    $scope.equipment = approval.machine.eqid; 
									  	    $scope.pendingApprovals = Object.assign({}, approval);
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
		
												function formatDate(date) {
												    var d = new Date(date);
												    var day = String(d.getDate()).padStart(2, "0");
												    var month = String(d.getMonth() + 1).padStart(2, "0");
												    var year = d.getFullYear();
												    return year + "-" + month + "-" + day;
												}

												function filterComplaints() {
												    if (vm.fromDate && vm.toDate) {
												        const fromDate = formatDate(vm.fromDate);
												        const toDate = formatDate(vm.toDate);
												        const url = `${baseUrl}/maint/date/total_closedmaintenance?fromDate=${fromDate}&toDate=${toDate}`;

												        console.log("Requesting:", url);

												        $http.get(url).then(function(response) {
												            vm.filteredComplaints = response.data;
												            console.log("Filtered data received:", vm.filteredComplaints);
												        }).catch(function(error) {
												            console.error("Error loading filtered complaints:", error);
												        });
												    } else {
												        vm.filteredComplaints = vm.totalclosedMaintaince;
												        console.log("Fallback to all complaints:", vm.filteredComplaints);
												    }
												}

												// Bind to vm
												vm.filterComplaints = filterComplaints;

	
		
				function exportToExcel() {
				    if (!vm.filteredComplaints || vm.filteredComplaints.length === 0) {
				        toastr.error('No data available for export!');
				        return;
				    }

				    // Format each row from filteredComplaints
				    var formattedData = vm.filteredComplaints.map(function(item, index) {
				        return {
				            'Sr No': index + 1,
				            'Machine Name': item.machine?.machine_name || '',
				            'Machine ID': item.machine?.eqid || '',
				            'Location': item.machine?.location || '',
				            'Frequency': item.frequency || '',
				            'Lab': item.lab?.labName || '',
				            'PPM Status': $scope.getPPMStatus(item),
				            'Done By': item.done_by ? item.done_by.firstName + ' ' + item.done_by.lastName : '',
				            'Overall Status': item.overall_status || '',
				            'Scheduled Date': formatDate(item.schedule_date),
				            'Closed Date': formatDate(item.closedDate)
				        };
				    });

				    var ws = XLSX.utils.json_to_sheet(formattedData);
				    var wb = XLSX.utils.book_new();
				    XLSX.utils.book_append_sheet(wb, ws, "Closed PPM");

				    XLSX.writeFile(wb, "Closed_PPM.xlsx");
				}

			
				

							
		
		
	}
	
	function totalopenMaintainceController($scope, ApiEndpoint, $state, genericFactory, $http) {

		const baseUrl = ApiEndpoint.url;

		var vm = angular.extend(this, {
			filterComplaints:filterComplaints,
			exportToExcel: exportToExcel
		});
		vm.totalopenMaintaince = [];
		 vm.filteredComplaints = [];
			      vm.fromDate = null;
   				 vm.toDate = null;
		vm.machienNames = [];
		activate();

		function activate() {
			console.log("Activating totalopenMaintainceController");
			fetchopenMaintaince();
		}
		
		
		function fetchopenMaintaince() {
			const url = `${baseUrl}/maint/total_openmaintenance`;
			genericFactory.getAll("", url).then(response => {
				vm.totalopenMaintaince = response.data;
				   vm.filteredComplaints = vm.totalopenMaintaince;
				//updateListMachine(vm.openMaintaince)
				console.log(" totalopenMaintaince:", vm.totalopenMaintaince);
			}).catch(error => {
				console.error('Error fetching   totalopenMaintaince:', error);
			});
		}
		
		 function filterComplaints(){
			  
			        if (vm.fromDate && vm.toDate) {
			            const fromDate = formatDate(vm.fromDate);
			            const toDate = formatDate(vm.toDate);
			            
			            console.log("Formatted From Date:", fromDate);
			            console.log("Formatted To Date:", toDate);
			            
			            const url = `${baseUrl}/maint/total_open_maintenance?fromDate=${fromDate}&toDate=${toDate}`;
			            $http.get(url).then(function(response) {
			                const filteredData = response.data;
			                vm.filteredComplaints = filteredData;
			                console.log("Filtered complaints:", vm.filteredComplaints);
			            }).catch(function(error) {
			                console.error('Error filtering complaints:', error);
			            });
			        } else {
			            vm.filteredComplaints = vm.totalopenMaintaince;
			            console.log("Showing all complaints data:", vm.filteredComplaints);
			        }
			    };
			
			 function formatDate(date) {
			        var d = new Date(date);
			        var day = String(d.getDate()).padStart(2, "0");
			        var month = String(d.getMonth() + 1).padStart(2, "0");
			        var year = d.getFullYear();
			        return year + "-" + month + "-" + day;
			    }
		
		
	function exportToExcel() {
    if (!vm.filteredComplaints || vm.filteredComplaints.length === 0) {
        toastr.error('No data available for export!');
        return;
    }

    // Format the data for export
    var formattedData = vm.filteredComplaints.map(function(item, index) {
        return {
            'Sr No': index + 1,
            'Machine Name': item.machine.machine_name, // Nested machine name
            'Machine Id': item.machine.eqid,           // Nested machine ID
            'Location': item.machine.location, 
			'Lab': item.lab.labName,        // Nested machine location
            'Frequency': item.frequency,               // Frequency
            'Raised By': item.raisedBy ? item.raisedBy.firstName + ' ' + item.raisedBy.lastName : '',  // Full name from raisedBy object
            'Scheduled Date': formatDate(item.schedule_date)  // Format schedule date to string
                   // Overdue (check if available, else 'No')
        };
    });

    // Create a worksheet from the formatted data
    var ws = XLSX.utils.json_to_sheet(formattedData);

    // Create a workbook
    var wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, "Open Maintenance");

    // Export to Excel
    XLSX.writeFile(wb, "openMaintenance.xlsx");
}

        
        


			
	
	}


	function TodayMaintainceController($scope, ApiEndpoint, $state, genericFactory, $http) {

		const baseUrl = ApiEndpoint.url;

		var vm = angular.extend(this, {
			filterComplaints:filterComplaints,
			exportToExcel: exportToExcel
		});
		vm.TodayMaintaince = [];
		 vm.filteredComplaints = [];
			      vm.fromDate = null;
   				 vm.toDate = null;
		vm.machienNames = [];
		activate();

		function activate() {
			console.log("Activating TodayMaintainceController");
			fetchTodayMaintaince();
		}
		vm.labels = { 'srNo': 'Sr No', 'machine.machine_name': 'Machine', 'machine.eqid': 'Machine Is', 'machine.location': 'Location', 'frequency': 'Frequency', 'raisedName': 'RaisedBy', 'week': 'Week', 'overdues': 'Overdues' , 'scheduleDateStr': 'Date'}
		$scope.export = function() {
			document.getElementById('btnExport').click();
		}

		function fetchTodayMaintaince() {
			const url = `${baseUrl}/maint/getCurrentWeekOverduesMaintenence`;
			genericFactory.getAll("", url).then(response => {
				vm.TodayMaintaince = response.data;
					   vm.filteredComplaints = vm.TodayMaintaince;
				//updateListMachine(vm.TodayMaintaince)
				console.log(" TodayMaintaince:", vm.TodayMaintaince);
			}).catch(error => {
				console.error('Error fetching   TodayMaintaince:', error);
			});
		}
		
		 function filterComplaints(){
			  
			        if (vm.fromDate && vm.toDate) {
			            const fromDate = formatDate(vm.fromDate);
			            const toDate = formatDate(vm.toDate);
			            
			            console.log("Formatted From Date:", fromDate);
			            console.log("Formatted To Date:", toDate);
			            
			            const url = `${baseUrl}/maint/getOverduesMaintenanceByDateRange?fromDate=${fromDate}&toDate=${toDate}`;
			            $http.get(url).then(function(response) {
			                const filteredData = response.data;
			                vm.filteredComplaints = filteredData;
			                console.log("Filtered complaints:", vm.filteredComplaints);
			            }).catch(function(error) {
			                console.error('Error filtering complaints:', error);
			            });
			        } else {
			            vm.filteredComplaints = vm.TodayMaintaince;
			            console.log("Showing all complaints data:", vm.filteredComplaints);
			        }
			    };
			
			 function formatDate(date) {
			        var d = new Date(date);
			        var day = String(d.getDate()).padStart(2, "0");
			        var month = String(d.getMonth() + 1).padStart(2, "0");
			        var year = d.getFullYear();
			        return year + "-" + month + "-" + day;
			    }
		

		/*
		$scope.filterData = function(machineName) {
			const url = `${baseUrl}/maint/getCurrentWeekOverduesMaintenenceByMachine?machineName=` + machineName;
			genericFactory.getAll("", url).then(response => {
				vm.TodayMaintaince = response.data;

				console.log(" TodayMaintaince:", vm.TodayMaintaince);
			}).catch(error => {
				console.error('Error fetching   TodayMaintaince:', error);
			});
		}
		function updateListMachine(TodayMaintaince) {
			angular.forEach(TodayMaintaince, function(item) {
				console.log(" ITEM :", JSON.stringify(item.machine.machine_name));
				var str = item.machine.machine_name;
				if (!vm.machienNames.includes(str)) {
					// Add the string to the array if it's not a duplicate
					vm.machienNames.push(str);
				}
			});
			console.log(" ITEM :", JSON.stringify(vm.machienNames));
		}
		
		*/
		
		
		function exportToExcel(fromDate, toDate) {
				    const url = `${baseUrl}/maint/export/getCurrentWeekOverduesMaintenence`;
				
				   
				    const params = {};
				
				  
				    if (fromDate && toDate) {
				        params.fromDate = formatDate(fromDate);  // Pass 'fromDate'
				        params.toDate = formatDate(toDate);      // Pass 'toDate'
				    } else {
				        
				    }
				
				    console.log("Exporting with params:", params);
				
				    $http({
				        method: "GET",
				        url: url,
				        params: params,
				        responseType: "arraybuffer"
				    })
				    .then(function (response) {
				        // Create a Blob from the response data
				        var blob = new Blob([response.data], {
				            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
				        });
				
				        // Create a temporary download link and trigger download
				        var link = document.createElement("a");
				        link.href = window.URL.createObjectURL(blob);
				
				        // Set download file name based on whether dates are specified
				        link.download = (fromDate && toDate) ? "Datewise_overdue_maintenance.xlsx" : "Total_overdue_maintenance.xlsx";
				
				        // Trigger the download
				        link.click();
				    })
				    .catch(function (error) {
				        console.error("Error exporting Excel:", error);
				    });
				}

		
		
		
	}

// ByMachine // 






})();














