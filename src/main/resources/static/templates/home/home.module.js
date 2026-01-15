(function() {
	'use strict';

	angular.module('myApp.home', [

	]).config(function($stateProvider) {
		$stateProvider.
			state('main.home', {
			url : "/home",
			views : {
				"sub" : {
					templateUrl : "templates/home/home.html",
					controller : "HomeController as vm"
				}
			}
		})
		
		
		 .state('main.home.totalMachines', {
            url: "/totalMachines",
            views: {
                  "sub@main": {
                    templateUrl: "templates/home/totalMachines.html",
                    controller: "totalMachinesController as vm"
                }
            }
        })
		
		
		.state('main.home.totalApproved', {
		           url: "/totalApproved",
		           views: {
		                 "sub@main": {
		                   templateUrl: "templates/home/totalApproved.html",
		                   controller: "totalApprovedController as vm"
		               }
		           }
		       })
			   
			   
			   .state('main.home.totalUnApproved', {
			   		           url: "/totalUnApproved",
			   		           views: {
			   		                 "sub@main": {
			   		                   templateUrl: "templates/home/totalUnApproved.html",
			   		                   controller: "totalUnApprovedController as vm"
			   		               }
			   		           }
			   		       })
        
         .state('main.home.totalUsers', {
            url: "/totalUsers",
            views: {
                  "sub@main": {
                    templateUrl: "templates/home/totalUsers.html",
                    controller: "totalUsersController as vm"
                }
            }
        })
        
        
         .state('main.home.totalBreakdown', {
            url: "/totalBreakdown",
            views: {
                  "sub@main": {
                    templateUrl: "templates/home/totalBreakdown.html",
                    controller: "totalBreakdownController as vm"
                }
            }
        })
        
        //new
        
                 .state('main.home.overallBreakdown', {
            url: "/overallBreakdown",
            views: {
                  "sub@main": {
                    templateUrl: "templates/home/overallBreakdown.html",
                    controller: "overallBreakdownController as vm"
                }
            }
        })
        
        
        
                 .state('main.home.totalopenBreakdown', {
            url: "/totalopenBreakdown",
            views: {
                  "sub@main": {
                    templateUrl: "templates/home/totalopenBreakdown.html",
                    controller: "totalopenBreakdownController as vm"
                }
            }
        })
        
        
          .state('main.home.totalclosedBreakdown', {
            url: "/totalclosedBreakdown",
            views: {
                  "sub@main": {
                    templateUrl: "templates/home/totalclosedBreakdown.html",
                    controller: "totalclosedBreakdownController as vm"
                }
            }
        })
        
        
         .state('main.home.totaltrialBreakdown', {
            url: "/totaltrialBreakdown",
            views: {
                  "sub@main": {
                    templateUrl: "templates/home/totaltrialBreakdown.html",
                    controller: "totaltrialBreakdownController as vm"
                }
            }
        })
        
        
        //new
        
        

        
			         .state('main.home.openBreakdown', {
			            url: "/openBreakdown",
			            views: {
			                  "sub@main": {
			                    templateUrl: "templates/home/openBreakdown.html",
			                    controller: "openBreakdownController as vm"
			                }
			            }
			        })
			        
			         .state('main.home.closedBreakdown', {
			            url: "/closedBreakdown",
			            views: {
			                  "sub@main": {
			                    templateUrl: "templates/home/closedBreakdown.html",
			                    controller: "closedBreakdownController as vm"
			                }
			            }
			        })
			        
			         .state('main.home.trialBreakdown', {
			            url: "/trialBreakdown",
			            views: {
			                  "sub@main": {
			                    templateUrl: "templates/home/trialBreakdown.html",
			                    controller: "trialBreakdownController as vm"
			                }
			            }
			        })
        
					
 
        
         .state('main.home.totalMaintaince', {
            url: "/totalMaintaince",
            views: {
                  "sub@main": {
                    templateUrl: "templates/home/totalMaintaince.html",
                    controller: "totalMaintainceController as vm"
                }
            }
        })
        
           .state('main.home.openMaintaince', {
            url: "/openMaintaince",
            views: {
                  "sub@main": {
                    templateUrl: "templates/home/openMaintaince.html",
                    controller: "openMaintainceController as vm"
                }
            }
        })
        
        
          .state('main.home.totalopenMaintaince', {
            url: "/totalopenMaintaince",
            views: {
                  "sub@main": {
                    templateUrl: "templates/home/totalopenMaintaince.html",
                    controller: "totalopenMaintainceController as vm"
                }
            }
        })
        
          .state('main.home.totalclosedMaintaince', {
            url: "/totalclosedMaintaince",
            views: {
                  "sub@main": {
                    templateUrl: "templates/home/totalclosedMaintaince.html",
                    controller: "totalclosedMaintainceController as vm"
                }
            }
        })
        
        
           .state('main.home.closedMaintaince', {
            url: "/closedMaintaince",
            views: {
                  "sub@main": {
                    templateUrl: "templates/home/closedMaintaince.html",
                    controller: "closedMaintainceController as vm"
                }
            }
        })
        
        
         .state('main.home.TodayMaintaince', {
            url: "/TodayMaintaince",
            views: {
                  "sub@main": {
                    templateUrl: "templates/home/TodayMaintaince.html",
                    controller: "TodayMaintainceController as vm"
                }
            }
        })
        
        ///by machine breakdown ///
		
		
		.state('main.home.totalRepeatBreakdownByMachine', {
				            url: "/totalRepeatBreakdownByMachine/:machineId",
				            views: {
				                  "sub@main": {
				                    templateUrl: "templates/home/totalRepeatBreakdownByMachine.html",
				                    controller: "totalRepeatBreakdownByMachineController as vm"
				                }
				            }
				        })
						
						
						.state('main.home.totalclosedBreakdownByMachine', {
						       url: "/totalclosedBreakdownByMachine/:machineId",
						       views: {
						           "sub@main": {
						               templateUrl: "templates/home/totalclosedBreakdownByMachine.html",
						               controller: "totalclosedBreakdownByMachineController as vm"
						           }
						       }
						   })
						
        
	
												
	   .state('main.home.totaltrialBreakdownByMachine', {
	   		         url: "/totaltrialBreakdownByMachine/:machineId",
	   	           views: {
	   		              "sub@main": {
	   	            templateUrl: "templates/home/totaltrialBreakdownByMachine.html",
	   	          controller: "totaltrialBreakdownByMachineController as vm"
	   	      }
	      }
	   })   
	   													
	   .state('main.home.totalopenBreakdownByMachine', {
	       url: "/totalopenBreakdownByMachine/:machineId",
	       views: {
	           "sub@main": {
	               templateUrl: "templates/home/totalopenBreakdownByMachine.html",
	               controller: "totalopenBreakdownByMachineController as vm"
	           }
	       }
	   })

	   											
												
	   .state('main.home.overallBreakdownByMachine', {
	   		         url: "/overallBreakdownByMachine/:machineId",
	   	           views: {
	   		              "sub@main": {
	   	            templateUrl: "templates/home/overallBreakdownByMachine.html",
	   	          controller: "overallBreakdownByMachineController as vm"
	   	      }
	      }
	   })   
	   	
	   
	   
	   ///machine ppm
	   
	   											 
	   .state('main.home.TodayMaintainceByMachine', {
	   				            url: "/TodayMaintainceByMachine/:machineId",
	   				            views: {
	   				                  "sub@main": {
	   				                    templateUrl: "templates/home/TodayMaintainceByMachine.html",
	   				                    controller: "TodayMaintainceByMachineController as vm"
	   				                }
	   				            }
	   				        })
	   						
	   						
	   						.state('main.home.totalMaintainceByMachine', {
	   						       url: "/totalMaintainceByMachine/:machineId",
	   						       views: {
	   						           "sub@main": {
	   						               templateUrl: "templates/home/totalMaintainceByMachine.html",
	   						               controller: "totalMaintainceByMachineController as vm"
	   						           }
	   						       }
	   						   })
	   						
	           
	   	
	   												
	   	   .state('main.home.totalApprovedByMachine', {
	   	   		         url: "/totalApprovedByMachine/:machineId",
	   	   	           views: {
	   	   		              "sub@main": {
	   	   	            templateUrl: "templates/home/totalApprovedByMachine.html",
	   	   	          controller: "totalApprovedByMachineController as vm"
	   	   	      }
	   	      }
	   	   })   
	   	   													
	   	   .state('main.home.totalUnApprovedByMachine', {
	   	       url: "/totalUnApprovedByMachine/:machineId",
	   	       views: {
	   	           "sub@main": {
	   	               templateUrl: "templates/home/totalUnApprovedByMachine.html",
	   	               controller: "totalUnApprovedByMachineController as vm"
	   	           }
	   	       }
	   	   })

	   	   											
	   												
	   	   .state('main.home.totalopenMaintainceByMachine', {
	   	   		         url: "/totalopenMaintainceByMachine/:machineId",
	   	   	           views: {
	   	   		              "sub@main": {
	   	   	            templateUrl: "templates/home/totalopenMaintainceByMachine.html",
	   	   	          controller: "totalopenMaintainceByMachineController as vm"
	   	   	      }
	   	      }
	   	   })   
	   	   								
		   .state('main.home.totalclosedMaintainceByMachine', {
		      	   		         url: "/totalclosedMaintainceByMachine/:machineId",
		      	   	           views: {
		      	   		              "sub@main": {
		      	   	            templateUrl: "templates/home/totalclosedMaintainceByMachine.html",
		      	   	          controller: "totalclosedMaintainceByMachineController as vm"
		      	   	      }
		      	      }
		      	   })   
		      	   			
																		
																		
																		
	});
	
})();