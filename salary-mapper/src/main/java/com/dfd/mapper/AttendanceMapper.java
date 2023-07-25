package com.dfd.mapper;


import com.dfd.base.DfdMapper;
import com.dfd.entity.Attendance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttendanceMapper extends DfdMapper<Attendance> {
    Integer updateByItemUid(@Param("list") List<Attendance> list);
}