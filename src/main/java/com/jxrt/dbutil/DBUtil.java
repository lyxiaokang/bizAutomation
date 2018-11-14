package com.jxrt.dbutil;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.jxrt.util.PropertiesUtil;

public class DBUtil {
	
	public static String mysql_driver = "com.mysql.jdbc.Driver";
	public static String oracle_driver = "oracle.jdbc.driver.OracleDriver";
	public static String db2_driver = "com.ibm.db2.jcc.DB2Driver";
	public static String dbType = PropertiesUtil.getOptValue("db_type");
	public static String mysql_url = "jdbc:mysql://" + PropertiesUtil.getOptValue("mysql_ip") + ":3306/mdm";
	public static String oracle_ip=null;
	public static String oracle_port=null;
	public static String oracle_instance=null;
	public static String oracle_url=null;
	public static String USERNAME=null;
	public static String PASSWORD=null;
	public static String db2_url = "jdbc:db2://" + PropertiesUtil.getOptValue("db2_ip") + ":50000/mdm";
	public static String schema=null;
	static{
		String driver_class = "";
		try {
			if (dbType.equalsIgnoreCase("mysql"))
				driver_class = mysql_driver;
			else if (dbType.equalsIgnoreCase("db2"))
				driver_class = db2_driver;
			else if (dbType.equalsIgnoreCase("oracle"))
				driver_class = oracle_driver;
			Class.forName(driver_class);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("初始化"+PropertiesUtil.getOptValue("server_ip")+"数据库连接信息");
		getOracleInstruction();
		getSchema();
	}
	
	/**
	 * 根据连接环境数据库基本参数
	 * @author 邱刚
	 * @return
	 */
    private static void getOracleInstruction(){
    	switch(PropertiesUtil.getOptValue("server_ip"))
    	{
    	case "test1.ccbscf.com":
    		oracle_ip=PropertiesUtil.getOptValue("oracle_ip_t1");
    		oracle_port=PropertiesUtil.getOptValue("oracle_port_t1");
    		oracle_instance=PropertiesUtil.getOptValue("oracle_instance_t1");
    		oracle_url = "jdbc:oracle:thin:@" + oracle_ip +":"+oracle_port+":"+oracle_instance;
    		USERNAME = PropertiesUtil.getOptValue("user_t1");
    		PASSWORD = PropertiesUtil.getOptValue("password_t1");
    		break;
    	case "test2.ccbscf.com":
    		oracle_ip=PropertiesUtil.getOptValue("oracle_ip_t2");
    		oracle_port=PropertiesUtil.getOptValue("oracle_port_t2");
    		oracle_instance=PropertiesUtil.getOptValue("oracle_instance_t2");
    		oracle_url = "jdbc:oracle:thin:@" + oracle_ip +":"+oracle_port+":"+oracle_instance;
    		USERNAME = PropertiesUtil.getOptValue("user_t2");
    		PASSWORD = PropertiesUtil.getOptValue("password_t2");
    		break;
    	case "test4.ccbscf.com":
    		oracle_ip=PropertiesUtil.getOptValue("oracle_ip_t4");
    		oracle_port=PropertiesUtil.getOptValue("oracle_port_t4");
    		oracle_instance=PropertiesUtil.getOptValue("oracle_instance_t4");
    		oracle_url = "jdbc:oracle:thin:@" + oracle_ip +":"+oracle_port+":"+oracle_instance;    		USERNAME = PropertiesUtil.getOptValue("user_t4");
    		PASSWORD = PropertiesUtil.getOptValue("password_t4");
    		break;
    	case "test5.ccbscf.com":
    		oracle_ip=PropertiesUtil.getOptValue("oracle_ip_t5");
    		oracle_port=PropertiesUtil.getOptValue("oracle_port_t5");
    		oracle_instance=PropertiesUtil.getOptValue("oracle_instance_t5");
    		oracle_url = "jdbc:oracle:thin:@" + oracle_ip +":"+oracle_port+":"+oracle_instance;
    		USERNAME = PropertiesUtil.getOptValue("user_t5");
    		PASSWORD = PropertiesUtil.getOptValue("password_t5");
    		break;
    	}

    }
    
	/**
	 * 根据连接环境返回schema
	 * @author 邱刚
	 * @return
	 */
    public static String getSchema(){
    	switch(PropertiesUtil.getOptValue("server_ip"))
    	{
    	case "test1.ccbscf.com":
    		schema="t_ccbscf.";
    		break;
    	case "test2.ccbscf.com":
    		schema="t2_ccbscf.";
    		break;
    	case "test4.ccbscf.com":
    		schema="prod_ccbscf.";
    		break;
    	case "test5.ccbscf.com":
    		schema="auto_ccbscf.";
    		break;
    	}

    	return schema;
    }
	
	public static Connection getConnection(){
		Connection conn = null;
		String url = "";
		try {
			if (dbType.equalsIgnoreCase("mysql"))
				url = mysql_url;
			else if (dbType.equalsIgnoreCase("db2"))
				url = db2_url;
			else if (dbType.equalsIgnoreCase("oracle"))
				url = oracle_url;
			System.out.println(url);
			conn = DriverManager.getConnection(url, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void closeConnection(Connection conn){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
