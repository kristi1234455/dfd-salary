package com.dfd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.dto.ItemMemberDelDTO;
import com.dfd.dto.ItemMemberInfoDTO;
import com.dfd.dto.ItemMemberQueryDTO;
import com.dfd.dto.MemberQueryDTO;
import com.dfd.entity.ItemMember;
import com.dfd.mapper.ItemMemberMapper;
import com.dfd.service.ItemMemberService;
import com.dfd.utils.PageResult;
import com.dfd.vo.ItemMemberInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yy
 * @date 2023/6/7 16:24
 */
@Service
public class ItemMemberServiceImpl extends ServiceImpl<ItemMemberMapper, ItemMember> implements ItemMemberService {

    @Autowired
    private ItemMemberMapper itemMemberMapper;

    @Override
    public PageResult<ItemMemberInfoVO> queryItemMemberList(MemberQueryDTO memberQueryDTO) {
        return null;
    }

    @Override
    public PageResult<ItemMemberInfoVO> queryItemMemberList(ItemMemberQueryDTO itemMemberQueryDTO) {
        return null;
    }

    @Override
    public void add(ItemMemberInfoDTO itemMemberInfoDTO) {

    }

    @Override
    public void delete(ItemMemberDelDTO itemMemberDelDTO) {

    }
}
