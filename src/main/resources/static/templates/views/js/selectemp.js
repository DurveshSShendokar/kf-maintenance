	$(document)
			.ready(
					function() {

						$
								.ajax({

									type : "GET",
									url : 'http://localhost:8080/AssetMGMT/employee/list',
									//data : "{}",
									contentType : "application/json; charset=utf-8",
									dataType : "json",
									cache : false,

									success : function(
											response) {

										//alert(response.message);

										var len = response.length;
										for (var i = 0; i < len; i++) {

											var name = response[i].name;
											var employee_id = response[i].employee_id;
											
											  var option = $('<option value="'+employee_id+'">'+name+'</option>');
											  $('#employee_id').append(option);
										}



									}
								});

					});
