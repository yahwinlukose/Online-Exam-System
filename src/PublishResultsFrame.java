import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PublishResultsFrame extends JFrame {

    DefaultTableModel model;

    public PublishResultsFrame() {

        setTitle("Publish Results");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Set background
        getContentPane().setBackground(new Color(240, 242, 245));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(155, 89, 182));
        titlePanel.setPreferredSize(new Dimension(700, 60));
        titlePanel.setLayout(new GridBagLayout());

        JLabel title = new JLabel("Publish Exam Results");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        titlePanel.add(title);

        add(titlePanel, BorderLayout.NORTH);

        // Table Panel
        model = new DefaultTableModel(
                new String[]{"ID", "Username", "Score", "Total", "Published"}, 0
        );

        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(155, 89, 182));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        add(sp, BorderLayout.CENTER);

        loadResults();

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 242, 245));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        JButton publishBtn = new JButton("Publish Selected");
        publishBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        publishBtn.setBackground(new Color(46, 204, 113));
        publishBtn.setForeground(Color.WHITE);
        publishBtn.setFocusPainted(false);
        publishBtn.setPreferredSize(new Dimension(160, 40));
        publishBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPanel.add(publishBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        publishBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a result first");
                return;
            }

            int id = Integer.parseInt(model.getValueAt(row, 0).toString());
            publishResult(id);
            model.setValueAt(true, row, 4);
        });

        setVisible(true);
    }

    void loadResults() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM results");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getInt("score"),
                        rs.getInt("total"),
                        rs.getBoolean("published")
                });
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void publishResult(int id) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps =
                    con.prepareStatement("UPDATE results SET published=TRUE WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
