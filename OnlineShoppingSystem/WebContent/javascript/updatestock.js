






$(document).ready( function () {
	$(document).on('click' , '#submitbtn' , function (event) {
		alert("button clicked");
		var category = document.getElementById("category").value ; 
		var productname = document.getElementById("productname").value ; 
		var quantity = document.getElementById("quantity").value ; 
		
		
		
		if( category === "" || productname === "" || quantity === "")
			{
			$("#responsemsg").html("all fields are mandatory") ; 
			setTimeout(function() {
				window.location.reload(1);},1000) ; 
			}
		else {
		
				var formdata = {
					'category' : category ,
					'productname' : productname ,
					'quantity' : quantity
				};
				$.ajax({
					url : 'ChangeStock' , 
					type : 'POST' ,
					data : formdata ,
					success :  function(response) {
						
						$("#responsemsg").html(response) ; 
						setTimeout(function() {
							window.location.reload(1);},1000) ; 
						
					} ,
					error : function() {
						alert("error");
					}
					
				});
		}
				event.preventDefault();
				document.getElementById("category").value = " " ; 
				document.getElementById("productname").value = " " ; 
				document.getElementById("quantity").value = "" ; 
		
	}) ; 
	
});

function back(elem ) 
		{
			elem.setAttribute("action" , "dealerpage.jsp") ; 
			elem.submit() ; 
		}