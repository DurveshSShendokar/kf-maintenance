(function() {
    'use strict';

    angular.module('myApp.Engineer_Complaint_Status').controller('EngComplaintController', EngComplaintController);

    EngComplaintController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr', 'genericFactory'];

    function EngComplaintController($scope, ApiEndpoint, $http, toastr, genericFactory) {
        const baseUrl = ApiEndpoint.url;
        var vm = angular.extend(this, {
            employees: [],
            selectedEmployee: null,
            complaintSummary: [],
             engineerReport: [], 
            generateReport: generateReport,
            exportToExcel: exportToExcel 
        });

        (function activate() {
            fetchEngineers();
        })();

        function fetchEngineers() {
            const url = `${baseUrl}/user/getITEnginner`;
            genericFactory.getAll("", url).then(response => {
                vm.engineer = response.data;
                  console.log("Engineers -> : " + JSON.stringify(vm.engineer));
            }).catch(error => {
                console.error('Error fetching engineers:', error);
            });
        }

     function generateReport() {
    if (vm.selectedEmployee) {
        const url = `${baseUrl}/complaints/engineerComplaintRecords/${vm.selectedEmployee.id}`;
        genericFactory.getAll("", url).then(response => {
            vm.engineerReport = response.data;
            console.log("Engineer Complaints: " + JSON.stringify(vm.engineerReport));
        }).catch(error => {
            console.error('Error fetching engineer complaints:', error);
        });
    } else {
        toastr.warning('Please select an engineer');
    }
}

        
         function exportToExcel() {
            if (vm.selectedEmployee) {
                const url = `${baseUrl}/complaints/engineerComplaintRecords/${vm.selectedEmployee.id}/export`;
                // Send request to backend to generate Excel file
                $http.get(url, { responseType: 'blob' }).then(response => {
                    // Convert blob to File object
                    const blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
                    const url = window.URL.createObjectURL(blob);
                    const a = document.createElement('a');
                    a.style.display = 'none';
                    a.href = url;
                    a.download = 'Engineer_Complaints.xlsx';
                    document.body.appendChild(a);
                    a.click();
                    window.URL.revokeObjectURL(url);
                }).catch(error => {
                    console.error('Error exporting to Excel:', error);
                });
            } else {
                toastr.warning('Please select an engineer');
            }
        }
    }
})();
