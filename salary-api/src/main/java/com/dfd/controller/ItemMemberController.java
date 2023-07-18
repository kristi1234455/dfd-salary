package com.dfd.controller;

import com.dfd.dto.*;
import com.dfd.service.ItemMemberService;
import com.dfd.utils.DFDResult;
import com.dfd.utils.PageResult;
import com.dfd.vo.MemberInfoVO;
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

    @ApiOperation(value = "获取当前项目下的人员信息",httpMethod = "POST")
    @PostMapping("/info")
    public DFDResult<PageResult<MemberInfoVO>> infoItem(@RequestBody @Valid ItemMemberQueryDTO itemMemberQueryDTO){
        return DFDResult.sucess(itemMemberService.queryItemMemberList(itemMemberQueryDTO));
    }

    @ApiOperation(value = "当前项目批量新增人员",httpMethod = "POST")
    @PostMapping("/add/list")
    public DFDResult addItemList(@RequestBody @Valid ItemMemberAddListDTO itemMemberAddListDTO){
        itemMemberService.addItemMemberList(itemMemberAddListDTO);
        return DFDResult.sucess();
    }
}
