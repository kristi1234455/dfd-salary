package com.dfd.enums;

public enum ItemStageEnum {

    //项目阶段:1、设计；2、采购；3、制造；4、安装调试；5、安调验收；6、终验收；7、质保；

    STAGE_DESIGN("1","设计阶段"),
    STAGE_PURCHASE("2","采购阶段"),
    STAGE_MANUFACTURE("3","制造阶段"),
    STAGE_INSTALLATION("4","安装调试阶段"),
    STAGE_CHECK("5","安调验收阶段"),
    STAGE_FINAL("6","终验收阶段"),
    STAGE_GUARANTEE("7","质保阶段");

    ItemStageEnum() {

    }
    ItemStageEnum(String code, String desc) {
        Code = code;
        Desc = desc;
    }

    private String Code;
    private String Desc;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public static String getDescByCode(int code){
        for (ItemStageEnum value : ItemStageEnum.values()) {
            if (value.getCode().equals(code)) {
                return value.getDesc();
            }
        }
        return null;
    }
}
