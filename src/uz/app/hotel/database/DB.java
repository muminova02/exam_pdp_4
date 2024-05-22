package uz.app.hotel.database;

import uz.app.hotel.entity.*;
import uz.app.hotel.service.AdminService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DB {
    public Set<User> users=new HashSet<>();
    public ArrayList<Reservation> reservations = new ArrayList<>();

    public List<Hotel> hotelList = new ArrayList<>();


    private static DB db;
    public static DB getInstance(){
        if (db == null){
            db=new DB();
            db.users.add(new User("Admin","admin","admin",10000d, Role.ADMIN));
            db.users.add(new User("U1","u1","u1",10000d, Role.USER));
            db.users.add(new User("U2","u2","u2",10000d, Role.USER));
            db.users.add(new User("U3","u3","u3",10000d, Role.USER));
            db.users.add(new User("U4","u4","u4",10000d, Role.USER));
            db.hotelList.add(new Hotel("Diamond", Location.TASHKENT,12,12));
            db.hotelList.add(new Hotel("DoubleM", Location.TASHKENT,12,12));
            db.hotelList.add(new Hotel("Barchinoy", Location.NAMANGAN,12,12));

        }
        return db;
    }


}
