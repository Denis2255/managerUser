package model;


public class User {
    protected int id;
    protected String name;
    protected String email;
    protected String country;
    protected String phoneNumber;
    protected String language;

    public User() {
    }

    public User(int id, String name, String email, String country, String phoneNumber, String language) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.language = language;
    }

    public User( String name, String email, String country,String phoneNumber, String language) {
        super();
        this.name = name;
        this.email = email;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}