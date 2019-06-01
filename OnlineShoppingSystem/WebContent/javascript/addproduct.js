

$(document).ready( function () {
	$(document).on('click' , '#submitbtn' , function (event) {
		
		var category = $("input[name='category']").val() ; 
		var productname = $("input[name='productname']").val() ; 
		var price = $("input[name='price']").val() ; 
		var quantity = $("input[name='quantity']").val() ;
		
		
		
		if( category === "" || productname === "" || price === "" || quantity === "")
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
					'quantity' : quantity ,	
				};
				$.ajax({
					url : 'AddProduct' , 
					type : 'POST' ,
					data : formdata ,
					success :  function(response) {
						
						$.ajax({
							url : 'GenerateReport',
							type : 'GET',
							success : function(response) {
								$('#reportpage').load('reportgenerationpage.jsp');
							}
						})
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
				document.getElementById("quantity").value = "" ; 
		
	}) ; 
	
});







		

function back(elem ) 
		{
			elem.setAttribute("action" , "dealerpage.jsp") ; 
			elem.submit() ; 
		}