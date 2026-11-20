<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Staff Portal</title>

        <style>
            body {
                font-family: system-ui, -apple-system, Arial, sans-serif;
                background-color: #eef2f1;
                color: #1e293b;
                margin: 0;
                padding: 24px;
            }

            .container {
                max-width: 820px;
                margin: 24px auto;
                padding: 24px;
                background-color: #ffffff;
                border: 1px solid #d5dfe2;
                border-radius: 10px;
            }

            .topbar {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding-bottom: 12px;
                margin-bottom: 16px;
                border-bottom: 1px solid #e2e8f0;
            }

            .topbar h1 {
                margin: 0;
                font-size: 20px;
                color: #1f4e5f;
            }

            .logout {
                color: #1f4e5f;
                text-decoration: none;
                font-size: 14px;
            }

            .logout:hover {
                text-decoration: underline;
            }

            h2 {
                font-size: 16px;
                color: #1f4e5f;
                margin: 12px 0 8px;
            }

            p {
                color: #475569;
                margin: 3px 0;
                font-size: 13px;
            }

            .queue-grid {
                display: grid;
                grid-template-columns: repeat(auto-fill, minmax(230px, 1fr));
                gap: 12px;
                margin-top: 12px;
            }

            .appointment-card {
                background-color: #f8fafc;
                border: 1px solid #e2e8f0;
                padding: 10px 12px;
                border-radius: 8px;
            }

            .status-form {
                margin-top: 8px;
                padding-top: 8px;
                border-top: 1px solid #e2e8f0;
            }

            label {
                font-size: 12px;
                color: #334155;
            }

            select {
                padding: 5px 7px;
                border: 1px solid #cbd5e1;
                border-radius: 5px;
                font-size: 12px;
            }

            button {
                padding: 6px 10px;
                margin-top: 6px;
                background-color: #1f4e5f;
                color: #ffffff;
                border: none;
                border-radius: 5px;
                font-size: 12px;
                cursor: pointer;
            }

            button:hover {
                background-color: #163a47;
            }

            .status {
                padding: 2px 7px;
                border-radius: 4px;
                font-size: 11px;
                font-weight: 600;
            }

            .pending {
                background-color: #fef3c7;
                color: #92400e;
            }

            .confirmed {
                background-color: #dcfce7;
                color: #166534;
            }

            .completed {
                background-color: #dbeafe;
                color: #1e40af;
            }
        </style>
    </head>

    <body>

        <div class="container">
            <div class="topbar">
                <h1>Staff Portal - <c:out value="${sessionScope.fullName}" /></h1>
                <a class="logout" href="LogoutServlet">Log Out</a>
            </div>

            <h2>Patient Appointment Queue</h2>

            <c:choose>
                <c:when test="${empty appointments}">
                    <p>There are no appointments yet.</p>
                </c:when>

                <c:otherwise>
                    <div class="queue-grid">
                        <c:forEach var="app" items="${appointments}">
                            <div class="appointment-card">
                                <p>
                                    <strong>Patient:</strong>
                                    <c:out value="${app.patientUsername}" />
                                </p>

                                <p>
                                    <strong>Department:</strong>
                                    <c:out value="${app.department}" />
                                </p>

                                <p>
                                    <strong>Date:</strong>
                                    <c:out value="${app.appointmentDate}" />
                                </p>

                                <p>
                                    <strong>Symptoms:</strong>
                                    <c:out value="${app.symptoms}" />
                                </p>

                                <p>
                                    <strong>Status:</strong>

                                    <c:choose>
                                        <c:when test="${app.status == 'PENDING'}">
                                            <span class="status pending">PENDING</span>
                                        </c:when>

                                        <c:when test="${app.status == 'CONFIRMED'}">
                                            <span class="status confirmed">CONFIRMED</span>
                                        </c:when>

                                        <c:when test="${app.status == 'COMPLETED'}">
                                            <span class="status completed">COMPLETED</span>
                                        </c:when>
                                    </c:choose>
                                </p>

                                <form class="status-form" action="StaffServlet" method="POST">

                                    <input type="hidden"
                                           name="appointmentId"
                                           value="<c:out value='${app.appointmentId}' />">

                                    <label>Status:</label>

                                    <select name="status" required>

                                        <option value="PENDING"
                                                ${app.status == 'PENDING' ? 'selected' : ''}>
                                            Pending
                                        </option>

                                        <option value="CONFIRMED"
                                                ${app.status == 'CONFIRMED' ? 'selected' : ''}>
                                            Confirmed
                                        </option>

                                        <option value="COMPLETED"
                                                ${app.status == 'COMPLETED' ? 'selected' : ''}>
                                            Completed
                                        </option>
                                    </select>
                                    <button type="submit">Update</button>
                                </form>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>