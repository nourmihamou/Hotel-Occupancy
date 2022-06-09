import java.util.ArrayList;

/**
 * This class represents a hotel with a list of available rooms
 * and a list of reserved rooms.
 * 
 * @author Nour Mihamou and Lulama Nyembe
 * @version 04/28/21
 */
public class Hotel
{
    private ArrayList<Room> available;
    private ArrayList<Room> reserved;

    /**
     * Construct a hotel with an empty list of available rooms
     * and and empty list of reserved rooms.
     */
    public Hotel()
    {
        available = new ArrayList<Room>();
        reserved = new ArrayList<Room>();
    }

    /**
     * Construct a hotel with an input list of available rooms and
     * an input list of reserved rooms.
     * 
     * @param availableRooms The list of available rooms.
     * @param reservedRooms The list of reserved rooms.
     */
    public Hotel(ArrayList<Room> availableRooms, ArrayList<Room> reservedRooms)
    {
        available = availableRooms;
        reserved = reservedRooms; 
    }

    /**
     * Returns the list of available rooms.
     * 
     * @return The list of available rooms.
     */
    public ArrayList<Room> getAllAvailableRooms()
    {
        return available;
    }

    /**
     * Returns the list of reserved rooms.
     * 
     * @return The list of reserved rooms.
     */
    public ArrayList<Room> getAllReservedRooms()
    {
        return reserved;
    }

    /**
     * Returns the room associated with the input room number, as long as it is available.
     * 
     * @param roomNumber The requested room number.
     * @return The room associated with the input room number or null if the room is not available.
     */
    public Room getAvailableRoomByRoomNumber(int roomNumber)
    {
        Room room = null;
        for(int i = 0; i < available.size(); i++){
            int roomNum = available.get(i).getRoomNumber();
            if(roomNum == roomNumber){
                room = available.get(i);
            } else {
                room = room;
            }
        }
        return room;
    }

    /**
     * Returns a list of the available rooms matching the input criteria.  
     * Rooms on or above the floor requested are considered, as well as rooms with at least the maximum capacity.
     * 
     * @param floor The lowest requested floor on which the room can be located.
     * @param view The requested view from the room.
     * @param maxCapacity The lowest maximum capacity requested.
     * @param kitchenette True if a kitchenette is requested and false otherwise.
     * @return A list of the available rooms matching the input criteria.
     */
    public ArrayList<Room> getAvailableRooms(int floor, View view, int maxCapacity, boolean kitchenette)
    {
        ArrayList<Room> listCriteria = new ArrayList<Room>();

        int index = 0;
        while(index < available.size()){
            if (available.get(index).getView() == view){
                if(available.get(index).hasKitchenette() == kitchenette){
                    if(available.get(index).getMaxCapacity() >= maxCapacity){
                        if(available.get(index).getRoomNumber() / 100 >= floor) {
                            listCriteria.add(available.get(index));
                        }
                    }
                }           
            }
            index++;
        }

        return listCriteria;
    }

    /**
     * Returns the room associated with the input room number, as long as it is reserved.
     * 
     * @param roomNumber The requested room number.
     * @return The room associated with the input room number or null if the room is not reserved.
     */
    public Room getReservedRoomByRoomNumber(int roomNumber)
    {
        Room room = null;    

        for(int i = 0; i < reserved.size(); i++){
            int roomNum = reserved.get(i).getRoomNumber();
            if(roomNum == roomNumber){
                room = reserved.get(i);
            }
        }

        return room;
    }

    /**
     * Adds a room to the list of available rooms if it has a room number
     * not already listed in the hotel.  Room is added so room numbers are in 
     * increasing numerical order in the list of available rooms.
     * 
     * @param room The room to add to the hotel.
     * @return True if the room number is not already listed in the hotel
     *         and false otherwise.
     */
    public boolean addNewRoom(Room room)
    {
        boolean added = false;
        int index = 0;

        Room roomAdded = getAvailableRoomByRoomNumber(room.getRoomNumber());
        
        if(roomAdded == null){
            while(!added && index < available.size()){
                if(available.get(index).getRoomNumber() <= room.getRoomNumber()){
                    index++;
                } else{
                    added = true;
                }
            }
            available.add(index, room);
        }

        return added;
    }

    /**
     * Adds a room to the list of available rooms if it has a room number
     * not already listed in the hotel.  Room is added so room numbers are in 
     * increasing numerical order in the list of available rooms.
     * 
     * @param roomNumber The room number.
     * @param view The view from the room.
     * @param maxCapacity  The maximum number of guests that may stay in the room.
     * @param kitchenette True if the room has a kitchenette and false otherwise.
     * @return True if the room number is not already listed in the hotel
     *         and false otherwise.
     */
    public boolean addNewRoom(int roomNumber, View view, int maxCapacity, boolean kitchenette)
    {
        Room room = new Room(roomNumber, view, maxCapacity,kitchenette);
        return addNewRoom(room);
    }

    /**
     * If the room with the input room number is availble, reserves the room and returns true.  Otherwise, returns false.
     * Reserved room is added so room numbers are in increasing numerical order in the list of reserved rooms.
     * 
     * 
     * @param roomNumber The requested room number to be reserved.
     * @return True if the room is successfully reserved and false otherwise.
     */
    public boolean reserveRoom(int roomNumber)
    {
        boolean isReserved = false;
        int index = 0;

        Room reservedRoom = getAvailableRoomByRoomNumber(roomNumber);
        
        if(reservedRoom != null){
            while(index < reserved.size() && roomNumber > reserved.get(index).getRoomNumber()){
                    index++;
            }
            reserved.add(index, reservedRoom);
            available.remove(reservedRoom);
            isReserved = true;
        }

        return isReserved;
    }

    /**
     * If the room with the input room number is reserved, makes the room availabe and returns true.  Otherwise, returns false.
     * 
     * @param roomNumber The requested room number to be made available.
     * @return True if the room is successfully made available and false otherwise.
     */
    public boolean makeRoomAvailable(int roomNumber)
    {
        boolean isAvailable = false;
        int index = 0;

        Room availableRoom = getReservedRoomByRoomNumber(roomNumber);
        
        if(availableRoom != null){
            while(index < available.size() && roomNumber > available.get(index).getRoomNumber()){
                    index++;
            }
            available.add(index, availableRoom);
            reserved.remove(availableRoom);
            isAvailable = true;
        }

        return isAvailable;
    }

    /**
     * Returns a list of the avaialbe room descriptions followed by reserved room descriptions.
     * 
     * @return A list of the avaialbe room descriptions followed by reserved room descriptions.
     */
    public String toString()
    {
        StringBuffer description = new StringBuffer();

        for(Room room : available)
        {
            description.append(room.toString() + "\n");
        }

        for(Room room : reserved)
        {
            description.append(room.toString() + "\n");
        }

        return description.toString();
    }
}
