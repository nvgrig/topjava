<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<style>
    dl {
        background: none repeat scroll 0 0 #FAFAFA;
        margin: 8px 0;
        padding: 0;
    }

    dt {
        display: inline-block;
        width: 170px;
    }

    dd {
        display: inline-block;
        margin-left: 8px;
        vertical-align: top;
    }
</style>
<head>
    <title>Meal edit form</title>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
</head>
<body>
<h2><%=(meal.getId() == null)?"Add meal":"Edit meal"%></h2>
<br>
<form method="post" action="meals" enctype="application/x-www-form-urlencoded">
    <input name="mealId" type="hidden" value="${meal.id}">
    <dl>
        <dt>DateTime:</dt>
        <dd><input type="datetime-local" name="dateTime" value="${meal.dateTime}"></dd>
    </dl>
    <dl>
        <dt>description:</dt>
        <dd><input type="text" name="description" value="${meal.description}"></dd>
    </dl>
    <dl>
        <dt>Calories:</dt>
        <dd><input type="number" name="calories" value="${meal.calories}"></dd>
    </dl>
    <br>
    <button type="submit">Save</button>
    <button type="reset" onclick="window.history.back()">Cancel</button>
</form>
</body>
</html>
