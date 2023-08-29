package com.dfd.service.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSONObject;
import com.dfd.dto.MemberExcelImportDTO;
import com.dfd.entity.Member;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yy
 * @date 2023/8/29 15:47
 */
@Slf4j
public class MemberReadListener implements ReadListener<MemberExcelImportDTO> {
    List<MemberExcelImportDTO> list = new ArrayList<>();

    //每读一行触发一次
    @Override
    public void invoke(MemberExcelImportDTO member, AnalysisContext analysisContext) {
        log.info("表格数据读取到 ：{}", JSONObject.toJSONString(member));
        list.add(member);
    }

    //都读完后触发一次
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("表格数据读取完毕");
    }
}
