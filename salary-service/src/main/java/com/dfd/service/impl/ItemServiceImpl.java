package com.dfd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.dto.ItemInfoQueryDTO;
import com.dfd.entity.Item;
import com.dfd.entity.User;
import com.dfd.mapper.ItemMapper;
import com.dfd.mapper.UserMapper;
import com.dfd.service.ItemService;
import com.dfd.dto.BidItemDTO;
import com.dfd.dto.ItemDTO;
import com.dfd.utils.PageResult;
import com.dfd.vo.ItemInfoVO;
import com.dfd.dto.ScientificItemDTO;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.Date;
import java.util.List;

/**
 * @author yy
 * @date 2023/3/31 17:08
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PageResult<ItemInfoVO> queryItemList(ItemInfoQueryDTO itemInfoQueryDTO) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<User>();
        userLambdaQueryWrapper.eq(StringUtils.isNotBlank(itemInfoQueryDTO.getUid()),User::getUid, itemInfoQueryDTO.getUid());
        User user = userMapper.selectOne(userLambdaQueryWrapper);

        LambdaQueryWrapper<Item> itemLambdaQueryWrapper = new LambdaQueryWrapper();
        //todo：判断当前登录用户角色，是否是项目经理或管理员:如果权限是管理员，查所有的项目，如果权限是项目经理，查
        IPage<Item> pageReq = new Page(itemInfoQueryDTO.getCurrentPage(), itemInfoQueryDTO.getPageSize());
        IPage<Item> page = itemMapper.selectPage(pageReq, itemLambdaQueryWrapper);

        PageResult<ItemInfoVO> pageResult = new PageResult<>();
        pageResult.setRecords(convertToItemVO(page.getRecords()));
        pageResult.setPageSize(page.getSize());
        pageResult.setTotalRecords(page.getTotal());
        pageResult.setCurrentPage(page.getCurrent());
        return pageResult;
    }

    private List<ItemInfoVO> convertToItemVO(List<Item> records) {
        List<ItemInfoVO> result = Lists.transform(records, item -> {
            ItemInfoVO itemInfoVO = new ItemInfoVO();
            BeanUtils.copyProperties(item, itemInfoVO);
            return itemInfoVO;
        });
        return result;
    }

    @Override
    public void saveEpc(ItemDTO itemDTO) {
        Item item = new Item();
        BeanUtils.copyProperties(itemDTO,item);
        //todo:用户名设置到updateBy中
//        item.setUpdatedBy(itemSaveVO.get);
        item.setUpdatedTime(new Date());
        itemMapper.insert(item);
    }

    @Override
    public void saveBid(BidItemDTO bidVO) {

    }

    @Override
    public void saveScientific(ScientificItemDTO scientificVO) {

    }
}
