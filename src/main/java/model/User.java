package model;


public class User {
    protected int id;
    protected String name;
    protected String email;
    protected String country;
    protected String phoneNumber;
    protected String language;
    protected String roleList;
    private  String login;
    private  String password;
    private ROLE role;

    public User() {
    }

    public User(int id, String name, String email, String country, String phoneNumber, String language, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.language = language;
        this.roleList = role;
    }
    public User( String login, String password, ROLE role) {

        this.login = login;
        this.password = password;
        this.role = role;
    }
    public User(String name, String email, String country, String phoneNumber, String language, String role) {
        super();
        this.name = name;
        this.email = email;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.language = language;
        this.roleList = role;
    }

    public String getRoleList() {return roleList;}

        public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCountry() {
        return country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLanguage() {
        return language;
    }
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
    public ROLE getRole() {
        return role;
    }

    public enum ROLE {
        USER, ADMIN, UNKNOWN
    }
}