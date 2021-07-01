package bm.barManager;



import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import bm.items.Order;

public interface OrderRepo extends MongoRepository<Order, String> {

  public List<Order> findByRoom(int room);
  public void deleteByRoom(int room);
  
}