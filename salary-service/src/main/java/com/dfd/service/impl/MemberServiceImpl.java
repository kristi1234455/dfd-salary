package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.*;
import com.dfd.entity.*;
import com.dfd.mapper.MemberMapper;
import com.dfd.service.ItemMemberService;
import com.dfd.service.MemberService;
import com.dfd.service.util.ExcelUtils;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.PageResult;
import com.dfd.utils.UUIDUtil;
import com.dfd.vo.BidSalaryInfoVO;
import com.dfd.vo.DesignSalaryInfoVO;
import com.dfd.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Override
    public PageResult<MemberInfoVO> queryMemberList(MemberQueryDTO memberQueryDTO) {
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(memberQueryDTO.getName()), Member:: getName, memberQueryDTO.getName())
                .like(memberQueryDTO.getNumber() !=null, Member:: getNumber, memberQueryDTO.getNumber())
                .eq(Member::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(Member :: getCreatedTime);
        List<Member> olist = baseMapper.selectList(queryWrapper);
        List<MemberInfoVO> list = convertToSalaryInfoVO(olist);
        return PageResult.infoPage(olist.size(), memberQueryDTO.getCurrentPage(),memberQueryDTO.getPageSize(),list);
    }

    private List<MemberInfoVO> convertToSalaryInfoVO(List<Member> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<MemberInfoVO> result = list.stream().map(element -> {
            if(!Optional.ofNullable(element).isPresent()){
                throw new BusinessException("用户基本数据为空");
            }
            MemberInfoVO infoVO = new MemberInfoVO();
            BeanUtil.copyProperties(element,infoVO);
            return infoVO;
        }).collect(Collectors.toList());
        return result;
    }



    @Override
    public void add(MemberAddDTO memberAddDTO) {
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(memberAddDTO.getNumber()), Member:: getNumber, memberAddDTO.getNumber())
                .eq(Member::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(baseMapper.exists(queryWrapper)){
            throw new BusinessException("添加失败，该工号的用户已经存在！");
        }
        User currentUser = UserRequest.getCurrentUser();
        Member member = new Member();
        BeanUtil.copyProperties(memberAddDTO,member);
        member.setUid(UUIDUtil.getUUID32Bits())
                .setCreatedBy(currentUser.getNumber())
                .setUpdatedBy(currentUser.getNumber())
                .setCreatedTime(new Date())
                .setUpdatedTime(new Date())
                .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
        boolean b = this.saveOrUpdate(member);
        if (!b) {
            throw new BusinessException("基本用户数据保存失败");
        }
    }

    @Override
    public void update(MemberUpdateDTO memberUpdateDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<Member> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StringUtils.isNotBlank(memberUpdateDTO.getUid()), Member:: getUid, memberUpdateDTO.getUid())
                .eq(Member::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                .set(StringUtils.isNotBlank(memberUpdateDTO.getNumber()), Member:: getNumber, memberUpdateDTO.getNumber())
                .set(StringUtils.isNotBlank(memberUpdateDTO.getName()), Member:: getName, memberUpdateDTO.getName())
                .set(StringUtils.isNotBlank(memberUpdateDTO.getDepartment()), Member:: getDepartment, memberUpdateDTO.getDepartment())
                .set(StringUtils.isNotBlank(memberUpdateDTO.getRanks()), Member:: getRanks, memberUpdateDTO.getRanks())
                .set(StringUtils.isNotBlank(memberUpdateDTO.getManager()), Member:: getManager, memberUpdateDTO.getManager())
                .set(StringUtils.isNotBlank(memberUpdateDTO.getPost()), Member:: getPost, memberUpdateDTO.getPost())
                .set(StringUtils.isNotBlank(memberUpdateDTO.getMajor()), Member:: getMajor, memberUpdateDTO.getMajor())
                .set(StringUtils.isNotBlank(memberUpdateDTO.getRemarks()), Member:: getRemarks, memberUpdateDTO.getRemarks())
                .set(Member:: getUpdatedBy, currentUser.getNumber())
                .set(Member:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("用户基本数据更新失败!");
        }
    }

    @Override
    public void delete(MemberDelDTO memberDelDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<Member> updateWrapper = new UpdateWrapper<Member>()
                .lambda()
                .eq(Member::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                .in(!CollectionUtils.isEmpty(memberDelDTO.getUids()), Member:: getUid, memberDelDTO.getUids())
                .set(Member:: getIsDeleted, System.currentTimeMillis())
                .set(Member:: getUpdatedBy, currentUser.getNumber())
                .set(Member:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("用户基本数据删除失败!");
        }
    }


    @Override
    public List<Member> queryMemberByUids(List<String> uids) {
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(CollectionUtil.isNotEmpty(uids), Member::getUid, uids)
                .eq(Member::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<Member> members = baseMapper.selectList(queryWrapper);
        return members;
    }

    @Override
    public Map<String, String> queryNameByUids(List<String> uids) {
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(CollectionUtil.isNotEmpty(uids), Member::getUid, uids)
                .eq(Member::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<Member> members = baseMapper.selectList(queryWrapper);
        Map<String, String> itemMemberNames = members.stream().collect(Collectors.toMap(Member::getUid, Member::getName));
        return itemMemberNames;
    }

    @Override
    public Map<String, String> queryNumberByUids(List<String> uids) {
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(CollectionUtil.isNotEmpty(uids), Member::getUid, uids);
        List<Member> members = baseMapper.selectList(queryWrapper);
        Map<String, String> itemMemberNumbers = members.stream().collect(Collectors.toMap(Member::getUid, Member::getNumber));
        return itemMemberNumbers;
    }

    @Override
    public Member queryMemberByNumber(String number) {
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotEmpty(number), Member::getNumber,number)
                .eq(Member::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        Member member = baseMapper.selectOne(queryWrapper);
        return member;
    }

    @Override
    public Map<String, MemberVO> queryMemberByNumber(List<String> uids) {
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(CollectionUtil.isNotEmpty(uids), Member::getUid, uids);
        List<Member> members = baseMapper.selectList(queryWrapper);
        List<MemberVO> collect = members.stream().map(e -> {
            MemberVO memberVO = new MemberVO();
            BeanUtils.copyProperties(e, memberVO);
            return memberVO;
        }).collect(Collectors.toList());
        Map<String, MemberVO> result = collect.stream().collect(Collectors.toMap(MemberVO::getUid, MemberVO->MemberVO));
        return result;
    }

    @Override
    public void importExcel(String fileName) {
        User currentUser = UserRequest.getCurrentUser();
        List<MemberExcelImportDTO> memberDTOS = null;
        try {
            memberDTOS = ExcelUtils.importExcel(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(CollectionUtil.isNotEmpty(memberDTOS)){
            List<Member> members = new ArrayList<Member>();
            if(CollectionUtil.isNotEmpty(memberDTOS)){
                members = memberDTOS.stream().map(e->{
                    Member member = new Member();
                    BeanUtils.copyProperties(e,member);
                    member.setId(null)
                            .setUid(UUIDUtil.getUUID32Bits())
                            .setCreatedBy(currentUser.getNumber())
                            .setUpdatedBy(currentUser.getNumber())
                            .setCreatedTime(new Date())
                            .setUpdatedTime(new Date())
                            .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
                    return member;
                }).collect(Collectors.toList());
            }
            boolean b = saveBatch(members);
            if(!b){
                throw new BusinessException("通过表格导入人员数据错误");
            }
            log.info("通过表格导入人员数据成功！");
        }
    }
}
