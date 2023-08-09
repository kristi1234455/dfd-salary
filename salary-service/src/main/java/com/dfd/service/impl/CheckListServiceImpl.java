package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.stream.CollectorUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.*;
import com.dfd.entity.*;
import com.dfd.enums.TaskEnum;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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


    @Autowired
    private HttpServletRequest request;

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
                wrapper.eq(StringUtils.isNotBlank(currentUid), CheckList:: getAuditorUid, currentUid)
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
                .set(StringUtils.isNotBlank(checkListHandleDTO.getAuditorUid()), CheckList:: getAuditorUid, checkListHandleDTO.getAuditorUid())
                .set(StringUtils.isNotBlank(checkListHandleDTO.getAuditorName()), CheckList:: getAuditorName, checkListHandleDTO.getAuditorName())
                .set(CheckList:: getAuditTime, new Date())
                .set(checkListHandleDTO.getTaskStatus()!=null, CheckList::getTaskStatus, checkListHandleDTO.getTaskStatus())
                .set(StringUtils.isNotBlank(checkListHandleDTO.getTaskOpinion()), CheckList:: getTaskOpinion, checkListHandleDTO.getTaskOpinion())
                .set(CheckList:: getUpdatedBy, currentUser.getNumber())
                .set(CheckList:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("审核数据更新失败!");
        }
    }

    @Override
    public void partSubmit(CheckListPartSubmitDTO partSubmitDTO) {
        String currentUri = request.getRequestURI();
        User currentUser = UserRequest.getCurrentUser();LambdaQueryWrapper<Item> wrapper = new LambdaQueryWrapper();
        wrapper.eq(StringUtils.isNotBlank(partSubmitDTO.getItemUid()), Item:: getUid, partSubmitDTO.getItemUid())
                .eq(Item::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        Item item = itemService.getOne(wrapper);

        LambdaQueryWrapper<CheckList> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(partSubmitDTO.getItemUid()), CheckList:: getItemUid, partSubmitDTO.getItemUid())
                .eq(StringUtils.isNotBlank(currentUri), CheckList:: getUrl, currentUri)
                .eq(CheckList::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<CheckList> checkLists = baseMapper.selectList(queryWrapper);

        List<CheckListNormalVO> normalFlow = itemService.queryCheckListByUid(partSubmitDTO.getItemUid());
        Map<String, Integer> auditorUidLevel = normalFlow.stream().collect(Collectors.toMap(CheckListNormalVO::getAuditorUid, CheckListNormalVO::getTaskSequenceNumber));

        if(checkLists.isEmpty()) {
            CheckList var = new CheckList();
            BeanUtil.copyProperties(partSubmitDTO,var);
            var.setUid(UUIDUtil.getUUID32Bits())
                    .setUrl(currentUri)
                    .setAuditTime(new Date())
                    .setTaskName("这是一个需要处理的"+item.getItemName())
                    .setTaskSequenceNumber(GlobalConstant.GLOBAL_INT_ONE)
                    .setAuditorLevel(String.valueOf(auditorUidLevel.get(partSubmitDTO.getAuditorUid())))
                    .setCreatedBy(currentUser.getNumber())
                    .setUpdatedBy(currentUser.getNumber())
                    .setCreatedTime(new Date())
                    .setUpdatedTime(new Date())
                    .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
            boolean b = this.save(var);
            if (!b) {
                throw new BusinessException("审核数据保存失败");
            }
            return;
        }

        CheckList dataMaxCheckList = getMaxObj(checkLists);
        CheckList dataMinCheckList = getMinObj(checkLists);
        if(ObjectUtil.isNotEmpty(dataMaxCheckList)){
            if(dataMaxCheckList.getTaskStatus().equals(TaskEnum.TASK_UNPASS.getCode())) {
                if(!partSubmitDTO.getAuditorUid().equals(dataMinCheckList.getAuditorUid())) {
                    throw new BusinessException("上级审核不通过，重新填报");
                }
            }
            if(dataMaxCheckList.getTaskStatus().equals(TaskEnum.TASK_PASS.getCode())) {
                if(partSubmitDTO.getAuditorUid().equals(dataMaxCheckList.getAuditorUid())) {
                    throw new BusinessException("该任务已审核，请不要重复审核！");
                }
            }
                CheckList var = new CheckList();
                BeanUtil.copyProperties(partSubmitDTO,var);
                var.setUid(UUIDUtil.getUUID32Bits())
                        .setUrl(currentUri)
                        .setAuditorLevel(String.valueOf(auditorUidLevel.get(partSubmitDTO.getAuditorUid())))
                        .setAuditTime(new Date())
                        .setTaskName("这是一个需要处理的"+item.getItemName())
                        .setTaskSequenceNumber(dataMaxCheckList.getTaskSequenceNumber() + 1)
                        .setCreatedBy(currentUser.getNumber())
                        .setUpdatedBy(currentUser.getNumber())
                        .setCreatedTime(new Date())
                        .setUpdatedTime(new Date())
                        .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
                boolean b = this.save(var);
                if (!b) {
                    throw new BusinessException("审核数据保存失败");
                }

        }

    }

    @Override
    public List<CheckListPartInfoVO> partInfo(CheckListPartInfoDTO partInfoDTO) {
        String oldUri = request.getRequestURI();
        String[] split = oldUri.split("/");
        String[] strings = Arrays.copyOf(split, split.length - 1);
        String currentUri = "";
        for(String str : strings){
            currentUri += str + "/";
        }
        currentUri = currentUri+"submit/";
        LambdaQueryWrapper<CheckList> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(partInfoDTO.getItemUid()), CheckList:: getItemUid, partInfoDTO.getItemUid())
                .eq(StringUtils.isNotBlank(currentUri), CheckList:: getUrl, currentUri)
                .eq(CheckList::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<CheckList> dataFinishedFlow = baseMapper.selectList(queryWrapper);

        List<CheckListNormalVO> normalFlow = itemService.queryCheckListByUid(partInfoDTO.getItemUid());

        if(dataFinishedFlow.isEmpty()){
            List<CheckListPartInfoVO> collect = normalFlow.stream().map(e -> new CheckListPartInfoVO()
                            .setAuditorUid(e.getAuditorUid())
                            .setAuditorName(e.getAuditorName())
                            .setTaskSequenceNumber(e.getTaskSequenceNumber())
                            .setTaskStatus(TaskEnum.TASK_UNDO.getCode())
                            .setAuditorLevel(e.getAuditorLevel()))
                    .collect(Collectors.toList());
            return collect;
        }

        List<CheckListPartInfoVO> resultFlow = new ArrayList<>();
        CheckList dataMaxFinishedFlow = getMaxObj(dataFinishedFlow);
        if(ObjectUtil.isNotEmpty(dataMaxFinishedFlow)){
            if(dataMaxFinishedFlow.getTaskStatus().equals(TaskEnum.TASK_UNPASS.getCode())) {
                dataFinishedFlow.stream().forEach(e -> {
                    CheckListPartInfoVO var = new CheckListPartInfoVO();
                    BeanUtils.copyProperties(e, var);
                    resultFlow.add(var);
                });
                int sequence = dataMaxFinishedFlow.getTaskSequenceNumber() + 1;
                for(CheckListNormalVO e : normalFlow) {
                    CheckListPartInfoVO var = new CheckListPartInfoVO();
                    BeanUtils.copyProperties(e, var);
                    var.setTaskStatus(TaskEnum.TASK_UNDO.getCode())
                            .setTaskSequenceNumber(sequence);
                    resultFlow.add(var);
                    sequence++;
                }
                return resultFlow;
            }else{
                dataFinishedFlow.stream().forEach(e -> {
                    CheckListPartInfoVO var = new CheckListPartInfoVO();
                    BeanUtils.copyProperties(e, var);
                    resultFlow.add(var);
                });
                int sequence = dataMaxFinishedFlow.getTaskSequenceNumber() + 1;
                String maxFinishedAuditorLevel = dataMaxFinishedFlow.getAuditorLevel();
//                List<CheckListNormalVO> unfinishedFlow = normalFlow.stream().filter(normal ->
//                                dataFinishedFlow.stream().noneMatch(datafinished -> datafinished.getAuditorUid().equals(normal.getAuditorUid())))
//                        .collect(Collectors.toList());
                boolean flag = false;
                for(int i = 0;i<normalFlow.size();i++){
                    CheckListNormalVO normalVO = normalFlow.get(i);
                    if(normalVO.getTaskSequenceNumber().equals(Integer.parseInt(maxFinishedAuditorLevel))){
                        flag = true;
                        continue;
                    }
                    if(flag) {
                        CheckListPartInfoVO var = new CheckListPartInfoVO();
                        BeanUtils.copyProperties(normalVO, var);
                        var.setTaskStatus(TaskEnum.TASK_UNDO.getCode())
                                .setTaskSequenceNumber(sequence);
                        resultFlow.add(var);
                        sequence++;
                    }
                }
                return resultFlow;
            }
        }
        return resultFlow;
    }

    private CheckList getMaxObj(List<CheckList> checkLists){
        CheckList dataMaxCheckList = null;
        int maxTaskSequenceNumber = 1;
        for(CheckList checkList : checkLists){
            int dataTaskNumber = checkList.getTaskSequenceNumber();
            if( dataTaskNumber >= maxTaskSequenceNumber){
                maxTaskSequenceNumber = dataTaskNumber;
                dataMaxCheckList = checkList;
            }
        }
        return dataMaxCheckList;
    }

    private CheckList getMinObj(List<CheckList> checkLists){
        CheckList dataMinCheckList = null;
        int maxTaskSequenceNumber = 1;
        for(CheckList checkList : checkLists){
            int dataTaskNumber = checkList.getTaskSequenceNumber();
            if(dataTaskNumber == maxTaskSequenceNumber){
                dataMinCheckList = checkList;
            }
        }
        return dataMinCheckList;
    }

}
