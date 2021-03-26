package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import connection.ConnectionFactory;
import model.User;
import utils.ControllerUtils;
import utils.ErrorMessages;

public class UserDAO {

    protected static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());

    private static final String insertStatementString = "INSERT INTO user (id,username,password,name,is_admin)" + " VALUES (?,?,?,?,?)";

    private final static String deleteStatementString = "DELETE FROM user where id = ?;";

    private final static String findStatementString = "SELECT * FROM user where username = ?";

    private final static String findByIdStatementString = "SELECT * FROM user where id = ?";

    private final static String getStatementString = "SELECT * FROM user";

    private final static String updateUserStatementString = "UPDATE User a set a.username = ?, a.password = ?, a.name = ?, a.is_admin = ? where a.id = ?";

    public static int insert(User user) {
        if (user.getUsername().isEmpty()) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.USERNAME_EMPTY);
            return -1;
        }
        if (user.getPassword().isEmpty()) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.PASSWORD_EMPTY);
            return -1;
        }
        if (user.getName().isEmpty()) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.NAME_EMPTY);
            return -1;
        }

        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        try {
            User u = findByUsername(user.getUsername());
            if (u != null) {
                ControllerUtils.createSwingErrorMessage(ErrorMessages.USERNAME_TAKEN);
                return -1;
            }
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, user.getId());
            insertStatement.setString(2, user.getUsername());
            insertStatement.setString(3, user.getPassword());
            insertStatement.setString(4, user.getName());
            insertStatement.setBoolean(5, user.isAdmin());

            insertStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "UserDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return 0;
    }

    public static int delete(User user) {
        if (user == null)
            return -1;
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setString(1, user.getId());
            deleteStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "UserDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
        return 0;
    }

    public static User findByUsername(String username) {
        User toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setString(1, username);

            rs = findStatement.executeQuery();
            if (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                boolean admin = rs.getBoolean("is_admin");
                toReturn = new User(id,username,password,name,admin);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"UserDAO:findByUserName " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static User findByID(String id) {
        User toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findByIdStatementString);
            findStatement.setString(1, id);

            rs = findStatement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String username = rs.getString("username");
                String password = rs.getString("password");
                boolean admin = rs.getBoolean("is_admin");
                toReturn = new User(id,username,password,name,admin);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"UserDAO:findByID " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static int updateUser(String oldUsername, User user) {
        if (user.getUsername().isEmpty()) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.USERNAME_EMPTY);
            return -1;
        }
        if (user.getPassword().isEmpty()) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.PASSWORD_EMPTY);
            return -1;
        }
        if (user.getName().isEmpty()) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.NAME_EMPTY);
            return -1;
        }

        User old = findByUsername(oldUsername);

        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;

        try {
            insertStatement = dbConnection.prepareStatement(updateUserStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, user.getUsername());
            insertStatement.setString(2, user.getPassword());
            insertStatement.setString(3, user.getName());
            insertStatement.setBoolean(4, user.isAdmin());
            insertStatement.setString(5, old.getId());

            insertStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "UserDAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return 0;
    }

    public static ArrayList<User> getUsers() {
        ArrayList<User> toReturn = new ArrayList<>();
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(getStatementString);

            rs = findStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String id = rs.getString("id");
                boolean admin = rs.getBoolean("is_admin");
                toReturn.add(new User(id,username,password,name,admin));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"UserDAO:getUsers " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

}