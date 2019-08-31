package com.sxt.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.sxt.po.BoxInfo;
import com.sxt.po.CellGrantLog;
import com.sxt.po.GrantDetail;
import com.sxt.po.GrantLog;
import com.sxt.po.GrantLogForPhone;
import com.sxt.po.GrantLogSearch;
import com.sxt.po.KeyInfo;
import com.sxt.po.KeyInfoSearch;
import com.sxt.po.KeyOpenLog;
import com.sxt.po.KeyTypeInfo;
import com.sxt.po.KeyTypeInfoSearch;
import com.sxt.po.LockInfo;
import com.sxt.po.LockInfoSearch;
import com.sxt.po.LockModify;
import com.sxt.po.LockTypeInfo;
import com.sxt.po.LockTypeInfoSearch;
import com.sxt.po.PhoneOpenLog;

@Component("lockandkeyDao")
public class LockAndKeyDao {
    @Resource
    private HibernateTemplate hibernateTemplate;

    public String findrfidbyKeycode(String keycode) {

        List<KeyInfo> keylst = hibernateTemplate.find("from KeyInfo k where k.key_code=? ", keycode);

        if (keylst.size() > 0) {
            return keylst.get(0).getRfid();
        } else {
            return "";
        }

    }

    public List<Map<String, Object>> findLock(LockModify li) {
        String sql = "select new map(li.id as id) from LockModify li where li.lock_code = '" + li.getLock_code() + "'";
        return this.hibernateTemplate.find(sql);
    }

    public void addkeyopenLog(KeyOpenLog cg) {
        hibernateTemplate.save(cg);
    }


    //添加锁
    public void addLock(LockModify li) {
        hibernateTemplate.save(li);
    }


    public static String getDate() {
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return formatter.format(date);

    }


    //添加锁类型
    public void addLockType(LockTypeInfo li) {
        hibernateTemplate.save(li);
    }

    //获取锁类型
    public String findLockInfoByType(int typeid) {


        StringBuffer sb = new StringBuffer();
        sb.append("from LockTypeInfo l where 0=0 ");

        sb.append("and l.id = " + typeid + " ");


        List<LockTypeInfo> bs = hibernateTemplate.find(sb.toString());
        String type = "";
        for (LockTypeInfo lo : bs) {


            type = lo.getType();
        }


        return type;

    }


    //获取锁类型id
    public int findLockInfoByID(String type) {


        StringBuffer sb = new StringBuffer();
        sb.append("from LockTypeInfo l where 0=0 ");

        sb.append("and l.type = '" + type + "' ");

        List<LockTypeInfo> bs = hibernateTemplate.find(sb.toString());
        int typeid = 0;
        for (LockTypeInfo lo : bs) {


            typeid = lo.getId();
        }


        return typeid;

    }


    //findIdbycode
    public int findIdbycode(String keycode) {

        StringBuffer sb = new StringBuffer();
        sb.append("from KeyInfo l  where 0=0 ");

        sb.append("and l.key_code = '" + keycode + "' ");

        List<KeyInfo> bs = hibernateTemplate.find(sb.toString());

        if (bs.size() > 0) {
            return bs.get(0).getId();
        }
        return 0;
    }

    //获取锁列表byly
    public List<LockModify> findLockInfo(int boxid) {
        System.out.println("LockAndKeyDao.findLockInfo()========11");
        return hibernateTemplate.find("from LockModify l where l.box_id =? ", boxid);
    }

    //删除某一光交箱下所有的锁，并新增新上传上来的锁updateLockInfo，新增的时候要判断下是不是有重复的
    public void updateLockInfo(List<LockModify> locklist, int boxid) {

        if (locklist.size() > 0) {

            this.hibernateTemplate.bulkUpdate("delete from LockModify lo where  lo.box_id = " + boxid + "");

            for (LockModify lo : locklist) {

                boxid = lo.getBox_id();
                //this.hibernateTemplate.save(lo);
                if (lo.getLock_code() != null && !lo.getLock_code().equals("")) {
                    List<Map<String, Object>> result = findLock(lo);
                    if (result == null || result.size() <= 0) {
                        this.hibernateTemplate.save(lo);
                    }
                }
            }

        }
    }


    //删除某一光交箱下所有的锁delLockInfo
    public void delLockInfo(int boxid) {

        this.hibernateTemplate.bulkUpdate("delete from LockModify lo where  lo.box_id = " + boxid + "");

    }

    //获取锁列表
    public List<LockInfo> findLockInfoByPager(LockInfoSearch ds) {
        StringBuffer sb = new StringBuffer();
        sb.append("from LockInfo d where 0=0 ");
        if (ds.getSort() != null && !ds.getSort().equals("")) {
            sb.append(" order by d." + ds.getSort() + " " + ds.getOrder() + " ");
        }
        List<LockInfo> d = this.getListForPage(sb.toString(), (ds.getPage() - 1) * ds.getRows(), ds.getRows());
        return d;
    }

    //获取锁类型列表
    public List<LockTypeInfo> findLockTypeInfoByPager(LockTypeInfoSearch ds) {
        StringBuffer sb = new StringBuffer();
        sb.append("from LockTypeInfo d where 0=0 ");
        if (ds.getSort() != null && !ds.getSort().equals("")) {
            sb.append(" order by d." + ds.getSort() + " " + ds.getOrder() + " ");
        }
        List<LockTypeInfo> d = this.getListForPage(sb.toString(), (ds.getPage() - 1) * ds.getRows(), ds.getRows());
        return d;
    }

    //获取所有锁类型
    public List<LockTypeInfo> findAllLockType() {
        List<LockTypeInfo> ds = hibernateTemplate.find("FROM LockTypeInfo d");
        return ds;
    }


    //获取锁数量
    public int findLockCounts() {
        SessionFactory sf = hibernateTemplate.getSessionFactory();
        Session session = sf.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("select count(*) from LockInfo d");
        Long count = (Long) q.uniqueResult();
        tx.commit();
        session.close();
        return count.intValue();
    }

    //获取锁类型数量
    public int findLockTypeCounts() {
        SessionFactory sf = hibernateTemplate.getSessionFactory();
        Session session = sf.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("select count(*) from LockTypeInfo d");
        Long count = (Long) q.uniqueResult();
        tx.commit();
        session.close();
        return count.intValue();
    }


    ////////////////////////////

    //添加钥匙
    public void addKey(KeyInfo li) {
        hibernateTemplate.save(li);
    }

    //获取钥匙详情
    public KeyInfo findKey(int id) {
        return hibernateTemplate.get(KeyInfo.class, id);
    }

    //修改钥匙
    public void updateKey(KeyInfo ki) {
        this.hibernateTemplate.update(ki);
    }

    //删除钥匙
    public void deleteKey(int[] ids) {
        for (int i = 0; i < ids.length; i++) {
            KeyInfo ki = this.hibernateTemplate.get(KeyInfo.class, ids[i]);
            List<GrantLog> gls = this.hibernateTemplate.find("from GrantLog gl where gl.key.id = ?", ids[i]);
            for (int j = 0; j < gls.size(); j++) {
                this.hibernateTemplate.delete(gls.get(j));
            }
            this.hibernateTemplate.delete(ki);
        }

    }

    public List<GrantLogForPhone> findGrantLogByPhone(int userid, Integer boxid) {
        StringBuffer sb = new StringBuffer();
        sb.append("from GrantLogForPhone d where  d.operators_id ='" + userid + "'  and  d.auth_type = 2 ");

        if (boxid != null && boxid != 0)
            sb.append("  and  d.gds.box_id = " + boxid + " ");

        List<GrantLogForPhone> d = hibernateTemplate.find(sb.toString());
        return d;
    }

    public Integer findBoxidbylock(String lockid) {

        List<LockModify> los = hibernateTemplate.find("from LockModify l where l.lock_code=? ", lockid);
        if (los.size() > 0) {
            return los.get(0).getBox_id();
        } else {
            return 0;
        }
    }

    //findBoxnobylock
    public String findBoxnobylock(String lockid) {

        List<LockInfo> los = hibernateTemplate.find("from LockInfo l where l.lock_code=? ", lockid);
        if (los.size() > 0) {
            return los.get(0).getBoxInfo().getBox_no();
        } else {
            return "";
        }
    }

    public void savephonelog(PhoneOpenLog p) {

        hibernateTemplate.save(p);

    }


    //添加钥匙类型
    public void addKeyType(KeyTypeInfo li) {
        hibernateTemplate.save(li);
    }

    //获取钥匙列表
    public List<KeyInfo> findKeyInfoByPager(KeyInfoSearch ds) {
        StringBuffer sb = new StringBuffer();
        sb.append("from KeyInfo d where 0=0 ");
        if (ds.getKey_no() != null && !ds.getKey_no().equals("")) {
            sb.append(" and d.key_no like '%" + ds.getKey_no() + "%'");
        }
        if (ds.getRfid() != null && !ds.getRfid().equals("")) {
            sb.append(" and d.rfid like '%" + ds.getRfid() + "%'");
        }
        if (ds.getKey_code() != null && !ds.getKey_code().equals("")) {
            sb.append(" and d.key_code like '%" + ds.getKey_code() + "%'");
        }
        if (ds.getDepartment_name() != null && !ds.getDepartment_name().equals("")) {
            sb.append(" and d.department.name like '%" + ds.getDepartment_name() + "%'");
        }
        if (ds.getKey_type() != null && !ds.getKey_type().equals("")) {
            sb.append(" and d.keyTypeInfo.type like '%" + ds.getKey_type() + "%'");
        }
        if (ds.getOp_no() != null && !ds.getOp_no().equals("")) {
            sb.append(" and d.operators.op_no like '%" + ds.getOp_no() + "%'");
        }
        if (ds.getOp_name() != null && !ds.getOp_name().equals("")) {
            sb.append(" and d.operators.name like '%" + ds.getOp_name() + "%'");
        }
        if (ds.getAuth_boxes_count() > 0) {
            sb.append(" and d.auth_boxes_count = " + ds.getAuth_boxes_count() + "%'");
        }
        if (ds.getAuth_boxes_count() > 0) {
            sb.append(" and d.auth_boxes_count = " + ds.getAuth_boxes_count() + "%'");
        }
        if (ds.getSort() != null && !ds.getSort().equals("")) {
            sb.append(" order by d." + ds.getSort() + " " + ds.getOrder() + " ");
        }
        List<KeyInfo> d = this.getListForPage(sb.toString(), (ds.getPage() - 1) * ds.getRows(), ds.getRows());
        return d;
    }

    //获取钥匙类型列表
    public List<KeyTypeInfo> findKeyTypeInfoByPager(KeyTypeInfoSearch ds) {
        StringBuffer sb = new StringBuffer();
        sb.append("from KeyTypeInfo d where 0=0 ");
        if (ds.getSort() != null && !ds.getSort().equals("")) {
            sb.append(" order by d." + ds.getSort() + " " + ds.getOrder() + " ");
        }
        List<KeyTypeInfo> d = this.getListForPage(sb.toString(), (ds.getPage() - 1) * ds.getRows(), ds.getRows());
        return d;
    }

    //获取所有钥匙类型
    public List<KeyTypeInfo> findAllKeyType() {
        List<KeyTypeInfo> ds = hibernateTemplate.find("FROM KeyTypeInfo d order by d.type");
        return ds;
    }

    //获取所有钥匙
    public List<KeyInfo> findAllKey() {
        List<KeyInfo> ds = hibernateTemplate.find("FROM KeyInfo k order by k.key_no");
        return ds;
    }


    //获取钥匙数量
    public int findKeyCounts(KeyInfoSearch ds) {
        SessionFactory sf = hibernateTemplate.getSessionFactory();
        StringBuffer sb = new StringBuffer();
        sb.append("select count(*) from KeyInfo d where 0=0 ");
        if (ds.getKey_no() != null && !ds.getKey_no().equals("")) {
            sb.append(" and d.key_no like '%" + ds.getKey_no() + "%'");
        }
        if (ds.getRfid() != null && !ds.getRfid().equals("")) {
            sb.append(" and d.rfid like '%" + ds.getRfid() + "%'");
        }
        if (ds.getKey_code() != null && !ds.getKey_code().equals("")) {
            sb.append(" and d.key_code like '%" + ds.getKey_code() + "%'");
        }
        if (ds.getDepartment_name() != null && !ds.getDepartment_name().equals("")) {
            sb.append(" and d.department.name like '%" + ds.getDepartment_name() + "%'");
        }
        if (ds.getKey_type() != null && !ds.getKey_type().equals("")) {
            sb.append(" and d.keyTypeInfo.type like '%" + ds.getKey_type() + "%'");
        }
        if (ds.getOp_no() != null && !ds.getOp_no().equals("")) {
            sb.append(" and d.operators.op_no like '%" + ds.getOp_no() + "%'");
        }
        if (ds.getOp_name() != null && !ds.getOp_name().equals("")) {
            sb.append(" and d.operators.name like '%" + ds.getOp_name() + "%'");
        }
        if (ds.getAuth_boxes_count() > 0) {
            sb.append(" and d.auth_boxes_count = " + ds.getAuth_boxes_count() + "%'");
        }
        Session session = sf.getCurrentSession();
        Query q = session.createQuery(sb.toString());
        Long count = (Long) q.uniqueResult();
        session.close();
        return count.intValue();
    }

    //获取钥匙类型数量
    public int findKeyTypeCounts() {
        SessionFactory sf = hibernateTemplate.getSessionFactory();
        Session session = sf.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query q = session.createQuery("select count(*) from KeyTypeInfo d");
        Long count = (Long) q.uniqueResult();
        tx.commit();
        session.close();
        return count.intValue();
    }


    //删除钥匙授权
    public void deleteGrantLog(int[] ids) {
        for (int i = 0; i < ids.length; i++) {
            GrantLog gl = this.hibernateTemplate.get(GrantLog.class, ids[i]);
            KeyInfo key = this.hibernateTemplate.get(KeyInfo.class, gl.getKey().getId());
            key.setOperators(null);
            key.setAuth_boxes_count(0);
            this.hibernateTemplate.update(key);
            this.hibernateTemplate.delete(gl);
        }
    }

    //获取钥匙授权列表
    public List<GrantLog> findGrantLogByPager(GrantLogSearch ds) {
        StringBuffer sb = new StringBuffer();
        sb.append("from GrantLog d where 0=0 ");
        if (ds.getLog_name() != null && !ds.getLog_name().equals("")) {
            sb.append(" and d.log_name like '%" + ds.getLog_name() + "%' ");
        }
        if (ds.getGrant_user() != null && !ds.getGrant_user().equals("")) {
            sb.append(" and d.grant_user like '%" + ds.getGrant_user() + "%' ");
        }
        if (ds.getKey_no() != null && !ds.getKey_no().equals("")) {
            sb.append(" and d.key.key_no like '%" + ds.getKey_no() + "%' ");
        }
        if (ds.getKey_code() != null && !ds.getKey_code().equals("")) {
            sb.append(" and d.key.key_code like '%" + ds.getKey_code() + "%' ");
        }
        if (ds.getOperator_name() != null && !ds.getOperator_name().equals("")) {
            sb.append(" and d.operators.name like '%" + ds.getOperator_name() + "%' ");
        }
        if (ds.getSort() != null && !ds.getSort().equals("")) {
            sb.append(" order by d." + ds.getSort() + " " + ds.getOrder() + " ");
        } else {
            sb.append(" order by d.grant_time " + ds.getOrder() + " ");
        }
        List<GrantLog> d = this.getListForPage(sb.toString(), (ds.getPage() - 1) * ds.getRows(), ds.getRows());
        return d;
    }


    //获取钥匙授权信息
    public GrantLog findGrantLogById(int id) {
        return this.hibernateTemplate.get(GrantLog.class, id);
    }


    //获取钥匙授权信息bykeyid
    public List<GrantLog> findGrantLogBykeyId(String keyno) {
        StringBuffer sb = new StringBuffer();
        sb.append("from GrantLog d where d.key.key_code ='" + keyno + "'  ");

        List<GrantLog> d = hibernateTemplate.find(sb.toString());
        return d;
    }

    //获取钥匙授权信息byoperatorid
    public List<GrantLog> findGrantLogByOperatorId(int operatorid) {
        StringBuffer sb = new StringBuffer();
        sb.append("from GrantLog d where d.operators.id ='" + operatorid + "' and d.auth_type=2 order by d.id desc");
        List<GrantLog> d = hibernateTemplate.find(sb.toString());
        return d;
    }

    //通过锁码获取光交箱信息bylockcode
    public List<LockInfo> findBoxInfoByLockCode(String lockcode) {
        StringBuffer sb = new StringBuffer();
        sb.append("from LockInfo d where d.lock_code ='" + lockcode + "' ");
        List<LockInfo> d = hibernateTemplate.find(sb.toString());
        return d;
    }

    //通过grantlogid和boxid获取授权详情
    public List<GrantDetail> findGrantDetailBylogidAndboxid(int logid, int boxid) {
        StringBuffer sb = new StringBuffer();
        sb.append("from GrantDetail d where d.boxInfo.id ='" + boxid + "' " + "and d.grantLog.id ='" + logid + "' ");
        List<GrantDetail> d = hibernateTemplate.find(sb.toString());
        return d;
    }

    //添加cellgrantlog记录
    public void addCellGrantLog(CellGrantLog cg) {
        hibernateTemplate.save(cg);
    }

    //获取cellgrantlog记录
    public CellGrantLog findCellGrantLog(int id) {
        return hibernateTemplate.get(CellGrantLog.class, id);
    }

    //修改cellgrantlog记录
    public void updateCellGrantLog(CellGrantLog cg) {
        this.hibernateTemplate.update(cg);
    }

    //删除cellgrantlog记录
    public void deleteCellGrantLog(int id) {
        this.hibernateTemplate.bulkUpdate("delete from CellGrantLog cg where cg.id = " + id + "");
    }

    //获取钥匙授权数量
    public int findGrantLogCounts(GrantLogSearch ds) {
        SessionFactory sf = hibernateTemplate.getSessionFactory();
        StringBuffer sb = new StringBuffer();
        sb.append("select count(*) from GrantLog d where 0=0 ");

        if (ds.getLog_name() != null && !ds.getLog_name().equals("")) {
            sb.append(" and d.log_name like '%" + ds.getLog_name() + "%' ");
        }
        if (ds.getGrant_user() != null && !ds.getGrant_user().equals("")) {
            sb.append(" and d.grant_user like '%" + ds.getGrant_user() + "%' ");
        }
        if (ds.getKey_no() != null && !ds.getKey_no().equals("")) {
            sb.append(" and d.key.key_no like '%" + ds.getKey_no() + "%' ");
        }
        if (ds.getKey_code() != null && !ds.getKey_code().equals("")) {
            sb.append(" and d.key.key_code like '%" + ds.getKey_code() + "%' ");
        }
        if (ds.getOperator_name() != null && !ds.getOperator_name().equals("")) {
            sb.append(" and d.operators.name like '%" + ds.getOperator_name() + "%' ");
        }
        Session session = sf.getCurrentSession();
        Query q = session.createQuery(sb.toString());
        Long count = (Long) q.uniqueResult();
        session.close();
        return count.intValue();
    }

    //分页工具
    public List getListForPage(final String hql, final int offset,
                               final int length) {
        List list = hibernateTemplate.executeFind(new HibernateCallback() {
            @Override
            public Object doInHibernate(org.hibernate.Session session)
                    throws HibernateException, SQLException {
                // TODO Auto-generated method stub
                Query query = session.createQuery(hql);
                query.setFirstResult(offset);
                query.setMaxResults(length);
                List list = query.list();
                session.close();
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

}
