package com.sxt.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.sxt.po.AlarmOrderDetails;
import com.sxt.po.AtomType;
import com.sxt.po.BoxGlobals;
import com.sxt.po.BoxImages;
import com.sxt.po.BoxInfo;
import com.sxt.po.BoxInfoModify;
import com.sxt.po.BoxInfoSearch;
import com.sxt.po.BoxModule;
import com.sxt.po.BoxModuleNew;
import com.sxt.po.BoxPrivilege;
import com.sxt.po.BoxSettings;
import com.sxt.po.BoxStates;
import com.sxt.po.BoxTerminal;
import com.sxt.po.BoxTerminalNew;
import com.sxt.po.BoxTerminalUsed;
import com.sxt.po.BoxTerminalUsedForXQ;
import com.sxt.po.BoxTerminalUsedForXQNew;
import com.sxt.po.BoxTerminalUsedNew;
import com.sxt.po.Core;
import com.sxt.po.CoreAtomType;
import com.sxt.po.CoreUsed;
import com.sxt.po.DTO_BoxStates_Values_Levels;
import com.sxt.po.Department;
import com.sxt.po.JumpCore;
import com.sxt.po.LabelInfo;
import com.sxt.po.LockInfo;
import com.sxt.po.ModificationInfo;
import com.sxt.po.Opticalcable;
import com.sxt.po.OrderInfactNew;
import com.sxt.po.RepairRecord;
import com.sxt.po.WorkOrder;
import com.sxt.po.WorkOrderImage;
import com.sxt.po.WorkOrderNew;
import com.sxt.service.BoxInfoService;

@Component("boxInfoDao")
public class BoxInfoDao {
	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public String findBoxnobyLockCode(String lockcode){
		
		
		 List<LockInfo> lolst = hibernateTemplate.find("from LockInfo l where l.lock_code=? ",lockcode);
	 		
   	 if (lolst.size() > 0)
		{
			return lolst.get(0).getBoxInfo().getBox_no();
		}
		else
		{
			return "";
		}
	}
	
	
	public String findBoxaddressbyLockCode(String lockcode){
		
		
		 List<LockInfo> lolst = hibernateTemplate.find("from LockInfo l where l.lock_code=? ",lockcode);
	 		
  	 if (lolst.size() > 0)
		{
			return lolst.get(0).getBoxInfo().getAddress();
		}
		else
		{
			return "";
		}
	}
	
	//获取光交箱全局设置值
	public BoxGlobals getBoxGlobals()
	{
		BoxGlobals bg = null;
		List<BoxGlobals> bgs = this.hibernateTemplate.find("from BoxGlobals bg");
		if (bgs.size() > 0)
		{
			bg = bgs.get(0);
		}
		return bg;
	}
	//添加光交箱设置值
	public void addBoxSettings(BoxSettings bs){
		hibernateTemplate.save(bs);
	}
	
	//添加光交箱下发值
	public void addModificationInfo(ModificationInfo mi){
		hibernateTemplate.save(mi);
	}
	//添加光交箱
	public void add(BoxInfo b){
		System.out.println("BoxInfoDao.add()");
		int i, j, k;
		hibernateTemplate.save(b);
		BoxInfo temp = new BoxInfo();
		temp.setId(b.getId());
		if (b.getBoxModules() != null)
		{
			for (i = 0; i < b.getBoxModules().size(); i ++)
			{
				
				b.getBoxModules().get(i).setBoxInfo(temp);
				hibernateTemplate.save(b.getBoxModules().get(i));
				for (j = 0; j < b.getBoxModules().get(i).getRows(); j ++)
				{
					for (k = 0; k < b.getBoxModules().get(i).getCols(); k ++)
					{
						BoxTerminal bt = new BoxTerminal();
						bt.setBoxInfo(temp);
						bt.setBoxModule(b.getBoxModules().get(i));
						bt.setSideName(b.getBoxModules().get(i).getSideName());
						bt.setRow(j + 1);
						bt.setCol(k + 1);
						this.hibernateTemplate.save(bt);
					}
				}
			}
		}
		
		if (b.getBoxStates() != null)
		{
			for (i = 0; i < b.getBoxStates().size(); i ++) {
	
				Date date = new Date();
				b.getBoxStates().get(i).setBoxInfo(temp);
				b.getBoxStates().get(i).setTimestamp(date);
				this.hibernateTemplate.save(b.getBoxStates().get(i));
			}
		}
	}
	
	//addboximage
		public void addboximage(BoxImages b){
			try{
				
				hibernateTemplate.save(b);
			
			}catch(Exception ex)
			{
				System.out.println(ex.toString());
				
			}
		}
		
		
		
       public int getrecivedep(int boxid){
			
			
			List<BoxInfo> jcs = this.hibernateTemplate.find("from BoxInfo bt where bt.id = "+boxid+" and is_deleted=0");
			if (jcs.size() > 0)
			{
				Integer dep =  jcs.get(0).getWorkorder_department();
				
				if (dep!=null)
					return dep.intValue();
				else
					return 0;
			}else
				return 0;
			
			
		}
       
       
       public int getreciveuserid(int boxid){
			
			
			List<BoxInfo> jcs = this.hibernateTemplate.find("from BoxInfo bt where bt.id = "+boxid+" and is_deleted=0");
			if (jcs.size() > 0)
			{
				Integer dep =  jcs.get(0).getWorkorder_receive_id();
				
				if (dep!=null)
					return dep.intValue();
				else
					return 0;
			}else
				return 0;
			
			
		}
		
		
		//addworkorderimage
		public void addworkorderimage(WorkOrderImage b){
			try{
				
				hibernateTemplate.save(b);
			
			}catch(Exception ex)
			{
				System.out.println(ex.toString());
				
			}
		}
	
		
		public AtomType transferTQ(int teminalid){
			
			BoxTerminal bt = new BoxTerminal();
			AtomType at = new AtomType();
			bt = hibernateTemplate.get(BoxTerminal.class,teminalid);
			
			at.setTeminal_id(bt.getId());
			at.setRow(bt.getRow());
			at.setCol(bt.getCol());
			at.setSideName(bt.getSideName());
			
			return at;
		
		
		
		}
		
		//transferTQtoInt
          public int transferTQtoInt(AtomType teminal,int box_id){
			
        	int i = 0;
			
			StringBuffer sb = new StringBuffer();
			sb.append("from BoxTerminal b where sideName ='"+ teminal.getSideName() +"' and row = "+ teminal.getRow() +" and  col = "+ teminal.getCol() +"  and  box_id="+ box_id +" ");

			List<BoxTerminal> bs = hibernateTemplate.find(sb.toString());
			
			if(bs !=null)
			{
			
			for(BoxTerminal b :bs){
				
				i = b.getId();
				
			}
			
			}
			
			return i;
		
		}
		
		
//transferGL
       public CoreAtomType transferGL(int coreid){
			
			Core bt = new Core();
			CoreAtomType at = new CoreAtomType();
			bt = hibernateTemplate.get(Core.class,coreid);
			
			at.setCore_id(bt.getId());
			at.setCore_name(bt.getName());
			at.setOp_id(bt.getOpticalcable().getId());
			at.setOp_name(bt.getOpticalcable().getName());
			at.setSequence(bt.getSequence());
			
			return at;
		
		
		
		}
		
		
	//添加光交箱
		public void addboxinfo(BoxInfoModify b){
		
			try{
			
				hibernateTemplate.save(b);
				
				/*
				//保存动态信息
				BoxVarInfo bvi = new BoxVarInfo();
				bvi.setBoxInfo(b);
				this.hibernateTemplate.save(bvi);
				
				//保存设置值
				BoxGlobals bg = null;
				if (bio.getBox_setting_flag() != 0 || bio.getModificationInfo_flag() != 0)
				{
					bg = this.getBoxGlobals();
				}
				BoxSettings bs = new BoxSettings();
				bs.setBoxinfo(b);
				bs.setFlag(bio.getBox_setting_flag());
				if (bio.getBox_setting_flag() != 0 && bg != null)
				{
					bs.setHb_interval(bg.getHb_interval());
					bs.setVolt_threshold(bg.getVolt_threshold());
					bs.setAngle_threshold(bg.getAngle_threshold());
					bs.setHigh_t_threshold(bg.getHigh_t_threshold());
					bs.setLow_t_threshold(bg.getLow_t_threshold());
					bs.setLowpower_period(bg.getLowpower_period());
					bs.setLowpower_periodpercent(bg.getLowpower_periodpercent());
					bs.setShake_peroid(bg.getShake_peroid());
					bs.setShake_frequency(bg.getShake_frequency());
					bs.setShake_time(bg.getShake_time());
					bs.setUser(b.getLastedituser());
					bs.setLastedittime(new Date());
				}
				this.hibernateTemplate.save(bs);
				//保存下发设置
				ModificationInfo mi = new ModificationInfo();
				mi.setBoxinfo(b);
				mi.setFlag(bio.getModificationInfo_flag());
				if (bio.getModificationInfo_flag() != 0 && bg != null)
				{
					mi.setShake_threshold(bg.getShake_threshold());
					mi.setShake_rate(bg.getShake_rate());
					mi.setCenter_ip(bg.getCenter_ip());
					mi.setCenter_upd_port(bg.getCenter_upd_port());
					mi.setIs_send(0);
					mi.setUser(b.getLastedituser());
					mi.setLastedittime(new Date());
				}
				this.hibernateTemplate.save(mi);*/
			
			}catch(Exception ex)
			{
				System.out.println(ex.toString());
				
			}
			
		}
		
		
		//添加光交箱权限addBoxPrivilege
		public void addBoxPrivilege(BoxPrivilege b){
			
			try{
			
				hibernateTemplate.save(b);
			
			}catch(Exception ex)
			{
				System.out.println(ex.toString());
				
			}
			
			
		}
		
		public String findgrantBox(int userid){
			
			StringBuffer sb = new StringBuffer();
			sb.append("from BoxPrivilege b where user_id ="+ userid +"  ");
			
			List<BoxPrivilege> bs = hibernateTemplate.find(sb.toString());
			
			StringBuffer boxlst = new StringBuffer();
			if(bs!=null && bs.size()>0){
				
			for(BoxPrivilege b:bs){
				
				boxlst.append(b.getBox_id());
				boxlst.append(",");
				
		 	}
			 boxlst.delete(boxlst.length()-1, boxlst.length());
			
			}

			return boxlst.toString();
			
		}
		
		public boolean findbyno (String no){
			
		
				StringBuffer sb = new StringBuffer();
				sb.append("from BoxInfo b where box_no ='"+ no +"' and is_deleted = 0 ");

				List<BoxImages> bs = hibernateTemplate.find(sb.toString());
				
				if(bs.size() >0)
				   return true;
				
				else
				   return false;
		
		}
		
		public String getdepnamebyId(int depid){
			
			Department dep =hibernateTemplate.get(Department.class, depid);
			
			String depname = "";
			depname = dep.getFull_name();
			
			return depname;
			
		}
		
		
		public boolean findbyno (String no,int id){
			
			
			StringBuffer sb = new StringBuffer();
			sb.append("from BoxInfo b where box_no ='"+ no +"' and is_deleted = 0 and id <> "+id+"");

			List<BoxImages> bs = hibernateTemplate.find(sb.toString());
			
			if(bs.size() >0)
			   return true;
			
			else
			   return false;
		
	
	}
		
		//findbycontrollerid
		public boolean findbycontrollerid (long id){
			
			
			StringBuffer sb = new StringBuffer();
			sb.append("from BoxInfo b where controller_id = "+ id +" and is_deleted = 0 ");

			List<BoxInfo> bs = hibernateTemplate.find(sb.toString());
			
			if(bs != null && bs.size() >0)
			   return true;
			
			else
			   return false;
		
	     }
		

        public boolean findbycontrollerid (long id, int boxid){
			
			
			StringBuffer sb = new StringBuffer();
			sb.append("from BoxInfo b where controller_id = "+ id +" and is_deleted = 0 and id <> "+boxid+"");

			List<BoxInfo> bs = hibernateTemplate.find(sb.toString());
			
			if(bs != null && bs.size() >0)
			   return true;
			
			else
			   return false;
		
	     }
		
		
		//findimagebyTime
		public List<BoxImages>  findimagebyTime(int boxid, String picdate){
			
			try{
				StringBuffer sb = new StringBuffer();
				sb.append("from BoxImages b where box_id ="+boxid+"  and Date(updatetime) = '"+picdate+"'");
				
				System.out.println(sb.toString());
				List<BoxImages> bs = hibernateTemplate.find(sb.toString());
				return bs;
				
			}catch(Exception ex){
					return null;
					
				}
				
			
		}
		//findimagebyId
           public List<BoxImages>  findimagebyId(int boxid){
			
		 	try{
		 		StringBuffer sb = new StringBuffer();
				sb.append("from BoxImages b where box_id ="+boxid+"");
				
				System.out.println(sb.toString());
				List<BoxImages> bs = hibernateTemplate.find(sb.toString());
				return bs;
				
		    	}catch(Exception ex){
					return null;
					
				}

		    }
		
	//findimagebyNum
	     public List<BoxImages>  findimagebyNum(int boxid, int num){
			
		try{		
	    
			 String sb ="select * from boximages b where b.box_id ="+boxid+" "+ " order by b.updatetime desc  limit "+ ""+num +"";
			 	
			 SQLQuery query  = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sb).addEntity(BoxImages.class);  
			 List<BoxImages> bs=query.list();
			 return bs;   
		}
		
		catch(Exception ex){
			
			
			return null;
		}
			
		}
	
		
		
	//分页查询
	public List<BoxInfo> findByPager(BoxInfoSearch bis){
		StringBuffer sb = new StringBuffer();
		sb.append("from BoxInfo b where 0=0 ");
		if (bis.getBox_no() != null && ! bis.getBox_no().equals(""))
		{
			sb.append("and b.box_no like '%" + bis.getBox_no() + "%' ");
		}
		if (bis.getBox_name() != null && ! bis.getBox_name().equals(""))
		{
			sb.append("and b.box_name like '%" + bis.getBox_name() + "%' ");
		}
		if (bis.getDepartment() != null)
		{
			sb.append("and b.department.id = " + bis.getDepartment().getId() + " ");
		}
		if (bis.getSim_phone_no() != null && ! bis.getSim_phone_no().equals(""))
		{
			sb.append("and b.sim_phone_no like '%" + bis.getSim_phone_no() + "%' ");
		}
		if (bis.getLocks_count() > 0)
		{
			sb.append("and b.locks_count = " + bis.getLocks_count() + " ");
		}
		if (bis.getBox_type() != null && ! bis.getBox_type().equals(""))
		{
			sb.append("and b.box_type like '%" + bis.getBox_type() + "%' ");
		}
		if (bis.getSort() != null && ! bis.getSort().equals(""))
		{
			sb.append("order by b." + bis.getSort() + " " + bis.getOrder() + " ");
		}
		System.out.println(sb.toString());
		List<BoxInfo> b = this.getListForPage(sb.toString(), (bis.getPage() - 1) * bis.getRows(), bis.getRows());
		for (int i = 0; i < b.size(); i ++)
		{
			List<BoxModule> bm = this.hibernateTemplate.find("from BoxModule bm where bm.boxInfo.id = " + b.get(i).getId());
			b.get(i).setBoxModules(bm);
		}
		return b;
	}
	
	//获取某光交箱
	public BoxInfo find(int id)
	{
		return hibernateTemplate.get(BoxInfo.class, id);
	}
	
	
	
	//获取某维修申请
		public RepairRecord findRepairRecord(int id)
		{
			return hibernateTemplate.get(RepairRecord.class, id);
		}
	
	
	//修改光交箱信息
	public void updateBoxinfo(BoxInfoModify b)
	{
		StringBuilder sb = new StringBuilder();
        sb.append("update BoxInfoModify b set ");
       

	 if (b.getBox_no()!=null && !b.getBox_no().equals(""))
     {
         sb.append("b.box_no ='"+ b.getBox_no() +"'");
     }
	 if (b.getDepartment_id()!=0)
     {
         sb.append(" , b.department_id ="+ b.getDepartment_id() +"");
     } 
	 if (b.getAddress()!=null && !b.getAddress().equals(""))
     {
         sb.append(" , b.address ='"+ b.getAddress() +"'");
     } 
	 if (b.getController_id()!=0 )
     {
         sb.append(" , b.controller_id ="+ b.getController_id() +"");
     } 
	 if (b.getBox_name()!=null && !b.getBox_name().equals(""))
     {
         sb.append(" , b.box_name ='"+ b.getBox_name() +"'");
     }
	 if (b.getSim_phone_no()!=null && !b.getSim_phone_no().equals(""))
     {
         sb.append("  , b.sim_phone_no ='"+ b.getSim_phone_no() +"'");
     }
	 if (b.getLongitude()!=0)
     {
         sb.append(" , b.longitude ='"+ b.getLongitude() +"'");
     } 
	 if (b.getLatitude()!=0 )
     {
         sb.append(" , b.latitude ='"+ b.getLatitude() +"'");
     } 
	 if (b.getB_longitude()!=0)
     {
         sb.append(" , b.b_longitude ='"+ b.getB_longitude() +"'");
     } 
	 if (b.getB_latitude()!=0 )
     {
         sb.append(" , b.b_latitude ='"+ b.getB_latitude() +"'");
     } 
	 if (b.getBox_type()!=null && !b.getBox_type().equals(""))
     {
         sb.append(" , b.box_type ='"+ b.getBox_type() +"'");
     }  
	 if (b.getLocks_count()!=0)
     {
         sb.append(" , b.locks_count ="+ b.getLocks_count() +"");
     } 
	 if (b.getK_code()!=null && !b.getK_code().equals(""))
     {
         sb.append(" , b.k_code ='"+ b.getK_code() +"'");
     } 
	 if (b.getRemarks()!=null && !b.getRemarks().equals(""))
     {
         sb.append(" , b.remarks ='"+ b.getRemarks() +"'");
     }
	 if (b.getUse_state()!=0)
     {
         sb.append(" , b.use_state ="+ b.getUse_state() +"");
     } 
	 sb.append(" where  b.id = " + b.getId());
	 this.hibernateTemplate.bulkUpdate(sb.toString());

		
	}
	
	//查询列表
	public List<BoxInfo> findAll(){
		
		try{
		StringBuffer sb = new StringBuffer();
		sb.append("from BoxInfo b where is_deleted=0  ");
		
		List<BoxInfo> bs = hibernateTemplate.find(sb.toString());
		return bs;
		}catch(Exception ex){
			return null;
			
		}
		

	}
	
	
	
	//findAllByGrant查找权限内的箱子
public List<BoxInfo> findAllByGrant(String boxlst){
		
		try{
		StringBuffer sb = new StringBuffer();
		sb.append("from BoxInfo b where is_deleted=0 ");
		
		if (boxlst != null && !boxlst.equals(""))
		{
			sb.append("and b.id in (" + boxlst + ") ");
			
			
		}
		else{
			
			return null;
		}
		
		List<BoxInfo> bs = hibernateTemplate.find(sb.toString());
		return bs;
		}catch(Exception ex){
			return null;
			
		}
		

	}
	
	
	//findbydep
	public List<BoxInfo> findbydep(int  id){
		
	    	StringBuffer sb = new StringBuffer();
		    sb.append("from BoxInfo b where 0=0 ");

			if (id != 0)
			{
				sb.append("and b.department.id = " + id + " ");
			}

		System.out.println(sb.toString());
		
		List<BoxInfo> bs = this.hibernateTemplate.find(sb.toString());
		return bs;

	}
	
	
	//查询列表editby ly 14.10.4
		public List<BoxInfo> findAllSimple(String bis){
			
			StringBuffer sb = new StringBuffer();
			sb.append("from BoxInfo b where is_deleted=0 ");
			if (bis != null)
			{
				
					sb.append("and (b.box_no like '%" + bis + "%' or address like '%" + bis + "%' or controller_id  like '%" + bis + "%')");
				
				
			}
			System.out.println(sb.toString());
			List<BoxInfo> bs = hibernateTemplate.find(sb.toString());
			return bs;
		}
		
		
		
	public List<BoxInfo> findAllSimple(String bis,String boxlst){
			
			StringBuffer sb = new StringBuffer();
			sb.append("from BoxInfo b where is_deleted=0 ");
			if (bis != null)
			{
				
					sb.append("and (b.box_no like '%" + bis + "%' or address like '%" + bis + "%' or controller_id  like '%" + bis + "%')  ");
				
				
			}
			
			if (boxlst != null && !boxlst.equals(""))
			{
				
					sb.append("  and b.id in ("+ boxlst +") ");
				
				
			}
			else{
				
				
				return null;
			}
			
			System.out.println(sb.toString());
			List<BoxInfo> bs = hibernateTemplate.find(sb.toString());
			return bs;
		}
		
		
		//findImage  14.10.5
           public List<BoxImages> findImage(String bis){
			
			StringBuffer sb = new StringBuffer();
			sb.append("from BoxImages b where 0=0 ");
			if (bis != null)
			{
				
					sb.append(bis);
				
				
			}
			System.out.println(sb.toString());
			List<BoxImages> bs = hibernateTemplate.find(sb.toString());
			return bs;
		}
		
	 
           //修改光交箱施工状态updateStatus editby ly 14.10.7
       	public void updateStatus(BoxInfo o){
       		StringBuilder sb = new StringBuilder();
            sb.append("update BoxInfo b set ");
           

    	 if (o.getUse_state()!=0)
         {
             sb.append("b.use_state ="+ o.getUse_state() +"");
         }
    	 if (o.getUse_begindate()!=null && !o.getUse_begindate().equals(""))
         {
             sb.append(" , b.use_begindate ='"+ o.getUse_begindate() +"'");
         } 
    	 if (o.getUse_enddate()!=null && !o.getUse_enddate().equals(""))
         {
             sb.append(" , b.use_enddate ='"+ o.getUse_enddate() +"'");
         } 
    	  
    	 
    	 sb.append(" where  b.id = " + o.getId());
    	 this.hibernateTemplate.bulkUpdate(sb.toString());	
    		
    	 
    	 //this.hibernateTemplate.bulkUpdate("update BoxInfo b set b.use_state ="+ o.getUse_state() +", b.use_begindate='"+o.getUse_begindate()+"', b.use_enddate='"+o.getUse_enddate()+"' where id = "+ o.getId() +"");
    		
       	}
       	
    	public void updateLabe(LabelInfo o){
       		StringBuilder sb = new StringBuilder();
            sb.append("update BoxTerminal b set b.label_info = '"+ o.getLabel_info() +"' ");
    	    sb.append(" where  b.id = " + o.getTeminalid());
    	    this.hibernateTemplate.bulkUpdate(sb.toString());	
    		
    	 
    	 //this.hibernateTemplate.bulkUpdate("update BoxInfo b set b.use_state ="+ o.getUse_state() +", b.use_begindate='"+o.getUse_begindate()+"', b.use_enddate='"+o.getUse_enddate()+"' where id = "+ o.getId() +"");
    		
       	}
       	
       	
      // 	updateWordOrderStatus
        //获取当前系统日期
	    public static String getDate() {
	    	 java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
	    	 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	    	 return formatter.format(date);
	    	    }
	    
       	//修改工单状态updateWordOrderStatus editby ly 14.10.24
       	public void updateWordOrderStatus(WorkOrder w){
       		StringBuilder sb = new StringBuilder();
            sb.append("update WorkOrder b set ");
           

    	 if (w.getDone_type()!=0)
         {
             sb.append("b.done_type ="+ w.getDone_type() +"");
             
             sb.append(", b.done_time = '"+ getDate() +"'");
         }
    	
 /*
    	 if (w.getUse_begindate()!=null && !o.getUse_begindate().equals(""))
         {
             sb.append(" , b.use_begindate ='"+ o.getUse_begindate() +"'");
         } 
    	 if (w.getUse_enddate()!=null && !o.getUse_enddate().equals(""))
         {
             sb.append(" , b.use_enddate ='"+ o.getUse_enddate() +"'");
         } */
    	  
    	 
    	 sb.append(" where  b.id = " + w.getId());
    	 this.hibernateTemplate.bulkUpdate(sb.toString());	
    		
    	 
    	 //this.hibernateTemplate.bulkUpdate("update BoxInfo b set b.use_state ="+ o.getUse_state() +", b.use_begindate='"+o.getUse_begindate()+"', b.use_enddate='"+o.getUse_enddate()+"' where id = "+ o.getId() +"");
    		
       	}
       	
       	
       	
       	//addorderinfact 增加工单资源实际使用表
       	public void addorderinfact(OrderInfactNew b){
    		
			try{
				List<OrderInfactNew> temp = new ArrayList<OrderInfactNew>();
				if(b.getOrder_type() ==2){  //跳纤
					
					temp = this.hibernateTemplate.find("from OrderInfactNew o where o.order_type = 2 and o.order_id = ? and  o.a_terminal_id = ? and o.z_terminal_id =? and o.box_id =? ", b.getOrder_id(), b.getA_terminal_id(),b.getZ_terminal_id(),b.getBox_id());
					if(temp.size() > 0)
					   return;
				
				}else if(b.getOrder_type() ==1){  //关联
					
					temp = this.hibernateTemplate.find("from OrderInfactNew o where o.order_type = 1 and o.order_id = ? and  o.terminal_id = ? and o.core_id =? and o.box_id =? ", b.getOrder_id(), b.getTerminal_id(),b.getCore_id(),b.getBox_id());
					if(temp.size() > 0)
						   return;
				}else if(b.getOrder_type() ==3){
					
					temp = this.hibernateTemplate.find("from OrderInfactNew o where o.order_type = 3 and o.order_id = ? and  o.a_core_id = ? and o.z_core_id =? and o.box_id =? ", b.getOrder_id(), b.getA_core_id(),b.getZ_core_id(),b.getBox_id());
					if(temp.size() > 0)
						   return;
				}
				
				if(temp.size()<=0)
				{ 
					hibernateTemplate.save(b);
				    
				}
			
			}catch(Exception ex)
			  {  
				System.out.println(ex.toString());
				
			}
			
			
		}
       	
       	
       //addrepairapp  增加维修申请
      	public void addrepairapp(RepairRecord b){
    		
    			try{
    			
    				hibernateTemplate.save(b);
    			
    			}catch(Exception ex)
    			{
    				System.out.println(ex.toString());
    				
    			}
    			
    			
    		}
      	
      	
      	//checkreapairapp
     	public boolean checkreapairapp(int userid, int boxid){
    		
			
     		StringBuffer sb = new StringBuffer();
			sb.append("from RepairRecord r where user_id ="+ userid +" and box_id="+boxid+" and app_result=0 and is_deleted = 0 ");

			List<RepairRecord> bs = hibernateTemplate.find(sb.toString());
			
			if(bs.size() >0)
				return false;
			else
				return true;
						
   
			
			
		}
     	
     	public List<RepairRecord>   findrepairrecord(int userid)
     	{
     		
     		StringBuffer sb = new StringBuffer();
			sb.append("from RepairRecord r where user_id ="+ userid +" and is_deleted = 0 ");

			List<RepairRecord> bs = hibernateTemplate.find(sb.toString());
			
     		return bs;
     		
     	}
     	
     	
     	public RepairRecord findrepairrecordbyid(int id){
     		
     		try
     		{
    			 
     			 return hibernateTemplate.get(RepairRecord.class, id);
    			 
     		 }
    			
     		 catch(Exception ex){
    				 
    				 
    				 return null;
    		}
     		
     	}
       	
       	//
     	//修改工单告警状态updateWordOrderAlarmStatus editby ly 14.10.24
       	public void updateWordOrderAlarmStatus(AlarmOrderDetails w){
       		StringBuilder sb = new StringBuilder();
            sb.append("update AlarmOrderDetails b set ");
           

    	 if (w.getDone_type()!=0)
         {
             sb.append("b.done_type ="+ w.getDone_type() +"");
             
             sb.append(", b.done_time = '"+ getDate() +"'");
         }
    	 
    	 
    	 sb.append(" where  b.id = " + w.getId());
    	 this.hibernateTemplate.bulkUpdate(sb.toString());	
    		
       	}
       	
       	
       	public void updateBoxState(int boxid,String normaltype,int statevalueid){
       		
       		StringBuilder sb = new StringBuilder();
            sb.append("update BoxStates b set b.state_value ='"+ normaltype +"'");
    	    sb.append(" where  b.boxInfo.id = " + boxid+" and b.stateValue.id="+ statevalueid+"");
    	    
    	    this.hibernateTemplate.bulkUpdate(sb.toString());
       		
       	}
       	
       	
       	public int getboxidbyAlarmStatus(int id){
       		
       		List<AlarmOrderDetails> jcs = this.hibernateTemplate.find("from AlarmOrderDetails bt where bt.id = "+id+" ");
			if (jcs.size() > 0)
			{
				Integer boxid =  jcs.get(0).getBoxinfo().getId();
				
				if (boxid!=null)
					return boxid.intValue();
				else
					return 0;
			}else
				return 0;
       	}
       	
       	
       	
       	public long getcontrolleridbyboxid(int boxid){
       		
       		List<BoxInfoModify> jcs = this.hibernateTemplate.find("from BoxInfoModify bt where bt.id = "+boxid+" ");
			if (jcs.size() > 0)
			{
				long controllerid =  jcs.get(0).getController_id();
				
			    return controllerid;
			}else
				return 0;
       		
       	}
       	
       	public void updatecabinet(long controllerid){
       		
       		
       		StringBuilder sb = new StringBuilder();
            sb.append("update Cabinet b set initializing_angles_switch = 1 ,initializing_angles_now = 1");
    	    sb.append(" where  b.device_id = " + controllerid);
    	    this.hibernateTemplate.bulkUpdate(sb.toString());
       	}
       	
       	public String getTypebyAlarmStatus(int id){
       		
       		
       		List<AlarmOrderDetails> jcs = this.hibernateTemplate.find("from AlarmOrderDetails bt where bt.id = "+id+" ");
			if (jcs.size() > 0)
			{
				String type =  jcs.get(0).getAlarm_type();

					return type;
			}else
				return "";
       		
       	}
       	
       	
       	public int getStatekeyid(int id){
       		
       		List<AlarmOrderDetails> jcs = this.hibernateTemplate.find("from AlarmOrderDetails bt where bt.id = "+id+" ");
			if (jcs.size() > 0)
			{
				Integer alarm_type_id =  jcs.get(0).getAlarm_type_id();
				
				if (alarm_type_id!=null)
					return alarm_type_id.intValue();
				else
					return 0;
			}else
				return 0;
       		
       	}
       	
     
	//修改光交箱
	public void update(BoxInfo o){
		hibernateTemplate.update(o);
	}
	
	//删除光交箱
	public void delete(BoxInfo o) {
		hibernateTemplate.delete(o);
	}
	
	
	//删除光交箱  -----假删
	public void deletebox(BoxInfo o) {
		StringBuilder sb = new StringBuilder();
        sb.append("update BoxInfoModify b set is_deleted = 1 ");
	    sb.append(" where  b.id = " + o.getId());
	 this.hibernateTemplate.bulkUpdate(sb.toString());
	 }
	
	//获取总数量
	public int findCounts(BoxInfoSearch bis)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from BoxInfo b where 0=0 ");
		if (bis != null)
		{
			if (bis.getBox_no() != null && ! bis.getBox_no().equals(""))
			{
				sb.append("and b.box_no like '%" + bis.getBox_no() + "%' ");
			}
			if (bis.getBox_name() != null && ! bis.getBox_name().equals(""))
			{
				sb.append("and b.box_name like '%" + bis.getBox_name() + "%' ");
			}
			if (bis.getDepartment() != null)
			{
				sb.append("and b.department.id = " + bis.getDepartment().getId() + " ");
			}
			if (bis.getSim_phone_no() != null && ! bis.getSim_phone_no().equals(""))
			{
				sb.append("and b.sim_phone_no like '%" + bis.getSim_phone_no() + "%' ");
			}
			if (bis.getLocks_count() > 0)
			{
				sb.append("and b.locks_count = " + bis.getLocks_count() + " ");
			}
			if (bis.getBox_type() != null && ! bis.getBox_type().equals(""))
			{
				sb.append("and b.box_type like '%" + bis.getBox_type() + "%' ");
			}
		}
		SessionFactory sf = hibernateTemplate.getSessionFactory(); 
		Session session = sf.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query q = session.createQuery(sb.toString());
		Long count = (Long)q.uniqueResult();
		tx.commit();
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
	

	//获取某光交箱的所有状态
		public List<BoxStates> findAllBoxStatesByAjax(int id) {
			return this.hibernateTemplate.find("from BoxStates bs where bs.boxInfo.id = " + id);
		}
		
		//获取所有光交箱状态
		public List<DTO_BoxStates_Values_Levels> findAllBoxStatesByAjax() {
			return this.hibernateTemplate.find("select new com.sxt.po.DTO_BoxStates_Values_Levels(bs.boxInfo.id, bs.stateKey.state_key, bs.stateValue.state_value, bs.stateValue.stateLevel.level, bs.stateValue.stateLevel.state_image) from BoxStates bs order by bs.stateValue.stateLevel.level");
		}
		
		//获取某光交箱所有模块
		public List<BoxModule> findBoxModuleById(int id) {
			
			
			return this.hibernateTemplate.find("from BoxModule bm where bm.boxInfo.id = " + id + " order by bm.sideName");
		}
		
		//获取某光交箱所有模块
		public List<BoxModuleNew> findBoxModuleByIdNew(int id) {
			
			List<BoxModuleNew> bms = this.hibernateTemplate.find("from BoxModuleNew bm where bm.box_id = " + id + " order by bm.sideName");
			List<BoxModuleNew> bmsnew = new ArrayList<BoxModuleNew>();
			
			if(bms!=null){
				for(BoxModuleNew t :bms){
					int count = getterminalRow(t.getBox_id(),t.getSideName(),t.getCols());
					t.setRows(count);
					bmsnew.add(t);
				}
			}
			 return bmsnew;
		
		}
		
		
		public int getterminalRow(int boxid, String sidename,int cols){
			
			StringBuffer sb = new StringBuffer();
			SessionFactory sf = hibernateTemplate.getSessionFactory(); 
			Session session = sf.getCurrentSession();
			Long count = new Long(0);
			try
			{
				sb.append("select count(*) from BoxTerminalNew w where w.box_id ="+ boxid +" and w.sideName = '"+ sidename +"'");
				
				Query q = session.createQuery(sb.toString());
				count = (Long)q.uniqueResult();
			}
			finally
			{
				session.close();
			}
			
			int i = count.intValue();
			int j=0;
			if(i!=0){
				
				j=i/cols;
			}
			return j;		
			
			
		}
		
		//获取某光交箱下的所有端子
		public List<BoxTerminalUsedNew> findBoxTerminalById(int id) {
			return this.hibernateTemplate.find("from BoxTerminalUsedNew bt where bt.boxTerminal.box_id = " + id + " order by bt.boxTerminal.box_module_id, bt.boxTerminal.row, bt.boxTerminal.col");
		}
		
		
        public BoxTerminalNew getBoxTerminalNewbyID(int id){
			
        	BoxTerminalNew dep =hibernateTemplate.get(BoxTerminalNew.class, id);
			
			return dep;
			
		}
		
		
		//findBoxTerminalByIdNew
		//获取某光交箱下的所有端子
		public List<BoxTerminalUsedForXQNew> findBoxTerminalByIdNew(int id) {
		//return this.hibernateTemplate.find("from BoxTerminalUsedNew bt where bt.boxTerminal.box_id = " + id + " order by bt.boxTerminal.box_module_id, bt.boxTerminal.row, bt.boxTerminal.col");
		List<BoxTerminalUsedForXQ> bs  = this.hibernateTemplate.find("from BoxTerminalUsedForXQ bt where bt.boxTerminal.box_id = " + id + " ");
		List<BoxTerminalUsedForXQNew> bsnew = new  ArrayList<BoxTerminalUsedForXQNew>();
		
/*		StringBuffer sb = new StringBuffer();
		sb.append("select * from box_terminal_used as a inner join box_terminal as b on a.box_terminal_id = b.id left join core as c on a.back_core_id = c.id  where  b.box_id   = " + id );

		SessionFactory sf = hibernateTemplate.getSessionFactory(); 
		Session session = sf.getCurrentSession();
		List<BoxTerminalUsedForXQ> bs = new ArrayList<BoxTerminalUsedForXQ>();
		*/
		
		try {
		/*	Query query = session.createSQLQuery(sb.toString())
			.addEntity(BoxTerminalUsedForXQ.class)
		
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			bs = (List<BoxTerminalUsedForXQ>)query.list();
	*/
			
			
			if(bs !=null){
		    
			for (BoxTerminalUsedForXQ td : bs) {
			 
			  BoxTerminalUsedForXQNew tempnew = new BoxTerminalUsedForXQNew();
			  List<BoxTerminalNew> lst  = new ArrayList<BoxTerminalNew>();
			  
			  tempnew.setId(td.getId());
			  tempnew.setFrontUsed(td.getFrontUsed());
			  tempnew.setFrontFreezed(td.getFrontFreezed());
			  tempnew.setBoxTerminal(td.getBoxTerminal());
			  tempnew.setBackUsed(td.getBackUsed());
			  tempnew.setBackFreezed(td.getBackFreezed());
			  tempnew.setBackCore(td.getBackCore());
			  
			  if(td.getFront_terminal_id()!=null && !td.getFront_terminal_id().equals(""))
			  {  
				  
				  String[] ss = td.getFront_terminal_id().split(",");
				  
				  if(ss.length > 0){
					  
						 for(int i = 0;i<ss.length;i++){
						  
						  BoxTerminalNew bn = getBoxTerminalNewbyID(Integer.parseInt(ss[i]));
						  lst.add(bn);
						  
					  }
			   tempnew.setFrontTerminal(lst);
					  
					  }
			  }
			  
			  bsnew.add(tempnew);
			
		}
			
		}
		}
		finally {
		//	session.close();
		}
		return bsnew;
		}

		//解除跳纤
		public JumpCore relieveJumpTerminal(int id) {
			JumpCore jc = new JumpCore();
			List<BoxTerminalUsed> jcs = this.hibernateTemplate.find("from BoxTerminalUsed bt where bt.boxTerminal.id = ?", id);
			if (jcs.size()  > 0)
			{
				BoxTerminalUsed bt = jcs.get(0);
				BoxTerminal a = new BoxTerminal();
				a.setId(jcs.get(0).getBoxTerminal().getId());
				jc.setA(a);
				BoxTerminal b = new BoxTerminal();
				b.setId(jcs.get(0).getFrontTerminal().getId());
				jc.setB(b);
				this.hibernateTemplate.bulkUpdate("update BoxTerminalUsed bt set bt.frontUsed = 0, bt.frontTerminal = NULL where bt.frontTerminal.id = ?", bt.getBoxTerminal().getId());
				bt.setFrontUsed(0);
				bt.setFrontTerminal(null);
				this.hibernateTemplate.update(bt);
			}
			return jc;
		}
		
		//解除纤芯成端
		public void relieveCoreToTerminal(int tid) {
			List<CoreUsed> cs = this.hibernateTemplate.find("from CoreUsed c where c.a_terminal.id = ? or c.z_terminal.id = ?", tid, tid);
			if (cs.size() > 0)
			{
				this.hibernateTemplate.bulkUpdate("update BoxTerminalUsed bt set bt.backUsed = 0, bt.backCore = NULL where bt.boxTerminal.id = ?", tid);
				if (cs.get(0).getA_terminal() != null && cs.get(0).getA_terminal().getId() == tid)
				{
					this.hibernateTemplate.bulkUpdate("update CoreUsed c set c.a_terminal = NULL, c.a_type = 0 where c.a_terminal.id = ?", tid);
				}
				else if (cs.get(0).getZ_terminal() != null && cs.get(0).getZ_terminal().getId() == tid)
				{
					this.hibernateTemplate.bulkUpdate("update CoreUsed c set c.z_terminal = NULL, c.z_type = 0 where c.z_terminal.id = ?", tid);
				}
			}
		}
		
		//纤芯成端
		public void coreToTerminal(int tid, int oid, int cid, int bid) {
			List<CoreUsed> cs = this.hibernateTemplate.find("from CoreUsed c where c.a_terminal.id = ? or c.z_terminal.id = ?", tid, tid);
			if (cs.size() <= 0)
			{
				Opticalcable o = this.hibernateTemplate.get(Opticalcable.class, oid);
				this.hibernateTemplate.bulkUpdate("update BoxTerminalUsed bt set bt.backUsed = 1, bt.backCore = ? where bt.boxTerminal.id = ?", cid, tid);
				if (o.getStart_box() != null && o.getStart_box().getId() == bid)
				{				
					this.hibernateTemplate.bulkUpdate("update CoreUsed c set c.a_terminal.id = ?, c.a_type = 2 where c.core.id = ? and c.a_type = 0", tid, cid);
				}
				else if (o.getEnd_box() != null && o.getEnd_box().getId() == bid)
				{
					this.hibernateTemplate.bulkUpdate("update CoreUsed c set c.z_terminal.id = ?, c.z_type = 2 where c.core.id = ? and c.z_type = 0", tid, cid);
				}
			}
		}
		
		//获取纤芯成端
		public CoreUsed findCoreToTerminal(int tid) {
			List<CoreUsed> cs = this.hibernateTemplate.find("from CoreUsed c where c.a_terminal.id = ? or c.z_terminal.id = ?", tid, tid);
			if (cs.size() > 0)
			{
				return cs.get(0);
			}
			return null;
		}
		
		//获取跳纤
		public BoxTerminalUsed findJumpTerminal(int id) {
			List<BoxTerminalUsed> jcs = this.hibernateTemplate.find("from BoxTerminalUsed bt where bt.frontUsed = 1 and bt.frontTerminal.id = ?", id);
			if (jcs.size() > 0)
			{
				return jcs.get(0);
			}
			return null;
		}
		
		
		
		
		//跳纤
		public void jumpTerminal(int a, int b) {
			this.hibernateTemplate.bulkUpdate("update BoxTerminalUsed bt set bt.frontUsed = 1, bt.frontTerminal.id = ? where bt.boxTerminal.id = ?", b, a);
			this.hibernateTemplate.bulkUpdate("update BoxTerminalUsed bt set bt.frontUsed = 1, bt.frontTerminal.id = ? where bt.boxTerminal.id = ?", a, b);
		}
		
		//纤芯直熔
		public void coreToCore(int bid, int sid, int eid) {
			String sql = "";
			Core sc = this.hibernateTemplate.get(Core.class, sid);
			if (sc.getOpticalcable() != null && sc.getOpticalcable().getStart_box() != null && sc.getOpticalcable().getStart_box().getId() == bid)
			{
				sql = "update CoreUsed c set c.a_type = 3, c.a_core.id = ?, c.a_string = NULL, c.a_terminal = NULL where c.core.id = ? and c.a_type = 0";
				this.hibernateTemplate.bulkUpdate(sql, eid, sid);
			}
			else
			{
				sql = "update CoreUsed c set c.z_type = 3, c.z_core.id = ?, c.z_string = NULL, c.z_terminal = NULL where c.core.id = ? and c.z_type = 0";
				this.hibernateTemplate.bulkUpdate(sql, eid, sid);
			}
			Core ec = this.hibernateTemplate.get(Core.class, eid);
			if (ec.getOpticalcable() != null && ec.getOpticalcable().getStart_box() != null && ec.getOpticalcable().getStart_box().getId() == bid)
			{
				sql = "update CoreUsed c set c.a_type = 3, c.a_core.id = ?, c.a_string = NULL, c.a_terminal = NULL where c.core.id = ? and c.a_type = 0";
				this.hibernateTemplate.bulkUpdate(sql, sid, eid);
			}
			else
			{
				sql = "update CoreUsed c set c.z_type = 3, c.z_core.id = ?, c.z_string = NULL, c.z_terminal = NULL where c.core.id = ? and c.z_type = 0";
				this.hibernateTemplate.bulkUpdate(sql, sid, eid);
			}
		}
	
	
	//查询工单列表
		public List<WorkOrder> findAllWorkOrder(){
			
			try{
				StringBuffer sb = new StringBuffer();
				sb.append("from WorkOrder w where 0=0 ");
				
				List<WorkOrder> b = hibernateTemplate.find(sb.toString());
				return b;
				
			  }
				 catch(Exception ex){
					 
					 
					 return null;
				 }
			
		}
		
		
		//findAllWorkOrderByType
        public List<WorkOrderNew> findAllWorkOrderByType(int type,int userid ,int userdepid){
			
			try{
				StringBuffer sb = new StringBuffer();
				sb.append("from WorkOrderNew w where is_deleted=0  and done_type ="+type+ " ");
				
				List<WorkOrderNew> b = hibernateTemplate.find(sb.toString());
				
				List<WorkOrderNew> f = new ArrayList<WorkOrderNew>();
				
				
				if (b!=null){
					for(WorkOrderNew w: b){
						
						if(w.getReceive_operators() == null && w.getDepartment() !=null && w.getDepartment().getId() == userdepid )    //没有填写接收人则按部门返回
						{
							f.add(w);
							
						}else if(w.getReceive_operators() !=null && w.getReceive_operators().getId()==userid)
						{
							
							f.add(w);
							
						}
						
					}
					
					
				}
				return f;
				
			  }
				 catch(Exception ex){
					 
					 
					 return null;
				 }
			
		}
		
		//findWorkOrderbyID查询工单详细信息
		 public WorkOrder findWorkOrderbyID(int id){
			 try{
			 return hibernateTemplate.get(WorkOrder.class, id);}
			 catch(Exception ex){
				 
				 
				 return null;
			 }
			 
			
		}
		
	
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
}
