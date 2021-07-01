## Requirements:
1. Eclipse (https://www.eclipse.org/downloads/)
2. MoogoDB (https://docs.mongodb.com/manual/administration/install-community/)

## Deploy Back-End:
1. Import the project in Eclipse (Maven Project)
2. Run as java application: src/main/java/bm/barManager/BarManagerApplication.java
3. Server can be accessed at localhost:8080
4. Interface can be seen in src/main/java/bm/webserver/RESTControllerInfra.java

## Data Structures 

+ ID of item & category are assigned by the database, so leave it empty when adding new items/categories
+ If you want to edit items or categories, use the ID that was assigned to them (call getItems or getCategories to get the IDs)

| Structure | Format as Response |
| --- | --- |
| Item | { "id" : String, "name" : [x,y,z], "description" : [x,y,z], "category" : [x,y,z], "price" : float } |
| JSON Array of Items | [ { "id" : String, "name" : [x,y,z], "description" : [x,y,z], "category" : [x,y,z], "price" : float }, { "id" : String, "name" : [x,y,z], "description" : [x,y,z], "category" : [x,y,z], "price" : float }, ..] |
| Category | { "id" : String, "name" : [x,y,z] } |
| JSON ARRAY of Category | [ { "id" : String, "name" : [x,y,z] }, { "id" : String, "name" : [x,y,z] }, .. ] |
| RoomStatus | { "time_remaining" : int, "current_balance" : float } | 
| Array of RoomInfo | [ { "room" : Room, "order" : [ { Order }, ..], "time_left" : long |
| Room | { "id" : String, "room_number" : int, "start" : long, "end" : long, "total_balance" : int, "current_balance" : int, "discount" : int, "call_waiter" : boolean, "waiter_service" : boolean } |
| Order | { "id" : String, "d" : long, "room_number" : int, "orders": [ { Article }, .. ] |
| Article | { "item" : Item, "quantity" : int } |


## Communication for the Guest Client

| Method | Address | Body | Return | Description |
| --- | --- | --- | --- | --- |
| `GET` | localhost:8080/getItems | none | JSON Array of Items | Gets a list of all items |
| `GET` | localhost:8080/getItems/{category} (if category contains spaces, replace with "_")| none | JSON Array of Items | Gets a list of all items of a certain category |
| `GET` | localhost:8080/getStatus/{room_number} | none | Returns a JSON of RoomStatus | Returns the status of a specific room | 
| `POST` | localhost:8080/order/{room_number} | List of Article (f.e. [{item, quantity}, {item, quantity}..]) | Returns a JSON RoomStatus | Take an order |
| `POST` | localhost:8080/callWaiter/{room_number} | none | Returns a boolean (room needs to be active) | Calls the waiter |

## Communication for the Manager Client

| Method | Address | Body | Return | Description |
| --- | --- | --- | --- | --- |
| `GET` | localhost:8080/getItems | none | JSON Array of Items | Gets a list of all items |
| `GET` | localhost:8080/getItems/{category} (if category contains spaces, replace with "_") | none | JSON of Items | Gets a list of all items of a certain category |
| `POST` | localhost:8080/addItem | Item | Returns a boolean (name needs to be unique) | Adds a new item into the database |
| `POST` | localhost:8080/editItem | Item | Returns true if the ID exists (get the ID through getItems) | Modify an item (everything can be edited except the assigned ID) |
| `GET` | localhost:8080/getCategories | none | Array of Categories | Gets a list of categories |
| `POST` | localhost:8080/addCategory | String[3] category (de, en, cn) | Returns a boolean (name needs to be unique) | Adds a new category into the database |
| `POST` | localhost:8080/editCategory | Category (id and string) | Returns a boolean | If the ID exists, replaces the old category with the new one and adjusts the items as well |
| `POST` | localhost:8080/deleteCategory | Category string (language is optional) | Returns a boolean | Deletes a category and all items within |
| `GET` | localhost:8080/getStatus | none | Returns JSON Array of RoomInfo (size = 3) | Gives a full overview of all rooms |
| `POST` | localhost:8080/rent/{room_number}/{duration} (in h) | none | Returns a boolean | Starts a rent of a specific room for a specific duration |
| `POST` | localhost:8080/extendRent/{room_number} | none | Returns a boolean | Extends a room's rent by an hour and adds an hourly credit |
| `POST` | localhost:8080/endRent/{room_number} | none | Returns a String (the bill) | Resets the room |
| `POST` | localhost:8080/order/{room_number} | List of Article (f.e. [{item, quantity}, {item, quantity}..]) | Returns RoomStatus if it was successful | Take an order |
| `POST` | localhost:8080/cancelOrder/{room_number} | edited Order | Returns a boolean | Cancels specific articles of a room |
| `POST` | localhost:8080/discount/{room_number}/{value} | none | Returns a boolean | Increases the discount of a specific room |
| `POST` | localhost:8080/comfirmCall/{room_number} | none | Returns a boolean | Signifies that the waiter saw and comfirmed the call |



Helpful tool to test POST functions: Postman

1. Install: https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop?hl=en
2. Import barManager.postman_collection.json into Postman (can be found in the folder "Postman (for testing)")

