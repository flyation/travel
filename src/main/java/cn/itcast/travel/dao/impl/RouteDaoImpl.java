package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fly
 * @version 1.0
 * @className RouteDaoImpl
 * @description TODO
 * @date 2019/9/28
 */
public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCount(int cid, String rname) {
        //sql模板
        String sql = "select count(*) from tab_route where 1 = 1";
        //查询参数集合
        List params = new ArrayList();
        //拼接查询语句
        StringBuilder sb = new StringBuilder(sql);
        if (cid != 0) {
            sb.append(" and cid = ?");
            params.add(cid);
        }
        if (rname != null && rname.length() > 0) {
            sb.append(" and rname like ?");
            params.add("%" + rname + "%");
        }

        sql = sb.toString();

        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
//        String sql = "select * from tab_route where cid = ? and rname like ? limit ? , ?";
        //sql模板
        String sql ="select * from tab_route where 1 = 1";
        //查询参数集合
        List params = new ArrayList();
        //拼接查询语句
        StringBuilder sb = new StringBuilder(sql);
        if (cid != 0) {
            sb.append(" and cid = ?");
            params.add(cid);
        }
        if (rname != null && rname.length() > 0) {
            sb.append(" and rname like ?");
            params.add("%" + rname + "%");
        }
        sb.append(" limit ? , ?");
        params.add(start);
        params.add(pageSize);

        sql = sb.toString();

        return template.query(sql, new BeanPropertyRowMapper<>(Route.class), params.toArray());
    }

    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ?";
        Route route = template.queryForObject(sql, new BeanPropertyRowMapper<>(Route.class), rid);
        return route;
    }
}
