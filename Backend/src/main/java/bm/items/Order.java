package bm.items;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Order {
	// Defines a rent
	@Id
	private String id;
	
	private long d;										// Date of the order
	private int room;							// Which room ordered
	private List<Article> orders;						// List of articles
	
	public Order() {
	}

	public Order(long d, int room, List<Article> orders) {
		this.d = d;
		this.room = room;
		this.orders = orders;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getD() {
		return d;
	}

	public void setD(long d) {
		this.d = d;
	}

	public int getRoom() {
		return room;
	}

	public void setRoom(int room) {
		this.room = room;
	}

	public List<Article> getOrders() {
		return orders;
	}

	public void setOrders(List<Article> orders) {
		this.orders = orders;
	}	
	
}
