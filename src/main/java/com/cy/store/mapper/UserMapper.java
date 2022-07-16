package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 用户模块持久层接口
 */
public interface UserMapper {

    /**
     * 插入用户数据
     * @param user 用户的数据
     * @return 受影响的行数 （增、删、改 都有影响行数的返回值，可以根据判断是否返回成功）
     */
    Integer insertUser(User user);

    /**
     * 根据用户名来查询用户的数据
     * @param username 用户名
     * @return 如果找到对应的用户，则返回这个用户的数据，没有找到返回null值
     */
    User findUserByUsername(String username);

    /**
     * 根据用户的uid来修改密码
     * @param uid 用户id
     * @param password 用户密码
     * @param modifiedUser  修改数据的执行者
     * @param modifiedTime  修改数据的时间
     * @return
     */
    Integer updatePasswordByUid(Integer uid,
                             String password,
                             String modifiedUser,
                             Date modifiedTime);

    /**
     * 根据用户uid查询用户数据
     * @param uid 用户的id
     * @return 返回值为用户的数据
     */
    User findByUid(Integer uid);

    /**
     * 更新用户的数据
     * @param user
     * @return
     */
    Integer updateInfoByUid(User user);

    /**
     * 修改用户的头像
     *
     * @Param("SQL映射文件中#{}占位符的变量名"):解决问题
     * 当SQL语句的占位符和映射的接口方法参数不一致时，需要将某个参数强行注入到某个占位符变量上时，
     * 可以使用@Param这个注解来标注映射的关系
     *
     * @param uid 用户id
     * @param avatar 头像地址
     * @param modifiedUser 修改人
     * @param modifiedTime 修改时间
     * @return
     */
    Integer updateAvatarByUid(@Param("uid") Integer uid,
                              @Param("avatar") String avatar,
                              @Param("modifiedUser") String modifiedUser,
                              @Param("modifiedTime") Date modifiedTime);
}
