package controller;

import dao.ActionDAO;
import dao.UserDAO;
import model.Action;
import model.User;
import utils.ActionTypes;
import utils.ControllerUtils;
import utils.ErrorMessages;
import view.AdminView;
import view.EmployeeView;

public class LoginController {

    public LoginController() {
    }

    public void login(String username, String password) {
        if (username.isEmpty()) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.USERNAME_EMPTY);
            return;
        }
        if (password.isEmpty()) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.PASSWORD_EMPTY);
            return;
        }
        User user = UserDAO.findByUsername(username);
        if (user == null) {
            ControllerUtils.createSwingErrorMessage(username + ": " + ErrorMessages.INVALID_USERNAME_MESSAGE);
            return;
        }
        if (!password.equals(user.getPassword())) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.WRONG_PASSWORD_MESSAGE);
            return;
        }
        if (user.isAdmin()) {
            Action action = new Action(user.getId(), ActionTypes.ACTION_LOGIN, null, null, null);
            ActionDAO.insert(action);
            new AdminView(user);
        } else {
            Action action = new Action(user.getId(), ActionTypes.ACTION_LOGIN, null, null, null);
            ActionDAO.insert(action);
            new EmployeeView(user);
        }
    }

}
