package com.dfd.enums;

public enum RoleEnum {

    ROLE_ITEM("1","项目经理"),
    ROLE_SUB_DIRECTOR("2","所长"),
    ROLE_SUB_LEADER("3","部门分管领导"),
    ROLE_FUNC_LEDAER("4","部门职能领导"),
    ROLE_DEPARTMENT("5","部门负责人"),
    ROLE_ADMIN("6","管理员");

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
