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

/* Full height image header */
.bgimg-1 {
  background-position: center;
  background-size: cover;
  background-image: url("resources/img/Capture.jpg");
  min-height: 100%;
}

.w3-bar .w3-button {
  padding: 16px;
}

.container {
  padding: 16px;
}

/* The Modal (background) */
.modal {
  display: none; /* Hidden by default */
  position: fixed; /* Stay in place */
  z-index: 1; /* Sit on top */
  left: 0;
  top: 0;
  width: 100%; /* Full width */
  height: 100%; /* Full height */
  overflow: auto; /* Enable scroll if needed */
  background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
  padding-top: 60px;
}

/* Modal Content/Box */
.modal-content {
  background-color: #fefefe;
  margin: 5% auto 15% auto; /* 5% from the top, 15% from the bottom and centered */
  width: 50%; /* Could be more or less, depending on screen size */
}

/* Clear floats */
.clearfix::after {
  content: "";
  clear: both;
  display: table;
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
        <a href="home" class="w3-bar-item w3-button w3-wide"> <fmt:message key="home"/></a>
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
        <!-- Right-sided navbar links -->
        <div class="w3-right w3-hide-small">
            <a href="#about" class="w3-bar-item w3-button"> <fmt:message key="about"/></a>
            <a href="#pricing" class="w3-bar-item w3-button"><i class="fa fa-usd"></i> <fmt:message key="pricing"/></a>
            <a href="#calculator" class="w3-bar-item w3-button"><i class="fa fa-calculator"></i> <fmt:message key="calculator"/></a>
            <a href="#contact" class="w3-bar-item w3-button"><i class="fa fa-envelope"></i> <fmt:message key="contact"/></a>
            <c:choose>
                <c:when test="${sessionScope.user != null}">
                    <a href="profile" class="w3-button w3-bar-item"><i class="fa fa-user-circle-o"></i> <fmt:message key="profile"/></a>
                </c:when>
                <c:otherwise>
                    <button class="w3-button w3-bar-item"
                            onclick="document.getElementById('id01').style.display='block'">
                        <fmt:message key="login"/>
                    </button>
                    <a href="signup_page" class="w3-bar-item w3-button"><fmt:message key="signup"/></a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<!-- Header with full-height image -->
<header class="bgimg-1 w3-display-container w3-grayscale-min" id="home">
    <div class="w3-display-left w3-text-white" style="padding:42px">
        <span class="w3-xxlarge"><fmt:message key="header.info"/></span><br>
        <p><a href="#calculator"
              class="w3-button w3-white w3-padding-large w3-large w3-margin-top w3-opacity w3-hover-opacity-off"><fmt:message key="header.calculate"/></a></p>
    </div>
</header>

<!-- Login window -->
<div id="id01" class="modal w3-container w3-padding-16 w3-grayscale w3-card w3-center">
    <form class="modal-content animate" action="login" method="post" style="border-radius: 8px;">
        <div class="w3-container w3-padding-16">
            <div class="w3-row-padding">
                <button class="w3-button w3-large w3-right w3-opacity-max"
                        onclick="this.form.reset(); document.getElementById('id01').style.display='none'"><i
                        class="fa fa-close"></i>
                </button>
            </div>
            <div class="w3-row-padding">
                <label for="emaillogin" class="w3-left"><fmt:message key="login.label.email"/></label>
            </div>
            <fmt:message key="login.placeholder.email" var="placeholderEmail"/>
            <div class="w3-row-padding">
                <input class="w3-input w3-border" type="email" placeholder="${placeholderEmail}" id="emaillogin"
                       name="emaillogin" required>
            </div>
            <div class="w3-row-padding">
                <br>
            </div>
            <div class="w3-row-padding">
                <label for="passlogin" class="w3-left"><fmt:message key="login.label.password"/></label>
            </div>
            <fmt:message key="login.placeholder.password" var="placeholderPassword"/>
            <div class="w3-row-padding">
                <input class="w3-input w3-border" type="password" placeholder="${placeholderPassword}" id="passlogin"
                       name="passlogin" required>
            </div>
            <div class="w3-row-padding">
                <br>
            </div>
            <div class="w3-row-padding w3-left">
                <input type="checkbox" onclick="myFunction()"> <fmt:message key="login.show.password"/>
            </div>
            <div class="w3-row-padding">
                <br>
            </div>
            <div class="w3-row-padding clearfix">
                <button class="w3-button" type="submit"><fmt:message key="login.button.login"/></button>
                <button class="w3-button" type="button"
                        onclick="this.form.reset(); document.getElementById('id01').style.display='none'">
                    <fmt:message key="login.button.cancel"/>
                </button>
            </div>
        </div>
    </form>
</div>

<!-- About Section -->
<div class="w3-container" style="padding:128px 16px" id="about">
    <h3 class="w3-center"><fmt:message key="about.section.logo"/></h3>
    <p class="w3-center w3-large"><fmt:message key="about.section.key.features"/></p>
    <div class="w3-row-padding w3-center" style="margin-top:64px">
        <div class="w3-quarter">
            <i class="fa fa-compass w3-margin-bottom w3-jumbo w3-center"></i>
            <p class="w3-large"><fmt:message key="about.section.first"/></p>
            <p><fmt:message key="about.section.text1"/></p>
        </div>
        <div class="w3-quarter">
            <i class="fa fa-cubes w3-margin-bottom w3-jumbo"></i>
            <p class="w3-large"><fmt:message key="about.section.second"/></p>
            <p><fmt:message key="about.section.text2"/></p>
        </div>
        <div class="w3-quarter">
            <i class="fa fa-hourglass-o w3-margin-bottom w3-jumbo"></i>
            <p class="w3-large"><fmt:message key="about.section.third"/></p>
            <p><fmt:message key="about.section.text3"/></p>
        </div>
        <div class="w3-quarter">
            <i class="fa fa-cog w3-margin-bottom w3-jumbo"></i>
            <p class="w3-large"><fmt:message key="about.section.fourth"/></p>
            <p><fmt:message key="about.section.text4"/></p>
        </div>
    </div>
</div>

<!-- Pricing Section -->
<div class="w3-container w3-light-grey" style="padding:128px 16px" id="pricing">
    <h3 class="w3-center"><fmt:message key="pricing"/></h3>
    <p class="w3-center w3-large"><fmt:message key="pricing.section.table"/></p>
    <div class="w3-half">
        <table class="w3-table w3-centered w3-border w3-bordered">
            <tr>
                <td class="w3-large"><fmt:message key="pricing.section.weight"/></td>
                <td class="w3-large"><fmt:message key="pricing.section.fare"/></td>
            </tr>
            <tr>
                <td>0.5</td>
                <td>35</td>
            </tr>
            <tr>
                <td>1</td>
                <td>37</td>
            </tr>
            <tr>
                <td>2</td>
                <td>40</td>
            </tr>
            <tr>
                <td>5</td>
                <td>50</td>
            </tr>
        </table>
    </div>
    <div class="w3-half">
        <table class="w3-table w3-centered w3-border w3-bordered">
            <tr>
                <td class="w3-large"><fmt:message key="pricing.section.weight"/></td>
                <td class="w3-large"><fmt:message key="pricing.section.fare"/></td>
            </tr>
            <tr>
                <td>10</td>
                <td>60</td>
            </tr>
            <tr>
                <td>20</td>
                <td>70</td>
            </tr>
            <tr>
                <td>30</td>
                <td>80</td>
            </tr>
            <tr>
                <td>40</td>
                <td>100</td>
            </tr>
        </table>
    </div>
</div>

<!-- Calculator Section -->
<div class="w3-container" style="padding:128px 16px" id="calculator">
    <h3 class="w3-center"><fmt:message key="calculator"/></h3>
    <p class="w3-center w3-large"><fmt:message key="calculator.section.text"/></p>
    <div class="w3-container w3-padding-16 w3-grayscale w3-card w3-center">
        <c:choose>
            <c:when test="${sessionScope.result == null}">
                <form action="calculate" method="post">
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
                    <br>
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
                    <br>
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
                            <input type="number" id="cost" name="cost" placeholder="0.0">
                        </div>
                    </div>
                    <div class="w3-row-padding">
                        <button class="w3-button" type="submit"><fmt:message key="calculator.button.calculate"/></button>
                        <button class="w3-button" type="reset"><fmt:message key="calculator.button.reset"/></button>
                    </div>
                </form>
            </c:when>
            <c:otherwise>
                <div class="w3-container w3-padding-16 w3-center">
                    <div class="w3-half w3-left">
                        <p><fmt:message key="calculator.label.from"/>: ${cityfrom}</p>
                        <p><fmt:message key="calculator.label.to"/>: ${cityto}</p>
                        <p><fmt:message key="calculator.label.freight"/>: ${freighttype}</p>
                        <p><fmt:message key="calculator.label.delivery"/>: ${deliverytype}</p>
                    </div>
                    <div class="w3-half w3-left">
                        <p><fmt:message key="calculator.freight.weight"/>: ${weight}</p>
                        <p><fmt:message key="calculator.freight.length"/>: ${length}</p>
                        <p><fmt:message key="calculator.freight.width"/>: ${width}</p>
                        <p><fmt:message key="calculator.freight.height"/>: ${height}</p>
                    </div>
                </div>
                <div class="w3-container w3-left">
                    <p class="w3-large" style="line-height: 0.5;"><fmt:message key="calculator.label.cost"/>: ${sessionScope.result}</p>
                    <p class="w3-small w3-left" style="line-height: 0.5;"><fmt:message key="calculator.cost.note1"/></p>
                    <br>
                    <p class="w3-small w3-left"><fmt:message key="calculator.cost.note2"/></p>
                </div>
                <div class="w3-container w3-padding-16 w3-center">
                    <a href="signup_page" class="w3-button"><fmt:message key="create.shipment"/></a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>


<!-- Contact Section -->
<div class="w3-container w3-light-grey" style="padding:128px 16px" id="contact">
    <h3 class="w3-center"><fmt:message key="contact.section.logo"/></h3>
    <p class="w3-center w3-large"><fmt:message key="contact.section.text"/></p>
    <div style="margin-top:48px">
        <p><i class="fa fa-map-marker fa-fw w3-xxlarge w3-margin-right"></i><fmt:message key="contact.section.city"/></p>
        <p><i class="fa fa-phone fa-fw w3-xxlarge w3-margin-right"></i><fmt:message key="contact.section.phone"/>: +38 (098) 000-00-00</p>
        <p><i class="fa fa-envelope fa-fw w3-xxlarge w3-margin-right"> </i><fmt:message key="contact.section.email"/>: mail@mail.com</p>
    </div>
</div>

<!-- Footer -->
<footer class="w3-center w3-black w3-padding-64">
    <a href="#home" class="w3-button w3-light-grey"><i class="fa fa-arrow-up w3-margin-right"></i><fmt:message key="footer.section.top"/></a>
</footer>


<script>

 // Get the modal
var modal = document.getElementById('id01');

// When the user clicks anywhere outside the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

</script>

<script src="./resources/js/showPassword.js"></script>
<script src="./resources/js/confirmPassword.js"></script>
<script src="./resources/js/phoneValidation.js"></script>
</body>
</html>
