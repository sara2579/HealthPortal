<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Patient Dashboard</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #4895cf;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 600px;
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

        label {
            display: block;
            margin-top: 15px;
            color: #654B3C;
        }

        input, select, textarea {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #E8C7B5;
            border-radius: 6px;
            box-sizing: border-box;
        }

        button {
            width: 100%;
            padding: 11px;
            margin-top: 20px;
            background-color: #286da1;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }

        button:hover {
            background-color: #1d5c8c;
        }

        .logout {
            display: block;
            text-align: right;
            color: #145485;
            margin-bottom: 20px;
        }

        .history {
            margin-top: 35px;
            border-top: 1px solid #E8C7B5;
            padding-top: 20px;
        }

        .appointment-card {
            background-color: #fff;
            padding: 15px;
            margin-top: 15px;
            border-radius: 8px;
            text-align: left;
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

        <h2>Welcome, <c:out value="${sessionScope.fullName}" />!</h2>

        <h2>Book an Appointment</h2>

        <form action="AppointmentServlet" method="POST">

            <label>Department</label>

            <select name="department" required>
                <option value="">Select a department</option>
                <option value="General Medicine">General Medicine</option>
                <option value="Dentistry">Dentistry</option>
                <option value="Dermatology">Dermatology</option>
                <option value="Cardiology">Cardiology</option>
                <option value="Ophthalmology">Ophthalmology</option>
            </select>

            <label>Appointment Date and Time</label>

            <input type="datetime-local"
                   name="appointmentDate" required>

            <label>Symptoms</label>

            <textarea name="symptoms"
                      rows="4"
                      placeholder="Describe your symptoms..."
                      required></textarea>

            <button type="submit">Book Appointment</button>

        </form>

        <div class="history">

            <h2>My Appointments</h2>

            <c:choose>
                <c:when test="${empty appointments}">
                    <p>You have no appointments yet.</p>
                </c:when>

                <c:otherwise>
                    <c:forEach var="app" items="${appointments}">
                        <div class="appointment-card">
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
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html>
