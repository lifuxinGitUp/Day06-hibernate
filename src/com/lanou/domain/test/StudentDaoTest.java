package com.lanou.domain.test;

import com.lanou.dao.StudentDao;
import com.lanou.dao.impl.StudentDaoImpl;
import com.lanou.domain.Student;
import org.junit.Test;

/**
 * Created by dllo on 17/10/17.
 */
public class StudentDaoTest  {

    @Test
    public void save(){
        // 创建一个dao层的对应
        StudentDao dao = new StudentDaoImpl();

        // 创建要保存的对象
        Student student = new Student("zhy","男",24);
        System.out.println("保存前--->>>" + student);
        dao.save(student);
        System.out.println("保存后--->>>" + student);

    }

    @Test
    public void login(){
        StudentDao dao = new StudentDaoImpl();
        boolean result = dao.login("aaa", "123");
        System.out.println("登录结果: " + result);

    }


}
