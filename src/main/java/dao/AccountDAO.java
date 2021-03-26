package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import connection.ConnectionFactory;
import model.Account;

public class AccountDAO {

    protected static final Logger LOGGER = Logger.getLogger(AccountDAO.class.getName());

    private static final String insertStatementString = "INSERT INTO account (id,number,type,balance,creation_date)" + " VALUES (?,?,?,?,?)";

    private final static String deleteStatementString = "DELETE FROM account where id = ?;";

    private final static String findStatementString = "SELECT * FROM account where id = ?";

    private final static String findByNumberStatementString = "SELECT * FROM account where number = ?";

    private final static String updateAccountStatementString = "UPDATE Account a set a.type = ?, a.balance = ? where a.number = ?";

    private final static String getStatementString = "SELECT * FROM account where id not like ?";

    private final static String getUtilitiesStatementString = "SELECT * FROM account where id like ?";

    public static int insert(Account account) {

        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, account.getId());
            insertStatement.setString(2, account.getNumber());
            insertStatement.setString(3, account.getType());
            insertStatement.setFloat(4, account.getBalance());
            insertStatement.setDate(5, account.getCreationDate());

            insertStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "AccountDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return 0;
    }

    public static Account findById(String id) {
        Account toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setString(1, id);

            rs = findStatement.executeQuery();
            if (rs.next()) {
                String number = rs.getString("number");
                String type = rs.getString("type");
                float balance = rs.getFloat("balance");
                Date creationDate = rs.getDate("creation_date");
                toReturn = new Account(id,number,type,balance,creationDate);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"AccountDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static Account findByNumber(String number) {
        Account toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findByNumberStatementString);
            findStatement.setString(1, number);

            rs = findStatement.executeQuery();
            if (rs.next()) {
                String id = rs.getString("id");
                String type = rs.getString("type");
                float balance = rs.getFloat("balance");
                Date creationDate = rs.getDate("creation_date");
                toReturn = new Account(id,number,type,balance,creationDate);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"AccountDAO:findByNumber " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static int updateAccount(Account account) {

        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;

        try {
            insertStatement = dbConnection.prepareStatement(updateAccountStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, account.getType());
            insertStatement.setFloat(2, account.getBalance());
            insertStatement.setString(3, account.getNumber());

            insertStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "AccountDAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return 0;
    }

    public static int delete(Account account) {
        if (account == null)
            return -1;
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setString(1, account.getId());
            deleteStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "AccountDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
        return 0;
    }

    public static ArrayList<Account> getAccounts() {
        ArrayList<Account> toReturn = new ArrayList<>();
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(getStatementString);
            findStatement.setString(1,"UTILITIES.%");
            rs = findStatement.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String number = rs.getString("number");
                String type = rs.getString("type");
                float balance = rs.getFloat("balance");
                Date creationDate = rs.getDate("creation_date");
                toReturn.add(new Account(id,number,type,balance,creationDate));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"AccountDAO:getAccounts " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static ArrayList<Account> getUtilitiesAccounts() {
        ArrayList<Account> toReturn = new ArrayList<>();
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(getUtilitiesStatementString);

            findStatement.setString(1, "UTILITIES.%");

            rs = findStatement.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String number = rs.getString("number");
                String type = rs.getString("type");
                float balance = rs.getFloat("balance");
                Date creationDate = rs.getDate("creation_date");
                toReturn.add(new Account(id, number, type, balance, creationDate));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"AccountDAO:getUtilitiesAccounts " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

}