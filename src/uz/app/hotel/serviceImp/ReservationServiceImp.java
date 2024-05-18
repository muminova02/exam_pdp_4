package uz.app.hotel.serviceImp;

import uz.app.hotel.database.DB;
import uz.app.hotel.entity.Reservation;
import uz.app.hotel.service.ReservationService;
import static uz.app.hotel.ui.Utils.*;

import java.time.LocalDate;
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
        return null;
    }

    @Override
    public List<Reservation> showReservationByUser(String id) {
        return null;
    }

    @Override
    public List<Reservation> showReservationByHotel(String id) {
        return null;
    }

    @Override
    public boolean cancelReservation(String id) {
        return false;
    }

    @Override
    public boolean finishReservation(String id, LocalDate date) {
        return false;
    }

    @Override
    public boolean rescheduleReservation(String id, LocalDate from, LocalDate to) {
        return false;
    }





}
