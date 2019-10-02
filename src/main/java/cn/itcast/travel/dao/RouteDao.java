package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * @author fly
 * @version 1.0
 * @className RouteDao
 * @description TODO
 * @date 2019/9/28
 */
public interface RouteDao {
    /**
     * 根据cid查询总记录数
     */
    public int findTotalCount(int cid);

    /**
     * 根据cid, start, pageSize查询当前页的数据集合
     */
    public List<Route> findByPage(int cid, int start, int pageSize);

}
