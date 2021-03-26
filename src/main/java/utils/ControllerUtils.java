package utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.AccountClientDAO;
import dao.AccountDAO;
import dao.ClientDAO;
import dao.TransferDAO;
import model.Account;
import model.Action;
import model.Client;
import model.Transfer;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ControllerUtils {

    public static void createSwingErrorMessage(String message){
        JOptionPane.showMessageDialog(null,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void generateReport(String username, String startDate, String endDate, ArrayList<Action> actions) {
        String path = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a File to upload");

        int response = fileChooser.showSaveDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            path = selectedFile.getAbsolutePath();
        }

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(path + ".pdf"));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

        document.open();

        PdfPTable table = new PdfPTable(8);
        table.setTotalWidth(PageSize.A4.getWidth());
        table.setLockedWidth(true);
        PdfPCell cell = new PdfPCell(new Phrase(""));
        PdfPCell dateCell;
        PdfPCell timeCell;
        cell.setBackgroundColor(BaseColor.GRAY);

        table.addCell(new PdfPCell(new Phrase("Date")));
        table.addCell(new PdfPCell(new Phrase("Time")));
        table.addCell(new PdfPCell(new Phrase("Type")));
        table.addCell(new PdfPCell(new Phrase("Client name")));
        table.addCell(new PdfPCell(new Phrase("Account number")));
        table.addCell(new PdfPCell(new Phrase("Transferred from")));
        table.addCell(new PdfPCell(new Phrase("Transferred to")));
        table.addCell(new PdfPCell(new Phrase("Amount")));

        for (Action a : actions) {
            String actionType = a.getType();
            dateCell = new PdfPCell(new Phrase(new SimpleDateFormat("dd.MM.yyyy").format(a.getDate())));
            timeCell = new PdfPCell(new Phrase(a.getTime().toString()));
            switch (actionType) {
                case ActionTypes.ACTION_LOGIN:
                    table.addCell(dateCell);
                    table.addCell(timeCell);
                    table.addCell(new PdfPCell(new Phrase("Login")));
                    table.addCell(cell);
                    table.addCell(cell);
                    table.addCell(cell);
                    table.addCell(cell);
                    table.addCell(cell);
                    break;
                case ActionTypes.ACTION_TRANSFER:
                    table.addCell(dateCell);
                    table.addCell(timeCell);
                    table.addCell(new PdfPCell(new Phrase("Balance transfer")));
                    table.addCell(cell);
                    table.addCell(cell);
                    Transfer transfer = TransferDAO.findById(a.getIdTransfer());
                    Account accountFrom = AccountDAO.findById(transfer.getIdAccountFrom());
                    Client clientFrom = AccountClientDAO.getClient(accountFrom);
                    Account accountTo = AccountDAO.findById(transfer.getIdAccountTo());
                    Client clientTo = AccountClientDAO.getClient(accountTo);
                    if (accountFrom != null) {
                        if (clientFrom != null)
                            table.addCell(new PdfPCell(new Phrase(accountFrom.getNumber() + " / " + clientFrom.getName())));
                        else
                            table.addCell(new PdfPCell(new Phrase((accountFrom.getNumber() + " / " + " [DELETED CLIENT]"))));
                    }
                    else {
                        table.addCell("[DELETED ACCOUNT] ID: " + transfer.getIdAccountFrom());
                    }

                    if (accountTo != null) {
                        if (clientTo != null)
                            table.addCell(new PdfPCell(new Phrase(accountTo.getNumber() + " / " + clientTo.getName())));
                        else
                            table.addCell(new PdfPCell(new Phrase((accountTo.getNumber() + " / " + " [DELETED CLIENT]"))));
                    }
                    else
                        table.addCell( "[DELETED ACCOUNT] ID: " + transfer.getIdAccountTo());

                    table.addCell(Float.toString(transfer.getAmount()));
                    break;
                case ActionTypes.ACTION_INSERT_CLIENT:
                    table.addCell(dateCell);
                    table.addCell(timeCell);
                    table.addCell(new PdfPCell(new Phrase("Insert client")));
                    Client client = ClientDAO.findById(a.getIdClient());
                    if (client != null)
                        table.addCell(client.getName());
                    else
                        table.addCell(new PdfPCell(new Phrase("[DELETED CLIENT] ID: " + a.getIdClient())));
                    table.addCell(cell);
                    table.addCell(cell);
                    table.addCell(cell);
                    table.addCell(cell);
                    break;
                case ActionTypes.ACTION_EDIT_CLIENT:
                    table.addCell(dateCell);
                    table.addCell(timeCell);
                    table.addCell(new PdfPCell(new Phrase("Edit client")));
                    Client clientEdit = ClientDAO.findById(a.getIdClient());
                    if (clientEdit != null)
                        table.addCell(clientEdit.getName());
                    else
                        table.addCell(new PdfPCell(new Phrase("[DELETED CLIENT] ID: " + a.getIdClient())));
                    table.addCell(cell);
                    table.addCell(cell);
                    table.addCell(cell);
                    table.addCell(cell);
                    break;
                case ActionTypes.ACTION_DELETE_CLIENT:
                    table.addCell(dateCell);
                    table.addCell(timeCell);
                    table.addCell(new PdfPCell(new Phrase("Delete client")));
                    table.addCell(new PdfPCell(new Phrase("[DELETED CLIENT] ID:" + a.getIdClient())));
                    table.addCell(cell);
                    table.addCell(cell);
                    table.addCell(cell);
                    table.addCell(cell);
                    break;
                case ActionTypes.ACTION_INSERT_ACCOUNT:
                    table.addCell(dateCell);
                    table.addCell(timeCell);
                    table.addCell(new PdfPCell(new Phrase("Insert account")));
                    Account account = AccountDAO.findById(a.getIdAccount());
                    if (account != null) {
                        Client client1 = AccountClientDAO.getClient(account);
                        table.addCell(client1.getName());
                        table.addCell(account.getNumber());
                    }
                    else {
                        table.addCell(cell);
                        table.addCell(new PdfPCell(new Phrase("[DELETED ACCOUNT] ID: " + a.getIdAccount())));
                    }
                    table.addCell(cell);
                    table.addCell(cell);
                    table.addCell(cell);
                    break;
                case ActionTypes.ACTION_EDIT_ACCOUNT:
                    table.addCell(dateCell);
                    table.addCell(timeCell);
                    table.addCell(new PdfPCell(new Phrase("Edit account")));
                    Account accountEdit = AccountDAO.findById(a.getIdAccount());
                    if (accountEdit != null) {
                        Client client2 = AccountClientDAO.getClient(accountEdit);
                        table.addCell(client2.getName());
                        table.addCell(accountEdit.getNumber());
                    }
                    else {
                        table.addCell(cell);
                        table.addCell(new PdfPCell(new Phrase("[DELETED ACCOUNT] ID: " + a.getIdAccount())));
                    }
                    table.addCell(cell);
                    table.addCell(cell);
                    table.addCell(cell);
                    break;
                case ActionTypes.ACTION_DELETE_ACCOUNT:
                    table.addCell(dateCell);
                    table.addCell(timeCell);
                    table.addCell(new PdfPCell(new Phrase("Delete account")));
                    table.addCell(cell);
                    table.addCell(new PdfPCell(new Phrase("[DELETED ACCOUNT] ID: " + a.getIdAccount())));
                    table.addCell(cell);
                    table.addCell(cell);
                    table.addCell(cell);
                    break;
                case ActionTypes.ACTION_BILL_PAYMENT:
                    table.addCell(dateCell);
                    table.addCell(timeCell);
                    table.addCell(new PdfPCell(new Phrase("Bill payment")));
                    table.addCell(cell);
                    table.addCell(cell);
                    Transfer transferUtility = TransferDAO.findById(a.getIdTransfer());
                    Account accountFrom1 = AccountDAO.findById(transferUtility.getIdAccountFrom());
                    Client clientFrom1 = AccountClientDAO.getClient(accountFrom1);
                    Account accountTo1 = AccountDAO.findById(transferUtility.getIdAccountTo());
                    if (accountFrom1 != null) {
                        if (clientFrom1 != null)
                            table.addCell(new PdfPCell(new Phrase(accountFrom1.getNumber() + " / " + clientFrom1.getName())));
                        else
                            table.addCell(new PdfPCell(new Phrase((accountFrom1.getNumber() + " / " + " [DELETED CLIENT]"))));
                    }
                    else
                        table.addCell( "[DELETED ACCOUNT] ID: " + transferUtility.getIdAccountFrom());
                    if (accountTo1 != null)
                        table.addCell(new PdfPCell(new Phrase(accountTo1.getNumber() + " / " + accountTo1.getType())));
                    else
                        table.addCell(new PdfPCell(new Phrase("[DELETED]")));
                    table.addCell(Float.toString(transferUtility.getAmount()));
                    break;
            }
        }
        try {
            Chunk chunk = new Chunk("Report for user " + username + " for the period " + startDate + " - " + endDate);
            document.add(chunk);
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();

    }

}
