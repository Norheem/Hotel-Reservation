package model;

public class FreeRoom extends Room{
//    private final Double price;

    public FreeRoom(String roomNumber, Double price, RoomType roomType) {
        super(roomNumber, 0.0, roomType);
//        this.price = 0.0;
    }

    @Override
    public String toString() {
        return super.toString();
//        +" Price " + price;
    }

    @Override
    public String getRoomNumber() {
        return super.getRoomNumber();
    }

    @Override
    public Double getRoomPrice() {
        return super.getRoomPrice();
    }

    @Override
    public RoomType getRoomType() {
        return super.getRoomType();
    }

    @Override
    public boolean isFree() {
        return true;
    }
}
