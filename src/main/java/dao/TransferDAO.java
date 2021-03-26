package dao;

import connection.ConnectionFactory;
import model.Client;
import model.Transfer;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransferDAO {

    protected static final Logger LOGGER = Logger.getLogger(TransferDAO.class.getName());

    private static final String insertStatementString = "INSERT INTO transfer (id,id_user,id_account_from,id_account_to,amount,date)" + " VALUES (?,?,?,?,?,?)";

    private final static String findByIdStatementString = "SELECT * FROM transfer where id = ?";

    public static void insert(Transfer transfer) {

        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, transfer.getId());
            insertStatement.setString(2, transfer.getIdUser());
            insertStatement.setString(3, transfer.getIdAccountFrom());
            insertStatement.setString(4, transfer.getIdAccountTo());
            insertStatement.setFloat(5, transfer.getAmount());
            insertStatement.setDate(6, transfer.getDate());

            insertStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "TransferDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
    }

    public static Transfer findById(String id) {
        Transfer toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findByIdStatementString);
            findStatement.setString(1, id);

            rs = findStatement.executeQuery();
            if (rs.next()) {
                String idUser = rs.getString("id_user");
                String idAccountFrom = rs.getString("id_account_from");
                String idAccountTo = rs.getString("id_account_to");
                float amount = rs.getFloat("amount");
                Date date = rs.getDate("date");
                toReturn = new Transfer(id, idUser, idAccountFrom, idAccountTo, amount, date);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"TransferDAO:findByID " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

}
