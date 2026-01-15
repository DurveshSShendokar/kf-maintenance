angular.module('myApp.home').constant('ApiEndpoint', {
    baseUrl: 'http://localhost:8088/dashboard/total_count',
    baseUrl1:'http://localhost:8088/dashboard/breakdown_count',
    baseUrl2:'http://localhost:8088/dashboard/maintaince_count',
     baseUrl3:'http://localhost:8088/dashboard/todayFetchCount',
     baseUrl4:'http://localhost:8088/dashboard/getMaintenanceCountsByMachine',
      baseUrl5:'http://localhost:8088/dashboard/getBreakdownCountsByMachine'
});
