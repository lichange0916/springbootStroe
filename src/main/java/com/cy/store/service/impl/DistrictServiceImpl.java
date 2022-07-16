package com.cy.store.service.impl;

import com.cy.store.entity.District;
import com.cy.store.mapper.DistrictMapper;
import com.cy.store.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements IDistrictService {

    @Autowired
    private DistrictMapper districtMapper;

    @Override
    public List<District> getParent(String parent) {
        List<District> list = districtMapper.findByParent(parent);
        /**
         * 在进行网络数据传输时，为了尽量避免无效数据的传递，可以将无效数据设置为null
         * 节省流量开销
         * 提升效率
         */
        for (District d:list){
            d.setId(null);
            d.setParent(null);
        }
        return list;
    }

    /**
     * 获取code对应的市区名
     * @param code 市区代码
     * @return 市区名字
     */
    @Override
    public String getNameByCode(String code) {
        return districtMapper.findNameByCode(code);
    }
}
