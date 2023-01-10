<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<html xmlns:c="http://java.sun.com/jsp/jstl/core">
<head>
    <title>Cargo Delivery Service</title>
    <link rel="icon" type="image/x-icon" href="resources/img/favicon.ico">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>

body,h1,h2,h3,h4,h5,h6 {font-family: "Raleway", sans-serif}
body, html {
  height: 100%;
  line-height: 1.8;
}

.w3-bar .w3-button {
  padding: 16px;
  display: inline;
}

.container {
  padding: 16px;
}

</style>

</head>

<body>

<!-- Navbar (sit on top) -->
<jsp:include page="/templates/managerMenu.jsp"/>

<!-- Header with profile info -->
<header class="w3-container" id="info">
    <div class="w3-left" style="padding-top: 58px; padding-right: 58px; padding-bottom: 0px; padding-left: 58px;">
        <h3><i class="fa fa-tasks"></i> <fmt:message key="manager.reports"/></h3>
    </div>
</header>

<div class="w3-container" style="padding:16px 16px">
    <div class="w3-row-padding">
        <form action="reports" method="get">
            <div class="w3-row-padding">
                <div class="w3-quarter w3-left">
                    <label for="searchParameter"><fmt:message key="label.search.by"/>:</label>
                </div>
            </div>
            <br>
            <div class="w3-row-padding">
                <div class="w3-third w3-left">
                    <select class="w3-input w3-border" id="searchParameter" name="searchParameter">
                        <option value="sender"><fmt:message key="option.sender"/></option>
                        <option value="city_from"><fmt:message key="option.city.from"/></option>
                        <option value="city_to"><fmt:message key="option.city.to"/></option>
                        <option value="date"><fmt:message key="option.date"/></option>
                    </select>
                </div>
                <div class="w3-third w3-left">
                    <input type="date" class="w3-input w3-border" id="calendar" name="calendar"
                           onchange="this.form.submit()">
                </div>
                <div class="w3-third w3-left"><br></div>
            </div>
            <br>
            <div class="w3-row-padding">
                <div class="w3-left">
                    <input type="text" name="search" placeholder="Search..">
                    <button class="w3-button" type="submit"><i class="fa fa-search"></i></button>
                </div>
            </div>
        </form>
        <p class="w3-small">*<fmt:message key="search.sender.note"/> +380XXXXXXXXX.</p>
    </div>
</div>
<hr>
<div class="w3-container" style="padding:16px 16px">
    <c:choose>
        <c:when test="${empty requestScope.list && requestScope.user == null}">
            <p class="w3-center"><fmt:message key="search.nothing.found"/></p>
        </c:when>
        <c:otherwise>
            <c:if test="${not empty requestScope.list}">
                <c:set var="searchParameter" value="${searchParameter}" scope="request"/>
                <c:set var="parameter" value="${parameter}" scope="request"/>
                <form action="pdf" method="get" target="_blank">
                    <input type="hidden" name="searchParameter" value="${searchParameter}"/>
                    <input type="hidden" name="parameter" value="${parameter}"/>
                    <button type="submit" class="w3-button"><i class="fa fa-file-pdf-o"></i> <fmt:message key="button.download"/></button>
                </form>
                <br>
                <div class="w3-container w3-padding-16 w3-grayscale w3-card w3-center">
                    <table class="w3-table w3-centered w3-border w3-bordered w3-striped">
                        <tr>
                            <td class="w3-border-right"><fmt:message key="number.character"/></td>
                            <td class="w3-border-right"><fmt:message key="table.shipment.date"/></td>
                            <td class="w3-border-right"><fmt:message key="calculator.label.from"/></td>
                            <td class="w3-border-right"><fmt:message key="calculator.label.to"/></td>
                            <td class="w3-border-right"><fmt:message key="table.freight.info"/></td>
                            <td class="w3-border-right"><fmt:message key="table.total.cost"/></td>
                            <td class="w3-border-right"><fmt:message key="calculator.label.delivery"/></td>
                            <td class="w3-border-right"><fmt:message key="option.sender"/></td>
                            <td class="w3-border-right"><fmt:message key="table.receiver"/></td>
                            <td class="w3-border-right"><fmt:message key="option.filter.payment"/></td>
                            <td class="w3-border-right"><fmt:message key="option.filter.execution"/></td>
                        </tr>
                        <c:forEach var="order" items="${requestScope.list}" varStatus="orderStatus">
                            <tr>
                                <td class="w3-border-right">${orderStatus.index + 1}</td>
                                <td class="w3-border-right">${order.orderDate}</td>
                                <td class="w3-border-right">${order.cityFrom}</td>
                                <td class="w3-border-right">${order.receiver.city}</td>
                                <td class="w3-border-right">${order.freight.type}, ${order.freight.weight} kg,<br>
                                    ${order.freight.length} x ${order.freight.width} x ${order.freight.height} cm,<br>
                                    <fmt:message key="calculator.label.cost"/>: ${order.freight.estimatedCost}
                                </td>
                                <td class="w3-border-right">${order.totalCost}</td>
                                <td class="w3-border-right">${order.deliveryType}</td>
                                <td class="w3-border-right">${order.sender.firstname} ${order.sender.lastname},<br>
                                    ${order.sender.email},<br>
                                    ${order.sender.phone},<br>
                                    ${order.sender.city}, ${order.sender.street},<br>
                                    ${order.sender.postalCode}
                                </td>
                                <td class="w3-border-right">${order.receiver.firstname} ${order.receiver.lastname},<br>
                                    ${order.receiver.phone},<br>
                                    ${order.receiver.street}, ${order.receiver.postal_code}
                                </td>
                                <td class="w3-border-right">${order.paymentStatus}</td>
                                <td class="w3-border-right">${order.executionStatus}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </c:if>
            <c:if test="${requestScope.user != null}">
                <div class="w3-container w3-padding-16 w3-grayscale w3-card w3-center">
                    <div class="w3-container">
                        <div class="w3-third">
                            <h5 class="w3-left">${user.firstname} ${user.lastname}</h5>
                        </div>
                        <div class="w3-twothird"><br></div>
                    </div>
                    <div class="w3-container w3-left">
                        <p>${user.phone}, ${user.email}</p>
                        <p>${user.city}, ${user.street}, ${user.postalCode}.</p>
                        <p><fmt:message key="account.current"/> ${user.account} <fmt:message key="uah"/>.</p>
                    </div>
                    <c:choose>
                        <c:when test="${empty requestScope.userShipments}">
                            <p class="w3-center"><fmt:message key="user.no.shipments"/></p>
                        </c:when>
                        <c:otherwise>
                            <table class="w3-table w3-centered w3-border w3-bordered w3-striped">
                                <tr>
                                    <td class="w3-border-right"><fmt:message key="number.character"/></td>
                                    <td class="w3-border-right"><fmt:message key="table.shipment.date"/></td>
                                    <td class="w3-border-right"><fmt:message key="calculator.label.from"/></td>
                                    <td class="w3-border-right"><fmt:message key="calculator.label.to"/></td>
                                    <td class="w3-border-right"><fmt:message key="table.freight.info"/></td>
                                    <td class="w3-border-right"><fmt:message key="table.total.cost"/></td>
                                    <td class="w3-border-right"><fmt:message key="calculator.label.delivery"/></td>
                                    <td class="w3-border-right"><fmt:message key="table.receiver"/></td>
                                    <td class="w3-border-right"><fmt:message key="option.filter.payment"/></td>
                                    <td class="w3-border-right"><fmt:message key="option.filter.execution"/></td>
                                </tr>
                                <c:forEach var="shipment" items="${requestScope.userShipments}" varStatus="shipmentStatus">
                                    <tr>
                                        <td class="w3-border-right">${shipmentStatus.index + 1}</td>
                                        <td class="w3-border-right">${shipment.orderDate}</td>
                                        <td class="w3-border-right">${shipment.cityFrom}</td>
                                        <td class="w3-border-right">${shipment.receiver.city}</td>
                                        <td class="w3-border-right">${shipment.freight.type}, ${shipment.freight.weight}
                                            kg,<br>
                                            ${shipment.freight.length} x ${shipment.freight.width} x
                                            ${shipment.freight.height} cm,<br>
                                            <fmt:message key="calculator.label.cost"/>: ${shipment.freight.estimatedCost}
                                        </td>
                                        <td class="w3-border-right">${shipment.totalCost}</td>
                                        <td class="w3-border-right">${shipment.deliveryType}</td>
                                        <td class="w3-border-right">${shipment.receiver.firstname}
                                            ${shipment.receiver.lastname},<br>
                                            ${shipment.receiver.phone},<br>
                                            ${shipment.receiver.street}, ${shipment.receiver.postal_code}
                                        </td>
                                        <td class="w3-border-right">${shipment.paymentStatus}</td>
                                        <td class="w3-border-right">${shipment.executionStatus}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>