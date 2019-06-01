





$(document).on('click' , '#bsubmitbtn' , function(event) {
	$('#responsemsg').html("") ; 
	var row = $(this).parents('tr');
	var category = row.find('input[name = "category"]').val() ;
	var dealer = row.find('input[name = "dealer"]').val() ;
	var productname = row.find('input[name = "productname"]').val() ;
	var price = row.find('input[name = "price"]').val() ;
	var quantity = row.find('input[name = "quantity"]').val() ;
	
	
	
	var formdata = {
			'category' : category , 
			'dealer' : dealer ,
			'productname' : productname ,
			'price' : price ,
			'quantity' : quantity
	} ; 
	
	
	$.ajax ({
		url : 'BuyProduct' ,
		type : 'POST' , 
		data : formdata ,
		success : function(response) {
			response = response.trim() ;
			if( response === "failed")
				{
					$("#responsemsg").html("no sufficient quantity") ; 
				}
			else {
				$("#responsemsg").html("successfully ordered") ;
				
				row.find('td[name = "available_quantity"]').html(response);
				
			}
			
		} ,
	
		error : function(e) {
			alert(e) ;
		}
	}) ; 
	event.preventDefault() ;
});



$(document).on('click' , '#csubmitbtn' , function(event) {
	$('#responsemsg').html("") ; 
	var row = $(this).parents('tr');
	var category = row.find('input[name = "category"]').val() ;
	var dealer = row.find('input[name = "dealer"]').val() ;
	var productname = row.find('input[name = "productname"]').val() ;
	var price = row.find('input[name = "price"]').val() ;
	var quantity = row.find('input[name = "quantity"]').val() ;
	
	
	var formdata = {
			'category' : category , 
			'dealer' : dealer ,
			'productname' : productname ,
			'price' : price ,
			'quantity' : quantity ,
			
	} ; 
	
	$.ajax({
		url : 'AddToCart' , 
		type : 'POST' ,
		data : formdata , 
		success : function(response) {
			$("#responsemsg").html(response) ;
		} ,
		
		error : function() {
			alert("error") ; 
		}
	})
	event.preventDefault() ;
});

function back(elem ) 
{
	elem.setAttribute("action" , "displayproducts.jsp") ; 
	elem.submit() ; 
}