package bm.rooms;

public class RoomStatus {
	// For Spring so it can automatically generate a JSON of it
	
	int time_remaining; 		// in minutes
	int current_balance;
	boolean waiter_called;
	boolean waiter_service;
	int price_per_hour;
	
	public RoomStatus() {
		time_remaining = 0;
		current_balance = 0;
		waiter_called = false;
		waiter_service = false;
		price_per_hour = 0;
	}
	
	public RoomStatus(int price_per_hour) {
		time_remaining = 0;
		current_balance = 0;
		waiter_called = false;
		waiter_service = false;
		this.price_per_hour = price_per_hour; 
	}
	
	public RoomStatus(int time_remaining, int current_balance, boolean waiter_called, boolean waiter_service, int price_per_hour) {
		this.time_remaining = time_remaining;
		this.current_balance = current_balance;
		this.waiter_called = waiter_called;
		this.waiter_service = waiter_service;
		this.price_per_hour = price_per_hour;
	}

	public int getPrice_per_hour() {
		return price_per_hour;
	}

	public void setPrice_per_hour(int price_per_hour) {
		this.price_per_hour = price_per_hour;
	}

	public int getTime_remaining() {
		return time_remaining;
	}

	public void setTime_remaining(int time_remaining) {
		this.time_remaining = time_remaining;
	}

	public int getCurrent_balance() {
		return current_balance;
	}

	public void setCurrent_balance(int current_balance) {
		this.current_balance = current_balance;
	}

	public boolean isWaiter_called() {
		return waiter_called;
	}

	public void setWaiter_called(boolean waiter_called) {
		this.waiter_called = waiter_called;
	}

	public boolean isWaiter_service() {
		return waiter_service;
	}

	public void setWaiter_service(boolean waiter_service) {
		this.waiter_service = waiter_service;
	}
	
}
