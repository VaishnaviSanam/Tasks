package webmedicalsystem;

import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

// Database Connection Class
class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/medical_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

// Patient Model
class Patient {
    private int id;
    private String name, email, password, phone, gender;
    private int age;

    public Patient(int id, String name, String email, String password, String phone, int age, String gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
    }
    // Getters & Setters
}

// Patient DAO (Database Access)
class PatientDAO {
    private Connection conn;

    public PatientDAO() {
        conn = DBConnection.getConnection();
    }

    public boolean addPatient(Patient patient) {
        String sql = "INSERT INTO patients (name, email, password, phone, age, gender) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patient.getName());
            stmt.setString(2, patient.getEmail());
            stmt.setString(3, patient.getPassword());
            stmt.setString(4, patient.getPhone());
            stmt.setInt(5, patient.getAge());
            stmt.setString(6, patient.getGender());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

// Patient Servlet
@WebServlet("/PatientServlet")
public class PatientServlet extends HttpServlet {
    private PatientDAO patientDAO;

    public void init() {
        patientDAO = new PatientDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("register".equals(action)) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String phone = request.getParameter("phone");
            int age = Integer.parseInt(request.getParameter("age"));
            String gender = request.getParameter("gender");

            Patient patient = new Patient(0, name, email, password, phone, age, gender);
            if (patientDAO.addPatient(patient)) {
                response.sendRedirect("login.jsp?message=Registered successfully!");
            } else {
                response.sendRedirect("register.jsp?error=Registration failed!");
            }
        }
    }
}
