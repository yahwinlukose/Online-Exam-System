import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;
import java.sql.*;
import java.util.ArrayList;

public class ExamWindow extends JFrame {

    // Dynamic question bank
    ArrayList<String> questions = new ArrayList<>();
    ArrayList<String[]> options = new ArrayList<>();
    ArrayList<Integer> answers = new ArrayList<>();

    int index = 0;
    int score = 0;

    int timeLeft = 60;
    JLabel timerLabel;
    Timer timer;

    JLabel questionLabel;
    JRadioButton op1, op2, op3, op4;
    ButtonGroup group;
    JButton nextBtn;

    String currentUser = "john"; // later pass real username

    public ExamWindow() {

        setTitle("Online Exam");
        setSize(600, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        questionLabel = new JLabel();
        questionLabel.setBounds(50, 40, 500, 30);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(questionLabel);

        timerLabel = new JLabel("Time Left: 60");
        timerLabel.setBounds(450, 10, 120, 30);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(timerLabel);

        op1 = new JRadioButton();
        op2 = new JRadioButton();
        op3 = new JRadioButton();
        op4 = new JRadioButton();

        op1.setBounds(50, 90, 400, 25);
        op2.setBounds(50, 120, 400, 25);
        op3.setBounds(50, 150, 400, 25);
        op4.setBounds(50, 180, 400, 25);

        group = new ButtonGroup();
        group.add(op1);
        group.add(op2);
        group.add(op3);
        group.add(op4);

        add(op1);
        add(op2);
        add(op3);
        add(op4);

        nextBtn = new JButton("Next");
        nextBtn.setBounds(250, 240, 100, 30);
        add(nextBtn);

        loadFromDB();   // load real questions
        loadQuestion(); // show first question
        startTimer();

        nextBtn.addActionListener(e -> checkAnswer());

        setVisible(true);
    }

    void loadFromDB() {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM questions ORDER BY RAND() LIMIT 10";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                questions.add(rs.getString("question"));
                options.add(new String[]{
                        rs.getString("op1"),
                        rs.getString("op2"),
                        rs.getString("op3"),
                        rs.getString("op4")
                });
                answers.add(rs.getInt("answer"));
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void loadQuestion() {
        group.clearSelection();
        questionLabel.setText((index + 1) + ". " + questions.get(index));
        op1.setText(options.get(index)[0]);
        op2.setText(options.get(index)[1]);
        op3.setText(options.get(index)[2]);
        op4.setText(options.get(index)[3]);
    }

    void checkAnswer() {
        int selected = -1;

        if (op1.isSelected()) selected = 0;
        if (op2.isSelected()) selected = 1;
        if (op3.isSelected()) selected = 2;
        if (op4.isSelected()) selected = 3;

        if (selected == answers.get(index)) {
            score++;
        }

        index++;

        if (index < questions.size()) {
            loadQuestion();
        } else {
            showResult();
        }
    }

    void startTimer() {
        timer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time Left: " + timeLeft);

            if (timeLeft <= 0) {
                timer.stop();
                showResult();
            }
        });
        timer.start();
    }

    void showResult() {
        if (timer != null) timer.stop();
        saveResult();

        JOptionPane.showMessageDialog(this,
                "Exam finished!\nYour score: " + score + "/" + questions.size());
        System.exit(0);
    }

    void saveResult() {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO results(username, score, total) VALUES(?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, currentUser);
            ps.setInt(2, score);
            ps.setInt(3, questions.size());

            ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
