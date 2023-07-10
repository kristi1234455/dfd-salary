package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.MemberInfoVO;
import com.dfd.dto.MemberQueryDTO;
import com.dfd.entity.BidSalary;
import com.dfd.entity.DesignSalary;
import com.dfd.entity.Member;
import com.dfd.mapper.MemberMapper;
import com.dfd.service.ItemMemberService;
import com.dfd.service.MemberService;
import com.dfd.utils.BusinessException;
import com.dfd.utils.PageResult;
import com.dfd.vo.BidSalaryInfoVO;
import com.dfd.vo.DesignSalaryInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {


    @Override
    public PageResult<MemberInfoVO> queryMemberList(MemberQueryDTO memberQueryDTO) {
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(memberQueryDTO.getName()), Member:: getName, memberQueryDTO.getName())
                .eq(memberQueryDTO.getNumber() !=null, Member:: getNumber, memberQueryDTO.getNumber())
                .eq(Member::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(Member :: getCreatedTime);

        Page<Member> pageReq = new Page(memberQueryDTO.getCurrentPage(), memberQueryDTO.getPageSize());
        IPage<Member> page = baseMapper.selectPage(pageReq, queryWrapper);
        PageResult<MemberInfoVO> pageResult = new PageResult(page)
                .setRecords(convertToSalaryInfoVO(page.getRecords()));
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
    public List<Member> queryMemberByUids(List<String> uids) {
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(CollectionUtil.isNotEmpty(uids), Member::getUid, uids);
        List<Member> members = baseMapper.selectList(queryWrapper);
        return members;
    }

    @Override
    public Map<Integer, String> queryNameByUids(List<String> uids) {
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(CollectionUtil.isNotEmpty(uids), Member::getUid, uids);
        List<Member> members = baseMapper.selectList(queryWrapper);
        Map<Integer, String> itemMemberNames = members.stream().collect(Collectors.toMap(Member::getId, Member::getName));
        return itemMemberNames;
    }

    @Override
    public Map<Integer, String> queryNumberByUids(List<String> uids) {
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(CollectionUtil.isNotEmpty(uids), Member::getUid, uids);
        List<Member> members = baseMapper.selectList(queryWrapper);
        Map<Integer, String> itemMemberNumbers = members.stream().collect(Collectors.toMap(Member::getId, Member::getNumber));
        return itemMemberNumbers;
    }

}
