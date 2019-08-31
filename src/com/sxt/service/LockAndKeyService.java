package com.sxt.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxt.dao.LockAndKeyDao;
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
import com.sxt.po.LockSimpleInfo;
import com.sxt.po.LockTypeInfo;
import com.sxt.po.LockTypeInfoSearch;
import com.sxt.po.PhoneOpenLog;

@Service("lockandkeyService")
public class LockAndKeyService {
	
	private LockAndKeyDao lockandkeyDao;
	
	/*//添加锁
	@Transactional
	public void addLockold(LockInfo li){
		this.lockandkeyDao.addLock(li);
	}*/
	
	public void addkeyopenLog(KeyOpenLog  kl){

		this.lockandkeyDao.addkeyopenLog(kl);

	}
	
	public String  findrfidbyKeycode(String keycode){

		return this.lockandkeyDao.findrfidbyKeycode(keycode);

	} 
	//添加锁
		@Transactional
		public void addLock(LockModify li){
	
			if (li.getLock_code() != null && ! li.getLock_code().equals("")) {
				List<Map<String, Object>> result = this.lockandkeyDao.findLock(li);
				if (result == null || result.size() <= 0) {
					this.lockandkeyDao.addLock(li);
				}
			}
			
		}
	
		
	//添加锁类型
	@Transactional
	public void addLockType(LockTypeInfo li){
		this.lockandkeyDao.addLockType(li);
	}
	
	//获取锁列表
	public List<LockInfo> findLockInfoByPager(LockInfoSearch ds){
		return this.lockandkeyDao.findLockInfoByPager(ds);
	}
	
	//获取锁列表byly
	public List<LockModify> findLockInfo(int boxid){
			return this.lockandkeyDao.findLockInfo(boxid);
   }
	
	//获取锁具体类型byly
		public String findLockInfoByType(int typeid){
			return this.lockandkeyDao.findLockInfoByType(typeid);
		}
		
		
	////根据锁类型获取idbyly
		public int findLockInfoByID(String type){
			return this.lockandkeyDao.findLockInfoByID(type);
		}
		
		
		//findIdbycode
		public int findIdbycode(String keycode){
			return this.lockandkeyDao.findIdbycode(keycode);
		}
		
		//addLockInfo
		/*@Transactional
		public void addLockInfo(List<LockModify>  locklist,int boxid){
				this.lockandkeyDao.addLockInfo(locklist,boxid);
	   }*/
		
		//删除某一光交箱下所有的锁，并新增新上传上来的锁
		@Transactional
		public void updateLockInfo(List<LockModify>  locklist,int boxid){
				this.lockandkeyDao.updateLockInfo(locklist,boxid);
	   }
		

		//删除某一光交箱下所有的锁
		@Transactional
		public void delLockInfo(int boxid){
				this.lockandkeyDao.delLockInfo(boxid);
	   }
		
		
		
	//获取锁类型列表
	public List<LockTypeInfo> findLockTypeInfoByPager(LockTypeInfoSearch ds){
		return this.lockandkeyDao.findLockTypeInfoByPager(ds);
	}
	
	//获取所有锁类型
	public List<LockTypeInfo> findAllLockType(){
		return this.lockandkeyDao.findAllLockType();
	}
	
	//获取手机开门的权限（手机、界面、蓝牙）
	public List<GrantLogForPhone> findGrantLogByPhone(int userid, Integer boxid){
		return this.lockandkeyDao.findGrantLogByPhone(userid,boxid);
	}

	//
	public Integer findBoxidbylock(String lockid)
	{
		return this.lockandkeyDao.findBoxidbylock(lockid);
	}
	
	
	public String findBoxnobylock(String lockid)
	{
		return this.lockandkeyDao.findBoxnobylock(lockid);
	}


	public void savephonelog(PhoneOpenLog p)
	{

		this.lockandkeyDao.savephonelog(p);
	}
	
	//获取锁数量
	public int findLockCounts()
	{
		return this.lockandkeyDao.findLockCounts();
	}
	
	//获取锁类型数量
	public int findLockTypeCounts()
	{
		return this.lockandkeyDao.findLockTypeCounts();
	}
	
	////////////////////////////////////
	
	//添加钥匙
	@Transactional
	public void addKey(KeyInfo li){
		this.lockandkeyDao.addKey(li);
	}
	
	//获取钥匙详情
	public KeyInfo findKey(int id){
		 return this.lockandkeyDao.findKey(id);
	}
	
	//修改钥匙
	public void updateKey(KeyInfo ki){
		this.lockandkeyDao.updateKey(ki);
	}
	
	//删除钥匙
	public void deleteKey(int[] ids){
		this.lockandkeyDao.deleteKey(ids);
	}
	
	//添加钥匙类型
	@Transactional
	public void addKeyType(KeyTypeInfo li){
		this.lockandkeyDao.addKeyType(li);
	}
	
	//获取钥匙列表
	public List<KeyInfo> findKeyInfoByPager(KeyInfoSearch ds){
		return this.lockandkeyDao.findKeyInfoByPager(ds);
	}
	
	//获取钥匙类型列表
	public List<KeyTypeInfo> findKeyTypeInfoByPager(KeyTypeInfoSearch ds){
		return this.lockandkeyDao.findKeyTypeInfoByPager(ds);
	}
	
	//获取所有钥匙类型
	public List<KeyTypeInfo> findAllKeyType(){
		return this.lockandkeyDao.findAllKeyType();
	}

	//获取所有钥匙
	public List<KeyInfo> findAllKey(){
		return this.lockandkeyDao.findAllKey();
	}
	
	
	//获取钥匙数量
	public int findKeyCounts(KeyInfoSearch ds)
	{
		return this.lockandkeyDao.findKeyCounts(ds);
	}
	
	//获取钥匙类型数量
	public int findKeyTypeCounts()
	{
		return this.lockandkeyDao.findKeyTypeCounts();
	}
	
	
	//删除钥匙授权
	public void deleteGrantLog(int[] ids){
		this.lockandkeyDao.deleteGrantLog(ids);
	}
	
	//获取钥匙授权列表
	public List<GrantLog> findGrantLogByPager(GrantLogSearch ds){
		return this.lockandkeyDao.findGrantLogByPager(ds);
	}
	
	//获取钥匙授权信息
	public GrantLog findGrantLogById(int id){
		return this.lockandkeyDao.findGrantLogById(id);
	}
	
	//findGrantLogBykeyId
	public List<GrantLog> findGrantLogBykeyId(String keyid){
		return this.lockandkeyDao.findGrantLogBykeyId(keyid);
	}
	
	//通过钥匙使用人ID获取授权信息
	public List<GrantLog> findGrantLogByOperatorId(int operatorid){
		return this.lockandkeyDao.findGrantLogByOperatorId(operatorid);
	}
	
	//通过锁码获取光交箱信息
	public List<LockInfo> findBoxInfoByLockCode(String lockcode){
		return this.lockandkeyDao.findBoxInfoByLockCode(lockcode);
	}
	
	//通过grantlogid和boxid获取授权详情
	public List<GrantDetail> findGrantDetailBylogidAndboxid(int logid, int boxid){
		return this.lockandkeyDao.findGrantDetailBylogidAndboxid(logid, boxid);
	}
	

	//添加cellgrantlog记录
	@Transactional
	public void addCellGrantLog(CellGrantLog cg){
		this.lockandkeyDao.addCellGrantLog(cg);
	}
	//获取cellgrantlog记录
	public CellGrantLog findCellGrantLog(int id){
		return this.lockandkeyDao.findCellGrantLog(id);
	}
	//修改cellgrantlog记录
	public void updateCellGrantLog(CellGrantLog cg){
		this.lockandkeyDao.updateCellGrantLog(cg);
	}
	//删除cellgrantlog记录
	public void deleteCellGrantLog(int id){
		this.lockandkeyDao.deleteCellGrantLog(id);
	}
	
	//获取钥匙授权数量
	public int findGrantLogCounts(GrantLogSearch ds)
	{
		return this.lockandkeyDao.findGrantLogCounts(ds);
	}
	

	public LockAndKeyDao getLockandkeyDao() {
		return lockandkeyDao;
	}

	@Resource
	public void setLockandkeyDao(LockAndKeyDao lockandkeyDao) {
		this.lockandkeyDao = lockandkeyDao;
	}

	
}
