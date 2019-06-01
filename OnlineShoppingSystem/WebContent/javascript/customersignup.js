



$(document).ready( function() {
	$(document).on('click' , '#submitbtn' , function(event) {
		var username = document.getElementById("username").value;
		var password = document.getElementById("password").value;
		
		if( username === "" || password == "")
		{
			$("#responsemsg").html("all fields are mandatory");
			setTimeout( function() {
				window.location.reload(1)},1000) ; 
	
		}
		
		else {
			var formdata = {
				'username' : username ,
				'password' : password
			};
			
			$.ajax({
				url : 'CustomerSignUp' , 
				type : 'POST' ,
				data : formdata ,
				success : function(response) {
					var res = response.trim() ; 
					
					if( res === "signedup")
						{
						window.location.href = "customerlogin.jsp" ; 
						}
					else {
						$("#responsemsg").html("username already exists");
						setTimeout( function() {
							window.location.reload(1)},1000) ; 
					}
				}
			});
		}
		event.preventDefault() ; 
		document.getElementById("username").value = "" ; 
		document.getElementById("password").value = "" ; 
	});
});