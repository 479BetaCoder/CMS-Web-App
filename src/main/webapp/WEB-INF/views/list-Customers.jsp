<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>

<html>

<head>
<title>List Customers</title>

<!-- reference our style sheet -->

<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/style.css" />

</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>CRM - Customer Relationship Manager</h2>
		</div>
	</div>

	<div id="container">

		<div id="content">
			<security:authorize access="hasRole('ADMIN')">
				<input type="button" value="Add Customer"
					onclick="window.location.href='showFormForAdd';return false;"
					class="add-button" />
			</security:authorize>

			<!--  add our html table here -->
			<!--  add a search box -->
			<form:form action="search" method="GET">
                Search Customer: <input type="text" name="theSearchName" />
				<input type="submit" value="Search" class="add-button" />
			</form:form>
			<table>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
				<security:authorize access="hasRole('MANAGER')">	<th>Action</th>
				</security:authorize>
				</tr>

				<!-- loop over and print our customers -->
				<c:forEach var="tempCustomer" items="${customers}">


					<c:url var="updateLink" value="/Customer/showFormForUpdate">
						<c:param name="customerId" value="${tempCustomer.id}"></c:param>
					</c:url>

					<c:url var="deleteLink" value="/Customer/deleteCustomer">
						<c:param name="customerId" value="${tempCustomer.id}"></c:param>
					</c:url>


					<tr>
						<td>${tempCustomer.firstName}</td>
						<td>${tempCustomer.lastName}</td>
						<td>${tempCustomer.emailId}</td>
						<td><security:authorize access="hasRole('MANAGER')">
								<a href="${updateLink}">Update</a>
						|
						<a href="${deleteLink}"
									onclick="if(!(confirm('Are you sure you want to delete this customer ?'))) return false;">Delete</a>
							</security:authorize></td>
					</tr>

				</c:forEach>

			</table>

		</div>

	</div>


</body>

</html>









