package model;

import java.util.Date;
import java.util.Objects;

public class Reservation {
    private final Customer customer;

    private final IRoom room;

    private final Date checkInDate;

    private final Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        super();
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
//
//    public void setRoom(IRoom room) {
//        this.room = room;
//    }
//
//    public void setCheckInDate(Date checkInDate) {
//        this.checkInDate = checkInDate;
//    }
//
//    public void setCheckOutDate(Date checkOutDate) {
//        this.checkOutDate = checkOutDate;
//    }

    @Override
    public String toString() {
        return "This is customer " + customer +
                " \nthis is room " + room +
                " \nthis is check in date " + checkInDate +
                " \nthis is check out date " + checkOutDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(customer, that.customer)
                && Objects.equals(room, that.room)
                && Objects.equals(checkInDate, that.checkInDate)
                && Objects.equals(checkOutDate, that.checkOutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, room, checkInDate, checkOutDate);
    }
}
