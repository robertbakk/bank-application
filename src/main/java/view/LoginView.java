package view;

import controller.LoginController;
import javax.swing.*;
import java.awt.*;

public class LoginView {

    private LoginController loginController;

    public JFrame frame;
    private JTextField username;
    private JPasswordField password;
    private final JButton btnExit = new JButton("Exit");
    private final JButton btnLogin = new JButton("Login");
    private final JLabel lblUsername = new JLabel("Username");
    private final JLabel lblPassword = new JLabel("Password");
    private final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    public LoginView() {
        this.loginController = new LoginController();
        frame = new JFrame("Bank - Login");
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {

        username = new JTextField(10);
        password = new JPasswordField(10);

        JLabel lblNewLabel = new JLabel("Enter your login credentials");
        lblNewLabel.setFont(new Font("Franklin Gothic Demi", Font.BOLD | Font.ITALIC, 16));

        JPanel panel = new JPanel(new GridLayout(4,1));
        JPanel panel1 = new JPanel(new GridBagLayout());
        JPanel panel2 = new JPanel(new FlowLayout());
        JPanel panel3 = new JPanel(new FlowLayout());
        JPanel panel4 = new JPanel(new FlowLayout());

        panel1.add(lblNewLabel);
        panel2.add(lblUsername);
        panel2.add(username);
        panel3.add(lblPassword);
        panel3.add(password);
        panel4.add(btnExit);
        panel4.add(btnLogin);
        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);
        panel.add(panel4);
        frame.add(panel);

        frame.setSize(300,300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        btnLogin.addActionListener( e -> {
            loginController.login(username.getText(), String.valueOf(password.getPassword()));
            username.setText("");
            password.setText("");
        });

        btnExit.addActionListener( e -> {
            frame.setVisible(false);
            frame.dispose();
        });

    }

}
