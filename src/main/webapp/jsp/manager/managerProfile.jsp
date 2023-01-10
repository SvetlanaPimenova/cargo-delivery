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
  float: left;
  border: 1px solid #ccc;
  background-color: #f1f1f1;
  width: 30%;
  height: 550px;
}

/* Style the buttons inside the tab */
.tab button {
  display: block;
  background-color: inherit;
  color: black;
  padding: 22px 16px;
  width: 100%;
  border: none;
  outline: none;
  text-align: left;
  cursor: pointer;
  transition: 0.3s;
  font-size: 17px;
}

/* Change background color of buttons on hover */
.tab button:hover {
  background-color: #ddd;
}

/* Create an active/current "tab button" class */
.tab button.active {
  background-color: #ccc;
}

/* Style the tab content */
.tabcontent {
  float: left;
  padding: 12px 12px;
  border: 1px solid #ccc;
  width: 70%;
  border-left: none;
  height: 550px;
}

#message {
  display:none;
  background: #f1f1f1;
  color: #000;
  position: relative;
  padding: 20px;
  margin-top: 10px;
}

#m1 {
  display:none;
  background: #f1f1f1;
  color: #000;
  position: relative;
  padding: 20px;
  margin-top: 10px;
}

.valid {
  color: green;
}

.valid:before {
  position: relative;
  left: -35px;
  content: "✔";
}

.invalid {
  color: red;
}

.invalid:before {
  position: relative;
  left: -35px;
  content: "✖";
}


/* Clear floats */
.clearfix::after {
  content: "";
  clear: both;
  display: table;
}

</style>
</head>

<body>

<!-- Navbar (sit on top) -->
<jsp:include page="/templates/managerMenu.jsp"/>

<!-- Header with profile info -->
<header class="w3-container" id="info">
    <div class="w3-left" style="padding-top: 58px; padding-right: 58px; padding-bottom: 0px; padding-left: 58px;">
        <h3><i class="fa fa-user-circle-o"></i> <fmt:message key="user.profile"/></h3>
    </div>
</header>

<!-- Vertical tabs -->
<div class="w3-container" style="padding:16px 16px">
    <div class="tab">
        <button class="tablinks" onclick="openTab(event, 'personalData')" id="defaultOpen">
            <i class="fa fa-vcard-o"></i> <fmt:message key="label.personal.data"/></button>
        <button class="tablinks" onclick="openTab(event, 'contacts')">
            <i class="fa fa-phone"></i> <fmt:message key="label.contacts"/></button>
        <button class="tablinks" onclick="openTab(event, 'changePassword')"> <fmt:message key="label.change.password"/></button>
    </div>
    <div id="personalData" class="tabcontent">
        <c:choose>
            <c:when test="${requestScope.message == null}">
                <form action="update" method="post">
                    <input type="hidden" id="updateAction1" name="updateAction" value="personalData">
                    <label for="firstname"><fmt:message key="label.firstname"/>:</label><br>
                    <input class="w3-input w3-border" type="text" name="firstname" id="firstname" value="${sessionScope.user.firstname}"><br>
                    <label for="lastname"><fmt:message key="label.lastname"/>:</label><br>
                    <input class="w3-input w3-border" type="text" name="lastname" id="lastname" value="${sessionScope.user.lastname}"><br>
                    <div class="w3-row-padding w3-center">
                        <button class="w3-button" type="submit"><fmt:message key="button.submit"/></button>
                        <button class="w3-button" type="reset"><fmt:message key="calculator.button.reset"/></button>
                    </div>
                </form>
            </c:when>
            <c:otherwise>
                <div class="w3-center">
                    <h3>${requestScope.message}</h3>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <div id="contacts" class="tabcontent">
        <c:choose>
            <c:when test="${requestScope.message == null}">
                <form action="update" method="post">
                    <input type="hidden" id="updateAction2" name="updateAction" value="contactData">
                    <div class="w3-row-padding w3-left">
                        <div class="w3-half">
                            <label for="phone"><fmt:message key="contact.section.phone"/>:</label><br>
                            <p class="w3-left w3-small">* <fmt:message key="phone.message"/></p><br>
                            <input class="w3-input w3-border" type="tel" value="${sessionScope.user.phone}" name="phone"
                                   id="phone" pattern="^\+380\d{2}\d{3}\d{2}\d{2}$" maxlength="13"
                                   title="+380XXXXXXXXX"><br>
                        </div>
                        <div class="w3-half">
                            <label for="email"><fmt:message key="login.label.email"/>:</label><br>
                            <p class="w3-left w3-small">* <fmt:message key="email.message"/></p><br>
                            <input class="w3-input w3-border" type="text" value="${sessionScope.user.email}" name="email"
                                   id="email">
                        </div>
                    </div>
                    <label for="city"><fmt:message key="label.city"/>:</label><br>
                    <input class="w3-input w3-border" type="text" value="${sessionScope.user.city}" name="city" id="city"><br>
                    <label for="street"><fmt:message key="label.street"/>:</label><br>
                    <input class="w3-input w3-border" type="text" value="${sessionScope.user.street}" name="street" id="street"><br>
                    <label for="postalcode"><fmt:message key="label.postalcode"/>:</label><br>
                    <input class="w3-input w3-border" type="text" value="${sessionScope.user.postalCode}" name="postalcode" id="postalcode"><br>
                    <div class="w3-row-padding w3-center">
                        <button class="w3-button" type="submit"><fmt:message key="button.submit"/></button>
                        <button class="w3-button" type="reset"><fmt:message key="calculator.button.reset"/></button>
                    </div>
                </form>
            </c:when>
            <c:otherwise>
                <div class="w3-center">
                    <h3>${requestScope.message}</h3>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <div id="changePassword" class="tabcontent">
        <c:choose>
            <c:when test="${requestScope.message == null}">
                <form action="update" method="post">
                    <input type="hidden" id="updateAction3" name="updateAction" value="passwordData">
                    <label for="password"><fmt:message key="label.new.password"/>:</label><br>
                    <fmt:message key="login.placeholder.password" var="placeholderPassword"/>
                    <fmt:message key="signup.password.title" var="passwordTitle"/>
                    <fmt:message key="signup.repeat.password" var="repeatPassword"/>
                    <input class="w3-input w3-border" type="password" placeholder="${placeholderPassword}" id="password"
                           name="password" onkeyup="matchPassword()" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                           title="${passwordTitle}"><br>
                    <input class="w3-input w3-border" type="password" placeholder="${repeatPassword}" id="reppass"
                           name="reppass" onkeyup="matchPassword()">
                    <p><span id='wrong_pass'></span></p>
                    <p class="w3-left"><input type="checkbox" onclick="myFunction2(); myFunction3();"> <fmt:message key="login.show.password"/></p><br>
                    <div id="message" class="w3-container w3-small w3-center">
                        <p><fmt:message key="password.must.contain"/>:</p>
                        <p id="letter" class="invalid"><fmt:message key="lowercase.letter"/></p>
                        <p id="capital" class="invalid"><fmt:message key="undercase.letter"/></p>
                        <p id="number" class="invalid"><fmt:message key="password.number"/></p>
                        <p id="passwordlength" class="invalid"><fmt:message key="password.characters"/></p>
                    </div>
                    <br>
                    <div class="w3-row-padding w3-center">
                        <button class="w3-button" type="submit"><fmt:message key="button.submit"/></button>
                        <button class="w3-button" type="reset"><fmt:message key="calculator.button.reset"/></button>
                    </div>
                </form>
            </c:when>
            <c:otherwise>
                <div class="w3-center">
                    <h3>${requestScope.message}</h3>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<script>
function openTab(evt, option) {
  var i, tabcontent, tablinks;
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }
  tablinks = document.getElementsByClassName("tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }
  document.getElementById(option).style.display = "block";
  evt.currentTarget.className += " active";
}

// Get the element with id="defaultOpen" and click on it
document.getElementById("defaultOpen").click();

</script>
<script src="resources/js/showPassword.js"></script>
<script src="resources/js/passwordValidation.js"></script>
<script src="resources/js/confirmPassword.js"></script>
<script src="resources/js/phoneValidation.js"></script>
</body>
</html>