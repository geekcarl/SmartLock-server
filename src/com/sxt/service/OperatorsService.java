package com.sxt.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sxt.dao.OperatorsDao;
import com.sxt.dao.UserDao;
import com.sxt.po.Operators;
import com.sxt.po.User;

@Component("operatorsService")
public class OperatorsService {
	private OperatorsDao operatorsDao;

	public OperatorsDao getOperatorsDao() {
		return operatorsDao;
	}

	@Resource
	public void setOperatorsDao(OperatorsDao operatorsDao) {
		this.operatorsDao = operatorsDao;
	}
	
	public List<Operators> findByPager(int pageIndex, int pageSize){
		return operatorsDao.findByPager(pageIndex, pageSize);
	}
	
	public List<Operators> findAll()
	{
		return operatorsDao.findAll();
	}
	
	public Operators find(int id)
	{
		return operatorsDao.find(id);
	}
	
	@Transactional
	public void add(Operators o)
	{
		operatorsDao.add(o);
	}
	
	@Transactional
	public void update(Operators o)
	{
		operatorsDao.update(o);
	}
	
	@Transactional
	public void delete(Operators o) {
		operatorsDao.delete(o);
	}
	
	public int findCounts()
	{
		return operatorsDao.findCounts();
	}
	
}
