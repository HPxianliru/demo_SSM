package com.xlr.demo.basic.IDao;

import com.xlr.demo.bean.Clazz;

public interface ClazzMapper {

    int deleteByPrimaryKey(Integer cId);

    int insert(Clazz record);

    int insertSelective(Clazz record);

    Clazz selectByPrimaryKey(Integer cId);

    int updateByPrimaryKeySelective(Clazz record);

    int updateByPrimaryKey(Clazz record);

    Clazz getClazzyById(Integer cId);

}