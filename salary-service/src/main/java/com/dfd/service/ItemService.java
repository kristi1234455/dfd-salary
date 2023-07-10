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

    /**
     * 更新epc项目
     * @param itemDTO
     */
    void updateEpc(ItemUpDTO itemDTO);

    /**
     * 更新策划系数对象
     * @param itemPlanUpDTO
     */
    void updateEpcItemPlan(ItemPlanUpDTO itemPlanUpDTO);

    /**
     * 删除epc项目
     * @param itemDTO
     */
    void deleteEpc(ItemDelDTO itemDTO);

    /**
     * 删除策划系数对象
     * @param itemPlanDelDTO
     */
    void deleteEpcItemPlan(ItemPlanDelDTO itemPlanDelDTO);

    /**
     * 保存投标项目信息
     * @param bidItemDTO
     * @return
     */
    void saveBid(BidItemDTO bidItemDTO);

    /**
     * 更新投标项目
     * @param bidItemDTO
     */
    void updateBid(BidItemUpdateDTO bidItemDTO);

    /**
     * 删除投标项目
     * @param bidItemDTO
     */
    void deleteBid(BidItemDelDTO bidItemDTO);
    /**
     * 保存科研项目信息
     * @param scientificVO
     * @return
     */
    void saveScientific(ScientificItemDTO scientificVO);

    /**
     * 更新科研项目信息
     * @param scientificItemUpdateDTO
     */
    void updateScientific(ScientificItemUpdateDTO scientificItemUpdateDTO);

    /**
     * 删除科研项目
     * @param scientificItemDelDTO
     */
    void deleteScientific(ScientificItemDelDTO scientificItemDelDTO);


    /**
     * 根据itemuid获取项目名字
     * @param uids
     * @return
     */
    Map<Integer, String> queryNameByUids(List<String> uids);
}
