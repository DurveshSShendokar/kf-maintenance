	$(document)
			.ready(
					function() {

						$
								.ajax({

									type : "GET",
									url : 'http://localhost:8080/AssetMGMT/category/list',
									//data : "{}",
									contentType : "application/json; charset=utf-8",
									dataType : "json",
									cache : false,

									success : function(
											response) {

										//alert(response.message);

										var len = response.length;
										for (var i = 0; i < len; i++) {

											var assets_cat_name = response[i].assets_cat_name;
											var assets_cat_id = response[i].assets_cat_id;
											
											  var option = $('<option value="'+assets_cat_id+'">'+assets_cat_name+'</option>');
											  $('#assets_cat_id').append(option);
										}



									}
								});

					});
