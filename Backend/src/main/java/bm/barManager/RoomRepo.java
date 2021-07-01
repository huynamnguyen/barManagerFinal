package bm.barManager;


import org.springframework.data.mongodb.repository.MongoRepository;
import bm.items.Room;

public interface RoomRepo extends MongoRepository<Room, String> {
	public boolean existsByRoom(int room);
	public Room findByRoom(int room);
}