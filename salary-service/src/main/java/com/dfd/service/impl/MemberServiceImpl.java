package com.dfd.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.entity.Member;
import com.dfd.mapper.MemberMapper;
import com.dfd.service.ItemMemberService;
import com.dfd.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

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
