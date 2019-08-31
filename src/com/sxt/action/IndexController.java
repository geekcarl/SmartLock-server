package com.sxt.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.sxt.po.BoxInfo;
import com.sxt.po.Department;
import com.sxt.po.Dictionarys;
import com.sxt.po.User;
import com.sxt.service.BoxInfoService;
import com.sxt.service.DepartmentService;
import com.sxt.service.DictionaryService;
import com.sxt.service.UserService;

@Controller
public class IndexController  {

	private DepartmentService departmentService;
	private BoxInfoService boxInfoService;
	private DictionaryService dictionaryService;
	
	@RequestMapping("/index")
	public String index(ModelMap map){
		System.out.println("IndexController.index()");
		return "index";
	}
	
	@RequestMapping("/")
	public String Login(ModelMap map){
		map.put("login_first", "first_login");
		return "login";
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	@Resource
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public BoxInfoService getBoxInfoService() {
		return boxInfoService;
	}

	@Resource
	public void setBoxInfoService(BoxInfoService boxInfoService) {
		this.boxInfoService = boxInfoService;
	}

	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}

	@Resource
	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	

	
}
