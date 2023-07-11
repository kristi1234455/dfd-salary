package com.dfd.enums;

public enum RoleEnum {

    ROLE_ITEM("1","项目经理"),
    ROLE_SUB_LEADER("2","部门分管领导"),
    ROLE_FUNC_LEDAER("3","部门职能领导"),
    ROLE_DEPARTMENT("4","部门负责人"),
    ROLE_ADMIN("5","管理员");

    RoleEnum() {

    }
    RoleEnum(String code, String desc) {
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
