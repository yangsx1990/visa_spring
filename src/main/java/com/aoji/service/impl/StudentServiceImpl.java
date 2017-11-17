package com.aoji.service.impl;

import com.aoji.mapper.StudentInfoMapper;
import com.aoji.model.StudentInfo;
import com.aoji.model.SysPermission;
import com.aoji.service.StudentService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yangsaixing
 * @description
 * @date Created in 上午11:15 2017/11/10
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentInfoMapper studentInfoMapper;

    @Override
    public List<StudentInfo> getList(StudentInfo studentInfo) {
        return studentInfoMapper.select(studentInfo);
    }

    @JsonView
    public static void main(String[] args) {

    }
}
