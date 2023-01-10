<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>
<html lang="en">
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
.pagination {
  display: inline-block;
}

.pagination a {
  color: black;
  float: left;
  padding: 8px 16px;
  text-decoration: none;
}

.pagination a.active {
  background-color: #a1a1a1;
  color: white;
  border-radius: 5px;
}

.pagination a:hover:not(.active) {
  background-color: #ddd;
  border-radius: 5px;
}
    </style>
</head>

<body>

<!-- Navbar (sit on top) -->
<jsp:include page="/templates/managerMenu.jsp"/>

<!-- Header with profile info -->
<header class="w3-container" id="info">
    <div class="w3-left" style="padding-top: 58px; padding-right: 58px; padding-bottom: 0px; padding-left: 58px;">
        <h3><i class="fa fa-cubes"></i> <fmt:message key="manager.packages"/></h3>
    </div>
</header>

<!-- List of packages -->

<div class="w3-container" style="padding:16px 16px">
    <div class="w3-row-padding">
        <form action="packages" method="get">
            <div class="w3-row-padding">
                <div class="w3-half w3-margin-bottom w3-row-padding">
                    <div class="w3-third w3-margin-bottom">
                        <select class="w3-input w3-border" id="sort" name="sort" onchange="this.form.submit()">
                            <option value="" selected><fmt:message key="option.sort"/></option>
                            <option value="cost_asc"><fmt:message key="option.sort.cost.low"/></option>
                            <option value="cost_desc"><fmt:message key="option.sort.cost.high"/></option>
                            <option value="date_asc"><fmt:message key="option.sort.date.old"/></option>
                            <option value="date_desc"><fmt:message key="option.sort.date.new"/></option>
                        </select>
                    </div>
                    <div class="w3-third w3-margin-bottom">
                        <select class="w3-input w3-border" id="deliveryFilter" name="deliveryFilter">
                            <option value="" selected><fmt:message key="calculator.label.delivery"/></option>
                            <option value="to_the_branch"><fmt:message key="calculator.delivery.branch"/></option>
                            <option value="courier"><fmt:message key="calculator.delivery.courier"/></option>
                        </select>
                    </div>
                    <div class="w3-third w3-margin-bottom">
                        <select class="w3-input w3-border" id="freightFilter" name="freightFilter">
                            <option value="" selected><fmt:message key="calculator.label.freight"/></option>
                            <option value="goods"><fmt:message key="calculator.freight.goods"/></option>
                            <option value="glass"><fmt:message key="calculator.freight.glass"/></option>
                            <option value="compact"><fmt:message key="option.filter.freight.compact"/></option>
                        </select>
                    </div>
                </div>
                <div class="w3-half w3-margin-bottom w3-row-padding">
                    <div class="w3-third w3-margin-bottom">
                        <select class="w3-input w3-border" id="paymentFilter" name="paymentFilter">
                            <option value="" selected><fmt:message key="option.filter.payment"/></option>
                            <option value="paid"><fmt:message key="option.filter.payment.paid"/></option>
                            <option value="unpaid"><fmt:message key="option.filter.payment.unpaid"/></option>
                        </select>
                    </div>
                    <div class="w3-third w3-margin-bottom">
                        <select class="w3-input w3-border" id="executionFilter" name="executionFilter">
                            <option value="" selected><fmt:message key="option.filter.execution"/></option>
                            <option value="in_processing"><fmt:message key="option.filter.execution.processing"/></option>
                            <option value="formed"><fmt:message key="option.filter.execution.formed"/></option>
                            <option value="sent"><fmt:message key="option.filter.execution.sent"/></option>
                            <option value="arrived_at_destination"><fmt:message key="option.filter.execution.destination"/></option>
                            <option value="delivered"><fmt:message key="option.filter.execution.delivered"/></option>
                        </select>
                    </div>
                    <div class="w3-third w3-margin-bottom">
                        <div class="w3-row-padding w3-center">
                            <button class="w3-button" type="submit"><fmt:message key="button.submit"/></button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="w3-row-padding">
                <input type="hidden" name="currentPage" value="1">
                <label for="recordsPerPage"><fmt:message key="option.records"/>:</label>
                <select id="recordsPerPage" name="recordsPerPage">
                    <option value="4" selected>4</option>
                    <option value="8">8</option>
                </select>
                <c:set var="recordsPerPage" value="${recordsPerPage}" scope="page"/>
            </div>
        </form>
    </div>
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
            <td><br></td>
        </tr>
        <c:forEach var="shipment" items="${requestScope.shipments}" varStatus="shipmentStatus">
            <tr>
                <td class="w3-border-right">${(currentPage - 1)*recordsPerPage + shipmentStatus.index + 1}</td>
                <td class="w3-border-right">${shipment.orderDate}</td>
                <td class="w3-border-right">${shipment.cityFrom}</td>
                <td class="w3-border-right">${shipment.receiver.city}</td>
                <td class="w3-border-right">${shipment.freight.type}, ${shipment.freight.weight} kg,<br>
                    ${shipment.freight.length} x ${shipment.freight.width} x ${shipment.freight.height} cm,<br>
                    <fmt:message key="calculator.label.cost"/>: ${shipment.freight.estimatedCost}
                </td>
                <td class="w3-border-right">${shipment.totalCost}</td>
                <td class="w3-border-right">${shipment.deliveryType}</td>
                <td class="w3-border-right">${shipment.sender.firstname} ${shipment.sender.lastname},<br>
                    ${shipment.sender.email},<br>
                    ${shipment.sender.phone},<br>
                    ${shipment.sender.city}, ${shipment.sender.street},<br>
                    ${shipment.sender.postalCode}
                </td>
                <td class="w3-border-right">${shipment.receiver.firstname} ${shipment.receiver.lastname},<br>
                    ${shipment.receiver.phone},<br>
                    ${shipment.receiver.street}, ${shipment.receiver.postal_code}
                </td>
                <td class="w3-border-right">${shipment.paymentStatus}</td>
                <td class="w3-border-right">${shipment.executionStatus}</td>
                <td>
                    <c:set var="shipmentId" value="${shipment.id}" scope="request"/>
                    <form action="updateShipment_page" method="get">
                        <input type="hidden" id="shipmentId" name="shipment_id" value="${shipmentId}"><br>
                        <button class="w3-button"><i class="fa fa-edit"></i></button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<nav class="w3-center" style="padding:16px 16px">
    <div class="pagination w3-center">
        <c:if test="${currentPage != 1}">
            <a href="packages?recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}&sort=${param.sort}&deliveryFilter=${param.deliveryFilter}&freightFilter=${param.freightFilter}&paymentFilter=${param.paymentFilter}&executionFilter=${param.executionFilter}">&laquo;</a>
        </c:if>
        <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <a class="active">${i}</a>
                </c:when>
                <c:otherwise>
                    <a href="packages?recordsPerPage=${recordsPerPage}&currentPage=${i}&sort=${param.sort}&deliveryFilter=${param.deliveryFilter}&freightFilter=${param.freightFilter}&paymentFilter=${param.paymentFilter}&executionFilter=${param.executionFilter}">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${currentPage lt noOfPages}">
            <a href="packages?recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}&sort=${param.sort}&deliveryFilter=${param.deliveryFilter}&freightFilter=${param.freightFilter}&paymentFilter=${param.paymentFilter}&executionFilter=${param.executionFilter}">&raquo;</a>
        </c:if>
    </div>
</nav>


</body>
</html>