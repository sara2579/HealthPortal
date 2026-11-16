package com.taxx.healthservicesportal;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AppointmentServlet")
public class AppointmentServlet extends HttpServlet {
        
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("authenticatedUser") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String username = (String) session.getAttribute("authenticatedUser");

        try {

            AppointmentDAO dao = new AppointmentDAO();

            request.setAttribute("appointments",
                    dao.getAppointmentsByPatient(username));

            request.getRequestDispatcher("patient_dashboard.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Could not load appointments.");
        }
    }

    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("authenticatedUser") == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        
        String username = (String) session.getAttribute("authenticatedUser");
        String department = request.getParameter("department");
        String appointmentDate = request.getParameter("appointmentDate");
        String symptoms = request.getParameter("symptoms");

        if (department == null || department.trim().isEmpty()
                || appointmentDate == null || appointmentDate.trim().isEmpty()
                || symptoms == null || symptoms.trim().isEmpty()) {

            response.getWriter().println("Please fill in all fields.");
            return;
        }

        Appointment appointment = new Appointment(
                0,
                username,
                department.trim(),
                appointmentDate,
                symptoms.trim(),
                "PENDING"
        );

        try {

            AppointmentDAO dao = new AppointmentDAO();
            dao.createAppointment(appointment);

            response.sendRedirect("patient_dashboard.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Could not book appointment. Please try again.");
        }
    }
}