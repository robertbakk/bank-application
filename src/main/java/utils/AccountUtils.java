package utils;

import model.Account;

import java.util.ArrayList;

public class AccountUtils {

    public static ArrayList<Account> getUtilityAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(new Account("UTILITIES.ELEC", "RO21BANK28491823959318", "Electricity account", 0));
        accounts.add(new Account("UTILITIES.INTERNET", "RO21BANK34819384938491", "Internet account", 0));
        accounts.add(new Account("UTILITIES.GAS", "RO21BANK48362859374857", "Gas account", 0));
        return accounts;
    }

    public static String[] getTypes() {
        return new String[]{"Savings account", "Current account", "Reccuring deposit account", "Fixed deposit account", "Demat account"};
    }

}
