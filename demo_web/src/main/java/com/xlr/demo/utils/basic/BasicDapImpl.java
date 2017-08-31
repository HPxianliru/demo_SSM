package com.xlr.demo.utils.basic;

import com.xlr.demo.bean.Teacher;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @deprecated mybatis源码探究
 * @Author: xlr
 * @Date: Created in 下午11:12 2017/8/29
 */
public abstract class BasicDapImpl<T> implements BasicDao{

    private SqlSessionFactoryBean factory ;

    protected static final String INSERT = "insert";

    protected static final String INSERTSELECTIVE = "insertSelective";


    protected static final String UPDATE = "updateByPrimaryKey";

    protected static final String UPDATESELECTIVE = "updateByPrimaryKeySelective";


    protected static final String DELETE_BY_ID = "deleteByPrimaryKey";


    protected static final String GET_BY_ID = "selectByPrimaryKey";


    protected static final String DOT = ".";

    protected Class<T> entityClass = null;

    protected String getStatementPrefix() {
        return entityClass.getSimpleName() + DOT;
    }

    public BasicDapImpl() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass =  (Class)params[0];
    }

    public void setSessionFactory(SqlSessionFactoryBean factory) {
        this.factory = factory;
    }

    protected SqlSession getSession() {

        try {
            return factory.getObject().openSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public T find(Long id) {
        @SuppressWarnings("unchecked")
        List<T> list = getSession().selectList(getStatementPrefix()+GET_BY_ID, id);
        if(list.isEmpty()){
            try {
                throw new Exception("查询数据失败");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
