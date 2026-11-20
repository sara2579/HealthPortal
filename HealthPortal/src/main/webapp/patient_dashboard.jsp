<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Patient Dashboard</title>

        <style>
            body {
                font-family: system-ui, -apple-system, Arial, sans-serif;
                background-color: #eef2f1;
                color: #1e293b;
                margin: 0;
                padding: 24px;
            }

            .container {
                max-width: 680px;
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
                margin-bottom: 18px;
                border-bottom: 1px solid #e2e8f0;
            }

            .topbar h2 {
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
                font-size: 17px;
                color: #1f4e5f;
                margin: 0 0 10px;
            }

            p {
                color: #475569;
                margin: 4px 0;
                font-size: 14px;
            }

            .form-grid {
                display: grid;
                grid-template-columns: 1fr 1fr;
                gap: 12px;
            }

            .full-width {
                grid-column: 1 / -1;
            }

            label {
                display: block;
                margin-top: 8px;
                font-size: 13px;
                color: #334155;
            }

            input, select, textarea {
                width: 100%;
                padding: 8px 10px;
                margin-top: 4px;
                border: 1px solid #cbd5e1;
                border-radius: 6px;
                box-sizing: border-box;
                font-family: inherit;
                font-size: 14px;
            }

            button {
                padding: 10px 16px;
                margin-top: 14px;
                background-color: #1f4e5f;
                color: #ffffff;
                border: none;
                border-radius: 6px;
                font-size: 14px;
                cursor: pointer;
            }

            button:hover {
                background-color: #163a47;
            }

            .history {
                margin-top: 28px;
                padding-top: 18px;
                border-top: 1px solid #e2e8f0;
            }

            .appointment-card {
                background-color: #f8fafc;
                border: 1px solid #e2e8f0;
                padding: 12px 14px;
                margin-top: 10px;
                border-radius: 8px;
            }

            .status {
                padding: 2px 8px;
                border-radius: 4px;
                font-size: 12px;
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
                <h2>Welcome, <c:out value="${sessionScope.fullName}" />!</h2>
                <a class="logout" href="LogoutServlet">Log Out</a>
            </div>

            <h2>Book an Appointment</h2>

            <form action="AppointmentServlet" method="POST">
                <div class="form-grid">
                    <div class="full-width">
                        <label>Department</label>
                        <select name="department" required>
                            <option value="">Select a department</option>
                            <option value="General Medicine">General Medicine</option>
                            <option value="Dentistry">Dentistry</option>
                            <option value="Dermatology">Dermatology</option>
                            <option value="Cardiology">Cardiology</option>
                            <option value="Ophthalmology">Ophthalmology</option>
                        </select>
                    </div>

                    <div>
                        <label>Appointment Date</label>
                        <input type="date"
                               id="appointmentDate"
                               name="appointmentDate"
                               required>
                    </div>

                    <div>
                        <label>Appointment Time (7:00 AM - 5:00 PM)</label>
                        <input type="time"
                               id="appointmentTime"
                               name="appointmentTime"
                               min="07:00"
                               max="17:00"
                               required>
                    </div>

                    <div class="full-width">
                        <label>Symptoms</label>
                        <textarea name="symptoms"
                                  rows="3"
                                  placeholder="Describe your symptoms..."
                                  required></textarea>
                    </div>
                </div>

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

        <script>
            const dateInput = document.getElementById("appointmentDate");
            const timeInput = document.getElementById("appointmentTime");

            // Get today's date in YYYY-MM-DD format
            const today = new Date();
            const year = today.getFullYear();
            const month = String(today.getMonth() + 1).padStart(2, '0');
            const day = String(today.getDate()).padStart(2, '0');

            const todayString = year + "-" + month + "-" + day;

            // Prevent selecting dates before today
            dateInput.min = todayString;

            // When the patient selects a date
            dateInput.addEventListener("change", function () {

                if (dateInput.value === todayString) {

                    // If today is selected, prevent selecting past times
                    const now = new Date();
                    const hours = String(now.getHours()).padStart(2, '0');
                    const minutes = String(now.getMinutes()).padStart(2, '0');

                    timeInput.min = hours + ":" + minutes;

                } else {

                    // For future dates, allow the full office hours
                    timeInput.min = "07:00";
                }

                timeInput.value = "";
            });
        </script>
    </body>
</html>