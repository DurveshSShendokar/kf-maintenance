angular.module('myApp', [
	'ui.router',
	'LocalStorageModule',
	'angularUtils.directives.dirPagination',
	'ui.bootstrap',
	'ngJsonExportExcel',
	'toastr',
	'chart.js',
	'ja.qr',
	'myApp.barcode',
	'myApp.common',
	'myApp.common1',
	'myApp.common2',
	'myApp.generic',
	'myApp.machineOwner',
	'myApp.main',
	'myApp.home',
	'myApp.user',
	'myApp.login_form',
	

	

	'myApp.roleToUser',
	'myApp.userToRole',

	'myApp.roleManagement',
	'myApp.reports',
	
	
	
	/*maintenance*/
	'myApp.machine',
	'myApp.machine_mst',
	'myApp.checklist',
	'myApp.maintlist',
	'myApp.shift',
	'myApp.category',
	'myApp.breakdownupdate',
	'myApp.breakdown',
	'myApp.trial',
	'myApp.ftr',
	'myApp.mttr',
	'myApp.mtbf',
	'myApp.breakdownhistory',
	'myApp.maintDepertment',
	'myApp.cunsumption_device',
	'myApp.consumptionReport',
	'myApp.maintreport',
	'myApp.machineOperation',
	'myApp.uploads',
	'myApp.AssetInventory',
	'myApp.Employee',
	'myApp.spare_mst',
	'myApp.category_mst',
	'myApp.lab_mst',
	'myApp.location_mst',
	'myApp.department_mst',
	'myApp.Allocation',
	'myApp.AssetAllocation',
	'myApp.SpareStocking',
	'myApp.Complaint',
	'myApp.Ticket',
	'myApp.dashboard',
	'myApp.Device',
	'myApp.UnitLocation',
	'myApp.ModBusAddress',
	'myApp.DeviceModelAddress',	
	'myApp.ControlPanelLocation',
	'myApp.EnergyMeterModule',
	'myApp.EnergyMeterRegister',
	'myApp.EnergyMeterMaster',	
	'myApp.MeterDetials',	
	'myApp.PPMDashboard',	
	'myApp.BreakDownDashboard',	
	'myApp.ConsumptionDashboard',
	'myApp.WeekWiseMaintenance',
	'myApp.WeekWiseBrekdown',
	'myApp.Complaint_Summary_Report',
	'myApp.Engineer_Complaint_Status',
	'myApp.TicketReport',	
	'myApp.EnergyMeterPriceSlab',
	'myApp.EnergyMeterRegisterGroup',
	'myApp.Notification',
	'myApp.reportConsumption',
	'myApp.AlertConfiguration',
	'myApp.DashboardDesign',
	'myApp.reportEnergyConsumption',
	'myApp.reportBreakdownHistory',

	'myApp.GaugeChart',

	'myApp.SpareReport',
	'myApp.SpareUtilReport',
	'myApp.generalBreakdown',
	
	,'myApp.DateWise_Breakdown_Report'
	,'myApp.StatusWise_breakdown_Report'
	,'myApp.BreakdownReport',
	,'myApp.Breakdown_Report_2'
	,'myApp.Analysis_Report'
	,'myApp.Break_dashboard'
	,'myApp.SpareConsumptionReport'
	,'myApp.MaintSpareStocking'
	,'myApp.maintspare_mst'
	,'myApp.UserWise_PPM_Report'
	,'myApp.PPMoverdue'
	,'myApp.LabDash'
	,'myApp.PPMLabWise'
	,'myApp.ApprovedPPM'
	,'myApp.Upload_PPMExcel'
	,'myApp.UserWise_Breakdown_Report'
	,'myApp.AssetAllocationReport'
	,'myApp.AssetReAllocation'
	,'myApp.ComplaintReAllocation'
	,'myApp.machine_history'
])

.value('_', window._)

.constant('ApiEndpoint', {
//url: 'http://192.168.0.88:1002',
url: 'http://20.219.1.165:8081',
//url: 'http://localhost:1002',
  userKey : 'KFapplication'
})

.run(function(localStorageService, $location, $rootScope, $state){
	
	$rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams){
		
		console.log(JSON.stringify(localStorageService.get('KFapplication')));
	
		
		if(sessionStorage.getItem('KFapplication') == null){
//		if(localStorageService.get('renataLoggedInUser') == null){
			$location.url('/login_form');
			//	$location.url("/main/home");
		}
		
	});
	
})
.config(function($urlRouterProvider) {
	// if none of the above states are matched, use this as the fallback
	
	$urlRouterProvider.otherwise("/main/home");
});