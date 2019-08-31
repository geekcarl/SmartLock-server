package com.sxt.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.sxt.po.Core;
import com.sxt.po.CoreUsed;
import com.sxt.po.Opticalcable;
import com.sxt.po.OpticalcableCore;
import com.sxt.po.OpticalcableCoreTree;
import com.sxt.po.OpticalcableSearch;
import com.sxt.po.Pager;

@Component("opticalcableDao")
public class OpticalcableDao {
	
	private HibernateTemplate hibernateTemplate;
	
	//添加光缆
	public void add(Opticalcable b){
		int i;
		String name;
		name = (b.getStart_box() == null ? b.getStartAddress() : b.getStart_box().getBox_name()) + "_" + (b.getEnd_box() == null ? b.getEndAddress() : b.getEnd_box().getBox_name());
		b.setName(name);
		hibernateTemplate.save(b);
		if (b.getCoreCounts() > 0)
		{
			for (i = 0; i < b.getCoreCounts(); i ++)
			{
				Core core = new Core();
				core.setOpticalcable(b);
				core.setSequence(i + 1);
				core.setName((b.getStart_box() == null ? b.getStartAddress() : b.getStart_box().getBox_name()) + "_" + (b.getEnd_box() == null ? b.getEndAddress() : b.getEnd_box().getBox_name()) + "_纤芯_" + (i + 1));
				this.hibernateTemplate.save(core);
				CoreUsed cu = new CoreUsed();
				cu.setCore(core);
				this.hibernateTemplate.save(cu);
			}
		}
	}
	
	//光缆分页
	public List<Opticalcable> findByPager(OpticalcableSearch bis){
		StringBuffer sb = new StringBuffer();
		sb.append("from Opticalcable b where 0=0 ");
		if (bis.getName() != null && ! bis.getName().equals(""))
		{
			sb.append(" and b.name like '%" + bis.getName() + "%'");
		}
		if (bis.getStartAddress() != null && ! bis.getStartAddress().equals(""))
		{
			sb.append(" and b.startAddress like '%" + bis.getStartAddress() + "%'");
		}
		if (bis.getStartBoxName() != null && ! bis.getStartBoxName().equals(""))
		{
			sb.append(" and b.start_box.box_name like '%" + bis.getStartBoxName() + "%'");
		}
		if (bis.getEndAddress() != null && ! bis.getEndAddress().equals(""))
		{
			sb.append(" and b.endAddress like '%" + bis.getEndAddress() + "%'");
		}
		if (bis.getEndBoxName() != null && ! bis.getEndBoxName().equals(""))
		{
			sb.append(" and b.end_box.box_name like '%" + bis.getEndBoxName() + "%'");
		}
		if (bis.getType() != null && ! bis.getType().equals(""))
		{
			sb.append(" and b.type like '%" + bis.getType() + "%'");
		}
		if (bis.getSort() != null && ! bis.getSort().equals(""))
		{
			sb.append(" order by b." + bis.getSort() + " " + bis.getOrder() + " ");
		}
		List<Opticalcable> b = this.getListForPage(sb.toString(), (bis.getPage() - 1) * bis.getRows(), bis.getRows());
		return b;
	}
	
	//铅芯分页
	public List<CoreUsed> findCoreByPager(Pager bis){
		StringBuffer sb = new StringBuffer();
		sb.append("from CoreUsed c where 0=0 ");
		if (bis.getId() > 0)
		{
			sb.append(" and c.core.opticalcable.id = " + bis.getId() + "");
		}
		sb.append(" order by c.core.sequence ");
		List<CoreUsed> b = this.getListForPage(sb.toString(), (bis.getPage() - 1) * bis.getRows(), bis.getRows());
		return b;
	}
	
	//获取全部光缆
	public List<Opticalcable> findAll(){
		String sql = "from Opticalcable b";
		List<Opticalcable> b = this.hibernateTemplate.find(sql);
		return b;
	}
	
	//获取全部光缆
	public List<Opticalcable> findAllByBoxId(int id){
		String sql = "from Opticalcable b where b.start_box.id = ? or b.end_box.id = ?";
		List<Opticalcable> b = this.hibernateTemplate.find(sql, id, id);
		return b;
	}
	
	//获取全部光缆和纤芯
	public List<OpticalcableCoreTree> findAllTreeByBoxId(int id){
		String sql = "from Opticalcable b where b.start_box.id = ? or b.end_box.id = ?";
		List<Opticalcable> b = this.hibernateTemplate.find(sql, id, id);
		List<OpticalcableCoreTree> o = new ArrayList<OpticalcableCoreTree>();
		for (Opticalcable t : b)
		{
			OpticalcableCoreTree temp = new OpticalcableCoreTree();
			temp.setId(t.getId());
			temp.setCoreCounts(t.getCoreCounts());
			temp.setEnd_box(t.getEnd_box());
			temp.setEndAddress(t.getEndAddress());
			temp.setName(t.getName());
			temp.setRemarks(t.getRemarks());
			temp.setStart_box(t.getStart_box());
			temp.setStartAddress(t.getStartAddress());
			temp.setType(t.getType());
			List<CoreUsed> cs = this.hibernateTemplate.find("from CoreUsed c where c.core.opticalcable.id = ?", t.getId());
			temp.setCores(cs);
			o.add(temp);
		}
		return o;
	}
	
	
	//findAllOPCoreByBoxId
	 public List<OpticalcableCore> findAllOPCoreByBoxId(int id){
		String sql = "from Opticalcable b where b.start_box.id = ? or b.end_box.id = ?";
		List<Opticalcable> b = this.hibernateTemplate.find(sql, id, id);
		List<OpticalcableCore> o = new ArrayList<OpticalcableCore>();
		for (Opticalcable t : b)
		{
			OpticalcableCore temp = new OpticalcableCore();
			temp.setId(t.getId());
			temp.setCoreCounts(t.getCoreCounts());
			temp.setEndAddress(t.getEndAddress());
			temp.setName(t.getName());
			temp.setRemarks(t.getRemarks());
			temp.setStartAddress(t.getStartAddress());
			temp.setType(t.getType());
			List<CoreUsed> cs = this.hibernateTemplate.find("from CoreUsed c where c.core.opticalcable.id = ?", t.getId());
			temp.setCores(cs);
			o.add(temp);
		}
		return o;
	 }
	
	
	
	//获取光缆全部纤芯
	public List<CoreUsed> findAllCore(int id){
		String sql = "from CoreUsed c where c.core.opticalcable.id = ?";
		List<CoreUsed> c = this.hibernateTemplate.find(sql, id);
		return c;
	}
	
	//获取光缆全部可用纤芯
	public List<CoreUsed> findAllCoreByBoxId(int boxid, int optiid){
		String sql = "from CoreUsed c where c.core.opticalcable.id = ? and c.a_type = 0 and c.core.opticalcable.start_box.id = ?";
		List<CoreUsed> c1 = this.hibernateTemplate.find(sql, optiid, boxid);
		sql = "from CoreUsed c where c.core.opticalcable.id = ? and c.z_type = 0 and c.core.opticalcable.end_box.id = ?";
		List<CoreUsed> c2 = this.hibernateTemplate.find(sql, optiid, boxid);
		c1.addAll(c2);
		return c1;
	}
	
	//获取某光缆
	public Opticalcable find(int id)
	{
		return hibernateTemplate.get(Opticalcable.class, id);
	}
	
	//修改光缆信息
	public void update(Opticalcable b){
		int i;
		String name;
		name = (b.getStart_box() == null ? b.getStartAddress() : b.getStart_box().getBox_name()) + "_" + (b.getEnd_box() == null ? b.getEndAddress() : b.getEnd_box().getBox_name());
		b.setName(name);
		hibernateTemplate.update(b);
		this.hibernateTemplate.flush();
		this.hibernateTemplate.clear();
		if (b.getCoreCounts() > 0)
		{
			List<Core> cs = this.hibernateTemplate.find("from Core c where c.opticalcable.id = ?", b.getId());
			if (cs.size() > 0)
			{
				for (i = 0; i < cs.size(); i ++)
				{
					Core core = cs.get(i);
					core.setName((b.getStart_box() == null ? b.getStartAddress() : b.getStart_box().getBox_name()) + "_" + (b.getEnd_box() == null ? b.getEndAddress() : b.getEnd_box().getBox_name()) + "_纤芯_" + core.getSequence());
					this.hibernateTemplate.update(core);
				}
			}
		}
	}
	
	//删除光缆
	public void delete(int[] ids) {
		for (int i = 0; i < ids.length; i ++)
		{
			Opticalcable o = hibernateTemplate.get(Opticalcable.class, ids[i]);
			this.hibernateTemplate.bulkUpdate("update BoxTerminalUsed bt set bt.backUsed = 0, backCore = NULL where bt.backCore.opticalcable.id = ?", ids[i]);
			this.hibernateTemplate.delete(o);
		}
	}
	
	//获取数量
	public int findCounts(OpticalcableSearch bis)
	{
		StringBuffer sb = new StringBuffer();
		SessionFactory sf = hibernateTemplate.getSessionFactory(); 
		Session session = sf.getCurrentSession();
		Long count = new Long(0);
		try
		{
			sb.append("select count(*) from Opticalcable b where 0=0 ");
			if (bis.getName() != null && ! bis.getName().equals(""))
			{
				sb.append(" and b.name like '%" + bis.getName() + "%'");
			}
			if (bis.getStartAddress() != null && ! bis.getStartAddress().equals(""))
			{
				sb.append(" and b.startAddress like '%" + bis.getStartAddress() + "%'");
			}
			if (bis.getStartBoxName() != null && ! bis.getStartBoxName().equals(""))
			{
				sb.append(" and b.start_box.box_name like '%" + bis.getStartBoxName() + "%'");
			}
			if (bis.getEndAddress() != null && ! bis.getEndAddress().equals(""))
			{
				sb.append(" and b.endAddress like '%" + bis.getEndAddress() + "%'");
			}
			if (bis.getEndBoxName() != null && ! bis.getEndBoxName().equals(""))
			{
				sb.append(" and b.end_box.box_name like '%" + bis.getEndBoxName() + "%'");
			}
			if (bis.getType() != null && ! bis.getType().equals(""))
			{
				sb.append(" and b.type like '%" + bis.getType() + "%'");
			}
			Query q = session.createQuery(sb.toString());
			count = (Long)q.uniqueResult();
		}
		finally
		{
			session.close();
		}
		return count.intValue();
	}
	
	//获取铅芯分页数量
	public int findCoreCounts(Pager bis)
	{
		StringBuffer sb = new StringBuffer();
		SessionFactory sf = hibernateTemplate.getSessionFactory(); 
		Session session = sf.getCurrentSession();
		Long count = new Long(0);
		try
		{
			sb.append("select count(*) from CoreUsed b where 0=0 ");
			if (bis.getId() > 0)
			{
				sb.append(" and b.core.opticalcable.id = " + bis.getId() + "");
			}
			Query q = session.createQuery(sb.toString());
			count = (Long)q.uniqueResult();
		}
		finally
		{
			session.close();
		}
		return count.intValue();
	}
	
	public List getListForPage(final String hql, final int offset,
			final int length) {
		List list = hibernateTemplate.executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(org.hibernate.Session session)
					throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				List list = null;
				try
				{
					Query query = session.createQuery(hql);
					query.setFirstResult(offset);
					query.setMaxResults(length);
					list = query.list();
				}
				finally
				{
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

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
}
