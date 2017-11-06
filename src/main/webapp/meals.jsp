<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Еда</h1>


    <h2><a href="${pageContext.request.contextPath}/addmeal.jsp">Добавить</a></h2>

    <table>
        <thead>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
        </thead>

        <tbody>

        <c:forEach items="${mealWithExceedList}" var="meal">
            <tr style="color: <c:out value="${ meal.exceed ?  'red': 'green'}"/>" >
                <td style="padding: 8px">${meal.getDateTime().format(dateTimeFormat)}</td>
                <td style="padding: 8px">${meal.description}</td>
                <td style="padding: 8px">${meal.calories}</td>
                <td><a href="meals?action=edit&mealId=<c:out value="${meal.id}"/>">Изменить</a></td>
                <td><a href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">Удалить</a></td>
            </tr>
        </c:forEach>


        </tbody>
    </table>
</body>
</html>
