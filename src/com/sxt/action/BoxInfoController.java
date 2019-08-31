package com.sxt.action;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.sxt.dao.BoxInfoDao;
import com.sxt.po.AlarmOrderDetails;
import com.sxt.po.AlarmOrderDetailsNew;
import com.sxt.po.AlertTypeList;
import com.sxt.po.AtomType;
import com.sxt.po.BaiduLocation;
import com.sxt.po.BoxGlobals;
import com.sxt.po.BoxImages;
import com.sxt.po.BoxInfo;
import com.sxt.po.BoxInfoDetail;
import com.sxt.po.BoxInfoGroupbyDep;
import com.sxt.po.BoxInfoModify;
import com.sxt.po.BoxInfoSearch;
import com.sxt.po.BoxPrivilege;
import com.sxt.po.BoxSettings;
import com.sxt.po.BoxStates;
import com.sxt.po.BoxUpload;
import com.sxt.po.CoreAtomType;
import com.sxt.po.ImagesInfo;
import com.sxt.po.LabelInfo;
import com.sxt.po.LockModify;
import com.sxt.po.LockSimpleInfo;
import com.sxt.po.ModificationInfo;
import com.sxt.po.ModulesAndTerminals;
import com.sxt.po.ModulesAndTerminalsForXQ;
import com.sxt.po.OpticalcableCore;
import com.sxt.po.OrderInfactNew;
import com.sxt.po.OrderRespect;
import com.sxt.po.OrderRespectDetail;
import com.sxt.po.RepairRecord;
import com.sxt.po.RepairRecordNew;
import com.sxt.po.RepairRecordSimple;
import com.sxt.po.RepairRecordUpload;
import com.sxt.po.SysConfig;
import com.sxt.po.User;
import com.sxt.po.WorkOrder;
import com.sxt.po.WorkOrderDetail;
import com.sxt.po.WorkOrderImage;
import com.sxt.po.WorkOrderNew;
import com.sxt.po.WorkOrderSimple;
import com.sxt.po.guanlian;
import com.sxt.po.tiaoqian;
import com.sxt.po.zhirong;
import com.sxt.service.BoxEventAndWarnService;
import com.sxt.service.BoxInfoService;
import com.sxt.service.DepartmentService;
import com.sxt.service.DictionaryService;
import com.sxt.service.LockAndKeyService;
import com.sxt.service.OpticalcableService;
import com.sxt.service.UserService;
import com.sxt.utils.GpsToBaidu;

@Controller
public class BoxInfoController {

    private boolean authenResult = false;
    private BoxInfoService boxInfoService;
    private BoxEventAndWarnService boxEventAndWarnService;
    private LockAndKeyService lockandkeyService;
    private DictionaryService dictionaryService;
    private DepartmentService departmentService;
    private OpticalcableService opticalcableService;
    private UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    // 获取所有的光交箱信息，AJAX
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public @ResponseBody List<BoxInfo> listByAjax(BoxInfoSearch bis) {
        System.out.println("BoxInfoController.listByAjax()");
        Map<String, Object> map = new HashMap<String, Object>(); // LinkedHashMap
        List<BoxInfo> bs = boxInfoService.findAll();
        return bs;
    }

    /* begin******************LY */

    // 获取有权限的光交箱列表
    private String getBoxidlst(HttpServletRequest request) {

        String username = "";

        username = request.getHeader("username");

        String boxlst = "";
        int userid = 0;
        userid = userService.findByName(username);

        if (userid != 0)
            boxlst = boxInfoService.findgrantBox(userid);

        return boxlst;

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

    // 添加光交箱@RequestBody BoxInfo
    @RequestMapping(value = "/boxinfo", method = RequestMethod.POST)
    synchronized public @ResponseBody Map newFocccInfo(@RequestBody BoxUpload b, HttpServletRequest request)
            throws IOException {
        System.out.println("boxInfoController.newFocccInfo()");
        String errorcode = "100";
        String errorinfo = "";

        try {
            // ****************备忘 新建的时候要判断box_no是否唯一 departmentid 不能为空
            if (b.getBox_no() == null || b.getBox_no().equals("")) {

                errorcode = "104";
                errorinfo = "请务必填写光交箱编号";

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorcode", errorcode); // errorcode
                map.put("errorinfo", errorinfo);
                map.put("date", getDate());
                map.put("results", null);
                return map;
            }

            if (b.getDepartment_id() == 0) {

                errorcode = "104";
                errorinfo = "请务必填写光交箱部门";

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorcode", errorcode); // errorcode
                map.put("errorinfo", errorinfo);
                map.put("date", getDate());
                map.put("results", null);
                return map;
            }

            BoxInfoModify boxinfo = new BoxInfoModify();

            // 如果光交箱编号已存在则不能上传
            if (b.getBox_no() != null && !b.getBox_no().equals("")) {

                if (boxInfoService.findbyno(b.getBox_no().trim())) {

                    errorcode = "104";
                    errorinfo = "光交箱编号已存在";

                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("errorcode", errorcode); // errorcode
                    map.put("errorinfo", errorinfo);
                    map.put("date", getDate());
                    map.put("results", null);
                    return map;

                }
            }

            // 如果控制器号已存在则不能上传！！！
            if (b.getController_id() != 0) {

                if (boxInfoService.findbycontrollerid(b.getController_id())) {

                    errorcode = "104";
                    errorinfo = "光交箱控制器号已存在";

                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("errorcode", errorcode); // errorcode
                    map.put("errorinfo", errorinfo);
                    map.put("date", getDate());
                    map.put("results", null);
                    return map;

                }
            }

            int use_state = 1; // 光交箱运行状态
            boxinfo.setId(b.getId());
            if (b.getAddress() != null && !b.getAddress().equals(""))
                boxinfo.setAddress(b.getAddress());
            boxinfo.setBox_no(b.getBox_no());
            if (b.getController_id() != 0) {

                boxinfo.setController_id(b.getController_id());
                use_state = 3; // 若控制器id不为空 ，则光交箱状态为已运行

            }

            boxinfo.setUse_state(use_state);

            if (b.getBox_name() != null && !b.getBox_name().equals(""))
                boxinfo.setBox_name(b.getBox_name());
            if (b.getSim_phone_no() != null && !b.getSim_phone_no().equals(""))
                boxinfo.setSim_phone_no(b.getSim_phone_no());
            boxinfo.setLongitude(b.getLongitude());
            boxinfo.setLatitude(b.getLatitude());

            if (b.getLongitude() != 0 || b.getLatitude() != 0) {
                BaiduLocation bl = new BaiduLocation();
                bl.gpsx = b.getLongitude();
                bl.gpsy = b.getLatitude();
                GpsToBaidu convertor = new GpsToBaidu();
                convertor.GetBaiduLocation(bl);
                if (bl.ok) {
                    boxinfo.setB_longitude(bl.baidux);
                    boxinfo.setB_latitude(bl.baiduy);
                }
            }

            boxinfo.setDepartment_id(b.getDepartment_id());
            if (b.getBox_type() != null && !b.getBox_type().equals(""))
                boxinfo.setBox_type(b.getBox_type());

            if (b.getK_code() != null && !b.getK_code().equals(""))
                boxinfo.setK_code(b.getK_code());
            if (b.getRemarks() != null && !b.getRemarks().equals(""))
                boxinfo.setRemarks(b.getRemarks());

            List<LockSimpleInfo> lo = b.getLocklist();

            // 更新锁的数量
            if (lo != null) {

                boxinfo.setLocks_count(lo.size());

            }

            boxinfo.setLastedittime(getDate());
            // boxinfo.setLastedituser(lastedituser);

            boxInfoService.addboxinfo(boxinfo); // 新增光交箱

            // 新增光交箱时要在boxsetting表中增加对应的

            /*** 新增光交箱后 要在权限表中增加对应的箱子 ***/
            String username = "";
            username = request.getHeader("username");

            if (!username.equals("admin")) {// 赋予此用户此光交箱权限
                BoxPrivilege bps = new BoxPrivilege();
                bps.setBox_id(boxinfo.getId());
                bps.setSet_date(new Date());

                if (username != null && !username.equals("")) {

                    int userid = 0;
                    userid = userService.findByName(username);
                    bps.setUser_id(userid);
                    bps.setSet_user_id(userid);

                }
                this.boxInfoService.addBoxPrivilege(bps);
            }

            if (lo != null) {

                List<LockModify> lonew = new ArrayList<LockModify>();
                for (LockSimpleInfo l : lo) {
                    LockModify lockinfo = new LockModify();
                    lockinfo.setBox_id(boxinfo.getId());
                    lockinfo.setLock_code(l.getNumber());
                    lockinfo.setType_id(lockandkeyService.findLockInfoByID(l.getType()));

                    lonew.add(lockinfo);

                    lockandkeyService.addLock(lockinfo);
                }

            }

            // 1.获取光交箱全局设置值
            // BoxGlobals bg = boxInfoService.getBoxGlobals();
            // int userid = userService.findByName(username);
            //

            // //2.新增光交箱设置值(全局)
            // BoxSettings bs = new BoxSettings();
            // if (bg != null){
            // bs.setBox_id(boxinfo.getId());
            // bs.setFlag(1);
            // bs.setHb_interval(bg.getHb_interval());
            // bs.setVolt_threshold(bg.getVolt_threshold());
            // bs.setAngle_threshold(bg.getAngle_threshold());
            // bs.setHigh_t_threshold(bg.getHigh_t_threshold());
            // bs.setLow_t_threshold(bg.getLow_t_threshold());
            //
            // bs.setLowpower_period(bg.getLowpower_period());
            // bs.setLowpower_periodpercent(bg.getLowpower_periodpercent());
            // bs.setShake_peroid(bg.getShake_peroid());
            // bs.setShake_frequency(bg.getShake_frequency());
            // bs.setShake_time(bg.getShake_time());
            // bs.setSet_user(userid);
            // bs.setLastedittime(new Date());
            // }
            // boxInfoService.addBoxSettings(bs);

            // //3.新增光交箱下发值(全局)
            // ModificationInfo mi = new ModificationInfo();
            // if(bg != null){
            // mi.setBox_id(boxinfo.getId());
            // mi.setFlag(1);
            // mi.setShake_threshold(bg.getShake_threshold());
            // mi.setShake_rate(bg.getShake_rate());
            // mi.setCenter_ip(bg.getCenter_ip());
            // mi.setCenter_upd_port(bg.getCenter_upd_port());
            // mi.setIs_send(0);
            // mi.setSet_user(userid);
            // mi.setLastedittime(new Date());
            // }
            // boxInfoService.addModificationInfo(mi);

        } catch (Exception ex) {
            errorcode = "104";
            // errorinfo=ex.toString();
            ex.printStackTrace();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorcode", errorcode); // errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", null);
        return map;

    }

    /*
     * //修改光交箱@RequestBody BoxInfo
     * 
     * @RequestMapping(value = "/boxinfo", method = RequestMethod.PUT)
     * public @ResponseBody Map editFocccInfo(@RequestBody BoxInfo boxInfo) throws
     * IOException{ System.out.println("boxInfoController.editFocccInfo()");
     * System.out.println("boxInfo.getId()============"+boxInfo.getId());
     * 
     * // boxInfoService.update(boxInfo);
     * 
     * 
     * Map<String, Object> map = new HashMap<String, Object>(); map.put("errorcode",
     * "100"); //errorcode map.put("errorinfo", "2222"); map.put("date", getDate());
     * map.put("results",boxInfo.getId()); return map; }
     */

    // 更新光交箱信息 含锁 //删掉以前的，插入post的
    @RequestMapping(value = "/boxinfo", method = RequestMethod.PUT)
    public @ResponseBody Map editLockInfo(@RequestBody BoxUpload b) throws IOException {
        System.out.println("boxInfoController.editLockInfo()");
        String errorcode = "100";
        String errorinfo = "";

        if (b.getId() == 0) {

            errorcode = "104";
            errorinfo = "请务必填写光交箱id";

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("errorcode", errorcode); // errorcode
            map.put("errorinfo", errorinfo);
            map.put("date", getDate());
            map.put("results", null);
            return map;
        }

        if (b.getBox_no() == null || b.getBox_no().equals("")) {

            errorcode = "104";
            errorinfo = "请务必填写光交箱编号";

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("errorcode", errorcode); // errorcode
            map.put("errorinfo", errorinfo);
            map.put("date", getDate());
            map.put("results", null);
            return map;
        }

        // 如果光交箱编号已存在则不能上传
        if (b.getBox_no() != null && !b.getBox_no().equals("")) {

            if (boxInfoService.findbyno(b.getBox_no().trim(), b.getId())) {

                errorcode = "104";
                errorinfo = "光交箱编号已存在";

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorcode", errorcode); // errorcode
                map.put("errorinfo", errorinfo);
                map.put("date", getDate());
                map.put("results", null);
                return map;

            }
        }

        // 如果控制器号已存在则不能上传！！！
        if (b.getController_id() != 0) {

            if (boxInfoService.findbycontrollerid(b.getController_id(), b.getId())) {

                errorcode = "104";
                errorinfo = "光交箱控制器号已存在";

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorcode", errorcode); // errorcode
                map.put("errorinfo", errorinfo);
                map.put("date", getDate());
                map.put("results", null);
                return map;

            }
        }

        try {

            BoxInfoModify boxinfo = new BoxInfoModify();
            boxinfo.setId(b.getId());
            if (b.getAddress() != null && !b.getAddress().equals(""))
                boxinfo.setAddress(b.getAddress());
            boxinfo.setBox_no(b.getBox_no());
            if (b.getController_id() != 0) {
                boxinfo.setController_id(b.getController_id());
                boxinfo.setUse_state(3);
            }

            if (b.getBox_name() != null && !b.getBox_name().equals(""))
                boxinfo.setBox_name(b.getBox_name());
            if (b.getSim_phone_no() != null && !b.getSim_phone_no().equals(""))
                boxinfo.setSim_phone_no(b.getSim_phone_no());
            boxinfo.setLongitude(b.getLongitude());
            boxinfo.setLatitude(b.getLatitude());
            if (b.getLongitude() != 0 || b.getLatitude() != 0) {
                BaiduLocation bl = new BaiduLocation();
                bl.gpsx = b.getLongitude();
                bl.gpsy = b.getLatitude();
                GpsToBaidu convertor = new GpsToBaidu();
                convertor.GetBaiduLocation(bl);
                if (bl.ok) {
                    boxinfo.setB_longitude(bl.baidux);
                    boxinfo.setB_latitude(bl.baiduy);
                }

            }

            boxinfo.setDepartment_id(b.getDepartment_id());
            if (b.getBox_type() != null && !b.getBox_type().equals(""))
                boxinfo.setBox_type(b.getBox_type());

            if (b.getK_code() != null && !b.getK_code().equals(""))
                boxinfo.setK_code(b.getK_code());
            if (b.getRemarks() != null && !b.getRemarks().equals(""))
                boxinfo.setRemarks(b.getRemarks());

            if (b.getLocklist() != null) {

                boxinfo.setLocks_count(b.getLocklist().size());

            }

            boxInfoService.updateBoxinfo(boxinfo);

            if (b.getLocklist() != null) {
                List<LockSimpleInfo> lo = b.getLocklist();
                List<LockModify> lonew = new ArrayList<LockModify>();
                for (LockSimpleInfo l : lo) {
                    LockModify lockinfo = new LockModify();
                    lockinfo.setBox_id(b.getId());
                    lockinfo.setLock_code(l.getNumber());
                    lockinfo.setType_id(lockandkeyService.findLockInfoByID(l.getType()));

                    lonew.add(lockinfo);
                }

                lockandkeyService.updateLockInfo(lonew, b.getId()); // 先删除光交箱下所有的锁，再将锁列表添加
            } else {

                lockandkeyService.delLockInfo(b.getId());

            }

        } catch (Exception ex) {
            errorcode = "104";
            System.out.println(ex.toString());
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorcode", errorcode); // errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", null);
        return map;
    }

    // 删除光交箱@RequestBody BoxInfo
    @RequestMapping(value = "/boxinfo", method = RequestMethod.DELETE)
    public @ResponseBody Map delFocccInfo(Integer id) throws IOException {
        System.out.println("boxInfoController.delFocccInfo()");
        String errorcode = "100";
        String errorinfo = "";

        try {

            if (id == null) {

                errorcode = "104";
                errorinfo = "请务必填写光交箱id";

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorcode", errorcode); // errorcode
                map.put("errorinfo", errorinfo);
                map.put("date", getDate());
                map.put("results", null);
                return map;
            }

            BoxInfo b = new BoxInfo();
            b.setId(id);
            boxInfoService.deletebox(b);
        } catch (Exception ex) {
            errorcode = "104";

        } finally {

        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorcode", "100"); // errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", id);
        return map;

    }

    // 获取所有的光交箱信息 按部门分组
    @RequestMapping(value = "/boxinfo", method = RequestMethod.GET)
    public @ResponseBody Map getAllBoxInfo(HttpServletRequest request) {
        System.out.println("BoxInfoController.getAllBoxInfo()");

        String username = "";
        username = request.getHeader("username");

        String grantBoxid = getBoxidlst(request);

        System.out.println("===========================" + grantBoxid);

        BoxInfoSearch bis = null;

        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> depart = new HashMap<String, Object>();

        Map<String, Object> depart2 = new HashMap<String, Object>();
        String boxstate = "";

        List ls = new ArrayList();
        List<BoxInfo> bs = new ArrayList<BoxInfo>();

        if (username.equals("admin")) {

            bs = boxInfoService.findAll();

        } else {

            bs = boxInfoService.findAllByGrant(grantBoxid);
        }

        int departmentid = 0;

        if (bs != null) {
            // 將光交箱的值按照部门分组
            for (BoxInfo bill : bs) {
                departmentid = bill.getDepartment().getId();

                depart.put(bill.getDepartment().getFull_name(), "");
            }

            for (String depname : depart.keySet()) {
                List<BoxInfoGroupbyDep> groupbs = new ArrayList<BoxInfoGroupbyDep>();
                Map<String, Object> boxdetail = new HashMap<String, Object>();

                for (BoxInfo b : bs) {

                    if (depname.equals(b.getDepartment().getFull_name())) {
                        boxstate = "";
                        // 处理该组的数据
                        BoxInfoGroupbyDep bo = new BoxInfoGroupbyDep();
                        bo.setId(b.getId());
                        if (b.getController_id() != null)
                            bo.setController_id(b.getController_id());
                        bo.setBox_no(b.getBox_no());
                        bo.setDepartmentid(b.getDepartment().getId());
                        bo.setDepartmentname(b.getDepartment().getFull_name());
                        if (b.getLongitude() != null)
                            bo.setLongitude(b.getLongitude());
                        if (b.getLatitude() != null)
                            bo.setLatitude(b.getLatitude());
                        if (b.getK_code() != null && !b.getK_code().equals(""))
                            bo.setK_code(b.getK_code());

                        if (b.getAddress() != null && !b.getAddress().equals(""))
                            bo.setAddress(b.getAddress());
                        if (b.getBoxVarInfo() != null && b.getBoxVarInfo().getLast_heard() != null)
                            bo.setLast_heard(formatdate(b.getBoxVarInfo().getLast_heard()));

                        for (BoxStates bstate : b.getBoxStates()) {

                            if (bstate.getState_key().equals("在线状态")) {
                                boxstate = bstate.getState_value();
                            }
                        }
                        bo.setStatus(boxstate);

                        groupbs.add(bo);

                    }

                }

                boxdetail.put("depname", depname);
                boxdetail.put("boxinfo", groupbs);
                ls.add(boxdetail);
            }

        }

        map.put("errorcode", "100"); // errorcode
        map.put("errorinfo", "");
        map.put("date", getDate());
        map.put("results", ls);
        return map;
    }

    // 按部门id获取光交箱信息
    @RequestMapping(value = "/boxinfobydep", method = RequestMethod.GET)
    public @ResponseBody Map getBoxInfobydep(int depid) {

        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> depart = new HashMap<String, Object>();

        String boxstate = "";

        List<BoxInfo> bs = boxInfoService.findbydep(depid);
        List<BoxInfoGroupbyDep> groupbs = new ArrayList<BoxInfoGroupbyDep>();

        for (BoxInfo b : bs) {

            BoxInfoGroupbyDep bo = new BoxInfoGroupbyDep();
            bo.setId(b.getId());
            if (b.getController_id() != null)
                bo.setController_id(b.getController_id());
            bo.setBox_no(b.getBox_no());
            bo.setDepartmentid(b.getDepartment().getId());
            bo.setDepartmentname(b.getDepartment().getName());
            bo.setAddress(b.getAddress());
            if (b.getBoxVarInfo() != null && b.getBoxVarInfo().getLast_heard() != null)
                bo.setLast_heard(formatdate(b.getBoxVarInfo().getLast_heard()));
            for (BoxStates bstate : b.getBoxStates()) {

                if (bstate.getState_key().equals("在线状态")) {
                    boxstate = bstate.getState_value();
                }
            }
            bo.setStatus(boxstate);

            groupbs.add(bo);

        }

        map.put("errorcode", "100"); // errorcode
        map.put("errorinfo", "");
        map.put("date", getDate());
        map.put("results", groupbs);
        return map;

    }

    // 按关注获取光交箱信息，返回最新的状态
    @RequestMapping(value = "/boxinfobyfocus", method = RequestMethod.GET)
    public @ResponseBody Map getBoxInfobyfocus(String ids) {

        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> depart = new HashMap<String, Object>();
        String boxstate = "";

        List<BoxInfoGroupbyDep> groupbs = new ArrayList<BoxInfoGroupbyDep>();
        String errorcode = "100";
        String errorinfo = "";

        if (ids != null && !ids.equals("")) {
            String idlst[] = ids.split(",");

            for (int i = 0; i < idlst.length; i++) {

                int id = Integer.parseInt(idlst[i]);
                BoxInfo b = boxInfoService.find(id);

                if (b != null) {
                    BoxInfoGroupbyDep bo = new BoxInfoGroupbyDep();
                    bo.setId(b.getId());
                    if (b.getController_id() != null)
                        bo.setController_id(b.getController_id());
                    bo.setBox_no(b.getBox_no());
                    bo.setDepartmentid(b.getDepartment().getId());
                    bo.setDepartmentname(b.getDepartment().getName());
                    bo.setAddress(b.getAddress());
                    if (b.getBoxVarInfo() != null && b.getBoxVarInfo().getLast_heard() != null)
                        bo.setLast_heard(formatdate(b.getBoxVarInfo().getLast_heard()));

                    if (b.getBoxStates() != null) {
                        for (BoxStates bstate : b.getBoxStates()) {

                            if (bstate.getState_key().equals("在线状态")) {
                                boxstate = bstate.getState_value();
                            }
                        }
                    }
                    bo.setStatus(boxstate);

                    groupbs.add(bo);
                }

            }

        } else {

            map.put("errorcode", "100"); // errorcode
            map.put("errorinfo", "");
            map.put("date", getDate());
            map.put("results", null);
            return map;

        }

        map.put("errorcode", "100"); // errorcode
        map.put("errorinfo", "");
        map.put("date", getDate());
        map.put("results", groupbs);
        return map;

    }

    // 获取指定光交箱详细信息
    @RequestMapping(value = "/boxinfo/listdetail", method = RequestMethod.GET)
    public @ResponseBody Map getDetailBoxInfo(Integer boxid) {
        System.out.println("BoxInfoController.getDetailBoxInfo()");
        BoxInfo bs = new BoxInfo();

        BoxInfoDetail bo = new BoxInfoDetail();
        String errorcode = "100";
        String errorinfo = "";
        try {

            if (boxid == null) {

                errorcode = "104";
                errorinfo = "请务必填写光交箱id";

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorcode", errorcode); // errorcode
                map.put("errorinfo", errorinfo);
                map.put("date", getDate());
                map.put("results", boxid);
                return map;
            }

            bs = boxInfoService.find(boxid);

            if (bs == null) {

                errorcode = "104";
                errorinfo = "此光交箱不存在";

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorcode", errorcode); // errorcode
                map.put("errorinfo", errorinfo);
                map.put("date", getDate());
                map.put("results", boxid);
                return map;

            }

            String connStates = ""; // 在线状态
            String doorStates = ""; // 门状态
            String volStates = ""; // 电量状态
            String boxStates = ""; // 箱体状态
            String waterStates = ""; // 浸水状态
            String tempStates = ""; // 温度状态

            bo.setId(bs.getId());
            bo.setBox_no(bs.getBox_no());

            if (bs.getBox_type() != null && !bs.getBox_type().equals(""))
                bo.setBox_type(bs.getBox_type());

            if (bs.getSim_phone_no() != null && !bs.getSim_phone_no().equals(""))
                bo.setSim_phone_no(bs.getSim_phone_no());
            bo.setDepartment(bs.getDepartment().getName());
            bo.setAddress(bs.getAddress());
            bo.setController_id(bs.getController_id());
            bo.setLocks_count(bs.getLocks_count());

            /*
             * if (bs.getBoxMeasures()!=null && bs.getBoxMeasures().getDbm()!=null)
             * bo.setDbm(bs.getBoxMeasures().getDbm());
             */
            // {"date":"2019-05-29
            // 14:09:02","errorinfo":"","results":{"address":"镇江","id":869,"box_no":"测试光交箱1","controller_id":150000,
            // "department":"江苏崇道","sim_phone_no":null,"longitude":0.0,"latitude":0.0,"box_type":null,"k_code":null,"last_heard":null,
            // "boxStates":"","locks_count":0,"use_state":null,"dbm":null,"installDate":null,"connStates":"","doorStates":"","volStates":"",
            // "waterStates":"","tempStates":"","lockinfo":[]},"errorcode":"104"}

            if (bs.getBoxVarInfo() != null && bs.getBoxVarInfo().getLast_heard() != null)
                bo.setLast_heard(formatdate(bs.getBoxVarInfo().getLast_heard()));

            // 获取锁列表
            List<LockModify> locklst = lockandkeyService.findLockInfo(bs.getId());
            if (locklst != null && !locklst.isEmpty()) {
                List<LockSimpleInfo> locksimplelst = new ArrayList<LockSimpleInfo>();
                for (LockModify lo : locklst) {
                    LockSimpleInfo b = new LockSimpleInfo();
                    b.setNumber(lo.getLock_code());
                    b.setType(lockandkeyService.findLockInfoByType(lo.getType_id()));
                    b.setBox_id(lo.getBox_id());
                    locksimplelst.add(b);
                }
                bo.setLockinfo(locksimplelst); // LockSimpleInfo
            }

            if (bs.getBoxStates() != null) {
                for (BoxStates bstate : bs.getBoxStates()) {

                    if (bstate.getState_key().equals("在线状态")) {
                        connStates = bstate.getState_value();
                    }
                    if (bstate.getState_key().equals("门状态")) {
                        doorStates = bstate.getState_value();
                    }
                    if (bstate.getState_key().equals("电量状态")) {
                        volStates = bstate.getState_value();
                    }
                    if (bstate.getState_key().equals("箱体状态")) {
                        boxStates = bstate.getState_value();
                    }
                    if (bstate.getState_key().equals("浸水状态")) {
                        waterStates = bstate.getState_value();
                    }
                    if (bstate.getState_key().equals("温度状态")) {
                        tempStates = bstate.getState_value();
                    }
                }
            }

            bo.setConnStates(connStates);
            bo.setDoorStates(doorStates);
            bo.setVolStates(volStates);
            bo.setBoxStates(boxStates);
            bo.setWaterStates(waterStates);
            bo.setTempStates(tempStates);
            if (bs.getLatitude() != null) {
                bo.setLatitude(bs.getLatitude());
            }
            if (bs.getLongitude() != null) {
                bo.setLongitude(bs.getLongitude());
            }
            bo.setK_code(bs.getK_code());

            String usestate = "";
            int status = bs.getUse_state();
            switch (status) {
            case 1:
                usestate = "未安装";
                break;
            case 2:
                usestate = "安装调试";
                break;
            case 3:
                usestate = "运行";
                break;
            default:
                usestate = "未知";
                break;
            }

            bo.setUse_state(usestate);

        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            errorcode = "104";
            errorinfo = ""; // ex.toString();

        } finally {

        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorcode", errorcode); // errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", bo);
        return map;

    }

    // 查询光交箱信息
    @RequestMapping(value = "/boxinfo/listsimple", method = RequestMethod.GET)
    public @ResponseBody Map getSimpleBoxInfo(String param, HttpServletRequest request) {
        String errorcode = "100";
        String errorinfo = "";

        String username = "";
        username = request.getHeader("username");

        String grantBoxid = getBoxidlst(request);

        if (param == null || param.equals("")) {

            errorcode = "104";
            errorinfo = "查询条件不能为空";

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("errorcode", errorcode); // errorcode
            map.put("errorinfo", errorinfo);
            map.put("date", getDate());
            map.put("results", null);
            return map;

        }

        String boxparam = "";
        try {
            boxparam = new String(param.getBytes("iso8859-1"), "utf-8"); // 解决乱码问题
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> depart = new HashMap<String, Object>();

        String boxstate = "";

        List<BoxInfo> bs = new ArrayList<BoxInfo>();

        if (username.equals("admin")) {

            bs = boxInfoService.findAllSimple(boxparam);

        } else {
            bs = boxInfoService.findAllSimple(boxparam, grantBoxid);

        }
        List<BoxInfoGroupbyDep> groupbs = new ArrayList<BoxInfoGroupbyDep>();

        for (BoxInfo b : bs) {

            BoxInfoGroupbyDep bo = new BoxInfoGroupbyDep();
            bo.setId(b.getId());
            if (b.getController_id() != null)
                bo.setController_id(b.getController_id());
            bo.setBox_no(b.getBox_no());
            bo.setDepartmentid(b.getDepartment().getId());
            bo.setDepartmentname(b.getDepartment().getName());
            bo.setAddress(b.getAddress());
            if (b.getBoxVarInfo() != null && b.getBoxVarInfo().getLast_heard() != null)
                bo.setLast_heard(formatdate(b.getBoxVarInfo().getLast_heard()));

            for (BoxStates bstate : b.getBoxStates()) {

                if (bstate.getState_key().equals("在线状态")) {
                    boxstate = bstate.getState_value();
                }
            }
            bo.setStatus(boxstate);

            groupbs.add(bo);

        }

        map.put("errorcode", "100"); // errorcode
        map.put("errorinfo", "");
        map.put("date", getDate());
        map.put("results", groupbs);
        return map;

    }

    // 获取光交箱告警状态信息
    @RequestMapping(value = "/warntypelist", method = RequestMethod.GET)
    public @ResponseBody Map getWarnTypelist(HttpServletRequest request) {

        String username = "";
        username = request.getHeader("username");

        String grantBoxid = getBoxidlst(request);

        List<BoxInfo> bs = new ArrayList<BoxInfo>();

        List<AlertTypeList> alst = new ArrayList<AlertTypeList>();

        BoxInfoDetail bo = new BoxInfoDetail();

        String errorcode = "100";
        String errorinfo = "";
        try {
            if (username.equals("admin"))
                bs = boxInfoService.findAll();
            else
                bs = boxInfoService.findAllByGrant(grantBoxid);

            if (bs == null) {

                errorcode = "100";
                errorinfo = " 无法获取状态信息";

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorcode", errorcode); // errorcode
                map.put("errorinfo", errorinfo);
                map.put("date", getDate());
                map.put("results", null);
                return map;

            }

            /*
             * 返回告警列表 正常打开 非法打开 箱门未关 箱体倾斜 有浸水 电量过低 温度过低 温度过高 离线 未安装
             *
             *
             */

            int normalopencount = 0;
            int illegalopencount = 0;
            int notclosecount = 0;
            int leancount = 0;
            int watercount = 0;
            int lowcount = 0;
            int templowcount = 0;
            int temphighcount = 0;
            int offlinecount = 0;
            int notsetcount = 0;

            // List<BoxInfoGroupbyDep>

            List<BoxInfoGroupbyDep> normallist = new ArrayList<BoxInfoGroupbyDep>();
            List<BoxInfoGroupbyDep> illegalopenlist = new ArrayList<BoxInfoGroupbyDep>();
            List<BoxInfoGroupbyDep> notcloselist = new ArrayList<BoxInfoGroupbyDep>();
            List<BoxInfoGroupbyDep> leanlist = new ArrayList<BoxInfoGroupbyDep>();
            List<BoxInfoGroupbyDep> waterlist = new ArrayList<BoxInfoGroupbyDep>();
            List<BoxInfoGroupbyDep> lowlist = new ArrayList<BoxInfoGroupbyDep>();
            List<BoxInfoGroupbyDep> templowlist = new ArrayList<BoxInfoGroupbyDep>();
            List<BoxInfoGroupbyDep> temphighlist = new ArrayList<BoxInfoGroupbyDep>();
            List<BoxInfoGroupbyDep> offlinelist = new ArrayList<BoxInfoGroupbyDep>();
            List<BoxInfoGroupbyDep> notsetlist = new ArrayList<BoxInfoGroupbyDep>();

            for (BoxInfo bi : bs) {

                for (BoxStates bstate : bi.getBoxStates()) {

                    if (bstate.getState_key().equals("在线状态") && bstate.getState_value().equals("未安装")) {
                        notsetcount++;

                        BoxInfoGroupbyDep bd = new BoxInfoGroupbyDep();
                        bd.setId(bi.getId());
                        if (bi.getController_id() != null)
                            bd.setController_id(bi.getController_id());
                        bd.setBox_no(bi.getBox_no());
                        bd.setDepartmentid(bi.getDepartment().getId());
                        bd.setDepartmentname(bi.getDepartment().getName());
                        bd.setAddress(bi.getAddress());
                        if (bi.getBoxVarInfo() != null && bi.getBoxVarInfo().getLast_heard() != null
                                && !bi.getBoxVarInfo().getLast_heard().equals(""))
                            bd.setLast_heard(formatdate(bi.getBoxVarInfo().getLast_heard()));
                        bd.setStatus("未安装");

                        notsetlist.add(bd);

                    }
                    if (bstate.getState_key().equals("在线状态") && bstate.getState_value().equals("离线")) {
                        offlinecount++;

                        BoxInfoGroupbyDep bd = new BoxInfoGroupbyDep();
                        bd.setId(bi.getId());
                        if (bi.getController_id() != null)
                            bd.setController_id(bi.getController_id());
                        bd.setBox_no(bi.getBox_no());
                        bd.setDepartmentid(bi.getDepartment().getId());
                        bd.setDepartmentname(bi.getDepartment().getName());
                        bd.setAddress(bi.getAddress());
                        if (bi.getBoxVarInfo() != null && bi.getBoxVarInfo().getLast_heard() != null
                                && !bi.getBoxVarInfo().getLast_heard().equals(""))
                            bd.setLast_heard(formatdate(bi.getBoxVarInfo().getLast_heard()));
                        bd.setStatus("离线");

                        offlinelist.add(bd);
                    }
                    if (bstate.getState_key().equals("门状态") && bstate.getState_value().equals("正常打开")) {
                        normalopencount++;

                        BoxInfoGroupbyDep bd = new BoxInfoGroupbyDep();
                        bd.setId(bi.getId());
                        bd.setBox_no(bi.getBox_no());
                        bd.setDepartmentid(bi.getDepartment().getId());
                        bd.setDepartmentname(bi.getDepartment().getName());
                        bd.setAddress(bi.getAddress());
                        if (bi.getBoxVarInfo() != null && bi.getBoxVarInfo().getLast_heard() != null
                                && !bi.getBoxVarInfo().getLast_heard().equals(""))
                            bd.setLast_heard(formatdate(bi.getBoxVarInfo().getLast_heard()));
                        bd.setStatus("正常打开");

                        normallist.add(bd);
                    }
                    if (bstate.getState_key().equals("门状态") && bstate.getState_value().equals("非法打开")) {
                        illegalopencount++;

                        BoxInfoGroupbyDep bd = new BoxInfoGroupbyDep();
                        bd.setId(bi.getId());
                        if (bi.getController_id() != null)
                            bd.setController_id(bi.getController_id());
                        bd.setBox_no(bi.getBox_no());
                        bd.setDepartmentid(bi.getDepartment().getId());
                        bd.setDepartmentname(bi.getDepartment().getName());
                        bd.setAddress(bi.getAddress());
                        if (bi.getBoxVarInfo() != null && bi.getBoxVarInfo().getLast_heard() != null
                                && !bi.getBoxVarInfo().getLast_heard().equals(""))
                            bd.setLast_heard(formatdate(bi.getBoxVarInfo().getLast_heard()));
                        bd.setStatus("非法打开");

                        illegalopenlist.add(bd);
                    }
                    if (bstate.getState_key().equals("门状态") && bstate.getState_value().equals("箱门未关")) {
                        notclosecount++;

                        BoxInfoGroupbyDep bd = new BoxInfoGroupbyDep();
                        bd.setId(bi.getId());
                        if (bi.getController_id() != null)
                            bd.setController_id(bi.getController_id());
                        bd.setBox_no(bi.getBox_no());
                        bd.setDepartmentid(bi.getDepartment().getId());
                        bd.setDepartmentname(bi.getDepartment().getName());
                        bd.setAddress(bi.getAddress());
                        if (bi.getBoxVarInfo() != null && bi.getBoxVarInfo().getLast_heard() != null
                                && !bi.getBoxVarInfo().getLast_heard().equals(""))
                            bd.setLast_heard(formatdate(bi.getBoxVarInfo().getLast_heard()));
                        bd.setStatus("箱门未关");

                        notcloselist.add(bd);
                    }
                    if (bstate.getState_key().equals("电量状态") && bstate.getState_value().equals("电量过低")) {
                        lowcount++;

                        BoxInfoGroupbyDep bd = new BoxInfoGroupbyDep();
                        bd.setId(bi.getId());
                        if (bi.getController_id() != null)
                            bd.setController_id(bi.getController_id());
                        bd.setBox_no(bi.getBox_no());
                        bd.setDepartmentid(bi.getDepartment().getId());
                        bd.setDepartmentname(bi.getDepartment().getName());
                        bd.setAddress(bi.getAddress());
                        if (bi.getBoxVarInfo() != null && bi.getBoxVarInfo().getLast_heard() != null
                                && !bi.getBoxVarInfo().getLast_heard().equals(""))
                            bd.setLast_heard(formatdate(bi.getBoxVarInfo().getLast_heard()));
                        bd.setStatus("电量过低");

                        lowlist.add(bd);
                    }
                    if (bstate.getState_key().equals("箱体状态") && bstate.getState_value().equals("箱体倾斜")) {
                        leancount++;

                        BoxInfoGroupbyDep bd = new BoxInfoGroupbyDep();
                        bd.setId(bi.getId());
                        bd.setBox_no(bi.getBox_no());
                        bd.setDepartmentid(bi.getDepartment().getId());
                        bd.setDepartmentname(bi.getDepartment().getName());
                        bd.setAddress(bi.getAddress());
                        if (bi.getBoxVarInfo() != null && bi.getBoxVarInfo().getLast_heard() != null
                                && !bi.getBoxVarInfo().getLast_heard().equals(""))
                            bd.setLast_heard(formatdate(bi.getBoxVarInfo().getLast_heard()));
                        bd.setStatus("箱体倾斜");

                        leanlist.add(bd);
                    }
                    if (bstate.getState_key().equals("浸水状态") && bstate.getState_value().equals("有浸水")) {
                        watercount++;

                        BoxInfoGroupbyDep bd = new BoxInfoGroupbyDep();
                        bd.setId(bi.getId());
                        if (bi.getController_id() != null)
                            bd.setController_id(bi.getController_id());
                        bd.setBox_no(bi.getBox_no());
                        bd.setDepartmentid(bi.getDepartment().getId());
                        bd.setDepartmentname(bi.getDepartment().getName());
                        bd.setAddress(bi.getAddress());
                        if (bi.getBoxVarInfo() != null && bi.getBoxVarInfo().getLast_heard() != null
                                && !bi.getBoxVarInfo().getLast_heard().equals(""))
                            bd.setLast_heard(formatdate(bi.getBoxVarInfo().getLast_heard()));
                        bd.setStatus("有浸水");

                        waterlist.add(bd);
                    }
                    if (bstate.getState_key().equals("温度状态") && bstate.getState_value().equals("温度过低")) {
                        templowcount++;

                        BoxInfoGroupbyDep bd = new BoxInfoGroupbyDep();
                        bd.setId(bi.getId());
                        if (bi.getController_id() != null)
                            bd.setController_id(bi.getController_id());
                        bd.setBox_no(bi.getBox_no());
                        bd.setDepartmentid(bi.getDepartment().getId());
                        bd.setDepartmentname(bi.getDepartment().getName());
                        bd.setAddress(bi.getAddress());
                        if (bi.getBoxVarInfo() != null && bi.getBoxVarInfo().getLast_heard() != null
                                && !bi.getBoxVarInfo().getLast_heard().equals(""))
                            bd.setLast_heard(formatdate(bi.getBoxVarInfo().getLast_heard()));
                        bd.setStatus("温度过低");

                        templowlist.add(bd);
                    }

                    if (bstate.getState_key().equals("温度状态") && bstate.getState_value().equals("温度过高")) {
                        temphighcount++;

                        BoxInfoGroupbyDep bd = new BoxInfoGroupbyDep();
                        bd.setId(bi.getId());
                        if (bi.getController_id() != null)
                            bd.setController_id(bi.getController_id());
                        bd.setBox_no(bi.getBox_no());
                        bd.setDepartmentid(bi.getDepartment().getId());
                        bd.setDepartmentname(bi.getDepartment().getName());
                        bd.setAddress(bi.getAddress());
                        if (bi.getBoxVarInfo() != null && bi.getBoxVarInfo().getLast_heard() != null
                                && !bi.getBoxVarInfo().getLast_heard().equals(""))
                            bd.setLast_heard(formatdate(bi.getBoxVarInfo().getLast_heard()));
                        bd.setStatus("温度过高");

                        temphighlist.add(bd);
                    }
                }

            }

            // 正常打开
            AlertTypeList atnormallist = new AlertTypeList();
            atnormallist.setCount(normalopencount);
            atnormallist.setBoxlist(normallist);
            atnormallist.setTypename("正常打开");

            // 非法打开
            AlertTypeList atillegalopenlist = new AlertTypeList();
            atillegalopenlist.setCount(illegalopencount);
            atillegalopenlist.setBoxlist(illegalopenlist);
            atillegalopenlist.setTypename("非法打开");

            // 箱门未关
            AlertTypeList atnotcloselist = new AlertTypeList();
            atnotcloselist.setCount(notclosecount);
            atnotcloselist.setBoxlist(notcloselist);
            atnotcloselist.setTypename("箱门未关");

            // 倾斜
            AlertTypeList atleanlist = new AlertTypeList();
            atleanlist.setCount(leancount);
            atleanlist.setBoxlist(leanlist);
            atleanlist.setTypename("倾斜");

            // 水浸
            AlertTypeList atwaterlist = new AlertTypeList();
            atwaterlist.setCount(watercount);
            atwaterlist.setBoxlist(waterlist);
            atwaterlist.setTypename("水浸");

            // 低电量
            AlertTypeList atlowlist = new AlertTypeList();
            atlowlist.setCount(lowcount);
            atlowlist.setBoxlist(lowlist);
            atlowlist.setTypename("低电量");

            // 箱门未关
            AlertTypeList attemplowlist = new AlertTypeList();
            attemplowlist.setCount(templowcount);
            attemplowlist.setBoxlist(templowlist);
            attemplowlist.setTypename("温度过低");

            // 箱门未关
            AlertTypeList attemphighlist = new AlertTypeList();
            attemphighlist.setCount(temphighcount);
            attemphighlist.setBoxlist(temphighlist);
            attemphighlist.setTypename("温度过高");

            // 未安装
            AlertTypeList atofflinelist = new AlertTypeList();
            atofflinelist.setCount(notsetcount);
            atofflinelist.setBoxlist(notsetlist);
            atofflinelist.setTypename("未安装");

            // 离线
            AlertTypeList atnotsetlist = new AlertTypeList();
            atnotsetlist.setCount(offlinecount);
            atnotsetlist.setBoxlist(offlinelist);
            atnotsetlist.setTypename("离线");

            alst.add(atnormallist);
            alst.add(atillegalopenlist);
            alst.add(atnotcloselist);
            alst.add(atleanlist);
            alst.add(atwaterlist);
            alst.add(atlowlist);
            alst.add(attemplowlist);
            alst.add(attemphighlist);
            alst.add(atofflinelist);
            alst.add(atnotsetlist);

        } catch (Exception ex) {
            errorcode = "104";
            errorinfo = ""; // ex.toString();

        } finally {

        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorcode", errorcode); // errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", alst);
        return map;

    }

    // 上传光交箱图片
    @RequestMapping(value = "/boxinfo/uploadimage", method = RequestMethod.POST)
    public @ResponseBody Map upload(@RequestParam("file") CommonsMultipartFile mFile,
            @RequestParam("boxid") String boxid, @RequestParam(value = "remark", required = false) String remark,
            @RequestParam("pictureTime") String pictureTime, HttpServletRequest request) {

        String errorcode = "100";
        String errorinfo = "";

        System.out.println("remark==================" + remark);
        if (!mFile.isEmpty()) {

            try {

                if (boxid.equals("") || boxid.isEmpty()) {

                    errorcode = "104";
                    errorinfo = "请务必填写光交箱id";

                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("errorcode", errorcode); // errorcode
                    map.put("errorinfo", errorinfo);
                    map.put("date", getDate());
                    map.put("results", null);
                    return map;
                }

                if (pictureTime.equals("") || pictureTime.isEmpty()) {

                    errorcode = "104";
                    errorinfo = "请务必发送拍照时间";

                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("errorcode", errorcode); // errorcode
                    map.put("errorinfo", errorinfo);
                    map.put("date", getDate());
                    map.put("results", null);
                    return map;
                }

                // 新建文件
                String realpath = request.getSession().getServletContext().getRealPath("/");

                String webpath = realpath.substring(0, realpath.lastIndexOf("newinterlockwebservice"));
                String uploadpath = webpath + "/" + "gjx_uploadfile";
                // 判断文件夹是否存在 不存在就新建
                File file = new File(uploadpath);
                // 判断文件夹是否存在,如果不存在则创建文件夹
                if (!file.exists()) {
                    file.mkdir();
                }

                String imagename = new Date().getTime() + mFile.getOriginalFilename();
                FileOutputStream os = new FileOutputStream(uploadpath + "/" + imagename);
                InputStream in = mFile.getInputStream();
                int b = 0;
                while ((b = in.read()) != -1) {
                    os.write(b);
                }
                os.flush();
                os.close();
                in.close();

                int id = Integer.parseInt(boxid);

                // 更新数据库
                BoxImages boximage = new BoxImages();
                boximage.setBox_id(id);
                boximage.setImage_path(imagename);
                boximage.setUpdatetime(pictureTime);
                boximage.setTimestamp(getDate());
                boximage.setRemarks(remark);
                boxInfoService.addboximage(boximage);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                errorcode = "104";
                // errorinfo=e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                errorcode = "104";
                // errorinfo=e.toString();
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorcode", errorcode); // errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", null);
        return map;

    }

    // 上传光交箱图片
    @RequestMapping(value = "/boxinfo/test/{boxid}", method = RequestMethod.POST)
    public @ResponseBody Map test(@PathVariable String boxid, HttpServletRequest request) {
        String pictureTime = "";
        String errorcode = "100";
        String errorinfo = "";

        System.out.println("boxid===" + boxid);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorcode", errorcode); // errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", null);
        return map;

    }

    static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
    static BASE64Decoder decoder = new sun.misc.BASE64Decoder();

    // 下载光交箱图片 返回路径
    @RequestMapping(value = "/boxinfo/downloadimage", method = RequestMethod.GET)
    public @ResponseBody Map download(Integer boxid, String pictureTime, Integer num, HttpServletRequest request) {

        String errorcode = "100";
        String errorinfo = "";
        String findsql = "";

        System.out.println("pictureTime---->" + pictureTime);

        String realpath = request.getSession().getServletContext().getRealPath("/");

        String webpath = realpath.substring(0, realpath.lastIndexOf("newinterlockwebservice"));
        String uploadpath = webpath + "/" + "gjx_uploadfile";

        if (boxid == null) {

            errorcode = "104";
            errorinfo = "请务必填写光交箱id";

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("errorcode", errorcode); // errorcode
            map.put("errorinfo", errorinfo);
            map.put("date", getDate());
            map.put("results", null);
            return map;
        }

        List<BoxImages> imagelist = new ArrayList<BoxImages>();
        try {

            // 若填写了拍照日期，则將该日期下所有照片返回 只取年月日
            if (pictureTime != null && !pictureTime.equals("")) {

                imagelist = boxInfoService.findimagebyTime(boxid, pictureTime);

            } else if (num != 0) {

                imagelist = boxInfoService.findimagebyNum(boxid, num);

            }

        } catch (Exception ex) {

            errorcode = "104";

        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorcode", errorcode); // errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", imagelist);
        return map;

    }

    // 返回光交箱照片拍照日期 及个数
    @RequestMapping(value = "/boxinfo/imagelist", method = RequestMethod.GET)
    public @ResponseBody Map imagelist(int boxid) throws ParseException {

        String errorcode = "100";
        String errorinfo = "";
        String findsql = "";

        if (boxid == 0) {

            errorcode = "104";
            errorinfo = "请务必填写光交箱id";

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("errorcode", errorcode); // errorcode
            map.put("errorinfo", errorinfo);
            map.put("date", getDate());
            map.put("results", null);
            return map;

        }

        List<BoxImages> imagelist = boxInfoService.findimagebyId(boxid);

        Map<String, Object> picturetime = new HashMap<String, Object>();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        for (BoxImages im : imagelist) {

            String ss = formatStandardDate(format.parse(im.getUpdatetime()));

            picturetime.put(ss, ""); // 按拍照时间（年月日 去重）

        }

        List<ImagesInfo> ls = new ArrayList<ImagesInfo>();

        for (String picdate : picturetime.keySet()) {

            ImagesInfo bo = new ImagesInfo();
            int id = 0;
            int count = 0;

            Map<String, Object> boxdetail = new HashMap<String, Object>();

            for (BoxImages b : imagelist) {

                if (picdate.equals(formatStandardDate(format.parse(b.getUpdatetime())))) {

                    count++;

                }

            }

            bo.setCount(count);
            bo.setPicturetime(picdate);

            ls.add(bo);

        }

        System.out.println("picturetime----->" + picturetime.size());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorcode", errorcode); // errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", ls);
        return map;

    }

    static void base64StringToImage(String base64String) {
        try {
            byte[] bytes1 = decoder.decodeBuffer(base64String);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            BufferedImage bi1 = ImageIO.read(bais);
            File w2 = new File("d://2.png");// 可以是jpg,png,gif格式
            ImageIO.write(bi1, "jpg", w2);// 不管输出什么格式图片，此处不需改动
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 下载光交箱图片old
    @RequestMapping(value = "/boxinfo/downloadimageold", method = RequestMethod.GET)
    public ModelAndView downloadold(Integer boxid, String pictureTime, Integer num, HttpServletRequest request,
            HttpServletResponse response) {

        /*
         * System.out.println("fileName--->"+mFile.getOriginalFilename());
         * System.out.println("boxid--->"+boxid);
         * System.out.println("pictureTime--->"+pictureTime);
         */

        String errorcode = "100";
        String errorinfo = "";
        String findsql = "";
        System.out.println("pictureTime---->" + pictureTime);

        try {

            /*
             * if(boxid == null){
             * 
             * errorcode ="104"; errorinfo="请务必填写光交箱id";
             * 
             * Map<String, Object> map = new HashMap<String, Object>();
             * map.put("errorcode",errorcode); //errorcode map.put("errorinfo", errorinfo);
             * map.put("date", getDate()); map.put("results", ""); // return map;
             * 
             * }
             */
            String storeName = "ip.JPG";
            String realName = "ip.JPG";
            String contentType = "application/octet-stream";

            /*
             * downloadly(request, response, storeName, contentType, realName);
             */
            downloadly(request, response, "92006000097.JPG", contentType, "92006000097.JPG");

            if (!pictureTime.isEmpty()) { // 如果填写了日期

            } else {

            }

            BoxImages boximg = new BoxImages();

            // List<BoxImages> bs = boxInfoService.findImage(findsql);

            // 更新数据库

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            errorcode = "104";
            errorinfo = e.toString();
        }

        /*
         * Map<String, Object> map = new HashMap<String, Object>(); map.put("errorcode",
         * errorcode); //errorcode map.put("errorinfo", errorinfo); map.put("date",
         * getDate()); map.put("results", ""); return map;
         */

        return null;

    }

    public static void downloadly(HttpServletRequest request, HttpServletResponse response, String storeName,
            String contentType, String realName) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        String ctxPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/");
        String downLoadPath = ctxPath + "/" + storeName;

        long fileLength = new File(downLoadPath).length();

        response.setContentType(contentType);
        response.setHeader("Content-disposition",
                "attachment; filename=" + new String(realName.getBytes("utf-8"), "ISO8859-1"));
        response.setHeader("Content-Length", String.valueOf(fileLength));

        bis = new BufferedInputStream(new FileInputStream(downLoadPath));
        bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[2048];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        bis.close();
        bos.close();
    }

    // 修改光交箱施工状态
    @RequestMapping(value = "/boxinfo/updatestatus", method = RequestMethod.PUT)
    public @ResponseBody Map editBoxUseStatus(@RequestBody BoxInfo boxInfo) throws IOException {

        String errorcode = "100";
        String errorinfo = "";

        try {

            if (boxInfo.getId() == 0) {

                errorcode = "104";
                errorinfo = "请务必填写光交箱id";

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorcode", errorcode); // errorcode
                map.put("errorinfo", errorinfo);
                map.put("date", getDate());
                map.put("results", null);
                return map;
            }

            if (boxInfo.getUse_state() == 0) {

                errorcode = "104";
                errorinfo = "请务必填写施工状态";

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorcode", errorcode); // errorcode
                map.put("errorinfo", errorinfo);
                map.put("date", getDate());
                map.put("results", null);
                return map;
            }

            // 更新数据库
            boxInfoService.updateStatus(boxInfo);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            errorcode = "104";
            errorinfo = e.toString();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorcode", errorcode); // errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", boxInfo.getId());
        return map;
    }

    // 获取工单列表 返回简单信息
    @RequestMapping(value = "/workorderlist", method = RequestMethod.GET)
    public @ResponseBody Map listWorkOrderByAjax(int orderstatus, HttpServletRequest request) { // 工单状态 int orderstatus
        String errorcode = "100";
        String errorinfo = "";

        /*
         * if(orderstatus == null){
         * 
         * errorcode ="104"; errorinfo="请务必填写工单状态";
         * 
         * Map<String, Object> map = new HashMap<String, Object>();
         * map.put("errorcode",errorcode); //errorcode map.put("errorinfo", errorinfo);
         * map.put("date", getDate()); map.put("results", ""); return map; }
         */

        String username = "";
        username = request.getHeader("username");
        int userid = userService.findByName(username);
        int userdepid = userService.findDepByName(username);

        Map<String, Object> map = new HashMap<String, Object>();

        // List<WorkOrder> wo = boxInfoService.findAllWorkOrder();
        List<WorkOrderNew> wo = boxInfoService.findAllWorkOrderByType(orderstatus, userid, userdepid);
        List<WorkOrderSimple> wols = new ArrayList<WorkOrderSimple>();

        if (wo != null) {
            for (WorkOrderNew w : wo) {

                WorkOrderSimple bo = new WorkOrderSimple();
                bo.setId(w.getId());
                bo.setTitle(w.getTitle());
                if (w.getCreate_time() != null)
                    bo.setCreate_time(w.getCreate_time().toString());

                String worktype = "";
                int status = w.getType();
                switch (status) {
                case 1:
                    worktype = "资源配置";
                    break;
                case 2:
                    worktype = "告警";
                    break;
                case 3:
                    worktype = "其他";
                    break;
                case 4:
                    worktype = "维修申请";
                    break;
                default:
                    worktype = "未知";
                    break;

                }

                bo.setType(worktype);

                String create_type = "";
                int createstatus = w.getCreate_type();
                switch (createstatus) {
                case 1:
                    create_type = "服务器自动产生";
                    break;
                case 2:
                    create_type = "手工生成";
                    break;
                case 3:
                    create_type = "维修申请";
                    break;
                default:
                    create_type = "未知";
                    break;

                }

                bo.setCreate_type(create_type);

                wols.add(bo);

            }

        }

        map.put("errorcode", "100"); // errorcode
        map.put("errorinfo", "");
        map.put("date", getDate());
        map.put("results", wols);
        return map;

    }

    // 获取工单详细信息
    @RequestMapping(value = "/workorderdetail", method = RequestMethod.GET)
    public @ResponseBody Map listWorkOrderdetail(int id) { // 用时间做参数?

        WorkOrder bs = new WorkOrder();

        WorkOrderDetail wod = new WorkOrderDetail();

        String errorcode = "100";
        String errorinfo = "";

        try {

            bs = boxInfoService.findWorkOrderbyID(id);

            if (bs == null) {

                errorcode = "104";
                errorinfo = "此工单记录不存在";

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorcode", errorcode); // errorcode
                map.put("errorinfo", errorinfo);
                map.put("date", getDate());
                map.put("results", null);
                return map;

            }

            wod.setOrder_no(bs.getOrder_no());
            wod.setTitle(bs.getTitle());
            wod.setId(bs.getId());

            String worktype = "";
            int status = bs.getType();
            switch (status) {
            case 1:
                worktype = "资源配置";
                break;
            case 2:
                worktype = "告警";
                break;
            case 3:
                worktype = "其他";
                break;
            case 4:
                worktype = "维修申请";
                break;
            default:
                worktype = "未知";
                break;

            }

            wod.setType(worktype);
            if (bs.getCreate_user() != null)
                wod.setCreate_user(bs.getCreate_user().getFull_name());
            wod.setDepartment(bs.getDepartment().getName());
            if (bs.getReceive_operators() != null)
                wod.setReceive_operators(bs.getReceive_operators().getFull_name());
            if (bs.getReceive_time() != null)
                wod.setReceive_time(formatdate(bs.getReceive_time()));
            if (bs.getDone_time() != null)
                wod.setDone_time(formatdate(bs.getDone_time()));

            /*
             * String donetype = ""; int status2 = bs.getDone_type(); switch (status2) {
             * case 1: donetype = "未接收"; break; case 2: donetype = "未完成"; break; case 3:
             * donetype = "已完成"; break; default: donetype = "未知"; break;
             * 
             * }
             */

            wod.setDone_type(bs.getDone_type());
            wod.setContent(bs.getContent());

            String create_type = "";
            int createstatus = bs.getCreate_type();
            switch (createstatus) {
            case 1:
                create_type = "服务器自动产生";
                break;
            case 2:
                create_type = "手工生成";
                break;
            case 3:
                create_type = "维修申请";
                break;
            default:
                create_type = "未知";
                break;

            }

            wod.setCreate_type(create_type);
            if (bs.getCreate_time() != null)
                wod.setCreate_time(formatdate(bs.getCreate_time()));
            if (bs.getRespect_starttime() != null)
                wod.setRespect_starttime(formatdate(bs.getRespect_starttime()));
            if (bs.getRespect_endtime() != null)
                wod.setRespect_endtime(formatdate(bs.getRespect_endtime()));
            if (bs.getInfact_starttime() != null)
                wod.setInfact_starttime(formatdate(bs.getInfact_starttime()));
            if (bs.getInfact_endtime() != null)
                wod.setInfact_endtime(formatdate(bs.getInfact_endtime()));
            wod.setRemarks(bs.getRemarks());

            if (bs.getAls() != null) {

                Set<AlarmOrderDetails> als = bs.getAls();
                List<AlarmOrderDetailsNew> aln = new ArrayList<AlarmOrderDetailsNew>();

                for (AlarmOrderDetails aod : als) {
                    AlarmOrderDetailsNew tmp = new AlarmOrderDetailsNew();
                    tmp.setId(aod.getId());
                    tmp.setOrder_id(aod.getOrder().getId());
                    tmp.setAlarmevent_id(aod.getAlarm_event_id()); // 待注意
                    if (aod.getBoxinfo() != null) {
                        tmp.setBox_id(aod.getBoxinfo().getId());
                        tmp.setBox_no(aod.getBoxinfo().getBox_no());
                        tmp.setBox_address(aod.getBoxinfo().getAddress());
                        tmp.setAlarm_type(aod.getAlarm_type());
                    }
                    if (aod.getCreate_time() != null)
                        tmp.setCreate_time(formatdate(aod.getCreate_time()));

                    String dtype = "";
                    int dtstatus = aod.getDone_type();
                    switch (dtstatus) {
                    case 1:
                        dtype = "未完成";
                        break;
                    case 2:
                        dtype = "已完成";
                        break;
                    default:
                        dtype = "未知";
                        break;

                    }

                    tmp.setDone_type(dtype);
                    if (aod.getDone_time() != null)
                        tmp.setDone_time(formatdate(aod.getDone_time()));

                    aln.add(tmp);

                }

                wod.setAls(aln);
            }

            // 资源配置 -
            if (bs.getOps() != null) {

                boolean opflag = false; // 是否返回光缆纤芯数据 默认不返回 关联和直熔返回

                Set<OrderRespect> als = bs.getOps();
                List<OrderRespectDetail> ord = new ArrayList<OrderRespectDetail>();

                List<tiaoqian> tqlst = new ArrayList<tiaoqian>();

                List<guanlian> gllst = new ArrayList<guanlian>();

                List<zhirong> zrlst = new ArrayList<zhirong>();

                List<OpticalcableCore> opcorelst = new ArrayList<OpticalcableCore>();
                int boxid = 0;

                for (OrderRespect aod : als) {

                    if (aod.getOrder_type() == 2)// 跳纤
                    {

                        if (aod.getA_terminal_id() != null && !aod.getA_terminal_id().equals("")
                                && aod.getZ_terminal_id() != null && !aod.getZ_terminal_id().equals("")) { // 起始、终止都指定了端子
                            String begin = aod.getA_terminal_id();
                            String end = aod.getZ_terminal_id();
                            String[] beginlst = begin.split(",");
                            String[] endlst = end.split(",");

                            for (int i = 0; i < beginlst.length; i++) {
                                tiaoqian tq = new tiaoqian();
                                AtomType fromatom = new AtomType();
                                AtomType toatom = new AtomType();

                                fromatom = boxInfoService.transferTQ(Integer.parseInt(beginlst[i]));
                                toatom = boxInfoService.transferTQ(Integer.parseInt(endlst[i]));
                                boxid = aod.getBoxinfo().getId();
                                tq.setFrom(fromatom);
                                tq.setTo(toatom);
                                tq.setBox_id(boxid);
                                tq.setBox_no(aod.getBoxinfo().getBox_no());
                                tqlst.add(tq);

                            }

                        }

                    }

                    if (aod.getOrder_type() == 1)// 关联
                    {
                        opflag = true;

                        if (aod.getCore_id() != null && !aod.getCore_id().equals("") && aod.getTerminal_id() != null
                                && !aod.getTerminal_id().equals("")) {
                            String begin = aod.getTerminal_id();
                            String end = aod.getCore_id();
                            String[] beginlst = begin.split(",");
                            String[] endlst = end.split(",");

                            for (int i = 0; i < beginlst.length; i++) {
                                guanlian gl = new guanlian();
                                AtomType fromatom = new AtomType();
                                CoreAtomType toatom = new CoreAtomType();
                                fromatom = boxInfoService.transferTQ(Integer.parseInt(beginlst[i]));
                                toatom = boxInfoService.transferGL(Integer.parseInt(endlst[i]));
                                boxid = aod.getBoxinfo().getId();

                                gl.setFrom(fromatom);
                                gl.setTo(toatom);
                                gl.setBox_id(boxid);
                                gl.setBox_no(aod.getBoxinfo().getBox_no());
                                gllst.add(gl);

                            }

                        }

                    }

                    if (aod.getOrder_type() == 3) // 直熔
                    {
                        opflag = true; // 返回光缆纤芯数据

                        if (aod.getA_core_id() != null && !aod.getA_core_id().equals("") && aod.getZ_core_id() != null
                                && !aod.getZ_core_id().equals("")) {
                            String begin = aod.getA_core_id();
                            String end = aod.getZ_core_id();
                            String[] beginlst = begin.split(",");
                            String[] endlst = end.split(",");

                            for (int i = 0; i < beginlst.length; i++) {
                                zhirong zr = new zhirong();
                                CoreAtomType fromatom = new CoreAtomType();
                                CoreAtomType toatom = new CoreAtomType();

                                fromatom = boxInfoService.transferGL(Integer.parseInt(beginlst[i]));
                                toatom = boxInfoService.transferGL(Integer.parseInt(endlst[i]));
                                boxid = aod.getBoxinfo().getId();

                                zr.setFrom(fromatom);
                                zr.setTo(toatom);
                                zr.setBox_id(boxid);
                                zr.setBox_no(aod.getBoxinfo().getBox_no());
                                zrlst.add(zr);

                            }

                        }

                    }

                }

                if (opflag) {

                    // 如果是资源配置类型的工单关联and直熔需要返回相应的光缆纤芯数据
                    opcorelst = opticalcableService.findAllOPCoreByBoxId(boxid);
                }

                wod.setTq(tqlst);
                wod.setGl(gllst);
                wod.setZr(zrlst);
                wod.setOpcorelst(opcorelst);
                if (boxid != 0) {
                    wod.setBms(boxInfoService.findBoxModuleById(boxid));
                }

            }

        } catch (Exception ex) {
            errorcode = "104";
            errorinfo = ex.toString(); // ex.toString();

        } finally {

        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorcode", errorcode); // errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", wod);
        return map;

    }

    // 工单上传
    @RequestMapping(value = "/updateworkorder", method = RequestMethod.PUT)
    public @ResponseBody Map updateworkorder(@RequestBody WorkOrderDetail b) throws IOException {
        String errorcode = "100";
        String errorinfo = "";

        if (b.getId() == 0) {

            errorcode = "104";
            errorinfo = "请务必填写工单id";

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("errorcode", errorcode); // errorcode
            map.put("errorinfo", errorinfo);
            map.put("date", getDate());
            map.put("results", null);
            return map;
        }

        try {

            // 更新工单状态为已完成
            /*
             * WorkOrderDetail wo = new WorkOrderDetail(); wo.setDone_type(3); //已完成
             * wo.setDone_time(getDate()); //wo.setRemarks(b.getRemarks()); wo.
             */

            List<tiaoqian> tqlst = b.getTq();
            if (tqlst != null) { // 跳纤数据

                for (tiaoqian t : tqlst) {

                    AtomType fromatom = t.getFrom();
                    AtomType toatom = t.getTo();
                    int box_id = t.getBox_id();

                    int a_terminal_id = boxInfoService.transferTQtoInt(fromatom, box_id);
                    int z_terminal_id = boxInfoService.transferTQtoInt(toatom, box_id);

                    OrderInfactNew orinfo = new OrderInfactNew();

                    if (a_terminal_id != 0 && z_terminal_id != 0) {

                        orinfo.setA_terminal_id(a_terminal_id);
                        orinfo.setZ_terminal_id(z_terminal_id);
                        orinfo.setOrder_id(b.getId());
                        orinfo.setOrder_type(2); // 跳纤
                        orinfo.setBox_id(box_id);

                        // 跳纤数据已存在
                        boxInfoService.addorderinfact(orinfo); // 新增工单实际表

                    }

                }

            }

            List<guanlian> gllst = b.getGl();

            if (gllst != null) { // 关联数据

                for (guanlian g : gllst) {
                    AtomType fromatom = g.getFrom();
                    CoreAtomType toatom = g.getTo();
                    int core_idtemp = toatom.getCore_id();

                    int box_id = g.getBox_id();

                    int terminal_id = boxInfoService.transferTQtoInt(fromatom, box_id);
                    int core_id = core_idtemp;

                    OrderInfactNew orinfo = new OrderInfactNew();

                    if (core_id != 0 && terminal_id != 0) {

                        orinfo.setTerminal_id(terminal_id);
                        orinfo.setCore_id(core_id);
                        orinfo.setOrder_id(b.getId());
                        orinfo.setOrder_type(1); // 关联
                        orinfo.setBox_id(box_id);

                        boxInfoService.addorderinfact(orinfo); // 新增工单实际表

                    }

                }

                List<zhirong> zrlst = b.getZr();
                if (zrlst != null) { // 直熔数据

                    for (zhirong z : zrlst) {
                        CoreAtomType fromatom = z.getFrom();
                        CoreAtomType toatom = z.getTo();

                        int box_id = z.getBox_id();

                        int a_core_id = fromatom.getCore_id();
                        int z_core_id = toatom.getCore_id();

                        OrderInfactNew orinfo = new OrderInfactNew();

                        if (a_core_id != 0 && z_core_id != 0) {

                            orinfo.setA_core_id(a_core_id);
                            orinfo.setZ_core_id(z_core_id);
                            orinfo.setOrder_id(b.getId());
                            orinfo.setOrder_type(3); // 直熔
                            orinfo.setBox_id(box_id);

                            boxInfoService.addorderinfact(orinfo); // 新增工单实际表

                        }

                    }

                }

            }

        } catch (Exception ex) {
            errorcode = "104";
            errorinfo = ex.toString();
            System.out.println(ex.toString());
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorcode", errorcode); // errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", null);
        return map;
    }

    // 修改工单状态
    @RequestMapping(value = "/workorder/updatestatus", method = RequestMethod.PUT)
    public @ResponseBody Map editworkorderStatus(@RequestBody WorkOrder woinfo) throws IOException {

        String errorcode = "100";
        String errorinfo = "";

        try {

            if (woinfo.getId() == 0) {

                errorcode = "104";
                errorinfo = "请务必填写工单id";

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorcode", errorcode); // errorcode
                map.put("errorinfo", errorinfo);
                map.put("date", getDate());
                map.put("results", "");
                return map;
            }

            if (woinfo.getDone_type() == 0) {

                errorcode = "104";
                errorinfo = "请务必填写工单状态";

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorcode", errorcode); // errorcode
                map.put("errorinfo", errorinfo);
                map.put("date", getDate());
                map.put("results", "");
                return map;
            }

            // 更新数据库
            boxInfoService.updateWordOrderStatus(woinfo);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            errorcode = "104";
            errorinfo = e.toString();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorcode", errorcode); // errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", null);
        return map;
    }

    // 修改工单告警的状态
    @RequestMapping(value = "/workorder/updatealarmstatus", method = RequestMethod.PUT)
    public @ResponseBody Map editworkorderAlarmStatus(@RequestBody AlarmOrderDetails woinfo) throws IOException {

        String errorcode = "100";
        String errorinfo = "";

        try {

            if (woinfo.getId() == 0) {

                errorcode = "104";
                errorinfo = "请务必填写id";

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorcode", errorcode); // errorcode
                map.put("errorinfo", errorinfo);
                map.put("date", getDate());
                map.put("results", null);
                return map;
            }

            if (woinfo.getDone_type() == 0) {

                errorcode = "104";
                errorinfo = "请务必填写状态";

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorcode", errorcode); // errorcode
                map.put("errorinfo", errorinfo);
                map.put("date", getDate());
                map.put("results", null);
                return map;
            }

            if (woinfo.getDone_type() == 2) {
                // 如果状态为已完成

                int alarmid = woinfo.getId();
                int boxid = boxInfoService.getboxidbyAlarmStatus(alarmid);
                long controllerid = boxInfoService.getcontrolleridbyboxid(boxid);

                String alrmtype = boxInfoService.getTypebyAlarmStatus(alarmid);
                int statekeyid = boxInfoService.getStatekeyid(alarmid);
                String normaltype = "";
                if (alrmtype.equals("电量过低"))
                    normaltype = "电量正常";
                if (alrmtype.equals("离线"))
                    normaltype = "在线";
                if (alrmtype.equals("非法打开"))
                    normaltype = "门关闭";
                if (alrmtype.equals("箱门未关"))
                    normaltype = "门关闭";
                if (alrmtype.equals("箱体倾斜")) {
                    normaltype = "箱体正常";

                    // 箱体倾斜除了更新状态外 还需要更新cabinet表中的值 这样下次服务器收到包后 会將倾斜值初始化
                    boxInfoService.updatecabinet(controllerid);

                }
                if (alrmtype.equals("箱体震动"))
                    normaltype = "箱体正常";
                if (alrmtype.equals("有浸水"))
                    normaltype = "无浸水";
                if (alrmtype.equals("温度过低"))
                    normaltype = "温度正常";
                if (alrmtype.equals("温度过高"))
                    normaltype = "温度正常";

                // 更新数据库
                boxInfoService.updateBoxState(boxid, normaltype, statekeyid);

            }
            // 更新数据库
            boxInfoService.updateWordOrderAlarmStatus(woinfo);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            errorcode = "104";
            errorinfo = e.toString();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorcode", errorcode); // errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", null);
        return map;
    }

    // 上传工单图片
    @RequestMapping(value = "/workorder/uploadimage", method = RequestMethod.POST)
    public @ResponseBody Map uploadworkorderimage(@RequestParam("file") CommonsMultipartFile mFile,
            @RequestParam("orderid") String orderid, @RequestParam(value = "remark", required = false) String remark,
            @RequestParam("pictureTime") String pictureTime, HttpServletRequest request) {

        String errorcode = "100";
        String errorinfo = "";

        System.out.println("remark==================" + remark);
        if (!mFile.isEmpty()) {

            try {

                if (orderid.equals("") || orderid.isEmpty()) {

                    errorcode = "104";
                    errorinfo = "请务必填写工单id";

                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("errorcode", errorcode); // errorcode
                    map.put("errorinfo", errorinfo);
                    map.put("date", getDate());
                    map.put("results", "");
                    return map;
                }

                if (pictureTime.equals("") || pictureTime.isEmpty()) {

                    errorcode = "104";
                    errorinfo = "请务必发送拍照时间";

                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("errorcode", errorcode); // errorcode
                    map.put("errorinfo", errorinfo);
                    map.put("date", getDate());
                    map.put("results", "");
                    return map;
                }

                // 新建文件
                String realpath = request.getSession().getServletContext().getRealPath("/");

                String webpath = realpath.substring(0, realpath.lastIndexOf("newinterlockwebservice"));
                String uploadpath = webpath + "/" + "gjx_uploadfile";
                // 判断文件夹是否存在 不存在就新建
                File file = new File(uploadpath);
                // 判断文件夹是否存在,如果不存在则创建文件夹
                if (!file.exists()) {
                    file.mkdir();
                }

                String imagename = new Date().getTime() + mFile.getOriginalFilename();
                FileOutputStream os = new FileOutputStream(uploadpath + "/" + imagename);
                InputStream in = mFile.getInputStream();
                int b = 0;
                while ((b = in.read()) != -1) {
                    os.write(b);
                }
                os.flush();
                os.close();
                in.close();

                int id = Integer.parseInt(orderid);

                // 更新数据库
                WorkOrderImage boximage = new WorkOrderImage();
                boximage.setOrder_id(id);
                boximage.setImage_path(imagename);
                boximage.setUpdatetime(pictureTime);
                boximage.setTimestamp(getDate());
                boximage.setRemarks(remark);
                boxInfoService.addworkorderimage(boximage);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                errorcode = "104";
                errorinfo = e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                errorcode = "104";
                errorinfo = e.toString();
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorcode", errorcode); // errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", "");
        return map;

    }

    // 光交箱面板图
    @RequestMapping(value = "/boxinfo/findBoxModulesAndTerminals")
    public @ResponseBody Map findBoxModulesAndTerminalsByAjax(int id) {

        String errorcode = "100";
        String errorinfo = "";

        if (id == 0) {

            errorcode = "104";
            errorinfo = "请务必填写光交箱id";

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("errorcode", errorcode); // errorcode
            map.put("errorinfo", errorinfo);
            map.put("date", getDate());
            map.put("results", "");
            return map;
        }

        ModulesAndTerminals mat = new ModulesAndTerminals();
        mat.setBms(this.boxInfoService.findBoxModuleByIdNew(id));
        mat.setBts(this.boxInfoService.findBoxTerminalById(id));

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorcode", "100"); // errorcode
        map.put("errorinfo", "");
        map.put("date", getDate());
        map.put("results", mat);
        return map;
    }

    // 光交箱面板图-----------新 支持小区光交箱
    @RequestMapping(value = "/boxinfo/findBoxModulesAndTerminalsNew")
    public @ResponseBody Map findBoxModulesAndTerminalsByAjaxNew(int id) {

        String errorcode = "100";
        String errorinfo = "";

        if (id == 0) {

            errorcode = "104";
            errorinfo = "请务必填写光交箱id";

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("errorcode", errorcode); // errorcode
            map.put("errorinfo", errorinfo);
            map.put("date", getDate());
            map.put("results", "");
            return map;
        }

        ModulesAndTerminalsForXQ mat = new ModulesAndTerminalsForXQ();
        mat.setBms(this.boxInfoService.findBoxModuleByIdNew(id));
        mat.setBts(this.boxInfoService.findBoxTerminalByIdNew(id));

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorcode", "100"); // errorcode
        map.put("errorinfo", "");
        map.put("date", getDate());
        map.put("results", mat);
        return map;
    }

    // 维修申请
    @RequestMapping(value = "/repairapp", method = RequestMethod.POST)
    synchronized public @ResponseBody Map newRepairapp(@RequestBody RepairRecordUpload b, HttpServletRequest request)
            throws IOException {
        String errorcode = "100";
        String errorinfo = "";

        String username = request.getHeader("username");

        if (b.getBox_id() == 0) {

            errorcode = "104";
            errorinfo = "请务必填写光交箱id";

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("errorcode", errorcode); // errorcode
            map.put("errorinfo", errorinfo);
            map.put("date", getDate());
            map.put("results", "");
            return map;
        }

        try {
            // 数据去重 避免重复提交
            if (!boxInfoService.checkreapairapp(userService.findByName(username), b.getBox_id())) {

                errorcode = "104";
                errorinfo = "该光交箱下已有尚未审批的维修申请，请通过审批后再提交新的申请";

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorcode", errorcode); // errorcode
                map.put("errorinfo", errorinfo);
                map.put("date", getDate());
                map.put("results", "");
                return map;

            }

            // 判断维修申请是自动审批还是人工审批
            SysConfig sc = this.boxEventAndWarnService.findSysConfig();
            int appflag = 1; // 1：审批 0：不审批
            if (sc != null)
                appflag = sc.getOrder_app();

            // 如果申请人 对同一个箱子有尚未审批的维修申请 或者生成了同种类型的工单 但是尚未完成 则不能创建新的维修申请
            /*
             * if( boxInfoService.checkapp(userService.findByName(username),b.getBox_id())){
             * 
             * errorcode ="104"; errorinfo="光交箱控制器号已存在";
             * 
             * Map<String, Object> map = new HashMap<String, Object>();
             * map.put("errorcode",errorcode); //errorcode map.put("errorinfo", errorinfo);
             * map.put("date", getDate()); map.put("results", ""); return map;
             * 
             * }
             */

            if (appflag == 1) // 如果设置为审批 则增加维修申请即可
            {
                RepairRecord rp = new RepairRecord();
                rp.setBox_id(b.getBox_id());
                rp.setUser_id(userService.findByName(username));
                rp.setDepartment_id(userService.findDepByName(username));

                rp.setReceiveuser_id(userService.findByName(username));
                rp.setReceivedepartment_id(userService.findDepByName(username));

                rp.setRepairtype(b.getRepairtype());
                rp.setRemark(b.getRemark());
                rp.setRespect_start_time(b.getRespect_start_time());
                rp.setRespect_end_time(b.getRespect_end_time());
                rp.setCreate_time(getDate());

                // 获取光交箱维护部门

                /*
                 * int recievedep = boxInfoService.getrecivedep(b.getBox_id()); int
                 * reciveuserid= boxInfoService.getreciveuserid(b.getBox_id());
                 * 
                 * if (recievedep==0 &&reciveuserid==0 ){
                 * 
                 * errorcode ="104"; errorinfo="此光交箱没有填写维护部门或维护人 不能申请";
                 * 
                 * Map<String, Object> map = new HashMap<String, Object>();
                 * map.put("errorcode",errorcode); //errorcode map.put("errorinfo", errorinfo);
                 * map.put("date", getDate()); map.put("results", ""); return map;
                 * 
                 * }
                 */

                boxInfoService.addrepairapp(rp);

            } else { // 如果设置为不审批 则新增维修申请的同时 还需要增加工单

                RepairRecord rp = new RepairRecord();
                rp.setBox_id(b.getBox_id());
                rp.setUser_id(userService.findByName(username));
                rp.setDepartment_id(userService.findDepByName(username));

                rp.setReceiveuser_id(userService.findByName(username));
                rp.setReceivedepartment_id(userService.findDepByName(username));

                rp.setRepairtype(b.getRepairtype());
                rp.setRemark(b.getRemark());
                rp.setRespect_start_time(b.getRespect_start_time());
                rp.setRespect_end_time(b.getRespect_end_time());
                rp.setCreate_time(getDate());
                rp.setApp_result(1);
                rp.setApp_time(getDate());

                boxInfoService.addrepairapp(rp);

                boxEventAndWarnService.affirmFixapply(rp);

            }

        } catch (Exception ex) {
            errorcode = "104";
            errorinfo = ex.toString();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorcode", errorcode); // errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", "");
        return map;

    }

    // 获取维修申请简版
    @RequestMapping(value = "/repairapp", method = RequestMethod.GET)
    public @ResponseBody Map getRepairapp(HttpServletRequest request) throws IOException {
        String errorcode = "100";
        String errorinfo = "";

        String username = request.getHeader("username");
        int userid = userService.findByName(username);
        List<RepairRecord> bs = new ArrayList<RepairRecord>();
        List<RepairRecordSimple> rs = new ArrayList<RepairRecordSimple>();

        try {

            bs = boxInfoService.findrepairrecord(userid);
            if (bs != null) {

                for (RepairRecord r : bs) {
                    BoxInfo bi = boxInfoService.find(r.getBox_id());
                    // User u = userService.findById(r.getAppuser_id());
                    RepairRecordSimple rd = new RepairRecordSimple();

                    rd.setUsername(username);
                    rd.setBox_no(bi.getBox_no());
                    rd.setCreate_time(r.getCreate_time());

                    String dtype = "";
                    int dtstatus = r.getApp_result();
                    switch (dtstatus) {
                    case 0:
                        dtype = "未审批";
                        break;
                    case 1:
                        dtype = "审批通过";
                        break;
                    case 2:
                        dtype = "审批拒绝";
                        break;
                    default:
                        dtype = "未知";
                        break;

                    }
                    rd.setApp_result(dtype);

                    String repairtype = "";
                    int rstatus = r.getRepairtype();
                    switch (rstatus) {
                    case 1:
                        repairtype = "资源配置";
                        break;
                    case 2:
                        repairtype = "其他";
                        break;
                    default:
                        repairtype = "未知";
                        break;

                    }
                    rd.setRepairtype(repairtype);
                    rd.setApp_time(r.getApp_time());

                    rs.add(rd);

                }

            }

        } catch (Exception ex) {
            errorcode = "104";
            // errorinfo=ex.toString();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorcode", errorcode); // errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", rs);
        return map;

    }

    // 获取维修申请详细
    @RequestMapping(value = "/repairappdetail", method = RequestMethod.GET)
    public @ResponseBody Map getRepairappdetail(int id) throws IOException {
        String errorcode = "100";
        String errorinfo = "";

        RepairRecord bs = new RepairRecord();
        List<RepairRecordNew> rs = new ArrayList<RepairRecordNew>();

        try {
            bs = boxInfoService.findrepairrecordbyid(id);

            if (bs != null) {

                BoxInfo bi = boxInfoService.find(bs.getBox_id());
                User u = userService.findById(bs.getAppuser_id());
                RepairRecordNew rd = new RepairRecordNew();

                rd.setDepname(boxInfoService.getdepnamebyId(bs.getDepartment_id()));
                rd.setBox_no(bi.getBox_no());
                rd.setBox_address(bi.getAddress());
                rd.setAppuser(u.getFull_name());
                rd.setCreate_time(bs.getCreate_time());

                String dtype = "";
                int dtstatus = bs.getApp_result();
                switch (dtstatus) {
                case 0:
                    dtype = "未审批";
                    break;
                case 1:
                    dtype = "审批通过";
                    break;
                case 2:
                    dtype = "审批拒绝";
                    break;
                default:
                    dtype = "未知";
                    break;

                }
                rd.setApp_result(dtype);

                String iscreate = "";
                int cstatus = bs.getIs_create();
                switch (cstatus) {
                case 0:
                    iscreate = "未产生";
                    break;
                case 1:
                    iscreate = "已产生";
                    break;
                default:
                    iscreate = "未知";
                    break;

                }
                rd.setIs_create(iscreate);

                String repairtype = "";
                int rstatus = bs.getRepairtype();
                switch (rstatus) {
                case 1:
                    repairtype = "资源配置";
                    break;
                case 2:
                    repairtype = "其他";
                    break;
                default:
                    repairtype = "未知";
                    break;

                }
                rd.setRepairtype(repairtype);
                rd.setRemark(bs.getRemark());
                rd.setApp_time(bs.getApp_time());
                rd.setWorkorder_id(bs.getWorkorder_id());

                rs.add(rd);

            }

        } catch (Exception ex) {
            errorcode = "104";
            // errorinfo=ex.toString();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorcode", errorcode); // errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", rs);
        return map;

    }

    // 更新端子业务信息（label——info）
    @RequestMapping(value = "/boxinfo/updatelabel", method = RequestMethod.PUT)
    public @ResponseBody Map editLabelinfo(@RequestBody LabelInfo lInfo) throws IOException {

        String errorcode = "100";
        String errorinfo = "";

        try {

            if (lInfo.getTeminalid() == 0) {

                errorcode = "104";
                errorinfo = "请务必填写端子id";

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("errorcode", errorcode); // errorcode
                map.put("errorinfo", errorinfo);
                map.put("date", getDate());
                map.put("results", "");
                return map;
            }

            // 更新数据库
            boxInfoService.updateLabe(lInfo);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            errorcode = "104";
            errorinfo = e.toString();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorcode", errorcode); // errorcode
        map.put("errorinfo", errorinfo);
        map.put("date", getDate());
        map.put("results", null);
        return map;
    }

    /* end******************LY */

    // 光交箱监控页面请求
    /*
     * @RequestMapping(params="method=list") public String list(HttpServletRequest
     * request, ModelMap map){ List<Department> departs =
     * departmentService.findAll(); map.put("departments",
     * DepartmentController.departs2json(departs, 1)); return "boxinfoList"; }
     */

    /*
     * //获取分页数据，AJAX方式
     * 
     * @RequestMapping(params="method=listPageByAjax") public @ResponseBody
     * ListInfoTemplate listPageByAjax(BoxInfoSearch bis){
     * System.out.println("BoxInfoController.listPageByAjax()");
     * 
     * if (bis != null && bis.getRows() <= 0) { bis.setRows(10); } ListInfoTemplate
     * lit = new ListInfoTemplate(); try{ lit.setPage(bis.getPage()); int counts =
     * boxInfoService.findCounts(bis); lit.setTotal(counts % bis.getRows() > 0 ?
     * (counts / bis.getRows() + 1) : (counts / bis.getRows()));
     * lit.setRecords(counts); lit.setRows(boxInfoService.findByPager(bis)); } catch
     * (Exception e) { e.printStackTrace(); } return lit; }
     * 
     * 
     * 
     * //获取所有的光交箱状态和字典状态项，AJAX
     * 
     * @RequestMapping(value = "/boxinfo/listAllBoxStatesByAjax", method =
     * RequestMethod.GET) public @ResponseBody BoxStatesAndStateValues
     * listAllBoxStatesByAjax(){ BoxStatesAndStateValues bs = new
     * BoxStatesAndStateValues(); bs.setBis(this.boxInfoService.findAll(null));
     * bs.setBss(this.boxInfoService.findAllBoxStatesByAjax()); return bs; }
     * 
     * 
     * 
     * 
     * //获取所有的光交箱状态信息，AJAX
     * 
     * @RequestMapping(params="method=findAllBoxStatesByAjax") public @ResponseBody
     * BoxStatesList findAllBoxStatesByAjax(){ BoxStatesList bsl = new
     * BoxStatesList(); bsl.setSks(this.dictionaryService.findStateKeyAll());
     * bsl.setSvs(this.dictionaryService.findStateValueAll()); bsl.setResultMark(1);
     * return bsl; }
     * 
     * 
     * //获取光交箱信息，AJAX方式
     * 
     * @RequestMapping(params="method=findByAjax") public @ResponseBody
     * BoxInfoWithDictionarys findByAjax(int id){ BoxInfoWithDictionarys biwd = new
     * BoxInfoWithDictionarys(); return biwd; }
     * 
     * //解除跳纤
     * 
     * @RequestMapping(params="method=relieveJumpTerminal") public @ResponseBody
     * AjaxRetObjTemplate relieveJumpTerminal(int id){ JumpCore jc =
     * this.boxInfoService.relieveJumpTerminal(id); AjaxRetObjTemplate arot = new
     * AjaxRetObjTemplate(); arot.setResultMark(1); arot.setObject(jc); return arot;
     * }
     * 
     * //获取跳纤
     * 
     * @RequestMapping(params="method=findJumpTerminal") public @ResponseBody
     * AjaxRetObjTemplate findJumpTerminal(int id) { BoxTerminalUsed jc =
     * this.boxInfoService.findJumpTerminal(id); AjaxRetObjTemplate arot = new
     * AjaxRetObjTemplate(); arot.setResultMark(1); arot.setObject(jc); return arot;
     * }
     * 
     * //跳纤
     * 
     * @RequestMapping(params="method=jumpTerminal") public @ResponseBody
     * AjaxRetObjTemplate jumpTerminal(int a, int b){
     * this.boxInfoService.jumpTerminal(a, b); AjaxRetObjTemplate arot = new
     * AjaxRetObjTemplate(); arot.setResultMark(1); return arot; }
     * 
     * 
     * //解除纤芯成端
     * 
     * @RequestMapping(params="method=relieveCoreToTerminal") public @ResponseBody
     * AjaxRetObjTemplate relieveCoreToTerminal(int id){
     * this.boxInfoService.relieveCoreToTerminal(id); AjaxRetObjTemplate arot = new
     * AjaxRetObjTemplate(); arot.setResultMark(1); return arot; }
     * 
     * //获取纤芯成端
     * 
     * @RequestMapping(params="method=findCoreToTerminal") public @ResponseBody
     * AjaxRetObjTemplate findCoreToTerminal(int id) { CoreUsed jc =
     * this.boxInfoService.findCoreToTerminal(id); AjaxRetObjTemplate arot = new
     * AjaxRetObjTemplate(); arot.setResultMark(1); arot.setObject(jc); return arot;
     * }
     * 
     * //添加光交箱
     * 
     * @RequestMapping(params="method=add") public void add(BoxInfo boxInfo,
     * HttpServletResponse response) throws IOException{
     * System.out.println("boxInfoController.add()"); boxInfoService.add(boxInfo);
     * String resultMark = "{\"resultMark\" : 1}";
     * response.setContentType("application/json; charset=UTF-8");
     * response.getWriter().print(resultMark); }
     * 
     * //����ʱ���
     * 
     * @RequestMapping(params="method=ts") public void ts(Test date, int id,
     * HttpServletResponse response) throws IOException{
     * System.out.println("boxInfoController.ts()"); String resultMark =
     * "{\"resultMark\" : 1}";
     * response.setContentType("application/json; charset=UTF-8");
     * response.getWriter().print(resultMark); }
     * 
     * //修改光交箱
     * 
     * @RequestMapping(params="method=update") public void update(BoxInfo boxInfo,
     * HttpServletResponse response) throws IOException{
     * boxInfoService.update(boxInfo); String resultMark = "{\"resultMark\" : 1}";
     * response.setContentType("application/json; charset=UTF-8");
     * response.getWriter().print(resultMark); }
     * 
     * //删除光交箱
     * 
     * @RequestMapping(params="method=delete") public void delete(BoxInfo boxInfo,
     * HttpServletResponse response) throws IOException{
     * boxInfoService.delete(boxInfo); String resultMark = "{\"resultMark\" : 1}";
     * response.setContentType("application/json; charset=UTF-8");
     * response.getWriter().print(resultMark); }
     */

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

    public DepartmentService getDepartmentService() {
        return departmentService;
    }

    @Resource
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public LockAndKeyService getLockandkeyService() {
        return lockandkeyService;
    }

    @Resource
    public void setLockandkeyService(LockAndKeyService lockandkeyService) {
        this.lockandkeyService = lockandkeyService;
    }

    public OpticalcableService getOpticalcableService() {
        return opticalcableService;
    }

    @Resource
    public void setOpticalcableService(OpticalcableService opticalcableService) {
        this.opticalcableService = opticalcableService;
    }

    public UserService getUserService() {
        return userService;
    }

    @Resource
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public BoxEventAndWarnService getBoxEventAndWarnService() {
        return boxEventAndWarnService;
    }

    @Resource
    public void setBoxEventAndWarnService(BoxEventAndWarnService boxEventAndWarnService) {
        this.boxEventAndWarnService = boxEventAndWarnService;
    }

}
