package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

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
    //RouteServlet业务对象
    private RouteService routeService = new RouteServiceImpl();

    /**
     * 分页查询
     *
     * @param request
     * @param response
     * @author fly
     * @date 2019/9/28
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收参数
        String currentPage_str = request.getParameter("currentPage");
        String pageSize_str = request.getParameter("pageSize");
        String cid_str = request.getParameter("cid");

        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"), "utf-8");

        //2.处理参数
        //当前页码
        int currentPage = 0;
        if (currentPage_str != null && currentPage_str.length() > 0) {
            currentPage = Integer.parseInt(currentPage_str);
        } else {
            //若不传递,则默认第一页
            currentPage = 1;
        }

        //每页显示条数
        int pageSize = 0;
        if (pageSize_str != null && pageSize_str.length() > 0) {
            pageSize = Integer.parseInt(pageSize_str);
        } else {
            //若不传递,则默认每页5条记录
            pageSize = 5;
        }

        //类别id
        int cid = 0;
        if (cid_str != null && cid_str.length() > 0 && !"null".equals(cid_str)) {
            cid = Integer.parseInt(cid_str);
        }

        //3.调用service查询PageBean对象
        PageBean<Route> routePageBean = routeService.pageQuery(cid, currentPage, pageSize, rname);

        //4.序列化PageBean对象为json,并返回客户端
        writeValue(response, routePageBean);
    }

    /**
     * 根据id查询一个旅游线路的详细信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取参数
        String rid = request.getParameter("rid");

        //2.调用service
        Route route =  routeService.findOne(rid);

        //3.json序列化,写回页面
        writeValue(response, route);
    }
}