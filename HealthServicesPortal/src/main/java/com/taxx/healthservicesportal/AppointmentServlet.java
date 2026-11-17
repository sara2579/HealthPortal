package com.taxx.healthservicesportal;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.ZoneId;

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

            request.setAttribute("appointments", dao.getAppointmentsByPatient(username));

            request.getRequestDispatcher("patient_dashboard.jsp") .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Could not load appointments.");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("authenticatedUser") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String username = (String) session.getAttribute("authenticatedUser");
        
        if (!"PATIENT".equals(session.getAttribute("userRole"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String department = request.getParameter("department");
        String appointmentDate = request.getParameter("appointmentDate");
        String appointmentTime = request.getParameter("appointmentTime");
        String symptoms = request.getParameter("symptoms");

        if (department == null || department.trim().isEmpty()
                || appointmentDate == null || appointmentDate.trim().isEmpty()
                || appointmentTime == null || appointmentTime.trim().isEmpty()
                || symptoms == null || symptoms.trim().isEmpty()) {

            response.getWriter().println("Please fill in all fields.");
            return;
        }

        try {
            // Convert the separate date and time into Java date/time objects
            LocalDate date = LocalDate.parse(appointmentDate);
            LocalTime time = LocalTime.parse(appointmentTime);

            LocalDateTime appointmentDateTime = LocalDateTime.of(date, time);

            // Get the current date and time in UAE
            LocalDateTime now = LocalDateTime.now(
                    ZoneId.of("Asia/Dubai"));

            // Check that the appointment is not in the past
            if (appointmentDateTime.isBefore(now)) {
                response.getWriter().println(
                        "Please select a future date and time.");
                return;
            }

            // Check office hours: 7 AM to 5 PM
            if (time.isBefore(LocalTime.of(7, 0))
                    || time.isAfter(LocalTime.of(17, 0))) {

                response.getWriter().println(
                        "Appointments are only available from 7 AM to 5 PM.");
                return;
            }

            // Combine date and time into one value for the database
            String combinedDateTime = appointmentDate + "T" + appointmentTime;

            Appointment appointment = new Appointment(
                    0,
                    username,
                    department.trim(),
                    combinedDateTime,
                    symptoms.trim(),
                    "PENDING"
            );

            AppointmentDAO dao = new AppointmentDAO();
            dao.createAppointment(appointment);

            // Reload the appointment history
            response.sendRedirect(
                    request.getContextPath() + "/AppointmentServlet");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println(
                    "Could not book appointment. Please check the date and time.");
        }
    }

}