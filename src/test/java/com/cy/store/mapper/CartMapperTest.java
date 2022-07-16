package com.cy.store.mapper;

import com.cy.store.entity.Address;
import com.cy.store.entity.Cart;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

//@SpringBootTest 表示标注当前的类是一个测试类，不会随同项目一起打包
@SpringBootTest
//@RunWith 表示启动这个单元测试类(单元测试是不能够运行的)，需要传递一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class CartMapperTest {

    @Autowired
    private CartMapper cartMapper;

    @Test
    public void insert(){
        Cart cart = new Cart();
        cart.setUid(12);
        cart.setPid(10000011);
        cart.setPrice(1000L);
        cart.setNum(10);
        cartMapper.insert(cart);
    }

    @Test
    public void updateNumByCid(){
       cartMapper.updateNumByCid(1,4,"马飞飞",new Date());
    }

    @Test
    public void findByUidAndPid(){
        System.err.println(cartMapper.findByUidAndPid(12, 10000011));
    }



}
