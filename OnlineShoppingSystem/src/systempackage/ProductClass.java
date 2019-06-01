package systempackage;

public class ProductClass implements Comparable<ProductClass> {
	private String productname ; 
	private String dealer ; 
	private int quantity ; 
	private int price ;
	
		
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getDealer() {
		return dealer;
	}
	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	} 
	
	public int compareTo(ProductClass product )
	{
		return this.getProductname().compareTo(product.getProductname()) ; 
	}
	
	public String toString()
	{
		return null ; 
	}
	
}
