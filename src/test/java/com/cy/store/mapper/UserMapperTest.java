package com.cy.store.mapper;

import com.cy.store.entity.User;
import com.cy.store.service.ex.UpdateException;
import com.cy.store.service.ex.UserNameNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.websocket.Session;
import java.util.Date;

//@SpringBootTest 表示标注当前的类是一个测试类，不会随同项目一起打包
@SpringBootTest
//@RunWith 表示启动这个单元测试类(单元测试是不能够运行的)，需要传递一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class UserMapperTest {

    //idea有检测功能，接口是不能够直接创建Bean的（动态代理技术来解决）
    @Autowired
    private UserMapper userMapper;

    /**
     * 单元测试方法：可以单独运行，不用启动整个项目，可以做单元测试，提升了代码的测试效率
     * 1、必须被@Test注解修饰
     * 2、返回值类型必须是void
     * 3、方法的参数列表不指定任何类型
     * 4、方法的访问修饰符必须是public
     */
    @Test
    public void insertUser(){
        User user = new User();
        user.setUsername("lichange");
        user.setPassword("123456");
        Integer rows = userMapper.insertUser(user);
        if (rows>0){
            System.out.println(rows + "注册成功！");
        }
    }

    @Test
    public void findUserByUsername(){
        User user = userMapper.findUserByUsername("lichange");
        System.out.println(user);
    }

    @Test
    public void updatePasswordByUid(){
        userMapper.updatePasswordByUid(8,"123","李昌鄂",new Date());
    }

    @Test
    public void findByUid(){
        User user = userMapper.findByUid(8);
        System.out.println(user);
    }

    @Test
    public void updateInfoByUid(){
      User user = new User();
      user.setUid(11);
      user.setPhone("15549355156");
      user.setEmail("863806366@qq.com");
      user.setGender(1);
      userMapper.updateInfoByUid(user);
    }

    @Test
    public void updateAvatarByUid(){
        userMapper.updateAvatarByUid(11,"lichange","李昌鄂",new Date());
    }

}
