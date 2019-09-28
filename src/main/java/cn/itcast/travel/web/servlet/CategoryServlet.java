package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author fly
 * @date 2019/9/4
 */
@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    //CategoryServlet业务对象
    private CategoryService categoryService = new CategoryServiceImpl();

    /**
     * 查询所有类别
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.调用categoryService查询所有类别
        List<Category> categories = categoryService.findAll();

        //2.将list序列化为json,并写回客户端
        writeValue(response, categories);

    }

}
