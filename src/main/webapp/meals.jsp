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
        <%--со стилями не стал особо заморачиваться, на крастоу нужно время--%>
        <tbody style="color: green">

        <c:forEach items="${mealWithExceedList}" var="meal">
            <%--использовать тернарный оператор--%>
            <tr style=" <c:if test="${meal.exceed}">color: red </c:if>" >
                <td style="padding: 8px">${meal.getDateTime().format(dateTimeFormat)}</td>
                <td style="padding: 8px">${meal.description}</td>
                <td style="padding: 8px">${meal.calories}</td>
                <td><a href="meals?action=edit&mealId=<c:out value="${meal.id}"/>">Изменить</a></td>
                <td><a href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">Удалить</a></td>
            </tr>
        </c:forEach>

        <%--<c:if test="${param.age gt 12}" var="if_less_12">--%>
        <%--Возраст более 12 лет--%>
        <%--</c:if>--%>

        </tbody>
    </table>
</body>
</html>
