package com.dfd.controller;

import com.dfd.dto.*;
import com.dfd.service.ItemService;
import com.dfd.utils.DFDResult;
import com.dfd.utils.PageResult;
import com.dfd.vo.ItemInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yy
 * @date 2023/3/31 17:06
 */
@Api(value = "项目管理", tags = {"用于项目管理的相关接口"})
@RestController
@RequestMapping("item")
@CrossOrigin
public class ItemController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "根据登录用户获取项目信息", httpMethod = "POST")
    @PostMapping("/info")
    public DFDResult<PageResult<ItemInfoVO>> info(@RequestBody @Valid ItemInfoQueryDTO itemInfoQueryDTO) {
        return DFDResult.sucess(itemService.queryItemList(itemInfoQueryDTO));
    }

    @ApiOperation(value = "保存EPC项目信息", httpMethod = "POST")
    @PostMapping("/save/epc")
    public DFDResult saveEpc(@RequestBody @Valid ItemDTO itemDTO){
        itemService.saveEpc(itemDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "保存投标项目信息", httpMethod = "POST")
    @PostMapping("/save/bid")
    public DFDResult saveBid(@RequestBody @Valid BidItemDTO bidVO){
        itemService.saveBid(bidVO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "保存科研项目信息", httpMethod = "POST")
    @PostMapping("/save/scientific")
    public DFDResult saveScientific(@RequestBody @Valid ScientificItemDTO scientificVO){
        itemService.saveScientific(scientificVO);
        return DFDResult.sucess();
    }
}
