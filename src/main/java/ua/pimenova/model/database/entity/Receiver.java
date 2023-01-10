package ua.pimenova.model.database.entity;

public class Receiver {
    private int id;
    private String firstname;
    private String lastname;
    private String phone;
    private String city;
    private String street;
    private String postal_code;

    public Receiver() {
    }

    public Receiver(int id, String firstname, String lastname, String phone, String city, String street, String postal_code) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.city = city;
        this.street = street;
        this.postal_code = postal_code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    @Override
    public String toString() {
        return firstname + " " + lastname+ ",\n" +
                phone + ",\n" + street + ", " + postal_code;
    }

    public static class ReceiverFields {
        public static final String ID = "id";
        public static final String FIRSTNAME = "firstname";
        public static final String LASTNAME = "lastname";
        public static final String PHONE = "phone";
        public static final String CITY = "city";
        public static final String STREET = "street";
        public static final String POSTAL_CODE = "postal_code";
    }
}
