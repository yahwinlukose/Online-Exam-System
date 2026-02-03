import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminDashboard extends JFrame {

    JTable table;
    DefaultTableModel model;

    public AdminDashboard(String username) {

        setTitle("Admin Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Welcome Admin: " + username, SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        JButton addBtn = new JButton("Add Question");
        addBtn.addActionListener(e -> new AddQuestionFrame());
        add(addBtn, BorderLayout.SOUTH);


        model = new DefaultTableModel();
        model.addColumn("Username");
        model.addColumn("Score");
        model.addColumn("Total");
        model.addColumn("Date");

        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        add(sp, BorderLayout.CENTER);

        loadResults();



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
