package com.sxt.service;

import java.util.List;

import javax.annotation.Resource;

import com.sxt.po.*;
import org.springframework.stereotype.Service;

import com.sxt.dao.BoxEventAndWarnDao;
import com.sxt.dao.LockAndKeyDao;

@Service("boxEventAndWarnService")
public class BoxEventAndWarnService {
	
	private LockAndKeyDao lockandkeyDao;
	private BoxEventAndWarnDao boxEventAndWarnDao;
	
	//获取系统配置信息
		public SysConfig findSysConfig() {
			return this.boxEventAndWarnDao.findSysConfig();
		}
		
		
		//审批维修申请
		public void affirmFixapply(RepairRecord rr) {
			this.boxEventAndWarnDao.affirmFixapply(rr);
		}
	
	
	//获取门开关列表
	public List<DoorEvent> findDoorEventByPager(DoorEventSearch ds){
		return this.boxEventAndWarnDao.findDoorEventByPager(ds);
	}
	
	
	//获取门开关列表byly
	public List<DoorEvent> findDoorEvent(int boxid, String opendate,int num){
		
		return this.boxEventAndWarnDao.findDoorEvent(boxid,opendate, num);
	}

	public boolean addOrUpdateDoorEvent(DoorEventAdd eventAdd) {
		return this.boxEventAndWarnDao.addOrUpdateDoorEvent(eventAdd);
	}
	
	//获取门开关列表详细信息byly
		public DoorEvent findDoorEventbyID(int id){
			
			return this.boxEventAndWarnDao.findDoorEventbyID(id);
		}
	
		
		//findAlarmEventbyID
           public AlarmEvent findAlarmEventbyID(int id){
			
			return this.boxEventAndWarnDao.findAlarmEventbyID(id);
		}
		
	//获取门告警列表byly
	public List<AlarmEvent> findWarnEvent(int boxid, String opendate,int num){
		
		return this.boxEventAndWarnDao.findWarnEvent(boxid,opendate, num);
	}
	
	//获取门开关列表-数量
	public int findDoorEventCounts(DoorEventSearch ds) {
		return this.boxEventAndWarnDao.findDoorEventCounts(ds);
	}
	
	//获取报警列表
	public List<AlarmEvent> findAlarmEventByPager(AlarmEventSearch ds){
		return this.boxEventAndWarnDao.findAlarmEventByPager(ds);
	}
	
	//获取报警列表-数量
	public int findAlarmEventCounts(AlarmEventSearch ds)
	{
		return this.boxEventAndWarnDao.findAlarmEventCounts(ds);
	}
	
	//删除报警记录-数量
	public void deleteAlarmEvent(DeleteAlarmForm ae)
	{
		this.boxEventAndWarnDao.deleteAlarmEvent(ae);
	}
	
	//确认报警记录
	public void affirmAlarmEvent(AlarmEvent ae)
	{
		this.boxEventAndWarnDao.affirmAlarmEvent(ae);
	}
	
	//删除报警记录-数量
	public void deleteDoorEvent(DeleteDoorForm daf)
	{
		this.boxEventAndWarnDao.deleteDoorEvent(daf);
	}

	public LockAndKeyDao getLockandkeyDao() {
		return lockandkeyDao;
	}

	@Resource
	public void setLockandkeyDao(LockAndKeyDao lockandkeyDao) {
		this.lockandkeyDao = lockandkeyDao;
	}

	public BoxEventAndWarnDao getBoxEventAndWarnDao() {
		return boxEventAndWarnDao;
	}

	@Resource
	public void setBoxEventAndWarnDao(BoxEventAndWarnDao boxEventAndWarnDao) {
		this.boxEventAndWarnDao = boxEventAndWarnDao;
	}

	
}
