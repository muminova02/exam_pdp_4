package uz.app.hotel.serviceImp;

import uz.app.hotel.database.DB;
import uz.app.hotel.entity.Hotel;
import uz.app.hotel.enums.HotelStates;
import uz.app.hotel.service.HotelService;
import uz.app.hotel.ui.Utils;

import static uz.app.hotel.enums.HotelStates.DELETED;
import static uz.app.hotel.ui.Utils.*;

import java.util.ArrayList;
import java.util.List;

public class HotelServiceImp implements HotelService {
    private DB db=DB.getInstance();

    @Override
    public void add(Hotel hotel) {
     db.hotelList.add(hotel);
        System.out.println("Hotel is added ");
    }

    @Override
    public Hotel show(String id) {
        for (Hotel hotel : db.hotelList) {
              if(hotel.getId().equals(id)){
                  System.out.println(hotel);
                  return hotel;
              }
        }

        return null;
    }

    @Override
    public List<Hotel> showAll() {
//        for (Hotel hotel : db.hotelList) {
//            System.out.println(hotel + " " + hotel.getId());
//        }
        return db.hotelList;
    }

    @Override
    public boolean edit(String id, Hotel hotel) {
        for (Hotel hotel1 : db.hotelList) {
            if(id.equals(hotel1.getId())){
               hotel1.setHotel(hotel);
               return  true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        for (Hotel hotel : db.hotelList) {
            if (id.equals(hotel.getId())){
                hotel.setStates(DELETED);
            }
            return true;
        }
        return false;
    }



}
