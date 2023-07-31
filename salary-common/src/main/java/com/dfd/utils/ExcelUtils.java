package com.dfd.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description:excel工具类
 * @author:yupanli
 * @date:2022-12-20 11:51:34
 */
@Slf4j
public class ExcelUtils {
    @SneakyThrows
    public static <T> void exportExcelData(HttpServletResponse response, String fileName, List<T> dataList, Class<T> cls) {
        Field[] fields = cls.getDeclaredFields();
        List<String> includeColumnList = Arrays.stream(fields)
                .filter(column -> null != column.getAnnotation(ExcelProperty.class))
                .map(obj -> obj.getName())
                .collect(Collectors.toList());

        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), cls)
                .includeColumnFiledNames(includeColumnList)
                //.includeColumnFieldNames(includeColumnList)
                .sheet(0).doWrite(dataList);
    }



    /**
     * 大数量导出
     * @param fileName 生成文件地址
     * @param head 表头
     * @param pageSize 页大小
     * @param pages 总页数
     * @param function 获取数据函数
     * @return
     */
    public static String exportBigData(HttpServletResponse response,String fileName, Class head, int pageSize, int pages, Function<Integer,List<?>> function) {
        Field[] fields = head.getDeclaredFields();
        List<String> includeColumnList = Arrays.stream(fields)
            .filter(column -> null != column.getAnnotation(ExcelProperty.class))
            .map(obj -> obj.getName())
            .collect(Collectors.toList());
        // 每个 sheet 数据量大小 固定最大1百万
        int sheet = 1000000;
        int sheetNum = sheet / pageSize;
        int sheetIndex = 1;
        com.alibaba.excel.ExcelWriter excelWriter = null;
        try {
            // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码
            String encodeFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + encodeFileName + ".xlsx");
            excelWriter = EasyExcel.write(response.getOutputStream(), head).includeColumnFiledNames(includeColumnList).build();
            WriteSheet writeSheet = null;
            for (int i = 1; i <= pages; i++) {
                writeSheet = EasyExcel.writerSheet(sheetIndex, fileName+sheetIndex).build();
                // 分页去数据库查询数据
                List list = function.apply(i);
                excelWriter.write(list, writeSheet);
                if(i % sheetNum == 0){
                    sheetIndex++;
                }
            }
        }catch (IOException e){
            log.info("导出 Excel错误",e);
        } finally{
            // 关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
        return fileName;
    }
}
