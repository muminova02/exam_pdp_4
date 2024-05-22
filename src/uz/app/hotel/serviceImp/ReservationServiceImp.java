package uz.app.hotel.serviceImp;

import uz.app.hotel.database.DB;
import uz.app.hotel.entity.Reservation;
import uz.app.hotel.entity.Role;
import uz.app.hotel.entity.User;
import uz.app.hotel.enums.ReservationStates;
import uz.app.hotel.service.ReservationService;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class ReservationServiceImp implements ReservationService {
    private final Double roomPrise = 100_000d;
    private final Double jarima= 50_000d;

    private DB db = DB.getInstance();
    @Override
    public boolean addReservation(Reservation reservation) {
        Integer floor = reservation.getFloor();
        Integer room=reservation.getRoom();
        LocalDate date=reservation.getStartDate();
        LocalDate date2=reservation.getEndDate();
        for (Reservation reservation1 : db.reservations) {
            if (checkReserveAdd(reservation, reservation1, floor, room)) {
                if (date.isAfter(reservation1.getEndDate()) || date2.isBefore(reservation1.getStartDate())) {
                        db.reservations.add(reservation);
                        return true;
                } else return false;
            }
        }
            db.reservations.add(reservation);
            return true;

    }

    private static boolean checkReserveAdd(Reservation reservation, Reservation reservation1, Integer floor, Integer room) {
        return reservation1.getReservState().equals(ReservationStates.ACTIVE) && reservation1.getHotel().getId().equals(reservation.getHotel().getId()) &&
                reservation1.getFloor().equals(floor) &&
                reservation1.getRoom().equals(room);
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
            if (!reservation.getUser().getRole().equals(Role.ANNONYMOUS_USER)&&reservation.getUser().getUsername().equals(username)){
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
    public boolean cancelReservation(String id, ReservationStates state) {
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
            if (reservation.getReservState().equals(ReservationStates.ACTIVE)&&reservation.getId().equals(id)){
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
        Reservation reservation=null;
        for (Reservation reservationDb : db.reservations) {
            if (reservationDb.getId().equals(id))
               reservation=reservationDb;
        }
        if (reservation == null){
            return false;
        }
        reservation.setReservState(ReservationStates.CANCELEDBYUSER);
        Integer floor=reservation.getFloor();
        Integer room=reservation.getRoom();
        for (Reservation reservationDb2 : db.reservations) {
            if (checkReserveAdd(reservation,reservationDb2,floor,room)){
                if (from.isAfter(reservationDb2.getEndDate()) || to.isBefore(reservationDb2.getStartDate())) {
                    reservation.setStartDate(from);
                    reservation.setEndDate(to);
                    reservation.setReservState(ReservationStates.ACTIVE);
                    return true;
                } else return false;
            }
        }
        reservation.setStartDate(from);
        reservation.setEndDate(to);
        reservation.setReservState(ReservationStates.ACTIVE);
        return true;
    }

    public Double getRoomPrise() {
        return roomPrise;
    }

    public Double getJarima() {
        return jarima;
    }
}
