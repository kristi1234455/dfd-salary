package com.dfd.service;

import com.dfd.dto.ItemMemberAddListDTO;
import com.dfd.dto.ItemMemberDTO;
import com.dfd.entity.User;

import java.util.List;

/**
 * @author yy
 * @date 2023/6/9 17:26
 */
public interface FlushItemMemberService {

    /**
     * 项目人员新增刷新到其他类别
     * todo:查询条件匹配 / 定时任务刷新
     */
    void flushToSalaryCategory(ItemMemberAddListDTO itemMemberAddListDTO);

    //TODO:其他类别查找人员的时候，找到当前项目下绑定的人员，与日期无关

    //todo:其他类别人员更新、删除

    //todo:其他类别刷新到项目人员


}
