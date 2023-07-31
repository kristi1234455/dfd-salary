package com.dfd.mapper;


import com.dfd.base.DfdMapper;
import com.dfd.entity.TotalSalaryRoom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TotalSalaryRoomMapper extends DfdMapper<TotalSalaryRoom> {


    Integer updateByItemUid(@Param("list") List<TotalSalaryRoom> list);
}