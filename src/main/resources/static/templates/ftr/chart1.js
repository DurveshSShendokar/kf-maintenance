   function graph1(){
	   
	   var app = angular.module('myApp', []);

	   app.controller('VendorPerformanceController', ['$scope', function ($scope) {
		   debugger;
	       
	   }]);    

alert("hiiGGG");
alert("bbbb");
var fpy_ja =angular.element(document.querySelector('[ng-controller="VendorPerformanceController"]')).scope().vm.fpy[0];
alert("fpy_ja==="+fpy_ja);
    var fpy_jan =angular.element(document.querySelector('[ng-controller="VendorPerformanceController"]')).scope().vm.fpy[0];
    alert("fpy_jan"+fpy_jan);
 /*   
    var fpy_jan1=fpy_jan.replace(/%/gi, "");
    alert("fpy_jan1=="+fpy_jan1);
    
    var fpy_feb =angular.element(document.querySelector('[ng-controller="VendorPerformanceController"]')).scope().vm.fpy[0].fpy_feb;
    var fpy_feb1=fpy_feb.replace(/%/gi, "");
   // alert("fpy_feb1=="+fpy_feb1);
    var fpy_mar =angular.element(document.querySelector('[ng-controller="VendorPerformanceController"]')).scope().vm.fpy[0].fpy_mar;
    var fpy_mar1=fpy_mar.replace(/%/gi, "");
  //  alert("fpy_mar1=="+fpy_mar1);
    var fpy_apr =angular.element(document.querySelector('[ng-controller="VendorPerformanceController"]')).scope().vm.fpy[0].fpy_apr;
    var fpy_apr1=fpy_apr.replace(/%/gi, "");
  //  alert("fpy_apr1=="+fpy_apr1);
    var fpy_may =angular.element(document.querySelector('[ng-controller="VendorPerformanceController"]')).scope().vm.fpy[0].fpy_may;
      var fpy_may1=fpy_may.replace(/%/gi, "");
    //  alert("fpy_may1=="+fpy_may1);
    var fpy_june =angular.element(document.querySelector('[ng-controller="VendorPerformanceController"]')).scope().vm.fpy[0].fpy_june;
    var fpy_june1=fpy_june.replace(/%/gi, "");
   // alert("fpy_june1=="+fpy_june1);
    var fpy_july =angular.element(document.querySelector('[ng-controller="VendorPerformanceController"]')).scope().vm.fpy[0].fpy_july;
    var fpy_july1=fpy_july.replace(/%/gi, "");
   // alert("fpy_july1=="+fpy_july1);
    var fpy_aug =angular.element(document.querySelector('[ng-controller="VendorPerformanceController"]')).scope().vm.fpy[0].fpy_aug;
    var fpy_aug1=fpy_aug.replace(/%/gi, "");
   // alert("fpy_aug1=="+fpy_aug1);
    var fpy_sep =angular.element(document.querySelector('[ng-controller="VendorPerformanceController"]')).scope().vm.fpy[0].fpy_sep;
    var fpy_sep1=fpy_sep.replace(/%/gi, "");
   // alert("fpy_sep1=="+fpy_sep1);
    var fpy_oct =angular.element(document.querySelector('[ng-controller="VendorPerformanceController"]')).scope().vm.fpy[0].fpy_oct;
    var fpy_oct1=fpy_oct.replace(/%/gi, "");
    //alert("fpy_oct1=="+fpy_oct1);
    var fpy_nov =angular.element(document.querySelector('[ng-controller="VendorPerformanceController"]')).scope().vm.fpy[0].fpy_nov;
    var fpy_nov1=fpy_nov.replace(/%/gi, "");
   // alert("fpy_nov1=="+fpy_nov1);
    var fpy_dec =angular.element(document.querySelector('[ng-controller="VendorPerformanceController"]')).scope().vm.fpy[0].fpy_dec;
    var fpy_dec1=fpy_dec.replace(/%/gi, "");
   // alert("fpy_dec1=="+fpy_dec1);
*/    
    var speedCanvas = document.getElementById("speedChart");

    Chart.defaults.global.defaultFontFamily = "Lato";
    Chart.defaults.global.defaultFontSize = 18;

    var speedData = {
      labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun","July","Aug","Sep","Oct","Nov","Dec"],
      datasets: [{
        label: "Car Speed (mph)",
        data: [0, 1,34,65,78,45,90],/*,fpy_feb,fpy_mar,fpy_apr,fpy_may,fpy_june, fpy_july, fpy_aug, fpy_sep, fpy_oct, fpy_nov,fpy_dec*/
        lineTension: 0,
        fill: false,
        borderColor: 'orange',
        backgroundColor: 'transparent',
        borderDash: [5, 5],
        pointBorderColor: 'orange',
        pointBackgroundColor: 'rgba(255,150,0,0.5)',
        pointRadius: 5,
        pointHoverRadius: 10,
        pointHitRadius: 30,
        pointBorderWidth: 2,
        pointStyle: 'rectRounded'
      }]
    };

    var chartOptions = {
      legend: {
        display: true,
        position: 'top',
        labels: {
          boxWidth: 80,
          fontColor: 'black'
        }
      }
    };

    var lineChart = new Chart(speedCanvas, {
      type: 'line',
      data: speedData,
      options: chartOptions
    });
    
   }