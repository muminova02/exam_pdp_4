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
    }

    public HotelStates getStates() {
        return states;
    }

    public HotelStates setStates(HotelStates states) {
        this.states = states;
        return states;
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

    @Override
    public String toString() {
        return "Hotel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", location=" + location +
                ", floors=" + floors +
                ", roomsCount=" + roomsCount +
                '}';
    }
}
