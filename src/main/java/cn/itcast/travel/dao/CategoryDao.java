package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * @author fly
 * @date 2019/9/26
 */
public interface CategoryDao {
    /**
     * 查询所有类别
     *
     * @return list
     */
    public List<Category> findAll();
}
