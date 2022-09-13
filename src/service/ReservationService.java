package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.util.*;

public class ReservationService {



    private static ReservationService reservationService = null;
    public static Collection<IRoom> rooms = new HashSet<IRoom>();

    public static Collection<Reservation> reservations = new HashSet<Reservation>();

    public static Map<String, IRoom> mapForRooms = new HashMap<String, IRoom>();

    private ReservationService(){}


    static public ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    public void addRoom(final IRoom room) {
        mapForRooms.put(room.getRoomNumber(), room);
    }

    public static IRoom getARoom(String roomId) {
        return mapForRooms.get(roomId);
    }

    public Collection<IRoom> getAllRooms() {
        return mapForRooms.values();
    }

    public static Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservedRooms = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservedRooms);
        return reservedRooms;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> roomsAvailableForBooking = new HashSet<>();
        if (reservations.isEmpty()) {
            return rooms;
        } else {
            for (Reservation reservation : reservations) {
                if (((!checkInDate.after(reservation.getCheckInDate()))) && (!checkOutDate.before(reservation.getCheckOutDate()))
                || (((checkOutDate.after(reservation.getCheckInDate())) && (!checkInDate.before(reservation.getCheckOutDate()))))) {
                    for (IRoom room : rooms) {
                        if (!reservation.getRoom().equals(room)) {
                            roomsAvailableForBooking.add(room);
                        }
                    }
                }
            }
//            return findAvailableRooms(checkInDate, checkOutDate);
            return roomsAvailableForBooking;
        }
    }

//    static Collection<IRoom> findAvailableRooms(Date checkInDate, Date checkOutDate) {
//        List<IRoom> availableRooms = new ArrayList<>();
//
//        for (Reservation reservation : reservations) {
//            if (!reservation.getCheckInDate().after(checkInDate) && !reservation.getCheckOutDate().before(checkOutDate)) {
//                for (IRoom room : rooms) {
//                    if (!reservation.getRoom().equals(room)) {
//                        availableRooms.add(room);
//                    }
//                }
//            }
//        }
//        return availableRooms;
//    }




    public Collection<Reservation> getCustomersReservation(Customer customer) {
        Collection<Reservation> customerReservation = new HashSet<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                customerReservation.add(reservation);
            }
        }
//        CustomerService.getInstance().getCustomer(customer.getEmail());
//        return reservations;
        return customerReservation;
    }

    public void printAllReservation() {
        for (Reservation reserve : getReservation()) {
            System.out.println(reserve);
        }
    }

    public Collection<Reservation> getReservation() {
        return reservations;
    }

}
