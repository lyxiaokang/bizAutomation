package com.jxrt.dbutil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlDataFactory implements DataFactory {

	public int getUserIdByUserName(String userName) {
		int deviceId = 0;
		Connection conn = DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();

		sqlBuffer.append("select id from t_user ");

		sqlBuffer.append("where status=1 and name = ?");
		try {
			PreparedStatement ps = conn.prepareStatement(sqlBuffer.toString());
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				deviceId = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		return deviceId;
	}
	
	public static void main(String[] args) {
		DataFactory dataFactory = new MySqlDataFactory();
		System.out.println(dataFactory.getUserIdByUserName("autotester"));
		
	}

}
