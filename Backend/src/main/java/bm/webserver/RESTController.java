package bm.webserver;

import org.springframework.web.bind.annotation.RestController;

import bm.barManager.CategoryRepo;
import bm.barManager.ItemRepo;
import bm.barManager.OrderRepo;
import bm.barManager.RoomRepo;
import bm.items.Article;
import bm.items.Category;
import bm.items.Item;
import bm.items.Order;
import bm.items.Room;
import bm.rooms.RoomInfo;
import bm.rooms.RoomStatus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin
@RestController
public class RESTController implements RESTControllerInfra {
	
	@Autowired
 	private ItemRepo i_repo;														// MoogoDB database that contains all articles
	@Autowired
	private CategoryRepo c_repo;													// MoogoDB database that contains all categories
	@Autowired
 	private RoomRepo r_repo;														// MoogoDB database that contains all rooms
	@Autowired
	private OrderRepo o_repo;														// MoogoDB database that contains all orders

	private int[] price_per_hour = { -1, -1, -1, -1 };
	private boolean firstcall = true;
		
	
	// Greets Asians -- for testing
	
	/*
	 * GET "localhost:8080"
	 * Resets the database, good for first start/testing
	 */
	@Override
	@RequestMapping("/")
	public String index() {
		if(firstcall) {
			fillPrices();
			firstcall = false;
		}
		// Reset the database, add some items in it for testing
		i_repo.deleteAll();
		c_repo.deleteAll();
		r_repo.deleteAll();
		o_repo.deleteAll();
		
		// Rooms
		r_repo.insert(new Room(1, -1, -1, -1));
		r_repo.insert(new Room(2, -1, -1, -1));
		r_repo.insert(new Room(3, -1, -1, -1));
		r_repo.insert(new Room(4, -1, -1, -1));
		
		// Items to buy
		i_repo.insert(new Item("Apfel", "Apple", "苹果", "Es ist rot.","It's red.","它是红色的.", "Frucht", "Fruit", "水果", 150));
		i_repo.insert(new Item("Orange",  "Orange", "橙子", "Es ist orange.","It's orange.", "是橙色的.", "Frucht", "水果", "Fruit", 201));
		i_repo.insert(new Item("Banane",  "Banana", "巴嫩", "Es ist gelb!.","It's yellow!.","黄色!", "Frucht","Fruit", "水果",  175));
		i_repo.insert(new Item("Salat",  "Lettuce", "生菜", "Es ist grün...","It's green...","它是绿色的...", "Gemüse",  "Vegetable", "蔬菜",055));
		
		// Rooms
		i_repo.insert(new Item("Huans Schlafzimmer", "Huans Schlafzimmer", "Huans Schlafzimmer", "0", "0","0","Room","Room","Room", 3000));
		i_repo.insert(new Item("Bachs Bett", "Bachs Bett", "Bachs Bett", "1","1","1", "Room", "Room", "Room", 10000));
		i_repo.insert(new Item("Hieus Toilette", "Hieus Toilette", "Hieus Toilette", "2", "2", "2", "Room", "Room", "Room", 500));
		i_repo.insert(new Item("Viet", "Viet", "Viet", "3", "3", "3", "Room", "Room", "Room", 99999));
		
		// Add the categories
		c_repo.insert(new Category("Frucht", "Fruit", "水果"));
		c_repo.insert(new Category("Gemüse", "Vegetable", "蔬菜"));
		c_repo.insert(new Category("Room", "Room", "Room"));
		
	    return "Hello asian fellows";
	}
	
	/* 
	 * POST "localhost:8080/getItem"
	 * Body: String name
	 * Returns an item with a given name
	*/
	@Override
	@RequestMapping(value="getItem/{name}", produces= MediaType.APPLICATION_JSON_VALUE)
	public Item getItem(@PathVariable String name) {
		if(firstcall) {
			fillPrices();
			firstcall = false;
		}
		try {
			Item arc = i_repo.findByName(name);
			return arc;
		} catch (Exception e) {
			return null;
		}
	}
	
	/*
	 * POST "localhost:8080/addItem"
	 * Body: Item in JSON
	 * Returns true if it was successful
	 * Returns false if the name is not unique
	 */
	@Override
	@RequestMapping(value = "addItem", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public boolean addItem(@RequestBody Item arc) {
		if(firstcall) {
			fillPrices();
			firstcall = false;
		}
		// Checks if arc is valid, name is unique and if the category exists before adding arc into the database
		if(arc == null) return false;
		if(i_repo.existsByName(arc.getName()[0])) return false;
		if(!c_repo.existsByName(arc.getCategory()[0])) {
			// Automatically add the new category into the database
			c_repo.insert(new Category(arc.getCategory()));
		}
		
		i_repo.insert(arc);
		System.out.println("Article added: " + arc);
	    return true;
	}	
	
	/*
	 * POST "localhost:8080/editItem"
	 * Body: Item in JSON
	 * Returns true if it was successful
	 * Returns false if the id doesn't exist
	 */
	@Override
	@RequestMapping(value = "editItem", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public boolean editItem(@RequestBody Item arc) {
		if(firstcall) {
			fillPrices();
			firstcall = false;
		}
		if (!i_repo.existsById(arc.getId())) 
			return false;
		
		i_repo.save(arc);
		return true;
	}
	/*
	* POST "localhost:8080/deleteItem"
	 * Body: Item in JSON
	 * Returns true if the item was successfully deleted
	 * Returns false if the id doesn't exist
	 * */
	@Override
	@RequestMapping(value = "deleteItem", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public boolean deleteItem(@RequestBody Item arc) {
		if(firstcall) {
			fillPrices();
			firstcall = false;
		}
		if (!i_repo.existsById(arc.getId())) 
			return false;
		
		i_repo.deleteById(arc.getId());
		return true;
	}
	/*
	 * GET "localhost:8080/getItems"
	 * Returns a list of "Item" in JSON format
	 */
	@Override
	@RequestMapping(value="getItems", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Item> getAllItems() {
		if(firstcall) {
			fillPrices();
			firstcall = false;
		}
		try {
			return i_repo.findAll();
		} catch (Exception e) {
			return null;
		}
	}
	
	/*
	 * POST "localhost:8080/getItems"
	 * Body: String category
	 * Returns a list of "Item" of the given category in JSON format
	 */
	@Override
	@RequestMapping(value="getItems/{category}", method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
	public List<Item> getItemsByCategory(@PathVariable String category) {
		if(firstcall) {
			fillPrices();
			firstcall = false;
		}
		if(category == null) return null;
		try {
			List<Item> arc = i_repo.findByCategory(category.replace("_", " "));
			return arc;
		} catch (Exception e) {
			return null;
		}
	}
	
	/******************** Category QUERY *********************/
	/*
	 * GET "localhost:8080/getCategories"
	 * Returns a lit of "Category" in JSON format
	 */
	@Override
	@RequestMapping(value="getCategories", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Category> getCategories() {
		if(firstcall) {
			fillPrices();
			firstcall = false;
		}
		return c_repo.findAll();
	}
	
	/*
	 * POST "localhost:8080/addCategory"
	 * Body: String category
	 * Returns true if the category is new
	 * Returns false if the category already exists
	 */
	@Override
	@RequestMapping(value ="addCategory", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public boolean addCategory(@RequestBody String[] category) {
		if(firstcall) {
			fillPrices();
			firstcall = false;
		}
		if(category == null) return false;
		if(category.length != 3) return false;
		if(!c_repo.existsByName(category[0])){
			c_repo.insert(new Category(category));
			return true;
		}
		return false;
	}
	
	/*
	 * POST "localhost:8080/deleteCategory"
	 * Body: String category
	 * Returns true if category was deleted
	 * Returns false if category doesn't exist
	 */
	@Override
	@RequestMapping(value="deleteCategory", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public boolean deleteCategory(@RequestBody String category) {
		if(firstcall) {
			fillPrices();
			firstcall = false;
		}
		if(category == null) return false;
		try {
			if(!c_repo.existsByName(category)) return false;
			c_repo.deleteByName(category);
			i_repo.deleteByCategory(category);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/*
	 * POST "localhost:8080/editCategory"
	 * Body: String category
	 * Returns true if category was edited
	 * Returns false if category doesn't exist
	 */
	@Override
	@RequestMapping(value="editCategory", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public boolean editCategory(@RequestBody Category c) {
		if(firstcall) {
			fillPrices();
			firstcall = false;
		}
		try {
			Optional<Category> oldcategory = c_repo.findById(c.getId());
			Category old_c = oldcategory.get();
			c_repo.save(c);
			
			List<Item> items = i_repo.findByCategory(old_c.getName()[0]);
			for (Item i:items) {
				i.setCategory(c.getName());
				i_repo.save(i);
			}
			return true;
			
		} catch (Exception e) { 
			return false;
		}	
	
	}
	
	/******************** RENT QUERY *********************/
	
	/*
	 * POST "localhost:8080/getStatus"
	 * Head: Room Number in Header
	 * Returns the room status
	 */
	@Override
	@RequestMapping(value="getStatus/{room_number}", method = RequestMethod.GET)
	public RoomStatus getStatus(@PathVariable int room_number) {
		if(firstcall) {
			fillPrices();
			firstcall = false;
		}
		return m_getStatus(room_number);
	}
	
	/*
	 * POST "localhost:8080/getStatus"
	 * Returns status of all rooms for the manager
	 */
	@Override
	@RequestMapping(value="getStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public RoomInfo[] getStatusAll() {
		if(firstcall) {
			fillPrices();
			firstcall = false;
		}
		return m_getStatus();
	}
	
	/*
	 * POST "localhost:8080/rent/{room}/{duration}"
	 * Head: Room Number & duration in Header
	 * Gets the room price and starts the rent for the chosen room
	 * Returns false if all rooms are occupied or room_number doesn't exists
	 */
	@Override
	@RequestMapping(value="rent/{room_number}/{duration}", method = RequestMethod.POST)
	public boolean rentRoom(@PathVariable int room_number, @PathVariable int duration) {
		if(firstcall) {
			fillPrices();
			firstcall = false;
		}
		return m_rentRoom(room_number, duration);
	}
	
	/*
	 * POST "localhost:8080/extendRent/{room}"
	 * Head: Room Number in Header
	 * Checks if room number exists, extends the rent for chosen room
	 */
	@Override
	@RequestMapping(value="extendRent/{room_number}", method = RequestMethod.POST)
	public boolean extendRent(@PathVariable int room_number) {
		if(firstcall) {
			fillPrices();
			firstcall = false;
		}
		return m_extendRoom(room_number);
	}
	
	/*
	 * POST "localhost:8080/endRent/{room}"
	 * Head: Room Number in Header
	 * Checks if room number exists, ends the rent for chosen room
	 * Returns nothing if all rooms are occupied or room_number doesn't exists
	 */
	@Override
	@RequestMapping(value="endRent/{room_number}", method = RequestMethod.POST, produces= MediaType.TEXT_PLAIN_VALUE)
	public String endRent(@PathVariable int room_number) {
		if(firstcall) {
			fillPrices();
			firstcall = false;
		}
		return m_completeRent(room_number);
	}
	
	/*
	 * POST "localhost:8080/order/{room_number}"
	 * Body: JSON Array with JSON Articles
	 * Returns new room status when successful (how much time is left & new balance)
	 */
	@Override
	@RequestMapping(value="order/{room}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public RoomStatus takeOrder(@PathVariable int room, @RequestBody List<Article> l_article) {
		if(firstcall) {
			fillPrices();
			firstcall = false;
		}

		m_addArticles(room, l_article);
		return m_getStatus(room);
	}
	
	/*
	 * POST "localhost:8080/CancelOrder/{room}"
	 * Head: Room in Header
	 * Deletes the chosen Articles for the chosen room
	 * Returns true if successful
	 * Returns False if the room isn't occupied
	 */
	@Override
	@RequestMapping(value="cancelOrder/{room}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean cancelOrder(@PathVariable int room, @RequestBody Order article) {
		if(firstcall) {
			fillPrices();
			firstcall = false;
		}
		return m_removeArticles(room, article);
	}

	/*
	 * POST "localhost:8080/discount/{room}/{value}"
	 * Head: Room and discount amount in Header
	 * Returns true if room exists
	 * Returns false if the room isn't occupied or doesn't exist
	 */
	@Override
	@RequestMapping(value="discount/{room}/{value}", method = RequestMethod.POST, produces= MediaType.TEXT_PLAIN_VALUE)
	public boolean giveDiscount(@PathVariable int room, @PathVariable int value) {
		if(firstcall) {
			fillPrices();
			firstcall = false;
		}
		return m_giveDiscount(room, value);
	}
	
	/******************** WAITER QUERY *********************/

	/*
	 * POST "localhost:8080/callWaiter/{room}"
	 * Head: Room Number in Header
	 * Signals that the room requests a waiter
	 */
	@Override
	@RequestMapping(value="callWaiter/{room_number}/{calling}", method = RequestMethod.POST)
	public boolean callWaiter(@PathVariable int room_number, @PathVariable boolean calling) {
		if(firstcall) {
			fillPrices();
			firstcall = false;
		}
		return m_setCall(room_number, calling);
	}

	/*
	 * POST "localhost:8080/confirmCall/{room}"
	 * Head: Room Number in Header
	 * Waiter comfirms that he saw the call
	 */
	@Override
	@RequestMapping(value="confirmCall/{room_number}", method = RequestMethod.POST)
	public boolean confirmCall(@PathVariable int room_number) {
		if(firstcall) {
			fillPrices();
			firstcall = false;
		}
		return m_confirmCall(room_number);
	}
	
	/*
	 * POST "localhost:8080/waiterService/{room}/{bool}"
	 * Head: Room Number & boolean whether service is required or not
	 * Activate/Deactivate normal service
	 */
	@Override
	@RequestMapping(value="waiterService/{room_number}/{activate}", method = RequestMethod.POST)
	public boolean waiterService(@PathVariable int room_number, @PathVariable boolean activate) {
		if(firstcall) {
			fillPrices();
			firstcall = false;
		}
		return m_setWaiterService(room_number, activate);
	}
	
	private void fillPrices() {
		List<Item> all_rooms = i_repo.findByCategory("Room");
		// Description of room has to be the number of the room
		for(Item i : all_rooms) {
			price_per_hour[Integer.valueOf(i.getDescription()[0])] = i.getPrice();
		}
	}
	
	// Start renting
	private boolean m_rentRoom(int room_number, int hours) {
		try {
			Room r = r_repo.findByRoom(room_number);
			if(r.getStart() != -1) return false;
			
			Date d = new Date();
			long start = d.getTime();
			r.setStart(start);
			r.setEnd(start+ (hours*3600000));
			r.setTotal_balance(hours*price_per_hour[room_number]);
			r.setCurrent_balance(hours*price_per_hour[room_number]);
			r_repo.save(r);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// Rent status
	private RoomStatus m_getStatus(int room_number) {
		try {
			Room r = r_repo.findByRoom(room_number);
			if(r.getStart() == -1) return new RoomStatus(price_per_hour[room_number]);
			return r.getStatus(price_per_hour[room_number]);
		} catch (Exception e) {
			e.printStackTrace();
			return new RoomStatus();
		}
	}
	
	// Returns info of all rooms
	private RoomInfo[] m_getStatus() {
		try {
			RoomInfo[] toReturn = new RoomInfo[4];
			for(int i=1; i<=4; i++) {
				Room r = null;
				List<Order> o = null;
				long time_left = 0;
				if(r_repo.existsByRoom(i)) {
					r = r_repo.findByRoom(i);
			 		time_left = 0;
					if(r.getStart() != -1) {
				 		o =  o_repo.findByRoom(i);
						Date d = new Date();
						time_left = ((r.getEnd()-d.getTime()) / 1000) /60;
					}
				}
				toReturn[i-1] = new RoomInfo(r,o,time_left);
			}
			return toReturn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// Extend rent by 1hour
	private boolean m_extendRoom(int room_number) {
		try {
			Room r = r_repo.findByRoom(room_number);
			if(r.getStart() == -1) return false;
			r.setEnd(r.getEnd() + 3600000);
			r.setTotal_balance(r.getTotal_balance() + price_per_hour[room_number]);
			r.setCurrent_balance(r.getCurrent_balance() + price_per_hour[room_number]);
			
			r_repo.save(r);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// Resets a room
	private void m_resetRoom(int room_number) {
		try {
			Room r = r_repo.findByRoom(room_number);
			if(r.getStart() == -1) return;
			r.reset();
			r_repo.save(r);
			
			o_repo.deleteByRoom(r.getRoom());
		} catch (Exception e) {}
	}
	
	// Complete Rent
	private String m_completeRent(int roomnumber) {
		try {
			Room room = r_repo.findByRoom(roomnumber);
			if(room.getStart() == -1) return "";
			List<Order> order = o_repo.findByRoom(roomnumber);
			System.out.println(order.size());
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
	
			m_resetRoom(roomnumber);
			return toReturn;
			
		} catch (Exception e) {
			e.printStackTrace();
			m_resetRoom(roomnumber);
			return "";
		}
	}
	
	// Adds articles into the order list
	private boolean m_addArticles(int room_number, List<Article> l) {
		try {
			Room room = r_repo.findByRoom(room_number);
			
			if(room.getStart() == -1) return false;
			System.out.println("before: " + room.getCurrent_balance());
			for(Article a : l) {
				room.reduceBalance(a.getSum());
			}
			System.out.println("now: " + room.getCurrent_balance());
			r_repo.save(room);
			
			Order o = new Order(new Date().getTime(), room_number, l);
			o_repo.insert(o);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// Remove articles in order list
	private boolean m_removeArticles(int room_number, Order order) {
		try {
			Room room = r_repo.findByRoom(room_number);
			if(room.getStart() == -1) return false;
			
			Optional<Order> old_o = o_repo.findById(order.getId());
			Order old = old_o.get();
			List<Article> old_list = old.getOrders();
			List<Article> new_list = order.getOrders();
			
			for(int i=0; i<new_list.size(); i++) {
				Article a = new_list.get(i);
				for(int j=0; j<old_list.size(); j++) {
					Article b = old_list.get(j);
					if(a.getItem().getId().equals(b.getItem().getId())) {
						room.increaseBalance(b.getSum()-a.getSum());
					}
				}
			}
			r_repo.save(room);
			
			o_repo.save(order);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	// Returns all orders
	private List<Order> m_getOrderedItems(int room) {
		try {
			Room roomm = r_repo.findByRoom(room);
			if(roomm.getStart() == -1) return null;
			List<Order> toReturn = o_repo.findByRoom(room);
			return toReturn;
		} catch (Exception e) {
			return null;
		}
	}

	// Gives a discount
	private boolean m_giveDiscount(int room, int value) {
		try {
			Room roomm = r_repo.findByRoom(room);
			if(roomm.getStart() == -1) return false;
			roomm.addDiscount(value);
			r_repo.save(roomm);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private boolean m_setCall(int room, boolean bool) {
		try {
			Room roomm = r_repo.findByRoom(room);
			if(roomm.getStart() == -1) return false;
			roomm.setCalledWaiter(bool);
			r_repo.save(roomm);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean m_confirmCall(int room) {
		try {
			Room roomm = r_repo.findByRoom(room);
			if(roomm.getStart() == -1) return false;
			roomm.setCalledWaiter(false);
			r_repo.save(roomm);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private boolean m_setWaiterService(int room_number, boolean activate) {
		try {
			Room room = r_repo.findByRoom(room_number);
			if(room.getStart() == -1) return false;
			room.setWaiterService(activate);
			r_repo.save(room);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
	
