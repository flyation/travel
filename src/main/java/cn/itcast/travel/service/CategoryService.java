package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * @author fly
 * @date 2019/9/26
 */
public interface CategoryService {
    /**
     * 查询所有类别
     *
     * @return
     */
    public List<Category> findAll();
}
