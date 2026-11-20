package com.mycompany.healthportal;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/StaffServlet")
public class StaffServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("authenticatedUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        if (!"STAFF".equals(session.getAttribute("userRole"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {

            AppointmentDAO dao = new AppointmentDAO();

            request.setAttribute("appointments", dao.getAllAppointments());

            request.getRequestDispatcher("staff_portal.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Could not load appointments.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("authenticatedUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        if (!"STAFF".equals(session.getAttribute("userRole"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String appointmentId = request.getParameter("appointmentId");
        String status = request.getParameter("status");

        if (!"PENDING".equals(status)
                && !"CONFIRMED".equals(status)
                && !"COMPLETED".equals(status)) {

            response.getWriter().println("Invalid appointment status.");
            return;
        }

        try {

            int id = Integer.parseInt(appointmentId);

            AppointmentDAO dao = new AppointmentDAO();

            dao.updateStatus(id, status);

            response.sendRedirect(request.getContextPath() + "/StaffServlet");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Could not update appointment status.");
        }
    }
}