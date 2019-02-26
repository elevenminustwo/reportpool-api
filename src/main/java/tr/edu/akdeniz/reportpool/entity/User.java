package tr.edu.akdeniz.reportpool.entity;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User {

    @Id
    @Column(name="UserID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int userId;

    @Column(name="Username")
    private String username;

    @Column(name="Email")
    private String email;

    @Column(name="Password",nullable = true)
    private int password;

    @Column(name="Name")
    private String name;

    @Column(name="Surname")
    private String surname;

    public User(){

    }

    public User(String username, String email, int password, String name, String surname) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
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

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
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
