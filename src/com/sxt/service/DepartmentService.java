package com.sxt.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxt.dao.DepartmentDao;
import com.sxt.dao.UserDao;
import com.sxt.po.Department;
import com.sxt.po.DepartmentSearch;
import com.sxt.po.Operators;
import com.sxt.po.User;

@Service("departmentService")
public class DepartmentService {
	@Resource
	private DepartmentDao departmentDao;
	
	@Transactional
	public void add(Department d){
		departmentDao.add(d);
	}
	
	public List<Department> findByPager(DepartmentSearch ds){
		return departmentDao.findByPager(ds);
	}
	
	public List<Department> findAll(){
		return departmentDao.findAll();
	}
	
	public List<Department> findAlldep(){
		return departmentDao.findAlldep();
	}
	

	public Department find(int id)
	{
		return departmentDao.find(id);
	}
	
	@Transactional
	public void update(Department o)
	{
		departmentDao.update(o);
	}
	
	@Transactional
	public void delete(int[] ids) {
		departmentDao.delete(ids);
	}
	
	public int findCounts(DepartmentSearch ds)
	{
		return departmentDao.findCounts(ds);
	}

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	
}
