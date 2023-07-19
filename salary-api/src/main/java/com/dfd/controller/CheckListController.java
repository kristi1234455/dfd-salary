package com.dfd.controller;

import com.dfd.dto.*;
import com.dfd.service.CheckListService;
import com.dfd.utils.DFDResult;
import com.dfd.utils.PageResult;
import com.dfd.vo.CheckListNormalVO;
import com.dfd.vo.CheckListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yy
 * @date 2023/6/12 16:21
 */
@Api(value = "审核管理", tags = {"用于审核管理的相关接口"})
@RestController
@CrossOrigin
@Slf4j
@RequestMapping("check/list")
public class CheckListController {

    @Autowired
    private CheckListService checkListService;

    @ApiOperation(value = "获取个人任务待办事项", httpMethod = "POST")
    @PostMapping("/info")
    public DFDResult<PageResult<CheckListVO>> info(@RequestBody @Valid CheckLisQueryDTO checkLisQueryDTO){
        return DFDResult.sucess(checkListService.info(checkLisQueryDTO));
    }

    @ApiOperation(value = "处理任务待办事项", httpMethod = "POST")
    @PostMapping("/handle")
    public DFDResult handle(@RequestBody @Valid CheckListHandleDTO checkListHandleDTO){
        checkListService.handle(checkListHandleDTO);
        return DFDResult.sucess();
    }
}
