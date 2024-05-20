package uz.app.hotel.serviceImp;

import uz.app.hotel.database.DB;
import uz.app.hotel.entity.Hotel;
import uz.app.hotel.entity.Location;
import uz.app.hotel.entity.User;
import uz.app.hotel.service.AdminService;
import uz.app.hotel.service.HotelService;
import uz.app.hotel.ui.Utils;

import static uz.app.hotel.ui.Utils.*;

public class AdminServiceImp implements AdminService {
    private User currentuser;
    private DB dataBase = DB.getInstance();
    HotelServiceImp hotelService=new HotelServiceImp();



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
        String name= Utils.scanStr("Enter Hotel name : ");
        Location locaton= Location.valueOf(Utils.scanStr("Enter location "));
        Integer floor= Utils.getInt("Enter floor");
        Integer roomCount=Utils.getInt("Enetr room ");
        Hotel hotel =new Hotel(name,locaton,floor,roomCount);
        hotelService.add(hotel);
      
    }

    @Override
    public void showHotel() {

        hotelService.show();

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
