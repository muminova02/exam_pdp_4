package uz.app.hotel.database;

import uz.app.hotel.entity.Hotel;
import uz.app.hotel.entity.Reservation;
import uz.app.hotel.entity.User;
import uz.app.hotel.service.AdminService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DB {
    public Set<User> users=new HashSet<>();
    public ArrayList<Reservation> reservations = new ArrayList<>();

    public List<Hotel> hotelList = new ArrayList<>();


    private static DB db ;
    public static DB getInstance(){
        if (db == null)
            db=new DB();
        return db;
    }


}
