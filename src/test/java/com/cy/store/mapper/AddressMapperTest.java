package com.cy.store.mapper;

import com.cy.store.entity.Address;
import com.cy.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

//@SpringBootTest 表示标注当前的类是一个测试类，不会随同项目一起打包
@SpringBootTest
//@RunWith 表示启动这个单元测试类(单元测试是不能够运行的)，需要传递一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class AddressMapperTest {

    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insertAddress(){
        Address address = new Address();
        address.setUid(23);
        address.setPhone("15549355156");
        address.setName("李小帅");
        addressMapper.insertAddress(address);
    }

    @Test
    public void findByUid(){
        List<Address> res = addressMapper.findByUid(12);
        System.out.println(res);

    }

    @Test
    public void findByAid(){
        System.out.println(addressMapper.findByAid(10));

    }

    @Test
    public void updateNonDefault(){
       addressMapper.updateNonDefault(12);

    }

    @Test
    public void updateDefaultByAid(){
        addressMapper.updateDefaultByAid(11,"admin01",new Date());
    }

    @Test
    public void deleteByAid(){
        addressMapper.deleteByAid(10);
    }

    @Test
    public void findLastModified(){
        System.out.println(addressMapper.findLastModified(12));
    }
}
