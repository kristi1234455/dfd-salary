package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.*;
import com.dfd.entity.*;
import com.dfd.mapper.ItemTotalSalaryMapper;
import com.dfd.mapper.TotalSalaryMapper;
import com.dfd.service.ItemService;
import com.dfd.service.ItemTotalSalaryService;
import com.dfd.service.MemberService;
import com.dfd.service.TotalSalaryService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.PageResult;
import com.dfd.utils.UUIDUtil;
import com.dfd.vo.BidSalaryInfoVO;
import com.dfd.vo.SpecialInfoVO;
import com.dfd.vo.TechnicalFeeInfoVO;
import com.dfd.vo.TotalSalaryInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yy
 * @date 2023/6/9 17:30
 */
@Service
public class ItemTotalSalaryServiceImpl extends ServiceImpl<ItemTotalSalaryMapper, ItemTotalSalary> implements ItemTotalSalaryService {

    @Autowired
    private ItemService itemService;


    @Override
    public PageResult<TechnicalFeeInfoVO> infoTechnical(TechnicalFeeInfoDTO technicalFeeInfoDTO) {
        LambdaQueryWrapper<Item> itemLambdaQueryWrapper = new LambdaQueryWrapper();
        itemLambdaQueryWrapper.like(StringUtils.isNotBlank(technicalFeeInfoDTO.getItemName()), Item::getItemName, technicalFeeInfoDTO.getItemName())
                .eq(Item::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<Item> olist = itemService.list(itemLambdaQueryWrapper);

        Integer pageNum = technicalFeeInfoDTO.getCurrentPage();
        Integer pageSize = technicalFeeInfoDTO.getPageSize();
        //总页数
//        int totalPage = list.size() / pageSize;
        int totalPage = (olist.size() + pageSize - 1) / pageSize;
        List<TechnicalFeeInfoVO> list = convertToInfoVO(olist);
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
        PageResult<TechnicalFeeInfoVO> pageResult = new PageResult<>();
        pageResult.setCurrentPage(pageNum)
                .setPageSize(pageSize)
                .setRecords(list)
                .setTotalPages(totalPage)
                .setTotalRecords(size);
        return pageResult;
    }


    private List<TechnicalFeeInfoVO> convertToInfoVO(List<Item> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<TechnicalFeeInfoVO> result = list.stream().map(item -> {
            if(!Optional.ofNullable(item).isPresent()){
                throw new BusinessException("item项目数据为空");
            }
            TechnicalFeeInfoVO infoVO = new TechnicalFeeInfoVO();
            BeanUtil.copyProperties(item,infoVO);
            infoVO.setItemUid(item.getUid())
                    .setItemName(item.getItemName())
                    .setTotalTechnicalFee(item.getTechnicalFee());
            return infoVO;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public Map<String, String> queryUsedTechnicalFeeByUids(List<String> uids) {
        return null;
    }
}












