package com.lanou.dao.impl;

import com.lanou.dao.BaseDao;
import com.lanou.domain.test.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by dllo on 17/10/17.
 */
public class BaseDaoImpl<T> implements BaseDao<T> {

    @Override
    public T save(T t) {

        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(t);
        transaction.commit();
        HibernateUtil.closeSession();
        return t;
    }

    @Override
    public void delete(T t) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(t);
        transaction.commit();
        HibernateUtil.closeSession();
    }

    @Override
    public void update(T t) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.update(t); // 跟新某个对象
        transaction.commit();
        HibernateUtil.closeSession();

    }

    @Override
    public T get(Class<T> c, Serializable id) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        // 根据主键id查询某个对象
        T t = session.get(c,id);
        transaction.commit();
        HibernateUtil.closeSession();
        // 返回查询到的t对象
        return t;
    }

    @Override
    public T find(String hql, Map<String, Object> params) {
        // 先调用查询所有
        List<T> tlist = findAll(hql,params);
        if (tlist.size() > 0){
            return tlist.get(0); // 返回查询到的第一个对象
        }
        return null; //如果没有符合条件的返回null对象
    }

    @Override
    public List<T> findAll(String hql, Map<String, Object> params) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        // 创建sql查询语句
        Query query = session.createQuery(hql);
        // 判断参数列表是否为空
        if (params != null && params.size()>0){
            // 遍历参数map集合
            for (String key : params.keySet()) {
                // 设置条件语句中对应的参数
                // 第一个参数对应列名,第二个参数对应列值
                query.setParameter(key,params.get(key));
            }
        }
        // 得到符合查询语句的集合
        List<T> tlist = query.list();
        transaction.commit();
        HibernateUtil.closeSession();
        return tlist; //返回查询结果集合
    }
}
