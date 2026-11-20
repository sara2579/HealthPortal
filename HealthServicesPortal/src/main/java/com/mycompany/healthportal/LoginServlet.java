package com.mycompany.healthportal;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    // Set to true for Apache Derby, or false for MySQL (XAMPP)
    private static final boolean USE_DERBY = true;

    private static final String DERBY_URL = "jdbc:derby://localhost:1527/health_portal_db;create=true";
    private static final String DERBY_USER = "app";
    private static final String DERBY_PASSWORD = "app";

    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/health_portal_db";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASSWORD = "";

    private Connection getConnection() throws Exception {
        if (USE_DERBY) {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            return DriverManager.getConnection(DERBY_URL, DERBY_USER, DERBY_PASSWORD);
        } else {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.trim().isEmpty()
            || password == null || password.isEmpty()) {

            request.setAttribute("errorMessage", "Please enter your username and password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        username = username.trim();

        String sql = "SELECT username, password_hash, full_name, role FROM users WHERE username = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    String storedHash = rs.getString("password_hash");
                    String role = rs.getString("role");
                    String fullName = rs.getString("full_name");

                    if (PasswordUtil.checkPassword(password, storedHash)) {

                        HttpSession session = request.getSession();

                        session.setAttribute("authenticatedUser", username);
                        session.setAttribute("userRole", role);
                        session.setAttribute("fullName", fullName);

                        if (role.equals("PATIENT")) {
                            response.sendRedirect(request.getContextPath() + "/AppointmentServlet");
                        }
                        else if (role.equals("STAFF")) {
                            response.sendRedirect(request.getContextPath() + "/StaffServlet");
                        }
                        else {
                            session.invalidate();
                            response.sendRedirect(request.getContextPath() + "/login.jsp");
                        }

                    } else {
                        request.setAttribute("errorMessage", "Incorrect username or password.");
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                    }

                } else {
                    request.setAttribute("errorMessage", "Incorrect username or password.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Login failed. Please try again.");
        }
    }
}