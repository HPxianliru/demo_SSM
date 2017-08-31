package com.xlr.demo.utils.basic;

import com.xlr.demo.bean.Teacher;

import java.io.Serializable;

/**
 * @Author: xlr
 * @Date: Created in 下午11:10 2017/8/29
 */
 public interface BasicDao<T extends Serializable> {

    int deleteByPrimaryKey(Integer tId);

    int insert(Teacher record);

    int insertSelective(T record);

    <T> T  selectByPrimaryKey(Integer tId);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(Teacher record);
}
