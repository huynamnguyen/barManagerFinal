package bm.items;

import org.springframework.data.annotation.Id;

public class Category {
	// Category class for moogoDB
	// Describes a category of the menu
	
	@Id
	private String id;
	
	private String[] name;
	
	public Category() {
		this.name = new String[3];
	}
	
	public Category(String[] name) {
		this.name = name;
	}
	
	public Category(String name, String name2, String name3) {
		this.name = new String[3];
		this.name[0] = name;
		this.name[1] = name2;
		this.name[2] = name3;
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
	
	@Override
	public String toString() {
		return this.name[0] + ", " + this.name[1] + ", " + this.name[2];
	}
}
