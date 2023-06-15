package com.dfd.controller;

import com.dfd.dto.ItemMemberDelDTO;
import com.dfd.dto.ItemMemberInfoDTO;
import com.dfd.dto.ItemMemberQueryDTO;
import com.dfd.dto.MemberQueryDTO;
import com.dfd.service.ItemMemberService;
import com.dfd.utils.DFDResult;
import com.dfd.utils.PageResult;
import com.dfd.vo.ItemMemberInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yy
 * @date 2023/6/7 16:22
 */
@Api(value = "项目人员管理", tags = {"用于项目人员管理的相关接口"})
@RestController
@RequestMapping("item/member")
@CrossOrigin
public class ItemMemberController {

    @Autowired
    private ItemMemberService itemMemberService;

    @ApiOperation(value = "获取所有人员信息",httpMethod = "POST")
    @PostMapping("/info/all")
    public DFDResult<PageResult<ItemMemberInfoVO>> info(@RequestBody @Valid MemberQueryDTO memberQueryDTO){
        return DFDResult.sucess(itemMemberService.queryItemMemberList(memberQueryDTO));
    }

    @ApiOperation(value = "获取当前项目下的人员信息",httpMethod = "POST")
    @PostMapping("/info")
    public DFDResult<PageResult<ItemMemberInfoVO>> infoItem(@RequestBody @Valid ItemMemberQueryDTO itemMemberQueryDTO){
        return DFDResult.sucess(itemMemberService.queryItemMemberList(itemMemberQueryDTO));
    }

    //todo：暂存如何处理，创建人和更新人
    @ApiOperation(value = "新增人员",httpMethod = "POST")
    @PostMapping("/add")
    public DFDResult add(@RequestBody @Valid ItemMemberInfoDTO itemMemberInfoDTO){
        itemMemberService.add(itemMemberInfoDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "删除人员",httpMethod = "POST")
    @PostMapping("/delete")
    public DFDResult delete(@RequestBody @Valid ItemMemberDelDTO itemMemberDelDTO){
        itemMemberService.delete(itemMemberDelDTO);
        return DFDResult.sucess();
    }
}
