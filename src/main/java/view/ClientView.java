package view;

import controller.ClientController;
import model.Account;
import model.Client;
import model.User;
import utils.AccountUtils;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ClientView {

    private ClientController clientController;
    private final User user;
    private final Client client;

    public JFrame frame;

    private JLabel lblType = new JLabel("Type");
    private JLabel lblBalance = new JLabel("Balance");
    private JLabel lblUpdatedType = new JLabel("New type");
    private JLabel lblUpdatedBalance = new JLabel("New balance");
    private JLabel lblUpdatedNumber = new JLabel("Account number");
    private JLabel lblCreationDate = new JLabel("Creation date");

    private JTextField balance;
    private JTextField updatedNumber;
    private JTextField updatedBalance;
    private JTextField creationDate;

    private String type;
    private String selectedNumber;
    private String selectedType;
    private String selectedBalance;

    private final JButton btnAddAccount = new JButton("Add account");
    private final JButton btnUpdateAccount = new JButton("Update account");
    private final JButton btnDeleteAccount = new JButton("Delete account");
    private final JButton btnConfirmAddAccount = new JButton("Confirm");
    private final JButton btnConfirmUpdateAccount = new JButton("Confirm");
    private final JButton btnCancel = new JButton("Cancel");
    private final JButton btnCancel2 = new JButton("Cancel");

    private final Object[] row = new Object[4];
    private final JTable table = new JTable();
    private final DefaultTableModel model = new DefaultTableModel();
    private final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private final CardLayout cardLayout = new CardLayout();

    public ClientView(User user, Client client) {
        this.clientController = new ClientController();
        this.user = user;
        this.client = client;
        frame = new JFrame("Bank - Client " + client.getName() + " accounts");
        initialize();
        updateTable();
        frame.setVisible(true);
    }

    private void updateTable() {
        model.setRowCount(0);
        ArrayList<Account> accounts = clientController.getAccounts(client);
        for (Account account : accounts) {
            row[0] = account.getNumber();
            row[1] = account.getType();
            row[2] = account.getBalance();
            row[3] = new SimpleDateFormat("dd.MM.yyyy").format(account.getCreationDate());
            model.addRow(row);
        }

        for (int column = 0; column < table.getColumnCount(); column++)
        {
            TableColumn tableColumn = table.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < table.getRowCount(); row++)
            {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Component c = table.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);

                if (preferredWidth >= maxWidth)
                {
                    preferredWidth = maxWidth;
                    break;
                }
            }
            tableColumn.setPreferredWidth(preferredWidth);
        }
    }

    private void initialize() {
        Object[] columns = {"Account number", "Type", "Balance", "Creation date"};
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        table.setDefaultEditor(Object.class,null);
        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        table.setBackground(Color.LIGHT_GRAY);
        table.setForeground(Color.black);
        Font font = new Font("",1,22);
        table.setFont(font);
        table.setRowHeight(30);

        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 0, 1000, 200);

        balance = new JTextField(10);

        updatedNumber = new JTextField(18);
        updatedBalance = new JTextField(10);
        creationDate = new JTextField(10);
        updatedNumber.setEditable(false);
        creationDate.setEditable(false);

        String[] types = AccountUtils.getTypes();
        JComboBox<String> typesList = new JComboBox<>(types);
        type = types[0];
        typesList.setSelectedItem(0);
        JComboBox<String> updatedTypesList = new JComboBox<>(types);

        JLabel lblNewLabel = new JLabel("Enter the new account's details");
        lblNewLabel.setFont(new Font("Franklin Gothic Demi", Font.BOLD | Font.ITALIC, 16));

        JLabel lblNewLabel2 = new JLabel("Enter the new account's details");
        lblNewLabel2.setFont(new Font("Franklin Gothic Demi", Font.BOLD | Font.ITALIC, 16));

        JPanel panou = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelButoane = new JPanel(new GridBagLayout());
        JPanel panel0 = new JPanel(new FlowLayout());
        JPanel panel1 = new JPanel(new FlowLayout());
        JPanel panel3 = new JPanel(new FlowLayout());
        JPanel panel4 = new JPanel(new FlowLayout());
        JPanel panel5 = new JPanel(new FlowLayout());
        JPanel panel6 = new JPanel(new FlowLayout());
        JPanel panel7 = new JPanel(new FlowLayout());
        JPanel panel8 = new JPanel(new FlowLayout());
        JPanel cardPanel = new JPanel(cardLayout);
        JPanel panelAdd = new JPanel(new GridLayout(4,1));
        JPanel panelUpdate = new JPanel(new GridLayout(6,1));
        JPanel panelConfirmAdd = new JPanel(new FlowLayout());
        JPanel panelConfirmUpdate = new JPanel(new FlowLayout());
        panel0.add(lblNewLabel);
        panel1.add(lblNewLabel2);

        panel3.add(lblType);
        panel3.add(typesList);
        panel4.add(lblBalance);
        panel4.add(balance);
        panel5.add(lblUpdatedNumber);
        panel5.add(updatedNumber);
        panel6.add(lblUpdatedType);
        panel6.add(updatedTypesList);
        panel7.add(lblUpdatedBalance);
        panel7.add(updatedBalance);
        panel8.add(lblCreationDate);
        panel8.add(creationDate);

        panelConfirmAdd.add(btnCancel);
        panelConfirmAdd.add(btnConfirmAddAccount);
        panelConfirmUpdate.add(btnCancel2);
        panelConfirmUpdate.add(btnConfirmUpdateAccount);

        panelAdd.add(panel0);
        panelAdd.add(panel3);
        panelAdd.add(panel4);
        panelAdd.add(panelConfirmAdd);

        panelUpdate.add(panel1);
        panelUpdate.add(panel5);
        panelUpdate.add(panel6);
        panelUpdate.add(panel7);
        panelUpdate.add(panel8);
        panelUpdate.add(panelConfirmUpdate);

        panelButoane.add(btnAddAccount);
        panelButoane.add(btnUpdateAccount);
        panelButoane.add(btnDeleteAccount);
        cardPanel.add(panelAdd, "addpanel");
        cardPanel.add(panelUpdate, "updatepanel");
        panel.add(panelButoane,BorderLayout.NORTH);
        panel.add(cardPanel,BorderLayout.SOUTH);
        panou.add(pane,BorderLayout.CENTER);
        panou.add(panel,BorderLayout.EAST);

        frame.add(panou);

        cardPanel.setVisible(false);

        frame.setSize(1100,300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int j = table.getSelectedRow();
                selectedNumber = model.getValueAt(j, 0).toString();
                selectedType = model.getValueAt(j, 1).toString();
                selectedBalance = model.getValueAt(j, 2).toString();
                updatedNumber.setText(model.getValueAt(j, 0).toString());
                updatedTypesList.setSelectedItem(model.getValueAt(j, 1).toString());
                updatedBalance.setText(model.getValueAt(j, 2).toString());
                creationDate.setText(model.getValueAt(j, 3).toString());
            }
        });

        typesList.addItemListener(arg0 ->  {
            type = typesList.getSelectedItem().toString();
        });

        updatedTypesList.addItemListener(arg0 ->  {
            selectedType = updatedTypesList.getSelectedItem().toString();
        });

        btnCancel.addActionListener(e ->  {
            cardPanel.setVisible(false);
            balance.setText("");
        });

        btnCancel2.addActionListener(e ->  {
            cardPanel.setVisible(false);
        });

        btnAddAccount.addActionListener(e ->  {
            cardPanel.setVisible(true);
            cardLayout.show(cardPanel,"addpanel");
        });

        btnUpdateAccount.addActionListener(e ->  {
            cardPanel.setVisible(true);
            cardLayout.show(cardPanel,"updatepanel");
        });

        btnDeleteAccount.addActionListener(e -> {
            cardPanel.setVisible(false);
            clientController.delete(user.getId(), client.getId(), selectedNumber);
            updateTable();
            updatedNumber.setText("");
            updatedBalance.setText("");
            selectedNumber = "";
            selectedType = "";
            selectedBalance = "";
        });

        btnConfirmAddAccount.addActionListener(e -> {
            clientController.insert(user.getId(), type, balance.getText(), client);
            cardPanel.setVisible(false);
            updateTable();
            balance.setText("");

        });

        btnConfirmUpdateAccount.addActionListener(e -> {
            if (clientController.update(user.getId(), client.getId(), updatedNumber.getText(),selectedType,updatedBalance.getText()) != -1) {
                cardPanel.setVisible(false);
                updateTable();
            } else {
                updatedTypesList.setSelectedItem(selectedType);
                updatedBalance.setText(selectedBalance);
            }
        });
    }

}
