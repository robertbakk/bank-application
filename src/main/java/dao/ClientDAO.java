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
import model.Client;
import utils.ControllerUtils;
import utils.ErrorMessages;

public class ClientDAO {

    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());

    private static final String insertStatementString = "INSERT INTO client (id,name,address,cnp)" + " VALUES (?,?,?,?)";

    private final static String deleteStatementString = "DELETE FROM client where id = ?;";

    private final static String findStatementString = "SELECT * FROM client where cnp = ?";

    private final static String findByIdStatementString = "SELECT * FROM client where id = ?";

    private final static String getStatementString = "SELECT * FROM client";

    private final static String updateClientStatementString = "UPDATE Client a set a.name = ?, a.address = ? where a.cnp = ?";

    public static int insert(Client client) {
        if (client.getName().isEmpty()) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.NAME_EMPTY);
            return -1;
        }
        if (client.getAddress().isEmpty()) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.ADDRESS_EMPTY);
            return -1;
        }
        if (client.getCnp().isEmpty()) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.CNP_EMPTY);
            return -1;
        }

        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        try {
            Client c = findByCNP(client.getCnp());
            if (c != null) {
                ControllerUtils.createSwingErrorMessage(ErrorMessages.CNP_TAKEN);
                return -1;
            }
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, client.getId());
            insertStatement.setString(2, client.getName());
            insertStatement.setString(3, client.getAddress());
            insertStatement.setString(4, client.getCnp());

            insertStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return 0;
    }

    public static int delete(Client client) {
        if (client == null)
            return -1;
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setString(1, client.getId());
            deleteStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
        return 0;
    }

    public static Client findById(String id) {
        Client toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findByIdStatementString);
            findStatement.setString(1, id);

            rs = findStatement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String address = rs.getString("address");
                String cnp = rs.getString("cnp");
                toReturn = new Client(id,name,address,cnp);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ClientDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static Client findByCNP(String cnp) {
        Client toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setString(1, cnp);

            rs = findStatement.executeQuery();
            if (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                toReturn = new Client(id,name,address,cnp);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ClientDAO:findByCNP " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static int updateClient(Client client) {
        if (client.getName().isEmpty()) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.NAME_EMPTY);
            return -1;
        }
        if (client.getAddress().isEmpty()) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.ADDRESS_EMPTY);
            return -1;
        }
        if (client.getCnp().isEmpty()) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.CNP_EMPTY);
            return -1;
        }

        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;

        try {
            insertStatement = dbConnection.prepareStatement(updateClientStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, client.getName());
            insertStatement.setString(2, client.getAddress());
            insertStatement.setString(3, client.getCnp());

            insertStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return 0;
    }

    public static ArrayList<Client> getClients() {
        ArrayList<Client> toReturn = new ArrayList<>();
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(getStatementString);

            rs = findStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String address = rs.getString("address");
                String cnp = rs.getString("cnp");
                String id = rs.getString("id");
                toReturn.add(new Client(id,name,address,cnp));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ClientDAO:getClients " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

}