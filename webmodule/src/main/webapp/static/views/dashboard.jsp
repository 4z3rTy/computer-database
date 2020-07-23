<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<title><spring:message code="Title"/></title>
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - <spring:message code="Title"/> </a>
		</div>
		<a href="dashboard?lang=en">English </a> | <a href="dashboard?lang=fr">French </a>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${sum} <spring:message code="HomeTitle"/></h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="dashboard" method="GET"
						class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="<spring:message code="Search"/>" /> <input
							type="submit" id="searchsubmit" name="searchName"
							value="<spring:message code="Add"/>" class="btn btn-primary" /> <input
							type="submit" id="searchsubmit2" name="searchIntro"
							value="<spring:message code="FilterIntro"/>" class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer"><spring:message code="Add"/></a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();"><spring:message code="Edit"/></a>

				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><spring:message code="Name"/></th>
						<th><spring:message code="Intro"/></th>
						<th><spring:message code="Disco"/></th>
						<th><spring:message code="Company"/></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${compList}" var="compList">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${compList.id}"></td>
							<td><a href="editComputer?Id=${compList.id }"> <c:out
										value="${compList.computerName}" />
							</a></td>
							<td>${compList.introduced}</td>
							<td>${compList.discontinued}</td>
							<td>${compList.companyName}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<c:if test="${currentPage > 1}">
					<li><a
						href="dashboard?search=${search}&searchType=${searchType}&currentPage=${currentPage-1}&pageAmount=${pageAmount}"
						aria-label="Previous"> <span aria-hidden="true">&laquo;</span></a></li>

				</c:if>
				<c:forEach var="i" begin="0" end="${items}">
					<c:if test="${currentPage+i <= pageTotal}">
						<li><a
							href="dashboard?search=${search}&searchType=${searchType}&currentPage=${currentPage+i}&pageAmount=${pageAmount}">
								<c:out value="${currentPage+i}"></c:out>
						</a></li>
					</c:if>

				</c:forEach>
				<c:if test="${currentPage < pageTotal}">
					<li><a
						href="dashboard?search=${search}&searchType=${searchType}&currentPage=${currentPage+1}&pageAmount=${pageAmount}"
						aria-label="Next"> <span aria-hidden="true">&raquo;</span>
					</a></li>
				</c:if>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<a
					href="dashboard?search=${search}&searchType=${searchType}&currentPage=1&pageAmount=10">
					<button type="button" class="btn btn-default">10</button>
				</a> <a
					href="dashboard?search=${search}&searchType=${searchType}&currentPage=1&pageAmount=50">
					<button type="button" class="btn btn-default">50</button>
				</a> <a
					href="dashboard?search=${search}&searchType=${searchType}&currentPage=1&pageAmount=100">
					<button type="button" class="btn btn-default">100</button>
				</a>
			</div>
		</div>
	</footer>
	<script src="static/js/jquery.min.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/dashboard.js"></script>

</body>
</html>