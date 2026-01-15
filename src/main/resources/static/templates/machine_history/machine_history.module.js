(function() {
    'use strict';

    angular
    .module('myApp.machine_history', ['datatables'] )
    .config(function($stateProvider) {
        $stateProvider
        .state('main.machine_history', {
            url: "/machine_history",
            views: {
                "sub": {
                    templateUrl: "templates/machine_history/machine_history.html",
                    controller: "machine_historyController as vm"
                }
            }
        });

        
    });

})();
