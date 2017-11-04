<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <h2>Таблица с едой</h2>

    <table>
        <thead>
            <th>Дата/Время</th>
            <th>Описание</th>
            <th>Калории</th>
        </thead>
        <%--со стилями не стал особо заморачиваться, на крастоу нужно время--%>
        <tbody style="color: green">
            <jsp:useBean id="mealWithExceedList" scope="request" type="java.util.List"/>
            <c:forEach items="${mealWithExceedList}" var="meal">
            <tr style=" <c:if test="${meal.exceed}">color: red </c:if>" >
                <td style="padding: 8px">${meal.getDateTime().format(dateTimeFormat)}</td>
                <td style="padding: 8px">${meal.description}</td>
                <td style="padding: 8px">${meal.calories}</td>
            </tr>
            </c:forEach>

            <%--<c:if test="${param.age gt 12}" var="if_less_12">--%>
                <%--Возраст более 12 лет--%>
            <%--</c:if>--%>

        </tbody>
    </table>

</body>
</html>
