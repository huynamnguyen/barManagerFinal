package bm.barManager;



import org.springframework.data.mongodb.repository.MongoRepository;

import bm.items.Category;

public interface CategoryRepo extends MongoRepository<Category, String> {

  public boolean existsByName(String name);					// Indicates if a category with a given name already exists
  public Category findByName(String name);					// Returns the category
  public void deleteByName(String name);					// Delete by name
}