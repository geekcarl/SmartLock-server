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
		<link rel="stylesheet" href="assets/css/datepicker.css" />
		<link rel="stylesheet" href="assets/css/ui.jqgrid.css" />
		
		
		<!-- ace styles -->

		<link rel="stylesheet" href="assets/css/ace.min.css" />
		<link rel="stylesheet" href="assets/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="assets/css/ace-skins.min.css" />
		
		<style>
			form.cmxform {
			}
			em.error {
				background:url("images/unchecked.gif") no-repeat 0px 0px;
				padding-left: 16px;
			}
			em.success {
				background:url("images/checked.gif") no-repeat 0px 0px;
				padding-left: 16px;
			}
			form.cmxform label.error {
				margin-left: auto;
				width: 250px;
			}
			em.error {
				color: black;
			}
			#warning {
				display: none;
			}
		</style>

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
						$('#rightclick_contextmenu').hide();
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
					<a href="#">查看详情</a>
				</li>

				<li>
					<a href="#">编辑</a>
				</li>

				<li>
					<a href="#">删除</a>
				</li>

				<li>
					<a href="#">XXXXXXX</a>
				</li>

				<li>
					<a href="#">XXXXXX</a>
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
					setTimeout("javascript:window.frames['menu'].initmenu(4, 7);", 1);
					
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
								<a href="#">基本设置</a>
							</li>
							<li class="active">数据字典</li>
						</ul>
						<!-- .breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								基本设置
								<small>
									<i class="icon-double-angle-right"></i>
									数据字典
								</small>
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->

								<div id="add_statekey_mark_div" class="alert alert-danger" style="display:none;">
									<i class="icon-hand-right"></i>
									<label id="add_statekey_mark_label"></label>
									<button class="close" data-dismiss="alert">
										<i class="icon-remove"></i>
									</button>
								</div>

								<table id="grid-table"></table>

								<div id="grid-pager"></div>
								
								
								<br/><br/><br/>
								<div id="add_statevalue_mark_div" class="alert alert-danger" style="display:none;">
									<i class="icon-hand-right"></i>
									<label id="add_statevalue_mark_label"></label>
									<button class="close" data-dismiss="alert">
										<i class="icon-remove"></i>
									</button>
								</div>
								<table id="value-grid-table"></table>

								<div id="value-grid-pager"></div>
								
								<br/><br/><br/>
								<div id="add_statelevel_mark_div" class="alert alert-danger" style="display:none;">
									<i class="icon-hand-right"></i>
									<label id="add_statelevel_mark_label"></label>
									<button class="close" data-dismiss="alert">
										<i class="icon-remove"></i>
									</button>
								</div>
								<table id="level-grid-table"></table>

								<div id="level-grid-pager"></div>

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
		</div>
		<!-- /.main-container-inner -->
		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="icon-double-angle-up icon-only bigger-110"></i> </a>
		</div>
		<!-- /.main-container -->
		<div id="dialog-add-state-key" class="hide">
			<div id="add_statekey_mark_div_error" class="alert alert-danger" style="display:none;">
				<i class="icon-hand-right"></i>
				<label id="add_statekey_mark_label_error"></label>
				<button class="close" data-dismiss="alert">
					<i class="icon-remove"></i>
				</button>
			</div>
			 <div style="padding:10px 20px 10px 20px">
		        <form class="cmxform" id="form-add-state-key" method="post" action="dictionary.do?method=addStateKey">
				  <fieldset>
				    <table width="100%">
						<tr>
							<td width="100px">
								<label for="add_statekey_state_key">状态名：</label>
							</td>
							<td width="300px">
				     			 <input id="add_statekey_state_key" name="state_key" type="text" class="input required"/>
				     			 <font style="color:red;">*</font>
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>
								<label for="add_statekey_remarks">备注：</label>
							</td>
							<td>
		             			 <textarea id="add_statekey_remarks" name="remarks" rows="4" cols="30"></textarea>
							</td>
							<td>&nbsp;</td>
						</tr>
					</table>
				  </fieldset>
				</form>
		     </div>
		</div><!-- #dialog-message -->
		
		
		<!-- 修改字典窗口-开始 -->
		<div id="dialog-update-state-key" class="hide">
			<div id="update_statekey_mark_div_error" class="alert alert-danger" style="display:none;">
				<i class="icon-hand-right"></i>
				<label id="update_statekey_mark_label_error"></label>
				<button class="close" data-dismiss="alert">
					<i class="icon-remove"></i>
				</button>
			</div>
			 <div style="padding:10px 20px 10px 20px">
		        <form class="cmxform" id="form-update-state-key" method="post">
				  <input type="hidden" id="update_statekey_id"/>
				  <fieldset>
				    <table width="100%">
						<tr>
							<td width="100px">
								<label for="update_statekey_state_key">状态名：</label>
							</td>
							<td width="300px">
				     			 <input id="update_statekey_state_key" name="update_statekey_state_key" type="text" class="input required"/>
				     			 <font style="color:red;">*</font>
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>
								<label for="update_statekey_remarks">备注：</label>
							</td>
							<td>
		             			 <textarea id="update_statekey_remarks" name="update_statekey_remarks" rows="4" cols="30"></textarea>
							</td>
							<td>&nbsp;</td>
						</tr>
					</table>
				  </fieldset>
				</form>
		     </div>
		</div><!-- #dialog-message -->
		<!-- 修改字典窗口-结束 -->
		
		
		<!-- 查看字典窗口-开始 -->
		<div id="dialog-view-state-key" class="hide">
			 <div style="pupdateing:10px 20px 10px 20px">
				    <table width="100%">
						<tr>
							<td width="100px">
								<label for="view_statekey_state_key">状态名：</label>
							</td>
							<td width="300px">
				     			 <input id="view_statekey_state_key" name="view_statekey_state_key" type="text" readonly="readonly"/>
				     			 <font style="color:red;">*</font>
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>
								<label for="view_statekey_remarks">备注：</label>
							</td>
							<td>
		             			 <textarea id="view_statekey_remarks" name="view_statekey_remarks" rows="4" cols="30" readonly="readonly"></textarea>
							</td>
							<td>&nbsp;</td>
						</tr>
					</table>
		     </div>
		</div><!-- #dialog-message -->
		<!-- 查看字典窗口-结束 -->
		
		
		<!-- 添加字典项窗口-开始 -->
		<div id="dialog-add-state-value" class="hide">
			<div id="add_statevalue_mark_div_error" class="alert alert-danger" style="display:none;">
				<i class="icon-hand-right"></i>
				<label id="add_statevalue_mark_label_error"></label>
				<button class="close" data-dismiss="alert">
					<i class="icon-remove"></i>
				</button>
			</div>
			 <div style="padding:10px 20px 10px 20px">
		        <form class="cmxform" id="form-add-state-value" method="post" action="dictionary.do?method=addStateValue">
				  <fieldset>
				    <table width="100%">
						<tr>
							<td width="100px">
								<label for="add_statevalue_state_key">状态字典：</label>
							</td>
							<td width="300px">
				     			 <select id="add_statevalue_state_key" name="add_statevalue_state_key" class="select required">
				     			 </select>
				     			 <font style="color:red;">*</font>
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td width="100px">
								<label for="add_statevalue_state_value">状态值：</label>
							</td>
							<td width="300px">
				     			 <input id="add_statevalue_state_value" name="add_statevalue_state_value" type="text" class="required"/>
				     			 <font style="color:red;">*</font>
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td width="100px">
								<label for="add_statevalue_state_level">状态值等级：</label>
							</td>
							<td width="300px">
				     			 <select id="add_statevalue_state_level" name="add_statevalue_state_level" class="select required">
				     			 </select>
				     			 <font style="color:red;">*</font>
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>
								<label for="add_statevalue_remarks">备注：</label>
							</td>
							<td>
		             			 <textarea id="add_statevalue_remarks" name="add_statevalue_remarks" rows="4" cols="30"></textarea>
							</td>
							<td>&nbsp;</td>
						</tr>
					</table>
				  </fieldset>
				</form>
		     </div>
		</div><!-- #dialog-message -->
		<!-- 添加字典项窗口-结束 -->
		
		<!-- 修改字典项窗口-开始 -->
		<div id="dialog-update-state-value" class="hide">
			<div id="update_statevalue_mark_div_error" class="alert alert-danger" style="display:none;">
				<i class="icon-hand-right"></i>
				<label id="update_statevalue_mark_label_error"></label>
				<button class="close" data-dismiss="alert">
					<i class="icon-remove"></i>
				</button>
			</div>
			 <div style="padding:10px 20px 10px 20px">
		        <form class="cmxform" id="form-update-state-value" method="post">
				  <input type="hidden" id="update_statevalue_id"/>
				  <fieldset>
				    <table width="100%">
						<tr>
							<td width="100px">
								<label for="update_statevalue_state_key">状态字典：</label>
							</td>
							<td width="300px">
				     			 <select id="update_statevalue_state_key" name="update_statevalue_state_key" class="select required">
				     			 </select>
				     			 <font style="color:red;">*</font>
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td width="100px">
								<label for="update_statevalue_state_value">状态值：</label>
							</td>
							<td width="300px">
				     			 <input id="update_statevalue_state_value" name="update_statevalue_state_value" type="text" class="required"/>
				     			 <font style="color:red;">*</font>
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td width="100px">
								<label for="update_statevalue_state_level">状态值等级：</label>
							</td>
							<td width="300px">
				     			 <select id="update_statevalue_state_level" name="update_statevalue_state_level" class="select required">
				     			 </select>
				     			 <font style="color:red;">*</font>
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>
								<label for="update_statevalue_remarks">备注：</label>
							</td>
							<td>
		             			 <textarea id="update_statevalue_remarks" name="update_statevalue_remarks" rows="4" cols="30"></textarea>
							</td>
							<td>&nbsp;</td>
						</tr>
					</table>
				  </fieldset>
				</form>
		     </div>
		</div><!-- #dialog-message -->
		<!-- 修改字典项窗口-结束 -->
		
		
		<!-- 查看字典项窗口-开始 -->
		<div id="dialog-view-state-value" class="hide">
			 <div style="pupdateing:10px 20px 10px 20px">
				    <table width="100%">
						<tr>
							<td width="100px">
								<label for="view_statevalue_state_key">状态字典：</label>
							</td>
							<td width="300px">
				     			 <select id="view_statevalue_state_key" name="view_statevalue_state_key" disabled="disabled">
				     			 </select>
				     			 <font style="color:red;">*</font>
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td width="100px">
								<label for="view_statevalue_state_value">状态值：</label>
							</td>
							<td width="300px">
				     			 <input id="view_statevalue_state_value" name="view_statevalue_state_value" type="text" readonly="readonly"/>
				     			 <font style="color:red;">*</font>
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td width="100px">
								<label for="view_statevalue_state_level">状态值等级：</label>
							</td>
							<td width="300px">
				     			 <select id="view_statevalue_state_level" name="view_statevalue_state_level" disabled="disabled">
				     			 </select>
				     			 <font style="color:red;">*</font>
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>
								<label for="view_statevalue_remarks">备注：</label>
							</td>
							<td>
		             			 <textarea id="view_statevalue_remarks" name="view_statevalue_remarks" rows="4" cols="30" readonly="readonly"></textarea>
							</td>
							<td>&nbsp;</td>
						</tr>
					</table>
		     </div>
		</div><!-- #dialog-message -->
		<!-- 查看字典项窗口-结束 -->
		
		
		<!-- 添加字典等级窗口-开始 -->
		<div id="dialog-add-state-level" class="hide">
			<div id="add_statelevel_mark_div_error" class="alert alert-danger" style="display:none;">
				<i class="icon-hand-right"></i>
				<label id="add_statelevel_mark_label_error"></label>
				<button class="close" data-dismiss="alert">
					<i class="icon-remove"></i>
				</button>
			</div>
			 <div style="padding:10px 20px 10px 20px">
		        <form class="cmxform" id="form-add-state-level" method="post" action="dictionary.do?method=addStateLevel">
				  <fieldset>
				    <table width="100%">
						<tr>
							<td width="100px">
								<label for="add_statelevel_level">字典等级：</label>
							</td>
							<td width="300px">
				     			 <input id="add_statelevel_level" name="add_statelevel_level" type="text" class="required"/>
				     			 <font style="color:red;">*</font>
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td width="100px">
								<label for="add_statelevel_state_image">字典等级图片：</label>
							</td>
							<td width="300px" id="add_statelevel_state_image_td">
				     			 <input id="add_statelevel_state_image" name="add_statelevel_state_image" type="file" class="required"/>
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>
								<label for="add_statelevel_remarks">备注：</label>
							</td>
							<td>
		             			 <textarea id="add_statelevel_remarks" name="add_statelevel_remarks" rows="4" cols="30"></textarea>
							</td>
							<td>&nbsp;</td>
						</tr>
					</table>
				  </fieldset>
				</form>
		     </div>
		</div><!-- #dialog-message -->
		<!-- 添加字典等级窗口-结束 -->
		
		
		<!-- 修改字典等级窗口-开始 -->
		<div id="dialog-update-state-level" class="hide">
			<div id="update_statelevel_mark_div_error" class="alert alert-danger" style="display:none;">
				<i class="icon-hand-right"></i>
				<label id="update_statelevel_mark_label_error"></label>
				<button class="close" data-dismiss="alert">
					<i class="icon-remove"></i>
				</button>
			</div>
			 <div style="padding:10px 20px 10px 20px">
		        <form class="cmxform" id="form-update-state-level" method="post">
				  <input type="hidden" id="update_statelevel_id"/>
				  <fieldset>
				    <table width="100%">
						<tr>
							<td width="100px">
								<label for="update_statelevel_level">字典等级：</label>
							</td>
							<td width="300px">
				     			 <input id="update_statelevel_level" name="update_statelevel_level" type="text" class="required"/>
				     			 <font style="color:red;">*</font>
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td width="100px">
								<label for="update_statelevel_state_image">字典等级图片：</label>
							</td>
							<td width="300px" id="update_statelevel_state_image_td">
				     			 <input id="update_statelevel_state_image" name="update_statelevel_state_image" type="file"/><div id="update_statelevel_state_image_view" width="100%"></div>
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>
								<label for="update_statelevel_remarks">备注：</label>
							</td>
							<td>
		             			 <textarea id="update_statelevel_remarks" name="update_statelevel_remarks" rows="4" cols="30"></textarea>
							</td>
							<td>&nbsp;</td>
						</tr>
					</table>
				  </fieldset>
				</form>
		     </div>
		</div><!-- #dialog-message -->
		<!-- 修改字典等级窗口-结束 -->
		
		
		<!-- 查看字典等级窗口-开始 -->
		<div id="dialog-view-state-level" class="hide">
			 <div style="padding:10px 20px 10px 20px">
				    <table width="100%">
						<tr>
							<td width="100px">
								<label for="view_statelevel_level">字典等级：</label>
							</td>
							<td>
				     			 <input id="view_statelevel_level" type="text" readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td width="100px">
								<label for="view_statelevel_state_image">字典等级图片：</label>
							</td>
							<td id="view_statelevel_state_image">
				     			 
							</td>
						</tr>
						<tr>
							<td>
								<label for="view_statelevel_remarks">备注：</label>
							</td>
							<td>
		             			 <textarea id="view_statelevel_remarks" readonly="readonly" rows="4" cols="70"></textarea>
		             			 
							</td>
						</tr>
					</table>
		     </div>
		</div><!-- #dialog-message -->
		<!-- 查看字典等级窗口-结束 -->
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
		
		<script src="assets/js/date-time/bootstrap-datepicker.min.js"></script>
		<script src="assets/js/jqGrid/jquery.jqGrid.min.js"></script>
		<script src="assets/js/jqGrid/i18n/grid.locale-en.js"></script>
		<script src="js/jquery.validate.js"></script>
		<script src="js/validate-ex.js"></script>

		<!-- ace scripts -->

		<script src="assets/js/ace-elements.min.js"></script>
		<script src="assets/js/ace.min.js"></script>
		<script type="text/javascript">

			$(document).ready(function(){

				//初始化ace的file框框
				$('#add_statelevel_state_image').ace_file_input({});
				$('#update_statelevel_state_image').ace_file_input({});

				//验证字典添加
				$("#form-add-state-key").validate({

					 event:"keyup" || "blur",
					 errorElement: "em",
					errorPlacement: function(error, element) {
						error.appendTo(element.parent("td").next("td"));
					},
					success: function(label) {
						label.text("ok!").addClass("success");
					},
			         rules:{ //定义验证规则 
					 	state_key: {
					 		required: true,
					 		remote:{   
							    url:'dictionary.do?method=isStateKeyValid',
							    data:{
					                 state_key : function(){return $('#add_statekey_state_key').val();}
					            },
							    type:"post",
							    dateType:"json",
							   } 
					 	}
			         }, 
			         messages:{ //定义提示信息 
			            state_key: {
			        	 	required:"状态项不能为空！",
			        	 	remote:"该状态已存在！ "
			         	}
			         } 
			    });

				//验证字典添加
				$("#form-add-state-key").validate({

					 event:"keyup" || "blur",
					 errorElement: "em",
					errorPlacement: function(error, element) {
						error.appendTo(element.parent("td").next("td"));
					},
					success: function(label) {
						label.text("ok!").addClass("success");
					},
			         rules:{ //定义验证规则 
					 	state_key: {
					 		required: true,
					 		remote:{   
							    url:'dictionary.do?method=isStateKeyValid',
							    data:{
					                 state_key : function(){return $('#add_statekey_state_key').val();}
					            },
							    type:"post",
							    dateType:"json",
							   } 
					 	}
			         }, 
			         messages:{ //定义提示信息 
			            state_key: {
			        	 	required:"状态项不能为空！",
			        	 	remote:"该状态已存在！ "
			         	}
			         } 
			    });

				//验证字典修改
				$("#form-update-state-key").validate({

					 event:"keyup" || "blur",
					 errorElement: "em",
					errorPlacement: function(error, element) {
						error.appendTo(element.parent("td").next("td"));
					},
					success: function(label) {
						label.text("ok!").addClass("success");
					},
			         rules:{ //定义验证规则 
					 	update_statekey_state_key: {
					 		required: true
					 	}
			         }, 
			         messages:{ //定义提示信息 
			            update_statekey_state_key: {
			        	 	required:"状态项不能为空！"
			         	}
			         } 
			    });


				// 添加验证方法 
				jQuery.validator.addMethod("isSelected", function(value, element) {  
					return value != 0;   
				}, "请选择"); 
				//验证状态值添加
				$("#form-add-state-value").validate({

					 event:"keyup" || "blur",
					 errorElement: "em",
					errorPlacement: function(error, element) {
						error.appendTo(element.parent("td").next("td"));
					},
					success: function(label) {
						label.text("ok!").addClass("success");
					},
			         rules:{ //定义验证规则 
						add_statevalue_state_value: {
					 		required: true
					 	},
					 	add_statevalue_state_key: {
						 	required: true,
					 		isSelected: true
						},
					 	add_statevalue_state_level: {
						 	required: true,
					 		isSelected: true
						}
			         }, 
			         messages:{ //定义提示信息 
			        	 add_statevalue_state_value: {
			        	 	required:"状态值不能为空！"
			         	 },
			         	 add_statevalue_state_key: {
				        	 required:"请选择所属状态项！",
						 	isSelected: '请选择所属状态项！'
					     },
			         	 add_statevalue_state_level: {
				        	 required:"请选择状态等级！",
							 isSelected: '请选择状态等级！'
					     }
			         } 
			    });

				//验证状态值修改
				$("#form-update-state-value").validate({

					 event:"keyup" || "blur",
					 errorElement: "em",
					errorPlacement: function(error, element) {
						error.appendTo(element.parent("td").next("td"));
					},
					success: function(label) {
						label.text("ok!").addClass("success");
					},
			         rules:{ //定义验证规则 
						update_statevalue_state_value: {
					 		required: true
					 	},
					 	update_statevalue_state_key: {
						 	required: true,
					 		isSelected: true
						},
					 	update_statevalue_state_level: {
						 	required: true,
					 		isSelected: true
						}
			         }, 
			         messages:{ //定义提示信息 
			        	 update_statevalue_state_value: {
			        	 	required:"状态值不能为空！"
			         	 },
			         	 update_statevalue_state_key: {
				        	 required:"请选择所属状态项！",
						 	isSelected: '请选择所属状态项！'
					     },
			         	 update_statevalue_state_level: {
				        	 required:"请选择状态等级！",
							 isSelected: '请选择状态等级！'
					     }
			         } 
			    });
				//验证状态等级添加
				$("#form-add-state-level").validate({
	
					event:"keyup" || "blur",
					errorElement: "em",
					errorPlacement: function(error, element) {
						error.appendTo(element.parent("td").next("td"));
					},
					success: function(label) {
						label.text("ok!").addClass("success");
					},
			         rules:{ //定义验证规则 
						add_statelevel_level: {
					 		required: true
					 	},
					 	add_statelevel_state_image: {
						 	required: true
						}
			         }, 
			         messages:{ //定义提示信息 
			        	 add_statelevel_level: {
			        	 	required:"状态等级不能为空！"
			         	 },
			         	 add_statelevel_state_image: {
				        	 required:"请选择状态等级缩略图！",
					     }
			         } 
			    });

				//验证状态等级修改
				$("#form-update-state-level").validate({
	
					event:"keyup" || "blur",
					errorElement: "em",
					errorPlacement: function(error, element) {
						error.appendTo(element.parent("td").next("td"));
					},
					success: function(label) {
						label.text("ok!").addClass("success");
					},
			         rules:{ //定义验证规则 
						update_statelevel_level: {
					 		required: true
					 	}
			         }, 
			         messages:{ //定义提示信息 
			        	 update_statelevel_level: {
			        	 	required:"状态等级不能为空！"
			         	 }
			         } 
			    });
			});
			var g_obj = {};
			g_obj.rightMenuMouseOver = false;
			function rightMenuMouseOver() {
			}
			function rightMenuMouseOut() {
			}
			

			
			jQuery(function($) {
				var grid_selector = "#grid-table";
				var pager_selector = "#grid-pager";

				var value_grid_selector = "#value-grid-table";
				var value_pager_selector = "#value-grid-pager";
				
				var level_grid_selector = "#level-grid-table";
				var level_pager_selector = "#level-grid-pager";
				//override dialog's title function to allow for HTML titles
				$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
					_title: function(title) {
						var $title = this.options.title || '&nbsp;'
						if( ("title_html" in this.options) && this.options.title_html == true )
							title.html($title);
						else title.text($title);
					}
				}));

				//绑定字典项列表
				jQuery(grid_selector).jqGrid({
					//direction: "rtl",
					url: 'dictionary.do?method=listPageByAjax',
					datatype: 'json',
					mtype: 'post',
					prmNames: {sort: 'sort', order: 'order'},
					height: 'auto',
					loadtext: '加载中...',
					colNames:['ID','状态名', '备注'],
					colModel:[
						{name:'id',index:'id', width:60, sorttype:"int", hidden: true},
						{name:'state_key',index:'state_key'},
						{name:'remarks',index:'remarks', sortable:false}
					], 
					caption: '光交箱状态字典',
					viewrecords : true,
					rowNum:10,
					rowList:[10,20,30],
					pager : pager_selector,
					altRows: true,
					//toppager: true,
					ondblClickRow: function() {
						var table = this;
						console.log($('#grid-table').jqGrid('getGridParam','selarrrow'));
					},
					onRightClickRow: function(rowid,irow,icol,e) {
						$('#rightclick_contextmenu').css("left", e.pageX + "px");
						$('#rightclick_contextmenu').css("top", e.pageY + "px");
						$('#rightclick_contextmenu').show();
					},
					loadComplete: function(xhr) {
						console.log(xhr);
					},
					multiselect: true,
			
					loadComplete : function() {
						var table = this;
						setTimeout(function(){
							styleCheckbox(table);
							
							updateActionIcons(table);
							updatePagerIcons(table);
							enableTooltips(table);
						}, 0);
					},
					autowidth: true
			
				});

				//绑定字典项列表的分页
				jQuery(grid_selector).jqGrid('navGrid',pager_selector,
					{ 	//navbar options
						edit: true,
						editfunc: function() {
							var sels = $('#grid-table').jqGrid('getGridParam','selarrrow');
							if (sels.length <= 0)
							{
								$('#add_statekey_mark_div').show();
								$('#add_statekey_mark_label').html('请选择要编辑的行！');
								mySetTimeOut('add_statekey_mark_div', 4000);
							}
							else if (sels.length > 1)
							{
								$('#add_statekey_mark_div').show();
								$('#add_statekey_mark_label').html('只能编辑一行数据，请重新选择！');
								mySetTimeOut('add_statekey_mark_div', 4000);
							}
							else
							{
								$.ajax( {
									type : "post",
									url : "dictionary.do?method=findStateKeyByAjax",  
									data: "id=" + sels[0],
									success : function(data) {
										if (data.resultMark == 1)
										{
											$('#update_statekey_id').val(data.object.id);
											$('#update_statekey_state_key').val(data.object.state_key);
											$('#update_statekey_remarks').html(data.object.remarks);
											var dialog = $("#dialog-update-state-key").removeClass('hide').dialog({
												modal: true,
												width: 800,
												height: 400,
												title: "<div class='widget-header widget-header-small'><h4 class='smaller'>编辑光交箱状态字典</h4></div>",
												title_html: true,
												buttons: [ 
												    {
														text: "修改",
														"class" : "btn btn-xs",
														click: function() {
															if ($('#form-update-state-key').valid())
															{
																$.ajax( {
																	type : "post",
																	url : "dictionary.do?method=updateStateKeyByAjax",
																	data: "id=" + $('#update_statekey_id').val() + "&state_key=" + $('#update_statekey_state_key').val() + "&remarks=" + $('#update_statekey_remarks').val(),
																	success : function(data) {
																		if (data.resultMark == 1)
																		{
																			$('#add_statekey_mark_div').show();
																			$('#add_statekey_mark_label').html('修改状态字典成功！');
																			mySetTimeOut('add_statekey_mark_div', 4000);
																			$('#form-update-state-key')[0].reset();
																			$('#dialog-update-state-key').dialog( "close" );
																			$('#grid-table').trigger("reloadGrid"); 
																		}
																		else
																		{
																			$('#add_statekey_mark_div_error').show();
																			$('#add_statekey_mark_label_error').html('编辑状态字典失败，请重试');
																			mySetTimeOut('add_statekey_mark_div_error', 4000);
																		}
																	},
																	error : function() {
																		$('#add_statekey_mark_div_error').show();
																		$('#add_statekey_mark_label_error').html('编辑状态字典失败，请重试');
																		mySetTimeOut('add_statekey_mark_div_error', 4000);
																	}
																});
															}
												    	}
												    },
													{
														text: "取消",
														"class" : "btn btn-primary btn-xs",
														click: function() {
															$( this ).dialog( "close" ); 
														} 
													}
												]
											});
										}
										else
										{
											$('#add_statevalue_mark_div').show();
											$('#add_statevalue_mark_label').html('编辑状态字典项失败，请重试！');
											mySetTimeOut('add_statevalue_mark_div', 4000);
										}
									},
									error : function() {
										$('#add_statevalue_mark_div').show();
										$('#add_statevalue_mark_label').html('编辑状态字典项失败，请重试！');
										mySetTimeOut('add_statevalue_mark_div', 4000);
									}
								});
							}
						},
						editicon : 'icon-pencil blue',
						add: true,
						addfunc: function(){
							var dialog = $( "#dialog-add-state-key" ).removeClass('hide').dialog({
								modal: true,
								width: 800,
								height: 400,
								title: "<div class='widget-header widget-header-small'><h4 class='smaller'>添加光交箱状态字典</h4></div>",
								title_html: true,
								buttons: [ 
									{
										text: "添加",
										"class" : "btn btn-xs",
										click: function() {
											if ($('#form-add-state-key').valid())
											{
												$.ajax( {
													type : "post",
													url : "dictionary.do?method=addStatekey",
													data : "state_key=" + $('#add_statekey_state_key').val() + '&remarks=' + $('#add_statekey_remarks').val(),
													success : function(data) {
														if (data.resultMark == 1)
														{
															$('#add_statekey_mark_div').show();
															$('#add_statekey_mark_label').html('添加状态字典成功！');
															mySetTimeOut('add_statekey_mark_div', 4000);
															$('#form-add-state-key')[0].reset();
															$('#dialog-add-state-key').dialog( "close" );
															$('#grid-table').trigger("reloadGrid"); 
														}
														else
														{
															$('#add_statekey_mark_div_error').show();
															$('#add_statekey_mark_label_error').html('添加状态字典失败，请重试');
															mySetTimeOut('add_statekey_mark_div_error', 4000);
														}
													},
													error : function() {
														$('#add_statekey_mark_div_error').show();
														$('#add_statekey_mark_label_error').html('添加状态字典失败，请重试');
														mySetTimeOut('add_statekey_mark_div_error', 4000);
													}
												});
											}
										} 
									},
									{
										text: "取消",
										"class" : "btn btn-primary btn-xs",
										click: function() {
											$('#form-add-state-key')[0].reset();
											$( this ).dialog( "close" ); 
										} 
									}
								]
							});
						},
						addicon : 'icon-plus-sign purple',
						delfunc: function() {
							var sels = $('#grid-table').jqGrid('getGridParam','selarrrow');
							if (sels.length <= 0)
							{
								$('#add_statekey_mark_div').show();
								$('#add_statekey_mark_label').html('请选择要删除的行！');
								mySetTimeOut('add_statekey_mark_div', 4000);
							}
							else
							{
								if (confirm("删除是不可恢复的，你确认要删除吗？"))
								{
									var lls = [];
									for (var i = 0; i < sels.length; i ++)
									{
										lls.push(sels[i]);
									}
									$.ajax( {
										type : "post",
										url : "dictionary.do?method=deleteStateKeyByAjax",
										dataType:"json",      
										contentType:"application/json",   
										data:JSON.stringify(lls),
										success : function(data) {
											if (data.resultMark == 1)
											{
												$('#add_statekey_mark_div').show();
												$('#add_statekey_mark_label').html('删除状态字典成功！');
												mySetTimeOut('add_statekey_mark_div', 4000);
												$('#grid-table').trigger("reloadGrid");
											}
											else
											{
												$('#add_statekey_mark_div').show();
												$('#add_statekey_mark_label').html('删除状态字典失败，请重试！');
												mySetTimeOut('add_statekey_mark_div', 4000);
											}
										},
										error : function() {
											$('#add_statekey_mark_div').show();
											$('#add_statekey_mark_label').html('删除状态字典失败，请重试！');
											mySetTimeOut('add_statekey_mark_div', 4000);
										}
									});
								}
							}
						},
						del: true,
						delicon : 'icon-trash red',
						search: false,
						searchicon : 'icon-search orange',
						refresh: true,
						refreshicon : 'icon-refresh green',
						view: true,
						viewfunc: function() {
							var sels = $('#grid-table').jqGrid('getGridParam','selarrrow');
							if (sels.length <= 0)
							{
								$('#add_statekey_mark_div').show();
								$('#add_statekey_mark_label').html('请选择要查看的行！');
								mySetTimeOut('add_statekey_mark_div', 4000);
							}
							else if (sels.length > 1)
							{
								$('#add_statekey_mark_div').show();
								$('#add_statekey_mark_label').html('只能查看一行数据，请重新选择！');
								mySetTimeOut('add_statekey_mark_div', 4000);
							}
							else
							{
								$.ajax( {
									type : "post",
									url : "dictionary.do?method=findStateKeyByAjax",  
									data: "id=" + sels[0],
									success : function(data) {
										if (data.resultMark == 1)
										{
											$('#view_statekey_state_key').val(data.object.state_key);
											$('#view_statekey_remarks').html(data.object.remarks);
											var dialog = $("#dialog-view-state-key").removeClass('hide').dialog({
												modal: true,
												width: 800,
												height: 400,
												title: "<div class='widget-header widget-header-small'><h4 class='smaller'>查看光交箱状态字典</h4></div>",
												title_html: true,
												buttons: [ 
													{
														text: "确定",
														"class" : "btn btn-primary btn-xs",
														click: function() {
															$( this ).dialog( "close" ); 
														} 
													}
												]
											});
										}
										else
										{
											$('#add_statevalue_mark_div').show();
											$('#add_statevalue_mark_label').html('查看状态字典项失败，请重试！');
											mySetTimeOut('add_statevalue_mark_div', 4000);
										}
									},
									error : function() {
										$('#add_statevalue_mark_div').show();
										$('#add_statevalue_mark_label').html('查看状态字典项失败，请重试！');
										mySetTimeOut('add_statevalue_mark_div', 4000);
									}
								});
							}
						},
						viewicon : 'icon-zoom-in grey',
					},
					{
						//edit record form
						//closeAfterEdit: true,
						recreateForm: true,
						beforeShowForm : function(e) {
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
							style_edit_form(form);
						}
					},
					{
						//new record form
						closeAfterAdd: true,
						recreateForm: true,
						viewPagerButtons: false,
						beforeShowForm : function(e) {
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
							style_edit_form(form);
						}
					},
					{
						//delete record form
						recreateForm: true,
						beforeShowForm : function(e) {
							var form = $(e[0]);
							if(form.data('styled')) return false;
							
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
							style_delete_form(form);
							
							form.data('styled', true);
						},
						onClick : function(e) {
							alert(1);
						}
					},
					{
						//search form
						recreateForm: true,
						afterShowSearch: function(e){
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
							style_search_form(form);
						},
						afterRedraw: function(){
							style_search_filters($(this));
						}
						,
						multipleSearch: true,
						/**
						multipleGroup:true,
						showQuery: true
						*/
					},
					{
						//view record form
						recreateForm: true,
						beforeShowForm: function(e){
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
						}
					}
				)
				
				
				//绑定字典项列表
				jQuery(value_grid_selector).jqGrid({
					//direction: "rtl",
					url: 'dictionary.do?method=stateValueListPageByAjax',
					datatype: 'json',
					mtype: 'post',
					prmNames: {sort: 'sort', order: 'order'},
					height: 'auto',
					loadtext: '加载中...',
					colNames:['ID','状态字典','状态值', '状态值等级', '备注'],
					colModel:[
						{name:'id',index:'id', width:60, sorttype:"int", hidden: true},
						{name:'stateKey',index:'stateKey.state_key', sortable:false, width:90, formatter:function(cellvalue, options, rowObject){
							var temp = '';
							if (rowObject.stateKey != null)
							{
								temp = rowObject.stateKey.state_key;
							}
							return temp;
							}
						},
						{name:'state_value',index:'state_value', width:120},
						{name:'stateLevel',index:'stateLevel.level', width:90, formatter:function(cellvalue, options, rowObject){
								return rowObject.stateLevel == null ? "─────" : rowObject.stateLevel.level;
							}
						},
						{name:'remarks',index:'remarks', width:120}
					], 
					caption: '光交箱状态字典项',
					viewrecords : true,
					rowNum:10,
					rowList:[10,20,30],
					pager : value_pager_selector,
					altRows: true,
					//toppager: true,
					ondblClickRow: function() {
						var table = this;
						console.log($('#grid-table').jqGrid('getGridParam','selarrrow'));
					},
					onRightClickRow: function(rowid,irow,icol,e) {
						$('#rightclick_contextmenu').css("left", e.pageX + "px");
						$('#rightclick_contextmenu').css("top", e.pageY + "px");
						$('#rightclick_contextmenu').show();
					},
					loadComplete: function(xhr) {
						console.log(xhr);
					},
					multiselect: true,
			
					loadComplete : function() {
						var table = this;
						setTimeout(function(){
							styleCheckbox(table);
							
							updateActionIcons(table);
							updatePagerIcons(table);
							enableTooltips(table);
						}, 0);
					},
					autowidth: true
			
				});

				//绑定字典项列表的分页
				//navButtons
				jQuery(value_grid_selector).jqGrid('navGrid',value_pager_selector,
					{ 	//navbar options
						edit: true,
						editfunc: function() {
							var sels = $('#value-grid-table').jqGrid('getGridParam','selarrrow');
							if (sels.length <= 0)
							{
								$('#add_statevalue_mark_div').show();
								$('#add_statevalue_mark_label').html('请选择要编辑的行！');
								mySetTimeOut('add_statevalue_mark_div', 4000);
							}
							else if (sels.length > 1)
							{
								$('#add_statevalue_mark_div').show();
								$('#add_statevalue_mark_label').html('只能编辑一行数据，请重新选择！');
								mySetTimeOut('add_statevalue_mark_div', 4000);
							}
							else
							{
								$.ajax( {
									type : "post",
									url : "dictionary.do?method=findStateValueByAjax",  
									data: "id=" + sels[0],
									success : function(data) {
										if (data.resultMark == 1)
										{
											var outerdata = data;
											$.ajax( {
												type : "post",
												url : "dictionary.do?method=listStateKeyAllByAjax",
												success : function(data) {
													if (data.resultMark == 1)
													{
														document.getElementById('update_statevalue_state_key').options.length = 0;
														$("<option value='0'></option>").appendTo($("#update_statevalue_state_key"));
														for (var i = 0; i < data.rows.length; i ++)
														{
															$("<option value='" + data.rows[i].id + "'>" + data.rows[i].state_key + "</option>").appendTo($("#update_statevalue_state_key"));
														}
														$('#update_statevalue_state_key').val(outerdata.object.stateKey.id);
													}
												}
											});
											$.ajax( {
												type : "post",
												url : "dictionary.do?method=listStateLevelAllByAjax",
												success : function(data) {
													if (data.resultMark == 1)
													{
														document.getElementById('update_statevalue_state_level').options.length = 0;
														$("<option value='0'></option>").appendTo($("#update_statevalue_state_level"));
														for (var i = 0; i < data.rows.length; i ++)
														{
															$("<option value='" + data.rows[i].id + "'>" + data.rows[i].level + "</option>").appendTo($("#update_statevalue_state_level"));
														}
														$('#update_statevalue_state_level').val(outerdata.object.stateLevel.id);
													}
												}
											});
											$('#update_statevalue_id').val(data.object.id);
											$('#update_statevalue_state_value').val(data.object.state_value);
											$('#update_statevalue_remarks').html(data.object.remarks);
											var dialog = $("#dialog-update-state-value").removeClass('hide').dialog({
												modal: true,
												width: 800,
												height: 400,
												title: "<div class='widget-header widget-header-small'><h4 class='smaller'>编辑光交箱状态项</h4></div>",
												title_html: true,
												buttons: [ 
												    {
														text: "修改",
														"class" : "btn btn-xs",
														click: function() {
															if ($('#form-update-state-value').valid())
															{
																var stateValue = {};
																stateValue.stateKey = {};
																stateValue.id = $('#update_statevalue_id').val();
																stateValue.stateKey.id = $('#update_statevalue_state_key').val();
																stateValue.state_value = $('#update_statevalue_state_value').val();
																stateValue.stateLevel = {};
																stateValue.stateLevel.id = $('#update_statevalue_state_level').val();
																stateValue.remarks = $('#update_statevalue_remarks').val();
																$.ajax( {
																	type : "post",
																	url : "dictionary.do?method=updateStateValueByAjax",
																	contentType : "application/json;charset=UTF-8",
																	data:JSON.stringify(stateValue),
																	dataType: "json", 
																	success : function(data) {
																		if (data.resultMark == 1)
																		{
																			$('#add_statevalue_mark_div').show();
																			$('#add_statevalue_mark_label').html('修改状态字典项成功！');
																			mySetTimeOut('add_statevalue_mark_div', 4000);
																			$('#form-update-state-value')[0].reset();
																			$('#dialog-update-state-value').dialog( "close" );
																			$('#value-grid-table').trigger("reloadGrid"); 
																		}
																		else
																		{
																			$('#add_statevalue_mark_div_error').show();
																			$('#add_statevalue_mark_label_error').html('编辑状态字典项失败，请重试');
																			mySetTimeOut('add_statevalue_mark_div_error', 4000);
																		}
																	},
																	error : function() {
																		$('#add_statevalue_mark_div_error').show();
																		$('#add_statevalue_mark_label_error').html('编辑状态字典项失败，请重试');
																		mySetTimeOut('add_statevalue_mark_div_error', 4000);
																	}
																});
															}
												    	}
												    },
													{
														text: "取消",
														"class" : "btn btn-primary btn-xs",
														click: function() {
															$( this ).dialog( "close" ); 
														} 
													}
												]
											});
										}
										else
										{
											$('#add_statevalue_mark_div').show();
											$('#add_statevalue_mark_label').html('编辑状态字典项失败，请重试！');
											mySetTimeOut('add_statevalue_mark_div', 4000);
										}
									},
									error : function() {
										$('#add_statevalue_mark_div').show();
										$('#add_statevalue_mark_label').html('编辑状态字典项失败，请重试！');
										mySetTimeOut('add_statevalue_mark_div', 4000);
									}
								});
							}
						},
						editicon : 'icon-pencil blue',
						add: true,
						addfunc: function(){
							$.ajax( {
								type : "post",
								url : "dictionary.do?method=listStateKeyAllByAjax",
								success : function(data) {
									if (data.resultMark == 1)
									{
										document.getElementById('add_statevalue_state_key').options.length = 0;
										$("<option value='0'></option>").appendTo($("#add_statevalue_state_key"));
										for (var i = 0; i < data.rows.length; i ++)
										{
											$("<option value='" + data.rows[i].id + "'>" + data.rows[i].state_key + "</option>").appendTo($("#add_statevalue_state_key"));
										}
									}
								}
							});
							$.ajax( {
								type : "post",
								url : "dictionary.do?method=listStateLevelAllByAjax",
								success : function(data) {
									if (data.resultMark == 1)
									{

										document.getElementById('add_statevalue_state_level').options.length = 0;
										$("<option value='0'></option>").appendTo($("#add_statevalue_state_level"));
										for (var i = 0; i < data.rows.length; i ++)
										{
											$("<option value='" + data.rows[i].id + "'>" + data.rows[i].level + "</option>").appendTo($("#add_statevalue_state_level"));
										}
									}
								}
							});
							var dialog = $( "#dialog-add-state-value" ).removeClass('hide').dialog({
								modal: true,
								width: 800,
								height: 400,
								title: "<div class='widget-header widget-header-small'><h4 class='smaller'>添加光交箱状态值</h4></div>",
								title_html: true,
								buttons: [ 
									{
										text: "添加",
										"class" : "btn btn-xs",
										click: function() {
											if ($('#form-add-state-value').valid())
											{
												var stateValue = {};
												stateValue.stateKey = {};
												stateValue.stateKey.id = $('#add_statevalue_state_key').val();
												stateValue.state_value = $('#add_statevalue_state_value').val();
												stateValue.stateLevel = {};
												stateValue.stateLevel.id = $('#add_statevalue_state_level').val();
												stateValue.remarks = $('#add_statevalue_remarks').val();
												$.ajax( {
													type : "post",
													url : "dictionary.do?method=addStateValue",
													contentType : "application/json;charset=UTF-8",
													data:JSON.stringify(stateValue),
													dataType: "json", 
													success : function(data) {
														if (data.resultMark == 1)
														{
															$('#add_statevalue_mark_div').show();
															$('#add_statevalue_mark_label').html('添加状态字典项成功！');
															mySetTimeOut('add_statevalue_mark_div', 4000);
															$('#form-add-state-value')[0].reset();
															$('#dialog-add-state-value').dialog( "close" );
															$('#value-grid-table').trigger("reloadGrid"); 
														}
														else
														{
															$('#add_statevalue_mark_div_error').show();
															$('#add_statevalue_mark_label_error').html('添加状态字典项失败，请重试');
															mySetTimeOut('add_statevalue_mark_div_error', 4000);
														}
													},
													error : function() {
														$('#add_statevalue_mark_div_error').show();
														$('#add_statevalue_mark_label_error').html('添加状态字典项失败，请重试');
														mySetTimeOut('add_statevalue_mark_div_error', 4000);
													}
												});
											}
										} 
									},
									{
										text: "取消",
										"class" : "btn btn-primary btn-xs",
										click: function() {
											$('#form-add-state-value')[0].reset();
											$( this ).dialog( "close" ); 
										} 
									}
								]
							});
						},
						addicon : 'icon-plus-sign purple',
						del: true,
						delfunc: function() {
							var sels = $('#value-grid-table').jqGrid('getGridParam','selarrrow');
							if (sels.length <= 0)
							{
								$('#add_statevalue_mark_div').show();
								$('#add_statevalue_mark_label').html('请选择要删除的行！');
								mySetTimeOut('add_statevalue_mark_div', 4000);
							}
							else
							{
								if (confirm("删除是不可恢复的，你确认要删除吗？"))
								{
									var lls = [];
									for (var i = 0; i < sels.length; i ++)
									{
										lls.push(sels[i]);
									}
									$.ajax( {
										type : "post",
										url : "dictionary.do?method=delStateValueByAjax",
										dataType:"json",      
										contentType:"application/json",   
										data:JSON.stringify(lls),
										success : function(data) {
											if (data.resultMark == 1)
											{
												$('#add_statevalue_mark_div').show();
												$('#add_statevalue_mark_label').html('删除状态字典项成功！');
												mySetTimeOut('add_statevalue_mark_div', 4000);
												$('#value-grid-table').trigger("reloadGrid");
											}
											else
											{
												$('#add_statevalue_mark_div').show();
												$('#add_statevalue_mark_label').html('删除状态字典项失败，请重试！');
												mySetTimeOut('add_statevalue_mark_div', 4000);
											}
										},
										error : function() {
											$('#add_statevalue_mark_div').show();
											$('#add_statevalue_mark_label').html('删除状态字典项失败，请重试！');
											mySetTimeOut('add_statevalue_mark_div', 4000);
										}
									});
								}
							}
						},
						delicon : 'icon-trash red',
						search: false,
						searchicon : 'icon-search orange',
						refresh: true,
						refreshicon : 'icon-refresh green',
						view: true,
						viewfunc: function() {
							var sels = $('#value-grid-table').jqGrid('getGridParam','selarrrow');
							if (sels.length <= 0)
							{
								$('#add_statevalue_mark_div').show();
								$('#add_statevalue_mark_label').html('请选择要查看的行！');
								mySetTimeOut('add_statevalue_mark_div', 4000);
							}
							else if (sels.length > 1)
							{
								$('#add_statevalue_mark_div').show();
								$('#add_statevalue_mark_label').html('只能查看一行数据，请重新选择！');
								mySetTimeOut('add_statevalue_mark_div', 4000);
							}
							else
							{
								$.ajax( {
									type : "post",
									url : "dictionary.do?method=findStateValueByAjax",  
									data: "id=" + sels[0],
									success : function(data) {
										if (data.resultMark == 1)
										{
											var outerdata = data;
											$.ajax( {
												type : "post",
												url : "dictionary.do?method=listStateKeyAllByAjax",
												success : function(data) {
													if (data.resultMark == 1)
													{
														document.getElementById('view_statevalue_state_key').options.length = 0;
														$("<option value='0'></option>").appendTo($("#view_statevalue_state_key"));
														for (var i = 0; i < data.rows.length; i ++)
														{
															$("<option value='" + data.rows[i].id + "'>" + data.rows[i].state_key + "</option>").appendTo($("#view_statevalue_state_key"));
														}
														$('#view_statevalue_state_key').val(outerdata.object.stateKey.id);
													}
												}
											});
											$.ajax( {
												type : "post",
												url : "dictionary.do?method=listStateLevelAllByAjax",
												success : function(data) {
													if (data.resultMark == 1)
													{

														document.getElementById('view_statevalue_state_level').options.length = 0;
														$("<option value='0'></option>").appendTo($("#view_statevalue_state_level"));
														for (var i = 0; i < data.rows.length; i ++)
														{
															$("<option value='" + data.rows[i].id + "'>" + data.rows[i].level + "</option>").appendTo($("#view_statevalue_state_level"));
														}
														$('#view_statevalue_state_level').val(outerdata.object.stateLevel.id);
													}
												}
											});
											$('#view_statevalue_state_value').val(data.object.state_value);
											$('#view_statevalue_remarks').html(data.object.remarks);
											var dialog = $("#dialog-view-state-value").removeClass('hide').dialog({
												modal: true,
												width: 800,
												height: 400,
												title: "<div class='widget-header widget-header-small'><h4 class='smaller'>查看光交箱状态项</h4></div>",
												title_html: true,
												buttons: [ 
													{
														text: "确定",
														"class" : "btn btn-primary btn-xs",
														click: function() {
															$( this ).dialog( "close" ); 
														} 
													}
												]
											});
										}
										else
										{
											$('#add_statevalue_mark_div').show();
											$('#add_statevalue_mark_label').html('查看状态字典项失败，请重试！');
											mySetTimeOut('add_statevalue_mark_div', 4000);
										}
									},
									error : function() {
										$('#add_statevalue_mark_div').show();
										$('#add_statevalue_mark_label').html('查看状态字典项失败，请重试！');
										mySetTimeOut('add_statevalue_mark_div', 4000);
									}
								});
							}
						},
						viewicon : 'icon-zoom-in grey',
					},
					{
						//edit record form
						//closeAfterEdit: true,
						recreateForm: true,
						beforeShowForm : function(e) {
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
							style_edit_form(form);
						}
					},
					{
						//new record form
						closeAfterAdd: true,
						recreateForm: true,
						viewPagerButtons: false,
						beforeShowForm : function(e) {
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
							style_edit_form(form);
						}
					},
					{
						//delete record form
						recreateForm: true,
						beforeShowForm : function(e) {
							var form = $(e[0]);
							if(form.data('styled')) return false;
							
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
							style_delete_form(form);
							
							form.data('styled', true);
						},
						onClick : function(e) {
							alert(1);
						}
					},
					{
						//search form
						recreateForm: true,
						afterShowSearch: function(e){
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
							style_search_form(form);
						},
						afterRedraw: function(){
							style_search_filters($(this));
						}
						,
						multipleSearch: true,
						/**
						multipleGroup:true,
						showQuery: true
						*/
					},
					{
						//view record form
						recreateForm: true,
						beforeShowForm: function(e){
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
						}
					}
				)
				
				
				//绑定字典等级列表
				jQuery(level_grid_selector).jqGrid({
					//direction: "rtl",
					url: 'dictionary.do?method=stateLevelListPageByAjax',
					datatype: 'json',
					mtype: 'post',
					prmNames: {sort: 'sort', order: 'order'},
					height: 'auto',
					loadtext: '加载中...',
					colNames:['ID','状态字典等级','等级缩略图', '备注'],
					colModel:[
						{name:'id',index:'id', width:60, sorttype:"int", hidden: true},
						{name:'level',index:'level', width:120},
						{name:'state_image',index:'state_image', width:90, formatter:function(cellvalue, options, rowObject){
								return (rowObject.state_image == null || rowObject.state_image == '') ? "─────" : '<img  src="../' + rowObject.state_image + '" height="50"/>';
							}
						},
						{name:'remarks',index:'remarks', width:120}
					], 
					caption: '光交箱状态字典等级',
					viewrecords : true,
					rowNum:10,
					rowList:[10,20,30],
					pager : level_pager_selector,
					altRows: true,
					//toppager: true,
					ondblClickRow: function() {
						var table = this;
						console.log($('#grid-table').jqGrid('getGridParam','selarrrow'));
					},
					onRightClickRow: function(rowid,irow,icol,e) {
						$('#rightclick_contextmenu').css("left", e.pageX + "px");
						$('#rightclick_contextmenu').css("top", e.pageY + "px");
						$('#rightclick_contextmenu').show();
					},
					loadComplete: function(xhr) {
						console.log(xhr);
					},
					multiselect: true,
			
					loadComplete : function() {
						var table = this;
						setTimeout(function(){
							styleCheckbox(table);
							
							updateActionIcons(table);
							updatePagerIcons(table);
							enableTooltips(table);
						}, 0);
					},
					autowidth: true
			
				});

				//绑定字典等级列表的分页
				//navButtons
				jQuery(level_grid_selector).jqGrid('navGrid',level_pager_selector,
					{ 	//navbar options
						edit: true,
						editfunc: function() {
							var sels = $('#level-grid-table').jqGrid('getGridParam','selarrrow');
							if (sels.length <= 0)
							{
								$('#add_statelevel_mark_div').show();
								$('#add_statelevel_mark_label').html('请选择要编辑的行！');
								mySetTimeOut('add_statelevel_mark_div', 4000);
							}
							else if (sels.length > 1)
							{
								$('#add_statelevel_mark_div').show();
								$('#add_statelevel_mark_label').html('只能编辑一行数据，请重新选择！');
								mySetTimeOut('add_statelevel_mark_div', 4000);
							}
							else
							{

								$('#update_statelevel_state_image_td').html('');
								$('#update_statelevel_state_image_td').html('<input id="update_statelevel_state_image" name="update_statelevel_state_image" type="file"/><div id="update_statelevel_state_image_view" width="100%"></div>');
								$('#update_statelevel_state_image').ace_file_input({});
								$.ajax( {
									type : "post",
									url : "dictionary.do?method=findStateLevelByAjax",  
									data: "id=" + sels[0],
									success : function(data) {
										if (data.resultMark == 1)
										{
											$('#update_statelevel_id').val(data.object.id);
											$('#update_statelevel_level').val(data.object.level);
											$('#update_statelevel_state_image_view').html('<img style="max-width:100px !important" src="../' + data.object.state_image + '" />');
											$('#update_statelevel_remarks').html(data.object.remarks);
											var dialog = $("#dialog-update-state-level").removeClass('hide').dialog({
												modal: true,
												width: 800,
												height: 400,
												title: "<div class='widget-header widget-header-small'><h4 class='smaller'>编辑光交箱状态等级</h4></div>",
												title_html: true,
												buttons: [ 
												    {
														text: "修改",
														"class" : "btn btn-xs",
														click: function() {
															if ($('#form-update-state-level').valid())
															{
																$.ajaxFileUpload({
																    //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
																    url:'dictionary.do?method=updateStateLevel',
																    data: {id : $('#update_statelevel_id').val(), level : $('#update_statelevel_level').val(), remarks : $('#update_statelevel_remarks').val()},
																    secureuri:false,                       //是否启用安全提交,默认为false
																    fileElementId:'update_statelevel_state_image',           //文件选择框的id属性
																    dataType:'text',                       //服务器返回的格式,可以是json或xml等
																    success:function(data, status){        //服务器响应成功时的处理函数
																		var resultMark = data.substr(data.indexOf('</pre>') - 1, 1);
																		if (resultMark == 1)
																		{
																			$('#add_statelevel_mark_div').show();
																			$('#add_statelevel_mark_label').html('修改状态字典等级成功！');
																			mySetTimeOut('add_statelevel_mark_div', 4000);
																			$('#form-update-state-level')[0].reset();
																			$('#dialog-update-state-level').dialog( "close" );
																			$('#level-grid-table').trigger("reloadGrid"); 
																		}
																		else
																		{
																			$('#add_statelevel_mark_div_error').show();
																			$('#add_statelevel_mark_label_error').html('修改状态字典等级失败，请重试');
																			mySetTimeOut('add_statelevel_mark_div_error', 4000);
																		}
																    },
																    error:function(data, status, e){ //服务器响应失败时的处理函数
																		$('#add_statelevel_mark_div_error').show();
																		$('#add_statelevel_mark_label_error').html('修改状态字典等级失败，请重试');
																		mySetTimeOut('add_statelevel_mark_div_error', 4000);
																    }
																});
															}
												    	}
												    },
													{
														text: "取消",
														"class" : "btn btn-primary btn-xs",
														click: function() {
															$( this ).dialog( "close" ); 
														} 
													}
												]
											});
										}
										else
										{
											$('#add_statelevel_mark_div').show();
											$('#add_statelevel_mark_label').html('编辑状态字典等级失败，请重试！');
											mySetTimeOut('add_statelevel_mark_div', 4000);
										}
									},
									error : function() {
										$('#add_statelevel_mark_div').show();
										$('#add_statelevel_mark_label').html('编辑状态字典等级失败，请重试！');
										mySetTimeOut('add_statelevel_mark_div', 4000);
									}
								});
							}
						},
						editicon : 'icon-pencil blue',
						add: true,
						addfunc: function(){
							$('#add_statelevel_state_image_td').html('');
							$('#add_statelevel_state_image_td').html('<input id="add_statelevel_state_image" name="add_statelevel_state_image" type="file" class="required"/>');
							$('#add_statelevel_state_image').ace_file_input({});
							var dialog = $( "#dialog-add-state-level" ).removeClass('hide').dialog({
								modal: true,
								width: 800,
								height: 400,
								title: "<div class='widget-header widget-header-small'><h4 class='smaller'>添加光交箱状态等级</h4></div>",
								title_html: true,
								buttons: [ 
									{
										text: "添加",
										"class" : "btn btn-xs",
										click: function() {
											if ($('#form-add-state-level').valid())
											{
												$.ajaxFileUpload({
												    //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
												    url:'dictionary.do?method=addStateLevel',
												    data: {level : $('#add_statelevel_level').val(), remarks : $('#add_statelevel_remarks').val()},
												    secureuri:false,                       //是否启用安全提交,默认为false
												    fileElementId:'add_statelevel_state_image',           //文件选择框的id属性
												    dataType:'text',                       //服务器返回的格式,可以是json或xml等
												    success:function(data, status){        //服务器响应成功时的处理函数
														var resultMark = data.substr(data.indexOf('</pre>') - 1, 1);
														if (resultMark == 1)
														{
															$('#add_statelevel_mark_div').show();
															$('#add_statelevel_mark_label').html('添加状态字典等级成功！');
															mySetTimeOut('add_statelevel_mark_div', 4000);
															$('#form-add-state-level')[0].reset();
															$('#dialog-add-state-level').dialog( "close" );
															$('#level-grid-table').trigger("reloadGrid"); 
														}
														else
														{
															$('#add_statelevel_mark_div_error').show();
															$('#add_statelevel_mark_label_error').html('添加状态字典等级失败，请重试');
															mySetTimeOut('add_statelevel_mark_div_error', 4000);
														}
												    },
												    error:function(data, status, e){ //服务器响应失败时的处理函数
														$('#add_statelevel_mark_div_error').show();
														$('#add_statelevel_mark_label_error').html('添加状态字典等级失败，请重试');
														mySetTimeOut('add_statelevel_mark_div_error', 4000);
												    }
												});
											}
										} 
									},
									{
										text: "取消",
										"class" : "btn btn-primary btn-xs",
										click: function() {
											$('#form-add-state-level')[0].reset();
											$( this ).dialog( "close" ); 
										} 
									}
								]
							});
						},
						addicon : 'icon-plus-sign purple',
						del: true,
						delfunc: function() {
							var sels = $('#level-grid-table').jqGrid('getGridParam','selarrrow');
							if (sels.length <= 0)
							{
								$('#add_statelevel_mark_div').show();
								$('#add_statelevel_mark_label').html('请选择要删除的行！');
								mySetTimeOut('add_statelevel_mark_div', 4000);
							}
							else
							{
								if (confirm("删除是不可恢复的，你确认要删除吗？"))
								{
									var lls = [];
									for (var i = 0; i < sels.length; i ++)
									{
										lls.push(sels[i]);
									}
									$.ajax( {
										type : "post",
										url : "dictionary.do?method=delStateLevelByAjax",
										dataType:"json",      
										contentType:"application/json",   
										data:JSON.stringify(lls),
										success : function(data) {
											if (data.resultMark == 1)
											{
												$('#add_statelevel_mark_div').show();
												$('#add_statelevel_mark_label').html('删除状态字典等级成功！');
												mySetTimeOut('add_statelevel_mark_div', 4000);
												$('#level-grid-table').trigger("reloadGrid");
											}
											else
											{
												$('#add_statelevel_mark_div').show();
												$('#add_statelevel_mark_label').html('删除状态字典等级失败，请重试！');
												mySetTimeOut('add_statelevel_mark_div', 4000);
											}
										},
										error : function() {
											$('#add_statelevel_mark_div').show();
											$('#add_statelevel_mark_label').html('删除状态字典等级失败，请重试！');
											mySetTimeOut('add_statelevel_mark_div', 4000);
										}
									});
								}
							}
						},
						delicon : 'icon-trash red',
						search: false,
						searchicon : 'icon-search orange',
						refresh: true,
						refreshicon : 'icon-refresh green',
						view: true,
						viewfunc: function() {
							var sels = $('#level-grid-table').jqGrid('getGridParam','selarrrow');
							if (sels.length <= 0)
							{
								$('#add_statelevel_mark_div').show();
								$('#add_statelevel_mark_label').html('请选择要查看的行！');
								mySetTimeOut('add_statelevel_mark_div', 4000);
							}
							else if (sels.length > 1)
							{
								$('#add_statelevel_mark_div').show();
								$('#add_statelevel_mark_label').html('只能查看一行数据，请重新选择！');
								mySetTimeOut('add_statelevel_mark_div', 4000);
							}
							else
							{
								$.ajax( {
									type : "post",
									url : "dictionary.do?method=findStateLevelByAjax",  
									data: "id=" + sels[0],
									success : function(data) {
										if (data.resultMark == 1)
										{
											$('#view_statelevel_level').val(data.object.level);
											$('#view_statelevel_state_image').html('<img style="max-width:100px !important" src="../' + data.object.state_image + '" />');
											$('#view_statelevel_remarks').html(data.object.remarks);
											var dialog = $("#dialog-view-state-level").removeClass('hide').dialog({
												modal: true,
												width: 800,
												height: 400,
												title: "<div class='widget-header widget-header-small'><h4 class='smaller'>查看光交箱状态等级</h4></div>",
												title_html: true,
												buttons: [ 
													{
														text: "确定",
														"class" : "btn btn-primary btn-xs",
														click: function() {
															$( this ).dialog( "close" ); 
														} 
													}
												]
											});
										}
										else
										{
											$('#add_statelevel_mark_div').show();
											$('#add_statelevel_mark_label').html('查看状态字典项失败，请重试！');
											mySetTimeOut('add_statelevel_mark_div', 4000);
										}
									},
									error : function() {
										$('#add_statelevel_mark_div').show();
										$('#add_statelevel_mark_label').html('查看状态字典项失败，请重试！');
										mySetTimeOut('add_statelevel_mark_div', 4000);
									}
								});
							}
						},
						viewicon : 'icon-zoom-in grey',
					},
					{
						//edit record form
						//closeAfterEdit: true,
						recreateForm: true,
						beforeShowForm : function(e) {
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
							style_edit_form(form);
						}
					},
					{
						//new record form
						closeAfterAdd: true,
						recreateForm: true,
						viewPagerButtons: false,
						beforeShowForm : function(e) {
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
							style_edit_form(form);
						}
					},
					{
						//delete record form
						recreateForm: true,
						beforeShowForm : function(e) {
							var form = $(e[0]);
							if(form.data('styled')) return false;
							
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
							style_delete_form(form);
							
							form.data('styled', true);
						},
						onClick : function(e) {
							alert(1);
						}
					},
					{
						//search form
						recreateForm: true,
						afterShowSearch: function(e){
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
							style_search_form(form);
						},
						afterRedraw: function(){
							style_search_filters($(this));
						}
						,
						multipleSearch: true,
						/**
						multipleGroup:true,
						showQuery: true
						*/
					},
					{
						//view record form
						recreateForm: true,
						beforeShowForm: function(e){
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
						}
					}
				)
			
			
				
				function style_edit_form(form) {
					//enable datepicker on "sdate" field and switches for "stock" field
					form.find('input[name=sdate]').datepicker({format:'yyyy-mm-dd' , autoclose:true})
						.end().find('input[name=stock]')
							  .addClass('ace ace-switch ace-switch-5').wrap('<label class="inline" />').after('<span class="lbl"></span>');
			
					//update buttons classes
					var buttons = form.next().find('.EditButton .fm-button');
					buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();//ui-icon, s-icon
					buttons.eq(0).addClass('btn-primary').prepend('<i class="icon-ok"></i>');
					buttons.eq(1).prepend('<i class="icon-remove"></i>')
					
					buttons = form.next().find('.navButton a');
					buttons.find('.ui-icon').remove();
					buttons.eq(0).append('<i class="icon-chevron-left"></i>');
					buttons.eq(1).append('<i class="icon-chevron-right"></i>');		
				}
			
				function style_delete_form(form) {
					var buttons = form.next().find('.EditButton .fm-button');
					buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();//ui-icon, s-icon
					buttons.eq(0).addClass('btn-danger').prepend('<i class="icon-trash"></i>');
					buttons.eq(1).prepend('<i class="icon-remove"></i>')
				}
				
				function style_search_filters(form) {
					form.find('.delete-rule').val('X');
					form.find('.add-rule').addClass('btn btn-xs btn-primary');
					form.find('.add-group').addClass('btn btn-xs btn-success');
					form.find('.delete-group').addClass('btn btn-xs btn-danger');
				}
				function style_search_form(form) {
					var dialog = form.closest('.ui-jqdialog');
					var buttons = dialog.find('.EditTable')
					buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'icon-retweet');
					buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'icon-comment-alt');
					buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'icon-search');
				}
				
				function beforeDeleteCallback(e) {
					var form = $(e[0]);
					if(form.data('styled')) return false;
					
					form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
					style_delete_form(form);
					
					form.data('styled', true);
				}
				
				function beforeEditCallback(e) {
					var form = $(e[0]);
					form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
					style_edit_form(form);
				}
			
			
			
				//it causes some flicker when reloading or navigating grid
				//it may be possible to have some custom formatter to do this as the grid is being created to prevent this
				//or go back to default browser checkbox styles for the grid
				function styleCheckbox(table) {
				/**
					$(table).find('input:checkbox').addClass('ace')
					.wrap('<label />')
					.after('<span class="lbl align-top" />')
			
			
					$('.ui-jqgrid-labels th[id*="_cb"]:first-child')
					.find('input.cbox[type=checkbox]').addClass('ace')
					.wrap('<label />').after('<span class="lbl align-top" />');
				*/
				}
				
			
				//unlike navButtons icons, action icons in rows seem to be hard-coded
				//you can change them like this in here if you want
				function updateActionIcons(table) {
					/**
					var replacement = 
					{
						'ui-icon-pencil' : 'icon-pencil blue',
						'ui-icon-trash' : 'icon-trash red',
						'ui-icon-disk' : 'icon-ok green',
						'ui-icon-cancel' : 'icon-remove red'
					};
					$(table).find('.ui-pg-div span.ui-icon').each(function(){
						var icon = $(this);
						var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
						if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
					})
					*/
				}
				
				//replace icons with FontAwesome icons like above
				function updatePagerIcons(table) {
					var replacement = 
					{
						'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
						'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
						'ui-icon-seek-next' : 'icon-angle-right bigger-140',
						'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
					};
					$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
						var icon = $(this);
						var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
						
						if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
					})
				}
			
				function enableTooltips(table) {
					$('.navtable .ui-pg-button').tooltip({container:'body'});
					$(table).find('.ui-pg-div').tooltip({container:'body'});
				}
			
				//var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');

				//Menu
				$("#menu").menu();
			
			});
		</script>
		
		<script src="js/ajaxfileupload.js"></script>
	</body>
</html>

