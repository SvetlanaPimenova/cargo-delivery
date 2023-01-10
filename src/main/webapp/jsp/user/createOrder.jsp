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

/* Style the tab */
.tab {
  overflow: hidden;
  border: 1px solid #ccc;
  background-color: #f1f1f1;
}

/* Style the buttons inside the tab */
.tab button {
  background-color: inherit;
  float: left;
  border: none;
  outline: none;
  cursor: pointer;
  padding: 14px 16px;
  transition: 0.3s;
  font-size: 17px;
}

/* Change background color of buttons on hover */
.tab button:hover {
  background-color: #ddd;
}

/* Create an active/current tablink class */
.tab button.active {
  background-color: #ccc;
}

/* Style the tab content */
.tabcontent {
  display: none;
  padding: 6px 12px;
  border: 1px solid #ccc;
  border-top: none;
}


    </style>

</head>

<body>

<!-- Navbar (sit on top) -->
<jsp:include page="/templates/userMenu.jsp"/>

<!-- Header with profile info -->
<header class="w3-container" id="info">
    <div class="w3-left" style="padding-top: 58px; padding-right: 58px; padding-bottom: 0px; padding-left: 58px;">
        <h3><i class="fa fa-edit"></i> <fmt:message key="create.shipment"/></h3>
    </div>
</header>

<!-- Create order form -->
<div class="w3-container" style="padding:16px 16px">
    <c:choose>
        <c:when test="${newOrder == null}">
            <form action="createOrder" method="post">
                <div class="w3-row-padding w3-margin-bottom">
                    <h4 class="w3-left"><fmt:message key="shipping.options"/></h4><br>
                </div>
                <div class="w3-row-padding">
                    <div class="w3-third w3-margin-bottom">
                        <label><fmt:message key="calculator.section.route"/></label>
                    </div>
                    <div class="w3-third w3-margin-bottom">
                        <label for="cityfrom"><fmt:message key="calculator.label.from"/></label>
                        <fmt:message key="calculator.placeholder.route" var="placeholderRoute"/>
                        <fmt:message key="calculator.city.vinnytsia" var="cityVinnytsia"/>
                        <fmt:message key="calculator.city.dnipro" var="cityDnipro"/>
                        <fmt:message key="calculator.city.zaporizhzhia" var="cityZaporizhzhia"/>
                        <fmt:message key="calculator.city.kyiv" var="cityKyiv"/>
                        <fmt:message key="calculator.city.kryvyi.rih" var="cityKryvyiRih"/>
                        <fmt:message key="calculator.city.lviv" var="cityLviv"/>
                        <fmt:message key="calculator.city.mykolayiv" var="cityMykolayiv"/>
                        <fmt:message key="calculator.city.odesa" var="cityOdesa"/>
                        <fmt:message key="calculator.city.poltava" var="cityPoltava"/>
                        <fmt:message key="calculator.city.kharkiv" var="cityKharkiv"/>

                        <input class="w3-input w3-border" list="citiesfrom" placeholder="${placeholderRoute}" id="cityfrom"
                               name="cityfrom" required>
                        <datalist id="citiesfrom">
                            <option value="${cityVinnytsia}">
                            <option value="${cityDnipro}">
                            <option value="${cityZaporizhzhia}">
                            <option value="${cityKyiv}">
                            <option value="${cityKryvyiRih}">
                            <option value="${cityLviv}">
                            <option value="${cityMykolayiv}">
                            <option value="${cityOdesa}">
                            <option value="${cityPoltava}">
                            <option value="${cityKharkiv}">
                        </datalist>
                    </div>
                    <div class="w3-third w3-margin-bottom">
                        <label for="cityto"><fmt:message key="calculator.label.to"/></label>
                        <input class="w3-input w3-border" list="citiesto" placeholder="${placeholderRoute}" id="cityto"
                               name="cityto"
                               required>
                        <datalist id="citiesto">
                            <option value="${cityVinnytsia}">
                            <option value="${cityDnipro}">
                            <option value="${cityZaporizhzhia}">
                            <option value="${cityKyiv}">
                            <option value="${cityKryvyiRih}">
                            <option value="${cityLviv}">
                            <option value="${cityMykolayiv}">
                            <option value="${cityOdesa}">
                            <option value="${cityPoltava}">
                            <option value="${cityKharkiv}">
                        </datalist>
                    </div>
                </div>
                <div class="w3-row-padding">
                    <div class="w3-third w3-margin-bottom">
                        <br>
                    </div>
                    <div class="w3-twothird w3-margin-bottom">
                        <p class="w3-small w3-left-align"><fmt:message key="calculator.route.note"/></p>
                    </div>
                </div>
                <div class="w3-row-padding">
                    <div class="w3-third w3-margin-bottom">
                        <label for="freighttype"><fmt:message key="calculator.label.freight"/></label>
                    </div>
                    <div class="w3-twothird w3-margin-bottom">
                        <select class="w3-input w3-border" id="freighttype" name="freighttype">
                            <option value="goods"><fmt:message key="calculator.freight.goods"/></option>
                            <option value="glass"><fmt:message key="calculator.freight.glass"/></option>
                            <option value="compact"><fmt:message key="calculator.freight.documents"/></option>
                        </select>
                    </div>
                </div>
                <div class="w3-row-padding">
                    <div class="w3-third w3-margin-bottom">
                        <label for="deliverytype"><fmt:message key="calculator.label.delivery"/></label>
                    </div>
                    <div class="w3-twothird w3-margin-bottom">
                        <select class="w3-input w3-border" id="deliverytype" name="deliverytype">
                            <option value="to_the_branch"><fmt:message key="calculator.delivery.branch"/></option>
                            <option value="courier"><fmt:message key="calculator.delivery.courier"/></option>
                        </select>
                    </div>
                </div>
                <div class="w3-row-padding">
                    <div class="w3-third w3-margin-bottom">
                        <label for="weight"><fmt:message key="calculator.freight.weight"/></label>
                    </div>
                    <div class="w3-twothird w3-margin-bottom">
                        <div class="w3-third w3-margin-bottom">
                            <label for="length"><fmt:message key="calculator.freight.length"/></label>
                        </div>
                        <div class="w3-third w3-margin-bottom">
                            <label for="width"><fmt:message key="calculator.freight.width"/></label>
                        </div>
                        <div class="w3-third w3-margin-bottom">
                            <label for="height"><fmt:message key="calculator.freight.height"/></label>
                        </div>
                    </div>
                </div>
                <div class="w3-row-padding">
                    <div class="w3-third w3-margin-bottom">
                        <input type="number" id="weight" name="weight" placeholder="0.0" step="0.1" min="0.1"
                               max="100.0"
                               required>
                    </div>
                    <div class="w3-twothird w3-margin-bottom">
                        <div class="w3-third w3-margin-bottom">
                            <input type="number" id="length" name="length" placeholder="0.0" step="0.1" min="0.1"
                                   max="10000.0" required>
                        </div>
                        <div class="w3-third w3-margin-bottom">
                            <input type="number" id="width" name="width" placeholder="0.0" step="0.1" min="0.1"
                                   max="10000.0" required>
                        </div>
                        <div class="w3-third w3-margin-bottom">
                            <input type="number" id="height" name="height" placeholder="0.0" step="0.1" min="0.1"
                                   max="10000.0" required>
                        </div>
                    </div>
                </div>
                <div class="w3-row-padding">
                    <div class="w3-third w3-margin-bottom">
                        <label for="cost"><fmt:message key="calculator.label.cost"/></label>
                    </div>
                    <div class="w3-twothird w3-margin-bottom">
                        <input type="number" id="cost" name="cost" placeholder="0.0" min="0.0">
                    </div>
                </div>
                <hr>
                <div class="w3-row-padding w3-margin-bottom">
                    <h4 class="w3-left"><fmt:message key="shipping.receiver.info"/></h4><br>
                </div>
                <div class="w3-row-padding">
                    <div class="w3-half w3-margin-bottom">
                        <label for="rfname"><fmt:message key="label.firstname"/>:</label><br>
                        <input class="w3-input w3-border" type="text" id="rfname" name="rfname">
                    </div>
                    <div class="w3-half w3-margin-bottom">
                        <label for="rlname"><fmt:message key="label.lastname"/>:</label><br>
                        <input class="w3-input w3-border" type="text" id="rlname" name="rlname">
                    </div>
                </div>
                <div class="w3-row-padding">
                    <div class="w3-half w3-margin-bottom">
                        <label for="rphone"><fmt:message key="contact.section.phone"/>:</label><br>
                        <input class="w3-input w3-border" type="tel" name="rphone"
                               id="rphone" pattern="^\+?38?(0\d{2}\d{3}\d{2}\d{2})$" maxlength="13">
                    </div>
                    <div class="w3-half w3-margin-bottom">
                        <br>
                    </div>
                </div>
                <div class="w3-row-padding">
                    <div class="w3-half w3-margin-bottom">
                        <label for="rstreet"><fmt:message key="label.street"/>:</label><br>
                        <input class="w3-input w3-border" type="text" id="rstreet" name="rstreet"><br>
                    </div>
                    <div class="w3-half w3-margin-bottom">
                        <label for="rpcode"><fmt:message key="label.postalcode"/>:</label><br>
                        <input class="w3-input w3-border" type="text" id="rpcode" name="rpcode">
                    </div>
                </div>
                <div class="w3-row-padding w3-center">
                    <button class="w3-button" type="submit"><fmt:message key="button.submit"/></button>
                </div>
            </form>
        </c:when>
        <c:otherwise>
            <p><fmt:message key="shipping.successfully"/> <a href="orders"><fmt:message key="here"/></a></p>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>