package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// @Controller
@RestController //@Controller + @ResponseBody(此方法的响应结果以json的格式进行数据响应到前端)
@RequestMapping("users")
public class UserController extends BaseController{
    @Autowired
    private IUserService userService;

    /*新版本异常处理方法*/

    /**
     * 登录
     * @param session session
     * @return
     */
    @RequestMapping("login")
    public JsonResult<User> login(String username,String password,HttpSession session){
        User date = userService.login(username,password);
        session.setAttribute("uid",date.getUid());
        session.setAttribute("username",date.getUsername());
        return new JsonResult<>(OK,date);
    }

    /**
     * 注册密码
     * @param user
     * @return
     */
    @RequestMapping("reg")
    public JsonResult<Void> reg(User user){
        userService.reg(user);
        return new JsonResult<>(OK);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword,
                                           String newPassword,
                                           HttpSession session){

        Integer uid = getUidFromSession(session);//获取session中的uid的值
        String username = getUserNameFromSession(session);//获取session中username的值
        userService.changePassword(uid,username,oldPassword,newPassword);
        return new JsonResult<>(OK);
    }

    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){
        //在session中查询uid的数据
        User data = userService.getByUid(getUidFromSession(session));
        //返回状态码
        return new JsonResult<>(OK,data);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user,HttpSession session){
        //user对象中有四部分的数据:username、phone、email、gender
        //uid数据需要再次封装到user对象中
        Integer uid = getUidFromSession(session);
        String username = getUserNameFromSession(session);
        userService.changeInfo(uid,username,user);
        return new JsonResult<>(OK);
    }


    /**
     * 设置上传文件的最大值
     */
    public static final int AVATAR_MAX_SIZE =10 * 1024 *1024;

    /**
     * 限制上传文件的类型
     */
    public static final List<String> AVATAR_TYPE = new ArrayList<>();
    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
        AVATAR_TYPE.add("image/svg");
    }

    /**
     * MultipartFile 接口是SpringMVC提供一个接口，这个接口为我们包装了获取文件类型的数据
     * 任何类型的file都可以接收，SpringBoot它有整合了SpringMvc，只需要在处理请求的方法参数列表上声明一个
     * 参数类型为 MultipartFile的参数，然后Springboot自动将传递给服务的文件的数据赋值给这个参数
     *
     * @RequestParam 表示请求中的参数，将请求中的参数注入请求处理的方法的参数上
     *                如果名称不一致则可以使用 @RequestParam注解进行标记和映射
     * @param session
     * @param file
     * @return
     */
    @RequestMapping("/change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session,
                                           @RequestParam("file") MultipartFile file){

        //判断文件是否为空
        if(file.isEmpty()){
            throw new FileEmptyException("文件为空!");
        }
        //判断文件大小是否超出规定
        if (file.getSize() > AVATAR_MAX_SIZE){
            throw new FileSizeException("文件大小超出限制!");
        }
        //判断文件类型
        String contentType = file.getContentType();
        //如果集合包含某个元素则返回值true
        if (!AVATAR_TYPE.contains(contentType)){
            throw new FileTypeException("文件类型不支持!");
        }
        //上传的文件.../upload/文件.png
        String parent = session.getServletContext().getRealPath("images/upload");
        //保存文件的地址
        String realFile = parent;
        System.out.println(realFile);
        //File对象指向这个路径
        File dir = new File(realFile);
        if (!dir.exists()){
            dir.mkdirs();//创建当前目录
        }
        //获取到文件的名称
        String originalFilename = file.getOriginalFilename();
        //截取最后一个字符串 . 出现的位置
        int index = originalFilename.lastIndexOf(".");
        //截取这个字符串.后面的字符
        String suffix = originalFilename.substring(index);
        //用UUID工具来生成一个新的字符串文件名 + 文件后缀
        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;
        //创建一个名字叫filename和路径为dir的空文件dest
        File dest = new File(dir,filename);
        //将参数file中的数据写入到空文件dest中
        try {
            //将file文件中的数据写入到dest中，前提是文件的后缀一致
            file.transferTo(dest);
        } catch (FileStateException e) {
            throw new FileStateException("文件状态异常");
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }
        //从session中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUserNameFromSession(session);
        //返回头像的路径/upload/test.png
        String avatar = "/images/upload/"+filename;
        System.out.println(avatar);
        userService.changeAvatar(uid,avatar,username);
        //返回用户头像的路径给前端页面，将来用于展示头像使用
        return new JsonResult<>(OK,avatar);
    }

    /* 老版本的错误拦截方法
    @RequestMapping("reg")
    public JsonResult<Void> reg(User user){
        //创建响应结果对象
        JsonResult<Void> result = new JsonResult<>();
        try{
            userService.reg(user);
            result.setState(200);
            result.setMessage("用户注册成功!");
        }catch (UsernameDuplicatedException e){
            result.setState(400);
            result.setMessage("用户名被占用！");
        }catch (InsertException e){
            result.setState(5000);
            result.setMessage("注册时产生未知异常!");
        }
        return result;
    }
    */
}
