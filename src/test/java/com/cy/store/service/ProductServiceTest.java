package com.cy.store.service;

import com.cy.store.entity.Product;
import com.cy.store.service.ex.ServiceException;
import com.cy.store.service.impl.ProductServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@SpringBootTest 表示标注当前的类是一个测试类，不会随同项目一起打包
@SpringBootTest
//@RunWith 表示启动这个单元测试类(单元测试是不能够运行的)，需要传递一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findById() {
        try {
            Integer id = 10000017;
            Product result = productService.findById(id);
            System.out.println(result);
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

}
