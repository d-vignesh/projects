








$(document).ready(function() {
	
	$(document).on('click' , '#submitbtn' , function(event) {
		
		var username = document.getElementById("username").value ; 
		var password = document.getElementById("password").value ; 
		
		if( username ==="" || password === "" )
			{
			$("#responsemsg").html("all fields are mandatory") ; 
			setTimeout(function() {
				window.location.reload(1);},1000) ;
			}
		else {
			
			var formdata = {
				'username' : username ,
				'password' : password 
			};
				$.ajax ({
					url : 'DealerValidateServlet'  ,
					type : 'POST' ,
					data : formdata ,
					
					success : function(response) {
						 var check ="valid" ; 
						 var result = $.trim(response) ; 
						
						 if( check == result) {
									
									window.location.href = "dealerpage.jsp" ; 
								}
						else
							{
							$("#responsemsg").html("not a user , sign up if new user") ; 
							setTimeout(function() {
								window.location.reload(1);},1000) ;
							}
					} ,
					
					error : function (e) {
						alert("error !!"+ e) ;
					}
					
				});	
		}
		
		event.preventDefault() ; 
		document.getElementById("username").value = "";
		document.getElementById("password").value = "";
		
	});
});




function back(elem ) 
{
	elem.setAttribute("action" , "login.jsp") ; 
	elem.submit() ; 
}

