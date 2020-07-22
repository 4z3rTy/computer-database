<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<title><spring:message code="Title"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application -
				<spring:message code="Title"/> </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">id: ${id}</div> 
					<h1><spring:message code="Edit"/></h1>

					<form action="editComputer?Id=${id}" method="POST">  
						<input type="hidden" value="${id}" id="id" name="id" />
						<fieldset>
							<div class="form-group">
								<label for="computerName"><spring:message code="Name"/></label> <input
									type="text" class="form-control" name="computerName"
									id="computerName" placeholder="<spring:message code="Name"/>"> <span
									class="error">${messages['computerName']}</span>
							</div>
							<div class="form-group">
								<label for="introduced"><spring:message code="Intro"/></label> <input
									type="date" class="form-control" name="introduced"
									id="introduced" placeholder="<spring:message code="Intro"/>"> <span
									class="error">${messages['introduced']}</span>
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message code="Disco"/></label> <input
									type="date" class="form-control" name="discontinued"
									id="discontinued" placeholder="<spring:message code="Disco"/>"> <span
									class="error">${messages['discontinued']}</span>
							</div>
							<div class="form-group">
								<label for="cId"><spring:message code="Company"/></label> <select
									class="form-control" name="cId" id="cId">
									<c:forEach var="company" items="${companies}">
										<option value="${company.cId}">
											<c:out value="${company.name}"></c:out>
										</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="<spring:message code="Edit"/>" class="btn btn-primary">
							<span class="success">${messages['success']}</span> or <a
								href="dashboard?Pagenum=1" class="btn btn-default"><spring:message code="Cancel"/></a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>