package com.cy.store.mapper;

import com.cy.store.entity.Product;

import java.util.List;

/**
 * 商品持久层接口
 */
public interface ProductMapper {
    /**
     * 当前最热商品持久层接口
     * @return 最热商品信息
     */
    List<Product> findHotList();

    /**
     * 根据商品id查询商品详情
     * @param id 商品id
     * @return 匹配的商品详情，如果没有匹配的数据则返回null
     */
    Product findById(Integer id);
}
