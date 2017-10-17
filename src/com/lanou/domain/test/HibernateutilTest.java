package com.lanou.domain.test;


import com.lanou.domain.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.util.List;

/**
 * Created by dllo on 17/10/17.
 */
public class HibernateutilTest {

    @Test
    public void findAll(){
        // 通过工具类获得一个session对象
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        // 查询学生表中所有的对象
        List<Student> students = session.createQuery("from Student ").list();
        // 遍历
        for (Student stu : students) {
            System.out.println(stu);
        }
        // 提交本次事务
        transaction.commit();
        HibernateUtil.closeSession(); // 使用工具类关闭session对象
    }


}
