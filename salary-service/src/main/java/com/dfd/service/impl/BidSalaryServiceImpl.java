package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.BidSalaryAddDTO;
import com.dfd.dto.BidSalaryDelDTO;
import com.dfd.dto.BidSalaryDTO;
import com.dfd.dto.BidSalaryInfoDTO;
import com.dfd.entity.*;
import com.dfd.mapper.BidSalaryMapper;
import com.dfd.mapper.ItemMapper;
import com.dfd.mapper.ItemMemberMapper;
import com.dfd.service.BidSalaryService;
import com.dfd.service.ItemMemberService;
import com.dfd.service.ItemService;
import com.dfd.service.MemberService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.PageResult;
import com.dfd.utils.UUIDUtil;
import com.dfd.vo.BidSalaryInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yy
 * @date 2023/6/8 17:33
 */
@Service
public class BidSalaryServiceImpl extends ServiceImpl<BidSalaryMapper, BidSalary> implements BidSalaryService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private MemberService memberService;

    @Override
    public PageResult<BidSalaryInfoVO> info(BidSalaryInfoDTO bidSalaryInfoDTO) {
        LambdaQueryWrapper<BidSalary> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(bidSalaryInfoDTO.getItemUid()), BidSalary:: getItemUid, bidSalaryInfoDTO.getItemUid())
                .likeRight(bidSalaryInfoDTO.getDeclareTime() !=null, BidSalary:: getDeclareTime, bidSalaryInfoDTO.getDeclareTime())
                .eq(BidSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(BidSalary :: getCreatedTime);

        Page<BidSalary> pageReq = new Page(bidSalaryInfoDTO.getCurrentPage(), bidSalaryInfoDTO.getPageSize());
        IPage<BidSalary> page = baseMapper.selectPage(pageReq, queryWrapper);
        PageResult<BidSalaryInfoVO> pageResult = new PageResult(page)
                .setRecords(convertToSalaryInfoVO(page.getRecords()));
        return pageResult;
    }

    private List<BidSalaryInfoVO> convertToSalaryInfoVO(List<BidSalary> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<String> itemUIdList = list.stream().map(BidSalary::getItemUid).collect(Collectors.toList());
        Map<Integer, String> itemNames = itemService.queryNameByUids(itemUIdList);

        List<String> memUIdList = list.stream().map(BidSalary::getItemMemberUid).collect(Collectors.toList());
        Map<Integer, String> itemMemberNames = memberService.queryNameByUids(memUIdList);
        Map<Integer, String> itemMemberNumbers = memberService.queryNumberByUids(memUIdList);

        List<BidSalaryInfoVO> result = list.stream().map(bidSalary -> {
            if(!Optional.ofNullable(bidSalary).isPresent()){
                throw new BusinessException("投标数据为空");
            }
            BidSalaryInfoVO bidSalaryInfoVO = new BidSalaryInfoVO();
            BeanUtil.copyProperties(bidSalary,bidSalaryInfoVO);
            bidSalaryInfoVO.setItemName(!itemNames.isEmpty() ? itemNames.get(bidSalary.getItemUid()) : null)
                    .setName(!itemMemberNames.isEmpty() ? itemMemberNames.get(bidSalary.getItemMemberUid()) : null)
                    .setNumber(!itemMemberNumbers.isEmpty() ? itemMemberNumbers.get(bidSalary.getItemMemberUid()) : null);
            return bidSalaryInfoVO;
        }).collect(Collectors.toList());
        return result;
    }


    @Override
    public void save(BidSalaryAddDTO bidSalaryDTO) {
        LambdaQueryWrapper<BidSalary> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(bidSalaryDTO.getItemUid()), BidSalary:: getItemUid, bidSalaryDTO.getItemUid())
                .eq(StringUtils.isNotBlank(bidSalaryDTO.getItemMemberUid()), BidSalary:: getItemMemberUid, bidSalaryDTO.getItemMemberUid())
                .eq(bidSalaryDTO.getDeclareTime()!=null, BidSalary:: getDeclareTime, bidSalaryDTO.getDeclareTime())
                .eq(BidSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(baseMapper.exists(queryWrapper)){
            throw new BusinessException("添加失败，该用户投标数据已经存在！");
        }
        User currentUser = UserRequest.getCurrentUser();
        BidSalary bidSalary = new BidSalary();
        BeanUtil.copyProperties(bidSalaryDTO,bidSalary);
        bidSalary.setUid(UUIDUtil.getUUID32Bits())
                .setCreatedBy(currentUser.getPhone())
                .setUpdatedBy(currentUser.getPhone())
                .setCreatedTime(new Date())
                .setUpdatedTime(new Date())
                .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
        boolean b = this.saveOrUpdate(bidSalary);
        if (!b) {
            throw new BusinessException("投标状态保存失败");
        }
    }

    @Override
    public void update(BidSalaryDTO bidSalaryDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<BidSalary> updateWrapper = new LambdaUpdateWrapper<BidSalary>();
        updateWrapper.eq(StringUtils.isNotBlank(bidSalaryDTO.getUid()), BidSalary:: getUid, bidSalaryDTO.getUid())
                .eq(BidSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                .set(StringUtils.isNotBlank(bidSalaryDTO.getDeviceCategory()), BidSalary:: getDeviceCategory, bidSalaryDTO.getDeviceCategory())
                .set(StringUtils.isNotBlank(bidSalaryDTO.getDeviceName()), BidSalary:: getDeviceName, bidSalaryDTO.getDeviceName())
                .set((bidSalaryDTO.getDeviceOutput()!=null), BidSalary:: getDeviceOutput, bidSalaryDTO.getDeviceOutput())
                .set(StringUtils.isNotBlank(bidSalaryDTO.getBidTask()), BidSalary:: getBidTask, bidSalaryDTO.getBidTask())
                .set(StringUtils.isNotBlank(bidSalaryDTO.getDesigner()), BidSalary:: getDesigner, bidSalaryDTO.getDesigner())
                .set(StringUtils.isNotBlank(bidSalaryDTO.getReviewer()), BidSalary:: getReviewer, bidSalaryDTO.getReviewer())
                .set((bidSalaryDTO.getFinishTime()!=null), BidSalary:: getFinishTime, bidSalaryDTO.getFinishTime())
                .set((bidSalaryDTO.getDistributeFee()!=null), BidSalary:: getDistributeFee, bidSalaryDTO.getDistributeFee())
                .set(StringUtils.isNotBlank(bidSalaryDTO.getAdjustedCoefficient()), BidSalary:: getAdjustedCoefficient, bidSalaryDTO.getAdjustedCoefficient())
                .set(StringUtils.isNotBlank(bidSalaryDTO.getRatio()), BidSalary:: getRatio, bidSalaryDTO.getRatio())
                .set((bidSalaryDTO.getBidFee()!=null), BidSalary:: getBidFee, bidSalaryDTO.getBidFee())
                .set((bidSalaryDTO.getDeclareTime()!=null), BidSalary:: getDeclareTime, bidSalaryDTO.getDeclareTime())
                .set((bidSalaryDTO.getDistributeTotalFee()!=null), BidSalary:: getDistributeTotalFee, bidSalaryDTO.getDistributeTotalFee())
                .set(BidSalary:: getUpdatedBy, currentUser.getPhone())
                .set(BidSalary:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("投标状态更新失败!");
        }
    }

    @Override
    public void delete(BidSalaryDelDTO bidSalaryDelDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<BidSalary> updateWrapper = new UpdateWrapper<BidSalary>()
                .lambda()
                .in(!CollectionUtils.isEmpty(bidSalaryDelDTO.getUids()), BidSalary:: getUid, bidSalaryDelDTO.getUids())
                .set(BidSalary:: getIsDeleted, System.currentTimeMillis())
                .set(BidSalary:: getUpdatedBy, currentUser.getPhone())
                .set(BidSalary:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("投标状态删除失败!");
        }
    }
}
