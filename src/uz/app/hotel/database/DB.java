package uz.app.hotel.database;

import uz.app.hotel.entity.User;
import uz.app.hotel.service.AdminService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DB {
    private static DB db ;
    public static DB getInstance(){
        if (db == null)
            db=new DB();
        return db;
    }
    public Set<User> users=new HashSet<>();

}
