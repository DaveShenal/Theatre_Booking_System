package SD.CW.java;
import java.io.*;
import java.util.*;

public class Theatre { //task 1
    static int[] row1 = new int[12];
    static int[] row2 = new int[16];
    static int[] row3 = new int[20];
    static ArrayList<Ticket> tickets = new ArrayList<>(); //ticket obj arraylist.
    static Scanner input = new Scanner(System.in); //creating static object to get inputs inside a Theatre class.

    public static void main(String[] args) {
        System.out.println("\nWelcome to the New Theatre");
        String menu = """
                -------------------------------------------------
                Please select an option:
                1) Buy a ticket
                2) Print seating area
                3) Cancel ticket
                4) List available seats
                5) Save to file
                6) Load from file
                7) Print ticket information and total price
                8) Sort tickets by price
                   0) Quit
                -------------------------------------------------
                Enter option :\s""";
        boolean menu_loop = true;//do while loop condition.
        do {//to run the program until user enter 0.
            int option = positive_input(menu, "option number.");//to get positive input for menu.

            switch (option) {
                case 0:
                    menu_loop = false; //to terminate the program when user entered 0.
                    System.out.println("Bye.");
                    break;
                case 1:
                    buy_ticket();
                    break;
                case 2:
                    print_seating_area();
                    break;
                case 3:
                    cancel_ticket();
                    break;
                case 4:
                    show_available();
                    break;
                case 5:
                    save();
                    break;
                case 6:
                    load();
                    break;
                case 7:
                    show_tickets_info();
                    break;
                case 8:
                    sort_tickets();
                    break;
                default:
                    System.out.println("Please enter menu option number (0 to 8).");//All cases are unsatisfiable.
            }
        } while (menu_loop);
    }


    //method 1 : to get positive integer inputs.
    public static int positive_input(String input_message, String input_type) { //input type : Option Number(for menu)
        int user_input; //return variable.//                                                   Row NUmber
        while (true) { //to loop until get positive integer.                                    Seat Number
            try { //
                System.out.print(input_message); //to display the message to get input.
                user_input = Integer.parseInt(input.nextLine());

                if (user_input == 0) { //not negative either.
                    if (input_type.equals("option number.")) {
                        break; //if input type is option(to menu) it will allow to enter 0.
                    } else if (input_type.equals("price.")) { //to avoid taking zero as a price.
                        System.out.println("Price cannot be zero.");
                        continue;
                    } else { //if user entered 0 as a row or seat number.
                        System.out.println("Error: Zero is not a valid " + input_type);
                        continue;
                    }
                } else if (user_input < 0) { //if its negative.
                    System.out.println("Error: Negative " + input_type); //to display an error message.
                    continue;
                }
            } catch (Exception e) { //String which can not be converted to an integer.
                System.out.println("Please enter valid "+input_type);
                continue;
            }
            break;
        }
        return user_input; //positive integer.
    }


    //method 2 : to validate row number and seat number.
    public static int[] seat_validation() { // to use when ask user to enter row and seat number.
        int[] row_and_seat = new int[2]; //to return valid row and seat number.

        while (true) { // to loop until user enter valid row number.
            row_and_seat[0] = positive_input("Enter row number : ", "row number."); //to get positive numbers
            if (row_and_seat[0] != 1 && row_and_seat[0] != 2 && row_and_seat[0] != 3) {
                System.out.println("Error: This row number does not exist. Please select 1-3.");
                continue;
            }
            break;
        }
        while (true) { // to loop until user enter valid seat number.
            row_and_seat[1] = positive_input("Enter seat number : ", "seat number.");
            if (row_and_seat[1] > (row_and_seat[0] * 4 + 8)) {// row*4 + 8 = maximum seat number for each row.
                System.out.println("Error: This seat number does not exist in this row.");
                continue;
            }
            break;
        }
        return row_and_seat; // return an array with valid row number and seat number.
    }


    //method 3 : to update the ticket list when buying and canceling.
    public static void buy_or_cancel(boolean buy, int row, int seat, String message) {
        int[][] array = {row1,row2,row3};//to get row array by row parameter.

        int check1 = 0, check2 = 1;  //to switch between buy and cancel.
        if (!buy) {                              //check1 & check2:
            check2 = 0;                          //  when buying -> check if the element is 0 (check1)
            check1 = 1;//if it true : change element to 1 (check2)
        }

        if (array[row-1][seat - 1] == check1) { //to check if it booked or not.
            get_infor_update_list(buy, row, seat); //to update tickets list.
            System.out.println("Completed.");
            array[row-1][seat - 1] = check2; //to update the array.
        } else System.out.println(message); //to display message when the task is unsuccessful.
    }


    //task 3 : to buy tickets.
    public static void buy_ticket() {
        int[] valid_place = seat_validation();
        buy_or_cancel(true, valid_place[0], valid_place[1], "This seat is not available.");
    }
    //task 5 : to cancel tickets.
    public static void cancel_ticket() {
        int[] valid_place = seat_validation();
        buy_or_cancel(false, valid_place[0], valid_place[1], "Error: This seat has not been sold. therefore, cannot cancel it.");
    }


    //method 4 : to print an array elements.
    public static void print_array(int[] row, String space) {
        System.out.print(space); //to show the seats in the correct location. (align)
        for (int i = 0; i < row.length; i++) {               // i = row.length/2 -> middle seat(right side).
            if (i == (row.length) / 2) System.out.print(" ");// print space before the middle seat.
            if (row[i] == 1) System.out.print("X");
            else System.out.print("O");
        }
        System.out.println();//next line.
    }


    //task 4 : to print the seats in the correct location.
    public static void print_seating_area() {
        String stage = """
                            
                \t ***********
                \t *  STAGE  *
                \t ***********""";
        System.out.println(stage);
        print_array(row1, "    "); //using print_array method to display the seats.
        print_array(row2, "  ");
        print_array(row3, "");
    }


    //method 5 : to print available seat numbers in a row.
    public static void available_seats(int[] row, int row_number) {       //parameters:
        System.out.print("Seats available in row " + row_number + " : "); //    row -> to identify the array.
        for (int j = 0; j < row.length; j++) {                            //    row_number -> for string.
            if (row[j] == 0) System.out.print((j + 1) + ", ");
        }
        System.out.println("\b\b."); //delete last 2 characters ("," and " ") and print "." .
    }


    //task 6 : to displays the eats that are still available.
    public static void show_available() {
        available_seats(row1, 1); //using available_seats method to display available seats.
        available_seats(row2, 2);
        available_seats(row3, 3);
    }


    //method 6 : to save array to file
    public static void save_array(int[] array, boolean append) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("row_data.txt", append));
            for (int i : array) {
                writer.write(i + " ");
            }
            writer.write("\n"); //move to the next line.
            writer.close();
        } catch (Exception e) { //if file can not create for some reason.
            System.out.println("Error : "+e);
        }
    }


    //task 7 : to saves the 3 arrays with the row’s information in a file.
    public static void save() {
        save_array(row1, false); //saving 3 arrays.
        save_array(row2, true);
        save_array(row3, true);
        save_obj(); //saving obj arraylist.
        System.out.println("Successfully saved to a file.");
    }


    //task 8 : to loads the file saved in Task 7 and restores the arrays with the row’s information.
    public static void load() {
        try {
            File file1 = new File("row_data.txt");//new file obj.
            Scanner reader1 = new Scanner(file1);//new scanner obj.
            for (int i = 0; i < 48; i++) {
                if (i < 12) {
                    row1[i] = reader1.nextInt();
                } else if (i < 28) {
                    row2[i - 12] = reader1.nextInt();
                } else {
                    row3[i - 28] = reader1.nextInt();
                }
            }
            reader1.close();

            File file2 = new File("tickets.txt"); //new file obj.
            Scanner reader2 = new Scanner(file2); //new scanner obj.
            tickets.clear();
            while (reader2.hasNextLine()) { //if there is no more lines to read, loop will end.
                String line = reader2.nextLine(); //assign whole line in to a String variable.
                String[] property = line.split(","); //create array using split items of line variable.
                int row = Integer.parseInt(property[0]); //to convert string in to an integer.
                int seat = Integer.parseInt(property[1]);
                int price = Integer.parseInt(property[2]);
                tickets.add(new Ticket(row, seat, price, new Person(property[3], property[4], property[5])));
            }
            reader2.close();
            System.out.println("Successfully loaded from a file.");
        } catch (Exception FileNotFoundException) {
            System.out.println("Loading failed: Cannot find the file.");
        }
    }


    //method 7 : to get valid email
    public static String email_validation() {
        String entered_email; //return variable
        boolean invalid_email = true; //do while loop condition.
        do {
            System.out.print("Enter your email : ");
            entered_email = input.nextLine();
            if (entered_email.contains(".") && entered_email.contains("@")) {
                invalid_email = false; //to terminate the loop.
            } else {
                System.out.println("Error: This email address is not valid.");
            }
        } while (invalid_email);
        return entered_email;
    }


    //method 8 : to avoid getting empty string as an input.
    public static String name_validation(String message, String input_name) {
        boolean invalid_name = true;
        String name;
        do {
            System.out.print(message);
            name = input.nextLine();

            if (name.isBlank()) {
                System.out.println("Error: " + input_name + " has not been entered.");
                continue;
            }
            invalid_name = false;
        } while (invalid_name);
        return name;
    }


    //method 9 : to get information and update the array list(tickets).
    public static void get_infor_update_list(boolean buy, int row, int seat) { //valid row and seat, price for seat.
        if (buy) {
            String name = name_validation("Enter name : ", "Name");
            String surname = name_validation(("Enter surname : "), "Surname");
            String email = email_validation().toLowerCase(); //will return valid email.
            int price = positive_input("Enter ticket price : ", "price.");
            tickets.add(new Ticket(row, seat, price, new Person(name, surname, email))); //to create ticket obj.
        } else {
            for (int i = 0; i < tickets.size(); i++) { //error ??
                if (tickets.get(i).row == row && tickets.get(i).seat == seat) {
                    tickets.remove(i);
                    break;
                }
            }
        }
    }


    //task 13
    public static void show_tickets_info() {
        int total_ticket_price = 0;
        for (Ticket ticket_obj : tickets) { //ticket object in tickets array list.
            ticket_obj.print(); //print method in Ticket class.
            total_ticket_price += ticket_obj.price; //updating total.
        }
        System.out.println("\n-------------------------------------------------");
        System.out.println("Number of tickets that have been sold is " + tickets.size() + ".");
        System.out.println("Total ticket price is £" + total_ticket_price + ".");
    }


    //method 10 : to save the objects to a file. (using with save_array method in task 7)
    public static void save_obj() {
        try {
            BufferedWriter obj_writer = new BufferedWriter(new FileWriter("tickets.txt"));
            for (Ticket obj : tickets) {
                obj_writer.write(obj.row + "," + obj.seat + "," + obj.price + "," + obj.person.name + "," + obj.person.surname + "," + obj.person.email + "\n");
            }
            obj_writer.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }


    //method 11 : to merge array.
    public static ArrayList<Ticket> merge_array(ArrayList<Ticket> obj_list, int startIndex, int endIndex) {
        ArrayList<Ticket> sorted = new ArrayList<>(); //to return.
        if (startIndex < endIndex) { // -> array has more than one element.
            int midIndex = (startIndex + endIndex) / 2; //to split up the array.
            ArrayList<Ticket> leftArray = merge_array(obj_list, startIndex, midIndex); //first part of the array.
            ArrayList<Ticket> rightArray = merge_array(obj_list, midIndex + 1, endIndex); //second part of the array.
            sorted = sortArrays(leftArray, rightArray);
        } else {
            sorted.add(obj_list.get(startIndex)); //to return arraylist with one element.
        }
        return sorted;
    }


    //method 12 : to make a sorted array using two sorted arrays.
    public static ArrayList<Ticket> sortArrays(ArrayList<Ticket> array1, ArrayList<Ticket> array2) {
        ArrayList<Ticket> sortedArray = new ArrayList<>(array1.size() + array2.size()); //sorted elements will add to this array.
        int index1 = 0; // to find the element to compare with array2 element.
        int index2 = 0; // to find the element to compare with array1 element.
        int sortedArrayIndex = 0; // to add the smallest element to next none sorted index.

        // if both of arrays has remaining elements to compare.
        while (index1 < array1.size() && index2 < array2.size()) {      //this loop will fill up the array comparing
            if (array1.get(index1).price <= array2.get(index2).price) {  //smallest elements in each array.
                sortedArray.add(sortedArrayIndex, array1.get(index1));  //if no elements to compare in array1 or array2
                index1++; //moving to the next element.                //loop will end!
            } else {                                                    //will start one of the loop down below.
                sortedArray.add(sortedArrayIndex, array2.get(index2));  //to fill the array using remaining elements.
                index2++; //moving to the next element.
            }
            sortedArrayIndex++; //moving to the next index to fill.
        }
        //if only the first array has remaining elements.
        while (index1 < array1.size()) {
            sortedArray.add(sortedArrayIndex, array1.get(index1)); //adding the element to array.
            index1++; //moving to the next element.
            sortedArrayIndex++; //moving to the next index to fill.
        }
        //if only the second array has remaining elements.
        while (index2 < array2.size()) {
            sortedArray.add(sortedArrayIndex, array2.get(index2));
            index2++;
            sortedArrayIndex++;
        }
        return sortedArray;
    }


    //task 14
    public static void sort_tickets() {
        int ticket_size = tickets.size();
        if (!tickets.isEmpty()) { //if it is empty it will throw an error.
            for (Ticket obj : merge_array(tickets, 0, ticket_size - 1)) {
                obj.print();//print method in Ticket class.
            }
        } else System.out.println("There is not any ticket that have been sold.");
    }
}