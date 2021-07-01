package bm.webserver;

import java.util.List;

import bm.items.Article;
import bm.items.Category;
import bm.items.Item;
import bm.items.Order;
import bm.rooms.RoomInfo;
import bm.rooms.RoomStatus;

public interface RESTControllerInfra {

	/*
	 * GET "localhost:8080"
	 * Resets the database, good for first start/testing
	 */
	String index();

	/******************** ITEM QUERY *********************/

	/* 
	 * POST "localhost:8080/getItem"
	 * Body: String name
	 * Returns an item with a given name
	*/
	Item getItem(String name);

	/*
	 * POST "localhost:8080/addItem"
	 * Body: Item in JSON
	 * Returns true if it was successful
	 * Returns false if the name is not unique
	 */
	boolean addItem(Item arc);
	
	/*
	 * POST "localhost:8080/editItem"
	 * Body: Item in JSON
	 * Returns true if it was successful
	 * Returns false if the id doesn't exist
	 */
	boolean editItem(Item arc);
	
	/*
	* POST "localhost:8080/deleteItem"
	 * Body: Item in JSON
	 * Returns true if the item was successfully deleted
	 * Returns false if the id doesn't exist
	 * */
	boolean deleteItem(Item arc);

	/******************** ITEMS QUERY ********************/

	/*
	 * GET "localhost:8080/getItems"
	 * Returns a list of "Item" in JSON format
	 */
	List<Item> getAllItems();

	/*
	 * POST "localhost:8080/getItems"
	 * Body: String category
	 * Returns a list of "Item" of the given category in JSON format
	 */
	List<Item> getItemsByCategory(String category);

	/******************** CATEGORY QUERY ********************/
	
	/*
	 * GET "localhost:8080/getCategories"
	 * Returns a lit of "Category" in JSON format
	 */
	List<Category> getCategories();

	/*
	 * POST "localhost:8080/addCategory"
	 * Body: String category
	 * Returns true if the category is new
	 * Returns false if the category already exists
	 */
	boolean addCategory(String[] category);

	/*
	 * POST "localhost:8080/deleteCategory"
	 * Body: String category
	 * Returns true if category was deleted
	 * Returns false if category doesn't exist
	 */
	boolean deleteCategory(String category);
	
	/*
	 * POST "localhost:8080/editCategory"
	 * Body: String category
	 * Returns true if category was edited
	 * Returns false if category doesn't exist
	 */
	boolean editCategory(Category c);

	/******************** ORDER QUERY ********************/

	/*
	 * POST "localhost:8080/order/{room_number}"
	 * Body: JSON Array with JSON Articles
	 * Returns new room status when successful (how much time is left & new balance)
	 */
	// Guest und kellner
	RoomStatus takeOrder(int room, List<Article> l_article);
	
	 /* POST "localhost:8080/cancelOrder/{room}"
	 * Head: Room in Header
	 * Deletes the chosen Articles for the chosen room
	 * Returns true if successful
	 * Returns False if the room isn't occupied
	 */
	boolean cancelOrder(int room, Order article);

	/*
	 * POST "localhost:8080/giveDiscount/{room}/{value}" 
	 * Gives Discount for the chosen room
	 */
	
	boolean giveDiscount(int room, int value);
	
	/******************** RENT QUERY ********************/
	
	/*
	 * GET "localhost:8080/getStatus/{room_number}"
	 * Head: Room Number in Header
	 * Returns the room status
	 */
	RoomStatus getStatus(int room_number);
	
	/*
	 * GET "localhost:8080/getStatus"
	 * Returns status of all rooms for the manager
	 */
	RoomInfo[] getStatusAll();

	/*
	 * POST "localhost:8080/rent/{room}/{duration}"
	 * Head: Room Number & duration in Header
	 * Gets the room price and starts the rent for the chosen room
	 * Returns false if all rooms are occupied or room_number doesn't exists
	 */
	boolean rentRoom(int room_number, int duration);
	
	/*
	 * POST "localhost:8080/endRent/{room}"
	 * Head: Room Number in Header
	 * Checks if room number exists, ends the rent for chosen room
	 * Returns nothing if all rooms are occupied or room_number doesn't exists
	 */
	String endRent(int room_number);

	/*
	 * POST "localhost:8080/extendRent/{room}"
	 * Head: Room Number in Header
	 * Checks if room number exists, extends the rent for chosen room
	 */
	boolean extendRent(int room_number);
	
	/* 1. Kellner rufen (z.B. wenn etwas verschüttelt wurde, Mikrofonbatterie leer ist)
	 * 2. Normalen Service fordern (Man will das System nicht benutzen sondern normalen Service bekommen)
	 * 3. Bestellung canceln (Falls etwas was bestellt wurde, gelöscht werden soll)
	 * 
	 */
	
	/******************** WAITER QUERY ********************/
	
	/*
	 * POST "localhost:8080/callWaiter/{room}"
	 * Head: Room Number in Header
	 * Signals that the room requests a waiter
	 */
	boolean callWaiter(int room_number, boolean calling);
	
	/*
	 * POST "localhost:8080/comfirmCall/{room}"
	 * Head: Room Number in Header
	 * Waiter comfirms that he saw the call
	 */
	boolean confirmCall(int room_number);
	
	/*
	 * POST "localhost:8080/waiterService/{room}/{bool}"
	 * Head: Room Number & boolean whether service is required or not
	 * Activate/Deactivate normal service
	 */
	boolean waiterService(int room_number, boolean activate);

	
}