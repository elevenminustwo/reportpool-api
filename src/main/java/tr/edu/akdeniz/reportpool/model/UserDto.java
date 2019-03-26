package tr.edu.akdeniz.reportpool.model;

import java.io.Serializable;

public class UserDto {
    private int userId;
    private String username;
    private String email;
    private String password;
    private int isActive;
    private String name;
    private String surname;

    public UserDto(int userId, String username, String email, String password, String name, String surname,int isActive) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.isActive =isActive;
    }
    public UserDto(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public Serializable getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}
