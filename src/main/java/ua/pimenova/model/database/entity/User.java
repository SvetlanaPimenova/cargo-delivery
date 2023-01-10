package ua.pimenova.model.database.entity;

public class User {
    private int id;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private int account;
    private Role role;
    private String city;
    private String street;
    private String postalCode;
    public enum Role {
        USER,
        MANAGER
    }

    public User() {
    }

    public User(int id, String password, String firstname, String lastname, String phone, String email, int account, Role role, String city, String street, String postalCode) {
        this.id = id;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.account = account;
        this.role = role;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postal_code) {
        this.postalCode = postal_code;
    }

    @Override
    public String toString() {
        return firstname + " " + lastname + ",\n" +
                email + ",\n" + phone + ",\n" +
                city + ", " + street + ",\n" +
                postalCode;
    }

    public static class UserFields {
        public static final String ID = "id";
        public static final String PASSWORD = "password";
        public static final String FIRSTNAME = "firstname";
        public static final String LASTNAME = "lastname";
        public static final String PHONE = "phone";
        public static final String EMAIL = "e-mail";
        public static final String ACCOUNT = "account";
        public static final String ROLE = "role";
        public static final String CITY = "city";
        public static final String STREET = "street";
        public static final String POSTAL_CODE = "postal_code";
    }
}
