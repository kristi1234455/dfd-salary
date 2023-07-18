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
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.PageResult;
import com.dfd.utils.UUIDUtil;
import com.dfd.vo.BidSalaryInfoVO;
import com.dfd.vo.DesignSalaryInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public PageResult<MemberInfoVO> queryMemberList(MemberQueryDTO memberQueryDTO) {
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(memberQueryDTO.getName()), Member:: getName, memberQueryDTO.getName())
                .like(memberQueryDTO.getNumber() !=null, Member:: getNumber, memberQueryDTO.getNumber())
                .eq(Member::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(Member :: getCreatedTime);

        Integer pageNum = memberQueryDTO.getCurrentPage();
        Integer pageSize = memberQueryDTO.getPageSize();
        List<Member> members = baseMapper.selectList(queryWrapper);
        //总页数
//        int totalPage = list.size() / pageSize;
        int totalPage = (members.size() + pageSize - 1) / pageSize;
        List<MemberInfoVO> list = convertToSalaryInfoVO(members);
        int size = list.size();
        //先判断pageNum(使之page <= 0 与page==1返回结果相同)
        pageNum = pageNum <= 0 ? 1 : pageNum;
        pageSize = pageSize <= 0 ? 0 : pageSize;
        int pageStart = (pageNum - 1) * pageSize;//截取的开始位置 pageNum>=1
        int pageEnd = size < pageNum * pageSize ? size : pageNum * pageSize;//截取的结束位置
        if (size > pageNum) {
            list = list.subList(pageStart, pageEnd);
        }
        //防止pageSize出现<=0
        pageSize = pageSize <= 0 ? 1 : pageSize;
        PageResult<MemberInfoVO> pageResult = new PageResult<>();
        pageResult.setCurrentPage(pageNum)
                .setPageSize(pageSize)
                .setRecords(list)
                .setTotalPages(totalPage)
                .setTotalRecords(size);
        return pageResult;
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
                .setCreatedBy(currentUser.getPhone())
                .setUpdatedBy(currentUser.getPhone())
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
                .set(Member:: getUpdatedBy, currentUser.getPhone())
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
                .set(Member:: getUpdatedBy, currentUser.getPhone())
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
        queryWrapper.eq(StringUtils.isNotEmpty(number), Member::getName,number)
                .eq(Member::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        Member member = baseMapper.selectOne(queryWrapper);
        return member;
    }

}
