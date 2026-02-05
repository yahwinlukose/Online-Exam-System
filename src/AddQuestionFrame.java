import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddQuestionFrame extends JFrame {

    JTextField qField, op1, op2, op3, op4;
    JComboBox<String> ansBox;
    JButton saveBtn;

    public AddQuestionFrame() {

        setTitle("Add Question");
        setSize(600, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Set background
        getContentPane().setBackground(new Color(240, 242, 245));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(41, 128, 185));
        titlePanel.setPreferredSize(new Dimension(600, 60));
        titlePanel.setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Add New Question");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        add(titlePanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        formPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Question
        JLabel qLabel = new JLabel("Question:");
        qLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        formPanel.add(qLabel, gbc);

        qField = new JTextField();
        qField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        qField.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        formPanel.add(qField, gbc);

        // Option 1
        JLabel l1 = new JLabel("Option 1:");
        l1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        formPanel.add(l1, gbc);

        op1 = new JTextField();
        op1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        op1.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.7;
        formPanel.add(op1, gbc);

        // Option 2
        JLabel l2 = new JLabel("Option 2:");
        l2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        formPanel.add(l2, gbc);

        op2 = new JTextField();
        op2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        op2.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.7;
        formPanel.add(op2, gbc);

        // Option 3
        JLabel l3 = new JLabel("Option 3:");
        l3.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        formPanel.add(l3, gbc);

        op3 = new JTextField();
        op3.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        op3.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 0.7;
        formPanel.add(op3, gbc);

        // Option 4
        JLabel l4 = new JLabel("Option 4:");
        l4.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.3;
        formPanel.add(l4, gbc);

        op4 = new JTextField();
        op4.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        op4.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 0.7;
        formPanel.add(op4, gbc);

        // Correct Answer
        JLabel ansLabel = new JLabel("Correct Answer:");
        ansLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.3;
        formPanel.add(ansLabel, gbc);

        ansBox = new JComboBox<>(new String[]{"1","2","3","4"});
        ansBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ansBox.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.weightx = 0.7;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(ansBox, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 242, 245));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        saveBtn = new JButton("Save Question");
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveBtn.setBackground(new Color(46, 204, 113));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.setPreferredSize(new Dimension(150, 40));
        saveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPanel.add(saveBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        saveBtn.addActionListener(e -> saveQuestion());

        setVisible(true);
    }

    void saveQuestion() {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO questions(question, op1, op2, op3, op4, answer) VALUES(?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, qField.getText());
            ps.setString(2, op1.getText());
            ps.setString(3, op2.getText());
            ps.setString(4, op3.getText());
            ps.setString(5, op4.getText());
            ps.setInt(6, Integer.parseInt(ansBox.getSelectedItem().toString()));

            ps.executeUpdate();
            con.close();

            JOptionPane.showMessageDialog(this, "Question Added!");
            clear();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void clear() {
        qField.setText("");
        op1.setText("");
        op2.setText("");
        op3.setText("");
        op4.setText("");
    }
}
