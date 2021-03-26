package view;

import controller.ClientController;
import controller.EmployeeController;
import model.Account;
import model.Client;
import model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class EmployeeView {

    private EmployeeController employeeController;
    private ClientController clientController;
    private final User user;

    public JFrame frame;

    private JLabel lblName = new JLabel("Name");
    private JLabel lblCNP = new JLabel("CNP");
    private JLabel lblAddress = new JLabel("Address");
    private JLabel lblUpdatedName = new JLabel("New name");
    private JLabel lblUpdatedCNP = new JLabel("CNP");
    private JLabel lblUpdatedAddress = new JLabel("New address");

    private JTextField name;
    private JTextField address;
    private JTextField cnp;
    private JTextField updatedName;
    private JTextField updatedAddress;
    private JTextField updatedCNP;

    private String selectedName;
    private String selectedCNP;
    private String selectedAddress;

    private final JButton btnAddClient = new JButton("Add client");
    private final JButton btnUpdateClient = new JButton("Update client");
    private final JButton btnDeleteClient = new JButton("Delete client");
    private final JButton btnAccounts = new JButton("View accounts");
    private final JButton btnBills = new JButton("Pay bills");
    private final JButton btnTransfer = new JButton("Balance transfer");
    private final JButton btnConfirmAddClient = new JButton("Confirm");
    private final JButton btnConfirmUpdateClient = new JButton("Confirm");
    private final JButton btnCancel = new JButton("Cancel");
    private final JButton btnCancel2 = new JButton("Cancel");

    private final Object[] row = new Object[3];
    private final JTable table = new JTable();
    private final DefaultTableModel model = new DefaultTableModel();
    private final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private final CardLayout cardLayout = new CardLayout();

    public EmployeeView(User user) {
        this.employeeController = new EmployeeController();
        this.clientController = new ClientController();
        this.user = user;
        frame = new JFrame("Bank - Employee");
        initialize();
        updateTable();
        frame.setVisible(true);
    }

    private void updateTable() {
        model.setRowCount(0);
        ArrayList<Client> clients = employeeController.getClients();
        for (Client client : clients) {
            row[0] = client.getName();
            row[1] = client.getAddress();
            row[2] = client.getCnp();
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
        Object[] columns = {"Name", "Address", "CNP"};
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


        name = new JTextField(10);
        address = new JTextField(10);
        cnp = new JTextField(10);

        updatedName = new JTextField(10);
        updatedAddress = new JTextField(10);
        updatedCNP = new JTextField(10);
        updatedCNP.setEditable(false);

        JLabel lblNewLabel = new JLabel("Enter the new client's details");
        lblNewLabel.setFont(new Font("Franklin Gothic Demi", Font.BOLD | Font.ITALIC, 16));

        JLabel lblNewLabel2 = new JLabel("Enter the new client's details");
        lblNewLabel2.setFont(new Font("Franklin Gothic Demi", Font.BOLD | Font.ITALIC, 16));

        JPanel panou = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelButoane = new JPanel(new GridBagLayout());
        JPanel panel0 = new JPanel(new FlowLayout());
        JPanel panel1 = new JPanel(new FlowLayout());
        JPanel panel2 = new JPanel(new FlowLayout());
        JPanel panel3 = new JPanel(new FlowLayout());
        JPanel panel4 = new JPanel(new FlowLayout());
        JPanel panel5 = new JPanel(new FlowLayout());
        JPanel panel6 = new JPanel(new FlowLayout());
        JPanel panel7 = new JPanel(new FlowLayout());
        JPanel cardPanel = new JPanel(cardLayout);
        JPanel panelAdd = new JPanel(new GridLayout(5,1));
        JPanel panelUpdate = new JPanel(new GridLayout(5,1));
        JPanel panelConfirmAdd = new JPanel(new FlowLayout());
        JPanel panelConfirmUpdate = new JPanel(new FlowLayout());
        panel0.add(lblNewLabel);
        panel1.add(lblNewLabel2);
        panel2.add(lblName);
        panel2.add(name);
        panel3.add(lblAddress);
        panel3.add(address);
        panel4.add(lblCNP);
        panel4.add(cnp);
        panel5.add(lblUpdatedName);
        panel5.add(updatedName);
        panel6.add(lblUpdatedAddress);
        panel6.add(updatedAddress);
        panel7.add(lblUpdatedCNP);
        panel7.add(updatedCNP);

        panelConfirmAdd.add(btnCancel);
        panelConfirmAdd.add(btnConfirmAddClient);
        panelConfirmUpdate.add(btnCancel2);
        panelConfirmUpdate.add(btnConfirmUpdateClient);

        panelAdd.add(panel0);
        panelAdd.add(panel2);
        panelAdd.add(panel3);
        panelAdd.add(panel4);
        panelAdd.add(panelConfirmAdd);

        panelUpdate.add(panel1);
        panelUpdate.add(panel5);
        panelUpdate.add(panel6);
        panelUpdate.add(panel7);
        panelUpdate.add(panelConfirmUpdate);

        panelButoane.add(btnAddClient);
        panelButoane.add(btnUpdateClient);
        panelButoane.add(btnDeleteClient);
        panelButoane.add(btnAccounts);
        panelButoane.add(btnBills);
        panelButoane.add(btnTransfer);
        cardPanel.add(panelAdd, "addpanel");
        cardPanel.add(panelUpdate, "updatepanel");
        panel.add(panelButoane,BorderLayout.NORTH);
        panel.add(cardPanel,BorderLayout.SOUTH);
        panou.add(pane,BorderLayout.CENTER);
        panou.add(panel,BorderLayout.EAST);

        frame.add(panou);

        cardPanel.setVisible(false);

        frame.setSize(1200,250);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int j = table.getSelectedRow();
                selectedName = model.getValueAt(j, 0).toString();
                selectedAddress = model.getValueAt(j, 1).toString();
                selectedCNP = model.getValueAt(j, 2).toString();
                updatedName.setText(model.getValueAt(j, 0).toString());
                updatedAddress.setText(model.getValueAt(j, 1).toString());
                updatedCNP.setText(model.getValueAt(j, 2).toString());
            }
        });

        btnCancel.addActionListener(e ->  {
            cardPanel.setVisible(false);
            name.setText("");
            address.setText("");
            cnp.setText("");
        });

        btnCancel2.addActionListener(e ->  {
            cardPanel.setVisible(false);
        });

        btnAddClient.addActionListener(e ->  {
            cardPanel.setVisible(true);
            cardLayout.show(cardPanel,"addpanel");
        });

        btnUpdateClient.addActionListener(e ->  {
            cardPanel.setVisible(true);
            cardLayout.show(cardPanel,"updatepanel");
        });

        btnDeleteClient.addActionListener(e -> {
            cardPanel.setVisible(false);
            Client client = employeeController.getClient(selectedCNP);
            if (client != null) {
                ArrayList<Account> accounts = clientController.getAccounts(client);
                for (Account a : accounts)
                    clientController.delete(user.getId(), client.getId(), a.getNumber());
                employeeController.delete(user.getId(), selectedCNP);
            }
            updateTable();
            updatedName.setText("");
            updatedAddress.setText("");
            updatedCNP.setText("");
            selectedName = "";
            selectedAddress = "";
            selectedCNP = "";
        });

        btnAccounts.addActionListener(e -> {
            cardPanel.setVisible(false);
            Client client = employeeController.getClient(selectedCNP);
            if (client != null)
                new ClientView(user, client);
            updateTable();
            updatedName.setText("");
            updatedAddress.setText("");
            updatedCNP.setText("");
            selectedName = "";
            selectedAddress = "";
            selectedCNP = "";
        });

        btnBills.addActionListener(e -> {
            cardPanel.setVisible(false);
            Client client = employeeController.getClient(selectedCNP);
            if (client != null)
                new BillView(user, client);
            updateTable();
            updatedName.setText("");
            updatedAddress.setText("");
            updatedCNP.setText("");
            selectedName = "";
            selectedAddress = "";
            selectedCNP = "";
        });

        btnTransfer.addActionListener(e -> {
            cardPanel.setVisible(false);
            new TransferView(user);
            updateTable();
            updatedName.setText("");
            updatedAddress.setText("");
            updatedCNP.setText("");
            selectedName = "";
            selectedAddress = "";
            selectedCNP = "";
        });

        btnConfirmAddClient.addActionListener(e -> {
            if (employeeController.insert(user.getId(), name.getText(), address.getText(), cnp.getText()) != -1) {
                cardPanel.setVisible(false);
                updateTable();
                name.setText("");
                address.setText("");
                cnp.setText("");
            }
        });

        btnConfirmUpdateClient.addActionListener(e -> {
            if (employeeController.update(user.getId(), updatedName.getText(),updatedAddress.getText(),updatedCNP.getText()) != -1) {
                cardPanel.setVisible(false);
                updateTable();
            } else {
                updatedName.setText(selectedName);
                updatedAddress.setText(selectedAddress);
                updatedCNP.setText(selectedCNP);
            }
        });
    }

}
