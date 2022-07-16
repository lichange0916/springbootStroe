package com.cy.store.service;

import com.cy.store.entity.Address;
import com.cy.store.entity.District;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

//@SpringBootTest 表示标注当前的类是一个测试类，不会随同项目一起打包
@SpringBootTest
//@RunWith 表示启动这个单元测试类(单元测试是不能够运行的)，需要传递一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class DistrictServiceTest {

    @Autowired
    private IDistrictService districtService;

    @Test
    public void getParent(){
        /**
         * 86 表示中国
         * 所有省的父代号都是中国
         */
        List<District> list = districtService.getParent("86");
        for (District district : list) {
            System.err.println(district);
        }
    }

}
