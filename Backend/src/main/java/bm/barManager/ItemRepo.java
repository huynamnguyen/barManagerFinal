package bm.barManager;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import bm.items.Item;

public interface ItemRepo extends MongoRepository<Item, String> {

  public Item findByName(String name);						// Finds an article by name
  public List<Item> findByDescription(String description);	// Finds all articles with a given description -- maybe unncessary and can be deleted
  public List<Item> findByCategory(String category);		// Finds all articles of a given category
  public boolean existsByName(String name);					// Indicates if an article with a given name already exists
  public void deleteByCategory(String category);			// Deletes all items of a category
}