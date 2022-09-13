package app;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class MainMenu {


    public static HotelResource hotelResource;

    static final Scanner input = new Scanner(System.in);


    public static final String dateFormat = "MM/dd/yyyy";

    public static final SimpleDateFormat date = new SimpleDateFormat(dateFormat);

    public static void mainPanel() {
        int selecting = menuOptionsList();
        System.out.println(selecting);
        selectOptions(selecting);
    }

    public static void selectOptions(int select) {

        while (true) {

            try {
                switch (select) {
                    case 1:
                        findAndReserveARoom();
                        break;
                    case 2:
                        seeMyReservation();
                        break;
                    case 3:
                        createAnAccount();
                        break;
                    case 4:
                        AdminMenu.adminPanel();
                        break;
                    case 5:
                        System.exit(0);
                        System.out.println("Thank you for checking in into our Hotel!");
                        break;
                    default:
                        System.out.println("Invalid Input, please check your input and try again");
                        System.exit(0);
                }
            } catch (NumberFormatException ex) {
                System.out.println("ERROR! Please enter a valid option from 1-5");
                menuOptionsList();
            }
        }

    }


    public static int menuOptionsList() {
        System.out.println();
        System.out.println("Welcome to Norheem's Hotel Reservation System\n " +
                " Please how can we help you?");
        System.out.println("-------------------------------------------");
        System.out.println("Press ");
        System.out.println("1 To find and reserve a room");
        System.out.println("2 To see your reservation");
        System.out.println("3 To create a new account");
        System.out.println("4 for Admin Panel");
        System.out.println("5 To Exit!");
        System.out.println("------------------------------------------");
        System.out.println("Please enter any number to carry out your work: \n");
        int selecting = Integer.parseInt(input.nextLine());

        return selecting;
    }


    private static void findAndReserveARoom()
    {
        String reserve;
        String account;
        String email;
        IRoom roomNumObject = null;
        Calendar calendar = Calendar.getInstance();
        Date checkInDate;
        Date checkOutDate;
        Date currentDate;
//        input.nextLine();
        try {

            // currentDate =  date.parse(date.format(calendar.getTime()));
                    //date.parse(date.format(calendar.getTime()));
            currentDate = date.parse(date.format(calendar.getTime()));
            System.out.println("Enter your check-In Date MM/dd/yyy: ");
            checkInDate = date.parse(input.nextLine());
            System.out.println(checkInDate);
            System.out.println("Enter your check-Out Date MM/dd/yyy: ");
            checkOutDate = date.parse(input.nextLine());
            System.out.println(checkOutDate);
            if (!checkInDate.before(currentDate) && !checkOutDate.before(checkInDate)) {
                Collection<IRoom> rooms = HotelResource.getInstance().findARoom(checkInDate, checkOutDate);
                rooms.forEach(System.out::println);
                if (rooms != null) {
                    do {
                        System.out.println("Would you like to book a room? \n" +
                                "Enter Y or N");
                        reserve = input.next().toUpperCase().trim();
                        if (reserve.equalsIgnoreCase("y")) {
                            do {
                                System.out.println("Do you have an account with us? \n" +
                                        "Enter Y or N");
                                account = input.next().toUpperCase().trim();
                                if (account.equalsIgnoreCase("y")) {
                                    input.nextLine();
                                    System.out.println("Enter your email in this format: name@domain.com: ");
                                     email = input.nextLine();
                                    Customer customer = HotelResource.customerService.getCustomer(email);
                                     if (HotelResource.customerService.getCustomer(email) != null) {
                                         System.out.println("What room number would you like to reserve? ");
                                         rooms.forEach(System.out::println);
                                         String roomNum = input.nextLine();
                                         if (HotelResource.getRoom(roomNum) == null) {
                                             System.out.println("Room not available. \n" +
                                                    " Try to reserve another room");
                                             findAndReserveARoom();
                                         } else {
                                             roomNumObject = HotelResource.getRoom(roomNum);
                                             //System.out.println("You have reserved the following room: " + roomNum);
//                                             if (!rooms.contains(roomNumObject)) {
                                             try {
                                                 if (!rooms.contains(roomNumObject)) {
                                                     HotelResource.bookARoom(customer.getEmail(), roomNumObject, checkInDate, checkOutDate);
                                                     System.out.println("Room " + roomNum + " is " +
                                                             " Reserved for customer: " + customer + "\n");
                                                 } else {
                                                     System.out.println("Your alredy book the room before");
                                                 }
                                             } catch (IllegalArgumentException ex) {
                                                 System.out.println(ex.getLocalizedMessage());
                                                 findAndReserveARoom();
                                             }
////                                             } else {
//                                                 System.out.println("Room " + roomNum + " is already reserve " +
//                                                         " please select another room");
//                                                 findAndReserveARoom();
////                                             }

                                         }

                                     } else {
                                         System.out.println("No account found\n " +
                                                 "You need to create a new account");
                                         createAnAccount();
                                         findAndReserveARoom();
                                     }
                                     mainPanel();
                                }else if (account.equalsIgnoreCase("n")) {
                                    System.out.println("You have to create an account first before you can book a room");
                                    createAnAccount();
                                } else {
                                    System.out.println("Invalid Input Entered!");
                                    findAndReserveARoom();
                                }
                            } while (!account.equalsIgnoreCase("y") && !account.equalsIgnoreCase("n"));
                        } else if (reserve.equalsIgnoreCase("n")) {
                            menuOptionsList();
                        } else {
                            System.out.println("Invalid Input Entered!");
                        }
                    } while (!reserve.equalsIgnoreCase("y") && !reserve.equalsIgnoreCase("n"));

                    } else {
                    System.out.println("No room is available presently");

                }
            } else {
                System.out.println("Check in date needs to be from today onwards and check " +
                        "out date must be after check in date");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private static void seeMyReservation() {
        System.out.println("Enter your email: eg. name@domain.com");
        String email = input.nextLine();
        printReservations(HotelResource.getInstance().getCustomersReservations(email));
    }

    private static void printReservations(final Collection<Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
        mainPanel();
    }


    /**
     * The method below validate customer's email
     * @return validated email
     */

    private static void createAnAccount() {
        System.out.println("Do you have an account? \n If no, create your account now");
        System.out.println("CREATING AN ACCOUNT");
        System.out.println("----------------------------------");
        System.out.println("Enter your email address format: name@domain.com...");
        String email = input.next();
        System.out.println("Enter your first name: ");
        final String firstName = input.next();
        System.out.println("Enter your last name:");
        final String lastName = input.next();

        try {
            HotelResource.getInstance().createACustomer(email, firstName, lastName);

            System.out.println("You have successfully create your account");
//            System.out.println("Press enter key again to reserve a room");
            findAndReserveARoom();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
            createAnAccount();
        }
    }

}
