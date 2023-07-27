package com.dfd.controller;

import com.dfd.dto.*;
import com.dfd.service.ItemService;
import com.dfd.utils.DFDResult;
import com.dfd.utils.PageResult;
import com.dfd.vo.ItemInfoDetailVO;
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

    @ApiOperation(value = "查询单个项目详情信息", httpMethod = "POST")
    @PostMapping("/info/item")
    public DFDResult<ItemInfoDetailVO> infoItem(@RequestBody @Valid ItemInfoDetailDTO itemInfoDetailDTO) {
        return DFDResult.sucess(itemService.infoItem(itemInfoDetailDTO));
    }

    @ApiOperation(value = "保存EPC项目信息", httpMethod = "POST")
    @PostMapping("/save/epc")
    public DFDResult saveEpc(@RequestBody @Valid ItemDTO itemDTO) {
        itemService.saveEpc(itemDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "更新EPC项目信息", httpMethod = "POST")
    @PostMapping("/update/epc")
    public DFDResult updateEpc(@RequestBody @Valid ItemUpDTO itemDTO) {
        itemService.updateEpc(itemDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "更新EPC项目的阶段策划系数信息", httpMethod = "POST")
    @PostMapping("/update/epc/itemplan")
    public DFDResult updateEpcItemPlan(@RequestBody @Valid ItemPlanUpDTO itemPlanUpDTO) {
        itemService.updateEpcItemPlan(itemPlanUpDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "删除EPC项目信息", httpMethod = "POST")
    @PostMapping("/delete/epc")
    public DFDResult deleteEpc(@RequestBody @Valid ItemDelDTO itemDTO){
        itemService.deleteEpc(itemDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "删除EPC项目的阶段策划系数信息", httpMethod = "POST")
    @PostMapping("/delete/epc/itemplan")
    public DFDResult deleteEpcItemPlan(@RequestBody @Valid ItemPlanDelDTO itemPlanDelDTO){
        itemService.deleteEpcItemPlan(itemPlanDelDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "保存投标项目信息", httpMethod = "POST")
    @PostMapping("/save/bid")
    public DFDResult saveBid(@RequestBody @Valid BidItemDTO bidItemDTO) {
        itemService.saveBid(bidItemDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "更新投标项目信息", httpMethod = "POST")
    @PostMapping("/update/bid")
    public DFDResult updateBid(@RequestBody @Valid BidItemUpdateDTO bidItemUpdateDTO) {
        itemService.updateBid(bidItemUpdateDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "删除投标项目信息", httpMethod = "POST")
    @PostMapping("/delete/bid")
    public DFDResult deleteBid(@RequestBody @Valid BidItemDelDTO bidItemDelDTO) {
        itemService.deleteBid(bidItemDelDTO);
        return DFDResult.sucess();
    }


    @ApiOperation(value = "保存科研项目信息", httpMethod = "POST")
    @PostMapping("/save/scientific")
    public DFDResult saveScientific(@RequestBody @Valid ScientificItemDTO scientificVO) {
        itemService.saveScientific(scientificVO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "更新科研项目信息", httpMethod = "POST")
    @PostMapping("/update/scientific")
    public DFDResult updateScientific(@RequestBody @Valid ScientificItemUpdateDTO scientificItemUpdateDTO) {
        itemService.updateScientific(scientificItemUpdateDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "删除科研项目信息", httpMethod = "POST")
    @PostMapping("/delete/scientific")
    public DFDResult deleteScientific(@RequestBody @Valid ScientificItemDelDTO scientificItemDelDTO) {
        itemService.deleteScientific(scientificItemDelDTO);
        return DFDResult.sucess();
    }

}
