package com.sxt.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxt.po.AjaxRetObjTemplate;
import com.sxt.po.ListInfoTemplate;
import com.sxt.service.BoxInfoService;
import com.sxt.service.OpticalcableService;

@Controller
public class OpticalcableController  {

	private BoxInfoService boxInfoService;
	
	private OpticalcableService opticalcableService; 
	
	@InitBinder  
    public void initBinder(WebDataBinder binder) {  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        dateFormat.setLenient(false);  
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
    } 
	
	
	//获取某光交箱下所有光缆，AJAX方式
	@RequestMapping(value = "/oplist", method = RequestMethod.GET) 
	public @ResponseBody ListInfoTemplate findAllByBoxId(int id){
		ListInfoTemplate lit = new ListInfoTemplate();
		lit.setRows(this.opticalcableService.findAllByBoxId(id));
		lit.setResultMark(1);
		return lit;
	}
	
	
	
	//获取某光交箱下所有光缆以及纤芯，AJAX方式
	@RequestMapping(value = "/opandcorelist", method = RequestMethod.GET) 
	public @ResponseBody ListInfoTemplate findAllTreeByBoxId(int id){
		ListInfoTemplate lit = new ListInfoTemplate();
		lit.setRows(this.opticalcableService.findAllTreeByBoxId(id));
		lit.setResultMark(1);
		return lit;
	}
	
	
	//获取所有纤芯，AJAX方式
	@RequestMapping(value = "/corelist", method = RequestMethod.GET) 
	public @ResponseBody ListInfoTemplate findAllCoreByAjax(int id){
		ListInfoTemplate lit = new ListInfoTemplate();
		lit.setRows(this.opticalcableService.findAllCore(id));
		lit.setResultMark(1);
		return lit;
	}
	
	//获取所有可用纤芯，AJAX方式
	@RequestMapping(params="method=findAllCoreByBoxId")
	public @ResponseBody ListInfoTemplate findAllCoreByBoxId(int boxid, int optiid){
		ListInfoTemplate lit = new ListInfoTemplate();
		lit.setRows(this.opticalcableService.findAllCoreByBoxId(boxid, optiid));
		lit.setResultMark(1);
		return lit;
	}
	
	//获取光缆，AJAX方式
	@RequestMapping(params="method=findOpticalcableByAjax")
	public @ResponseBody AjaxRetObjTemplate findOpticalcableByAjax(int id){
		AjaxRetObjTemplate arot = new AjaxRetObjTemplate();
		arot.setObject(this.opticalcableService.find(id));
		arot.setResultMark(1);
		return arot;
	}
	
	
	
	public BoxInfoService getBoxInfoService() {
		return boxInfoService;
	}

	@Resource
	public void setBoxInfoService(BoxInfoService boxInfoService) {
		this.boxInfoService = boxInfoService;
	}

	public OpticalcableService getOpticalcableService() {
		return opticalcableService;
	}

	@Resource
	public void setOpticalcableService(OpticalcableService opticalcableService) {
		this.opticalcableService = opticalcableService;
	}
	
	
	

	
}
