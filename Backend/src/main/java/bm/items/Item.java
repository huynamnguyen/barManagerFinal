package bm.items;

import org.springframework.data.annotation.Id;

public class Item {
	// Item Class for moogoDB
	// Describes an item for sale
	
	@Id
	private String id;
	
	private String[] name;
	private String[] description;
	private String[] category;
	private int price;
	
	public Item() {
		this.name = new String[3];
		this.description = new String[3];
		this.category = new String[3];
	}
	
	public Item(String name, String name2, String name3, String description, String description2, String description3, String category, String category2, String category3, int price) {
		this.name = new String[3];
		this.name[0] = name;
		this.name[1] = name2;
		this.name[2] = name3;
		this.description = new String[3];
		this.description[0] = description;
		this.description[1] = description2;
		this.description[2] = description3;
		this.category = new String[3];
		this.category[0] = category;
		this.category[1] = category2;
		this.category[2] = category3;
		this.price = price;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getName() {
		return name;
	}

	public void setName(String[] name) {
		this.name = name;
	}

	public String[] getDescription() {
		return description;
	}

	public void setDescription(String[] description) {
		this.description = description;
	}

	public String[] getCategory() {
		return category;
	}

	public void setCategory(String[] category) {
		this.category = category;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Article[" + this.id + "]= Name: " + this.name + "; Desc: " + this.description + "; Price: " + this.price + ";";
	}
}
