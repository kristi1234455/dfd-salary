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
import com.dfd.dto.MemberInfoVO;
import com.dfd.enums.ItemPropertiesEnum;
import com.dfd.dto.*;
import com.dfd.entity.*;
import com.dfd.enums.RoleEnum;
import com.dfd.mapper.ItemMapper;
import com.dfd.mapper.ItemPlanMapper;
import com.dfd.mapper.UserMapper;
import com.dfd.service.ItemMemberService;
import com.dfd.service.ItemPlanService;
import com.dfd.service.ItemService;
import com.dfd.service.MemberService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.PageResult;
import com.dfd.utils.UUIDUtil;
import com.dfd.vo.*;
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
    private ItemPlanMapper itemPlanMapper;

    @Autowired
    private ItemMemberService itemMemberService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private MemberService memberService;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PageResult<ItemInfoVO> queryItemList(ItemInfoQueryDTO itemInfoQueryDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(StringUtils.isNotBlank(currentUser.getNumber()),User::getNumber, currentUser.getNumber());
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        IPage<Item> page = null;
        String role = user.getRole();
        LambdaQueryWrapper<Item> queryWrapper = new LambdaQueryWrapper();
        queryWrapper
                .eq(StringUtils.isNotEmpty(itemInfoQueryDTO.getItemProperties()),Item::getItemProperties,itemInfoQueryDTO.getItemProperties())
                .like(StringUtils.isNotEmpty(itemInfoQueryDTO.getItemName()),Item::getItemName,itemInfoQueryDTO.getItemName())
                .eq(Item::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(Optional.ofNullable(user).isPresent() && StringUtils.isNotBlank(role)) {
            if (role.equals(RoleEnum.ROLE_ITEM)) {
                queryWrapper.eq(Item::getItemManager, currentUser.getUid());
            }else if (role.equals(RoleEnum.ROLE_SUB_LEADER)){
                queryWrapper.eq(Item::getSubLeader, currentUser.getUid());
            }else if(role.equals(RoleEnum.ROLE_FUNC_LEDAER)) {
                queryWrapper.eq(Item::getFunctionalLeader, currentUser.getUid());
            }else if(role.equals(RoleEnum.ROLE_DEPARTMENT)){
                queryWrapper.eq(Item::getDepartmenLeader, currentUser.getUid());
            }else{

            }
        }

        Integer pageNum = itemInfoQueryDTO.getCurrentPage();
        Integer pageSize = itemInfoQueryDTO.getPageSize();
        List<Item> members = baseMapper.selectList(queryWrapper);
        //总页数
//        int totalPage = list.size() / pageSize;
        int totalPage = (members.size() + pageSize - 1) / pageSize;
        List<ItemInfoVO> list = convertToItemVO(members);
        int size = list.size();
        //先判断pageNum(使之page <= 0 与page==1返回结果相同)
        pageNum = pageNum <= 0 ? 1 : pageNum;
        pageSize = pageSize <= 0 ? 0 : pageSize;
        int pageStart = (pageNum - 1) * pageSize;//截取的开始位置 pageNum>=1
        int pageEnd = size < pageNum * pageSize ? size : pageNum * pageSize;//截取的结束位置
        if (size > pageNum) {
            list = list.subList(pageStart, pageEnd);
        }
        //防止pageSize出现<=0
        pageSize = pageSize <= 0 ? 1 : pageSize;
        PageResult<ItemInfoVO> pageResult = new PageResult<>();
        pageResult.setCurrentPage(pageNum)
                .setPageSize(pageSize)
                .setRecords(list)
                .setTotalPages(totalPage)
                .setTotalRecords(size);
        return pageResult;
    }

    private List<ItemInfoVO> convertToItemVO(List<Item> records) {
        List<String> memUIdList = new ArrayList<>();
        for(Item item : records){
            memUIdList.add(item.getBidDirector());
            memUIdList.add(item.getDesignManager());
            memUIdList.add(item.getScientificManager());
            memUIdList.add(item.getItemLeader());
            memUIdList.add(item.getSubLeader());
            memUIdList.add(item.getFunctionalLeader());
            memUIdList.add(item.getDepartmenLeader());
        }
        Map<String, String> itemMemberNames = memberService.queryNameByUids(memUIdList);

        List<ItemInfoVO> result = Lists.transform(records, item -> {
            ItemInfoVO itemInfoVO = new ItemInfoVO();
            BeanUtils.copyProperties(item, itemInfoVO);
            itemInfoVO.setDesignManager(!itemMemberNames.isEmpty() ? itemMemberNames.get(item.getDesignManager()) : null)
                    .setItemManager(!itemMemberNames.isEmpty() ? itemMemberNames.get(item.getItemManager()) : null)
                    .setScientificManager(!itemMemberNames.isEmpty() ? itemMemberNames.get(item.getScientificManager()) : null)
                    .setBidDirector(!itemMemberNames.isEmpty() ? itemMemberNames.get(item.getBidDirector()) : null);
            return itemInfoVO;
        });
        return result;
    }

    @Override
    public ItemEpcInfoVO infoEpc(ItemEpcInfoDTO itemEpcInfoDTO) {
        LambdaQueryWrapper<Item> itemLambdaQueryWrapper = new LambdaQueryWrapper();
        itemLambdaQueryWrapper.eq(StringUtils.isNotBlank(itemEpcInfoDTO.getUid()), Item::getUid,itemEpcInfoDTO.getUid())
                .eq(Item::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        Item item = itemMapper.selectOne(itemLambdaQueryWrapper);
        ItemEpcInfoVO result = new ItemEpcInfoVO();
        BeanUtil.copyProperties(item,result);

        LambdaQueryWrapper<ItemPlan> itemPlanLambdaQueryWrapper = new LambdaQueryWrapper();
        itemPlanLambdaQueryWrapper.eq(StringUtils.isNotBlank(itemEpcInfoDTO.getUid()), ItemPlan::getItemUid,itemEpcInfoDTO.getUid())
                .eq(ItemPlan::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<ItemPlan> list = itemPlanMapper.selectList(itemPlanLambdaQueryWrapper);

       List<String> memUIdList = list.stream().map(ItemPlan::getItemMemberUid).collect(Collectors.toList());
        memUIdList.add(item.getBidDirector());
        memUIdList.add(item.getDesignManager());
        memUIdList.add(item.getScientificManager());
        memUIdList.add(item.getItemLeader());
        memUIdList.add(item.getSubLeader());
        memUIdList.add(item.getFunctionalLeader());
        memUIdList.add(item.getDepartmenLeader());
        Map<String, String> itemMemberNames = memberService.queryNameByUids(memUIdList);
        Map<String, String> itemMemberNumbers = memberService.queryNumberByUids(memUIdList);

        List<ItemPlanInfoVO> itemPlans = list.stream().map(itemPlan -> {
            if(!Optional.ofNullable(itemPlan).isPresent()){
                throw new BusinessException("策划系数数据为空");
            }
            ItemPlanInfoVO itemPlanInfoVO = new ItemPlanInfoVO();
            BeanUtil.copyProperties(itemPlan,itemPlanInfoVO);
            itemPlanInfoVO.setItemName(item.getItemName())
                    .setName(!itemMemberNames.isEmpty() ? itemMemberNames.get(itemPlan.getItemMemberUid()) : null)
                    .setNumber(!itemMemberNumbers.isEmpty() ? itemMemberNumbers.get(itemPlan.getItemMemberUid()) : null);
            return itemPlanInfoVO;
        }).collect(Collectors.toList());
        result.setItemPlanDTOList(itemPlans);

        //根据uid获取member中的名字
        result.setDesignManager(!itemMemberNames.isEmpty() ? itemMemberNames.get(result.getDesignManager()) : null)
                .setItemManager(!itemMemberNames.isEmpty() ? itemMemberNames.get(result.getItemManager()) : null)
                .setItemLeader(!itemMemberNames.isEmpty() ? itemMemberNames.get(result.getItemLeader()) : null)
                .setAgencyLeader(!itemMemberNames.isEmpty() ? itemMemberNames.get(result.getAgencyLeader()) : null)
                .setDesignLeader(!itemMemberNames.isEmpty() ? itemMemberNames.get(result.getDesignLeader()) : null)
                .setEngineeringLeader(!itemMemberNames.isEmpty() ? itemMemberNames.get(result.getEngineeringLeader()) : null)
                .setSubLeader(!itemMemberNames.isEmpty() ? itemMemberNames.get(result.getSubLeader()) : null)
                .setFunctionalLeader(!itemMemberNames.isEmpty() ? itemMemberNames.get(result.getFunctionalLeader()) : null)
                .setDepartmenLeader(!itemMemberNames.isEmpty() ? itemMemberNames.get(result.getDepartmenLeader()) : null);
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
                .setCreatedBy(currentUser.getNumber())
                .setUpdatedBy(currentUser.getNumber())
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
                        .setCreatedBy(currentUser.getNumber())
                        .setUpdatedBy(currentUser.getNumber())
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
    public ItemBidInfoVO infoBid(ItemBidInfoDTO itemBidInfoDTO) {
        LambdaQueryWrapper<Item> itemLambdaQueryWrapper = new LambdaQueryWrapper();
        itemLambdaQueryWrapper.eq(StringUtils.isNotBlank(itemBidInfoDTO.getUid()), Item::getUid,itemBidInfoDTO.getUid())
                .eq(Item::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        Item item = itemMapper.selectOne(itemLambdaQueryWrapper);

        ItemBidInfoVO result = new ItemBidInfoVO();
        BeanUtil.copyProperties(item,result);

        LambdaQueryWrapper<ItemMember> wrapper = new LambdaQueryWrapper();
        wrapper.eq(StringUtils.isNotBlank(itemBidInfoDTO.getUid()), ItemMember::getItemUid,itemBidInfoDTO.getUid())
                .eq(ItemMember::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<ItemMember> list = itemMemberService.list(wrapper);

        List<String> memUIdList = list.stream().map(ItemMember::getMemberUid).collect(Collectors.toList());
        memUIdList.add(item.getBidDirector());
        memUIdList.add(item.getDesignManager());
        memUIdList.add(item.getScientificManager());
        memUIdList.add(item.getItemLeader());
        memUIdList.add(item.getSubLeader());
        memUIdList.add(item.getFunctionalLeader());
        memUIdList.add(item.getDepartmenLeader());
        Map<String, String> itemMemberNames = memberService.queryNameByUids(memUIdList);
        Map<String, String> itemMemberNumbers = memberService.queryNumberByUids(memUIdList);

        List<ItemMemberDTO> itemPlans = list.stream().map(itemMember -> {
            if(!Optional.ofNullable(itemMember).isPresent()){
                throw new BusinessException("策划系数数据为空");
            }
            ItemMemberDTO itemMemberDTO = new ItemMemberDTO();
            itemMemberDTO.setMemberUid(itemMember.getMemberUid())
                    .setName(!itemMemberNames.isEmpty() ? itemMemberNames.get(itemMember.getMemberUid()) : null)
                    .setNumber(!itemMemberNumbers.isEmpty() ? itemMemberNumbers.get(itemMember.getMemberUid()) : null);
            return itemMemberDTO;
        }).collect(Collectors.toList());
        result.setItemMemberDTOS(itemPlans);

        //根据uid获取member中的名字
        result.setBidDirector(!itemMemberNames.isEmpty() ? itemMemberNames.get(result.getBidDirector()) : null)
                .setItemLeader(!itemMemberNames.isEmpty() ? itemMemberNames.get(result.getItemLeader()) : null)
                .setSubLeader(!itemMemberNames.isEmpty() ? itemMemberNames.get(result.getSubLeader()) : null)
                .setFunctionalLeader(!itemMemberNames.isEmpty() ? itemMemberNames.get(result.getFunctionalLeader()) : null)
                .setDepartmenLeader(!itemMemberNames.isEmpty() ? itemMemberNames.get(result.getDepartmenLeader()) : null);
        return result;
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
                .setCreatedBy(currentUser.getNumber())
                .setUpdatedBy(currentUser.getNumber())
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
                        .setCreatedBy(currentUser.getNumber())
                        .setUpdatedBy(currentUser.getNumber())
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
    public ItemScientificInfoVO infoScientific(ItemScientificInfoDTO itemScientificInfoDTO) {
        LambdaQueryWrapper<Item> itemLambdaQueryWrapper = new LambdaQueryWrapper();
        itemLambdaQueryWrapper.eq(StringUtils.isNotBlank(itemScientificInfoDTO.getUid()), Item::getUid,itemScientificInfoDTO.getUid())
                .eq(Item::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        Item item = itemMapper.selectOne(itemLambdaQueryWrapper);

        ItemScientificInfoVO result = new ItemScientificInfoVO();
        BeanUtil.copyProperties(item,result);

        LambdaQueryWrapper<ItemMember> wrapper = new LambdaQueryWrapper();
        wrapper.eq(StringUtils.isNotBlank(itemScientificInfoDTO.getUid()), ItemMember::getItemUid,itemScientificInfoDTO.getUid())
                .eq(ItemMember::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<ItemMember> list = itemMemberService.list(wrapper);

        List<String> memUIdList = list.stream().map(ItemMember::getMemberUid).collect(Collectors.toList());
        memUIdList.add(item.getBidDirector());
        memUIdList.add(item.getDesignManager());
        memUIdList.add(item.getScientificManager());
        memUIdList.add(item.getItemLeader());
        memUIdList.add(item.getSubLeader());
        memUIdList.add(item.getFunctionalLeader());
        memUIdList.add(item.getDepartmenLeader());
        Map<String, String> itemMemberNames = memberService.queryNameByUids(memUIdList);
        Map<String, String> itemMemberNumbers = memberService.queryNumberByUids(memUIdList);

        List<ItemMemberDTO> itemPlans = list.stream().map(itemMember -> {
            if(!Optional.ofNullable(itemMember).isPresent()){
                throw new BusinessException("策划系数数据为空");
            }
            ItemMemberDTO itemMemberDTO = new ItemMemberDTO();
            itemMemberDTO.setMemberUid(itemMember.getMemberUid())
                    .setName(!itemMemberNames.isEmpty() ? itemMemberNames.get(itemMember.getMemberUid()) : null)
                    .setNumber(!itemMemberNumbers.isEmpty() ? itemMemberNumbers.get(itemMember.getMemberUid()) : null);
            return itemMemberDTO;
        }).collect(Collectors.toList());
        result.setItemMemberDTOS(itemPlans);

        //根据uid获取member中的名字
        result.setScientificManager(!itemMemberNames.isEmpty() ? itemMemberNames.get(result.getScientificManager()) : null)
                .setItemLeader(!itemMemberNames.isEmpty() ? itemMemberNames.get(result.getItemLeader()) : null)
                .setSubLeader(!itemMemberNames.isEmpty() ? itemMemberNames.get(result.getSubLeader()) : null)
                .setFunctionalLeader(!itemMemberNames.isEmpty() ? itemMemberNames.get(result.getFunctionalLeader()) : null)
                .setDepartmenLeader(!itemMemberNames.isEmpty() ? itemMemberNames.get(result.getDepartmenLeader()) : null);
        return result;
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
                .setCreatedBy(currentUser.getNumber())
                .setUpdatedBy(currentUser.getNumber())
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
                        .setCreatedBy(currentUser.getNumber())
                        .setUpdatedBy(currentUser.getNumber())
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
                .set((scientificItemUpdateDTO.getItemPerformance()!=null), Item:: getItemPerformance, scientificItemUpdateDTO.getItemPerformance())
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
    public Map<String, String> queryNameByUids(List<String> uids) {
        LambdaQueryWrapper<Item> wrapper = new LambdaQueryWrapper();
        wrapper.in(CollectionUtil.isNotEmpty(uids), Item::getUid, uids);
        List<Item> items = baseMapper.selectList(wrapper);
        Map<String, String> itemNames = items.stream().collect(Collectors.toMap(Item::getUid, Item::getItemName));
        return itemNames;
    }

    @Override
    public List<CheckListNormalVO> queryCheckListByUid(String itemUid) {
        List<CheckListNormalVO> result = new ArrayList<>();
        int taskSequenceNumber = 1;

        LambdaQueryWrapper<Item> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(itemUid), Item:: getUid, itemUid)
                .eq(Item::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        Item item = itemService.getOne(queryWrapper);
        BeanUtils.copyProperties(item, result);

        CheckListNormalVO one = new CheckListNormalVO();
        if(StringUtils.isNotEmpty(item.getItemManager()) && item.getItemProperties().equals(ItemPropertiesEnum.ITEM_PRO_EPC.getCode())) {
            one.setAuditorUid(item.getItemManager());
        }else if(StringUtils.isNotEmpty(item.getBidDirector()) && item.getItemProperties().equals(ItemPropertiesEnum.ITEM_PRO_BID.getCode())){
            one.setAuditorUid(item.getBidDirector());
        }else if(StringUtils.isNotEmpty(item.getScientificManager()) && item.getItemProperties().equals(ItemPropertiesEnum.ITEM_PRO_SCIEN.getCode())) {
            one.setAuditorUid(item.getScientificManager());
        }else if(StringUtils.isNotEmpty(item.getDesignManager())) {
            one.setAuditorUid(item.getDesignManager());
        }else {
            throw new BusinessException("该项目没有指定项目经理，请指定");
        }
        one.setTaskSequenceNumber(taskSequenceNumber);
        result.add(one);
        taskSequenceNumber++;


        if(StringUtils.isNotEmpty(item.getItemLeader())){
            CheckListNormalVO two = new CheckListNormalVO();
            two.setAuditorUid(item.getItemLeader());
            two.setTaskSequenceNumber(taskSequenceNumber);
            result.add(two);
        }
        if(StringUtils.isNotEmpty(item.getAgencyLeader())){
            CheckListNormalVO two = new CheckListNormalVO();
            two.setAuditorUid(item.getAgencyLeader());
            two.setTaskSequenceNumber(taskSequenceNumber);
            result.add(two);
        }
        if(StringUtils.isNotEmpty(item.getDesignLeader())){
            CheckListNormalVO two = new CheckListNormalVO();
            two.setAuditorUid(item.getDesignLeader());
            two.setTaskSequenceNumber(taskSequenceNumber);
            result.add(two);
        }
        if(StringUtils.isNotEmpty(item.getEngineeringLeader())){
            CheckListNormalVO two = new CheckListNormalVO();
            two.setAuditorUid(item.getEngineeringLeader());
            two.setTaskSequenceNumber(taskSequenceNumber);
            result.add(two);
        }
        taskSequenceNumber++;

        if(StringUtils.isNotEmpty(item.getSubLeader())){
            CheckListNormalVO three = new CheckListNormalVO();
            three.setAuditorUid(item.getSubLeader());
            three.setTaskSequenceNumber(taskSequenceNumber);
            result.add(three);
            taskSequenceNumber++;
        }

        if(StringUtils.isNotEmpty(item.getFunctionalLeader())){
            CheckListNormalVO four = new CheckListNormalVO();
            four.setAuditorUid(item.getFunctionalLeader());
            four.setTaskSequenceNumber(taskSequenceNumber);
            result.add(four);
            taskSequenceNumber++;
        }

        if(StringUtils.isNotEmpty(item.getDepartmenLeader())){
            CheckListNormalVO five = new CheckListNormalVO();
            five.setAuditorUid(item.getDepartmenLeader());
            five.setTaskSequenceNumber(taskSequenceNumber);
            result.add(five);
            taskSequenceNumber++;
        }

        List<String> uids = result.stream().map(CheckListNormalVO::getAuditorUid).collect(Collectors.toList());
        Map<String, String> nameByUids = memberService.queryNameByUids(uids);
        result.stream().forEach(e -> e.setAuditorName(nameByUids.get(e.getAuditorUid())));
        result.stream()
                .sorted(Comparator.comparingInt(CheckListNormalVO::getTaskSequenceNumber))
                .collect(Collectors.toList());
        return result;
    }


}
