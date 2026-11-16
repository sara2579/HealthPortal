<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Staff Portal</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #4895cf;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 750px;
            margin: 50px auto;
            padding: 30px;
            background-color: #FFF9F4;
            border-radius: 12px;
        }

        h1, h2 {
            color: #145485;
        }

        p {
            color: #70554a;
        }

        .logout {
            display: block;
            text-align: right;
            color: #145485;
            margin-bottom: 20px;
        }

        .appointment-card {
            background-color: white;
            padding: 15px;
            margin-top: 15px;
            border-radius: 8px;
        }

        label {
            color: #654B3C;
        }

        select {
            padding: 8px;
            border: 1px solid #E8C7B5;
            border-radius: 6px;
        }

        button {
            padding: 9px 15px;
            margin-top: 10px;
            background-color: #286da1;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }

        button:hover {
            background-color: #1d5c8c;
        }

        .status {
            padding: 5px;
            border-radius: 5px;
        }

        .pending {
            background-color: #fff3cd;
        }

        .confirmed {
            background-color: #d4edda;
        }

        .completed {
            background-color: #cce5ff;
        }

    </style>
</head>

<body>

    <div class="container">
        <a class="logout" href="LogoutServlet">Log Out</a>

        <h1>Staff Portal</h1>
        <h2>Welcome, <c:out value="${sessionScope.fullName}" />!</h2>
        <h2>Patient Appointment Queue</h2>

        <c:choose>
            <c:when test="${empty appointments}">
                <p>There are no appointments yet.</p>
            </c:when>

            <c:otherwise>
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

                        <form action="StaffServlet" method="POST">

                            <input type="hidden"
                                   name="appointmentId"
                                   value="<c:out value='${app.appointmentId}' />">

                            <label>Update Status:</label>

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
                            <button type="submit">Update Status</button>
                        </form>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>