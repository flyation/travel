package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

/**
 * @author fly
 * @version 1.0
 * @className RouteService
 * @description TODO
 * @date 2019/9/28
 */
public interface RouteService {
    /**
     * 查询页面数据
     * @param cid
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);

    /**
     * 根据id查询一个旅游线路的详细信息
     * @param rid
     * @return
     */
    Route findOne(String rid);
}
