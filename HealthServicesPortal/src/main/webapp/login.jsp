<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>HealthCenterLogin</title>

        <style>
            body {
                font-family: system-ui, -apple-system, Arial, sans-serif;
                background-color: #eef2f1;
                color: #1e293b;
                margin: 0;
                padding: 24px;
            }

            .login-container {
                max-width: 360px;
                margin: 80px auto;
                padding: 24px;
                background-color: #ffffff;
                border: 1px solid #d5dfe2;
                border-radius: 10px;
            }

            h1 {
                margin: 0 0 6px;
                font-size: 22px;
                color: #1f4e5f;
                text-align: center;
            }

            p {
                margin: 0 0 16px;
                color: #475569;
                font-size: 14px;
                text-align: center;
            }

            .error {
                color: #b91c1c;
                background-color: #fef2f2;
                border: 1px solid #fecaca;
                padding: 8px 10px;
                border-radius: 6px;
                font-size: 13px;
                margin-bottom: 12px;
            }

            label {
                display: block;
                margin-top: 12px;
                font-size: 14px;
                color: #334155;
            }

            input {
                width: 100%;
                padding: 9px 10px;
                margin-top: 4px;
                border: 1px solid #cbd5e1;
                border-radius: 6px;
                box-sizing: border-box;
                font-size: 14px;
            }

            button {
                width: 100%;
                padding: 10px;
                margin-top: 18px;
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
        </style>
    </head>

    <body>

        <div class="login-container">

            <h1>Welcome to AU Health Center!</h1>
            <p>Please fill in the log-in form.</p>

            <c:if test="${not empty errorMessage}">
                <p class="error">
                    <c:out value="${errorMessage}" />
                </p>
            </c:if>

            <form action="LoginServlet" method="POST">

                <label>Username</label>
                <input type="text" name="username" required>

                <label>Password</label>
                <input type="password" name="password" required>

                <button type="submit">Login</button>

            </form>

        </div>

    </body>
</html>