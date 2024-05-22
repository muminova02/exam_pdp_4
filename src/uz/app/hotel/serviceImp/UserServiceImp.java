package uz.app.hotel.serviceImp;

import uz.app.hotel.database.DB;
import uz.app.hotel.entity.Hotel;
import uz.app.hotel.entity.Reservation;
import uz.app.hotel.entity.Role;
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
                    7. show MyBalance
                    """)){
                case 0->{
                    System.out.println("By");
                    return;
                }
                case 1-> showHotels();
                case 2->reserve();
                case 3-> showReservations();
                case 4->cancelReservation();
                case 5->rescheduleReservation();
                case 6->showHistory();
                case 7->showUserBalance();
            }
        }

    }

    @Override
    public boolean showHotels() {
        if (checkEMPTY(dataBase.hotelList,"Hotel")) return false;
        System.out.println("""
              Eslatma bron qilish narxi: bir kunga >> 100 ming so'm;
              """);
        int i=1;
        for (Hotel hotel : hotelServiceImp.showAll()) {
            if (hotel.getStates().equals(HotelStates.ACTIVE)) {
                System.out.println((i++) +"-> " +hotel);
            }
        }
        return true;
    }

    private boolean checkEMPTY(List<?> list,String name) {
        if (list.isEmpty()){
            System.out.println(name + " is not yet");
            return true;
        }
        return false;
    }

    @Override
    public boolean showReservations() {
        if (checkEMPTY(dataBase.reservations,"Reservation")) return false;
        for (Reservation reservation : reserveServise.showReservationByUser(currentuser.getUsername())) {
            if (reservation.getReservState().equals(ReservationStates.ACTIVE))
              System.out.println(reservation);
        }
        return true;
    }

    @Override
    public void reserve() {
        if (!showHotels()) {
            return;
        }
        int hotelNum = getInt("choose Hotel: ");
        Hotel hotel = dataBase.hotelList.get(hotelNum-1);
        Integer floor = getInt("enter floor num: ");
        Integer room = getInt("enter room num: ");
        if (floor>hotel.getFloors()||floor==0&&room>hotel.getRoomsCount()||room==0){
            System.out.println("etaj va xona raqamlarini to'g'ri kiriting.");
            return;
        }
        LocalDate parse1;
        LocalDate parse2;
        while (true) {
            String sana1 = scanStr("boshlanish sanani kiriting (yyyy-mm-dd): ");
            parse1 = LocalDate.parse(sana1);
            String sana2 = scanStr("tugash sanani kiriting (yyyy-mm-dd): ");
            parse2 = LocalDate.parse(sana2);
            if (parse1.isBefore(parse2)){
                int dayOfYear = parse1.getDayOfYear();
                int dayofyear2 = parse2.getDayOfYear();
                double countDay = (double) dayofyear2-dayOfYear;
                double allPrise = countDay*reserveServise.getRoomPrise();
                Reservation reservation = new Reservation(currentuser,hotel,floor,room,parse1,parse2,allPrise);
                if (currentuser.getBalance()<= allPrise){
                    System.out.println("Eslatma>> Jami narx: " + allPrise + " sum bo'ladi");
                    System.out.println("Sizda Mablag' yetarli emas");
                    return;
                }
                System.out.println("Eslatma>> Jami narx: " + allPrise + " sum bo'ladi");
                if (reserveServise.addReservation(reservation)) {
                    System.out.println("Aytilgan sanaga yozildingiz");
                    currentuser.setBalance(currentuser.getBalance()-allPrise);
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
        if (!showReservations()) {
            return;
        }
        String cancelResId = scanStr("Enter Reservation id: ");
        if (reserveServise.cancelReservation(cancelResId, ReservationStates.CANCELEDBYUSER)) {
            for (Reservation reservation : dataBase.reservations) {
                if (reservation.getId().equals(cancelResId)){
                    currentuser.setBalance(currentuser.getBalance()+reservation.getPrise());
                }
            }
            System.out.println("Reservation is canseled✅");

        }else {
            System.out.println("something is wrong 🔂⚠️");
        }

    }

    @Override
    public void rescheduleReservation() {
        if (!showReservations()) {
            return;
        }
        LocalDate parse1;
        LocalDate parse2;
        String id=scanStr("Enter Reservation id: ");
        while (true){
            String sana1 = scanStr("boshlanish sanani kiriting (yyyy-mm-dd) : ");
            parse1 = LocalDate.parse(sana1);
            String sana2 = scanStr("tugash sanani kiriting (yyyy-mm-dd): ");
            parse2 = LocalDate.parse(sana2);
            if (parse1.isBefore(parse2)){
                int dayOfYear = parse1.getDayOfYear();
                int dayofyear2 = parse2.getDayOfYear();
                double countDay = (double) dayofyear2-dayOfYear;
                double allPrise = countDay*reserveServise.getRoomPrise();
                if (currentuser.getBalance()<= allPrise){
                    System.out.println("Eslatma>> Jami narx: " + allPrise + " sum bo'ladi");
                    System.out.println("Sizda Mablag' yetarli emas");
                    return;
                }
                System.out.println("Eslatma>> Jami narx: " + allPrise + " sum bo'ladi");
                if (reserveServise.rescheduleReservation(id,parse1,parse2)) {
                    for (Reservation reservation : dataBase.reservations) {
                        if (reservation.getId().equals(id)){
                           currentuser.setBalance(currentuser.getBalance()+reservation.getPrise());
                           currentuser.setBalance(currentuser.getBalance()-allPrise);
                           reservation.setPrise(allPrise);
                            System.out.println("Successfully rescheduled");
                           return;
                        }
                    }
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
        if (checkEMPTY(dataBase.reservations,"Reservations")) {
            return;
        }
        for (Reservation reservation : reserveServise.showReservationByUser(currentuser.getUsername())) {
            if (reservation.getReservState().equals(ReservationStates.FINISH)
                    ||reservation.getReservState().equals(ReservationStates.CANCELEDBYADMIN)
                    ||reservation.getReservState().equals(ReservationStates.CANCELEDBYUSER))
                System.out.println(reservation);
        }
    }

    @Override
    public void finishReservation() {

    }

    private void showUserBalance() {
        System.out.println("Balance bo'limi ========");
        while (true){
            switch (showBalanceMenu()){
                case 0-> {
                    return;
                }
                case 1->{
                    System.out.println( "My Balance: " + currentuser.getBalance());
                }
                case 2->{
                    double addMoney = (double) getInt("Qo'shmoqchi bo'lgan pulni kiriting: ");
                    currentuser.setBalance(currentuser.getBalance()+addMoney);
                }
            }
        }
    }


    private static UserServiceImp userService;

    public static UserServiceImp getInstance() {
        if (userService == null)
            userService = new UserServiceImp();
        return userService;
    }



}
