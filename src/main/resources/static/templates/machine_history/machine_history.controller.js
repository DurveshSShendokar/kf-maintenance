(function() {
    'use strict';

    angular.module('myApp.machine_history').controller('machine_historyController', machine_historyController);
        
    machine_historyController.$inject = ['$scope', '$http', 'ApiEndpoint', 'toastr', '$state', 'genericFactory', 'localStorageService'];

    function machine_historyController($scope, $http, ApiEndpoint, toastr, $state, genericFactory, localStorageService) {

        const baseUrl = ApiEndpoint.url;
        var userDetail = localStorageService.get(ApiEndpoint.userKey);
        
        var vm = angular.extend(this, {
            user: userDetail,
            loadMachines: loadMachines,
            loadBreakdashboard: loadBreakdashboard,         
            onMachineSelect: onMachineSelect,
            
		
            machines: [],
            ATRepo: {},
            ATRepo2: [] ,        
		    selectedmachine :null, 
		    open_breaks : {},
		    closed_breaks : {},
		    trial_breaks : {},
		    total_breaks : {},
		    repetative_breaks : {}, 
		  
		    showTimeline: false,
            timelineData: [],timelineData :[]
        });
     
        (function activate() {
            console.log("Activating machine_historyController");
            loadMachines();
        })();
        
        
		function onMachineSelect(machineId) {
		    console.log(">>> onMachineSelect called with:", machineId);
		    if (!machineId) return;

		    // Set selectedmachine so UI shows counts
		    vm.selectedmachine = machineId;

		   
		    loadBreakdashboard(machineId);
		    fetchTotalBreaks(machineId);
		    fetchRepetativeBreaks(machineId);
		    fetchOpenBreaks(machineId);
		    fetchClosedBreaks(machineId);
		    fetchTrialBreaks(machineId);
		    MaintainceCount(machineId);
		    ApprovedCount(machineId);
		    UnApprovedCount(machineId);
		}

        
       
        // Maintaince //
		$scope.handleUnApprovedCardClick = function(cardType) {
							    switch (cardType) {
							        case 'totalUnApprovedByMachine':
							            if (vm.selectedEqid && vm.selectedEqid.machine_id) {
							                $state.go('main.home.totalUnApprovedByMachine', {
							                    machineId: vm.selectedEqid.machine_id
							                });
							            } else {
							                toastr.warning("Please select a machine first!");
							            }
							            break;
							        default:
							            vm.totalUnApprovedByMachine = [];
							    }
							};
		$scope.handleApprovedCardClick = function(cardType) {
							    switch (cardType) {
							        case 'totalApprovedByMachine':
							            if (vm.selectedEqid && vm.selectedEqid.machine_id) {
							                $state.go('main.home.totalApprovedByMachine', {
							                    machineId: vm.selectedEqid.machine_id
							                });
							            } else {
							                toastr.warning("Please select a machine first!");
							            }
							            break;
							        default:
							            vm.totalApprovedByMachine = [];
							    }
							};
		$scope.TodayMaintainceCardClick = function(cardType) {
							    switch (cardType) {
							        case 'TodayMaintainceByMachine':
							            if (vm.selectedEqid && vm.selectedEqid.machine_id) {
							                $state.go('main.home.TodayMaintainceByMachine', {
							                    machineId: vm.selectedEqid.machine_id
							                });
							            } else {
							                toastr.warning("Please select a machine first!");
							            }
							            break;
							        default:
							            vm.TodayMaintainceByMachine = [];
							    }
							};
		$scope.totalclosedMaintainceCardClick = function(cardType) {
							    switch (cardType) {
							        case 'totalclosedMaintainceByMachine':
							            if (vm.selectedEqid && vm.selectedEqid.machine_id) {
							                $state.go('main.home.totalclosedMaintainceByMachine', {
							                    machineId: vm.selectedEqid.machine_id
							                });
							            } else {
							                toastr.warning("Please select a machine first!");
							            }
							            break;
							        default:
							            vm.totalclosedMaintainceByMachine = [];
							    }
							};
		$scope.totalopenMaintainceCardClick = function(cardType) {
							    switch (cardType) {
							        case 'totalopenMaintainceByMachine':
							            if (vm.selectedEqid && vm.selectedEqid.machine_id) {
							                $state.go('main.home.totalopenMaintainceByMachine', {
							                    machineId: vm.selectedEqid.machine_id
							                });
							            } else {
							                toastr.warning("Please select a machine first!");
							            }
							            break;
							        default:
							            vm.totalopenMaintainceByMachine = [];
							    }
							};
		$scope.handleMaintainceCardClick = function(cardType) {
							    switch (cardType) {
							        case 'totalMaintainceByMachine':
							            if (vm.selectedEqid && vm.selectedEqid.machine_id) {
							                $state.go('main.home.totalMaintainceByMachine', {
							                    machineId: vm.selectedEqid.machine_id
							                });
							            } else {
							                toastr.warning("Please select a machine first!");
							            }
							            break;
							        default:
							            vm.totalMaintainceByMachine = [];
							    }
							};
					
		
		
		
		
		function ApprovedCount(machineId) {
						        var url = baseUrl + "/maint/getClosedApprovalss/" + machineId;
						        $http.get(url)
						            .then(function(response) {
						                if (response.status === 200) {
						                    vm.ApprovedCount = response.data;  
						                    
						                    console.log("ApprovedCount data:", vm.ApprovedCount);
						                } else {
						                   
						                }
						            })
						            .catch(function(error) {
						                console.error("Error fetching ApprovedCount data:", error);
						             
						            });
						    }
							
		function UnApprovedCount(machineId) {
		        var url = baseUrl + "/maint/getUnApprovalss/" + machineId;
			        $http.get(url)
		            .then(function(response) {
		               if (response.status === 200) {
		                   vm.UnApprovedCount = response.data;  
												                    
		                  console.log("UnApprovedCount data:", vm.UnApprovedCount);
		                } else {
		        
			                }
			            })
		           .catch(function(error) {
		              console.error("Error fetching UnApprovedCount data:", error);
		            
		            });
		    }		
			
		function MaintainceCount(machineId) {
				        var url = baseUrl + "/dashboard/maintaince_counts/" + machineId;
				        $http.get(url)
				            .then(function(response) {
				                if (response.status === 200) {
				                    vm.MaintainceCount = response.data;  
				                    
				                    console.log("MaintainceCount data:", vm.MaintainceCount);
				                } else {
				                    toastr.error("No MaintainceCount data found.");
				                }
				            })
				            .catch(function(error) {
				                console.error("Error fetching MaintainceCount data:", error);
				           
				            });
				    }
		
		

		
		// breakdown //
		
		
		$scope.handleUsersCardClickover = function(cardType) {
					    switch (cardType) {
					        case 'overallBreakdownByMachine':
					            if (vm.selectedEqid && vm.selectedEqid.machine_id) {
					                $state.go('main.home.overallBreakdownByMachine', {
					                    machineId: vm.selectedEqid.machine_id
					                });
					            } else {
					                toastr.warning("Please select a machine first!");
					            }
					            break;
					        default:
					            vm.overallBreakdownByMachine = [];
					    }
					};


	

				$scope.handleUsersCardClickopen = function(cardType) {
				    switch (cardType) {
				        case 'totalopenBreakdownByMachine':
				            if (vm.selectedEqid && vm.selectedEqid.machine_id) {
				                $state.go('main.home.totalopenBreakdownByMachine', {
				                    machineId: vm.selectedEqid.machine_id
				                });
				            } else {
				                toastr.warning("Please select a machine first!");
				            }
				            break;
				        default:
				            vm.totalopenBreakdownByMachine = [];
				    }
				};
				
				
				
				$scope.handleUsersCardClickclosed = function(cardType) {
							    switch (cardType) {
							        case 'totalclosedBreakdownByMachine':
							            if (vm.selectedEqid && vm.selectedEqid.machine_id) {
							                $state.go('main.home.totalclosedBreakdownByMachine', {
							                    machineId: vm.selectedEqid.machine_id
							                });
							            } else {
							                toastr.warning("Please select a machine first!");
							            }
							            break;
							        default:
							            vm.totalclosedBreakdownByMachine = [];
							    }
							};
							
							
							$scope.handleUsersCardClicktrial = function(cardType) {
													    switch (cardType) {
													        case 'totaltrialBreakdownByMachine':
													            if (vm.selectedEqid && vm.selectedEqid.machine_id) {
													                $state.go('main.home.totaltrialBreakdownByMachine', {
													                    machineId: vm.selectedEqid.machine_id
													                });
													            } else {
													                toastr.warning("Please select a machine first!");
													            }
													            break;
													        default:
													            vm.totaltrialBreakdownByMachine = [];
													    }
													};


													$scope.handleUsersCardClickRepeat = function(cardType) {
																	    switch (cardType) {
																	        case 'totalRepeatBreakdownByMachine':
																	            if (vm.selectedEqid && vm.selectedEqid.machine_id) {
																	                $state.go('main.home.totalRepeatBreakdownByMachine', {
																	                    machineId: vm.selectedEqid.machine_id
																	                });
																	            } else {
																	                toastr.warning("Please select a machine first!");
																	            }
																	            break;
																	        default:
																	            vm.totalRepeatBreakdownByMachine = [];
																	    }
																	};
																	

		
				
				
				
		
			
		
		
         function fetchTimelineData(machineId) {
            var url = baseUrl + "/breakdown/Break_dashboard/" + machineId;
            $http.get(url)
                .then(function(response) {
                    if (response.status === 200) {
                        vm.timelineData = response.data;  
                        
                        console.log("Timeline data:", vm.timelineData);
                    } else {
                        toastr.error("No timeline data found.");
                    }
                })
                .catch(function(error) {
                    console.error("Error fetching timeline data:", error);
                    toastr.error("Error fetching timeline data.");
                });
        }
		function loadMachines() {
		    var url = baseUrl + "/machine_mst/list";
		    $http.get(url)
		        .then(function(response) {
		            vm.machines = response.data;
		            console.log("Fetched machines:", vm.machines);

		            // Extract unique lab names safely
		            vm.labNames = [...new Set(
		                vm.machines
		                    .filter(m => m.lab && m.lab.labName)   // ✅ only machines with lab
		                    .map(m => m.lab.labName)
		            )];

		            // Extract unique machine names safely
		            vm.machineNames = [...new Set(
		                vm.machines
		                    .filter(m => m.machine_name)          // ✅ avoid null names
		                    .map(m => m.machine_name)
		            )];
		        })
		        .catch(function(error) {
		            console.error("Error fetching machines:", error);
		        });
		}

		
		vm.onLabSelect = function(labName) {
		    vm.filteredMachinesByLab = vm.machines.filter(m => m.lab && m.lab.labName === labName);
		    vm.machineNames = [...new Set(
		        vm.filteredMachinesByLab
		            .filter(m => m.machine_name)   // avoid null names
		            .map(m => m.machine_name)
		    )];

		    vm.selectedMachineName = null;
		    vm.selectedEqid = null;
		    vm.eqidList = [];
		};



		// Step 2: On machineName select → filter eqids
		vm.onMachineNameSelect = function(machineName) {
		    vm.eqidList = vm.filteredMachinesByLab.filter(m => m.machine_name === machineName);
		    vm.selectedEqid = null; // reset second dropdown
		    console.log("Filtered eqidList:", vm.eqidList);
		};

		// Step 3: On eqid select → send machineId
		vm.onEqidSelect = function(machine) {
		    if (machine && machine.machine_id) {
				vm.selectedMachineId = machine.machine_id;
		        console.log("Selected Machine ID:", machine.machine_id);
		        vm.onMachineSelect(machine.machine_id);
				vm.loadCardsByMachineId(vm.selectedMachineId);
		    }
		};

		vm.loadCardsByMachineId = function(machineId) {
		    console.log("Loading card data for Machine ID:", machineId);
		    // Call your API / logic here
		};
			
        function loadBreakdashboard(machineId) {
            if (!machineId) return;  

            var url = baseUrl + "/analysis_time_frames/report/" + machineId;
            $http.get(url)
                .then(function(response) {
                    if (response.status === 200) {
                        vm.ATRepo = response.data;  
                        console.log("Repetative Breakdown :", vm.ATRepo);
                    } else {
                        toastr.error("No Repetative breakdown  found.");
                    }
                })
                .catch(function(error) {
                    console.error("Error fetching Repetative breakdown :", error);
                    toastr.error("Error fetching Repetative breakdown .");
                });
        }

	  function fetchTotalBreaks(machineId) {
            if (!machineId) return;  
            
            var url = baseUrl + "/breakdown/report/" + machineId;
            $http.get(url)
                .then(function(response) {
                    if (response.status === 200) {
                        vm.total_breaks = response.data;  
                        console.log("Breakdown total summary:", vm.total_breaks);
                    } else {
                        toastr.error("No breakdown total summary found.");
                    }
                })
                .catch(function(error) {
                    console.error("Error fetching breakdown total summary:", error);
                    toastr.error("Error fetching breakdown summary.");
                });
        }

		
        
        function fetchTrialBreaks(machineId) {
            if (!machineId) return;  
            
            var url = baseUrl + "/breakdown/Breakdowncounts/" + machineId;
            $http.get(url)
                .then(function(response) {
                    if (response.status === 200) {
                        vm.trial_breaks = response.data;  
                        console.log("Breakdown closed summary:", vm.trial_breaks);
                    } else {
                        toastr.error("No breakdown closed summary found.");
                    }
                })
                .catch(function(error) {
                    console.error("Error fetching breakdown closed summary:", error);
                    toastr.error("Error fetching breakdown summary.");
                });
        }
        
        
        function fetchOpenBreaks(machineId) {
            if (!machineId) return;  
            
            var url = baseUrl + "/breakdown/Breakdowncounts/" + machineId;
            $http.get(url)
                .then(function(response) {
                    if (response.status === 200) {
                        vm.open_breaks = response.data;  
                        console.log("Breakdown open summary:", vm.open_breaks);
                    } else {
                        toastr.error("No breakdown open summary found.");
                    }
                })
                .catch(function(error) {
                    console.error("Error fetching breakdown open summary:", error);
                    toastr.error("Error fetching breakdown summary.");
                });
        }
        
        
        
        function fetchClosedBreaks(machineId) {
            if (!machineId) return;  
            
            var url = baseUrl + "/breakdown/Breakdowncounts/" + machineId;
            $http.get(url)
                .then(function(response) {
                    if (response.status === 200) {
                        vm.closed_breaks = response.data;  
                        console.log("Breakdown Closed summary:", vm.closed_breaks);
                    } else {
                        toastr.error("No breakdown Closed summary found.");
                    }
                })
                .catch(function(error) {
                    console.error("Error fetching breakdown Closed summary:", error);
                    toastr.error("Error fetching breakdown summary.");
            });
        }  
        
        
        
         function fetchRepetativeBreaks(machineId) {
            var url = baseUrl + "/analysis_time_frames/report/" + machineId;
            genericFactory.getAll("", url)
                .then(response => {
                    vm.repetative_breaks = response.data;
                })
                .catch(error => {
                    console.error("Error fetching repetative breaks:", error);
                });
        }
        
		
		
		
		
		
		
		
		
        
            
    }

})();
