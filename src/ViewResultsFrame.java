import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewResultsFrame extends JFrame {

    public ViewResultsFrame(String username) {

        setTitle("My Results");
        setSize(550, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Set background
        getContentPane().setBackground(new Color(240, 242, 245));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(155, 89, 182));
        titlePanel.setPreferredSize(new Dimension(550, 60));
        titlePanel.setLayout(new GridBagLayout());

        JLabel title = new JLabel("My Exam Results");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        titlePanel.add(title);

        add(titlePanel, BorderLayout.NORTH);

        // Table Panel
        DefaultTableModel model =
                new DefaultTableModel(new String[]{"Score", "Total"}, 0);

        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(155, 89, 182));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        add(scrollPane, BorderLayout.CENTER);

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT score, total FROM results WHERE username=? AND published=TRUE"
            );
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("score"),
                        rs.getInt("total")
                });
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(true);
    }
}
