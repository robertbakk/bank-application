package view;

import controller.ClientController;
import controller.TransferController;
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

public class BillView {

    private TransferController transferController;
    private ClientController clientController;
    private final User user;
    private final Client client;

    public JFrame frame;

    private JLabel lblBalance1 = new JLabel("Balance");
    private JLabel lblNumber1 = new JLabel("Account number");
    private JLabel lblType2 = new JLabel("Type");
    private JLabel lblNumber2 = new JLabel("Account number");
    private JLabel lblTo = new JLabel("TO");
    private JLabel lblAmount = new JLabel("Amount");

    private JTextField balance1;
    private JTextField number1;
    private JTextField type2;
    private JTextField number2;
    private JTextField amount;

    private String selectedNumber1;
    private String selectedBalance1;
    private String selectedNumber2;
    private String selectedType2;

    private final JButton btnConfirm = new JButton("Confirm");
    private final JButton btnCancel = new JButton("Cancel");

    private final Object[] row = new Object[2];
    private final JTable table = new JTable();
    private final DefaultTableModel model = new DefaultTableModel();
    private final Object[] row2 = new Object[2];
    private final JTable table2 = new JTable();
    private final DefaultTableModel model2 = new DefaultTableModel();
    private final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    public BillView(User user, Client client) {
        this.transferController = new TransferController();
        this.clientController = new ClientController();
        this.user = user;
        this.client = client;
        frame = new JFrame("Bank - Balance Transfer");
        initialize();
        updateTable();
        frame.setVisible(true);
    }

    private void updateTable() {
        model.setRowCount(0);
        model2.setRowCount(0);
        ArrayList<Account> utilitiesAccounts = clientController.getUtilitiesAccounts();
        ArrayList<Account> accounts = clientController.getAccounts(client);
        for (Account account : accounts) {
            row[0] = account.getNumber();
            row[1] = account.getBalance();
            model.addRow(row);
        }

        for (Account account : utilitiesAccounts) {
            row2[0] = account.getNumber();
            row2[1] = account.getType();
            model2.addRow(row2);
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

        for (int column = 0; column < table2.getColumnCount(); column++)
        {
            TableColumn tableColumn = table2.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < table2.getRowCount(); row++)
            {
                TableCellRenderer cellRenderer = table2.getCellRenderer(row, column);
                Component c = table2.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + table2.getIntercellSpacing().width;
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
        Object[] columns = {"Account number", "Balance"};
        Object[] columns2 = {"Account number", "Account type"};
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        table.setDefaultEditor(Object.class,null);
        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        model2.setColumnIdentifiers(columns2);
        table2.setModel(model2);
        table2.setDefaultEditor(Object.class,null);
        table2.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        table.setBackground(Color.LIGHT_GRAY);
        table.setForeground(Color.black);
        Font font = new Font("", 1,22);
        table.setFont(font);
        table.setRowHeight(30);

        table2.setBackground(Color.LIGHT_GRAY);
        table2.setForeground(Color.black);
        table2.setFont(font);
        table2.setRowHeight(30);

        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 0, 800, 200);

        JScrollPane pane2 = new JScrollPane(table2);
        pane2.setBounds(0, 0, 800, 200);

        balance1 = new JTextField(18);
        type2 = new JTextField(18);
        number1 = new JTextField(18);
        number2 = new JTextField(18);
        amount = new JTextField(18);
        number1.setEditable(false);
        number2.setEditable(false);
        balance1.setEditable(false);
        type2.setEditable(false);


        JLabel lblNewLabel = new JLabel("Enter the amount to be paid");
        lblNewLabel.setFont(new Font("Franklin Gothic Demi", Font.BOLD | Font.ITALIC, 16));

        JPanel panou = new JPanel(new BorderLayout());
        JPanel panel0 = new JPanel(new FlowLayout());
        JPanel panel1 = new JPanel(new FlowLayout());
        JPanel panel2 = new JPanel(new FlowLayout());
        JPanel panel3 = new JPanel(new FlowLayout());
        JPanel panel4 = new JPanel(new FlowLayout());
        JPanel panel5 = new JPanel(new FlowLayout());
        JPanel panel6 = new JPanel(new FlowLayout());
        JPanel panelMiddle = new JPanel(new GridLayout(8,1));
        JPanel panelConfirm = new JPanel(new FlowLayout());
        panel0.add(lblNewLabel);

        panel1.add(lblNumber1);
        panel1.add(number1);
        panel2.add(lblBalance1);
        panel2.add(balance1);
        panel3.add(lblTo);
        panel4.add(lblNumber2);
        panel4.add(number2);
        panel5.add(lblType2);
        panel5.add(type2);
        panel6.add(lblAmount);
        panel6.add(amount);
        panelConfirm.add(btnCancel);
        panelConfirm.add(btnConfirm);

        panelMiddle.add(panel0);
        panelMiddle.add(panel1);
        panelMiddle.add(panel2);
        panelMiddle.add(panel3);
        panelMiddle.add(panel4);
        panelMiddle.add(panel5);
        panelMiddle.add(panel6);
        panelMiddle.add(panelConfirm);

        panou.add(pane,BorderLayout.WEST);
        panou.add(panelMiddle,BorderLayout.CENTER);
        panou.add(pane2,BorderLayout.EAST);

        frame.add(panou);


        frame.setSize(1300,500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int j = table.getSelectedRow();
                selectedNumber1 = model.getValueAt(j, 0).toString();
                selectedBalance1 = model.getValueAt(j, 1).toString();
                number1.setText(selectedNumber1);
                balance1.setText(selectedBalance1);
            }
        });

        table2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int j = table2.getSelectedRow();
                selectedNumber2 = model2.getValueAt(j, 0).toString();
                selectedType2 = model2.getValueAt(j, 1).toString();
                number2.setText(selectedNumber2);
                type2.setText(selectedType2);
            }
        });

        btnCancel.addActionListener(e ->  {
            frame.dispose();
        });

        btnConfirm.addActionListener(e -> {
            if (transferController.transfer(user.getId(), number1.getText(), number2.getText(), amount.getText(), true) != -1) {
                updateTable();
                number1.setText("");
                balance1.setText("");
                type2.setText("");
                number2.setText("");
            }
            amount.setText("");
        });

    }

}
