package cn.itcast.dao;

import cn.itcast.domain.User;

public interface UserDao {
    User findUserByUsernameAndPassword(String username, String password);
    void add(User user);
}
