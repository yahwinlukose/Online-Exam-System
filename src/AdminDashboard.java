import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminDashboard extends JFrame {

    JTable table;
    DefaultTableModel model;

    public AdminDashboard(String username) {

        setTitle("Admin Dashboard");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Set background
        getContentPane().setBackground(new Color(240, 242, 245));

        // Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(41, 128, 185));
        titlePanel.setPreferredSize(new Dimension(800, 70));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel("Welcome Admin: " + username);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        titlePanel.add(title, BorderLayout.WEST);

        add(titlePanel, BorderLayout.NORTH);

        // Table Panel
        model = new DefaultTableModel();
        model.addColumn("Username");
        model.addColumn("Score");
        model.addColumn("Total");
        model.addColumn("Date");

        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(52, 152, 219));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(sp, BorderLayout.CENTER);

        loadResults();

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(240, 242, 245));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton addBtn = new JButton("Add Question");
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        addBtn.setBackground(new Color(46, 204, 113));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        addBtn.setPreferredSize(new Dimension(150, 35));
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addBtn.addActionListener(e -> new AddQuestionFrame());
        buttonPanel.add(addBtn);

        JButton publishBtn = new JButton("Publish Results");
        publishBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        publishBtn.setBackground(new Color(155, 89, 182));
        publishBtn.setForeground(Color.WHITE);
        publishBtn.setFocusPainted(false);
        publishBtn.setPreferredSize(new Dimension(150, 35));
        publishBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        publishBtn.addActionListener(e -> {
            new PublishResultsFrame();
        });
        buttonPanel.add(publishBtn);

        JButton chatBtn = new JButton("Chat");
        chatBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        chatBtn.setBackground(new Color(230, 126, 34));
        chatBtn.setForeground(Color.WHITE);
        chatBtn.setFocusPainted(false);
        chatBtn.setPreferredSize(new Dimension(150, 35));
        chatBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        chatBtn.addActionListener(e -> {
            new AdminChatFrame();
        });
        buttonPanel.add(chatBtn);

        JButton manageStudentsBtn = new JButton("Manage Students");
        manageStudentsBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        manageStudentsBtn.setBackground(new Color(52, 152, 219));
        manageStudentsBtn.setForeground(Color.WHITE);
        manageStudentsBtn.setFocusPainted(false);
        manageStudentsBtn.setPreferredSize(new Dimension(150, 35));
        manageStudentsBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        manageStudentsBtn.addActionListener(e -> {
            new ManageStudentsFrame();
        });
        buttonPanel.add(manageStudentsBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        logoutBtn.setBackground(new Color(231, 76, 60));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setPreferredSize(new Dimension(150, 35));
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> {
            this.dispose();
            new LoginFrame();
        });
        buttonPanel.add(logoutBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    void loadResults() {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT username, score, total, exam_time FROM results";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("username"),
                        rs.getInt("score"),
                        rs.getInt("total"),
                        rs.getTimestamp("exam_time")
                });
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
