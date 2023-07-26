package com.dfd.enums;

public enum AttendanceEnum {

    ATTDANCE_SITE("1","驻现场"),
    ATTDANCE_OUT("2","项目出差"),
    TTDANCE_LOCAL("3","本地办公"),
    TTDANCE_VACATION("4","休假");

    AttendanceEnum() {

    }
    AttendanceEnum(String code, String desc) {
        Code = code;
        Desc = desc;
    }

    private String Code;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    private String Desc;
}
