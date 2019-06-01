package systempackage;

public class ReportClass {
	
	private String category ; 
	private String productname ;
	private int price ; 
	private int quantity ;
	
	public String getCategory() {
		return category ; 
	}
	public void setCategory(String category )
	{
		 this.category = category; 
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	} 
	
}
