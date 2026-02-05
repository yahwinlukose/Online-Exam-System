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

    String currentUser; // store logged-in user

    // ✅ UPDATED CONSTRUCTOR
    public ExamWindow(String username) {
        this.currentUser = username;

        setTitle("Online Exam");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Set background
        getContentPane().setBackground(new Color(240, 242, 245));

        // Header Panel with Timer
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setPreferredSize(new Dimension(700, 60));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel examTitle = new JLabel("Online Examination");
        examTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        examTitle.setForeground(Color.WHITE);
        headerPanel.add(examTitle, BorderLayout.WEST);

        timerLabel = new JLabel("Time Left: 60");
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        timerLabel.setForeground(new Color(255, 255, 100));
        headerPanel.add(timerLabel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Question Panel
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BorderLayout());
        questionPanel.setBackground(Color.WHITE);
        questionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        questionLabel.setForeground(new Color(52, 73, 94));
        questionPanel.add(questionLabel, BorderLayout.CENTER);

        contentPanel.add(questionPanel);

        // Options Panel
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBackground(Color.WHITE);
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        op1 = new JRadioButton();
        op2 = new JRadioButton();
        op3 = new JRadioButton();
        op4 = new JRadioButton();

        op1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        op2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        op3.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        op4.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        op1.setBackground(Color.WHITE);
        op2.setBackground(Color.WHITE);
        op3.setBackground(Color.WHITE);
        op4.setBackground(Color.WHITE);

        op1.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));
        op2.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));
        op3.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));
        op4.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));

        group = new ButtonGroup();
        group.add(op1);
        group.add(op2);
        group.add(op3);
        group.add(op4);

        optionsPanel.add(op1);
        optionsPanel.add(op2);
        optionsPanel.add(op3);
        optionsPanel.add(op4);

        contentPanel.add(optionsPanel);

        add(contentPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 242, 245));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        nextBtn = new JButton("Next");
        nextBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nextBtn.setBackground(new Color(46, 204, 113));
        nextBtn.setForeground(Color.WHITE);
        nextBtn.setFocusPainted(false);
        nextBtn.setPreferredSize(new Dimension(120, 40));
        nextBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPanel.add(nextBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        loadFromDB();
        loadQuestion();
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
