package com.dfd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.*;
import com.dfd.entity.ItemMember;
import com.dfd.utils.PageResult;
import com.dfd.vo.MemberInfoVO;

import java.util.List;

/**
 * @author yy
 * @date 2023/6/7 16:23
 */
public interface ItemMemberService extends IService<ItemMember> {

    /**
     * 查询当前项目下的人员信息
     * @param itemMemberQueryDTO
     * @return
     */
    PageResult<MemberInfoVO> queryItemMemberList(ItemMemberQueryDTO itemMemberQueryDTO);


    void addItemMemberList(ItemMemberAddListDTO itemMemberAddListDTO);

    /**
     * 根据itemUid更新项目下的项目成员
     * @param itemUid
     * @param nitemIds
     */
    void updateMembersByItemId(String itemUid, List<String> nitemIds);

}
