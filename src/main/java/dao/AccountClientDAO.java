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
import model.Account;
import model.Client;

public class AccountClientDAO {

    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());

    private static final String insertStatementString = "INSERT INTO account_client (id_account, id_client)" + " VALUES (?,?)";

    private final static String deleteStatementString = "DELETE FROM account_client where id_account = ?;";

    private final static String getAccountsStatementString = "SELECT * FROM account_client where id_client = ?";

    private final static String getClientStatementString = "SELECT * FROM account_client where id_account = ?";

    public static ArrayList<Account> getAccounts(Client client) {
        ArrayList<Account> toReturn = new ArrayList<>();
        ArrayList<String> accountIDs = new ArrayList<>();
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(getAccountsStatementString);
            findStatement.setString(1, client.getId());
            rs = findStatement.executeQuery();
            while (rs.next()) {
                String id_account = rs.getString("id_account");
                accountIDs.add(id_account);
            }
            for (String accountId : accountIDs) {
                Account account = AccountDAO.findById(accountId);
                toReturn.add(account);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"AccountClientDAO:getAccounts " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static Client getClient(Account account) {
        if (account == null)
            return null;

        Client toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(getClientStatementString);
            findStatement.setString(1, account.getId());
            rs = findStatement.executeQuery();
            if (rs.next()) {
                String idClient = rs.getString("id_client");
                toReturn = ClientDAO.findById(idClient);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"AccountClientDAO:getAccounts " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static int insert(Account account, Client client) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, account.getId());
            insertStatement.setString(2, client.getId());

            insertStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "AccountClientDAO:insert " + e.getMessage());
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
            LOGGER.log(Level.WARNING, "AccountClientDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
        return 0;
    }

}