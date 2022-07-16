package com.cy.store.mapper;

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
public class DistrictMapperTest {

    @Autowired
    private DistrictMapper districtMapper;

    @Test
    public void findByParent(){
        List<District> parent = districtMapper.findByParent("130200");
        for (District district : parent) {
            System.out.println(district);
        }
    }

    @Test
    public void findNameByCode(){
        String nameByCode = districtMapper.findNameByCode("610000");
        System.out.println(nameByCode);
    }

}
