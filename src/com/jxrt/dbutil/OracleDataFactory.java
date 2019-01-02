package com.jxrt.dbutil;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.jxrt.test.TestBase;

import net.sf.json.JSONObject;

public class OracleDataFactory implements DataFactory {
    
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
	
	/**
	 * 通过数据库查询签发金额统计
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	public static BigDecimal getIssuedMoneyByDB(String time,String department) throws SQLException{
		String issueTimeBegin=null;
		String issueTimeEnd=null;
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select sum(maturity_amount) from "+DBUtil.schema+"t_credit");
		//签发条
		sqlBuffer.append(" where  pk_credit = fk_credit_root");
		//签发时间不为空，且状态不为已撤回、已拒收、已回收、已废弃
		sqlBuffer.append(" and issue_time is not null");
		sqlBuffer.append(" and credit_state not in ('IS4', 'IS6','CAN','DIS')");
		//时间范围
		LocalDate today=LocalDate.now();
		LocalDate firstDayOfThisMonth = today.with(TemporalAdjusters.firstDayOfMonth());
		LocalDate lastDayOfThisMonth = today.with(TemporalAdjusters.lastDayOfMonth());
		DayOfWeek dayOfWeek=today.getDayOfWeek();
		LocalDate firstDayOfThisWeek=today.minusDays(dayOfWeek.getValue()-1);
		LocalDate lastDayOfThisWeek=firstDayOfThisWeek.plusDays(6);
		switch(time){
		case "Today":
			issueTimeBegin=today.toString();
			issueTimeEnd=today.plusDays(1).toString();
			break;
		case "Week":
			issueTimeBegin=firstDayOfThisWeek.toString();
			issueTimeEnd=lastDayOfThisWeek.plusDays(1).toString();
			break;
		case "Month":
			issueTimeBegin=firstDayOfThisMonth.toString();
			issueTimeEnd=lastDayOfThisMonth.plusDays(1).toString();
			break;
		case "Now":
			//七一是我党成立日
			issueTimeBegin="1921-07-01";
			issueTimeEnd=today.plusDays(1).toString();
			break;
			
		}
		sqlBuffer.append(" and issue_time >= to_date('"+issueTimeBegin+"', 'yyyy-mm-dd')");
		sqlBuffer.append(" and issue_time <= to_date('"+issueTimeEnd+"', 'yyyy-mm-dd')");
		//业务一部二部等部门过滤
		// TODO
		if(!department.equals("All")){
			sqlBuffer.append(" and fk_corp_core in ("+getFkCorpByDepartment(department)+")");
		}
		//删除标识位为0
		sqlBuffer.append(" and delete_flag = '0'");
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		try{
			PreparedStatement pstm=connection.prepareStatement(sql);
		    ResultSet rs=pstm.executeQuery(sql);
			while(rs.next()){
				BigDecimal issuedMoney=rs.getBigDecimal(1);
				if(!(issuedMoney!= null)){
					issuedMoney=BigDecimal.valueOf(0);
				}
				System.out.println(issuedMoney);
				return issuedMoney;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		
		return null;
	}
	/**
	 * 通过数据库查询部门下方企业
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */

	public static String getFkCorpByDepartment(String department) throws SQLException{
		StringBuffer fkCorpCoreListString = new StringBuffer();
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
		if(department.equals("All")){

			sqlBuffer.append("select pk_core from "+DBUtil.schema+"T_ci_core");
		}else{
			sqlBuffer.append("select distinct d.fk_corp from "+DBUtil.schema+"T_ORG_STAFF_DEPT a");
			sqlBuffer.append(" inner join "+DBUtil.schema+"t_org_staff b");
			sqlBuffer.append(" on a.fk_staff = b.PK_STAFF");
			sqlBuffer.append(" inner join "+DBUtil.schema+"t_cr_team_ccbscf c");
			sqlBuffer.append(" on b.pk_staff = c.fk_staff_cm");
			sqlBuffer.append(" inner join "+DBUtil.schema+"t_cr_corp_team d");
			sqlBuffer.append(" on c.fk_team = d.fk_team");
			sqlBuffer.append(" inner join "+DBUtil.schema+"t_ci_corp e");
			sqlBuffer.append(" on d.fk_corp = e.pk_corp");
			switch(department){
			case "Mine":
				// TODO 姓名待参数化
				sqlBuffer.append(" where a.STAFF_NAME = '"+"邱刚"+"'");
				break;
			case "DepartmentOne":
				sqlBuffer.append(" where a.DEPT_NAME = '"+"公司业务一部"+"'");
				break;
			case "DepartmentTwo":
				sqlBuffer.append(" where a.DEPT_NAME = '"+"公司业务二部"+"'");
				break;
			}
			sqlBuffer.append(" and c.leave_time > sysdate");
		}

		String sql=sqlBuffer.toString();
		System.out.println(sql);
		try{
			PreparedStatement pstm=connection.prepareStatement(sql);
		    ResultSet rs=pstm.executeQuery(sql);
		    
			while(rs.next()){
				String fkCorpCore=rs.getString(1);
				fkCorpCoreListString.append("'");
				fkCorpCoreListString.append(fkCorpCore.toString());
				fkCorpCoreListString.append("',");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		
		return fkCorpCoreListString.toString().substring(0, fkCorpCoreListString.toString().length()-1);
	}
	
	/**
	 * 通过数据库查询已融资未放款核心企业选择list
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	public static String getFNLCoreSelectFkcorpByDepartment(String department) throws SQLException{
		StringBuffer fkCorpCoreListString = new StringBuffer();
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
	
		sqlBuffer.append("select distinct fk_corp_core from "+DBUtil.schema+"t_credit c");
		sqlBuffer.append(" left outer join "+DBUtil.schema+"T_CREDIT_APPLY_TEMP t");
		sqlBuffer.append(" on c.PK_CREDIT = t.FK_CREDIT");
		sqlBuffer.append(" left outer join "+DBUtil.schema+"T_CREDIT_FINANCE_TEMP f");
		sqlBuffer.append(" on (c.PK_CREDIT = f.FK_CREDIT and c.FK_WORKFLOW = f.FK_WORKFLOW and f.DELETE_FLAG ='0')");
		sqlBuffer.append(" where c.credit_state_main in ('FI1') and c.delete_flag='0'");
		sqlBuffer.append(" and  fk_corp_core in ("+getFkCorpByDepartment(department)+")");
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		try{
			PreparedStatement pstm=connection.prepareStatement(sql);
		    ResultSet rs=pstm.executeQuery(sql);
		    
			while(rs.next()){
				String fkCorpCore=rs.getString(1);
				fkCorpCoreListString.append("'");
				fkCorpCoreListString.append(fkCorpCore.toString());
				fkCorpCoreListString.append("',");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		return fkCorpCoreListString.toString().substring(0, fkCorpCoreListString.toString().length()-1);
	}

	/**
	 * 通过数据库查询已签收未融资核心企业选择list
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	public static String getSNFCoreSelectFkcorpByDepartment(String department) throws SQLException{
		StringBuffer fkCorpCoreListString = new StringBuffer();
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
	
		sqlBuffer.append("select distinct fk_corp_core from "+DBUtil.schema+"t_credit c");
		sqlBuffer.append(" left outer join "+DBUtil.schema+"T_CREDIT_APPLY_TEMP t");
		sqlBuffer.append(" on c.PK_CREDIT = t.FK_CREDIT");
		sqlBuffer.append(" left outer join "+DBUtil.schema+"T_CREDIT_FINANCE_TEMP f");
		sqlBuffer.append(" on (c.PK_CREDIT = f.FK_CREDIT and c.FK_WORKFLOW = f.FK_WORKFLOW and f.DELETE_FLAG ='0')");
		sqlBuffer.append(" where c.credit_state_main in ('ACD') and c.delete_flag='0'");
		sqlBuffer.append(" and  fk_corp_core in ("+getFkCorpByDepartment(department)+")");
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		try{
			PreparedStatement pstm=connection.prepareStatement(sql);
		    ResultSet rs=pstm.executeQuery(sql);
		    
			while(rs.next()){
				String fkCorpCore=rs.getString(1);
				fkCorpCoreListString.append("'");
				fkCorpCoreListString.append(fkCorpCore.toString());
				fkCorpCoreListString.append("',");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		return fkCorpCoreListString.toString().substring(0, fkCorpCoreListString.toString().length()-1);
	}
	
	
	/**
	 * 通过数据库查询首页各部门的已融资未放款统计
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */

	private static List<Map<String, String>> getFNLListByDB(String department) throws SQLException{
		List<Map<String, String>> list=new ArrayList<Map<String, String>>();
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();

		sqlBuffer.append("select c.fk_corp_core,c.corp_name_hold,c.pk_credit,c.maturity_amount,c.finance_time from "+DBUtil.schema+"t_credit c");
		sqlBuffer.append(" join "+DBUtil.schema+"t_credit_finance_temp f");
		
		//表关联
		sqlBuffer.append(" on  c.pk_credit = f.fk_credit and f.delete_flag = '0'");
		
		//状态为融资中
		sqlBuffer.append(" where  c.credit_state_main in ('FI0','FI1')");
		
		//部门过滤
		if(department.equals("Mine")||department.equals("DepartmentOne")||department.equals("DepartmentTwo")){
			//如果对业务一部，业务二部，我的，使用getFkcorpByDepartment()结果

			sqlBuffer.append(" and c.fk_corp_core in ("+getFkCorpByDepartment(department)+")");
		}else if(department.equals("All")){
			//如果是所有企业，则不带入此查询条件
			sqlBuffer.append("");
		}else{
			//如果不是上述类型，直接将department当做企业名称带入进行查询

			sqlBuffer.append(" and c.fk_corp_core in ('"+department+"')");
		}
		//删除标识位为0
		sqlBuffer.append(" and c.delete_flag = '0'");
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		try{
			PreparedStatement pstm=connection.prepareStatement(sql);
		    ResultSet rs=pstm.executeQuery(sql);
			while(rs.next()){
				Map<String, String> map=new HashMap<String, String>();
				map.put("fkCorpCore",rs.getString(1));
				map.put("corpNameHold",rs.getString(2));
				map.put("pkCredit",rs.getString(3));
				map.put("maturityAmount",rs.getBigDecimal(4).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				if(rs.getString(5)!=null){
					map.put("financeTime",rs.getString(5).replaceAll("-",".").substring(0, 10));
				}else{
					map.put("financeTime","");
				}
				
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		return list;
	}
	/**
	 * 通过白条号查询元素的creditList的其它信息
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	
	public static Map<String, String> getFNLListByPkCredit(String pkCredit,String department) throws SQLException{
		List<Map<String, String>> list=getFNLListByDB(department);
		for(Map<String, String> map:list){
			if(map.get("pkCredit").equals(pkCredit)){
				return map;
			}
		}
		return null;
	}
	
	
	/**
	 * 通过数据库查询首页各部门的已融资未放款统计
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */

	private static List<Map<String, String>> getSNFListByDB(String department) throws SQLException{
		List<Map<String, String>> list=new ArrayList<Map<String, String>>();
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();

		sqlBuffer.append("select fk_corp_core,corp_name_hold,pk_credit,maturity_amount,accept_time from "+DBUtil.schema+"t_credit");
		//状态为融资中
		sqlBuffer.append(" where  credit_state_main in ('ACD')");

		//部门过滤
		if(department.equals("Mine")||department.equals("DepartmentOne")||department.equals("DepartmentTwo")){
			//如果对业务一部，业务二部，我的，使用getFkcorpByDepartment()结果
			sqlBuffer.append(" and fk_corp_core in ("+getFkCorpByDepartment(department)+")");
		}else if(department.equals("All")){
			//如果是所有企业，则不带入此查询条件
			sqlBuffer.append("");
		}else{
			//如果不是上述类型，直接将department当做企业名称带入进行查询
			sqlBuffer.append(" and fk_corp_core in ('"+department+"')");
		}
		//删除标识位为0
		sqlBuffer.append(" and delete_flag = '0'");
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		try{
			PreparedStatement pstm=connection.prepareStatement(sql);
		    ResultSet rs=pstm.executeQuery(sql);
			while(rs.next()){
				Map<String, String> map=new HashMap<String, String>();
				map.put("fkCorpCore",rs.getString(1));
				map.put("corpNameHold",rs.getString(2));
				map.put("pkCredit",rs.getString(3));
				map.put("maturityAmount",rs.getBigDecimal(4).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				if(rs.getString(5)!=null){
					map.put("acceptTime",rs.getString(5).replaceAll("-",".").substring(0, 10));
				}else{
					map.put("acceptTime","");
				}
				
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		return list;
	}
	/**
	 * 通过白条号查询元素的creditList的其它信息
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	
	public static Map<String, String> getSNFListByPkCredit(String pkCredit,String department) throws SQLException{
		List<Map<String, String>> list=getSNFListByDB(department);
		for(Map<String, String> map:list){
			if(map.get("pkCredit").equals(pkCredit)){
				return map;
			}
		}
		return null;
	}
	

	/**
	 * 通过企业名称查询企业编号
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	public static String getPkCorpByCorpName(String corpName) throws SQLException{
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select pk_corp from "+DBUtil.schema+"t_ci_corp");
		sqlBuffer.append(" where  corp_name = '"+corpName+"'");
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		try{
			PreparedStatement pstm=connection.prepareStatement(sql);
		    ResultSet rs=pstm.executeQuery(sql);
			while(rs.next()){
				String pkCorp=rs.getString(1);
				return pkCorp;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		return null;
	}
	
	/**
	 * 通过企业简称查询企业编号
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	public static String getPkCorpByCorpShortName(String corpShortname) throws SQLException{
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select pk_corp from "+DBUtil.schema+"t_ci_corp");
		sqlBuffer.append(" where  corp_shortname = '"+corpShortname+"'");
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		try{
			PreparedStatement pstm=connection.prepareStatement(sql);
		    ResultSet rs=pstm.executeQuery(sql);
			while(rs.next()){
				String pkCorp=rs.getString(1);
				return pkCorp;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		return null;
	}
	
	/**
	 * 通过企业编号查询企业名称
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	public static String getCorpNameByPkCorp(String pkCorp) throws SQLException{
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select corp_name from "+DBUtil.schema+"t_ci_corp");
		sqlBuffer.append(" where  pk_corp = '"+pkCorp+"'");
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		try{
			PreparedStatement pstm=connection.prepareStatement(sql);
		    ResultSet rs=pstm.executeQuery(sql);
			while(rs.next()){
				String corpName=rs.getString(1);
				return corpName;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		return null;
	}
	
	
	/**
	 * 查询给定状态的白条数量
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	public static int countCredit(List<String> creditStateList,String corpNameCore,
			String corpName,String productTypeCcbscf,LocalDate reddemDateBegin,LocalDate reddemDateEnd) throws SQLException{
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select count(*) from "+DBUtil.schema+"t_credit");
		sqlBuffer.append(" where  credit_state in (");
		for (String creditState:creditStateList){
			sqlBuffer.append("'"+creditState+"',");
		}
		sqlBuffer.deleteCharAt(sqlBuffer.length()-1);
		sqlBuffer.append(")");
		if(corpNameCore!=null){
			sqlBuffer.append(" and corp_name_core like '%"+corpNameCore+"'");
		}
		if(corpName!=null){
			sqlBuffer.append(" and corp_name_accept like '%"+corpName+"'");
		}
		if(productTypeCcbscf!=null){
			sqlBuffer.append(" and PRODUCT_TYPE_CCBSCF ='"+productTypeCcbscf+"'");
		}
		if(reddemDateBegin!=null){
			sqlBuffer.append(" and (maturity_date + GRACE_PERIOD) >=date'"+reddemDateBegin+"'");
		}
		if(reddemDateEnd!=null){
			sqlBuffer.append(" and (maturity_date + GRACE_PERIOD) <=date'"+reddemDateEnd+"'");
		}
		sqlBuffer.append(" and delete_flag !=1");
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		try{
			PreparedStatement pstm=connection.prepareStatement(sql);
		    ResultSet rs=pstm.executeQuery(sql);
			while(rs.next()){
				int count=rs.getInt(1);
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		return 0 ;
	}

	/**
	 * 查询付款通知书的白条list
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	public static List<Map<String, String>> listRedeemCredit(List<String> creditStateList,String corpNameCore,
			String corpName,String productTypeCcbscf,LocalDate reddemDateBegin,LocalDate reddemDateEnd) throws SQLException{
		List<Map<String, String>> list=new ArrayList<Map<String, String>>();
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select pk_credit,corp_name_core,corp_name_accept,product_type_ccbscf,issue_time,maturity_amount,(maturity_date+GRACE_PERIOD) as redeem_date,"
				+ "REDEEM_AMOUNT_SUM,(maturity_amount-REDEEM_AMOUNT_SUM) as redeem_amount,credit_state from "+DBUtil.schema+"t_credit");
		sqlBuffer.append(" where  credit_state in (");
		for (String creditState:creditStateList){
			sqlBuffer.append("'"+creditState+"',");
		}
		sqlBuffer.deleteCharAt(sqlBuffer.length()-1);
		sqlBuffer.append(")");
		if(corpNameCore!=null){
			sqlBuffer.append(" and corp_name_core like '%"+corpNameCore+"%'");
		}
		if(corpName!=null){
			sqlBuffer.append(" and corp_name_accept like '%"+corpName+"%'");
		}
		if(productTypeCcbscf!=null){
			sqlBuffer.append(" and PRODUCT_TYPE_CCBSCF ='"+productTypeCcbscf+"'");
		}
		if(reddemDateBegin!=null){
			sqlBuffer.append(" and (maturity_date + GRACE_PERIOD) >=date'"+reddemDateBegin+"'");
		}
		if(reddemDateEnd!=null){
			sqlBuffer.append(" and (maturity_date + GRACE_PERIOD) <=date'"+reddemDateEnd+"'");
		}
		sqlBuffer.append(" and delete_flag !=1 order by maturity_date nulls last,sequence_no desc");
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		try{
			PreparedStatement pstm=connection.prepareStatement(sql);
		    ResultSet rs=pstm.executeQuery(sql);
			while(rs.next()){
				Map<String, String> map=new HashMap<String, String>();
				map.put("pkCredit",rs.getString("pk_credit"));
				map.put("corpNameCore",rs.getString("corp_name_core"));
				map.put("corpName",rs.getString("corp_name_accept"));
				map.put("productTypeCcbscf",rs.getString("product_type_ccbscf"));
				map.put("issueTime",rs.getString("issue_time").substring(0,10));
				map.put("maturityAmount",rs.getBigDecimal("maturity_amount").setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				map.put("redeemDate",rs.getString("redeem_date").substring(0,10));
				map.put("redeemedAmount",rs.getBigDecimal("REDEEM_AMOUNT_SUM").setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				map.put("redeemAmount",rs.getBigDecimal("redeem_amount").setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				map.put("creditState",rs.getString("credit_state"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		return list;
	}
	
	/**
	 * 查询付款通知书的白条list
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	public static List<Map<String, String>> listRedeemCreditFinanced(List<String> creditStateList,String corpNameCore,
			String corpName,String productTypeCcbscf,LocalDate reddemDateBegin,LocalDate reddemDateEnd) throws SQLException{
		List<Map<String, String>> list=new ArrayList<Map<String, String>>();
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select pk_credit,corp_name_core,corp_name_accept,product_type_ccbscf,issue_time,maturity_amount,(maturity_date+GRACE_PERIOD) as redeem_date,"
				+ "REDEEM_AMOUNT_SUM,(maturity_amount-REDEEM_AMOUNT_SUM) as redeem_amount,credit_state from "+DBUtil.schema+"t_credit");
		sqlBuffer.append(" where  credit_state in (");
		for (String creditState:creditStateList){
			sqlBuffer.append("'"+creditState+"',");
		}
		sqlBuffer.deleteCharAt(sqlBuffer.length()-1);
		sqlBuffer.append(")");
		if(corpNameCore!=null){
			sqlBuffer.append(" and corp_name_core like '%"+corpNameCore+"%'");
		}
		if(corpName!=null){
			sqlBuffer.append(" and corp_name_accept like '%"+corpName+"%'");
		}
		if(productTypeCcbscf!=null){
			sqlBuffer.append(" and PRODUCT_TYPE_CCBSCF ='"+productTypeCcbscf+"'");
		}
		if(reddemDateBegin!=null){
			sqlBuffer.append(" and (maturity_date + GRACE_PERIOD) >=date'"+reddemDateBegin+"'");
		}
		if(reddemDateEnd!=null){
			sqlBuffer.append(" and (maturity_date + GRACE_PERIOD) <=date'"+reddemDateEnd+"'");
		}
		//第一级子条必须是已融资状态
		sqlBuffer.append(" and pk_credit in(select fk_credit_parent from "+DBUtil.schema+"t_credit where credit_state ='FID')");
		sqlBuffer.append(" and delete_flag !=1 order by maturity_date nulls last,sequence_no desc");
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		try{
			PreparedStatement pstm=connection.prepareStatement(sql);
		    ResultSet rs=pstm.executeQuery(sql);
			while(rs.next()){
				Map<String, String> map=new HashMap<String, String>();
				map.put("pkCredit",rs.getString("pk_credit"));
				map.put("corpNameCore",rs.getString("corp_name_core"));
				map.put("corpName",rs.getString("corp_name_accept"));
				map.put("productTypeCcbscf",rs.getString("product_type_ccbscf"));
				map.put("issueTime",rs.getString("issue_time").substring(0,10));
				map.put("maturityAmount",rs.getBigDecimal("maturity_amount").setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				map.put("redeemDate",rs.getString("redeem_date").substring(0,10));
				map.put("redeemedAmount",rs.getBigDecimal("REDEEM_AMOUNT_SUM").setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				map.put("redeemAmount",rs.getBigDecimal("redeem_amount").setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				map.put("creditState",rs.getString("credit_state"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		return list;
	}
	/**
	 * 查询代发工资申请中白条公用条件组装方法
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	private static String getPayrollCreditUtil(String corpNameCore,String corpName,String partnerName,
			LocalDate createTimeBegin,LocalDate createTimeEnd,String state) throws SQLException{
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" where a.PRODUCT_TYPE_CCBSCF = 'CREDIT' and (c.FK_PARTNER is null or c.FK_PARTNER = 'PARTNER_CCBSCF' "
				+ "or a.IS_CHECK_PASS <> '0') and a.MARKETING_BRAND is null and c.BUSI_TYPE in ('PAYROLL_CREDIT') and a.APPLY_STATE in ('APPLY_DIRECTOR')");
		if(corpNameCore!=null){
			sqlBuffer.append(" and c.CORP_NAME_CORE like '%"+corpNameCore+"%'");
		}
		if(corpName!=null){
			sqlBuffer.append(" and a.CORP_NAME_APPLY like '%"+corpName+"%'");
		}
		if(partnerName!=null){
			sqlBuffer.append(" and c.PARTNER_NAME like '%"+partnerName+"%'");
		}
		if(createTimeBegin!=null){
			sqlBuffer.append(" and a.APPLY_TIME >=date'"+createTimeBegin+"'");
		}
		if(createTimeEnd!=null){
			sqlBuffer.append(" and a.APPLY_TIME <=date'"+createTimeEnd+"'");
		}
		if(state!=null){
			if(state=="新增中"){
				sqlBuffer.append(" and a.APPROVE_REASON like '%正在进行银行账户校验，请等待校验结果%'");
			}
			if(state=="新增失败"){
				sqlBuffer.append(" and a.APPROVE_REASON like '%条数据校验不通过，请下载工资文件查看详情%'");
			}
			if(state=="待提交"){
				sqlBuffer.append(" and a.APPROVE_RESULT is null and c.FK_PARTNER = 'PARTNER_CCBSCF' and a.IS_CHECK_PASS = '1'");
			}
			if(state=="待确认"){ 
				sqlBuffer.append(" and c.FK_PARTNER not in 'PARTNER_CCBSCF' and a.APPROVE_RESULT is null and a.IS_CHECK_PASS = '1'");
			}
			if(state=="核心企业审核不通过"){
				sqlBuffer.append(" and a.APPROVE_RESULT = 'NOPASS' ");
			}
			
		}
		sqlBuffer.append(" order by a.APPLY_TIME desc");
		return sqlBuffer.toString();
	}
	
	/**
	 * 查询代发工资申请中白条总数
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	public static int getPayrollCreditCount(String corpNameCore,String corpName,String partnerName,
			LocalDate createTimeBegin,LocalDate createTimeEnd,String state) throws SQLException{
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select count(*) from "+DBUtil.schema+"T_CREDIT_APPLY_TEMP a left outer join "+DBUtil.schema+"T_CREDIT c on a.FK_CREDIT = c.PK_CREDIT");
		sqlBuffer.append(getPayrollCreditUtil(corpNameCore,corpName,partnerName,
				createTimeBegin,createTimeEnd,state));
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		try{
			PreparedStatement pstm=connection.prepareStatement(sql);
		    ResultSet rs=pstm.executeQuery(sql);
			while(rs.next()){
				int count=rs.getInt(1);
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		return 0;
	}
	
	/**
	 * 查询代发工资申请中白条list
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	public static List<Map<String, String>> getPayrollCreditList(String corpNameCore,String corpName,String partnerName,
			LocalDate createTimeBegin,LocalDate createTimeEnd,String state) throws SQLException{
		List<Map<String, String>> list=new ArrayList<Map<String, String>>();
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select a.APPLY_AMOUNT,a.CORP_NAME_APPROVE,a.CORP_NAME_APPLY,c.PARTNER_NAME,a.APPLY_FORM,c.ISSUE_APPLY_TIME,c.product_type_ccbscf from "+DBUtil.schema+"T_CREDIT_APPLY_TEMP a left outer join "+DBUtil.schema+"T_CREDIT c on a.FK_CREDIT = c.PK_CREDIT");
		sqlBuffer.append(getPayrollCreditUtil(corpNameCore,corpName,partnerName,
				createTimeBegin,createTimeEnd,state));
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		try{
			PreparedStatement pstm=connection.prepareStatement(sql);
		    ResultSet rs=pstm.executeQuery(sql);
			while(rs.next()){
				Map<String, String> map=new HashMap<String, String>();
				map.put("corpNameCore",rs.getString("CORP_NAME_APPROVE"));
				map.put("corpName",rs.getString("CORP_NAME_APPLY"));
				map.put("partnerName",rs.getString("PARTNER_NAME"));
				map.put("createTime",rs.getString("ISSUE_APPLY_TIME"));
				map.put("paymentDetailTotalAmount",rs.getBigDecimal("APPLY_AMOUNT").setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				String applyForm=rs.getString("APPLY_FORM");
				JSONObject jsonObject=JSONObject.fromObject(applyForm);
				map.put("projectName",jsonObject.getString("projectName"));
				try{
					map.put("batchTeamGroup",jsonObject.getString("batchTeamGroup"));
				}catch(Exception e){
					map.put("batchTeamGroup","");
				}
				String payrollYearMonthBegin=new SimpleDateFormat("yyyy-MM").format(new Date(Long.parseLong(jsonObject.getString("payrollYearMonthBegin"))));
				String payrollYearMonthEnd=new SimpleDateFormat("yyyy-MM").format(new Date(Long.parseLong(jsonObject.getString("payrollYearMonthEnd"))));
				map.put("payrollDuring",payrollYearMonthBegin+"至"+payrollYearMonthEnd);
				map.put("payrollDetailCount",jsonObject.getString("payrollDetailCount")+"笔");
				map.put("productTypeCcbscf",rs.getString("product_type_ccbscf"));
				
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		return list;
	}
	
	/**
	 * 查询代发工资申请中白条金额
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	public static BigDecimal getPayrollCreditAmount(String corpNameCore,String corpName,String partnerName,
			LocalDate createTimeBegin,LocalDate createTimeEnd,String state) throws SQLException{
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select sum(c.maturity_amount) from "+DBUtil.schema+"T_CREDIT_APPLY_TEMP a left outer join "+DBUtil.schema+"T_CREDIT c on a.FK_CREDIT = c.PK_CREDIT");
		sqlBuffer.append(getPayrollCreditUtil(corpNameCore,corpName,partnerName,
				createTimeBegin,createTimeEnd,state));
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		try{
			PreparedStatement pstm=connection.prepareStatement(sql);
		    ResultSet rs=pstm.executeQuery(sql);
			while(rs.next()){
				BigDecimal amount=rs.getBigDecimal(1).setScale(2, BigDecimal.ROUND_HALF_UP);
				return amount;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		return new BigDecimal(0);
	}
	
	/**
	 * 查询代发工资申请中签发中tab白条公用条件组装方法
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	private static String getPayrollCreditIssuingUtil(String corpNameCore,String corpName,String pkCredit,String partnerName,
			LocalDate submitTimeBegin,LocalDate submitTimeEnd) throws SQLException{
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" where a.PRODUCT_TYPE_CCBSCF = 'CREDIT' and (c.FK_PARTNER is null or c.FK_PARTNER = 'PARTNER_CCBSCF' "
				+ "or a.IS_CHECK_PASS <> '0') and a.MARKETING_BRAND is null and c.BUSI_TYPE in ('PAYROLL_CREDIT') and a.APPLY_STATE "
				+ "in ('APPROVE_OPERATOR', 'APPROVE_DIRECTOR', 'APPROVE_FINAL_DIRECTOR')");
		if(corpNameCore!=null){
			sqlBuffer.append(" and c.CORP_NAME_CORE like '%"+corpNameCore+"%'");
		}
		if(corpName!=null){
			sqlBuffer.append(" and a.CORP_NAME_APPLY like '%"+corpName+"%'");
		}
		if(pkCredit!=null){
			sqlBuffer.append(" and a.FK_CREDIT like '%"+pkCredit+"%'");
		}
		if(partnerName!=null){
			sqlBuffer.append(" and c.PARTNER_NAME like '%"+partnerName+"%'");
		}
		if(submitTimeBegin!=null){
			sqlBuffer.append(" and a.SUBMIT_TIME >=date'"+submitTimeBegin+"'");
		}
		if(submitTimeEnd!=null){
			sqlBuffer.append(" and a.SUBMIT_TIME <=date'"+submitTimeEnd+"'");
		}
		sqlBuffer.append(" order by a.SUBMIT_TIME desc nulls last, a.SEQUENCE_NO desc");
		return sqlBuffer.toString();
	}
	
	/**
	 * 查询代发工资申请中签发中tab白条总数
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	public static int getPayrollCreditIssuingCount(String corpNameCore,String corpName,String pkCredit,String partnerName,
			LocalDate submitTimeBegin,LocalDate submitTimeEnd) throws SQLException{
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select count(*) from "+DBUtil.schema+"T_CREDIT_APPLY_TEMP a left outer join "+DBUtil.schema+"T_CREDIT c on a.FK_CREDIT = c.PK_CREDIT");
		sqlBuffer.append(getPayrollCreditIssuingUtil(corpNameCore,corpName,pkCredit,partnerName,
				submitTimeBegin,submitTimeEnd));
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		try{
			PreparedStatement pstm=connection.prepareStatement(sql);
		    ResultSet rs=pstm.executeQuery(sql);
			while(rs.next()){
				int count=rs.getInt(1);
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		return 0;
	}
	
	/**
	 * 查询代发工资申请中签发中tab白条list
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	public static List<Map<String, String>> getPayrollCreditIssuingList(String corpNameCore,String corpName,String pkCredit,String partnerName,
			LocalDate submitTimeBegin,LocalDate submitTimeEnd) throws SQLException{
		List<Map<String, String>> list=new ArrayList<Map<String, String>>();
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select a.APPLY_AMOUNT,a.CORP_NAME_APPROVE,a.CORP_NAME_APPLY,c.PARTNER_NAME,a.APPLY_FORM,a.SUBMIT_TIME,c.product_type_ccbscf,a.FK_CREDIT,c.CREDIT_STATE from "+DBUtil.schema+"T_CREDIT_APPLY_TEMP a left outer join "+DBUtil.schema+"T_CREDIT c on a.FK_CREDIT = c.PK_CREDIT");
		sqlBuffer.append(getPayrollCreditIssuingUtil(corpNameCore,corpName,pkCredit,partnerName,
				submitTimeBegin,submitTimeEnd));
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		try{
			PreparedStatement pstm=connection.prepareStatement(sql);
		    ResultSet rs=pstm.executeQuery(sql);
			while(rs.next()){
				Map<String, String> map=new HashMap<String, String>();
				map.put("corpNameCore",rs.getString("CORP_NAME_APPROVE"));
				map.put("corpName",rs.getString("CORP_NAME_APPLY"));
				map.put("partnerName",rs.getString("PARTNER_NAME"));
				map.put("submitTime",rs.getString("SUBMIT_TIME"));
				map.put("pkCredit",rs.getString("FK_CREDIT"));
				map.put("creditState",rs.getString("CREDIT_STATE"));
				map.put("paymentDetailTotalAmount",rs.getBigDecimal("APPLY_AMOUNT").setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				map.put("productTypeCcbscf",rs.getString("product_type_ccbscf"));
				String applyForm=rs.getString("APPLY_FORM");
				JSONObject jsonObject=JSONObject.fromObject(applyForm);
				map.put("payrollDetailCount",jsonObject.getString("payrollDetailCount")+"笔");
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		return list;
	}
	
	/**
	 * 查询代发工资申请中签发中tab白条金额
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	public static BigDecimal getPayrollCreditIssuingAmount(String corpNameCore,String corpName,String pkCredit,String partnerName,
			LocalDate submitTimeBegin,LocalDate submitTimeEnd) throws SQLException{
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select sum(c.maturity_amount) from "+DBUtil.schema+"T_CREDIT_APPLY_TEMP a left outer join "+DBUtil.schema+"T_CREDIT c on a.FK_CREDIT = c.PK_CREDIT");
		sqlBuffer.append(getPayrollCreditIssuingUtil(corpNameCore,corpName,pkCredit,partnerName,
				submitTimeBegin,submitTimeEnd));
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		try{
			PreparedStatement pstm=connection.prepareStatement(sql);
		    ResultSet rs=pstm.executeQuery(sql);
			while(rs.next()){
				BigDecimal amount=rs.getBigDecimal(1).setScale(2, BigDecimal.ROUND_HALF_UP);
				return amount;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		return new BigDecimal(0);
	}
	
	/**
	 * 查询核心企业对应的英文简称
	 * @author 邱刚
	 * @return
	 * @throws SQLException 
	 */
	public static String getShortNameENByCorpName(String corpName) throws SQLException{
		Connection connection=DBUtil.getConnection();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select CORP_SHORTNAME_EN from "+DBUtil.schema+"T_CI_CORP");
		sqlBuffer.append(" where CORP_NAME = '"+corpName+"'");
		String sql=sqlBuffer.toString();
		System.out.println(sql);
		try{
			PreparedStatement pstm=connection.prepareStatement(sql);
		    ResultSet rs=pstm.executeQuery(sql);
			while(rs.next()){
				String shortNameEN=rs.getString(1);
				return shortNameEN;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(connection);
		}
		return null;
	}
	public static void main(String[] args) throws SQLException {
			File downloadsFiles=new File(TestBase.downloadsPath);
			for(File file:downloadsFiles.listFiles()){
				file.delete();
			}
		}

}
