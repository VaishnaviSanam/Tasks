import java.io.IOException;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WebMedicalManagement {
    private JFrame frame;
    private JTextField userField, passField;
    private JTextArea outputArea;
    private Connection conn;

    public WebMedicalManagement() {
        frame = new JFrame("Medical Management System");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Username:"));
        userField = new JTextField();
        panel.add(userField);
        panel.add(new JLabel("Password:"));
        passField = new JPasswordField();
        panel.add(passField);
        JButton loginButton = new JButton("Login");
        panel.add(loginButton);

        frame.add(panel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });

        connectDatabase();
        frame.setVisible(true);
    }

    private void connectDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/medical_db", "root", "password");
            outputArea.append("Database connected successfully.\n");
        } catch (Exception e) {
            outputArea.append("Database connection failed: " + e.getMessage() + "\n");
        }
    }

    private void loginUser() {
        String user = userField.getText();
        String pass = passField.getText();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
            stmt.setString(1, user);
            stmt.setString(2, pass);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                outputArea.append("Login successful! Welcome, " + user + "\n");
            } else {
                outputArea.append("Invalid credentials. Try again.\n");
            }
        } catch (SQLException e) {
            outputArea.append("Login error: " + e.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        new WebMedicalManagement();
    }
}
