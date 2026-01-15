(function() {
    'use strict';

    angular
    .module('myApp.Break_dashboard', ['datatables'] )
    .config(function($stateProvider) {
        $stateProvider
        .state('main.Break_dashboard', {
            url: "/Break_dashboard",
            views: {
                "sub": {
                    templateUrl: "templates/Break_dashboard/Break_dashboard.html",
                    controller: "Break_dashboardController as vm"
                }
            }
        });

        
    });

})();
