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

    </style>
</head>

<body>

<!-- Navbar (sit on top) -->
<jsp:include page="/templates/managerMenu.jsp"/>

<!-- Header with profile info -->
<header class="w3-container" id="info">
<br>
</header>
<div class="w3-container w3-padding-16 w3-grayscale w3-card w3-center">
    <form action="updateStatus" method="post">
        <div class="w3-container w3-padding-16">
            <input type="hidden" name="shipment_id" value="${currentShipment.id}"><br>
            <div class="w3-row-padding">
                <select class="w3-input w3-border w3-center" id="newStatus" name="newStatus">
                    <option value="sent"><fmt:message key="option.filter.execution.sent"/></option>
                    <option value="arrived_at_destination"><fmt:message key="option.filter.execution.destination"/></option>
                    <option value="delivered"><fmt:message key="option.filter.execution.delivered"/></option>
                </select><br>
                <p><fmt:message key="update.execution.status"/></p><br>
                <button class="w3-button" type="submit"><fmt:message key="button.yes"/></button>
            </div>
        </div>
    </form>
</div>

</body>
</html>