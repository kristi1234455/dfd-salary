package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.DesignSalaryDTO;
import com.dfd.dto.DesignSalaryDelDTO;
import com.dfd.dto.DesignSalaryInfoDTO;
import com.dfd.entity.*;
import com.dfd.mapper.DesignSalaryMapper;
import com.dfd.mapper.ItemMapper;
import com.dfd.mapper.ItemMemberMapper;
import com.dfd.service.DesignSalaryService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.PageResult;
import com.dfd.utils.UUIDUtil;
import com.dfd.vo.BidSalaryInfoVO;
import com.dfd.vo.DesignSalaryInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yy
 * @date 2023/6/9 15:20
 */
@Service
public class DesignSalaryServiceImpl extends ServiceImpl<DesignSalaryMapper, DesignSalary> implements DesignSalaryService {
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemMemberMapper itemMemberMapper;
    @Override
    public PageResult<DesignSalaryInfoVO> info(DesignSalaryInfoDTO designSalaryInfoDTO) {
        LambdaQueryWrapper<DesignSalary> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(designSalaryInfoDTO.getItemUid()), DesignSalary:: getItemUid, designSalaryInfoDTO.getItemUid())
                .eq(designSalaryInfoDTO.getDeclareTime() !=null, DesignSalary:: getDeclareTime, designSalaryInfoDTO.getDeclareTime())
                .eq(DesignSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(DesignSalary :: getCreatedTime);

        Page<DesignSalary> pageReq = new Page(designSalaryInfoDTO.getCurrentPage(), designSalaryInfoDTO.getPageSize());
        IPage<DesignSalary> page = baseMapper.selectPage(pageReq, queryWrapper);
        PageResult<DesignSalaryInfoVO> pageResult = new PageResult(page)
                .setRecords(convertToSalaryInfoVO(page.getRecords()));
        return pageResult;
    }

    private List<DesignSalaryInfoVO> convertToSalaryInfoVO(List<DesignSalary> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<String> itemIdList = list.stream().map(DesignSalary::getItemUid).collect(Collectors.toList());
        List<Item> items = itemMapper.selectBatchIds(itemIdList);
        Map<Integer, String> itemNames = items.stream().collect(Collectors.toMap(Item::getId, Item::getItemName));

        List<String> itemMemIdList = list.stream().map(DesignSalary::getItemMemberUid).collect(Collectors.toList());
        List<ItemMember> itemMembers = itemMemberMapper.selectBatchIds(itemMemIdList);
        Map<Integer, String> itemMemberNames = itemMembers.stream().collect(Collectors.toMap(ItemMember::getId, ItemMember::getName));
        Map<Integer, String> itemMemberNumbers = itemMembers.stream().collect(Collectors.toMap(ItemMember::getId, ItemMember::getNumber));

        List<DesignSalaryInfoVO> result = list.stream().map(designSalary -> {
            if(!Optional.ofNullable(designSalary).isPresent()){
                throw new BusinessException("设计数据为空");
            }
            DesignSalaryInfoVO designSalaryInfoVO = new DesignSalaryInfoVO();
            BeanUtil.copyProperties(designSalary,designSalaryInfoVO);
            designSalaryInfoVO.setItemName(!itemNames.isEmpty() ? itemNames.get(designSalary.getItemUid()) : null)
                    .setName(!itemMemberNames.isEmpty() ? itemMemberNames.get(designSalary.getItemMemberUid()) : null)
                    .setNumber(!itemMemberNumbers.isEmpty() ? itemMemberNumbers.get(designSalary.getItemMemberUid()) : null);
            return designSalaryInfoVO;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public void add(DesignSalaryDTO designSalaryDTO) {
        LambdaQueryWrapper<DesignSalary> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(designSalaryDTO.getItemUid()), DesignSalary:: getItemUid, designSalaryDTO.getItemUid())
                .eq(StringUtils.isNotBlank(designSalaryDTO.getItemMemberUid()), DesignSalary:: getItemMemberUid, designSalaryDTO.getItemMemberUid())
                .eq(designSalaryDTO.getDeclareTime()!=null, DesignSalary:: getDeclareTime, designSalaryDTO.getDeclareTime())
                .eq(DesignSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(baseMapper.exists(queryWrapper)){
            throw new BusinessException("添加失败，该用户设计数据已经存在！");
        }
        User currentUser = UserRequest.getCurrentUser();
        DesignSalary designSalary = new DesignSalary();
        BeanUtil.copyProperties(designSalaryDTO,designSalary);
        designSalary.setUid(UUIDUtil.getUUID32Bits())
                .setCreatedBy(currentUser.getPhone())
                .setUpdatedBy(currentUser.getPhone())
                .setCreatedTime(new Date())
                .setUpdatedTime(new Date())
                .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
        boolean b = this.saveOrUpdate(designSalary);
        if (!b) {
            throw new BusinessException("设计状态保存失败");
        }
    }

    @Override
    public void update(DesignSalaryDTO designSalaryDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<DesignSalary> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StringUtils.isNotBlank(designSalaryDTO.getUid()), DesignSalary:: getUid, designSalaryDTO.getUid())
                .eq(DesignSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                .set(StringUtils.isNotBlank(designSalaryDTO.getMainMajor()), DesignSalary:: getMainMajor, designSalaryDTO.getMainMajor())
                .set(StringUtils.isNotBlank(designSalaryDTO.getMinorMajor()), DesignSalary:: getMinorMajor, designSalaryDTO.getMinorMajor())
                .set((designSalaryDTO.getDesignManager()!=null), DesignSalary:: getDesignManager, designSalaryDTO.getDesignManager())
                .set(designSalaryDTO.getDirector()!=null, DesignSalary:: getDirector, designSalaryDTO.getDirector())
                .set(designSalaryDTO.getDesign()!=null, DesignSalary:: getDesign, designSalaryDTO.getDesign())
                .set((designSalaryDTO.getProofread()!=null), DesignSalary:: getProofread, designSalaryDTO.getProofread())
                .set((designSalaryDTO.getAudit()!=null), DesignSalary:: getAudit, designSalaryDTO.getAudit())
                .set((designSalaryDTO.getSubtotal() !=null), DesignSalary:: getSubtotal, designSalaryDTO.getSubtotal())
                .set(StringUtils.isNotBlank(designSalaryDTO.getRatioCensus()), DesignSalary:: getRatioCensus, designSalaryDTO.getRatioCensus())
                .set((designSalaryDTO.getTotal()!=null), DesignSalary:: getTotal, designSalaryDTO.getTotal())
                .set((designSalaryDTO.getDeclareTime()!=null), DesignSalary:: getDeclareTime, designSalaryDTO.getDeclareTime())
                .set(StringUtils.isNotBlank(designSalaryDTO.getRemarks()), DesignSalary:: getRemarks, designSalaryDTO.getRemarks())
                .set(DesignSalary:: getUpdatedBy, currentUser.getPhone())
                .set(DesignSalary:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("设计状态更新失败!");
        }
    }

    @Override
    public void delete(DesignSalaryDelDTO designSalaryDelDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<DesignSalary> updateWrapper = new UpdateWrapper<DesignSalary>()
                .lambda()
                .in(!CollectionUtils.isEmpty(designSalaryDelDTO.getUids()), DesignSalary:: getUid, designSalaryDelDTO.getUids())
                .set(DesignSalary:: getIsDeleted, System.currentTimeMillis())
                .set(DesignSalary:: getUpdatedBy, currentUser.getPhone())
                .set(DesignSalary:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("设计状态删除失败!");
        }
    }
}
