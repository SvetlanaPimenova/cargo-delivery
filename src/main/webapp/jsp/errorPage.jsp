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
                <form method="post">
                    <select name="locale" onchange='submit()'>
                        <option value="en" ${sessionScope.locale eq 'en' ? 'selected' : ''}> <fmt:message key="en"/></option>
                        <option value="ua" ${sessionScope.locale eq 'ua' ? 'selected' : ''}> <fmt:message key="ua"/></option>
                    </select>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Header with profile info -->
<header class="w3-container" id="home">
    <div class="w3-center" style="padding-top: 58px; padding-right: 58px; padding-bottom: 0px; padding-left: 58px;">
        <h3><fmt:message key="error.message"/></h3>
        <h6>${errorMessage}</h6>
        <h6><fmt:message key="error.return"/> <a href="home"><fmt:message key="home"/></a></h6>
    </div>
</header>
</body>
</html>