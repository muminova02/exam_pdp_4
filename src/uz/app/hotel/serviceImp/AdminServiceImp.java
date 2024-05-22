package uz.app.hotel.serviceImp;

import uz.app.hotel.database.DB;
import uz.app.hotel.entity.*;
import uz.app.hotel.enums.ReservationStates;
import uz.app.hotel.service.AdminService;
import uz.app.hotel.service.HotelService;
import uz.app.hotel.service.ReservationService;
import uz.app.hotel.ui.Utils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static javax.net.ssl.SSLEngineResult.HandshakeStatus.FINISHED;
import static uz.app.hotel.ui.Utils.*;

public class AdminServiceImp implements AdminService {
    private User currentuser;
    private DB dataBase = DB.getInstance();
     HotelServiceImp hotelService=new HotelServiceImp();
     ReservationServiceImp resService=new ReservationServiceImp();


    @Override
    public void service(User user) {
        currentuser = user;
        while (true){
            switch (getInt("""
                    1 Add Hotel
                    2 Show Hotel
                    3 Show Hotels
                    4 Edit Hotels
                    5 Delete hotel
                    6 Show users
                    7 Show reservation history
                    8 Reservayion for user
                    9 Cansel reservation
                    0 Exit ;)
                    
                    """)){
                case 0->{
                    System.out.println("Bye ;)");
                    return;
                }
                case 1-> addHotel();
                case 2-> showHotel();
                case 3-> showHotels();
                case 4-> editHotel();
                case 5-> deleteHotel();
                case 6-> showUsers();
                case 7-> showReservationHistory();
                case 8-> reserveForUser();
                case 9->calcelReservation();
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
        if (dataBase.hotelList.isEmpty()) {
            System.out.println("Hotel is not yet ");
        } else {
            showHotels();
            String id = Utils.scanStr("Enter hotel id :");
            hotelService.show(id);
        }
    }
    @Override
    public void showHotels() {
            int i=1;
            for (Hotel hotel : dataBase.hotelList) {
                System.out.println((i++) +"-> " +hotel);
            }
    }
    @Override
    public void editHotel() {
        if (dataBase.hotelList.isEmpty()) {
            System.out.println("Hotel is not yet ");
        } else {
            showHotels();
            String id = Utils.scanStr("Enter hotel id : ");
            String name = Utils.scanStr("Enter hotel name : ");
            Location location = Location.valueOf(Utils.scanStr("Enter hotel location : "));
            Integer floor = Utils.getInt("Enter hotel floor: ");
            Integer roomcount = Utils.getInt("Enter hotel roomcount: ");
            Hotel hotel = new Hotel(name, location, floor, roomcount);
            hotelService.edit(id, hotel);
        }
    }
    @Override
    public void deleteHotel() {
        if (dataBase.hotelList.isEmpty()) {
            System.out.println("Hotel is not yet ");
        } else {
            showHotels();
            String id = Utils.scanStr("Enter hotel id : ");
            hotelService.delete(id);
        }
    }
    @Override
    public void showUsers() {
        for (User user : dataBase.users) {
            System.out.println(user);
        }
    }
    @Override
    public void showReservationHistory() {
        if (dataBase.reservations.isEmpty()) {
            System.out.println("Reservation is not yet ");
        } else {
            for (Reservation reservation : dataBase.reservations) {
//                if (reservation.getReservState().equals(ReservationStates.FINISH) || reservation.getReservState().equals(ReservationStates.CANCELEDBYADMIN) ||
//                        reservation.getReservState().equals(ReservationStates.CANCELEDBYUSER)) {
//                    System.out.println(reservation);
//                }
                System.out.println(reservation);
            }
        }
    }
    @Override
    public void calcelReservation() {
        if (dataBase.reservations.isEmpty()) {
            System.out.println("Reservation is not yet ");
        } else {
            for (Reservation reservation : dataBase.reservations) {
                if (reservation.getReservState().equals(ReservationStates.ACTIVE)) {
                    System.out.println(reservation);
                }
            }
            String id = Utils.scanStr("Enter reservation id : ");
            resService.cancelReservation(id, ReservationStates.CANCELEDBYADMIN);
        }
    }
    @Override
    public void reserveForUser() {
        showHotels();
        String anonimName=scanStr("Enter name: ");
        int hotelNum = getInt("choose Hotel: ");
        Hotel hotel = dataBase.hotelList.get(hotelNum - 1);
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
            if (parse1.isBefore(parse2)) {
                int dayOfYear = parse1.getDayOfYear();
                int dayofyear2 = parse2.getDayOfYear();
                double countDay = (double) dayofyear2-dayOfYear;
                double allPrise = countDay*resService.getRoomPrise();
                Reservation reservation = new Reservation(new User(anonimName), hotel, floor, room, parse1, parse2,allPrise);
                System.out.println("Eslatma>> Jami narx: " + allPrise + " sum bo'ladi");
                if (resService.addReservation(reservation)) {
                    System.out.println("Aytilgan sanaga yozildingiz");
                } else {
                    System.out.println("Bu sanalarga bron qilingan");
                }
                return;
            } else {
                System.out.println("sanani ketma ketlikda kiriting");
            }
        }
    }
    private static AdminServiceImp adminService;

    public static AdminServiceImp getInstance() {
        if (adminService == null)
            adminService = new AdminServiceImp();
        return adminService;
    }
}
