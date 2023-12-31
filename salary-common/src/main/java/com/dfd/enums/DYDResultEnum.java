package com.dfd.enums;

/**
 * @author yy
 * @date 2023/3/30 11:20
 */
public enum DYDResultEnum {

    SUCCESS(200,"成功"),
    ERROR(500,"失败"),
    ERROR_TOKEN(501,"失败");


    private Integer Code;
    private String Desc;

    DYDResultEnum(){}

    DYDResultEnum(Integer code, String desc) {
        Code = code;
        Desc = desc;
    }

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }
}
