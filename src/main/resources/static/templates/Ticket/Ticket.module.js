(function() {
	'use strict';

	angular
	.module('myApp.Ticket', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.Ticket', {
					url : "/Ticket",
					
					views : {
						"sub" : {
							templateUrl : "templates/Ticket/Ticket.html",
							controller : "TicketController as vm"
				}
			}
		})
	
	});
	
})();




