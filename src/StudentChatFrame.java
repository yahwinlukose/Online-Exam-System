import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class StudentChatFrame extends JFrame {

    JTextArea chatArea;
    JTextField msgField;
    String username;

    public StudentChatFrame(String username) {
        this.username = username;

        setTitle("Chat with Admin");
        setSize(550, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Set background
        getContentPane().setBackground(new Color(240, 242, 245));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(230, 126, 34));
        titlePanel.setPreferredSize(new Dimension(550, 60));
        titlePanel.setLayout(new GridBagLayout());

        JLabel title = new JLabel("Chat with Admin");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        titlePanel.add(title);

        add(titlePanel, BorderLayout.NORTH);

        // Chat Area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        add(scrollPane, BorderLayout.CENTER);

        // Input Panel
        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        msgField = new JTextField();
        msgField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        msgField.setPreferredSize(new Dimension(0, 35));

        JButton sendBtn = new JButton("Send");
        sendBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        sendBtn.setBackground(new Color(46, 204, 113));
        sendBtn.setForeground(Color.WHITE);
        sendBtn.setFocusPainted(false);
        sendBtn.setPreferredSize(new Dimension(80, 35));
        sendBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        inputPanel.add(msgField, BorderLayout.CENTER);
        inputPanel.add(sendBtn, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        sendBtn.addActionListener(e -> sendMessage());

        // Auto refresh every 2 seconds
        new javax.swing.Timer(2000, e -> loadMessages()).start();

        loadMessages();
        setVisible(true);
    }

    void sendMessage() {
        String msg = msgField.getText().trim();
        if (msg.isEmpty()) return;

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO messages(sender, receiver, message) VALUES(?,?,?)"
            );
            ps.setString(1, username);
            ps.setString(2, "admin");
            ps.setString(3, msg);
            ps.executeUpdate();
            con.close();

            msgField.setText("");
            loadMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void loadMessages() {
        try {
            chatArea.setText("");
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM messages WHERE sender=? OR receiver=? ORDER BY time"
            );
            ps.setString(1, username);
            ps.setString(2, username);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                chatArea.append(
                        rs.getString("sender") + ": " +
                                rs.getString("message") + "\n"
                );
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
