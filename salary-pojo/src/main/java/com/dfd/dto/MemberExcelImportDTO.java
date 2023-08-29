package com.dfd.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class MemberExcelImportDTO {
    /**
     * 序号
     */
    @ExcelProperty(value = "序号", index = 0)
    private String id;

    /**
     * 员工编号
     */
    @ExcelProperty(value = "员工编号", index = 1)
    private String number;

    /**
     * 部门
     */
    @ExcelProperty(value = "所属部门", index = 2)
    private String room;

    /**
     * 名字
     */
    @ExcelProperty(value = "员工姓名", index = 3)
    private String name;

    /**
     * 性别
     */
    @ExcelProperty(value = "性别", index = 4)
    private String gender;


    /**
     * 岗位名称:1、总承包管理；2、总承包管理、工艺设计；3、非标设计、机械化；4、非标机械设计；5、自动化设计；6、技术档案资料；7、技术档案资料
     */
    @ExcelProperty(value = "岗位名称", index = 5)
    private String postTitle;

    /**
     * 部门所室：1、项目管理所；2、经营所；3、设计所；4、工程所；5、主师室；6、设计所所长；7、工程所所长；8、项目管理所所长；9、总工程师；10、助理总经理；11、部门副总经理；12、部门总经理
     */
    @ExcelProperty(value = "所室分类", index = 6)
    private String department;

    /**
     * 职称：1、高级工程师；2、工程师；3、馆员；4、技术员；5、研究员级高级工程师；6、助理工程师
     */
    @ExcelProperty(value = "职称", index = 7)
    private String post;

    /**
     * 技术职务：1、见习生；2、设计经理、审核人；3、设计经理、专业负责人；4、设计人；5、审核人；6、项目管理；7、项目经理、审核人；8、校对人；9、专业负责人；10、专业工程师；11、专业经理
     */
    @ExcelProperty(value = "技术职务", index = 8)
    private String technicalPosition;

    /**
     * 职级：1、助理工程师；2、二级工程师；3、一级工程师；4、主任工程师；5、实习设计师；6、助理设计师；7、二级设计师；8、一级设计师；9、主任设计师；10、见习本科；11、助理管理师；12、技术管理；
     */
    @ExcelProperty(value = " 职级", index = 9)
    private String ranks;

   }