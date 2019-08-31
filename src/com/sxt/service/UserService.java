package com.sxt.service;

import javax.annotation.Resource;

import com.sxt.po.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sxt.dao.UserDao;

import java.util.List;

@Component("userService")
public class UserService {
    private UserDao userDao;

    public User findByLogin(User u) {
        System.out.println("UserService.find()");
        User user = userDao.findByLogin(u);
        return user;
    }


    public VersionManage getVersion() {

        VersionManage v = userDao.getVersion();
        return v;

    }

    public int findByName(String name) {
        int userid = userDao.findByName(name);
        return userid;
    }


    public int findDepByName(String name) {
        int depid = userDao.findDepByName(name);
        return depid;
    }


    public String getPassword(String name) {

        return userDao.getPassword(name);

    }


    public User findById(int id) {

        return userDao.findById(id);
    }

    @Transactional
    public int update(User u) {
        return userDao.update(u);
    }

    //updatepwd
    @Transactional
    public int updatepwd(User u) {
        return userDao.updatepwd(u);
    }


    public UserDao getUserDao() {
        return userDao;
    }

    @Resource
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<UserList> getUserList(int user_id) {
        return this.userDao.getUserList(user_id);
    }

    public User getUserInfo(String name) {
        return this.userDao.getUserInfo(name);
    }

    public void addGrantLog(UserInfoPhoneRmoteGrant user, String username) throws Exception {
        this.userDao.addGrantLog(user, username);
    }

    public List<GrantTaskInfo> getGrantTaskList(String name) throws Exception {
        return this.userDao.getGrantTaskList(name);
    }

    public boolean updateTaskState(int taskid, String state, String username) throws Exception {
        return this.userDao.updateTaskState(taskid, state,username);
    }

    public List<Privilege> getUserPrivileges(int roleId){
        return this.userDao.getUserPrivileges(roleId);
    }

}
