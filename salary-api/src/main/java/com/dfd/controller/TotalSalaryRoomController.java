package com.dfd.controller;

import cn.hutool.core.util.PageUtil;
import com.dfd.dto.*;
import com.dfd.service.TotalSalaryRoomService;
import com.dfd.utils.DFDResult;
import com.dfd.utils.ExcelUtils;
import com.dfd.utils.PageResult;
import com.dfd.vo.TotalSalaryRoomExportInfoVO;
import com.dfd.vo.TotalSalaryRoomInfoVO;
import com.dfd.vo.TechnicalFeeInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * @author yy
 * @date 2023/6/12 10:28
 */
@Api(value = "项目清单", tags = {"用于项目清单汇总模块的相关接口"})
@Controller
@RequestMapping("/total/salary/room")
@CrossOrigin
public class TotalSalaryRoomController {

    @Autowired
    private TotalSalaryRoomService totalSalaryRoomService;


    @ApiOperation(value = "返回项目的技术管理费", httpMethod = "POST")
    @PostMapping("info/technical")
    @ResponseBody
    public DFDResult<PageResult<TechnicalFeeInfoVO>> infoTechnical(@RequestBody @Valid TechnicalFeeInfoDTO technicalFeeInfoDTO){
        return DFDResult.sucess(totalSalaryRoomService.infoTechnical(technicalFeeInfoDTO));
    }

    @ApiOperation(value = "获取项目清单汇总工资", httpMethod = "POST")
    @PostMapping("info")
    @ResponseBody
    public DFDResult<PageResult<TotalSalaryRoomInfoVO>> infoRoomSalary(@RequestBody @Valid TotalSalaryRoomInfoDTO totalSalaryRoomInfoDTO){
        return DFDResult.sucess(totalSalaryRoomService.infoRoomSalary(totalSalaryRoomInfoDTO));
    }

    @ApiOperation(value = "项目清单表格导出", httpMethod = "POST")
    @GetMapping("export")
    public DFDResult exportRoomSalary(TotalSalaryRoomInfoDTO totalSalaryRoomInfoDTO, HttpServletResponse response){
        int totalSize = totalSalaryRoomService.exportRoomSalaryCount(totalSalaryRoomInfoDTO);
        int pageSize = 100;
        totalSalaryRoomInfoDTO.setPageSize(pageSize);
        int pages = PageUtil.totalPage(totalSize,pageSize);
        if (totalSize > 0) {
            ExcelUtils.exportBigData(response, "项目清单",  TotalSalaryRoomExportInfoVO.class,pageSize,pages,(i)->{
                totalSalaryRoomInfoDTO.setCurrentPage(i);
                List<TotalSalaryRoomExportInfoVO> data = totalSalaryRoomService.exportRoomSalaryList(totalSalaryRoomInfoDTO);
                return data;
            });
        }
        return DFDResult.sucess();
    }
}
