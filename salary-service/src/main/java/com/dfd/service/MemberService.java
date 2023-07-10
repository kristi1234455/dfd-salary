package com.dfd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.ItemMemberDelDTO;
import com.dfd.dto.ItemMemberInfoDTO;
import com.dfd.dto.ItemMemberQueryDTO;
import com.dfd.dto.MemberQueryDTO;
import com.dfd.entity.ItemMember;
import com.dfd.entity.Member;
import com.dfd.utils.PageResult;
import com.dfd.vo.ItemMemberInfoVO;

import java.util.List;
import java.util.Map;

/**
 * @author yy
 * @date 2023/6/7 16:23
 */
public interface MemberService extends IService<Member> {

    List<Member> queryMemberByUids(List<String> uids);

    Map<Integer, String> queryNameByUids(List<String> uids);

    Map<Integer, String> queryNumberByUids(List<String> uids);

}
