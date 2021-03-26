package controller;

import dao.ActionDAO;
import dao.UserDAO;
import model.Action;
import model.User;
import utils.ControllerUtils;
import utils.ErrorMessages;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdminController {

    public AdminController() {
    }

    public int insert(String username, String password, String name, boolean isAdmin) {
        User user = new User(username, password, name, isAdmin);
        return UserDAO.insert(user);
    }

    public int delete(String username, User user) {
        User u = UserDAO.findByUsername(username);
        if (u != null && user.getId().equals(u.getId())) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.CANT_DELETE_USER);
            return -1;
        } else {
            UserDAO.delete(u);
            return 0;
        }
    }

    public int update(String oldUsername, String username, String password, String name, boolean isAdmin) {
        if (oldUsername == null) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.NO_USER_SELECTED);
            return -1;
        }
        User user = new User(username, password, name, isAdmin);
        return UserDAO.updateUser(oldUsername, user);
    }

    public ArrayList<User> getUsers() {
        return UserDAO.getUsers();
    }

    public String getPassword(String username) {
        return UserDAO.findByUsername(username).getPassword();
    }

    public int generateReport(String username, String startDate, String endDate) {
        if (username.isEmpty()) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.NO_USER_SELECTED);
            return -1;
        }
        if (startDate.isEmpty()) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.START_DATE_EMPTY);
            return -1;
        }
        if (endDate.isEmpty()) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.END_DATE_EMPTY);
            return -1;
        }

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date sDate;
        Date eDate;
        try {
            sDate = new Date(format.parse(startDate).getTime());
            eDate = new Date(format.parse(endDate).getTime());
        } catch (ParseException e) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.INCORRECT_DATE_FORMAT);
            return -1;
        }
        User user = UserDAO.findByUsername(username);
        ArrayList<Action> actions = ActionDAO.getActions(user, sDate, eDate);
        ControllerUtils.generateReport(username, startDate, endDate, actions);
        return 0;
    }

}
