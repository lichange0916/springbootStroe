package com.cy.store.mapper;

import com.cy.store.entity.Address;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 表示收获地址持久层接口
 */
public interface AddressMapper {
    /**
     * 插入用户的收获地址数据
     * @param address 收获地址数据
     * @return 受影响的行数
     */
    Integer insertAddress(Address address);

    /**
     * 根据用户的id统计收获地址数量
     * @param uid 用户的id
     * @return 用户的收获地址总数
     */
    Integer countByUid(Integer uid);

    /**
     * 根据用户id查询用户收货地址数据
     * @param uid 用户id
     * @return 收货地址数据
     */
    List<Address> findByUid(Integer uid);

    /**
     * 根据aid查询收货地址数据
     * @param aid 收货地址id
     * @return 收货地址数据，如果没有找到则返回null
     */
    Address findByAid(Integer aid);

    /**
     * 根据用户的uid值来修改用户的收货地址设置为非默认
     * @param uid 用户id的值
     * @return 受影响的行数
     */
    Integer updateNonDefault(Integer uid);

    /**
     * 根据aid修改用户收货地址为默认
     * @param aid 地址id
     * @param modifiedUser 修改人
     * @param modifiedTime 修改时间
     * @return
     */
    Integer updateDefaultByAid(@Param("aid") Integer aid,
                               @Param("modifiedUser") String modifiedUser,
                               @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据aid删除用户收货地址
     * @param aid 收货地址id
     * @return 受影响的行数
     */
    Integer deleteByAid(Integer aid);

    /**
     * 根据用户uid查询最后一次被修改的收货地址数据
     * @param uid 用户id
     * @return  收货地址数据
     */
    Address findLastModified(Integer uid);

}
