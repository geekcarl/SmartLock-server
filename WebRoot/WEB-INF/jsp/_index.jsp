<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>-->
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传</title>
</head>
<body>
	<h>上传</h>
	<form name="userForm" action="http://pc201404091006:8080/newinterlockwebservice/boxinfo/uploadimage" method="post" enctype="multipart/form-data">
		选择文件：<input type="file" name="file">
		<input type="submit" value="提交">
	</form>
	
</body>
</html>