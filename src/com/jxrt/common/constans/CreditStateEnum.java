package com.jxrt.common.constans;


public enum CreditStateEnum {
    /** 已逾期 */
    OVERDUE("RD9", "已逾期"),
    /** 待付款 */
    REDEEM("RD0", "待付款"),
    /** 付款中 */
    REDEEMING("RD1", "付款中"),
    /** 已签发 */
    ISSUED("ISD", "已签发"),
    /** 未知 */
    UNKNOWN("UNKNOWN", "未知");

    /**
     * 枚举编码
     */
    private final String code;

    /**
     * 枚举名称
     */
    private final String name;

    /**
     * 构造函数
     * 
     * @param code
     * @param name
     */
    CreditStateEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    /**
     * 获取枚举编码
     * 
     * @return String
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取枚举名称
     * 
     * @return String
     */
    public String getName() {
        return name;
    }
    
    /**
     * 根据枚举编码返回枚举对象
     * 
     * @param code
     * @return ScaleEnum
     */
    public static CreditStateEnum valueOfCode(String code) {
        if (code == null || code.isEmpty()) {
            return UNKNOWN;
        }
        for (CreditStateEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return UNKNOWN;
    }

    /**
     * 根据枚举名称返回枚举对象
     * 
     * @param name
     * @return ScaleEnum
     */
    public static CreditStateEnum valueOfName(String name) {
        if (name == null || name.isEmpty()) {
            return UNKNOWN;
        }
        for (CreditStateEnum value : values()) {
            if (value.getName().equals(name)) {
                return value;
            }
        }
        return UNKNOWN;
    }

}
