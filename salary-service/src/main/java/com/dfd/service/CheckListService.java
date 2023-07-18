package com.dfd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.*;
import com.dfd.entity.CheckList;
import com.dfd.utils.PageResult;
import com.dfd.vo.CheckListNormalVO;
import com.dfd.vo.CheckListVO;

/**
 * @author yy
 * @date 2023/6/12 16:21
 */
public interface CheckListService extends IService<CheckList> {

    PageResult<CheckListVO> info(CheckLisQueryDTO checkLisQueryDTO);

    CheckListNormalVO infoNormal(CheckListNormalDTO normalDTO);

    void handle(CheckListHandleDTO checkListHandleDTO);

    void partSubmit(CheckListPartSubmitDTO partSubmitDTO);

    void partHandle(CheckListPartHandleDTO partHandleDTO);

}
