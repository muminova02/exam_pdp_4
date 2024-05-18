package uz.app.hotel.serviceImp;

import uz.app.hotel.database.DB;
import uz.app.hotel.entity.Role;
import uz.app.hotel.entity.User;
import uz.app.hotel.service.AuthService;
import static uz.app.hotel.ui.Utils.*;

import java.util.Objects;

import static uz.app.hotel.ui.Utils.getInt;
import static uz.app.hotel.ui.Utils.scanStr;

public class AuthServiceImp implements AuthService {
    static DB database = DB.getInstance();
    static AdminServiceImp adminService = AdminServiceImp.getInstance();
    static UserServiceImp userService = UserServiceImp.getInstance();



    public void service(){
        while (true) {
            switch (getInt("""
                    0 exit
                    1 sign in
                    2 sign up
                    """)) {
                case 0 -> {
                    System.out.println("see you soon!");
                    return;
                }
                case 1 -> {
                    signIn();
                }
                case 2 -> {
                    signUp();
                }
            }
        }
    }




    @Override
    public void signUp() {
        User user = new User();
        System.out.println();
        user.setName(scanStr("enter name"));
        System.out.println();
        user.setUsername(scanStr("enter username"));
        System.out.println();
        user.setPassword(scanStr("enter password"));
        user.setName(String.valueOf(Role.USER));
        user.setBalance(100000.0);
        if (database.users.add(user)) {
            System.out.println("successfully registered!");
        } else {
            System.out.println("username exists!");
        }
    }

    @Override
    public void signIn() {
        String username = scanStr("enter username");
        String password = scanStr("enter passwrd");
        for (User user : database.users) {
            if (Objects.equals(user.getUsername(), username) &&
                    Objects.equals(user.getPassword(), password)) {
                switch (user.getRole().name()) {
                    case "ADMIN" -> {
                        adminService.service(user);
                    }
                    case "USER" -> {
                        userService.service(user);
                    }

                }
            }
        }
    }
}
