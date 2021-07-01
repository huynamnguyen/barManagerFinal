package bm.items;

import java.util.Date;

import org.springframework.data.annotation.Id;

import bm.rooms.RoomStatus;

public class Room {
	// Defines a rent
	@Id
	private String id;
	
	private int room;									// room number
	private long start;									// When the rent started
	private long end;									// when the rent is supposed to end
	private int total_balance;							// How much balance they had
	private int current_balance;						// Their actual balance
	private int discount;								// Given discount, default = 0
	private boolean call_waiter;
	private boolean waiter_service;
	
	public Room() {
	}
	
	public Room(int room, long start, long end, int total_balance) {
		this.room = room;
		this.start = start;
		this.end = end;
		this.total_balance = total_balance;
		this.setCurrent_balance(total_balance);
		this.discount = 0;
		this.call_waiter = false;
		this.waiter_service = false;
	}
	
	public Room(int room, long start, long end, int total_balance, int current_balance, int discount,
			boolean call_waiter, boolean waiter_service) {
		this.room = room;
		this.start = start;
		this.end = end;
		this.total_balance = total_balance;
		this.current_balance = current_balance;
		this.discount = discount;
		this.call_waiter = call_waiter;
		this.waiter_service = waiter_service;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getRoom() {
		return room;
	}

	public void setRoom(int room) {
		this.room = room;
	}
	
	public void reset() {
		this.start = -1;
		this.end = -1;
		this.total_balance = -1;
		this.current_balance = -1;
		this.discount = 0;
		this.call_waiter = false;
		this.waiter_service = false;
	}

	public boolean isCalledWaiter() {
		return call_waiter;
	}

	public void setCalledWaiter(boolean calledWaiter) {
		this.call_waiter = calledWaiter;
	}	

	public boolean isWaiterService() {
		return waiter_service;
	}

	public void setWaiterService(boolean waiterService) {
		this.waiter_service = waiterService;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public int getTotal_balance() {
		return total_balance;
	}

	public void setTotal_balance(int total_balance) {
		this.total_balance = total_balance;
	}
	
	public void increaseEnd(long duration) {
		this.end += duration;
	}
	
	public int getCurrent_balance() {
		return current_balance;
	}

	public void setCurrent_balance(int current_balance) {
		this.current_balance = current_balance;
	}
	
	public void reduceBalance(int cost) {
		this.setCurrent_balance(this.current_balance - cost);
	}
	
	public void addDiscount(int cost) {
		this.discount += cost;
	}
	
	public int getDiscount() {
		return this.discount;
	}
	
	public void increaseBalance(int credit) {
		this.setCurrent_balance(this.current_balance + credit);
	}
	
	public RoomStatus getStatus(int price_per_hour) {
		Date d = new Date();
		long duration_left = this.getEnd() - d.getTime();
		return new RoomStatus((int) ((duration_left/1000)/60), this.getCurrent_balance(), call_waiter, waiter_service, price_per_hour);
	}
	
	@Override
	public String toString() {
		return room + " - " + start + " - " + end + " - " + total_balance + " - " + current_balance + " - " + discount + " - " + call_waiter + " - " + waiter_service;
	}
	
}
