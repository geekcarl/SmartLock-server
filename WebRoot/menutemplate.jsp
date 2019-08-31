<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title></title>

		<link rel="stylesheet" href="assets/css/font-awesome.min.css" />

		<link href="assets/css/bootstrap.min.css" rel="stylesheet" />
		

		<!--[if IE 7]>
		  <link rel="stylesheet" href="assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		

		<!-- fonts -->

		

		<style>
			html, body{
				background-color: #f2f2f2 !important;
			}
		</style>
		<!-- ace styles -->

		<link rel="stylesheet" href="assets/css/ace.min.css" />
		<link rel="stylesheet" href="assets/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="assets/css/ace-skins.min.css" />

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->

		<script src="assets/js/ace-extra.min.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="assets/js/html5shiv.js"></script>
		<script src="assets/js/respond.min.js"></script>
		<![endif]-->
		<script>
		function onKeyDown()
		{
			if ((event.keyCode==116)||(window.event.ctrlKey)||(window.event.shiftKey)||(event.keyCode==122))
			{
			event.keyCode=0;
			event.returnValue=false;
			}
		}
		</script>
		
		<script>
		function yxl() { 
		if(window.event.altKey) 
		{
		window.event.returnValue=false;
		}
		}
		document.onkeydown=yxl 
		</script> 
	</head>

	<body  onkeydown="onKeyDown()" oncontextmenu="return false" onselectstart="return false" ondragstart="return false">
		<div class="main-container" id="main-container" style="background-color: #f2f2f2 !important; height:480px !important;">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<div class="main-container-inner" style="background-color: #f2f2f2 !important; height:440px !important;">
				<div width="100%" id="sidebar" style="background-color: #f2f2f2 !important; height:440px !important;">
					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
					</script>
					<ul class="nav nav-list" style="background-color: #f2f2f2">

						<li id="li_1">
							<a href="#" class="dropdown-toggle" onclick="changeactive(1)">
								<i class="icon-desktop"></i>
								<span class="menu-text">安全管控</span>

								<b class="arrow icon-angle-down"></b>
							</a>

							<ul class="submenu" id="submenu_1">
								<li id="li_1_1">
									<a href="javascript:void(0);" onclick="menuclick('index')">
										<i class="icon-double-angle-right"></i>
										光交箱监控
									</a>
								</li>

								<li id="li_1_2">
									<a href="javascript:void(0);" onclick="menuclick('lockandkey.do?method=list')">
										<i class="icon-double-angle-right"></i>
										智能钥匙管理
									</a>
								</li>

								<li id="li_1_3">
									<a href="javascript:void(0);" onclick="menuclick('lockandkey.do?method=keyAuthorize')">
										<i class="icon-double-angle-right"></i>
										智能钥匙授权
									</a>
								</li>
								<li id="li_1_4">
									<a href="javascript:void(0);" onclick="menuclick('boxeventandwarn.do?method=boxeventList')">
										<i class="icon-double-angle-right"></i>
										开关门记录
									</a>
								</li>
								<li id="li_1_5">
									<a href="javascript:void(0);" onclick="menuclick('boxeventandwarn.do?method=boxwarnList')">
										<i class="icon-double-angle-right"></i>
										报警记录
									</a>
								</li>
							</ul>
						</li>
						<li id="li_2">
							<a href="#" class="dropdown-toggle" onclick="changeactive(2)">
								<i class="icon-list"></i>
								<span class="menu-text">资源管理</span>

								<b class="arrow icon-angle-down"></b>
							</a>

							<ul class="submenu" id="submenu_2">
								<li id="li_2_1">
									<a href="javascript:void(0);" onclick="menuclick('boxinfo.do?method=list')">
										<i class="icon-double-angle-right"></i>
										光交箱管理
									</a>
								</li>
								
								<li id="li_2_2">
									<a href="javascript:void(0);" onclick="menuclick('opticalcable.do?method=list')">
										<i class="icon-double-angle-right"></i>
										光缆管理
									</a>
								</li>
								<li id="li_2_3">
									<a href="javascript:void(0);">
										<i class="icon-double-angle-right"></i>
										业务管理
									</a>
								</li>
							</ul>
						</li>

						<li id="li_3">
							<a href="#" class="dropdown-toggle" onclick="changeactive(3)">
								<i class="icon-edit"></i>
								<span class="menu-text">日常维护管理</span>

								<b class="arrow icon-angle-down"></b>
							</a>

							<ul class="submenu" id="submenu_3">
								<li id="li_3_1">
									<a href="javascript:void(0);">
										<i class="icon-double-angle-right"></i>
										报警信息
									</a>
								</li>

								<li id="li_3_2">
									<a href="javascript:void(0);">
										<i class="icon-double-angle-right"></i>
										光纤维护管理
									</a>
								</li>

								<li id="li_3_3">
									<a href="javascript:void(0);">
										<i class="icon-double-angle-right"></i>
										维修记录
									</a>
								</li>

							</ul>
						</li>
						<li id="li_4">
							<a href="#" class="dropdown-toggle" onclick="changeactive(4)">
								<i class="icon-wrench"></i>
								<span class="menu-text">基本设置</span>

								<b class="arrow icon-angle-down"></b>
							</a>

							<ul class="submenu" id="submenu_4">
								<li id="li_4_1">
									<a href="javascript:void(0);" onclick="menuclick('department.do?method=list')">
										<i class="icon-double-angle-right"></i>
										组织机构管理(单位管理)
									</a>
								</li>

								<li id="li_4_2">
									<a href="javascript:void(0);" onclick="menuclick('operators.do?method=list')">
										<i class="icon-double-angle-right"></i>
										人员管理
									</a>
								</li>

								<li id="li_4_3">
									<a href="javascript:void(0);">
										<i class="icon-double-angle-right"></i>
										短信报警设置
									</a>
								</li>

								<li id="li_4_4">
									<a href="javascript:void(0);">
										<i class="icon-double-angle-right"></i>
										参数设置
									</a>
								</li>

								<li id="li_4_5">
									<a href="javascript:void(0);">
										<i class="icon-double-angle-right"></i>
										数据维护
									</a>
								</li>

								<li id="li_4_6">
									<a href="javascript:void(0);">
										<i class="icon-double-angle-right"></i>
										系统用户管理
									</a>
								</li>
								
								<li id="li_4_7">
									<a href="javascript:void(0);" onclick="menuclick('dictionary.do?method=list')">
										<i class="icon-double-angle-right"></i>
										数据字典
									</a>
								</li>
								
								<li id="li_4_8">
									<a href="javascript:void(0);">
										<i class="icon-double-angle-right"></i>
										系统设置
									</a>
								</li>
							</ul>
						</li>
					</ul><!-- /.nav-list -->

					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
					</script>
				</div>
				
			</div><!-- /.main-container-inner -->
		</div><!-- /.main-container -->

		<!-- basic scripts -->

		<!--[if !IE]> -->

		
		<script src="assets/js/googleapis-jq-2.0.3.min.js"></script>

		<!-- <![endif]-->

		<!--[if IE]>
		<script src="assets/js/googleapis-jq-1.10.2.min.js"></script>
		<![endif]-->

		<!--[if !IE]> -->

		<script type="text/javascript">
			window.jQuery || document.write("<script src='assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
		<script type="text/javascript">
		 window.jQuery || document.write("<script src='assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
		</script>
		<![endif]-->

		<script type="text/javascript">
			var lastsubmenuid = 0;
			function initmenu(x, y)
			{
				$('#submenu_' + x).toggle();
				$('#li_' + x + '_' + y).addClass("active");
				lastsubmenuid = x;
			}
			function changeactive(x) {
				if (lastsubmenuid != 0 && x != lastsubmenuid)
				{
					$('#submenu_' + lastsubmenuid).toggle();
					lastsubmenuid = x;
				}
			}
			function menuclick(url) {
				window.parent.location.href = url;
			}
			if("ontouchend" in document) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="assets/js/bootstrap.min.js"></script>
		<script src="assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<!-- ace scripts -->

		<script src="assets/js/ace-elements.min.js"></script>
		<script src="assets/js/ace.min.js"></script>

		<!-- inline scripts related to this page -->
</body>
</html>
