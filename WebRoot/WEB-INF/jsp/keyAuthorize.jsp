<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>光交箱监控管理系统</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="stylesheet" href="assets/css/font-awesome.min.css" />
		<link href="assets/css/bootstrap.min.css" rel="stylesheet" />
		

		<!--[if IE 7]>
		  <link rel="stylesheet" href="assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		

		<!-- fonts -->

		<link rel="stylesheet" href="assets/css/jquery-ui-1.10.3.full.min.css" />
		<link rel="stylesheet" href="assets/css/ui.jqgrid.css" />
		

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
				if ((window.event.ctrlKey)||(window.event.shiftKey))
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
			function scrollFunc() {
				var e = window.event;
				if (e.button == 0)
				{
					if ($('#rightclick_contextmenu').css('display') == 'block')
					{
						if (e.pageX < g_obj.rightClickPos.pageX || e.pageX > (g_obj.rightClickPos.pageX + 160) || e.pageY < g_obj.rightClickPos.pageY || e.pageY > (g_obj.rightClickPos.pageY + 150))
						{
							$('#rightclick_contextmenu').hide();
						}
					}
				}
			}
			document.onkeydown=yxl;
			document.onMouseDown=scrollFunc;
		</script> 
	</head>

	<body  onkeydown="onKeyDown()" onmousedown="scrollFunc()" oncontextmenu="return false">
		<div class="col-sm-6" id="rightclick_contextmenu" onmouseover="rightMenuMouseOver()" onmouseout="rightMenuMouseOut()" style="display:none; z-index:9999; position: absolute;">
			<ul id="menu">
				<li>
					<a href="javascript:void(0);" onclick="showModules()">光交接箱面板图</a>
				</li>
				<li>
					<a href="javascript:void(0);" onclick="managelock()">智能锁管理</a>
				</li>
				<li>
					<a href="javascript:void(0);">光交接箱端子管理</a>
				</li>

				<li>
					<a href="javascript:void(0);" onclick="jumpTerminal()">跳纤管理</a>
				</li>

				<li>
					<a href="javascript:void(0);" onclick="coreToTerminal()">纤芯关联</a>
				</li>

				<li>
					<a href="javascript:void(0);">端子直熔</a>
				</li>
				
			</ul>
		</div><!-- ./span -->

		<div class="navbar navbar-default" id="navbar">
			<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
			</script>

			<div class="navbar-container" id="navbar-container">
				<div class="navbar-header pull-left">
					<a href="#" class="navbar-brand"> <small> <img src="assets/images/logo-Small.png" /> 光交箱监控管理系统 </small> </a>
					<!-- /.brand -->
				</div>
				<!-- /.navbar-header -->

				<div class="navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						<li class="light-blue">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle"> <img
									class="nav-user-photo" src="assets/avatars/avatar2.png"
									alt="用户头像" /> <span class="user-info"> <small>欢迎光临,</small>
									${sessionScope.user_name} </span> <i class="icon-caret-down"></i>
							</a>
							<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">

								<li>
									<a href="preUpdateUser?id=${sessionScope.user.id}"><i
										class="icon-user"></i>个人资料修改</a>
								</li>
								<li class="divider"></li>
								<li>
									<a href="logout"> <i class="icon-off"></i> 退出 </a>
								</li>
							</ul>
						</li>
					</ul>
					<!-- /.ace-nav -->
				</div>
				<!-- /.navbar-header -->
			</div>
			<!-- /.container -->
		</div>

		<div class="main-container" id="main-container" style="background-color: white">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
				function dyniframesize(down) { 
					setTimeout("javascript:window.frames['menu'].initmenu(1, 3);", 1);
					
				}
			</script>

			<div class="main-container-inner" style="background-color: #f2f2f2">
				<a class="menu-toggler" id="menu-toggler" href="#"> <span
					class="menu-text"></span> </a>
				<div class="sidebar" id="sidebar" style="background-color: #f2f2f2">
					<iframe id="menu" width="190px" height="480px"  style="background-color: #f2f2f2" frameborder="0" scrolling="no" onload="dyniframesize('menu')" src="menutemplate.jsp"></iframe>
				</div>

				<div class="main-content">
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

						<ul class="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i>
								<a href="#">首页</a>
							</li>
							<li>
								<a href="#">安全管控</a>
							</li>
							<li class="active">智能钥匙授权</li>
						</ul>
						<!-- .breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								安全管控
								<small>
									<i class="icon-double-angle-right"></i>
									智能钥匙授权
								</small>
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->

								<div class="well well-sm">
									任务名称：<input id="f_s_log_name" type="text" placeholder="任务名称"/> 
									授权人：<input id="f_s_grant_user" type="text" placeholder="授权人"/>
									钥匙编号：<input id="f_s_key_no" type="text" placeholder="钥匙编号"/>
									<br/>
									钥匙内码：<input id="f_s_key_code" type="text" placeholder="钥匙内码"/>
									使用人姓名：<input id="f_s_operator_name" type="text" placeholder="使用人姓名"/>
									<a class="green" href="javascript:void(0);" onclick="fastsearch()" title="快速检索"><i class="icon-search bigger-130"></i></a>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<button onclick="resetsearch()" class="btn btn-xs btn-success">
										重置检索
										<i class="icon-refresh icon-on-right"></i>
									</button>
								</div>
							 	<div id="manage_grant_warn" class="alert alert-danger" style="display:none;">
									<i class="icon-hand-right"></i>
									<label id="manage_grant_warn_label"></label>
									<button class="close" data-dismiss="alert">
										<i class="icon-remove"></i>
									</button>
								</div>
								<table id="manage-grant-grid-table"></table>
				
								<div id="manage-grant-grid-pager"></div>
								 	
								<script type="text/javascript">
									var $path_base = "/";//this will be used in gritter alerts containing images
								</script>

								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
						
					</div><!-- /.page-content -->
				</div>
				<!-- /.row -->
			</div>
		</div>
		<!-- /.main-content -->
		<!-- /.main-container-inner -->
		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="icon-double-angle-up icon-only bigger-110"></i> </a>
		<!-- /.main-container -->
		<div id="dialog-update-grant" class="hide">
			 <div style="padding:10px 50px 20px 50px">
		        <form>
		        	<table width="100%">
		        		 <tr>
		                    <td>授权任务名称:</td>
		                    <td>
		                    	<input id="u_grant_log_name" type="text"></input>
		                    	<input id="u_grant_id" type="hidden" />"
		                    </td>
		                </tr>
		                <tr>
		                    <td>权限时间:</td>
		                    <td>
		                    	<input type="text" id="u_grant_begin_time" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
		                    	至
		                    	<input type="text" id="u_grant_end_time"  value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
		                    </td>
		                </tr>
		        	</table>
		        </form>
		        <div style="width:100%; margin-top:20px; margin-bottom:20px;">
			        <div style="width:250px; height:400px; overflow:auto; border:1px solid black; float: left; margin-left:30px;">
				        <h4>选择钥匙</h4>
						<div id="u_tree_div1" class="widget-main padding-8">
							<div id="u_tree1" class="tree"></div>
						</div>
			        </div>
			        <div style="width:250px; height:400px; overflow:auto; border:1px solid black;  float: left; margin-left:30px;">
			        	<h4>选择光交箱</h4>
						<div id="u_tree_div2" class="widget-main padding-8">
							<div id="u_tree2" class="tree"></div>
						</div>
					</div>
			        <div style="width:250px; height:400px; overflow:auto; border:1px solid black;  float: left; margin-left:30px;">
			        	<h4>选择人员</h4>
						<div id="u_tree_div3" class="widget-main padding-8">
							<div id="u_tree3" class="tree"></div>
						</div>
			        </div>
		        </div>
		        <div style="position: relative; top:20px; clear: both;">
			        <form>
			        	<table width="100%">
			        		 <tr>
			                    <td>备注:</td>
			                    <td>
			                    	<textarea id="u_grant_remarks" rows="5" cols="70"></textarea>
			                    </td>
			                </tr>
			        	</table>
			        </form>
			    </div>
		     </div>
		</div><!-- #dialog-add-grant -->
		
		<div id="dialog-add-grant" class="hide">
			 <div style="padding:10px 50px 20px 50px">
		        <form>
		        	<table width="100%">
		        		 <tr>
		                    <td>授权任务名称:</td>
		                    <td>
		                    	<input id="a_grant_log_name" type="text"></input>
		                    </td>
		                </tr>
		                <tr>
		                    <td>权限时间:</td>
		                    <td>
		                    	<input type="text" id="a_grant_begin_time" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
		                    	至
		                    	<input type="text" id="a_grant_end_time"  value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
		                    </td>
		                </tr>
		        	</table>
		        </form>
		        <div style="width:100%; margin-top:20px; margin-bottom:20px;">
			        <div style="width:250px; height:400px; overflow:auto; border:1px solid black; float: left; margin-left:30px;">
				        <h4>选择钥匙</h4>
						<div id="tree_div1" class="widget-main padding-8">
							<div id="tree1" class="tree"></div>
						</div>
			        </div>
			        <div style="width:250px; height:400px; overflow:auto; border:1px solid black;  float: left; margin-left:30px;">
			        	<h4>选择光交箱</h4>
						<div id="tree_div2" class="widget-main padding-8">
							<div id="tree2" class="tree"></div>
						</div>
					</div>
			        <div style="width:250px; height:400px; overflow:auto; border:1px solid black;  float: left; margin-left:30px;">
			        	<h4>选择人员</h4>
						<div id="tree_div3" class="widget-main padding-8">
							<div id="tree3" class="tree"></div>
						</div>
			        </div>
		        </div>
		        <div style="position: relative; top:20px; clear: both;">
			        <form>
			        	<table width="100%">
			        		 <tr>
			                    <td>备注:</td>
			                    <td>
			                    	<textarea id="a_grant_remarks" rows="5" cols="70"></textarea>
			                    </td>
			                </tr>
			        	</table>
			        </form>
			    </div>
		     </div>
		</div><!-- #dialog-update-grant -->
		
		<!-- basic scripts -->

		<!--[if !IE]> -->

		<script src="assets/js/googleapis-jq-2.0.3.min.js"></script>

		<!-- <![endif]-->

		<!--[if IE]>
		<script src="assets/js/googleapis-jq-1.10.2.min.js"></script>
		<![endif]-->

		<!--[if !IE]> -->

		<script type="text/javascript">
			window.jQuery || document.write("<script src='assets/js/jquery-2.0.3.min.js'>"+"<"+"script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
		<script type="text/javascript">
		 window.jQuery || document.write("<script src='assets/js/jquery-1.10.2.min.js'>"+"<"+"script>");
		</script>
		<![endif]-->

		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"script>");
		</script>
		<script src="assets/js/bootstrap.min.js"></script>
		<script src="assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<!--[if lte IE 8]>
		  <script src="assets/js/excanvas.min.js"></script>
		<![endif]-->

		<script src="assets/js/jquery-ui-1.10.3.full.min.js"></script>
		<script src="assets/js/jquery.ui.touch-punch.min.js"></script>
		<script src="assets/js/jquery.slimscroll.min.js"></script>
		<script src="assets/js/jquery.easy-pie-chart.min.js"></script>
		<script src="assets/js/bootbox.min.js"></script>
		<script src="assets/js/jquery.sparkline.min.js"></script>
		<script src="assets/js/flot/jquery.flot.min.js"></script>
		<script src="assets/js/flot/jquery.flot.pie.min.js"></script>
		<script src="assets/js/flot/jquery.flot.resize.min.js"></script>
		
		<script src="assets/js/jqGrid/jquery.jqGrid.min.js"></script>
		<script src="assets/js/jqGrid/i18n/grid.locale-en.js"></script>
		<script src="My97DatePicker/WdatePicker.js"></script>
		<script src="assets/js/jquery.bootstrap.min.js"></script>
		<script src="js/jquery.validate.js"></script>
		<script src="js/validate-ex.js"></script>
		<script src="assets/js/fuelux/data/fuelux.tree-sampledata.js"></script>
		<script src="assets/js/fuelux/fuelux.tree.min.js"></script>

		<!-- ace scripts -->

		<script src="assets/js/ace-elements.min.js"></script>
		<script src="assets/js/ace.min.js"></script>
		<script src="js/keyAuthorize.js"></script>
		
	</body>
</html>

