package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.*;
import com.dfd.entity.ItemMember;
import com.dfd.entity.Member;
import com.dfd.entity.User;
import com.dfd.mapper.ItemMemberMapper;
import com.dfd.service.ItemMemberService;
import com.dfd.service.MemberService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.PageResult;
import com.dfd.vo.MemberInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yy
 * @date 2023/6/7 16:24
 */
@Service
public class ItemMemberServiceImpl extends ServiceImpl<ItemMemberMapper, ItemMember> implements ItemMemberService {

    @Autowired
    private ItemMemberMapper itemMemberMapper;

    @Autowired
    private MemberService memberService;

    @Override
    public PageResult<MemberInfoVO> queryItemMemberList(ItemMemberQueryDTO itemMemberQueryDTO) {
        LambdaQueryWrapper<ItemMember> queryWrapper = new LambdaQueryWrapper();
        String itemUid = itemMemberQueryDTO.getItemUid();
        queryWrapper.eq(StringUtils.isNotBlank(itemUid), ItemMember:: getItemUid, itemUid)
                .eq(ItemMember::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<ItemMember> itemMembers = this.list(queryWrapper);
        List<String> collect = itemMembers.stream().map(ItemMember::getMemberUid).collect(Collectors.toList());

        LambdaQueryWrapper<Member> wrapper = new LambdaQueryWrapper();
        wrapper.in(CollectionUtil.isNotEmpty(collect),Member::getUid,collect)
                .eq(Member::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<Member> members = memberService.getBaseMapper().selectList(wrapper);
        PageResult<MemberInfoVO> pageResult = new PageResult(itemMemberQueryDTO.getCurrentPage(),itemMemberQueryDTO.getPageSize(),convertToSalaryInfoVO(itemUid,members));
        return pageResult;
    }

    private List<MemberInfoVO> convertToSalaryInfoVO(String itemUid,List<Member> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<MemberInfoVO> result = list.stream().map(element -> {
            if(!Optional.ofNullable(element).isPresent()){
                throw new BusinessException("用户基本数据为空");
            }
            MemberInfoVO infoVO = new MemberInfoVO();
            BeanUtil.copyProperties(element,infoVO);
            infoVO.setItemMemberUid(itemUid);
            return infoVO;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public void updateMembersByItemId(String itemUid, List<String> nMemberIds) {
        User currentUser = UserRequest.getCurrentUser();

        LambdaUpdateWrapper<ItemMember> itemMemberWrapper = new LambdaUpdateWrapper<>();
        itemMemberWrapper.eq(StringUtils.isNotBlank(itemUid), ItemMember:: getItemUid, itemUid)
                .eq(ItemMember::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<ItemMember> oMembers = list(itemMemberWrapper);

        List<ItemMember> nMembers = this.listByIds(nMemberIds);

        //批量删除
        List<ItemMember> odifferentElements = oMembers.stream()
                .filter(obj1 -> nMembers.stream().noneMatch(obj2 -> obj1.getId() == obj2.getId()))
                .collect(Collectors.toList());
        List<ItemMember> delItemMembers = new ArrayList<>();
        odifferentElements.stream().forEach(e ->{
            ItemMember itemMember = new ItemMember();
            BeanUtil.copyProperties(e,itemMember);
            itemMember.setUpdatedBy(currentUser.getPhone())
                    .setIsDeleted(String.valueOf(System.currentTimeMillis()));
            delItemMembers.add(itemMember);
        });
        boolean delete = updateBatchById(delItemMembers);

        //批量新增
        List<ItemMember> upItemMembers = new ArrayList<>();
        List<ItemMember> ndifferentElements = nMembers.stream()
                .filter(obj1 -> oMembers.stream().noneMatch(obj2 -> obj1.getId() == obj2.getId()))
                .collect(Collectors.toList());
        ndifferentElements.stream().forEach(e ->{
            ItemMember itemMember = new ItemMember();
            BeanUtil.copyProperties(e,itemMember);
            itemMember.setItemUid(itemUid)
                    .setUpdatedBy(currentUser.getPhone())
                    .setUpdatedTime(new Date())
                    .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
            upItemMembers.add(itemMember);
        });
        boolean update = updateBatchById(upItemMembers);

        if (!delete || !update) {
            throw new BusinessException("根据项目id更新项目人员数据失败!");
        }
    }
}
