(function() {
    'use strict';

    angular
    .module('myApp.PPMLabWise', ['datatables'] )
    .config(function($stateProvider) {
        $stateProvider
        .state('main.PPMLabWise', {
            url: "/PPMLabWise",
            views: {
                "sub": {
                    templateUrl: "templates/PPMLabWise/PPMLabWise.html",
                    controller: "PPMLabController as vm"
                }
            }
        });

        
    });

})();
