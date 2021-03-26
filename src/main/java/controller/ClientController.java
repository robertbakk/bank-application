package controller;

import dao.AccountClientDAO;
import dao.AccountDAO;
import dao.ActionDAO;
import model.Account;
import model.Action;
import model.Client;
import utils.ActionTypes;
import utils.ControllerUtils;
import utils.ErrorMessages;
import java.util.ArrayList;

public class ClientController {

    public ClientController() {
    }

    public void insert(String userId, String type, String balance, Client client) {
        try {
            Account account = new Account(type, Float.parseFloat(balance));
            AccountDAO.insert(account);
            AccountClientDAO.insert(account, client);
            Action action = new Action(userId, ActionTypes.ACTION_INSERT_ACCOUNT, client.getId(), account.getId(), null);
            ActionDAO.insert(action);
        } catch (NumberFormatException ex) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.BALANCE_NOT_NUMBER);
        }

    }

    public void delete(String userId, String clientId, String number) {
        Account account = AccountDAO.findByNumber(number);
        AccountDAO.delete(account);
        AccountClientDAO.delete(account);
        if (account != null) {
            Action action = new Action(userId, ActionTypes.ACTION_DELETE_ACCOUNT, clientId, account.getId(), null);
            ActionDAO.insert(action);
        }
    }

    public int update(String userId, String clientId, String number, String type, String balance) {
        Account account;
        try {
            account = new Account(number, type, Float.parseFloat(balance));
        } catch (NumberFormatException ex) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.BALANCE_NOT_NUMBER);
            return -1;
        }
        if (Float.parseFloat(balance) < 0) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.BALANCE_NEGATIVE);
            return -1;
        }
        if (AccountDAO.updateAccount(account) == -1) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.NO_ACCOUNT_SELECTED);
            return -1;
        }
        Action action = new Action(userId, ActionTypes.ACTION_EDIT_ACCOUNT, clientId, AccountDAO.findByNumber(number).getId(), null);
        ActionDAO.insert(action);
        return 0;
    }

    public ArrayList<Account> getAccounts (Client client) {
        return AccountClientDAO.getAccounts(client);
    }

    public ArrayList<Account> getUtilitiesAccounts () {
        return AccountDAO.getUtilitiesAccounts();
    }

}
