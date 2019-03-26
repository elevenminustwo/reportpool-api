package tr.edu.akdeniz.reportpool.model;

public class PersonDto {

    private int userId;
    private String username;
    private String email;
    private String password;
    private int isActive;
    private String name;
    private String surname;
    private String role;
    private String unit;

    public PersonDto(String username, String email, int isActive, String name, String surname) {
        this.username = username;
        this.email = email;
        this.isActive = isActive;
        this.name = name;
        this.surname = surname;
    }


    public PersonDto(int userId, String username, String email, String password, int isActive, String name, String surname, String role, String unit) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.unit = unit;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public String getPassword() {
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
