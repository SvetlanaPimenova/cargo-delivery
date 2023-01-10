<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="messages"/>

<style>

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
        <!-- Right-sided navbar links -->
        <div class="w3-right w3-hide-small" style="display: inline;">
            <a href="packages" class="w3-button w3-bar-item" style="display: inline;"><fmt:message key="manager.packages"/></a>
            <a href="reports" class="w3-button w3-bar-item" style="display: inline;"><fmt:message key="manager.reports"/></a>
            <a href="profile" class="w3-bar-item w3-button"><i class="fa fa-user-circle-o"></i> <fmt:message key="profile"/></a>
            <a href="logout" class="w3-button w3-bar-item" style="display: inline;"><fmt:message key="logout"/></a>
        </div>
    </div>
</div>