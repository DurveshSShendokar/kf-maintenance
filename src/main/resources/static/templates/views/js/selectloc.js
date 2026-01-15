
	$(document)
			.ready(
					function() {

						$
								.ajax({

									type : "GET",
									url : 'http://localhost:8080/AssetMGMT/location/list',
									//data : "{}",
									contentType : "application/json; charset=utf-8",
									dataType : "json",
									cache : false,

									success : function(
											response) {

										//alert(response.message);

										var len = response.length;
										for (var i = 0; i < len; i++) {

											var loc_description = response[i].loc_description;
											var location_id = response[i].location_id;
											
											  var option = $('<option value="'+location_id+'">'+loc_description+'</option>');
											  $('#location_id').append(option);
										}
									}
								});

					});
