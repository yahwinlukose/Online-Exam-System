import javax.swing.*;

public class StudentDashboard extends JFrame {

    public StudentDashboard(String username) {

        setTitle("Student Dashboard");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Welcome Student: " + username, SwingConstants.CENTER);
        add(label);
        JButton startBtn = new JButton("Start Exam");
        startBtn.setBounds(130, 120, 120, 30);
        add(startBtn);

        startBtn.addActionListener(e -> {
            this.dispose();
            new ExamWindow();
        });


        setVisible(true);
    }
}
