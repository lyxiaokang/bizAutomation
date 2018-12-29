package com.jxrt.common.constans;


public enum CreditStateEnum {
    /** 已逾期 */
    OVERDUE("RD9", "已逾期"),
    /** 待还款 */
    REDEEM("RD0", "待还款"),
    /** 付款中 */
    REDEEMING("RD1", "还款审核中"),
    /** 已付款 */
    REDEEMED("RDD", "已还款"),
    /** 待签发 */
    ISSUE("IS0", "待签发"),
    /** 签发审核中 */
    ISSUING("IS1", "签发审核中"),
    /** 供应商待签收 */
    ISSUING_ACCEPTING("IS2", "供应商待签收"),
    /** 供应商拒收中 */
    ISSUING_REFUSEING("IS3", "供应商拒收中"),
    /** 供应商已拒收 */
    ISSUING_REFUSED("IS4", "供应商已拒收"),
    /** 撤回审核中 */
    ISSUING_CANCELEING("IS5", "撤回审核中"),
    /** 已撤回 */
    ISSUING_CANCELED("IS6", "已撤回"),
    /** 已签发 */
    ISSUED("ISD", "已签发"),
    /** 已废弃 */
    DISCARD("DIS", "已废弃"),
    /** 收款期自动撤回 */
    CANCELED("CAN", "收款期自动撤回"),
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
