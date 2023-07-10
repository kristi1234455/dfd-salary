package com.dfd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.MemberInfoVO;
import com.dfd.dto.MemberQueryDTO;
import com.dfd.entity.Member;
import com.dfd.utils.PageResult;

import java.util.List;
import java.util.Map;

/**
 * @author yy
 * @date 2023/6/7 16:23
 */
public interface MemberService extends IService<Member> {

    PageResult<MemberInfoVO> queryMemberList(MemberQueryDTO memberQueryDTO);

    List<Member> queryMemberByUids(List<String> uids);

    Map<Integer, String> queryNameByUids(List<String> uids);

    Map<Integer, String> queryNumberByUids(List<String> uids);


}
