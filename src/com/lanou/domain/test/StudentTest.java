package com.lanou.domain.test;


import com.lanou.domain.Student;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


/**
 * Created by dllo on 17/10/17.
 * 针对Student的单元测试
 */
public class StudentTest {

//    Session的工厂类对象, 是线程安全的, 一个数据库对应一个session工厂类对象
//    通常早项目启动时初始化该对象,由Configuration对象创建
    private SessionFactory sessionFactory;
//    真正执行CURD操作的数据库连接对象, 代表了一次数据库连接,是非线程安全的
    private Session session;
//    事物对象 与jdbc中的事物对象类似,只不过hibernate的事物是不制动提交的,而jdbc中的事物会制动提交
//    hibernate中的事物需要手动提交
    private Transaction transaction;





//    初始化操作
    @Before  // before注解; 在Test注解之前执行的方法,通常该方法中做一些初始化的操作
    public void init(){
        System.out.println("init");
        // 创建配置对象
        Configuration configuration = new Configuration();
        // 加载指定配置文件
        configuration.configure("hibernate.cfg.xml");
        // 通过配置对象创建一个sessionFactory对象,创建结束之后configuration
//        就与sessionFactory失去关联了
        sessionFactory = configuration.buildSessionFactory();
        //打开一个数据库连接
        session = sessionFactory.openSession();
        // 开启事物
        transaction = session.beginTransaction();
    }
//    销毁操作
    @After // After注解: 在Test注解之后执行的方法,通常该方法做一些释放关闭的操作
    public void destroy(){
        System.out.println("destroy");
        // 提交本次事物
        transaction.commit();
        // 关闭本次连接
        session.close();


    }
//    数据库插入操作
    @Test // Test 注解: 单元测试执行的方法
    public void insert(){
        System.out.println("inert");

        Student student = new Student("张三","男",23);
        // 将实体类对象保存到数据库中
        session.save(student);
    }
    // 数据库查询操作
    @Test
    public void query(){
        // 获得一个查询对象 等同于select * from student
        Query query = session.createQuery("FROM Student where sname=?");
        // 条件语句中的站位符参数对应
        query.setString(0,"张三");

        // 返回查询的结果集
        List<Student> students = query.list();
        // 遍历结果集
        for (Student stu : students) {
            System.out.println(stu);
        }
    }
    // 数据库删除操作
    @Test
    public void delete(){
        // 从数据库中查询要删除的对象
        Query query = session.createQuery("from Student where sname=?");
        query.setString(0,"张三");
        List<Student> students = query.list();
        if (students.size() > 0){
            session.delete(students.get(0));
        }
    }

    // 数据库修改操作
    @Test
    public void update(){
        Query query = session.createQuery("from Student where sname=?");
        query.setString(0,"张三");
        List<Student> students = query.list();
        if (students.size() > 0){
            // 取出集合中一个叫张三的学生
            Student student = students.get(0);
            // 修改该学生的基础信息
            student.setSname("李四");
            student.setGender("女");
            student.setAge(32);
            session.update(student); // 更新学生对象
        }
    }

    // 查询所有
    @Test
    public void createCriteria(){
        // 获得要进行查询的Student的接口对象,他是比HQL更加高级的查询方式
        Criteria criteria = session.createCriteria(Student.class);
        // 设置最多的查询条数
        criteria.setMaxResults(2);

        // 获取查询的结果集
        List<Student> students = criteria.list();
        // 遍历结果集
        for (Student stu : students) {
            System.out.println(stu);
        }
    }

    // 查询单个结果, 只查询第0个位置的结果
    @Test
    public void querySingle(){
        Query query = session.createQuery("from Student where id=2");
        // 返回单个结果
        Student student = (Student) query.uniqueResult();
        System.out.println(student);
    }


}
