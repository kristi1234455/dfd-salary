package com.dfd.dto;

import com.dfd.utils.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author yy
 * @date 2023/6/8 16:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel
public class ItemMemberAddListDTO extends Query implements Serializable {

    /**
     * itemUid
     */
    @ApiModelProperty(value = "itemUid", name = "itemUid", required = true)
    @NotBlank(message = "项目uid不能为空")
    private String itemUid;

    /**
     * 项目阶段:1、设计；2、采购；3、制造；4、安装调试；5、安调验收；6、终验收；7、质保；8、已完结；
     */
    @ApiModelProperty(value = "项目阶段", name = "itemStage")
    private Integer itemStage;

    /**
     * 当前登录者工号
     */
    @ApiModelProperty(value = "当前登录者工号",name = "currentUserNumber")
    private String currentUserNumber;

    /**
     * 项目成员
     */
    @ApiModelProperty(value = "项目成员对象", name = "itemMemberDTOS")
    @NotNull(message = "项目成员对象不能为空")
    @Size(min=1,message = "项目成员对象长度最小为1")
    private List<ItemMemberDTO> itemMemberDTOS;

}
