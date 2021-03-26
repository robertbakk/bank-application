package start;

import dao.AccountDAO;
import dao.UserDAO;
import model.Account;
import model.User;
import utils.AccountUtils;
import view.LoginView;

public class Start {

    public static void main(String[] args) {
        new LoginView();
        if (UserDAO.findByUsername("admin") == null) {
            UserDAO.insert(new User("1", "admin", "1234", "admin", true));
        }

        for (Account account : AccountUtils.getUtilityAccounts()) {
            if (AccountDAO.findById(account.getId()) == null) {
                AccountDAO.insert(account);
            }
        }
    }
}
