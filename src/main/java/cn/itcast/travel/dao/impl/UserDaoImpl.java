package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author fly
 * @date 2019/8/24
 */
public class UserDaoImpl implements UserDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByUsername(String username) {
        String sql = "select * from tab_user where username = ?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
            System.out.println("findByUsername用户名有重复");
        } catch (DataAccessException e) {
//            e.printStackTrace();
            System.out.println("findByUsername用户名无重复");
        }
        return user;
    }

    @Override
    public void save(User user) {
        String sql = "insert into tab_user (username, password, email, name, telephone, sex, birthday, status, code) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        template.update(sql, user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getName(),
                user.getTelephone(),
                user.getSex(), user.getBirthday(),
                user.getStatus(),
                user.getCode());
    }


    @Override
    public User findByCode(String code) {
        String sql = "select * from tab_user where code = ?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), code);
        } catch (DataAccessException e) {
            e.printStackTrace();
            System.out.println("findByCode用户不存在");
        }
        return user;
    }

    @Override
    public void updateStatus(User user) {
        String sql = "update tab_user set status = 'Y' where uid = ?";
        template.update(sql, user.getUid());
    }


    @Override
    public User findByUsernameAndPassword(String username, String password) {
        String sql = "select * from tab_user where username = ? and password = ?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username, password);
        } catch (DataAccessException e) {
//            e.printStackTrace();
            System.out.println("findByUsernameAndPassword密码错误");
        }
        return user;
    }
}
