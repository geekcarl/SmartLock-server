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
		<meta charset="UTF-8">
		<title>光交箱监控管理系统</title>
		<link id="link" rel="stylesheet" type="text/css"
			href="themes/bootstrap/easyui.css">
		<link rel="stylesheet" type="text/css" href="themes/icon.css">
		<link rel="stylesheet" type="text/css" href="css/demo.css">
		<link rel="stylesheet" type="text/css" href="css/applenav_style.css" />
		<style>
html,body {
	margin: 0;
	padding: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}

a,a:visited {
	color: black;
	text-decoration: none;
}
</style>
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="js/applenav.js"></script>
		<script type="text/javascript">
		$(document).ready(function(){
			
		});
	</script>
	</head>
	<body>

		<div class="easyui-layout" id="body"
			style="width: 100%; height: 100% !important">
			<div data-options="region:'south',split:true" id="south"
				style="height: 70px;">
			</div>
			<!-- 操作人员列表 -->
			<div data-options="region:'center'" id="main">
				<table width="100%" height="auto" id="operators_list"
					title="操作员列表" style=""
					data-options="rownumbers:true,singleSelect:true,loadMsg:'正在加载,请稍候...',pageSize:15,pagination:true,fitColumns:true">
					<thead>
						<tr>
							<th data-options="field:'id',align:'center',hidden:true">
								主键
							</th>
							<th data-options="field:'department_id',align:'center',hidden:true">
								主键
							</th>
							<th data-options="field:'op_no',align:'center',sortable:true" width="10%">
								编号
							</th>
							<th data-options="field:'name',align:'center',sortable:true" width="20%">
								姓名
							</th>
							<th data-options="field:'sex',align:'center',sortable:true" width="10%">
								性别
							</th>
							<th data-options="field:'department_name',align:'center',sortable:true" width="20%">
								部门
							</th>
							<th data-options="field:'phone_no',align:'center',sortable:true" width="20%">
								电话
							</th>
							<th data-options="field:'email',align:'center',sortable:true" width="20%">
								邮箱
							</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		<!-- 添加业务操作人员 -->
		<div id="operator_add" class="easyui-window" title="添加业务操作人员"
			data-options="modal:true,closed:true,iconCls:'icon-save'"
			style="width: 500px; height: 500px; padding: 10px;">
			<div class="easyui-panel" title="基本信息" style="width:450px">
	        <div style="padding:10px 50px 20px 50px">
		        <form id="a_form" method="post">
		            <table cellpadding="5">
		                <tr>
		                    <td>编号:</td>
		                    <td><input class="easyui-validatebox" type="text" id="a_op_no" name="op_no" data-options="required:true"></input></td>
		                </tr>
		                <tr>
		                    <td>姓名:</td>
		                    <td><input class="easyui-validatebox" type="text" id="a_name" name="name" data-options="required:true"></input></td>
		                </tr>
		                
		                <tr>
		                    <td>部门:</td>
		                    <td>
		                    	<input class="easyui-validatebox" type="text" id="a_department_name" name="department.name" readonly="readonly" data-options="required:true" onfocus="operateDepartmentTreePanel('a_treepanel','open','a_tree')"></input>
		                    	<input type="hidden" id="a_department_id" name="department.id"/>
		                    	<div class="easyui-panel" id="a_treepanel" style="padding: 5px;" data-options="closed:true,title:'选择部门',closable:true">
									<ul class="easyui-tree" id="a_tree" data-options="animate:true"></ul>
								</div>
		                    </td>
		                </tr>
		                <tr>
		                    <td>邮箱:</td>
		                    <td><input class="easyui-validatebox" type="text" id="a_email" name="email" data-options="validType:'email'"></input></td>
		                </tr>
		                <tr>
		                    <td>电话:</td>
		                    <td><input type="text" id="a_phone_no" name="phone_no"></input></td>
		                </tr>
		                <tr>
		                    <td>性别:</td>
		                    <td>
		                        <select class="easyui-combobox" name="sex" id="a_sex"><option value="1">男</option><option value="0">女</option></select>
		                    </td>
		                </tr>
		                <tr>
		                    <td>住址:</td>
		                    <td><input type="text" id="a_address" name="address"></input></td>
		                </tr>
		                <tr>
		                    <td>备注:</td>
		                    <td><textarea id="a_remarks" rows="5" cols="40" name="remarks"></textarea></td>
		                </tr>
		            </table>
		        </form>
		        <div style="text-align:center;padding:5px">
		            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="return submitAddForm()">提交</a>
		        </div>
		        </div>
		    </div>
		</div>
		
		<!-- 修改业务操作人员信息 -->
		<div id="operator_update" class="easyui-window" title="修改业务操作人员"
			data-options="modal:true,closed:true,iconCls:'icon-save'"
			style="width: 500px; height: 500px; padding: 10px;">
			<div class="easyui-panel" title="基本信息" style="width:450px">
	        <div style="padding:10px 50px 20px 50px">
		        <form id="u_form" method="post">
		            <table cellpadding="5">
		                <tr>
		                    <td>编号:</td>
		                    <td><input class="easyui-validatebox" type="text" id="u_op_no" name="op_no" data-options="required:true"></input></td>
		                </tr>
		                <tr>
		                    <td>姓名:</td>
		                    <td><input class="easyui-validatebox" type="text" id="u_name" name="name" data-options="required:true"></input></td>
		                </tr>
		                
		                <tr>
		                    <td>部门:</td>
		                    <td>
		                    	<input type="text" id="u_department_name" name="department.name" readonly="readonly" onfocus="operateDepartmentTreePanel('u_treepanel','open','u_tree')"></input>
		                    	<input type="hidden" id="u_department_id" name="department.id"/>
		                    	<input type="hidden" id="u_id" name="id"/>
		                    	<div class="easyui-panel" id="u_treepanel" style="padding: 5px;" data-options="closed:true,title:'选择部门',closable:true">
									<ul class="easyui-tree" id="u_tree" data-options="animate:true"></ul>
								</div>
		                    </td>
		                </tr>
		                <tr>
		                    <td>邮箱:</td>
		                    <td><input class="easyui-validatebox" type="text" id="u_email" name="email" data-options="validType:'email'"></input></td>
		                </tr>
		                <tr>
		                    <td>电话:</td>
		                    <td><input type="text" id="u_phone_no" name="phone_no"></input></td>
		                </tr>
		                <tr>
		                    <td>性别:</td>
		                    <td>
		                        <select class="easyui-combobox" name="sex" id="u_sex"><option value="1">男</option><option value="0">女</option></select>
		                    </td>
		                </tr>
		                <tr>
		                    <td>住址:</td>
		                    <td><input type="text" id="u_address" name="address"></input></td>
		                </tr>
		                <tr>
		                    <td>备注:</td>
		                    <td><textarea id="u_remarks" rows="5" cols="40" name="remarks"></textarea></td>
		                </tr>
		            </table>
		        </form>
		        <div style="text-align:center;padding:5px">
		            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="return submitUpdateForm()">提交</a>
		        </div>
		        </div>
		    </div>
		</div>

		<!--通用html部分开始-->
		<div id="div1" style="">
			<a href="index"><img src="images/box.png"
					width="48" longdesc="1" title="光交箱监控" /> </a>
			<a href="department.do?method=list"><img
					src="images/department.png" width="48" longdesc="1" title="部门管理" />
			</a>
			<a href="http://www.miaov.com/"><img src="images/key.png"
					width="48" longdesc="2" title="钥匙管理" /> </a>
			<a href="http://www.miaov.com/"><img src="images/work.png"
					width="48" longdesc="3" title="工单管理" /> </a>
			<a href="operators.do?method=list"><img src="images/staff.png"
					width="48" longdesc="4" title="员工管理" /> </a>
			<a href="http://www.miaov.com/"><img src="images/resources.png"
					width="48" longdesc="5" title="纤芯资源管理" /> </a>
			<a href="javascript:void(0);" onclick=$('#s_set').window('open');><img
					src="images/setting.png" width="48" longdesc="5" title="系统设置" /> </a>
			<a href="http://www.miaov.com/"><img src="images/user.png"
					width="48" longdesc="5" alt="5" title="个人信息管理" /> </a>
		</div>
		<div id="s_set" class="easyui-window" title="系统设置"
			data-options="modal:true,closed:true,iconCls:'icon-save'"
			style="width: 500px; height: auto; padding: 10px;">
			主体&nbsp;
			<select onchange="changecss(this.value)">
				<option value="default">
					蓝色
				</option>
				<option value="metro">
					灰白
				</option>
				<option value="black">
					黑色
				</option>
				<option value="gray">
					灰色
				</option>
				<option value="bootstrap">
					白色
				</option>
			</select>
		</div>
		<!--通用html部分结束-->
		<script type="text/javascript">
		//初始化部门树和操作人员列表
		$(function() {
			$('#operators_list').datagrid({
                fitColumns: true,
				loadMsg : '正在加载,请稍候...',
				pageList: [15,20,25,30],
				pageNumber: 1,
				pageSize: 15,
				url:'operators.do?method=listPageByAjax',
				loadFilter: function(data){
					if (data.resultMark == 1) {
						var newdata = {};
						newdata.total = data.total;
						var rows = [];
						for (var i = 0; i < data.rows.length; i ++)
						{
							var row = {
									"id" : data.rows[i].id,
									"department_id" : data.rows[i].department.id,
									"op_no" : data.rows[i].op_no,
									"name" : data.rows[i].name,
									"sex" : data.rows[i].sex == 1 ? '男' : '女',
									"department_name" : data.rows[i].department.name,
									"phone_no" : data.rows[i].phone_no,
									"email" : data.rows[i].email
									};
							rows.push(row);
						}
						newdata.rows = rows;
						return newdata;
					}
				},
				onDblClickRow:function(rowIndex, rowData){
					prepareUpdate();
				}
				//data : pageInfo
			});
		});
		
		$(function() {
			initDataAndEvent();
		})
		
		function initDataAndEvent() {
			
			var pager = $('#operators_list').datagrid('getPager'); // get the pager of datagrid
			pager.pagination( {
		            beforePageText: '第',//页数文本框前显示的汉字           
		            afterPageText: '页    共 {pages} 页',           
		            displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
					buttons : [ {
						iconCls : 'icon-search',
						handler : function() {
							alert('search');
						}
					}, {
						iconCls : 'icon-add',
						handler : function() {
							$('#operator_add').window('open');
						}
					}, {
						iconCls : 'icon-edit',
						handler : function() {
							var selects = $('#operators_list').datagrid('getSelections');
							if (selects.length > 0)
							{
								prepareUpdate();
							}
							else
							{
								alert('请选中一行信息');
							}
						}
					}, {
						iconCls : 'icon-cancel',
						handler : function() {
							if ($('#operators_list').datagrid('getSelections').length > 0)
							{
								$.ajax( {
									type : "post",
									url : "operators.do?method=delete",
									data: "id=" + $('#operators_list').datagrid('getSelected').id,
									success : function(data) {
										if (data.resultMark == 1)
										{
											$('#operators_list').datagrid('reload');
										}
										else
										{
											alert("删除失败,请重试");
										}
									},
									error : function() {
										alert("删除失败,请重试");
									}
								});
							}
							else
							{
								alert('请选中一行信息');
							}
						}
					} ],
					onRefresh:function(pageNumber, pageSize){
					}
				});
				$('#u_tree').tree({
					onSelect:function(node){
						$('#u_department_name').attr({'value' : node.text});
						$('#u_department_id').attr({'value' : node.id});
						$('#u_treepanel').panel('close');
					}
				});
				$('#a_tree').tree({
					onSelect:function(node){
					$('#a_department_name').attr({'value' : node.text});
					$('#a_department_id').attr({'value' : node.id});
					$('#a_treepanel').panel('close');
				}
			});
		}

		function prepareUpdate() {
			$.ajax( {
				type : "get",
				url : "operators.do?method=findByAjax",
				data: "id=" + $('#operators_list').datagrid('getSelected').id,
				success : function(data) {
					if (data != null)
					{
						$('#u_id').val(data.id);
						$('#u_department_id').val(data.department.id);
						$('#u_department_name').val(data.department.name);
						$('#u_op_no').val(data.op_no);
						$('#u_phone_no').val(data.phone_no);
						$('#u_name').val(data.name);
						$('#u_sex').val(data.sex);
						$('#u_address').val(data.address);
						$('#u_remarks').text(data.remarks);
						$('#u_email').val(data.email);
						$('#operator_update').window('open');
					}
				},
				error : function() {
					alert("加载失败,请重试");
				}
			});
		}
		
		function jump(where) {
			$('#main').panel('open').panel('refresh', where);
		}

		//将一个表单的数据返回成JSON对象     
        $.fn.serializeObject = function() {     
          var o = {};     
          var a = this.serializeArray();     
          $.each(a, function() {     
            if (o[this.name]) {     
              if (!o[this.name].push) {     
                o[this.name] = [ o[this.name] ];     
              }     
              o[this.name].push(this.value || '');     
            } else {     
              o[this.name] = this.value || '';     
            }     
          });     
          return o;     
        };

        //添加表单提交
		function submitAddForm(){
			var operators = {};
			operators.op_no = $('#a_op_no').val();
			operators.name = $('#a_name').val();
			operators.phone_no = $('#a_phone_no').val();
			operators.address = $('#a_address').val();
			operators.remarks = $('#a_remarks').val();
			operators.department = {};
			operators.department.id = $('#a_department_id').val();
			operators.sex = $('#a_sex').val();
			operators.email = $('#a_email').val();
			$.ajax( {
				type : "post",
				url : "operators.do?method=add",
				contentType : "application/json;charset=UTF-8",
				data:JSON.stringify(operators),
				dataType: "json", 
				success : function(data) {
					if (data.resultMark == 1)
					{
						$('#operators_list').datagrid('load');
						$('#operator_add').window('close');
					}
					else
					{
						alert('添加失败，请重试');
					}
				},
				error : function() {
					alert("添加失败,请重试");
				}
			});
			return false;	
		}

        //修改表单提交
		function submitUpdateForm(){
			var operators = {};
			operators.id = $('#u_id').val();
			operators.op_no = $('#u_op_no').val();
			operators.name = $('#u_name').val();
			operators.phone_no = $('#u_phone_no').val();
			operators.address = $('#u_address').val();
			operators.remarks = $('#u_remarks').val();
			operators.department = {};
			operators.department.id = $('#u_department_id').val();
			operators.sex = $('#u_sex').val();
			operators.email = $('#u_email').val();
			$.ajax( {
				type : "post",
				url : "operators.do?method=update",
				contentType : "application/json;charset=UTF-8",
				data:JSON.stringify(operators),
				dataType: "json", 
				success : function(data) {
					if (data.resultMark == 1)
					{
						$('#operators_list').datagrid('reload');
						$('#operator_update').window('close');
					}
					else
					{
						alert('修改失败，请重试');
					}
				},
				error : function() {
					alert("修改失败,请重试");
				}
			});
			return false;	
		}

		
		function operateDepartmentTreePanel(panelid, operate, treeid){
			if (operate == 'open')
			{
				$.ajax( {
					type : "get",
					url : "department.do?method=findAllByAjax",
					contentType : "application/json",
					dataType: "json", 
					success : function(data) {
						$('#' + treeid).tree( {
							data : data
						});
						$('#' + panelid).panel(operate);
					},
					error : function() {
						alert("加载出错,请重试");
					}
				});
			}
			else
			{
				$('#' + panelid).panel(operate);
			}
		}

		function operatePanelTree(treeid, departmentid) {
			$('#' + departmentid).html($('#' + treeid).tree('getSelected').name);
		}
	
		//通用js部分开始
	
		function changecss(css) {
			document.getElementById('link').href = 'themes/' + css + '/easyui.css';
		}
		//通用js部分结束
	</script>
	</body>
</html>