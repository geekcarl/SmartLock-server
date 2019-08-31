package com.sxt.dao;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.sxt.po.*;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component("userDao")
public class UserDao {
    @Resource
    private HibernateTemplate hibernateTemplate;

    public User findByLogin(User u) {
        System.out.println("UserDao.find()");
        List<User> users = hibernateTemplate.find("from User u where u.user_name=? and u.passwd=? and is_deleted = 0", u.getUser_name(), u.getPasswd());
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }

    }


    public VersionManage getVersion() {

        List<VersionManage> v = hibernateTemplate.find("from VersionManage v ");
        if (v.size() > 0) {
            return v.get(0);
        } else {
            return null;
        }

    }

    public int findByName(String name) {

        List<User> users = hibernateTemplate.find("from User u where u.user_name=? and is_deleted = 0 ", name);
        if (users.size() > 0) {
            return users.get(0).getId();
        } else {
            return 0;
        }
    }


    //findDepByName
    public int findDepByName(String name) {

        List<User> users = hibernateTemplate.find("from User u where u.user_name=? and is_deleted = 0", name);
        if (users.size() > 0) {
            return users.get(0).getDepartment().getId();
        } else {
            return 0;
        }


    }


    public String getPassword(String name) {
        List<User> users = hibernateTemplate.find("from User u where u.user_name=? and is_deleted = 0 ", name);

        if (users.size() > 0) {
            return users.get(0).getPasswd();
        } else {
            return "";
        }


    }


    public User findById(int id) {

        return hibernateTemplate.get(User.class, id);
    }

    //ֻ�޸Ĳ�����Ϣ
    public int update(User u) {
        return hibernateTemplate.bulkUpdate("update User u set u.full_name=?, u.sex=?, u.phone_no=? where u.id=?", u.getFull_name(), u.getSex(), u.getPhone_no(), u.getId());
    }

    public int updatepwd(User u) {
        return hibernateTemplate.bulkUpdate("update User u set u.passwd=? where u.id=?", u.getPasswd(), u.getId());
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }


    private void getDepartmentUsers(int departid, List<User> container) {
        List<User> users = this.hibernateTemplate.find("from User u where u.department.id =? and is_deleted = 0 ", departid);
        container.addAll(users);
        List<Department> departments = this.hibernateTemplate.find("from Department d where d.id =? and is_deleted = 0 ", departid);
        if (departments != null && !departments.isEmpty()) {
            List<Department> childDepartments = departments.get(0).getChildren();
            if (childDepartments == null || childDepartments.isEmpty()) {
                return;
            }
            for (Department department : childDepartments) {
                getDepartmentUsers(department.getId(), container);
            }
        }
    }

    public List<UserList> getUserList(int user_id) {
        User user = this.findById(user_id);
        List<Department> depslist = user.getDepartment().getChildren();
        List<User> users = null;
        if (user.getId() == 1) {
            users = this.hibernateTemplate.find("from User u where is_deleted = 0 and role_id !=" + null);
        } else {
//            users = this.hibernateTemplate.find("from User u where u.department.id =" + user.getDepartment().getId() + " and is_deleted = 0 ");
            users = new ArrayList();
            getDepartmentUsers(user.getDepartment().getId(), users);
        }

        Set<Integer> roleSet = getPrivilegesRoIds("user", "opendoorAuthorize");

        if (users.size() <= 0) {
            return null;
        } else {
            List<UserList> userlist = new ArrayList();

            for (int i = 0; i < users.size(); ++i) {
                UserList list = new UserList();
                User iu = users.get(i);
                list.setUserid(iu.getId());
                list.setUsername(iu.getUser_name());
                list.setFullname(iu.getFull_name());
                list.setHasAuth(roleSet.contains(iu.roleId()));
                userlist.add(list);
            }

            return userlist;
        }
    }

    public String getUserList_str(int user_id) {
        User user = this.findById(user_id);
        List<Department> depslist = user.getDepartment().getChildren();
        String deps = getDepartments(depslist);
        return deps;
    }

    public static String getDepartments(List<Department> deps) {
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        if (deps != null && deps.size() > 0) {
            for (int i = 0; i < deps.size(); ++i) {
                sb.append(((Department) deps.get(i)).getId() + ",");
            }

            return sb.toString().substring(0, sb.toString().length() - 1) + ")";
        } else {
            return "(-1)";
        }
    }

    public User getUserInfo(String name) {
        List<User> users = this.hibernateTemplate.find("from User u where u.user_name=? and is_deleted = 0 ", name);
        return users.size() > 0 ? (User) users.get(0) : null;
    }

    public void addGrantLog(UserInfoPhoneRmoteGrant user, String username) throws Exception {
        GrantLog newli = new GrantLog();
        User liuser = this.hibernateTemplate.get(User.class, user.getUserid());
        KeyInfo key = this.getUserKey(user.getUserid());
        newli.setLog_name(!StringUtils.isEmpty(user.getGrantName()) ? user.getGrantName() : "手机授权");
        newli.setGrant_user(user.getGrantUserName()); // 授权人
        newli.setGrant_time(new java.util.Date());
        newli.setOperators(liuser); //操作人
        newli.setDep_id(liuser.getDepartment().getId());
        newli.setOperators_name(liuser.getFull_name());
        newli.setAuth_type(0); //'0: 钥匙  1：人员卡 2： 手机开门',

        if (key != null) {
            newli.setKey(key);
            newli.setKey_no(key.getKey_no());
            newli.setKey_rfid(key.getRfid());
        }

        String state = null;
        //初始化任务状态：
        if (username.equals(user.getGrantUserName())) {
            //如果授权人是自己，任务直接变为未下发
            state = GrantLog.STATE_PENDING;
        } else {
            //如果授权人是别人，任务需要先授权
            state = GrantLog.STATE_AUTHING;
        }
        newli.setRemarks(state);

        String boxid = user.getBoxid();
        String[] boxids = boxid.split("\\,");
        int size = boxids.length;
        List<GrantDetail> gdlist = new ArrayList();

        int i;
        for (i = 0; i < size; ++i) {
            GrantDetail gd = new GrantDetail();
            gd.setBegin_time(this.strToDate(user.getBegin_time()));
            gd.setEnd_time(this.strToDate(user.getEnd_time()));
            BoxInfo boxInfo = new BoxInfo();
            boxInfo.setId(Integer.valueOf(boxids[i]));
            gd.setBoxInfo(boxInfo);
            gdlist.add(gd);
        }

        newli.setGds(gdlist);
        this.hibernateTemplate.save(newli);

        for (i = 0; i < size; ++i) {
            ((GrantDetail) newli.getGds().get(i)).setGrantLog(newli);
            this.hibernateTemplate.save(newli.getGds().get(i));
        }
    }

    private java.util.Date strToDate(String strStartDate) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date startDate = df.parse(strStartDate);
        return startDate;
    }

    private String getStringDate(java.util.Date time) {
        if (time == null) {
            return "";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(time);
            return dateString;
        }
    }

    public List<GrantTaskInfo> getGrantTaskList(String name) throws Exception {
        List<GrantTaskInfo> list = new ArrayList();
        User user = getUserInfo(name);
        Date date = new Date(System.currentTimeMillis());
        StringBuffer sb = new StringBuffer();
//        sb.append("from GrantLog d where d.grant_user ='" + name + "' ");
//        sb.append("or operators_id ='" + user.getId() + "' order by d.id desc"); //这里授权人和操作人都能看到任务

        sb.append("from GrantLog d where d.auth_type =0 and ");
        sb.append("(d.grant_user ='" + name + "' or operators_id ='" + user.getId() + "') ");
        sb.append("order by d.remarks asc, d.id desc"); //这里授权人和操作人都能看到任务
        List<GrantLog> gls = this.hibernateTemplate.find(sb.toString());

        for (int i = 0; i < gls.size(); i++) {
            GrantLog log = gls.get(i);
            GrantTaskInfo task = new GrantTaskInfo();
            task.setGrantName(log.getLog_name());
            task.setState(log.getRemarks());
            task.setGrantUserName(log.getGrant_user());
            task.setUserid(log.getOperators().getId());
            task.setOperUserName(log.getOperators().getFull_name());
            task.setTaskId(log.getId());
            task.setGrantTime(getStringDate(log.getGrant_time()));

            boolean avaiable = false;

            List<GrantDetail> gds = log.getGds();
            List<GrantTaskInfo.Box> boxList = new ArrayList<GrantTaskInfo.Box>();
            for (int j = 0; j < gds.size(); j++) {

                GrantDetail detail = gds.get(j);
                if ((detail.getEnd_time() != null && !(detail.getEnd_time().before(date)))
                || (detail.getEnd_time() == null && detail.getBegin_time() == null)) {
                    avaiable = true;
                    GrantTaskInfo.Box box = new GrantTaskInfo.Box();
                    List<LockModify> locklist = this.getLockModifys(detail.getBoxInfo().getId());
                    for (int k = 0; k < locklist.size(); k++) {
                        String lockCode = locklist.get(k).getLock_code();
                        box.addLock(lockCode);
                    }
                    box.boxId = detail.getBoxInfo().getId();
                    box.boxNo = detail.getBoxInfo().getBox_no();
                    boxList.add(box);
                }
                task.setBegin_time(getStringDate(detail.getBegin_time()));
                task.setEnd_time(getStringDate(detail.getEnd_time()));
            }

            if (avaiable) {
                task.setOpenBoxs(boxList);
                list.add(task);
            }

        }
        return list;
    }

    private List<LockModify> getLockModifys(int boxid) {
        List<LockModify> bs = this.hibernateTemplate.find("from LockModify lo where lo.box_id=? ", boxid);
        return bs;
    }

    private KeyInfo getUserKey(int userid) {
        List<KeyInfo> keys = this.hibernateTemplate.find("from KeyInfo where operators_id=? ", userid);
        if (keys != null && !keys.isEmpty()) {
            return keys.get(0);
        }
        return null;
    }

    /**
     * 根据roleId 查询当前用户拥有的权限
     *
     * @param roleId
     * @return
     */
    public List<Privilege> getUserPrivileges(int roleId) {
        List<RolePrivileges> pirIds = this.hibernateTemplate.find("from RolePrivileges rp where rp.role_id=? ", roleId);
        if (pirIds == null || pirIds.isEmpty()) {
            return null;
        }
        StringBuilder set = new StringBuilder();
        int size = pirIds.size();
        for (int i = 0; i < size; i++) {
            String priId = String.valueOf(pirIds.get(i).getPrivilege_id());
            set.append(priId);
            if (i < size - 1) {
                set.append(",");
            }
        }
        StringBuilder sql = new StringBuilder();
        sql.append("from Privilege p where p.id in (");
        sql.append(set.toString());
        sql.append(")");
        return (List<Privilege>) this.hibernateTemplate.find(sql.toString());
    }

    public Set<Integer> getPrivilegesRoIds(String pvirilege, String method) {
        List<Privilege> privileges = this.hibernateTemplate.find("from Privilege p where p.privilege=? and p.method=? ", pvirilege, method);
        if (privileges == null || privileges.isEmpty()) {
            return null;
        }
        Set<Integer> set = new HashSet<Integer>();
        for (RolePrivileges rp : privileges.get(0).getRolePrivileges()) {
            set.add(rp.getRole_id());
        }
        return set;
    }


    public boolean updateTaskState(int taskid, String state, String username) {
        List<GrantLog> gls = this.hibernateTemplate.find("from GrantLog d where d.id =? ", taskid);
        if (gls == null || gls.isEmpty()) {
            return false;
        }
        GrantLog log = gls.get(0);
        log.setRemarks(state);
        this.hibernateTemplate.update(log);
        return true;
    }
}
