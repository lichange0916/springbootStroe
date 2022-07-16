package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.websocket.Session;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.UUID;

/**
 * 用户模块业务层的实现类
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Override

    /**
     * 用户注册
     */
    public void reg(User user) {

        //1、通过user阐述来获取传递过来的username
        String username = user.getUsername();
        //2、调用 findUserByUsername(username) 判断用户是否被注册过
        User result = userMapper.findUserByUsername(username);
        //3、判断结果集是否为null,则抛出用户名被占用的异常
        if (result != null){
            //抛出异常
            throw new UsernameDuplicatedException("用户名被占用!");
        }

        /**
         * 密码加密处理的实现:MD5 算法的形式
         * 串 + password + 串 ---------- MD5算法加密，连续加载三次
         * 盐值 + password + 盐值 ---------研制就是一个随机的字符串
         */
        String oldPassword=user.getPassword();//获取用户密码
        String salt = UUID.randomUUID().toString().toUpperCase();//获取盐值(随机生产一个盐值)
        user.setSalt(salt);
        String md5Password = getMD5Password(oldPassword, salt);//将密码和盐值作为一个整体进行加密处理，忽略原有密码强度，提升了数据的安全性
        user.setPassword(md5Password);//将加密后的md5密码重新设置到user对象中

        /**
         * 补全数据
         * 1、is_delete 设置成 0
         * 2、4个日志字段信息
         */
        user.setIsDelete(0);

        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getModifiedUser());

        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        //4、执行注册业务功能的实现
        Integer rows = userMapper.insertUser(user);
        if (rows != 1){
            throw new InsertException("在用户注册过程中产生了未知异常!");
        }

    }
    /**
     * 定义一个md5算法的加密处理
     */
    private String getMD5Password(String password,String salt){
        for (int i = 0; i < 3; i++) { //进行三次加密
            password = DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();//md5加密算法方法的调用
        }
        // 返回加密之后的密码
        return password;
    }

    /**
     * 用户登录
     * @param userName 账号
     * @param passWord 密码
     * @return 返回该用户的数据
     */
    @Override
    public User login(String userName, String passWord) {
        //检测是否有该用户的账号
        User result = userMapper.findUserByUsername(userName);
        if (result == null){
            throw new UserNameNotFoundException("用户数据不存在");
        }

        //检测该用户密码是否匹配
        //1、先获取数据库中加密之后的密码
        String oldPassWord = result.getPassword();
        //2、和用户传递过来的密码进行比较
        //2.1、先获取盐值：上一次在注册时所自动生成的盐值
        String salt = result.getSalt();
        //2.1、将用户密码按照相同的MD5算法的规则进行加密
        String newMD5Password = getMD5Password(passWord,salt);

        //3、将密码进行比较
        if (!newMD5Password.equals(oldPassWord)){
            throw new PassWordNotFoundException("用户密码错误");
        }

        //4、判断is_delete字段是否表示为1 表示标记为删除
        if (result.getIsDelete() == 1){
            throw new UserNameNotFoundException("用户数据不存在");
        }

        //调用mapper层的findUserByUsername来查询用户的数据,提升了系统的性能
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        //返回用户头像
        user.setAvatar(result.getAvatar());

        //返回当前的用户数据，返回数据时为了辅助其他页面数据做展示使用
        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        //判断改用户是否存在且是否被注销
        User result = userMapper.findByUid(uid); //根据uid查询用户
        if (result == null || result.getIsDelete()==1){
            throw new UserNameNotFoundException("用户数据不存在");
        }

        //输入的旧密码进行MD5加密
        String oldMD5Password = getMD5Password(oldPassword, result.getSalt());
        //MD5加密后的旧密码和数据库中的密码比较
        if (!result.getPassword().equals(oldMD5Password)){
            throw new PassWordNotFoundException("密码错误");
        }

        //将新的密码设置到数据库中，将新的密码进行加密再进行更新
        String newMD5Password = getMD5Password(newPassword, result.getSalt());
        //调用方法保存数据
        Integer rows = userMapper.updatePasswordByUid(uid, newMD5Password, username, new Date());
        //如果数据不等于1抛出异常代表失败
        if (rows !=1){
            throw new UpdateException("更新数据时产生未知的异常");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        //获取用户id
        User result = userMapper.findByUid(uid);
        //如果用户id不存在且用户被注销返回 UserNameNotFoundException
        if (result == null || result.getIsDelete() == 1){
            throw new UserNameNotFoundException("用户数据不存在!");
        }
        //返回数据量太大，所以只返回需要的数据
        result.setUsername(result.getUsername());
        result.setPhone(result.getPhone());
        result.setEmail(result.getEmail());
        result.setGender(result.getGender());
        return result;
    }

    /**
     * user对象中的数据phone、email、gender，手动再将uid、username封装
     * @param user 用户
     * @param uid 用户id
     * @param username 用户名称
     */
    @Override
    public void changeInfo(Integer uid, String username,User user) {
        //获取用户id
        User result = userMapper.findByUid(uid);
        //如果用户id不存在且用户被注销返回 UserNameNotFoundException
        if (result == null || result.getIsDelete() == 1){
            throw new UserNameNotFoundException("用户数据不存在!");
        }
        //添加修改人和修改日期
        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        //判断修改了几行
        Integer rows = userMapper.updateInfoByUid(user);
        //如果修改行数不等于1就是修改失败
        if (rows != 1){
            throw new UpdateException("数据更新时产生未知异常!");
        }
    }

    /**
     * 修改用户头像头像
     * @param uid 用户id
     * @param avatar 用户头像
     * @param username 修改执行者
     */
    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
       //判断用户uid是否存在
        isLive(uid);
        //更新数据
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        //判断数据是否更新成功
        if (rows != 1){
            throw new UpdateException("更新用户头像时产生未知异常!");
        }
    }

    /**
     * 判断用户id是否存在且用户是否被注销
     * @param uid 用户uid
     * @return
     */
    boolean isLive(Integer uid){
        User result = userMapper.findByUid(uid);
        //如果用户id不存在且用户被注销返回 UserNameNotFoundException
        if (result == null || result.getIsDelete() == 1){
            throw new UserNameNotFoundException("用户数据不存在!");
        }
        return true;
    }
}
