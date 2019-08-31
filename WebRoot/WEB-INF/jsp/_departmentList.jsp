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
			<div data-options="region:'north',split:true" id="north" title="快速检索"
				style="width: 100%; height:120px; padding:10px;">
				<form >
					<table width="100%" id="s_table" style="display:none;">
						<tr>
							<td>简称:</td><td><input type="text" id="s_name"/></td>
							<td>全称:</td><td><input type="text" id="s_full_name"/></td>
							<td>上级部门:</td>
							<td>
								<input onfocus="s_department_f()" type="text" id="s_department"/>
								<input type="hidden" id="s_department_id"/>
							</td>
							<td>联系方式:</td><td><input type="text" id="s_contact"/></td>
						</tr>
						<tr>
							<td colspan="8" style="text-align:center; padding-top:10px;">
								<input type="button" value="搜索" onclick="return search()"/>
								<input type="reset" value="重置" style="margin-left:40px;"/>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div data-options="region:'south',split:true" id="south"
				style="height: 70px;">
			</div>
			<!-- 部门列表 -->
			<div data-options="region:'center'" id="main">
				<table width="100%" height="auto" id="department_list"
					title="部门列表" style=""
					data-options="rownumbers:true,singleSelect:true,loadMsg:'正在加载,请稍候...',pageSize:15,pagination:true,fitColumns:true">
					<thead>
						<tr>
							<th data-options="field:'id',align:'center',hidden:true">
								主键
							</th>
							<th data-options="field:'name',align:'center',sortable:true" width="20%">
								简称
							</th>
							<th data-options="field:'full_name',align:'center',sortable:true" width="20%">
								全称
							</th>
							<th data-options="field:'parent.name',align:'center',sortable:true" width="20%">
								上级部门
							</th>
							<th data-options="field:'contact',align:'center',sortable:true" width="20%">
								联系电话
							</th>
							<th data-options="field:'address',align:'center',sortable:true" width="20%">
								地址
							</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		<!-- 添加部门 -->
		<div id="department_add" class="easyui-window" title="添加部门"
			data-options="modal:true,closed:true,iconCls:'icon-save'"
			style="width: 500px; height: 500px; padding: 10px;">
			<div class="easyui-panel" title="基本信息" style="width:450px">
	        <div style="padding:10px 50px 20px 50px">
		        <form id="a_form" method="post">
		            <table cellpadding="5">
		                <tr>
		                    <td>简称:</td>
		                    <td><input class="easyui-validatebox" type="text" id="a_name" name="name" data-options="required:true"></input></td>
		                </tr>
		                <tr>
		                    <td>全称:</td>
		                    <td><input class="easyui-validatebox" type="text" id="a_full_name" name="full_name" data-options=""></input></td>
		                </tr>
		                
		                <tr>
		                    <td>上级部门:</td>
		                    <td>
		                    	<input class="easyui-validatebox" type="text" id="a_parent_name" name="parent.name" readonly="readonly" data-options="" onfocus="operateDepartmentTreePanel('a_treepanel','open','a_tree')"></input>
		                    	<input type="hidden" id="a_parent_id" name="parent.id"/>
		                    	<div class="easyui-panel" id="a_treepanel" style="padding: 5px;" data-options="closed:true,title:'选择部门',closable:true">
									<ul class="easyui-tree" id="a_tree" data-options="animate:true"></ul>
								</div>
		                    </td>
		                </tr>
		                <tr>
		                    <td>联系方式:</td>
		                    <td><input type="text" id="a_contact" name="contact"></input></td>
		                </tr>
		                <tr>
		                    <td>地址:</td>
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
		
		<!-- 修改部门 -->
		<div id="department_update" class="easyui-window" title="修改部门"
			data-options="modal:true,closed:true,iconCls:'icon-save'"
			style="width: 500px; height: 500px; padding: 10px;">
			<div class="easyui-panel" title="基本信息" style="width:450px">
	        <div style="padding:10px 50px 20px 50px">
		        <form id="u_form" method="post">
		            <table cellpadding="5">
		                <tr>
		                    <td>简称:</td>
		                    <td><input class="easyui-validatebox" type="text" id="u_name" name="name" data-options="required:true"></input></td>
		                </tr>
		                <tr>
		                    <td>全称:</td>
		                    <td><input class="easyui-validatebox" type="text" id="u_full_name" name="full_name" data-options=""></input></td>
		                </tr>
		                
		                <tr>
		                    <td>上级部门:</td>
		                    <td>
		                    	<input class="easyui-validatebox" type="text" id="u_parent_name" name="parent.name" readonly="readonly" data-options="" onfocus="operateDepartmentTreePanel('u_treepanel','open','u_tree')"></input>
		                    	<input type="hidden" id="u_id" name="id"/>
		                    	<input type="hidden" id="u_parent_id" name="parent.id"/>
		                    	<div class="easyui-panel" id="u_treepanel" style="padding: 5px;" data-options="closed:true,title:'选择部门',closable:true">
									<ul class="easyui-tree" id="u_tree" data-options="animate:true"></ul>
								</div>
		                    </td>
		                </tr>
		                <tr>
		                    <td>联系方式:</td>
		                    <td><input type="text" id="u_contact" name="contact"></input></td>
		                </tr>
		                <tr>
		                    <td>地址:</td>
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
		<div id="c_department_tree" class="easyui-window" title="选择部门"
			data-options="modal:true,closed:true,iconCls:'icon-save'"
			style="width: 500px; height: auto; min-height:200px; padding: 10px;">
				<div class="easyui-panel" style="padding: 5px; border: 0;">
					<ul class="easyui-tree" id="tt" data-options="animate:true"></ul>
				</div>
		</div>
		<!--通用html部分结束-->
		<script type="text/javascript">
		//初始化部门列表
		$(function() {
			//初始化搜索框的部门树
			$('#s_table').css('display','inline');
			var data = '${requestScope.departs}';
			data = eval("(" + data + ")")
			$('#tt').tree( {
				data : data,
				onSelect : function(node) {
					$('#s_department').val(node.text);
					$('#s_department_id').val(node.id);
					$('#c_department_tree').window('close');
				}
			});
			//初始化部门列表
			$('#department_list').datagrid({
                fitColumns: true,
				loadMsg : '正在加载,请稍候...',
				pageList: [15,20,25,30],
				pageNumber: 1,
				pageSize: 15,
				url:'department.do?method=listPageByAjax',
				loadFilter: function(data){
					if (data.resultMark == 1) {
						var newdata = {};
						newdata.total = data.total;
						var rows = [];
						for (var i = 0; i < data.rows.length; i ++)
						{
							var row = {
									"id" : data.rows[i].id,
									"name" : data.rows[i].name,
									"full_name" : data.rows[i].full_name,
									"parent.name" : data.rows[i].parent == null ? '──────' : data.rows[i].parent.name,
									"contact" : data.rows[i].contact,
									"address" : data.rows[i].address
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

		//搜索框中的部门焦点触发
		function s_department_f() {
			$('#c_department_tree').window('open');
		}


		//搜索
		function search() {
			var searchObj = {};
			if ($('#s_name').val() != ''){
				searchObj.name = $('#s_name').val();
			}
			if ($('#s_full_name').val() != ''){
				searchObj.full_name = $('#s_full_name').val();
			}
			if ($('#s_contact').val() != ''){
				searchObj.contact = $('#s_contact').val();
			}
			if ($('#s_department').val() != '')
			{
				searchObj['parent.id'] = $('#s_department_id').val();
			}
			$('#department_list').datagrid('load', searchObj);
			return false;
		}
		
		$(function() {
			initDataAndEvent();
		})
		
		function initDataAndEvent() {
			
			var pager = $('#department_list').datagrid('getPager'); // get the pager of datagrid
			pager.pagination( {
		            beforePageText: '第',//页数文本框前显示的汉字           
		            afterPageText: '页    共 {pages} 页',           
		            displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
					buttons : [ {
						iconCls : 'icon-search',
						handler : function() {
							if ($('#body').layout('panel', 'north').panel('options').collapsed)
							{
								$('#body').layout('expand', 'north');
							}
							else
							{
								$('#body').layout('collapse', 'north');
							}
						}
					}, {
						iconCls : 'icon-add',
						handler : function() {
							$('#department_add').window('open');
						}
					}, {
						iconCls : 'icon-edit',
						handler : function() {
							var selects = $('#department_list').datagrid('getSelections');
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
							if ($('#department_list').datagrid('getSelections').length > 0)
							{
								if (confirm("是否将\"" + $('#department_list').datagrid('getSelected').name + "\"删除?"))
								{
									$.ajax( {
										type : "post",
										url : "department.do?method=delete",
										data: "id=" + $('#department_list').datagrid('getSelected').id,
										success : function(data) {
											if (data.resultMark == 1)
											{
												$('#department_list').datagrid('reload');
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
						$('#u_parent_name').attr({'value' : node.text});
						$('#u_parent_id').attr({'value' : node.id});
						$('#u_treepanel').panel('close');
					}
				});
				$('#a_tree').tree({
					onSelect:function(node){
					$('#a_parent_name').attr({'value' : node.text});
					$('#a_parent_id').attr({'value' : node.id});
					$('#a_treepanel').panel('close');
				}
			});
		}

		//获取要修改的部门信息
		function prepareUpdate() {
			$.ajax( {
				type : "get",
				url : "department.do?method=findByAjax",
				data: "id=" + $('#department_list').datagrid('getSelected').id,
				success : function(data) {
					if (data != null)
					{
						$('#u_id').val(data.id);
						if (data.parent)
						{
							$('#u_parent_id').val(data.parent.id);
							$('#u_parent_name').val(data.parent.name);
						}
						$('#u_contact').val(data.contact);
						$('#u_name').val(data.name);
						$('#u_address').val(data.address);
						$('#u_remarks').text(data.remarks == null ? '' : data.remarks);
						$('#u_full_name').val(data.full_name);
						$('#department_update').window('open');
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
			var department = {};
			department.name = $('#a_name').val();
			department.contact = $('#a_contact').val();
			department.address = $('#a_address').val();
			department.remarks = $('#a_remarks').val();
			if ($('#a_parent_name').val() != '')
			{
				department.parent = {};
				department.parent.id = $('#a_parent_id').val();
			}
			department.full_name = $('#a_full_name').val();
			$.ajax( {
				type : "post",
				url : "department.do?method=add",
				contentType : "application/json;charset=UTF-8",
				data:JSON.stringify(department),
				dataType: "json", 
				success : function(data) {
					if (data.resultMark == 1)
					{
						$('#department_list').datagrid('load');
						$('#department_add').window('close');
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
			var department = {};
			department.id = $('#u_id').val();
			department.name = $('#u_name').val();
			department.contact = $('#u_contact').val();
			department.address = $('#u_address').val();
			department.remarks = $('#u_remarks').val();
			if ($('#u_parent_name') != '')
			{
				department.parent = {};
				department.parent.id = $('#u_parent_id').val();
			}
			department.full_name = $('#u_full_name').val();
			$.ajax( {
				type : "post",
				url : "department.do?method=update",
				contentType : "application/json;charset=UTF-8",
				data:JSON.stringify(department),
				dataType: "json", 
				success : function(data) {
					if (data.resultMark == 1)
					{
						$('#department_list').datagrid('reload');
						$('#department_update').window('close');
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