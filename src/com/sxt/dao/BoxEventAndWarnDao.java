package com.sxt.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.sxt.po.*;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

@Component("boxEventAndWarnDao")
public class BoxEventAndWarnDao {
    @Resource
    private HibernateTemplate hibernateTemplate;


    //获取系统配置信息
    public SysConfig findSysConfig() {
        List<SysConfig> temp = this.hibernateTemplate.find("from SysConfig");
        return temp.size() > 0 ? temp.get(0) : null;
    }

    public static String getDate() {
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return formatter.format(date);
    }


    //审批维修申请
    public void affirmFixapply(RepairRecord rr) {
        WorkOrderTable wo = new WorkOrderTable();
        //this.hibernateTemplate.bulkUpdate("update RepairRecord rr set rr.app_result = ?, rr.app_user.id=?, rr.app_time=now() where rr.id = ?", rr.getApp_result(), rr.getAppuser_id(), rr.getId());
        //如果是审批为通过要绑定工单
        if (rr.getApp_result() == 1) {
            rr = this.hibernateTemplate.get(RepairRecord.class, rr.getId());
            wo.setContent(rr.getRemark());
            wo.setCreate_time(getDate());
            wo.setCreate_type(3);
            User u = new User();

            wo.setTitle("手机发起工单" + getDate());
            wo.setUser_id(rr.getUser_id());
            wo.setDepartment_id(rr.getDepartment_id());
            wo.setOrder_no("order_" + new Date().getTime());
            wo.setReceive_operators_id(rr.getReceiveuser_id());
            wo.setRespect_endtime(rr.getRespect_end_time());
            wo.setRespect_starttime(rr.getRespect_start_time());
            wo.setType(rr.getRepairtype() == 1 ? 1 : 3);
            this.hibernateTemplate.save(wo);
            //rr.setWorkorder_id(wo.getId());
            //rr.setIs_create(1);
            //this.hibernateTemplate.update(rr);
            this.hibernateTemplate.bulkUpdate("update RepairRecord rr set rr.workorder_id = ?, rr.is_create=?  where rr.id = ?", wo.getId(), 1, rr.getId());
        }
    }


    //获取门开关列表
    public List<DoorEvent> findDoorEventByPager(DoorEventSearch ds) {
        List<DoorEvent> list;
        try {
            StringBuffer sb = new StringBuffer();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            sb.append("from DoorEvent d where d.boxinfo.is_deleted=0 ");
            if (ds.getBox_id() > 0) {
                sb.append(" and b.boxinfo.id = " + ds.getBox_id());
            }
            if (ds.getOpen_time() != null) {
                sb.append(" and DateDiff(d.open_time,'" + df.format(ds.getOpen_time()) + "')=0");
            }
            if (ds.getOpen_keys() != null && !ds.getOpen_keys().equals("")) {
                sb.append(" and d.open_keys like '%" + ds.getOpen_keys() + "%'");
            }
            if (ds.getOpen_operators() != null && !ds.getOpen_operators().equals("")) {
                sb.append(" and d.open_operators like '%" + ds.getOpen_operators() + "%'");
            }
            if (ds.getOpen_rfids() != null && !ds.getOpen_rfids().equals("")) {
                sb.append(" and d.open_rfids like '%" + ds.getOpen_rfids() + "%'");
            }
            if (ds.getClose_time() != null) {
                sb.append(" and DateDiff(d.close_time,'" + df.format(ds.getClose_time()) + "')=0");
            }
            if (ds.getClose_keys() != null && !ds.getClose_keys().equals("")) {
                sb.append(" and d.close_keys like '%" + ds.getClose_keys() + "%'");
            }
            if (ds.getClose_operators() != null && !ds.getClose_operators().equals("")) {
                sb.append(" and d.close_operators like '%" + ds.getClose_operators() + "%'");
            }
            if (ds.getClose_rfids() != null && !ds.getClose_rfids().equals("")) {
                sb.append(" and d.close_rfids like '%" + ds.getClose_rfids() + "%'");
            }
            if (ds.getSort() != null && !ds.getSort().equals("")) {
                sb.append(" order by d." + ds.getSort() + " " + ds.getOrder() + " ");
            } else {
                sb.append(" order by d.open_time desc ");
            }
            list = this.getListForPage(sb.toString(), (ds.getPage() - 1) * ds.getRows(), ds.getRows());
        } finally {
        }
        return list;
    }


    //findDoorEvent
    public List<DoorEvent> findDoorEventold(String param) {
        try {

            StringBuffer sb = new StringBuffer();
            sb.append("from DoorEvent b where 0=0 ");

            if (param != null && !param.equals("")) {

                sb.append("and (b.boxinfo.box_no like '%" + param + "%' or b.boxinfo.controller_id like '%" + param + "%' )");


            }

            System.out.println(sb.toString());
            List<DoorEvent> bs = hibernateTemplate.find(sb.toString());
            return bs;

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return null;
    }


    //findDoorEventbyID
    public DoorEvent findDoorEventbyID(int id) {

        return hibernateTemplate.get(DoorEvent.class, id);

    }


    //findAlarmEventbyID
    public AlarmEvent findAlarmEventbyID(int id) {

        return hibernateTemplate.get(AlarmEvent.class, id);

    }

    //findDoorEvent
    public List<DoorEvent> findDoorEvent(int boxid, String opendate, int num) {
        try {

            StringBuffer sb = new StringBuffer();
            sb.append("from DoorEvent b where 0=0 ");

            sb.append("and b.boxinfo.id = " + boxid + " and  ( datediff('" + opendate + "', b.open_time) >=  -" + num + " and  datediff('" + opendate + "', b.open_time) <= " + num + ") ");

            System.out.println(sb.toString());
            List<DoorEvent> bs = hibernateTemplate.find(sb.toString());
            return bs;

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return null;
    }

    //findWarnEvent
    public List<AlarmEvent> findWarnEvent(int boxid, String opendate, int num) {
        try {

            StringBuffer sb = new StringBuffer();
            sb.append("from AlarmEvent b where 0=0 ");

            sb.append("and b.boxinfo.id = " + boxid + " and  ( datediff('" + opendate + "', b.alarm_time) >=  -" + num + " and  datediff('" + opendate + "', b.alarm_time) <= " + num + ") ");

            System.out.println(sb.toString());
            List<AlarmEvent> bs = hibernateTemplate.find(sb.toString());
            return bs;

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return null;
    }

    //获取门开关列表-数量
    public int findDoorEventCounts(DoorEventSearch ds) {
        SessionFactory sf = hibernateTemplate.getSessionFactory();
        Session session = sf.getCurrentSession();
        Long count;
        try {
            StringBuffer sb = new StringBuffer();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            sb.append("select count(*) from DoorEvent d where d.boxinfo.is_deleted=0 ");
            if (ds.getBox_id() > 0) {
                sb.append(" and b.boxinfo.id = " + ds.getBox_id());
            }
            if (ds.getOpen_time() != null) {
                sb.append(" and DateDiff(d.open_time,'" + df.format(ds.getOpen_time()) + "')=0");
            }
            if (ds.getOpen_keys() != null && !ds.getOpen_keys().equals("")) {
                sb.append(" and d.open_keys like '%" + ds.getOpen_keys() + "%'");
            }
            if (ds.getOpen_operators() != null && !ds.getOpen_operators().equals("")) {
                sb.append(" and d.open_operators like '%" + ds.getOpen_operators() + "%'");
            }
            if (ds.getOpen_rfids() != null && !ds.getOpen_rfids().equals("")) {
                sb.append(" and d.open_rfids like '%" + ds.getOpen_rfids() + "%'");
            }
            if (ds.getClose_time() != null) {
                sb.append(" and DateDiff(d.close_time,'" + df.format(ds.getClose_time()) + "')=0");
            }
            if (ds.getClose_keys() != null && !ds.getClose_keys().equals("")) {
                sb.append(" and d.close_keys like '%" + ds.getClose_keys() + "%'");
            }
            if (ds.getClose_operators() != null && !ds.getClose_operators().equals("")) {
                sb.append(" and d.close_operators like '%" + ds.getClose_operators() + "%'");
            }
            if (ds.getClose_rfids() != null && !ds.getClose_rfids().equals("")) {
                sb.append(" and d.close_rfids like '%" + ds.getClose_rfids() + "%'");
            }
            Query q = session.createQuery(sb.toString());
            count = (Long) q.uniqueResult();
        } finally {
            session.close();
        }
        return count.intValue();
    }

    //获取报警列表
    public List<AlarmEvent> findAlarmEventByPager(AlarmEventSearch ds) {
        List<AlarmEvent> list;
        try {
            StringBuffer sb = new StringBuffer();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            sb.append("from AlarmEvent d where d.boxinfo.is_deleted=0 ");
            if (ds.getBox_id() > 0) {
                sb.append(" and d.boxinfo.id = " + ds.getBox_id());
            }
            if (ds.getController_id() > 0) {
                sb.append(" and d.controller_id = " + ds.getController_id());
            }
            if (ds.getAddress() != null && !ds.getAddress().equals("")) {
                sb.append(" and d.address like '%" + ds.getAddress() + "%'");
            }
            if (ds.getDepartment() != null && !ds.getDepartment().equals("")) {
                sb.append(" and d.department like '%" + ds.getDepartment() + "%'");
            }
            if (ds.getAlarm_type() != null && !ds.getAlarm_type().equals("")) {
                sb.append(" and d.alarm_type like '%" + ds.getAlarm_type() + "%'");
            }
            if (ds.getAlarm_time() != null) {
                sb.append(" and DateDiff(d.alarm_time,'" + df.format(ds.getAlarm_time()) + "')=0");
            }
            if (ds.getAlarm_keys() != null && !ds.getAlarm_keys().equals("")) {
                sb.append(" and d.alarm_keys like '%" + ds.getAlarm_keys() + "%'");
            }
            if (ds.getAlarm_operators() != null && !ds.getAlarm_operators().equals("")) {
                sb.append(" and d.alarm_operators like '%" + ds.getAlarm_operators() + "%'");
            }
            if (ds.getAlarm_rfids() != null && !ds.getAlarm_rfids().equals("")) {
                sb.append(" and d.alarm_rfids like '%" + ds.getAlarm_rfids() + "%'");
            }
            if (ds.getIs_affirmed() > 0) {
                sb.append(" and d.is_affirmed = " + (ds.getIs_affirmed() - 1));
            }
            if (ds.getAffirm_time() != null) {
                sb.append(" and DateDiff(d.affirm_time,'" + df.format(ds.getAffirm_time()) + "')=0");
            }
            if (ds.getAffirm_user() != null && !ds.getAffirm_user().equals("")) {
                sb.append(" and d.affirm_user like '%" + ds.getAffirm_user() + "%'");
            }
            if (ds.getSort() != null && !ds.getSort().equals("")) {
                sb.append(" order by d." + ds.getSort() + " " + ds.getOrder() + " ");
            } else {
                sb.append(" order by d.alarm_time desc ");
            }
            list = this.getListForPage(sb.toString(), (ds.getPage() - 1) * ds.getRows(), ds.getRows());
        } finally {
        }
        return list;
    }

    //获取报警列表-数量
    public int findAlarmEventCounts(AlarmEventSearch ds) {
        SessionFactory sf = hibernateTemplate.getSessionFactory();
        Session session = sf.getCurrentSession();
        Long count;
        try {
            StringBuffer sb = new StringBuffer();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            sb.append("select count(*) from AlarmEvent d where d.boxinfo.is_deleted=0 ");
            if (ds.getBox_id() > 0) {
                sb.append(" and d.boxinfo.id = " + ds.getBox_id());
            }
            if (ds.getController_id() > 0) {
                sb.append(" and d.controller_id = " + ds.getController_id());
            }
            if (ds.getAddress() != null && !ds.getAddress().equals("")) {
                sb.append(" and d.address like '%" + ds.getAddress() + "%'");
            }
            if (ds.getDepartment() != null && !ds.getDepartment().equals("")) {
                sb.append(" and d.department like '%" + ds.getDepartment() + "%'");
            }
            if (ds.getAlarm_type() != null && !ds.getAlarm_type().equals("")) {
                sb.append(" and d.alarm_type like '%" + ds.getAlarm_type() + "%'");
            }
            if (ds.getAlarm_time() != null) {
                sb.append(" and DateDiff(d.alarm_time,'" + df.format(ds.getAlarm_time()) + "')=0");
            }
            if (ds.getAlarm_keys() != null && !ds.getAlarm_keys().equals("")) {
                sb.append(" and d.alarm_keys like '%" + ds.getAlarm_keys() + "%'");
            }
            if (ds.getAlarm_operators() != null && !ds.getAlarm_operators().equals("")) {
                sb.append(" and d.alarm_operators like '%" + ds.getAlarm_operators() + "%'");
            }
            if (ds.getAlarm_rfids() != null && !ds.getAlarm_rfids().equals("")) {
                sb.append(" and d.alarm_rfids like '%" + ds.getAlarm_rfids() + "%'");
            }
            if (ds.getIs_affirmed() > 0) {
                sb.append(" and d.is_affirmed = " + (ds.getIs_affirmed() - 1));
            }
            if (ds.getAffirm_time() != null) {
                sb.append(" and DateDiff(d.affirm_time,'" + df.format(ds.getAffirm_time()) + "')=0");
            }
            if (ds.getAffirm_user() != null && !ds.getAffirm_user().equals("")) {
                sb.append(" and d.affirm_user like '%" + ds.getAffirm_user() + "%'");
            }
            Query q = session.createQuery(sb.toString());
            count = (Long) q.uniqueResult();
        } finally {
            session.close();
        }
        return count.intValue();
    }

    //删除报警记录-数量
    public void deleteAlarmEvent(DeleteAlarmForm daf) {
        SessionFactory sf = hibernateTemplate.getSessionFactory();
        Session session = sf.getCurrentSession();
        for (int i = 0; i < daf.getAe().size(); i++) {
            AlarmEvent ae = daf.getAe().get(i);
            Query q = session.createSQLQuery("delete a from alarmevent a, boxinfo b where a.box_id = b.id and b.controller_id = ? and a.alarm_type = ? and a.alarm_time = ?");
            q.setParameter(0, ae.getController_id());
            q.setParameter(1, ae.getAlarm_type());
            q.setParameter(2, ae.getAlarm_time());
            q.executeUpdate();
            q = session.createSQLQuery("delete from alarmeventhistory where controller_id = ? and alarm_type = ? and alarm_time = ?");
            q.setParameter(0, ae.getController_id());
            q.setParameter(1, ae.getAlarm_type());
            q.setParameter(2, ae.getAlarm_time());
            q.executeUpdate();
        }
    }

    //确认报警记录
    public void affirmAlarmEvent(AlarmEvent ae) {
        SessionFactory sf = hibernateTemplate.getSessionFactory();
        Session session = null;
        session = sf.getCurrentSession();
        Query q = session.createSQLQuery("update alarmevent a set a.is_affirmed = 1, a.affirm_time = now(), a.affirm_user = ? where a.id = ?");
        q.setParameter(0, ae.getAffirm_user());
        q.setParameter(1, ae.getId());
        q.executeUpdate();
    }

    //删除报警记录-数量
    public void deleteDoorEvent(DeleteDoorForm daf) {
        SessionFactory sf = hibernateTemplate.getSessionFactory();
        Session session = sf.getCurrentSession();

    }


    public List getListForPage(final String hql, final int offset,
                               final int length) {
        List list = hibernateTemplate.executeFind(new HibernateCallback() {
            @Override
            public Object doInHibernate(org.hibernate.Session session)
                    throws HibernateException, SQLException {
                // TODO Auto-generated method stub
                List list = null;
                try {
                    Query query = session.createQuery(hql);
                    query.setFirstResult(offset);
                    query.setMaxResults(length);
                    list = query.list();
                } finally {
                    session.close();
                }
                return list;
            }
        });
        return list;
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public boolean addOrUpdateDoorEvent(DoorEventAdd eventAdd) {
        //如果是开门直接插入，如果是关门，更新记录
        //SELECT TOP 1 * FROM user order by id desc
        if (null != eventAdd.getOpen_time()) {
            this.hibernateTemplate.save(eventAdd);
        } else if (null != eventAdd.getClose_time()) {
            List<DoorEventAdd> doorEventAdds = this.hibernateTemplate.find("from DoorEventAdd d " +
                            "where d.id=(select max(d.id) from d where d.close_time is null and d.open_time is not null and d.box_id=?)",
                    eventAdd.getBox_id());

            if (doorEventAdds != null && !doorEventAdds.isEmpty()) {
                DoorEventAdd d = doorEventAdds.get(0);
                d.setClose_time(eventAdd.getClose_time());
                d.setClose_keys(eventAdd.getClose_keys());
                d.setClose_rfids(eventAdd.getClose_rfids());
                d.setClose_operators(eventAdd.getClose_operators());
                this.hibernateTemplate.update(d);
            }
        }
        return true;
    }
}
