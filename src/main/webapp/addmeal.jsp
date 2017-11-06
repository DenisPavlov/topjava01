<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавить еду</title>
</head>
<body>

<form method="POST" action="meals" name="AddMeal">
    Дата : <input type="datetime-local" name="dateTime"
                  value="<c:out value="${meal.getDateTime().format(dateTimeFormat)}" />"/> <br/>
    Описание : <input type="text" name="description" value="<c:out value="${meal.description}" />"/> <br/>
    Калории : <input type="text" name="calories" value="<c:out value="${meal.calories}" />"/> <br/>
    <input type="submit" value="Добавить"/>
</form>

</body>
</html>
