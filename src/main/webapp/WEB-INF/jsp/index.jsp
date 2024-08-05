<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Project Manager</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .welcome-container {
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            text-align: center;
            background-color: #f8f9fa;
        }
        .welcome-message {
            max-width: 600px;
        }
        .btn-start {
            font-size: 1.25rem;
            padding: 10px 20px;
        }
    </style>
</head>
<body>
<div class="welcome-container">
    <div class="welcome-message">
        <h1 class="display-4">Welcome to Project Manager</h1>
        <p class="lead">Manage your projects</p>
        <a href="${pageContext.request.contextPath}/project/list" class="btn btn-primary btn-start">START</a>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.7/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
