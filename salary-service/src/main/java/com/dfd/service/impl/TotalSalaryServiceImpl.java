package com.dfd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.SpecialDTO;
import com.dfd.dto.SpecialDelDTO;
import com.dfd.dto.SpecialInfoDTO;
import com.dfd.entity.PerformanceSalary;
import com.dfd.entity.TotalSalary;
import com.dfd.mapper.ItemMapper;
import com.dfd.mapper.ItemMemberMapper;
import com.dfd.mapper.TotalSalaryMapper;
import com.dfd.service.TotalSalaryService;
import com.dfd.utils.PageResult;
import com.dfd.vo.PerformanceSalaryInfoVO;
import com.dfd.vo.SpecialInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yy
 * @date 2023/6/9 17:30
 */
@Service
public class TotalSalaryServiceImpl extends ServiceImpl<TotalSalaryMapper, TotalSalary> implements TotalSalaryService {
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemMemberMapper itemMemberMapper;
    @Override
    public PageResult<SpecialInfoVO> infoSpecial(SpecialInfoDTO specialInfoDTO) {
//        LambdaQueryWrapper<PerformanceSalary> queryWrapper = new LambdaQueryWrapper();
//        queryWrapper.eq(StringUtils.isNotBlank(specialInfoDTO.getItemUid()), TotalSalary:: getItemUid, specialInfoDTO.getItemUid())
//                .eq(specialInfoDTO.getDeclareTime() !=null, TotalSalary:: getDeclareTime, specialInfoDTO.getDeclareTime())
//                .eq(PerformanceSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
//        queryWrapper.orderByDesc(PerformanceSalary :: getCreatedTime);
//
//        Page<PerformanceSalary> pageReq = new Page(performanceSalaryInfoDTO.getCurrentPage(), performanceSalaryInfoDTO.getPageSize());
//        IPage<PerformanceSalary> page = baseMapper.selectPage(pageReq, queryWrapper);
//        PageResult<PerformanceSalaryInfoVO> pageResult = new PageResult(page)
//                .setRecords(convertToSalaryInfoVO(page.getRecords()));
//        return pageResult;
        return null;
    }

    @Override
    public void addSpecial(SpecialDTO specialDTO) {

    }

    @Override
    public void updateSpecial(SpecialDTO specialVO) {

    }

    @Override
    public void delSpecial(SpecialDelDTO specialDelDTO) {

    }
}
