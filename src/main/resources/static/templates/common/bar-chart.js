(function() {
	'use strict';
	angular.module('myApp.common',[])
	.directive('reportChart',function(){
  	return {
  		templateUrl: '/templates/common/graph.html',
    	restrict: 'A',
    	scope: {
            divId: '@?',
            type: '@',
            titleText : '@?',
            subTitleText : '@?',
            categories : '=',
            yAxisText : '@?',
            series: '='
        },
    	link: function (scope, element, attrs) {
    		var el = element[0];
//	        element.bind('click', function(e){
	        	
	        	Highcharts.chart('graphContainer', {
	        	    chart: {
	        	        type: scope.type
	        	    },
	        	    title: {
	        	        text: scope.titleText	//'Monthly Average Rainfall'
	        	    },
	        	    subtitle: {
	        	        text: scope.subTitleText	//'Source: WorldClimate.com'
	        	    },
	        	    xAxis: {
	        	        categories: scope.categories,
	        	        crosshair: true,
	        	        /*title: {
	        	            text: "Month"	//'Rainfall (mm)'
	        	        }*/
	        	    },
	        	    yAxis: {
	        	        min: 0,
	        	        title: {
	        	            text: scope.yAxisText	//'Rainfall (mm)'
	        	        }
	        	    },
	        	    tooltip: {
	        	        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	        	        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	        	            '<td style="padding:0"><b>{point.y:.3f}</b></td></tr>',
	        	        footerFormat: '</table>',
	        	        shared: true,
	        	        useHTML: true
	        	    },
	        	    plotOptions: {
	        	        column: {
	        	            pointPadding: 0.2,
	        	            borderWidth: 0
	        	        }
	        	    },
	        	    series: scope.series	/*[{
	        	        name: 'Tokyo',
	        	        data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]

	        	    }, {
	        	        name: 'New York',
	        	        data: [83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3]

	        	    }, {
	        	        name: 'London',
	        	        data: [48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2]

	        	    }, {
	        	        name: 'Berlin',
	        	        data: [42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1]

	        	    }]*/
	        	});
	        	
//	        });
    	}
  	}
	});
})();