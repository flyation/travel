package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

/**
 * @author fly
 * @description TODO
 * @date 2019/10/7
 */
public interface SellerDao {
    /**
     * 根据sid查询商家
     * @param sid
     * @return
     */
    public Seller findBySid(int sid);
}
