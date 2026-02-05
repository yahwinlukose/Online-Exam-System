import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ManageStudentsFrame extends JFrame {

    DefaultTableModel model;

    public ManageStudentsFrame() {

        setTitle("Manage Students");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Set background
        getContentPane().setBackground(new Color(240, 242, 245));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(52, 152, 219));
        titlePanel.setPreferredSize(new Dimension(700, 60));
        titlePanel.setLayout(new GridBagLayout());

        JLabel title = new JLabel("Student Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        titlePanel.add(title);

        add(titlePanel, BorderLayout.NORTH);

        // Table Panel
        model = new DefaultTableModel(
                new String[]{"Username", "Name", "Department", "Email"}, 0
        );

        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(52, 152, 219));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        add(scrollPane, BorderLayout.CENTER);

        loadStudents();

        setVisible(true);
    }

    void loadStudents() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getString("email")
                });
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
