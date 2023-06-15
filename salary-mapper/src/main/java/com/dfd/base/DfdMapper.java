package com.dfd.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author yy
 * @date 2023/3/29 16:42
 */
//public interface DfdMapper<T> extends Mapper<T>, MySqlMapper<T>, BaseMapper<T> {
public interface DfdMapper<T> extends BaseMapper<T> {
}
