import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends JFrame {

    public StudentDashboard(String username) {

        setTitle("Student Dashboard");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Background
        getContentPane().setBackground(new Color(240, 242, 245));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(52, 152, 219));
        titlePanel.setPreferredSize(new Dimension(600, 70));
        titlePanel.setLayout(new GridBagLayout());

        JLabel label = new JLabel("Welcome, " + username);
        label.setFont(new Font("Segoe UI", Font.BOLD, 22));
        label.setForeground(Color.WHITE);
        titlePanel.add(label);

        add(titlePanel, BorderLayout.NORTH);

        // Center card panel
        JPanel card = new JPanel();
        card.setLayout(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(30, 50, 30, 50),
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        // My Profile button
        JButton profileBtn = new JButton("My Profile");
        profileBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        profileBtn.setBackground(new Color(52, 152, 219));
        profileBtn.setForeground(Color.WHITE);
        profileBtn.setFocusPainted(false);
        profileBtn.setPreferredSize(new Dimension(200, 40));
        profileBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 0;
        card.add(profileBtn, gbc);

        profileBtn.addActionListener(e -> {
            new StudentProfileFrame(username);
        });

        // Start Exam button
        JButton startBtn = new JButton("Start Exam");
        startBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        startBtn.setBackground(new Color(46, 204, 113));
        startBtn.setForeground(Color.WHITE);
        startBtn.setFocusPainted(false);
        startBtn.setPreferredSize(new Dimension(200, 40));
        startBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 1;
        card.add(startBtn, gbc);

        startBtn.addActionListener(e -> {
            this.dispose();
            new ExamWindow(username);
        });

        // View Results button
        JButton resultBtn = new JButton("View Results");
        resultBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        resultBtn.setBackground(new Color(155, 89, 182));
        resultBtn.setForeground(Color.WHITE);
        resultBtn.setFocusPainted(false);
        resultBtn.setPreferredSize(new Dimension(200, 40));
        resultBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 2;
        card.add(resultBtn, gbc);

        resultBtn.addActionListener(e -> {
            new ViewResultsFrame(username);
        });

        // Chat with Admin button
        JButton chatBtn = new JButton("Chat with Admin");
        chatBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        chatBtn.setBackground(new Color(230, 126, 34));
        chatBtn.setForeground(Color.WHITE);
        chatBtn.setFocusPainted(false);
        chatBtn.setPreferredSize(new Dimension(200, 40));
        chatBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 3;
        card.add(chatBtn, gbc);

        chatBtn.addActionListener(e -> {
            new StudentChatFrame(username);
        });

        // Logout button
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutBtn.setBackground(new Color(231, 76, 60));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setPreferredSize(new Dimension(200, 40));
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 4;
        gbc.insets = new Insets(20, 20, 10, 20);
        card.add(logoutBtn, gbc);

        logoutBtn.addActionListener(e -> {
            this.dispose();
            new LoginFrame();
        });

        add(card, BorderLayout.CENTER);

        setVisible(true);
    }
}
