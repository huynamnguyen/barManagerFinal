package bm.items;

public class Article {
	// Used by the RoomManager to add items into the list of orders
	
	private Item item;
	private int quantity;
	
	public Article (Item item, int quantity) {
		this.item = item;
		this.quantity = quantity;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void addQuantity(int additional) {
		this.quantity += additional;
	}
	
	public int getSum() {
		return this.quantity * this.item.getPrice();
	}
	
	public int getPrice() {
		return item.getPrice();
	}

	public void setPrice(int price) {
		item.setPrice(price);
	}

	@Override
	public String toString() {
		return this.quantity +"x " + this.item + " ----------- " + this.item.getPrice() * this.quantity + "€"; 
	}
}
