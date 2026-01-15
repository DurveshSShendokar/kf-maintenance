(function() {
	'use strict';
	angular.module('myApp.reports', ['datatables'])
	.config(function($stateProvider) {
		$stateProvider.state('main.incomingAssembly', {
//			abstract : true,
			url : "/incomingAssemblyReport",
			views : {
				"sub" : {
					templateUrl: 'templates/reports/incomingAssembly.html',
					controller : "incomingAssemblyController as vm"
				}
			}
		})
		.state('main.qrcodeTraverse', {
			url : "/qrcodeTraverse",
			views : {
				"sub" : {
					templateUrl: 'templates/reports/qrcodeTraverse.html',
					controller : "qrcodeTraverseController as vm"
				}
			}
		})
		.state('main.itemwiseMonthlyReport', {
			url : "/itemwiseMonthlyReport",
			views : {
				"sub" : {
					templateUrl: 'templates/reports/itemwiseMonthlyReport.html',
					controller : "itemwiseMonthlyReportController as vm"
				}
			}
		})
		.state('main.materialIssuedHistory', {
			url : "/materialIssuedHistory",
			views : {
				"sub" : {
					templateUrl: 'templates/reports/materialIssuedHistory.html',
					controller : "materialIssuedHistoryController as vm"
				}
			}
		})
		.state('main.monthlyYieldReport', {
			url : "/monthlyYieldReport",
			views : {
				"sub" : {
					templateUrl: 'templates/reports/yieldReportMonthly.html',
					controller : "monthlyYieldReportController as vm"
				}
			}
		})
		.state('main.materialAtEachStageReport', {
			url : "/materialAtEachStageReport",
			views : {
				"sub" : {
					templateUrl: 'templates/reports/materialAtEachStageReport.html',
					controller : "materialAtEachStageReportController as vm"
				}
			}
		})
	});
})();