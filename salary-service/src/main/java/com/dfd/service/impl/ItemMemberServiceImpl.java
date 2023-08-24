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
import com.dfd.entity.PerformanceSalary;
import com.dfd.entity.User;
import com.dfd.mapper.ItemMemberMapper;
import com.dfd.service.ItemMemberService;
import com.dfd.service.MemberService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.PageResult;
import com.dfd.utils.UUIDUtil;
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

    @Override
    public void addItemMemberList(ItemMemberAddListDTO itemMemberAddListDTO) {
        LambdaQueryWrapper<ItemMember> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(itemMemberAddListDTO.getItemUid()), ItemMember:: getItemUid, itemMemberAddListDTO.getItemUid())
                .eq(ItemMember::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<ItemMember> omemberUids = baseMapper.selectList(queryWrapper);
        List<String> oUids = omemberUids.stream().map(ItemMember::getMemberUid).collect(Collectors.toList());

        List<ItemMember> result = new ArrayList<>();
        User currentUser = UserRequest.getCurrentUser();
        itemMemberAddListDTO.getUids().stream().forEach(uid ->{
            if(!oUids.contains(uid)){
                ItemMember itemMember = new ItemMember();
                itemMember.setUid(UUIDUtil.getUUID32Bits())
                        .setItemUid(itemMemberAddListDTO.getItemUid())
                        .setMemberUid(uid)
                        .setDeclareTime(new Date())
                        .setCreatedBy(currentUser.getNumber())
                        .setUpdatedBy(currentUser.getNumber())
                        .setCreatedTime(new Date())
                        .setUpdatedTime(new Date())
                        .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
                result.add(itemMember);
            }
        });
        boolean b = this.saveBatch(result);
        if (!b) {
            throw new BusinessException("项目中添加人员状态保存失败");
        }

        //将项目人员刷新到其他工资类别中去

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
    public void updateMembersByItemId(String itemUid, List<String> nMemberUids) {
        User currentUser = UserRequest.getCurrentUser();

        LambdaUpdateWrapper<ItemMember> itemMemberWrapper = new LambdaUpdateWrapper<>();
        itemMemberWrapper.eq(StringUtils.isNotBlank(itemUid), ItemMember:: getItemUid, itemUid)
                .eq(ItemMember::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<ItemMember> oMembers = list(itemMemberWrapper);

        oMembers.stream().forEach(e ->{
            e.setRemarks(String.valueOf(new Date()))
                    .setIsDeleted(String.valueOf(System.currentTimeMillis()))
                    .setDeclareTime(new Date())
                    .setUpdatedBy(currentUser.getNumber())
                    .setUpdatedTime(new Date());
        });
        if(CollectionUtil.isNotEmpty(oMembers)){
            boolean delete = updateBatchById(oMembers);
            if(!delete){
                throw new BusinessException("项目删除原来项目人员失败！");
            }
        }

        //批量新增
        List<ItemMember> upItemMembers = new ArrayList<>();
        nMemberUids.stream().forEach(e ->{
            ItemMember itemMember = new ItemMember();
            BeanUtil.copyProperties(e,itemMember);
            itemMember.setUid(UUIDUtil.getUUID32Bits()).setItemUid(itemUid)
                    .setMemberUid(e)
                    .setDeclareTime(new Date())
                    .setCreatedBy(currentUser.getNumber())
                    .setCreatedTime(new Date())
                    .setUpdatedBy(currentUser.getNumber())
                    .setUpdatedTime(new Date())
                    .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
            upItemMembers.add(itemMember);
        });
        boolean update = saveBatch(upItemMembers);
        if ( !update) {
            throw new BusinessException("根据项目id更新项目人员数据失败!");
        }
    }
}
