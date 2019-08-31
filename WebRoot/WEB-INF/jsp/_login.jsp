<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>-->
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta charset="UTF-8">
    <title>光交箱管理系统</title>
    <meta name="msapplication-TileColor" content="#5bc0de" />
    <meta name="msapplication-TileImage" content="assets/img/metis-tile.png" />
    <link rel="stylesheet" href="assets/lib/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="assets/css/main.css">
    <link rel="stylesheet" href="assets/lib/magic/magic.css">
    <script>
      (function(i, s, o, g, r, a, m) {
        i['GoogleAnalyticsObject'] = r;
        i[r] = i[r] || function() {
          (i[r].q = i[r].q || []).push(arguments)
        }, i[r].l = 1 * new Date();
        a = s.createElement(o),
        m = s.getElementsByTagName(o)[0];
        a.async = 1;
        a.src = g;
        m.parentNode.insertBefore(a, m)
      })(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');
      ga('create', 'UA-1669764-16', 'onokumus.com');
      ga('send', 'pageview');
    </script>
  </head>
  <body class="login">
    <div class="container" id="container">
      <div class="text-center">
        <h2 style="color:white;">光交箱后台管理系统</h2>
      </div>
      <div class="tab-content">
        <div id="login" class="tab-pane active">
        <form action="user.do?method=login" method="post" class="form-signin">
            <p id="mark" class="text-muted text-center" style="color:red;">
            	${requestScope.login_failed}
            </p>
            <input type="text" placeholder="用户名" id="account" name="user_name" class="form-control" onfocus="javascript:document.getElementById('mark').innerHTML = '';">
            <input type="password" placeholder="密码" name="passwd" class="form-control" onfocus="javascript:document.getElementById('mark').innerHTML = '';">
            <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
          </form>
        </div>
        <div id="forgot" class="tab-pane">
          <form action="index.html" class="form-signin">
            <p class="text-muted text-center">验证邮箱</p>
            <input type="email" placeholder="mail@domain.com" required="required" class="form-control">
            <br>
            <button class="btn btn-lg btn-danger btn-block" type="submit">重置密码</button>
          </form>
        </div>
        <div id="signup" class="tab-pane">
          <form action="index.html" class="form-signin">
            <input type="text" placeholder="用户名" class="form-control">
            <input type="email" placeholder="mail@domain.com" class="form-control">
            <input type="password" placeholder="密码" class="form-control">
            <button class="btn btn-lg btn-success btn-block" type="submit">注册</button>
          </form>
        </div>
      </div>
      <div class="text-center">
        <ul class="list-inline">
          <li> <a class="text-muted" href="#login" data-toggle="tab">登录</a>  </li>
          <li> <a class="text-muted" href="#forgot" data-toggle="tab">忘记密码</a>  </li>
          <li> <a class="text-muted" href="#signup" data-toggle="tab">快速注册</a>  </li>
        </ul>
      </div>
    </div><!-- /container -->
    <script type="text/javascript">
	  	document.body.onload = function(){
	  	    $("#container").animate({marginTop:(document.body.clientHeight - 350) / 2 + 'px'},2000);
	  	  	document.getElementById('account').focus();
	  	}
	</script>
    <script src="assets/lib/jquery.min.js"></script>
    <script src="assets/lib/bootstrap/js/bootstrap.js"></script>
    <script>
      $('.list-inline li > a').click(function() {
        var activeForm = $(this).attr('href') + ' > form';
        //console.log(activeForm);
        $(activeForm).addClass('magictime swap');
        //set timer to 1 seconds, after that, unload the magic animation
        setTimeout(function() {
          $(activeForm).removeClass('magictime swap');
        }, 1000);
      });
    </script>
  </body>
</html>