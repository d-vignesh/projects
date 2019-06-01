



$(document).ready( function () {
	$(document).on('click' , '#submitbtn' , function (event) {
		
		var category = document.getElementById("category").value ; 
		var productname = document.getElementById("productname").value ; 
		var price = document.getElementById("price").value ; 
		
		
		
		if( category === "" || productname === "" || price === "")
			{
			$("#responsemsg").html("all fields are mandatory") ; 
			setTimeout(function() {
				window.location.reload(1);},1000) ; 
			}
		else {
		
				var formdata = {
					'category' : category ,
					'productname' : productname ,
					'price' : price ,
				};
				$.ajax({
					url : 'ChangePrice' , 
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
				document.getElementById("price").value = "" ; 
		
	}) ; 
	
});

function back(elem ) 
		{
			elem.setAttribute("action" , "dealerpage.jsp") ; 
			elem.submit() ; 
		}