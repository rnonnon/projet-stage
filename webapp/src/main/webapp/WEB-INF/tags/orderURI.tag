<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="goTo" required="true"%>
<%@ attribute name="orderBy" required="true" %>
<%@ attribute name="search" required="true" %>

<c:url var="urlOrder" value="dashboard">
	<c:param name="goTo" value="${goTo}"/>
	<c:param name="search" value="${search}"/>
	<c:param name="orderBy" value="${orderBy}"/>
</c:url>

<a href="${urlOrder}"><jsp:doBody /></a>