package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.DesignSalaryAddDTO;
import com.dfd.dto.DesignSalaryDTO;
import com.dfd.dto.DesignSalaryDelDTO;
import com.dfd.dto.DesignSalaryInfoDTO;
import com.dfd.entity.*;
import com.dfd.mapper.DesignSalaryMapper;
import com.dfd.mapper.ItemMapper;
import com.dfd.service.DesignSalaryService;
import com.dfd.service.ItemService;
import com.dfd.service.MemberService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.DateUtil;
import com.dfd.utils.PageResult;
import com.dfd.utils.UUIDUtil;
import com.dfd.vo.BidSalaryInfoVO;
import com.dfd.vo.DesignSalaryInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yy
 * @date 2023/6/9 15:20
 */
@Service
public class DesignSalaryServiceImpl extends ServiceImpl<DesignSalaryMapper, DesignSalary> implements DesignSalaryService {
    @Autowired
    private ItemService itemService;

    @Autowired
    private MemberService memberService;
    @Override
    public PageResult<DesignSalaryInfoVO> info(DesignSalaryInfoDTO designSalaryInfoDTO) {
        LambdaQueryWrapper<DesignSalary> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(designSalaryInfoDTO.getItemUid()), DesignSalary:: getItemUid, designSalaryInfoDTO.getItemUid())
                .likeRight(designSalaryInfoDTO.getDeclareTime() !=null, DesignSalary:: getDeclareTime, designSalaryInfoDTO.getDeclareTime())
                .eq(DesignSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(DesignSalary :: getCreatedTime);
        List<DesignSalary> olist = baseMapper.selectList(queryWrapper);
        List<DesignSalaryInfoVO> list = convertToSalaryInfoVO(olist);
        return PageResult.infoPage(olist.size(), designSalaryInfoDTO.getCurrentPage(),designSalaryInfoDTO.getPageSize(),list);
    }

    private List<DesignSalaryInfoVO> convertToSalaryInfoVO(List<DesignSalary> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<String> itemUIdList = list.stream().map(DesignSalary::getItemUid).collect(Collectors.toList());
        Map<String, String> itemNames = itemService.queryNameByUids(itemUIdList);

        List<String> memUIdList = list.stream().map(DesignSalary::getItemMemberUid).collect(Collectors.toList());
        Map<String, String> itemMemberNames = memberService.queryNameByUids(memUIdList);
        Map<String, String> itemMemberNumbers = memberService.queryNumberByUids(memUIdList);

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
    public void add(DesignSalaryAddDTO designSalaryDTO) {
        LambdaQueryWrapper<DesignSalary> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(designSalaryDTO.getItemUid()), DesignSalary:: getItemUid, designSalaryDTO.getItemUid())
                .eq(StringUtils.isNotBlank(designSalaryDTO.getItemMemberUid()), DesignSalary:: getItemMemberUid, designSalaryDTO.getItemMemberUid())
                .likeRight(designSalaryDTO.getDeclareTime()!=null, DesignSalary:: getDeclareTime, designSalaryDTO.getDeclareTime())
                .eq(DesignSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(baseMapper.exists(queryWrapper)){
            throw new BusinessException("添加失败，该用户设计数据已经存在！");
        }
        User currentUser = UserRequest.getCurrentUser();
        DesignSalary designSalary = new DesignSalary();
        BigDecimal subTotal = designSalary.getDesignManager().add(designSalary.getDirector())
                .add(designSalary.getDesign()).add(designSalary.getProofread()).add(designSalary.getAudit()) ;
        subTotal = subTotal != null ? subTotal : new BigDecimal(0);
        BeanUtil.copyProperties(designSalaryDTO,designSalary);
        String uid = designSalaryDTO.getItemUid() + designSalaryDTO.getItemMemberUid() + DateUtil.getYM();
        designSalary.setUid(uid)
                .setSubtotal(subTotal)
                .setCreatedBy(currentUser.getNumber())
                .setUpdatedBy(currentUser.getNumber())
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
        String uid = designSalaryDTO.getItemUid() + designSalaryDTO.getItemMemberUid() + DateUtil.getYM();
        updateWrapper.eq(StringUtils.isNotBlank(designSalaryDTO.getUid()), DesignSalary:: getUid, designSalaryDTO.getUid())
                .eq(DesignSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                .set(DesignSalary:: getUid, uid)
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
                .set(DesignSalary:: getUpdatedBy, currentUser.getNumber())
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
                .set(DesignSalary:: getUpdatedBy, currentUser.getNumber())
                .set(DesignSalary:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("设计状态删除失败!");
        }
    }
}
