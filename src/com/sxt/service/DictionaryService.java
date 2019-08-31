package com.sxt.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxt.dao.DepartmentDao;
import com.sxt.dao.DictionaryDao;
import com.sxt.dao.UserDao;
import com.sxt.po.Operators;
import com.sxt.po.StateKey;
import com.sxt.po.StateKeySearch;
import com.sxt.po.StateLevel;
import com.sxt.po.StateLevelSearch;
import com.sxt.po.StateValue;
import com.sxt.po.StateValueSearch;
import com.sxt.po.User;

@Service("dictionaryService")
public class DictionaryService {
	
	private DictionaryDao dictionaryDao;

	public DictionaryDao getDictionaryDao() {
		return dictionaryDao;
	}

	@Resource
	public void setDictionaryDao(DictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
	}
	//获取状态字典分页
	public List<StateKey> findByPager(StateKeySearch sks){
		return dictionaryDao.findByPager(sks);
	}
	
	//获取所有状态字典
	public List<StateKey> findStateKeyAll(){
		return dictionaryDao.findStateKeyAll();
	}
	
	//获取所有状态项字典
	public List<StateValue> findStateValueAll(){
		return dictionaryDao.findStateValueAll();
	}
	
	//获取所有状态项字典项
	public List<StateValue> findStateValueAllForCam(){
		return this.dictionaryDao.findStateValueAllForCam();
	}
	
	//删除状态字典
	@Transactional
	public void deleteStateKey(int[] sks) {
		dictionaryDao.deleteStateKey(sks);
	}
	
	//删除状态字典等级
	@Transactional
	public void deleteStateLevel(int[] lls) {
		dictionaryDao.deleteStateLevel(lls);
	}
	
	//删除状态字典项
	@Transactional
	public void deleteStateValue(int[] svs) {
		dictionaryDao.deleteStateValue(svs);
	}
	
	//获取所有状态字典等级
	public List<StateKey> findStateLevelAll(){
		return dictionaryDao.findStateLevelAll();
	}
	
	//获取状态字典总数
	public int findCounts(StateKeySearch sks)
	{
		return dictionaryDao.findCounts(sks);
	}

	//获取状态字典项分页
	public List<StateValue> findStateValueByPager(StateValueSearch svs){
		return dictionaryDao.findStateValueByPager(svs);
	}
	
	//获取状态字典项总数
	public int findStateValueCounts(StateValueSearch svs)
	{
		return dictionaryDao.findStateValueCounts(svs);
	}
	

	//获取字典等级分页
	public List<StateLevel> findStateLevelByPager(StateLevelSearch sls){
		return dictionaryDao.findStateLevelByPager(sls);
	}

	//获取字典等级总数
	public int findStateLevelCounts()
	{
		return dictionaryDao.findStateLevelCounts();
	}
	
	
	//判断字典项是否唯一
	public int isStateKeyValid(String state_key) {
		return dictionaryDao.isStateKeyValid(state_key);
	}
	
	//添加状态字典
	@Transactional
	public void addStateKey(StateKey sk)
	{
		this.dictionaryDao.addStateKey(sk);
	}
	
	//添加状态字典项
	@Transactional
	public void addStateValue(StateValue sv)
	{
		this.dictionaryDao.addStateValue(sv);
	}
	
	//添加状态字典等级
	@Transactional
	public void addStateLevel(StateLevel sl)
	{
		this.dictionaryDao.addStateLevel(sl);
	}
	
	//修改状态字典
	public void updateStateKey(StateKey sk)
	{
		this.dictionaryDao.updateStateKey(sk);
	}
	
	//修改状态字典等级
	@Transactional
	public void updateStateLevel(StateLevel sl)
	{
		this.dictionaryDao.updateStateLevel(sl);
	}
	
	//修改状态字典项
	@Transactional
	public void updateStateValue(StateValue sv)
	{
		this.dictionaryDao.updateStateValue(sv);
	}
	
	//获取状态字
	public StateKey findStateKey(int id)
	{
		return this.dictionaryDao.findStateKey(id);
	}
	
	//获取状态字典等级
	public StateLevel findStateLevel(int id)
	{
		return this.dictionaryDao.findStateLevel(id);
	}
	
	//获取状态字典项
	public StateValue findStateValue(int id)
	{
		return this.dictionaryDao.findStateValue(id);
	}
}
