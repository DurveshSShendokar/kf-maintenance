	(function() {
	    'use strict';
	
	    angular.module('myApp.LabDash').controller('LabDashController', LabDashController);
	        
	    LabDashController.$inject = ['$scope', '$http', 'ApiEndpoint', '$state', 'genericFactory', 'localStorageService'];
	
	    function LabDashController($scope, $http, ApiEndpoint, $state, genericFactory, localStorageService) {
	
	        const baseUrl = ApiEndpoint.url;
	        var userDetail = localStorageService.get(ApiEndpoint.userKey);
	        
	        var vm = angular.extend(this, {
	            user: userDetail,
	            loadLabs: loadLabs,
	            loadBreakdashboard: loadBreakdashboard,         
	            onLabSelect: onLabSelect,
				showBreakdownDetails:showBreakdownDetails,
	             
	            
	            machines: [],
	            selectedLab: null,
				selectedLabName : null,
	            open_breaks: 0,
	            closed_breaks: 0,
	            trial_breaks: 0,
	            total_breaks: 0,
	            repetative_breaks: 0, 
	            breakdownDetails: [],
	            showTimeline: false,
	            timelineData: []
	        });
	    
			vm.selectedMachineCount= 5;
			
	        (function activate() {
	            console.log("Activating LabDashController");
	            loadLabs();
				
				
				loadBreakdownCountsByMachine();
				   loadgetBreakdownGraphData();
				
				vm.selectedWeek = getCurrentWeek();
							get52WeekDataByLab(vm.selectedWeek, vm.selectedLab);
							
							$scope.brekdownActive = true;
							   $scope.showTypeData = getWeekNumber(new Date());

							   // Set start and end dates for the current week
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
			
			
			
			
			function showBreakdownDetails(type) {
			           switch(type) {
			               case 'total':
			                   vm.breakdownDetails = vm.AllBreaks || [];
			                   break;
			             
			               case 'open':
			                   vm.breakdownDetails = vm.OpenBreaks || [];
			                   break;
			               case 'closed':
			                   vm.breakdownDetails = vm.ClosedBreaks || [];
			                   break;
			               case 'trial':
			                   vm.breakdownDetails = vm.TrialBreaks || [];
			                   break;
			               default:
			                   vm.breakdownDetails = [];
			           }
			       }
			
				   

				       function loadOpenBreak(labName) {
				               var url = baseUrl + "/breakdown/openByLab?labName=" + encodeURIComponent(labName);
				               $http.get(url)
				                   .then(function(response) {
				                       vm.OpenBreaks = response.data;
				                      
				                       console.log("Breakdown counts updated:", vm.OpenBreaks);
				                   })
				                   .catch(function(error) {
				                       console.error("Error fetching breakdown counts:", error);
				                   });
				           }
						   
						   function loadClosedBreak(labName) {
						   			               var url = baseUrl + "/breakdown/closedByLab?labName=" + encodeURIComponent(labName);
						   			               $http.get(url)
						   			                   .then(function(response) {
						   			                       vm.ClosedBreaks = response.data;
						   			                      
						   			                       console.log("Breakdown counts updated:", vm.ClosedBreaks);
						   			                   })
						   			                   .catch(function(error) {
						   			                       console.error("Error fetching breakdown counts:", error);
						   			                   });
						   			           }
											   
											   function loadTrialBreak(labName) {
	   					   			               var url = baseUrl + "/breakdown/trialByLab?labName=" + encodeURIComponent(labName);
											           $http.get(url)
								                   .then(function(response) {
				  			                       vm.TrialBreaks = response.data;
											   					   			                      
											              console.log("Breakdown counts updated:", vm.TrialBreaks);
											   		          })
									           .catch(function(error) {
								                  console.error("Error fetching breakdown counts:", error);
											                });
			   					   			           }						   
			
													   function loadAllBreaks(labName) {
													               var url = baseUrl + "/breakdown/allByLab?labName=" + encodeURIComponent(labName);
													               $http.get(url)
													                   .then(function(response) {
													                       vm.AllBreaks = response.data;
													                      
													                       console.log("Breakdown counts updated:", vm.AllBreaks);
													                   })
													                   .catch(function(error) {
													                       console.error("Error fetching breakdown counts:", error);
													                   });
													           }								   
													   
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

			    var url = baseUrl + "/breakdown/get52WeekBreakDownByLab?weekNo=" + weekno + "&labName=" + encodeURIComponent(labName);
			    
			    genericFactory.getAll("", url).then(function(response) {
			        vm.dataWeekMachine = response.data;
			        console.log("Breakdown By Machine and Week:", JSON.stringify(vm.dataWeekMachine));
			        calculateOpenClosedCounts();
			    }).catch(function(error) {
			        console.error("Error fetching breakdown data:", error);
			    });
			}


						
						function calculateOpenClosedCounts() {
						    angular.forEach(vm.dataWeekMachine, function(item) {
						        let openCount = 0;
						        let closedCount = 0;
						         let TrialCount = 0;

						        // Sum open and closed data for each week's data
						        angular.forEach(item.weekDatas, function(weekData) {
						            openCount += weekData.open || 0;  
						            closedCount += weekData.closed || 0;
						             TrialCount += weekData.trail || 0;
						        });

						       
						        item.openTotal = openCount;
						        item.closedTotal = closedCount;
						        item.TrialCount = TrialCount;
						    });
							}
							
							$scope.gotoDetials=function(data,machineName,status){
										console.log("Data: " + JSON.stringify(data));
										console.log("machineName: " + machineName);
											$location.path('main/WeekWiseBrekdown/'+data.weekNo+'/'+machineName+'/'+status);
									}
									
									  vm.selectedWeek = getCurrentWeek(); 
							        vm.availableWeeks = Array.from({ length: 52 }, (_, i) => i + 1); 
									
									  // Function to calculate the current week of the year
							        function getCurrentWeek() {
							            const date = new Date();
							            const startDate = new Date(date.getFullYear(), 0, 1);
							            const days = Math.floor((date - startDate) / (24 * 60 * 60 * 1000));
							            return Math.ceil((days + 1) / 7);
							        }

									 // On dropdown change
			    vm.onWeekChange = function () 
							        {
							            console.log("Selected Week:", vm.selectedWeek, vm.selectedLab);
							            //loadMaintenanceOverallDashaboardGraph(vm.selectedWeek);
							            get52WeekDataByLab(vm.selectedWeek,vm.selectedLab);
							        };
								
									
										function getWeekNumber(date) {
										let currentDate = new Date(date.getTime());
										currentDate.setHours(0, 0, 0, 0);
										currentDate.setDate(currentDate.getDate() + 3 - (currentDate.getDay() + 6) % 7);
										let week1 = new Date(currentDate.getFullYear(), 0, 4);
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
				vm.breakdownDetails = [];
				loadOpenBreak(labName);
				loadAllBreaks(labName);
				loadTrialBreak(labName) ;
				loadClosedBreak(labName);
	            loadBreakdashboard(labName);
				loadBreakdashboard1(labName);
				loadBreakdownCountsByMachine(labName);  
				   loadgetBreakdownGraphData(labName);
				   get52WeekDataByLab(vm.selectedWeek, vm.selectedLab);
				
	        }
	        
	        function loadBreakdashboard(labName) {
	            var url = baseUrl + "/breakdown/breakdownstatusCountsByLab?labName=" + encodeURIComponent(labName);
	            $http.get(url)
	                .then(function(response) {
	                    vm.datas = response.data;
	                   
	                    console.log("Breakdown counts updated:", vm.datas);
	                })
	                .catch(function(error) {
	                    console.error("Error fetching breakdown counts:", error);
	                });
	        }
			
			function loadBreakdashboard1(labName) {
			            var url = baseUrl + "/dashboard/total_countByLab?labName=" + encodeURIComponent(labName);
			            $http.get(url)
			                .then(function(response) {
			                    vm.dats = response.data;
			                   
			                    console.log("Machine counts updated:", vm.dats);
			                })
			                .catch(function(error) {
			                    console.error("Error fetching machine counts:", error);
			                });
			        }
					
					
						vm.updateMachineData2 = function () {
					    vm.filteredBreakdownCountsByMachine = filterTopMachines2(vm.BreakdownCountsByMachine, vm.selectedMachineCount);
					    updateBreakdownChart();
					};

					vm.updateMachineData3 = function () {
					    vm.filteredBreakdownCountsByMachineOwner = filterTopMachines2(vm.BreakdownCountsByMachineOwner, vm.selectedMachineCount);
					    updategetBreakdownGraphDataChart();
					};

							
					function loadBreakdownCountsByMachine(labName) {
					    var msg = "";
					   	
					    var url = baseUrl + "/dashboard/getBreakdownCountsByMachineByLab?labName=" + encodeURIComponent(labName);
					    genericFactory.getAll(msg, url).then(function(response) {
					        vm.BreakdownCountsByMachine = response.data;
					        console.log("Breakdown Counts By Machine: " + JSON.stringify(vm.BreakdownCountsByMachine));
					        vm.updateMachineData2();
					    }).catch(function(error) {
					        console.error("Error fetching breakdown counts by machine:", error);
					    });
					}




							function loadgetBreakdownGraphData(labName) {
							    var msg = "";
							   	
							    var url = baseUrl + "/dashboard/getBreakdownGraphDataByLab?labName=" + encodeURIComponent(labName);
							    console.log("URL " + url);
							    genericFactory.getAll(msg, url).then(function(response) {
							        vm.BreakdownCountsByMachineOwner = response.data;
							        console.log("Breakdown Counts By Machine Owner: " + JSON.stringify(vm.BreakdownCountsByMachineOwner));
							        vm.updateMachineData3();
							    }).catch(function(error) {
							        console.error("Error fetching breakdown graph data:", error);
							    });
							}

							
							
							function updategetBreakdownGraphDataChart() {
								var machineOwners = [];
								var openCounts = [];
								var closedCounts = [];
								var trialCounts = [];

								angular.forEach(vm.filteredBreakdownCountsByMachineOwner, function(item) {
						console.log("ITEM",item)
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
									backgroundColor: 'rgba(139, 0, 0, 0.5)',
									borderColor: 'rgba(139, 0, 0, 1)',
								}, {
									backgroundColor: 'rgba(0, 0, 139, 0.5)',
									borderColor: 'rgba(0, 0, 139, 1)',
								}, {
									backgroundColor: 'rgba(0, 100, 0, 0.5)',
									borderColor: 'rgba(0, 100, 0, 1)',
								}];
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
									backgroundColor: 'rgba(139, 0, 0, 0.5)',
									borderColor: 'rgba(139, 0, 0, 1)',
								}, {
									backgroundColor: 'rgba(0, 0, 139, 0.5)',
									borderColor: 'rgba(0, 0, 139, 1)',
									
									
								}, {
									backgroundColor: 'rgba(0, 100, 0, 0.5)',
									borderColor: 'rgba(0, 100, 0, 1)',
								}];
							}
							
							
							
							function filterTopMachines2(data, count) {

									//console.log('Original Data:', JSON.stringify(data, null, 2));
									const sortedData = data.slice().sort((a, b) => {
										const aTotal = a.open + a.closed + a.trail;
										const bTotal = b.open + b.closed + b.trail;
										return bTotal - aTotal;
									});
									//console.log('Sorted Data:', JSON.stringify(sortedData, null, 2));

									const filteredData = sortedData.slice(0, count);

									//console.log('Filtered Data:', JSON.stringify(filteredData, null, 2));

									return filteredData;
								}
	    
	}
	
	
})();
	
	
