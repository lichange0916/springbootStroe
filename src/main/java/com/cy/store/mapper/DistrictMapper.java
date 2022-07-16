package com.cy.store.mapper;

import com.cy.store.entity.District;

import java.util.List;

public interface DistrictMapper {
    /**
     * 根据父代号查询区域信息
     * @param parent 父代号
     * @return 某个父代号下所有的区域列表
     */
    List<District> findByParent(String parent);

    /**
     * 获取code对应的市区名
     * @param code 市区代码
     * @return 市区名字
     */
    String findNameByCode(String code);
}
