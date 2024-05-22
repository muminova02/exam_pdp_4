package uz.app.hotel.entity;

import java.util.Objects;
import java.util.UUID;

public class User {
    private final String  id= UUID.randomUUID().toString();
    private String name;
    private String username;
    private String password;
    private double balance;
    private Role role;


    public User() {

    }
    public User(String name,String username,String password,Double balance,Role role){
        this.name = name;
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.role = role;
    }

    public User(String anonimName) {
        this.name = anonimName;
        this.role=Role.ANNONYMOUS_USER;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", role=" + role +
                '}';
    }

}
