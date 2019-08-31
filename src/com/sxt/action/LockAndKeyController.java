package com.sxt.action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.sxt.po.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxt.service.BoxInfoService;
import com.sxt.service.DepartmentService;
import com.sxt.service.LockAndKeyService;
import com.sxt.service.UserService;

@Controller
public class LockAndKeyController {
    private LockAndKeyService lockandkeyService;
    private DepartmentService departmentService;
    private UserService userservice;
    private BoxInfoService boxinfoservice;

    @Resource
    public void setBoxinfoservice(BoxInfoService boxinfoservice) {
        this.boxinfoservice = boxinfoservice;
    }

    @Resource
    public void setUserservice(UserService userservice) {
        this.userservice = userservice;
    }

    /*add by ly*/
    //获取当前系统日期
    public static String getDate() {
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return formatter.format(date);

    }

    public static Date getDateYMD() throws ParseException {
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return formatter.parse(date.toString());

    }

    public static String formatdate(Date dt) {

        SimpleDateFormat formatter = new SimpleDateFormat("MMdd");
        return formatter.format(dt);
    }

    //获取所有锁类型
    @RequestMapping(value = "/locktype", method = RequestMethod.GET)
    public @ResponseBody
    Map findAllLockType() throws IOException {

        Map<String, Object> map = new HashMap<String, Object>();

        List<LockTypeInfo> lst = lockandkeyService.findAllLockType();

        map.put("errorcode", "100");    //errorcode
        map.put("errorinfo", "");
        map.put("date", getDate());
        map.put("results", lst);
        return map;
    }


    //获取某个钥匙的所有权限
    @RequestMapping(value = "/lockgrantlist", method = RequestMethod.GET)
    public @ResponseBody
    Map findlockgrantlist(String keycode) throws IOException, ParseException {

        int keyid = 0;
        keyid = lockandkeyService.findIdbycode(keycode);

        System.out.println("keyid======>" + keyid);
        String errorcode = "100";
        String errorinfo = "";


        if (keyid == 0) {

            errorcode = "104";
            errorinfo = "此钥匙码系统中不存在";

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("errorcode", errorcode);    //errorcode
            map.put("errorinfo", errorinfo);
            map.put("date", getDate());
            return map;
        }


        List<GrantLog> glst = lockandkeyService.findGrantLogBykeyId(keycode);
        List<GrantDetailNew> lst = new ArrayList<GrantDetailNew>();

        if (glst == null) {
            errorcode = "104";
            errorinfo = "此钥匙权限为空";

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("errorcode", errorcode);    //errorcode
            map.put("errorinfo", errorinfo);
            map.put("date", getDate());
            return map;


        }

        Map<String, Object> map = new HashMap<String, Object>();

        try {
            if (glst != null) {

                for (GrantLog t : glst) {

                    List<GrantDetail> sst = new ArrayList<GrantDetail>();
                    sst = t.getGds();

                    if (sst != null) {
                        for (GrantDetail a : sst) {

                            GrantDetailNew g = new GrantDetailNew();
                            if (a.getBegin_time() != null)
                                g.setBegin_time(formatdate(a.getBegin_time()));
                            if (a.getEnd_time() != null)
                                g.setEnd_time(formatdate(a.getEnd_time()));

                            List<LockModify> lost = lockandkeyService.findLockInfo(a.getBoxInfo().getId());
                            if (lost != null)
                                g.setLocklist(lost);

                            //判断下权限的时间
                            if (a.getBegin_time() != null && a.getEnd_time() != null) {
                                boolean flag = a.getEnd_time().before(getDateYMD());
                                if (!flag)
                                    lst.add(g);
                            } else
                                lst.add(g);

                        }

                    }
                }

            }
        } catch (Exception ex) {

            errorcode = "104";

            errorinfo = "获取权限发生错误";
        }

        map.put("errorcode", errorcode);    //errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", lst);
        return map;
    }


    //查询用户开锁权限
    @RequestMapping(value = "/keygrant", method = RequestMethod.GET)
    public @ResponseBody
    Map queryUserUnlockingAuthority(String user_name, String lock_code) {
        System.out.println("queryUserUnlockingAuthority......");
        String errorinfo = "";
        String errorcode = "100";
        GrantBox gbinfo = new GrantBox();
        try {
            //用户名和锁码是否为空
            if (lock_code == null || "".equals(lock_code)) {
                errorcode = "104";
                errorinfo = "请务必填写锁码";
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorinfo", errorinfo);
                map.put("errorcode", errorcode);
                map.put("date", getDate());
                map.put("results", null);
                return map;
            }
            if (user_name == null || "".equals(user_name)) {
                errorcode = "104";
                errorinfo = "请务必填写用户名";
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorinfo", errorinfo);
                map.put("errorcode", errorcode);
                map.put("date", getDate());
                map.put("results", null);
                return map;
            }
            //当前用户是否被授权过
            int userid = userservice.findByName(user_name);
            //根据用户ID获取授权日志记录
            List<GrantLog> gls = lockandkeyService.findGrantLogByOperatorId(userid);
            if (gls.size() == 0) {
                errorcode = "104";
                errorinfo = "该用户没有被授权";
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorinfo", errorinfo);
                map.put("errorcode", errorcode);
                map.put("date", getDate());
                map.put("results", null);
                return map;
            }
            //获取用户授权ID
            int logid = gls.get(0).getId();
            //根据锁码获取光交箱信息
            List<LockInfo> bis = lockandkeyService.findBoxInfoByLockCode(lock_code);
            if (bis.size() == 0) {
                errorcode = "104";
                errorinfo = "该锁芯不存在";
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorinfo", errorinfo);
                map.put("errorcode", errorcode);
                map.put("date", getDate());
                map.put("results", null);
                return map;
            }
            //获取光交箱ID
            int boxid = bis.get(0).getBoxInfo().getId();
            //获取光交箱编号
            String boxno = boxinfoservice.find(boxid).getBox_no();
            gbinfo.setBox_id(boxid);
            gbinfo.setBox_no(boxno);
            //查看授权详情
            List<GrantDetail> gds = lockandkeyService.findGrantDetailBylogidAndboxid(logid, boxid);
            if (gds.size() == 0) {
                errorcode = "104";
                errorinfo = "找不到授权记录";
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorinfo", errorinfo);
                map.put("errorcode", errorcode);
                map.put("date", getDate());
                map.put("results", null);
                return map;
            }
            //判断是否过期
            GrantDetail gd = gds.get(0);
            gbinfo.setFlag(true);
    		/*if(gd.getEnd_time() == null && gd.getEnd_time() == null){
    			gbinfo.setFlag(true);
    		}*/
            if (gd.getEnd_time() != null && gd.getEnd_time().before(getDateYMD())) {
                gbinfo.setFlag(false);
            }
        } catch (Exception e) {
            errorcode = "104";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorinfo", errorinfo);
        map.put("errorcode", errorcode);
        map.put("date", getDate());
        map.put("results", gbinfo);
        return map;
    }

    //上传开锁事件记录
    @RequestMapping(value = "/lockopenlog", method = RequestMethod.POST)
    public @ResponseBody
    Map postLockOpenLog(@RequestBody List<OpenRecord> ol) {
        System.out.println("postLockOpenLog......");

        String errorcode = "100";
        String errorinfo = "";

        KeyOpenLog kl;
        try {

            //存入数据库
            if (ol.size() > 0) {

                for (OpenRecord lst : ol) {

                    //获取上传 数据
                    String key_code = lst.getKeycode();
                    String lock_code = lst.getLockcode();
                    String open_time = lst.getOpentime();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date opentimedate = sdf.parse(open_time);


                    String key_rfid = lockandkeyService.findrfidbyKeycode(key_code);
                    String box_no = boxinfoservice.findBoxnobyLockCode(lock_code);
                    String box_address = boxinfoservice.findBoxaddressbyLockCode(lock_code);

                    //向KeyOpenLog表插入一条数据
                    kl = new KeyOpenLog();
                    kl.setBox_address(box_address);
                    kl.setBox_no(box_no);
                    kl.setKey_code(key_code);
                    kl.setKey_rfid(key_rfid);
                    kl.setLockcode(lock_code);
                    kl.setOpen_time(opentimedate);
                    lockandkeyService.addkeyopenLog(kl);
                }
            }

        } catch (Exception e) {
            errorcode = "104";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorinfo", errorinfo);
        map.put("errorcode", errorcode);
        map.put("date", getDate());
        map.put("results", null);
        return map;
    }

    //上传开锁事件记录
    @RequestMapping(value = "/openlog", method = RequestMethod.POST)
    public @ResponseBody
    Map postOpenLog(@RequestBody OpenLog ol) {
        System.out.println("postOPenLog......");
        String errorcode = "100";
        String errorinfo = "";
        CellGrantLog cg;
        try {
            if (ol.getUser_name() != null && !"".equals(ol.getUser_name())
                    && ol.getLock_code() != null && !"".equals(ol.getLock_code())
                    && ol.getBox_id() != 0) {
                //获取上传 数据
                String username = ol.getUser_name();
                String lockcode = ol.getLock_code();
                int boxid = ol.getBox_id();
                //向cellgrantlog表插入一条数据
                cg = new CellGrantLog();
                cg.setUser_name(username);
                cg.setLock_code(lockcode);
                cg.setBox_id(boxid);
                cg.setCreatetime(new Date());
                cg.setServer_flag(0);
                lockandkeyService.addCellGrantLog(cg);
            } else {
                errorcode = "104";
                errorinfo = "数据格式有误";
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorinfo", errorinfo);
                map.put("errorcode", errorcode);
                map.put("date", getDate());
                map.put("results", null);
                return map;
            }
        } catch (Exception e) {
            errorcode = "104";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorinfo", errorinfo);
        map.put("errorcode", errorcode);
        map.put("date", getDate());
        map.put("results", null);
        return map;
    }
    
   
/*	//获取某光交箱下的所有锁信息
	 @RequestMapping(value = "/locklist", method = RequestMethod.GET) 
     public @ResponseBody Map getLockInfo(Integer boxid) throws IOException{
		 
		  System.out.println("boxid------>"+boxid);
		  String errorcode ="100";
		  String errorinfo="";
		  List<LockModify> bs = new ArrayList();;
		  
	
		  try{
			  
			if(boxid == null){
				 
      		errorcode ="104";
      		errorinfo="请务必填写光交箱id";
      		
      		      Map<String, Object> map = new HashMap<String, Object>(); 
				  map.put("errorcode",errorcode);    //errorcode  
	              map.put("errorinfo", errorinfo);
	              map.put("date",  getDate());
	              map.put("results", "");
	              return map;
		 }
		
		
			   bs = lockandkeyService.findLockInfo(boxid);
		 
		  }catch(Exception ex)
		  {
			 
			System.out.println(ex);
		   }
	
			 Map<String, Object> map = new HashMap<String, Object>(); 
			 map.put("errorcode", errorcode);    //errorcode  
             map.put("errorinfo", errorinfo);
             map.put("date",  getDate());
             map.put("results",bs);
             return map;
	  }*/


    //新增某光交箱下的所有锁信息  -------------------delete
	/* @RequestMapping(value = "/lockinfo", method = RequestMethod.POST) 
     public @ResponseBody Map newLockInfo(@RequestBody LockModify lockinfo) throws IOException{
		 
		  System.out.println("/lockinfo-->boxid------>"+lockinfo.getBox_id());
		  String errorcode ="100";
		  String errorinfo="";

		  try{
			  
			if(lockinfo.getBox_id() == 0){
				 
      		     errorcode ="104";
      		     errorinfo="请务必填写光交箱id";
      		
      		      Map<String, Object> map = new HashMap<String, Object>(); 
				  map.put("errorcode",errorcode);    //errorcode  
	              map.put("errorinfo", errorinfo);
	              map.put("date",  getDate());
	              map.put("results", "");
	              return map;
		     }
			
			
			if(lockinfo.getLock_code()==null || lockinfo.getLock_code().equals("")){
				 
     		     errorcode ="104";
     		     errorinfo="请务必填写锁码";
     		
     		      Map<String, Object> map = new HashMap<String, Object>(); 
				  map.put("errorcode",errorcode);    //errorcode  
	              map.put("errorinfo", errorinfo);
	              map.put("date",  getDate());
	              map.put("results", "");
	              return map;
		     }
			
			
			if(lockinfo.getType_id()==0){
				 
    		     errorcode ="104";
    		     errorinfo="请务必填写锁类型";
    		
    		      Map<String, Object> map = new HashMap<String, Object>(); 
				  map.put("errorcode",errorcode);    //errorcode  
	              map.put("errorinfo", errorinfo);
	              map.put("date",  getDate());
	              map.put("results", "");
	              return map;
		     }
		
		
			    lockandkeyService.addLock(lockinfo);
		 
		  }catch(Exception ex)
		  {
			 
			System.out.println(ex);
		   }
	
			 Map<String, Object> map = new HashMap<String, Object>(); 
			 map.put("errorcode", errorcode);    //errorcode  
             map.put("errorinfo", errorinfo);
             map.put("date",  getDate());
             map.put("results","");
             return map;
	  }*/


    //获取手机开门的权限结果
    @RequestMapping(value = "/phoneauthresult", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> phoneauthresult(HttpServletRequest request, String lockid) {

        String username = request.getHeader("username");

        int userid = 0;
        userid = userservice.findByName(username);

        String errorcode = "100";
        String errorinfo = "";
        ResultFlag reflag = new ResultFlag();

        if (lockid == null || lockid.equals("")) {
            errorcode = "104";
            errorinfo = "请上传锁id";
            reflag.setFlag(false);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("errorcode", errorcode);    //errorcode
            map.put("errorinfo", errorinfo);
            map.put("date", getDate());
            map.put("results", reflag);
            return map;
        }

        Integer boxid = 0;

        boxid = lockandkeyService.findBoxidbylock(lockid);

        if (boxid == 0 || boxid == null) {
            errorcode = "100";
            errorinfo = "系统中不存在此锁号";
            reflag.setFlag(false);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("errorcode", errorcode);    //errorcode
            map.put("errorinfo", errorinfo);
            map.put("date", getDate());
            map.put("results", reflag);
            return map;
        }

        String boxno = lockandkeyService.findBoxnobylock(lockid);


        List<GrantLogForPhone> glst = lockandkeyService.findGrantLogByPhone(userid, boxid);
        List<GrantDetailforPhoneNew> lst = new ArrayList<GrantDetailforPhoneNew>();


        if (glst == null) {
            errorcode = "100";
            errorinfo = "此用户权限为空";
            reflag.setFlag(false);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("errorcode", errorcode);    //errorcode
            map.put("errorinfo", errorinfo);
            map.put("date", getDate());
            map.put("results", reflag);
            return map;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        try {
            if (glst != null) {

                for (GrantLogForPhone t : glst) {

                    List<GrantDetailforPhone> sst = new ArrayList<GrantDetailforPhone>();
                    sst = t.getGds();

                    if (sst != null) {
                        for (GrantDetailforPhone a : sst) {

                            GrantDetailforPhoneNew g = new GrantDetailforPhoneNew();

                            //判断下权限的时间
                            if (a.getBegin_time() != null && a.getEnd_time() != null) {
                                boolean flag = a.getEnd_time().before(new Date());
                                if (!flag)
                                    lst.add(g);
                            } else
                                lst.add(g);

                        }

                    }
                }


            }
        } catch (Exception ex) {

            errorcode = "104";

            errorinfo = "获取权限结果发生错误";
        }

        map.put("errorcode", errorcode);    //errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        if (lst.size() == 0) {
            reflag.setFlag(false);
            reflag.setBoxno(boxno);
            map.put("results", reflag);
        } else {
            reflag.setFlag(true);
            reflag.setBoxno(boxno);
            map.put("results", reflag);
        }

        return map;
    }


    //获取手机开门的权限结果
    @RequestMapping(value = "/phonopenlog", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> phonopenlog(HttpServletRequest request, String lockid) {

        String username = request.getHeader("username");

        int userid = 0;
        userid = userservice.findByName(username);

        String errorcode = "100";
        String errorinfo = "";

        if (lockid == null || lockid.equals("")) {
            errorcode = "104";
            errorinfo = "请上传锁id";

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("errorcode", errorcode);    //errorcode
            map.put("errorinfo", errorinfo);
            map.put("date", getDate());
            map.put("results", null);
            return map;
        }

        Integer boxid = 0;

        boxid = lockandkeyService.findBoxidbylock(lockid);


        PhoneOpenLog p = new PhoneOpenLog();
        p.setApplytime(new Date());
        p.setBoxid(boxid);
        p.setLockid(lockid);
        p.setUserid(userid);
        p.setUsername(username);
        p.setType(2);

        lockandkeyService.savephonelog(p);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorcode", errorcode);    //errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", null);
        return map;
    }

    //获取手机开门的权限结果
    @RequestMapping(value = "/phoneOpenLogRemote", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> phoneOpenLogRemote(HttpServletRequest request, Integer boxid) {

        String username = request.getHeader("username");

        int userid = 0;
        userid = userservice.findByName(username);

        String errorcode = "100";
        String errorinfo = "";

        PhoneOpenLog p = new PhoneOpenLog();
        p.setApplytime(new Date());
        p.setBoxid(boxid);
        p.setUserid(userid);
        p.setUsername(username);
        p.setType(0);

        lockandkeyService.savephonelog(p);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorcode", errorcode);    //errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", null);
        return map;
    }


    public LockAndKeyService getLockandkeyService() {
        return lockandkeyService;
    }

    @Resource
    public void setLockandkeyService(LockAndKeyService lockandkeyService) {
        this.lockandkeyService = lockandkeyService;
    }

    public DepartmentService getDepartmentService() {
        return departmentService;
    }

    @Resource
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

}
