package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

/**
 * @author fly
 * @description TODO
 * @date 2019/10/7
 */
public interface RouteImgDao {
    /**
     * 根据rid查询图片
     * @param rid
     * @return
     */
    public List<RouteImg> findByRid(int rid);
}
