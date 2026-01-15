(function() {
    'use strict';

    angular
    .module('myApp.dashboard', ['datatables'] )
    .config(function($stateProvider) {
        $stateProvider
        .state('main.dashboard', {
            url: "/dashboard",
            views: {
                "sub": {
                    templateUrl: "templates/dashboard/dashboard.html",
                    controller: "dashboardController as vm"
                }
            }
        })
        .state('main.dashboard.total-assets', {
            url: "/total-assets",
            views: {
                  "sub@main": {
                    templateUrl: "templates/dashboard/total-assets.html",
                    controller: "totalAssetsController as vm"
                }
            }
        })
        
         .state('main.dashboard.total-complaints', {
            url: "/total-complaints",
            views: {
                  "sub@main": {
                    templateUrl: "templates/dashboard/total-complaints.html",
                    controller: "totalComplaintsController as vm"
                }
            }
        })
        
        
         .state('main.dashboard.total-allocates', {
            url: "/total-allocates",
            views: {
                  "sub@main": {
                    templateUrl: "templates/dashboard/total-allocates.html",
                    controller: "totalAllocatesController as vm"
                }
            }
        })
        
         .state('main.dashboard.total-Nonallocates', {
            url: "/total-Nonallocates",
            views: {
                  "sub@main": {
                    templateUrl: "templates/dashboard/total-Nonallocates.html",
                    controller: "totalNonAllocatesController as vm"
                }
            }
        })
        
         .state('main.dashboard.OpenComplaints', {
            url: "/OpenComplaints",
            views: {
                  "sub@main": {
                    templateUrl: "templates/dashboard/OpenComplaints.html",
                    controller: "totalOpenComplaintsController as vm"
                }
            }
        })
        
         .state('main.dashboard.InprocessComplaints', {
            url: "/InprocessComplaints",
            views: {
                  "sub@main": {
                    templateUrl: "templates/dashboard/InprocessComplaints.html",
                    controller: "InprocessComplaintsController as vm"
                }
            }
        })
        
        
         .state('main.dashboard.ClosedComplaints', {
            url: "/ClosedComplaints",
            views: {
                  "sub@main": {
                    templateUrl: "templates/dashboard/ClosedComplaints.html",
                    controller: "totalClosedComplaintsController as vm"
                }
            }
        });
        
        
    });

})();
