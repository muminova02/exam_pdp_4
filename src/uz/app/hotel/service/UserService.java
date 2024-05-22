package uz.app.hotel.service;

import uz.app.hotel.entity.User;

public interface UserService {
    void service(User user);
    boolean showHotels();
    boolean showReservations();
    void reserve();
    void cancelReservation();
    void rescheduleReservation();
    void finishReservation();
    void showHistory();

}
