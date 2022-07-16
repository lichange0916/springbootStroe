package com.cy.store.mapper;

import com.cy.store.entity.Cart;
import java.util.Date;

/**
 * 购物车
 */
public interface CartMapper {
    /**
     * 插入购物车数据
     * @param cart 购物车数据
     * @return 受影响的行数
     */
    Integer insert(Cart cart);

    /**
     * 更新购物车某件商品的数量
     * @param cid 购物车id
     * @param num 商品数量
     * @param modifiedUser 执行人
     * @param modifiedTime 执行时间
     * @return 受影响的行数
     */
    Integer updateNumByCid(Integer cid, Integer num, String modifiedUser, Date modifiedTime);

    /**
     * 根据用户的id和商品的id来查询购物车的数据
     * @param uid 用户id
     * @param pid 商品id
     * @return 购物车数据信息
     */
    Cart findByUidAndPid(Integer uid, Integer pid);
}
