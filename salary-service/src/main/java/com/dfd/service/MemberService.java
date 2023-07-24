package com.dfd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.*;
import com.dfd.entity.Member;
import com.dfd.utils.PageResult;
import com.dfd.vo.MemberVO;

import java.util.List;
import java.util.Map;

/**
 * @author yy
 * @date 2023/6/7 16:23
 */
public interface MemberService extends IService<Member> {

    PageResult<MemberInfoVO> queryMemberList(MemberQueryDTO memberQueryDTO);

    void add(MemberAddDTO memberAddDTO);

    void update(MemberUpdateDTO memberUpdateDTO);

    void delete(MemberDelDTO memberDelDTO);

    List<Member> queryMemberByUids(List<String> uids);

    Map<String, String> queryNameByUids(List<String> uids);

    Map<String, String> queryNumberByUids(List<String> uids);

    Member queryMemberByNumber(String number);

    Map<String, MemberVO> queryMemberByNumber(List<String> uids);

}
