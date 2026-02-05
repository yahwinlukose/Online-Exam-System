import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class StudentProfileFrame extends JFrame {

    public StudentProfileFrame(String username) {

        setTitle("My Profile");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Set background
        getContentPane().setBackground(new Color(240, 242, 245));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(52, 152, 219));
        titlePanel.setPreferredSize(new Dimension(500, 60));
        titlePanel.setLayout(new GridBagLayout());

        JLabel title = new JLabel("Student Profile");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        titlePanel.add(title);

        add(titlePanel, BorderLayout.NORTH);

        // Profile Panel
        JPanel profilePanel = new JPanel();
        profilePanel.setBackground(Color.WHITE);
        profilePanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        profilePanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel nameLabel = new JLabel();
        JLabel deptLabel = new JLabel();
        JLabel emailLabel = new JLabel();

        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        deptLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        gbc.gridx = 0;
        gbc.gridy = 0;
        profilePanel.add(nameLabel, gbc);

        gbc.gridy = 1;
        profilePanel.add(deptLabel, gbc);

        gbc.gridy = 2;
        profilePanel.add(emailLabel, gbc);

        add(profilePanel, BorderLayout.CENTER);

        loadProfile(username, nameLabel, deptLabel, emailLabel);

        setVisible(true);
    }

    void loadProfile(String username, JLabel name, JLabel dept, JLabel email) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM students WHERE username=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                name.setText("Name: " + rs.getString("name"));
                dept.setText("Department: " + rs.getString("department"));
                email.setText("Email: " + rs.getString("email"));
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
