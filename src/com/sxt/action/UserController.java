package com.sxt.action;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.sxt.po.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxt.service.DepartmentService;
import com.sxt.service.UserService;
import com.sxt.utils.Md5;

@Controller
public class UserController {

    private UserService userService;
    private DepartmentService departmentService;
    private boolean authenResult = false;


    //获取当前系统日期
    public static String getDate() {
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return formatter.format(date);
    }


    public boolean authenticate(HttpServletRequest request) {
        String username = request.getHeader("username");
        String pwd = request.getHeader("pwd");

        if (username == null || username.equals("")) {

            return false;
        }

        if (pwd == null || pwd.equals("")) {

            return false;
        }

        String hashed_pwd = md5(pwd);

        if (hashed_pwd.equalsIgnoreCase(getPassword(username))) {
            this.authenResult = true;
            return true;
        }
        return false;
    }


    public static String md5(String input) {
        String ret = null;
        if (null == input) return null;
        try {
            // Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");
            // Update input string in message digest
            digest.update(input.getBytes());
            // Convert message digest value in base 16 (hex)
            //ret = new BigInteger(1, digest.digest()).toString(16);
            byte[] md = digest.digest();
            StringBuffer buf = new StringBuffer();
            for (int i = 0; i < md.length; i++) {
                buf.append(Integer.toString((md[i] & 0xff) + 0x100, 16).substring(1));
            }
            ret = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static void main(String... args) {
        System.out.println(md5("123456").toUpperCase());
    }


    private String getPassword(String name) {
        return userService.getPassword(name);

    }


    //用户登录判断byLY
    @RequestMapping(value = "/checkuser", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> checkuser(HttpServletRequest request) {
        System.out.println("checkuser()");
        String username = request.getHeader("username");
        String pwd = request.getHeader("pwd");
        System.out.println(username + "+++++++++++++++" + pwd);

        Boolean flag = false;
        flag = authenticate(request);

        Map<String, Object> map = new HashMap<String, Object>();

        if (flag) {

            map.put("errorcode", "100");    //errorcode
            map.put("errorinfo", "");
            map.put("date", getDate());
            map.put("results", flag);
        } else {

            map.put("errorcode", "104");    //errorcode
            map.put("errorinfo", "用户名或者密码错误");
            map.put("date", getDate());
            map.put("results", flag);

        }
		
		/*  User u =new User();
		  u.setUser_name(username);
		  u.setPasswd(pwd);
		  Map<String, Object> map = new HashMap<String, Object>(); 
		  
		  User nu = userService.findByLogin(u);
			if (nu != null)
			{
				flag = true;
				  map.put("errorcode", "100");    //errorcode  
		          map.put("errorinfo", "");
		          map.put("date",  getDate());
		          map.put("results", flag);
			}
			else
			{
				flag = false;

				  map.put("errorcode", "104");    //errorcode  
		          map.put("errorinfo", "用户名或者密码错误");
		          map.put("date",  getDate());
		          map.put("results", flag);

			}
*/

        return map;
    }


    public Boolean checkuserlogin(String username, String pwd) {
		/*  String username =request.getHeader("username");
		  String pwd =request.getHeader("pwd");*/

        Boolean flag = false;
        User u = new User();
        u.setUser_name(username);
        u.setPasswd(Md5.toMD5(pwd));

        User nu = userService.findByLogin(u);
        if (nu != null) {
            flag = true;
        } else {
            flag = false;

        }

        return flag;
    }

    //更改用户信息，AJAX
    @RequestMapping(value = "/updatepwd", method = RequestMethod.PUT)
    public @ResponseBody
    Map<String, Object> updatepwd(@RequestBody UserUpload u, HttpServletRequest request) {

        String errorcode = "100";
        String errorinfo = "";
        String username = request.getHeader("username");
        String pwd = request.getHeader("pwd");

        if (!checkuserlogin(username, u.getPwd_old())) {

            errorcode = "104";
            errorinfo = "原始密码输入错误";

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("errorcode", errorcode);    //errorcode
            map.put("errorinfo", errorinfo);
            map.put("date", getDate());
            map.put("results", null);
            return map;

        }

        User t = new User();
        t.setUser_name(username);
        t.setPasswd(Md5.toMD5(u.getPwd_old()));

        User nu = userService.findByLogin(t);

        User ur = new User();
        ur.setId(nu.getId());
        ur.setPasswd(Md5.toMD5(u.getPwd_new()));

        Map<String, Object> map = new HashMap<String, Object>();

        try {
            userService.updatepwd(ur);
            errorcode = "100";
            errorinfo = "";

        } catch (Exception e) {
            errorcode = "104";
            errorinfo = e.toString();
        }

        map.put("errorcode", errorcode);    //errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", null);


        return map;
    }


    //获取版本信息
    @RequestMapping(value = "/checkversion", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> checkversion() {
        System.out.println("checkversion()");

        Boolean flag = false;
        Map<String, Object> map = new HashMap<String, Object>();

        try {

            VersionManage v = userService.getVersion();

            map.put("errorcode", "100");    //errorcode
            map.put("errorinfo", "");
            map.put("date", getDate());
            map.put("results", v);

        } catch (Exception e) {
            map.put("errorcode", "104");    //errorcode
            map.put("errorinfo", "");
            map.put("date", getDate());
            map.put("results", null);
        }

        return map;
    }


    /*//用户登录，AJAX返回
    @RequestMapping(params="method=login")
    public @ResponseBody AjaxRetObjTemplate login(User u, HttpServletRequest request, ModelMap map){
        System.out.println("UserController.login()");
        AjaxRetObjTemplate arot = new AjaxRetObjTemplate();
        try
        {
            User user = userService.findByLogin(u);
            if (user != null)
            {
                arot.setResultMark(1);
                request.getSession().setAttribute("user_id", user.getId());
                request.getSession().setAttribute("user_name", user.getUser_name());
                request.getSession().setAttribute("user_theme", user.getTheme());
                request.getSession().setAttribute("user_searchexpand", user.getSearchexpand());
            }
            else
            {
                arot.setResultMark(0);
                arot.setErrMessage("用户名或者密码错误");
            }
        }
        catch (Exception e)
        {
            arot.setResultMark(0);
            arot.setErrMessage("服务器异常，请重试");
            e.printStackTrace();
        }
        return arot;
    }
    */
	/*//根据用户id获取用户信息，AJAX
	@RequestMapping(params="method=findByAjax")
	public @ResponseBody User findByAjax(int id){
		User u = userService.findById(id);
		return u;
	}
	
	//更改用户信息，AJAX
	@RequestMapping(params="method=update")
	public void update(User u, HttpServletRequest request, HttpServletResponse response) throws IOException{
		String resultMark = "";
		try
		{
			if (userService.update(u) > 0)
			{
				resultMark = "{\"resultMark\" : 1}";
				request.getSession().setAttribute("user_theme", u.getTheme());
				request.getSession().setAttribute("user_searchexpand", u.getSearchexpand());
			}
			else
			{
				resultMark = "{\"resultMark\" : 0}";
			}
		}
		catch (Exception e)
		{
			resultMark = "{\"resultMark\" : 0}";
			e.printStackTrace();
		}
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(resultMark);
	}
*/
    public UserService getUserService() {
        return userService;
    }

    @Resource
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public DepartmentService getDepartmentService() {
        return departmentService;
    }

    @Resource
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    private User getUserInfo(String name) {
        return this.userService.getUserInfo(name);
    }


    @RequestMapping(
            value = {"/userlist"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public Map<String, Object> userlist(String userid) {
        Map<String, Object> map = new HashMap();
        if (userid == null || userid.isEmpty()) {
            map.put("errorcode", "104");
            map.put("errorinfo", "no userlist please check userid");
            map.put("date", getDate());
            map.put("results", (Object) null);
        }

        List userlist = null;

        try {
            int user_id = Integer.valueOf(userid);
            userlist = this.userService.getUserList(user_id);
        } catch (Exception var6) {
            var6.printStackTrace(System.out);
            map.put("errorcode", "104");
            map.put("errorinfo", "no userlist please check userid");
            map.put("date", getDate());
            map.put("results", var6.getMessage());
        }

        if (userlist != null && userlist.size() != 0) {
            map.put("errorcode", "100");
            map.put("errorinfo", "success");
            map.put("date", getDate());
            map.put("results", userlist);
        } else {
            map.put("errorcode", "104");
            map.put("errorinfo", "no userlist please check userid");
            map.put("date", getDate());
            map.put("results", (Object) null);
        }

        return map;
    }

    @RequestMapping(
            value = {"/userrole"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public Map<String, Object> userrole(HttpServletRequest request) {
        String username = request.getHeader("username");
        String pwd = request.getHeader("pwd");
        Boolean flag = false;
        flag = this.authenticate(request);
        Map<String, Object> map = new HashMap();
        if (flag) {
            User us = this.getUserInfo(username);
            UserInfo user = new UserInfo();
            user.setUserid(us.getId());
            if (us.getId() == us.roleId()) {
                user.setIs_role(true);
            } else {
                user.setIs_role(false);
            }
            user.setPrivilegeList(this.userService.getUserPrivileges(us.roleId()));
            user.setFullName(us.getFull_name());
            user.setRoleName(us.roleName());
            user.setDepartmentName(us.departMentFullName());

            user.setName("");
            map.put("errorcode", "100");
            map.put("errorinfo", "");
            map.put("date", getDate());
            map.put("results", user);
        } else {
            map.put("errorcode", "104");
            map.put("errorinfo", "用户名或者密码错误");
            map.put("date", getDate());
            map.put("results", flag);
        }

        return map;
    }


    @RequestMapping(
            value = {"/opendoorauthorize"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public synchronized Map openDoorauthorize(@RequestBody String requestBody, HttpServletRequest request) throws IOException {
        System.out.println("opendoorauthorize：" + requestBody);
        String username = request.getHeader("username");
        Map<String, Object> map = new HashMap();
        map.put("errorcode", "100");
        map.put("errorinfo", "创建成功");
        map.put("date", getDate());
        map.put("results", (Object) null);

        try {
            Gson gson = new Gson();
            UserInfoPhoneRmoteGrant user = (UserInfoPhoneRmoteGrant) gson.fromJson(requestBody.toString(), UserInfoPhoneRmoteGrant.class);
            this.userService.addGrantLog(user, username);
        } catch (Exception var5) {
            map.put("errorcode", "104");
            map.put("errorinfo", "add failed > json is error");
        }
        return map;
    }

    @RequestMapping(
            value = {"/updateTaskState"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    public synchronized Map updateTaskState(int taskId, String state, HttpServletRequest request) throws IOException {
        String username = request.getHeader("username");

        System.out.println("updateTaskState username = " + username + ", taskId = " + taskId);
        Map<String, Object> map = new HashMap();
        map.put("errorcode", "100");
        map.put("errorinfo", "创建成功");
        map.put("date", getDate());
        map.put("results", (Object) null);

        try {
            if (this.userService.updateTaskState(taskId, state, username)) {
                map.put("errorcode", "100");
                map.put("errorinfo", "操作成功");
            } else {
                map.put("errorcode", "104");
                map.put("errorinfo", "操作失败");
            }
        } catch (Exception var5) {
            map.put("errorcode", "104");
            map.put("errorinfo", "授权失败");
        }
        return map;
    }

    @RequestMapping(
            value = {"/opendoorauthlst"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public Map<String, Object> opendoorauthlst(HttpServletRequest request) {
        String username = request.getHeader("username");
        String pwd = request.getHeader("pwd");
        Map<String, Object> map = new HashMap();
        if (username != null && !"".equals(username)) {
            try {
                List<GrantTaskInfo> list = this.userService.getGrantTaskList(username);
                map.put("errorcode", "100");
                map.put("errorinfo", "");
                map.put("date", getDate());
                map.put("results", list);
            } catch (Exception var6) {
                map.put("errorcode", "104");
                map.put("errorinfo", "获取授权列表失败");
                map.put("date", getDate());
                map.put("results", "");
                var6.printStackTrace(System.out);
            }
            return map;
        } else {
            map.put("errorinfo", "104");
            map.put("errorcode", "请务必填写用户名");
            map.put("date", getDate());
            map.put("results", (Object) null);
            return map;
        }
    }
}
