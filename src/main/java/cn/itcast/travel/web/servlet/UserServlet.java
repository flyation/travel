package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author fly
 * @date 2019/9/4
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    //UserService业务对象
    private UserService userService = new UserServiceImpl();

    /**
     * 注册
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //0.校验验证码
        //用户输入的验证码
        String check = request.getParameter("check");
        //生成的正确的验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //一旦获取验证码后,立即移除,以防止用户复用
        session.removeAttribute("CHECKCODE_SERVER");


        //验证码错误
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            //创建info
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");

            //将info序列化为json
            String json = writeValueAsString(info);

            //设置ContentType
            response.setContentType("application/json;charset=utf-8");
            //写回客户端
            response.getWriter().write(json);
            return;
        }

        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();

        //2.封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //3.调用service
        boolean flag = userService.register(user);

        //4.响应结果
        //创建info
        ResultInfo info = new ResultInfo();
        if (flag) {
            //注册成功
            info.setFlag(true);
        } else {
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败");
        }
        //将info序列化为json
//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(info);
//        //设置ContentType
//        response.setContentType("application/json;charset=utf-8");
//        //写回客户端
//        response.getWriter().write(json);
        writeValue(response, info);
    }

    /**
     * 激活
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取激活码
        String code = request.getParameter("code");

        if (code != null) {
            //2.调用service
            boolean flag = userService.active(code);

            //3.判断flag
            String msg = null;
            if (flag) {
                msg = "激活成功,请<a href='login.html'>登录</a>嗷";
            } else {
                msg = "激活失败了呢";
            }

            //将msg输出到前台页面上
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

    /**
     * 登录
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //0.校验验证码
        //用户输入的验证码
        String check = request.getParameter("check");
        //生成的正确的验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //一旦获取验证码后,立即移除,以防止用户复用
        session.removeAttribute("CHECKCODE_SERVER");


        //验证码错误
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            //创建info
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");

            //将info序列化为json
            String json = writeValueAsString(info);

            //设置ContentType
            response.setContentType("application/json;charset=utf-8");
            //写回客户端
            response.getWriter().write(json);
            return;
        }

        //获取前台参数
        Map<String, String[]> map = request.getParameterMap();

        //封装User对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //调用service
        User u = userService.login(user);


        ResultInfo info = new ResultInfo();

        //判断
        if (u == null) {
            //用户不存在
            info.setFlag(false);
            info.setErrorMsg("用户不存在或密码错误");
        } else {
            //密码正确
            if ("N".equals(u.getStatus())) {
                //未激活
                info.setFlag(false);
                info.setErrorMsg("尚未激活");
            } else if ("Y".equals(u.getStatus())) {
                //已激活
                //存入session
                request.getSession().setAttribute("user", u);
                info.setFlag(true);
            }
        }

        //将info序列化为json,并写回客户端
        writeValue(response, info);
    }

    /**
     * 登出
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //销毁session
        request.getSession().invalidate();

        //跳转登录页面(重定向)
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    /**
     * 查询单个对象
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从session中获取登录用户
        User user = (User) request.getSession().getAttribute("user");

        //将user序列化为json,并写回客户端
        writeValue(response, user);
    }
}
