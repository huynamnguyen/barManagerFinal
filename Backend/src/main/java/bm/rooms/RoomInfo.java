package bm.rooms;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import bm.items.Article;
import bm.items.Order;
import bm.items.Room;

public class RoomInfo {

	private Room room;
	private List<Order> order;
	private long time_left;
	
	public RoomInfo(Room room, List<Order> order, long time_left) {
		this.room = room;
		this.order = order;
		this.time_left = time_left;
	}

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public long getTime_left() {
		return time_left;
	}

	public void setTime_left(long time_left) {
		this.time_left = time_left;
	}

	@Override
	public String toString() {
		String toReturn = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		// Maybe html to pdf? I don't know what a receipt requires
		
		toReturn += "COMPANY NAME\n";
		toReturn += "----------------------------------\n";
		toReturn += "Ordered articles:\n";
		for(Order orderr : order) {
			for(Article s : orderr.getOrders()) {
				toReturn += s.getQuantity() + "x " + s.getItem().getName() + " - " + s.getQuantity() * s.getPrice() + "€\n";
			}
		}
		toReturn += "----------------------------------\n";
		float total = (room.getCurrent_balance() < 0 ? room.getTotal_balance()+(room.getCurrent_balance()*-1)-room.getDiscount() : room.getTotal_balance()- room.getDiscount());
		toReturn += "Total: " + total + "€\n";
		toReturn += "Date: " + sdf.format(calendar.getTime()) + "\n";
		return toReturn;
	}
}
