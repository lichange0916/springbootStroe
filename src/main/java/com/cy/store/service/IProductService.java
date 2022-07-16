package com.cy.store.service;

import com.cy.store.entity.Product;

import java.util.List;

/**
 * 商品服务层接口
 */
public interface IProductService {
    /**
     * 查询最热商品
     * @return
     */
    List<Product> findHotList();

    /**
     * 根据商品id查询商品详情
     * @param id 商品id
     * @return 匹配的商品详情，如果没有匹配的数据则返回null
     */
    Product findById(Integer id);
}
