<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<br>
<a href="">Add meal</a>
<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <jsp:useBean id="mealsTo" scope="request" type="java.util.List"/>
    <c:forEach items="${mealsTo}" var="mealTo">
        <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr style="color:<%=mealTo.isExcess()?"red":"green"%>">
            <td><%=mealTo.getDateTime().format(TimeUtil.DATE_TIME_FORMATTER)%></td>
            <td><%=mealTo.getDescription()%></td>
            <td><%=mealTo.getCalories()%></td>
            <td><a href="">Update</a></td>
            <td><a href="">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>