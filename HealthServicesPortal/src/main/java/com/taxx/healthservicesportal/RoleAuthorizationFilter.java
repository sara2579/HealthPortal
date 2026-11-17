package com.taxx.healthservicesportal;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(
    urlPatterns = {
        "/patient_dashboard.jsp",
        "/AppointmentServlet",
        "/staff_portal.jsp",
        "/StaffServlet"
    },
    dispatcherTypes = {
        DispatcherType.REQUEST,
        DispatcherType.FORWARD
    }
)
public class RoleAuthorizationFilter implements Filter {

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request,
                         jakarta.servlet.ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        
        if (session == null
                || session.getAttribute("authenticatedUser") == null) {

            res.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        String role = (String) session.getAttribute("userRole");
        String path = req.getServletPath();
        
        if (path.equals("/patient_dashboard.jsp")
                || path.equals("/AppointmentServlet")) {

            if (!"PATIENT".equals(role)) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
        
        if (path.equals("/staff_portal.jsp")
                || path.equals("/StaffServlet")) {

            if (!"STAFF".equals(role)) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        chain.doFilter(request, response);
    }
}