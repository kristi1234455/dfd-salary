package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.*;
import com.dfd.entity.*;
import com.dfd.enums.AttendanceEnum;
import com.dfd.mapper.AttendanceMapper;
import com.dfd.mapper.ItemMapper;
import com.dfd.mapper.ItemMemberMapper;
import com.dfd.service.AttendanceService;
import com.dfd.service.ItemService;
import com.dfd.service.MemberService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.PageResult;
import com.dfd.utils.UUIDUtil;
import com.dfd.vo.AttendanceInfoVO;
import com.dfd.vo.AttendanceMonDataVO;
import com.dfd.vo.AttendanceMonInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yy
 * @date 2023/6/12 16:24
 */
@Service
public class AttendanceServiceImpl extends ServiceImpl<AttendanceMapper, Attendance> implements AttendanceService {

    @Autowired
    private AttendanceMapper attendanceMapper;
    @Autowired
    private ItemService itemService;

    @Autowired
    private MemberService memberService;

    @Override
    public PageResult<AttendanceInfoVO> info(AttendanceInfoDTO attendanceInfoDTO) {
        LambdaQueryWrapper<Attendance> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(attendanceInfoDTO.getItemUid()), Attendance:: getItemUid, attendanceInfoDTO.getItemUid())
                .eq(attendanceInfoDTO.getYear() !=null, Attendance:: getYear, attendanceInfoDTO.getYear())
                .eq(attendanceInfoDTO.getMonth() !=null, Attendance:: getMonth, attendanceInfoDTO.getMonth())
                .eq(attendanceInfoDTO.getDay() !=null, Attendance:: getDay, attendanceInfoDTO.getDay())
                .eq(Attendance::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<Attendance> olist = baseMapper.selectList(queryWrapper);
        List<AttendanceInfoVO> list = convertToAttendanceInfoVO(olist);
        return PageResult.infoPage(olist.size(), attendanceInfoDTO.getCurrentPage(),attendanceInfoDTO.getPageSize(),list);
    }

    @Override
    public PageResult<AttendanceMonInfoVO> monInfo(AttendanceMonInfoDTO attendanceMonInfoDTO) {
        PageResult<AttendanceMonInfoVO> pageResult = new PageResult<>();
        LambdaQueryWrapper<Attendance> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(attendanceMonInfoDTO.getItemUid()), Attendance:: getItemUid, attendanceMonInfoDTO.getItemUid())
                .eq(attendanceMonInfoDTO.getYear() !=null, Attendance:: getYear, attendanceMonInfoDTO.getYear())
                .eq(attendanceMonInfoDTO.getMonth() !=null, Attendance:: getMonth, attendanceMonInfoDTO.getMonth())
                .eq(Attendance::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(Attendance :: getCreatedTime);

        Integer pageNum = attendanceMonInfoDTO.getCurrentPage();
        Integer pageSize = attendanceMonInfoDTO.getPageSize();
        List<Attendance> olist = baseMapper.selectList(queryWrapper);
        List<AttendanceMonInfoVO> result = convertToAttendanceMonInfoVO(olist);
        result.sort(new Comparator<AttendanceMonInfoVO>() {
            @Override
            public int compare(AttendanceMonInfoVO o1, AttendanceMonInfoVO o2) {
                return Integer.parseInt(o1.getNumber()) - Integer.parseInt(o2.getNumber());
            }

        });

        if (CollectionUtils.isEmpty(result)) {
            return pageResult
                    .setTotalRecords(GlobalConstant.GLOBAL_Lon_ZERO)
                    .setTotalPages(GlobalConstant.GLOBAL_Lon_ZERO)
                    .setRecords(Collections.emptyList());
        }
        //总页数
//        int totalPage = list.size() / pageSize;
        int totalPage = (result.size() + pageSize - 1) / pageSize;
//        List<AttendanceMonInfoVO> list = convertToAttendanceMonInfoVO(olist);
        int size = result.size();
        //先判断pageNum(使之page <= 0 与page==1返回结果相同)
        pageNum = pageNum <= 0 ? 1 : pageNum;
        pageSize = pageSize <= 0 ? 0 : pageSize;
        int pageStart = (pageNum - 1) * pageSize;//截取的开始位置 pageNum>=1
        int pageEnd = size < pageNum * pageSize ? size : pageNum * pageSize;//截取的结束位置
        if (size > pageNum) {
            result = result.subList(pageStart, pageEnd);
        }
        //防止pageSize出现<=0
        pageSize = pageSize <= 0 ? 1 : pageSize;

        pageResult.setCurrentPage(pageNum)
                .setPageSize(pageSize)
                .setRecords(result)
                .setTotalPages(totalPage)
                .setTotalRecords(size);
        return pageResult;
    }

    private List<AttendanceMonInfoVO> convertToAttendanceMonInfoVO(List<Attendance> list) {
        List<String> itemUIdList = list.stream().map(Attendance::getItemUid).collect(Collectors.toList());
        Map<String, String> itemNames = itemService.queryNameByUids(itemUIdList);

        List<String> memUIdList = list.stream().map(Attendance::getItemMemberUid).collect(Collectors.toList());
        Map<String, String> itemMemberNames = memberService.queryNameByUids(memUIdList);
        Map<String, String> itemMemberNumbers = memberService.queryNumberByUids(memUIdList);

        List<AttendanceMonInfoVO> result = new ArrayList<>();
        Map<String, List<Attendance>> attCollect = list.stream().collect(Collectors.groupingBy(o ->
                (o.getItemUid() + o.getItemMemberUid() + o.getYear() + o.getMonth()), Collectors.toList()));
        for(Map.Entry<String, List<Attendance>> entry : attCollect.entrySet()){
            List<Attendance> value = entry.getValue();
            AttendanceMonInfoVO infoVO = new AttendanceMonInfoVO();
            int duty = 0;
            int out = 0;
            List<AttendanceMonDataVO> dataVOList = new ArrayList<>();
            for(Attendance a : value){
                BeanUtil.copyProperties(a,infoVO);
                infoVO.setItemName(!itemNames.isEmpty() ? itemNames.get(a.getItemUid()) : null)
                        .setName(!itemMemberNames.isEmpty() ? itemMemberNames.get(a.getItemMemberUid()) : null)
                        .setNumber(!itemMemberNumbers.isEmpty() ? itemMemberNumbers.get(a.getItemMemberUid()) : null);
                AttendanceMonDataVO dataVO = new AttendanceMonDataVO();
                BeanUtil.copyProperties(a, dataVO);
                if(a.getStatus() == 1){
                    duty++;
                }
                if (a.getStatus() == 2) {
                    out++;
                }
                dataVOList.add(dataVO);
            }
            infoVO.setDutyTotalDays(duty)
                    .setOutgoingTotalDays(out)
                    .setAttendanceMonDataVOList(dataVOList);
            result.add(infoVO);
        }
        flushTotalDays(list);
        return result;
    }

    private List<AttendanceInfoVO> convertToAttendanceInfoVO(List<Attendance> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<String> itemUIdList = list.stream().map(Attendance::getItemUid).collect(Collectors.toList());
        Map<String, String> itemNames = itemService.queryNameByUids(itemUIdList);

        List<String> memUIdList = list.stream().map(Attendance::getItemMemberUid).collect(Collectors.toList());
        Map<String, String> itemMemberNames = memberService.queryNameByUids(memUIdList);
        Map<String, String> itemMemberNumbers = memberService.queryNumberByUids(memUIdList);

        List<AttendanceInfoVO> result = list.stream().map(attendance -> {
            if(!Optional.ofNullable(attendance).isPresent()){
                throw new BusinessException("考勤数据为空");
            }
            AttendanceInfoVO attendanceInfoVO = new AttendanceInfoVO();
            BeanUtil.copyProperties(attendance,attendanceInfoVO);
            attendanceInfoVO.setItemName(!itemNames.isEmpty() ? itemNames.get(attendance.getItemUid()) : null)
                    .setName(!itemMemberNames.isEmpty() ? itemMemberNames.get(attendance.getItemMemberUid()) : null)
                    .setNumber(!itemMemberNumbers.isEmpty() ? itemMemberNumbers.get(attendance.getItemMemberUid()) : null);
            return attendanceInfoVO;
        }).collect(Collectors.toList());
        flushTotalDays(list);
        return result;
    }

    private void flushTotalDays(List<Attendance> list) {
        int outGoings = 0;
        int dutys = 0;
        for(Attendance e : list){
            if(!e.getStatus().equals(AttendanceEnum.TTDANCE_VACATION.getCode())){
                dutys++;
            }
            if(e.getStatus().equals(AttendanceEnum.ATTDANCE_SITE.getCode())
                    || e.getStatus().equals(AttendanceEnum.ATTDANCE_OUT.getCode())){
                outGoings++;
            }
        }
        for(Attendance e : list){
            e.setDutyTotalDays(dutys).setOutgoingTotalDays(outGoings);
        }
        updateBatchById(list);
    }


    @Override
    public void add(AttendanceDayDTO attendanceDTO) {
        LambdaQueryWrapper<Attendance> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(attendanceDTO.getItemUid()), Attendance:: getItemUid, attendanceDTO.getItemUid())
                .eq(StringUtils.isNotBlank(attendanceDTO.getItemMemberUid()), Attendance:: getItemMemberUid, attendanceDTO.getItemMemberUid())
                .eq(attendanceDTO.getYear() !=null, Attendance:: getYear, attendanceDTO.getYear())
                .eq(attendanceDTO.getMonth() !=null, Attendance:: getMonth, attendanceDTO.getMonth())
                .eq(attendanceDTO.getDay() !=null, Attendance:: getDay, attendanceDTO.getDay())
                .eq(Attendance::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(baseMapper.exists(queryWrapper)){
            throw new BusinessException("添加失败，该用户考勤数据已经存在！");
        }
        User currentUser = UserRequest.getCurrentUser();
        Attendance attendance = new Attendance();
        BeanUtil.copyProperties(attendanceDTO,attendance);
        attendance.setUid(UUIDUtil.getUUID32Bits())
                .setCreatedBy(currentUser.getNumber())
                .setUpdatedBy(currentUser.getNumber())
                .setCreatedTime(new Date())
                .setUpdatedTime(new Date())
                .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
        boolean b = this.saveOrUpdate(attendance);
        if (!b) {
            throw new BusinessException("考勤状态保存失败");
        }
    }

    @Override
    public void addList(AttendanceDayListDTO attendanceDTO) {
        List<Attendance> params = new ArrayList<>();
        List<AttendanceDataDTO> dataList = attendanceDTO.getAttendanceDataDTOList();
        if(CollectionUtils.isEmpty(dataList)){
            throw new BusinessException("保存失败，入参中考勤日期和状态缺失！");
        }
        User currentUser = UserRequest.getCurrentUser();
        dataList.stream().forEach(e ->{
            if(e.getDay()==null || e.getStatus()==null){
                throw new BusinessException("入参中考勤数据不能为空！");
            }
            Attendance attendance = new Attendance();
            BeanUtil.copyProperties(attendanceDTO,attendance);
            attendance.setDay(e.getDay())
                    .setStatus(e.getStatus())
                    .setUid(UUIDUtil.getUUID32Bits())
                    .setCreatedBy(currentUser.getNumber())
                    .setUpdatedBy(currentUser.getNumber())
                    .setCreatedTime(new Date())
                    .setUpdatedTime(new Date())
                    .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
            params.add(attendance);
        });
        boolean var = this.saveBatch(params);
        if(!var){
            throw new BusinessException("保存考勤数据失败");
        }
    }

    @Override
    public void update(AttendanceDTO attendanceDTO) {
        List<Attendance> params = new ArrayList<>();
        List<AttendanceDataDTO> dataList = attendanceDTO.getAttendanceDataDTOList();
        if(CollectionUtils.isEmpty(dataList)){
            throw new BusinessException("更新失败，入参中考勤日期和状态缺失！");
        }
        User currentUser = UserRequest.getCurrentUser();
        dataList.stream().forEach(e ->{
            if(e.getDay()==null || e.getStatus()==null){
                throw new BusinessException("入参中考勤数据不能为空！");
            }
            Attendance attendance = new Attendance();
            BeanUtil.copyProperties(attendanceDTO,attendance);
            attendance.setDay(e.getDay())
                    .setStatus(e.getStatus())
                    .setUpdatedBy(currentUser.getNumber())
                    .setUpdatedTime(new Date())
                    .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
            attendance.setId(null);
            params.add(attendance);
        });
        LambdaQueryWrapper<Attendance> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(attendanceDTO.getItemUid()), Attendance:: getItemUid, attendanceDTO.getItemUid())
                .eq(StringUtils.isNotBlank(attendanceDTO.getItemMemberUid()), Attendance:: getItemMemberUid, attendanceDTO.getItemMemberUid())
                .eq(attendanceDTO.getYear() !=null, Attendance:: getYear, attendanceDTO.getYear())
                .eq(attendanceDTO.getMonth() !=null, Attendance:: getMonth, attendanceDTO.getMonth())
                .eq(Attendance::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(Attendance :: getCreatedTime);
        List<Attendance> olist = baseMapper.selectList(queryWrapper);

        List<Attendance> insertElements = params.stream()
                .filter(obj1 -> olist.stream().noneMatch(obj2 -> obj1.getDay().equals(obj2.getDay())))
                .collect(Collectors.toList());
        insertElements.stream().forEach(e ->{
            e.setUid(UUIDUtil.getUUID32Bits()).setCreatedBy(currentUser.getNumber()).setCreatedTime(new Date());
            e.setId(null);
        });
        if(CollectionUtil.isNotEmpty(insertElements)){
            boolean var = saveBatch(insertElements);
            if(!var){
                throw new BusinessException("更新考勤数据中保存数据失败");
            }
        }

        List<Attendance> upadteElements = params.stream()
                .filter(obj1 -> insertElements.stream().noneMatch(obj2 -> obj1.getDay().equals(obj2.getDay())))
                .collect(Collectors.toList());
        for(Attendance var1 : upadteElements){
            for(Attendance var2 : olist){
                if(var1.getDay().equals(var2.getDay())){
                    var1.setId(var2.getId());
                    var1.setUid(var2.getUid());
                    var1.setCreatedBy(var2.getCreatedBy());
                    var1.setCreatedTime(var2.getCreatedTime());
                }
            }
        }
        boolean var2 = updateBatchById(upadteElements);
        if( !var2){
            throw new BusinessException("更新考勤数据失败");
        }

    }

    @Override
    public void delete(List<AttendanceDelDTO> list) {
        for(AttendanceDelDTO attendanceDelDTO :  list) {
            List<AttendanceMonDataVO> dataVOList = attendanceDelDTO.getAttendanceMonDataVOList();
            if (CollectionUtil.isEmpty(list)) {
                throw new BusinessException("考勤数据删除失败，考勤对象为空");
            }
            List<String> uids = dataVOList.stream().map(AttendanceMonDataVO::getUid).collect(Collectors.toList());
            User currentUser = UserRequest.getCurrentUser();
            LambdaUpdateWrapper<Attendance> updateWrapper = new UpdateWrapper<Attendance>()
                    .lambda()
                    .eq(StringUtils.isNotBlank(attendanceDelDTO.getItemUid()), Attendance::getItemUid, attendanceDelDTO.getItemUid())
                    .eq(StringUtils.isNotBlank(attendanceDelDTO.getItemMemberUid()), Attendance::getItemMemberUid, attendanceDelDTO.getItemMemberUid())
                    .in(!CollectionUtils.isEmpty(uids), Attendance::getUid, uids)
                    .set(Attendance::getIsDeleted, System.currentTimeMillis())
                    .set(Attendance::getUpdatedBy, currentUser.getNumber())
                    .set(Attendance::getUpdatedTime, new Date());
            boolean update = this.update(updateWrapper);
            if (!update) {
                throw new BusinessException("考勤状态删除失败");
            }
        }
    }
}
