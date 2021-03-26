package dao;

import connection.ConnectionFactory;
import model.Action;
import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActionDAO {

    protected static final Logger LOGGER = Logger.getLogger(ActionDAO.class.getName());

    private static final String insertStatementString = "INSERT INTO action (id,id_user,type,date,time,id_client,id_account,id_transfer)" + " VALUES (?,?,?,?,?,?,?,?)";

    private final static String getStatementString = "SELECT * FROM action where id_user = ? and date between ? and ? order by date, time";

    public static void insert(Action action) {

        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, action.getId());
            insertStatement.setString(2, action.getIdUser());
            insertStatement.setString(3, action.getType());
            insertStatement.setDate(4, action.getDate());
            insertStatement.setTime(5, action.getTime());
            insertStatement.setString(6, action.getIdClient());
            insertStatement.setString(7, action.getIdAccount());
            insertStatement.setString(8, action.getIdTransfer());

            insertStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ActionDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
    }

    public static ArrayList<Action> getActions(User user, Date startDate, Date endDate) {
        ArrayList<Action> toReturn = new ArrayList<>();
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(getStatementString);
            findStatement.setString(1, user.getId());
            findStatement.setDate(2, startDate);
            findStatement.setDate(3, endDate);
            rs = findStatement.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String type = rs.getString("type");
                Date date = rs.getDate("date");
                Time time = rs.getTime("time");
                String idClient = rs.getString("id_client");
                String idAccount = rs.getString("id_account");
                String idTransfer = rs.getString("id_transfer");

                toReturn.add(new Action(id, user.getId(), type, date, time, idClient, idAccount, idTransfer));
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

}
