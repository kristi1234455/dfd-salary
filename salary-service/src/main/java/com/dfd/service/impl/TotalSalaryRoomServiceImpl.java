package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.*;
import com.dfd.entity.*;
import com.dfd.enums.ItemStageEnum;
import com.dfd.mapper.TotalSalaryRoomMapper;
import com.dfd.service.*;
import com.dfd.utils.BusinessException;
import com.dfd.utils.PageResult;
import com.dfd.vo.*;
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
public class TotalSalaryRoomServiceImpl extends ServiceImpl<TotalSalaryRoomMapper, TotalSalaryRoom> implements TotalSalaryRoomService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private TotalSalaryFlushService totalSalaryFlushService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private TotalSalaryRoomMapper totalSalaryRoomMapper;


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
        //todo:获取项目技术费用
        return null;
    }



    @Override
    public PageResult<TotalSalaryRoomInfoVO> infoRoomSalary(TotalSalaryRoomInfoDTO totalSalaryRoomInfoDTO) {
        totalSalaryFlushService.flushMonthTotalSalaryRoom();
        totalSalaryFlushService.flushMonthTotalSalary();
        LambdaQueryWrapper<TotalSalaryRoom> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(totalSalaryRoomInfoDTO.getRoom()), TotalSalaryRoom:: getRoom, totalSalaryRoomInfoDTO.getRoom())
                .likeRight(totalSalaryRoomInfoDTO.getDeclareTime() !=null, TotalSalaryRoom:: getDeclareTime, totalSalaryRoomInfoDTO.getDeclareTime())
                .eq(TotalSalaryRoom::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(TotalSalaryRoom :: getCreatedTime);
        List<TotalSalaryRoom> olist = baseMapper.selectList(queryWrapper);

        Integer pageNum = totalSalaryRoomInfoDTO.getCurrentPage();
        Integer pageSize = totalSalaryRoomInfoDTO.getPageSize();
        //总页数
//        int totalPage = list.size() / pageSize;
        int totalPage = (olist.size() + pageSize - 1) / pageSize;
        List<TotalSalaryRoomInfoVO> list = convertToRoomInfoVO(olist);
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
        PageResult<TotalSalaryRoomInfoVO> pageResult = new PageResult<>();
        pageResult.setCurrentPage(pageNum)
                .setPageSize(pageSize)
                .setRecords(list)
                .setTotalPages(totalPage)
                .setTotalRecords(size);
        return pageResult;
    }

    private List<TotalSalaryRoomInfoVO> convertToRoomInfoVO(List<TotalSalaryRoom> list){
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<TotalSalaryRoomInfoVO> result = list.stream().map(var -> {
            if(!Optional.ofNullable(var).isPresent()){
                throw new BusinessException("项目工资部门汇总数据为空");
            }
            TotalSalaryRoomInfoVO infoVO = new TotalSalaryRoomInfoVO();
            BeanUtil.copyProperties(var,infoVO);
            return infoVO;
        }).collect(Collectors.toList());
        return result;
    }


    @Override
    public int exportRoomSalaryCount(TotalSalaryRoomInfoDTO totalSalaryRoomInfoDTO) {
        LambdaQueryWrapper<TotalSalaryRoom> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(totalSalaryRoomInfoDTO.getRoom()), TotalSalaryRoom:: getRoom, totalSalaryRoomInfoDTO.getRoom())
                .likeRight(totalSalaryRoomInfoDTO.getDeclareTime() !=null, TotalSalaryRoom:: getDeclareTime, totalSalaryRoomInfoDTO.getDeclareTime())
                .eq(TotalSalaryRoom::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(TotalSalaryRoom :: getCreatedTime);
        return Integer.parseInt(String.valueOf(baseMapper.selectCount(queryWrapper)));
    }

    @Override
    public List<TotalSalaryRoomExportInfoVO> exportRoomSalaryList(TotalSalaryRoomInfoDTO totalSalaryRoomInfoDTO) {
        LambdaQueryWrapper<TotalSalaryRoom> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(totalSalaryRoomInfoDTO.getRoom()), TotalSalaryRoom:: getRoom, totalSalaryRoomInfoDTO.getRoom())
                .likeRight(totalSalaryRoomInfoDTO.getDeclareTime() !=null, TotalSalaryRoom:: getDeclareTime, totalSalaryRoomInfoDTO.getDeclareTime())
                .eq(TotalSalaryRoom::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(TotalSalaryRoom :: getCreatedTime);
        List<TotalSalaryRoom> list = list(queryWrapper);

        List<String> memUIdList = list.stream().map(TotalSalaryRoom::getItemManager).collect(Collectors.toList());
        Map<String, String> itemMemberNames = memberService.queryNameByUids(memUIdList);

        List<TotalSalaryRoomExportInfoVO> result = list.stream().map(var -> {
            TotalSalaryRoomExportInfoVO infoVO = new TotalSalaryRoomExportInfoVO();
            BeanUtil.copyProperties(var, infoVO);
            infoVO.setItemStage(ItemStageEnum.getDescByCode(Integer.parseInt(var.getItemStage())))
                    .setItemManager(itemMemberNames.get(var.getItemManager()));
            return infoVO;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public Integer updateByItemUid(List<TotalSalaryRoom> list) {
        return totalSalaryRoomMapper.updateByItemUid(list);
    }
}












