	(function() {
	    'use strict';
	
	    angular.module('myApp.PPMLabWise').controller('PPMLabController', PPMLabController);
	        
	    PPMLabController.$inject = ['$scope', '$http', 'ApiEndpoint', '$state', 'genericFactory', 'localStorageService'];
	
	    function PPMLabController($scope, $http, ApiEndpoint, $state, genericFactory, localStorageService) {
	
	        const baseUrl = ApiEndpoint.url;
	        var userDetail = localStorageService.get(ApiEndpoint.userKey);
	        
	        var vm = angular.extend(this, {
	            user: userDetail,
	            loadLabs: loadLabs,
	            loadPPMRecords: loadPPMRecords,         
	            onLabSelect: onLabSelect,
				showPPMDetails:showPPMDetails,
	             
				loadPPMmachines:loadPPMmachines,
	            machines: [],
	            selectedLab: null,
				selectedLabName : null,
	            OpenPPM: 0,
	            ClosedPPM: 0,
	            TrialPPM: 0,
	            AllPPM: 0,
				UnApprovalPPM : 0,
				ApprovalPPM : 0,
	            PPMDetails: [],
				addNew: addNew,
		        cancel: cancel,
				add: add
	           
	           
	        });
	    
			vm.selectedMachineCount= 5;
			
	        (function activate() {
	            console.log("Activating PPMLabController");
	            loadLabs();
				
				$scope.pendingApprovals = {};
				          
						$scope.addNewTab = false;
				
				
				vm.selectedWeek = getCurrentWeek();
							get52WeekDataByLab(vm.selectedWeek, vm.selectedLab);
							
							$scope.brekdownActive = true;
							   $scope.showTypeData = getWeekNumber(new Date());

							
							   let today = new Date();
							   let dayOfWeek = today.getDay();
							   let startOfWeek = new Date(today);
							   startOfWeek.setDate(today.getDate() - dayOfWeek);
							   let endOfWeek = new Date(today);
							   endOfWeek.setDate(today.getDate() + (6 - dayOfWeek));

							   $scope.startDate = formatDate(startOfWeek);
							   $scope.endDate = formatDate(endOfWeek);
							   
							   function formatDate(date) {
							   											    let day = String(date.getDate()).padStart(2, '0');
							   											    let month = String(date.getMonth() + 1).padStart(2, '0'); // Months are zero-based
							   											    let year = date.getFullYear();
							   											    return `${year}-${month}-${day}`;
							   											}
				
				
	        })();
			
			vm.availableWeeks = Array.from({ length: 52 }, (_, i) => i + 1);
			vm.weeks = Array.from({ length: 52 }, (_, i) => i + 1);
			
			function showPPMDetails(type) {
			           switch(type) {
			               case 'total':
			                   vm.PPMDetails = vm.AllPPM || [];
			                   break;
			             
			               case 'open':
			                   vm.PPMDetails = vm.OpenPPM || [];
			                   break;
			               case 'closed':
			                   vm.PPMDetails = vm.ClosedPPM || [];
			                   break;
			               case 'trial':
			                   vm.PPMDetails = vm.TrialPPM || [];
			                   break;
						   case 'UnApproval':
							    vm.PPMDetails = vm.UnApprovalPPM.datas || [];
							   break;
						    case 'Approval':
							     vm.PPMDetails = vm.ApprovalPPM.datas || [];
							   	 break;   
			               default:
			                   vm.PPMDetails = [];
			           }
			       }
			
				   
				   function cancel() {
				   		          $scope.addNewTab = false;
				   		      }

				   		      function addNew() {
				   		          $scope.addNewTab = true;
				   		         
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
				   					
				   					function add(approval) {
				   								    $scope.addNewTab = true; 
				   								    $scope.equipment = approval.machine.eqid; 
				   								    $scope.pendingApprovals = Object.assign({}, approval);
				   									/*vm.unapprovedPpm = "";*/
				   									
				   								    loadChecklist(approval.maint_id);
				   								}
				   

				       function loadOpenPPM(labName) {
				               var url = baseUrl + "/maint/TotalrecordsBylab?labName=" + encodeURIComponent(labName);
				               $http.get(url)
				                   .then(function(response) {
				                       vm.OpenPPM = response.data.openRecords;
				                      
				                       console.log("maintenance counts updated:", vm.OpenPPM);
				                   })
				                   .catch(function(error) {
				                       console.error("Error fetching maintenance counts:", error);
				                   });
				           }
						   
						   function loadUnApprovalPPM(labName) {
						   			               var url = baseUrl + "/maint/getUnApprovalsByLab?labName=" + encodeURIComponent(labName);
						   			               $http.get(url)
						   			                   .then(function(response) {
						   			                       vm.UnApprovalPPM = response.data;
						   			                      
						   			                       console.log("UnApproval counts updated:", vm.UnApprovalPPM);
						   			                   })
						   			                   .catch(function(error) {
						   			                       console.error("Error fetching UnApproval counts:", error);
						   			                   });
						   			           }
											   
											   
							  function loadApprovalPPM(labName) {
											   					   			               var url = baseUrl + "/maint/getClosedApprovalsByLab?labName=" + encodeURIComponent(labName);
											   					   			               $http.get(url)
											   					   			                   .then(function(response) {
											   					   			                       vm.ApprovalPPM = response.data;
											   					   			                      
											   					   			                       console.log("Approval counts updated:", vm.ApprovalPPM);
											   					   			                   })
											   					   			                   .catch(function(error) {
											   					   			                       console.error("Error fetching Approval counts:", error);
											   					   			                   });
											   					   			           }
						   
						   function loadClosedPPM(labName) {
						   			               var url = baseUrl + "/maint/total_closedmaintenanceByLab?labName=" + encodeURIComponent(labName);
						   			               $http.get(url)
						   			                   .then(function(response) {
						   			                       vm.ClosedPPM = response.data;
						   			                      
						   			                       console.log("maintenance counts updated:", vm.ClosedPPM);
						   			                   })
						   			                   .catch(function(error) {
						   			                       console.error("Error fetching maintenance counts:", error);
						   			                   });
						   			           }
											   
											   function loadTrialPPM(labName) {
	   					   			               var url = baseUrl + "/maint/TotalrecordsBylab?labName=" + encodeURIComponent(labName);
											           $http.get(url)
								                   .then(function(response) {
				  			                       vm.TrialPPM = response.data.overdueRecords;
											   					   			                      
											              console.log("maintenance counts updated:", vm.TrialPPM);
											   		          })
									           .catch(function(error) {
								                  console.error("Error fetching maintenance counts:", error);
											                });
			   					   			           }						   
			
													   function loadAllPPM(labName) {
													               var url = baseUrl + "/maint/TotalrecordsBylab?labName=" + encodeURIComponent(labName);
													               $http.get(url)
													                   .then(function(response) {
													                       vm.AllPPM = response.data.totalRecords;
													                      
													                       console.log("maintenance counts updated:", vm.AllPPM);
													                   })
													                   .catch(function(error) {
													                       console.error("Error fetching maintenance counts:", error);
													                   });
													           }								   
								//table					   
			function get52WeekDataByLab(weekno, labName) { 
			    if (!weekno) {
			        weekno = getCurrentWeek(); 
			    }
			    
				if (!vm.selectedLab) {
				    console.error("Selected lab is not defined.");
				    return;
				}


			    console.log("Selected Week:", weekno);
			    console.log("Selected Lab:", labName);

			    var url = baseUrl + "/maint/get52WeekMaintenenceByLab?weekNo=" + weekno + "&labName=" + encodeURIComponent(labName);
			    
			    genericFactory.getAll("", url).then(function(response) {
			        vm.dataWeekMachine = response.data;
			        console.log("Maintenance By Machine and Week:", JSON.stringify(vm.dataWeekMachine));
			        calculateOpenClosedCounts();
			    }).catch(function(error) {
			        console.error("Error fetching Maintenance data:", error);
			    });
			}


			
				
				function calculateOpenClosedCounts() {
			    angular.forEach(vm.dataWeekMachine, function(item) {
			        let openCount = 0;
			        let closedCount = 0;

			        // Sum open and closed data for each week's data
			        angular.forEach(item.weekDatas, function(weekData) {
			            openCount += weekData.open || 0;  
			            closedCount += weekData.closed || 0;
			        });

			       
			        item.openTotal = openCount;
			        item.closedTotal = closedCount;
			    });
			}
					
				
			        vm.selectedWeek = getCurrentWeek(); 
			        vm.availableWeeks = Array.from({ length: 52 }, (_, i) => i + 1); 

					  loadMaintenanceOverallDashaboardGraph(vm.selectedWeek);

			        // On dropdown change
			       vm.onWeekChange = function () 
			        {
			             console.log("Selected Week:", vm.selectedWeek, vm.selectedLab);
			            loadMaintenanceOverallDashaboardGraph(vm.selectedWeek);
			           get52WeekDataByLab(vm.selectedWeek,vm.selectedLab);
			        };
					
				

			        // Function to calculate the current week of the year
			        function getCurrentWeek() {
			            const date = new Date();
			            const startDate = new Date(date.getFullYear(), 0, 1);
			            const days = Math.floor((date - startDate) / (24 * 60 * 60 * 1000));
			            return Math.ceil((days + 1) / 7);
			        }

				
					function getWeekNumber(date) {		
						let currentDate = new Date(date.getTime());
						currentDate.setHours(0, 0, 0, 0);
						currentDate.setDate(currentDate.getDate() + 3 - (currentDate.getDay() + 6) % 7);
						let week1 = new Date(currentDate.getFullYear(), 0, 4);
						// Adjust to Thursday in week 1 and count number of weeks from the beginning of the year
						return 1 + Math.round(((currentDate.getTime() - week1.getTime()) / 86400000
							- 3 + (week1.getDay() + 6) % 7) / 7);
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
	        
	        function onLabSelect(labName) {
	            if (!labName) return;
	            vm.selectedLab = labName;
	            console.log("Selected Lab:", labName);
				vm.PPMDetails = [];
				loadOpenPPM(labName);
				loadAllPPM(labName);
				loadTrialPPM(labName) ;
				loadUnApprovalPPM(labName);
				loadApprovalPPM(labName);
				loadClosedPPM(labName);
	            loadPPMRecords(labName);
				loadPPMmachines(labName);
				loadMaintenanceOverallDashaboardGraph();
				 
				   get52WeekDataByLab(vm.selectedWeek, vm.selectedLab);
				
	        }
	        
	        function loadPPMRecords(labName) {
	            var url = baseUrl + "/maint/TotalrecordsBylab?labName=" + encodeURIComponent(labName);
	            $http.get(url)
	                .then(function(response) {
	                    vm.records = response.data;
	                   
	                    console.log("Maintenance counts updated:", vm.records);
	                })
	                .catch(function(error) {
	                    console.error("Error fetching Maintenance counts:", error);
	                });
	        }
			
			function loadPPMmachines(labName) {
			            var url = baseUrl + "/dashboard/total_countByLab?labName=" + encodeURIComponent(labName);
			            $http.get(url)
			                .then(function(response) {
			                    vm.counts = response.data;
			                   
			                    console.log("Machine counts updated:", vm.counts);
			                })
			                .catch(function(error) {
			                    console.error("Error fetching machine counts:", error);
			                });
			        }
					
					
				
					
					
					// Graph
					
					
					
					function loadMaintenanceOverallDashaboardGraph(selectedWeek, labName) {
							
							var selectedWeek = vm.selectedWeek || null;
							  var selectedMonth = vm.selectedMonth || null;
							  var currentYear = new Date().getFullYear();
							  var selectedLab = labName || vm.selectedLab || null;
								
								var msg = "";
								  var url = baseUrl + "/dashboard/getMaintenanceOverallDashaboardGraphByLab?year=" + currentYear;

								  if (selectedMonth) {
								      url += "&month=" + selectedMonth;
								  } else if (selectedWeek) {
								      url += "&week=" + selectedWeek;
								  }


								
								genericFactory.getAll(msg, url).then(function(response) {
									vm.maintenanceOverallGraph= response.data;
									console.log("maintenanceOverallGraph : " + JSON.stringify(vm.maintenanceCountsByMachine));
									
										var machineNames = [];
								var openCounts = [];
								var closedCounts = [];
								var overuesCounts = [];
								angular.forEach(vm.maintenanceOverallGraph, function(item) {
									machineNames.push(item.date);
									openCounts.push(item.open);
									closedCounts.push(item.closed);
									overuesCounts.push(item.overdue)
								});
								vm.chartDataOverall = [openCounts, closedCounts,overuesCounts];
								vm.chartLabelsOverall = machineNames;
								
								 vm.chartColorsOverall = [{
					            backgroundColor: 'rgba(255, 99, 132, 0.5)', // Light pink for "open"
					            borderColor: 'rgba(255, 99, 132, 1)',
					        }, {
					            backgroundColor: 'rgba(54, 162, 235, 0.5)', // Light blue for "closed"
					            borderColor: 'rgba(54, 162, 235, 1)',
					        }, {
					            backgroundColor: 'rgba(0, 139, 139, 0.5)', // Dark cyan with 50% transparency
					    borderColor: 'rgba(0, 139, 139, 1)', 
					        }];

								
								});
							}
							
							vm.updateGraphData = function (type) {
								
								if (type === 'week') {
								               vm.selectedMonth = null; // Reset month if week is selected
								           } else if (type === 'month') {
								               vm.selectedWeek = null; // Reset week if month is selected
								           }
							    console.log("Selected Week:", vm.selectedWeek);
							    console.log("Selected Month:", vm.selectedMonth);
							    loadMaintenanceOverallDashaboardGraph(selectedWeek, labName);
							
							};
							
										
				


						
							
						
	    
	}
	
	
})();
	
	
