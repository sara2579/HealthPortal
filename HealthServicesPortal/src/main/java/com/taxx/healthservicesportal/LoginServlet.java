package com.taxx.healthservicesportal;

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.trim().isEmpty()
            || password == null || password.isEmpty()) {

            request.setAttribute("errorMessage", "Please enter your username and password.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        username = username.trim();

        String url = "jdbc:mysql://localhost:3306/health_portal_db";
        String dbUser = "root";
        String dbPassword = "";

        String sql = "SELECT username, password_hash, full_name, role FROM users WHERE username = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, dbUser, dbPassword);

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

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
                        response.sendRedirect(request.getContextPath() + "/index.jsp");
                    }

                } else {
                    request.setAttribute("errorMessage", "Incorrect username or password.");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }

            } else {
                request.setAttribute("errorMessage", "Incorrect username or password.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Login failed. Please try again.");
        }
    }
}