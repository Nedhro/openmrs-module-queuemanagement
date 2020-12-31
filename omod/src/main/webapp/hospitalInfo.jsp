<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap.min.js"></script>

<style>
		.headText {
				background: rgb(0, 3, 36);
				background: linear-gradient(90deg, rgba(0, 3, 36, 1) 0%, rgba(9, 121, 97, 1) 54%, rgba(0, 212, 255, 1) 100%);
				color: white;
		}
</style>

<%@ include file="/WEB-INF/template/include.jsp" %>

<%@ include file="/WEB-INF/template/header.jsp" %>

<div class="container">
		<br/>
		<h2 class="headText">Hospital Information</h2>
		<br/>
		<form:form action="saveHospitalInfo.form" method="POST" modelAttribute="hospitalinfo" id="hospitalinfo">
				<table>
						<form:hidden name="id" path="id"/>
						<tr>
								<td>Name:</td>
								<td><form:input name="name" path="name" placeholder="Hospital Name" required="true"/></td>
						</tr>
						<tr>
								<td>Email:</td>
								<td><form:input name="email" path="email"/></td>
						</tr>
						<tr>
								<td>Address:</td>
								<td><form:input name="address" path="address"/></td>
						</tr>
						<tr>
								<td>Address:</td>
								<td><form:input name="mobile" path="mobile"/></td>
						</tr>
				<%--		<tr>
								<td>Logo:</td>
								<td><form:input type="file" name="logo" path="logo" size="50"/></td>
						</tr>--%>
						<tr>
								<td colspan="2" style="height: 20px; padding-top: 5px;">
										<input type="submit" value="<spring:message text="Save Info"/>">
								</td>
						</tr>
				</table>
		</form:form>
</div>

<%@ include file="/WEB-INF/template/footer.jsp" %>