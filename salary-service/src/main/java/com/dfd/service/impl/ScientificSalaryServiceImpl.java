package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.ScientificSalaryDTO;
import com.dfd.dto.ScientificSalaryDelDTO;
import com.dfd.dto.ScientificSalaryInfoDTO;
import com.dfd.entity.*;
import com.dfd.mapper.ItemMapper;
import com.dfd.mapper.ItemMemberMapper;
import com.dfd.mapper.ScientificSalaryMapper;
import com.dfd.service.ScientificSalaryService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.PageResult;
import com.dfd.utils.UUIDUtil;
import com.dfd.vo.AttendanceInfoVO;
import com.dfd.vo.ScientificSalaryInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yy
 * @date 2023/6/9 11:50
 */
@Service
public class ScientificSalaryServiceImpl extends ServiceImpl<ScientificSalaryMapper, ScientificSalary> implements ScientificSalaryService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemMemberMapper itemMemberMapper;

    @Override
    public PageResult<ScientificSalaryInfoVO> info(ScientificSalaryInfoDTO scientificSalaryInfoDTO) {
        LambdaQueryWrapper<ScientificSalary> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(scientificSalaryInfoDTO.getItemUid()), ScientificSalary:: getItemUid, scientificSalaryInfoDTO.getItemUid())
                .eq(scientificSalaryInfoDTO.getDeclareTime() !=null, ScientificSalary:: getDeclareTime, scientificSalaryInfoDTO.getDeclareTime())
                .eq(ScientificSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(ScientificSalary :: getCreatedTime);

        Page<ScientificSalary> pageReq = new Page(scientificSalaryInfoDTO.getCurrentPage(), scientificSalaryInfoDTO.getPageSize());
        IPage<ScientificSalary> page = baseMapper.selectPage(pageReq, queryWrapper);
        PageResult<ScientificSalaryInfoVO> pageResult = new PageResult(page)
                .setRecords(convertToInfoVO(page.getRecords()));
        return pageResult;
    }

    private List<ScientificSalaryInfoVO> convertToInfoVO(List<ScientificSalary> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<String> itemIdList = list.stream().map(ScientificSalary::getItemUid).collect(Collectors.toList());
        List<Item> items = itemMapper.selectBatchIds(itemIdList);
        Map<Integer, String> itemNames = items.stream().collect(Collectors.toMap(Item::getId, Item::getItemName));

        List<String> itemMemIdList = list.stream().map(ScientificSalary::getItemMemberUid).collect(Collectors.toList());
        List<ItemMember> itemMembers = itemMemberMapper.selectBatchIds(itemMemIdList);
        Map<Integer, String> itemMemberNames = itemMembers.stream().collect(Collectors.toMap(ItemMember::getId, ItemMember::getName));
        Map<Integer, String> itemMemberNumbers = itemMembers.stream().collect(Collectors.toMap(ItemMember::getId, ItemMember::getNumber));

        List<ScientificSalaryInfoVO> result = list.stream().map(salary -> {
            if(!Optional.ofNullable(salary).isPresent()){
                throw new BusinessException("科研数据为空");
            }
            ScientificSalaryInfoVO scientificSalaryInfoVO = new ScientificSalaryInfoVO();
            BeanUtil.copyProperties(salary,scientificSalaryInfoVO);
            scientificSalaryInfoVO.setItemName(!itemNames.isEmpty() ? itemNames.get(salary.getItemUid()) : null)
                    .setName(!itemMemberNames.isEmpty() ? itemMemberNames.get(salary.getItemMemberUid()) : null)
                    .setNumber(!itemMemberNumbers.isEmpty() ? itemMemberNumbers.get(salary.getItemMemberUid()) : null);
            return scientificSalaryInfoVO;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public void add(ScientificSalaryDTO scientificSalaryDTO) {
        LambdaQueryWrapper<ScientificSalary> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(scientificSalaryDTO.getItemUid()), ScientificSalary:: getItemUid, scientificSalaryDTO.getItemUid())
                .eq(StringUtils.isNotBlank(scientificSalaryDTO.getItemMemberUid()), ScientificSalary:: getItemMemberUid, scientificSalaryDTO.getItemMemberUid())
                .eq(scientificSalaryDTO.getDeclareTime()!=null, ScientificSalary:: getDeclareTime, scientificSalaryDTO.getDeclareTime())
                .eq(ScientificSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(baseMapper.exists(queryWrapper)){
            throw new BusinessException("添加失败，该用户科研数据已经存在！");
        }
        User currentUser = UserRequest.getCurrentUser();
        ScientificSalary scientificSalary = new ScientificSalary();
        BeanUtil.copyProperties(scientificSalaryDTO,scientificSalary);
        scientificSalary.setUid(UUIDUtil.getUUID32Bits())
                .setCreatedBy(currentUser.getPhone())
                .setUpdatedBy(currentUser.getPhone())
                .setCreatedTime(new Date())
                .setUpdatedTime(new Date())
                .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
        boolean b = this.saveOrUpdate(scientificSalary);
        if (!b) {
            throw new BusinessException("科研数据保存失败");
        }
    }

    @Override
    public void update(ScientificSalaryDTO scientificSalaryDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<ScientificSalary> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StringUtils.isNotBlank(scientificSalaryDTO.getUid()), ScientificSalary:: getUid, scientificSalaryDTO.getUid())
                .eq(ScientificSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                .set(StringUtils.isNotBlank(scientificSalaryDTO.getMainMajor()), ScientificSalary:: getMainMajor, scientificSalaryDTO.getMainMajor())
                .set(StringUtils.isNotBlank(scientificSalaryDTO.getMinorMajor()), ScientificSalary:: getMinorMajor, scientificSalaryDTO.getMinorMajor())
                .set((scientificSalaryDTO.getScientificManager()!=null), ScientificSalary:: getScientificManager, scientificSalaryDTO.getScientificManager())
                .set(scientificSalaryDTO.getDirector()!=null, ScientificSalary:: getDirector, scientificSalaryDTO.getDirector())
                .set(scientificSalaryDTO.getDesign()!=null, ScientificSalary:: getDesign, scientificSalaryDTO.getDesign())
                .set((scientificSalaryDTO.getProofread()!=null), ScientificSalary:: getProofread, scientificSalaryDTO.getProofread())
                .set((scientificSalaryDTO.getAudit()!=null), ScientificSalary:: getAudit, scientificSalaryDTO.getAudit())
                .set((scientificSalaryDTO.getSubtotal() !=null), ScientificSalary:: getSubtotal, scientificSalaryDTO.getSubtotal())
                .set(StringUtils.isNotBlank(scientificSalaryDTO.getRatioCensus()), ScientificSalary:: getRatioCensus, scientificSalaryDTO.getRatioCensus())
                .set((scientificSalaryDTO.getTotal()!=null), ScientificSalary:: getTotal, scientificSalaryDTO.getTotal())
                .set((scientificSalaryDTO.getDeclareTime()!=null), ScientificSalary:: getDeclareTime, scientificSalaryDTO.getDeclareTime())
                .set(StringUtils.isNotBlank(scientificSalaryDTO.getRemarks()), ScientificSalary:: getRemarks, scientificSalaryDTO.getRemarks())
                .set(ScientificSalary:: getUpdatedBy, currentUser.getPhone())
                .set(ScientificSalary:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("科研数据更新失败!");
        }
    }

    @Override
    public void delete(ScientificSalaryDelDTO scientificSalaryDelDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<ScientificSalary> updateWrapper = new UpdateWrapper<ScientificSalary>()
                .lambda()
                .in(!CollectionUtils.isEmpty(scientificSalaryDelDTO.getUids()), ScientificSalary:: getUid, scientificSalaryDelDTO.getUids())
                .set(ScientificSalary:: getIsDeleted, System.currentTimeMillis())
                .set(ScientificSalary:: getUpdatedBy, currentUser.getPhone())
                .set(ScientificSalary:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("科研数据删除失败!");
        }
    }
}
