package com.sxt.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxt.dao.BoxInfoDao;
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
import com.sxt.po.BoxTerminalUsed;
import com.sxt.po.BoxTerminalUsedForXQ;
import com.sxt.po.BoxTerminalUsedForXQNew;
import com.sxt.po.BoxTerminalUsedNew;
import com.sxt.po.CoreAtomType;
import com.sxt.po.CoreUsed;
import com.sxt.po.DTO_BoxStates_Values_Levels;
import com.sxt.po.JumpCore;
import com.sxt.po.LabelInfo;
import com.sxt.po.ModificationInfo;
import com.sxt.po.OrderInfactNew;
import com.sxt.po.RepairRecord;
import com.sxt.po.WorkOrder;
import com.sxt.po.WorkOrderImage;
import com.sxt.po.WorkOrderNew;

@Service("boxInfoService")
public class BoxInfoService {
	private BoxInfoDao boxInfoDao;
	
	@Transactional
	public void add(BoxInfo b){
		boxInfoDao.add(b);
	}
	
	public String findBoxnobyLockCode(String lockcode){

		return boxInfoDao.findBoxnobyLockCode(lockcode);

	}


	public String findBoxaddressbyLockCode(String lockcode){

		return boxInfoDao.findBoxaddressbyLockCode(lockcode);

	}
	
	//获取光交箱全局设置值
	public BoxGlobals getBoxGlobals(){
		return boxInfoDao.getBoxGlobals();
	}
	
	//添加光交箱设置值
	public void addBoxSettings(BoxSettings bs){
		boxInfoDao.addBoxSettings(bs);
	}
	
	//添加光交箱下发值
	public void addModificationInfo(ModificationInfo mi){
		boxInfoDao.addModificationInfo(mi);
	}
	
	public int getrecivedep(int boxid){
		
		
		return boxInfoDao.getrecivedep(boxid);
		
	}
	
	public int getreciveuserid(int boxid){
		
		return boxInfoDao.getreciveuserid(boxid);
	}
	
	public String findgrantBox(int userid){
		
		return boxInfoDao.findgrantBox(userid);
	}

	
	@Transactional
	public void addBoxPrivilege(BoxPrivilege bp){
		
		boxInfoDao.addBoxPrivilege(bp);
	}
	
	@Transactional
	public void addboxinfo(BoxInfoModify b){
		boxInfoDao.addboxinfo(b);
	}
	
	
	//addboximage
	@Transactional
	public void addboximage(BoxImages b){
		boxInfoDao.addboximage(b);
	}
	
	//addworkorderimage
	@Transactional
	public void addworkorderimage(WorkOrderImage b){
		boxInfoDao.addworkorderimage(b);
	}
	
	public boolean findbyno(String no){
		
		
		return boxInfoDao.findbyno(no);
	}
	
	
   public boolean findbyno(String no,int  id){
		
		
		return boxInfoDao.findbyno(no,id);
	}
	
	//findbycontrollerid
    public boolean findbycontrollerid(long id){
		
		
		return boxInfoDao.findbycontrollerid(id);
	}
    
    
    public boolean findbycontrollerid(long id,int boxid){
		
		
		return boxInfoDao.findbycontrollerid(id,boxid);
	}
	
   
	
	public AtomType transferTQ(int terminal_id){
			return boxInfoDao.transferTQ(terminal_id);
	}
	
	
	//transferTQtoInt  將json类型转换为int
	public int transferTQtoInt(AtomType terminal,int box_id){
		return boxInfoDao.transferTQtoInt(terminal,box_id);
     }

	
	//transferGL
	public CoreAtomType transferGL(int core_id){
		return boxInfoDao.transferGL(core_id);
}
	
	//findimagebyTime
	public List<BoxImages> findimagebyTime(int boxid, String picdate){
		return boxInfoDao.findimagebyTime(boxid,picdate);
	}
	//findimagebyNum
	public List<BoxImages> findimagebyNum(int boxid, int num){
		return boxInfoDao.findimagebyNum(boxid,num);
	}
	
	//findimagebyid
	public List<BoxImages> findimagebyId(int boxid){
			return boxInfoDao.findimagebyId(boxid);
	}
	
	
	public String getdepnamebyId(int depid){
		
		 return boxInfoDao.getdepnamebyId(depid);
		
	}
	
	
	public List<BoxInfo> findByPager(BoxInfoSearch bis){
		return boxInfoDao.findByPager(bis);
	}
	
	public List<BoxInfo> findAll(){
		return boxInfoDao.findAll();
	}
	
	
	public List<BoxInfo> findAllByGrant(String boxlst){
		
		return boxInfoDao.findAllByGrant(boxlst);
	}
	
	
	public List<BoxInfo> findbydep(int depid){
		return boxInfoDao.findbydep(depid);
	}
	
	
	public List<BoxInfo> findAllSimple(String bis){
		return boxInfoDao.findAllSimple(bis);
	}
	
	public List<BoxInfo> findAllSimple(String bis,String boxlst){
		return boxInfoDao.findAllSimple(bis,boxlst);
	}
	
	public  List<BoxImages> findImage(String bis){
		
		return boxInfoDao.findImage(bis);
		
	}
	
	
	@Transactional
	public void updateBoxinfo(BoxInfoModify b)
	{
		boxInfoDao.updateBoxinfo(b);
	}
	
	
	public BoxInfo find(int id)
	{
		return boxInfoDao.find(id);
	}
	
	@Transactional
	public void update(BoxInfo b)
	{
		boxInfoDao.update(b);
	}
	
	@Transactional
	public void updateStatus(BoxInfo b)
	{
		boxInfoDao.updateStatus(b);
	}
	
	//updateLabe
	@Transactional
	public void updateLabe(LabelInfo b)
	{
		boxInfoDao.updateLabe(b);
	}
	
	
	//updateWordOrderStatus
	@Transactional
	public void updateWordOrderStatus(WorkOrder b)
	{
		boxInfoDao.updateWordOrderStatus(b);
	}
	
	
	//addorderinfact  增加工单实际操作表
	@Transactional
	public void addorderinfact(OrderInfactNew b)
	{
		boxInfoDao.addorderinfact(b);
	}
	
	//addrepairapp 增加维修申请
	@Transactional
	public void addrepairapp(RepairRecord b)
	{
		boxInfoDao.addrepairapp(b);
	}
	
	
	//checkappcheckapp/如果申请人 对同一个箱子有尚未审批的维修申请   则不能创建新的维修申请
	 public boolean checkreapairapp(int userid,int boxid){
			
			
			return boxInfoDao.checkreapairapp(userid,boxid);
	 }
	 
	 public List<RepairRecord> findrepairrecord(int userid){
		 
		 return boxInfoDao.findrepairrecord(userid);
		 
	 }
	 
	 
      public RepairRecord findrepairrecordbyid(int id){
		 
		 return boxInfoDao.findrepairrecordbyid(id);
		 
	 }
	
	//updateWordOrderAlarmStatus
	@Transactional
	public void updateWordOrderAlarmStatus(AlarmOrderDetails b)
	{
		boxInfoDao.updateWordOrderAlarmStatus(b);
	}
	
	
	public void updateBoxState(int boxid,String normaltype,int statevalueid){
		
		boxInfoDao.updateBoxState(boxid,normaltype, statevalueid);
	}
	
	public int getboxidbyAlarmStatus(int id){
		
		return boxInfoDao.getboxidbyAlarmStatus(id);
		
	}
	
	public long getcontrolleridbyboxid(int boxid){
		
		return boxInfoDao.getcontrolleridbyboxid(boxid);
		
	}
	
	public void updatecabinet(long controllerid){
		
		boxInfoDao.updatecabinet(controllerid);
		
	}
	
	public String getTypebyAlarmStatus(int id){
		
		return boxInfoDao.getTypebyAlarmStatus(id);
	
	}
	
	public int getStatekeyid(int id){
		
		
		return boxInfoDao.getStatekeyid(id);

	}
	
	@Transactional
	public void delete(BoxInfo b) {
		boxInfoDao.delete(b);
	}
	
	//deletebox
	@Transactional
	public void deletebox(BoxInfo b) {
		boxInfoDao.deletebox(b);
	}
	
	
	
	public int findCounts(BoxInfoSearch bis)
	{
		return boxInfoDao.findCounts(bis);
	}

	public BoxInfoDao getBoxInfoDao() {
		return boxInfoDao;
	}

	@Resource
	public void setBoxInfoDao(BoxInfoDao boxInfoDao) {
		this.boxInfoDao = boxInfoDao;
	}

	public List<BoxStates> findAllBoxStatesByAjax(int id) {
		return this.boxInfoDao.findAllBoxStatesByAjax(id);
	}
	
	//获取所有光交箱状态DTO
	public List<DTO_BoxStates_Values_Levels> findAllBoxStatesByAjax() {
		return this.boxInfoDao.findAllBoxStatesByAjax();
	}
	
	//解除跳纤
		public JumpCore relieveJumpTerminal(int id) {
			return this.boxInfoDao.relieveJumpTerminal(id);
		}
		
		//获取跳纤
		public BoxTerminalUsed findJumpTerminal(int id) {
			return this.boxInfoDao.findJumpTerminal(id);
		}
		
		//跳纤
		public void jumpTerminal(int a, int b) {
			this.boxInfoDao.jumpTerminal(a, b);
		}
		

		//解除纤芯成端
		public void relieveCoreToTerminal(int tid) {
			this.boxInfoDao.relieveCoreToTerminal(tid);
		}
		
		//纤芯成端
		public void coreToTerminal(int tid, int oid, int cid, int bid) {
			this.boxInfoDao.coreToTerminal(tid, oid, cid, bid);
		}
		
		//纤芯直熔
		public void coreToCore(int bid, int sid, int eid) {
			this.boxInfoDao.coreToCore(bid, sid, eid);
		}
		
		//获取纤芯成端
		public CoreUsed findCoreToTerminal(int tid) {
			return this.boxInfoDao.findCoreToTerminal(tid);
		}
		
		//获取光交箱面板
		public List<BoxModule> findBoxModuleById(int id) {
			return this.boxInfoDao.findBoxModuleById(id);
		}
		
		//获取光交箱面板
				public List<BoxModuleNew> findBoxModuleByIdNew(int id) {
					return this.boxInfoDao.findBoxModuleByIdNew(id);
				}
		
		//获取光交箱端子
		public List<BoxTerminalUsedNew> findBoxTerminalById(int id) {
			return this.boxInfoDao.findBoxTerminalById(id);
		}
		
		//findBoxTerminalByIdNew
		//获取光交箱端子new
		public List<BoxTerminalUsedForXQNew> findBoxTerminalByIdNew(int id) {
				return this.boxInfoDao.findBoxTerminalByIdNew(id);
		}
	
		
	
	//查询工单列表
		public List<WorkOrder> findAllWorkOrder(){
			return this.boxInfoDao.findAllWorkOrder();
		}
		
		
		//findAllWorkOrderByType
		public List<WorkOrderNew> findAllWorkOrderByType(int  type,int userid,int userdepid){
			return this.boxInfoDao.findAllWorkOrderByType(type,userid,userdepid);
		}
		
	
		//查询工单详细信息
				public WorkOrder findWorkOrderbyID(int id){
					return this.boxInfoDao.findWorkOrderbyID( id );
				}
	
}
