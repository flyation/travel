package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

/**
 * @author fly
 * @date 2019/8/24
 */
public interface UserService {
    /**
     * 用户注册
     * @param user
     * @return
     */
    boolean regist(User user);

    /**
     * 用户激活
     * @param code
     * @return
     */
    boolean active(String code);

    /**
     * 用户登录
     * @param user
     * @return
     */
    User login(User user);
}
