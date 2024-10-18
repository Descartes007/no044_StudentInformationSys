package cn.itcast.dao.impl;

import cn.itcast.dao.StudentDao;
import cn.itcast.domain.Student;
import cn.itcast.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StudentDaoImpl implements StudentDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Student> findAll() {
        //使用JDBC操作数据库...
        //1.定义sql
        String sql = "select * from student";
        List<Student> students = template.query(sql, new BeanPropertyRowMapper<Student>(Student.class));

        return students;
    }



    @Override
    public void add(Student student) {
        //1.定义sql
        String sql = "insert into student values(null,?,?,?,?,?,?)";
        //2.执行sql
        template.update(sql, student.getName(), student.getGender(), student.getAge(), student.getAddress(), student.getQq(), student.getEmail());
    }

    @Override
    public void delete(int id) {
        //1.定义sql
        String sql = "delete from student where id = ?";
        //2.执行sql
        template.update(sql, id);
    }

    @Override
    public Student findById(int id) {
        String sql = "select * from student where id = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Student>(Student.class), id);
    }

    @Override
    public void update(Student student) {
        String sql = "update student set name = ?,gender = ? ,age = ? , address = ? , qq = ?, email = ? where id = ?";
        template.update(sql, student.getName(), student.getGender(), student.getAge(), student.getAddress(), student.getQq(), student.getEmail(), student.getId());
    }

    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        //1.定义模板初始化sql
        String sql = "select count(*) from student where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        //2.遍历map
        Set<String> keySet = condition.keySet();
        //定义参数的集合
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {

            //排除分页条件参数
            if("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }

            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if(value != null && !"".equals(value)){
                //有值
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//？条件的值
            }
        }
        System.out.println(sb.toString());
        System.out.println(params);

        return template.queryForObject(sb.toString(),Integer.class,params.toArray());
    }

    @Override
    public List<Student> findByPage(int start, int rows, Map<String, String[]> condition) {
        String sql = "select * from student  where 1 = 1 ";

        StringBuilder sb = new StringBuilder(sql);
        //2.遍历map
        Set<String> keySet = condition.keySet();
        //定义参数的集合
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {

            //排除分页条件参数
            if("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }

            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if(value != null && !"".equals(value)){
                //有值
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//？条件的值
            }
        }

        //添加分页查询
        sb.append(" limit ?,? ");
        //添加分页查询参数值
        params.add(start);
        params.add(rows);
        sql = sb.toString();
        System.out.println(sql);
        System.out.println(params);

        return template.query(sql,new BeanPropertyRowMapper<Student>(Student.class),params.toArray());
    }
}
