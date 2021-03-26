package controller;

import dao.AccountDAO;
import dao.ActionDAO;
import dao.TransferDAO;
import model.Account;
import model.Action;
import model.Transfer;
import utils.ActionTypes;
import utils.ControllerUtils;
import utils.ErrorMessages;

import java.util.ArrayList;

public class TransferController {

    public ArrayList<Account> getAccounts() {
        return AccountDAO.getAccounts();
    }

    public int transfer(String idUser, String number1, String number2, String amount, boolean billPayment) {
        if (number1.equals(number2)) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.SAME_ACCOUNT_TRANSFER);
            return -1;
        }

        float am;

        try {
            am = Float.parseFloat(amount);
        } catch (NumberFormatException ex) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.AMOUNT_NOT_NUMBER);
            return -1;
        }

        if (am <= 0) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.NOT_POSITIVE_AMOUNT);
            return -1;
        }

        Account account1 = AccountDAO.findByNumber(number1);

        if (account1.getBalance() - am < 0) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.INSUFFICIENT_FUNDS);
            return -1;
        }
        Account account2 = AccountDAO.findByNumber(number2);
        account1.setBalance(account1.getBalance() - am);
        account2.setBalance(account2.getBalance() + am);
        AccountDAO.updateAccount(account1);
        AccountDAO.updateAccount(account2);
        Transfer transfer = new Transfer(idUser, account1.getId(), account2.getId(), am);
        TransferDAO.insert(transfer);
        Action action;
        if (billPayment)
            action = new Action(idUser, ActionTypes.ACTION_BILL_PAYMENT, null, null, transfer.getId());
        else
            action = new Action(idUser, ActionTypes.ACTION_TRANSFER, null, null, transfer.getId());
        ActionDAO.insert(action);
        return 0;
    }

}
