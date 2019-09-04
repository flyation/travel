package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

/**
 * @author fly
 * @date 2019/8/24
 */
public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();

    @Override
    public boolean regist(User user) {
        //1.根据用户名查询用户对象
        User u = dao.findByUsername(user.getUsername());

        //判断是否重名
        if (u != null) {
            //用户名存在,注册失败
            return false;
        }

        //用户名不存在
        //2.保存用户信息
        //2.1设置激活码,唯一字符串
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        dao.save(user);

        //2.2设置邮件正文
        String content = "<a href='http://localhost/travel/activeUserServlet?code="+ user.getCode() +"'>点击激活[旅游网]</a>";
        MailUtils.sendMail(user.getEmail(), content, "激活邮件");

        return true;


    }

    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        User user =  dao.findByCode(code);

        if (user != null) {
            //修改激活状态
            dao.updateStatus(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User login(User user) {
        User u = dao.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        return u;
    }
}
