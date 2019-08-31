package com.sxt.dao;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.sxt.po.Department;
import com.sxt.po.DepartmentSearch;
import com.sxt.po.KeyInfo;
import com.sxt.po.LockInfo;
import com.sxt.po.Operators;
import com.sxt.po.User;

@Component("departmentDao")
public class DepartmentDao {
	@Resource
	private HibernateTemplate hibernateTemplate;
	
	//添加部门
	public void add(Department d){
		System.out.println("DepartmentDao.add()");
		hibernateTemplate.save(d);
	}
	
	//获取部门分页
	public List<Department> findByPager(DepartmentSearch ds){
		StringBuffer sb = new StringBuffer();
		sb.append("from Department d where 0=0 ");
		if (ds.getName() != null && ! ds.getName().equals(""))
		{
			sb.append("and d.name like '%" + ds.getName() + "%' ");
		}
		if (ds.getFull_name() != null && ! ds.getFull_name().equals(""))
		{
			sb.append("and d.full_name like '%" + ds.getFull_name() + "%' ");
		}
		if (ds.getContact() != null && ! ds.getContact().equals(""))
		{
			sb.append("and d.contact like '%" + ds.getContact() + "%' ");
		}
		if (ds.getParent_name() != null && ! ds.getParent_name().equals(""))
		{
			sb.append("and d.parent.name like '%" + ds.getParent_name() + "%' ");
		}
		if (ds.getSort() != null && ! ds.getSort().equals(""))
		{
			sb.append("order by d." + ds.getSort() + " " + ds.getOrder() + " ");
		}
		System.out.println(sb.toString());
		List<Department> d = this.getListForPage(sb.toString(), (ds.getPage() - 1) * ds.getRows(), ds.getRows());
		return d;
	}
	
	//获取部门详情
	public Department find(int id)
	{
		return hibernateTemplate.get(Department.class, id);
	}
	
	//获取所有部门
	public List<Department> findAll(){

		System.out.println("DepartmentDao.findAll()");
		List<Department> ds = hibernateTemplate.find("FROM Department d where d.parent.id IS NULL");
		return ds;
	}
	
	
	//获取所有部门byly
		public List<Department> findAlldep(){

			System.out.println("DepartmentDao.findAll()");
			List<Department> ds = hibernateTemplate.find("FROM Department d where d.is_deleted = 0 and  type = 1");
			return ds;
		}
	
	//修改部门信息
	public void update(Department o){
		hibernateTemplate.update(o);
	}
	
	
	//批量删除部门
	//删除的时候还需要考虑其他地方有department外键的情况,要置空 NULL
	public void delete(int[] ids) {
		for (int i = 0; i < ids.length; i ++)
		{
			Department d = this.hibernateTemplate.get(Department.class, ids[i]);
			List<BoxInfo> bis = this.hibernateTemplate.find("from BoxInfo b where b.department.id = ?", ids[i]);
			for (int j = 0; j < bis.size(); j ++)
			{
				bis.get(j).setDepartment(null);
				this.hibernateTemplate.update(bis.get(j));
			}
			List<KeyInfo> kis = this.hibernateTemplate.find("from KeyInfo k where k.department.id = ?", ids[i]);
			for (int j = 0; j < kis.size(); j ++)
			{
				kis.get(j).setDepartment(null);
				this.hibernateTemplate.update(kis.get(j));
			}
			
			List<Operators> os = this.hibernateTemplate.find("from Operators o where o.department.id = ?", ids[i]);
			for (int j = 0; j < os.size(); j ++)
			{
				os.get(j).setDepartment(null);
				this.hibernateTemplate.update(os.get(j));
			}
			hibernateTemplate.delete(d);
		}
	}
	
	//获取部门数量
	public int findCounts(DepartmentSearch ds)
	{
		SessionFactory sf = hibernateTemplate.getSessionFactory(); 
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from Department d where 0=0 ");
		if (ds.getName() != null && ! ds.getName().equals(""))
		{
			sb.append(" and d.name like '%" + ds.getName() + "%' ");
		}
		if (ds.getFull_name() != null && ! ds.getFull_name().equals(""))
		{
			sb.append(" and d.full_name like '%" + ds.getFull_name() + "%' ");
		}
		if (ds.getContact() != null && ! ds.getContact().equals(""))
		{
			sb.append(" and d.contact like '%" + ds.getContact() + "%' ");
		}
		if (ds.getParent_name() != null && ! ds.getParent_name().equals(""))
		{
			sb.append("and d.parent.name like '%" + ds.getParent_name() + "%' ");
		}
		Session session = sf.getCurrentSession();
		Query q = session.createQuery(sb.toString());
		Long count = (Long)q.uniqueResult();
		session.close();
		return count.intValue();
	}
	
	//分页模板
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
