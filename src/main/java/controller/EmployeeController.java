package controller;

import dao.ActionDAO;
import dao.ClientDAO;
import model.Action;
import model.Client;
import utils.ActionTypes;
import utils.ControllerUtils;
import utils.ErrorMessages;

import java.util.ArrayList;

public class EmployeeController {

    public EmployeeController() {
    }

    public int insert(String userId, String name, String address, String cnp) {
        try {
            Long.parseLong(cnp);
        } catch (NumberFormatException e) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.CNP_NOT_NUMBER);
            return -1;
        }
        Client client = new Client(name, address, cnp);
        if (ClientDAO.insert(client) == -1)
            return -1;
        Action action = new Action(userId, ActionTypes.ACTION_INSERT_CLIENT, client.getId(), null, null);
        ActionDAO.insert(action);
        return 0;
    }

    public void delete(String userId, String cnp) {
        Client client = ClientDAO.findByCNP(cnp);
        ClientDAO.delete(client);
        if (client != null) {
            Action action = new Action(userId, ActionTypes.ACTION_DELETE_CLIENT, client.getId(), null, null);
            ActionDAO.insert(action);
        }
    }

    public int update(String userId, String name, String address, String cnp) {
        try {
            Long.parseLong(cnp);
        } catch (NumberFormatException e) {
            ControllerUtils.createSwingErrorMessage(ErrorMessages.CNP_NOT_NUMBER);
            return -1;
        }
        Client client = new Client(name, address, cnp);
        if (ClientDAO.updateClient(client) == -1)
            return -1;
        Action action = new Action(userId, ActionTypes.ACTION_EDIT_CLIENT, ClientDAO.findByCNP(cnp).getId(), null, null);
        ActionDAO.insert(action);
        return 0;
    }

    public ArrayList<Client> getClients() {
        return ClientDAO.getClients();
    }

    public Client getClient (String cnp) {
        return ClientDAO.findByCNP(cnp);
    }

}
