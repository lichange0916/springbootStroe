package com.cy.store.service;

import com.cy.store.entity.User;

import javax.websocket.Session;

/**
 * 用户模块业务层接口
 */
public interface IUserService {

    /**
     * 用户登录方法
     * @param userName 账号
     * @param passWord 密码
     * @return 当前匹配的用户数据,如果没有则返回null值
     */
    User login(String userName,String passWord);

    /**
     * 用户注册方法
     * @param user 用户的数据对象
     */
    void reg(User user);

    /**
     * 修改密码
     * @param uid 用户id
     * @param username 用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void changePassword(Integer uid,
                        String username,
                        String oldPassword,
                        String newPassword);

    /**
     * 根据用户的uid查询用户的数据
     * @param uid 用户id
     * @return 用户的数据
     */
    User getByUid(Integer uid);

    /**
     * 更新用户的数据
     * @param user 用户
     * @param uid 用户id
     * @param username 用户名称
     */
    void changeInfo(Integer uid, String username, User user);

    /**
     * 修改用户头像头像
     * @param uid 用户id
     * @param avatar 用户头像
     * @param username 修改执行者
     */
    void changeAvatar(Integer uid,String avatar,String username);
}
