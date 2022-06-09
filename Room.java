/**
 * This class represents a room in a hotel.
 * 
 * @author Prof. White
 * @version Spring 2021
 */
public class Room
{
    private int roomNumber;
    private View view;
    private int maxCapacity;
    private boolean kitchenette;

    /**
     * Construct a room object given the input values.
     * @param roomNumber The room number.  The first digit indicates the floor on which the room is located.
     * @param view The view from the room.
     * @param maxCapacity  The maximum number of guests that may stay in the room.
     * @param kitchenette True if the room has a kitchenette and false otherwise.
     */
    public Room(int roomNumber, View view, int maxCapacity, boolean kitchenette)
    {
        this.roomNumber = roomNumber;
        this.view = view;
        this.maxCapacity = maxCapacity;
        this.kitchenette = kitchenette;
    }

    /**
     * Returns the room number.
     * 
     * @return The room number.
     */
    public int getRoomNumber() 
    {
        return roomNumber;
    }

    /**
     * Returns the type of view the room has.
     * 
     * @return The type of the view the room has.
     */
    public View getView()
    {
        return view;
    }

    /**
     * Returns the maximum number of guests that may stay in the room.
     * 
     * @return The maximum number of guests that may stay in the room.
     */
    public int getMaxCapacity()
    {
        return maxCapacity;
    }

    /**
     * Returns true if the room has a kitchenette and false otherwise.
     * 
     * @returns True if the room has a kitchenette and false otherwise.
     */
    public boolean hasKitchenette()
    {
        return kitchenette;
    }

    /**
     * Changes the room number.  
     * 
     * @param roomNumber The new room number.
     */
    public void setRoomNumber(int roomNumber) 
    {
        this.roomNumber = roomNumber;
    }

    /**
     * Changes the room view description.
     * 
     * @param view The new room view description.
     */
    public void setView(View view)
    {
        this.view = view;
    }

    /**
     * Changes the maximum number of guests that can stay in a room.  
     * 
     * @param maxCapacity The maximum number of guests that can stay in a room. 
     */
    public void setMaxCapacity(int maxCapacity)
    {
        this.maxCapacity = maxCapacity;
    }

    /**
     * Changes whether the room has a kitchenette.
     * 
     * @param kitchenette True if the room has a kitchenette and false otherwise.
     */
    public void setKitchenette(boolean kitchenette)
    {
        this.kitchenette = kitchenette;
    }

    /**
     * Returns a full description of the room.
     * 
     * @return A full description of the room.
     */
    public String toString()
    {
        String description = "Room Number: " + roomNumber;

        switch (view)
        {
            case OCEAN_FRONT: description += " View:  Ocean Front"; break;
            case OCEAN_SIDE: description += " View:  Ocean Side"; break;
            case STREET_SIDE: description += " View:  Street Side";
        }

        description += " Maximum Capacity:  " + maxCapacity;

        if(kitchenette) 
        {
            description += " Kitchenette";
        }

        return description;
    }
}