import javax.swing.*;
import java.awt.*;
import java.sql.*;


public class LoginFrame extends JFrame {

    public LoginFrame() {

        // Window settings
        setTitle("Online Exam System - Login");
        setSize(400, 350);
        setLocationRelativeTo(null); // Center
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Absolute layout (easy for beginners)

        // Title
        JLabel title = new JLabel("Online Exam System");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(80, 30, 300, 30);
        add(title);

        // Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(60, 90, 100, 25);
        add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(160, 90, 160, 25);
        add(userField);

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(60, 130, 100, 25);
        add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(160, 130, 160, 25);
        add(passField);

        // Login button
        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(140, 220, 100, 30);
        add(loginBtn);

        setVisible(true);
        loginBtn.addActionListener(e -> {

            String username = userField.getText();
            String password = passField.getText();

            try {
                Connection con = DBConnection.getConnection();

                String sql = "SELECT role FROM users WHERE username=? AND password=?";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, username);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String role = rs.getString("role");
                    this.dispose();

                    if (role.equals("ADMIN")) {
                        new AdminDashboard(username);
                    } else {
                        new StudentDashboard(username);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid login");
                }

                con.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });


    }

    // Main method to run this window
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        new LoginFrame();
    }
}
