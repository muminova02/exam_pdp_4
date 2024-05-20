package uz.app.hotel.serviceImp;

import uz.app.hotel.database.DB;
import uz.app.hotel.entity.Hotel;
import uz.app.hotel.entity.Reservation;
import uz.app.hotel.entity.User;
import uz.app.hotel.enums.HotelStates;
import uz.app.hotel.enums.ReservationStates;
import uz.app.hotel.service.HotelService;
import uz.app.hotel.service.UserService;

import java.time.LocalDate;
import java.util.List;

import static uz.app.hotel.ui.Utils.*;

public class UserServiceImp implements UserService {
    private User currentuser;
    private DB dataBase = DB.getInstance();
    private ReservationServiceImp reserveServise= new ReservationServiceImp();
    private HotelServiceImp hotelServiceImp = new HotelServiceImp();

    @Override
    public void service(User user) {
        currentuser = user;
        while (true){
            switch (getInt("""
                    1. showHotels
                    2. reserve
                    3. showReservations
                    4. cancelReservation
                    5. rescheduleReservation
                    6. showHistory
                    """)){
                case 0->{
                    System.out.println("By");
                    return;
                }
                case 1->showHotels();
                case 2->reserve();
                case 3->showReservations();
                case 4->cancelReservation();
                case 5->rescheduleReservation();
                case 6->showHistory();
            }
        }

    }

    @Override
    public void showHotels() {
        if (checkEMPTY(dataBase.hotelList)) return;
        for (Hotel hotel : hotelServiceImp.showAll()) {
            if (hotel.getStates().equals(HotelStates.ACTIVE)) {
                System.out.println(hotel);
            }
        }
    }

    private boolean checkEMPTY(List<?> list) {
        if (list.isEmpty()){
            System.out.println("Hotels is not yet");
            return true;
        }
        return false;
    }

    @Override
    public void showReservations() {
        if (checkEMPTY(dataBase.reservations)) return;
        for (Reservation reservation : reserveServise.showReservationByUser(currentuser.getUsername())) {
            if (reservation.getReservState().equals(ReservationStates.ACTIVE))
              System.out.println(reservation);
        }
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
            String sana1 = scanStr(" boshlanish sanani kiriting (yyyy-mm-dd): ");
            parse1 = LocalDate.parse(sana1);
            String sana2 = scanStr("tugash sanani kiriting (yyyy-mm-dd): ");
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
        showReservations();
        if (reserveServise.cancelReservation(scanStr("Enter Reservation id: "), ReservationStates.CANCELEDBYUSER)) {
            System.out.println("Reservation is canseled✅");
        }else {
            System.out.println("something is wrong 🔂⚠️");
        }

    }

    @Override
    public void rescheduleReservation() {
        showReservations();
        LocalDate parse1;
        LocalDate parse2;
        String id=scanStr("Enter Reservation id: ");
        while (true){
            String sana1 = scanStr(" boshlanish sanani kiriting (yyyy-mm-dd) : ");
            parse1 = LocalDate.parse(sana1);
            String sana2 = scanStr("tugash sanani kiriting (yyyy-mm-dd): ");
            parse2 = LocalDate.parse(sana2);
            if (parse1.isBefore(parse2)){
                if (reserveServise.rescheduleReservation(id,parse1,parse2)) {
                    System.out.println("Successfully rescheduled");
                    return;
                }
            }
            else {
                System.out.println("Wrong number ⚠️, please enter valid dates");
            }
            if (scanStr("reDate or stop").equals("stop")){
                return;
            }
        }
    }

    @Override
    public void showHistory() {
        if (checkEMPTY(dataBase.reservations)) {
            return;
        }
        for (Reservation reservation : reserveServise.showReservationByUser(currentuser.getUsername())) {
            if (reservation.getReservState().equals(ReservationStates.FINISH)
                    ||reservation.getReservState().equals(ReservationStates.CANCELEDBYADMIN)
                    ||reservation.getReservState().equals(ReservationStates.CANCELEDBYUSER))
                System.out.println(reservation);
        }
    }
    private static UserServiceImp userService;

    public static UserServiceImp getInstance() {
        if (userService == null)
            userService = new UserServiceImp();
        return userService;
    }
}
