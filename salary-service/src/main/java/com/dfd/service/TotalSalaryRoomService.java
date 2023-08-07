package com.dfd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.*;
import com.dfd.entity.TotalSalaryRoom;
import com.dfd.utils.PageResult;
import com.dfd.vo.TotalSalaryRoomExportInfoVO;
import com.dfd.vo.TotalSalaryRoomInfoVO;
import com.dfd.vo.TechnicalFeeInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author yy
 * @date 2023/6/9 17:26
 */
public interface TotalSalaryRoomService extends IService<TotalSalaryRoom> {

    PageResult<TechnicalFeeInfoVO> infoTechnical(TechnicalFeeInfoDTO technicalFeeInfoDTO);


    /**
     * 根据itemuid获取项目的技术管理费
     * @param uids
     * @return
     */
    Map<String, String> queryUsedTechnicalFeeByUids(List<String> uids);

    /**
     * 获取项目清单汇总工资
     * @param totalSalaryRoomInfoDTO
     * @return
     */
    PageResult<TotalSalaryRoomInfoVO> infoRoomSalary(TotalSalaryRoomInfoDTO totalSalaryRoomInfoDTO);

    int exportRoomSalaryCount(TotalSalaryRoomInfoDTO totalSalaryRoomInfoDTO);

    List<TotalSalaryRoomExportInfoVO> exportRoomSalaryList(TotalSalaryRoomInfoDTO totalSalaryRoomInfoDTO);

    Integer updateByItemUid(List<TotalSalaryRoom> list);
}
