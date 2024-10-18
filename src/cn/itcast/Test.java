package cn.itcast;

import cn.itcast.dao.impl.StudentDaoImpl;
import cn.itcast.dao.impl.UserDaoImpl;
import cn.itcast.domain.Student;
import cn.itcast.domain.User;

public class Test {
    public static void main(String[] args) {
        UserDaoImpl impl=new UserDaoImpl();
        User zhangsan = impl.findUserByUsernameAndPassword("zhangsan", "123456");
        System.out.println(zhangsan);
    }
}
