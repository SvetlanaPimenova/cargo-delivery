<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
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
}

.container {
  padding: 16px;
}

#message {
  display:none;
  background: #f1f1f1;
  color: #000;
  position: relative;
  padding: 20px;
  margin-top: 10px;
}

/* Add a green text color and a checkmark when the requirements are right */
.valid {
  color: green;
}

.valid:before {
  position: relative;
  left: -35px;
  content: "✔";
}

/* Add a red text color and an "x" when the requirements are wrong */
.invalid {
  color: red;
}

.invalid:before {
  position: relative;
  left: -35px;
  content: "✖";
}

/* Add Zoom Animation */
.animate {
  -webkit-animation: animatezoom 0.6s;
  animation: animatezoom 0.6s
}

.dropdown .dropbtn {
  font-size: 16px;
  border: none;
  outline: none;
  color: black;
  margin: 0;
}

.dropdown {
  float: left;
  overflow: hidden;
}
.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
}

.dropdown-content form {
  float: bottom;
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
  text-align: left;
}

.dropdown-content a:hover {
  background-color: #ddd;
}

.dropdown:hover .dropdown-content {
  display: block;
}

@-webkit-keyframes animatezoom {
  from {-webkit-transform: scale(0)}
  to {-webkit-transform: scale(1)}
}

@keyframes animatezoom {
  from {transform: scale(0)}
  to {transform: scale(1)}
}


    </style>

</head>

<body>

<!-- Navbar (sit on top) -->
<div class="w3-top">
    <div class="w3-bar w3-white w3-card" id="myNavbar">
        <a href="home" class="w3-bar-item w3-button w3-wide"><fmt:message key="home"/></a>
        <div class="dropdown">
            <button class="w3-bar-item w3-button dropbtn"><i class="fa fa-globe"></i> <fmt:message key="language"/></button>
            <div class="dropdown-content">
                <form method="get">
                    <select name="locale" onchange='submit()'>
                        <option value="en" ${sessionScope.locale eq 'en' ? 'selected' : ''}> <fmt:message key="en"/></option>
                        <option value="ua" ${sessionScope.locale eq 'ua' ? 'selected' : ''}> <fmt:message key="ua"/></option>
                    </select>
                </form>
            </div>
        </div>
    </div>
</div>

<header class="w3-container" id="info">
    <div class="w3-left" style="padding-top: 58px; padding-right: 58px; padding-bottom: 0px; padding-left: 58px;">
        <h3><i class="fa fa-sign-in"></i> <fmt:message key="signup"/></h3>
    </div>
</header>

<!-- Sign-up window -->
<div class="w3-container w3-padding-16 w3-grayscale w3-card w3-center">
    <form action="signup" method="post" id="regForm">
        <div class="w3-container w3-padding-16">
            <p class="w3-center w3-large"><fmt:message key="signup.text"/></p>
            <hr>
            <p class="w3-left"><fmt:message key="signup.label.name"/>:</p>
            <fmt:message key="signup.placeholder.firstname" var="placeholderFirstname"/>
            <fmt:message key="signup.placeholder.lastname" var="placeholderLastname"/>
            <input class="w3-input w3-border" type="text" placeholder="${placeholderFirstname}" name="firstname"
                   id="firstname" required><br>
            <input class="w3-input w3-border" type="text" placeholder="${placeholderLastname}" name="lastname"
                   id="lastname" required><br>
            <p class="w3-left"><fmt:message key="signup.label.contact"/>:</p>
            <fmt:message key="login.placeholder.email" var="placeholderEmail"/>
            <input class="w3-input w3-border" type="text" placeholder="${placeholderEmail}" name="email"
                   id="email" required>
            <c:if test="${errorEmail != null}">
                <p class="w3-left" style="color: red;">${errorEmail}</p>
            </c:if><br>
            <fmt:message key="signup.placeholder.phone" var="placeholderPhone"/>
            <input class="w3-input w3-border" type="tel" placeholder="${placeholderPhone}" name="phone"
                   id="phone" pattern="^\+?380\d{2}\d{3}\d{2}\d{2}$" maxlength="13"
                   title="+380XXXXXXXXX" required>
            <c:if test="${errorPhone != null}">
                <p class="w3-left" style="color: red;">${errorPhone}</p>
            </c:if><br>
            <p class="w3-left"><fmt:message key="signup.label.address"/>:</p>
            <fmt:message key="signup.placeholder.city" var="placeholderCity"/>
            <fmt:message key="signup.placeholder.street" var="placeholderStreet"/>
            <fmt:message key="signup.placeholder.postalcode" var="placeholderPostalcode"/>

            <input class="w3-input w3-border" type="text" placeholder="${placeholderCity}" name="city" id="city"
                   required><br>
            <input class="w3-input w3-border" type="text" placeholder="${placeholderStreet}" name="street"
                   id="street" required><br>
            <input class="w3-input w3-border" type="text" placeholder="${placeholderPostalcode}"
                   name="postalcode" id="postalcode" required><br>
            <p class="w3-left"><fmt:message key="login.label.password"/>:</p>
            <fmt:message key="login.placeholder.password" var="placeholderPassword"/>
            <fmt:message key="signup.password.title" var="passwordTitle"/>
            <fmt:message key="signup.repeat.password" var="repeatPassword"/>
            <input class="w3-input w3-border" type="password" placeholder="${placeholderPassword}" id="password"
                   name="password" onkeyup="matchPassword()" pattern="(?=.*\d)(?=.*[a-zа-я])(?=.*[A-ZА-Я]).{8,}"
                   title="${passwordTitle}"
                   required><br>
            <input class="w3-input w3-border" type="password" placeholder="${repeatPassword}" id="reppass"
                   name="reppass"
                   required onkeyup="matchPassword()"><br>
            <span id='wrong_pass'></span><br>
            <p class="w3-left"><input type="checkbox" onclick="myFunction2(); myFunction3();"> <fmt:message key="login.show.password"/></p>
            <br>
            <div id="message" class="w3-container w3-small">
                <p><fmt:message key="password.must.contain"/>:</p>
                <p id="letter" class="invalid"><fmt:message key="lowercase.letter"/></p>
                <p id="capital" class="invalid"><fmt:message key="undercase.letter"/></p>
                <p id="number" class="invalid"><fmt:message key="password.number"/></p>
                <p id="passwordlength" class="invalid"><fmt:message key="password.characters"/></p>
            </div>
            <div class="w3-center">
                <button class="w3-button" type="submit"><fmt:message key="button.submit"/></button>
            </div>
        </div>
    </form>
</div>

<script>
var myInput = document.getElementById("password");
var letter = document.getElementById("letter");
var capital = document.getElementById("capital");
var number = document.getElementById("number");
var length = document.getElementById("passwordlength");

// When the user clicks on the password field, show the message box
myInput.onfocus = function() {
  document.getElementById("message").style.display = "block";
}

// When the user clicks outside of the password field, hide the message box
myInput.onblur = function() {
  document.getElementById("message").style.display = "none";
}

// When the user starts to type something inside the password field
myInput.onkeyup = function() {
  // Validate lowercase letters
  var lowerCaseLetters = /[a-zа-я]/g;
  if(myInput.value.match(lowerCaseLetters)) {
    letter.classList.remove("invalid");
    letter.classList.add("valid");
  } else {
    letter.classList.remove("valid");
    letter.classList.add("invalid");
}

  // Validate capital letters
  var upperCaseLetters = /[A-ZА-Я]/g;
  if(myInput.value.match(upperCaseLetters)) {
    capital.classList.remove("invalid");
    capital.classList.add("valid");
  } else {
    capital.classList.remove("valid");
    capital.classList.add("invalid");
  }

  // Validate numbers
  var numbers = /[0-9]/g;
  if(myInput.value.match(numbers)) {
    number.classList.remove("invalid");
    number.classList.add("valid");
  } else {
    number.classList.remove("valid");
    number.classList.add("invalid");
  }

  // Validate length
  if(myInput.value.length >= 8) {
    length.classList.remove("invalid");
    length.classList.add("valid");
  } else {
    length.classList.remove("valid");
    length.classList.add("invalid");
  }
}

</script>

<script src="resources/js/showPassword.js"></script>
<script src="resources/js/confirmPassword.js"></script>
<script src="resources/js/phoneValidation.js"></script>
</body>
</html>
