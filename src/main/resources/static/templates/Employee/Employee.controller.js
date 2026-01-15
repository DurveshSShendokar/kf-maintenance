(function() {
    'use strict';

    angular.module('myApp.Employee').controller('EmployeeController', EmployeeController);

    EmployeeController.$inject = ['$scope', 'ApiEndpoint', '$http', 'toastr','genericFactory'];

    function EmployeeController($scope, ApiEndpoint, $http, toastr, genericFactory) {
      
	const baseUrl = ApiEndpoint.url;
	var employeeUrl = staticUrl+"/employee";
        var vm = angular.extend(this, {
           
             employees: [],
           
           add: add,
            delet: delet,
            ok: ok,
            addNew: addNew,
            cancel: cancel,
            resetFormFields: resetFormFields,
            uploadNew: uploadNew,
			upload:upload,
			 confirmDelete: confirmDelete,
             cancelDelete:cancelDelete,
             showChooseOption: true,
            export: exportExcel ,
            userCreate:userCreate 
        });

        (function activate() {
            $scope.employee = {};
          
          loadDepartments();
          loadEmployees();
          loadLabs();
               loadRole();
            $scope.addNewTab = false;
             vm.showModal = false;
             $scope.uploadTab=false;
           
        })();

        function cancel() {
            $scope.addNewTab = false;
        }

		   function upload(){
			$scope.uploadTab=true;
			 $scope.showChooseOption = true;
			$scope.addNewTab=false;
			
		}
			
        function addNew() {
            $scope.addNewTab = true;
             $scope.uploadTab=false;
             resetFormFields(); 
        }
        function userCreate(employee){
			var msg="";
			   var url = employeeUrl + "/userCreated";
			 genericFactory.add(msg, url, employee)
				        .then(function(response) {
				            vm.responce = response.data;
				           console.log("checking employee:", vm.responce);
				loadEmployees();
				           /* if (response.status === 200) {
				                toastr.success('employee added successfully!');
				                loadEmployees();
				                $scope.addNewTab = false;
				                resetFormFields(); 
				            } else {
				                toastr.error('Failed to add employee');
				            }*/
				        })
			
		}
        
         function exportExcel() {
            var url = employeeUrl + "/exportExcel";

            $http.get(url, { responseType: 'blob' })
                .then(function(response) {
					  console.log("Export response data:", response.data);
                    var blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
                    var downloadLink = document.createElement('a');
                    downloadLink.href = window.URL.createObjectURL(blob);
                    downloadLink.download = 'Employee.xlsx';
                    document.body.appendChild(downloadLink);
                    downloadLink.click();
                    document.body.removeChild(downloadLink);
                })
                .catch(function(error) {
                    toastr.error('Error exporting data');
                    console.error("Error exporting data:", error);
                });
        }



			  $scope.handleFileSelect = (files)=>{
			$scope.selectedFile = files[0];
			console.log("File: ", $scope.selectedFile);
		}
		
		function uploadNew(){
			var file = $scope.selectedFile;
			console.log("File: ", file);

			if (!file) {
				toastr.error("Please select file");
				return;
			}
			   var url = employeeUrl + "/uploadExcel";

				$('.loading').show();
				var fd = new FormData();
				fd.append('file', file);
			
				console.log("URL :: "+url)
				$http.post(url, fd, {
					transformRequest: angular.identity,
					headers: {
						'Content-Type': undefined
					}
				})
				.then(function successCallback(response) {
					var text=response.data.resmessage
					console.log("RESPONCE  "+JSON.stringify(response.data))
					$('.loading').hide();
					if(response.data.code==200){
						toastr.success('Uploaded....', 'Succesful !!',{ timeOut: 10000 });	
						
						loadEmployees();
					}else{
						toastr.error('Upload....', 'UnSuccesful !!');
					}
					 $scope.uploadTab = false;
					$scope.uploadMediaPrepTab= false;
				}, function errorCallback(response) {
			    	$('.loading').hide();
					$scope.uploadMediaPrepTab= false;
					toastr.error('Upload....', 'UnSuccesful !!');
				});
				angular.element("input[type='file']").val(null);
				$scope.selectedFile = null;
				  $scope.showChooseOption = false; 
		}

      
      
			
			
			function loadDepartments() {
			    var url = baseUrl + "/department/getDepartmentByLimit?pageNo=1&perPage=10";
			    $http.get(url)
			        .then(function(response) {
			            vm.departments = response.data;
			            console.log("Fetched departments:", vm.departments);
			        })
			        .catch(function(error) {
			            console.error("Error fetching departments:", error);
			        });
			}


		function loadEmployees() {
            var url = baseUrl + "/employee/getEmployeeByLimit?pageNo=1&perPage=10";
            $http.get(url)
                .then(function(response) {
                    vm.employees = response.data;
                     console.log("Fetched employees:", vm.employees);
                })
                .catch(function(error) {
                    console.error("Error fetching assets:", error);
                });
        }


			function loadLabs() {
			    var url = baseUrl + "/lab/getLabByLimit?pageNo=1&perPage=10";
			    $http.get(url)
			        .then(function(response) {
			            vm.labs = response.data;
			            console.log("Fetched labs:", vm.labs);
			        })
			        .catch(function(error) {
			            console.error("Error fetching labs:", error);
			        });
			}
			
			
			function loadRole() {
			    var url = baseUrl + "/Role/roles";
			    $http.get(url)
			        .then(function(response) {
			            vm.roles = response.data;
			            console.log("Fetched roles:", vm.roles);
			        })
			        .catch(function(error) {
			            console.error("Error fetching roles:", error);
			        });
			}
			
			
			
			
			
		/*	
     	 function delet(employee) {
			    var url = baseUrl + "/employee/delete/" + employee.employee_id;
			    console.log("Delete URL:", url);
			    $http.delete(url)
			        .then(function(response) {
			            toastr.success('Deleted employee Successfully!');
			            loadEmployees();
			        })
			        .catch(function(error) {
			            toastr.error('Error deleting employee');
			            console.error("Error deleting employee:", error);
			        });
			}

*/


	let selectedemployee = null;
       function delet(employee) {
    selectedemployee = employee;
    vm.showModal = true; // Show the confirmation modal
}

function confirmDelete() {
    if (!selectedemployee) return;

    var url = baseUrl + "/employee/delete/" + selectedemployee.employee_id;

    $http.delete(url)
        .then(function(response) {
            toastr.success('Deleted Employee successfully!');
            loadEmployees();
        })
        .catch(function(error) {
            toastr.error('Error deleting Employee');
            console.error("Error deleting Employee:", error);
        })
        .finally(function() {
            vm.showModal = false; // Hide the modal after confirmation
        });
}

function cancelDelete() {
    vm.showModal = false; // Hide the modal without taking any action
}




				function ok(employee) {
					
					
					
				    var url = baseUrl + "/employee/addEmployee";
				    var msg = "";
				    
				    
				    // Check if all fields are filled
					 if (!employee.department || !employee.lab || !employee.role || !employee.firstName 
					    || !employee.lastName || !employee.manager) 
					    {
					        toastr.error(' fill all the fields!');
					        return;
					    }
					    
					      // Check if mobile number is valid
		            if (!/^\d{10}$/.test(employee.mobile)) {
		                toastr.error('Mobile number must be exactly 10 digits and contain only numbers.');
		                return;
		            }
				
				  // Check if email is valid
				    if (!/\b[A-Za-z0-9._%+-]+@gmail\.com\b/.test(employee.email)) {
				        toastr.error('Invalid Gmail address.');
				        return;
				    }
								
				  //  console.log("ADD" + JSON.stringify(employee));
				
				    genericFactory.add(msg, url, employee)
				        .then(function(response) {
				            vm.saved = response.data;
				           console.log("checking employees:", vm.saved);
				
				            if (response.status === 200) {
				                toastr.success('employee added successfully!');
				                loadEmployees();
				                $scope.addNewTab = false;
				                resetFormFields(); 
				            } else {
				                toastr.error('Failed to add employee');
				            }
				        })
				        .catch(function(error) {
				            toastr.error('Failed to add employee');
				            console.error("Error adding employee:", error);
				        });
				}
				
				
				
					function resetFormFields() 
						{
						    $scope.Employee = {}; 
						}
									
				

			
			
			
   
        function add(Employee) {
            $scope.addNewTab = true;
            $scope.Employee = Object.assign({}, Employee);
        }
    }
})();
