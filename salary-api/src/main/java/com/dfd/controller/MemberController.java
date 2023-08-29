package com.dfd.controller;

import com.alibaba.excel.event.AbstractIgnoreExceptionReadListener;
import com.dfd.dto.*;
import com.dfd.entity.Member;
import com.dfd.service.MemberService;
import com.dfd.utils.DFDResult;
import com.dfd.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @ApiOperation(value = "新增人员",httpMethod = "POST")
    @PostMapping("/add")
    public DFDResult add(@RequestBody @Valid MemberAddDTO memberAddDTO){
        memberService.add(memberAddDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "更新人员",httpMethod = "POST")
    @PostMapping("/update")
    public DFDResult update(@RequestBody @Valid MemberUpdateDTO memberUpdateDTO){
        memberService.update(memberUpdateDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "删除人员",httpMethod = "POST")
    @PostMapping("/delete")
    public DFDResult delete(@RequestBody @Valid MemberDelDTO memberDelDTO){
        memberService.delete(memberDelDTO);
        return DFDResult.sucess();
    }

    @GetMapping("/import/excel")
    public DFDResult excel(){
        String fileName = "C:\\\\Users\\\\kristi\\\\Desktop\\\\1---工资申报-2023-03-27\\\\副本装配部员工信息统计2023.8.28.xls";
        memberService.importExcel(fileName);
        return DFDResult.sucess();
    }
}
