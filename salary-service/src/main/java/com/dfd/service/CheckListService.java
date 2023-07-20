package com.dfd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.*;
import com.dfd.entity.CheckList;
import com.dfd.utils.PageResult;
import com.dfd.vo.CheckListNormalVO;
import com.dfd.vo.CheckListPartInfoVO;
import com.dfd.vo.CheckListVO;

import java.util.List;

/**
 * @author yy
 * @date 2023/6/12 16:21
 */
public interface CheckListService extends IService<CheckList> {

    /**
     * 获取个人任务待办事项
     * @param checkLisQueryDTO
     * @return
     */
    PageResult<CheckListVO> info(CheckLisQueryDTO checkLisQueryDTO);

    /**
     * 处理个人任务待办事项
     * @param checkListHandleDTO
     */
    void handle(CheckListHandleDTO checkListHandleDTO);

    /**
     * 各个模块 处理 各个模块的审核流程
     * @param partSubmitDTO
     */
    void partSubmit(CheckListPartSubmitDTO partSubmitDTO);

    /**
     * 各个模块 获取 各个模块的审核流程
     * @param partInfoDTO
     * @return
     */
    List<CheckListPartInfoVO> partInfo(CheckListPartInfoDTO partInfoDTO);
}
