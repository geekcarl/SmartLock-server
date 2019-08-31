package com.sxt.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.sxt.po.*;
import com.sxt.service.UserService;
import com.sxt.utils.LogUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.sxt.service.BoxEventAndWarnService;
import com.sxt.service.DepartmentService;
import com.sxt.service.LockAndKeyService;

@Controller
public class BoxEventAndWarnController {
    private LockAndKeyService lockandkeyService;
    private DepartmentService departmentService;
    private BoxEventAndWarnService boxEventAndWarnService;
    private UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    // 获取当前系统日期
    public static String getDate() {
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return formatter.format(date);
    }

    public static String formatdate(Date dt) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(dt);
    }

    public static String formatStandardDate(Date dt) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(dt);
    }

    // 查询开关门记录
    @RequestMapping(value = "/openlistold", method = RequestMethod.GET)
    public @ResponseBody
    Map getDoorEventold(String param) {

        /*
         * String errorcode ="100"; String errorinfo="";
         *
         * if (param == null || param.equals("")){
         *
         * errorcode ="104"; errorinfo="查询条件不能为空";
         *
         * Map<String, Object> map = new HashMap<String, Object>();
         * map.put("errorcode",errorcode); //errorcode map.put("errorinfo", errorinfo);
         * map.put("date", getDate()); map.put("results", ""); return map;
         *
         * }
         *
         * String boxparam=""; try { boxparam = new
         * String(param.getBytes("iso8859-1"),"utf-8"); //解决乱码问题 } catch
         * (UnsupportedEncodingException e) { // TODO Auto-generated catch block
         * e.printStackTrace(); } System.out.println("param================"+boxparam);
         *
         *
         * Map<String, Object> map = new HashMap<String, Object>(); Map<String, Object>
         * depart = new HashMap<String, Object>();
         *
         *
         * String boxstate=""; List<DoorEventSimple> ds = new
         * ArrayList<DoorEventSimple>();
         *
         * List<DoorEvent> de = boxEventAndWarnService.findDoorEvent(param);
         *
         * for(DoorEvent b : de){
         *
         * DoorEventSimple bo = new DoorEventSimple(); bo.setId(b.getId());
         * bo.setBox_no(b.getBoxinfo().getBox_no());
         * bo.setControllerid(b.getBoxinfo().getController_id());
         * bo.setDepname(b.getBoxinfo().getDepartment().getName());
         * bo.setOpen_time(b.getOpen_time().toString());
         * bo.setClose_time(b.getClose_time().toString());
         *
         *
         * ds.add(bo);
         *
         * }
         *
         *
         * map.put("errorcode", "100"); //errorcode map.put("errorinfo", "");
         * map.put("date", getDate()); map.put("results", ds); return map;
         */

        return null;
    }

    //上传开关门日志记录
    @RequestMapping(value = "/uploadopendoorlog", method = RequestMethod.POST)
    public @ResponseBody
    Map uploadopendoorlog(@RequestBody List<DoorEventLog> ol) {

        String errorcode = "100";
        String errorinfo = "";
        try {
            //存入数据库
            if (ol.size() > 0) {
                for (DoorEventLog lst : ol) {
                    LogUtils.debug("开始处理数据：" + lst.toString());

                    DoorEventAdd event = new DoorEventAdd();
                    //获取上传 数据
                    String key_code = lst.getKeycode();
                    String lock_code = lst.getLockcode();
                    String open_time = lst.getOperTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date opentimedate = sdf.parse(open_time);

                    String key_rfid = lockandkeyService.findrfidbyKeycode(key_code);
                    List<LockInfo> lockInfos = lockandkeyService.findBoxInfoByLockCode(lock_code);
                    if (lockInfos != null && !lockInfos.isEmpty()) {
                        BoxInfoNew info = lockInfos.get(0).getBoxInfo();
                        event.setBox_id(info.getId());
                    }
                    User user = userService.findById(lst.getOperUserId());

                    if (lst.getOperType() == 0) {
                        //开门
                        if (user != null) {
                            event.setOpen_operators(user.getFull_name());
                        }
                        event.setOpen_rfids(key_rfid);
                        event.setOpen_keys(key_rfid);
                        event.setOpen_time(opentimedate);
                    } else {
                        //关门
                        if (user != null) {
                            event.setClose_operators(user.getFull_name());
                        }
                        event.setClose_rfids(key_rfid);
                        event.setClose_keys(key_rfid);
                        event.setClose_time(opentimedate);
                    }
                    boxEventAndWarnService.addOrUpdateDoorEvent(event);
                }
            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
            errorcode = "104";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorinfo", errorinfo);
        map.put("errorcode", errorcode);
        map.put("date", getDate());
        map.put("results", null);
        return map;
    }

    // 查询开关门记录
    @RequestMapping(value = "/openlist", method = RequestMethod.GET)
    public @ResponseBody
    Map getDoorEvent(Integer boxid, String opendate, Integer num) {

        String errorcode = "100";
        String errorinfo = "";

        if (boxid == null) {

            errorcode = "104";
            errorinfo = "光交箱id不能为空";

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("errorcode", errorcode); // errorcode
            map.put("errorinfo", errorinfo);
            map.put("date", getDate());
            map.put("results", "");
            return map;
        }

        if (opendate == null || opendate.equals("")) {

            errorcode = "104";
            errorinfo = "请输入查询日期";

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("errorcode", errorcode); // errorcode
            map.put("errorinfo", errorinfo);
            map.put("date", getDate());
            map.put("results", "");
            return map;

        }

        if (num == null) {

            errorcode = "104";
            errorinfo = "请输入天数";

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("errorcode", errorcode); // errorcode
            map.put("errorinfo", errorinfo);
            map.put("date", getDate());
            map.put("results", "");
            return map;

        }

        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> depart = new HashMap<String, Object>();

        String boxstate = "";
        List<DoorEventSimple> ds = new ArrayList<DoorEventSimple>();

        List<DoorEvent> de = boxEventAndWarnService.findDoorEvent(boxid, opendate, num);

        for (DoorEvent b : de) {

            DoorEventSimple bo = new DoorEventSimple();
            bo.setId(b.getId());
            bo.setBox_no(b.getBoxinfo().getBox_no());
            bo.setControllerid(b.getBoxinfo().getController_id());
            bo.setDepname(b.getBoxinfo().getDepartment().getName());
            if (b.getOpen_time() != null)
                bo.setOpen_time(formatdate(b.getOpen_time()));
            if (b.getClose_time() != null)
                bo.setClose_time(formatdate(b.getClose_time()));

            ds.add(bo);

        }

        map.put("errorcode", "100"); // errorcode
        map.put("errorinfo", "");
        map.put("date", getDate());
        map.put("results", ds);
        return map;

    }

    // 查询开关门记录详细信息
    @RequestMapping(value = "/openlistdetail", method = RequestMethod.GET)
    public @ResponseBody
    Map getDetailOpenInfo(Integer id) {

        DoorEvent dv = new DoorEvent();

        DoorEventDetail drdetail = new DoorEventDetail();

        String errorcode = "100";
        String errorinfo = "";
        try {

            if (id == null) {

                errorcode = "104";
                errorinfo = "请务必传送id";

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorcode", errorcode); // errorcode
                map.put("errorinfo", errorinfo);
                map.put("date", getDate());
                map.put("results", "");
                return map;
            }

            dv = boxEventAndWarnService.findDoorEventbyID(id);

            if (dv == null) {

                errorcode = "104";
                errorinfo = "此记录不存在";

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorcode", errorcode); // errorcode
                map.put("errorinfo", errorinfo);
                map.put("date", getDate());
                map.put("results", "");
                return map;

            }

            drdetail.setId(dv.getId());

            if (dv.getBoxinfo() != null && dv.getBoxinfo().getBox_no() != null && !dv.getBoxinfo().getBox_no().equals(""))
                drdetail.setBox_no(dv.getBoxinfo().getBox_no());
            if (dv.getBoxinfo() != null && dv.getBoxinfo().getDepartment() != null
                    && !dv.getBoxinfo().getDepartment().getName().equals(""))
                drdetail.setDepname(dv.getBoxinfo().getDepartment().getName());
            if (dv.getBoxinfo() != null && dv.getBoxinfo().getController_id() != null)
                drdetail.setControllerid(dv.getBoxinfo().getController_id());
            if (dv.getBoxinfo() != null && dv.getBoxinfo().getAddress() != null && !dv.getBoxinfo().getAddress().equals(""))
                drdetail.setAddress(dv.getBoxinfo().getAddress());

            if (dv.getOpen_time() != null && !dv.getOpen_time().equals(""))
                drdetail.setOpen_time(formatdate(dv.getOpen_time()));
            if (dv.getClose_time() != null && !dv.getClose_time().equals(""))
                drdetail.setClose_time(formatdate(dv.getClose_time()));
            if (dv.getOpen_keys() != null && !dv.getOpen_keys().equals(""))
                drdetail.setOpen_keys(dv.getOpen_keys());
            if (dv.getOpen_operators() != null && !dv.getOpen_operators().equals(""))
                drdetail.setOpen_operators(dv.getOpen_operators());
            if (dv.getOpen_rfids() != null && !dv.getOpen_rfids().equals(""))
                drdetail.setOpen_rfids(dv.getOpen_rfids());
            if (dv.getClose_operators() != null && !dv.getClose_operators().equals(""))
                drdetail.setClose_operators(dv.getClose_operators());
            if (dv.getClose_rfids() != null && !dv.getClose_rfids().equals(""))
                drdetail.setClose_rfids(dv.getClose_rfids());
            if (dv.getClose_keys() != null && !dv.getClose_keys().equals(""))
                drdetail.setClose_keys(dv.getClose_keys());
        } catch (Exception ex) {
            errorcode = "104";
            errorinfo = ""; // ex.toString();

        } finally {

        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorcode", errorcode); // errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", drdetail);
        return map;

    }

    // 查询告警记录
    @RequestMapping(value = "/warnlist", method = RequestMethod.GET)
    public @ResponseBody
    Map getWarnEvent(Integer boxid, String alertdate, Integer num) {

        String errorcode = "100";
        String errorinfo = "";

        if (boxid == null) {

            errorcode = "104";
            errorinfo = "光交箱id不能为空";

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("errorcode", errorcode); // errorcode
            map.put("errorinfo", errorinfo);
            map.put("date", getDate());
            map.put("results", "");
            return map;

        }

        if (alertdate == null || alertdate.equals("")) {

            errorcode = "104";
            errorinfo = "请输入查询日期";

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("errorcode", errorcode); // errorcode
            map.put("errorinfo", errorinfo);
            map.put("date", getDate());
            map.put("results", "");
            return map;

        }

        if (num == null) {

            errorcode = "104";
            errorinfo = "请输入天数";

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("errorcode", errorcode); // errorcode
            map.put("errorinfo", errorinfo);
            map.put("date", getDate());
            map.put("results", "");
            return map;

        }

        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> depart = new HashMap<String, Object>();

        String boxstate = "";
        List<AlarmEventSimple> ds = new ArrayList<AlarmEventSimple>();

        List<AlarmEvent> de = boxEventAndWarnService.findWarnEvent(boxid, alertdate, num);

        for (AlarmEvent b : de) {

            AlarmEventSimple bo = new AlarmEventSimple();
            bo.setId(b.getId());
            if (b.getBoxinfo() != null && b.getBoxinfo().getBox_no() != null && !b.getBoxinfo().getBox_no().equals(""))
                bo.setBox_no(b.getBoxinfo().getBox_no());
            if (b.getAlarm_type() != null && !b.getAlarm_type().equals(""))
                bo.setAlarm_type(b.getAlarm_type());

            if (b.getAlarm_time() != null)
                bo.setAlarm_time(formatdate(b.getAlarm_time()));

            ds.add(bo);

        }

        map.put("errorcode", "100"); // errorcode
        map.put("errorinfo", "");
        map.put("date", getDate());
        map.put("results", ds);
        return map;

    }

    // 查询告警记录详细信息
    @RequestMapping(value = "/warnlistdetail", method = RequestMethod.GET)
    public @ResponseBody
    Map getDetailWarnInfo(Integer id) {

        AlarmEvent dv = new AlarmEvent();

        AlarmEventDetail drdetail = new AlarmEventDetail();

        String errorcode = "100";
        String errorinfo = "";
        try {

            if (id == null) {

                errorcode = "104";
                errorinfo = "请务必传送id";

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorcode", errorcode); // errorcode
                map.put("errorinfo", errorinfo);
                map.put("date", getDate());
                map.put("results", "");
                return map;
            }

            dv = boxEventAndWarnService.findAlarmEventbyID(id);

            if (dv == null) {

                errorcode = "104";
                errorinfo = "此记录不存在";

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorcode", errorcode); // errorcode
                map.put("errorinfo", errorinfo);
                map.put("date", getDate());
                map.put("results", "");
                return map;

            }

            drdetail.setId(dv.getId());

            if (dv.getBoxinfo() != null && dv.getBoxinfo().getBox_no() != null && !dv.getBoxinfo().getBox_no().equals(""))
                drdetail.setBox_no(dv.getBoxinfo().getBox_no());
            if (dv.getBoxinfo() != null && dv.getBoxinfo().getDepartment() != null
                    && !dv.getBoxinfo().getDepartment().getName().equals(""))
                drdetail.setDepname(dv.getBoxinfo().getDepartment().getName());
            if (dv.getBoxinfo() != null && dv.getBoxinfo().getController_id() != null)
                drdetail.setController_id(dv.getBoxinfo().getController_id());

            if (dv.getAlarm_time() != null && !dv.getAlarm_time().equals(""))
                drdetail.setAlarm_time(formatdate(dv.getAlarm_time()));
            if (dv.getAlarm_type() != null && !dv.getAlarm_type().equals(""))
                drdetail.setAlarm_type(dv.getAlarm_type());
            if (dv.getAlarm_keys() != null && !dv.getAlarm_keys().equals(""))
                drdetail.setAlarm_keys(dv.getAlarm_keys());
            if (dv.getAlarm_operators() != null && !dv.getAlarm_operators().equals(""))
                drdetail.setAlarm_operators(dv.getAlarm_operators());
            if (dv.getAlarm_rfids() != null && !dv.getAlarm_rfids().equals(""))
                drdetail.setAlarm_rfids(dv.getAlarm_rfids());
            if (dv.getBoxinfo() != null && dv.getBoxinfo().getAddress() != null && !dv.getBoxinfo().getAddress().equals(""))
                drdetail.setAddress(dv.getBoxinfo().getAddress());

        } catch (Exception ex) {
            errorcode = "104";
            errorinfo = ""; // ex.toString();

        } finally {

        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorcode", errorcode); // errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", drdetail);
        return map;

    }

    // 开关门列表请求
    @RequestMapping(params = "method=boxeventList")
    public String boxEventList(HttpServletRequest request, ModelMap map) {
        List<Department> departs = departmentService.findAll();
        if (departs.size() > 0)
            map.put("departments", DepartmentController.departs2json(departs, 1));
        return "boxeventList";
    }

    // 警告列表请求
    @RequestMapping(params = "method=boxwarnList")
    public String boxWarnList(HttpServletRequest request, ModelMap map) {
        List<Department> departs = departmentService.findAll();
        map.put("departments", DepartmentController.departs2json(departs, 1));
        return "boxwarnList";
    }

    // 获取开关门分页
    @RequestMapping(params = "method=listBoxEventPageByAjax")
    public @ResponseBody
    ListInfoTemplate listBoxEventPageByAjax(DoorEventSearch ds) {
        if (ds != null && ds.getRows() <= 0) {
            ds.setRows(10);
        }
        ListInfoTemplate lit = new ListInfoTemplate();
        lit.setPage(ds.getPage());
        int counts = this.boxEventAndWarnService.findDoorEventCounts(ds);
        lit.setTotal(counts % ds.getRows() > 0 ? (counts / ds.getRows() + 1) : (counts / ds.getRows()));
        lit.setRecords(counts);
        lit.setRows(this.boxEventAndWarnService.findDoorEventByPager(ds));
        return lit;
    }

    // 获取报警分页
    @RequestMapping(params = "method=listAlarmEventPageByAjax")
    public @ResponseBody
    ListInfoTemplate listAlarmEventPageByAjax(AlarmEventSearch ds) {
        if (ds != null && ds.getRows() <= 0) {
            ds.setRows(10);
        }
        ListInfoTemplate lit = new ListInfoTemplate();
        lit.setPage(ds.getPage());
        int counts = this.boxEventAndWarnService.findAlarmEventCounts(ds);
        lit.setTotal(counts % ds.getRows() > 0 ? (counts / ds.getRows() + 1) : (counts / ds.getRows()));
        lit.setRecords(counts);
        lit.setRows(this.boxEventAndWarnService.findAlarmEventByPager(ds));
        return lit;
    }

    // 删除报警记录
    @RequestMapping(params = "method=deleteAlarmEventPageByAjax")
    public @ResponseBody
    AjaxRetObjTemplate deleteAlarmEventPageByAjax(DeleteAlarmForm daf) {
        AjaxRetObjTemplate arot = new AjaxRetObjTemplate();
        try {
            this.boxEventAndWarnService.deleteAlarmEvent(daf);
            arot.setResultMark(1);
        } catch (Exception e) {
            arot.setResultMark(0);
            e.printStackTrace();
        }
        return arot;
    }

    // 确认报警记录
    @RequestMapping(params = "method=affirmAlarmEventByAjax")
    public @ResponseBody
    AjaxRetObjTemplate affirmAlarmEventByAjax(AlarmEvent ae, HttpServletRequest request) {
        AjaxRetObjTemplate arot = new AjaxRetObjTemplate();
        try {
            ae.setAffirm_user(request.getSession().getAttribute("user_name").toString());
            this.boxEventAndWarnService.affirmAlarmEvent(ae);
            arot.setResultMark(1);
        } catch (Exception e) {
            arot.setResultMark(0);
            e.printStackTrace();
        }
        return arot;
    }

    // 删除开关门记录
    @RequestMapping(params = "method=deleteDoorEventPageByAjax")
    public @ResponseBody
    AjaxRetObjTemplate deleteDoorEventPageByAjax(DeleteDoorForm daf) {
        AjaxRetObjTemplate arot = new AjaxRetObjTemplate();
        try {
            this.boxEventAndWarnService.deleteDoorEvent(daf);
            arot.setResultMark(1);
        } catch (Exception e) {
            arot.setResultMark(0);
            e.printStackTrace();
        }
        return arot;
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

    public BoxEventAndWarnService getBoxEventAndWarnService() {
        return boxEventAndWarnService;
    }

    @Resource
    public void setBoxEventAndWarnService(BoxEventAndWarnService boxEventAndWarnService) {
        this.boxEventAndWarnService = boxEventAndWarnService;
    }

    public UserService getUserService() {
        return userService;
    }

    @Resource
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
