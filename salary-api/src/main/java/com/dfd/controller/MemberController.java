package com.dfd.controller;

import com.dfd.dto.MemberInfoVO;
import com.dfd.dto.MemberQueryDTO;
import com.dfd.service.MemberService;
import com.dfd.utils.DFDResult;
import com.dfd.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "基本信息人员管理", tags = {"用于基本信息人员管理的相关接口"})
@RestController
@RequestMapping("member")
@CrossOrigin
public class MemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "获取所有人员基本信息",httpMethod = "POST")
    @PostMapping("/info/all")
    public DFDResult<PageResult<MemberInfoVO>> info(@RequestBody @Valid MemberQueryDTO memberQueryDTO){
        return DFDResult.sucess(memberService.queryMemberList(memberQueryDTO));
    }
}
