
var product_choosed = 0 ; 
var button ;

$(document).ready(function() {
	$(document).on('click' , '#submitbtn' , function(event) {
		
		var row = $(this).parents('tr');
		var btn = $(this);
		var category = row.find('input[name = "category"]').val() ;
		var dealer = row.find('input[name = "dealer"]').val() ;
		var productname = row.find('input[name = "productname"]').val() ;
		var price = row.find('input[name = "price"]').val() ;
		var quantity = row.find('input[name = "quantity"]').val() ;
		product_choosed += 1 ;
		
		
		btn.val("added"); 
		btn.prop('disabled' ,true) ;
		
		var formdata = {
			'price': price ,
			'dealer' : dealer , 
			'productname' : productname ,
			'quantity' : quantity 
		} ; 
		
		$.ajax ({
			url : 'GenerateCompareList' , 
			type : 'POST' ,
			data : formdata ,
			success : function() {
				
		
			},
			error : function() {
				alert("error");
			}
		});
		event.preventDefault() ; 
	});
}) ;


$(document).on('click' , '#compare' , function(event) {
	
	if( product_choosed <= 1 )
		{
		$("#responsemsg").html("add more than one products to compare") ;
		}
	else
		{
		window.location.href = "comparisionlist.jsp" ; 
		}
	event.preventDefault() ;
});


function back(elem)
{
	elem.setAttribute("action" , "displayproducts.jsp");
	elem.sumbit() ;
}