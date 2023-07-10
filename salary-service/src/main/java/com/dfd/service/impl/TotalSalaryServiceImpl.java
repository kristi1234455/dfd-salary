package com.dfd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.dto.SpecialDTO;
import com.dfd.dto.SpecialDelDTO;
import com.dfd.dto.SpecialInfoDTO;
import com.dfd.entity.TotalSalary;
import com.dfd.mapper.ItemMapper;
import com.dfd.mapper.TotalSalaryMapper;
import com.dfd.service.ItemService;
import com.dfd.service.MemberService;
import com.dfd.service.TotalSalaryService;
import com.dfd.utils.PageResult;
import com.dfd.vo.SpecialInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yy
 * @date 2023/6/9 17:30
 */
@Service
public class TotalSalaryServiceImpl extends ServiceImpl<TotalSalaryMapper, TotalSalary> implements TotalSalaryService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private MemberService memberService;

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
