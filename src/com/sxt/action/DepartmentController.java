package com.sxt.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sxt.po.DepSimple;
import com.sxt.po.Department;
import com.sxt.service.DepartmentService;

@Controller
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    //获取当前系统日期
    public static String getDate() {
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return formatter.format(date);
    }

    //访问列表
    @RequestMapping(value = "/deplist", method = RequestMethod.GET)
    public @ResponseBody
    Map list() {
        System.out.println("DepartmentController.list( )");

        List<DepSimple> delst = new ArrayList<DepSimple>();


        Map<String, Object> map = new HashMap<String, Object>();
        List<Department> departs = departmentService.findAlldep();
        String errorcode = "100";
        String errorinfo = "";

        for (Department t : departs) {
            DepSimple dp = new DepSimple();
            dp.setId(t.getId());
            dp.setName(t.getName());
            if (t.getParent() != null)
                dp.setParent_id(t.getParent().getId());
            dp.setFullname(t.getFull_name());
            delst.add(dp);

        }
        map.put("errorcode", errorcode);    //errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", delst);
        return map;
    }
	
	/*
	//获取所有部门
	@RequestMapping(params="method=findAllByAjax")
	public void findAllByAjax(HttpServletResponse response) throws IOException{
		System.out.println("DepartmentController.getAllByAjax()");
		List<Department> departs = departmentService.findAll();
		String tree = departs2json(departs, 1);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(tree);
	}
	
	
	//获取分页
	@RequestMapping(params="method=listPageByAjax")
	public @ResponseBody ListInfoTemplate listPageByAjax(DepartmentSearch ds){
		System.out.println("DepartmentController.listPageByAjax()");
		if (ds != null && ds.getRows() <= 0) {
			ds.setRows(10);
		}
		ListInfoTemplate lit = new ListInfoTemplate();
		lit.setPage(ds.getPage());
		int counts = departmentService.findCounts(ds);
		lit.setTotal(counts % ds.getRows() > 0 ? (counts / ds.getRows() + 1) : (counts / ds.getRows()));
		lit.setRecords(counts);
		lit.setRows(departmentService.findByPager(ds));
		return lit;
	}
	
	//获取部门详情
	@RequestMapping(params="method=findByAjax")
	public @ResponseBody Department findByAjax(int id){
		return this.departmentService.find(id);
	}
	
	//添加部门
	@RequestMapping(params="method=add",consumes="application/json")
	public void add(@RequestBody Department department, HttpServletResponse response) throws IOException{
		departmentService.add(department);
		String resultMark = "{\"resultMark\" : 1}";
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(resultMark);
	}
	
	//修改部门
	@RequestMapping(params="method=update",consumes="application/json")
	public void update(@RequestBody Department department, HttpServletResponse response) throws IOException{
		departmentService.update(department);
		String resultMark = "{\"resultMark\" : 1}";
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(resultMark);
	}
	
	//删除部门
	@RequestMapping(params="method=delete")
	public void delete(@RequestBody int[] ids, HttpServletResponse response) throws IOException{
		departmentService.delete(ids);
		String resultMark = "{\"resultMark\" : 1}";
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(resultMark);
	}
	
	*/


    public static String departs2json(List<Department> departs, int zindex) {
        StringBuffer sb = new StringBuffer();
        for (Department d : departs) {
            sb.append("{");
            sb.append("\"id\":" + d.getId() + ",");
            sb.append("\"text\":\"" + d.getName() + "\"");
            if (d.getChildren().size() > 0) {
                sb.append(",\"children\":" + departs2json(d.getChildren(), zindex++));
            }
            sb.append("},");
        }
        return "[" + sb.substring(0, sb.length() - 1) + "]";
    }


    @RequestMapping(params = "method=reg3")
    public String reg3(@RequestParam("uname") String name, HttpServletRequest req, ModelMap map) {
        System.out.println("UserController.reg()");
        System.out.println(name);
        req.getSession().setAttribute("c", "ccc");
        map.put("a", "aaa");

        return "index";
    }

    @RequestMapping(params = "method=reg4")
    public String reg4(@ModelAttribute("a") String a, HttpServletRequest req, ModelMap map) {
        System.out.println("UserController.reg4()");
        System.out.println(a);
        return "redirect:http://www.baidu.com";
    }

    @RequestMapping(params = "method=reg5")
    public ModelAndView reg5(String uname) {
        System.out.println("UserController.reg5()");

        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");


        return mav;
    }

    public DepartmentService getDepartmentService() {
        return departmentService;
    }

    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


}
