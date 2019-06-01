




	$(document).on('click' , '#removebtn' , function(event) {
	$('#responsemsg').html("") ; 
	var row = $(this).parents('tr');
	
	var price = $(document).find("#total").val();
	
	var id = row.find('input[name = "id"]').val() ;
	var formdata = {
			'id' : id 
	}
	
	
	$.ajax({
		url : 'RemoveProduct' ,
		type : 'POST' ,
		data : formdata ,
		success : function(response) {
			$("#responsemsg").html("removed successfully");
			price -= response ;
			$("#total").html(price);
			row.remove();
		} ,
		error : function(e) {
			error(e);
		}
	
	}) ; 
	event.preventDefault();
});