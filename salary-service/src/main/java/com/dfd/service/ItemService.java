package com.dfd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.*;
import com.dfd.entity.Item;
import com.dfd.utils.PageResult;
import com.dfd.vo.ItemInfoVO;

import java.util.List;

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
     * 保存投标项目信息
     * @param bidVO
     * @return
     */
    void saveBid(BidItemDTO bidVO);

    /**
     * 保存科研项目信息
     * @param scientificVO
     * @return
     */
    void saveScientific(ScientificItemDTO scientificVO);

    void updateEpc(ItemUpDTO itemDTO);
}
