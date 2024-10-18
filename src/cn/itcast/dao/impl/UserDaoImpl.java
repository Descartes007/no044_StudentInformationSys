package cn.itcast.dao.impl;

import cn.itcast.dao.UserDao;
import cn.itcast.domain.Student;
import cn.itcast.domain.User;
import cn.itcast.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        try {
            String sql = "select * from user where username = ? and password = ?";
            User student = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return student;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    @Override
    public void add(User user) {
        //1.定义sql
        String sql = "insert into user values(null,?,?)";
        //2.执行sql
        template.update(sql, user.getUsername(),user.getPassword());
    }
}
