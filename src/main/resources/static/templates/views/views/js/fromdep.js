
	$(document)
			.ready(
					function() {

						$
								.ajax({

									type : "GET",
									url : 'http://localhost:8080/AssetMGMT/department/list',
									//data : "{}",
									contentType : "application/json; charset=utf-8",
									dataType : "json",
									cache : false,

									success : function(
											response) {

										//alert(response.message);

										var len = response.length;
										for (var i = 0; i < len; i++) {

											var department_name = response[i].department_name;
											var department_id = response[i].department_id;
											
											  var option = $('<option value="'+department_id+'">'+department_name+'</option>');
											  $('#fromdepartment').append(option);
										}
									}
								});

					});
