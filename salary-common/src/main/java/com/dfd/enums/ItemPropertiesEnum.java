package com.dfd.enums;

public enum ItemPropertiesEnum {

    ITEM_PRO_BID("1","投标项目"),
    ITEM_PRO_EPC("2","EPC项目"),
    ITEM_PRO_SCIEN("3","科研项目");

    ItemPropertiesEnum() {

    }
    ItemPropertiesEnum(String code, String desc) {
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
