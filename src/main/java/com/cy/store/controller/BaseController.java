package com.cy.store.controller;


import com.cy.store.controller.ex.*;
import com.cy.store.service.ex.*;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;


/**
 * 控制层类的基类
 */
public class BaseController {
    //操作成功的状态码
    public static final int OK = 200;

    /**
     * 请求处理方法，这个方法的返回值就是需要传递给前端的数据
     * 自动将异常对象传递给此方法的参数列表上
     * 当项目中产生了异常，被同意拦截此方法中，这个方法就是充当请求处理方法，返回值直接给到前端
     */
    @ExceptionHandler({ServiceException.class,FileUploadException.class}) //用于统一处理抛出的异常
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>();
        if (e instanceof UsernameDuplicatedException){
            result.setState(4000);
            result.setMessage("此用户名已被占用");
        }else if (e instanceof InsertException){
            result.setState(5000);
            result.setMessage("注册用户时发生未知异常!");
        } else if (e instanceof UserNameNotFoundException){
            result.setState(5001);
            result.setMessage("用户名被占用的异常");
        }else if (e instanceof PassWordNotFoundException){
            result.setState(5002);
            result.setMessage("用户密码错误的异常");
        }else if (e instanceof UpdateException){
            result.setState(5003);
            result.setMessage("更新数据时产生未知的异常");
        }else if (e instanceof FileEmptyException){
            result.setState(6000);
        }else if (e instanceof FileSizeException){
            result.setState(6001);
        }else if (e instanceof FileStateException){
            result.setState(6002);
        }else if (e instanceof FileUploadIOException){
            result.setState(6003);
        }else if (e instanceof FileTypeException){
            result.setState(6004);
        }else if (e instanceof AddressCountLimitException){
            result.setState(6005);
            result.setMessage("用户地址超出上线异常");
        }else if (e instanceof AddressNotFoundException){
            result.setState(6006);
            result.setMessage("用户的收货地址数据不存在的异常");
        }else if (e instanceof AccessDeniedException){
            result.setState(6007);
            result.setMessage("收货地址数据非法访问对象");
        }else if (e instanceof DeleteException){
            result.setState(6008);
            result.setMessage("删除数据时产生的未知异常");
        }else if (e instanceof ProductNotFoundException){
            result.setState(6009);
            result.setMessage("商品数据不存在异常");
        }
        return result;
    }

    /**
     * 获取session对象中的uid
     * @param session session对象
     * @return 当前用户的uid
     */
    protected final Integer getUidFromSession(HttpSession session){
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 获取当前用户名的username
     * @param session
     * @return 当前用户的用户名
     */
    protected final String getUserNameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }

}
