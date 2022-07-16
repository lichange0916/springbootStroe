package com.cy.store.service;

import com.cy.store.entity.District;

import java.util.List;

public interface IDistrictService {
    /**
     * 根据父区域对应的代号来查询区域的信息
     * @param parent 父区域编号
     * @return 多个区域的信息
     */
    List<District> getParent(String parent);

    /**
     * 获取code对应的市区名
     * @param code 市区代码
     * @return 市区名字
     */
    String getNameByCode(String code);
}
