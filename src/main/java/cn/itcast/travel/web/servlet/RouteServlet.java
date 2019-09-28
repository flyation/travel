package cn.itcast.travel.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fly
 * @version 1.0
 * @className RouteServlet
 * @description TODO
 * @date 2019/9/28
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    /**
     * 分页查询
     *
     * @param request
     * @param response
     * @author fly
     * @date 2019/9/28
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
