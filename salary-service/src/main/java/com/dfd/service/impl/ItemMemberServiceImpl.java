package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.*;
import com.dfd.entity.ItemMember;
import com.dfd.entity.User;
import com.dfd.mapper.ItemMemberMapper;
import com.dfd.service.ItemMemberService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.PageResult;
import com.dfd.vo.ItemMemberInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yy
 * @date 2023/6/7 16:24
 */
@Service
public class ItemMemberServiceImpl extends ServiceImpl<ItemMemberMapper, ItemMember> implements ItemMemberService {

    @Autowired
    private ItemMemberMapper itemMemberMapper;

    @Override
    public PageResult<ItemMemberInfoVO> queryItemMemberList(ItemMemQueryDTO memberQueryDTO) {
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

    @Override
    public void updateMembersByItemId(String itemUid, List<Integer> nMemberIds) {
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
