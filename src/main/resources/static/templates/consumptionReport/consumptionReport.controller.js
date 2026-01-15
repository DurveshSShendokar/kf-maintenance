(function() {
	'use strict';

	angular.module('myApp.consumptionReport').controller('ConsumptionReportController', ConsumptionReportController);

	ConsumptionReportController.$inject = [ '$state','$uibModal', '$log',
			'$scope', 'toastr','genericFactory','ApiEndpoint','$filter' ];
		/* @ngInject */
	function ConsumptionReportController($state, $uibModal, $log, $scope, toastr,genericFactory,ApiEndpoint,$filter) {
		var consumptionUrl = staticUrlMaintenance+"/consumption";
		var meterCommunicationUrl = staticUrlMaintenance+"/meterCommunication";

		var vm = angular.extend(this, {
			getReport:getReport,
			fetchDat:fetchDat
		});

		(function activate() {
		loadMeeters()
		})();

		// ******************************************************
		
		function fetchDat(){
			
			var msg=""
				 var url =meterCommunicationUrl+"/checkValue";
				genericFactory.getAll(msg,url).then(function(response) {
				
								
			});
			
		}
		function loadMeeters(){
			
			var msg=""
				 var url =consumptionUrl+"/getAllConsumptionDevices";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.meeters= response.data;
				console.log("meeters: "+JSON.stringify(vm.meeters))
								
			});
		}
		
		function getReport(report){
			var url
			console.log("report: "+JSON.stringify(report))

			if(report==undefined){
				toastr.error('Please Select  Report Type');
				return;
			}
			if(!report.type || report.type== ''){
				toastr.error('Please Select  Report Type');
				return;
			}
			if(report.type=="Meeter Wise"){
				if(!report.meeters || report.meeters== ''){
					toastr.error('Please Select  Meeters');
					return;
				}
				url =meterCommunicationUrl+"/meeterWiseReport?meeterId="+report.meeters.meterId;
			}else{
				if(!report.date || report.date== ''){
					toastr.error('Please Select  Date');
					return;
				}
				url=meterCommunicationUrl+"/dateWiseReport?date="+$filter('date')(new Date(report.date),'yyyy-MM-dd');
			}
			
			
			var msg=""
				console.log("url  : "+url)
 
				genericFactory.getAll(msg,url).then(function(response) {
				vm.consumptions= response.data;
				console.log("consumptions: "+JSON.stringify(vm.consumptions))
								
			});

		}

	}

})();
