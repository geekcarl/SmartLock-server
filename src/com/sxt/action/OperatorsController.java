package com.sxt.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.sxt.po.Department;
import com.sxt.po.ListInfoTemplate;
import com.sxt.po.Operators;
import com.sxt.po.User;
import com.sxt.service.DepartmentService;
import com.sxt.service.OperatorsService;
import com.sxt.service.UserService;

@Controller
@RequestMapping("/operators.do")
public class OperatorsController  {

	private OperatorsService operatorsService;
	private DepartmentService departmentService;
	
	//请求员工列表页面
	@RequestMapping(params="method=list")
	public String list(HttpServletRequest request, ModelMap map){
		return "operatorsList";
	}
	
	//获取所有员工信息，AJAX请求
	@RequestMapping(params="method=listByAjax")
	public @ResponseBody ListInfoTemplate listByAjax(){
		ListInfoTemplate lit = new ListInfoTemplate();
		lit.setTotal(operatorsService.findCounts());
		lit.setRows(operatorsService.findAll());
		return lit;
	}
	
	//分页获取员工信息，AJAX请求
	@RequestMapping(params="method=listPageByAjax")
	public @ResponseBody ListInfoTemplate listPageByAjax(int page, int rows){
		System.out.println("OperatorsController.listPageByAjax()");
		if (page <= 0)
		{
			page = 1;
		}
		if (rows <= 0)
		{
			rows = 10;
		}
		ListInfoTemplate lit = new ListInfoTemplate();
		lit.setPage(page);
		int counts = operatorsService.findCounts();
		lit.setTotal(counts % rows > 0 ? (counts / rows + 1) : (counts / rows));
		lit.setRecords(counts);
		lit.setRows(operatorsService.findByPager(page, rows));
		return lit;
	}
	
	//获取单个员工信息，AJAX请求
	@RequestMapping(params="method=findByAjax")
	public @ResponseBody Operators findByAjax(int id){
		return this.operatorsService.find(id);
	}
	
	//添加一个员工信息，AJAX请求
	@RequestMapping(params="method=add",consumes="application/json")
	public void add(@RequestBody Operators operators, HttpServletResponse response) throws IOException{
		operatorsService.add(operators);
		String resultMark = "{\"resultMark\" : 1}";
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(resultMark);
	}
	
	//修改一个员工信息，AJAX请求
	@RequestMapping(params="method=update",consumes="application/json")
	public void update(@RequestBody Operators operators, HttpServletResponse response) throws IOException{
		operatorsService.update(operators);
		String resultMark = "{\"resultMark\" : 1}";
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(resultMark);
	}
	
	//删除一个员工信息，AJAX请求
	@RequestMapping(params="method=delete")
	public void delete(Operators operators, HttpServletResponse response) throws IOException{
		operatorsService.delete(operators);
		String resultMark = "{\"resultMark\" : 1}";
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(resultMark);
	}
	

	public OperatorsService getOperatorsService() {
		return operatorsService;
	}

	@Resource
	public void setOperatorsService(OperatorsService operatorsService) {
		this.operatorsService = operatorsService;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	@Resource
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	
}
