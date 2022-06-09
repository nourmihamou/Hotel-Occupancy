import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * This program reads data from a CSV (Comma Separated Value) file and creates a new output
 * file to be written.  The data in the file has the following format on each line.
 * 
 * int, String, int, String, String
 *
 * @author Nour Mihamou and Lulama Nyembe
 * @version 05/05/2021
 */
public class HotelOccupancy
{
    private static Hotel zHotel = new Hotel();

    /**
     * This method asks the user if they would like to review available rooms. If so, user must choose
     * an option from the menu. Based on the user's selection, available rooms are displayed inn the 
     * terminal window as well as an external file.
     * 
     * @param args - args[0] is the input file of hotel data. 
     * @param args - args[1] is the name of the output file to be written.
     */
    public static void main(String[] args)
    {
        //Reading from keyboard
        Scanner keyboard = new Scanner(System.in);

        ArrayList<Room> reserved = new ArrayList<Room>();

        System.out.print("Would you like to see available rooms? (y/n): ");
        if(keyboard.next().equalsIgnoreCase("n")){
            System.out.println("The following reserved rooms have been saved to " + args[1]);
            readFile(args[0]);
            reserved = zHotel.getAllReservedRooms();

            for(int index = 0; index < reserved.size(); index++){
                System.out.println(reserved.get(index));
            }
            writeFile(args[1]);
        } else{

            System.out.println("\nWhich type of room would you prefer? (Enter the number): ");
            System.out.println("(1) Ocean rooms with a kitchenette");
            System.out.println("(2) Ocean rooms without a kitchenette");
            System.out.println("(3) Street side rooms with a kitchenette");
            System.out.println("(4) Street side rooms without a kitchenette");

            System.out.print("\nMenu selection: ");
            int selection = keyboard.nextInt();

            System.out.println();
            System.out.println("The following available rooms have been saved to " + args[1]);
            readFile(args[0]);
            writeFile(args[1]);

            //Loop to go through entire file based on user's selection
            for(int count = 0; count < zHotel.getAllAvailableRooms().size(); count++){
                if(selection == 1){
                    //int index = 0;
                    if(zHotel.getAllAvailableRooms().get(count).hasKitchenette() 
                    && zHotel.getAllAvailableRooms().get(count).getView() != View.STREET_SIDE){
                        System.out.println(zHotel.getAllAvailableRooms().get(count).toString());
                    }
                } else if(selection == 2){
                    if(!zHotel.getAllAvailableRooms().get(count).hasKitchenette() 
                    && zHotel.getAllAvailableRooms().get(count).getView() != View.STREET_SIDE){
                        System.out.println(zHotel.getAllAvailableRooms().get(count).toString());
                    }
                } else if(selection == 3){
                    int index = 0;
                    if(zHotel.getAllAvailableRooms().get(count).hasKitchenette() 
                    && zHotel.getAllAvailableRooms().get(count).getView() == View.STREET_SIDE){
                        System.out.println(zHotel.getAllAvailableRooms().get(count).toString());
                    }
                } else if(selection == 4){
                    int index = 0;
                    if(!zHotel.getAllAvailableRooms().get(count).hasKitchenette() 
                    && zHotel.getAllAvailableRooms().get(count).getView() == View.STREET_SIDE){
                        System.out.println(zHotel.getAllAvailableRooms().get(count).toString());
                    }
                }
            }

        }
    }

    /**
     * This method opens and reads from a Comma Separated File. The data in the file has the 
     * following format on each line:
     * 
     * int, String, int, String, String
     * 
     * @param inputFile The file input by the user.
     */
    public static void readFile(String inputFile)
    {
        Scanner fileScanner = null;

        // Opening and reading the file. Throwing an error if file not found.
        try{
            fileScanner = new Scanner(new File(inputFile));
        } catch (FileNotFoundException fne) {
            System.out.println("\nThe file " + inputFile + " cannot be found.");

            //By convention, a non-zero status code indicates abnormal termination.
            System.exit(1);
        }

        //Skipping the first line in the file.
        fileScanner.nextLine();

        // Read each line of the file. Set up Scanner to parse the line of input read
        // from file.
        Scanner scRoomLine;
        String roomLine;

        // These variables represent the entries in the file.
        int roomNumber;
        String viewStr;
        View view = null;
        int maxCapacity;
        boolean hasKitchenette;
        boolean isAvailable;

        while(fileScanner.hasNext()){
            //Read one line from the file
            roomLine = fileScanner.next();

            //Initialize the Scanner to read the line with a comma delimiter
            scRoomLine = new Scanner(roomLine);
            scRoomLine.useDelimiter(",");

            //Read each value from the file, converting it to its intended
            //datatype.
            roomNumber = Integer.parseInt(scRoomLine.next());

            viewStr = scRoomLine.next();

            if(viewStr.equals("OS")){
                view = View.OCEAN_SIDE;
            } else if(viewStr.equals("OF")){
                view = View.OCEAN_FRONT;
            } else if(viewStr.equals("SS")){
                view = View.STREET_SIDE;
            }

            maxCapacity = Integer.parseInt(scRoomLine.next());

            if(scRoomLine.next().equalsIgnoreCase("N")){
                hasKitchenette = false;
            } else {
                hasKitchenette = true;
            }

            if(scRoomLine.next().equalsIgnoreCase("N")){
                isAvailable = false;
            } else {
                isAvailable = true;
            }

            //Close the scanner for the line of data read
            scRoomLine.close();

            //Add these elements to a new room in the zHotel object.
            if(isAvailable == true){
                zHotel.addNewRoom(roomNumber, view, maxCapacity, hasKitchenette);
            } else if(isAvailable == false){
                zHotel.addNewRoom(roomNumber, view, maxCapacity, hasKitchenette);
                zHotel.reserveRoom(roomNumber);
            }
        }

        fileScanner.close();
    }

    /**
     * This method takes as input the file to be written and displayed on another window.
     * 
     * @param name The output text file.
     */
    public static void writeFile(String name)
    {
        String fileName = name + ".txt";
        PrintWriter pWriter = null;
        File outFile;

        //Create the output file.  If there is an error in this process,
        //e.g., we do not have permission to write to the directory, catch
        //the error and end the program gracefully.
        try{
            outFile = new File(fileName);
            pWriter = new PrintWriter(new FileWriter(outFile));
        } catch (java.io.IOException jioe) {
            System.out.println("\nCannot write to the file " + fileName + ".");

            //By convention, a non-zero status code indicates abnormal termination.
            System.exit(1);
        }

        //Write the date to the file, one line at a time.
        //In this example, we are separating the data by comma,
        //creating a CSV (Comma Separated Value) file.
        pWriter.print(zHotel.toString());

        //Note:  See the Java 11 API PrintWriter class for more methods.
        //E.g., You can use the methods such as print, printf, and println.

        //You must close the PrintWriter when you are done writing to the file.
        pWriter.close();

    }
}