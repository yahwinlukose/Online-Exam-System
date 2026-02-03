import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddQuestionFrame extends JFrame {

    JTextField qField, op1, op2, op3, op4;
    JComboBox<String> ansBox;
    JButton saveBtn;

    public AddQuestionFrame() {

        setTitle("Add Question");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel qLabel = new JLabel("Question:");
        qLabel.setBounds(30, 30, 100, 25);
        add(qLabel);

        qField = new JTextField();
        qField.setBounds(130, 30, 300, 25);
        add(qField);

        JLabel l1 = new JLabel("Option 1:");
        l1.setBounds(30, 70, 100, 25);
        add(l1);

        op1 = new JTextField();
        op1.setBounds(130, 70, 300, 25);
        add(op1);

        JLabel l2 = new JLabel("Option 2:");
        l2.setBounds(30, 110, 100, 25);
        add(l2);

        op2 = new JTextField();
        op2.setBounds(130, 110, 300, 25);
        add(op2);

        JLabel l3 = new JLabel("Option 3:");
        l3.setBounds(30, 150, 100, 25);
        add(l3);

        op3 = new JTextField();
        op3.setBounds(130, 150, 300, 25);
        add(op3);

        JLabel l4 = new JLabel("Option 4:");
        l4.setBounds(30, 190, 100, 25);
        add(l4);

        op4 = new JTextField();
        op4.setBounds(130, 190, 300, 25);
        add(op4);

        JLabel ansLabel = new JLabel("Correct:");
        ansLabel.setBounds(30, 230, 100, 25);
        add(ansLabel);

        ansBox = new JComboBox<>(new String[]{"1","2","3","4"});

        ansBox.setBounds(130, 230, 100, 25);
        add(ansBox);

        saveBtn = new JButton("Save Question");
        saveBtn.setBounds(180, 280, 150, 30);
        add(saveBtn);

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
