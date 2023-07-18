package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.*;
import com.dfd.entity.*;
import com.dfd.mapper.CheckListMapper;
import com.dfd.service.CheckListService;
import com.dfd.service.ItemService;
import com.dfd.service.MemberService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.PageResult;
import com.dfd.utils.UUIDUtil;
import com.dfd.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yy
 * @date 2023/6/12 16:24
 */
@Service
public class CheckListServiceImpl extends ServiceImpl<CheckListMapper, CheckList> implements CheckListService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private MemberService memberService;

    @Override
    public PageResult<CheckListVO> info(CheckLisQueryDTO checkLisQueryDTO) {
        //根据工号找uid
        User currentUser = UserRequest.getCurrentUser();
        if(StringUtils.isBlank(currentUser.getNumber())){
            throw new BusinessException("当前登录用户工号为空！");
        }
        Member member = memberService.queryMemberByNumber(currentUser.getNumber());
        if(ObjectUtil.isEmpty(member)){
            throw new BusinessException("当前登录用户基本信息为空！");
        }
        String currentUid = member.getUid();

        LambdaQueryWrapper<Item> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(checkLisQueryDTO.getItemUid()), Item:: getUid, checkLisQueryDTO.getItemUid())
                .eq(Item::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        Item item = itemService.getOne(queryWrapper);
        PageResult<CheckListVO> pageResult = new PageResult<>();
        if(Optional.ofNullable(item).isPresent()){
            if(currentUid.equals(item.getItemLeader()) || currentUid.equals(item.getAgencyLeader())
                    || currentUid.equals(item.getDesignLeader())|| currentUid.equals(item.getEngineeringLeader())
                    || currentUid.equals(item.getSubLeader()) || currentUid.equals(item.getFunctionalLeader())
                    || currentUid.equals(item.getDepartmenLeader())) {

                LambdaQueryWrapper<CheckList> wrapper = new LambdaQueryWrapper();
                wrapper.eq(StringUtils.isNotBlank(currentUid), CheckList:: getAuditor, currentUid)
                        .like(StringUtils.isNotBlank(checkLisQueryDTO.getTaskName()), CheckList:: getTaskName, checkLisQueryDTO.getTaskName())
                        .eq(CheckList::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
                wrapper.orderByDesc(CheckList :: getCreatedTime);
                List<CheckList> olist = baseMapper.selectList(wrapper);

                Integer pageNum = checkLisQueryDTO.getCurrentPage();
                Integer pageSize = checkLisQueryDTO.getPageSize();
                //总页数
//        int totalPage = list.size() / pageSize;
                int totalPage = (olist.size() + pageSize - 1) / pageSize;
                List<CheckListVO> list = convertToInfoVO(olist);
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

                pageResult.setCurrentPage(pageNum)
                        .setPageSize(pageSize)
                        .setRecords(list)
                        .setTotalPages(totalPage)
                        .setTotalRecords(size);
                return pageResult;
            }
        }
        return pageResult
                .setTotalRecords(GlobalConstant.GLOBAL_Lon_ZERO)
                .setTotalPages(GlobalConstant.GLOBAL_Lon_ZERO)
                .setRecords(Collections.emptyList());
    }


    private List<CheckListVO> convertToInfoVO(List<CheckList> olist) {
        List<CheckListVO> result = olist.stream().map(var -> {
            if(!Optional.ofNullable(var).isPresent()){
                throw new BusinessException("审核数据为空");
            }
            CheckListVO obj = new CheckListVO();
            BeanUtil.copyProperties(var,obj);
            return obj;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public void handle(CheckListHandleDTO checkListHandleDTO) {
        LambdaQueryWrapper<CheckList> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(checkListHandleDTO.getUid()), CheckList:: getUid, checkListHandleDTO.getUid())
                .eq(CheckList::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(baseMapper.exists(queryWrapper)){
            throw new BusinessException("处理失败，该用户审核数据不存在！");
        }
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<CheckList> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StringUtils.isNotBlank(checkListHandleDTO.getUid()), CheckList:: getUid, checkListHandleDTO.getUid())
                .eq(CheckList::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                .set(StringUtils.isNotBlank(checkListHandleDTO.getAuditor()), CheckList:: getAuditor, checkListHandleDTO.getAuditor())
                .set(CheckList:: getAuditTime, new Date())
                .set(checkListHandleDTO.getTaskStatus()!=null, CheckList::getTaskStatus, checkListHandleDTO.getTaskStatus())
                .set(StringUtils.isNotBlank(checkListHandleDTO.getTaskOpinion()), CheckList:: getTaskOpinion, checkListHandleDTO.getTaskOpinion())
                .set(CheckList:: getUpdatedBy, currentUser.getPhone())
                .set(CheckList:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("审核数据更新失败!");
        }
    }
}
