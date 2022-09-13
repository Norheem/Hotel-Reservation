package app;

import api.AdminResource;
import model.*;
import service.CustomerService;
import service.ReservationService;

import java.util.*;

public class AdminMenu {

    public Collection<IRoom> rooms = new HashSet<>();

    private static AdminResource adminResource = AdminResource.getInstance();

    public static
    CustomerService customerService = CustomerService.getInstance();

    public static ReservationService reservationService = ReservationService.getInstance();

    public Collection<Reservation> reservations = new HashSet<>();

    public Collection<Customer> customers = new HashSet<>();

    static final Scanner input = new Scanner(System.in);



    public static void adminPanel() {
        int selecting = adminOptionsList();
        System.out.println(selecting);
        adminSelectOptions(selecting);
    }

    public static void adminSelectOptions(int select) {
        while (true) {

            try {
                switch (select) {
                    case 1:
                        seeAllCustomers();
                        break;
                    case 2:
                        seeAllRooms();
                        break;
                    case 3:
                        seeAllReservation();
                        break;
                    case 4:
                        addARoom();
                        break;
                    case 5:
                        MainMenu.mainPanel();
                        break;
                    default:
                        System.out.println("Invalid Input, please check your input and try again");
                        System.exit(0);
                }
            } catch (InputMismatchException ex) {
                System.out.println("ERROR! Please enter a valid option from 1-5");
            }
        }

    }

    public static int adminOptionsList() {
        System.out.println();
        System.out.println("Welcome to Norheem's Hotel ADMIN MENU System");
        System.out.println("-------------------------------------------");
        System.out.println("Press ");
        System.out.println("1 To see all customers");
        System.out.println("2 To see all rooms");
        System.out.println("3 To see all reservation");
        System.out.println("4 To add a room");
        System.out.println("5 To Back to Main Menu!");
        System.out.println("------------------------------------------");
        System.out.println("Please enter your command: \n");
        int selecting = Integer.parseInt(input.next());

        return selecting;
    }

    public static void seeAllCustomers() {

        Collection<Customer> customers = adminResource.getAllCustomers();
//        System.out.println();
        if (!customers.isEmpty()) {
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        } else {
            System.out.println("Presently there are no customers available in our hotel");
        }
        adminPanel();
    }

    public static void seeAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();
        rooms.forEach(System.out::println);
        System.out.println();
        adminPanel();
    }

    public static void seeAllReservation() {
        adminResource.displayAllReservations();
    }


    public static void addARoom(){
        System.out.println("Enter the room number you want to add: ");
        input.nextLine();
        String roomNumber = String.valueOf(0);
        try {
            roomNumber = String.valueOf((input.nextInt()));
        } catch (InputMismatchException e) {
            System.out.println("Only integer values are allowed!");
            addARoom();
        }
        System.out.println("Enter the price for the room your added: ");
        double roomPrice = 0;
        try {
            roomPrice = input.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Only value with decimal point is allowed!");
            while (roomPrice == 0) {
                System.out.println("Please input a price");
                addARoom();
            }
        }
        System.out.println("""
                Enter the type of room you want to add:\s
                1 for SINGLE BED
                2 for DOUBLE BED""");
        RoomType roomType = null;
        try {
            roomType = input.nextInt() == 1 ? RoomType.SINGLE : RoomType.DOUBLE;
        } catch (InputMismatchException ex) {
            System.out.println("""
                    Invalid input.\s
                     Enter 1 for SINGLE room\s
                     Enter 2 for DOUBLE room""");
        } while (roomType == null) {
            System.out.println("Room Type is empty, Please try again");
            addARoom();
        }

        IRoom room = new Room(roomNumber, roomPrice, roomType);
        List<IRoom> rooms = new ArrayList<>();
        rooms.add(room);
        System.out.println("Room successfully added!");
        System.out.println(rooms);

        System.out.println("Would you like to add another room? \n" +
                "Enter Y or N");
        String continueToAddRoom = input.next().toUpperCase();
        switch (continueToAddRoom) {
            case "Y" -> {
                adminResource.addRoom(rooms);
                addARoom();
                System.out.println(rooms);
            }
            case "N" -> {
                adminResource.addRoom(rooms);
                adminPanel();
                System.out.println();
            }
            default -> {
                System.out.println("ERROR! \n" +
                        "Try another input");
                adminPanel();
            }
        }

        adminPanel();
    }
}
