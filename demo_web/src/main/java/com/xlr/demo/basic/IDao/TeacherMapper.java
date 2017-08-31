package com.xlr.demo.basic.IDao;

import com.xlr.demo.bean.Teacher;

public interface TeacherMapper {

    int deleteByPrimaryKey(Integer tId);

    int insert(Teacher record);

    int insertSelective(Teacher record);

    Teacher selectByPrimaryKey(Integer tId);

    int updateByPrimaryKeySelective(Teacher record);

    int updateByPrimaryKey(Teacher record);
}