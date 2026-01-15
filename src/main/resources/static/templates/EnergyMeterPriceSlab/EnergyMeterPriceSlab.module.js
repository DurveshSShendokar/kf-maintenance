(function() {
	'use strict';

	angular
	.module('myApp.EnergyMeterPriceSlab', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.EnergyMeterPriceSlab', {
					url : "/EnergyMeterPriceSlab",
					
					views : {
						"sub" : {
							templateUrl : "templates/EnergyMeterPriceSlab/EnergyMeterPriceSlab.html",
							controller : "EnergyMeterPriceSlabController as vm"
				}
			}
		})
	
	});
	
})();




