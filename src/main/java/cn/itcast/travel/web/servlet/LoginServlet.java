package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 登录
 * @author fly
 * @date 2019/9/3
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        UserService service = new UserServiceImpl();
        User u = service.login(user);


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
                info.setErrorMsg("尚为激活");
            } else if ("Y".equals(u.getStatus())){
                //已激活
                //存入session
                request.getSession().setAttribute("user",u);
                info.setFlag(true);
            }
        }

        //响应信息
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), info);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
