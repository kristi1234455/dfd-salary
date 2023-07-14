package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.enums.ItemPropertiesEnum;
import com.dfd.dto.*;
import com.dfd.entity.*;
import com.dfd.enums.RoleEnum;
import com.dfd.mapper.ItemMapper;
import com.dfd.mapper.UserMapper;
import com.dfd.service.ItemMemberService;
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

    @Autowired
    private ItemMemberService itemMemberService;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PageResult<ItemInfoVO> queryItemList(ItemInfoQueryDTO itemInfoQueryDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(StringUtils.isNotBlank(currentUser.getNumber()),User::getNumber, currentUser.getNumber());
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        IPage<Item> page = null;
        String role = user.getRole();
        LambdaQueryWrapper<Item> itemLambdaQueryWrapper = new LambdaQueryWrapper();
        itemLambdaQueryWrapper.eq(Item::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(Optional.ofNullable(user).isPresent() && StringUtils.isNotBlank(role)) {
            if (role.equals(RoleEnum.ROLE_ITEM)) {
                itemLambdaQueryWrapper.eq(Item::getItemManager, currentUser.getUid());
            }else if (role.equals(RoleEnum.ROLE_SUB_LEADER)){
                itemLambdaQueryWrapper.eq(Item::getSubLeader, currentUser.getUid());
            }else if(role.equals(RoleEnum.ROLE_FUNC_LEDAER)) {
                itemLambdaQueryWrapper.eq(Item::getFunctionalLeader, currentUser.getUid());
            }else if(role.equals(RoleEnum.ROLE_DEPARTMENT)){
                itemLambdaQueryWrapper.eq(Item::getDepartmenLeader, currentUser.getUid());
            }else{

            }
        }
        IPage<Item> pageReq = new Page(itemInfoQueryDTO.getCurrentPage(), itemInfoQueryDTO.getPageSize());
        page = itemMapper.selectPage(pageReq, itemLambdaQueryWrapper);

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
                .setItemProperties(ItemPropertiesEnum.ITEM_PRO_EPC.getCode())
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
                        .setItemMemberUid(itemPlanDTO.getItemMemberUid())
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
        List<ItemPlanDTO> itemPlanDTOList = itemDTO.getItemPlanDTOList();
        List<ItemPlan> list = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(itemPlanDTOList)) {
            itemPlanDTOList.stream().forEach(itemPlanDTO -> {
                ItemPlan itemPlan = new ItemPlan();
                BeanUtil.copyProperties(itemPlanDTO, itemPlan);
                itemPlan.setUpdatedBy(currentUser.getPhone())
                        .setUpdatedTime(new Date());
                list.add(itemPlan);
            });
            boolean b = itemPlanService.updateBatchById(list);
            if (!update || !b) {
                throw new BusinessException("项目工资更新失败!");
            }
        }

    }

    @Override
    public void updateEpcItemPlan(ItemPlanUpDTO itemPlanUpDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<ItemPlan> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(itemPlanUpDTO.getUid()), ItemPlan:: getUid, itemPlanUpDTO.getUid())
                .eq(ItemPlan::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                .set((itemPlanUpDTO.getDesignCoefficient()!=null), ItemPlan:: getDesignCoefficient, itemPlanUpDTO.getDesignCoefficient())
                .set((itemPlanUpDTO.getPurchaseCoefficient()!=null), ItemPlan:: getPurchaseCoefficient, itemPlanUpDTO.getPurchaseCoefficient())
                .set((itemPlanUpDTO.getManufactureCoefficient()!=null), ItemPlan:: getManufactureCoefficient, itemPlanUpDTO.getManufactureCoefficient())
                .set((itemPlanUpDTO.getInstallationCoefficient()!=null), ItemPlan:: getInstallationCoefficient, itemPlanUpDTO.getInstallationCoefficient())
                .set((itemPlanUpDTO.getInspectionCoefficient()!=null), ItemPlan:: getInspectionCoefficient, itemPlanUpDTO.getInspectionCoefficient())
                .set((itemPlanUpDTO.getFinalCoefficient()!=null), ItemPlan:: getFinalCoefficient, itemPlanUpDTO.getFinalCoefficient())
                .set((itemPlanUpDTO.getGuaranteeCoefficient()!=null), ItemPlan:: getGuaranteeCoefficient, itemPlanUpDTO.getGuaranteeCoefficient())
                .set(ItemPlan:: getUpdatedBy, currentUser.getPhone())
                .set(ItemPlan:: getUpdatedTime, new Date());
        boolean update = itemPlanService.update(wrapper);
        if(!update){
            throw new BusinessException("阶段策划系数更新失败!");
        }
    }

    @Override
    public void deleteEpc(ItemDelDTO itemDelDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<Item> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(itemDelDTO.getUid()), Item:: getUid, itemDelDTO.getUid())
                .set(Item:: getUpdatedBy, currentUser.getPhone())
                .set(Item:: getIsDeleted, System.currentTimeMillis());
        boolean var = this.update(wrapper);

        LambdaUpdateWrapper<ItemPlan> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StringUtils.isNotBlank(itemDelDTO.getUid()), ItemPlan:: getItemUid, itemDelDTO.getUid())
                .set(ItemPlan:: getUpdatedBy, currentUser.getPhone())
                .set(ItemPlan:: getIsDeleted, System.currentTimeMillis());
        boolean var1 = itemPlanService.update(updateWrapper);

        if(!var || !var1){
            throw new BusinessException("EPC项目删除失败!");
        }
    }

    @Override
    public void deleteEpcItemPlan(ItemPlanDelDTO itemPlanDelDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<ItemPlan> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StringUtils.isNotBlank(itemPlanDelDTO.getUid()), ItemPlan:: getUid, itemPlanDelDTO.getUid())
                .set(ItemPlan:: getUpdatedBy, currentUser.getPhone())
                .set(ItemPlan:: getIsDeleted, System.currentTimeMillis());
        boolean var = itemPlanService.update(updateWrapper);
        if(!var){
            throw new BusinessException("EPC项目策划系数删除失败!");
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
        String itemUid = UUIDUtil.getUUID32Bits();
        item.setUid(itemUid)
                .setItemProperties(ItemPropertiesEnum.ITEM_PRO_BID.getCode())
                .setCreatedBy(currentUser.getPhone())
                .setUpdatedBy(currentUser.getPhone())
                .setCreatedTime(new Date())
                .setUpdatedTime(new Date())
                .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
        boolean var = this.saveOrUpdate(item);
        if (!var) {
            throw new BusinessException("投标项目数据保存失败");
        }

        List<ItemMemberDTO> itemMemberDTOS = bidVO.getItemMemberDTOS();
        if(CollectionUtil.isNotEmpty(itemMemberDTOS)){
            List<String> memberUids = itemMemberDTOS.stream().map(e -> e.getMemberUid()).collect(Collectors.toList());
            List<ItemMember> memberList = new ArrayList<>();
            memberUids.stream().forEach(e ->{
                ItemMember itemMember = new ItemMember()
                        .setItemUid(itemUid)
                        .setMemberUid(e)
                        .setCreatedBy(currentUser.getPhone())
                        .setUpdatedBy(currentUser.getPhone())
                        .setCreatedTime(new Date())
                        .setUpdatedTime(new Date())
                        .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
                memberList.add(itemMember);
            });
            boolean var1 = itemMemberService.saveBatch(memberList);
            if (!var || !var1) {
                throw new BusinessException("投标项目数据保存失败!");
            }
        }
    }


    @Override
    public void updateBid(BidItemUpdateDTO bidItemDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<Item> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StringUtils.isNotBlank(bidItemDTO.getUid()), Item:: getUid, bidItemDTO.getUid())
                .eq(Item::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                .set(StringUtils.isNotBlank(bidItemDTO.getItemName()), Item:: getItemName, bidItemDTO.getItemName())
                .set(StringUtils.isNotBlank(bidItemDTO.getItemProperties()), Item:: getItemProperties, bidItemDTO.getItemProperties())
                .set((bidItemDTO.getTechnicalFee()!=null), Item:: getTechnicalFee, bidItemDTO.getTechnicalFee())
                .set((bidItemDTO.getItemSalary()!=null), Item:: getItemSalary, bidItemDTO.getItemSalary())
                .set((bidItemDTO.getItemPerformance()!=null), Item:: getItemPerformance, bidItemDTO.getItemPerformance())
                .set(StringUtils.isNotBlank(bidItemDTO.getBidManager()), Item:: getItemLeader, bidItemDTO.getItemLeader())
                .set(StringUtils.isNotBlank(bidItemDTO.getItemLeader()), Item:: getItemLeader, bidItemDTO.getItemLeader())
                .set(StringUtils.isNotBlank(bidItemDTO.getSubLeader()), Item:: getSubLeader, bidItemDTO.getSubLeader())
                .set(StringUtils.isNotBlank(bidItemDTO.getFunctionalLeader()), Item:: getFunctionalLeader, bidItemDTO.getFunctionalLeader())
                .set(StringUtils.isNotBlank(bidItemDTO.getDepartmenLeader()), Item:: getDepartmenLeader, bidItemDTO.getDepartmenLeader())
                .set(Item:: getUpdatedBy, currentUser.getPhone())
                .set(Item:: getUpdatedTime, new Date());
        boolean var = this.update(updateWrapper);
        if (!var) {
            throw new BusinessException("项目数据更新失败!");
        }

        String itemUid = bidItemDTO.getUid();
        List<ItemMemberDTO> itemMemberDTOS = bidItemDTO.getItemMemberDTOS();
        if(CollectionUtil.isNotEmpty(itemMemberDTOS)){
            List<String> nitemIds = itemMemberDTOS.stream().map(e -> e.getMemberUid()).collect(Collectors.toList());
            itemMemberService.updateMembersByItemId(itemUid, nitemIds);
        }
    }

    @Override
    public void deleteBid(BidItemDelDTO bidItemDelDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<Item> updateWrapper = new UpdateWrapper<Item>()
                .lambda()
                .eq(StringUtils.isNotBlank(bidItemDelDTO.getUid()), Item:: getUid, bidItemDelDTO.getUid())
                .set(Item:: getIsDeleted, System.currentTimeMillis())
                .set(Item:: getUpdatedBy, currentUser.getPhone())
                .set(Item:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("投标项目删除失败!");
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
        String itemUid = UUIDUtil.getUUID32Bits();
        Item item = new Item();
        BeanUtil.copyProperties(scientificVO,item);
        item.setUid(itemUid)
                .setItemProperties(ItemPropertiesEnum.ITEM_PRO_SCIEN.getCode())
                .setCreatedBy(currentUser.getPhone())
                .setUpdatedBy(currentUser.getPhone())
                .setCreatedTime(new Date())
                .setUpdatedTime(new Date())
                .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
        boolean var = this.saveOrUpdate(item);

        List<ItemMemberDTO> itemMemberDTOS = scientificVO.getItemMemberDTOS();
        if(CollectionUtil.isNotEmpty(itemMemberDTOS)){
            List<String> memberUids = itemMemberDTOS.stream().map(e -> e.getMemberUid()).collect(Collectors.toList());
            List<ItemMember> memberList = new ArrayList<>();
            memberUids.stream().forEach(e ->{
                ItemMember itemMember = new ItemMember()
                        .setItemUid(itemUid)
                        .setMemberUid(e)
                        .setCreatedBy(currentUser.getPhone())
                        .setUpdatedBy(currentUser.getPhone())
                        .setCreatedTime(new Date())
                        .setUpdatedTime(new Date())
                        .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
                memberList.add(itemMember);
            });
            boolean var1 = itemMemberService.saveBatch(memberList);
            if (!var || !var1) {
                throw new BusinessException("投标项目数据保存失败!");
            }
        }
    }


    @Override
    public void updateScientific(ScientificItemUpdateDTO scientificItemUpdateDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<Item> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StringUtils.isNotBlank(scientificItemUpdateDTO.getUid()), Item:: getUid, scientificItemUpdateDTO.getUid())
                .eq(Item::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                .set(StringUtils.isNotBlank(scientificItemUpdateDTO.getItemName()), Item:: getItemName, scientificItemUpdateDTO.getItemName())
                .set(StringUtils.isNotBlank(scientificItemUpdateDTO.getItemProperties()), Item:: getItemProperties, scientificItemUpdateDTO.getItemProperties())
                .set((scientificItemUpdateDTO.getTechnicalFee()!=null), Item:: getTechnicalFee, scientificItemUpdateDTO.getTechnicalFee())
                .set((scientificItemUpdateDTO.getItemSalary()!=null), Item:: getItemSalary, scientificItemUpdateDTO.getItemSalary())
                .set((scientificItemUpdateDTO.getPerformanceSalary()!=null), Item:: getItemPerformance, scientificItemUpdateDTO.getPerformanceSalary())
                .set(StringUtils.isNotBlank(scientificItemUpdateDTO.getScientificManager()), Item:: getItemLeader, scientificItemUpdateDTO.getScientificManager())
                .set(StringUtils.isNotBlank(scientificItemUpdateDTO.getItemLeader()), Item:: getItemLeader, scientificItemUpdateDTO.getItemLeader())
                .set(StringUtils.isNotBlank(scientificItemUpdateDTO.getSubLeader()), Item:: getSubLeader, scientificItemUpdateDTO.getSubLeader())
                .set(StringUtils.isNotBlank(scientificItemUpdateDTO.getFunctionalLeader()), Item:: getFunctionalLeader, scientificItemUpdateDTO.getFunctionalLeader())
                .set(StringUtils.isNotBlank(scientificItemUpdateDTO.getDepartmenLeader()), Item:: getDepartmenLeader, scientificItemUpdateDTO.getDepartmenLeader())
                .set(Item:: getUpdatedBy, currentUser.getPhone())
                .set(Item:: getUpdatedTime, new Date());
        boolean var = this.update(updateWrapper);
        if (!var) {
            throw new BusinessException("项目数据更新失败!");
        }

        String itemUid = scientificItemUpdateDTO.getUid();
        List<ItemMemberDTO> itemMemberDTOS = scientificItemUpdateDTO.getItemMemberDTOS();
        if(CollectionUtil.isNotEmpty(itemMemberDTOS)){
            List<String> nitemIds = itemMemberDTOS.stream().map(e -> e.getMemberUid()).collect(Collectors.toList());
            itemMemberService.updateMembersByItemId(itemUid, nitemIds);
        }
    }

    @Override
    public void deleteScientific(ScientificItemDelDTO scientificItemDelDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<Item> updateWrapper = new UpdateWrapper<Item>()
                .lambda()
                .eq(StringUtils.isNotBlank(scientificItemDelDTO.getUid()), Item:: getUid, scientificItemDelDTO.getUid())
                .set(Item:: getIsDeleted, System.currentTimeMillis())
                .set(Item:: getUpdatedBy, currentUser.getPhone())
                .set(Item:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("投标项目删除失败!");
        }
    }

    @Override
    public Map<Integer, String> queryNameByUids(List<String> uids) {
        LambdaQueryWrapper<Item> wrapper = new LambdaQueryWrapper();
        wrapper.in(CollectionUtil.isNotEmpty(uids), Item::getUid, uids);
        List<Item> items = baseMapper.selectList(wrapper);
        Map<Integer, String> itemNames = items.stream().collect(Collectors.toMap(Item::getId, Item::getItemName));
        return itemNames;
    }


}
