package uz.app.hotel.ui;

import java.util.Scanner;

public interface Utils {
    Scanner scannerStr = new Scanner(System.in);
    Scanner scannerInt = new Scanner(System.in);

    static String scanStr(String hint){
        System.out.print(hint);
        return scannerStr.nextLine();
    }

    static int scanInt(String hint){
        System.out.print(hint);
        return scannerInt.nextInt();
    }

    static int getInt(String text) {
        try {
            System.out.print(text);
            String n = scannerStr.nextLine();
            return Integer.parseInt(n);
        } catch (Exception e) {
            return getInt(text);
        }
    }

    static int showBalanceMenu() {
        return getInt("""
                1.MyBalance
                2.Pul qo'shish
                0.Exit
                
                choose: 
                """);
    }

}
