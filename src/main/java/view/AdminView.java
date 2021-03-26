package view;

import controller.AdminController;
import model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class AdminView {

    private final User user;

    private AdminController adminController;

    public JFrame frame;

    private JLabel lblUsername = new JLabel("Username");
    private JLabel lblPassword = new JLabel("Password");
    private JLabel lblName = new JLabel("Name");
    private JLabel lblAdmin = new JLabel("Admin");
    private JLabel lblEmployee = new JLabel("Employee");
    private JLabel lblUpdatedUsername = new JLabel("New username");
    private JLabel lblUpdatedPassword = new JLabel("New password");
    private JLabel lblUpdatedName = new JLabel("New name");
    private JLabel lblUpdatedAdmin = new JLabel("Admin");
    private JLabel lblUpdatedEmployee = new JLabel("Employee");
    private JLabel lblReportUsername = new JLabel("Username");
    private JLabel lblStartDate = new JLabel("Start date (dd.MM.yyyy)");
    private JLabel lblEndDate = new JLabel("End date (dd.MM.yyyy)");

    private JTextField username;
    private JTextField name;
    private JTextField updatedUsername;
    private JTextField updatedName;
    private JTextField startDate;
    private JTextField endDate;
    private JTextField reportUsername;

    private JPasswordField password;
    private JPasswordField updatedPassword;

    private String selectedUsername;
    private String selectedName;

    private boolean selectedAdmin;
    private boolean isAdmin;

    private JRadioButton admin;
    private JRadioButton employee;
    private JRadioButton updatedAdmin;
    private JRadioButton updatedEmployee;

    private final JButton btnAddUser = new JButton("Add user");
    private final JButton btnUpdateUser = new JButton("Update user");
    private final JButton btnDeleteUser = new JButton("Delete user");
    private final JButton btnGenerateReport = new JButton("Generate report");
    private final JButton btnConfirmAdd = new JButton("Confirm");
    private final JButton btnConfirmUpdate = new JButton("Confirm");
    private final JButton btnConfirmReport = new JButton("Confirm");
    private final JButton btnCancel = new JButton("Cancel");
    private final JButton btnCancel2 = new JButton("Cancel");
    private final JButton btnCancel3 = new JButton("Cancel");

    private final Object[] row = new Object[3];
    private final JTable table = new JTable();
    private final DefaultTableModel model = new DefaultTableModel();
    private final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private final CardLayout cardLayout = new CardLayout();

    public AdminView(User user) {
        this.adminController = new AdminController();
        this.user = user;
        frame = new JFrame("Bank - Admin");
        initialize();
        updateTable();
        frame.setVisible(true);
    }

    private void updateTable() {
        model.setRowCount(0);
        ArrayList<User> users = adminController.getUsers();
        for (User user : users) {
            row[0] = user.getUsername();
            row[1] = user.getName();
            row[2] = user.isAdmin() ? "Yes" : "No";
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
        Object[] columns = {"Username", "Name", "Admin"};
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


        username = new JTextField(10);
        password = new JPasswordField(10);
        name = new JTextField(10);
        startDate = new JTextField(10);
        endDate = new JTextField(10);
        reportUsername = new JTextField(10);
        reportUsername.setEditable(false);
        admin = new JRadioButton();
        employee = new JRadioButton();
        updatedAdmin = new JRadioButton();
        updatedEmployee = new JRadioButton();
        ButtonGroup b1 = new ButtonGroup();
        ButtonGroup b2 = new ButtonGroup();
        b1.add(admin);
        b1.add(employee);
        b2.add(updatedAdmin);
        b2.add(updatedEmployee);
        employee.setSelected(true);
        updatedEmployee.setSelected(true);

        updatedUsername = new JTextField(10);
        updatedPassword = new JPasswordField(10);
        updatedName = new JTextField(10);

        JLabel lblNewLabel = new JLabel("Enter the new user's details");
        lblNewLabel.setFont(new Font("Franklin Gothic Demi", Font.BOLD | Font.ITALIC, 16));

        JLabel lblNewLabel2 = new JLabel("Enter the new user's details");
        lblNewLabel2.setFont(new Font("Franklin Gothic Demi", Font.BOLD | Font.ITALIC, 16));

        JLabel lblNewLabel3 = new JLabel("Enter the start date and the end date");
        lblNewLabel2.setFont(new Font("Franklin Gothic Demi", Font.BOLD | Font.ITALIC, 16));

        JPanel panou = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelButoane = new JPanel(new GridBagLayout());
        JPanel panel0 = new JPanel(new FlowLayout());
        JPanel panel1 = new JPanel(new FlowLayout());
        JPanel panel2 = new JPanel(new FlowLayout());
        JPanel panel3 = new JPanel(new FlowLayout());
        JPanel panel4 = new JPanel(new FlowLayout());
        JPanel panel11 = new JPanel(new FlowLayout());
        JPanel panel5 = new JPanel(new FlowLayout());
        JPanel panel6 = new JPanel(new FlowLayout());
        JPanel panel7 = new JPanel(new FlowLayout());
        JPanel panel8 = new JPanel(new FlowLayout());
        JPanel panel9 = new JPanel(new FlowLayout());
        JPanel panel10 = new JPanel(new FlowLayout());
        JPanel panel12 = new JPanel(new FlowLayout());
        JPanel panel13 = new JPanel(new FlowLayout());
        JPanel cardPanel = new JPanel(cardLayout);
        JPanel panelAdd = new JPanel(new GridLayout(6,1));
        JPanel panelUpdate = new JPanel(new GridLayout(6,1));
        JPanel panelReport = new JPanel(new GridLayout(5,1));
        JPanel panelConfirmAdd = new JPanel(new FlowLayout());
        JPanel panelConfirmUpdate = new JPanel(new FlowLayout());
        JPanel panelConfirmReport = new JPanel(new FlowLayout());
        panel0.add(lblNewLabel);
        panel1.add(lblNewLabel2);
        panel9.add(lblNewLabel3);
        panel2.add(lblUsername);
        panel2.add(username);
        panel3.add(lblPassword);
        panel3.add(password);
        panel4.add(lblName);
        panel4.add(name);
        panel11.add(lblAdmin);
        panel11.add(admin);
        panel11.add(lblEmployee);
        panel11.add(employee);
        panel5.add(lblUpdatedUsername);
        panel5.add(updatedUsername);
        panel6.add(lblUpdatedPassword);
        panel6.add(updatedPassword);
        panel7.add(lblUpdatedName);
        panel7.add(updatedName);
        panel8.add(lblUpdatedAdmin);
        panel8.add(updatedAdmin);
        panel8.add(lblUpdatedEmployee);
        panel8.add(updatedEmployee);
        panel10.add(lblReportUsername);
        panel10.add(reportUsername);
        panel12.add(lblStartDate);
        panel12.add(startDate);
        panel13.add(lblEndDate);
        panel13.add(endDate);

        panelConfirmAdd.add(btnCancel);
        panelConfirmAdd.add(btnConfirmAdd);
        panelConfirmUpdate.add(btnCancel2);
        panelConfirmUpdate.add(btnConfirmUpdate);
        panelConfirmReport.add(btnCancel3);
        panelConfirmReport.add(btnConfirmReport);

        panelAdd.add(panel0);
        panelAdd.add(panel2);
        panelAdd.add(panel3);
        panelAdd.add(panel4);
        panelAdd.add(panel11);
        panelAdd.add(panelConfirmAdd);

        panelUpdate.add(panel1);
        panelUpdate.add(panel5);
        panelUpdate.add(panel6);
        panelUpdate.add(panel7);
        panelUpdate.add(panel8);
        panelUpdate.add(panelConfirmUpdate);

        panelReport.add(panel9);
        panelReport.add(panel10);
        panelReport.add(panel12);
        panelReport.add(panel13);
        panelReport.add(panelConfirmReport);

        panelButoane.add(btnAddUser);
        panelButoane.add(btnUpdateUser);
        panelButoane.add(btnDeleteUser);
        panelButoane.add(btnGenerateReport);
        cardPanel.add(panelAdd, "addpanel");
        cardPanel.add(panelUpdate, "updatepanel");
        cardPanel.add(panelReport, "reportpanel");
        panel.add(panelButoane,BorderLayout.NORTH);
        panel.add(cardPanel,BorderLayout.SOUTH);
        panou.add(pane,BorderLayout.CENTER);
        panou.add(panel,BorderLayout.EAST);

        frame.add(panou);

        cardPanel.setVisible(false);

        frame.setSize(1200,300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int j = table.getSelectedRow();
                selectedUsername = model.getValueAt(j, 0).toString();
                selectedName = model.getValueAt(j, 1).toString();
                selectedAdmin = model.getValueAt(j, 2).toString().equals("Yes");
                updatedUsername.setText(model.getValueAt(j, 0).toString());
                updatedName.setText(model.getValueAt(j, 1).toString());
                updatedPassword.setText(adminController.getPassword(updatedUsername.getText()));
                reportUsername.setText(selectedUsername);
                if (selectedAdmin)
                    updatedAdmin.setSelected(true);
                else updatedEmployee.setSelected(true);
            }
        });

        btnCancel.addActionListener(e ->  {
            cardPanel.setVisible(false);
            username.setText("");
            password.setText("");
            name.setText("");
        });

        btnCancel2.addActionListener(e ->  {
            cardPanel.setVisible(false);
        });

        btnCancel3.addActionListener(e ->  {
            cardPanel.setVisible(false);
            startDate.setText("");
            endDate.setText("");
        });

        btnAddUser.addActionListener(e ->  {
            cardPanel.setVisible(true);
            cardLayout.show(cardPanel,"addpanel");
        });

        admin.addActionListener(e ->  {
            isAdmin = true;
        });

        employee.addActionListener(e ->  {
            isAdmin = false;
        });

        updatedAdmin.addActionListener(e ->  {
            selectedAdmin = true;
        });

        updatedEmployee.addActionListener(e ->  {
            selectedAdmin = false;
        });

        btnUpdateUser.addActionListener(e ->  {
            cardPanel.setVisible(true);
            cardLayout.show(cardPanel,"updatepanel");
        });

        btnGenerateReport.addActionListener(e ->  {
            cardPanel.setVisible(true);
            cardLayout.show(cardPanel,"reportpanel");
        });

        btnDeleteUser.addActionListener(e -> {
            cardPanel.setVisible(false);
            if (adminController.delete(selectedUsername,this.user) == 0)
                updateTable();
            updatedUsername.setText("");
            updatedPassword.setText("");
            updatedName.setText("");
            reportUsername.setText("");
            selectedUsername = "";
            selectedName = "";
        });

        btnConfirmAdd.addActionListener(e -> {
            if (adminController.insert(username.getText(), String.valueOf(password.getPassword()), name.getText(), isAdmin) != -1) {
                cardPanel.setVisible(false);
                updateTable();
                username.setText("");
                password.setText("");
                name.setText("");
            }
        });

        btnConfirmUpdate.addActionListener(e -> {
            if (adminController.update(selectedUsername, updatedUsername.getText(), String.valueOf(updatedPassword.getPassword()), updatedName.getText(), selectedAdmin) != -1) {
                cardPanel.setVisible(false);
                updateTable();
                reportUsername.setText(updatedUsername.getText());
                selectedUsername = updatedUsername.getText();
            } else {
                updatedUsername.setText(selectedUsername);
                updatedName.setText(selectedName);
                reportUsername.setText(updatedUsername.getText());
                if (selectedUsername != null && !selectedName.isEmpty())
                    updatedPassword.setText(adminController.getPassword(selectedUsername));
            }

        });

        btnConfirmReport.addActionListener(e -> {
            if (adminController.generateReport(reportUsername.getText(), startDate.getText(), endDate.getText()) != -1) {
                cardPanel.setVisible(false);
                updateTable();
                startDate.setText("");
                endDate.setText("");
                reportUsername.setText("");
            }
        });

    }

}
