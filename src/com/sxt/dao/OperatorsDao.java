package com.sxt.dao;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.sxt.po.Department;
import com.sxt.po.Operators;
import com.sxt.po.User;

@Component("operatorsDao")
public class OperatorsDao {
	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public List<Operators> findByPager(int pageIndex, int pageSize){
		List<Operators> operators = this.getListForPage("from Operators o order by o.department.name", (pageIndex - 1) * pageSize, pageSize);
		return operators;
	}
	
	public Operators find(int id)
	{
		return hibernateTemplate.get(Operators.class, id);
	}
	
	public List<Operators> findAll()
	{
		List<Operators> operators = hibernateTemplate.find("from Operators o order by o.name");
		return operators;
	}
	
	public void add(Operators o)
	{
		hibernateTemplate.save(o);
		throw new RuntimeException();
	}
	
	public void update(Operators o){
		hibernateTemplate.update(o);
	}
	
	public void delete(Operators o) {
		hibernateTemplate.delete(o);
	}
	
	public int findCounts()
	{
		SessionFactory sf = hibernateTemplate.getSessionFactory(); 
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		Query q = session.createQuery("select count(*) from Operators o");
		Long count = (Long)q.uniqueResult();
		session.close();
		return count.intValue();
	}
	
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
