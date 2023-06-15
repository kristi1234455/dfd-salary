package com.dfd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.ItemMemberDelDTO;
import com.dfd.dto.ItemMemberInfoDTO;
import com.dfd.dto.ItemMemberQueryDTO;
import com.dfd.dto.MemberQueryDTO;
import com.dfd.entity.ItemMember;
import com.dfd.utils.PageResult;
import com.dfd.vo.ItemMemberInfoVO;

import java.util.List;

/**
 * @author yy
 * @date 2023/6/7 16:23
 */
public interface ItemMemberService extends IService<ItemMember> {

    /**
     * 查询所有人员信息
     * @param memberQueryDTO
     * @return
     */
    PageResult<ItemMemberInfoVO> queryItemMemberList(MemberQueryDTO memberQueryDTO);

    /**
     * 查询当前项目下的人员信息
     * @param itemMemberQueryDTO
     * @return
     */
    PageResult<ItemMemberInfoVO> queryItemMemberList(ItemMemberQueryDTO itemMemberQueryDTO);

    /**
     * 新增人员
     * @param itemMemberInfoDTO
     */
    void add(ItemMemberInfoDTO itemMemberInfoDTO);

    void delete(ItemMemberDelDTO itemMemberDelDTO);

}
