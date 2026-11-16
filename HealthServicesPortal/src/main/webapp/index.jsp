<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>HealthCenterLogin</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #4895cf;
            margin: 0;
            padding: 0;
        }

        .login-container {
            width: 350px;
            margin: 100px auto;
            padding: 30px;
            background-color: #FFF9F4;
            border-radius: 12px;
            text-align: center;
        }

        h1 {
            color: #145485;
            font-size: 24px;
        }

        p {
            color: #70554a;
        }

        label {
            display: block;
            text-align: left;
            margin-top: 15px;
            color: #654B3C;
        }

        input {
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
            margin-top: 25px;
            background-color: #286da1;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }

        button:hover {
            background-color: #1d5c8c;
        }
    </style>
</head>

<body>

    <div class="login-container">

        <h1>AU Health Center</h1>
        <p>Welcome! Please log in to continue.</p>
        
        <c:if test="${not empty errorMessage}">
            <p style="color: red;">
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