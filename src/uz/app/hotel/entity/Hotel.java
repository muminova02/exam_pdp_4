package uz.app.hotel.entity;

import uz.app.hotel.enums.HotelStates;

import java.util.UUID;

public class Hotel {
    private final String  id=UUID.randomUUID().toString();
    private String name;
    private Location location;
    private Integer floors;
    private Integer roomsCount;
    private HotelStates states;

    public Hotel(String name, Location location, Integer floors, Integer roomsCount) {
        this.name = name;
        this.location = location;
        this.floors = floors;
        this.roomsCount = roomsCount;
        this.states = HotelStates.ACTIVE;
    }
    public HotelStates getStates() {
        return states;
    }

    public void setStates(HotelStates states) {
        this.states = states;
    }

    public String getId() {
        return id;
    }

    public Integer getFloors() {
        return floors;
    }

    public void setFloors(Integer floors) {
        this.floors = floors;
    }

    public Integer getRoomsCount() {
        return roomsCount;
    }

    public void setRoomsCount(Integer roomsCount) {
        this.roomsCount = roomsCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    public void setHotel(Hotel hotel){
        this.setName(hotel.getName());
        this.setLocation(hotel.getLocation());
        this.setFloors(hotel.getFloors());
        this.setRoomsCount(hotel.getRoomsCount());
        this.setStates(hotel.getStates());
    }


    @Override
    public String toString() {
        return "Hotel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", location=" + location +
                ", floors=" + floors +
                ", roomsCount=" + roomsCount +
                ", states=" + states +
                '}';
    }
}
