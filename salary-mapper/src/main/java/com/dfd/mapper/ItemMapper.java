package com.dfd.mapper;


import com.dfd.base.DfdMapper;
import com.dfd.entity.Item;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface ItemMapper extends DfdMapper<Item> {

}