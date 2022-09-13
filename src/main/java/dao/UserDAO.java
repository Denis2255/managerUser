package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.*;
import model.User;
import org.slf4j.Logger;

public class UserDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);

    private static final String SELECT_USER_BY_ID = "select qwerty.users.id, name, qwerty.contactinformation.email, phoneNumber, qwerty.language.language, qwerty.country.country, qwerty.users.role from qwerty.users  \n" +
            "inner join qwerty.contactinformation on qwerty.users.id = qwerty.contactinformation.id\n" +
            "inner join qwerty.country on qwerty.users.id = qwerty.country.id\n" +
            "inner join qwerty.users_language on qwerty.users.id = qwerty.users_language.id\n" +
            "inner join qwerty.language on qwerty.users_language.id = qwerty.language.language_id\n" +
            "where qwerty.users.id like ? ;";
    private static final String SELECT_ALL_USERS = "select qwerty.users.id, name, qwerty.contactinformation.email, phoneNumber, qwerty.language.language, qwerty.country.country, qwerty.users.role from qwerty.users \n" +
            "inner join qwerty.contactinformation on qwerty.users.id = qwerty.contactinformation.id\n" +
            "inner join qwerty.country on qwerty.users.id = qwerty.country.id\n" +
            "inner join qwerty.users_language on qwerty.users.id = qwerty.users_language.id\n" +
            "inner join qwerty.language on qwerty.users_language.id = qwerty.language.language_id;";


    public UserDAO() {
    }

    protected java.sql.Connection getConnection() {
        java.sql.Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "root");
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return connection;
    }

    public void addUser(User user) {
        LOGGER.info("Add users is successfully");

        try {
            java.sql.Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO qwerty.users (name,role) VALUES(?,?);");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getRoleList());
            preparedStatement.execute();


            PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO `qwerty`.`contactinformation` ( `email`, `phoneNumber`) VALUES ( ?,?);");
            preparedStatement1.setString(1, user.getEmail());
            preparedStatement1.setString(2, user.getPhoneNumber());
            preparedStatement1.execute();

            PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO `qwerty`.`country` ( `country`) VALUES ( ?);");
            preparedStatement2.setString(1, user.getCountry());
            preparedStatement2.execute();

            PreparedStatement preparedStatement21 = connection.prepareStatement("SET @a = LAST_INSERT_ID();");
            preparedStatement21.execute();

            PreparedStatement preparedStatement3 = connection.prepareStatement("INSERT INTO `qwerty`.`users_language` (`language_id`) VALUES (@a);");
            preparedStatement3.execute();

            PreparedStatement preparedStatement4 = connection.prepareStatement("INSERT INTO `qwerty`.`language` ( `language`) VALUES ( ?);");
            preparedStatement4.setString(1, user.getLanguage());
            preparedStatement4.execute();


        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public User selectUser(int id) {
        LOGGER.info("Select users is successfully");
        User user = null;
        try (java.sql.Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                String phoneNumber = rs.getString("phoneNumber");
                String language = rs.getString("language");
                String role = rs.getString("role");
                user = new User(id, name, email, country, phoneNumber, language, role);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return user;
    }

    public List<User> selectAllUsers() {
        LOGGER.info("Select All users is successfully");
        List<User> users = new ArrayList<>();
        try (java.sql.Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                String phoneNumber = rs.getString("phoneNumber");
                String language = rs.getString("language");
                String role = rs.getString("role");
                users.add(new User(id, name, email, country, phoneNumber, language, role));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return users;
    }

    public void deleteUser(int id) throws SQLException {
        LOGGER.info("Delete users is successfully");
        try (java.sql.Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SET  @a=?;\n" +
                     "delete  from qwerty.contactinformation where qwerty.contactinformation.id = @a;\n" +
                     "delete  from qwerty.country where qwerty.country.id = @a;\n" +
                     "delete  from qwerty.language where qwerty.language.language_id = @a;\n" +
                     "delete  from qwerty.users_language where qwerty.users_language.id = @a;\n" +
                     "delete  from qwerty.users where qwerty.users.id = @a;")) {
            statement.setInt(1, id);
            statement.executeQuery();

        }
    }

    public void editUser(User user) {
        LOGGER.info("Edit users is successfully");
        try {
            Connection connectionPool = (Connection) Connection.getInstance().getConnection();
            java.sql.Connection connection = getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE qwerty.users SET name = ?, role= ? WHERE id = ?;");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getRoleList());
            preparedStatement.setInt(3, user.getId());
            preparedStatement.execute();

            PreparedStatement preparedStatement1 = connection.prepareStatement("UPDATE `qwerty`.`contactinformation` SET email = ?, phoneNumber = ? WHERE id = ?;");
            preparedStatement1.setString(1, user.getEmail());
            preparedStatement1.setString(2, user.getPhoneNumber());
            preparedStatement1.setInt(3, user.getId());
            preparedStatement1.execute();

            PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE `qwerty`.`country` SET country = ? WHERE id = ?;");
            preparedStatement2.setString(1, user.getCountry());
            preparedStatement2.setInt(2, user.getId());
            preparedStatement2.execute();

            PreparedStatement preparedStatement3 = connection.prepareStatement("UPDATE `qwerty`.`language` SET language = ? WHERE language_id = ?;");
            preparedStatement3.setString(1, user.getLanguage());
            preparedStatement3.setInt(2, user.getId());
            preparedStatement3.execute();


        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
    private final List<User> store = new ArrayList<>();
    public void addCostumer(final User user) {

        for (User u : store) {
            if (u.getLogin().equals(user.getLogin()) && u.getPassword().equals(user.getPassword())) {
                return;
            }
        }
        store.add(user);
    }
    public User.ROLE getRoleByLoginPassword(final String login, final String password) {
        User.ROLE result = User.ROLE.UNKNOWN;

        for (User user : store) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                result = user.getRole();
            }
        }
        return result;
    }

    public boolean userIsExist(final String login, final String password) {

        boolean result = false;

        for (User user : store) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                result = true;
                break;
            }
        }
        return result;
    }

}