package uz.app.hotel.serviceImp;

import uz.app.hotel.database.DB;
import uz.app.hotel.entity.User;
import uz.app.hotel.service.AdminService;
import static uz.app.hotel.ui.Utils.*;

public class AdminServiceImp implements AdminService {
    private User currentuser;
    private DB dataBase = DB.getInstance();



    @Override
    public void service(User user) {

        currentuser = user;
        while (true){
            switch (getInt("""
                    1 addHotel
                    2 showHotel
                    3 show Hotels
                    4 edit Hotels
                    5 
                    
                    """)){
                case 0->{
                    System.out.println("By");
                    return;
                }
                case 1->{

                }
            }
        }

    }

    @Override
    public void addHotel() {


    }

    @Override
    public void showHotel() {

    }

    @Override
    public void showHotels() {

    }

    @Override
    public void editHotel() {

    }

    @Override
    public void deleteHotel() {

    }

    @Override
    public void showUsers() {

    }

    @Override
    public void showReservationHistory() {

    }

    @Override
    public void calcelReservation() {

    }

    @Override
    public void reserveForUser() {

    }



    private static AdminServiceImp adminService;

    public static AdminServiceImp getInstance() {
        if (adminService == null)
            adminService = new AdminServiceImp();
        return adminService;
    }
}
