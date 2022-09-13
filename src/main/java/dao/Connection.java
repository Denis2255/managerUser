package dao;


import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;

public class Connection {

    private Connection(){
    }

    private static Connection instance = null;

    public static Connection getInstance(){
        if (instance==null)
            instance = new Connection();
        return instance;
    }

    public java.sql.Connection getConnection(){

        java.sql.Connection c = null;
        try {
            InitialContext initContext= new InitialContext();
            DataSource ds = (DataSource)initContext.lookup("java:comp/env/jdbc/qwerty@localhost");
            c = ds.getConnection();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
        return c;
    }
}
