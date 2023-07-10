package com.dfd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.*;
import com.dfd.entity.Item;
import com.dfd.utils.PageResult;
import com.dfd.vo.ItemInfoVO;

import java.util.List;
import java.util.Map;

/**
 * @author yy
 * @date 2023/3/31 17:08
 */
public interface ItemService extends IService<Item> {
    /**
     * 查询所有项目
     * @return
     */
    PageResult<ItemInfoVO> queryItemList(ItemInfoQueryDTO itemInfoQueryDTO);

    /**
     * 保存EPC项目信息
     * @param itemDTO
     * @return
     */
    void saveEpc(ItemDTO itemDTO);

    void updateEpc(ItemUpDTO itemDTO);

    void updateEpcItemPlan(ItemPlanUpDTO itemPlanUpDTO);

    void deleteEpc(ItemDelDTO itemDTO);

    void deleteEpcItemPlan(ItemPlanDelDTO itemPlanDelDTO);

    /**
     * 保存投标项目信息
     * @param bidItemDTO
     * @return
     */
    void saveBid(BidItemDTO bidItemDTO);

    void updateBid(BidItemUpdateDTO bidItemDTO);

    void deleteBid(BidItemDelDTO bidItemDTO);
    /**
     * 保存科研项目信息
     * @param scientificVO
     * @return
     */
    void saveScientific(ScientificItemDTO scientificVO);

    Map<Integer, String> queryNameByUids(List<String> uids);
}
