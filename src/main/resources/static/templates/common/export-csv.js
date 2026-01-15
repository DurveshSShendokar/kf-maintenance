(function() {
	'use strict';
	angular.module('myApp.common1',[])
	.directive('exportToCsv',function(){
  	return {
    	restrict: 'A',
    	scope: {
            divId: '@',
            name: '@'
        },
    	link: function (scope, element, attrs) {
    		var el = element[0];
//    		console.log(scope.divId);
	        element.bind('click', function(e){
	        	debugger
	        	var table = document.getElementById(scope.divId);//e.target.nextElementSibling;
	        	var csvString = '';
	        	for(var i=0; i<table.rows.length;i++){
	        		var rowData = table.rows[i].cells;
	        		for(var j=0; j<rowData.length;j++){
	        			csvString = csvString + rowData[j].innerHTML + ",";
	        		}
	        		csvString = csvString.substring(0,csvString.length - 1);
	        		csvString = csvString + "\n";
			    }
	         	csvString = csvString.substring(0, csvString.length - 1);
	         	var filename = scope.name + '.csv';
	         	var a = $('<a/>', {
		            style:'display:none',
		            href:'data:application/octet-stream;base64,'+btoa(csvString),
		            download:filename
		        }).appendTo('body')
		        a[0].click()
		        a.remove();
	        });
    	}
  	}
	});
})();