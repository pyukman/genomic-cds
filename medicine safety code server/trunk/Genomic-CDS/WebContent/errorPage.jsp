<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Error page</title>
    <meta name="author" content="Matthias Samwald">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet"  href="css/themes/default/jquery.mobile-1.3.2.min.css">
	<link rel="stylesheet" href="_assets/css/jqm-demos.css">
	<link rel="shortcut icon" href="images/favicon.ico">
	<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
	<script src="js/jquery.js"></script>
	<script src="_assets/js/index.js"></script>
	<script src="js/jquery.mobile-1.3.2.min.js"></script>
</head>
<body>
	<div data-role="page" class="jqm-demos">
		<div data-role="header" class="jqm-header" style="text-align: center; padding: 3px"> 
			<img src="images/safety-code-logo.png" width="209" height="30" alt="safety-code.org" />
		</div>
		
		<div data-role="content" class="jqm-content">
			<h2>Error message:</h2>
			<b><%=exception.getMessage()%></b>
		</div>
		<div data-role="footer" style="text-align: center; padding: 5px;">
			<div>
				This service is provided for research purposes only and comes without any warranty. (C)&nbsp;2012&nbsp;
				<a href="http://safety-code.org/" data-ajax="false">safety-code.org</a>
			</div>
		</div>
	</div>
</body>
</html>