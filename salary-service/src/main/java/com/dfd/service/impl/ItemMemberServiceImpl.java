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
import com.dfd.enums.ItemStageEnum;
import com.dfd.mapper.ItemMemberMapper;
import com.dfd.service.FlushItemMemberService;
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
    private FlushItemMemberService flushItemMemberService;

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
        List<ItemMemberDTO> itemMemberDTOS = itemMemberAddListDTO.getItemMemberDTOS();
        String itemUid = itemMemberAddListDTO.getItemUid();
        String currentUserNumber = itemMemberAddListDTO.getCurrentUserNumber();
        if (CollectionUtil.isNotEmpty(itemMemberDTOS)) {
            List<String> memberUids = itemMemberDTOS.stream().map(e -> e.getMemberUid()).collect(Collectors.toList());
            List<ItemMember> memberList = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(memberUids)) {
                memberUids.stream().forEach(e -> {
                    ItemMember itemMember = new ItemMember()
                            .setItemUid(itemUid)
                            .setMemberUid(e)
                            .setUid(UUIDUtil.getUUID32Bits())
                            .setDeclareTime(new Date())
                            .setCreatedBy(currentUserNumber)
                            .setUpdatedBy(currentUserNumber)
                            .setCreatedTime(new Date())
                            .setUpdatedTime(new Date())
                            .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
                    memberList.add(itemMember);
                });
                boolean var1 = this.saveBatch(memberList);
                if (!var1) {
                    throw new BusinessException("項目成員数据批量保存失败!");
                }
                flushItemMemberService.flushToSalaryCategory(itemMemberAddListDTO);
            }
        }
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

        //批量删除原来项目人员
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

        List<ItemMemberDTO> itemMemberDTOS = upItemMembers.stream().map(e->{
            ItemMemberDTO itemMemberDTO = ItemMemberDTO.builder()
                    .memberUid(e.getMemberUid())
                    .build();
             return itemMemberDTO;
        }).collect(Collectors.toList());
        ItemMemberAddListDTO itemMemberAddListDTO = ItemMemberAddListDTO.builder()
                .itemUid(itemUid)
                .itemStage(Integer.parseInt(ItemStageEnum.STAGE_EARLIER.getCode()))
                .currentUserNumber(currentUser.getNumber())
                .itemMemberDTOS(itemMemberDTOS)
                .build();
        flushItemMemberService.flushToSalaryCategory(itemMemberAddListDTO);
    }
}
