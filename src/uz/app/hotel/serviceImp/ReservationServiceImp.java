package uz.app.hotel.serviceImp;

import uz.app.hotel.database.DB;
import uz.app.hotel.entity.Reservation;
import uz.app.hotel.enums.ReservationStates;
import uz.app.hotel.service.ReservationService;
import static uz.app.hotel.ui.Utils.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationServiceImp implements ReservationService {

    private DB db = DB.getInstance();
    @Override
    public boolean addReservation(Reservation reservation) {

        Integer floor = reservation.getFloor();
        Integer room=reservation.getRoom();
        LocalDate date=reservation.getStartDate();
        LocalDate date2=reservation.getEndDate();
        for (Reservation reservation1 : db.reservations) {
            if (reservation1.getHotel().getId().equals(reservation.getHotel().getId()) &&
                    reservation1.getFloor().equals(floor) &&
                    reservation1.getRoom().equals(room)) {
                if (date.isAfter(reservation1.getEndDate()) || date2.isBefore(reservation1.getStartDate())) {
                    db.reservations.add(reservation);
                    return true;
                } else return false;
            }

        }
        return false;
    }

    @Override
    public Reservation showReservation(String id) {
        for (Reservation reservation : db.reservations) {
            if (reservation.getId().equals(id)){
                return reservation;
            }
        }
        return null;
    }

    @Override
    public List<Reservation> showReservationByUser(String username) {
        List<Reservation> reservations = new ArrayList<>();
        for (Reservation reservation : db.reservations) {
            if (reservation.getUser().getUsername().equals(username)){
                reservations.add(reservation);
            }
        }
        return reservations;
    }

    @Override
    public List<Reservation> showReservationByHotel(String id) {
        List<Reservation> reservations = new ArrayList<>();
        for (Reservation reservation : db.reservations) {
            if (reservation.getHotel().getId().equals(id)){
                reservations.add(reservation);
            }
        }
        return reservations;
    }

    @Override
    public boolean cancelReservation(String id,ReservationStates state) {
        for (Reservation reservation : db.reservations) {
            if (reservation.getId().equals(id)){
                reservation.setReservState(state);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean finishReservation(String id, LocalDate date) {
        for (Reservation reservation : db.reservations) {
            if (reservation.getId().equals(id)){
                if (reservation.getEndDate().isBefore(date)){
                    reservation.setReservState(ReservationStates.FINISH);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean rescheduleReservation(String id, LocalDate from, LocalDate to) {
        return false;
    }



    private String delateTsts(){
        System.out.println("""
                sadfasdf
                sadfsdf
                test Barchinoy
                """);
        return "hi";
    }




}
