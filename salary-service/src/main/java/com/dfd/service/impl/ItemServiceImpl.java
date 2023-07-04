package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.*;
import com.dfd.entity.Item;
import com.dfd.entity.ItemPlan;
import com.dfd.entity.ItemSalary;
import com.dfd.entity.User;
import com.dfd.mapper.ItemMapper;
import com.dfd.mapper.ItemPlanMapper;
import com.dfd.mapper.UserMapper;
import com.dfd.service.ItemPlanService;
import com.dfd.service.ItemService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.PageResult;
import com.dfd.utils.UUIDUtil;
import com.dfd.vo.ItemInfoVO;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.beans.Transient;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private ItemPlanService itemPlanService;

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
        LambdaQueryWrapper<Item> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(itemDTO.getItemName()), Item:: getItemName, itemDTO.getItemName())
                .eq(Item::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(baseMapper.exists(queryWrapper)){
            throw new BusinessException("添加失败，该用户EPC项目数据已经存在！");
        }
        User currentUser = UserRequest.getCurrentUser();
        Item item = new Item();
        BeanUtil.copyProperties(itemDTO,item);
        String uuid = UUIDUtil.getUUID32Bits();
        item.setUid(uuid)
                .setCreatedBy(currentUser.getPhone())
                .setUpdatedBy(currentUser.getPhone())
                .setCreatedTime(new Date())
                .setUpdatedTime(new Date())
                .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
        boolean var1 = this.saveOrUpdate(item);

        List<ItemPlanDTO> itemPlanDTOList = itemDTO.getItemPlanDTOList();
        if(CollectionUtil.isNotEmpty(itemPlanDTOList)){
            List<ItemPlan> collect = itemPlanDTOList.stream().map(itemPlanDTO -> {
                ItemPlan itemPlan = new ItemPlan();
                BeanUtils.copyProperties(itemPlanDTO, itemPlan);
                itemPlan.setUid(UUIDUtil.getUUID32Bits())
                        .setItemUid(uuid)
                        .setCreatedBy(currentUser.getPhone())
                        .setUpdatedBy(currentUser.getPhone())
                        .setCreatedTime(new Date())
                        .setUpdatedTime(new Date())
                        .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
                return itemPlan;
            }).collect(Collectors.toList());
            boolean var2 = itemPlanService.saveBatch(collect);
            //批量更新
            if (!var1 || !var2) {
                throw new BusinessException("EPC项目数据保存失败");
            }
        }else{
            throw new BusinessException("阶段策划系数为空，请添加相关数据");
        }
    }

    @Override
    public void updateEpc(ItemUpDTO itemDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<Item> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StringUtils.isNotBlank(itemDTO.getUid()), Item:: getUid, itemDTO.getUid())
                .eq(Item::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                .set(StringUtils.isNotBlank(itemDTO.getItemName()), Item:: getItemName, itemDTO.getItemName())
                .set(StringUtils.isNotBlank(itemDTO.getItemProperties()), Item:: getItemProperties, itemDTO.getItemProperties())
                .set(StringUtils.isNotBlank(itemDTO.getDesignManager()), Item:: getDesignManager, itemDTO.getDesignManager())
                .set(StringUtils.isNotBlank(itemDTO.getItemManager()), Item:: getItemManager, itemDTO.getItemManager())
                .set((itemDTO.getTechnicalFee()!=null), Item:: getTechnicalFee, itemDTO.getTechnicalFee())
                .set((itemDTO.getItemSalary()!=null), Item:: getItemSalary, itemDTO.getItemSalary())
                .set((itemDTO.getItemPerformance()!=null), Item:: getItemPerformance, itemDTO.getItemPerformance())
                .set((itemDTO.getDesignSalary()!=null), Item:: getDesignSalary, itemDTO.getDesignSalary())
                .set(StringUtils.isNotBlank(itemDTO.getItemLeader()), Item:: getItemLeader, itemDTO.getItemLeader())
                .set(StringUtils.isNotBlank(itemDTO.getAgencyLeader()), Item:: getAgencyLeader, itemDTO.getAgencyLeader())
                .set(StringUtils.isNotBlank(itemDTO.getDesignLeader()), Item:: getDesignLeader, itemDTO.getDesignLeader())
                .set(StringUtils.isNotBlank(itemDTO.getEngineeringLeader()), Item:: getEngineeringLeader, itemDTO.getEngineeringLeader())
                .set(StringUtils.isNotBlank(itemDTO.getSubLeader()), Item:: getSubLeader, itemDTO.getSubLeader())
                .set(StringUtils.isNotBlank(itemDTO.getFunctionalLeader()), Item:: getFunctionalLeader, itemDTO.getFunctionalLeader())
                .set(StringUtils.isNotBlank(itemDTO.getDepartmenLeader()), Item:: getDepartmenLeader, itemDTO.getDepartmenLeader())
                .set(Item:: getUpdatedBy, currentUser.getPhone())
                .set(Item:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("项目工资更新失败!");
        }
    }

    @Override
    public void saveBid(BidItemDTO bidVO) {
        LambdaQueryWrapper<Item> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(bidVO.getItemName()), Item:: getItemName, bidVO.getItemName())
                .eq(Item::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(baseMapper.exists(queryWrapper)){
            throw new BusinessException("添加失败，该用户投标项目数据已经存在！");
        }
        User currentUser = UserRequest.getCurrentUser();
        Item item = new Item();
        BeanUtil.copyProperties(bidVO,item);
        item.setUid(UUIDUtil.getUUID32Bits())
                .setCreatedBy(currentUser.getPhone())
                .setUpdatedBy(currentUser.getPhone())
                .setCreatedTime(new Date())
                .setUpdatedTime(new Date())
                .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
        boolean b = this.saveOrUpdate(item);
        if (!b) {
            throw new BusinessException("投标项目数据保存失败");
        }
    }

    @Override
    public void saveScientific(ScientificItemDTO scientificVO) {
        LambdaQueryWrapper<Item> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(scientificVO.getItemName()), Item:: getItemName, scientificVO.getItemName())
                .eq(Item::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(baseMapper.exists(queryWrapper)){
            throw new BusinessException("添加失败，该用户科研项目数据已经存在！");
        }
        User currentUser = UserRequest.getCurrentUser();
        Item item = new Item();
        BeanUtil.copyProperties(scientificVO,item);
        item.setUid(UUIDUtil.getUUID32Bits())
                .setCreatedBy(currentUser.getPhone())
                .setUpdatedBy(currentUser.getPhone())
                .setCreatedTime(new Date())
                .setUpdatedTime(new Date())
                .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
        boolean b = this.saveOrUpdate(item);
        if (!b) {
            throw new BusinessException("科研项目数据保存失败");
        }
    }

}
