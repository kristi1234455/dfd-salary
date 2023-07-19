package com.dfd.enums;

/**
 * @author yy
 * @date 2023/6/26 9:10
 */
public enum TaskEnum {

//    审核任务状态：0，未审核，1，审核通过，2，不通过

    TASK_UNDO(0,"未审核"),

    TASK_PASS(1,"审核通过"),

    TASK_UNPASS(2,"不通过");

    TaskEnum() {

    }
    TaskEnum(Integer code, String desc) {
        Code = code;
        Desc = desc;
    }

    private Integer Code;

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }

    private String Desc;

}
