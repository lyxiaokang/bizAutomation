package com.jxrt.common.constans;

import java.sql.SQLException;

public enum ProductTypeCcbscfEnum {
    /** 融信 */
    CREDIT("CREDIT", "融信"),
    /** 账款 */
    RECEIVABLE("RECEIVABLE", "账款融资"),
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
    ProductTypeCcbscfEnum(String code, String name) {
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
    public static ProductTypeCcbscfEnum valueOfCode(String code) {
        if (code == null || code.isEmpty()) {
            return UNKNOWN;
        }
        for (ProductTypeCcbscfEnum value : values()) {
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
    public static ProductTypeCcbscfEnum valueOfName(String name) {
        if (name == null || name.isEmpty()) {
            return UNKNOWN;
        }
        for (ProductTypeCcbscfEnum value : values()) {
            if (value.getName().equals(name)) {
                return value;
            }
        }
        return UNKNOWN;
    }
    
	public static void main(String[] args) throws InterruptedException, SQLException {
		
		System.out.println(ProductTypeCcbscfEnum.valueOfCode("CREDIT").getName());

	}

}
