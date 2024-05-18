package uz.app.hotel.serviceImp;

import uz.app.hotel.database.DB;
import uz.app.hotel.entity.Hotel;
import uz.app.hotel.entity.Reservation;
import uz.app.hotel.entity.User;
import uz.app.hotel.service.UserService;

import java.time.LocalDate;

import static uz.app.hotel.ui.Utils.*;

public class UserServiceImp implements UserService {
    private User currentuser;
    private DB dataBase = DB.getInstance();
    private ReservationServiceImp reserveServise= new ReservationServiceImp();

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

        showHotels();
        int hotelNum = getInt("choose Hotel: ");
        Hotel hotel = dataBase.hotelList.get(hotelNum-1);
        Integer floor = getInt("enter floor num");
        Integer room = getInt("enter room num");
        LocalDate parse1;
        LocalDate parse2;
        while (true) {
            String sana1 = scanStr(" boshlanish sanani kiriting (dd-mm-yyyy)");
            parse1 = LocalDate.parse(sana1);
            String sana2 = scanStr("tugash sanani kiriting (dd-mm-yyyy)");
            parse2 = LocalDate.parse(sana2);
            if (parse1.isBefore(parse2)){
                Reservation reservation = new Reservation(currentuser,hotel,floor,room,parse1,parse2);
                if (reserveServise.addReservation(reservation)) {
                    System.out.println("Aytilgan sanaga yozildingiz");
                }else {
                    System.out.println("Bu sanalarga bron qilingan");
                }
                return;
            }
            else {
                System.out.println("sanani ketma ketlikda kiriting");
            }
        }

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
