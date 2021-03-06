package com.cy.store.service;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
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
public class UserServiceTest {

    //idea有检测功能，接口是不能够直接创建Bean的（动态代理技术来解决）
    @Autowired
    private IUserService userService;

    /**
     * 单元测试方法：可以单独运行，不用启动整个项目，可以做单元测试，提升了代码的测试效率
     * 1、必须被@Test注解修饰
     * 2、返回值类型必须是void
     * 3、方法的参数列表不指定任何类型
     * 4、方法的访问修饰符必须是public
     */
    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("admin01");
            user.setPassword("123");
            userService.reg(user);
            System.out.println("OK");
        }catch (ServiceException e){
            //获取类的对象，再获取类的名称
            System.out.println(e.getClass());
            //获取异常的具体描述信息
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void login(){

        try {
            User user = userService.login("test01", "123456");
            System.out.println("OK");
            System.out.println(user);
        } catch (ServiceException e) {
            //获取类的对象，再获取类的名称
            System.out.println(e.getClass());
            //获取异常的具体描述信息
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void changePassword(){
        userService.changePassword(11,"admin","123456","123");
    }

    @Test
    public void getByUid() {
        System.out.println(userService.getByUid(12));
    }


    @Test
    public void changeInfo() {
        User user = new User();
        user.setPhone("1554936");
        user.setEmail("123@qq.com");
        user.setGender(0);
        userService.changeInfo(12,"admin01",user);
    }

    @Test
    public void changeAvatar(){
        userService.changeAvatar(15,"/upload/test.png","lichange001");
    }

}
