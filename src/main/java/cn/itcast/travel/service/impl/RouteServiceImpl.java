package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;

import java.util.List;

/**
 * @author fly
 * @version 1.0
 * @className RouteServiceImpl
 * @description TODO
 * @date 2019/9/28
 */
public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize) {
        //封装pb
        PageBean<Route> pb = new PageBean<>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示数量
        pb.setPageSize(pageSize);

        //设置总记录数(调用routeDao查询)
        int totalCount = routeDao.findTotalCount(cid);
        pb.setTotalCount(totalCount);

        //设置当前页的数据集合
        //开始的记录数
        int start = (currentPage - 1) * pageSize;
        List<Route> list = routeDao.findByPage(cid, start, pageSize);
        pb.setList(list);

        //设置总页数
        int totalPage = totalCount % pageSize == 0 ? (totalCount / pageSize) : ((totalCount / pageSize) + 1);
        pb.setTotalPage(totalPage);

        return pb;
    }
}
