package uz.app.hotel.serviceImp;

import uz.app.hotel.database.DB;
import uz.app.hotel.entity.User;
import uz.app.hotel.service.UserService;
import static uz.app.hotel.ui.Utils.*;

public class UserServiceImp implements UserService {
    private User currentuser;
    private DB dataBase = DB.getInstance();

    @Override
    public void service(User user) {
        currentuser = user;
        while (true){
            switch (getInt("""
                    methods
                    
                    
                    """)){
                case 0->{
                    System.out.println("By");
                    return;
                }
            }
        }

    }

    @Override
    public void showHotels() {


    }

    @Override
    public void showReservations() {

    }

    @Override
    public void reserve() {

    }

    @Override
    public void cancelReservation() {

    }

    @Override
    public void rescheduleReservation() {

    }

    @Override
    public void showHistory() {

    }
    private static UserServiceImp userService;

    public static UserServiceImp getInstance() {
        if (userService == null)
            userService = new UserServiceImp();
        return userService;
    }
}
