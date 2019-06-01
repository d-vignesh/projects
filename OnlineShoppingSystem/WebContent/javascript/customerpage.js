

$(document).ready(function() {
	$(document).on('click' , '#searchbtn' , function(event) {
		
		var categoryToSearch = document.getElementById("searchcategory").value ;
		
		if( categoryToSearch === "")
			{
			
			$("#responsemsg").html("enter a category to search") ; 
			}
		else {
			
			$.ajax({
				url : 'SearchByCategory' , 
				type : 'POST' ,
				data : { 'searchcategory' : categoryToSearch } ,
				success : function(response) {
					if( response === "found")
					{
						
						window.location.href = "searchcategoryresult.jsp" ;
					}
					else
					{
						$("#responsemsg").html("no result found");
					}
				} ,
				
				error : function(e) {
					
				}
			});
		}
		event.preventDefault() ;
	});
});