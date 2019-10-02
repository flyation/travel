package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author fly
 * @date 2019/9/26
 */
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        //1.从redis查询
        //获取jedis
        Jedis jedis = JedisUtil.getJedis();
        //使用sortedSet排序查询
        Set<Tuple> category_set = jedis.zrangeWithScores("category", 0, -1);

        //2.判断redis查询结果是否为空
        List<Category> category_list = null;
        if (category_set == null || category_set.size() == 0) {
            //2.1若为空(无缓存),则查询数据库,并存入redis
            System.out.println("从数据库查询category...");
            //从数据库查询
            category_list = categoryDao.findAll();
            //存入redis
            for (int i = 0; i < category_list.size(); i++) {
                jedis.zadd("category", category_list.get(i).getCid(), category_list.get(i).getCname());
            }
        } else {
            //2.2若不为空(有缓存),则将category_set的数据存入category_list
            System.out.println("从redis查询category...");
            category_list = new ArrayList<>();
            for (Tuple tuple : category_set) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int) tuple.getScore());
                category_list.add(category);
            }
        }
        return category_list;
    }
}
