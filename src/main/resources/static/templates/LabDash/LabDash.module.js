(function() {
    'use strict';

    angular
    .module('myApp.LabDash', ['datatables'] )
    .config(function($stateProvider) {
        $stateProvider
        .state('main.LabDash', {
            url: "/LabDash",
            views: {
                "sub": {
                    templateUrl: "templates/LabDash/LabDash.html",
                    controller: "LabDashController as vm"
                }
            }
        });

        
    });

})();
