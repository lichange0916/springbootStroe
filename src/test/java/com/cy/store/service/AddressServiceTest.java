package com.cy.store.service;

import com.cy.store.entity.Address;
import com.cy.store.entity.User;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@SpringBootTest 表示标注当前的类是一个测试类，不会随同项目一起打包
@SpringBootTest
//@RunWith 表示启动这个单元测试类(单元测试是不能够运行的)，需要传递一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class AddressServiceTest {

    @Autowired
    private IAddressService addressService;

    @Test
    public void addNewAddress(){
        Address address = new Address();
        address.setPhone("131452");
        address.setName("李小帅");
        addressService.addNewAddress(23,"李昌鄂",address);
    }

    @Test
    public void setDefault(){
       addressService.setDefault(10,12,"马飞飞");
    }

    @Test
    public void delete(){
        addressService.delete(11,12,"马飞飞");
    }

}
